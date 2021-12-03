package main

import (
	"fmt"
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
		prefix = "./2021/3/"
	}
	FirstCase(prefix + ".input")
	SecondCase(prefix + ".input")
}

func FirstCase(file string) int {
	wd, _ := os.Getwd()
	println("Workdir is ", wd)
	print("DAY-3 -- 1st case -- input: ", file)

	input, _ := ioutil.ReadFile(file)
	lines := strings.Split(strings.ReplaceAll(string(input), "\r", ""), "\n")
	firstLine := strings.Split(lines[0], "")
	var count0 = make([]int, len(firstLine))
	var count1 = make([]int, len(firstLine))
	var gammaBits = make([]int, len(firstLine))
	var epsilonBits = make([]int, len(firstLine))

	for j := 0; j < len(strings.Split(lines[0], "")); j++ {
		count0[j] = 0
		count1[j] = 0
	}
	for _, line := range lines {
		if line != "" {
			arr := strings.Split(line, "")
			for i, bit := range arr {
				if bit == "0" {
					count0[i] = count0[i] + 1
				} else {
					count1[i] = count1[i] + 1
				}

			}
		}
	}
	for j := 0; j < len(count0); j++ {
		c0 := count0[j]
		c1 := count1[j]
		if c0 > c1 {
			gammaBits[j] = 0
			epsilonBits[j] = 1
		} else {
			gammaBits[j] = 1
			epsilonBits[j] = 0
		}
	}
	result := powerConsumption(convert(gammaBits), convert(epsilonBits))
	println("\tResult: ", result)
	return result
}

func convert(arr []int) int {
	res, _ := strconv.ParseInt(arrayToString(arr[:], ""), 2, 32)
	return int(res)
}
func arrayToString(a []int, delim string) string {
	return strings.Trim(strings.Replace(fmt.Sprint(a), " ", delim, -1), "[]")
}

func SecondCase(file string) int {
	var lastSum = 0
	var count = -1
	// let's keep it simple :)
	print("DAY-3 -- 2nd case -- input: ", file)
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

/** The power consumption can then be found by multiplying the gamma rate by the epsilon rate. **/
func powerConsumption(gammaRate int, epsilonRate int) int {
	return gammaRate * epsilonRate
}
