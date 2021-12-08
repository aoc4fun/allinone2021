package main

import (
	"io/ioutil"
	"os"
	"strconv"
	"strings"
)

const Debug = false

type Game struct {
	Lottery      []string
	Boards       []Board
	winningOrder []int
}

type Board struct {
	numbers         [][]int
	colWinningCount []int
	rowWinningCount []int
	won             bool
	winningNumber   int
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

func processBoards(file string) Game {
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

	for i := 0; i < len(lottery); i++ {
		pickedNumber, _ := strconv.Atoi(lottery[i])
		if Debug {
			println("picking ", pickedNumber)
		}
		for boardIdx := 0; boardIdx < len(game.Boards); boardIdx++ {
			//print("\nBoard: ",boardIdx, ", number: ", pickedNumber)
			if !game.Boards[boardIdx].won && CheckWin(pickedNumber, &game.Boards[boardIdx]) {
				game.winningOrder = append(game.winningOrder, boardIdx)
			}
		}
		if len(game.winningOrder) == len(boards) {
			break
		}
	}
	if Debug {
		println("game ended, winning order is :")
		for i, boardIdx := range game.winningOrder {
			println("Rank ", i, " for board ", boardIdx)
			PrintBoard(&game.Boards[boardIdx])
		}
	}
	return game
}

func FirstCase(file string) int {
	if Debug {
		wd, _ := os.Getwd()
		println("Workdir is ", wd)
		println("DAY-4 -- 1st case -- input: ", file)
	}
	game := processBoards(file)
	firstBoard := game.Boards[game.winningOrder[0]]
	result := Score(firstBoard)
	PrintBoard(&firstBoard)
	return result
}

func SecondCase(file string) int {
	if Debug {
		wd, _ := os.Getwd()
		println("Workdir is ", wd)
		println("DAY-4 -- 1st case -- input: ", file)
	}
	game := processBoards(file)
	lastBoard := game.Boards[game.winningOrder[len(game.winningOrder)-1]]
	result := Score(lastBoard)
	PrintBoard(&lastBoard)
	//println("\tResult: ", result)
	return result
}

func Score(board Board) int {
	return board.winningNumber * SumBoard(board)
}

func SumBoard(board Board) int {
	sum := 0
	for i := 0; i < BoardRows; i++ {
		for j := 0; j < BoardCols; j++ {
			if board.numbers[i][j] != -1 {
				sum += board.numbers[i][j]
			}
		}
	}
	return sum
}

func CheckWin(numberPicked int, board *Board) bool {
	if board.won {
		return true
	}
	for i := 0; i < BoardRows; i++ {
		for j := 0; j < BoardCols; j++ {
			if board.numbers[i][j] == numberPicked {
				//print(" --> row ",i, " ++ , col ", j, " ++\n")
				board.colWinningCount[j]++
				board.rowWinningCount[i]++
				board.numbers[i][j] = -1
				if board.rowWinningCount[i] == BoardRows || board.colWinningCount[j] == BoardCols {
					board.won = true
					board.winningNumber = numberPicked
					//print("--> won!!!")
					//PrintBoard(board)
					return true
				}
			}
		}
	}
	return false
}

func PrintBoard(board *Board) {
	if !Debug {
		println("\tResult: ", Score(*board))
	} else {
		println("\nBoard:")
		for i := 0; i < BoardRows; i++ {
			for j := 0; j < BoardCols; j++ {
				if board.numbers[i][j] != -1 {
					print(" ", board.numbers[i][j])
				} else {
					print(" ..")
				}
			}
			print("\n")
		}
		if board.won {
			println("This board won with number ", board.winningNumber)
			print("Score is ", Score(*board))
		}
		print("\n")
	}
}

func ExtractBoard(lines []byte, size int) Board {
	result := make([][]int, size)

	lineSize := size * 3
	for i := 0; i < size; i++ {
		result[i] = make([]int, size)
		rj := 0
		for j := i * lineSize; j < (i+1)*lineSize; j += 3 {
			elt, _ := strconv.Atoi(strings.Trim(string(lines[j:j+2]), " "))
			result[i][rj] = elt
			rj++
		}

	}
	return Board{numbers: result, colWinningCount: make([]int, BoardCols), rowWinningCount: make([]int, BoardRows)}
}

func makeRange(min, max int) []int {
	a := make([]int, max-min)
	for i := range a {
		a[i] = min + i
	}
	return a
}
