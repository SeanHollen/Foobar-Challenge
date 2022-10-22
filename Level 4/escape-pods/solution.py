def solution(entrances, exits, path):
    # merge all entrances together
    merge_rooms(path, entrances, entrances[0])
    # merge all exits together
    replace_all_paths(path, exits, exits[0])
    # find maximum flow
    answer = ford_fulkerson(path, entrances[0], exits[0])
    return answer
    

def merge_rooms(paths, to_merge, merge_into):
    for room in to_merge:
        if room == merge_into:
            continue
        for i in range(0, len(paths)):
            paths[merge_into][i] += paths[room][i]
            paths[room][i] = 0

     
def replace_all_paths(paths, to_replace, replace_with):
    for path in paths:
        total = 0
        for n in to_replace:
            total += path[n]
            path[n] = 0
        path[replace_with] = total


def ford_fulkerson(capacities, source, end):
    flows = [[0 for y in range(len(capacities))] for x in range(len(capacities))]
    path = dfs_path(capacities, flows, source, end, [])
    while path is not None:
        flow = min(move[2] for move in path)
        for x, y, residual in path:
            flows[x][y] += flow
            flows[y][x] -= flow
        path = dfs_path(capacities, flows, source, end, [])
    return sum(flows[source])


def dfs_path(capacities, flows, curr_node, end_node, path):
    if curr_node == end_node:
        return path
    for i in range(0, len(capacities)):
        residual_capacity = capacities[curr_node][i] - flows[curr_node][i]
        move = (curr_node, i, residual_capacity)
        if residual_capacity > 0 and move not in path:
            result = dfs_path(capacities, flows, i, end_node, path + [move])
            if result is not None:
                return result

