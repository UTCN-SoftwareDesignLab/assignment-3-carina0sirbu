package service.consultation;

import dto.ConsultationDto;
import model.Consultation;
import model.Patient;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;
import repository.ConsultationRepository;
import repository.PatientRepository;
import repository.UserRepository;

import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService{

    private ConsultationRepository consultationRepository;
    private PatientRepository patientRepository;
    private UserRepository userRepository;

    @Autowired
    public ConsultationServiceImpl(ConsultationRepository consultationRepository, PatientRepository patientRepository, UserRepository userRepository) {
        this.consultationRepository = consultationRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Consultation> findByPatientAndDoctor(String patient, String doctor) {
        return consultationRepository.findByPatientAndDoctor(patient, doctor);
    }

    @Override
    public boolean create(ConsultationDto consultationDto) {

        Patient patient = patientRepository.findByName(consultationDto.getPatient().getName());

        User doctor = userRepository.findByName(consultationDto.getDoctor().getName());

        List<Consultation> consultations = consultationRepository.findByDoctor(doctor.getName());

        for(Consultation c:consultations){

            if(consultationDto.getPatient().getName() != c.getPatient().getName() && Math.abs(c.getConsultationDate().getTime() - consultationDto.getConsultationDate().getTime()) < 86400 * 1000 + 1) {
                return false;
            }
        }

        Consultation consultation = new Consultation(patient, doctor, consultationDto.getConsultationDate(), consultationDto.getObservations());

        consultationRepository.save(consultation);

        return consultationRepository.findById(consultation.getId()).isPresent();
    }

    @Override
    public boolean deleteById(Long id) {

        consultationRepository.deleteById(id);

        return true;
    }

    @Override
    public boolean update(ConsultationDto consultationDto) {

        Patient patient;
        User doctor;

        if(patientRepository.findByName(consultationDto.getPatient().getName()) != null ) {
            patient = patientRepository.findByName(consultationDto.getPatient().getName());
        } else {
            return false;
        }
        if(userRepository.findByName(consultationDto.getDoctor().getName()) != null) {
            doctor = userRepository.findByName(consultationDto.getDoctor().getName());
        } else {
            return false;
        }

        Consultation consultation = consultationRepository.findById(consultationDto.getId()).get();
        List<Consultation> consultations = consultationRepository.findByDoctor(doctor.getName());
        for(Consultation c:consultations){

            if(consultationDto.getPatient().getName() != c.getPatient().getName() && Math.abs(c.getConsultationDate().getTime() - consultationDto.getConsultationDate().getTime())<86400*1000+1) {
                return false;
            }
        }
        consultation.setConsultationDate(consultationDto.getConsultationDate());
        consultation.setPatient(patient);
        consultation.setDoctor(doctor);
        consultation.setObservations(consultationDto.getObservations());
        consultationRepository.save(consultation);
        return true;
    }

    @Override
    public void updateObservation(Long id, String observations) {

        Consultation consultation = consultationRepository.findById(id).get();

        consultation.setObservations(observations);

        consultationRepository.save(consultation);
    }

//    @Override
//    public boolean update(ConsultationDto consultationDto) {
//
//        Patient patient = patientRepository.findByName(consultationDto.getPatient().getName());
//
//        User doctor = userRepository.findByName(consultationDto.getDoctor().getName());
//
//        List<Consultation> consultations = consultationRepository.findByDoctor(doctor.getName());
//
//        for(Consultation c: consultations){
//
//            if(consultationDto.getPatient().getName() != c.getPatient().getName() && Math.abs(c.getConsultationDate().getTime() - consultationDto.getConsultationDate().getTime()) < 86400 * 1000 + 1) {
//                return false;
//            }
//        }
//
//        Consultation consultation = consultationRepository.findById(consultationDto.getId()).get();
//
//        consultation.setConsultationDate(consultationDto.getConsultationDate());
//        consultation.setObservations(consultationDto.getObservations());
//        consultation.setDoctor(doctor);
//        consultation.setPatient(patient);
//
//        consultationRepository.save(consultation);
//
//        return consultationRepository.findById(consultation.getId()).isPresent();
//    }


}
