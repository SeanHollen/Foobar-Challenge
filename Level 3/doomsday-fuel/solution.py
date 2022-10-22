# This solution hasn't been battle-tested. 
# I simply re-wrote my Java code in Python for fun.

def solution(m):
  terminal_states_list = []
  for a in range(0, len(m)):
    rowASum = sum(m[a])
    if rowASum == 0:
      terminal_states_list.append(a)
      continue
    rowASum -= m[a][a]
    m[a][a] = 0
    for b in range(0, len(m)):
      if m[b][a] == 0: continue
      for c in range(0, len(m)):
        if c == a: continue
        m[b][c] *= rowASum
        m[b][c] += m[a][c] * m[b][a]
      m[b][a] = 0
      simplify(m[b])
  result = []
  for item in terminal_states_list:
    result.append(m[0][item])
  result.append(sum(m[0]))
  if result[-1] == 0:
    result[0] = 1
    result[-1] = 1
  return result

def simplify(m):
  gcd_curr = m[0]
  for i in range(1, len(m)):
    gcd_curr = gcd(gcd_curr, m[i])
  for i in range(0, len(m)):
    m[i] /= gcd_curr

def gcd(a, b): return a if b == 0 else gcd(b, a % b)
