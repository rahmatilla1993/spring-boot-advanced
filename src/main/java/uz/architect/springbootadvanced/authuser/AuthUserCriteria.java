package uz.architect.springbootadvanced.authuser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserCriteria {
    private int page = 0;
    private int size = 5;
}
