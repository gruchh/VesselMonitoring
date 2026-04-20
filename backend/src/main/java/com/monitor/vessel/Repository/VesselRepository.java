package com.monitor.vessel.Repository;

import com.monitor.vessel.Model.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VesselRepository extends JpaRepository<Vessel, Long> {}