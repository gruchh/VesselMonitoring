package com.monitor.vessel.Repository;

import com.monitor.vessel.Model.VesselPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VesselPositionRepository extends JpaRepository<VesselPosition, Long> {

    VesselPosition findTopByVesselMmsiOrderBySavedAtDesc(Long mmsi);
    List<VesselPosition> findByVesselMmsiOrderBySavedAtDesc(Long mmsi);
}