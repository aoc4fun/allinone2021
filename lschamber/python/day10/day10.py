# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"

OPEN_PARENTHESIS_TOKEN = "("
CLOSE_PARENTHESIS_TOKEN = ")"
OPEN_BRACE_TOKEN = "{"
CLOSE_BRACE_TOKEN = "}"
OPEN_BRACKET_TOKEN = "["
CLOSE_BRACKET_TOKEN = "]"
OPEN_CHEVRON_TOKEN = "<"
CLOSE_CHEVRON_TOKEN = ">"


# -------- INPUT PROCESSING ------------

# Read the text file to get a 2D list
def read_file(path):
    with open(path, "r") as file:
        return [list(line.strip()) for line in file.readlines()]


# -------- LOGIC & FUN  --------------

def compute_expected_last_token(line):
    first_token = line[0]
    if first_token == OPEN_PARENTHESIS_TOKEN:
        return CLOSE_PARENTHESIS_TOKEN
    elif first_token == OPEN_BRACE_TOKEN:
        return CLOSE_BRACE_TOKEN
    elif first_token == OPEN_BRACKET_TOKEN:
        return CLOSE_BRACKET_TOKEN
    elif first_token == OPEN_CHEVRON_TOKEN:
        return CLOSE_CHEVRON_TOKEN


def check_corruption(current_token, current_expected_number, token_index, last_index, expected_last_token):
    is_unexpected = current_expected_number < 1
    is_closing_too_early = current_expected_number == 1 and current_token == expected_last_token and token_index != last_index
    return is_unexpected or is_closing_too_early


def check_corrupted_in_line(line):
    expected_close_parenthesis = 0
    expected_close_bracket = 0
    expected_close_brace = 0
    expected_close_chevron = 0

    expected_last_token = compute_expected_last_token(line)
    last_token_index = len(line) - 1
    for i, token in enumerate(line):
        # IF opening token, increment count
        if token == OPEN_PARENTHESIS_TOKEN:
            expected_close_parenthesis += 1
        elif token == OPEN_BRACE_TOKEN:
            expected_close_brace += 1
        elif token == OPEN_BRACKET_TOKEN:
            expected_close_bracket += 1
        elif token == OPEN_CHEVRON_TOKEN:
            expected_close_chevron += 1

        # If closing token, check corruption and decrement count if correct
        elif token == CLOSE_PARENTHESIS_TOKEN:
            if check_corruption(token, expected_close_parenthesis, i, last_token_index, expected_last_token):
                return i, token
            expected_close_parenthesis -= 1
        elif token == CLOSE_BRACE_TOKEN:
            if check_corruption(token, expected_close_brace, i, last_token_index, expected_last_token):
                return i, token
            expected_close_brace -= 1
        elif token == CLOSE_BRACKET_TOKEN:
            if check_corruption(token, expected_close_bracket, i, last_token_index, expected_last_token):
                return i, token
            expected_close_bracket -= 1
        elif token == CLOSE_CHEVRON_TOKEN:
            if check_corruption(token, expected_close_chevron, i, last_token_index, expected_last_token):
                return i, token
            expected_close_chevron -= 1
    return None, None


def line_with_emphasis_on_index(line, index):
    line_copy = line.copy()
    line_copy.insert(index, "  ")
    line_copy.insert(index + 2, "  ")
    return "".join(line_copy)


def check_corrupted1(lines):
    illegal_elements = []
    for i, line in enumerate(lines):
        index, element = check_corrupted_in_line(line)
        if element is not None:
            line_with_emphasis_on_index(line, index)
            print(
                f"Line {i} - Unexpected token {element} at position {index} - {line_with_emphasis_on_index(line, index)}")
            illegal_elements += element

    return illegal_elements


def parse_chunk(line_iter, expected_close_element):
    current_token_index, current_token = next(line_iter, (None, None))
    while current_token is not None:
        #print(f"Parsing chunk - token {current_token_index} - {current_token}")

        error_index, error_token = (None, None)
        # If we got opening token, we parse the chunk to get error
        if current_token == OPEN_PARENTHESIS_TOKEN:
            error_index, error_token = parse_chunk(line_iter, CLOSE_PARENTHESIS_TOKEN)
        elif current_token == OPEN_BRACE_TOKEN:
            error_index, error_token = parse_chunk(line_iter, CLOSE_BRACE_TOKEN)
        elif current_token == OPEN_BRACKET_TOKEN:
            error_index, error_token = parse_chunk(line_iter, CLOSE_BRACKET_TOKEN)
        elif current_token == OPEN_CHEVRON_TOKEN:
            error_index, error_token = parse_chunk(line_iter, CLOSE_CHEVRON_TOKEN)
        elif expected_close_element is not None and current_token == expected_close_element:
            return None, None
        # If we got closing element different from expected, this is an error
        else:
            error_index = current_token_index
            error_token = current_token

        # If an error we stop and return, else we continue
        if error_token is not None:
            return error_index, error_token
        else:
            current_token_index, current_token = next(line_iter, (None, None))
    # Return none if no errors occurs or if we ended the line too soon
    return None, None


def parse_line(line):
    line_iter = iter(enumerate(line))
    return parse_chunk(line_iter, None)


def check_corrupted(lines):
    illegal_elements = []
    for i, line in enumerate(lines):
        error_index, error_token = parse_line(line)
        if error_token is not None:
            line_with_emphasis_on_index(line, error_index)
            print(
                f"Line {i} - Unexpected token {error_token} at position {error_index} - {line_with_emphasis_on_index(line, error_index)}")
            illegal_elements += error_token

    return illegal_elements


def illegal_token_to_score(token):
    if token == CLOSE_PARENTHESIS_TOKEN:
        return 3
    elif token == CLOSE_BRACKET_TOKEN:
        return 57
    elif token == CLOSE_BRACE_TOKEN:
        return 1197
    elif token == CLOSE_CHEVRON_TOKEN:
        return 25137


def compute_score(illegal_elements):
    return sum(map(lambda x: illegal_token_to_score(x), illegal_elements))


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
    illegal_elements = check_corrupted(data)
    assert_equals("Corrupted found",
                  [CLOSE_BRACE_TOKEN, CLOSE_PARENTHESIS_TOKEN, CLOSE_BRACKET_TOKEN,
                   CLOSE_PARENTHESIS_TOKEN, CLOSE_CHEVRON_TOKEN], illegal_elements)
    assert_equals("Score\t\t\t", 26397, compute_score(illegal_elements))


def test2(data):
    print("Test 2")



# ---- CHALLENGE SOLVER FUNCTION -------

def solve1(data):
    print("Solve 1")
    print(f"Res = {compute_score(check_corrupted(data))}")


def solve2(data):
    print("Solve 2")


def part1(data, test_data):
    test1(test_data)
    solve1(data)


def part2(data, test_data):
    test2(test_data)
    solve2(data)


if __name__ == '__main__':
    test_data_lines = read_file(TEST_FILE)
    input_lines = read_file(INPUT_FILE)
    part1(input_lines, test_data_lines)
    part2(input_lines, test_data_lines)
