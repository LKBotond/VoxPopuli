# Overview

This service handles session generation persistrence and validation.

## Chapters:

1. [Redis](#redis)
2. [Data Flow](#data-flow)
3. [Create Session](#create-session)
4. [Validate Session](#validate-session)
5. [End Session](#end-session)

## Redis

```mermaid
erDiagram
     sessionToken{
        TEXT sessionId PK
        TEXT userId
        TEXT alias
        LONG expiryInSeconds
    }
```
## Data Flow
```mermaid
flowchart LR

    subgraph Backend Services
        Controller[Session Controller]
        Service[Session Service]
    end

    subgraph Infrastructure
        Redis[(Redis Store)]
    end

Controller-->Service
Service-->Redis
```
## Create Session:
```mermaid
sequenceDiagram
    autonumber
    actor Client as Auth Service
    participant Ctrl as Session Controller
    participant Svc as Session Service
    participant Redis as Redis Repository

    Note over Client, Ctrl: 1. Creation Request
    Client->>Ctrl: POST /sessions
    activate Ctrl

    Note over Ctrl, Svc: 2. Token Generation
    Ctrl->>Svc: createSession(InternalUserData)
    activate Svc
    Svc->>Svc: UUID.randomUUID()
    Svc->>Svc: buildToken()

    Note over Svc, Redis: 3. Persistence
    Svc->>Redis: save(Token)
    activate Redis
    Redis-->>Svc: Confirm
    deactivate Redis

    Note over Svc, Redis: 4. Verification Load
    Svc->>Redis: findById(sessionId)
    activate Redis
    Redis-->>Svc: Return SessionDomain
    deactivate Redis

    Svc-->>Ctrl: Return SessionToken (ID, Alias)
    deactivate Svc

    Ctrl-->>Client: 200 OK
    deactivate Ctrl
```
## Validate Session:
```mermaid
sequenceDiagram
    autonumber
    actor Client as Gateway
    participant Ctrl as Session Controller
    participant Svc as Session Service
    participant Redis as Redis Repository

    Note over Client, Ctrl: 1. Validation Request
    Client->>Ctrl: GET /sessions (Header: Session-ID)
    activate Ctrl

    Note over Ctrl, Svc: 2. Lookup Logic
    Ctrl->>Svc: validateSession(tokenString)
    activate Svc

    Note over Svc, Redis: 3. Authentication
    Svc->>Redis: findById(tokenString)
    activate Redis
    
    alt Token Not Found
        Redis-->>Svc: Empty/Null
        Svc-->>Client: Throw InvalidSessionException
    else Token Found
        Redis-->>Svc: Return SessionDomain
        deactivate Redis
    end

    Note over Svc, Redis: 4. Refresh TTL (Sliding Window)
    Svc->>Svc: setExpiryInSeconds(600)
    Svc->>Redis: save(UpdatedToken)
    activate Redis
    Redis-->>Svc: Confirm
    deactivate Redis

    Svc-->>Ctrl: Return InternalUserData
    deactivate Svc

    Ctrl-->>Client: 200 OK
    deactivate Ctrl
```
## End Session:
```mermaid
sequenceDiagram
    autonumber
    actor Client as Auth Service
    participant Ctrl as Session Controller
    participant Svc as Session Service
    participant Redis as Redis Repository

    Note over Client, Ctrl: 1. Termination Request
    Client->>Ctrl: DELETE /sessions/{id}
    activate Ctrl

    Note over Ctrl, Svc: 2. Logic
    Ctrl->>Svc: endSession(sessionId)
    activate Svc

    Note over Svc, Redis: 3. Check Existence
    Svc->>Redis: findById(sessionId)
    activate Redis
    Redis-->>Svc: Return SessionDomain
    deactivate Redis

    Note over Svc, Redis: 4. Delete
    Svc->>Redis: delete(SessionDomain)
    activate Redis
    Redis-->>Svc: Void
    deactivate Redis

    Svc-->>Ctrl: Return Void
    deactivate Svc

    Ctrl-->>Client: 204 No Content
    deactivate Ctrl
```
