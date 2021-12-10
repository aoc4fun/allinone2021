from itertools import accumulate

# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"

# Movement Keywords
FORWARD = "forward"
UP = "up"
DOWN = "down"


# -------- INPUT PROCESSING ------------

# Read a line of the text file in order to get a (KEYWORD, VALUE) tuples
def parse_line(line):
    split_line = line.split(" ")
    return split_line[0], int(split_line[1])


# Read the text file to get a list of tuples
def read_file(path):
    with open(path, "r") as file:
        return list(map(parse_line, file.readlines()))


# -------- LOGIC & FUN  --------------

# Parse the DOWN and UP keywords to produce a list with values to add in order to compute depth
def depth_mapping_function(line):
    if line[0] == DOWN:
        return line[1]
    elif line[0] == UP:
        return - line[1]
    else:
        return 0


# Compute the deep by summing the result of depthMapping and compute the depth by summing all forward entries
def compute_deep_and_position_1(data):
    deep = sum(map(depth_mapping_function, data))
    position = sum([line[1] for line in list(filter(lambda line: line[0] == FORWARD, data))])
    return deep, position


def compute_aim_list(data):
    return list(accumulate(map(depth_mapping_function, data), lambda x, y: x + y))


def compute_forward_list(data):
    return list(map(lambda x: x[1] if x[0] == FORWARD else 0, data))


def zip_forward_and_aim(data):
    return list(zip(compute_forward_list(data), compute_aim_list(data)))


def compute_deep_and_position2(data):
    forward_and_aim = zip_forward_and_aim(data)
    deep = 0
    position = 0
    for forward, aim in forward_and_aim:
        position += forward
        deep += forward * aim
    return deep, position


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assert_equals(test_name, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {test_name} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {test_name} \t|\t Expected '{expected}' but got '{actual}'")
        return False


# Compute and check depth and position using the given testedFunction and dataset
def abstract_test(name, tested_function, data, expected_depth, expected_position):
    print(f"Running {name}:")
    (depth, position) = tested_function(data)
    if assert_equals("Depth", expected_depth, depth) and assert_equals("Position", expected_position, position):
        print(f"{name} OK")
    else:
        print(f"{name} KO")


def test1(test_data):
    abstract_test("Test 1", compute_deep_and_position_1, test_data, 10, 15)


def test2(test_data):
    abstract_test("Test 2", compute_deep_and_position2, test_data, 60, 15)


def test():
    test_data = read_file(TEST_FILE)
    test1(test_data)
    test2(test_data)


# ---- CHALLENGE SOLVER FUNCTION -------

def print_answer(depth, position):
    print(f"\tDepth is {depth}")
    print(f"\tPosition is {position}")
    print(f"\tAnswer is {depth * position}.")


def part1(data):
    print("Running part 1:")
    print_answer(*compute_deep_and_position_1(data))


def part2(data):
    print("Running part 2:")
    print_answer(*compute_deep_and_position2(data))


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
