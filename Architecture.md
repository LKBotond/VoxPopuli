## Necessary DB data

**_PostgreSQL_**

```mermaid
erDiagram
    users {
        UUID user_id PK
        CITEXT email
        CITEXT alias
        TEXT pass_hash
    }
    comments {
        UUID id PK
        UUID parent_id
        UUID user_id
        TEXT source_link_hash
        TEXT content
        TIMESTAMP created_at
    }

```

**_Redis_**

```mermaid
erDiagram
     active_sessions{
        TEXT sessionId PK
        TEXT userId
        LONG expiryInSeconds
    }
```

## All the backend services in Coordinator->subordinate roles

```mermaid
flowchart LR

    subgraph Backend Services
        Gateway[API Gateway]
        Auth[Authentication Service]
        User[User Service]
        Session[Session Service]
        Comment[Comment Service]
        Filter[Filtering Service]
    end

    subgraph DBs
        userDB[(users DB)]
        session[(Active Sessions)]
        comment[(comments)]
    end

Gateway-->Auth
Gateway-->User
Gateway-->Session
Gateway-->Filter
Gateway-->Comment

User-->userDB
Session-->session
Comment-->comment
```

## Sequence Diagrams for implementation

### Registration:

#### Abstract:

```mermaid
flowchart TD
    A[User sends necessary data ] --> B[Check for integrity]
    B-->|Valid| C[Hash password]
    C --> D[Add user to DB]
    D --> E[Generate Session Token]
    E --> F[Return appropriate response]
    B -->|Invalid| F[Return appropriate response]
```

#### Implementation:

```mermaid
sequenceDiagram
autonumber
    participant User
    participant APIGateway
    participant AuthService
    participant SessionService
    participant UserService
    participant PersistanceLayer

    User->>APIGateway: RegistrationRequestDTO
    Note over APIGateway: validate DTO integrity (check for null fields)
    alt Missing necesary data
        APIGateway-->>User: 400 bad request
    else Valid data
        APIGateway->>AuthService: RegistrationRequestDTO
        Note over AuthService: HashPassword
        AuthService->>APIGateway: HashedRegistrationRequestDTO
        APIGateway->>UserService: HashedRegistrationRequestDTO

            UserService->>PersistanceLayer: Check if User Already exists
        alt User already exists
            PersistanceLayer-->>UserService: Exists
            UserService-->>APIGateway: 409 Conflict
            APIGateway-->>User: 409 Conflict
        else User does not exist
            PersistanceLayer-->>UserService: Ready to add user
            UserService->>PersistanceLayer: Add user to DB
            PersistanceLayer-->>UserService: User added successfully
            UserService->>APIGateway: UserDataDto
            APIGateway->>SessionService: UserDataDto
            Note over SessionService: create sessionToken
            SessionService->>PersistanceLayer: Save Session token
            PersistanceLayer-->>SessionService: Token Saved
            SessionService->>APIGateway: sessionToken
            APIGateway-->>User: 200 OK + Session Token
        end
    end
```
