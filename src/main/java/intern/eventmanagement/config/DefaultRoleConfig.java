package intern.eventmanagement.config;

import intern.eventmanagement.entity.Role;
import intern.eventmanagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Configuration
public class DefaultRoleConfig {

    private final RoleRepository roleRepository;

    @Autowired
    public DefaultRoleConfig(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void createDefaultRole() {
        Optional<Role> userRole = roleRepository.findByName("USER");
        if (!userRole.isPresent()) {
            Role defaultRole = new Role();
            defaultRole.setName("USER");
            roleRepository.save(defaultRole);
        }
    }

}
