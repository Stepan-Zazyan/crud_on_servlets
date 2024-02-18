package aston.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private String username;

    private String title;

    private String description;

    private int passportNumber;

}
