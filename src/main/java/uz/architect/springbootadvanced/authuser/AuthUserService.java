package uz.architect.springbootadvanced.authuser;

import java.util.List;

public interface AuthUserService {
    AuthUser create(AuthUserDto dto);
    void delete(int id);
    void update(AuthUser user);
    AuthUserDto get(int id);

    List<AuthUserDto> getAll(AuthUserCriteria criteria) ;
}
