package uz.architect.springbootadvanced.authuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.architect.springbootadvanced.NotFoundException;

import java.util.List;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    private final AuthUserRepository authUserRepository;
    private final AuthUserMapper authUserMapper;

    @Autowired
    public AuthUserServiceImpl(AuthUserRepository authUserRepository, AuthUserMapper authUserMapper) {
        this.authUserRepository = authUserRepository;
        this.authUserMapper = authUserMapper;
    }

    @Override
    public AuthUser create(AuthUserDto dto) {
        AuthUser authUser = authUserMapper.toEntity(dto);
        return authUserRepository.save(authUser);
    }

    @Override
    public void delete(int id) {
        AuthUser authUser = findById(id);
        authUserRepository.delete(authUser);
    }

    @Override
    public void update(AuthUser user) {
        AuthUser authUser = findById(user.getId());
        authUserRepository.save(authUser);
    }

    @Override
    public AuthUserDto get(int id) {
        AuthUser authUser = findById(id);
        return authUserMapper.toDto(authUser);
    }

    @Override
    public List<AuthUserDto> getAll(AuthUserCriteria criteria) {
        Page<AuthUser> userPage = authUserRepository.findAll(PageRequest.of(criteria.getPage(), criteria.getSize()));
        return authUserMapper.toDtos(userPage.getContent());
    }

    private AuthUser findById(int id) {
        return authUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No AuthUser found with id " + id));
    }
}
