package main

import (
	"image"
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
		prefix = "./2021/5/"
	}
	FirstCase(prefix + ".input")
	SecondCase(prefix + ".input")
}

type Segment struct {
	a            image.Point
	b            image.Point
	isVertical   bool
	isHorizontal bool
}

type IntersectionPoint struct {
	point image.Point
	count int
}

func makePoint(str string) image.Point {
	coords := strings.Split(str, ",")
	x, _ := strconv.Atoi(coords[0])
	y, _ := strconv.Atoi(coords[1])
	return image.Point{
		X: x,
		Y: y,
	}
}

func processInput(file string) ([]Segment, image.Point) {
	input, _ := ioutil.ReadFile(file)
	lineDefs := strings.Split(strings.ReplaceAll(string(input), "\r", ""), "\n")
	var lines []Segment
	maxPoint := image.Point{
		X: 0,
		Y: 0,
	}
	for _, def := range lineDefs {
		if strings.TrimSpace(def) == "" {
			break
		}
		arr := strings.Split(def, " -> ")
		lines = append(lines, makeSegment(arr, &maxPoint))
	}
	return lines, maxPoint
}
func makeSegment(arr []string, max *image.Point) Segment {
	a := makePoint(arr[0])
	b := makePoint(arr[1])
	if a.X > max.X {
		max.X = a.X
	}
	if b.X > max.X {
		max.X = b.X
	}
	if a.Y > max.Y {
		max.Y = a.Y
	}
	if b.Y > max.Y {
		max.Y = b.Y
	}
	if a.X > b.X {
		return Segment{a: b, b: a, isVertical: a.X == b.X, isHorizontal: a.Y == b.Y}
	} else if a.X == b.X {
		if a.Y > b.Y {
			return Segment{a: b, b: a, isVertical: a.X == b.X, isHorizontal: a.Y == b.Y}
		} else if a.Y == b.Y {
			println("warning same segment detected")
		}
	}
	return Segment{a: a, b: b, isVertical: a.X == b.X, isHorizontal: a.Y == b.Y}
}
func isAxisOrthogonal(l *Segment) bool {
	if l.a.X == l.b.X {
		l.isVertical = true
		return true
	}
	if l.a.Y == l.b.Y {
		l.isHorizontal = true
		return true
	}
	return false
}

func FirstCase(file string) int {
	if Debug {
		wd, _ := os.Getwd()
		println("Workdir is ", wd)
		println("DAY-5 -- 1st case -- input: ", file)
	}
	segments, maxPoint := processInput(file)
	n := 0
	for _, line := range segments {
		if isAxisOrthogonal(&line) {
			segments[n] = line
			n++
			if Debug {
				PrintLine(line)
				println()
			}
		}

	}

	segments = segments[:n]
	intersectionPoints := fill(make([][]int, maxPoint.X+1), maxPoint.Y+1, 0)
	counter := 0
	for _, segment := range segments {
		if segment.isHorizontal {
			for i := segment.a.X; i <= segment.b.X; i++ {
				if Debug {
					println("(", i, ",", segment.a.Y, ")")
				}
				intersectionPoints[segment.a.Y][i]++
				if intersectionPoints[segment.a.Y][i] == 2 {
					counter++
				}
			}
		} else {
			for i := segment.a.Y; i <= segment.b.Y; i++ {
				if Debug {
					println("(", segment.a.X, ",", i, ")")
				}
				intersectionPoints[i][segment.a.X]++
				if intersectionPoints[i][segment.a.X] == 2 {
					counter++
				}
			}
		}

	}
	println("\tResult: ", counter)
	return counter
}
func PrintLine(line Segment) {
	print("[(", line.a.X, ",", line.a.Y, "),(", line.b.X, ",", line.b.Y, ")]")
}
func PrintTable(lines [][]int) {
	for _, line := range lines {
		for _, c := range line {
			if c <= 0 {
				print(".")
			} else {
				print(c)
			}
		}
		print("\n")
	}
}
func fill(arr [][]int, innerArraySize int, value int) [][]int {
	for i := 0; i < len(arr); i++ {
		arr[i] = make([]int, innerArraySize)
		for j := 0; j < innerArraySize; j++ {
			arr[i][j] = value
		}
	}
	return arr
}

func SecondCase(file string) int {
	if Debug {
		wd, _ := os.Getwd()
		println("Workdir is ", wd)
		println("DAY-5 -- 1st case -- input: ", file)
	}
	segments, maxPoint := processInput(file)
	XMax := maxPoint.X
	YMax := maxPoint.Y

	intersectionPoints := fill(make([][]int, XMax+1), YMax+1, 0)
	counter := 0

	for _, segment := range segments {
		if segment.isHorizontal {
			for i := segment.a.X; i <= segment.b.X; i++ {
				if Debug {
					println("(", i, ",", segment.a.Y, ")")
				}
				intersectionPoints[segment.a.Y][i]++
				if intersectionPoints[segment.a.Y][i] == 2 {
					counter++
				}
			}
		} else if segment.isVertical {
			for i := segment.a.Y; i <= segment.b.Y; i++ {
				if Debug {
					println("(", segment.a.X, ",", i, ")")
				}
				intersectionPoints[i][segment.a.X]++
				if intersectionPoints[i][segment.a.X] == 2 {
					counter++
				}
			}
		} else {
			// since we are in an orthonormal plot with f(x)= a * x + offset, with a = [1 ; -1]
			reg := GetSegmentFunction(segment)

			for x := reg.LowerBound; x <= reg.UpperBound; x++ {
				y := f(reg, x)
				if Debug {
					println("(", x, ",", y, ")")
				}
				intersectionPoints[y][x]++
				if intersectionPoints[y][x] == 2 {
					counter++
				}
			}
		}
	}

	PrintTable(intersectionPoints)
	println("\tResult: ", counter)
	return counter
}

type RegressionLine struct {
	Slope      int
	Offset     int
	LowerBound int
	UpperBound int
}

func f(line RegressionLine, x int) int {
	y := line.Offset + line.Slope*x
	println("f(", x, ") = ", line.Slope, "* x + (", line.Offset, ") = ", y)
	return y
}
func GetSegmentFunction(segment Segment) RegressionLine {
	PrintLine(segment)
	reg := RegressionLine{}
	if (segment.b.X-segment.a.X)/(segment.b.Y-segment.a.Y) > 0 {
		reg.Offset = segment.a.Y - segment.a.X
		reg.Slope = 1
	} else {
		// negative slope
		reg.Offset = segment.a.X + segment.a.Y
		reg.Slope = -1
	}
	if segment.a.X < segment.b.X {
		reg.LowerBound = segment.a.X
		reg.UpperBound = segment.b.X
	} else {
		reg.LowerBound = segment.b.X
		reg.UpperBound = segment.a.X
	}

	return reg
}
