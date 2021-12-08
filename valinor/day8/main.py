from collections import defaultdict

test_data = """be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce""".split("\n")


def observe(raw_data):
    return raw_data.split("\n")


def filter_output(observation):
    return observation.split("|")[1].split()


def filter_input(observation):
    return observation.split("|")[0].split()


def find_unique(list_number):
    size=[len(number) for number in list_number]
    return size.count(2)+size.count(4)+size.count(3)+size.count(7)


def find_all_unique(input_observation):
    return [find_unique(filter_output(values)) for values in input_observation]


def problem1(input_observation):
    return sum(find_all_unique(input_observation))


def sort_data(data):
    return "".join(sorted([a for a in data]))

def find_all_number(list_number):
    is0 = find_0(list_number)
    is1 = find_1(list_number)
    is2 = find_2(list_number)
    is3 = find_3(list_number)
    is4 = find_4(list_number)
    is5 = find_5(list_number)
    is6 = find_6(list_number)
    is7 = find_7(list_number)
    is8 = find_8(list_number)
    is9 = find_9(list_number)
    return {
        sort_data(is0): "0",
        sort_data(is1): "1",
        sort_data(is2): "2",
        sort_data(is3): "3",
        sort_data(is4): "4",
        sort_data(is5): "5",
        sort_data(is6): "6",
        sort_data(is7): "7",
        sort_data(is8): "8",
        sort_data(is9): "9",
    }

def decode(input_list, to_decode_list):
    translatordict=find_all_number(input_list)
    return "".join([translatordict[sort_data(data)] for data in to_decode_list])

def find_simple(list_number,size):
    for search in list_number:
        if len(search)==size:
            return search

def find_1(list_number):
    return find_simple(list_number,2)

def find_7(list_number):
    return find_simple(list_number,3)

def find_4(list_number):
    return find_simple(list_number,4)

def find_8(list_number):
    return find_simple(list_number,7)

def filter_size(list_number,search_size):
    return [x for x in list_number if len(x)==search_size]

def find_3(list_number):
    one=find_1(list_number)
    result=filter_size(list_number,5)
    return [x for x in result if one[0] in x and one[1] in x][0]

def find_6(list_number):
    one=find_1(list_number)
    result=filter_size(list_number,6)
    return [x for x in result if not(one[0] in x and one[1] in x)][0]

def find_2(list_number):
    six=find_6(list_number)
    one=find_1(list_number)
    mustbe=list(set(list(one))-set(list(six)))[0]
    is3=find_3(list_number)
    result=filter_size(list_number,5)
    return [x for x in result if x!=is3 and mustbe in x][0]

def find_5(list_number):
    is2=find_2(list_number)
    is3 = find_3(list_number)
    result=filter_size(list_number,5)
    return [x for x in result if x!=is3 and x!=is2][0]

def find_9(list_number):
    is4=find_4(list_number)
    result=filter_size(list_number,6)
    return [x for x in result if len(set(list(is4))-set(list(x)))==0][0]

def find_0(list_number):
    is6=find_6(list_number)
    is9 = find_9(list_number)
    result=filter_size(list_number,6)
    return [x for x in result if x!=is6 and x!=is9][0]

def decode_all(observations):
    return [int(decode(filter_input(observation),filter_output(observation))) for observation in observations]


def problem2(observations):
    return sum(decode_all(observations))


def test():
    assert (filter_output(test_data[-1])==["fgae","cfgab","fg","bagce"])
    assert (filter_input(test_data[-1])==["gcafb","gcf","dcaebfg","ecagb","gf","abcdeg","gaef","cafbge","fdbac","fegbdc"])
    assert (find_unique(["fgae", "cfgab", "fg", "bagce"]) == 2)
    assert (sum(find_all_unique(test_data)) == 26)
    assert (problem1(test_data) == 26)

    search_space=["gcafb","gcf","dcaebfg","ecagb","gf","abcdeg","gaef","cafbge","fdbac","fegbdc"]
    assert (find_1(search_space)=="gf")
    assert (find_7(search_space) == "gcf")
    assert (find_4(search_space) == "gaef")
    assert (find_8(search_space) == "dcaebfg")
    assert (filter_size(search_space,5) == ["gcafb","ecagb","fdbac"])
    assert (find_3(search_space)=="gcafb")
    assert (find_6(search_space)=="abcdeg")
    assert (find_2(search_space) == "fdbac")
    assert (find_5(search_space) == "ecagb")
    assert (find_9(search_space) == "cafbge")
    assert (find_0(search_space) == "fegbdc")
    decode_sample="acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"
    assert(decode(filter_input(decode_sample),filter_output(decode_sample))=='5353')
    assert(problem2(test_data)==61229)


if __name__ == '__main__':
    data = [line.replace("\n","") for line in open("input.txt").readlines()]
    test()
    print(problem1(data))
    print(problem2(data))


