# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"
MAP_TEST_FILE1 = "./testMap1.txt"
MAP_TEST_FILE2 = "./testMap2.txt"


# -------- INPUT PROCESSING ------------

# Read the text file to get the list of [lineStart, lineEnd] elements
def read_file(path):
    with open(path, "r") as file:
        return [[[int(x) for x in point.split(",")] for point in line.split("->")] for line in file.readlines()]


def read_test_map_file(path):
    with open(path, "r") as file:
        return "".join(file.readlines())


# -------- LOGIC & FUN  --------------

def is_horizonal(line):
    return line[0][1] == line[1][1]


def is_vertical(line):
    return


# Init a vent map by checking max indexes used in the input
def init_vent_map(lines):
    max_x = max([max(line[0][0], line[1][0]) for line in lines])
    max_y = max([max(line[0][1], line[1][1]) for line in lines])
    return [[0 for _ in range(max_x + 1)] for _ in range(max_y + 1)]


# Return min and max for x and y values of the given line
def getMinMax(line):
    min_x = min(line[0][0], line[1][0])
    max_x = max(line[0][0], line[1][0])
    min_y = min(line[0][1], line[1][1])
    max_y = max(line[0][1], line[1][1])
    return min_x, max_x, min_y, max_y


def add_line_to_map(vent_map, line, check_diagonals=False):
    (minX, maxX, minY, maxY) = getMinMax(line)
    # Horizontal
    # It is a horizontal line if x does not change
    if is_horizonal(line):
        y = line[0][1]
        for x in range(minX, maxX + 1):
            vent_map[y][x] += 1
    # Vertical
    # It is a vertical line if y does not change
    elif line[0][0] == line[1][0]:
        x = line[0][0]
        for y in range(minY, maxY + 1):
            vent_map[y][x] += 1
    # Diagonal
    # It is a 45 degrees diagonal if there is as much delta from Horizontal than vertical
    elif check_diagonals and maxX - minX == maxY - minY:
        # Compute points of the diagonal
        range_x = range(minX, maxX + 1)
        range_y = range(minY, maxY + 1)
        # As we used min, max values to create iterator, x and y can be "unrelated"
        # In order to have a correct line, We may need to reverse some ranges.
        # Example: (0,8) -> (8,0) would be seen as (0,0)->(8,8) if we don't reverse the y range
        if line[0][0] > line[1][0]:
            range_x = reversed(range_x)
        if line[0][1] > line[1][1]:
            range_y = reversed(range_y)
        for (x, y) in zip(range_x, range_y):
            vent_map[y][x] += 1


def compute_vent_map(lines, check_diagonals=False):
    vent_map = init_vent_map(lines)
    for line in lines:
        add_line_to_map(vent_map, line, check_diagonals)
    return vent_map


# Compute string representation of the map ( With . rather than )
def vent_map_as_string(vent_map):
    return "\n".join(["".join([str(i) if i != 0 else "." for i in line]) for line in vent_map])


def count_overlap(vent_map):
    return sum([sum([True if elt >= 2 else False for elt in row]) for row in vent_map])


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assert_equals(test_name, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {test_name} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {test_name} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1(vent_lines):
    print("Test 1")

    vent_map = compute_vent_map(vent_lines, False)
    res = count_overlap(vent_map)

    test_map = read_test_map_file(MAP_TEST_FILE1)
    string_map = vent_map_as_string(vent_map)

    assert_equals("Overlap", 5, res)
    assert_equals("Map", test_map, string_map)


def test2(vent_lines):
    print("Test 2")

    vent_map = compute_vent_map(vent_lines, True)
    res = count_overlap(vent_map)

    test_map = read_test_map_file(MAP_TEST_FILE2)
    string_map = vent_map_as_string(vent_map)

    assert_equals("Overlap", 12, res)
    assert_equals("Map", test_map, string_map)


def test():
    vent_lines = read_file(TEST_FILE)
    test1(vent_lines)
    test2(vent_lines)


# ---- CHALLENGE SOLVER FUNCTION -------

def part1(data):
    vent_map = compute_vent_map(data, False)
    res = count_overlap(vent_map)
    print("Part 1: ")
    print(f"\tNumber of overlap is: {res}")


def part2(data):
    vent_map = compute_vent_map(data, True)
    res = count_overlap(vent_map)
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
