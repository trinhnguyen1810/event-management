package intern.eventmanagement.controllers;

import intern.eventmanagement.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping({"/","/login"})
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

}
