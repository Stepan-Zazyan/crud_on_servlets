package aston.mapper;

import aston.dto.PostDto;
import aston.entity.Passport;
import aston.entity.Post;
import aston.entity.User;

public class PostMapper {

    public PostDto getDto(Post post, User user, Passport passport) {
        PostDto postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setUsername(user.getName());
        postDto.setPassportNumber(passport.getNumber());
        return postDto;
    }
}
