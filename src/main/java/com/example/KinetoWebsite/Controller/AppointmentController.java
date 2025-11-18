package com.example.KinetoWebsite.Controller;

import com.example.KinetoWebsite.Model.DTO.AppointmentDTO;
import com.example.KinetoWebsite.Service.AppointmentService;
import com.example.KinetoWebsite.Service.EmailService;
import com.example.KinetoWebsite.Service.RecaptchaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final RecaptchaService recaptchaService;
    private final EmailService emailService;

    public AppointmentController(AppointmentService appointmentService, RecaptchaService recaptchaService,
                                 EmailService emailService){
        this.appointmentService = appointmentService;
        this.recaptchaService=recaptchaService;
        this.emailService=emailService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        // Verify CAPTCHA first
       /* if (!recaptchaService.verifyRecaptcha(appointmentDTO.getChaptchaResponse())) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "CAPTCHA verification failed", "message", "Vă rugăm să verificați că nu sunteți robot.")
            );
        } */

        try{
            AppointmentDTO savedAppointment = appointmentService.createAppointment(appointmentDTO);

            //send notification email to admin.
            String subject = "Programare noua: " + savedAppointment.getPatientName();
            String body = String.format(
                    "Ai o programare noua!:\n\n" +
                            "Nume: %s\n" +
                            "Telefon: %s\n" +
                            "Serviciu %s\n" +
                            "Data: %s\n" +
                            "Informatii aditionale: %s\n",
                    savedAppointment.getPatientName(),
                    savedAppointment.getPhoneNumber(),
                    savedAppointment.getServiceName(),
                    savedAppointment.getDate(),
                    savedAppointment.getAdditionalInfo()
            );
            //emailService.sendAdminNotification(subject, body);

            return ResponseEntity.ok(savedAppointment);

        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Appointment creation failed", "message", e.getMessage())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id,
                                                            @Valid @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, appointmentDTO);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}