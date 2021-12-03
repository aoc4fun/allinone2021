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
		prefix = "./2021/2/"
	}
	FirstCase(prefix + ".input")
	SecondCase(prefix + ".input")
}

type MoveType uint8

const (
	Z MoveType = iota
	X
)

func parse(s string) (MoveType, int) {
	//println(s)
	arr := strings.Split(s, " ")
	direction := arr[0]
	value, _ := strconv.Atoi(arr[1])
	switch direction {
	case "forward":
		return X, value
	case "up":
		return Z, -1 * value
	case "down":
		return Z, value
	}
	return 0, 0
}

func FirstCase(file string) (int, int) {
	wd, _ := os.Getwd()
	println("Workdir is ", wd)
	print("DAY-2 -- 1st case -- input: ", file)

	input, _ := ioutil.ReadFile(file)
	totalX, totalZ := 0, 0
	for _, line := range strings.Split(strings.ReplaceAll(string(input), "\r", ""), "\n") {
		if line != "" {
			moveType, value := parse(line)
			if moveType == X {
				totalX += value
			} else if moveType == Z {
				totalZ += value
			}
		}
	}
	println("\tResult: ", totalX, " * ", totalZ, " = ", totalX*totalZ)
	return totalX, totalZ
}

func SecondCase(file string) (int, int) {
	wd, _ := os.Getwd()
	println("Workdir is ", wd)
	print("DAY-2 -- 2nd case -- input: ", file)

	input, _ := ioutil.ReadFile(file)
	horizontalPosition, depth, aim := 0, 0, 0
	for _, line := range strings.Split(strings.ReplaceAll(string(input), "\r", ""), "\n") {
		if line != "" {
			moveType, value := parse(line)
			if moveType == X {
				horizontalPosition += value
				depth = aim*value + depth
			} else if moveType == Z {
				aim = aim + value
			}
		}
	}
	println("\tResult: ", horizontalPosition, " * ", depth, " = ", horizontalPosition*depth)
	return horizontalPosition, depth
}
