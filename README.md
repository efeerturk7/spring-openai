# 🚗 Gallerist AI - Advanced Spring AI Assistant

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring AI](https://img.shields.io/badge/Spring_AI-2.0.0--M5-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Ollama](https://img.shields.io/badge/Ollama-Local_LLM-000000?style=for-the-badge&logo=linux&logoColor=white)
![Llama 3](https://img.shields.io/badge/Model-Llama_3-0466C8?style=for-the-badge&logo=meta&logoColor=white)
![Project Reactor](https://img.shields.io/badge/WebFlux-Reactive-2088FF?style=for-the-badge&logo=react&logoColor=white)

**Gallerist AI** is an advanced, production-ready AI Assistant API designed to manage car gallery operations utilizing local Large Language Models (LLMs).

This project demonstrates a modern **AI-Integrated Backend Architecture**. It goes far beyond a simple API call, implementing **Enterprise-Grade AI Security (SafeGuard)**, **Vector-based Memory**, **Reactive Streaming (WebFlux)**, and **Advanced Prompt Engineering** using Spring AI and a local Ollama instance.

---

## 🔗 API TESTING & USAGE

The API runs locally using your machine's hardware to process the Llama 3 model safely and privately without cloud costs.

👉 **Ready to test via Postman or any REST Client at:** `http://localhost:8082/api/spring-ai`

---

## 🏗 System Architecture & Workflow

### 🔄 Request Lifecycle (AI Processing Flow with HITL)
How a prompt travels securely from the user, gets evaluated, and waits for human approval:

> **🌍 Client Request** 👉  **🛡️ SafeGuard Advisor** 👉  **🧠 Context Injection** 👉 **🤖 AI Generates Plan** 👉 **⏸️ PAUSE (Saved to Postgres)** 👉 **👨‍💻 Human Approves** 👉 **▶️ RESUME (AI Executes Tools)** 👉 **⚡ Reactive JSON Output**

### ⚙️ Runtime Architecture
How the application handles complex AI tasks in production:

1.  **🌍 Client Request:** User sends a query (e.g., "Create an ad for this car" or "Analyze this comment").
2.  **🛡️ Security Layer (`SafeGuardAdvisor`):** Intercepts the request. Checks for Prompt Injection or malicious keywords. Blocks hackers before hitting the AI.
3.  **💾 Memory Retrieval:** Checks previous conversations and injects context (Vector/In-Memory).
4.  **☕ Spring Boot Core:** Packages the System Prompt, Few-Shot examples, and User data.
5.  **🤖 Local LLM (Ollama):** Processes the data securely on local hardware.

---

## 🚀 Key Technical Features (Advanced AI Implementation)

This project heavily focuses on AI stability, security, and response performance.

### 1. 🛡️ Custom SafeGuard Architecture (Security First)
Implemented a custom `SafeGuardAdvisor` utilizing both `CallAdvisor` and `StreamAdvisor` interfaces:
* **Prompt Injection Protection:** Automatically detects and throws exceptions for malicious prompts (e.g., "forget rules", "database passwords").
* **Anti-Leaking Armor:** System prompts are strictly instructed to never reveal internal configurations to end-users.

### 2. ⚡ Reactive Streaming (Project Reactor)
* **Typewriter UX (`/generate-ad`):** Uses `Flux<String>` and `TEXT_EVENT_STREAM_VALUE` to stream AI responses word-by-word. This drastically improves user experience by eliminating the long wait times of block-responses.

### 3. 🧠 Advanced Prompt Engineering
* **System vs. User Prompt Isolation:** Roles and behaviors are strictly defined in the system prompt.
* **Few-Shot Prompting:** The AI is provided with input-output templates to guarantee 100% stable and predictable classification results.

### 4. 🧩 Structured Data Extraction (Entity Output)
* Avoids messy text parsing. Utilizes `BeanOutputConverter` to force the Llama 3 model to return valid JSON that automatically maps to Java POJOs (`CarExtractionDTO`).

### 5. 🗂️ Embeddings & Vector Space Foundation
* Successfully integrated `EmbeddingModel` to convert plain text into high-dimensional mathematical vectors, laying the groundwork for full **RAG (Retrieval-Augmented Generation)** integration.
### 6. 👁️ Multimodal AI (Vision / Image-to-Text)
* **Local Visual Processing:** Integrated the **LLaVA** model to process physical image files (`MultipartFile`) locally without relying on paid, cloud-based vision APIs (like OpenAI Vision).
* **Automated Expertise:** Analyzes uploaded car photos and generates detailed text-based condition reports (e.g., detecting scratches, dents, or identifying car models).
### 7. 📚 Local RAG Architecture (Retrieval-Augmented Generation)
Implemented a complete "Open Book" RAG pipeline to chat with private company policies without exposing data to external APIs.
* **Ingestion Pipeline (ETL):** Utilizes `TokenTextSplitter` (Builder Pattern) to chunk large documents and the local `mxbai-embed-large` model to convert text into mathematical vectors, storing them in an in-memory `SimpleVectorStore`.
* **Retrieval Pipeline:** Performs Semantic Search (Cosine Similarity) to find the Top-K relevant context blocks and securely augments the prompt before generating a response with Llama 3.
### 🚀8. Enterprise-Grade Advanced RAG Architecture
* **Persistent Vector Storage:** Transitioned from in-memory storage to **PostgreSQL (PgVector)** for robust, scalable, and persistent vector embedding management.
* **Metadata Filtering:** Implemented precise document retrieval using contextual metadata (e.g., department, category), preventing data leakage and AI hallucinations.
* **Clean Architecture:** Structured the RAG pipeline using industry-standard design patterns, decoupling configuration, ingestion (ETL), and retrieval services.
### 9. 🤖 AI Agent & Tool Calling (Function Calling)
Transformed the LLM from a passive chatbot into an active **AI Agent** capable of interacting with backend Java services in real-time.
* **Dynamic Function Discovery:** Utilized Spring AI's `.tools()` API to register Java `Function` beans (e.g., `carStockCheckTool`) as executable tools for the AI.
* **Real-Time Data Access (Zero Hallucination):** When a user asks about car inventory or prices, the AI autonomously pauses text generation, triggers the backend Java service to query the simulated database, and uses the deterministic `StockResponse` to generate a 100% accurate, up-to-date reply.
* **Declarative Tooling:** Tools are strictly defined using Java `record` types for inputs/outputs and the `@Description` annotation, allowing the local Llama 3 model to accurately decide *when* and *how* to invoke the method without exposing sensitive backend logic.
### 10. ⛓️ Chained Workflows & Multi-Tool Autonomous Agents
Evolved the AI from executing single tasks to managing complex, multi-step operations using a Chained Workflow architecture.
* **Web Scraping Tool:** The Agent can autonomously fetch and read real-time data from external URLs to analyze current security postures or external reports.
* **Internal RAG Tool:** The Agent can cross-reference external findings with internal PostgreSQL vector data (PgVector) without human intervention.
* **File System & Diagram Tool:** The Agent automatically translates its analytical findings into visual Mermaid.js architecture diagrams and saves them directly to the local file system.
* **Security Review Workflow:** A complete end-to-end use case where the AI gathers external data, checks internal policies, identifies vulnerabilities, and outputs an executive summary alongside a generated threat diagram.
### 11. ⏸️ Human-in-the-Loop (HITL) & Checkpoint State Machine
Integrated a robust pause-and-resume architecture to prevent autonomous AI from making critical decisions without human oversight.
* **State Persistence:** Utilizes PostgreSQL (via Spring Data R2DBC) to serialize and save the AI's complex prompt context and execution plan into a database.
* **Pause & Resume Execution:** The AI automatically pauses execution upon reaching the `WAITING_HUMAN_APPROVAL` state. It completely drops from memory to save server resources.
* **Human Intervention:** Exposes secure endpoints for administrators to review the AI's generated plan and either `Approve` (resuming the workflow seamlessly) or `Reject` (aborting the operation).
* **Fault Tolerance:** Built-in retry mechanisms prevent malformed JSON plans from crashing the pipeline, ensuring a stable state machine.


---

## 🛠️ Tech Stack

| Category            | Technology                                 |
|:--------------------|:-------------------------------------------|
| **Language**        | Java 25                                    |
| **Framework**       | Spring Boot 3.x                            |
| **AI Integration**  | Spring AI (2.0.0-M5)                       |
| **LLM Engine**      | Ollama (Local)                             |
| **AI Model**        | Meta Llama 3                               |
| **Reactive Stack**  | Spring WebFlux (Project Reactor)           |
| **Architecture**    | Interceptors, Advisors, Few-Shot Prompting |
| **Vector DB / RAG** | SimpleVectorStore, mxbai-embed-large       |
| **Vector Database** | PostgreSQL, PgVector Extension             |
| **AI Framework** | Spring AI (2.0.0-M5)                       |
| **Embedding Model** | mxbai-embed-large (via Ollama)             |
| **Workflow Engine** | Spring AI Chained Workflows                |
| **Visualization**   | Mermaid.js (AI-Generated Architecture)     |

---
## 🧠 Advanced RAG Workflow (How it works)
1.  **Ingestion Pipeline (ETL):** Corporate documents are ingested, labeled with specific metadata tags (e.g., `category='HR_Policies'`), chunked optimally using `TokenTextSplitter`, and embedded into a PostgreSQL Vector Database.
2.  **Dynamic Search Request:** When a user asks a question, a dynamic `SearchRequest` is generated, applying strict metadata filters (SQL-like WHERE clauses on vectors).
3.  **Semantic Retrieval:** The system performs Cosine Similarity search on PgVector to retrieve only the top-K relevant and authorized document chunks.
4.  **Context Augmentation & Generation:** The retrieved chunks are seamlessly injected into the LLM's system prompt, forcing the model to generate accurate answers strictly based on the provided corporate context.

## ⚙️ How to Run Locally

Since the project uses a local LLM, you need to have Ollama installed and running.

1.  **Install and Start Ollama:**
    Download Ollama and pull the required models in your terminal:
    ```bash
    ollama run llama3               # Text Generation (Chat)
    ollama pull llava               # Vision (Image-to-Text)
    ollama pull mxbai-embed-large   # Embeddings (Vector Math for RAG)
    ```

2.  **Clone the repository:**
    ```bash
    git clone [https://github.com/efeerturk7/spring-openai.git](https://github.com/efeerturk7/spring-openai.git)
    cd openai
    ```

3.  **Build and Run the Application:**
    Ensure you are using Java 25 and have Maven installed.
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4.  **Test the Endpoints:**
    * Chat: `GET http://localhost:8082/api/spring-ai/secure-chat?chatId=1&message=Hello`
    * Stream Ad: `GET http://localhost:8082/api/spring-ai/generate-ad?brand=BMW&model=M3`
    * Image-to-text: `POST http://localhost:8082/api/spring-ai/analyze-image` | Uploads an image (`multipart/form-data`) and uses the LLaVA model to extract physical car details and damage reports.|
    * Ingestion pipeline: `POST http://localhost:8082/api/spring-ai/rag/ingest` | Chunks raw text, creates embeddings, and saves to Vector Store. |
    * Retrieval pipeline : `GET http://localhost:8082/api/spring-ai/rag/ask` | Answers questions strictly based on the ingested internal company data. |
    * Advanced Ingest: `POST http://localhost:8082/api/spring-ai/advanced-ingest` | Ingests raw text, adds metadata (category/dept), and saves to PgVector. |
    * Advanced Ask: `GET http://localhost:8082/api/spring-ai/advanced-ask` | Queries the AI using Advanced RAG with strict metadata filtering. |
    * Agent Tool Calling: `GET http://localhost:8082/api/spring-ai/askAgent` | Triggers the AI Agent. The AI autonomously pauses, calls the backend Java method (`carStockCheckTool`) to fetch real database data, and returns an accurate response without hallucinating.
    * Security Review Agent: `POST http://localhost:8082/api/spring-ai/agent/security-review` | Triggers a chained workflow. The Agent fetches data from the provided URL, cross-references with internal RAG, and generates a vulnerability report along with a Mermaid diagram saved to the local disk.
    * HITL - Start Agent:** `POST http://localhost:8082/api/spring-ai/hitl/start` | Initiates the security review. The AI generates a plan, saves it to PostgreSQL, and returns a Task ID (Pauses execution).
    * HITL - Approve:** `POST http://localhost:8082/api/spring-ai/hitl/{id}/approve` | Administrator approves the plan. The AI retrieves context from the DB and resumes execution using Tools (RAG/Web).
    * HITL - Reject:** `POST http://localhost:8082/api/spring-ai/hitl/{id}/reject` | Administrator aborts the AI workflow securely.


---

### 👨‍💻 Author
**Bahadır Efe ERTÜRK** - Backend Developer

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/efeerturk7/)
[![GitHub](https://img.shields.io/badge/GitHub-Follow-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/efeerturk7)