package com.grupo1.alojapp.Repositories;

import com.grupo1.alojapp.Model.Pension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PensionRepository extends JpaRepository<Pension, Long> {

}