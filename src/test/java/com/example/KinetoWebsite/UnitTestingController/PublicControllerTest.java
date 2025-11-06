package com.example.KinetoWebsite.UnitTestingController;

import com.example.KinetoWebsite.Controller.PublicController;
import com.example.KinetoWebsite.Model.DTO.ServiceDetailsDTO;
import com.example.KinetoWebsite.Service.ServiceDetailsService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicControllerTest {

    @Mock
    ServiceDetailsService serviceDetailsService;

    @InjectMocks
    private PublicController publicController;

    private ServiceDetailsDTO service1;
    private ServiceDetailsDTO service2;
    private List<ServiceDetailsDTO> serviceList;

    @BeforeEach
    void setUp(){
        service1 = new ServiceDetailsDTO();
        service1.setNumeServiciu("Masaj");
        service1.setDescriereServiciu("Masaj test");
        service1.setPretServiciu(50);
        service1.setDurataServiciu(30);

        service2 = new ServiceDetailsDTO();
        service2.setNumeServiciu("Masaj 2");
        service2.setDescriereServiciu("Masaj test 2");
        service2.setPretServiciu(100);
        service2.setDurataServiciu(150);

        serviceList = Arrays.asList(service1, service2);
    }

    @Test
    void getAllServices_ShouldReturnListOfServices(){
        when(serviceDetailsService.getAllServices()).thenReturn(serviceList);

        ResponseEntity<List<ServiceDetailsDTO>> response = publicController.getAllServices();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Masaj", response.getBody().get(0).getNumeServiciu());
        assertEquals("Masaj 2", response.getBody().get(1).getNumeServiciu());

        verify(serviceDetailsService, times(1)).getAllServices();
    }

    @Test
    void getAllServices_WithNoServices_ShouldReturnEmptyList(){
        List<ServiceDetailsDTO> emptyList = List.of();
        when(serviceDetailsService.getAllServices()).thenReturn(emptyList);

        ResponseEntity<List<ServiceDetailsDTO>> response = publicController.getAllServices();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(serviceDetailsService, times(1)).getAllServices();
    }

    @Test
    void getServiceByName_WhenServiceExists_ShouldReturnService(){
        String serviceName = "Masaj";
        when(serviceDetailsService.getServiceByName(serviceName)).thenReturn(Optional.of(service1));

        ResponseEntity<ServiceDetailsDTO> response = publicController.getServiceByName(serviceName);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Masaj", response.getBody().getNumeServiciu());
        assertEquals("Masaj test", response.getBody().getDescriereServiciu());
        assertEquals(50, response.getBody().getPretServiciu());

        verify(serviceDetailsService, times(1)).getServiceByName(serviceName);
    }

    @Test
    void getServiceByName_WhenServiceNotExists_ShouldReturnNotFound(){
        String serviceName= "NonExistentService";
        when(serviceDetailsService.getServiceByName(serviceName)).thenReturn(Optional.empty());

        ResponseEntity<ServiceDetailsDTO> response = publicController.getServiceByName(serviceName);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(serviceDetailsService, times(1)).getServiceByName(serviceName);
    }

    @Test
    void getServiceByName_WithNullName_ShouldReturnNotFound(){
        when(serviceDetailsService.getServiceByName(null)).thenReturn(Optional.empty());

        ResponseEntity<ServiceDetailsDTO> response = publicController.getServiceByName(null);

        // FIXED: Was testing null instead of response
        assertNotNull(response); // This should test the response, not null
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(serviceDetailsService, times(1)).getServiceByName(null);
    }

    @Test
    void getServiceByName_withEmptyName_ShouldReturnNotFound(){
        when(serviceDetailsService.getServiceByName("")).thenReturn(Optional.empty());

        ResponseEntity<ServiceDetailsDTO> response = publicController.getServiceByName("");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(serviceDetailsService, times(1)).getServiceByName("");
    }

    @Test
    void getServiceByPrice_WhenServicesExists_ShouldReturnServices(){
        Integer price = 50;
        List<ServiceDetailsDTO> filteredServices = Arrays.asList(service1);
        when(serviceDetailsService.getServiceByPrice(price)).thenReturn(filteredServices);

        ResponseEntity<List<ServiceDetailsDTO>> response = publicController.getServiceByPrice(price);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Masaj", response.getBody().get(0).getNumeServiciu());
        assertEquals(50, response.getBody().get(0).getPretServiciu());

        verify(serviceDetailsService, times(1)).getServiceByPrice(price);
    }

    @Test
    void getServiceByPrice_WhenNoServicesWithPrice_ShouldReturnEmptyList(){
        Integer price = 200;
        List<ServiceDetailsDTO> emptyList = List.of();
        when(serviceDetailsService.getServiceByPrice(price)).thenReturn(emptyList);

        ResponseEntity<List<ServiceDetailsDTO>> response = publicController.getServiceByPrice(price);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(serviceDetailsService, times(1)).getServiceByPrice(price);
    }

    @Test
    void getServiceByPrice_WithZeroPrice_ShouldHandleGracefully(){
        Integer price = 0;
        List<ServiceDetailsDTO> emptyList = List.of();
        when(serviceDetailsService.getServiceByPrice(price)).thenReturn(emptyList);

        ResponseEntity<List<ServiceDetailsDTO>> response = publicController.getServiceByPrice(price);

        assertNotNull(response);
        // FIXED: Was comparing response.getBody() instead of response.getStatusCode()
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(serviceDetailsService, times(1)).getServiceByPrice(price);
    }

    @Test
    void getServiceByPrice_WithNegativePrice_ShouldHandleGracefully(){
        Integer price = -10;
        List<ServiceDetailsDTO> emptyList = List.of();
        when(serviceDetailsService.getServiceByPrice(price)).thenReturn(emptyList);

        ResponseEntity<List<ServiceDetailsDTO>> response = publicController.getServiceByPrice(price);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // FIXED: Cannot assertNull AND assertTrue on the same body - removed assertNull
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(serviceDetailsService, times(1)).getServiceByPrice(price);
    }

    @Test
    void getServiceByDuration_WhenServicesExist_ShouldReturnServices(){
        Integer duration = 30;
        List<ServiceDetailsDTO> filteredServices = Arrays.asList(service1);
        when(serviceDetailsService.getServiceByDuration(duration)).thenReturn(filteredServices);

        ResponseEntity<List<ServiceDetailsDTO>> response = publicController.getServiceByDuration(duration);

        assertNotNull(response);
        // FIXED: Was comparing response.getBody() instead of response.getStatusCode()
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Masaj", response.getBody().get(0).getNumeServiciu());
        assertEquals(30, response.getBody().get(0).getDurataServiciu());

        verify(serviceDetailsService, times(1)).getServiceByDuration(duration);
    }

    @Test
    void getServiceByDuration_WhenNoServicesWithDuration_ShouldReturnEmptyList() {
        Integer duration = 120;
        List<ServiceDetailsDTO> emptyList = List.of();
        when(serviceDetailsService.getServiceByDuration(duration)).thenReturn(emptyList);

        ResponseEntity<List<ServiceDetailsDTO>> response = publicController.getServiceByDuration(duration);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(serviceDetailsService, times(1)).getServiceByDuration(duration);
    }

    @Test
    void getServiceByDuration_WithZeroDuration_ShouldHandleGracefully() {
        Integer duration = 0;
        List<ServiceDetailsDTO> emptyList = List.of();
        when(serviceDetailsService.getServiceByDuration(duration)).thenReturn(emptyList);

        ResponseEntity<List<ServiceDetailsDTO>> response = publicController.getServiceByDuration(duration);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(serviceDetailsService, times(1)).getServiceByDuration(duration);
    }

    @Test
    void getServiceByDuration_WithNegativeDuration_ShouldHandleGracefully() {
        Integer duration = -15;
        List<ServiceDetailsDTO> emptyList = List.of();
        when(serviceDetailsService.getServiceByDuration(duration)).thenReturn(emptyList);

        ResponseEntity<List<ServiceDetailsDTO>> response = publicController.getServiceByDuration(duration);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(serviceDetailsService, times(1)).getServiceByDuration(duration);
    }

    @Test
    void constructor_ShouldInitializeServiceCorrectly() {
        ServiceDetailsService mockService = mock(ServiceDetailsService.class);
        PublicController controller = new PublicController(mockService);

        assertNotNull(controller);
    }
}