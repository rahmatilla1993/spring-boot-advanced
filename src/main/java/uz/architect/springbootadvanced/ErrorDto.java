package uz.architect.springbootadvanced;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    private String message;
    private String path;
    private int code;
    private Object devMessage;
    private LocalDateTime timestamp;
}
