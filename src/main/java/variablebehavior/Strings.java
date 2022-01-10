package variablebehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

interface Criterion {
  boolean test(String s);
}

//class SecondHalf implements Criterion {
class SecondHalf implements Predicate<String> {
  @Override
  public boolean test(String s) {
    return s.charAt(0) > 'M';
  }
}

class StringSelector {
  public StringSelector(Predicate<String> crit) {
    this.crit = crit;
  }
  private Predicate<String> crit;

  protected void setSelectionCriterion(Predicate<String> ps) {
    this.crit = ps;
  }

  public List<String> select(List<String> ls) {
    List<String> res = new ArrayList<>();
    for (String s : ls) {
      if (crit.test(s)) {
        res.add(s);
      }
    }
    return res;
  }
}

public class Strings {
//  public static List<String> select(List<String> ls) {
//    List<String> res = new ArrayList<>();
//    for (String s : ls) {
//      if (s.length() > 3) {
//        res.add(s);
//      }
//    }
//    return res;
//  }
//
//  public static List<String> select(List<String> ls, Criterion crit) {
  public static List<String> select(List<String> ls, Predicate<String> crit) {
    List<String> res = new ArrayList<>();
    for (String s : ls) {
      if (crit.test(s)) {
        res.add(s);
      }
    }
    return res;
  }

  public static void main(String[] args) {
    List<String> ls = List.of("Fred", "Jim", "Sheila");
//    System.out.println("Long strings " + select(ls));
    // this pattern separates:
    // selection (iteration etc. body of loop) logic
    // from *selection criterion*
    // it's the *caller's* responsibility to specify the
    // criterion logic with each call
    // this is typically called "command" pattern
    // also an example of "higher order function"
    System.out.println("Second-half strings "
        + select(ls, new SecondHalf()));
    System.out.println("Second-half strings "
        + select(ls, s -> s.charAt(0) > 'M'));
    System.out.println("First-half strings "
        + select(ls, s -> s.charAt(0) <= 'M'));

    // Initial configuration chooses a selection criterion
    // (there might be a mechanism to change this later)
    // so caller cannot influence this
    // not caller's responsibility, NOR authority
    // Strategy pattern.
    StringSelector selector = new StringSelector(s -> s.length() < 4);

    System.out.println("selected strings " + selector.select(ls));
    System.out.println("selected strings " + selector.select(
        List.of("Aardvark", "it", "an", "I", "Horse")
    ));

    selector.setSelectionCriterion(s -> s.length() < 6);
    System.out.println("selected strings " + selector.select(ls));
    System.out.println("selected strings " + selector.select(
        List.of("Aardvark", "it", "an", "I", "Horse")
    ));

  }
}
