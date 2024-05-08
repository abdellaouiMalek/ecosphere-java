package tn.esprit.services;

import java.util.Arrays;
import java.util.List;

public class BadWordServices {
    public static List<String> BAD_WORDS = Arrays.asList("idiot", "noir", "naif");
    public static boolean containsBadWords(String text) {
        text = text.toLowerCase();
        for (String word : BAD_WORDS) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}


