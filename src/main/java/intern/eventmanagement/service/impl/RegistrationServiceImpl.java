package intern.eventmanagement.service.impl;

import intern.eventmanagement.entity.Role;
import intern.eventmanagement.entity.User;
import intern.eventmanagement.repository.RoleRepository;
import intern.eventmanagement.repository.UserRepository;
import intern.eventmanagement.service.RegistrationService;
import intern.eventmanagement.service.UserService;
import intern.eventmanagement.service.dto.UserDto;
import intern.eventmanagement.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public RegistrationServiceImpl (UserService userService,
                                   PasswordEncoder passwordEncoder,
                                   RoleRepository roleRepository,
                                   UserRepository userRepository,
                                   UserMapper userMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void processRegistration(UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return;
        }

        User existingUser = userService.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            result.rejectValue("email", null, "Email already exists");
            return;
        }

        User newUser = userMapper.toEntity(userDto); // Use the mapper to create a new User entity
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setEnabled(true);
        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role 'USER' not found"));
        newUser.setRoles(Collections.singleton(defaultRole));
        userService.save(newUser);
        model.addAttribute("success", true);
        userMapper.toDto(newUser);}

    public void processRegistrationAdmin(UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return;
        }

        User existingUser = userService.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            result.rejectValue("email", null, "Email already exists");
            return;
        }

        User newUser = userMapper.toEntity(userDto); // Use the mapper to create a new User entity
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setEnabled(true);
        Role defaultRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Default role 'ADMIN' not found"));
        newUser.setRoles(Collections.singleton(defaultRole));
        userService.save(newUser);
        model.addAttribute("success", true);
        userMapper.toDto(newUser);


    }
}
