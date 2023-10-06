package com.NewDocPatMGT.repository;

import com.NewDocPatMGT.models.Entity.DoctorQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationRepository extends JpaRepository<DoctorQualification, Long> {
}
