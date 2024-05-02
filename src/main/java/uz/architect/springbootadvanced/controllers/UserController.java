package uz.architect.springbootadvanced.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.architect.springbootadvanced.dto.TodoDto;
import uz.architect.springbootadvanced.dto.UserDto;
import uz.architect.springbootadvanced.dto.UserWithTodosDto;
import uz.architect.springbootadvanced.entity.User;
import uz.architect.springbootadvanced.mappers.TodoMapper;
import uz.architect.springbootadvanced.mappers.UserMapper;
import uz.architect.springbootadvanced.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TodoMapper todoMapper;

    @QueryMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @QueryMapping
    public User getUser(@Argument("userId") int id) {
        return userRepository.findById(id).orElse(null);
    }

    @QueryMapping("getUsersWithTodo")
    public List<UserWithTodosDto> getUserWithTodosDto() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    List<TodoDto> dtoList = todoMapper.toDtoList(user.getTodos());
                    return UserWithTodosDto.builder()
                            .fullName(user.getFullName())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .todos(dtoList)
                            .build();
                })
                .toList();
    }

    @MutationMapping
    public User createUser(@Argument UserDto dto) {
        User user = userMapper.toEntity(dto);
        return userRepository.save(user);
    }
}
