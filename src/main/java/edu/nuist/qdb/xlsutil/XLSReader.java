package edu.nuist.qdb.xlsutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import cn.hutool.core.codec.Base64;
import cn.hutool.poi.excel.ExcelPicUtil;
import cn.hutool.poi.excel.ExcelReader;

public class XLSReader {
    private String file;
    private int rowNum = 0;
    private List<List<Cell>> cells = null;
    public static final String TEXT = "text";
    public static final String PIC = "pic";

    public XLSReader(String xlsFile){
        this.file = xlsFile;
    }

    public List<List<Cell>> read(int index) throws IOException{
        FileInputStream fi = new FileInputStream(new File(this.file));
        ExcelReader reader = new ExcelReader( fi, index );
        List<List<Cell>> txtCells = myReadAll(reader, index);
        fi = new FileInputStream(new File(this.file));
        HashMap<String, String> pics = readPics(fi);
        //System.out.println( pics );
        reader.close();
        fi.close();
        return this.assemble(txtCells, pics);
    }

    public List<List<Cell>> read() throws IOException{
        return read(0) ;
    }

    public List<List<Cell>> myReadAll(ExcelReader reader, int index){
        List<Object> heads = reader.readRow(0);
        List<List<Object>> rows = reader.read(index);
        List<List<Cell>> rst = new LinkedList<>();
        System.out.println( heads );
        int rowIndex = 1;
        for(List<Object> row : rows){
            int col = 0;
            List<Cell> tmp = new ArrayList<>();
            for(Object cell : row ){
                Cell c = Cell.builder()
                            .row(rowIndex)
                            .col(col)
                            .head((String)heads.get(col))
                            .build();
                
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(XLSReader.TEXT, cell+"");
                c.setDatas(map);
                tmp.add( c );
                col ++;
            }

            rst.add(tmp);
            rowIndex ++;
        }

        return rst;
    }

    public List<List<Cell>> myReadAll(ExcelReader reader){
        return myReadAll(reader, 0);
    }

    private List<List<Cell>> assemble(List<List<Cell>> txtCells, HashMap<String, String> pics){
        for(List<Cell> cells : txtCells){
            for(Cell c : cells ){
                String pic = pics.get(c.getRow() +"_"+ c.getCol());
                if( pic != null){
                    c.put(XLSReader.PIC, pic);
                }
            }
        }
        return txtCells;
    }

    private HashMap<String, String> readPics(InputStream inputStream) throws IOException{
	    Workbook hssfWorkbook = null;
		hssfWorkbook = new HSSFWorkbook(inputStream);
        Map pics = ExcelPicUtil.getPicMap(hssfWorkbook, 0);

        HashMap<String, String> rst = new HashMap<String, String>();
        for(Object e : pics.entrySet()){
            Entry tmp = (Entry)e;

            HSSFPictureData pic = (HSSFPictureData)tmp.getValue();
            String t = Base64.encode( pic.getData());
            rst.put((String)(tmp.getKey()), t);
        }
        return rst;
    }

    public int getRowsNum(){
        return this.cells.size();
    }
}
