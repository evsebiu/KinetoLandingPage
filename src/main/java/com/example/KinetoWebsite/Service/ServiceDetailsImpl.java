package com.example.KinetoWebsite.Service;


import com.example.KinetoWebsite.Repository.ServiceDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceDetailsImpl {

    private final ServiceDetailsRepository serviceDetailsRepo;

    public ServiceDetailsImpl(ServiceDetailsRepository serviceDetailsRepo){
        this.serviceDetailsRepo = serviceDetailsRepo;
    }



}
