package com.monitor.vessel.Mapper;

import com.monitor.vessel.Model.Dto.AisPositionDto;
import com.monitor.vessel.Model.Vessel;
import com.monitor.vessel.Model.VesselPosition;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class VesselMapper {

    public Vessel mapToVessel(AisPositionDto dto) {
        return Vessel.builder()
                .mmsi(dto.mmsi())
                .name(dto.name())
                .shipType(dto.shipType())
                .build();
    }

    public VesselPosition mapToPosition(AisPositionDto dto, Vessel vessel) {
        return VesselPosition.builder()
                .vessel(vessel)
                .latitude(dto.latitude())
                .longitude(dto.longitude())
                .courseOverGround(dto.courseOverGround())
                .speedOverGround(dto.speedOverGround())
                .trueHeading(dto.trueHeading())
                .navigationalStatus(dto.navigationalStatus())
                .stream(dto.stream())
                .msgtime(dto.msgtime() != null ? Instant.parse(dto.msgtime()) : null)
                .build();
    }
}