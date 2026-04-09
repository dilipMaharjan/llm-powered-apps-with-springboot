package com.dmed.llm_powered_apps_with_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "helpdesk_tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelpdeskTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Column(nullable = false)
    private String assignedTo;

    @Column(nullable = false)
    private String reportedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime resolvedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum TicketStatus {
        OPEN, IN_PROGRESS, RESOLVED, CLOSED, ON_HOLD
    }

    public enum TicketPriority {
        LOW, MEDIUM, HIGH, CRITICAL
    }
}

