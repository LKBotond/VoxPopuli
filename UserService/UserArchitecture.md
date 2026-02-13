# Overview
This service handles user specific persistence
## Chapters
1. [DB structure](#db-structure)
2. [Data Flow](#data-flow)
3. [Create User](#create-user)
4. [Retrieve User](#retrieve-user)
5. [Update password](#update-password)
## DB Structure
```mermaid
erDiagram
    users {
        UUID user_id PK
        CITEXT email "Unique, Not Null"
        CITEXT alias "Unique, Not Null"
        TEXT pass_hash "Not Null"
    }
```
## Data Flow

```mermaid
flowchart LR

    subgraph User Service
        Controller[User Controller]
        Service[User Service]
    end

    subgraph DB
        pg[(PostgreSQL)]
    end

Controller-->Service

Service-->pg
```
## Create User
```mermaid
sequenceDiagram
    autonumber
    actor Client as Auth Service
    participant Ctrl as User Controller
    participant Svc as User Service
    participant Repo as User Repository

    Note over Client, Ctrl: 1. Registration Request
    Client->>Ctrl: POST /users
    activate Ctrl

    Note over Ctrl, Svc: 2. Service Logic
    Ctrl->>Svc: createUser(HashedRequest)
    activate Svc

    Note over Svc, Repo: 3. Uniqueness Validation
    Svc->>Repo: findByAlias(alias)
    activate Repo
    Repo-->>Svc: Empty Optional
    deactivate Repo

    Svc->>Repo: findByEmail(email)
    activate Repo
    Repo-->>Svc: Empty Optional
    deactivate Repo

    Note over Svc, Repo: 4. Persistence
    Svc->>Svc: Map Request to Entity
    Svc->>Repo: save(User)
    activate Repo
    Repo-->>Svc: Return Saved User
    deactivate Repo

    Svc-->>Ctrl: Return User Entity
    deactivate Svc

    Note over Ctrl: 5. Mapping
    Ctrl->>Ctrl: userMapper.toUserData(user)
    
    Ctrl-->>Client: 200 OK (UserData)
    deactivate Ctrl
```
## Retrieve User
```mermaid
sequenceDiagram
    autonumber
    actor Client as Auth Service
    participant Ctrl as User Controller
    participant Svc as User Service
    participant Repo as User Repository

    Note over Client, Ctrl: 1. Retrieval Request
    Client->>Ctrl: GET /users/{email}
    activate Ctrl

    Note over Ctrl, Svc: 2. Lookup Logic
    Ctrl->>Svc: loginByEmail(email)
    activate Svc

    Note over Svc, Repo: 3. Database Query
    Svc->>Repo: findByEmail(email)
    activate Repo
    
    alt User Found
        Repo-->>Svc: Return User Entity
    else User Not Found
        Repo-->>Svc: Empty
        Svc-->>Client: Throw UserNotFoundException
        deactivate Repo
    end

    Svc-->>Ctrl: Return User
    deactivate Svc

    Ctrl-->>Client: 200 OK (UserData)
    deactivate Ctrl
```
## Update password 
```mermaid
sequenceDiagram
    autonumber
    actor Client as Auth Service
    participant Ctrl as User Controller
    participant Svc as User Service
    participant Repo as User Repository

    Note over Client, Ctrl: 1. Update Request
    Client->>Ctrl: PUT /users/{id}/password
    activate Ctrl

    Note over Ctrl, Svc: 2. Service Invocation
    Ctrl->>Svc: changePass(userId, newHash)
    activate Svc

    Note over Svc, Repo: 3. Persistence
    Svc->>Repo: updatePassByUserId(id, hash)
    activate Repo
    Repo-->>Svc: Void
    deactivate Repo

    Svc-->>Ctrl: Void
    deactivate Svc

    Ctrl-->>Client: 204 No Content
    deactivate Ctrl
```
