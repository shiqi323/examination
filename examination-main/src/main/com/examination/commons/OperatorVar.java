package main.com.examination.commons;

public enum OperatorVar {

    /**
     * 运算符
     */
    PLUS_SIGN("加号","+","+"),
    MINUS_SIGN("减号","-","-"),
    MULTIPLIED_SIGN("乘以号","*","*"),
    DIVISION_SIGN("除以号","÷","/"),
    EQUAL_SIGN("等于号","=","=");

    private String name;
    private String express;
    private String calculation;

    OperatorVar(String name, String express, String calculation) {
        this.name = name;
        this.express = express;
        this.calculation = calculation;
    }

    public String getName() {
        return name;
    }

    public String getExpress() {
        return express;
    }

    public String getCalculation() {
        return calculation;
    }

}
