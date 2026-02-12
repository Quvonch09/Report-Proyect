package sfera.reportproyect.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.MessageDto;
import sfera.reportproyect.dto.SendMessageDto;
import sfera.reportproyect.entity.Conversation;
import sfera.reportproyect.entity.Message;
import sfera.reportproyect.entity.enums.MessageType;
import sfera.reportproyect.exception.DataNotFoundException;
import sfera.reportproyect.repository.ConversationParticipantRepository;
import sfera.reportproyect.repository.ConversationRepository;
import sfera.reportproyect.repository.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final ConversationParticipantRepository participantRepository;


    @Transactional
    public MessageDto save(SendMessageDto dto) {

        // 1) Conversation mavjudligini tekshirish
        Conversation conversation = conversationRepository.findById(dto.getConversationId())
                .orElseThrow(() -> new DataNotFoundException("Message not found"));

        // 2) ✅ Sender shu conversation ichidami? (Participant tekshiruv)
        if (!participantRepository.existsByConversationIdAndUserId(dto.getConversationId(), dto.getSenderId())) {
            throw new IllegalArgumentException("User is not a participant of this conversation");
        }

        // 3) Message yasash
        String text = dto.getText() == null ? null : dto.getText().trim();
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Message text is empty");
        }

        Message message = Message.builder()
                .conversationId(dto.getConversationId())
                .senderId(dto.getSenderId())
                .type(MessageType.TEXT)
                .text(text)
                .build();

        // 3) DB ga saqlash
        Message saved = messageRepository.save(message);

        // 4) Conversation lastMessage ni update qilish (chat list uchun)
        conversation.setLastMessageAt(saved.getCreatedAt());
        conversation.setLastMessageId(saved.getId());
        conversationRepository.save(conversation);

        // 5) DTO qaytarish
        return MessageDto.builder()
                .id(saved.getId())
                .conversationId(saved.getConversationId())
                .senderId(saved.getSenderId())
                .text(saved.getText())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}