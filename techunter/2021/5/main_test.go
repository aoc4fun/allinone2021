package main

import (
	"image"
	"testing"
)

const (
	FirstCaseResult  = 5
	SecondCaseResult = 12
)

func TestFirstCase(t *testing.T) {

	ret := FirstCase(".test")
	if ret != FirstCaseResult {
		t.Errorf("First case was incorrect, got %d want %d", ret, FirstCaseResult)
	}

}

func TestSecondCase(t *testing.T) {

	ret := SecondCase(".test")
	if ret != SecondCaseResult {
		t.Errorf("Second case was incorrect, got %d want %d", ret, SecondCaseResult)
	}

}

func TestIsIntersect(t *testing.T) {
	vertical := Segment{
		a:          image.Point{X: 1, Y: 0},
		b:          image.Point{X: 1, Y: 3},
		isVertical: true,
	}
	horizontal := Segment{
		a:          image.Point{X: 1, Y: 0},
		b:          image.Point{X: 3, Y: 3},
		isVertical: true,
	}
	_, ret := IsIntersect(vertical, horizontal)
	if !ret {
		t.Errorf("Segment [(%d,%d),(%d,%d)] should intersect [(%d,%d),(%d,%d)]", vertical.a.X, vertical.a.Y, vertical.b.X, vertical.b.Y, horizontal.a.X, horizontal.a.Y, horizontal.b.X, horizontal.b.Y)
	}

}
