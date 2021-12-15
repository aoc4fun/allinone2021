# Files
import math

INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------

# Read the text file
def read_file(path):
    with open(path, "r") as file:
        lines = file.readlines()
        polymer_template = lines[0].strip()
        insertion_rules = [line.strip().split(" -> ") for line in lines[2:]]

    return polymer_template, insertion_rules


# -------- LOGIC & FUN  --------------

def find_index(polymer, sequence_to_find):
    indexes = []
    sequence_index = polymer.find(sequence_to_find)
    while sequence_index != -1:
        indexes.append(sequence_index + 1)
        sequence_index = polymer.find(sequence_to_find, sequence_index + 1)
    return indexes


def insert_char(polymer, insertions):
    # Sort the list by insert index ( reversed ) in order to avoid impact on index during insertions
    insertions = sorted(insertions, key=lambda x: -x[0])
    for insertion in insertions:
        polymer = polymer[:insertion[0]] + insertion[1] + polymer[insertion[0]:]
    return polymer


def perform_step(polymer, insertion_rules):
    insertion_buffer = []
    # Compute the list of char to insert
    for rule in insertion_rules:
        insert_indexes = find_index(polymer, rule[0])
        insertion_buffer += [(index, rule[1]) for index in insert_indexes]
    # Insert all the char
    polymer = insert_char(polymer, insertion_buffer)
    return polymer


def process_polymer(polymer, insertion_rules, nb_steps):
    for i in range(nb_steps):
        print(f"Step - {i}")
        polymer = perform_step(polymer, insertion_rules)
    return polymer


def compute_solution(polymer):
    min_val = math.inf
    max_val = -math.inf
    for char in set(polymer):
        occurrences = polymer.count(char)
        if occurrences < min_val:
            min_val = occurrences
        if occurrences > max_val:
            max_val = occurrences

    return max_val - min_val


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assert_equals(test_name, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {test_name} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {test_name} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1(polymer, insertion_rules):
    print("Test 1")
    assert_equals("Step 1", "NCNBCHB", process_polymer(polymer, insertion_rules, 1))
    assert_equals("Step 2", "NBCCNBBBCBHCB", process_polymer(polymer, insertion_rules, 2))
    assert_equals("Step 3", "NBBBCNCCNBBNBNBBCHBHHBCHB", process_polymer(polymer, insertion_rules, 3))
    assert_equals("Step 4", "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB",
                  process_polymer(polymer, insertion_rules, 4))
    assert_equals("Step 5 res", 1588, compute_solution(process_polymer(polymer, insertion_rules, 10)))


def test2(polymer, insertion_rules):
    print("Test 2")
    assert_equals("Step 40 res", 2188189693529, compute_solution(process_polymer(polymer, insertion_rules, 40)))


# ---- CHALLENGE SOLVER FUNCTION -------

def solve1(polymer, insertion_rules):
    print("Solve 1")
    print(f"Res {compute_solution(process_polymer(polymer, insertion_rules, 10))}")


def solve2(polymer, insertion_rules):
    print("Solve 2")
    print(f"Res {compute_solution(process_polymer(polymer, insertion_rules, 40))}")


def part1(data, test_data):
    test1(*test_data)
    solve1(*data)


def part2(data, test_data):
    test2(*test_data)
    solve2(*data)


if __name__ == '__main__':
    test_input = read_file(TEST_FILE)
    challenge_input = read_file(INPUT_FILE)
    part1(challenge_input, test_input)
    # part2(challenge_input, test_input)
