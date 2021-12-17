from math import ceil

# Files

INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------


# Read the text file
def read_file(path):
    with open(path, "r") as file:
        (x_trench, y_trench) = file.readline().strip().split(", y=")
        x_trench = [int(elt) for elt in x_trench[15:].split("..")]
        y_trench = [int(elt) for elt in y_trench.split("..")]
    return x_trench, y_trench


# -------- LOGIC & FUN  --------------

# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assert_equals(test_name, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {test_name} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {test_name} \t|\t Expected '{expected}' but got '{actual}'")
        return False


# Shooting probes just for fun
def shoot_probe(velocity, trench):
    position = [0, 0]
    print(f"Starting at {position} - Target : {trench}")
    i = 0
    max_height = 0
    while True:
        i += 1
        position[0] += velocity[0]
        position[1] += velocity[1]
        if velocity[0] > 0:
            velocity[0] -= 1
        elif velocity[0] < 0:
            velocity[0] += 1
        velocity[1] -= 1
        max_height = max(position[1], max_height)
        print(f"Step {i} - Position: {position} - Velocity: {velocity}")
        if trench[0][0] <= position[0] <= trench[0][1] and trench[1][0] <= position[1] <= trench[1][1]:
            print("Trench hit")
            return True, max_height
        elif (abs(trench[0][1]) < abs(position[0])  # We got too far on the x-axis
              or ((not trench[0][0] <= position[0] <= trench[0][1])
                  and velocity[0] == 0)  # We are stopped on x-axis and not over the trench
              or (velocity[1] < 0 and position[1] < trench[1][0])):  # we are going below the trench
            print("Target missed")
            return False, max_height


# This method was used to represent what occurs with y velocity
# With a velocity of 10, after 10 step, the velocity drops to 0. So max height occurs at step 10 ( or 9 )
# So the max height is the sum of every "y velocities" before  step 10.
def height_function(velocity, steps):
    height = 0
    for i in range(steps):
        height += velocity - i
        print(f"Step - {i} : velocity: {velocity - i} height = {height}")


# Velocity is a function. with V the constant of initial velocity and x the current step.
# So velocity at step x is v(x) = V - x. (i.e. With V = 10 and x = 5, velocity at step 5 = 5 )
# Then to retrieve max we take the sum of every value of the function before V
def compute_max_height(initial):
    def f_velocity(x):
        return initial - x

    return sum(f_velocity(x) for x in range(initial))


# Then, velocity function is v(x)   = V - X
# And , height function is   h(x+1) = h(x) + v(x), h(0) =0
# At step x = V, v(V) = 0, h(V) is at his maximum
# At step x = 2V, v(2V) = -V, h(2V) = 0
# At step x = 2V+1, h(2V+1) = h(2V) + v(2V+1) = v(2V+1) = V - (2V+1) = -V-1
# If we want to have a chance to shoot the trench, then we must have abs(h(2V+1)) <= abs(trench_bottom)
# abs(-V-1) <= abs(trench_bottom)
# V+1 <= abs(trench_bottom)
# V <= abs(trench_bottom)-1
# As we want to maximise V, we will take V = abs(trench_bottom)-1
# If we want to minimise V we could take

def compute_max_initial_velocity(y_trench):
    return abs(y_trench[0]) - 1


def find_most_stylish_velocity(trench):
    initial_v = compute_max_initial_velocity(trench[1])
    max_height = compute_max_height(initial_v)
    return initial_v, max_height


def compute_step_in_trench_with_given_y(initial_y_velocity, trench):
    def y_pos(current_step):
        def y_velocity(step):
            return initial_y_velocity - step

        return sum(y_velocity(x) for x in range(current_step))

    step_being_in_trench_with_given_y = []
    # We can skip until v(x) = 0. It means x = V. Cf part one. If negative, we start at 0

    start_step = initial_y_velocity if initial_y_velocity > 0 else 0
    end_step = trench[0][1] + 1
    for step_y in range(start_step, end_step):
        if trench[1][0] <= y_pos(step_y) <= trench[1][1]:
            step_being_in_trench_with_given_y.append(step_y)
    return step_being_in_trench_with_given_y


def find_possibilities_for_x_at_given_step(current_step, trench_x, current_y):
    def x_pos(initial_velocity):
        def x_velocity(step):
            if initial_velocity > 0:
                velocity = max(initial_velocity - step, 0)
            else:
                velocity = min(initial_velocity + step, 0)
            return velocity

        return sum([sum(x_velocity(x) for x in range(current_step))])

    possibilities = set()
    for possible_velocity in range(0, trench_x[1] + 1):
        if trench_x[0] <= x_pos(possible_velocity) <= trench_x[1]:
            possibilities.add((possible_velocity, current_y))
    return possibilities


def find_possibility_with_given_y_velocity(initial_y_velocity, trench):
    possibilities = set()
    steps_where_y_is_in_trench = compute_step_in_trench_with_given_y(initial_y_velocity, trench)
    for step in steps_where_y_is_in_trench:
        res = find_possibilities_for_x_at_given_step(step, trench[0], initial_y_velocity)
        possibilities = possibilities.union(res)
    return possibilities


def compute_number_of_possibilities(trench):
    possibilities = 0
    for y in range(trench[1][0], compute_max_initial_velocity(trench[1]) + 1):
        res = find_possibility_with_given_y_velocity(y, trench)
        possibilities += len(res)
    return possibilities


def test1(data):
    print("Test 1")
    velocity, highest_point = find_most_stylish_velocity(data)
    assert_equals("Initial y velocity", 9, velocity)
    assert_equals("Max y", 45, highest_point)


def test2(data):
    print("Test 2")
    assert_equals("Number possibilities", 112, compute_number_of_possibilities(data))


# ---- CHALLENGE SOLVER FUNCTION -------


def solve1(data):
    print("Solve 1")
    print(f"Res: {find_most_stylish_velocity(data)[1]}")


def solve2(data):
    print("Solve 2")
    print(f"Res: {compute_number_of_possibilities(data)}")


def part1(data, test_data):
    test1(test_data)
    solve1(data)


def part2(data, test_data):
    test2(test_data)
    solve2(data)


if __name__ == '__main__':
    test_input = read_file(TEST_FILE)
    challenge_input = read_file(INPUT_FILE)
    part1(challenge_input, test_input)
    part2(challenge_input, test_input)
