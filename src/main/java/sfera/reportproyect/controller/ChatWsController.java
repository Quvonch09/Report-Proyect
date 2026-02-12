package sfera.reportproyect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import sfera.reportproyect.dto.MessageDto;
import sfera.reportproyect.dto.SendMessageDto;
import sfera.reportproyect.service.MessageService;

@Controller
@RequiredArgsConstructor
public class ChatWsController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat.send") // client SEND: /app/chat.send
    public void send(@Payload SendMessageDto dto) {

        // 1) DB ga saqlab qo'yasiz
        MessageDto saved = messageService.save(dto);

        // 2) Shu conversationga ulangan hammaga yuborasiz
        messagingTemplate.convertAndSend(
                "/topic/conversations/" + saved.getConversationId(),
                saved
        );
    }
}
