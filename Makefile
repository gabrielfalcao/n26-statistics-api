all: tests

tests: unit functional

unit:
	./gradlew test # TODO: run unit tests only

functional:
	./gradlew test # TODO: run unit tests only

run:
	./gradlew bootRun

idea:
	./gradlew cleanIdea
	./gradlew idea
