package service.patient;

import dto.PatientDto;
import model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PatientRepository;


@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public boolean create(PatientDto patientDto) {

        Patient patient = new Patient(patientDto.getName(), patientDto.getIdentityCardNumber(), patientDto.getPersNumCode(), patientDto.getDateOfBirth(), patientDto.getAddress());

        System.out.println(patient);

        patientRepository.save(patient);

        return patientRepository.findById(patient.getId()).isPresent();
    }

    @Override
    public boolean update(PatientDto patientDto) {

        Patient patient = patientRepository.findById(patientDto.getId()).get();

        patient.setName(patientDto.getName());
        patient.setAddress(patientDto.getAddress());
        patient.setIdentityCardNumber(patientDto.getIdentityCardNumber());

        patientRepository.save(patient);

        return patientRepository.findById(patient.getId()).isPresent();
    }

    @Override
    public boolean search(PatientDto patientDto) {

        Patient patient = patientRepository.findByName(patientDto.getName());

        System.out.println(patient);

        if (patient != null) {

            patientDto.setId(patient.getId());
            patientDto.setAddress(patient.getAddress());
            patientDto.setDateOfBirth(patient.getDateOfBirth());
            patientDto.setIdentityCardNumber(patient.getIdentityCardNumber());
            patientDto.setPersNumCode(patient.getPersNumCode());

            return true;

        } else return false;


    }

    @Override
    public Patient findByName(String name) {

        return patientRepository.findByName(name);
    }
}
