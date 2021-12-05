package main

import (
	"bytes"
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
	wd, _ := os.Getwd()
	println("Workdir is ", wd)
	println("DAY-3 -- 1st case -- input: ", file)

	input, _ := ioutil.ReadFile(file)
	input = bytes.ReplaceAll(input, []byte("\r\n"), []byte("\n"))
	var width = bytes.Index(input, []byte("\n"))
	inputLength := len(input)
	if input[inputLength-1] == byte('\n') {
		inputLength--
	}
	numberOfLines := (inputLength + 1) / (width + 1)
	arr := make([][]byte, numberOfLines)
	for i := 0; i < numberOfLines; i++ {
		arr[i] = input[i*(width+1) : i*(width+1)+width]
	}
	println(numberOfLines, " lines of width ", width, ", total of ", inputLength, " bytes")
	oxy := filter(arr, makeRange(0, numberOfLines), numberOfLines, 0, width, true)
	co2 := filter(arr, makeRange(0, numberOfLines), numberOfLines, 0, width, false)

	oxyRating, _ := strconv.ParseInt(string(arr[oxy]), 2, 32)
	co2Rating, _ := strconv.ParseInt(string(arr[co2]), 2, 32)

	result := oxyRating * co2Rating
	println("\tResult: ", result)
	return int(result)
}

func makeRange(min, max int) []int {
	a := make([]int, max-min)
	for i := range a {
		a[i] = min + i
	}
	return a
}

func filter(input [][]byte, indexes []int, sizeOfIndexes int, offset int, width int, direction bool) int {
	if sizeOfIndexes > 1 {
		count0 := 0
		count1 := 0
		count0Indexes := make([]int, sizeOfIndexes)
		count1Indexes := make([]int, sizeOfIndexes)
		for i := 0; i < sizeOfIndexes; i++ {
			lineIndex := indexes[i]
			if input[lineIndex][offset] == byte('0') {
				count0Indexes[count0] = lineIndex
				count0++
			} else {
				count1Indexes[count1] = lineIndex
				count1++
			}
		}
		if count0 == count1 {
			if direction {
				return filter(input, count1Indexes, count1, offset+1, width, direction)
			}
			return filter(input, count0Indexes, count0, offset+1, width, direction)
		}

		if count0 > count1 {
			if direction {
				return filter(input, count0Indexes, count0, offset+1, width, direction)
			}
			return filter(input, count1Indexes, count1, offset+1, width, direction)

		} else {
			if direction {
				return filter(input, count1Indexes, count1, offset+1, width, direction)
			}
			return filter(input, count0Indexes, count0, offset+1, width, direction)
		}

	}
	return indexes[0]

}

/** The power consumption can then be found by multiplying the gamma rate by the epsilon rate. **/
func powerConsumption(gammaRate int, epsilonRate int) int {
	return gammaRate * epsilonRate
}
