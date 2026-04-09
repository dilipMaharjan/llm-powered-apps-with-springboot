package com.dmed.llm_powered_apps_with_springboot.dto;

import com.dmed.llm_powered_apps_with_springboot.entity.HelpdeskTicket;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Priority is required")
    private HelpdeskTicket.TicketPriority priority;

    @NotBlank(message = "Assigned person is required")
    private String assignedTo;

    @NotBlank(message = "Reporter is required")
    private String reportedBy;

    // Convert to Entity (status defaults to OPEN)
    public HelpdeskTicket toEntity() {
        HelpdeskTicket ticket = new HelpdeskTicket();
        ticket.setTitle(this.title);
        ticket.setDescription(this.description);
        ticket.setStatus(HelpdeskTicket.TicketStatus.OPEN);
        ticket.setPriority(this.priority);
        ticket.setAssignedTo(this.assignedTo);
        ticket.setReportedBy(this.reportedBy);
        return ticket;
    }
}
