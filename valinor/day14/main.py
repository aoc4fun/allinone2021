from collections import defaultdict

test_data = """NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C""".split("\n")

def observe_smart(raw_data):
    start=list(raw_data[0])
    program=[line.split(" -> ") for line in raw_data if "->" in line]
    memory={}
    result={}
    for i in range(0,len(program)):
        memory[program[i][0]]=program[i][1]
        result[program[i][0]]=0
    for i in range(0,len(start)-1):
        find = start[i] + start[i + 1]
        result[find]+=1
    return result,memory,start[-1]

def next_smart(start,program):
    intermediaire=defaultdict(int)
    for i in start.keys():
        next=program[i]
        intermediaire[i]-=1*start[i]
        intermediaire[i[0]+next]+=1*start[i]
        intermediaire[next+i[1]] += 1*start[i]
    for i in start.keys():
        if i in intermediaire:
            start[i]+=intermediaire[i]
    return start

def iter_smart(start,program,iter):
    for i in range(0,iter):
        start=next_smart(start,program)
    return start

def count(data,iteration):
    start, program,relicat = observe_smart(data)
    result = iter_smart(start, program, iteration)
    intermediaire = defaultdict(int)
    for i in result.keys():
        intermediaire[i[0]]+=result[i]
    intermediaire[relicat]+=1
    return max(intermediaire.values()) - (min(intermediaire.values()))

def problem1(data):
    return count(data,10)

def problem2(data):
    return count(data,40)


def test():
    assert(problem1(test_data)==1588)
    assert(problem2(test_data)==2188189693529)


if __name__ == '__main__':
    data = [line.replace("\n","") for line in open("input.txt").readlines()]
    test()
    print(problem1(data))
    print(problem2(data))


