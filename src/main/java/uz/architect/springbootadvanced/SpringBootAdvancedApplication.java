package uz.architect.springbootadvanced;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uz.architect.springbootadvanced.dto.TodoDto;
import uz.architect.springbootadvanced.entity.Todo;
import uz.architect.springbootadvanced.entity.User;
import uz.architect.springbootadvanced.mappers.TodoMapper;
import uz.architect.springbootadvanced.repository.TodoRepository;
import uz.architect.springbootadvanced.repository.UserRepository;

import java.net.URL;
import java.util.List;

@SpringBootApplication
public class SpringBootAdvancedApplication {

    private static final URL url = SpringBootAdvancedApplication.class.getClassLoader().getResource("todos.json");

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdvancedApplication.class, args);
    }

//    @Bean
    public ApplicationRunner applicationRunner(ObjectMapper objectMapper,
                                               TodoMapper todoMapper,
                                               TodoRepository todoRepository,
                                               UserRepository userRepository) {
        return args -> {
            List<User> users = userRepository.findAll();
            List<TodoDto> dtoList = objectMapper.readValue(url, new TypeReference<>() {
            });
            List<Todo> todos = dtoList.stream()
                    .map(dto -> {
                        int userId = dto.getUser_id();
                        Todo todo = todoMapper.toEntity(dto);
                        User userFromDb = users.stream().filter(user -> user.getId() == userId).findAny().get();
                        todo.setCreatedBy(userFromDb);
                        return todo;
                    }).toList();
            todoRepository.saveAll(todos);
        };
    }
}
