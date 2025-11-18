package com.example.KinetoWebsite.Service;

import com.example.KinetoWebsite.Model.DTO.AppointmentDTO;

public class AdminEmailService {

    public String createAppointmentHtmlTemplate(AppointmentDTO appointmentDTO){
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; }
                    .header { background-color: #4CAF50; color: white; padding: 10px; }
                    .content { padding: 20px; }
                    .footer { background-color: #f1f1f1; padding: 10px; }
                </style>
            </head>
            <body>
                <div class="header">
                    <h2>Appointment Confirmation</h2>
                </div>
                <div class="content">
                    <p>Dear <strong>%s</strong>,</p>
                    <p>Your appointment has been successfully scheduled!</p>
                    
                    <h3>Appointment Details:</h3>
                    <ul>
                        <li><strong>Title:</strong> %s</li>
                        <li><strong>Description:</strong> %s</li>
                        <li><strong>Start Time:</strong> %s</li>
                        <li><strong>End Time:</strong> %s</li>
                    </ul>
                    
                    <p>We look forward to seeing you!</p>
                </div>
                <div class="footer">
                    <p>Best regards,<br>VanuKineto</p>
                </div>
            </body>
            </html>
            """,
                appointmentDTO.getPatientName(),
                appointmentDTO.getServiceName(),
                appointmentDTO.getPhoneNumber(),
                appointmentDTO.getAdditionalInfo(),
                appointmentDTO.getTime()
        );
    }
}
