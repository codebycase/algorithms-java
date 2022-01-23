package a10_recursion_greedy_invariant;

public class MaximumPointsYouCanObtainFromCards {
  public int maxScore2(int[] cardPoints, int k) {
    if (cardPoints.length < k)
      return 0;

    int leftScore = 0;
    for (int i = 0; i < k; i++) {
      leftScore += cardPoints[i];
    }

    int maxScore = leftScore;
    int rightScore = 0;
    for (int i = k - 1; i >= 0; i--) {
      leftScore -= cardPoints[i];
      rightScore += cardPoints[cardPoints.length - (k - i)];
      maxScore = Math.max(maxScore, leftScore + rightScore);
    }

    return maxScore;
  }

  public int maxScore3(int[] cardPoints, int k) {
    int totalScore = 0;
    for (int c : cardPoints) {
      totalScore += c;
    }

    if (k == cardPoints.length) {
      return totalScore;
    }

    int minSubarrayScore = totalScore;
    int presentSubarrayScore = 0;
    int startIndex = 0;
    for (int i = 0; i < cardPoints.length; i++) {
      presentSubarrayScore += cardPoints[i];
      int windowLength = i - startIndex + 1;
      if (windowLength == cardPoints.length - k) {
        minSubarrayScore = Math.min(minSubarrayScore, presentSubarrayScore);
        presentSubarrayScore -= cardPoints[startIndex++];
      }
    }

    return totalScore - minSubarrayScore;
  }
}
