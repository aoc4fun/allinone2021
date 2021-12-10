# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------

def parse_line(line):
    split_line = line.split("|")
    return (
        [signal for signal in split_line[0].strip().split(" ")], [digit for digit in split_line[1].strip().split(" ")])


# Read the text file to get the list of (signalPattern, fourDigits) elements
def read_file(path):
    with open(path, "r") as file:
        return [parse_line(line) for line in file.readlines()]


# -------- LOGIC & FUN  --------------

# Discarded Idea: Each digit has 7 segment. The goal is to understand which letter represent which segment
# We may represent digit like binary, with value of 1 if the segment is on:
# 0: 1110111
# 1: 0010010
# 2: 1011101
# 3: 1011011
# 4: 0111010
# 5: 1101011
# 6: 1101111
# 7: 1010010
# 8: 1111111
# 9: 1111011
# A letter can also be represented by a binary :
# In the usual case
# c: 0010000
# f: 0000010
# So if we have the number 1 in the usual case we have 1 = ab = 0010000 & 0000010 = 0010010
# The idea is find how we can use "simple" number ( that have a unique number of 1 ) to compute letters.
# For instance with 1 and 7, we can find the which letter is represented by binary 1000000
# 1 xor 7 => 0010010 xor 1010010 => 1000000
# Then "make a xor" on 1 and 7 letters : cf xor acf => We got our first letter.
# Once we found all the letters, we can use this map to parse "string" numbers to digits by doing & between letters


# Expect list of number or None
def count_number_of_digit_found(digits):
    return sum(map(lambda digit: digit is not None, digits))


def compute_digits(line, only_simple=True):
    # Treat all numbers in the line as element to compute letters values
    line = set(line[0]).union(set(line[1]))

    digits = [None for _ in range(10)]
    digits[1] = set(next(x for x in line if len(x) == 2))
    digits[4] = set(next(x for x in line if len(x) == 4))
    digits[7] = set(next(x for x in line if len(x) == 3))
    digits[8] = set(next(x for x in line if len(x) == 7))

    if only_simple:
        return digits

    # 3 Is the only digit having both segment of 1 and of size 5
    digits[3] = next(set(digit) for digit in line if
                     len(digit) == 5  # Has size of 5
                     and len(set(digit).intersection(digits[1])) == len(digits[1])  # Include all segment of 1
                     and len(set(digit).symmetric_difference(digits[1])) > 0)  # Is not one

    # To find the 6 Digit, we search the only number with only 1 common bit with 1 and of size 6
    digits[6] = next(set(digit) for digit in line if
                     len(digit) == 6  # Has size of 6
                     and len(set(digit).intersection(digits[1])) == 1)  # Include only one segment of 1

    digits[9] = next(set(digit) for digit in line if
                     len(digit) == 6  # Has size of 6
                     and len(set(digit).intersection(digits[4])) == len(digits[4]))  # Include all four digit

    digits[0] = next(set(digit) for digit in line if
                     len(digit) == 6  # Has size of 6
                     and len(set(digit).symmetric_difference(digits[6])) > 0  # Is not six
                     and len(set(digit).symmetric_difference(digits[9])) > 0)  # Is not nine

    # 5 Is the only 5 segment digit with only difference with 6
    digits[5] = next(set(digit) for digit in line if
                     len(digit) == 5  # Has size of 5
                     and len(set(digit).symmetric_difference(digits[6])) == 1)

    # 2 Is a five segment digit but not 3 or 5
    digits[2] = next(set(digit) for digit in line if
                     len(digit) == 5  # Has size of 6
                     and len(set(digit).symmetric_difference(digits[3])) > 0  # Is not 3
                     and len(set(digit).symmetric_difference(digits[5])) > 0)  # Is not 5

    return digits


def translate_digits(line, only_simple=False):
    digit_set = compute_digits(line, only_simple)
    return [translate_digit(digit, digit_set) for digit in line[1]]


def translate_digit(digit, digit_set):
    digit = set(digit)
    for index, currentDigit in enumerate(digit_set):
        if currentDigit is not None and len(currentDigit.symmetric_difference(digit)) == 0:
            return index
    return None


def join_numbers(digits):
    return int("".join([str(s) for s in digits]))


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
    digit_parsed = [translate_digits(line, True) for line in data]
    res = sum([count_number_of_digit_found(d) for d in digit_parsed])
    assert_equals("Number of digit found", 26, res)


def test2(data):
    print("Test 2")
    test_line = parse_line("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
    # TODO Test each reading
    numbers_parsed = [join_numbers(translate_digits(line, False)) for line in data]
    assert_equals("Sum of numbers", 61229, sum(numbers_parsed))


# ---- CHALLENGE SOLVER FUNCTION -------

def solve1(data):
    print("Solve 1")
    digit_parsed = [translate_digits(line, True) for line in data]
    res = sum([count_number_of_digit_found(d) for d in digit_parsed])
    print(f"Number of digit found {res}")


def solve2(data):
    print("Solve 2")
    numbers_parsed = [join_numbers(translate_digits(line, False)) for line in data]
    print(f"Sum of numbers:  {sum(numbers_parsed)}")


def part1(data, test_data):
    test1(test_data)
    solve1(data)


def part2(data, test_data):
    test2(test_data)
    solve2(data)


if __name__ == '__main__':
    test_file_data = read_file(TEST_FILE)
    file_data = read_file(INPUT_FILE)
    part1(file_data, test_file_data)
    part2(file_data, test_file_data)
