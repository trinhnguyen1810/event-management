package intern.eventmanagement.controllers;

import intern.eventmanagement.service.RegistrationService;
import intern.eventmanagement.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
    @Controller
    public class RegistrationController {
        @Autowired
        private RegistrationService registrationService;


        @GetMapping("/register")
        public String showRegistrationForm(Model model){
            UserDto user = new UserDto();
            model.addAttribute("userDto", user);
            return "register";
        }
        @PostMapping("/register")
        public String registerUser(
                @ModelAttribute("userDto") UserDto userDto,
                BindingResult result,
                Model model
        ) {
            registrationService.processRegistration(userDto, result, model);
            return "register";
        }
    }







