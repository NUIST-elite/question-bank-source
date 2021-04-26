package edu.nuist.qdb.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;

import edu.nuist.qdb.service.ajax.result.ResultMap;
import edu.nuist.qdb.service.ajax.result.ResultMessage;

@Service
public class FileUploader {
	@Autowired
	private ResultMap resultMap;
	
	@Value("${file.temppath}")
	private String fileStoreBase;
	
	public MediaType getContentType(byte[] file, String fileName) throws IOException {
        final InputStream attachmentDataStream = new ByteArrayInputStream(file);

        final MediaType mediaType;
        try {
            mediaType = TikaDetector.tikaDetector().detect(attachmentDataStream, fileName);
        } catch (IOException e) {
            throw e;
        }
        return mediaType;
    }
	
	public ResultMessage singel(
			MultipartFile file,
			String folder,
			String fileType) {
		return singel( file, fileStoreBase, folder,fileType);
	}
	
	public ResultMessage singel(
			MultipartFile file,
			String fileBase,
			String folder,
			String fileType) {
		if(file.isEmpty()) {
			return resultMap.getRstMsg("fileManager", "upload", "nofile");
        }
        
        try {
        	String ext = file.getOriginalFilename();
        	if( !isAllowType(fileType, ext) ) {
//        		ResultMessage msg = resultMap.getRstMsg("fileManager", "upload", "wrongtype").copy();
//                msg.setMsg( MessageFormat.format(msg.getMsg(), ext, fileType));
                return new ResultMessage();
        	}
        	ext = ext.substring(ext.lastIndexOf(".") ) ;
            byte[] bytes = file.getBytes();            
            String fileName = UUID.randomUUID().toString() + ext;
            File targetFile = new File( fileBase + folder + "/" + fileName);
            System.out.println( targetFile );
            if( !new File( fileBase + folder + "/").exists() ) {
            	Files.createParentDirs(targetFile);
            }
            Files.write(bytes, targetFile);
            
//            ResultMessage msg = resultMap.getRstMsg("fileManager", "upload", "success").copy();
            JSONObject obj = new JSONObject();
            obj.put("origin", file.getOriginalFilename());
            obj.put("inner", fileName);
            ResultMessage msg = new ResultMessage();
			msg.setObj(obj);
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	} 
	
	public boolean isAllowType(String tagert, String fileName ) {
		String ext = fileName.substring(fileName.lastIndexOf(".") );
    	boolean flag = false;
    	System.out.println(tagert +"=======-----------"+ fileName);
		
    	if( tagert.toLowerCase().trim().equals("all") ) {
			for(Entry<String, String[]> e : extMap.entrySet()) {
				for(String fileExtent : e.getValue() ) {
	        		if( fileExtent.equals( ext.trim().toLowerCase() )) {
	        			return true;
	        		}
	        	}
			}
			return false;
		}
    	if(extMap.get(tagert) != null) {
    		
    		for(String e : extMap.get(tagert)) {
        		if( e.equals( ext.trim().toLowerCase() )) {
        			flag = true;
        			break;
        		}
        	}
    	}
    	return flag;
	}
	
	final Map<String, String[]> extMap = ImmutableMap.of(
			"word", new String[] {".txt", ".doc", ".docx", ".pptx", ".ppt"},
			"image", new String[] {".bmp", ".jpg", ".jpeg", ".png", ".gif"},
			"excel", new String[] {".xls", ".xlsx"},
			"pdf", new String[] {".pdf" },
			"rar", new String[] {".zip", ".rar"}
	);
	
	final String[][] MIME_MapTable = { { ".3gp", "video/3gpp" }, { ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" }, { ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" }, { ".c", "text/plain" }, { ".class", "application/octet-stream" },
			{ ".conf", "text/plain" }, { ".cpp", "text/plain" }, { ".doc", "application/msword" },
			{ ".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" }, { ".gif", "image/gif" }, { ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" }, { ".h", "text/plain" }, { ".htm", "text/html" }, { ".html", "text/html" },
			{ ".jar", "application/java-archive" }, { ".java", "text/plain" }, { ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" }, { ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" }, { ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" }, { ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" }, { ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" }, { ".mpc", "application/vnd.mpohun.certificate" }, { ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" }, { ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" }, { ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" }, { ".pdf", "application/pdf" },
			{ ".png", "image/png" }, { ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
			{ ".rtf", "application/rtf" }, { ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" }, { ".wav", "audio/x-wav" },
			{ ".wma", "audio/x-ms-wma" }, { ".wmv", "audio/x-ms-wmv" }, { ".wps", "application/vnd.ms-works" },
			{ ".xml", "text/plain" }, { ".z", "application/x-compress" }, { ".zip", "application/x-zip-compressed" },
			{ "", "*/*" } };
	
	
}
