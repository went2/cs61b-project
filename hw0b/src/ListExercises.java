import java.util.List;
import java.util.ArrayList;

public class ListExercises {

  /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
    if(L.isEmpty()) { 
      return 0;
    }
    int sum = 0;
    for(int n : L) {
      sum += n;
    }
    return sum;
  }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
      List<Integer> list = new ArrayList<>();
      if(L.isEmpty()) {
        return list;
      }
      for(int num : L) {
        if(num % 2 == 0) {
          list.add(num);
        }
      }
      return list;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
      List<Integer> resultList = new ArrayList<>();
      for(int num1 : L1) {
        for(int num2 : L2) {
          if(num1 == num2) {
            resultList.add(num1);
          }
        }
      }
      return resultList;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
      int occurs = 0;

      for(String str : words) {
        for(int i=0; i<str.length(); i++) {
          if(str.charAt(i) == c) {
            occurs++;
          }
        }
      }

      return occurs;
    }
}
