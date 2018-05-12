package service.patient;

import model.Patient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import repository.PatientRepository;
import sun.security.krb5.internal.PAData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PatientServiceTest {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    PatientServiceImpl patientService;

    Patient patient;

    @Before
    public void setUp() {

        List<Patient> patientList = new ArrayList<>();

        patient = new Patient("Carina Test", 2344, "2987362767", new Date(1996-02-26), "Clujului, A2");
        patientList.add(patient);

        Mockito.when(patientRepository.findByName("Carina Test")).thenReturn(patient);
        Mockito.when(patientRepository.findAll()).thenReturn(patientList);
        Mockito.when(patientRepository.save(new Patient("Andra Test", 2345, "12345678",
                new Date(2001-06-14), "Clujului A2")))
                .thenReturn(new Patient());
    }

    @Test
    public void findById() {

        Patient patient = patientService.findByName("Carina Test");
        Assert.assertTrue(patient.getIdentityCardNumber() == 2344);
    }

    @Test
    public void findAll() {

        Patient patient = patientService.findByName("Carina Test");
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);

        Assert.assertTrue(patientList.size() == 1);
    }

}