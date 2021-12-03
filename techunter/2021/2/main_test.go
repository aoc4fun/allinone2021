package main

import "testing"

const (
	SecondCaseResult = 5
)

func TestFirstCase(t *testing.T) {

	horizontalPosition, depth := FirstCase(".test")
	if horizontalPosition != 15 || depth != 10 {
		t.Errorf("First case was incorrect, got %d,%d want %d,%d", horizontalPosition, depth, 15, 10)
	}

}

func TestSecondCase(t *testing.T) {
	horizontalPosition, depth := SecondCase(".test")
	if horizontalPosition != 15 || depth != 60 {
		t.Errorf("First case was incorrect, got %d,%d want %d,%d", horizontalPosition, depth, 15, 60)
	}
}
