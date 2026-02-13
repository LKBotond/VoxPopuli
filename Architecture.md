# Architecture
System level overview of the most important logic chains
## Chapters:
1. [Necesary Db Data](#necessary-db-data)
2. [Authentication Flow](#authentication-flow)
3. [Comment Flow](#comment-flow)
## Necessary DB data

### **_PostgreSQL_**

```mermaid
erDiagram
    users {
        UUID user_id PK
        CITEXT email
        CITEXT alias
        TEXT pass_hash
    }
    comments {
        UUID comment_id PK
        UUID parent_id
        UUID user_id
        TEXT source_link_hash
        TEXT content
        TIMESTAMP last_updated
    }

```

### **_Redis_**

```mermaid
erDiagram
     sessionToken{
        TEXT sessionId PK
        TEXT userId
        TEXT alias
        LONG expiryInSeconds
    }
```

## Authentication flow:


```mermaid
sequenceDiagram
autonumber
    Actor User as User 
    participant GW as Gateway 
    participant Auth as Auth Service
    participant UserSvc as User Service
    participant Session as Session Service

    Note over User, GW: 1. Initial Request
    User->>GW: Send Request (Register/Login)
    activate GW
    
    Note over GW: 2. Edge Validation
    GW->>GW: Validate Origin & Clean Headers
    
    GW->>Auth: Forward/Proxy Request
    activate Auth
    
    Note over Auth: 3. Logic & Hashing
    Auth->>Auth: Hash Password (Argon2)
    
    Note over Auth, UserSvc: 4. Persistence
    Auth->>UserSvc: Request to Save User
    activate UserSvc
    UserSvc-->>Auth: Return Saved User Data
    deactivate UserSvc
    
    Note over Auth, Session: 5. Session Management
    Auth->>Session: Generate & Save Opaque Token
    activate Session
    Session-->>Auth: Return Token
    deactivate Session
    
    Note over Auth, User: 6. Response
    Auth-->>GW: Return Token
    deactivate Auth
    
    GW-->>User: Response (Token)
    deactivate GW
```

## Comment flow:

```mermaid
sequenceDiagram
    autonumber
    actor Client as User 
    participant GW as Gateway
    participant Session as Session Service
    participant Comment as Comment Service
    participant Filter as Filter Service
    participant DB as Comment DB

    Note over Client, GW: 1. Inbound Request
    Client->>GW: POST /comment
    activate GW

    Note over GW, Session: 2. Authentication & Enrichment
    GW->>Session: Validate Token
    activate Session
    Session-->>GW: Return Internal User Data (User ID, Alias)
    deactivate Session

    GW->>GW: Inject User ID/Role into Request Headers
    
    Note over GW, Comment: 3. Forwarding
    GW->>Comment: Forward Request (Headers: X-User-ID, etc.)
    activate Comment

    Note over Comment, Filter: 4. Moderation
    Comment->>Filter: Request Content Validation
    activate Filter
    Filter-->>Comment: Return Flag (e.g., IsSafe: True)
    deactivate Filter

    Note over Comment, DB: 5. Persistence

    Comment->>DB: Save Comment Entity
    activate DB
    DB-->>Comment: Confirm Save
    deactivate DB
        
    Comment-->>GW: Return Saved Comment Object
    deactivate Comment

    GW-->>Client: Final Response
    deactivate GW

```
