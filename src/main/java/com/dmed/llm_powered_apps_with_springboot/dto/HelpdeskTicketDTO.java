package com.dmed.llm_powered_apps_with_springboot.dto;

import com.dmed.llm_powered_apps_with_springboot.entity.HelpdeskTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelpdeskTicketDTO {

    private Long id;
    private String title;
    private String description;
    private HelpdeskTicket.TicketStatus status;
    private HelpdeskTicket.TicketPriority priority;
    private String assignedTo;
    private String reportedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;

    // Static factory method to convert Entity to DTO
    public static HelpdeskTicketDTO fromEntity(HelpdeskTicket ticket) {
        return new HelpdeskTicketDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority(),
                ticket.getAssignedTo(),
                ticket.getReportedBy(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt(),
                ticket.getResolvedAt()
        );
    }

    // Method to convert DTO to Entity
    public HelpdeskTicket toEntity() {
        HelpdeskTicket ticket = new HelpdeskTicket();
        ticket.setId(this.id);
        ticket.setTitle(this.title);
        ticket.setDescription(this.description);
        ticket.setStatus(this.status);
        ticket.setPriority(this.priority);
        ticket.setAssignedTo(this.assignedTo);
        ticket.setReportedBy(this.reportedBy);
        ticket.setCreatedAt(this.createdAt);
        ticket.setUpdatedAt(this.updatedAt);
        ticket.setResolvedAt(this.resolvedAt);
        return ticket;
    }
}
