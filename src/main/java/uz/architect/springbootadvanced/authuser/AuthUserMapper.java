package uz.architect.springbootadvanced.authuser;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AuthUserMapper {
    AuthUser toEntity(AuthUserDto dto);
    AuthUserDto toDto(AuthUser user);

    List<AuthUserDto> toDtos(List<AuthUser> entities);
}
