package main

import (
	"bytes"
	"io/ioutil"
	"os"
	"strconv"
)

const Debug = false

func main() {
	var prefix string
	if len(os.Args) > 1 {
		prefix = os.Args[1]
	} else {
		prefix = "./2021/6/"
	}
	FirstCase(prefix + ".input")
	SecondCase(prefix + ".input")
}

func processInput(file string) [][]byte {
	input, _ := ioutil.ReadFile(file)
	return bytes.Split(input[:bytes.Index(input, []byte("\n"))], []byte(","))
}

func FirstCase(file string) int {
	return run(file, 80)
}
func SecondCase(file string) int {
	return run(file, 256)
}

func run(file string, end int) int {
	if Debug {
		wd, _ := os.Getwd()
		println("Workdir is ", wd)
		println("DAY-6 -- 1st case -- input: ", file)
	}
	input := processInput(file)
	var fishArray [9]int
	for _, b := range input {
		fishVal, _ := strconv.Atoi(string(b))
		fishArray[fishVal]++
	}
	for val, count := range fishArray {
		println(val, " : ", count)
	}
	counter := 0

	for gen := 0; gen < end; gen++ {
		fishToMakeNew := fishArray[0]
		for t := range fishArray[:len(fishArray)-1] {
			fishArray[t] = fishArray[t+1]
		}
		fishArray[6] += fishToMakeNew
		fishArray[8] = fishToMakeNew

	}
	for _, fishes := range fishArray {
		counter += fishes
	}
	println("\tResult: ", counter)

	return counter
}
