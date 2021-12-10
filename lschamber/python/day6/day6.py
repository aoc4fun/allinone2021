# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------

# Read the text file to get the list of fish elements
def read_file(path):
    with open(path, "r") as file:
        return [int(fish) for fish in file.readline().strip().split(",")]


# -------- LOGIC & FUN  --------------
def compute_day(data, nb_days):
    # Make a list of 9 indexes where fish_list[0] are all fishes with internal timer of 0 and
    # fish_list[8]are all fishes with internal timer of 8
    fish_list = [data.count(i) for i in range(9)]

    for _ in range(1, nb_days + 1):
        # Recompute the list for each day. The 6 first elements just need to be moved 1 index left.
        # The 6 is the sum of fish with timer of 7 and timer of 0 (Gave birth)
        # The 7 is the number of fish with timer of 8
        # The 8 if the number of newborn ( So Index 0 )
        fish_list = fish_list[1:7] + [fish_list[0] + fish_list[7], fish_list[8], fish_list[0]]

    return sum(fish_list)


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assert_equals(test_name, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {test_name} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {test_name} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1(data):
    print("Test 1")

    assert_equals("Number of fish after 18 days", 26, compute_day(data, 18))
    assert_equals("Number of fish after 80 days", 5934, compute_day(data, 80))


def test2(data):
    print("Test 2")
    assert_equals("Number of fish after 256 days", 26984457539, compute_day(data, 256))


def test():
    data = read_file(TEST_FILE)
    test1(data)
    test2(data)


# ---- CHALLENGE SOLVER FUNCTION -------

def part1(data):
    print("Part 1")
    print(f"Number of fish after 80 days: {compute_day(data, 80)}")


def part2(data):
    print("Part 2")
    print(f"Number of fish after 256 days: {compute_day(data, 256)}")


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
