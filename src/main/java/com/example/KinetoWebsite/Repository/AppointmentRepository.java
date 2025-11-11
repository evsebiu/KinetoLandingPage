package com.example.KinetoWebsite.Repository;

import com.example.KinetoWebsite.Model.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
