package uz.architect.springbootadvanced.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.architect.springbootadvanced.entity.Todo;
import uz.architect.springbootadvanced.enums.Category;
import uz.architect.springbootadvanced.enums.Level;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findAllByLevel(Level level);

    List<Todo> findAllByCategory(Category category);

    List<Todo> findAllByDeadLine(LocalDate deadline);
}
