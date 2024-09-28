package main.com.examination.util;

import main.com.examination.commons.OperatorVar;
import main.com.examination.dao.IODao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CreateUtil {

    //日志输出
    private static final Logger logger = Logger.getLogger("CreateUtil");
    List<StringBuilder> formula;
    //备份式子，存储"分子/分母"结构的式子，便于结果计算
    List<StringBuilder> answer;
    StringBuilder extraCopy ;

    public List<StringBuilder> getFormula() {
        return formula;
    }

    public List<StringBuilder> getAnswer() {
        return answer;
    }

    /**
     * 随机生成式子
     * 用List存储式子
     * @param maxNum 最大值
     * @return 返回该字符串
     * */
    public StringBuilder create(int maxNum) {
        StringBuilder formula = new StringBuilder();
        extraCopy = new StringBuilder(" ");
        //符号个数 （1,2,3）
        int signNum = (int)(Math.random()*3+1);
        creatNum(formula,maxNum);
        for(int i=0; i<signNum; i++) {
            createSign(formula);
            creatNum(formula,maxNum);
        }
        formula.append(OperatorVar.EQUAL_SIGN.getExpress() +" ");
        return formula;
    }

    /**
     * 随机生成操作数
     * 并将操作数存入list中
     * @param formula 字符串
     * @param maxNum 数
     * @return 返回参数的字符串
     * */
    public StringBuilder creatNum(StringBuilder formula,int maxNum) {
    	int numerator,denominator,type;
		type = (int)(Math.random()*2);
        //生成整数
		if(type==0) {
			do {
				numerator =(int)(Math.random()*10);
			}while(numerator > maxNum);
            //备份分子/分母
			extraCopy.append(numerator+"/"+1+" ");
			formula.append(numerator+" ");
		}
		else {
			do {
                //随机生成分子
				numerator = (int)(Math.random()*10);
                //保证分母不等于0
				while((denominator=(int)(Math.random()*10))==0);
			}while(!numRange(numerator, denominator,maxNum));
            //备份分子/分母
			extraCopy.append(numerator+"/"+denominator+" ");
			formula.append(HandleUtil.creatNum(numerator, denominator));
		}
		return formula;
    }

    /**
     *  随机生成符号
     * 并将符号存入list中
     * @param formula 符号
     * @return 返回符号
     */
    public StringBuilder createSign(StringBuilder formula) {
        //符号类型（+ - * /）
        int signType = (int)(Math.random()*4+1);
        switch (signType){
            case 1 :
                formula.append(OperatorVar.PLUS_SIGN.getExpress());
                extraCopy.append(OperatorVar.PLUS_SIGN.getExpress());
                break;
            case 2 :
                formula.append(OperatorVar.MINUS_SIGN.getExpress());
                extraCopy.append(OperatorVar.MINUS_SIGN.getExpress());
                break;
            case 3 :
                formula.append(OperatorVar.MULTIPLIED_SIGN.getExpress());
                extraCopy.append(OperatorVar.MULTIPLIED_SIGN.getExpress());
                break;
            case 4 :
                formula.append(OperatorVar.DIVISION_SIGN.getExpress());
                extraCopy.append(OperatorVar.DIVISION_SIGN.getExpress());
                break;
            default:

        }
        extraCopy.append(" ");
        formula.append(" ");
        return formula;
    }

    /**
     * 设定随机生成一定数目的式子，并将式子和答案分别存在formula和answer中
     * @param num 生成的式子数目
     * @param maxNum 最大值
     */
    public void formulaNum(int num, int maxNum) throws IOException {
        Long beginTime = System.currentTimeMillis();
        //存放拆分完的式子
        List<List<String>> formulaLists = new ArrayList<List<String>>(num);
        formula = new ArrayList<StringBuilder>();
        answer = new ArrayList<StringBuilder>();
        //原始式子
        StringBuilder singleFormula;
        for(int i=0; formula.size()<num; i++) {
            formula.add(singleFormula = create(maxNum));
            CalculateUtil.calculateFormula(extraCopy);
            //式子不符合规范（结果为负数）,并且查重
            if(extraCopy.charAt(0)=='@' || CheckUtil.judgeRepeat(singleFormula,formulaLists,extraCopy,answer)) {
                formula.remove(formula.size()-1);
                continue;
            }
            answer.add(extraCopy);
        }
        int i=0;
        IODao.storageFile(formula,"Exercises.txt");
        IODao.storageFile(answer,"Answers.txt");
        System.out.println("生成时间: " + (System.currentTimeMillis()-beginTime));
    }

    /**
     * 设定操作数的最大数值
     * @param numerator 分子
     * @param denominator 分母
     * @param maxNum 最大值
     * @return 是否超过最大值
     */
    public boolean numRange(int numerator, int denominator,int maxNum) {
        if((numerator/denominator)<maxNum) {
            return true;
        }else if((numerator/denominator)==maxNum) {
            if((numerator%denominator)==0) {
                return true;
            }
        }
        return false;
    }

}
