package sfera.reportproyect.mapper;

import org.springframework.stereotype.Component;
import sfera.reportproyect.dto.response.ResDashBoard;
import sfera.reportproyect.dto.response.ResUser;
import sfera.reportproyect.dto.response.ResUserDTO;
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

    public ResUserDTO resUserDTO(User user, ResDashBoard report){
        return ResUserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .imgUrl(user.getImgUrl())
                .filialName(user.getFilial() != null ? user.getFilial().getName() : null)
                .report(report)
                .build();
    }
}
