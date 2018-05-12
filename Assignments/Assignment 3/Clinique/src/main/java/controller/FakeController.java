//package controller;
//import dto.UserDto;
//import model.Role;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import service.user.UserService;
//
//@Controller
//public class FakeController {
//
//    private UserService userService;
//
//    @Autowired
//    public FakeController(UserService userService) {
//        this.userService = userService;
//
//        createUser();
//    }
//
//    private void createUser() {
//
//        UserDto userDto = new UserDto("Carina Natalia", "carina@gmail.com", "Parola123#", Role.ADMIN);
//
//        userService.create(userDto);
//    }
//}