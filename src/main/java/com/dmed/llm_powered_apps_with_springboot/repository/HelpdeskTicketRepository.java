package com.dmed.llm_powered_apps_with_springboot.repository;

import com.dmed.llm_powered_apps_with_springboot.entity.HelpdeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpdeskTicketRepository extends JpaRepository<HelpdeskTicket, Long> {
    List<HelpdeskTicket> findByStatus(HelpdeskTicket.TicketStatus status);

    List<HelpdeskTicket> findByPriority(HelpdeskTicket.TicketPriority priority);

    List<HelpdeskTicket> findByAssignedTo(String assignedTo);

    List<HelpdeskTicket> findByReportedBy(String reportedBy);
}

