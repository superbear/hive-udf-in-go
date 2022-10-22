# init command params
GO := go

# env
export GO111MODULE := on

build-go: ## build go shared library
	cd go && $(GO) build -o awesome.so -buildmode=c-shared awesome.go
	cp go/awesome.so src/main/resources/

build: ## build jar
	make build-go
	mvn clean package assembly:single

test: ## test
	make build-go
	rm -f awesome.so
	mvn clean test

clean: ## go clean && rm build output
	rm -f awesome.so
	mvn clean
	cd go && $(GO) clean

help: ## show this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: build clean test help

## default target
.DEFAULT_GOAL := help
