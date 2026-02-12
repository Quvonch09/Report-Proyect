package sfera.reportproyect.entity;

import jakarta.persistence.*;
import lombok.*;
import sfera.reportproyect.entity.enums.ConversationType;

import java.time.Instant;

@Entity
@Table(name = "conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationType type; // DIRECT/GROUP

    @Column(name = "last_message_at")
    private Instant lastMessageAt;

    @Column(name = "last_message_id")
    private Long lastMessageId;
}