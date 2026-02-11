# Journal
This is a slightly less formal place where I type down the hurdles, and lessons that I’ve encountered in this project. The details will vary based on the headache they caused.

## Contents:
1. [Wrangling with JPA](#wrangling-with-jpa)
2. [Service to Service communication](#service-to-service-communication)
3. [Figuring out Redis](#figuring-out-redis)
4. [Security](#security)
4. [Gateway](#gateway)

## Wrangling with JPA:
This was the first project in which I started using JPA, it took a couple hours to familiarize myself with how the abstractions helped. Before this project, I've extensively utilized manual CRUD operations, where I took SQL files and loaded them at runtime into public static final strings which were executed on the DB via Springs JdbcTemplate and a bunch of Row mappers to map logic back to the Domain objects.

After a bit of trial and error I got the hang of it, and now I do believe these abstractions are essential. Whilst reinventing the wheel is fun, it gets stale after the 15th iteration. I still can’t let go of Manual Schemas though.

## Service to Service communication:
This was one of those issues which seemed easy at first before I started thinking of edge cases. Most of my services rely on synchronous calls, so for the interior I've Settled on Feign Clients. The syntax was familiar, since in essence it is the mirror of a controller, the questions arose though of: "How will my services find each other?", "How can I be sure which communications to trust?", and thus the need for a Gateway Service arose which would function as a doorman, that keeps the interior safe. 
At this point, I hadn’t yet adopted semantic REST best practices—my DTOs carried the entire request itself, and user specific data like ID, session String, etc. These would come back to haunt me once I got to the [Gateway building](#gateway) part.

To resolve service discovery and security, I've settled on the same Docker Stack approach, where the gateway would eventually vet incoming requests, validate them and then redirect them on the inside, also, the microservices would not be exposed to the outside only the gateway, thus solving internal security.

Then the issue arose of how I would configure the Docker Stack, with the whole rigmarole of what should be on what port. I decided against hardcoding ports after learning that docker can do internal routing well enough if the services run in the same docker stack and the Feign Clients are configured properly. Thus, the communication issue was resolved.

And then came the most jarring issue yet, how do I solve rollbacks, when interconnected requests fail. The prudent engineering choice is to have rollbacks defined and ready to fire once something irreversible fails. I settled on cacheing each step linked list of steps and rollbacks, for each logic chain that is dependent on other service states. In essence I’ve implemented an in-memory saga pattern, where if a request fails, everything would be rolled back till that point, thus avoiding the phantom users/changes problem.

## Figuring out Redis
Before this Project I only had experiences with Sqlite3, PostgreSQL, and H2 databases, all these being SQL based, Redis was a bit new. Especially after Freshly wrapping my brain around lovely abstractions like JPA. Here I rediscovered how to use Templates, and after a couple of trials and errors Redis was up and running to provide fast in memory access for Opaque Token Validation purposes. 

## Security
In my previous project I’ve handled password hashing, salt management, validation, data encryption and persistence manually, extensively utilizing established methods like the Web Crypto API or MKammerer's and BouncyCastle's Argon2 implementations. My first solo project was a Chrome based password manager chrome extension, which made me comfortable with cryptographic logic flows. Here I decided to upgrade and rely on spring security. To utilize an analogy, not every hammer is the same, and using other people’s prebuilt hammers brought up some unexpected issues down the line. 

Implementing an Argon2 based password validation method was relatively straight forward after browsing a bit of documentation and trial and error. However, including Spring security in a microservice came with some hiccups. I discovered this later once I got to integration testing logic flows, but by default spring security blocked any request tbhat didn't come with specific headers. Since I did my own logic at the Gateway, and did not rely on JWT, I decided on neutering Spring Security's security features beyond password hashing and validation for any service beyond the gateway. Whilst this might be frowned upon, the docker stack keeps only the Gateway exposed, the project has only one type of user, Sessions are handled via opaque tokens, and the Gateway has security filters for specific services, that blocks any incoming request to those that do not carry a valid sessionID. also, Interior userIDs are never exposed to the front end, the Opaque tokens and Session Service ensure a clear separation.

## Gateway
This was one of the hardest parts till I understood that a gateway is not a regular "spring application." My initial plan was to have a bunch of Feign Clients and controllers. The Gateway would receive a request, it would unpack and validate it, then it would make its own request to the specific interior service that needed to handle it. At this point not implementing semantic REST, best practices started biting. 

After a bit of digging, I learned about headers and their quirks, and about how to route the original request without baring the expenses of Jackson deserializing and reserializing Jsons.

The biggest hurdle by far was headers and how to modify them. Initially I had troubles with mutating them and getting to know how they work. Later I had more trouble sending and receiving data in said gateway, but this was an architectural challenge that I had to solve later. After Spending a couple of afternoons getting the hang of ServerWebExchange-s, Mono, and filter implementation via the application.yml file I finally had a functional gateway.


