package a06_sorting_searching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * You are given two string arrays username and website and an integer array timestamp. All the
 * given arrays are of the same length and the tuple [username[i], website[i], timestamp[i]]
 * indicates that the user username[i] visited the website website[i] at time timestamp[i].
 * 
 * A pattern is a list of three websites (not necessarily distinct).
 * 
 * For example, ["home", "away", "love"], ["leetcode", "love", "leetcode"], and ["luffy", "luffy",
 * "luffy"] are all patterns. The score of a pattern is the number of users that visited all the
 * websites in the pattern in the same order they appeared in the pattern.
 * 
 * For example, if the pattern is ["home", "away", "love"], the score is the number of users x such
 * that x visited "home" then visited "away" and visited "love" after that. Similarly, if the
 * pattern is ["leetcode", "love", "leetcode"], the score is the number of users x such that x
 * visited "leetcode" then visited "love" and visited "leetcode" one more time after that. Also, if
 * the pattern is ["luffy", "luffy", "luffy"], the score is the number of users x such that x
 * visited "luffy" three different times at different timestamps. Return the pattern with the
 * largest score. If there is more than one pattern with the same largest score, return the
 * lexicographically smallest such pattern.
 * 
 * 
 * <pre>
 * Example 1:
 * 
 * Input: username = ["joe","joe","joe","james","james","james","james","mary","mary","mary"], timestamp = [1,2,3,4,5,6,7,8,9,10], website = ["home","about","career","home","cart","maps","home","home","about","career"]
 * Output: ["home","about","career"]
 * Explanation: The tuples in this example are:
 * ["joe","home",1],["joe","about",2],["joe","career",3],["james","home",4],["james","cart",5],["james","maps",6],["james","home",7],["mary","home",8],["mary","about",9], and ["mary","career",10].
 * The pattern ("home", "about", "career") has score 2 (joe and mary).
 * The pattern ("home", "cart", "maps") has score 1 (james).
 * The pattern ("home", "cart", "home") has score 1 (james).
 * The pattern ("home", "maps", "home") has score 1 (james).
 * The pattern ("cart", "maps", "home") has score 1 (james).
 * The pattern ("home", "home", "home") has score 0 (no user visited home 3 times).
 * </pre>
 * 
 * 
 * https://leetcode.com/problems/analyze-user-website-visit-pattern/
 *
 */
public class UserWebsiteVisitPattern {

  public List<String> mostVisitedPattern(String[] usernames, int[] timestamps, String[] websites) {
    Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    for (int i = 0; i < timestamps.length; i++) {
      queue.offer(new int[] { timestamps[i], i });
    }

    Node root = new Node();
    while (!queue.isEmpty()) {
      int i = queue.poll()[1];
      // Add all to root first!
      root.usernames.add(usernames[i]);
      root.add(usernames[i], websites[i]);
    }

    Node current = root;
    List<String> result = new ArrayList<>();
    // Get the max website on each level
    while (!current.children.isEmpty()) {
      int maxCount = 0;
      String maxWebsite = "";
      for (Map.Entry<String, Node> entry : current.children.entrySet()) {
        Node node = entry.getValue();
        if (node.count > maxCount || (node.count == maxCount && entry.getKey().compareTo(maxWebsite) < 0)) {
          maxCount = node.count;
          maxWebsite = entry.getKey();
          current = node;
        }
      }
      result.add(maxWebsite);
    }

    return result;
  }

  private class Node {
    Map<String, Node> children;
    Set<String> usernames;
    int count;
    int level;

    public Node() {
      children = new HashMap<>();
      usernames = new HashSet<>();
    }

    public int add(String username, String website) {
      if (!usernames.contains(username)) {
        return 0;
      }

      // Bottom up and try to all 3 levels if applicable
      if (level < 2) {
        for (String key : children.keySet()) {
          count = Math.max(count, children.get(key).add(username, website));
        }
      }

      Node childNode = null;

      // Add to website node if not exists
      if (!children.containsKey(website)) {
        childNode = new Node();
        childNode.level = level + 1;
        children.put(website, childNode);
      }

      // Add to user names if not exists
      childNode = children.get(website);
      if (!childNode.usernames.contains(username)) {
        if (childNode.level == 3) {
          // This username reached here the first time
          childNode.count++;
        }
        childNode.usernames.add(username);
      }

      // Update level 2's count and up
      if (childNode.level == 3) {
        count = Math.max(count, childNode.count);
      }

      return count;
    }
  }
}
