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

const (
	Forward  int = 1
	Backward     = -1
)
const (
	Up   int = -1
	Down     = 1
)

func parse(s string) (int, int) {
	println(s)
	arr := strings.Split(s, " ")
	direction := arr[0]
	value, _ := strconv.Atoi(arr[1])
	switch direction {
	case "forward":
		return Forward * value, 0
	case "backward":
		return Backward * value, 0
	case "up":
		return 0, Up * value
	case "down":
		return 0, Down * value
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
			movementX, movementZ := parse(line)
			totalX += movementX
			totalZ += movementZ
		}
	}
	println("\tResult: ", totalX, " * ", totalZ, " = ", totalX*totalZ)
	return totalX, totalZ
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
