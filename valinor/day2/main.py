test_data = """forward 5
down 5
forward 8
up 3
down 8
forward 2""".split("\n")


def go(instructions:list, part:int=1) -> int:
    horizontal,depth,aim = 0,0,0
    for instruction in instructions:
        if "forward" in instruction:
            horizontal += int(instruction[len("forward"):])
            if part > 1:
                depth += aim * int(instruction[len("forward"):])
        if "down" in instruction:
            if part == 1:
                depth += int(instruction[len("down"):])
            else:
                aim += int(instruction[len("down"):])
        if "up" in instruction:
            if part == 1:
                depth -= int(instruction[len("up"):])
            else:
                aim -= int(instruction[len("up"):])
    return depth * horizontal


if __name__ == '__main__':
    clean_data=open("input.txt").readlines()
    assert (go(test_data) == 150)
    print(go(clean_data))
    assert (go(test_data, part=2) == 900)
    print(go(clean_data,part=2))


