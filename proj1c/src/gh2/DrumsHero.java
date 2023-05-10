package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class DrumsHero {
    private static final double CONCERT_A = 440.0;
    private static final String keyboard="q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        Drums[] strArr = getDrumsStringArr();

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int keyIdx = keyboard.indexOf(key);
                if(keyIdx == -1) {
                    continue;
                } else {
                    strArr[keyIdx].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (Drums instrString : strArr) {
                sample += instrString.sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (Drums instrString : strArr) {
                instrString.tic();
            }
        }
    }

    private static double getFrequency(int i) {
        return CONCERT_A * Math.pow(2, (double) (i - 24) /12);
    }

    private static Drums[] getDrumsStringArr() {
        int len = keyboard.length();
        Drums[] arr = new Drums[len];
        for(int i=0; i<len; i++) {
            arr[i] = new Drums(getFrequency(i));
        }
        return arr;
    }
}


