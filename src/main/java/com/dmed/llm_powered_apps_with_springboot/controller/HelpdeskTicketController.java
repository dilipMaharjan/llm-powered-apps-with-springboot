package com.dmed.llm_powered_apps_with_springboot.controller;

import com.dmed.llm_powered_apps_with_springboot.dto.CreateTicketRequest;
import com.dmed.llm_powered_apps_with_springboot.dto.HelpdeskTicketDTO;
import com.dmed.llm_powered_apps_with_springboot.entity.HelpdeskTicket;
import com.dmed.llm_powered_apps_with_springboot.service.HelpdeskTicketService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tickets")
@Slf4j
public class HelpdeskTicketController {

    private final HelpdeskTicketService ticketService;

    public HelpdeskTicketController(HelpdeskTicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Create a new ticket
     * POST /api/v1/tickets
     */
    @PostMapping
    public ResponseEntity<HelpdeskTicketDTO> createTicket(@Valid @RequestBody CreateTicketRequest request) {
        log.info("Received request to create ticket: {}", request.getTitle());
        HelpdeskTicket createdTicket = ticketService.createTicket(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(HelpdeskTicketDTO.fromEntity(createdTicket));
    }

    /**
     * Get all tickets
     * GET /api/v1/tickets
     */
    @GetMapping
    public ResponseEntity<List<HelpdeskTicketDTO>> getAllTickets() {
        log.info("Received request to fetch all tickets");
        List<HelpdeskTicket> tickets = ticketService.getAllTickets();
        List<HelpdeskTicketDTO> dtos = tickets.stream()
                .map(HelpdeskTicketDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get ticket by ID
     * GET /api/v1/tickets/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<HelpdeskTicketDTO> getTicketById(@PathVariable Long id) {
        log.info("Received request to fetch ticket with ID: {}", id);
        return ticketService.getTicketById(id)
                .map(ticket -> ResponseEntity.ok(HelpdeskTicketDTO.fromEntity(ticket)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get tickets by status
     * GET /api/v1/tickets/search/status?status=OPEN
     */
    @GetMapping("/search/status")
    public ResponseEntity<List<HelpdeskTicketDTO>> getTicketsByStatus(@RequestParam HelpdeskTicket.TicketStatus status) {
        log.info("Received request to fetch tickets with status: {}", status);
        List<HelpdeskTicket> tickets = ticketService.getTicketsByStatus(status);
        List<HelpdeskTicketDTO> dtos = tickets.stream()
                .map(HelpdeskTicketDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get tickets by priority
     * GET /api/v1/tickets/search/priority?priority=HIGH
     */
    @GetMapping("/search/priority")
    public ResponseEntity<List<HelpdeskTicketDTO>> getTicketsByPriority(@RequestParam HelpdeskTicket.TicketPriority priority) {
        log.info("Received request to fetch tickets with priority: {}", priority);
        List<HelpdeskTicket> tickets = ticketService.getTicketsByPriority(priority);
        List<HelpdeskTicketDTO> dtos = tickets.stream()
                .map(HelpdeskTicketDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get tickets assigned to a user
     * GET /api/v1/tickets/search/assigned?assignedTo=john
     */
    @GetMapping("/search/assigned")
    public ResponseEntity<List<HelpdeskTicketDTO>> getTicketsByAssignee(@RequestParam String assignedTo) {
        log.info("Received request to fetch tickets assigned to: {}", assignedTo);
        List<HelpdeskTicket> tickets = ticketService.getTicketsByAssignee(assignedTo);
        List<HelpdeskTicketDTO> dtos = tickets.stream()
                .map(HelpdeskTicketDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get tickets reported by a user
     * GET /api/v1/tickets/search/reported?reportedBy=jane
     */
    @GetMapping("/search/reported")
    public ResponseEntity<List<HelpdeskTicketDTO>> getTicketsByReporter(@RequestParam String reportedBy) {
        log.info("Received request to fetch tickets reported by: {}", reportedBy);
        List<HelpdeskTicket> tickets = ticketService.getTicketsByReporter(reportedBy);
        List<HelpdeskTicketDTO> dtos = tickets.stream()
                .map(HelpdeskTicketDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Update an existing ticket
     * PUT /api/v1/tickets/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<HelpdeskTicketDTO> updateTicket(@PathVariable Long id, @RequestBody HelpdeskTicket updatedTicket) {
        log.info("Received request to update ticket with ID: {}", id);
        return ticketService.updateTicket(id, updatedTicket)
                .map(ticket -> ResponseEntity.ok(HelpdeskTicketDTO.fromEntity(ticket)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update ticket status
     * PATCH /api/v1/tickets/{id}/status?status=RESOLVED
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<HelpdeskTicketDTO> updateTicketStatus(@PathVariable Long id, @RequestParam HelpdeskTicket.TicketStatus status) {
        log.info("Received request to update status for ticket ID: {} to {}", id, status);
        return ticketService.updateTicketStatus(id, status)
                .map(ticket -> ResponseEntity.ok(HelpdeskTicketDTO.fromEntity(ticket)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a ticket
     * DELETE /api/v1/tickets/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        log.info("Received request to delete ticket with ID: {}", id);
        if (ticketService.deleteTicket(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
