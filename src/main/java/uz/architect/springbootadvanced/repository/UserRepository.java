package uz.architect.springbootadvanced.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.architect.springbootadvanced.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
