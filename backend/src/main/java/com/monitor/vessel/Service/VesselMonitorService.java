package com.monitor.vessel.Service;

import com.monitor.vessel.Client.BarentswatchClient;
import com.monitor.vessel.Mapper.VesselMapper;
import com.monitor.vessel.Model.Dto.AisPositionDto;
import com.monitor.vessel.Model.Vessel;
import com.monitor.vessel.Model.VesselPosition;
import com.monitor.vessel.Repository.VesselPositionRepository;
import com.monitor.vessel.Repository.VesselRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VesselMonitorService {

    private final BarentswatchClient barentswatchClient;
    private final SimpMessagingTemplate messagingTemplate;
    private final VesselRepository vesselRepository;
    private final VesselPositionRepository vesselPositionRepository;
    private final VesselMapper vesselMapper;

    @Scheduled(fixedDelay = 2000)
    @CircuitBreaker(name = "barentswatch", fallbackMethod = "fallback")
    public void scheduledFetch() {
        AisPositionDto[] dtos = barentswatchClient.fetchLatestPositions();

        if (dtos == null || dtos.length == 0) {
            sendFromRepo();
            return;
        }

        List<VesselPosition> positions = save(dtos);
        messagingTemplate.convertAndSend("/topic/vessel/position", positions);
        log.info("Saved and pushed {} positions", positions.size());
    }

    private List<VesselPosition> save(AisPositionDto[] dtos) {
        Set<Long> mmsis = Arrays.stream(dtos)
                .map(AisPositionDto::mmsi)
                .collect(Collectors.toSet());

        Map<Long, Vessel> existingVessels = vesselRepository.findAllById(mmsis)
                .stream()
                .collect(Collectors.toMap(Vessel::getMmsi, v -> v));

        List<Vessel> newVessels = Arrays.stream(dtos)
                .filter(dto -> !existingVessels.containsKey(dto.mmsi()))
                .map(vesselMapper::mapToVessel)
                .toList();

        if (!newVessels.isEmpty()) {
            vesselRepository.saveAll(newVessels)
                    .forEach(v -> existingVessels.put(v.getMmsi(), v));
        }

        List<VesselPosition> positions = Arrays.stream(dtos)
                .map(dto -> vesselMapper.mapToPosition(dto, existingVessels.get(dto.mmsi())))
                .toList();

        return vesselPositionRepository.saveAll(positions);
    }

    public void fallback(Throwable t) {
        log.error("Circuit breaker open – AIS unavailable: {}", t.getMessage());
        sendFromRepo();
    }

    private void sendFromRepo() {
        List<VesselPosition> cached = vesselPositionRepository.findAll();
        if (!cached.isEmpty()) {
            messagingTemplate.convertAndSend("/topic/vessel/position", cached);
            log.info("Sent {} cached positions from repo", cached.size());
        } else {
            log.warn("No cached positions in repo");
        }
    }
}