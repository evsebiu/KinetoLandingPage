package com.example.KinetoWebsite.UnitTests.UnitTestingService;

import com.example.KinetoWebsite.Exceptions.DuplicateResourceException;
import com.example.KinetoWebsite.Exceptions.IllegalArgumentException;
import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import com.example.KinetoWebsite.Model.Entity.ServiceDetails;
import com.example.KinetoWebsite.Model.Mapper.ServiceDetailsMapper;
import com.example.KinetoWebsite.Repository.ServiceDetailsRepository;
import com.example.KinetoWebsite.Service.ServiceDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceDetailsServiceTest {

    @Mock
    private ServiceDetailsRepository serviceDetailsRepo;

    @Mock
    private ServiceDetailsMapper serviceDetailsMapper;

    @InjectMocks
    private ServiceDetailsServiceImpl serviceDetailsService;

    private ServiceDetailsDTO sampleDTO;
    private ServiceDetails sampleEntity;

    @BeforeEach
    void setUp() {
        // Initialize fresh data before each test to avoid side effects
        sampleDTO = new ServiceDetailsDTO();
        sampleDTO.setNumeServiciu("Kinetotherapy");
        sampleDTO.setDescriereServiciu("Recovery description");
        sampleDTO.setPretServiciu(150);
        sampleDTO.setDurataServiciu(50);
        sampleDTO.setContact("0700123456");

        sampleEntity = new ServiceDetails();
        sampleEntity.setId(1L);
        sampleEntity.setNumeServiciu("Kinetotherapy");
        sampleEntity.setDescriereServiciu("Recovery description");
        sampleEntity.setPretServiciu(150);
        sampleEntity.setDurataServiciu(50);
        sampleEntity.setContact("0700123456");
    }

    // --- GET Methods ---

    @Test
    @DisplayName("Should return list of DTOs when services exist")
    void getAllServices_WhenServicesExist_ReturnsDtoList() {
        // Arrange
        when(serviceDetailsRepo.findAll()).thenReturn(List.of(sampleEntity));
        when(serviceDetailsMapper.toDTO(sampleEntity)).thenReturn(sampleDTO);

        // Act
        List<ServiceDetailsDTO> result = serviceDetailsService.getAllServices();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(sampleDTO.getNumeServiciu(), result.get(0).getNumeServiciu());
        verify(serviceDetailsRepo).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no services exist")
    void getAllServices_WhenNoServices_ReturnsEmptyList() {
        // Arrange
        when(serviceDetailsRepo.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<ServiceDetailsDTO> result = serviceDetailsService.getAllServices();

        // Assert
        assertTrue(result.isEmpty());
        verify(serviceDetailsMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Should return ServiceDTO when searching by valid name")
    void getServiceByName_WhenExists_ReturnsOptionalDto() {
        // Arrange
        String name = "Kinetotherapy";
        when(serviceDetailsRepo.findByNumeServiciu(name)).thenReturn(Optional.of(sampleEntity));
        when(serviceDetailsMapper.toDTO(sampleEntity)).thenReturn(sampleDTO);

        // Act
        Optional<ServiceDetailsDTO> result = serviceDetailsService.getServiceByName(name);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleDTO, result.get());
    }

    @Test
    @DisplayName("Should return filtered list when searching by price")
    void getServiceByPrice_ReturnsDtoList() {
        // Arrange
        int price = 150;
        when(serviceDetailsRepo.findByPretServiciu(price)).thenReturn(List.of(sampleEntity));
        when(serviceDetailsMapper.toDTO(sampleEntity)).thenReturn(sampleDTO);

        // Act
        List<ServiceDetailsDTO> result = serviceDetailsService.getServiceByPrice(price);

        // Assert
        assertEquals(1, result.size());
        assertEquals(price, result.get(0).getPretServiciu());
    }

    @Test
    @DisplayName("Should return filtered list when searching by duration")
    void getServiceByDuration_ReturnsDtoList() {
        // Arrange
        int duration = 50;
        when(serviceDetailsRepo.findByDurataServiciu(duration)).thenReturn(List.of(sampleEntity));
        when(serviceDetailsMapper.toDTO(sampleEntity)).thenReturn(sampleDTO);

        // Act
        List<ServiceDetailsDTO> result = serviceDetailsService.getServiceByDuration(duration);

        // Assert
        assertEquals(1, result.size());
        assertEquals(duration, result.get(0).getDurataServiciu());
    }

    // --- CREATE Method ---

    @Test
    @DisplayName("Should save and return DTO when creating new unique service")
    void createService_HappyPath_ReturnsDto() {
        // Arrange
        when(serviceDetailsRepo.findByNumeServiciu(sampleDTO.getNumeServiciu())).thenReturn(Optional.empty());
        when(serviceDetailsMapper.toEntity(sampleDTO)).thenReturn(sampleEntity);
        when(serviceDetailsRepo.save(sampleEntity)).thenReturn(sampleEntity);
        when(serviceDetailsMapper.toDTO(sampleEntity)).thenReturn(sampleDTO);

        // Act
        ServiceDetailsDTO result = serviceDetailsService.createService(sampleDTO);

        // Assert
        assertNotNull(result);
        assertEquals(sampleDTO.getNumeServiciu(), result.getNumeServiciu());

        // Verify the save was called
        verify(serviceDetailsRepo).save(sampleEntity);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when input DTO is null")
    void createService_NullInput_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> serviceDetailsService.createService(null));

        assertEquals("Service details cannot be null.", exception.getMessage());
        verify(serviceDetailsRepo, never()).save(any());
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when service name exists")
    void createService_DuplicateName_ThrowsException() {
        // Arrange
        when(serviceDetailsRepo.findByNumeServiciu(sampleDTO.getNumeServiciu()))
                .thenReturn(Optional.of(sampleEntity));

        // Act & Assert
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> serviceDetailsService.createService(sampleDTO));

        assertEquals("Service already exists.", exception.getMessage());
        // Verify we never attempted to convert or save
        verify(serviceDetailsMapper, never()).toEntity(any());
        verify(serviceDetailsRepo, never()).save(any());
    }

    // --- UPDATE Method ---

    @Test
    @DisplayName("Should update fields and save when ID exists")
    void updateService_ValidId_UpdatesAndReturnsDto() {
        // Arrange
        Long id = 1L;
        ServiceDetailsDTO updateInfo = new ServiceDetailsDTO();
        updateInfo.setNumeServiciu("New Name");
        updateInfo.setPretServiciu(999);
        // Assuming other fields are null or generic in the update DTO

        // The entity found in DB
        ServiceDetails existingEntity = new ServiceDetails();
        existingEntity.setId(id);
        existingEntity.setNumeServiciu("Old Name");
        existingEntity.setPretServiciu(100);

        when(serviceDetailsRepo.findById(id)).thenReturn(Optional.of(existingEntity));

        // We mock the save to return whatever is passed to it (or the modified entity)
        when(serviceDetailsRepo.save(any(ServiceDetails.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Mock mapper response
        ServiceDetailsDTO expectedResponse = new ServiceDetailsDTO();
        expectedResponse.setNumeServiciu("New Name");
        when(serviceDetailsMapper.toDTO(any(ServiceDetails.class))).thenReturn(expectedResponse);

        // Act
        ServiceDetailsDTO result = serviceDetailsService.updateService(id, updateInfo);

        // Assert
        assertNotNull(result);

        // Captor: Verify the entity passed to save() actually has the NEW values
        ArgumentCaptor<ServiceDetails> captor = ArgumentCaptor.forClass(ServiceDetails.class);
        verify(serviceDetailsRepo).save(captor.capture());

        ServiceDetails capturedEntity = captor.getValue();
        assertEquals("New Name", capturedEntity.getNumeServiciu(), "Name should be updated before saving");
        assertEquals(999, capturedEntity.getPretServiciu(), "Price should be updated before saving");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when updating non-existent ID")
    void updateService_InvalidId_ThrowsException() {
        // Arrange
        Long invalidId = 99L;
        when(serviceDetailsRepo.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> serviceDetailsService.updateService(invalidId, sampleDTO));

        assertTrue(exception.getMessage().contains("Service not found with id: " + invalidId));
        verify(serviceDetailsRepo, never()).save(any());
    }

    // --- DELETE Method ---

    @Test
    @DisplayName("Should call deleteById on repository")
    void deleteService_CallsRepository() {
        // Arrange
        Long id = 1L;

        // Act
        serviceDetailsService.deleteService(id);

        // Assert
        verify(serviceDetailsRepo, times(1)).deleteById(id);
    }
}