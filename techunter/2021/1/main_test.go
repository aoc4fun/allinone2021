package main

import "testing"

func TestFirstCase(t *testing.T) {

	ret := FirstCase("./2021/1/.test")
	if ret != 7 {
		t.Errorf("First case was incorrect, got %d want %d", ret, 7)
	}

}
