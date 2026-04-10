# llm-powered-apps-with-springboot

A comprehensive helpdesk ticket management system with MCP (Model Context Protocol) tool integration.

## Overview

This application provides standard CRUD operations for managing helpdesk tickets and includes MCP tools for AI model
interaction with the system.

## Features

### Core Functionality

- **Ticket Management**: Complete CRUD operations for helpdesk tickets
- **Status Tracking**: Track ticket status (OPEN, IN_PROGRESS, RESOLVED, CLOSED, ON_HOLD)
- **Priority Management**: Set ticket priorities (LOW, MEDIUM, HIGH, CRITICAL)
- **Assignment & Reporting**: Assign tickets to users and track reporters
- **Search & Filtering**: Search tickets by status, priority, assignee, and reporter

### MCP (Model Context Protocol) Tools

- **Tool-Based AI Interaction**: AI models can directly interact with the helpdesk system through MCP tools
- **Available Tools**:
    - `createTicket`: Create new helpdesk tickets
    - `findTicketsByStatus`: Search tickets by status
    - `findTicketsByPriority`: Search tickets by priority
    - `getTicketDetails`: Get detailed ticket information
    - `updateTicketStatus`: Update ticket status
    - `findTicketsByAssignee`: Find tickets assigned to a user
    - `getAllTicketsSummary`: Get system-wide ticket statistics

## Technology Stack

- **Backend**: Spring Boot 3.5.8
- **Database**: H2 (in-memory for development)
- **AI Integration**: Spring AI 1.0.0 with MCP Server
- **Build Tool**: Maven
- **Java Version**: 20

## Prerequisites

1. **Java 20** or higher
2. **Maven 3.6+**

## Running the Application

1. **Clone the repository** (if applicable) and navigate to the project directory

2. **Start the application**:
    ```bash
    mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"
    ```

3. **Verify the application is running**:
    - API: http://localhost:8081/api/v1/tickets
    - H2 Console: http://localhost:8081/h2-console
        - JDBC URL: `jdbc:h2:file:~/helpdeskdb`
        - Username: `sa`
        - Password: `sa`

## Testing the Application

### Basic CRUD Operations

#### Create a ticket

```bash
curl -X POST http://localhost:8081/api/v1/tickets \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Cannot access email",
    "description": "User unable to log into email system",
    "priority": "HIGH",
    "assignedTo": "john",
    "reportedBy": "alice"
  }'
```

#### Get all tickets

```bash
curl http://localhost:8081/api/v1/tickets
```

#### Get ticket by ID

```bash
curl http://localhost:8081/api/v1/tickets/1
```

### MCP Tools

#### Create a ticket using MCP

```bash
curl -X POST http://localhost:8081/api/v1/tickets/ai/create \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Cannot access email",
    "description": "User unable to log into email system",
    "priority": "HIGH",
    "assignedTo": "john",
    "reportedBy": "alice"
  }'
```

#### Find tickets by status

```bash
curl -X GET http://localhost:8081/api/v1/tickets/ai/findByStatus?status=OPEN
```

#### Find tickets by priority

```bash
curl -X GET http://localhost:8081/api/v1/tickets/ai/findByPriority?priority=HIGH
```

#### Get ticket details

```bash
curl -X GET http://localhost:8081/api/v1/tickets/ai/details?id=1
```

#### Update ticket status

```bash
curl -X PATCH http://localhost:8081/api/v1/tickets/ai/updateStatus \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "status": "CLOSED"
  }'
```

#### Find tickets by assignee

```bash
curl -X GET http://localhost:8081/api/v1/tickets/ai/findByAssignee?assignee=john
```

#### Get all tickets summary

```bash
curl -X GET http://localhost:8081/api/v1/tickets/ai/allTicketsSummary
```

## API Documentation

For detailed API documentation including all endpoints, request/response formats, and examples,
see [HELPDESK_API.md](HELPDESK_API.md).

## Configuration

The application uses different profiles for different environments:

- **local**: Development profile with H2 database and MCP tools enabled
- **default**: Production-ready configuration (customize as needed)

Key configuration properties in `application-local.yml`:

- Database: H2 file-based database
- Server: Port 8081

## Architecture

The application follows a clean architecture pattern:

- **Controller Layer**: REST endpoints (`HelpdeskTicketController`)
- **Service Layer**: Business logic (`HelpdeskTicketService`)
- **Repository Layer**: Data access (`HelpdeskTicketRepository`)
- **Model Layer**: Entities and DTOs (`HelpdeskTicket`, `HelpdeskTicketDTO`)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
