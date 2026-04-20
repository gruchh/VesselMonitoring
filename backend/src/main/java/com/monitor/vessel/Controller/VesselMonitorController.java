package com.monitor.vessel.Controller;

import com.monitor.vessel.Model.Vessel;
import com.monitor.vessel.Model.VesselPosition;
import com.monitor.vessel.Repository.VesselPositionRepository;
import com.monitor.vessel.Repository.VesselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vessels")
@RequiredArgsConstructor
public class VesselMonitorController {

    private final VesselRepository vesselRepository;
    private final VesselPositionRepository vesselPositionRepository;

    @GetMapping
    public List<Vessel> getAllVessels() {
        return vesselRepository.findAll();
    }

    @GetMapping("/{mmsi}/position")
    public VesselPosition getLatestPosition(@PathVariable Long mmsi) {
        return vesselPositionRepository.findTopByVesselMmsiOrderBySavedAtDesc(mmsi);
    }

    @GetMapping("/{mmsi}/history")
    public List<VesselPosition> getHistory(@PathVariable Long mmsi) {
        return vesselPositionRepository.findByVesselMmsiOrderBySavedAtDesc(mmsi);
    }
}