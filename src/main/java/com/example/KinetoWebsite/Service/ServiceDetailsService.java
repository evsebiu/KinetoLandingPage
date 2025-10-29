package com.example.KinetoWebsite.Service;

import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import com.example.KinetoWebsite.Model.Entity.ServiceDetails;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


public interface ServiceDetailsService {

    // GET
    List<ServiceDetailsDTO> getAllServices();
    Optional<ServiceDetailsDTO> getServiceByName(String numeServiciu);
    List<ServiceDetailsDTO> getServiceByPrice(Integer pretServiciu);
    List<ServiceDetailsDTO> getServiceByDuration(Integer duration);

    //business logic
    ServiceDetailsDTO createService(ServiceDetailsDTO serviceDetailsDTO);
    void deleteService(Long id);
    ServiceDetailsDTO updateService(Long id, ServiceDetailsDTO serviceDetailsDTO);
}
