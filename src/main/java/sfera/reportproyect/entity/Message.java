package sfera.reportproyect.entity;

import jakarta.persistence.*;
import lombok.*;
import sfera.reportproyect.entity.enums.MessageType;

import java.time.Instant;

@Entity
@Table(name = "messages", indexes = {
        @Index(name = "idx_msg_conv_created", columnList = "conversation_id, created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conversation_id", nullable = false)
    private Long conversationId;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type; // TEXT, IMAGE ...

    @Column(columnDefinition = "text")
    private String text;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
        if (type == null) type = MessageType.TEXT;
    }
}