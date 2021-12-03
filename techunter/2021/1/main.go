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
	SecondCase(prefix + ".input")
}

func FirstCase(file string) int {
	var lastDepth = 0
	var count = -1
	wd, _ := os.Getwd()
	println("Workdir is ", wd)
	print("DAY-1 -- 1st case -- input: ", file)

	input, _ := ioutil.ReadFile(file)
	for _, line := range strings.Split(strings.ReplaceAll(string(input), "\r", ""), "\n") {
		depth, _ := strconv.Atoi(line)
		if depth > lastDepth {
			count++
		}
		lastDepth = depth
	}
	println("\tResult: ", count)
	return count
}

func SecondCase(file string) int {
	var lastSum = 0
	var count = -1
	// let's keep it simple :)
	print("DAY-1 -- 2nd case -- input: ", file)
	input, _ := ioutil.ReadFile(file)
	arr := strings.Split(strings.ReplaceAll(string(input), "\r", ""), "\n")
	for i := 0; i < len(arr)-2; i++ {
		depthN, _ := strconv.Atoi(arr[i])
		depthN1, _ := strconv.Atoi(arr[i+1])
		depthN2, _ := strconv.Atoi(arr[i+2])
		sum := depthN + depthN1 + depthN2

		if sum > lastSum {
			count++
		}
		lastSum = sum
	}
	println("\tResult: ", count)
	return count
}
