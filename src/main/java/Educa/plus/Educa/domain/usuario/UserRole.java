package Educa.plus.Educa.domain.usuario;

import lombok.Getter;
import lombok.Setter;

public enum UserRole {
    ADMIN("admin"),
    STAFF("staff"),
    USER("user"),
    ;

    private String role;

    UserRole(String rank ){
        this.role = rank;
    }


}
