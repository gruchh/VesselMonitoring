package com.monitor.vessel.Model.Dto;

import com.monitor.vessel.Model.VesselPosition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VesselPositionWsMapper {

    public VesselPositionWsDto mapToDto(VesselPosition position) {

        return VesselPositionWsDto.builder()
                .mmsi(position.getVessel() != null ? position.getVessel().getMmsi() : null)
                .latitude(position.getLatitude())
                .longitude(position.getLongitude())
                .courseOverGround(position.getCourseOverGround())
                .speedOverGround(position.getSpeedOverGround())
                .trueHeading(position.getTrueHeading())
                .msgtime(position.getMsgtime())
                .build();
    }

    public List<VesselPositionWsDto> mapToDtoList(List<VesselPosition> positions) {
        return positions.stream()
                .map(this::mapToDto)
                .toList();
    }
}