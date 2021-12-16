# Files
from functools import reduce

INPUT_FILE = "./input.txt"

PACKET_TYPE_SUM = 0
PACKET_TYPE_PRODUCT = 1
PACKET_TYPE_MINIMUM = 2
PACKET_TYPE_MAXIMUM = 3
PACKET_TYPE_LITERAL = 4
PACKET_TYPE_GREATER = 5
PACKET_TYPE_LESSER = 6
PACKET_TYPE_EQUALS = 7

PACKET_TYPE_LENGTH_BITS = 0
PACKET_TYPE_LENGTH_SUB_PACKET = 1


# -------- INPUT PROCESSING ------------


def read_file(path):
    with open(path, "r") as file:
        return file.readline().strip()


# -------- LOGIC & FUN  --------------

def hex_to_binary(hex_str):
    return format(int(hex_str, 16), f"0{len(hex_str) * 4}b")


def bin_to_dec(binary):
    return int(binary, 2)


def read_header(packet_iter):
    version = bin_to_dec("".join([next(packet_iter) for _ in range(3)]))
    packet_type = bin_to_dec("".join([next(packet_iter) for _ in range(3)]))
    return 6, (version, packet_type)


def read_literal_value(packet_iter):
    nb_of_read = 0
    last_bit_processed = False
    literal_value_binary = ""
    while not last_bit_processed:
        current_bit = "".join([next(packet_iter) for _ in range(5)])
        nb_of_read += 5
        literal_value_binary += current_bit[1:]
        last_bit_processed = current_bit[0] == "0"
    return bin_to_dec(literal_value_binary), nb_of_read


def read_length_type_id(packet_iter):
    return bin_to_dec(next(packet_iter)), 1


def read_total_length(packet_iter):
    return int("".join([next(packet_iter) for _ in range(15)]), 2), 15


def read_total_sub_packets(packet_iter):
    return int("".join([next(packet_iter) for _ in range(11)]), 2), 11


def read_sub_packet_length(packet_iter):
    length, nb_of_reads = read_total_length(packet_iter)

    sub_packets_reads = 0
    sub_packets = []
    while length - sub_packets_reads >= 4:
        (sub_packet), packet_reads = read_packet(packet_iter)
        sub_packets_reads += packet_reads
        sub_packets.append(sub_packet)
    # Read missing zeros
    while length - sub_packets_reads > 0:
        next(packet_iter)
        sub_packets_reads += 1
    nb_of_reads += sub_packets_reads
    return sub_packets, nb_of_reads


def read_sub_packet_number(packet_iter):
    length, nb_of_reads = read_total_sub_packets(packet_iter)
    sub_packets = []
    for i in range(length):
        (packet), reads = read_packet(packet_iter)
        sub_packets.append(packet)
        nb_of_reads += reads
    return sub_packets, nb_of_reads


def read_sub_packets(packet_iter):
    length_type, nb_of_read = read_length_type_id(packet_iter)
    if length_type == PACKET_TYPE_LENGTH_BITS:
        sub_packets, packet_length_reads = read_sub_packet_length(packet_iter)
        nb_of_read += packet_length_reads
    elif length_type == PACKET_TYPE_LENGTH_SUB_PACKET:
        sub_packets, packet_length_reads = read_sub_packet_number(packet_iter)
        nb_of_read += packet_length_reads
    else:
        print(f"Packet length type {length_type} unknown ")
        exit(1)
    return sub_packets, nb_of_read


def compute_operator_packets(operation, sub_packets):
    packet_values = [sub_packet[2] for sub_packet in sub_packets]
    value = 0
    if operation == PACKET_TYPE_SUM:
        value = sum(packet_values)
    elif operation == PACKET_TYPE_PRODUCT:
        if len(packet_values) == 1:
            value = packet_values[0]
        else:
            value = reduce(lambda a, b: a * b, packet_values)
    elif operation == PACKET_TYPE_MINIMUM:
        value = min(packet_values)
    elif operation == PACKET_TYPE_MAXIMUM:
        value = max(packet_values)
    elif operation == PACKET_TYPE_GREATER:
        if len(packet_values) != 2:
            print("Error 2 packets expected for GREATER operation")
            exit(1)
        else:
            value = 1 if packet_values[0] > packet_values[1] else 0
    elif operation == PACKET_TYPE_LESSER:
        if len(packet_values) != 2:
            print("Error 2 packets expected for LESSER operation")
            exit(1)
        else:
            value = 1 if packet_values[0] < packet_values[1] else 0
    elif operation == PACKET_TYPE_EQUALS:
        if len(packet_values) != 2:
            print("Error 2 packets expected for GREATER operation")
            exit(1)
        else:
            value = 1 if packet_values[0] == packet_values[1] else 0
    return value


def read_packet(packet_iter):
    nb_of_read, (version, packet_type) = read_header(packet_iter)
    # Length and length_type and sub_packets is only relevant if type is not 4
    sub_packets = []
    if packet_type == PACKET_TYPE_LITERAL:
        value, value_reads = read_literal_value(packet_iter)
        nb_of_read += value_reads
    else:
        sub_packets, sub_packets_reads = read_sub_packets(packet_iter)
        nb_of_read += sub_packets_reads
        value = compute_operator_packets(packet_type, sub_packets)

    return (version, packet_type, value, sub_packets), nb_of_read


def sum_versions_number(packet):
    sum_version = packet[0]
    if packet[-1] is not None:
        for sub_packet in packet[-1]:
            sum_version += sum_versions_number(sub_packet)
    return sum_version


# -------- TEST ------------
# Check that expected and actual values are equals. Display error and return false if not.
def assert_equals(test_name, expected, actual):
    if expected == actual:
        print(f"\tSuccess - {test_name} \t|\t Result is '{actual}'")
        return True
    else:
        print(f"\tFailure - {test_name} \t|\t Expected '{expected}' but got '{actual}'")
        return False


def test1():
    print("Test 1")
    assert_equals("Binary cast 1", "110100101111111000101000", hex_to_binary("D2FE28"))
    assert_equals("Binary cast 2", "00111000000000000110111101000101001010010001001000000000",
                  hex_to_binary("38006F45291200"))
    assert_equals("Binary cast 3", "11101110000000001101010000001100100000100011000001100000",
                  hex_to_binary("EE00D40C823060"))

    print()

    (packet1_version, packet1_type, packet1_value, _), _ = read_packet(iter("110100101111111000101000"))
    assert_equals("Packet 1 version", 6, packet1_version)
    assert_equals("Packet 1 type", 4, packet1_type)
    assert_equals("Packet 1 value", 2021, packet1_value)

    print()

    (packet2_version, packet2_type, packet2_value, packet2_sub_packets), _ = (
        read_packet(iter("00111000000000000110111101000101001010010001001000000000")))
    assert_equals("Packet 2 version", 1, packet2_version)
    assert_equals("Packet 2 type", 6, packet2_type)
    assert_equals("Packet 2 sub_packet_value 1", 10, packet2_sub_packets[0][2])
    assert_equals("Packet 2 sub_packet_value 2", 20, packet2_sub_packets[1][2])
    print()

    (packet3_version, packet3_type, packet3_value, packet3_sub_packets), _ = (
        read_packet(iter("11101110000000001101010000001100100000100011000001100000")))
    assert_equals("Packet 3 version", 7, packet3_version)
    assert_equals("Packet 3 type", 3, packet3_type)
    assert_equals("Packet 3 sub_packet_value 1", 1, packet3_sub_packets[0][2])
    assert_equals("Packet 3 sub_packet_value 2", 2, packet3_sub_packets[1][2])
    assert_equals("Packet 3 sub_packet_value 3", 3, packet3_sub_packets[2][2])

    print()

    sum_tests = [
        ("8A004A801A8002F478", 16),
        ("620080001611562C8802118E34", 12),
        ("C0015000016115A2E0802F182340", 23),
        ("A0016C880162017C3686B18A3D4780", 31)
    ]
    for input_hex, output in sum_tests:
        (packet), _ = read_packet(iter(hex_to_binary(input_hex)))
        assert_equals(f"Version sum test {input_hex}", output, sum_versions_number(packet))


def test2():
    print("Test 2")
    values_tests = [
        ("C200B40A82", 3),
        ("04005AC33890", 54),
        ("880086C3E88112", 7),
        ("CE00C43D881120", 9),
        ("D8005AC2A8F0", 1),
        ("F600BC2D8F", 0),
        ("9C005AC2F8F0", 0),
        ("9C0141080250320F1802104A08", 1)
    ]
    for input_hex, output in values_tests:
        (packet), _ = read_packet(iter(hex_to_binary(input_hex)))
        assert_equals(f"Values test {input_hex}", output, packet[2])


# ---- CHALLENGE SOLVER FUNCTION -------


def solve1(data):
    print("Solve 1")
    (packet), _ = read_packet(iter(hex_to_binary(data)))
    print(f"Res {sum_versions_number(packet)}")


def solve2(data):
    print("Solve 2")
    (packet), _ = read_packet(iter(hex_to_binary(data)))
    print(f"Res: {packet[2]}")


def part1(data, ):
    test1()
    solve1(data)


def part2(data):
    test2()
    solve2(data)


if __name__ == '__main__':
    challenge_input = read_file(INPUT_FILE)
    part1(challenge_input)
    part2(challenge_input)
