# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"

# -------- INPUT PROCESSING ------------

FOLD_START = "fold along "


# Read the text file
def read_file(path):
    dots = set()
    fold_instructions = []
    with open(path, "r") as file:
        for line in file.readlines():
            if line.startswith(FOLD_START):
                fold = line.strip().split(" ")[-1].split("=")
                fold_instructions.append((fold[0], int(fold[1])))
            elif line != "\n":
                dot = line.strip().split(",")
                dots.add((int(dot[0]), int(dot[1])))
    return dots, fold_instructions


# -------- LOGIC & FUN  --------------
def compute_char_to_draw(x, y, points, fold):
    if fold is not None:
        if fold[0] == "y" and y == fold[1]:
            return "-"
        elif fold[0] == "x" and x == fold[1]:
            return "|"
    if (x, y) in points:
        return "#"
    return "."


def draw_paper(points, fold=None):
    max_x = max([point[0] for point in points]) + 1
    max_y = max([point[1] for point in points]) + 1

    for y in range(max_y):
        print("".join([compute_char_to_draw(x, y, points, fold) for x in range(max_x)]))


def process_fold(points, fold):
    max_x = max([point[0] for point in points])
    max_y = max([point[1] for point in points])

    new_points = set()
    for point in points:
        if fold[0] == "y":
            if point[1] < fold[1]:
                new_points.add(point)
            else:
                new_points.add((point[0], max_y - point[1]))
        else:
            if point[0] < fold[1]:
                new_points.add(point)
            else:
                new_points.add((max_x - point[0], point[1]))

    return new_points


def perform_all_folds(points, folds):
    for fold in folds:
        points = process_fold(points, fold)
    draw_paper(points)


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assert_equals(test_name, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {test_name} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {test_name} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1(points, folds):
    print("Test 1")

    points = process_fold(points, folds[0])
    assert_equals("Points after first fold", 17, len(points))
    draw_paper(points)
    points = process_fold(points, folds[1])
    assert_equals("Points after second fold", 16, len(points))
    draw_paper(points)


def test2(points, folds):
    print("Test 2")
    perform_all_folds(points, folds)


# ---- CHALLENGE SOLVER FUNCTION -------

def solve1(points, folds):
    print("Solve 1")
    points = process_fold(points, folds[0])
    print(f"Res {len(points)}")


def solve2(points, folds):
    print("Solve 2")
    perform_all_folds(points, folds)


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
