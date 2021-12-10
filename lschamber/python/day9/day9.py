from functools import reduce

# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------

# Read the text file to get a 2D list
def read_file(path):
    with open(path, "r") as file:
        return [[int(i) for i in list(line.strip())] for line in file.readlines()]


# -------- LOGIC & FUN  --------------

# Find more pythonic way
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


def find_low_points(heightmap):
    low_points = []
    map_x = len(heightmap[0])
    map_y = len(heightmap)
    for y in range(map_y):
        for x in range(map_x):
            neighbours_heights = [heightmap[y2][x2] for x2, y2 in neighbours(x, y, map_x, map_y)]
            height = heightmap[y][x]
            if height < min(neighbours_heights):
                low_points += [height]
    return low_points


def risk_levels(low_points):
    return [i + 1 for i in low_points]


def find_low_points_coord(heightmap):
    low_points = []
    map_x = len(heightmap[0])
    map_y = len(heightmap)
    for y in range(map_y):
        for x in range(map_x):
            neighbours_heights = [heightmap[y2][x2] for x2, y2 in neighbours(x, y, map_x, map_y)]
            height = heightmap[y][x]
            if height < min(neighbours_heights):
                low_points.append((x, y))
    return low_points


def find_basin(lowest_point, heightmap):
    map_x = len(heightmap[0])
    map_y = len(heightmap)

    basin = set()
    basin.add(lowest_point)
    point_to_process = basin.copy()
    while len(point_to_process) > 0:
        current_point = point_to_process.pop()
        current_point_height = heightmap[current_point[1]][current_point[0]]
        for x, y in neighbours(current_point[0], current_point[1], map_x, map_y):
            if heightmap[y][x] > current_point_height and heightmap[y][x] != 9:
                basin.add((x, y))
                point_to_process.add((x, y))
    return list(basin)


def multiply_three_max_items(values):
    return reduce(lambda x, y: x * y, sorted(values, reverse=True)[0:3])


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
    low_points = find_low_points(data)
    assert_equals("Low points\t", [1, 0, 5, 5], low_points)
    assert_equals("Risk level\t", [2, 1, 6, 6], risk_levels(low_points))
    assert_equals("Sum Risk level", 15, sum(risk_levels(low_points)))


def test2(data):
    print("Test 2")

    basins = list(map(lambda point: find_basin(point, data), find_low_points_coord(data)))
    basins_values = list(map(lambda basin: [data[point[1]][point[0]] for point in basin], basins))
    basin_length = list(map(lambda basin: len(basin), basins))

    assert_equals("Basins \t\t",
                  [[(1, 0), (0, 1), (0, 0)], [(9, 0), (8, 1), (6, 1), (7, 0), (9, 2), (8, 0), (5, 0), (6, 0), (9, 1)],
                   [(1, 2), (2, 1), (4, 3), (3, 1), (4, 1), (0, 3), (4, 2), (1, 4), (2, 3), (3, 3), (2, 2), (3, 2),
                    (1, 3), (5, 2)], [(7, 4), (8, 4), (5, 4), (6, 4), (7, 3), (8, 3), (7, 2), (6, 3), (9, 4)]], basins)
    assert_equals("Basins Values ", [[1, 3, 2], [0, 2, 4, 2, 2, 1, 4, 3, 1], [8, 8, 8, 7, 8, 8, 7, 8, 6, 7, 5, 6, 7, 8],
                                     [6, 7, 6, 5, 7, 8, 8, 6, 8]], basins_values)
    assert_equals("Basins Length ", [3, 9, 14, 9], basin_length)

    draw_basins(basins, len(data[0]), len(data))
    assert_equals("Result\t\t", 1134, multiply_three_max_items(basin_length))


def draw_basins(basins, map_x, map_y):
    basins_flat = []
    for basin in basins:
        basins_flat += basin
    for y in range(map_y):
        line = ""
        for x in range(map_x):
            line += "0" if (x, y) in basins_flat else "X"
        print(line)


# ---- CHALLENGE SOLVER FUNCTION -------

def solve1(data):
    print("Solve 1")
    print(f"Res: {sum(risk_levels(find_low_points(data)))}")


def solve2(data):
    print("Solve 2")
    basins = list(map(lambda point: find_basin(point, data), find_low_points_coord(data)))
    # draw_basins(basins, len(data[0]), len(data))
    basin_length = list(map(lambda basin: len(basin), basins))
    print(f"Result is {multiply_three_max_items(basin_length)}")


def part1(data, test_data):
    test1(test_data)
    solve1(data)


def part2(data, test_data):
    test2(test_data)
    solve2(data)


if __name__ == '__main__':
    test_data_map = read_file(TEST_FILE)
    data_map = read_file(INPUT_FILE)
    part1(data_map, test_data_map)
    part2(data_map, test_data_map)
