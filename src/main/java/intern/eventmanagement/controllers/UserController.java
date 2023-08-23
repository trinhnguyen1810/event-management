package intern.eventmanagement.controllers;

import intern.eventmanagement.entity.User;
import intern.eventmanagement.service.RegistrationService;
import intern.eventmanagement.service.UserService;
import intern.eventmanagement.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationService registrationService;

    @GetMapping( "/listUsers" )
    public ModelAndView showEmployees() {
        ModelAndView mav = new ModelAndView("list-users");
        List<User> list = userService.findAllUser();
        mav.addObject("users",list);
        return mav;
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUserById(userId);
        return "redirect:/listUsers";
    }

    @GetMapping("/registerAdmin")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("userDto", user);
        return "register-admin";
    }

    @PostMapping("/registerAdmin")
    public String registerUser(
            @ModelAttribute("userDto") UserDto userDto,
            BindingResult result,
            Model model
    ) {
        registrationService.processRegistrationAdmin(userDto, result, model);
        return "register-admin";
    }


}


