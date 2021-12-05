# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"
MAP_TEST_FILE1 = "./testMap1.txt"
MAP_TEST_FILE2 = "./testMap2.txt"


# -------- INPUT PROCESSING ------------

# Read the text file to get a the list of [lineStart, lineEnd] elements
def read_file(path):
    with open(path, "r") as file:
        return [[[int(x) for x in point.split(",")] for point in line.split("->")] for line in file.readlines()]


def read_test_map_file(path):
    with open(path, "r") as file:
        return "".join(file.readlines())


# -------- LOGIC & FUN  --------------

def isHorizonal(line):
    return line[0][1] == line[1][1]


def isVertical(line):
    return


# Init a vent map by checking max indexes used in the input
def initVentMap(lines):
    maxX = max([max(line[0][0], line[1][0]) for line in lines])
    maxY = max([max(line[0][1], line[1][1]) for line in lines])
    return [[0 for _ in range(maxX + 1)] for _ in range(maxY + 1)]


# Return min and max for x and y values of the given line
def getMinMax(line):
    minX = min(line[0][0], line[1][0])
    maxX = max(line[0][0], line[1][0])
    minY = min(line[0][1], line[1][1])
    maxY = max(line[0][1], line[1][1])
    return (minX, maxX, minY, maxY)


def addLineToMap(ventMap, line, checkDiagonals=False):
    (minX, maxX, minY, maxY) = getMinMax(line)
    # Horizonal
    # It is an horizontal line if x does not change
    if isHorizonal(line):
        y = line[0][1]
        for x in range(minX, maxX + 1):
            ventMap[y][x] += 1
    # Vertical
    # It is a vertical line if y does not change
    elif line[0][0] == line[1][0]:
        x = line[0][0]
        for y in range(minY, maxY + 1):
            ventMap[y][x] += 1
    # Diagonal
    # It is a 45 degrees diag if there is as much delta from Horizontal than vertical
    elif checkDiagonals and maxX - minX == maxY - minY:
        # Compute points of the diag
        rangeX = range(minX, maxX + 1)
        rangeY = range(minY, maxY + 1)
        # As we used min, max values to create iterator, x and y can be "unrelated"
        # In order to have a correct line, We may need to reverse some ranges.
        # Exemple: (0,8) -> (8,0) would be seen as (0,0)->(8,8) if we don't reverse the y range
        if (line[0][0] > line[1][0]):
            rangeX = reversed(rangeX)
        if (line[0][1] > line[1][1]):
            rangeY = reversed(rangeY)
        for (x, y) in zip(rangeX, rangeY):
            ventMap[y][x] += 1


def computeVentMap(lines, checkDiagonals=False):
    ventMap = initVentMap(lines)
    for line in lines:
        addLineToMap(ventMap, line, checkDiagonals)
    return ventMap


# Compute string representation of the map ( With . rather than )
def ventMapAsString(ventMap):
    return "\n".join(["".join([str(i) if i != 0 else "." for i in line]) for line in ventMap])


def countOverlap(ventMap):
    return sum([sum([True if elt >= 2 else False for elt in row]) for row in ventMap])


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assertEquals(testName, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {testName} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {testName} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1(ventLines):
    print("Test 1")

    ventMap = computeVentMap(ventLines, False)
    res = countOverlap(ventMap)

    testMap = read_test_map_file(MAP_TEST_FILE1)
    stringMap = ventMapAsString(ventMap)

    assertEquals("Overlap", 5, res)
    assertEquals("Map", testMap, stringMap)


def test2(ventLines):
    print("Test 2")

    ventMap = computeVentMap(ventLines, True)
    res = countOverlap(ventMap)

    testMap = read_test_map_file(MAP_TEST_FILE2)
    stringMap = ventMapAsString(ventMap)

    assertEquals("Overlap", 12, res)
    assertEquals("Map", testMap, stringMap)


def test():
    ventLines = read_file(TEST_FILE)
    test1(ventLines)
    test2(ventLines)


# ---- CHALLENGE SOLVER FUNCTION -------

def part1(data):
    ventMap = computeVentMap(data, False)
    res = countOverlap(ventMap)
    print("Part 1: ")
    print(f"\tNumber of overlap is: {res}")


def part2(data):
    ventMap = computeVentMap(data, True)
    res = countOverlap(ventMap)
    print("Part 2: ")
    print(f"\tNumber of overlap is: {res}")


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
