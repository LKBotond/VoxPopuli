# Overview

This service handles comment persistence and orchestrates profanity filtration vie FilterService

## Chapters
1. [Necessary DB data](#necessary-db-data)
2. [Abstract data flow](#abstract-data-flow)
3. [Post Comment Logic Flow](#post-comment-logic-flow)
4. [Edit Comment FLow](#edit-comment-flow)
5. [Delete Comment Flow](#delete-comment-flow)
## Necessary DB data
```mermaid

erDiagram
 **_PostgreSQL_**-comments{
        UUID comment_id PK
        UUID parent_id
        UUID user_id
        TEXT source_link_hash
        TEXT content
        TIMESTAMP last_updated
    }
```

## Abstract data flow:

```mermaid
flowchart LR

    subgraph Comment Service
        Controller[Comment Controller]
        Service[Comment Service]
        Client[Filter Feign Client]
    end

    subgraph External Services
        FilterSvc[Filter Service API]
    end

    subgraph DBs
        pg[(PostgreSQL)]
    end

Controller-->Service
Service-->Client
Service-->pg
Client-.->|HTTP/REST|FilterSvc
```

## Post Comment Logic Flow

```mermaid
sequenceDiagram
    autonumber
    actor Client as User/Gateway
    participant Ctrl as Comment Controller
    participant Svc as Comment Service
    participant Filter as Filter Client (Feign)
    participant Repo as Comment Repository

    Note over Client, Ctrl: 1. Inbound Request
    Client->>Ctrl: POST /comments (Header: User-ID)
    activate Ctrl

    Note over Ctrl, Svc: 2. Service Logic
    Ctrl->>Svc: registerComment(Request, userId)
    activate Svc

    Note over Svc, Filter: 3. Moderation Check
    Svc->>Filter: checkRequest(content)
    activate Filter
    Filter-->>Svc: CensorResponse (Flagged: False)
    deactivate Filter

    Note over Svc: If Flagged -> Throw VandalismException

    Note over Svc, Repo: 4. Persistence
    Svc->>Svc: Map to Entity
    Svc->>Repo: save(Comment)
    activate Repo
    Repo-->>Svc: Returned Saved Entity
    deactivate Repo

    Svc-->>Ctrl: Return CommentResponse
    deactivate Svc

    Ctrl-->>Client: 200 OK
    deactivate Ctrl
```

## Edit Comment FLow

```mermaid
sequenceDiagram
    autonumber
    actor Client as User/Gateway
    participant Ctrl as Comment Controller
    participant Svc as Comment Service
    participant Filter as Filter Client (Feign)
    participant Repo as Comment Repository

    Note over Client, Ctrl: 1. Edit Request
    Client->>Ctrl: PUT /comments (Header: User-ID)
    activate Ctrl

    Note over Ctrl, Svc: 2. Service Logic
    Ctrl->>Svc: registerCommentEdit(Request, userId)
    activate Svc

    Note over Svc, Filter: 3. Moderation Check
    Svc->>Filter: checkRequest(newContent)
    activate Filter
    Filter-->>Svc: CensorResponse (Flagged: False)
    deactivate Filter

    Note over Svc, Repo: 4. Data Retrieval
    Svc->>Repo: findByCommentId(id)
    activate Repo
    Repo-->>Svc: Return Existing Comment
    deactivate Repo
    
    Note over Svc: 5. Ownership Verification
    Svc->>Svc: checkOwner(commentUser, requestUser)
    
    alt IDs do not match
        Svc-->>Client: Throw VandalismException
    end

    Note over Svc, Repo: 6. Update & Save
    Svc->>Svc: old.setContent(newContent)
    Svc->>Repo: save(UpdatedComment)
    activate Repo
    Repo-->>Svc: Confirm Save
    deactivate Repo

    Svc-->>Ctrl: Return CommentResponse
    deactivate Svc

    Ctrl-->>Client: 200 OK
    deactivate Ctrl
```

## Delete Comment Flow

```mermaid
sequenceDiagram
    autonumber
    actor Client as User/Gateway
    participant Ctrl as Comment Controller
    participant Svc as Comment Service
    participant Repo as Comment Repository

    Note over Client, Ctrl: 1. Delete Request
    Client->>Ctrl: DELETE /comments/{id}
    activate Ctrl

    Note over Ctrl, Svc: 2. Processing
    Ctrl->>Svc: registerCommentDeletion(id, userId)
    activate Svc

    Note over Svc, Repo: 3. Retrieval
    Svc->>Repo: findByCommentId(id)
    activate Repo
    Repo-->>Svc: Return Entity
    deactivate Repo

    Note over Svc: 4. Logic & Redaction
    Svc->>Svc: checkOwner(commentUser, requestUser)
    Svc->>Svc: deleteUserSpecificData()
    Note right of Svc: Sets UserId=null<br/>Content=null

    Note over Svc, Repo: 5. Soft Delete Persistence
    Svc->>Repo: save(RedactedEntity)
    activate Repo
    Repo-->>Svc: Confirm
    deactivate Repo

    Svc-->>Ctrl: Return CommentResponse
    deactivate Svc

    Ctrl-->>Client: 200 OK
    deactivate Ctrl
```

```mermaid

```

```mermaid

```
