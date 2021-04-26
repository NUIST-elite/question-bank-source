package edu.nuist.qdb.similarcheckor;
import java.util.HashSet;


public class MinHash {
	public static int[][] getHashFunctions(int wordsLength){
		int[][] functions = new int[(int) (wordsLength * 50)][2];
		HashSet<String> tmp = new HashSet<String>();
		
		int index = 0;
		while( tmp.size() < functions.length ){
			int a = (int)(Math.random()*100);
			int b = (int)(Math.random()*100);
			
			if( tmp.contains( a + "-" +b) ) continue;
			else{
				tmp.add(a + "-" +b);
				functions[ index ][0] = a;
				functions[ index ][1] = b;
				index ++;
			}
		}
		return functions;
	}
	
	public static int hash(int[] theta, int x, int wordsLength){
		return (theta[0] * x + theta[1]) % wordsLength;
	}
	
	public static int[][] mapDic(String[] str1, String[] str2){
		HashSet<String> tmp = new HashSet<String>();
		for(String s : str1) tmp.add(s);
		for(String s : str2) tmp.add(s);
		
		String[] dic = tmp.toArray(new String[0]);
		
		int[][] vector = new int[dic.length][2];
		
		for(int i=0; i<dic.length; i++){
			for(String s : str1){
				if(s.equals(dic[i])) vector[i][0] = 1;
			}
			for(String s : str2){
				if(s.equals(dic[i])) vector[i][1] = 1;
			}
		}
		return vector;
	}
	
	public static double sim(String[] s1, String[] s2){
		int[][] vectors = mapDic(s1, s2);
		int[][] hashParams =getHashFunctions( vectors.length );
		
		int[][] minHash = new int[hashParams.length][2];
		
		for(int i=0; i<hashParams.length; i++){
			minHash[i][0] = vectors.length;			minHash[i][1] = vectors.length;
		}
		
		for(int i=0; i<vectors.length; i++){
			if(vectors[i][0] == 1){
				for(int j=0; j<hashParams.length; j++){
					int x = hash( hashParams[j], i, vectors.length);
					if( minHash[j][0] > x ) minHash[j][0] = x;
				}
			}
			if(vectors[i][1] == 1){
				for(int j=0; j<hashParams.length; j++){
					int x = hash( hashParams[j], i, vectors.length);
					if( minHash[j][1] > x ) minHash[j][1] = x;
				}
			}
		}
		int count = 0;
		for(int i=0; i<hashParams.length; i++){
			if( minHash[i][0] == minHash[i][1]){
				count ++;
			};
		}
	
		return count * 1.0 / hashParams.length;
	}
	
	
	public static double sim(String str1, String str2){
		String[] s1 = WordTokenizer.cut(str1);
		String[] s2 = WordTokenizer.cut(str2);
		return sim(s1, s2);
	}
	
	public static void main(String[] args){
		 int x = hash(getHashFunctions(20)[1], 8 , 20);
		 String s1 = "如果想成为一个好的程序员，甚至架构师、技术总监等，显然只精通一种编程语言是不够的，还应该在常见领域学会几门编程语言，正如我们要成为高级人才不仅要会中文还要会英文，甚至还要会德文、俄文一样，其实是每一种语言都给人另一种思维方式，编程语言也一样。 ";
		 String s2 = "如何成为优秀的程序员、架构师或者技术总监呢？这意味着我们需要精通多种程序语言，就好比高级人才需要精通多种语言一样。";
					
		 System.out.println(sim( s1, s2));
	}
}
