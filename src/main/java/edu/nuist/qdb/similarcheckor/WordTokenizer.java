package edu.nuist.qdb.similarcheckor;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

import edu.nuist.qdb.util.FileUtil;
import edu.nuist.qdb.util.LineProcess;

class LoadStopWords extends LineProcess{
	HashSet<String> stopWords = new HashSet<String>();
	@Override
	public void doLine(String line) {
		stopWords.add(line);
	}
	public HashSet<String> getStopWords() {
		return stopWords;
	}
}

public class WordTokenizer {
	public static JiebaSegmenter segmenter = new JiebaSegmenter();
	public static HashSet<String> stopWords = new HashSet<String>();
	
	static{
		LoadStopWords l = new LoadStopWords();
		URL resource = WordTokenizer.class.getClassLoader().getResource("stopword.txt");  
        String path = resource.getPath();  
		FileUtil.processInLine( path, l);
		stopWords = l.getStopWords();
	}
	public static String[] cut(String str){

		List<SegToken> words = segmenter.process( CHNFilter.filter(str) , SegMode.SEARCH);
		
		List<String> rst = new LinkedList<String>();
		for(SegToken t : words){
			if( !t.word.equals(" ") && !stopWords.contains(t.word) )
				rst.add(t.word);
		}
		return rst.toArray(new String[0]);
	}	
	
	public static void main(String[] args){
		
	}
}
