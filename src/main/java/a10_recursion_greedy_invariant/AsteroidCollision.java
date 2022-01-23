package a10_recursion_greedy_invariant;

import java.util.Stack;

public class AsteroidCollision {
  public int[] asteroidCollision(int[] asteroids) {

    Stack<Integer> ss = new Stack<>();

    for (int c : asteroids) {

      if (c > 0) {
        ss.push(c);

      } else {
        // while!!!!new c > ss.peek(), pop
        while (!ss.isEmpty() && ss.peek() > 0 && ss.peek() < Math.abs(c)) { // must peek instack >0, and rn we have <0
          ss.pop();
        }

        if (ss.isEmpty() || ss.peek() < 0) { // c < 0 its same direction!! just add to stack!!
          ss.push(c);

        } else if (!ss.isEmpty() && ss.peek() > 0 && Math.abs(c) == ss.peek()) { // same size, both boom
          ss.pop();
        }

      }
    }
    int[] res = new int[ss.size()];

    int i = 0;
    for (int c : ss) {
      res[i++] = c;

    }
    return res;
  }
}
