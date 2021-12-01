test_data = """199
200
208
210
200
207
240
269
260
263""".split()


def count_increase(data):
    clean_data = [int(i) for i in data]
    count = 0
    for i in range(1, len(clean_data)):
        count = count + (clean_data[i] - clean_data[i - 1]>0)
    return count


def count_increase2(data):
    clean_data = [int(i) for i in data]
    count = 0
    for i in range(0, len(clean_data) - 3):
        count += sum(clean_data[i+1:i+4]) - sum(clean_data[i:i + 3]) > 0
    return count


if __name__ == '__main__':
    assert (count_increase(test_data) == 7)
    print(count_increase(open("input").readlines()))
    assert (count_increase2(test_data) == 5)
    print(count_increase2(open("input").readlines()))


