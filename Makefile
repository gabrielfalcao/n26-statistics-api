all: tests

tests: unit functional

unit:
	./gradlew test --tests it.falcao.n26.StatsAPI --info # TODO: run functional tests only

functional:
	./gradlew test --tests it.falcao.n26.StatsAPI --debug # TODO: run functional tests only

run:
	./gradlew bootRun

idea:
	./gradlew cleanIdea
	./gradlew idea
