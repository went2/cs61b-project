import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MyMapExercises {
  public static int passNums = 0;
  public static int failNums = 0;

  public static void main(String[] args) {
    testLetterToNum();
    testSquares();
    testCountWords();

    System.out.println("======");
    System.out.println("test complete" + "total pass " + passNums + " total failed " + failNums);
  }

  public static void testLetterToNum() {
    System.out.println("======");
    System.out.println("Test letterToNum");

    Map<Character, Integer> map = MapExercises.letterToNum();
    int passedNum = 0;
    for(int i=0; i < 26; i++) {
      if(map.get((char)('a' + i)) == i + 1) {
        passedNum++;
      } else {
        break;
      }
    }
    if(passedNum == 26) {
      recordPass("testLetterToNum pass");
    } else {
      recordFail("testLetterToNum fail");
    }
  }

  public static void testSquares() {
    System.out.println("======");
    System.out.println("Test Squares");

    List<Integer> emptyList = new ArrayList<>();

    Map<Integer, Integer> map1 = MapExercises.squares(List.of(1,2,3,4));
    List<Integer> list1 = List.of(1,4,9,16);
    Collection<Integer> collection = map1.values();
    if(collection.containsAll(list1)) {
      recordPass("testSquares1 pass");
    } else {
      recordFail("testSquares1 fail");
    }

    Map<Integer, Integer> map2 = MapExercises.squares(emptyList);
    if(map2.isEmpty()) {
      recordPass("testSquares2 pass");
    } else {
      recordFail("testSquares2 fail");
    }
  }

  public static void testCountWords() {
    System.out.println("======");
    System.out.println("Test CountWords");

    List<String> list1 = List.of("hug", "hug", "hug", "hug",
    "shreyas", "shreyas", "shreyas",
    "ergun", "ergun",
    "cs61b");

    Map<String, Integer> resultMap = MapExercises.countWords(list1);
    Map<String, Integer> correctMap = Map.of(
      "hug", 4,
      "shreyas", 3,
      "ergun", 2,
      "cs61b", 1
    );

    if(resultMap.equals(correctMap)) {
      recordPass("testCountWords pass");
    } else {
      recordFail("testCountWords failed");
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
