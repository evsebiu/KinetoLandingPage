package com.example.KinetoWebsite.UnitTests.UnitTestingController;


import com.example.KinetoWebsite.Controller.ViewController;
import com.example.KinetoWebsite.Service.AppointmentService;
import com.example.KinetoWebsite.Service.ServiceDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ViewControllerTest {

    @Mock
    private AppointmentService serviceAppointments;

    @Mock
    private ServiceDetailsService serviceDetails;

    @Mock
    private Model model;

    @InjectMocks
    private ViewController viewController;
}
