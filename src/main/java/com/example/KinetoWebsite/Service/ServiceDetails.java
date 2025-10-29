package com.example.KinetoWebsite.Service;

import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface ServiceDetails {

    // GET
    List<ServiceDetails> getAllServices();
    Optional<ServiceDetails> getServiceByName(String numeServiciu);
    List<ServiceDetails> getServiceByPrice(Integer pretServiciu);
    List<ServiceDetails> getServiceByDuration(Integer duration);

    //business logic
    ServiceDetailsDTO createService(ServiceDetailsDTO serviceDetailsDTO);
    void deleteService(Long id);
    ServiceDetailsDTO updateService(Long id, ServiceDetailsDTO serviceDetailsDTO);
}
