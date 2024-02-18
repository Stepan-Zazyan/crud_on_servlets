package aston.entity;

import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passport {

    @EqualsAndHashCode.Include
    private int id;

    private int number;

}
