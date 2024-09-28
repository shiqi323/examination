package main.com.examination.util;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class CalculateUtil {

	private static final Logger logger = Logger.getLogger("CalculateUtil");

	/**
	 * 加法运算
	 * @param numerator1 分子1
	 * @param denominator1 分母1
	 * @param numerator2 分子2
	 * @param denominator2 分母2
	 * @return 返回结果
	 */
	public static StringBuilder add(int numerator1,int denominator1,int numerator2,int denominator2) {
		int numerator,denominator;
		StringBuilder result = new StringBuilder();
		numerator = numerator1*denominator2+numerator2*denominator1;
		denominator = denominator1 * denominator2;
		if(numerator!=0) {
			//化简分子分母（除以最大公因数）
			int gcdNum = HandleUtil.gcd(numerator,denominator);
			numerator /= gcdNum;
			denominator /= gcdNum;
		}

		result.append(numerator+"/"+denominator);
		return result;
	}

	/**
	 * 减法运算
	 * @param numerator1 分子1
	 * @param denominator1 分母1
	 * @param numerator2 分子2
	 * @param denominator2 分母2
	 * @return 返回计算结果
	 */
	public static StringBuilder minus(int numerator1,int denominator1,int numerator2,int denominator2) {
		int numerator,denominator;
		StringBuilder result = new StringBuilder();

		numerator = numerator1*denominator2-numerator2*denominator1;
		denominator = denominator1*denominator2;
		//化简分子分母（除以最大公因数）
		if(numerator!=0) {
			int gcdNum = HandleUtil.gcd(numerator,denominator);
			numerator /= gcdNum;
			denominator /= gcdNum;
		}
		result.append(numerator+"/"+denominator);
		return result;
	}

	/**
	 * 乘法运算
	 * @param numerator1 分子1
	 * @param denominator1 分母1
	 * @param numerator2 分子2
	 * @param denominator2 分母2
	 * @return 返回计算结果
	 */
	public static StringBuilder multiply(int numerator1,int denominator1,int numerator2,int denominator2) {
		int numerator,denominator;
		StringBuilder result = new StringBuilder();
		//操作数有一个等于0的情况
		if(numerator1==0||numerator2==0) {
			result.append(0+"/"+1);
		}
		//操作数大于0的情况
		else {
			numerator = numerator1*numerator2;
			denominator = denominator1*denominator2;
			//化简分子分母（除以最大公因数）
			if(numerator!=0) {
				int gcdNum = HandleUtil.gcd(numerator,denominator);
				numerator /= gcdNum;
				denominator /= gcdNum;
			}
			result.append(numerator+"/"+denominator);
		}
		return result;
	}

	/**
	 * 除法运算
	 * @param numerator1 分子1
	 * @param denominator1 分母1
	 * @param numerator2 分子2
	 * @param denominator2 分母2
	 * @return 返回计算结果
	 */
	public static StringBuilder divide(int numerator1,int denominator1,int numerator2,int denominator2) {
		int numerator,denominator;
		StringBuilder result = new StringBuilder();
		numerator = numerator1*denominator2;
		denominator = denominator1*numerator2;
		//化简分子分母（除以最大公因数）
		if(numerator!=0) {
			int gcdNum = HandleUtil.gcd(numerator,denominator);
			numerator /= gcdNum;
			denominator /= gcdNum;
		}
		result.append(numerator+"/"+denominator);
		return result;
	}

	/**
	 * 对运算符号左右的两个数进行运算
	 * @param index 运算符的位序
	 * @param extraCopy 待计算的式子
	 * @return
	 */
	public static StringBuilder calculate(int index,StringBuilder extraCopy) {
		char sign = extraCopy.charAt(index);
		int beginIndex = 0, endIndex = -1;
		int[] datas;
		for(int index1=0; ; beginIndex=index1) {
			//找到第一个操作数的开头空格
			index1 = extraCopy.indexOf(" ", index1+1);
			if(index1==(index-1)) {
				break;
			}
		}
		datas = HandleUtil.change(extraCopy, beginIndex);
		int numerator1 = datas[1];
		int denominator1 = datas[2];
		datas = HandleUtil.change(extraCopy, index+1);
		int numerator2 = datas[1];
		int denominator2 = datas[2];
		endIndex = datas[0];
		//删除数字部分
		extraCopy.delete(beginIndex+1,endIndex);
		//根据符号进行相应的运算
		switch(sign){
			case '+':
				extraCopy.insert(beginIndex+1, add(numerator1,denominator1,numerator2,denominator2));
				break;
			case '-':
				if(!HandleUtil.judge(numerator1, denominator1, numerator2, denominator2)) {
					//识别答案是否为负数
					extraCopy.insert(0, "@ ");
					break;
				} else{
					extraCopy.insert(beginIndex+1, minus(numerator1,denominator1,numerator2,denominator2));
					break;
				}
			case '*':
				extraCopy.insert(beginIndex+1, multiply(numerator1,denominator1,numerator2,denominator2));
				break;
			case '÷':
				if(numerator2 == 0) {
					//识别答案是否为负数，是的话在开头插入@作为标识
					extraCopy.insert(0, "@ ");
					break;
				} else{
					extraCopy.insert(beginIndex+1, divide(numerator1,denominator1,numerator2,denominator2));
					break;
				}
			default: break;
		}
		return extraCopy;
	}

	/**
	 * 按优先级进行运算（*  /  +  -）
	 * @param extraCopy copy副本
	 * @return 返回
	 */
	public static StringBuilder calculateFormula(StringBuilder extraCopy) {
//		logger.info(extraCopy.toString());
		//记录符号的位序
		int index = -1;
		//计算式子
		Pattern pattern1 = Pattern.compile("[*]|[÷]");
		Matcher m1;
		while((m1 = pattern1.matcher(extraCopy)).find()) {
			index = m1.start();
			calculate(index, extraCopy);
			if(extraCopy.charAt(0)=='@') {
				break;
			}	
		}
		//如果式子正确，在进行加运算（从左到右）
		if(extraCopy.charAt(0)!='@') {
			Pattern pattern2 = Pattern.compile("[-]|[+]");
			Matcher m2;
			while((m2 = pattern2.matcher(extraCopy)).find()) {
				index = m2.start();
				calculate(index, extraCopy);
				if(extraCopy.charAt(0)=='@') {
					break;
				}	
			}
		}
		//如果运算结束后（式子正确），调整答案格式
		if(extraCopy.charAt(0)!='@') {
			int datas[];
			datas = HandleUtil.change(extraCopy, 0);
			//分子
			int numerator = datas[1];
			//分母
			int denominator = datas[2];
			//将原存储内容清空
			extraCopy.setLength(0);
			//将答案换成标准格式
			extraCopy.append(HandleUtil.creatNum(numerator, denominator));
		}
		return extraCopy;
	}
}
