package sfera.reportproyect.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Long id;
    private Long conversationId;
    private Long senderId;
    private String text;
    private Instant createdAt;
}