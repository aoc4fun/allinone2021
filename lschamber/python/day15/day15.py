# Files
import math

INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"
TEST_2_MAP_FILE = "./test_2_full_map.txt"


# -------- INPUT PROCESSING ------------


# Read the text file
def read_file(path):
    with open(path, "r") as file:
        return [[int(i) for i in list(line.strip())] for line in file.readlines()]


# -------- LOGIC & FUN  --------------
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


def select_next_node(next_nodes):
    min_key = (-1, -1)
    min_val = math.inf
    for key, value in next_nodes.items():
        if value < min_val:
            min_val = value
            min_key = key
    del next_nodes[min_key]
    return min_key, min_val


def update_next_nodes_to_process(next_nodes, processed_nodes, current_node, current_node_value, matrix):
    max_x = len(matrix[0])
    max_y = len(matrix)
    for neighbour in neighbours(current_node[0], current_node[1], max_x, max_y):
        if neighbour not in processed_nodes:
            neighbour_val = current_node_value + matrix[neighbour[1]][neighbour[0]]
            if neighbour in next_nodes.keys() and next_nodes[neighbour] > neighbour_val \
                    or neighbour not in next_nodes.keys():
                next_nodes[neighbour] = neighbour_val


def dijkstra_on_matrix(matrix):
    processed_nodes = set()
    next_nodes = {(0, 0): 0}
    last_node = (len(matrix[0]) - 1, len(matrix) - 1)
    while len(next_nodes) > 0:
        # Take the current element to process ( Lowest of the next nodes )
        current_node, current_node_value = select_next_node(next_nodes)
        if current_node == last_node:
            return current_node_value
        processed_nodes.add(current_node)

        update_next_nodes_to_process(next_nodes, processed_nodes, current_node, current_node_value, matrix)


# I should have use numpy.repeat here, but I won't use numpy on this AoC :(
def compute_full_map(matrix, x_repeat=5, y_repeat=5):
    # Note: Values are repeating, I could pre-compute the matrix needed in the full map
    new_matrix = []
    for y in range(y_repeat):
        for line in matrix:
            new_line = []
            for x in range(x_repeat):
                new_line += [elt + y + x if elt + y + x <= 9 else (elt + y + x) % 10 + 1 for elt in line]
            new_matrix.append(new_line)
    return new_matrix


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
    assert_equals("Total Risk: ", 40, dijkstra_on_matrix(data))


def test2(data, expected_new_map):
    print("Test 2")
    full_map = compute_full_map(data)
    full_map_success = assert_equals("New map", expected_new_map, full_map)
    if not full_map_success:
        for i, line in enumerate(full_map):
            assert_equals(f"New map - Line {i}", expected_new_map[i], line)
    assert_equals("Total Risk on expected map: ", 315, dijkstra_on_matrix(expected_new_map))
    assert_equals("Total Risk on computed map: ", 315, dijkstra_on_matrix(full_map))

    # ---- CHALLENGE SOLVER FUNCTION -------


def solve1(data):
    print("Solve 1")
    print(f"Res : {dijkstra_on_matrix(data)}")


def solve2(data):
    print("Solve 2")
    print(f"Res : {dijkstra_on_matrix(compute_full_map(data))}")


def part1(data, test_data):
    test1(test_data)
    solve1(data)


def part2(data, test_data):
    test_2_map = read_file(TEST_2_MAP_FILE)
    test2(test_data, test_2_map)
    solve2(data)


if __name__ == '__main__':
    test_input = read_file(TEST_FILE)
    challenge_input = read_file(INPUT_FILE)
    part1(challenge_input, test_input)
    part2(challenge_input, test_input)
