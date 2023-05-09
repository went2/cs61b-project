package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final String keyboard="q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
//        GuitarString[] strArr = getGuitarStringArr();
        Harp[] strArr = getHarpStringArr();

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
            for (Harp instrString : strArr) {
                sample += instrString.sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (Harp instrString : strArr) {
                instrString.tic();
            }
        }
    }

    private static double getFrequency(int i) {
        return CONCERT_A * Math.pow(2, (double) (i - 24) /12);
    }

    private static GuitarString[] getGuitarStringArr() {
        int len = keyboard.length();
        GuitarString[] arr = new GuitarString[len];
        for(int i=0; i<len; i++) {
            arr[i] = new GuitarString(getFrequency(i));
        }
        return arr;
    }

    private static Harp[] getHarpStringArr() {
        int len = keyboard.length();
        Harp[] arr = new Harp[len];
        for(int i=0; i<len; i++) {
            arr[i] = new Harp(getFrequency(i));
        }
        return arr;
    }
}


