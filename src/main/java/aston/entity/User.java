package aston.entity;

import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @EqualsAndHashCode.Include
    private int id;

    private String name;

    private String login;

    private String password;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
