package edu.nuist.qdb.xlsutil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Cell {
    private int row;
    private int col;
    private String head;
    HashMap<String, String> datas = null;
    
    public String getTag(){
        return this.row +"-"+this.col; 
    }

    public void put(String type, String data){
        this.datas.put( type, data);
    }

    public String toString(){
        return JSON.toJSONString(this);
    }
}
