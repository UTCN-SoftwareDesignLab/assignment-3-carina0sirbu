package repository;

import model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    @Query("select c from Consultation c where c.doctor.name like %:doctor% and c.patient.name like %:patient%")
    List<Consultation> findByPatientAndDoctor(@Param("patient") String patient, @Param("doctor") String doctor);

    @Query("select c from Consultation c where c.doctor.name like %:doctor%")
    List<Consultation> findByDoctor(@Param("doctor") String doctor);

}
