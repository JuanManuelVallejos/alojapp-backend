package com.grupo1.alojapp.Repositories;

import com.grupo1.alojapp.Model.CloudFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudFileRepository extends JpaRepository<CloudFile, Long> {

}
