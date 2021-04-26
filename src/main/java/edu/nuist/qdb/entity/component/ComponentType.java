package edu.nuist.qdb.entity.component;

public enum ComponentType {
    QUESTIONTYPE("题型", 0),
    OPTION("选项", 1),
    CODE("题号", 2),
    STEM("题干", 4),
    ANALYSIS("解析", 5),
    ANSWER("答案", 6),
    ANSWERBLANK("填空答案", 7),
    ANSWERBLANKGROUP("answer_blank_group", 8),
    ANSWERJUDGE("answer_judge", 9),
    ANSWEROPTION("answer_option", 10),
    ANSWEROPTIONGROUP("answer_option_group", 11),
    SUBQUESTIONEND("结束", 12),
    UNKNOWN("未知", -1);
	
	private int index;
    private String name;

    ComponentType(String name, int index) {
        this.name = name;
        this.index = index;
    }
   
    public static String getName(int index) {  
        for (ComponentType c : ComponentType.values()) {  
            if (c.getIndex() == index) {  
                return c.name;  
            }  
        }  
        return null;  
    }
    
    public static ComponentType getByIndex(int index) {  
        for (ComponentType c : ComponentType.values()) {  
            if (c.getIndex() == index) {  
                return c;
            }  
        }  
        return null;  
    }
    
    public static ComponentType getByName(String name) {   
        for (ComponentType c : ComponentType.values()) {  
            if (c.getName().equals(name)) {  
                return c;
            }  
        }  
        return null;  
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
