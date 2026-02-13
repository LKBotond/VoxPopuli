# Overview

This service Normalizes and filters input text.

## Chapters

1. [Data Flow](#dataflow)
2. [Dict Update Chain](#dict-update-chain)
3. [Filter Request Chain](#filter-request-chain)
4. [In Built Normalization chain](#in-built-normalization-chain)

## DataFlow

```mermaid
flowchart LR

    subgraph Filter Service
        Controller[Filter Controller]
        Censor[Censor Service]
        Normalizer[Text Normalizer]
        Handler[Profanity Handler]
    end

    subgraph Infrastructure
        RAM[(2d ConcurrentHashMap)]

    end

Controller<-->Censor
Controller-->Handler
Censor<-->Normalizer
Censor<-->Handler

Handler<-->RAM

```

## Dict Update Chain

This service supports hot Loading new Data sets into the concurentHashMap, thus avoiding downtime.

```mermaid
sequenceDiagram
    autonumber
    participant App as Application Boot
    participant Ctrl as Filter Controller
    participant Handler as Profanity Handler
    participant File as FileReader
    actor Admin as Admin/System

    Note over App, Handler: 1. Service Startup
    App->>Handler: Constructor()
    activate Handler
    Handler->>Handler: loadBasedict()

    Note over Handler, File: 2. File I/O
    Handler->>File: loadJsonSourceIntoSet("DefaultEnProfanities.json")
    activate File
    File-->>Handler: Return Set<String>
    deactivate File

    Handler->>Handler: map.put("eng", set)
    deactivate Handler

    Note over Admin, Ctrl: 3. Manual Update
    Admin->>Ctrl: POST /internal/censor/add
    activate Ctrl

    Note over Ctrl, Handler: 4. Memory Update
    Ctrl->>Handler: loadDictionary(lang, words)
    activate Handler
    Handler->>Handler: collection.put(lang, words)
    Handler-->>Ctrl: Void Return
    deactivate Handler

    Ctrl-->>Admin: 204 No Content
    deactivate Ctrl

```

## Filter Request Chain

**Logic chain for censorship requests**

```mermaid
sequenceDiagram
    autonumber
    actor Client as Internal Service
    participant Ctrl as Filter Controller
    participant Censor as Censor Service
    participant Norm as Text Normalizer
    participant Handler as Profanity Handler

    Note over Client, Ctrl: 1. Check Request
    Client->>Ctrl: POST /internal/censor/check
    activate Ctrl

    Note over Ctrl, Censor: 2. Service Invocation
    Ctrl->>Censor: censorInput(CensorRequest)
    activate Censor

    Note over Censor, Norm: 3. Normalization
    Censor->>Norm: normalizeInputIntoList(text)
    activate Norm
    Norm-->>Censor: Return List<String>
    deactivate Norm

    Note over Censor, Handler: 4. Memory Lookup
    loop For every word
        Censor->>Handler: foundWord(word)
        activate Handler
        Handler->>Handler: Iterate Map Values (RAM)
        Handler-->>Censor: Boolean Found
        deactivate Handler
    end

    Note over Censor, Ctrl: 5. Response Construction
    Censor->>Censor: buildResponse(flag, caughtWords)
    Censor-->>Ctrl: Return CensorResponse
    deactivate Censor

    Ctrl-->>Client: 200 OK (JSON)
    deactivate Ctrl
```

## In Built Normalization chain

**Logic chain for normalizing text inputs** Feel free to tinker with it.

### Abstract:

```mermaid
flowchart TD
    Start([Raw User Input]) --> Case[Case Normalization]

    subgraph Transformations
        Case -- "Lower & Unaccent" --> Leet[Cleverity Filter]
        Leet -- "Decode '1337'" --> Punct[Punctuation Norm]
        Punct -- "Remove Symbols" --> Sep[Separation Filter]
        Sep -- "Collapse 'w o r d'" --> Rep[Repetition Filter]
    end

    Rep -- "Limit 'aaaargh'" --> Token[Listify Text]
    Token --> End([List&lt;String&gt; Output])

```

### Detailed:

```mermaid
sequenceDiagram
    autonumber
    participant Client as Calling Service
    participant Norm as Text Normalizer

    Note over Client, Norm: 1. Pipeline Entry
    Client->>Norm: normalizeInputIntoList(rawString)
    activate Norm

    Note over Norm: 2. Unicode & Case
    Norm->>Norm: caseNormalization(input)
    Note right of Norm: NFKD Normalization<br/>Strip Accents (\p{M})<br/>Lowercase

    Note over Norm: 3. Leet-Speak Decoding
    Norm->>Norm: cleverityFilter(filtered)
    Note right of Norm: Map lookup<br/>(e.g., '@'→'a', '3'→'e', '$'→'s')

    Note over Norm: 4. Symbol Cleanup
    Norm->>Norm: punctuationNormalization(filtered)
    Note right of Norm: Replaces non-alphanumeric<br/>with single whitespace

    Note over Norm: 5. Anti-Evasion
    Norm->>Norm: separationFilter(filtered)
    Note right of Norm: Collapses artificially spaced chars<br/>(e.g., "w o r d" → "word")

    Note over Norm: 6. Spam Reduction
    Norm->>Norm: repetitionFilter(filtered)
    Note right of Norm: Limits repeating chars to max 2<br/>(e.g., "noooo" → "noo")

    Note over Norm: 7. Tokenization
    Norm->>Norm: listifyText(filtered)
    Note right of Norm: Extracts discrete words<br/>using Regex \p{L}+

    Norm-->>Client: Return List<String>
    deactivate Norm
```
