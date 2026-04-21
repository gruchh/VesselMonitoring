package com.monitor.vessel.Model.Dto;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VesselPositionWsDto {

    private Long mmsi;
    private Double latitude;
    private Double longitude;
    private Double courseOverGround;
    private Double speedOverGround;
    private Double trueHeading;
    private Instant msgtime;
}