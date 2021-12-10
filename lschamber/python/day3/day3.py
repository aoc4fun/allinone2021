# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------

# Read the text file to get a list like [['0', '0', '1', '0', '0'], ['1', '1', '1', '1', '0'],...]
def read_file(path):
    with open(path, "r") as file:
        return list(map(lambda line: [int(i) for i in line.strip()], file.readlines()))


# -------- LOGIC & FUN  --------------

# Create a list where each element contain the list of values for first bit, then for second bit, etc.
# Then Sum each element to get number of bit with 1
# And Use a mapping to get 1 when 1 Is most common
# This is also used to compute the most common bit.
# Not efficient but I did not have time to adapt the behaviour to find the "Most recurring" element bit by bit
def compute_gamma_binary(data):
    return "".join(str(x) for x in map(lambda occurrence: int(occurrence >= len(data) / 2),
                                       [sum(i) for i in [[elt[i] for elt in data] for i in range(len(data[0]))]]))


# Invert bits of binary number
def invert_binary(binary_string):
    return "".join("1" if x == "0" else "0" for x in binary_string)


def compute_gamma_epsilon(data):
    binary_gamma = compute_gamma_binary(data)
    binary_epsilon = invert_binary(binary_gamma)
    return int(binary_gamma, 2), int(binary_epsilon, 2)


# Duplicated behaviour here....
def compute_o2(data):
    data_length = len(data)
    i = 0
    while len(data) > 1 and i < data_length:
        data = list(filter(lambda elt: elt[i] == int(compute_gamma_binary(data)[i]), data))
        i += 1

    if len(data) != 1:
        print(f"Error O2 computation returned multiple results: found {data}")
        exit()
    return "".join(str(x) for x in data[0])


def compute_co2(data):
    data_length = len(data)
    i = 0
    while len(data) > 1 and i < data_length:
        data = list(filter(lambda elt: elt[i] != int(compute_gamma_binary(data)[i]), data))
        i += 1

    if len(data) != 1:
        print(f"Error CO2 computation returned multiple results: found {data}")
        exit()
    return "".join(str(x) for x in data[0])


def compute_o2_and_co2(data):
    return int(compute_o2(data), 2), int(compute_co2(data), 2)


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assert_equals(test_name, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {test_name} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tError - {test_name} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1(test_data):
    name = "Test1"
    expected_gamma = 22
    expected_epsilon = 9
    print(f"Running {name}:")
    (actualGamma, actualEpsilon) = compute_gamma_epsilon(test_data)
    # Run test like this to ensure that no lazy evaluation are made
    success = assert_equals("Gamma", expected_gamma, actualGamma)
    success = assert_equals("Epsilon", expected_epsilon, actualEpsilon) and success
    if success:
        print(f"{name} OK")
    else:
        print(f"{name} KO")


def test2(test_data):
    name = "Test2"
    expected_o2 = 23
    expected_co2 = 10
    print(f"Running {name}:")
    (O2, CO2) = compute_o2_and_co2(test_data)
    # Run test like this to ensure that no lazy evaluation are made
    success = assert_equals("O2", expected_o2, O2)
    success = assert_equals("CO2", expected_co2, CO2) and success
    if success:
        print(f"{name} OK")
    else:
        print(f"{name} KO")


def test():
    test_data = read_file(TEST_FILE)
    test1(test_data)
    test2(test_data)


# ---- CHALLENGE SOLVER FUNCTION -------

def part1(data):
    print("Running part 1:")
    (gamma, epsilon) = compute_gamma_epsilon(data)
    print(f"\t Gamma: {gamma}")
    print(f"\t Epsilon: {epsilon}")
    print(f"\t Result: {gamma * epsilon}")


def part2(data):
    print("Running part 2:")
    (o2, co2) = compute_o2_and_co2(data)
    print(f"\t O2: {o2}")
    print(f"\t CO2: {co2}")
    print(f"\t Result: {o2 * co2}")


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
