import statistics

# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------

# Read the text file to get the list of crabs elements
def read_file(path):
    with open(path, "r") as file:
        return [int(crabs) for crabs in file.readline().strip().split(",")]


# -------- LOGIC & FUN  --------------

# With consuming rate of 1, best position is the median.
# I used map to compute distance between optimal and crab, then sum the distances to compute the fuel
def compute_fuel_need1(crabs):
    position = statistics.median(crabs)
    fuel_used = sum(map(lambda x: abs(x - position), crabs))
    return position, fuel_used


# Compute a list with fuel consumption for movement. fuel[5] represent the fuel used to move of 5 positions.
def compute_fuel_consumption(max_dist):
    return [sum(range(i)) for i in range(1, max_dist + 2)]


def compute_fuel_need2(crabs):
    # Precompute fuel consumption for distance to avoid multiple computations
    fuel_consumption = compute_fuel_consumption(max(crabs))

    # Compute the fuel used for each position possible (Can be optimised)
    fuel_used_for_each_position = [sum(map(lambda x: fuel_consumption[abs(x - current_position)], crabs)) for
                                   current_position in range(max(crabs) + 1)]
    # Retrieve the min of the list (optimal fuel usage)
    optimal_fuel_consumption = min(fuel_used_for_each_position)
    # Optimal crabs position is index where min occurs
    optimal_position = fuel_used_for_each_position.index(optimal_fuel_consumption)

    return optimal_position, optimal_fuel_consumption

    # -------- TEST ------------


# Check that expected and actual values are equals. Display error and return false if not.
def assert_equals(test_name, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {test_name} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {test_name} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1(crabs):
    print("Test 1")
    (position, fuelUsed) = compute_fuel_need1(crabs)
    assert_equals("Position", 2, position)
    assert_equals("Fuel used", 37, fuelUsed)


def test2(crabs):
    print("Test 2")
    (position, fuelUsed) = compute_fuel_need2(crabs)
    assert_equals("Position", 5, position)
    assert_equals("Fuel used", 168, fuelUsed)


# ---- CHALLENGE SOLVER FUNCTION -------

def solve1(crabs):
    print("Solve 1")
    (position, fuelUsed) = compute_fuel_need1(crabs)
    print(f"Optimal position {position}")
    print(f"Optimal fuel {fuelUsed}")


def solve2(crabs):
    print("Solve 2")
    (position, fuelUsed) = compute_fuel_need2(crabs)
    print(f"Optimal position {position}")
    print(f"Optimal fuel {fuelUsed}")


def part1(crabs, test_crabs):
    test1(test_crabs)
    solve1(crabs)


def part2(crabs, test_crabs):
    test2(test_crabs)
    solve2(crabs)


if __name__ == '__main__':
    testData = read_file(TEST_FILE)
    data = read_file(INPUT_FILE)
    part1(data, testData)
    part2(data, testData)
