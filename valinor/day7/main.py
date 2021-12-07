from collections import defaultdict

test_data = """16,1,2,0,4,2,7,1,2,14"""


def observe(raw_data):
    return [int(i) for i in raw_data.split(",")]


def position(crabs):
    positions = defaultdict(int)
    for crab in crabs:
        positions[crab] += 1
    return positions


def fuel_use(positions_crabs, position):
    return sum([abs(current_position - position) * weight for current_position, weight in positions_crabs.items()])


def correct_fuel_use(positions_crabs, position):
    return sum([compute_crab_fuel(abs(current_position - position)) * weight for current_position, weight in
                positions_crabs.items()])


def compute_crab_fuel(distance):
    return ((distance + 1) * distance) // 2


def explore(positions, func=fuel_use):
    return [func(positions, position) for position in range(min(positions.keys()), max(positions.keys()))]


def problem1(crabs):
    return min(explore(position(crabs)))


def problem2(crabs):
    return min(explore(position(crabs), correct_fuel_use))


def test():
    crabs = observe(test_data)
    assert (crabs == [16, 1, 2, 0, 4, 2, 7, 1, 2, 14])
    assert (position(crabs) == {16: 1, 1: 2, 2: 3, 0: 1, 4: 1, 7: 1, 14: 1})
    assert (fuel_use(position(crabs), 2) == 37)
    assert (fuel_use(position(crabs), 10) == 71)
    assert (explore({16: 1, 1: 2, 2: 3, 0: 1, 4: 1, 7: 1, 14: 1}) == [49, 41, 37, 39, 41, 45, 49, 53, 59, 65, 71, 77,
                                                                      83, 89, 95, 103])
    assert (problem1(crabs) == 37)

    assert (compute_crab_fuel(3) == 6)
    assert (compute_crab_fuel(5) == 15)
    assert (correct_fuel_use(position(crabs), 2) == 206)
    assert (problem2(crabs) == 168)


if __name__ == '__main__':
    data = open("input.txt").readline()
    test()
    crabs = observe(data)
    print(problem1(crabs))
    print(problem2(crabs))

