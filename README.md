# REST service for Hibernate environment

This was inspired by a project I did for a J2EE class I took at BCIT. The prof is a guru of object design, and provided insights into encapsulation and abstraction that still blow my mind today.

There are screenshots and collaboration diagrams in the respsitory for the the client-side app, but succinctly this project uses stateless EJBs to manage a JNDI datasource in Wildfly via Hibernate, with an entity representation, and present those sevices in local and remote REST endpoints. In most cases it doesn't send the datastore's entity back, but just a custom object containg String responses.

It's over-engineered for its limited scope, but the granular design could easily be expanded out to provide any new services that just need to be "plugged in". 

## Coming Soon

I'm going to refactor to use a message-driven bean in the service layer
