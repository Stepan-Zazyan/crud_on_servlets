package aston.entity;

import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @EqualsAndHashCode.Include
    private int id;

    private  String title;

    private  String description;

    public Post(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
