package controller;

import dto.PatientDto;
import model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.patient.PatientService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/patientInfo")
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String getclient(Model model) {

        model.addAttribute("patientDto", new PatientDto());
        return "patientInfo";
    }

    @PostMapping(params = "search")
    public String searchPatient(@ModelAttribute @Valid PatientDto patientDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "patientInfo";
        }

        if (patientService.search(patientDto)) {

            model.addAttribute("message", "Patient is in the system");
        }
        else {
            model.addAttribute("message", "Patient not in the system. Would you like to add it?");
        }

        return "patientInfo";
    }

    @PostMapping(params = "create")
    public String createPatient(@ModelAttribute @Valid PatientDto patientDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "patientInfo";
        }

        if (patientService.create(patientDto)) {
            model.addAttribute("message", "Patient added to the system");
        }
        else {
            model.addAttribute("message", "Error while trying to add the client");
        }

        return "patientInfo";
    }

    @PostMapping(params = "update")
    public String updatePatient(@ModelAttribute @Valid PatientDto patientDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "patientInfo";
        }

        if (patientService.update(patientDto)) {
            model.addAttribute("message", "Patient updated in the system");
        }
        else {
            model.addAttribute("message", "Error while trying to update the client");
        }

        return "patientInfo";
    }
}
