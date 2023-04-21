package bomb;

import common.IntList;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BombMain {
    public static void main(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        // TODO: Find the correct passwords to each phase using debugging techniques
        Bomb b = new Bomb();
        if (phase >= 0) {
            b.phase0("39291226");
        }
        if (phase >= 1) {

            IntList list = IntList.of(0,9,3,0,8);
            assert list != null;
            list.print();
            b.phase1(list); // Figure this out too
        }
        if (phase >= 2) {
            Random r = new Random(1337);
            Set<Integer> numbers = new HashSet<>();
            while (numbers.size() < 100000) {
                numbers.add(r.nextInt());
            }
            Object[] arr = numbers.toArray();
            int targetI = (int) arr[1337];
            String str = "p ".repeat(1337) + String.valueOf(targetI);

            b.phase2(str);
        }
    }
}
