package edu.nuist.qdb.entity.question;

import java.util.HashMap;

public enum QuestionTypeEnum {
    CHOICE("单选题", 1),
    MULTICHOICES("多选题", 2),
    BLANK("填空题", 3),
    JUDEG("判断题", 4),
    SHORTANSWER("简答题", 5),
    SYNTHESIS("综合题", 6),
    MATRIALS("材料题", 7);

    private int index;
    private String name;

    private QuestionTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
   
    public static String getName(int index) {  
        for (QuestionTypeEnum c : QuestionTypeEnum.values()) {  
            if (c.getIndex() == index) {  
                return c.name;  
            }  
        }  
        return null;  
    }
    
    public static QuestionTypeEnum getByIndex(int index) {  
        for (QuestionTypeEnum c : QuestionTypeEnum.values()) {  
            if (c.getIndex() == index) {  
                return c;
            }  
        }  
        return null;  
    }
    
    public static QuestionTypeEnum getByName(String name) {  
        for (QuestionTypeEnum c : QuestionTypeEnum.values()) {  
            if (c.getName().equals(name)) {  
                return c;
            }  
        }  
        return null;  
    }

    public static HashMap<String, Integer> getAll(){
    	HashMap<String, Integer> rst = new HashMap<String, Integer>();
    	for (QuestionTypeEnum c : QuestionTypeEnum.values()) {  
            rst.put(c.getName(), c.getIndex()); 
        }  
        return rst;  
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
