package a18_the_honors_question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class OptimalAccountBalancing {
  public int minTransfers(int[][] transactions) {
    List<Integer> balanceList = getBalanceList(transactions);

    // Map the indices of balances for quick lookup to prune
    Map<Integer, TreeSet<Integer>> balanceToIdxMap = new HashMap<>();
    for (int i = 0; i < balanceList.size(); i++) {
      balanceToIdxMap.computeIfAbsent(balanceList.get(i), k -> new TreeSet<>()).add(i);
    }

    return backTrackTransactions(balanceList, 0, balanceToIdxMap);
  }

  private int backTrackTransactions(List<Integer> balanceList, int position, Map<Integer, TreeSet<Integer>> balanceToIdxMap) {
    if (position == balanceList.size())
      return 0;

    int currBalance = balanceList.get(position);

    if (currBalance == 0) {
      // Skip to proceed with next one
      return backTrackTransactions(balanceList, position + 1, balanceToIdxMap);
    }

    // First look for lowest index that is higher than current position, but has the opposite balance.
    int nextBalance = -currBalance;
    if (balanceToIdxMap.containsKey(nextBalance)) {
      TreeSet<Integer> set = balanceToIdxMap.get(nextBalance);
      Integer idx = set.ceiling(position); // lowest index
      if (idx != null) {
        set.remove(idx);
        balanceList.set(idx, 0);
        int transactions = backTrackTransactions(balanceList, position + 1, balanceToIdxMap) + 1;
        set.add(idx);
        balanceList.set(idx, nextBalance);
        return transactions;
      }
    }

    // Second go through the rest of the list if not found an optimal balance,
    int minTransactions = balanceList.size() - 1; // highest possible solutions
    for (int i = position + 1; i < balanceList.size(); i++) {
      nextBalance = balanceList.get(i);
      if (nextBalance * currBalance >= 0)
        continue; // Skip for the same side
      balanceList.set(i, currBalance + nextBalance);
      int transactions = backTrackTransactions(balanceList, position + 1, balanceToIdxMap) + 1;
      minTransactions = Math.min(minTransactions, transactions);
      balanceList.set(i, nextBalance);
    }

    return minTransactions;
  }

  // Just need a balance list as no need to care about account ids
  private List<Integer> getBalanceList(int[][] transactions) {
    Map<Integer, Integer> balanceMap = new HashMap<>();

    for (int[] transaction : transactions) {
      balanceMap.put(transaction[0], balanceMap.getOrDefault(transaction[0], 0) + transaction[2]);
      balanceMap.put(transaction[1], balanceMap.getOrDefault(transaction[1], 0) - transaction[2]);
    }

    // Need a list here, able to get value at specified index
    List<Integer> balancesList = new ArrayList<>();
    for (Map.Entry<Integer, Integer> standing : balanceMap.entrySet()) {
      balancesList.add(standing.getValue());
    }

    return balancesList;
  }

  public static void main(String[] args) {
    int[][] transactions = { { 0, 1, 10 }, { 1, 0, 1 }, { 1, 2, 5 }, { 2, 0, 5 } };
    OptimalAccountBalancing solution = new OptimalAccountBalancing();
    assert solution.minTransfers(transactions) == 1;
  }
}
