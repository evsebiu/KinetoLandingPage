package com.example.KinetoWebsite.Model.Mapper;

import com.example.KinetoWebsite.Model.DTO.AppointmentDTO;
import com.example.KinetoWebsite.Model.Entity.Appointment;
import org.springframework.stereotype.Component;


@Component
public class AppointmentMapper {
    public AppointmentDTO toDTO (Appointment entity){
        if (entity==null){
            return null;
        }

        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(entity.getId());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setPatientName(entity.getPatientName());
        dto.setAdditionalInfo(entity.getAdditionalInfo());
        dto.setDate(entity.getDate());
        return dto;
    }

    public Appointment toEntity(AppointmentDTO dto){
        if (dto==null){
            return null;
        }
        Appointment entity = new Appointment();
        entity.setId(dto.getId());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setPatientName(dto.getPatientName());
        entity.setAdditionalInfo(dto.getAdditionalInfo());
        entity.setDate(dto.getDate());
        return entity;
    }

}
