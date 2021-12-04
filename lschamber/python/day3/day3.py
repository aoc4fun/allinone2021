# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------

# Read the text file to get a list like [['0', '0', '1', '0', '0'], ['1', '1', '1', '1', '0'],...]
def read_file(path):
    with open(path, "r") as file:
        return list(map(lambda line: [int(i) for i in line.strip()], file.readlines()))


# -------- LOGIC & FUN  --------------

#Create a list where each elements contains the list of values for first bit, then for second bit, etc
#Then Sum each element to get number of bit with 1
#And Use a mapping to get 1 when 1 Is most commun
#This is also used to compute the most common bit. Not efficient but I did not have time to adapt the behaviour to find the "Most reccuring" element bit by bit
def computeGammaBinary(data):
    return "".join(str(x) for x in map(lambda occurence: int(occurence >= len(data)/2), [sum(i) for i in [[elt[i] for elt in data] for i in range(len(data[0]))]]))

#Invert bits of binary number
def invertBinary(binaryString):
    return "".join("1" if x == "0" else "0" for x in binaryString)

def compteGammaAndEpsilon(data):
    binaryGamma = computeGammaBinary(data)
    binaryEpsilon = invertBinary(binaryGamma)
    return (int(binaryGamma, 2), int(binaryEpsilon,2))


#Duplicated behaviour here....
def compute02(data):
    dataLenght = len(data)
    i = 0
    while len(data) > 1 and i < dataLenght:
        data = list(filter(lambda elt: elt[i] == int(computeGammaBinary(data)[i]), data))
        i+=1

    if(len(data) != 1):
        print(f"Error O2 computation returned multiple results: found {data}")
        exit()
    return "".join(str(x) for x in data[0])

def computeC02(data):
    dataLenght = len(data)
    i = 0
    while len(data) > 1 and i < dataLenght:
        data = list(filter(lambda elt: elt[i] != int(computeGammaBinary(data)[i]), data))
        i+=1

    if(len(data) != 1):
        print(f"Error CO2 computation returned multiple results: found {data}")
        exit()
    return "".join(str(x) for x in data[0])

def computeO2AndCO2(data):
    return  (int(compute02(data),2), int(computeC02(data),2))

# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assertEquals(testName, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {testName} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tError - {testName} \t|\t Expected '{expected}' but got '{actual}'")
        return False

def test1(testData):
    name="Test1"
    expectedGamma = 22
    expectedEpsilon = 9
    print(f"Running {name}:")
    (actualGamma, actualEpsilon) = compteGammaAndEpsilon(testData)
    #Run test like this to ensure that no lazy evaluation are made
    success = assertEquals("Gamma", expectedGamma, actualGamma)
    success = assertEquals("Epsilon", expectedEpsilon, actualEpsilon) and success
    if success:
        print(f"{name} OK")
    else:
        print(f"{name} KO")

def test2(testData):
    name="Test2"
    expectedO2 = 23
    expectedCO2 = 10
    print(f"Running {name}:")
    (O2, CO2) = computeO2AndCO2(testData)
    #Run test like this to ensure that no lazy evaluation are made
    success = assertEquals("O2", expectedO2, O2)
    success = assertEquals("CO2", expectedCO2, CO2) and success
    if success:
        print(f"{name} OK")
    else:
        print(f"{name} KO")


def test():
    testData = read_file(TEST_FILE)
    test1(testData)
    test2(testData)


# ---- CHALLENGE SOLVER FUNCTION -------

def part1(data):
    print("Running part 1:")
    (gamma, epsilon) = compteGammaAndEpsilon(data)
    print(f"\t Gamma: {gamma}")
    print(f"\t Epsilon: {epsilon}")
    print(f"\t Result: {gamma*epsilon}")


def part2(data):
    print("Running part 2:")
    (o2, co2) = computeO2AndCO2(data)
    print(f"\t O2: {o2}")
    print(f"\t CO2: {co2}")
    print(f"\t Result: {o2*co2}")


def solve():
    data = read_file(INPUT_FILE)
    part1(data)
    part2(data)


def main():
    test()
    print("-------------")
    solve()


if __name__ == '__main__':
    main()
