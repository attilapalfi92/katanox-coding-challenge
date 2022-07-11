
# testcontainers was not able to start and pull the images by itself. more details at:
# https://stackoverflow.com/questions/72132352/messagehead-https-registry-1-docker-io-v2-testcontainers-ryuk-manifests
# https://github.com/testcontainers/testcontainers-java/issues/5121
# I was able to solve this issue by manually pulling the images first
init-testcontainers:
	docker pull postgres:13.5
	docker pull testcontainers/ryuk:0.3.3

build: init-testcontainers
	mvn clean compile

run-deps:
	docker-compose -f docker-compose.yml up
