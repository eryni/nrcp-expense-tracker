## README: Expense Tracker Application

This repository contains the full-stack Expense Tracker application, developed with a Spring Boot backend and a modern Vue 3 frontend. The application allows users to log and review daily, weekly, and total expenses, meeting the core objectives of the project brief.

### Stack and Technologies

The application is built using a modern stack designed for rapid development and maintainability.

| Category | Technology | Version | Purpose |
| :--- | :--- | :--- | :--- |
| **Backend Framework** | Spring Boot | 4.0.0 | Enterprise-grade backend, handling business logic and API serving. |
| **Database** | MySQL | 8.0.44 | Relational database for persistent storage of expense records. |
| **Persistence** | Java | 17 LTS | Core language for the backend, running on Long-Term Support version. |
| **Build Tool** | Apache Maven | 3.8.2 | Dependency management and build automation for the Java backend. |
| **Frontend Framework** | Vue.js | (Latest) | Component-based library used for the Single Page Application (SPA). |
| **Bundler/Dev Server**| Vite | 7.2.7 | Fast build tool and development server for the Vue frontend. |
| **Runtime** | Node.js | v20.19.5 | JavaScript runtime environment. |
| **Package Manager** | npm | 10.8.2 | Package manager for frontend dependencies. |
| **Version Control** | Git | 2.52.0 | Version Control System used for atomic commits. |

### 💡 Project Context & Development Notes

This project was initiated using the **Spring Initializer** for the backend foundation. The initial plan involved using Riot.js for the frontend via Vite, but due to technical difficulties with the custom component resolution pipeline in the Vite/Riot setup, the decision was made to switch to **Vue.js** (Vue 3 with `<script setup>` style) to stabilize the frontend environment quickly.

### Local Development Setup

Follow these steps to clone the repository, set up the database, and run both the frontend and backend simultaneously.

#### I. Database Setup (MySQL)

You must ensure your local MySQL server is running on the default port (`3306`) before starting the backend.

1.  **Initialize Database:** Execute the provided setup script to create the `expense_tracker` database and the `expense_tracker` user/password (`nrcp`).

    ```bash
    # Navigate to the directory containing setup.sql
    mysql -u root -p < setup.sql
    ```

2.  **Verify Configuration:** Edit the backend's configuration file:

      * Navigate to `backend/src/main/resources/application.properties`.
      * **Uncomment the section** for `LOCAL DEVELOPMENT ENVIRONMENT` (containing `localhost:3306`, `username=expense_tracker`, `password=nrcp`).

#### II. Backend Setup (Spring Boot)

1.  **Build the Backend:** Navigate to the backend directory and compile the Java code.

    ```bash
    cd backend
    mvn clean install
    ```

2.  **Run the Backend API:** Start the Spring Boot application, which runs on port `8080`.

    ```bash
    mvn spring-boot:run
    ```

#### III. Frontend Setup (Vue 3 + Vite)

1.  **Install Dependencies:** Open a **new terminal window**, navigate to the Vue frontend directory, and install packages.

    ```bash
    cd frontend-vue
    npm install
    ```

2.  **Run the Frontend:** Start the Vite development server. It will automatically proxy API requests (`/api/*`) to the backend running on `localhost:8080`.

    ```bash
    npm run dev
    ```

The application will be accessible at `http://localhost:5173/`.