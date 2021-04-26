package edu.nuist.qdb.similarcheckor;

public class SimilarCheckor {
    public static double check(String str1, String str2){
        String[] s1 = WordTokenizer.cut(str1);
		String[] s2 = WordTokenizer.cut(str2);
		return MinHash.sim(s1, s2);
    }
}
