package service.consultation;

import dto.ConsultationDto;
import model.Consultation;

import java.util.List;

public interface ConsultationService {

    List<Consultation> findByPatientAndDoctor(String patient, String doctor);

    boolean create(ConsultationDto consultationDto);

    boolean deleteById(Long id);

    boolean update(ConsultationDto consultationDto);

    void updateObservation(Long id, String observations);
}
