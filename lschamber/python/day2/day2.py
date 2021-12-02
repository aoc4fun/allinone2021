from functools import reduce
from itertools import accumulate

# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"

# Movement Keywords
FORWARD = "forward"
UP = "up"
DOWN = "down"


# -------- INPUT PROCESSING ------------

# Read a line of the textfile in order to get a (KEYWORD, VALUE) tuples
def parse_line(input):
    split_line = input.split(" ")
    return (split_line[0], int(split_line[1]))


# Read the text file to get a list of tuples
def read_file(path):
    with open(path, "r") as file:
        return list(map(parse_line, file.readlines()))


# -------- LOGIC & FUN  --------------

# Parse the DOWN and UP keywords to produce a list with values to add in order to compute depth
def deepthMappingFunction(line):
    if line[0] == DOWN:
        return line[1]
    elif line[0] == UP:
        return - line[1]
    else:
        return 0


# Compute the deep by summing the result of depthMapping and compute the depth by summing all forward entries
def computeDeepAndPosition1(data):
    deep = sum(map(deepthMappingFunction, data))
    position = sum([line[1] for line in list(filter(lambda line: line[0] == FORWARD, data))])
    return (deep, position)


def computeAimList(data):
    return list(accumulate(map(deepthMappingFunction, data), lambda x, y: x + y))


def computeForwardList(data):
    return list(map(lambda x: x[1] if x[0] == FORWARD else 0, data))


def zipForwardAndAim(data):
    return list(zip(computeForwardList(data), computeAimList(data)))


def computeDeepAndPosition2(data):
    forwardAndAim = zipForwardAndAim(data)
    deep = 0
    position = 0
    for forward, aim in forwardAndAim:
        position += forward
        deep += forward * aim
    return (deep, position)


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assertEquals(testName, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {testName} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {testName} \t|\t Expected '{expected}' but got '{actual}'")
        return False


# Compute and check depth and postion using the given testedFunction and dataset
def abstractTest(name, testedFunction, data, expectedDepth, expectedPosition):
    print(f"Running {name}:")
    (depth, position) = testedFunction(data)
    if assertEquals("Depth", expectedDepth, depth) and assertEquals("Position", expectedPosition, position):
        print(f"{name} OK")
    else:
        print(f"{name} KO")


def test1(testData):
    abstractTest("Test 1", computeDeepAndPosition1, testData, 10, 15)


def test2(testData):
    abstractTest("Test 2", computeDeepAndPosition2, testData, 60, 15)


def test():
    testData = read_file(TEST_FILE)
    test1(testData)
    test2(testData)


# ---- CHALLENGE SOLVER FUNCTION -------

def print_answer(depth, position):
    print(f"\tDepth is {depth}")
    print(f"\tPosition is {position}")
    print(f"\tAnswer is {depth * position}.")


def part1(data):
    print("Running part 1:")
    print_answer(*computeDeepAndPosition1(data))


def part2(data):
    print("Running part 2:")
    print_answer(*computeDeepAndPosition2(data))


def solve():
    data = read_file(INPUT_FILE)
    part1(data)
    part2(data)


def main():
    test()
    print("-------------")
    solve()


if __name__ == '__main__':
    main()
