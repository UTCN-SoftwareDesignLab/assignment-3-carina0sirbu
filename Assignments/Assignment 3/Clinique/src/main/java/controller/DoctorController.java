package controller;

import dto.ConsultationDto;
import dto.PatientDto;
import model.Consultation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.consultation.ConsultationService;
import service.user.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/doctor")
public class DoctorController {

    private UserService userService;
    private ConsultationService consultationService;

    @Autowired
    public DoctorController(UserService userService, ConsultationService consultationService) {
        this.userService = userService;
        this.consultationService = consultationService;
    }

    @GetMapping()
    public String getCreate(Model model, Principal principal) {

        model.addAttribute("welcomeMessage", "Hello, Dr. " + userService.findByUsername(principal.getName()).getName() + "! ");
        model.addAttribute("consultationDto", new ConsultationDto());
        model.addAttribute("patientDto", new PatientDto());
        return "doctor";
    }

    @PostMapping(params = "search")
    public String searchConsultation(@RequestParam("name") String name, @ModelAttribute @Valid PatientDto patientDto,
                                     @ModelAttribute ConsultationDto consultationDto,
                                     BindingResult bindingResult, Principal principal, Model model) {

        model.addAttribute("welcomeMessage", "Hello, Dr. " + userService.findByUsername(principal.getName()).getName() + "! ");

        if (bindingResult.hasErrors()) {
            return "doctor";
        }

        List<Consultation> list = consultationService.findByPatientAndDoctor(name, userService.findByUsername(principal.getName()).getName());

        model.addAttribute("consult", list);

        return "doctor";
    }

    @PostMapping(params = "changeObs")
    public String change(@RequestParam("id") Long id,
                         @RequestParam("observations") String observations,
                         @ModelAttribute ConsultationDto consultationDto, BindingResult bindingResult,
                         @ModelAttribute PatientDto patientDto, Principal principal, Model model) {

        model.addAttribute("welcomeMessage", "Hello, Dr. " + userService.findByUsername(principal.getName()).getName() + "! ");

        if (bindingResult.hasErrors()) {
            return "doctor";
        }

        consultationService.updateObservation(id, observations);
        model.addAttribute("messageHtml", "Update done successfully");

        return "doctor";
    }
}
