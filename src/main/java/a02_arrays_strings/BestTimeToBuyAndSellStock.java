package a02_arrays_strings;

import java.util.Arrays;

/**
 * <pre>
Say you have an array for which the ith element is the price of a given stock on day i.

If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock), design an algorithm to find the maximum profit or minimum lose.

Example 1:
Input: [7, 1, 5, 3, 6, 4]
Output: 5

max. difference = 6-1 = 5 (not 7-1 = 6, as selling price needs to be larger than buying price)
Example 2:
Input: [7, 6, 4, 3, 1]
Output: 0

In this case, no transaction is done, i.e. max profit = 0.
 * </pre>
 * 
 * @author lchen
 *
 */
public class BestTimeToBuyAndSellStock {
  // track max current and also cover minimum lose
  public int maxProfitWithSingleTransaction(int[] prices) {
    if (prices.length < 2)
      return 0;
    int maxCur = Integer.MIN_VALUE, maxSoFar = Integer.MIN_VALUE;
    for (int i = 1; i < prices.length; i++) {
      int diff = prices[i] - prices[i - 1];
      maxCur = maxCur >= 0 ? maxCur + diff : diff;
      maxSoFar = Math.max(maxCur, maxSoFar);
    }
    return maxSoFar;
  }

  // track min price, and also cover minimum lose
  public int maxProfitWithSingleTransaction2(int[] prices) {
    if (prices.length < 2)
      return 0;
    int minPrice = Integer.MAX_VALUE, maxProfit = Integer.MIN_VALUE;
    for (int price : prices) {
      maxProfit = Math.max(maxProfit, price - minPrice);
      minPrice = Math.min(minPrice, price);
    }
    return maxProfit;
  }

  public int maxProfitWithMax2Transactions(int[] prices) {
    int buy1 = Integer.MIN_VALUE, sell1 = 0;
    int buy2 = Integer.MIN_VALUE, sell2 = 0;
    for (int i = 0; i < prices.length; i++) {
      buy1 = Math.max(buy1, 0 - prices[i]);
      sell1 = Math.max(sell1, buy1 + prices[i]);
      buy2 = Math.max(buy2, sell1 - prices[i]);
      sell2 = Math.max(sell2, buy2 + prices[i]);
    }
    return sell2;
  }

  public int maxProfitWithMax2Transactions2(int[] prices) {
    int maxProfit = 0;
    int[] firstBuySellProfits = new int[prices.length];

    // forward path
    int minPrice = Integer.MAX_VALUE;
    for (int i = 0; i < prices.length; i++) {
      minPrice = Math.min(minPrice, prices[i]);
      maxProfit = Math.max(maxProfit, prices[i] - minPrice);
      firstBuySellProfits[i] = maxProfit;
    }

    // backward path
    int maxPrice = Integer.MIN_VALUE;
    for (int i = prices.length - 1; i > 0; i--) {
      maxPrice = Math.max(maxPrice, prices[i]);
      maxProfit = Math.max(maxProfit, maxPrice - prices[i] + firstBuySellProfits[i - 1]);
    }

    return maxProfit;
  }

  /*
   * DP to store the previous result to reduce redundant calculations.
   * (k >= len / 2) means we will not reach the limit no matter how we try, can simply deal as max transactions.
   * Time Complexity: O(nk) if 2k ≤ n, O(n) if 2k > n where n is the length of the prices sequence, since we have two for-loop.
   * Space Complexity: O(nk) without state-compressed, and O(k) with state-compressed, where n is the length of the prices sequence.
   */
  public int maxProfitWithMaxKTransactions(int k, int[] prices) {
    int len = prices.length;
    if (k >= len / 2)
      return quickSolve(prices);

    int[][] t = new int[k + 1][len];
    for (int i = 1; i <= k; i++) {
      int tmpMax = 0 - prices[0];
      for (int j = 1; j < len; j++) {
        t[i][j] = Math.max(t[i][j - 1], prices[j] + tmpMax);
        tmpMax = Math.max(tmpMax, t[i - 1][j - 1] - prices[j]);
      }
    }

    return t[k][len - 1];
  }

  public int maxProfitWithMaxKTransactions2(int k, int[] prices) {
    int len = prices.length;
    if (k >= len / 2)
      return quickSolve(prices);

    int[] minPrices = new int[k + 1];
    Arrays.fill(minPrices, Integer.MAX_VALUE);
    int[] maxProfits = new int[k + 1];

    for (int price : prices) {
      for (int i = k; i > 0; i--) {
        maxProfits[i] = Math.max(maxProfits[i], price - minPrices[i]);
        minPrices[i] = Math.min(minPrices[i], price - (i > 0 ? maxProfits[i - 1] : 0));
      }
    }

    return maxProfits[maxProfits.length - 1];
  }

  /*
   * Simple One Pass: Crawling over the slope and keep on adding the profit obtained from every consecutive transaction.
   */
  public int maxProfitWithMaxTransactions(int[] prices) {
    int maxProfit = 0;
    for (int i = 1; i < prices.length; i++) {
      if (prices[i] > prices[i - 1]) {
        maxProfit += prices[i] - prices[i - 1];
      }
    }
    return maxProfit;
  }

  /* 
   * Peak Valley Approach: The key point is we need to consider every peak immediately following a valley to maximize the profit. 
   */
  public int maxProfitWithMaxTransactions2(int[] prices) {
    int i = 0;
    int valley = prices[0];
    int peak = prices[0];
    int maxProfit = 0;
    while (i < prices.length - 1) {
      while (i < prices.length - 1 && prices[i] >= prices[i + 1])
        i++;
      valley = prices[i];
      while (i < prices.length - 1 && prices[i] <= prices[i + 1])
        i++;
      peak = prices[i];
      maxProfit += peak - valley;
    }
    return maxProfit;
  }

  /*
   * To transition from the i-th day to the i+1-th day, we either sell our stock cash = max(cash, hold
   * + prices[i] - fee) or buy a stock hold = max(hold, cash - prices[i])
   */
  public int maxProfitWithMaxTransactionsAndFee(int[] prices, int fee) {
    int cash = 0; // max profit if we sell in past
    int hold = -prices[0]; // max profit if we buy in past
    for (int i = 1; i < prices.length; i++) {
      cash = Math.max(cash, hold + prices[i] - fee); // Let's sell and find max profit
      hold = Math.max(hold, cash - prices[i]); // Let's buy and find max profit
    }
    return cash;
  }

  /**
   * Given a list stock prices (i.e. price[0...n]), our agent would walk through each price point one
   * by one. At each point, the agent would be in one of three states (i.e. held, sold and reset) that
   * we defined before. And at each point, the agent would take one of the three actions (i.e. buy,
   * sell and rest), which then would lead to the next state at the next price point.
   */
  public int maxProfitWithMaxTransactionsAndCooldown(int[] prices) {
    int sold = Integer.MIN_VALUE, held = Integer.MIN_VALUE, reset = 0;

    for (int price : prices) {
      int preSold = sold;

      sold = held + price;
      held = Math.max(held, reset - price);
      reset = Math.max(reset, preSold);
    }

    return Math.max(sold, reset);
  }

  public int maxProfitWithMaxTransactionsAndCooldown2(int[] prices) {
    int sell = 0, prevSell = 0;
    int buy = Integer.MIN_VALUE, prevBuy;
    for (int price : prices) {
      prevBuy = buy;
      buy = Math.max(prevSell - price, prevBuy);
      prevSell = sell;
      sell = Math.max(prevBuy + price, prevSell);
    }
    return sell;
  }

  private int quickSolve(int[] prices) {
    int len = prices.length, profit = 0;
    for (int i = 1; i < len; i++) {
      if (prices[i] > prices[i - 1])
        profit += prices[i] - prices[i - 1];
    }
    return profit;
  }

  public static void main(String[] args) {
    BestTimeToBuyAndSellStock solution = new BestTimeToBuyAndSellStock();
    int[] prices = new int[] { 310, 315, 275, 295, 260, 270, 290, 230, 255, 250 };
    assert solution.maxProfitWithSingleTransaction(prices) == 30;
    assert solution.maxProfitWithSingleTransaction2(prices) == 30;
    prices = new int[] { 7, 1, 5, 3, 6, 4 };
    assert solution.maxProfitWithSingleTransaction(prices) == solution.maxProfitWithSingleTransaction2(prices);

    prices = new int[] { 12, 11, 13, 9, 12, 8, 14, 13, 15 };
    assert solution.maxProfitWithMax2Transactions(prices) == solution.maxProfitWithMax2Transactions2(prices);
    assert solution.maxProfitWithMax2Transactions(prices) == solution.maxProfitWithMaxKTransactions(2, prices);
    assert solution.maxProfitWithMaxKTransactions(3, prices) == solution.maxProfitWithMaxKTransactions2(3, prices);
    prices = new int[] { 1 };
    System.out.println(solution.maxProfitWithSingleTransaction2(prices));
  }
}
