package sfera.reportproyect.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageDto {
    private Long conversationId;
    private Long senderId;      // keyin JWTdan olgan yaxshi
    private String text;
}