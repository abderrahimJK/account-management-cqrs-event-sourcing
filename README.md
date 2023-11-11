# event_driven_architecture_cqrs_and_event_sourcing
    
- A micro-services architecture based on CQRS and event Sourcing with Spring Cloud and AXON.

---

# Objective

> Create an application that allows for managing accounts in accordance with the CQRS and Event Sourcing patterns using the AXON and Spring Boot frameworks

---

# POC (Proof of concept)

---

# Event Sourcing

### <a name='Whatisit'></a>What is it ?

- It is a Pattern architecture (Strategic) [The others are called tactic ex. Adapter, Sigleton ...].
- Used to implement this kind of Architecture (Even driven arch).
- In the implementation we use actually 2 famous patterns (CQRS & Event Sourcing).
- **Role**: :fire: Track all changes in the state of an application as a sequence of events [Event Store].
- The objective is to do not focus on the current state of the application, but on the sequence of state changes (business events) that led to the current state.
- From this sequence of events, we can aggregate the current state of the application.
- Every change in the state of the application has a unique cause (event).
- For example: operations performed on a bank account (CREATED, ACTIVATED, CREDITED, DEBITED ...).
- All the events are stored in a single database table, it is :fire: **Event store**.
- This gives us the possiblity to return to an exact state and the history of our app.

### <a name='EventsourcingAdvantages.'></a>Event sourcing  Advantages.
- Its privide an **Audit base** (Database).
- **Analysis and Debug**: Easily find the source of production bugs.
- **Data recovery**: In case of a failure (Panne), replay all recorded business events to find the state of the application.
- **Performance**: Asynchronous with message buses that scale well.
- From the events, we can create multiple projections with different data models.

### <a name='EventsourcingAchitectureandTerminology'></a>Event sourcing Achitecture and Terminology
- More info: https://microservices.io/patterns/data/event-sourcing.html

#### <a name='Command'></a>Command
- External solitation (Request, Demmad) to the system.
- Changes the system (Creat, Update, Delete ...).
- Each command arrives => invokes (déclenche) a **Decison function** (Fonction de décision) which is the Business logic [`Command Handler` => Command listener].

#### <a name='Decisonfunction'></a>Decison function
- It is the businness logic :fire:
- Invoked when a command accures
- It is `Command Handler` which means it is a command listener.
- (Actual state, Command) => List[Event]
- This list of events will be stored to the `Event Store`

#### <a name='Event'></a>Event
- The fact that they were produced in the recent past (ex. AccountCreatedEvent)
- They are Immutable : can not be changed or modified (It is past...No stters allowed)
- The are auto Descriptive : We do not have to go to other to understand its functionnality.

#### <a name='EventStore'></a>Event Store
- Database that stores all the events emmited by the **decision function**.

#### <a name='EvolutionFunction'></a>Evolution Function
- It is the **`EventSourcing handler`**
- It listens on the Events (Event Listener).
- Here where update the state of the application (with the new state arrived with the event).
- No business logic here :fire:
- (Actual State, Event ) => new State

#### <a name='Actions'></a>Actions
- It is an internal Command
- Acommand produced by the Application
- If for example the decision is done in multiple stapes [Create account + Activate account].

#### <a name='EffetsdeBordsEdgeEffects'></a>Effets de Bords |  Edge Effects
- Publish events to partner apps => `Event Published to a Topic`.
- Data that records all events emitted by the decision function.

---

# CQRS