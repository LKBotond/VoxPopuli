# VoxPopuli
VoxPopuli is a chrome extension integrated with a Spring boot backend system designed to provide a comment section to any website. It leverages a microservice architecture for scalability, maintainability and clear separation of concerns.

## Overview
I'm building this as a learning project to explore microservice architectures, backend authentication with Spring security, opaque tokens, and frontend Shadow DOM manipulation. The backend is built with Spring Boot, for long term persistence i've used PostgreSQL, for the opaque session tokens i've chosen Redis. I've opted for granual services since the aim was to simulate mass service to service communication, thus users, comments, authentication, filtering, and sessions are all independent services orchestrated via client calls from the gateway. 

## Stack used
**Backend:** Spring boot, Java, JPA, FeignClients
**Security:** Spring Security, Argon2, Opaque Tokens
**Database:** PostgreSQL, Redis

## Architecture
Service level flow charts and sequence diagrams can be found in the [Architecture.md](Architecture.md) file. Implementation level diagrams are found in the Architecture.md files within each service.

## Roadmap:
**Backend:**
- [x] User Service.
- [x] Comment Service.
- [x] Authentication Service.
- [x] Filter Service.
- [ ] Gateway Service.

**Frontend:**

- [ ] Popup UI.
- [ ] BackgroundScript.
- [ ] Authentication logic.
- [ ] Messaging logic (extension ↔ backend).
- [ ] Shadow DOM logic.

**Integration:**

- [ ] Deploy Microservices to a Host
- [ ] Publish Extension.
