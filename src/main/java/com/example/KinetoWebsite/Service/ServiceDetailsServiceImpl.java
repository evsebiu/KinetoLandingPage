package com.example.KinetoWebsite.Service;


import com.example.KinetoWebsite.Repository.ServiceDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceDetailsServiceImpl {

    private final ServiceDetailsRepository serviceDetailsRepo;

    public ServiceDetailsServiceImpl(ServiceDetailsRepository serviceDetailsRepo){
        this.serviceDetailsRepo = serviceDetailsRepo;
    }



}
