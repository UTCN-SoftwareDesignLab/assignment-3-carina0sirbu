package controller;

import dto.ConsultationDto;
import dto.PatientDto;
import dto.UserDto;
import model.Consultation;
import model.Message;
import model.Patient;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.consultation.ConsultationService;
import service.patient.PatientService;
import service.user.UserService;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/consultationInfo")
public class ConsultationController {

    private ConsultationService consultationService;
    private PatientService patientService;
    private UserService userService;

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ConsultationController(ConsultationService consultationService, PatientService patientService, UserService userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.consultationService = consultationService;
        this.patientService = patientService;
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping
    public String getConsultation(Model model) {

        model.addAttribute("consultationDto", new ConsultationDto());
        model.addAttribute("patientDto", new PatientDto());
        model.addAttribute("userDto", new UserDto());
        return "/consultationInfo";
    }

    @PostMapping(params = "search")
    public String searchConsult(@ModelAttribute @Valid ConsultationDto consultationDto,  BindingResult bindingResult,
                                @ModelAttribute PatientDto patientDto,
                                @ModelAttribute UserDto userDto, Model model) {

        Patient patient = patientService.findByName(consultationDto.getPatient().getName());

        User doctor = userService.findByName(consultationDto.getDoctor().getName());

        List<Consultation> consultation = consultationService.findByPatientAndDoctor(patient.getName(), doctor.getName());

        model.addAttribute("consult", consultation);

        return "viewConsultations";
    }

    @PostMapping(params = "add")
    public String createConsult(@ModelAttribute @Valid ConsultationDto consultationDto, BindingResult bindingResult,
                                @ModelAttribute UserDto userDto,
                                @ModelAttribute PatientDto patientDto, Principal principal, Model model) {

        if (consultationService.create(consultationDto)) {
            model.addAttribute("messageHtml", "Consultation added to the system");
        }
        else {
            model.addAttribute("messageHtml", "Cannot appoint a consultation on this day!");
        }

        Message message = new Message();

        User doctor = userService.findByName(consultationDto.getDoctor().getName());
        Patient patient = patientService.findByName(consultationDto.getPatient().getName());

        message.setMessage("You have a new incoming consultation on " + consultationDto.getConsultationDate() +
                            ". Patient name is " + patient.getName() + ". Scheduled by " + principal.getName());

        simpMessagingTemplate.convertAndSendToUser(doctor.getUsername(), "/queue/reply", message);

        return "consultationInfo";
    }

    @PostMapping(params = "delete")
    public String deleteConsult(@ModelAttribute @Valid ConsultationDto consultationDto, BindingResult bindingResult, Model model) {

        consultationService.deleteById(consultationDto.getId());

        return "consultationInfo";
    }

    @PostMapping(params = "update")
    public String updateConsult(@ModelAttribute @Valid ConsultationDto consultationDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "consultationInfo";
        }

        consultationService.update(consultationDto);
        model.addAttribute("messageHtml", "Succesul e relativ");

        return "consultationInfo";
    }
}
