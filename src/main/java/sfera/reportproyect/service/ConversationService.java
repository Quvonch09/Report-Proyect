package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sfera.reportproyect.entity.Conversation;
import sfera.reportproyect.entity.ConversationParticipant;
import sfera.reportproyect.entity.enums.ConversationType;
import sfera.reportproyect.repository.ConversationParticipantRepository;
import sfera.reportproyect.repository.ConversationRepository;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationParticipantRepository participantRepository;

    @Transactional
    public Long getOrCreateDirect(Long user1Id, Long user2Id) {

        if (user1Id == null || user2Id == null) {
            throw new IllegalArgumentException("User ids are required");
        }
        if (user1Id.equals(user2Id)) {
            throw new IllegalArgumentException("Direct chat uchun userlar har xil bo‘lishi kerak");
        }

        // 1) oldin bor DIRECT conversation bormi?
        return conversationRepository.findDirectBetween(user1Id, user2Id)
                .map(Conversation::getId)
                .orElseGet(() -> createDirect(user1Id, user2Id));
    }

    private Long createDirect(Long user1Id, Long user2Id) {
        Conversation c = Conversation.builder()
                .type(ConversationType.DIRECT)
                .build();
        Conversation saved = conversationRepository.save(c);

        participantRepository.save(
                ConversationParticipant.builder()
                        .conversationId(saved.getId())
                        .userId(user1Id)
                        .lastReadMessageId(null)
                        .build()
        );

        participantRepository.save(
                ConversationParticipant.builder()
                        .conversationId(saved.getId())
                        .userId(user2Id)
                        .lastReadMessageId(null)
                        .build()
        );

        return saved.getId();
    }
}
