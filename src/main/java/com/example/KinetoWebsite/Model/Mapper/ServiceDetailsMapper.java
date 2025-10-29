package com.example.KinetoWebsite.Model.Mapper;


import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import com.example.KinetoWebsite.Model.Entity.ServiceDetails;
import org.springframework.stereotype.Component;

@Component
public class ServiceDetailsMapper {


    public ServiceDetailsDTO toDTO(ServiceDetails entity){
        if (entity == null){
            return null;
        }

        ServiceDetailsDTO dto = new ServiceDetailsDTO();
        dto.setNumeServiciu(entity.getNumeServiciu());
        dto.setDescriereServiciu(entity.getDescriereServiciu());
        dto.setPretServiciu(entity.getPretServiciu());
        dto.setDurataServiciu(entity.getDurataServiciu());
        dto.setContact(entity.getContact());
        return dto;
    }

    public ServiceDetails toEntity(ServiceDetailsDTO dto){
        if (dto == null){
            return null;
        }

        ServiceDetails entity = new ServiceDetails();
        entity.setNumeServiciu(dto.getNumeServiciu());
        entity.setPretServiciu(dto.getPretServiciu());
        entity.setDescriereServiciu(dto.getDescriereServiciu());
        entity.setDurataServiciu(dto.getDurataServiciu());
        entity.setContact(dto.getContact());
        return entity;
    }


}
