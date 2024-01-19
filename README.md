# Minecraft Event Listener

A web service to listen for events from a Minecraft Bedrock server, and display recent data.

## Setup

Requires the "Player Location Service" addon to be installed on the Minecraft server. 
_I haven't published that yet, so instructions TBD._

## Tech Stack

Part of the motivation for this project was to try out some different Java libraries that I was interested in.

### Avaje
Avaje-DI provides annotation based dependency injection similar to Micronaut and Spring, but as a small independent library rather than a framework. 
Avaje-HTTP builds on it to add Controllers similar to Micronaut.

**Javalin** is being used as the underlying web server, but the controllers don't reference Javalin code directly.

Avaje-config and Avaje-Jsonb add other useful features but do not have to be used with the others.

### JDBI
JDBI supports annotation based declarative SQL queries.

### JStachio

Annotation based templates using mustache syntax.

_Yes, this project **is** over-engineered for its size._