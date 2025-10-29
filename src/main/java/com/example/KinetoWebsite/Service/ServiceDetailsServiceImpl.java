package com.example.KinetoWebsite.Service;

import com.example.KinetoWebsite.Exceptions.DuplicateResourceException;
import com.example.KinetoWebsite.Exceptions.IllegalArgumentException;
import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import com.example.KinetoWebsite.Model.Entity.ServiceDetails;
import com.example.KinetoWebsite.Model.Mapper.ServiceDetailsMapper;
import com.example.KinetoWebsite.Repository.ServiceDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceDetailsServiceImpl implements ServiceDetailsService {

    private final ServiceDetailsMapper serviceDetailsMapper;
    private final ServiceDetailsRepository serviceDetailsRepo;

    public ServiceDetailsServiceImpl(ServiceDetailsRepository serviceDetailsRepo,
                                     ServiceDetailsMapper serviceDetailsMapper){
        this.serviceDetailsRepo = serviceDetailsRepo;
        this.serviceDetailsMapper = serviceDetailsMapper;
    }

    // GET methods - Convert Entities to DTOs

    @Override
    public List<ServiceDetailsDTO> getAllServices(){
        return serviceDetailsRepo.findAll()
                .stream()
                .map(serviceDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ServiceDetailsDTO> getServiceByName(String numeServiciu){
        // Repository returns Optional<ServiceDetails>, convert to Optional<ServiceDetailsDTO>
        return serviceDetailsRepo.findByNumeServiciu(numeServiciu)
                .map(serviceDetailsMapper::toDTO);
    }

    @Override
    public List<ServiceDetailsDTO> getServiceByPrice(Integer pretServiciu){
        return serviceDetailsRepo.findByPretServiciu(pretServiciu)
                .stream()
                .map(serviceDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceDetailsDTO> getServiceByDuration(Integer duration){
        return serviceDetailsRepo.findByDurataServiciu(duration)
                .stream()
                .map(serviceDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }

    // business logic

    @Override
    public ServiceDetailsDTO createService(ServiceDetailsDTO serviceDetailsDTO){
        if(serviceDetailsDTO == null){
            throw new IllegalArgumentException("Service details cannot be null.");
        }

        // Fix: Repository now returns Optional<ServiceDetails> instead of Optional<ServiceDetailsDTO>
        Optional<ServiceDetails> existing = serviceDetailsRepo.findByNumeServiciu(serviceDetailsDTO.getNumeServiciu());
        if(existing.isPresent()){
            throw new DuplicateResourceException("Service already exists.");
        }

        ServiceDetails serviceDetails = serviceDetailsMapper.toEntity(serviceDetailsDTO);
        ServiceDetails savedService = serviceDetailsRepo.save(serviceDetails);

        return serviceDetailsMapper.toDTO(savedService);
    }

    @Override
    public void deleteService(Long id){
        serviceDetailsRepo.deleteById(id);
    }

    @Override
    public ServiceDetailsDTO updateService(Long id, ServiceDetailsDTO serviceDetailsDTO) {
        ServiceDetails existingService = serviceDetailsRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + id));

        // Update fields
        existingService.setNumeServiciu(serviceDetailsDTO.getNumeServiciu());
        existingService.setDescriereServiciu(serviceDetailsDTO.getDescriereServiciu());
        existingService.setPretServiciu(serviceDetailsDTO.getPretServiciu());
        existingService.setDurataServiciu(serviceDetailsDTO.getDurataServiciu());
        existingService.setContact(serviceDetailsDTO.getContact());

        ServiceDetails updatedService = serviceDetailsRepo.save(existingService);
        return serviceDetailsMapper.toDTO(updatedService);
    }
}