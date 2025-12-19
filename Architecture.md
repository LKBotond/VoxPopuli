## Necessary DB data

**_PostgreSQL_**

```mermaid
erDiagram
    users {
        BIGINT user_id PK
        CITEXT email
        CITEXT alias
        TEXT pass_hash
    }
     active_sessions{
        TEXT id PK
        BIGINT user_id
        TIMESTAMP issued_at
        TIMESTAMP expires_at
    }
    comments {
        BIGINT id PK
        BIGINT parent_id
        BIGINT user_id
        TEXT source_link_hash
        TEXT content
        TIMESTAMP created_at
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
Auth-->User
Auth-->Session
Gateway-->Filter
Filter-->Comment

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
    B -->|Valid| C[Add user to DB]
    C --> D[Generate Session Token]
    D --> E[Return response to user]
    B -->|Invalid| E[Return appropriate response]
```

#### Implementation:

```mermaid
sequenceDiagram
autonumber
    participant User
    participant APIGateway
    participant AuthService
    participant UserService
    participant PersistanceLayer

    User->>APIGateway: RegistrationRequestDTO
    Note over APIGateway: validate DTO integrity (check for ull fields)
    alt Missing necesary data
        APIGateway-->>User: 400 bad request
    else Valid data
        APIGateway->>AuthService: RegistrationRequestDTO
        Note over AuthService: HashPassword
        AuthService->>UserService: CreateUserDTO

            UserService->>PersistanceLayer: Check if User Already exists
        alt User already exists
            PersistanceLayer-->>UserService: Exists
            UserService-->>AuthService: 409 Conflict
            AuthService-->>APIGateway: 409 Conflict
            APIGateway-->>User: 409 Conflict
        else User does not exist
            PersistanceLayer-->>UserService: Ready to add user
            UserService->>PersistanceLayer: Add user to DB
            PersistanceLayer-->>UserService: User added successfully
            UserService-->>AuthService: User added succesfully
            Note over AuthService: Create Session Token
            AuthService-->>PersistanceLayer: Save Session token
            PersistanceLayer-->>AuthService: Token Saved
            AuthService-->>APIGateway: sessionToken
            APIGateway-->>User: 200 OK + Session Token
        end
    end
```
