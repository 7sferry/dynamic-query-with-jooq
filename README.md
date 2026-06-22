# Dynamic Query with jOOQ

Small Java 21 / Maven demo that uses jOOQ to build dynamic SQL queries against a PostgreSQL schema.

The project shows how to:

- generate jOOQ classes from a local `schema.sql`
- map query results into entity-like classes and projection records
- build optional `WHERE` clauses from a filter object
- conditionally join tables only when a query needs them

## Tech Stack

- Java 21
- Maven
- jOOQ 3.19.34
- PostgreSQL driver 42.7.5
- Lombok 1.18.42

## Project Structure

- `src/main/resources/schema.sql` - schema used by jOOQ code generation
- `src/main/java/src/com/example/jooq/App.java` - simple demo entry point
- `src/main/java/src/com/example/jooq/StudentJooqRepository.java` - repository implementation
- `src/main/java/src/com/example/jooq/StudentFilter.java` - dynamic filter input
- `src/main/java/src/com/example/jooq/StudentEntity.java` - entity-style result type
- `src/main/java/src/com/example/jooq/StudentNameAndBatchNameProjection.java` - projection record
- `src/main/java/src/com/example/jooq/StudentNameAndBirthDateProjection.java` - projection record

Generated jOOQ sources are written to `target/generated-sources/jooq`.

## Database Schema

The demo schema contains two tables:

- `student_batch`
- `student`

`student` references `student_batch` through `student_batch_id`.

## What the Demo Does

`StudentJooqRepository` demonstrates:

- filtering by `npm`, `npms`, `nik`, `studentName`, `active`, `birthDateRangeStart`, `birthDateRangeEnd`, and `batchYear`
- selecting only the columns needed for a projection
- joining `student_batch` only when the filter or projection needs batch data
- mapping directly into Java records with `fetchInto(...)`

## Requirements

- JDK 21
- Maven 3.9+ recommended
- PostgreSQL running locally

> The current `App.java` uses hardcoded connection details:
>
> `jdbc:postgresql://localhost:5432/dynamic_query?currentSchema=dynamic_query_example`
>
> username: `postgres`
>
> password: `12345`
>
> Update these values before running if your local database differs.

## Setup

1. Create the database referenced by the demo connection string.
2. Apply `src/main/resources/schema.sql` to that database.
3. Make sure the schema name in the JDBC URL matches the schema you created.
4. Install or select JDK 21.

## Build

Generate jOOQ sources and compile the project:

```bash
mvn clean compile
```

Run the demo:

```bash
mvn exec:java -Dexec.mainClass=src.com.example.jooq.App
```

If you do not have the Maven Exec plugin configured, you can run `App` from your IDE instead.

## Notes

- The repository is intentionally small and focused on showing dynamic query construction with jOOQ.
- `StudentJooqRepository` currently prints generated SQL for a couple of methods to help inspect the query shape during development.
- The local environment used to inspect this repo only had Java 17 installed, so `mvn test` fails here until JDK 21 is available.
