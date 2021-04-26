package edu.nuist.qdb.similarcheckor;

public class CHNFilter {
	public static String filter(String str){
		return str.replaceAll( "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , " ");  
	}
}
