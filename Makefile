all: build idea tests


build:
	./gradlew clean build

idea:
	./gradlew cleanIdea
	./gradlew idea

tests: unit functional


unit:
	./gradlew test --tests it.falcao.n26.StatsAPI.unit.*

functional:
	./gradlew test --tests it.falcao.n26.StatsAPI.functional.*

integration:
	./tools/wait-for-it.sh localhost 8000 -- bash ./integration-tests.sh

run:
	./gradlew bootRun
