# LLM-Powered Apps with Spring Boot

A comprehensive guide to building AI-powered applications using Spring Boot and Spring AI framework.

## Table of Contents

### 1. **Project Setup & Configuration**

- [1.1 Maven Dependencies](#11-maven-dependencies)
- [1.2 Spring Boot Configuration](#12-spring-boot-configuration)
- [1.3 Docker Compose Setup](#13-docker-compose-setup)

### 2. **Core AI Concepts**

- [2.1 Large Language Models (LLMs)](#21-large-language-models-llms)
- [2.2 Retrieval-Augmented Generation (RAG)](#22-retrieval-augmented-generation-rag)
- [2.3 Vector Stores](#23-vector-stores)
- [2.4 Embeddings](#24-embeddings)

### 3. **Spring AI Framework**

- [3.1 Spring AI Overview](#31-spring-ai-overview)
- [3.2 Ollama Integration](#32-ollama-integration)
- [3.3 ChatClient Configuration](#33-chatclient-configuration)
- [3.4 Chat Memory](#34-chat-memory)

### 4. **Vector Store Implementation**

- [4.1 Qdrant Vector Database](#41-qdrant-vector-database)
- [4.2 Vector Store Configuration](#42-vector-store-configuration)
- [4.3 Similarity Search](#43-similarity-search)

### 5. **Document Processing**

- [5.1 Document Readers (Tika)](#51-document-readers-tika)
- [5.2 Text Splitting Strategies](#52-text-splitting-strategies)
- [5.3 Token-Based Text Splitter](#53-token-based-text-splitter)
- [5.4 PDF Document Loading](#54-pdf-document-loading)

### 6. **RAG Implementation**

- [6.1 Data Loading & Initialization](#61-data-loading--initialization)
- [6.2 Manual RAG Implementation](#62-manual-rag-implementation)
- [6.3 RAG with Retrieval Advisor](#63-rag-with-retrieval-advisor)
- [6.4 Document Retrieval Configuration](#64-document-retrieval-configuration)

### 7. **Advisors**

- [7.1 Understanding Advisors](#71-understanding-advisors)
- [7.2 SimpleLoggerAdvisor](#72-simpleloggeradvisor)
- [7.3 MessageChatMemoryAdvisor](#73-messagechatmemoryadvisor)
- [7.4 RetrievalAugmentationAdvisor](#74-retrievalaugmentationadvisor)

### 8. **Prompt Engineering**

- [8.1 System Prompts](#81-system-prompts)
- [8.2 Prompt Templates](#82-prompt-templates)
- [8.3 Parameterized Prompts](#83-parameterized-prompts)

### 9. **REST API Design**

- [9.1 Controller Implementation](#91-controller-implementation)
- [9.2 RAG Endpoints](#92-rag-endpoints)
- [9.3 Request/Response Handling](#93-requestresponse-handling)
- [9.4 User Context Management](#94-user-context-management)

### 10. **Service Layer**

- [10.1 ChatService Implementation](#101-chatservice-implementation)
- [10.2 Vector Store Integration](#102-vector-store-integration)
- [10.3 Conversation Management](#103-conversation-management)

### 11. **Best Practices**

- [11.1 Security Considerations (CVE Management)](#111-security-considerations-cve-management)
- [11.2 Logging Configuration](#112-logging-configuration)
- [11.3 Dependency Management](#113-dependency-management)
- [11.4 Error Handling](#114-error-handling)

### 12. **Advanced Topics**

- [12.1 Similarity Thresholds](#121-similarity-thresholds)
- [12.2 Top-K Document Retrieval](#122-top-k-document-retrieval)
- [12.3 Model Configuration (Temperature, Top-K, Top-P)](#123-model-configuration-temperature-top-k-top-p)
- [12.4 Chunk Size Optimization](#124-chunk-size-optimization)

---

## Detailed Implementation Guide

### 1.1 Maven Dependencies

The project uses the following key dependencies:

- **Spring Boot 3.5.8** - Core framework
- **Spring AI 1.1.2** - AI integration framework
- **Ollama** - Local LLM integration
- **Qdrant** - Vector store database
- **Apache Tika** - Document reading
- **Lombok** - Boilerplate reduction

**Location**: `pom.xml`

### 1.2 Spring Boot Configuration

Configuration files manage:

- Application name and profiles
- AI model settings (Ollama with llama3.2)
- Vector store configuration (Qdrant)
- Model parameters (temperature, top-k, top-p, repeat-penalty, seed)
- Docker Compose integration

**Locations**:

- `src/main/resources/application.yml`
- `src/main/resources/application-local.yml`

### 1.3 Docker Compose Setup

Qdrant vector database runs as a containerized service with ports:

- 6333 - HTTP API
- 6334 - gRPC API

**Location**: `compose.yml`

### 2.1 Large Language Models (LLMs)

LLMs are AI models designed to understand and generate human language. This project uses:

- **Ollama** as the LLM provider
- **llama3.2** as the specific model

**Implementation**: `ChatConfig.java`, `application-local.yml`

### 2.2 Retrieval-Augmented Generation (RAG)

RAG combines:

1. **Retrieval**: Finding relevant information from a knowledge base
2. **Augmentation**: Enhancing the prompt with retrieved context
3. **Generation**: Using LLM to generate responses based on context

**Implementation**: `ChatService.java` (two approaches demonstrated)

### 2.3 Vector Stores

Vector stores efficiently store and retrieve high-dimensional embeddings for semantic search. This project uses Qdrant
with:

- Collection name: "rag"
- Automatic schema initialization
- Support for similarity search

**Implementation**: `DataLoader.java`, `ChatService.java`

### 2.4 Embeddings

Embeddings convert text into numerical vectors that capture semantic meaning, enabling similarity search.

**Implementation**: Handled automatically by Spring AI framework with Qdrant

### 3.1 Spring AI Overview

Spring AI provides:

- Integration with various AI models
- Vector store abstractions
- Document processing utilities
- RAG pattern support
- Memory and conversation management

### 3.2 Ollama Integration

Ollama provides local LLM hosting with configuration:

- Model: llama3.2
- Temperature: 0 (deterministic)
- Top-K: 1, Top-P: 1
- Repeat penalty: 1
- Seed: 42 (reproducibility)

**Location**: `application-local.yml`

### 3.3 ChatClient Configuration

Two ChatClient beans configured:

1. **chatClientForRag**: Manual RAG implementation with logger and memory advisors
2. **chatClientForRagWithAdvisor**: Automated RAG with retrieval augmentation advisor

**Location**: `ChatConfig.java`

### 3.4 Chat Memory

Conversation context managed per user using:

- `MessageChatMemoryAdvisor`
- Conversation ID based on username
- Maintains chat history across requests

**Implementation**: `ChatConfig.java`, `ChatService.java`

### 4.1 Qdrant Vector Database

High-performance vector similarity search engine features:

- Docker-based deployment
- gRPC API support
- Collection-based organization
- Automatic schema initialization

### 4.2 Vector Store Configuration

Spring AI VectorStore configuration:

- Host: localhost
- Port: 6334 (gRPC)
- Collection: "rag"
- Auto-initialization enabled

**Location**: `application-local.yml`

### 4.3 Similarity Search

Search parameters:

- **Query**: User prompt
- **Top-K**: 3 documents
- **Similarity Threshold**: 0.5 (50%)

**Implementation**: `ChatService.java`

### 5.1 Document Readers (Tika)

Apache Tika extracts text from various document formats including PDF. Configured with:

- Spring Resource loading
- Classpath-based document access

**Implementation**: `DataLoader.java`

### 5.2 Text Splitting Strategies

Documents are split into chunks for:

- Better embedding quality
- Reduced token usage
- More precise retrieval

### 5.3 Token-Based Text Splitter

Configuration:

- **Chunk Size**: 100 tokens
- **Max Chunks**: 400
- Preserves semantic boundaries

**Implementation**: `DataLoader.java`

### 5.4 PDF Document Loading

Process:

1. Load PDF using TikaDocumentReader
2. Extract text content
3. Split into chunks
4. Generate embeddings
5. Store in vector database

**Resource**: `src/main/resources/pdf/code-of-conduct.pdf`

### 6.1 Data Loading & Initialization

`@PostConstruct` methods load data at startup:

- Sample text data (Spring Boot, RAG, Vector stores, LLM concepts)
- PDF documents (Code of Conduct)

**Implementation**: `DataLoader.java`

### 6.2 Manual RAG Implementation

Endpoint: `/api/v1/rag/coc`

Steps:

1. Create SearchRequest with query
2. Perform similarity search in vector store
3. Extract document text
4. Combine with system prompt template
5. Send enhanced prompt to ChatClient
6. Return generated response

**Implementation**: `ChatService.getChatResponseFromRag()`

### 6.3 RAG with Retrieval Advisor

Endpoint: `/api/v1/rag/coc-with-advisor`

Simplified approach using RetrievalAugmentationAdvisor:

- Automatic document retrieval
- Transparent context augmentation
- Less code, same functionality

**Implementation**: `ChatService.getChatResponseFromRagWithRetriverAdvisor()`

### 6.4 Document Retrieval Configuration

VectorStoreDocumentRetriever settings:

- Vector store reference
- Top-K: 3
- Similarity threshold: 0.5

**Implementation**: `ChatConfig.retrievalAugmentationAdvisor()`

### 7.1 Understanding Advisors

Advisors are interceptors that:

- Modify requests/responses
- Add context (memory, retrieved documents)
- Enable logging
- Implement cross-cutting concerns

### 7.2 SimpleLoggerAdvisor

Logs:

- User prompts
- System prompts
- Model responses
- Useful for debugging

### 7.3 MessageChatMemoryAdvisor

Manages conversation history:

- Stores messages per conversation ID
- Provides context across requests
- Enables multi-turn conversations

### 7.4 RetrievalAugmentationAdvisor

Automates RAG pattern:

- Retrieves relevant documents
- Augments prompts automatically
- Configurable retrieval parameters

### 8.1 System Prompts

Define LLM behavior and constraints. This project uses strict document-based answering.

### 8.2 Prompt Templates

String Template (ST) format allows:

- Parameterization
- Reusable prompt structures
- Clean separation of concerns

**Location**: `src/main/resources/promptTemplates/systemPromptTemplateForRag.st`

### 8.3 Parameterized Prompts

Parameters used:

- **documents**: Retrieved context
- **question**: User query

**Implementation**: `ChatService.java`

### 9.1 Controller Implementation

REST controller exposes three endpoints:

1. `/api/v1/rag` - Basic RAG
2. `/api/v1/rag/coc` - Code of Conduct RAG
3. `/api/v1/rag/coc-with-advisor` - RAG with advisor

**Location**: `RagController.java`

### 9.2 RAG Endpoints

All endpoints:

- Accept query parameters (`prompt`)
- Require headers (`username`)
- Return string responses
- Use GET method

### 9.3 Request/Response Handling

ResponseEntity wrapper provides:

- HTTP status codes
- Response body
- Content negotiation

### 9.4 User Context Management

Username header enables:

- Per-user conversation history
- Isolated chat sessions
- User-specific context

### 10.1 ChatService Implementation

Service layer contains:

- Business logic
- ChatClient interaction
- Vector store operations
- Prompt orchestration

**Location**: `ChatService.java`

### 10.2 Vector Store Integration

Direct VectorStore access for:

- Manual similarity search
- Custom retrieval logic
- Fine-grained control

### 10.3 Conversation Management

Uses `CONVERSATION_ID` parameter to:

- Maintain chat history
- Enable contextual responses
- Support multi-user scenarios

### 11.1 Security Considerations (CVE Management)

Project includes CVE fixes:

- **Logback 1.5.32** - Updated version
- **gRPC BOM 1.75.0** - Fixes CVE-2025-55163
- **AssertJ 3.27.7** - Updated version

**Location**: `pom.xml`

### 11.2 Logging Configuration

Debug-level logging enabled for:

- Spring AI framework
- Request/response tracking
- Troubleshooting

**Location**: `application-local.yml`

### 11.3 Dependency Management

BOM (Bill of Materials) approach:

- Spring AI BOM for version consistency
- gRPC BOM for security patches
- Centralized version management

### 11.4 Error Handling

Basic error handling through:

- Spring Boot defaults
- ResponseEntity wrappers
- Exception propagation

### 12.1 Similarity Thresholds

Controls retrieval quality:

- Value: 0.5 (50% similarity)
- Filters irrelevant documents
- Balances precision vs recall

### 12.2 Top-K Document Retrieval

Limits retrieved documents:

- Value: 3 documents
- Reduces token usage
- Improves response time
- Focuses on most relevant content

### 12.3 Model Configuration (Temperature, Top-K, Top-P)

Model parameters affect generation:

- **Temperature 0**: Deterministic, focused responses
- **Top-K 1**: Most likely token only
- **Top-P 1**: No nucleus sampling
- **Seed 42**: Reproducible results

### 12.4 Chunk Size Optimization

Chunk size (100 tokens) balances:

- Embedding quality
- Context relevance
- Token limits
- Processing efficiency

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Ollama with llama3.2 model

### Running the Application

1. **Start Qdrant Vector Store**:
   ```bash
   docker-compose up -d
   ```

2. **Build the Application**:
   ```bash
   ./mvnw clean install
   ```

3. **Run the Application**:
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
   ```

### Testing the Endpoints

**Manual RAG**:

```bash
curl -X GET "http://localhost:8080/api/v1/rag/coc?prompt=What%20is%20the%20code%20of%20conduct?" \
  -H "username: testuser"
```

**RAG with Advisor**:

```bash
curl -X GET "http://localhost:8080/api/v1/rag/coc-with-advisor?prompt=What%20is%20the%20code%20of%20conduct?" \
  -H "username: testuser"
```

---

## Project Structure

```
src/
├── main/
│   ├── java/com/dmed/llm_powered_apps_with_springboot/
│   │   ├── LlmPoweredAppsWithSpringBootApplication.java  # Main application
│   │   ├── config/
│   │   │   └── ChatConfig.java                           # ChatClient & Advisor configuration
│   │   ├── controller/
│   │   │   └── RagController.java                        # REST endpoints
│   │   ├── service/
│   │   │   └── ChatService.java                          # Business logic
│   │   └── rag/
│   │       └── DataLoader.java                           # Document loading & initialization
│   └── resources/
│       ├── application.yml                                # Base configuration
│       ├── application-local.yml                          # Local profile configuration
│       ├── pdf/
│       │   └── code-of-conduct.pdf                       # Sample PDF document
│       └── promptTemplates/
│           └── systemPromptTemplateForRag.st             # System prompt template
```

---

## Key Learning Outcomes

After working through this project, you will understand:

1. ✅ How to integrate LLMs with Spring Boot applications
2. ✅ Implementation of RAG pattern (manual and automated)
3. ✅ Vector store configuration and usage
4. ✅ Document processing and embedding generation
5. ✅ Prompt engineering and template management
6. ✅ Conversation memory and context management
7. ✅ REST API design for AI applications
8. ✅ Advisor pattern for modular AI functionality
9. ✅ Similarity search and document retrieval
10. ✅ Model configuration and parameter tuning

---

## Technologies Used

- **Spring Boot 3.5.8** - Application framework
- **Spring AI 1.1.2** - AI integration
- **Ollama** - Local LLM runtime
- **Qdrant** - Vector database
- **Apache Tika** - Document parsing
- **Lombok** - Code generation
- **Docker** - Containerization
- **Maven** - Build tool

---

## References

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Ollama](https://ollama.ai/)
- [Qdrant Vector Database](https://qdrant.tech/)
- [RAG Pattern](https://www.promptingguide.ai/techniques/rag)
- [Spring Boot](https://spring.io/projects/spring-boot)

---

## License

This project is for educational purposes.
