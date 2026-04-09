package com.dmed.llm_powered_apps_with_springboot.service;

import com.dmed.llm_powered_apps_with_springboot.entity.HelpdeskTicket;
import com.dmed.llm_powered_apps_with_springboot.repository.HelpdeskTicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HelpdeskTicketService {

    private final HelpdeskTicketRepository ticketRepository;

    public HelpdeskTicketService(HelpdeskTicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * Create a new helpdesk ticket
     */
    public HelpdeskTicket createTicket(HelpdeskTicket ticket) {
        log.info("Creating new ticket: {}", ticket.getTitle());
        return ticketRepository.save(ticket);
    }

    /**
     * Get ticket by ID
     */
    public Optional<HelpdeskTicket> getTicketById(Long id) {
        log.info("Fetching ticket with ID: {}", id);
        return ticketRepository.findById(id);
    }

    /**
     * Get all tickets
     */
    public List<HelpdeskTicket> getAllTickets() {
        log.info("Fetching all tickets");
        return ticketRepository.findAll();
    }

    /**
     * Get tickets by status
     */
    public List<HelpdeskTicket> getTicketsByStatus(HelpdeskTicket.TicketStatus status) {
        log.info("Fetching tickets with status: {}", status);
        return ticketRepository.findByStatus(status);
    }

    /**
     * Get tickets by priority
     */
    public List<HelpdeskTicket> getTicketsByPriority(HelpdeskTicket.TicketPriority priority) {
        log.info("Fetching tickets with priority: {}", priority);
        return ticketRepository.findByPriority(priority);
    }

    /**
     * Get tickets assigned to a user
     */
    public List<HelpdeskTicket> getTicketsByAssignee(String assignedTo) {
        log.info("Fetching tickets assigned to: {}", assignedTo);
        return ticketRepository.findByAssignedTo(assignedTo);
    }

    /**
     * Get tickets reported by a user
     */
    public List<HelpdeskTicket> getTicketsByReporter(String reportedBy) {
        log.info("Fetching tickets reported by: {}", reportedBy);
        return ticketRepository.findByReportedBy(reportedBy);
    }

    /**
     * Update an existing ticket
     */
    public Optional<HelpdeskTicket> updateTicket(Long id, HelpdeskTicket updatedTicket) {
        log.info("Updating ticket with ID: {}", id);
        return ticketRepository.findById(id).map(existingTicket -> {
            if (updatedTicket.getTitle() != null) {
                existingTicket.setTitle(updatedTicket.getTitle());
            }
            if (updatedTicket.getDescription() != null) {
                existingTicket.setDescription(updatedTicket.getDescription());
            }
            if (updatedTicket.getStatus() != null) {
                existingTicket.setStatus(updatedTicket.getStatus());
            }
            if (updatedTicket.getPriority() != null) {
                existingTicket.setPriority(updatedTicket.getPriority());
            }
            if (updatedTicket.getAssignedTo() != null) {
                existingTicket.setAssignedTo(updatedTicket.getAssignedTo());
            }
            if (updatedTicket.getStatus() == HelpdeskTicket.TicketStatus.RESOLVED && existingTicket.getResolvedAt() == null) {
                existingTicket.setResolvedAt(LocalDateTime.now());
            }
            return ticketRepository.save(existingTicket);
        });
    }

    /**
     * Delete a ticket
     */
    public boolean deleteTicket(Long id) {
        log.info("Deleting ticket with ID: {}", id);
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Update ticket status
     */
    public Optional<HelpdeskTicket> updateTicketStatus(Long id, HelpdeskTicket.TicketStatus status) {
        log.info("Updating ticket status for ID: {} to {}", id, status);
        return ticketRepository.findById(id).map(ticket -> {
            ticket.setStatus(status);
            if (status == HelpdeskTicket.TicketStatus.RESOLVED && ticket.getResolvedAt() == null) {
                ticket.setResolvedAt(LocalDateTime.now());
            }
            return ticketRepository.save(ticket);
        });
    }
}

