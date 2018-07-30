# N26 Backend Challenge 286

[**challenge.pdf**](BackendChallenge_286.pdf)

## Requirements

- Java 8
- gradle

**Helpful Links:**

- [IntelliJ: Selecting the JDK version](https://intellij-support.jetbrains.com/hc/en-us/articles/206544879-Selecting-the-JDK-version-the-IDE-will-run-under)
- [SDKMAN! - SDK Version Manager](https://sdkman.io/usage#installspecific)

## Tech

- SpringBoot
- Lombok
- Junit5
- Hamcrest
- Mockito


## Running Tests

### Unit Tests

- No I/O involved

```bash
./gradlew test --tests it.falcao.n26.StatsAPI.unit.*
```


### Functional Tests

- API Endpoints tested with a real server instance

```bash
./gradlew test --tests it.falcao.n26.StatsAPI.functional.*
```

## Running Server

from command line:

```bash
./gradlew bootRun
```


Default host: [http://localhost:8000/](http://localhost:8000/)


## Configure IntelliJ

```bash
./gradlew cleanIdea idea
```

## Tools

- [N26_Challenge.postman_collection.json](N26_Challenge.postman_collection.json) contains
  a javascript-based test that automatically sends 10 transactoins, 7
  with amount 100, 2 with 50 and 1 with 200.
