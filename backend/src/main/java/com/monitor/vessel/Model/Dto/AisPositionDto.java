package com.monitor.vessel.Model.Dto;

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
}