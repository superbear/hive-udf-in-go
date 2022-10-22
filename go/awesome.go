package main

import "C"
import (
	"strconv"
	"strings"
)

//export Add
func Add(a, b int) int {
	return a + b
}

//export ToUpper
func ToUpper(s string) *C.char {
	return C.CString(strings.ToUpper(s))
}

//export Atoi
func Atoi(s string) (int, *C.char) {
	v, err := strconv.Atoi(s)
	if err != nil {
		return 0, C.CString(err.Error())
	}
	return v, nil
}

func main() {}
