package com.example.hms.repository;

import com.example.hms.entity.AffiliatedWith;
import com.example.hms.entity.AffiliatedWithId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AffiliatedWithRepository extends JpaRepository<AffiliatedWith, AffiliatedWithId> {

    List<AffiliatedWith> findByPhysician(int physician);

    List<AffiliatedWith> findByDepartment(int department);

    List<AffiliatedWith> findByPrimaryAffiliation(Boolean primaryAffiliation);
}
