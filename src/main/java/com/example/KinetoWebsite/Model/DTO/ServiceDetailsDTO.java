package com.example.KinetoWebsite.Model.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ServiceDetailsDTO {

    private Long id;

    private String numeServiciu;

    private String descriereServiciu;

    private Integer pretServiciu;

    private Integer durataServiciu;

    private String contact;

}
