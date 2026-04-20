package com.monitor.vessel.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "vessel_position")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VesselPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mmsi")
    private Vessel vessel;

    private Double latitude;
    private Double longitude;
    private Double courseOverGround;
    private Double speedOverGround;
    private Double trueHeading;
    private Integer navigationalStatus;
    private String stream;
    private Instant msgtime;
    private Instant savedAt;

    @PrePersist
    public void prePersist() {
        savedAt = Instant.now();
    }
}