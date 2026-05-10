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

### 🔄 Request Lifecycle (AI Processing Flow)
How a prompt travels securely from the user to the LLM and back:

> **🌍 Client Request** 👉  **🛡️ SafeGuard Advisor (Security Check)** 👉  **🧠 System Prompt & Context Injection** 👉  **🤖 Ollama (Llama 3 Execution)** 👉  **⚡ Reactive Stream / JSON Output**

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
### 🚀 Enterprise-Grade Advanced RAG Architecture
* **Persistent Vector Storage:** Transitioned from in-memory storage to **PostgreSQL (PgVector)** for robust, scalable, and persistent vector embedding management.
* **Metadata Filtering:** Implemented precise document retrieval using contextual metadata (e.g., department, category), preventing data leakage and AI hallucinations.
* **Clean Architecture:** Structured the RAG pipeline using industry-standard design patterns, decoupling configuration, ingestion (ETL), and retrieval services.

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
    * Advanced Ingest: `POST http://localhost:8082/api/spring-ai/rag/advanced-ingest` | Ingests raw text, adds metadata (category/dept), and saves to PgVector. |
    * Advanced Ask: `GET http://localhost:8082/api/spring-ai/rag/advanced-ask` | Queries the AI using Advanced RAG with strict metadata filtering. |

---

### 👨‍💻 Author
**Bahadır Efe ERTÜRK** - Backend Developer

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/efeerturk7/)
[![GitHub](https://img.shields.io/badge/GitHub-Follow-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/efeerturk7)