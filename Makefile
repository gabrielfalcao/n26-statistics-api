all: tests

tests: unit functional

unit:
	./gradlew test --tests it.falcao.n26.StatsAPI.repositories.*

functional:
	./gradlew test --tests it.falcao.n26.StatsAPI.controllers.*

run:
	./gradlew bootRun

idea:
	./gradlew cleanIdea
	./gradlew idea
