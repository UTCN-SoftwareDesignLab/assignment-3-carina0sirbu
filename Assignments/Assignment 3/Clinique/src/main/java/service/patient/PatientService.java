package service.patient;

import dto.PatientDto;
import model.Patient;

public interface PatientService {

    boolean create(PatientDto patientDto);

    boolean update(PatientDto patientDto);

    boolean search(PatientDto patientDto);

    Patient findByName(String name);
}
