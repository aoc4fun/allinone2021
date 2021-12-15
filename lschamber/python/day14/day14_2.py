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

# Input: rules are  [(pair, newchar)].
# Output: 2 dicts
#   -   new_rule[pair] = (firstEltOfPair+newchar, newchar+lastEltOfPair)
#   -   new_rule_char[pair] = newChar
def transform_insertion_rules(insertion_rules):
    new_rules = dict()
    new_rules_char = dict()
    for rule in insertion_rules:
        new_rules_char[rule[0]] = rule[1]
        new_rules[rule[0]] = (rule[0][0] + rule[1], rule[1] + rule[0][1])
    return new_rules_char, new_rules


def empty_insertion_rules_occurrence_dict(insertion_rules):
    rules_occurrences = dict()
    for rule in insertion_rules.keys():
        rules_occurrences[rule] = 0
    return rules_occurrences


def init_insertion_rules_occurrence_dict(polymer, insertion_rules):
    rules_occurrences = empty_insertion_rules_occurrence_dict(insertion_rules)

    for i in range(len(polymer) - 1):
        pair = polymer[i] + polymer[i + 1]
        if pair in rules_occurrences.keys():
            rules_occurrences[pair] += 1
    return rules_occurrences


def init_nb_char(polymer):
    char_dict = dict()
    for char in polymer:
        if char in char_dict.keys():
            char_dict[char] += 1
        else:
            char_dict[char] = 1
    return char_dict


def update_nb_char(char_dict, char, occurences):
    if char in char_dict.keys():
        char_dict[char] += occurences
    else:
        char_dict[char] = occurences
    return char_dict


def next_iteration(insertion_rules_occurrences, nb_char, insertion_rules, insertion_rules_couples):
    new_insertion_rules_occurrences = empty_insertion_rules_occurrence_dict(insertion_rules_couples)
    for rule, occurrences in insertion_rules_occurrences.items():
        if occurrences > 0:
            nb_char = update_nb_char(nb_char, insertion_rules[rule], occurrences)
            for rule_output in insertion_rules_couples[rule]:
                if rule_output in new_insertion_rules_occurrences.keys():
                    new_insertion_rules_occurrences[rule_output] += occurrences

    return new_insertion_rules_occurrences, nb_char


def compute_res(nb_of_char):
    min_val = math.inf
    max_val = -math.inf
    for val in nb_of_char.values():
        if val < min_val:
            min_val = val
        if val > max_val:
            max_val = val
    return max_val - min_val


def process_polymer(polymer, insertion_rules, iterations):
    insertion_rules, insertion_rules_couples = transform_insertion_rules(insertion_rules)
    insertion_rules_occurrences = init_insertion_rules_occurrence_dict(polymer, insertion_rules_couples)
    nb_of_char = init_nb_char(polymer)

    for i in range(iterations):
        insertion_rules_occurrences, nb_of_char = next_iteration(insertion_rules_occurrences, nb_of_char,
                                                                 insertion_rules, insertion_rules_couples)

    return compute_res(nb_of_char)


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
    assert_equals("Step 5 res", 1588, process_polymer(polymer, insertion_rules, 10))


def test2(polymer, insertion_rules):
    print("Test 2")
    assert_equals("Step 40 res", 2188189693529, process_polymer(polymer, insertion_rules, 40))


# ---- CHALLENGE SOLVER FUNCTION -------

def solve1(polymer, insertion_rules):
    print("Solve 1")
    print(f"Res {process_polymer(polymer, insertion_rules, 10)}")


def solve2(polymer, insertion_rules):
    print("Solve 2")
    print(f"Res {process_polymer(polymer, insertion_rules, 40)}")


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
    part2(challenge_input, test_input)
