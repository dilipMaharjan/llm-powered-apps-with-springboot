package com.dmed.llm_powered_apps_with_springboot.tool;

import com.dmed.llm_powered_apps_with_springboot.entity.HelpdeskTicket;
import com.dmed.llm_powered_apps_with_springboot.service.HelpdeskTicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * MCP Tools for Helpdesk Ticket Management
 * These tools allow AI models to interact with the helpdesk system
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HelpdeskTools {

    private final HelpdeskTicketService ticketService;

    /**
     * Tool to create a new helpdesk ticket
     */
    @Tool(description = "Create a new helpdesk ticket with the given details")
    public String createTicket(String title, String description, String priority, String assignedTo, String reportedBy) {
        log.info("AI Tool: Creating ticket - Title: {}, Priority: {}, Assigned: {}, Reported: {}", title, priority, assignedTo, reportedBy);

        try {
            HelpdeskTicket.TicketPriority ticketPriority = HelpdeskTicket.TicketPriority.valueOf(priority.toUpperCase());
            HelpdeskTicket ticket = new HelpdeskTicket();
            ticket.setTitle(title);
            ticket.setDescription(description);
            ticket.setPriority(ticketPriority);
            ticket.setAssignedTo(assignedTo);
            ticket.setReportedBy(reportedBy);
            ticket.setStatus(HelpdeskTicket.TicketStatus.OPEN);

            HelpdeskTicket savedTicket = ticketService.createTicket(ticket);

            return String.format("Successfully created ticket #%d: '%s' with %s priority, assigned to %s",
                    savedTicket.getId(), savedTicket.getTitle(), savedTicket.getPriority(), savedTicket.getAssignedTo());
        } catch (IllegalArgumentException e) {
            return String.format("Error: Invalid priority '%s'. Valid priorities are: LOW, MEDIUM, HIGH, CRITICAL", priority);
        } catch (Exception e) {
            log.error("Error creating ticket via AI tool", e);
            return "Error: Failed to create ticket. Please check the input parameters.";
        }
    }

    /**
     * Tool to find tickets by status
     */
    @Tool(description = "Find all helpdesk tickets with a specific status")
    public String findTicketsByStatus(String status) {
        log.info("AI Tool: Finding tickets with status: {}", status);

        try {
            HelpdeskTicket.TicketStatus ticketStatus = HelpdeskTicket.TicketStatus.valueOf(status.toUpperCase());
            List<HelpdeskTicket> tickets = ticketService.getTicketsByStatus(ticketStatus);

            if (tickets.isEmpty()) {
                return String.format("No tickets found with status: %s", status);
            }

            StringBuilder result = new StringBuilder();
            result.append(String.format("Found %d tickets with status %s:\n", tickets.size(), status));

            for (HelpdeskTicket ticket : tickets) {
                result.append(String.format("- Ticket #%d: '%s' (Priority: %s, Assigned: %s)\n",
                        ticket.getId(), ticket.getTitle(), ticket.getPriority(), ticket.getAssignedTo()));
            }

            return result.toString();
        } catch (IllegalArgumentException e) {
            return String.format("Error: Invalid status '%s'. Valid statuses are: OPEN, IN_PROGRESS, RESOLVED, CLOSED, ON_HOLD", status);
        }
    }

    /**
     * Tool to find tickets by priority
     */
    @Tool(description = "Find all helpdesk tickets with a specific priority level")
    public String findTicketsByPriority(String priority) {
        log.info("AI Tool: Finding tickets with priority: {}", priority);

        try {
            HelpdeskTicket.TicketPriority ticketPriority = HelpdeskTicket.TicketPriority.valueOf(priority.toUpperCase());
            List<HelpdeskTicket> tickets = ticketService.getTicketsByPriority(ticketPriority);

            if (tickets.isEmpty()) {
                return String.format("No tickets found with priority: %s", priority);
            }

            StringBuilder result = new StringBuilder();
            result.append(String.format("Found %d tickets with %s priority:\n", tickets.size(), priority));

            for (HelpdeskTicket ticket : tickets) {
                result.append(String.format("- Ticket #%d: '%s' (Status: %s, Assigned: %s)\n",
                        ticket.getId(), ticket.getTitle(), ticket.getStatus(), ticket.getAssignedTo()));
            }

            return result.toString();
        } catch (IllegalArgumentException e) {
            return String.format("Error: Invalid priority '%s'. Valid priorities are: LOW, MEDIUM, HIGH, CRITICAL", priority);
        }
    }

    /**
     * Tool to get ticket details by ID
     */
    @Tool(description = "Get detailed information about a specific helpdesk ticket by its ID")
    public String getTicketDetails(String ticketId) {
        log.info("AI Tool: Getting details for ticket ID: {}", ticketId);

        try {
            Long id = Long.parseLong(ticketId);
            Optional<HelpdeskTicket> ticketOpt = ticketService.getTicketById(id);

            if (ticketOpt.isEmpty()) {
                return String.format("No ticket found with ID: %s", ticketId);
            }

            HelpdeskTicket ticket = ticketOpt.get();
            return String.format(
                "Ticket #%d Details:\n" +
                "Title: %s\n" +
                "Description: %s\n" +
                "Status: %s\n" +
                "Priority: %s\n" +
                "Assigned To: %s\n" +
                "Reported By: %s\n" +
                "Created: %s\n" +
                "Updated: %s",
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority(),
                ticket.getAssignedTo(),
                ticket.getReportedBy(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
            );
        } catch (NumberFormatException e) {
            return String.format("Error: Invalid ticket ID '%s'. Please provide a valid number.", ticketId);
        }
    }

    /**
     * Tool to update ticket status
     */
    @Tool(description = "Update the status of a specific helpdesk ticket")
    public String updateTicketStatus(String ticketId, String newStatus) {
        log.info("AI Tool: Updating ticket {} status to: {}", ticketId, newStatus);

        try {
            Long id = Long.parseLong(ticketId);
            HelpdeskTicket.TicketStatus status = HelpdeskTicket.TicketStatus.valueOf(newStatus.toUpperCase());

            Optional<HelpdeskTicket> updatedTicket = ticketService.updateTicketStatus(id, status);

            if (updatedTicket.isEmpty()) {
                return String.format("No ticket found with ID: %s", ticketId);
            }

            HelpdeskTicket ticket = updatedTicket.get();
            return String.format("Successfully updated ticket #%d status to %s", ticket.getId(), ticket.getStatus());

        } catch (IllegalArgumentException e) {
            return String.format("Error: Invalid status '%s'. Valid statuses are: OPEN, IN_PROGRESS, RESOLVED, CLOSED, ON_HOLD", newStatus);
        } catch (NumberFormatException e) {
            return String.format("Error: Invalid ticket ID '%s'. Please provide a valid number.", ticketId);
        }
    }

    /**
     * Tool to get tickets assigned to a user
     */
    @Tool(description = "Find all helpdesk tickets assigned to a specific user")
    public String findTicketsByAssignee(String assignedTo) {
        log.info("AI Tool: Finding tickets assigned to: {}", assignedTo);

        List<HelpdeskTicket> tickets = ticketService.getTicketsByAssignee(assignedTo);

        if (tickets.isEmpty()) {
            return String.format("No tickets found assigned to: %s", assignedTo);
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("Found %d tickets assigned to %s:\n", tickets.size(), assignedTo));

        for (HelpdeskTicket ticket : tickets) {
            result.append(String.format("- Ticket #%d: '%s' (Status: %s, Priority: %s)\n",
                    ticket.getId(), ticket.getTitle(), ticket.getStatus(), ticket.getPriority()));
        }

        return result.toString();
    }

    /**
     * Tool to get a summary of all tickets
     */
    @Tool(description = "Get a summary of all helpdesk tickets in the system")
    public String getAllTicketsSummary() {
        log.info("AI Tool: Getting all tickets summary");

        List<HelpdeskTicket> tickets = ticketService.getAllTickets();

        if (tickets.isEmpty()) {
            return "No tickets found in the system.";
        }

        // Count by status
        long openCount = tickets.stream().filter(t -> t.getStatus() == HelpdeskTicket.TicketStatus.OPEN).count();
        long inProgressCount = tickets.stream().filter(t -> t.getStatus() == HelpdeskTicket.TicketStatus.IN_PROGRESS).count();
        long resolvedCount = tickets.stream().filter(t -> t.getStatus() == HelpdeskTicket.TicketStatus.RESOLVED).count();
        long closedCount = tickets.stream().filter(t -> t.getStatus() == HelpdeskTicket.TicketStatus.CLOSED).count();

        // Count by priority
        long criticalCount = tickets.stream().filter(t -> t.getPriority() == HelpdeskTicket.TicketPriority.CRITICAL).count();
        long highCount = tickets.stream().filter(t -> t.getPriority() == HelpdeskTicket.TicketPriority.HIGH).count();

        StringBuilder result = new StringBuilder();
        result.append(String.format("Helpdesk System Summary:\n"));
        result.append(String.format("Total Tickets: %d\n\n", tickets.size()));
        result.append(String.format("Status Breakdown:\n"));
        result.append(String.format("- Open: %d\n", openCount));
        result.append(String.format("- In Progress: %d\n", inProgressCount));
        result.append(String.format("- Resolved: %d\n", resolvedCount));
        result.append(String.format("- Closed: %d\n", closedCount));
        result.append(String.format("\nPriority Alerts:\n"));
        result.append(String.format("- Critical: %d\n", criticalCount));
        result.append(String.format("- High: %d\n", highCount));

        return result.toString();
    }
}
