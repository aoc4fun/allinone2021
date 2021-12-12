from itertools import product

# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------

# Read the text file to get a 2D list
def read_file(path):
    with open(path, "r") as file:
        return [[int(c) for c in line.strip()] for line in file.readlines()]


# -------- LOGIC & FUN  --------------
def print_map(octopuses_map):
    for line in octopuses_map:
        print("".join([str(i) for i in line]))


def neighbours(i, j, length, height):
    return [point for point in product(range(i - 1, i + 2), range(j - 1, j + 2)) if
            0 <= point[0] < length and 0 <= point[1] < height and (point[0] != i or point[1] != j)]


def increment_map(octopuses_map):
    return [[elt + 1 for elt in line] for line in octopuses_map]


def octopuses_flash(octopuses_map):
    flashing_octopuses = set((i, j) for j, line in enumerate(octopuses_map) for i, octopus in enumerate(line) if
                             octopus > 9)
    flashed_octopuses = set()
    while len(flashing_octopuses) > 0:
        point = flashing_octopuses.pop()

        for neighbour in neighbours(point[0], point[1], 10, 10):
            octopuses_map[neighbour[1]][neighbour[0]] += 1
            neighbour_value = octopuses_map[neighbour[1]][neighbour[0]]
            if neighbour_value > 9 and neighbour not in flashed_octopuses:
                flashing_octopuses.add(neighbour)
        flashed_octopuses.add(point)

    octopuses_map = [[elt if elt < 10 else 0 for elt in line] for line in octopuses_map]
    return octopuses_map, len(flashed_octopuses)


def run_step(octopuses_map):
    octopuses_map = increment_map(octopuses_map)
    octopuses_map, nb_flash = octopuses_flash(octopuses_map)
    return octopuses_map, nb_flash


def count_flashes(octopuses_map, n):
    nb_flash_tot = 0
    for i in range(n):
        octopuses_map, nb_flash = run_step(octopuses_map)
        nb_flash_tot += nb_flash
    return nb_flash_tot


def are_octopus_sync(octopuses_map):
    for line in octopuses_map:
        for octopus in line:
            if octopus != 0:
                return False
    return True


def check_sync(octopuses_map):
    i = 0
    while True:
        i += 1
        octopuses_map, _ = run_step(octopuses_map)
        if are_octopus_sync(octopuses_map):
            return i


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
    assert_equals("NB Flash", 204, count_flashes(data, 10))
    assert_equals("NB Flash", 1656, count_flashes(data, 100))


def test2(data):
    print("Test 2")
    assert_equals("First sync", 195, check_sync(data))


# ---- CHALLENGE SOLVER FUNCTION -------

def solve1(data):
    print("Solve 1")
    print(f"Result : {count_flashes(data, 100)}")


def solve2(data):
    print("Solve 2")
    print(f"First sync {check_sync(data)}")


def part1(data, test_data):
    test1(test_data)
    solve1(data)


def part2(data, test_data):
    test2(test_data)
    solve2(data)


if __name__ == '__main__':
    test_input = read_file(TEST_FILE)
    challenge_input = read_file(INPUT_FILE)
    part1(challenge_input, test_input)
    part2(challenge_input, test_input)
