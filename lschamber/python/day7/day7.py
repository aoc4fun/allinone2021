import statistics
import math

# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------

# Read the text file to get a the list of [lineStart, lineEnd] elements
def read_file(path):
    with open(path, "r") as file:
        return [int(crabs) for crabs in file.readline().strip().split(",")]


# -------- LOGIC & FUN  --------------

#With consuming rate of 1, best position is the median.
#I used map to compute distance between optimal and crab, then sum the distances to compute the fuel
def computeFuelNeed1(data):
    position = statistics.median(data)
    fuelUsed = sum(map(lambda x: abs(x - position), data))
    return position, fuelUsed

#Compute a list with fuel consumption for movement. fuel[5] represent the fuel used to move of 5 positions.
def computeFuelConsumption(maxDist):
    return [sum(range(i)) for i in range(1, maxDist+2)]


def computeFuelNeed2(data):
    #Precompute fuel consumption for distance to avoid multiple computations
    fuelConsumption = computeFuelConsumption(max(data))

    #Compute the fuel used for each position possible (May be optimised)
    fuelUsedForEachPosition = [sum(map(lambda x: fuelConsumption[abs(x - currentPosition)], data)) for currentPosition in range(max(data)+1)]
    #Retrieve the min of the list (optimal fuel usage)
    optimalFuelConsumption = min(fuelUsedForEachPosition)
    #Optimal crabs position is index where min occurs
    optimalPosition = fuelUsedForEachPosition.index(optimalFuelConsumption)


    return optimalPosition, optimalFuelConsumption

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
    (position, fuelUsed) = computeFuelNeed1(data)
    assertEquals("Position", 2, position)
    assertEquals("Fuel used", 37, fuelUsed)

def test2(data):
    print("Test 2")
    (position, fuelUsed) = computeFuelNeed2(data)
    assertEquals("Position", 5, position)
    assertEquals("Fuel used", 168, fuelUsed)



# ---- CHALLENGE SOLVER FUNCTION -------

def solve1(data):
    print("Solve 1")
    (position, fuelUsed) = computeFuelNeed1(data)
    print(f"Optimal position {position}")
    print(f"Optimal fuel {fuelUsed}")


def solve2(data):
    print("Solve 2")
    (position, fuelUsed) = computeFuelNeed2(data)
    print(f"Optimal position {position}")
    print(f"Optimal fuel {fuelUsed}")

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
