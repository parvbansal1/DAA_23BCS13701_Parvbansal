// File: src/main/java/com/example/dp/Utils.java
package com.example.dp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    // parse forms like [1,2,3] or 1,2,3 into int[]
    public static int[] parseArrayFromCsvString(String s) {
        if (s == null) return new int[0];
        s = s.trim();
        if (s.startsWith("[")) s = s.substring(1);
        if (s.endsWith("]")) s = s.substring(0, s.length()-1);
        if (s.trim().isEmpty()) return new int[0];
        String[] parts = s.split("\\s*,\\s*");
        ArrayList<Integer> list = new ArrayList<>();
        for (String p : parts) {
            p = p.replaceAll("[^\\d-]", "");
            if (p.length() == 0) continue;
            list.add(Integer.parseInt(p));
        }
        return list.stream().mapToInt(i->i).toArray();
    }

    // reuse previous label parser for weights/values etc.
    public static int[] parseIntArrayFromLabel(String input, String label) {
        Pattern p = Pattern.compile("\\[([^\\]]+)\\]");
        Matcher m = p.matcher(input);
        if (m.find()) {
            return parseArrayFromCsvString("[" + m.group(1) + "]");
        }
        // fallback simple csv parse of whole string
        return parseArrayFromCsvString(input);
    }

    // simple wrapper to keep compatibility with the earlier code
    public static int[] parseIntArrayFromLabel(String input) {
        return parseIntArrayFromLabel(input, "");
    }

    // parse integer after label like 'capacity:5' or just extract first integer
    public static int parseIntFromLabel(String input, String label) {
        Pattern p = Pattern.compile(label + "\\s*:\\s*(-?\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        if (m.find()) return Integer.parseInt(m.group(1));
        // fallback find first number
        p = Pattern.compile("(-?\\d+)");
        m = p.matcher(input);
        if (m.find()) return Integer.parseInt(m.group(1));
        throw new IllegalArgumentException("No integer found in input");
    }

    // parse arrays from labeled strings like weights:[...] values:[...]
    public static int[] parseIntArrayFromLabel(String input, String label, boolean ignoreLabel) {
        return parseIntArrayFromLabel(input, label);
    }
}
