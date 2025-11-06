package com.example.KinetoWebsite.UnitTestingController;


import com.example.KinetoWebsite.Controller.AdminController;
import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import com.example.KinetoWebsite.Model.Entity.ServiceDetails;
import com.example.KinetoWebsite.Service.ServiceDetailsService;
import com.example.KinetoWebsite.Service.ServiceDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private ServiceDetailsServiceImpl serviceDetailsService;

    @InjectMocks
    private AdminController adminController;

    private ServiceDetailsDTO serviceDetailsDTO;
    private ServiceDetailsDTO createdServiceDTO;
    private ServiceDetailsDTO updatedServiceDTO;

    @BeforeEach
    void setUp(){

        //create de obiecte
        serviceDetailsDTO = new ServiceDetailsDTO();
        serviceDetailsDTO.setNumeServiciu("Test Service");
        serviceDetailsDTO.setDescriereServiciu("Test Service");
        serviceDetailsDTO.setPretServiciu(100);

        createdServiceDTO = new ServiceDetailsDTO();
        createdServiceDTO.setNumeServiciu("Test Service");
        createdServiceDTO.setDescriereServiciu("Test Description");
        createdServiceDTO.setPretServiciu(100);

        updatedServiceDTO = new ServiceDetailsDTO();
        updatedServiceDTO.setNumeServiciu("Test Service");
        updatedServiceDTO.setDescriereServiciu("Test update");
        updatedServiceDTO.setPretServiciu(150);
    }

    @Test
    void createService_ShouldReturnCreatedService(){
        //Arrange
        when(serviceDetailsService.createService(serviceDetailsDTO)).thenReturn(createdServiceDTO);


        // Act
        ResponseEntity<ServiceDetailsDTO> response = adminController.createService(serviceDetailsDTO);

        //assert

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Service", response.getBody().getNumeServiciu());
        assertEquals("Test Description", response.getBody().getDescriereServiciu());
        assertEquals(100, response.getBody().getPretServiciu());

        verify(serviceDetailsService, times(1)).createService(serviceDetailsDTO);
    }

    @Test
    void updateService_ShouldReturnUpdatedService(){
        //Arrange
        Long serviceId= 1L;
        when(serviceDetailsService.updateService(eq(serviceId), any(ServiceDetailsDTO.class)))
                .thenReturn(updatedServiceDTO);


        //ACT
        ResponseEntity<ServiceDetailsDTO> response = adminController.updateService(serviceId, serviceDetailsDTO);

        //assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Service", response.getBody().getNumeServiciu());
        assertEquals("Test update", response.getBody().getDescriereServiciu());
        assertEquals(150, response.getBody().getPretServiciu());

        verify(serviceDetailsService, times(1)).updateService(serviceId, serviceDetailsDTO);
    }

    @Test
    void deleteService_ShouldReturnCallServiceAndReturnOk(){
        //arrange
        Long serviceId = 1L;
        doNothing().when(serviceDetailsService).deleteService(serviceId);

        //Act
        ResponseEntity<ServiceDetailsDTO> response = adminController.deleteService(serviceId);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(serviceDetailsService, times(1)).deleteService(serviceId);
    }

    @Test
    void getAllServicesAdmin_ShouldReturnListOfServices(){
        //Arrange
        ServiceDetailsDTO service1 = new ServiceDetailsDTO();
        service1.setNumeServiciu("Service 1");
        service1.setDescriereServiciu("Description 1");
        service1.setPretServiciu(100);

        ServiceDetailsDTO service2 = new ServiceDetailsDTO();
        service2.setNumeServiciu("Service 2");
        service2.setDescriereServiciu("Description 2");
        service2.setPretServiciu(150);

        List<ServiceDetailsDTO> expectedServices = Arrays.asList(service1, service2);
        when(serviceDetailsService.getAllServices()).thenReturn(expectedServices);


        //Act
        List<ServiceDetailsDTO> result = adminController.getAllServicesAdmin();

        //Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Service 1", result.get(0).getNumeServiciu());
        assertEquals("Service 2", result.get(1).getNumeServiciu());

        verify(serviceDetailsService, times(1)).getAllServices();
    }

    @Test
    void getAllServicesAdmin_WhenNoServices_ShouldReturnEmptyList(){
        //Arrange

        List<ServiceDetailsDTO> emptyList = List.of();
        when(serviceDetailsService.getAllServices()).thenReturn(emptyList);

        //Act
        List<ServiceDetailsDTO> result = adminController.getAllServicesAdmin();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(serviceDetailsService, times(1)).getAllServices();
    }

    @Test
    void createService_WithNullInput_ShouldHandleGracefully(){

        //Arrange
        when(serviceDetailsService.createService(null)).thenReturn(null);

        //Act
        ResponseEntity<ServiceDetailsDTO> response = adminController.createService(null);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        verify(serviceDetailsService, times(1)).createService(null);
    }
}