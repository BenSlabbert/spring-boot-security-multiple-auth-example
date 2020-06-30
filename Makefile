#!make

.PHONY: build clean fmt run

build: clean
	@ ./gradlew build

clean:
	 @./gradlew clean

fmt:
	@ ./gradlew spotlessJavaApply

run: fmt build
	@ ./gradlew bootRun
