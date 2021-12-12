# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"
TEST2_FILE = "./test2.txt"
TEST3_FILE = "./test3.txt"

START_STATE = "start"
END_STATE = "end"


# -------- INPUT PROCESSING ------------

def read_line(line):
    line = line.strip().split("-")
    return line[0], line[1]


def read_file(path):
    with open(path, "r") as file:
        return [read_line(line) for line in file.readlines()]


# -------- LOGIC & FUN  --------------

def add_edge(graph, edge):
    graph[edge[0]].add(edge[1])
    graph[edge[1]].add(edge[0])


def init_graph(states):
    values = [set() for _ in states]
    return dict(zip(states, values))


def make_graph(data):
    states = set()
    for path in data:
        states.add(path[0])
        states.add(path[1])
    graph = init_graph(states)
    for edge in data:
        add_edge(graph, edge)
    return graph


def compute_forbidden_states(visited_lower_states, part_2=False):
    forbidden_states = set()

    # Check if we already visited a small cave twice
    small_cave_visited_twice = False
    if part_2:
        for val in visited_lower_states.values():
            if val >= 2:
                small_cave_visited_twice = True
                break

    for state, number_of_visits in visited_lower_states.items():
        if (state == START_STATE or state == END_STATE) and number_of_visits >= 1:
            forbidden_states.add(state)
        elif not part_2 and number_of_visits >= 1:
            forbidden_states.add(state)
        # Part 2: If we did not visit a small cave twice, then every move to small caves are allowed
        elif part_2 and small_cave_visited_twice and number_of_visits >= 1:
            forbidden_states.add(state)

    return forbidden_states


def count_path_to_end(graph, current_state, visited_lower_states, part_2=False):
    if current_state == END_STATE:
        return 1
    if current_state.islower():
        visited_lower_states[current_state] += 1

    forbidden_states = compute_forbidden_states(visited_lower_states, part_2)
    possible_next_states = graph[current_state] - forbidden_states

    return sum([count_path_to_end(graph, next_state, visited_lower_states.copy(), part_2) for next_state in
                possible_next_states])


def compute_number_of_paths(data, part_2=False):
    graph = make_graph(data)
    visited_lower_states = dict.fromkeys([key for key in graph.keys() if key.islower()], 0)
    return count_path_to_end(graph, START_STATE, visited_lower_states, part_2)


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assert_equals(test_name, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {test_name} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {test_name} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1(data, data2, data3):
    print("Test 1")
    assert_equals("File 1", 10, compute_number_of_paths(data, False))
    assert_equals("File 2", 19, compute_number_of_paths(data2, False))
    assert_equals("File 3", 226, compute_number_of_paths(data3, False))


def test2(data, data2, data3):
    print("Test 2")
    assert_equals("File 1", 36, compute_number_of_paths(data, True))
    assert_equals("File 2", 103, compute_number_of_paths(data2, True))
    assert_equals("File 3", 3509, compute_number_of_paths(data3, True))


# ---- CHALLENGE SOLVER FUNCTION -------

def solve1(data):
    print("Solve 1")
    print(f"Res: {compute_number_of_paths(data)}")


def solve2(data):
    print("Solve 2")
    print(f"Res: {compute_number_of_paths(data, True)}")


def part1(data, test_data, test_data2, test_data3):
    test1(test_data, test_data2, test_data3)
    solve1(data)


def part2(data, test_data, test_data2, test_data3):
    test2(test_data, test_data2, test_data3)
    solve2(data)


if __name__ == '__main__':
    test_input = read_file(TEST_FILE)
    test_input2 = read_file(TEST2_FILE)
    test_input3 = read_file(TEST3_FILE)
    challenge_input = read_file(INPUT_FILE)
    part1(challenge_input, test_input, test_input2, test_input3)
    part2(challenge_input, test_input, test_input2, test_input3)
