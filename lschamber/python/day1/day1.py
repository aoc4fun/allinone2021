from functools import reduce

INPUT_FILE = "./input.txt"

#Explanation
# In order to compute the number of increment I zip the data to make an array of [n, n+1] element.
# Then I filter to only the entries with increments. The result is the size of the list
def part1(data):
        print("Part 1: "+ str(len(list(filter(lambda entries: entries[0]>entries[1], zip(data[1:], data[0:-1]))))))

# I Apologise for doing this. But the lesser lines it have, the best it is.
# Explanation:
# 1) Compute the Windows sums
#     In order to compute the sum for the sonar window, it zip the data 3 times. This gives us an array with [[n, n+1, n+2], [n+1, n+2, n+3],...]
#     Then we map each element of the array with the sum of the window. Then we have [SUM_A, SUM_B, SUM_C, ....]
# 2) Compute number of increments
#    CF part 1
def part2(data):
        print("Part 2: "+ str(len(list(filter(lambda entries: entries[0]>entries[1], zip(list(map(sum, zip(data[:-2],data[1:-1], data[2:])))[1:], list(map(sum, zip(data[:-2],data[1:-1], data[2:])))[0:-1]))))))

def main():
    with open(INPUT_FILE, "r") as file:
        data = [int(x) for x in file.readlines()]
    part1(data)
    part2(data)

if __name__ == '__main__':
    main()


