package intern.eventmanagement.service.mapper.impl;

import intern.eventmanagement.entity.Role;
import intern.eventmanagement.entity.User;
import intern.eventmanagement.service.dto.UserDto;
import intern.eventmanagement.service.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setEnabled(true);
        user.setRoles(dto.getRoles().stream().map(roleName -> {
            Role role = new Role();
            role.setName(roleName);
            return role;
        }).collect(Collectors.toSet()));

        return user;
    }

    @Override
    public UserDto toDto(User entity) {
        if (entity == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        userDto.setRoles(entity.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        return userDto;
    }
    @Override
    public List<User> toEntity(List<UserDto> dtoList) {
        if (dtoList == null) {
            return null;
        }
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
    @Override
    public List<UserDto> toDto(List<User> entityList) {
        if (entityList == null) {
            return null;
        }
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
