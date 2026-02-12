package sfera.reportproyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sfera.reportproyect.entity.Conversation;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("""
       SELECT c FROM Conversation c
       WHERE c.type = sfera.reportproyect.entity.enums.ConversationType.DIRECT
         AND c.id IN (
             SELECT p1.conversationId FROM ConversationParticipant p1
             WHERE p1.userId = :user1
         )
         AND c.id IN (
             SELECT p2.conversationId FROM ConversationParticipant p2
             WHERE p2.userId = :user2
         )
    """)
    Optional<Conversation> findDirectBetween(@Param("user1") Long user1,
                                             @Param("user2") Long user2);
}