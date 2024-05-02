package uz.architect.springbootadvanced.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import uz.architect.springbootadvanced.enums.Category;
import uz.architect.springbootadvanced.enums.Level;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TodoDto {
    private String title;
    private String description;
    private Category category;
    private Level level;
    private String deadLine;
    private boolean completed;
    @JsonProperty("created_by")
    @JsonIgnore
    private int user_id;
}
