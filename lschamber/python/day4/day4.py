# Files
INPUT_FILE = "./input.txt"
TEST_FILE = "./test.txt"


# -------- INPUT PROCESSING ------------


# Read the text file to get the list of numbers played and a List of 2D list for players grid
def read_file(path):
    with open(path, "r") as file:
        numbers = [int(x) for x in file.readline().split(",")]
        players = []
        current_player = None
        for line in file.readlines():
            if line == "\n":
                if current_player is not None:
                    players.append(current_player)
                current_player = []
            else:
                current_player.append([int(x) for x in line.strip().split(" ") if x != ""])
        players.append(current_player)
    return numbers, players


# -------- LOGIC & FUN  --------------

# Find all the lines and row for players.
# THis allows processing simple list of row, rather than dealing with rows and cols
def compute_rows_and_lines(players):
    rows_and_lines_for_player = []
    for player in players:
        # Add All lines in the list
        row_and_line = player.copy()
        for i in range(len(player[0])):
            row_and_line.append([row[i] for row in player])
        rows_and_lines_for_player.append(row_and_line)
    return rows_and_lines_for_player


# Sum all unmarked (remaining) values. Need to /2 as there is all unmarked duplicated ( Row and Lines )
def sum_unmarked(player_rows_and_lines):
    return sum([sum(x) for x in player_rows_and_lines]) / 2


# Play bingo and return  the first winner, the sum of unmarked element, and last number played
def play_bingo(numbers, players):
    player_row_and_lines_initial = compute_rows_and_lines(players)
    players = player_row_and_lines_initial.copy()
    for currentNumber in numbers:
        # Remove currentNumber
        players = [[list(filter(lambda x: x != currentNumber, row)) for row in playerRows] for playerRows in players]
        # check winner
        for indexPlayer, playerRows in enumerate(players):
            for indexRow, row in enumerate(playerRows):
                if len(row) == 0:
                    return indexPlayer, sum_unmarked(players[indexPlayer]), currentNumber
    return 0, 0, 0


# Check if a player has won
def has_won(player_rows):
    for row in player_rows:
        if len(row) == 0:
            return True
    return False


# Play bingo and return when the last player won
def play_bingo2(numbers, players):
    player_row_and_lines_initial = compute_rows_and_lines(players)
    players = player_row_and_lines_initial.copy()
    for currentNumber in numbers:
        # Remove currentNumber
        players = [[list(filter(lambda x: x != currentNumber, row)) for row in playerRows] for playerRows in players]
        # If the player the last one to win
        if len(players) == 1 and has_won(players[0]):
            return sum_unmarked(players[0]), currentNumber
        # Else remove potential winners
        else:
            # check winner and remove if there is one
            players = [player for player in players if not has_won(player)]
    return 0, 0


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assert_equals(test_name, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {test_name} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {test_name} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1(numbers, players):
    print(f"Running test1:")
    (winner, sumUnmarked, lastNumber) = play_bingo(numbers, players)
    success = assert_equals("Winner: ", 2, winner)
    success = assert_equals("Row sum: ", 188, sumUnmarked) and success
    success = assert_equals("Last number: ", 24, lastNumber) and success
    if success:
        print("Test 1 OK")
    else:
        print("Test 1 KO")


def test2(numbers, players):
    print(f"Running test2:")
    (sumUnmarked, lastNumber) = play_bingo2(numbers, players)
    success = assert_equals("Row sum: ", 148, sumUnmarked)
    success = assert_equals("Last number: ", 13, lastNumber) and success
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
    (winner, sumUnmarked, lastNumber) = play_bingo(numbers, players)
    print(f"Sum : {sumUnmarked}")
    print(f"Last Number : {lastNumber}")
    print(f"Res : {sumUnmarked * lastNumber}")


def part2(numbers, players):
    print("Running part 2:")
    (sumUnmarked, lastNumber) = play_bingo2(numbers, players)
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
