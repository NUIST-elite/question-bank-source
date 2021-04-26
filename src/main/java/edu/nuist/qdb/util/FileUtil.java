package edu.nuist.qdb.util;

import java.io.*;
import java.net.URL;

public class FileUtil {
	public static void processInLine(String data, LineProcess lp){
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(data)));
			String line = br.readLine();
			while (line != null)
			{
				lp.doLine(line);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void writeStringToFile(String str, String fileName){
		File f = new File(fileName);
		try {
		    if(!f.exists()){
		    	f.createNewFile();
		    }
		    FileWriter fw = new FileWriter(f);
		    BufferedWriter out = new BufferedWriter(fw);
		    out.write(str, 0, str.length());
		    out.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getFileContent(String targetFile){	
		BufferedReader br = null;
	    try{	    	
		    String data = "";
		    String jsonString = "";
		    InputStreamReader isr = new InputStreamReader(new FileInputStream(targetFile), "utf-8");
	    	br = new BufferedReader(isr);  
		    data = br.readLine();
		    jsonString += data;
		    while( data != null){ 
		        data = br.readLine();
		        if(data != null) jsonString += data;
		    }  		
		    if("".equals(jsonString))
		    	return null;
		    else
		    	return jsonString.trim();
	   }catch(IOException e){
			//logger.error(" FileIOException getJSONFromFile - get JSON from the file : "+ targetFile + " IOException");
		}finally{
			if(br != null)
				try{
					br.close();
				}catch(IOException e){;}
		}	    
	    return null;
	} 

	public static void appendLine(String file, String content){
		FileWriter writer = null;  
        try {     
            writer = new FileWriter(file, true);     
            writer.write(content +  System.getProperty("line.separator") );       
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
	}
	
	public static void main(String[] args){
		appendLine("c:/f.data", "11121e4qrqew");
	}
}
