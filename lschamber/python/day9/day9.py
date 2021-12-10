from functools import reduce

# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------

# Read the text file to get a the 2D list
def read_file(path):
    with open(path, "r") as file:
        return [[int(i) for i in list(line.strip())] for line in file.readlines()]


# -------- LOGIC & FUN  --------------

# Find more pythonic way
def neighbours(i, j, length, height):
    adjacent_indices = []
    if i > 0:
        adjacent_indices.append((i - 1, j))
    if j > 0:
        adjacent_indices.append((i, j - 1))
    if i + 1 < length:
        adjacent_indices.append((i + 1, j))
    if j + 1 < height:
        adjacent_indices.append((i, j + 1))
    return adjacent_indices


def findLowPoints(heightmap):
    lowPoints = []
    mapX = len(heightmap[0])
    mapY = len(heightmap)
    for y in range(mapY):
        for x in range(mapX):
            neightboursHeights = [heightmap[y2][x2] for x2, y2 in neighbours(x, y, mapX, mapY)]
            height = heightmap[y][x]
            if height < min(neightboursHeights):
                lowPoints += [height]
    return lowPoints


def riskLevels(lowPoints):
    return [i + 1 for i in lowPoints]


def findLowPointsCoord(heightmap):
    lowPoints = []
    mapX = len(heightmap[0])
    mapY = len(heightmap)
    for y in range(mapY):
        for x in range(mapX):
            neightboursHeights = [heightmap[y2][x2] for x2, y2 in neighbours(x, y, mapX, mapY)]
            height = heightmap[y][x]
            if height < min(neightboursHeights):
                lowPoints.append((x, y))
    return lowPoints


def findBasin(lowestPoint, heightmap):
    mapX = len(heightmap[0])
    mapY = len(heightmap)

    basin = set()
    basin.add(lowestPoint)
    pointToProcess = basin.copy()
    while len(pointToProcess) > 0:
        currentPoint = pointToProcess.pop()
        currentPointHeight = heightmap[currentPoint[1]][currentPoint[0]]
        for x, y in neighbours(currentPoint[0], currentPoint[1], mapX, mapY):
            if heightmap[y][x] > currentPointHeight and heightmap[y][x] != 9:
                basin.add((x, y))
                pointToProcess.add((x, y))
    return list(basin)


def multiplyThreeMaxItems(values):
    return reduce(lambda x, y: x * y, sorted(values, reverse=True)[0:3])


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assertEquals(testName, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {testName} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {testName} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1(data):
    print("Test 1")
    lowPoints = findLowPoints(data)
    assertEquals("Low points\t", [1, 0, 5, 5], lowPoints)
    assertEquals("Risk level\t", [2, 1, 6, 6], riskLevels(lowPoints))
    assertEquals("Sum Risk level", 15, sum(riskLevels(lowPoints)))


def test2(data):
    print("Test 2")

    basins = list(map(lambda point: findBasin(point, data), findLowPointsCoord(data)))
    basinsValues = list(map(lambda basin: [data[point[1]][point[0]] for point in basin], basins))
    basinLength = list(map(lambda basin: len(basin), basins))

    assertEquals("Basins \t\t",
                 [[(1, 0), (0, 1), (0, 0)], [(9, 0), (8, 1), (6, 1), (7, 0), (9, 2), (8, 0), (5, 0), (6, 0), (9, 1)],
                  [(1, 2), (2, 1), (4, 3), (3, 1), (4, 1), (0, 3), (4, 2), (1, 4), (2, 3), (3, 3), (2, 2), (3, 2),
                   (1, 3), (5, 2)], [(7, 4), (8, 4), (5, 4), (6, 4), (7, 3), (8, 3), (7, 2), (6, 3), (9, 4)]], basins)
    assertEquals("Basins Values ", [[1, 3, 2], [0, 2, 4, 2, 2, 1, 4, 3, 1], [8, 8, 8, 7, 8, 8, 7, 8, 6, 7, 5, 6, 7, 8],
                                    [6, 7, 6, 5, 7, 8, 8, 6, 8]], basinsValues)
    assertEquals("Basins Length ", [3, 9, 14, 9], basinLength)

    drawBasins(basins, len(data[0]), len(data))
    assertEquals("Result\t\t", 1134, multiplyThreeMaxItems(basinLength))


def drawBasins(basins, mapX, mapY):
    basinsFlat = []
    for basin in basins:
        basinsFlat += basin
    for y in range(mapY):
        line = ""
        for x in range(mapX):
            line += "0" if (x, y) in basinsFlat else "X"
        print(line)


# ---- CHALLENGE SOLVER FUNCTION -------

def solve1(data):
    print("Solve 1")
    print(f"Res: {sum(riskLevels(findLowPoints(data)))}")


def solve2(data):
    print("Solve 2")
    basins = list(map(lambda point: findBasin(point, data), findLowPointsCoord(data)))
    # drawBasins(basins, len(data[0]), len(data))
    basinLength = list(map(lambda basin: len(basin), basins))
    print(f"Result is {multiplyThreeMaxItems(basinLength)}")


def part1(data, testData):
    test1(testData)
    solve1(data)


def part2(data, testData):
    test2(testData)
    solve2(data)


if __name__ == '__main__':
    testData = read_file(TEST_FILE)
    data = read_file(INPUT_FILE)
    part1(data, testData)
    part2(data, testData)
