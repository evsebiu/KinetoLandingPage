package com.example.KinetoWebsite.IntegrationTests;

import com.example.KinetoWebsite.Controller.AdminController;
import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import com.example.KinetoWebsite.Service.ServiceDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServiceDetailsServiceImpl serviceDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createService_ShouldReturnCreatedService() throws Exception {
        ServiceDetailsDTO inputDTO = new ServiceDetailsDTO(
                "Masaj Relaxare",
                "Masaj de relaxare cu uleiuri esentiale",
                150,
                60,
                "0720123456"
        );

        ServiceDetailsDTO responseDTO = new ServiceDetailsDTO(
                "Masaj Relaxare",
                "Masaj de relaxare cu uleiuri esentiale",
                150,
                60,
                "0720123456"
        );

        when(serviceDetailsService.createService(any(ServiceDetailsDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/admin/services")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeServiciu").value("Masaj Relaxare"));

        verify(serviceDetailsService, times(1)).createService(any(ServiceDetailsDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateService_ShouldReturnUpdatedService() throws Exception {
        Long serviceId = 1L;
        ServiceDetailsDTO inputDTO = new ServiceDetailsDTO(
                "Masaj Terapeutic",
                "Masaj terapeutic pentru dureri musculare",
                200,
                90,
                "0720123456"
        );

        ServiceDetailsDTO responseDTO = new ServiceDetailsDTO(
                "Masaj Terapeutic",
                "Masaj terapeutic pentru dureri musculare",
                200,
                90,
                "0720123456"
        );

        when(serviceDetailsService.updateService(eq(serviceId), any(ServiceDetailsDTO.class)))
                .thenReturn(responseDTO);

        // For PUT, you might also need to use string concatenation
        mockMvc.perform(put("/api/admin/services/" + serviceId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeServiciu").value("Masaj Terapeutic"));

        verify(serviceDetailsService, times(1)).updateService(eq(serviceId), any(ServiceDetailsDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteService_ShouldReturnOk() throws Exception {
        Long serviceId = 1L;
        doNothing().when(serviceDetailsService).deleteService(serviceId);

        // Use string concatenation - this will work
        mockMvc.perform(delete("/api/admin/services/" + serviceId)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(serviceDetailsService, times(1)).deleteService(serviceId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllServicesAdmin_ShouldReturnListOfServices() throws Exception {
        List<ServiceDetailsDTO> services = Arrays.asList(
                new ServiceDetailsDTO("Masaj Relaxare", "Descriere 1", 150, 60, "0720123456"),
                new ServiceDetailsDTO("Fizioterapie", "Descriere 2", 200, 90, "0720123457")
        );

        when(serviceDetailsService.getAllServices()).thenReturn(services);

        mockMvc.perform(get("/api/admin/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].numeServiciu").value("Masaj Relaxare"))
                .andExpect(jsonPath("$[1].numeServiciu").value("Fizioterapie"));

        verify(serviceDetailsService, times(1)).getAllServices();
    }
}