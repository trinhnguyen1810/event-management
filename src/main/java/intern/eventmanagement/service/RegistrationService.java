package intern.eventmanagement.service;
import intern.eventmanagement.service.dto.UserDto;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

public interface RegistrationService {
    void processRegistration(UserDto userDto, BindingResult result, Model model);
}
