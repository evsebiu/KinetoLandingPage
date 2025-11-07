package com.example.KinetoWebsite.UnitTests.UnitTestingService;

import com.example.KinetoWebsite.Exceptions.DuplicateResourceException;
import com.example.KinetoWebsite.Exceptions.IllegalArgumentException;
import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import com.example.KinetoWebsite.Model.Entity.ServiceDetails;
import com.example.KinetoWebsite.Model.Mapper.ServiceDetailsMapper;
import com.example.KinetoWebsite.Repository.ServiceDetailsRepository;
import com.example.KinetoWebsite.Service.ServiceDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceDetailsServiceTest {

    @Mock
    private ServiceDetailsRepository serviceDetailsRepo;

    @Mock
    private ServiceDetailsMapper serviceDetailsMapper;

    @InjectMocks
    private ServiceDetailsServiceImpl serviceDetailsService;

    private ServiceDetailsDTO serviceDetailsDTO;
    private ServiceDetails serviceDetailsEntity;
    private ServiceDetails savedServiceDetailsEntity;
    private ServiceDetailsDTO savedServiceDetailsDTO;

    @BeforeEach
    void setUp() {
        // Setup test data
        serviceDetailsDTO = new ServiceDetailsDTO();
        serviceDetailsDTO.setNumeServiciu("Masaj");
        serviceDetailsDTO.setDescriereServiciu("Masaj relaxant");
        serviceDetailsDTO.setPretServiciu(100);
        serviceDetailsDTO.setDurataServiciu(60);
        serviceDetailsDTO.setContact("0722 123 456");

        serviceDetailsEntity = new ServiceDetails();
        serviceDetailsEntity.setNumeServiciu("Masaj");
        serviceDetailsEntity.setDescriereServiciu("Masaj relaxant");
        serviceDetailsEntity.setPretServiciu(100);
        serviceDetailsEntity.setDurataServiciu(60);
        serviceDetailsEntity.setContact("0722 123 456");

        savedServiceDetailsEntity = new ServiceDetails();
        savedServiceDetailsEntity.setId(1L);
        savedServiceDetailsEntity.setNumeServiciu("Masaj");
        savedServiceDetailsEntity.setDescriereServiciu("Masaj relaxant");
        savedServiceDetailsEntity.setPretServiciu(100);
        savedServiceDetailsEntity.setDurataServiciu(60);
        savedServiceDetailsEntity.setContact("0722 123 456");

        savedServiceDetailsDTO = new ServiceDetailsDTO();
        savedServiceDetailsDTO.setNumeServiciu("Masaj");
        savedServiceDetailsDTO.setDescriereServiciu("Masaj relaxant");
        savedServiceDetailsDTO.setPretServiciu(100);
        savedServiceDetailsDTO.setDurataServiciu(60);
        savedServiceDetailsDTO.setContact("0722 123 456");
    }

    // GET Methods Tests

    @Test
    void getAllServices_ShouldReturnListOfDTOs() {
        // Arrange
        ServiceDetails entity1 = new ServiceDetails();
        entity1.setId(1L);
        ServiceDetails entity2 = new ServiceDetails();
        entity2.setId(2L);

        List<ServiceDetails> entities = Arrays.asList(entity1, entity2);
        List<ServiceDetailsDTO> expectedDTOs = Arrays.asList(
                new ServiceDetailsDTO(), new ServiceDetailsDTO()
        );

        when(serviceDetailsRepo.findAll()).thenReturn(entities);
        when(serviceDetailsMapper.toDTO(entity1)).thenReturn(expectedDTOs.get(0));
        when(serviceDetailsMapper.toDTO(entity2)).thenReturn(expectedDTOs.get(1));

        // Act
        List<ServiceDetailsDTO> result = serviceDetailsService.getAllServices();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedDTOs, result);

        verify(serviceDetailsRepo, times(1)).findAll();
        verify(serviceDetailsMapper, times(2)).toDTO(any(ServiceDetails.class));
    }

    @Test
    void getAllServices_WhenNoServices_ShouldReturnEmptyList() {
        // Arrange
        when(serviceDetailsRepo.findAll()).thenReturn(List.of());

        // Act
        List<ServiceDetailsDTO> result = serviceDetailsService.getAllServices();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(serviceDetailsRepo, times(1)).findAll();
        verify(serviceDetailsMapper, never()).toDTO(any(ServiceDetails.class));
    }

    @Test
    void getServiceByName_WhenServiceExists_ShouldReturnDTO() {
        // Arrange
        String serviceName = "Masaj";
        when(serviceDetailsRepo.findByNumeServiciu(serviceName))
                .thenReturn(Optional.of(serviceDetailsEntity));
        when(serviceDetailsMapper.toDTO(serviceDetailsEntity)).thenReturn(serviceDetailsDTO);

        // Act
        Optional<ServiceDetailsDTO> result = serviceDetailsService.getServiceByName(serviceName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(serviceDetailsDTO, result.get());

        verify(serviceDetailsRepo, times(1)).findByNumeServiciu(serviceName);
        verify(serviceDetailsMapper, times(1)).toDTO(serviceDetailsEntity);
    }

    @Test
    void getServiceByName_WhenServiceNotExists_ShouldReturnEmpty() {
        // Arrange
        String serviceName = "NonExistent";
        when(serviceDetailsRepo.findByNumeServiciu(serviceName)).thenReturn(Optional.empty());

        // Act
        Optional<ServiceDetailsDTO> result = serviceDetailsService.getServiceByName(serviceName);

        // Assert
        assertTrue(result.isEmpty());
        verify(serviceDetailsRepo, times(1)).findByNumeServiciu(serviceName);
        verify(serviceDetailsMapper, never()).toDTO(any(ServiceDetails.class));
    }

    @Test
    void getServiceByPrice_ShouldReturnFilteredDTOs() {
        // Arrange
        Integer price = 100;
        ServiceDetails entity1 = new ServiceDetails();
        ServiceDetails entity2 = new ServiceDetails();
        List<ServiceDetails> entities = Arrays.asList(entity1, entity2);
        List<ServiceDetailsDTO> expectedDTOs = Arrays.asList(
                new ServiceDetailsDTO(), new ServiceDetailsDTO()
        );

        when(serviceDetailsRepo.findByPretServiciu(price)).thenReturn(entities);
        when(serviceDetailsMapper.toDTO(entity1)).thenReturn(expectedDTOs.get(0));
        when(serviceDetailsMapper.toDTO(entity2)).thenReturn(expectedDTOs.get(1));

        // Act
        List<ServiceDetailsDTO> result = serviceDetailsService.getServiceByPrice(price);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedDTOs, result);

        verify(serviceDetailsRepo, times(1)).findByPretServiciu(price);
        verify(serviceDetailsMapper, times(2)).toDTO(any(ServiceDetails.class));
    }

    @Test
    void getServiceByDuration_ShouldReturnFilteredDTOs() {
        // Arrange
        Integer duration = 60;
        ServiceDetails entity1 = new ServiceDetails();
        List<ServiceDetails> entities = Arrays.asList(entity1);
        List<ServiceDetailsDTO> expectedDTOs = Arrays.asList(new ServiceDetailsDTO());

        when(serviceDetailsRepo.findByDurataServiciu(duration)).thenReturn(entities);
        when(serviceDetailsMapper.toDTO(entity1)).thenReturn(expectedDTOs.get(0));

        // Act
        List<ServiceDetailsDTO> result = serviceDetailsService.getServiceByDuration(duration);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDTOs, result);

        verify(serviceDetailsRepo, times(1)).findByDurataServiciu(duration);
        verify(serviceDetailsMapper, times(1)).toDTO(entity1);
    }

    // CREATE Method Tests

    @Test
    void createService_WithValidData_ShouldReturnSavedDTO() {
        // Arrange
        when(serviceDetailsRepo.findByNumeServiciu(serviceDetailsDTO.getNumeServiciu()))
                .thenReturn(Optional.empty());
        when(serviceDetailsMapper.toEntity(serviceDetailsDTO)).thenReturn(serviceDetailsEntity);
        when(serviceDetailsRepo.save(serviceDetailsEntity)).thenReturn(savedServiceDetailsEntity);
        when(serviceDetailsMapper.toDTO(savedServiceDetailsEntity)).thenReturn(savedServiceDetailsDTO);

        // Act
        ServiceDetailsDTO result = serviceDetailsService.createService(serviceDetailsDTO);

        // Assert
        assertNotNull(result);
        assertEquals(savedServiceDetailsDTO, result);

        verify(serviceDetailsRepo, times(1)).findByNumeServiciu("Masaj");
        verify(serviceDetailsMapper, times(1)).toEntity(serviceDetailsDTO);
        verify(serviceDetailsRepo, times(1)).save(serviceDetailsEntity);
        verify(serviceDetailsMapper, times(1)).toDTO(savedServiceDetailsEntity);
    }

    @Test
    void createService_WithNullInput_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            serviceDetailsService.createService(null);
        });

        assertEquals("Service details cannot be null.", exception.getMessage());

        verify(serviceDetailsRepo, never()).findByNumeServiciu(anyString());
        verify(serviceDetailsRepo, never()).save(any(ServiceDetails.class));
    }

    @Test
    void createService_WithDuplicateName_ShouldThrowException() {
        // Arrange
        when(serviceDetailsRepo.findByNumeServiciu(serviceDetailsDTO.getNumeServiciu()))
                .thenReturn(Optional.of(serviceDetailsEntity));

        // Act & Assert
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class, () -> {
            serviceDetailsService.createService(serviceDetailsDTO);
        });

        assertEquals("Service already exists.", exception.getMessage());

        verify(serviceDetailsRepo, times(1)).findByNumeServiciu("Masaj");
        verify(serviceDetailsMapper, never()).toEntity(any(ServiceDetailsDTO.class));
        verify(serviceDetailsRepo, never()).save(any(ServiceDetails.class));
    }

    // UPDATE Method Tests

    @Test
    void updateService_WithValidData_ShouldReturnUpdatedDTO() {
        // Arrange
        Long serviceId = 1L;
        ServiceDetailsDTO updateDTO = new ServiceDetailsDTO();
        updateDTO.setNumeServiciu("Masaj Updated");
        updateDTO.setDescriereServiciu("Descriere updated");
        updateDTO.setPretServiciu(150);
        updateDTO.setDurataServiciu(90);
        updateDTO.setContact("0722 999 999");

        ServiceDetails updatedEntity = new ServiceDetails();
        updatedEntity.setId(serviceId);
        updatedEntity.setNumeServiciu("Masaj Updated");

        ServiceDetailsDTO expectedDTO = new ServiceDetailsDTO();
        expectedDTO.setNumeServiciu("Masaj Updated");

        when(serviceDetailsRepo.findById(serviceId)).thenReturn(Optional.of(serviceDetailsEntity));
        when(serviceDetailsRepo.save(serviceDetailsEntity)).thenReturn(updatedEntity);
        when(serviceDetailsMapper.toDTO(updatedEntity)).thenReturn(expectedDTO);

        // Act
        ServiceDetailsDTO result = serviceDetailsService.updateService(serviceId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDTO, result);
        assertEquals("Masaj Updated", serviceDetailsEntity.getNumeServiciu());
        assertEquals("Descriere updated", serviceDetailsEntity.getDescriereServiciu());
        assertEquals(150, serviceDetailsEntity.getPretServiciu());
        assertEquals(90, serviceDetailsEntity.getDurataServiciu());
        assertEquals("0722 999 999", serviceDetailsEntity.getContact());

        verify(serviceDetailsRepo, times(1)).findById(serviceId);
        verify(serviceDetailsRepo, times(1)).save(serviceDetailsEntity);
        verify(serviceDetailsMapper, times(1)).toDTO(updatedEntity);
    }

    @Test
    void updateService_WithNonExistingId_ShouldThrowException() {
        // Arrange
        Long nonExistingId = 999L;
        when(serviceDetailsRepo.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            serviceDetailsService.updateService(nonExistingId, serviceDetailsDTO);
        });

        assertEquals("Service not found with id: " + nonExistingId, exception.getMessage());

        verify(serviceDetailsRepo, times(1)).findById(nonExistingId);
        verify(serviceDetailsRepo, never()).save(any(ServiceDetails.class));
    }

    // DELETE Method Tests

    @Test
    void deleteService_WithValidId_ShouldCallRepository() {
        // Arrange
        Long serviceId = 1L;
        doNothing().when(serviceDetailsRepo).deleteById(serviceId);

        // Act
        serviceDetailsService.deleteService(serviceId);

        // Assert
        verify(serviceDetailsRepo, times(1)).deleteById(serviceId);
    }

    @Test
    void deleteService_WithNonExistingId_ShouldStillCallRepository() {
        // Arrange
        Long nonExistingId = 999L;
        doNothing().when(serviceDetailsRepo).deleteById(nonExistingId);

        // Act
        serviceDetailsService.deleteService(nonExistingId);

        // Assert
        verify(serviceDetailsRepo, times(1)).deleteById(nonExistingId);
    }

    // Constructor Test

    @Test
    void constructor_ShouldInitializeDependencies() {
        // This test verifies that constructor properly initializes dependencies
        ServiceDetailsRepository repo = mock(ServiceDetailsRepository.class);
        ServiceDetailsMapper mapper = mock(ServiceDetailsMapper.class);

        ServiceDetailsServiceImpl service = new ServiceDetailsServiceImpl(repo, mapper);

        assertNotNull(service);
    }
}

