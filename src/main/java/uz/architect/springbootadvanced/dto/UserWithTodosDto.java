package uz.architect.springbootadvanced.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWithTodosDto {
    private String fullName;
    private String email;
    private String password;
    private List<TodoDto> todos;
}
