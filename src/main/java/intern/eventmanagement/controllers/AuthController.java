package intern.eventmanagement.controllers;

import intern.eventmanagement.entity.User;
import intern.eventmanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("user") User user,
            BindingResult result,
            Model model
    ) {
        Logger logger = LoggerFactory.getLogger(AuthController.class);
        User foundUser = userRepository.findByEmail(user.getEmail());
        logger.info("Entered Password: {}", user.getPassword());
        logger.info("Hashed Password: {}", passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        System.out.println(passwordEncoder.encode(user.getPassword()));

        if (foundUser != null && passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return "redirect:/home";
        } else {

            model.addAttribute("error", true);
            return "redirect:/home";
        }
    }




}
