package uz.architect.springbootadvanced.commands;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.shell.Availability;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.ParameterValidationException;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.shell.command.annotation.ExceptionResolver;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.util.Set;
import java.util.StringJoiner;

@ShellComponent
public class SimplePasswordManager {
    private String password;

    @ShellMethod("Add simple password")
    public String addPassword(@NotBlank(message = "Password can not be empty") String password) {
        this.password = password;
        return "Password added successfully";
    }

    @ShellMethod("Create password with length")
    public String createPasswordWithLength(
            @ShellOption("-p")
            @Size(
                    min = 4,
                    max = 16,
                    message = "Password length should be between 4 and 16"
            ) String password
    ) {
        this.password = password;
        return "Password created successfully";
    }

    @ShellMethod("Create strong password")
    public String createStrongPassword(
            @ShellOption("-p")
            @Pattern(
                    message = "Minimum eight characters, at least one letter, one number and one special character",
                    regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$"
            ) String password) {
        this.password = password;
        return "Password created successfully";
    }

    @ShellMethod("Get your password")
    @ShellMethodAvailability({"availabilityPassword"})
    public String getPassword() {
        return "Your password is: \"%s\"".formatted(password);
    }

    @ShellMethod(value = "Clear password")
    @ShellMethodAvailability({"availabilityClear"})
    public String clearPassword() {
        this.password = null;
        return "Password cleared successfully";
    }

    public Availability availabilityClear() {
        return password != null ? Availability.available() : Availability.unavailable("Password is empty");
    }

    public Availability availabilityPassword() {
        return password != null ? Availability.available() : Availability.unavailable("You don't have a password");
    }

    @ExceptionResolver({ParameterValidationException.class})
    CommandHandlingResult errorHandler(ParameterValidationException e) {
        Set<ConstraintViolation<Object>> constraintViolations = e.getConstraintViolations();
        StringJoiner joiner = new StringJoiner("\n", "", "\n");
        for (ConstraintViolation<Object> violation : constraintViolations) {
            String arg = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            joiner.add(arg + " : " + message);
        }
        return CommandHandlingResult.of(joiner.toString(), 400);
    }
}
