package edu.nuist.qdb;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import cn.hutool.core.codec.Base64;
import cn.hutool.poi.excel.ExcelPicUtil;
import edu.nuist.qdb.xlsutil.Cell;
import edu.nuist.qdb.xlsutil.XLSReader;

public class TestExcel {
    //@Test
    public void t() throws IOException{
        ClassPathResource classPathResource = new ClassPathResource("/test/test.xls");
        InputStream inputStream = classPathResource.getInputStream();
        
	    Workbook hssfWorkbook = null;
		hssfWorkbook = new HSSFWorkbook(inputStream);
        Map pics = ExcelPicUtil.getPicMap(hssfWorkbook, 0);

        for(Object e : pics.entrySet()){
            Entry tmp = (Entry)e;
            System.out.println( tmp.getKey() );

            HSSFPictureData pic = (HSSFPictureData)tmp.getValue();
            String t = Base64.encode( pic.getData());
            System.out.println( t );
        }
    }    

    /**
     * 测试导入试题EXCEL，其中表头分别为题型，题干，选项，答案等，其中，每个单元格可以有相应的文本和图片，图片可以是多个
     */
    @Test
    public void s(){
        XLSReader rr = new XLSReader("d:/test.xls");
        try {
            List<List<Cell>> rows = rr.read();
            for(List<Cell> row : rows){
                System.out.println( row.toString() );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void regex(){
        Pattern pattern = Pattern.compile("(<#\\d+>)");
        Pattern pattern2 = Pattern.compile("^<(\\d+\\|)?\\d+>");
        Matcher matcher = pattern2.matcher("<3|2fdsfasf");
        int index = 0;
        while (matcher.find()) {
            System.out.println("ffffffffff");
        }
    }
}
