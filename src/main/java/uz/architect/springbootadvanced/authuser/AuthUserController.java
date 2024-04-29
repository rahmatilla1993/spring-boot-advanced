package uz.architect.springbootadvanced.authuser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.MethodInvocationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.architect.springbootadvanced.ErrorDto;
import uz.architect.springbootadvanced.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/authuser")
public class AuthUserController {

    private final AuthUserService service;

    public AuthUserController(AuthUserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody AuthUserDto dto) {
        service.create(dto);
        return new ResponseEntity<>("Successfully Created - User", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody AuthUser authUser) {
        service.update(authUser);
        return new ResponseEntity<>("Successfully Updated - User", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return new ResponseEntity<>("Successfully Deleted - User", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthUserDto> get(@PathVariable int id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuthUserDto>> list(@Valid AuthUserCriteria criteria) {
        return new ResponseEntity<>(service.getAll(criteria), HttpStatus.OK);
    }

    @ExceptionHandler({NotFoundException.class})
    public HttpEntity<ErrorDto> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public HttpEntity<ErrorDto> handleArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .message("Invalid input")
                        .devMessage(map)
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
