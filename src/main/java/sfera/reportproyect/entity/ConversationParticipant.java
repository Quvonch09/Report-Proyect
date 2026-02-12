package sfera.reportproyect.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "conversation_participants",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_conv_user", columnNames = {"conversation_id", "user_id"})
        },
        indexes = {
                @Index(name = "idx_cp_user", columnList = "user_id"),
                @Index(name = "idx_cp_conv", columnList = "conversation_id")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ConversationParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conversation_id", nullable = false)
    private Long conversationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    // Unread hisoblash uchun eng qulay:
    // user oxirgi qaysi message gacha o‘qiganini saqlaydi
    @Column(name = "last_read_message_id")
    private Long lastReadMessageId;

    @Column(name = "joined_at", nullable = false, updatable = false)
    private Instant joinedAt;

    @PrePersist
    public void prePersist() {
        if (joinedAt == null) joinedAt = Instant.now();
    }
}
