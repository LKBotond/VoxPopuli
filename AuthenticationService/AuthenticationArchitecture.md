# Overview

This Service Orchestrates **UserService** for user specific persistence, and **SessionService** for session generation and persistence.

## Chapters

1. [Data FLow](#request-flow)
2. [Login](#login)
3. [Registration](#registration)
4. [Rollback](#registration)

## Data Flow

```mermaid
flowchart LR

    subgraph Auth Service
        Controller[Auth Controller]
        Service[Auth Service]
        Hash[Argon2 Service]
        Saga[Saga Helper]
    end

    subgraph Clients
        U_Client[User Feign Client]
        S_Client[Session Feign Client]
    end

    subgraph External Services
        UserSvc[User Service]
        SessionSvc[Session Service]
    end

Controller-->Service
Service-->Hash
Service-->Saga
Service-->U_Client
Service-->S_Client

U_Client-.->|HTTP|UserSvc
S_Client-.->|HTTP|SessionSvc
```

## Login:

```mermaid

sequenceDiagram
    autonumber
    actor Client as User
    participant Ctrl as Auth Controller
    participant Svc as Auth Service
    participant Hash as Argon2 Service
    participant UserClient as User Client (Feign)
    participant SessClient as Session Client (Feign)

    Note over Client, Ctrl: 1. Inbound Request
    Client->>Ctrl: POST /auth/login
    activate Ctrl

    Note over Ctrl, Svc: 2. Logic Delegation
    Ctrl->>Svc: loginUser(request)
    activate Svc

    Note over Svc, UserClient: 3. User Retrieval
    Svc->>UserClient: getUserByEmail(email)
    activate UserClient
    UserClient-->>Svc: Return UserData (includes Hash)
    deactivate UserClient

    Note over Svc, Hash: 4. Secure Verification
    Svc->>Hash: verifyPass(rawPass, storedHash)
    activate Hash
    Hash->>Hash: Wrap CharBuffer & Wipe Memory
    Hash-->>Svc: Returns True
    deactivate Hash

    Note over Svc, SessClient: 5. Session Creation
    Svc->>SessClient: createSession(InternalUserData)
    activate SessClient
    SessClient-->>Svc: Return SessionToken
    deactivate SessClient

    Svc-->>Ctrl: Return Token
    deactivate Svc

    Ctrl-->>Client: 200 OK (Token)
    deactivate Ctrl

```

## Registration

```mermaid
sequenceDiagram
    autonumber
    actor Client as User
    participant Ctrl as Auth Controller
    participant Svc as Auth Service
    participant Hash as Argon2 Service
    participant UserClient as User Client
    participant SessClient as Session Client

    Note over Client, Ctrl: 1. Registration Request
    Client->>Ctrl: POST /auth/register
    activate Ctrl
    Ctrl->>Svc: registerUser(request)
    activate Svc

    Note over Svc, Hash: 2. Security
    Svc->>Hash: hashWithArgon2(rawPass)
    activate Hash
    Hash-->>Svc: Return Hash
    deactivate Hash

    Note over Svc, UserClient: 3. Step 1: Create User
    Svc->>UserClient: register(HashedRequest)
    activate UserClient
    UserClient-->>Svc: Return UserData
    deactivate UserClient

    Svc->>Svc: Add Rollback Step (Delete User)

    Note over Svc, SessClient: 4. Step 2: Create Session
    Svc->>SessClient: createSession(UserData)
    activate SessClient
    SessClient-->>Svc: Return SessionToken
    deactivate SessClient

    Svc->>Svc: Add Rollback Step (End Session)

    Note over Svc, Client: 5. Success
    Svc-->>Ctrl: Return Token
    deactivate Svc
    Ctrl-->>Client: 200 OK
    deactivate Ctrl
```

## Rollback

```mermaid
sequenceDiagram
    autonumber
    participant Svc as Auth Service
    participant UserClient as User Client
    participant SessClient as Session Client

    Note over Svc, UserClient: 1. Step 1 Succeeds
    Svc->>UserClient: register(...)
    UserClient-->>Svc: Success
    Svc->>Svc: Register Compensation: deleteUser(id)

    Note over Svc, SessClient: 2. Step 2 Fails
    Svc->>SessClient: createSession(...)
    activate SessClient
    SessClient-->>Svc: Throw Exception / Timeout
    deactivate SessClient

    Note over Svc: 3. Saga Compensation (Rollback)
    Svc->>Svc: Catch Exception

    loop Reverse Order
        Svc->>Svc: Trigger SagaStep.rollback()
        Svc->>UserClient: deleteUser(id)
        UserClient-->>Svc: Void
    end

    Svc-->>Svc: Throw Original Exception
```
