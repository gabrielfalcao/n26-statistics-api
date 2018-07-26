all: tests

tests: unit functional

unit:
	./gradlew test # TODO: run unit tests only

functional:
	./gradlew test # TODO: run unit tests only
