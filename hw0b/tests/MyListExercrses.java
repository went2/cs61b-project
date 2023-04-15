
import java.util.List;
import java.util.ArrayList;

public class MyListExercrses {
  public static int passNums = 0;
  public static int failNums = 0;
  public static void main(String[] args) {
    testSum();
    testEvens();
    testCommon();
    testCountOccurrencesOfC();

    System.out.println("======");
    System.out.println("test complete" + "total pass " + passNums + " total failed " + failNums);
  }

  public static void testSum() {
    List<Integer> list1 = List.of(1, 2, 3, 4);
    List<Integer> list2 = new ArrayList<>();

    System.out.println("======");
    System.out.println("Test sum correctness");
    if(ListExercises.sum(list1) == 10) recordPass("sum test1 [pass]");
    else recordFail("sum test1 [failed]");
    
    if(ListExercises.sum(list2) == 0) recordPass("sum test2 [pass]");
    else recordFail("sum test2 [failed]");
  }

  public static void testEvens() {
    List<Integer> list1 = List.of(34,1,18,9,20,8);
    List<Integer> list2 = new ArrayList<>();

    System.out.println("======");
    System.out.println("Test evens correctness");
    if(ListExercises.evens(list2).isEmpty()) {
      recordPass("even test1 [pass]");
    } else {
      recordFail("even test1 [failed]");
    }

    int passedInt = 0;
    List<Integer> evenedList= ListExercises.evens(list1);
    for(int num : evenedList) {
      if(num % 2 == 0) {
        passedInt++;
      }
    }
    if(passedInt == evenedList.size()) {
      recordPass("even test2 [pass]");
    } else {
      recordFail("even test2 [failed]");
    }
  }

  public static void testCommon() {
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = List.of(1,3,5,7,8);
    List<Integer> list3 = List.of(2,4,6,7,8);

    System.out.println("======");
    System.out.println("Test common correctness");

    if(ListExercises.common(list1, list2).isEmpty()) {
      recordPass("common test1 [pass]");
    } else {
      recordFail("common test1 [failed]");
    }

    if(ListExercises.common(list2, list3).equals(List.of(7,8))) {
      recordPass("common test2 [pass]");
    } else {
      recordFail("common test2 [failed]");
    }
  }

  public static void testCountOccurrencesOfC() {
    System.out.println("======");
    System.out.println("Test occurrences correctness");

    char c1 = 'z';
    char c2 = 'o';
    List<String> list1 = List.of("hello", "world", "good", "day");

    System.out.println("occurrences: " + ListExercises.countOccurrencesOfC(list1, c2));
    if(ListExercises.countOccurrencesOfC(list1, c2) == 4) {
      recordPass("occurrence test1 [pass]");
    } else {
      recordFail("occurrence test1 [failed]");
    }

    if(ListExercises.countOccurrencesOfC(list1, c1) == 0) {
      recordPass("occurrence test2 [pass]");
    } else {
      recordFail("occurrence test2 [failed]");
    }
  }

  public static void recordPass(String msg) {
    System.out.println(msg);
    passNums++;
  }

  public static void recordFail(String msg) {
    System.out.println(msg);
    failNums++;
  }
}
