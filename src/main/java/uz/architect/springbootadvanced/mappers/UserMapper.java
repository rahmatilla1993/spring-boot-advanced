package uz.architect.springbootadvanced.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.architect.springbootadvanced.dto.UserDto;
import uz.architect.springbootadvanced.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDto userDto);
}
