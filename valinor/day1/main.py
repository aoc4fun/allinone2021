test_data = [int(i) for i in """199 200 208 210 200 207 240 269 260 263""".split()]

def count_increase(data, sep=1):
    return sum([data[i+sep] > data[i] for i in range(0, len(data)-sep)])

if __name__ == '__main__':
    clean_data = [int(i) for i in open("input.txt").readlines()]
    assert (count_increase(test_data) == 7)
    print(count_increase(clean_data))
    assert (count_increase(test_data,3) == 5)
    print(count_increase(clean_data,3))

