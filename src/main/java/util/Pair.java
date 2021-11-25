package util;

public class Pair<T> {
  public T left;
  public T right;

  public Pair(T left, T right) {
    this.left = left;
    this.right = right;
  }

  public T getKey() {
    return left;
  }

  public T getValue() {
    return right;
  }
}
