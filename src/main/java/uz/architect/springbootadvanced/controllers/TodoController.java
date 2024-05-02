package uz.architect.springbootadvanced.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.architect.springbootadvanced.dto.TodoDto;
import uz.architect.springbootadvanced.entity.Todo;
import uz.architect.springbootadvanced.entity.User;
import uz.architect.springbootadvanced.enums.Category;
import uz.architect.springbootadvanced.enums.Level;
import uz.architect.springbootadvanced.mappers.TodoMapper;
import uz.architect.springbootadvanced.repository.TodoRepository;
import uz.architect.springbootadvanced.repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final TodoMapper todoMapper;

    @QueryMapping(value = "getTodos")
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @QueryMapping
    public List<Todo> getTodosByLevel(@Argument String level) {
        return todoRepository.findAllByLevel(Level.valueOf(level));
    }

    @QueryMapping
    public List<Todo> getTodosByCategory(@Argument String category) {
        return todoRepository.findAllByCategory(Category.valueOf(category));
    }

    @QueryMapping
    public List<Todo> getTodosByDeadline(@Argument("deadLine") String date) {
        return todoRepository.findAllByDeadLine(
                LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        );
    }

    @SchemaMapping(typeName = "Query", field = "getTodo")
    public Todo getOne(@Argument int id) {
        return todoRepository.findById(id).orElse(null);
    }

    @MutationMapping
    public Todo createTodo(@Argument TodoDto dto) {
        Todo todo = todoMapper.toEntity(dto);
        User user = userRepository.findById(dto.getUser_id()).get();
        todo.setCreatedBy(user);
        return todoRepository.save(todo);
    }

    @SchemaMapping(typeName = "Mutation", field = "completeTodo")
    public Todo setComplete(@Argument int todoId) {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        Objects.requireNonNull(todo).setCompleted(true);
        return todoRepository.save(todo);
    }

    @MutationMapping(value = "deleteTodo")
    public String delete(@Argument("todoId") int id) {
        todoRepository.deleteById(id);
        return "Deleted";
    }

    @MutationMapping
    public Todo updateTodo(@Argument("todoId") int id, @Argument TodoDto dto) {
        Todo todo = todoRepository.findById(id).orElse(null);
        assert todo != null;
        todo.setCompleted(dto.isCompleted());
        if (dto.getDescription() != null)
            todo.setDescription(dto.getDescription());
        if (dto.getCategory() != null)
            todo.setCategory(dto.getCategory());
        if (dto.getLevel() != null)
            todo.setLevel(dto.getLevel());
        if (dto.getTitle() != null)
            todo.setTitle(dto.getTitle());
        if (dto.getDeadLine() != null)
            todo.setDeadLine(LocalDate.parse(dto.getDeadLine()));
        return todoRepository.save(todo);
    }
}
