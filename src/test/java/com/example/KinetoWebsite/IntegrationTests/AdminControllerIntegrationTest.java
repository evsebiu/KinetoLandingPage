package com.example.KinetoWebsite.IntegrationTests;

import com.example.KinetoWebsite.Controller.AdminController;
import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import com.example.KinetoWebsite.Service.ServiceDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("POST /api/admin/services - Should create and return service")
    void createService_ShouldReturnCreatedService() throws Exception {
        // Arrange
        // Passing 'null' as the first argument (ID) because the DTO has @AllArgsConstructor
        ServiceDetailsDTO inputDTO = new ServiceDetailsDTO(
                null,
                "Masaj Relaxare",
                "Masaj de relaxare cu uleiuri esentiale",
                150,
                60,
                "0720123456"
        );

        // The service will return the DTO with an ID assigned (e.g., 1L)
        ServiceDetailsDTO responseDTO = new ServiceDetailsDTO(
                1L,
                "Masaj Relaxare",
                "Masaj de relaxare cu uleiuri esentiale",
                150,
                60,
                "0720123456"
        );

        when(serviceDetailsService.createService(any(ServiceDetailsDTO.class))).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/admin/services")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.numeServiciu").value("Masaj Relaxare"));

        verify(serviceDetailsService, times(1)).createService(any(ServiceDetailsDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("PUT /api/admin/services/{id} - Should update and return service")
    void updateService_ShouldReturnUpdatedService() throws Exception {
        // Arrange
        Long serviceId = 1L;

        ServiceDetailsDTO inputDTO = new ServiceDetailsDTO(
                null, // ID is passed in path variable, usually ignored in body or null
                "Masaj Terapeutic",
                "Masaj terapeutic pentru dureri musculare",
                200,
                90,
                "0720123456"
        );

        ServiceDetailsDTO responseDTO = new ServiceDetailsDTO(
                serviceId,
                "Masaj Terapeutic",
                "Masaj terapeutic pentru dureri musculare",
                200,
                90,
                "0720123456"
        );

        when(serviceDetailsService.updateService(eq(serviceId), any(ServiceDetailsDTO.class)))
                .thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(put("/api/admin/services/" + serviceId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeServiciu").value("Masaj Terapeutic"))
                .andExpect(jsonPath("$.pretServiciu").value(200));

        verify(serviceDetailsService, times(1)).updateService(eq(serviceId), any(ServiceDetailsDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("DELETE /api/admin/services/{id} - Should return 200 OK")
    void deleteService_ShouldReturnOk() throws Exception {
        // Arrange
        Long serviceId = 1L;
        doNothing().when(serviceDetailsService).deleteService(serviceId);

        // Act & Assert
        mockMvc.perform(delete("/api/admin/services/" + serviceId)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(serviceDetailsService, times(1)).deleteService(serviceId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /api/admin/services - Should return list of services")
    void getAllServicesAdmin_ShouldReturnListOfServices() throws Exception {
        // Arrange
        List<ServiceDetailsDTO> services = Arrays.asList(
                new ServiceDetailsDTO(1L, "Masaj Relaxare", "Descriere 1", 150, 60, "0720123456"),
                new ServiceDetailsDTO(2L, "Fizioterapie", "Descriere 2", 200, 90, "0720123457")
        );

        when(serviceDetailsService.getAllServices()).thenReturn(services);

        // Act & Assert
        mockMvc.perform(get("/api/admin/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].numeServiciu").value("Masaj Relaxare"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].numeServiciu").value("Fizioterapie"));

        verify(serviceDetailsService, times(1)).getAllServices();
    }
}