package intern.eventmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import intern.eventmanagement.entity.User;
import intern.eventmanagement.entity.UserDto;

@Service
public class RegistrationService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void processRegistration(UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return;
        }

        User existingUser = userService.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            result.rejectValue("email", null, "Email already exists");
            return;
        }

        User newUser = new User();
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole("ROLE_USER"); // Set the role as needed
        newUser.setEnabled(true);

        userService.save(newUser);

        model.addAttribute("success", true);
    }
}
