package com.monitor.vessel.Model.Dto;

import com.monitor.vessel.Model.Vessel;
import com.monitor.vessel.Model.VesselPosition;

import java.time.Instant;

public record AisPositionDto(
        Long mmsi,
        String name,
        Double latitude,
        Double longitude,
        Double courseOverGround,
        Double speedOverGround,
        Double trueHeading,
        Integer shipType,
        Integer navigationalStatus,
        String stream,
        String msgtime
) {
    public Vessel toVessel() {
        return Vessel.builder()
                .mmsi(mmsi)
                .name(name)
                .shipType(shipType)
                .build();
    }

    public VesselPosition toPosition(Vessel vessel) {
        return VesselPosition.builder()
                .vessel(vessel)
                .latitude(latitude)
                .longitude(longitude)
                .courseOverGround(courseOverGround)
                .speedOverGround(speedOverGround)
                .trueHeading(trueHeading)
                .navigationalStatus(navigationalStatus)
                .stream(stream)
                .msgtime(msgtime != null ? Instant.parse(msgtime) : null)
                .build();
    }
}