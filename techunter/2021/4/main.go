package main

import (
	"bytes"
	"fmt"
	"io/ioutil"
	"os"
	"strconv"
	"strings"
)

type Game struct {
	Lottery []string
	Boards  []Board
}

type Board struct {
	numbers         [][]int
	colWinningCount []int
	rowWinningCount []int
	won             bool
}

func main() {
	var prefix string
	if len(os.Args) > 1 {
		prefix = os.Args[1]
	} else {
		prefix = "./2021/4/"
	}
	FirstCase(prefix + ".input")
	SecondCase(prefix + ".input")
}

const BoardCols = 5
const BoardRows = 5

func FirstCase(file string) int {
	wd, _ := os.Getwd()
	println("Workdir is ", wd)
	print("DAY-4 -- 1st case -- input: ", file)

	input, _ := ioutil.ReadFile(file)
	lines := strings.Split(strings.ReplaceAll(string(input), "\r", ""), "\n\n")
	lottery := strings.Split(lines[0], ",")
	boards := lines[1:]
	game := Game{
		Lottery: lottery,
		Boards:  make([]Board, len(boards)),
	}
	println("Starting game with ", len(boards), " boards")

	for i, l := range boards {
		game.Boards[i] = ExtractBoard([]byte(l), BoardRows)
	}

	winnerIdx := -1
	var winningNumber int

	for i := 0; i < len(lottery) && winnerIdx == -1; i++ {
		pickedNumber, _ := strconv.Atoi(lottery[i])
		for boardIdx := 0; boardIdx < len(game.Boards) && winnerIdx == -1; boardIdx++ {
			if CheckWin(pickedNumber, game.Boards[boardIdx]) {
				winningNumber = pickedNumber
				winnerIdx = boardIdx
			}

		}
	}

	result := winningNumber * SumBoard(game.Boards[winnerIdx])
	println("\tResult: ", result)
	return result
}
func SumBoard(board Board) int {
	sum := 0
	for i := 0; i < BoardRows; i++ {
		for j := 0; j < BoardCols; j++ {
			sum += board.numbers[i][j]
		}
	}
	return sum
}

func CheckWin(numberPicked int, board Board) bool {
	if board.won {
		return true
	}
	for i := 0; i < BoardRows; i++ {
		for j := 0; j < BoardCols; j++ {
			if board.numbers[i][j] == numberPicked {
				board.colWinningCount[j]++
				board.rowWinningCount[i]++
				board.numbers[i][j] = 0
				if board.rowWinningCount[i] == BoardRows || board.colWinningCount[j] == BoardCols {
					board.won = true
					return true
				}
			}
		}
	}
	return false
}

func ExtractBoard(lines []byte, size int) Board {
	result := make([][]int, size)

	lineSize := size * 3
	for i := 0; i < size; i++ {
		result[i] = make([]int, size)
		rj := 0
		for j := i * lineSize; j < (i+1)*lineSize; j += 3 {
			elt, _ := strconv.Atoi(string(lines[j : j+2]))
			result[i][rj] = elt
			rj++
		}

	}
	return Board{numbers: result, colWinningCount: make([]int, BoardCols), rowWinningCount: make([]int, BoardRows)}
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
