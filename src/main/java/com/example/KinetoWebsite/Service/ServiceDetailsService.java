package com.example.KinetoWebsite.Service;

import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface ServiceDetailsService {

    // GET
    List<ServiceDetailsService> getAllServices();
    Optional<ServiceDetailsService> getServiceByName(String numeServiciu);
    List<ServiceDetailsService> getServiceByPrice(Integer pretServiciu);
    List<ServiceDetailsService> getServiceByDuration(Integer duration);

    //business logic
    ServiceDetailsDTO createService(ServiceDetailsDTO serviceDetailsDTO);
    void deleteService(Long id);
    ServiceDetailsDTO updateService(Long id, ServiceDetailsDTO serviceDetailsDTO);
}
