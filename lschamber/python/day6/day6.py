# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------

# Read the text file to get a the list of [lineStart, lineEnd] elements
def read_file(path):
    with open(path, "r") as file:
        return [int(fish) for fish in file.readline().strip().split(",")]


# -------- LOGIC & FUN  --------------

# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assertEquals(testName, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {testName} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {testName} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def computeDay(data, nbDays):
    # Make a list of 9 indexes where fishList[0] are all fishes with internal timer of 0 and fishList[8]are all fishes with internal timer of 8
    fishList = [data.count(i) for i in range(9)]

    for _ in range(1, nbDays + 1):
        # Recompute the list for each day. The 6 first elements just need to be moved 1 index left.
        # The 6 is the sum of fish with timer of 7 and timer of 0 (Gave birth)
        # The 7 is the number of fish with timer of 8
        # The 8 if the number of newborn ( So Index 0 )
        fishList = fishList[1:7] + [fishList[0] + fishList[7], fishList[8], fishList[0]]

    return sum(fishList)


def test1(data):
    print("Test 1")

    assertEquals("Number of fish after 18 days", 26, computeDay(data, 18))
    assertEquals("Number of fish after 80 days", 5934, computeDay(data, 80))


def test2(data):
    print("Test 2")
    assertEquals("Number of fish after 256 days", 26984457539, computeDay(data, 256))


def test():
    data = read_file(TEST_FILE)
    test1(data)
    test2(data)


# ---- CHALLENGE SOLVER FUNCTION -------

def part1(data):
    print("Part 1")
    print(f"Number of fish after 80 days: {computeDay(data, 80)}")


def part2(data):
    print("Part 2")
    print(f"Number of fish after 256 days: {computeDay(data, 256)}")


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
