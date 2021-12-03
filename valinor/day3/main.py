import unittest

test_data = """00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010""".split("\n")

def compute_epsilon_from_gamma(gamma):
    return "".join([str(1-int(i)) for i in gamma])

def consumption(data,oxygen=True):
    if oxygen:
        return "".join(["0" if colonne.count("0")>colonne.count("1") else "1" for colonne in zip(*data)])
    else:
        return "".join(["0" if colonne.count("0")>colonne.count("1") else "1" for colonne in zip(*data)])

def filternumber(rawdata,position,searchdata):
    return [data for data in rawdata if data[position]==searchdata]

def filterCO2(rawdata):
    step=0
    while (len(rawdata)>1):
        rawdata=filternumber(rawdata, step, compute_epsilon_from_gamma(consumption(rawdata,False))[step])
        step+=1
    return (int(rawdata[0], 2))

def filteroxygen(rawdata):
    step=0
    while (len(rawdata)>1):
        rawdata=filternumber(rawdata, step, consumption(rawdata)[step])
        step+=1
    return (int(rawdata[0], 2))


def gamma(data):
    return int(data,2)

def compute_epsilon_from_gamma(gamma):
    return "".join([str(1-int(i)) for i in gamma])

def epsilon(data):
    return int(compute_epsilon_from_gamma(data),2)

def problem1(input_data):
    raw_data=consumption(input_data)
    return gamma(raw_data)*epsilon(raw_data)

def problem2(input_data):
    raw_data=consumption(input_data)
    CO2=filterCO2(input_data)
    oxygen=filteroxygen(input_data)
    return oxygen*CO2

class Test(unittest.TestCase):
    def test_gamma(self):
        self.assertEqual(gamma("10110"), 22)

    def test_epsilon(self):
        self.assertEqual(epsilon("10110"), 9)


if __name__ == '__main__':
    #unittest.main()
    data = open("input.txt").readlines()
    clean_data=[i.replace('\n', '') for i in data]
    assert(problem1(test_data)==198)
    print(problem1(clean_data))
    assert(problem2(test_data)==230)
    print(problem2(clean_data))


