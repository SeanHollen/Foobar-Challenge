def solution(s):
    right_goers = 0
    total = 0
    for c in s:
        if c == '>':
            right_goers += 1
        elif c == '<':
            total += right_goers * 2
    return total
    
# And only 9 lines! Isn't it cute.