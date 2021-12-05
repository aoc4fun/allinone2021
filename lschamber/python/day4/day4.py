# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------


# Read the text file to get a the list of numbers played and a List of 2D list for players grid
def read_file(path):
    with open(path, "r") as file:
        numbers = [int(x) for x in file.readline().split(",")]
        players = []
        currentPlayer = None
        for line in file.readlines():
            if line == "\n":
                if currentPlayer is not None:
                    players.append(currentPlayer)
                currentPlayer = []
            else:
                currentPlayer.append([int(x) for x in line.strip().split(" ") if x != ""])
        players.append(currentPlayer)
    return (numbers, players)


# -------- LOGIC & FUN  --------------

# Find all the lines and row for players. THis allows to process simple list of row, rather than dealing with rows and cols
def computeRowsAndLines(players):
    rowsAndLinesForPlayer = []
    for player in players:
        # Add All lines in the list
        rowAndLine = player.copy()
        for i in range(len(player[0])):
            rowAndLine.append([row[i] for row in player])
        rowsAndLinesForPlayer.append(rowAndLine)
    return rowsAndLinesForPlayer


# Sum all unmarked (remaining) values. Need to /2 as there is all unmarked duplicated ( Row and Lines )
def sumUnmarked(playerRowsAndLines):
    return sum([sum(x) for x in playerRowsAndLines]) / 2

#Play bingo and return  the first winner, the sum of unmarked element, and last number played
def playBingo(numbers, players):
    playerRowAndLinesInital = computeRowsAndLines(players)
    players = playerRowAndLinesInital.copy()
    for currentNumber in numbers:
        # Remove currentNumber
        players = [[list(filter(lambda x: x != currentNumber, row)) for row in playerRows] for playerRows in players]
        # check winner
        for indexPlayer, playerRows in enumerate(players):
            for indexRow, row in enumerate(playerRows):
                if len(row) == 0:
                    return (indexPlayer, sumUnmarked(players[indexPlayer]), currentNumber)
    return (0, 0, 0)

#Check if a player has won
def hasWon(playerRows):
    for row in playerRows:
        if len(row) == 0:
            return True
    return False

#Play bingo and return when the last player won
def playBingo2(numbers, players):
    playerRowAndLinesInital = computeRowsAndLines(players)
    players = playerRowAndLinesInital.copy()
    for currentNumber in numbers:
        # Remove currentNumber
        players = [[list(filter(lambda x: x != currentNumber, row)) for row in playerRows] for playerRows in players]
        # If the player the last one to win
        if (len(players) == 1 and hasWon(players[0])):
            return (sumUnmarked(players[0]), currentNumber)
        # Else remove potential winners
        else:
            # check winner and remove if there is one
            players = [player for player in players if not hasWon(player)]
    return (0, 0)


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assertEquals(testName, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {testName} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {testName} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1(numbers, players):
    print(f"Running test1:")
    (winner, sumUnmarked, lastNumber) = playBingo(numbers, players)
    success = assertEquals("Winner: ", 2, winner)
    success = assertEquals("Row sum: ", 188, sumUnmarked) and success
    success = assertEquals("Last number: ", 24, lastNumber) and success
    if success:
        print("Test 1 OK")
    else:
        print("Test 1 KO")


def test2(numbers, players):
    print(f"Running test2:")
    (sumUnmarked, lastNumber) = playBingo2(numbers, players)
    success = assertEquals("Row sum: ", 148, sumUnmarked)
    success = assertEquals("Last number: ", 13, lastNumber) and success
    if success:
        print("Test 2 OK")
    else:
        print("Test 2 KO")


def test():
    (numbers, players) = read_file(TEST_FILE)
    test1(numbers, players)
    test2(numbers, players)


# ---- CHALLENGE SOLVER FUNCTION -------


def part1(numbers, players):
    print("Running part 1:")
    (winner, sumUnmarked, lastNumber) = playBingo(numbers, players)
    print(f"Sum : {sumUnmarked}")
    print(f"Last Number : {lastNumber}")
    print(f"Res : {sumUnmarked * lastNumber}")


def part2(numbers, players):
    print("Running part 2:")
    (sumUnmarked, lastNumber) = playBingo2(numbers, players)
    print(f"Sum : {sumUnmarked}")
    print(f"Last Number : {lastNumber}")
    print(f"Res : {sumUnmarked * lastNumber}")


def solve():
    (numbers, players) = read_file(INPUT_FILE)
    part1(numbers, players)
    part2(numbers, players)


def main():
    test()
    print("-------------")
    solve()


if __name__ == '__main__':
    main()
