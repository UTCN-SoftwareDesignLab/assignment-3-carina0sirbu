package controller;


import dto.ConsultationDto;
import dto.PatientDto;
import dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/secretary")
public class SecretaryController {


    @GetMapping()
    public String getCreate(Model model) {

        model.addAttribute("userDto", new UserDto());
        model.addAttribute("patientDto", new PatientDto());
        model.addAttribute("consultationDto", new ConsultationDto());
        return "secretary";
    }
}
