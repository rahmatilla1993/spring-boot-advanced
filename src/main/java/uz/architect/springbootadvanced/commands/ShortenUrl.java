package uz.architect.springbootadvanced.commands;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import uz.architect.springbootadvanced.service.ShortenUrlService;

@ShellComponent
public class ShortenUrl {

    private final ShortenUrlService shortenUrlService;
    private String shortenUrl;

    @Autowired
    public ShortenUrl(ShortenUrlService shortenUrlService) {
        this.shortenUrlService = shortenUrlService;
    }

    @ShellMethod
    public String shortenUrl(@NotBlank(message = "Url cannot be blank") String url) {
        shortenUrl = shortenUrlService.shortenUrl(url);
        return "Successfully added long url";
    }

    @ShellMethod("Get shorten url")
    public String get() {
        return shortenUrl;
    }

    @ShellMethod("Delete shorten url")
    public String delete() {
        shortenUrl = null;
        return "Successfully deleted shorten url";
    }

    @ShellMethodAvailability({"get", "delete"})
    public Availability checkAvailability() {
        return shortenUrl != null ? Availability.available() : Availability.unavailable("Shorten url is not available");
    }
}
