package main

import "testing"

const (
	FirstCaseResult  = 198
	SecondCaseResult = 230
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
		t.Errorf("First case was incorrect, got %d want %d", ret, SecondCaseResult)
	}

}
