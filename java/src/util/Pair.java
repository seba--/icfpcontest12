package util;

public class Pair<A, B> {
  public A a;
  public B b;
  
  public Pair (A a, B b) {
    this.a = a;
    this.b = b;
  }
  
  public static <A, B> Pair<A, B> create(A a, B b) {
    return new Pair(a, b);
  }
  
  public String toString() {
    return "(" + a.toString() + ", " + b.toString() + ")";
  }
}
