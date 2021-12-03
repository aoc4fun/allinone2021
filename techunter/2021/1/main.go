package main

import (
	"io/ioutil"
	"os"
	"strconv"
	"strings"
)

func main() {
	var prefix string
	if len(os.Args) > 1 {
		prefix = os.Args[1]
	} else {
		prefix = "./2021/1/"
	}
	FirstCase(prefix + ".input")
}

func FirstCase(file string) int {
	var lastDepth = 0
	var count = -1
	println("DAY-1 -- input: ", file)
	input, _ := ioutil.ReadFile(file)
	for _, line := range strings.Split(strings.ReplaceAll(string(input), "\r", ""), "\n") {
		depth, _ := strconv.Atoi(line)
		if depth > lastDepth {
			count++
		}
		lastDepth = depth
	}
	println("Result: ", count)
	return count
}
