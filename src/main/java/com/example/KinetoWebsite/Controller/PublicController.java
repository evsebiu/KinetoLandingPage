package com.example.KinetoWebsite.Controller;

import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import com.example.KinetoWebsite.Service.ServiceDetailsService; // Use INTERFACE
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final ServiceDetailsService serviceDetailsService; // Use interface

    public PublicController(ServiceDetailsService serviceDetailsService){
        this.serviceDetailsService = serviceDetailsService;
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceDetailsDTO>> getAllServices(){
        List<ServiceDetailsDTO> services = serviceDetailsService.getAllServices();
        return ResponseEntity.ok(services);
    }


    @GetMapping("/services/name/{name}")
    public ResponseEntity<ServiceDetailsDTO> getServiceByName(@PathVariable String name){
        Optional<ServiceDetailsDTO> service = serviceDetailsService.getServiceByName(name);
        return service.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/services/price/{price}")
    public ResponseEntity<List<ServiceDetailsDTO>> getServiceByPrice(@PathVariable Integer price){
        List<ServiceDetailsDTO> services = serviceDetailsService.getServiceByPrice(price);
        return ResponseEntity.ok(services);
    }


    @GetMapping("/services/duration/{duration}")
    public ResponseEntity<List<ServiceDetailsDTO>> getServiceByDuration(@PathVariable Integer duration){
        List<ServiceDetailsDTO> services = serviceDetailsService.getServiceByDuration(duration);
        return ResponseEntity.ok(services);
    }
}