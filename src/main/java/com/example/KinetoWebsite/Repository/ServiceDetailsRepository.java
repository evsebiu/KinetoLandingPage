package com.example.KinetoWebsite.Repository;

import com.example.KinetoWebsite.Model.Entity.ServiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceDetailsRepository extends JpaRepository<ServiceDetails, Long> {

    List<ServiceDetails> findByNumeServiciu(String numeServiciu);
    List<ServiceDetails> findByPretServiciu(Integer pretServiciu);
    List<ServiceDetails> findByDurataServiciu(Integer durataServiciu);
}
