
# 🎓 Smart College Election System

A full-stack web application for conducting real-time college class representative elections. This project is built with Spring Boot, MongoDB, and live WebSockets.

This application provides a secure admin dashboard to manage the election and a clean, responsive UI for students to cast their votes. The results page updates in real-time without needing a refresh.

-----

## ✨ Features

  * **Admin Dashboard:** Secure login for administrators to manage the election.
  * **Add Voters & Candidates:** Admins can easily add eligible voters and candidates to the database.
  * **Voter Verification:** Students are verified by their **PRN**, **Class**, and **Division** before voting.
  * **Secure Voting:** Backend logic prevents double-voting.
  * **NOTA Option:** Includes a "None of the Above" (NOTA) option.
  * **Live Results Dashboard:**
      * Results update automatically in real-time using **WebSockets**.
      * Live **Voter Turnout** percentage and count.
      * **Division Leader Highlighting** to easily see who is winning.
  * **Export Results:** Admins can export the final election results to an XML file.

-----

## 🛠️ Tech Stack

### Backend

  * **Java 17**
  * **Spring Boot:** For the web server and REST APIs.
  * **Spring Data MongoDB:** As the database driver.
  * **Spring WebSocket:** For STOMP over SockJS to broadcast live results.
  * **Maven:** For project dependencies.

### Database

  * **MongoDB:** A NoSQL database to store voter and candidate collections.

### Frontend

  * **HTML5**
  * **CSS3** & **Bootstrap 5:** For responsive design and a modern UI.
  * **JavaScript (ES6+):** For `fetch` API calls and WebSocket client logic (SockJS & StompJS).

-----

## 🚀 How to Run Locally

### Prerequisites

  * **Java 17 (or newer)**
  * **Apache Maven**
  * **MongoDB:** Must be installed and running on its default port (`mongodb://localhost:27017`).

### 1\. Clone the Repository

```bash
git clone https://github.com/your-username/SmartCollegeElection.git
cd SmartCollegeElection
```

### 2\. Run the Backend

The project is configured to connect to a local MongoDB database named `electiondb`.

```bash
# This will start the Spring Boot application on port 8080
./mvnw spring-boot:run
```

(If you don't have the Maven wrapper, use `mvn spring-boot:run`)

### 3\. Access the Application

Your application is now running\!

  * **Homepage:** `http://localhost:8080`
  * **Vote Page:** `http://localhost:8080/vote.html`
  * **Results Page:** `http://localhost:8080/results.html`
  * **Admin Login:** `http://localhost:8080/admin-login.html`
      * (Default credentials: `admin` / `1234`)

-----

## 📁 Project Structure

```
ElectionCommisionSystem/
│
├── src/main/java/com/aarya/election/
│   ├── config/         # WebSocketConfig.java
│   ├── controller/     # API endpoints (AdminController, VoterController, etc.)
│   ├── model/          # Data models (Candidate.java, Voter.java, ElectionStats.java)
│   ├── repository/     # MongoDB repositories (CandidateRepository, VoterRepository)
│   ├── service/        # Business logic (ElectionService.java)
│   └── websocket/      # (This was removed in favor of config)
│
├── src/main/resources/
│   ├── static/
│   │   ├── images/     # mitwpu_logo.png
│   │   ├── admin.html
│   │   ├── admin-login.html
│   │   ├── index.html
│   │   ├── results.html
│   │   └── vote.html
│   │
│   └── application.properties  # Database connection string
│
└── pom.xml             # Maven dependencies
```

-----

## 💻 API Endpoints

### Admin (`/api/admin`)

  * `POST /addVoter`: Adds a new voter.
  * `POST /addCandidate`: Adds a new candidate.
  * `GET /export`: Exports results to `results.xml`.

### Vote (`/api/vote` & `/api/candidates`)

  * `GET /api/candidates/{cls}/{div}`: Gets candidates for a specific class/division.
  * `POST /api/vote/{voterPrn}/{candidatePrn}`: Casts a vote.

### Results (`/api/results`)

  * `GET /results`: Gets all candidate results (for initial page load).
  * `GET /results/stats`: Gets the live dashboard stats (turnout, etc.).

### WebSocket

  * `/liveResults`: WebSocket connection endpoint.
  * `/topic/results`: Topic to subscribe to for live result updates.

-----

## 👤 Author

  * **Aarya Shinde**
