package controller;

import dto.UserDto;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.user.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/manageUsers")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getCreate(Model model) {

        model.addAttribute("userDto", new UserDto());
        return "manageUsers";
    }

    @PostMapping(params = "search")
    public String searchUser(@RequestParam("name") String name, @ModelAttribute UserDto userDto, Model model) {

        User user = userService.findByName(name);

        if(user != null) {
            model.addAttribute("message", "User is in the system");
            userDto.setUsername(user.getUsername());
            userDto.setId(user.getId());

            model.addAttribute("userDto", userDto);
            return "manageUsers";
        }
        else {
            model.addAttribute("message", "User is NOT in the system or some error occured");
            return "manageUsers";
        }
    }

    @PostMapping(params = "add")
    public String addUser(@ModelAttribute @Valid UserDto userDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "manageUsers";
        }

        if (userService.create(userDto)) {

            model.addAttribute("message", "User is in the system");
            return "manageUsers";
        }
        else {
            model.addAttribute("message", "User is NOT in the system or some error occured");
            return "manageUsers";
        }
    }

    @PostMapping(params = "update")
    public String updateUser(@ModelAttribute @Valid UserDto userDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "manageUsers";
        }

        if (userService.update(userDto)) {

            model.addAttribute("message", "User updated successfully");
            return "manageUsers";
        }
        else {
            model.addAttribute("message", "User couldn't be updated");
            return "manageUsers";
        }
    }

    @PostMapping(params = "delete")
    public String deleteUser(@RequestParam("id") Long id, @ModelAttribute UserDto userDto, Model model) {

        userService.deleteById(id);

        return "manageUsers";
    }



}
