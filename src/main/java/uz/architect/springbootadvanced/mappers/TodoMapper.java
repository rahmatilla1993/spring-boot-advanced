package uz.architect.springbootadvanced.mappers;

import org.mapstruct.*;
import uz.architect.springbootadvanced.dto.TodoDto;
import uz.architect.springbootadvanced.entity.Todo;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TodoMapper {
    @Mappings(
            @Mapping(target = "deadLine", source = "deadLine", dateFormat = "dd-MM-yyyy")
    )
    Todo toEntity(TodoDto dto);

    @InheritInverseConfiguration
    TodoDto toDto(Todo todo);

    List<Todo> toEntityList(List<TodoDto> dtoList);
    List<TodoDto> toDtoList(List<Todo> todoList);
}
