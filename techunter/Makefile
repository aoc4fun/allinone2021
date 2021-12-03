# Basic Makefile for Golang project
# Includes GRPC Gateway, Protocol Buffers
MONTH		:= $(shell date '+%-m')
THIS_YEAR	:= $(shell date '+%Y')
OFFSET		:= $(shell [ ${MONTH} -eq 12 ] && echo 0 || echo 1)
YEAR		:= $(shell expr $(THIS_YEAR) - $(OFFSET))
DAY			:= $(shell date '+%-d')
ifeq ($(THIS_YEAR), $(YEAR))
SEQ			:= $(shell seq 1 $(DAY))
else
SEQ			:= $(shell seq 1 31)
endif
PREPARE_JOBS:= $(addprefix $(YEAR)/prepare-,${SEQ})
BUILD_JOBS	:= $(addprefix $(YEAR)/build-,${SEQ})
RUN_JOBS	:= $(addprefix $(YEAR)/run-,${SEQ})
TEST_JOBS	:= $(addprefix $(YEAR)/test-,${SEQ})
URL			:= $(shell echo 'https://adventofcode.com/${YEAR}/day')
VERSION		?= $(shell git describe --tags --always --dirty --match=v* 2> /dev/null || cat $(PWD)/.version 2> /dev/null || echo v0)
PACKAGE		?= $(shell go list)
PACKAGES	?= $(shell go list ./...)
FILES		?= $(shell find . -type f -name '*.go' -not -path "./vendor/*")
OBJECTS		?= $(patsubst %.go, %.o, $(FILES))

.PHONY: help clean test build all

default: all

help:   ## show this help
	@echo 'usage: make [target] ...'
	@echo ''
	@echo 'targets:'
	@egrep '^(.+)\:\ .*##\ (.+)' ${MAKEFILE_LIST} | sed 's/:.*##/#/' | column -t -c 2 -s '#'

all:  clean run test ## clean, format, build and unit test

prepare: env $(PREPARE_JOBS)

$(YEAR)/prepare-%:
ifeq ($(strip $(COOKIE)),)
	echo missing COOKIE env && exit 1
endif
	mkdir -p $(YEAR)/$*
	[ -f "${YEAR}/$*/README.md" ] || (curl "https://adventofcode.com/${YEAR}/day/$*" --cookie "session=${COOKIE}" -o ${YEAR}/$*/.html && html2md -i "${YEAR}/$*/.html" > ${YEAR}/$*/README.md && rm ${YEAR}/$*/.html)
	[ -f "${YEAR}/$*/.input" ] || curl "https://adventofcode.com/${YEAR}/day/$*/input" --cookie "session=${COOKIE}" -o ${YEAR}/$*/.input

env:  ## Print useful environment variables to stdout
	echo MONTH		:= $(shell echo $(MONTH))
	echo DAY		:= $(shell echo $(DAY))
	echo OFFSET		:= $(shell echo $(OFFSET))
	echo YEAR		:= $(YEAR)
	echo SEQ		:= $(SEQ)
	echo $(PREPARE_JOBS)
	echo $(BUILD_JOBS)
	echo $(TEST_JOBS)
	echo $(CURDIR)
	echo $(PACKAGE)
	echo $(VERSION)

clean:  ## go clean
	go clean

tools:  ## fetch and install all required tools
	go get -u github.com/suntong/html2md

build: $(BUILD_JOBS)
run: $(RUN_JOBS)

$(YEAR)/build-%:
	make ${YEAR}/$*/main.o
$(YEAR)/run-%:
	go run ${YEAR}/$*/main.go

$(OBJECTS):

%.o: %.go
	go build -o bin/$*.o $*.go

test: ## generate grpc code and mocks and run all tests
	go test -v ./...
