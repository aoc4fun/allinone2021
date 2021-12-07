

def fire(raw_data):
    return [int(i) for i in raw_data.split(",")]


def burn(lanterns):
    lanterns_state=[(lantern-1) if lantern>0 else 6 for lantern in lanterns]
    lanterns_state.extend([8]*lanterns.count(0))
    return lanterns_state


def test():
    test_data = """3,4,3,1,2"""
    lantern = fire(test_data)
    lantern=burn(lantern)
    assert(lantern==[2,3,2,0,1])
    lantern=burn(lantern)
    assert(lantern==[1,2,1,6,0,8])
    lantern=burn(lantern)
    assert(lantern==[0,1,0,5,6,7,8])


def count(lanter_initial,iter):
    lanterns=[lanter_initial.count(i) for i in range(0,9)]
    for _ in range(0,iter):
        lanterns=lanterns[1:7]+[lanterns[0]+lanterns[7],lanterns[8],lanterns[0]]
    return sum(lanterns)


def test_fast():
    test_data = """3,4,3,1,2"""
    assert(count(fire(test_data),18) == 26)
    assert(count(fire(test_data), 80) == 5934)
    assert(count(fire(test_data), 256) == 26984457539)


if __name__ == '__main__':
    test()
    test_fast()
    data=open("input.txt").readline()
    print(count(fire(data), 80))
    print(count(fire(data), 256))


