package sfera.reportproyect.mapper;

import org.springframework.stereotype.Component;
import sfera.reportproyect.dto.response.ResUser;
import sfera.reportproyect.entity.User;

@Component
public class UserMapper {
    public ResUser  resUser(User user){
        return ResUser.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .imgUrl(user.getImgUrl())
                .build();
    }
}
