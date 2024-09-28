package main.com.examination.util;

public class HandleUtil {

    /**
     * 将答案按规范生成出来
     * @param numerator 分子
     * @param denominator 分母
     * @return 式子
     */
    public static StringBuilder creatNum(int numerator, int denominator) {
        StringBuilder num = new StringBuilder();
        int gcdNum = gcd(numerator, denominator);
        numerator /= gcdNum;
        denominator /= gcdNum;
        if (numerator >= denominator) {
            //分子大于等于分母
            if (numerator % denominator == 0) {
                //结果为整数
                num.append(numerator / denominator + "");
            } else {
                //结果为带分数
                int interger = numerator / denominator;
                numerator = numerator - (interger * denominator);
                num.append(interger + "'" + numerator + "/" + denominator + "");
            }
        } else {
            //分子小于分母
            if (numerator == 0) {
                //分子等于0
                num.append(0 + "");
            } else {
                //其他情况
                num.append(numerator + "/" + denominator + "");
            }
        }
        return num;
    }

    /**
     * 求两数的最大公因数
     * @param num01 数字1
     * @param num02 数字2
     * @return 返回公因数
     */
    public static int gcd(int num01, int num02) {
        int num;
        while (num02 != 0) {
            num = num01 % num02;
            num01 = num02;
            num02 = num;
        }
        return num01;
    }

    /**
     * 将式子中指定字符的所有位序存储起来
     * @param str 字符串
     * @param formula 式子
     * @return 返回位序
     */
    public static int[] charFind(String str, StringBuilder formula) {
        int[] indexs = new int[20];
        for (int i = 0; ; i++) {
            if (i == 0) {
                indexs[i] = formula.indexOf(str, 0);
                continue;
            }
            if (str.equals(" ") && (indexs[i - 1] == formula.length() - 1)) {
                break;
            }
            if (str.equals(" ") || str.equals("/")) {
                indexs[i] = formula.indexOf(str, indexs[i - 1] + 1);
            }
            if (str.equals("/") && (formula.length() - 1 - indexs[i] <= 4)) {
                break;
            }
        }
        return indexs;
    }


    /**
     * 将指定数字字符串转为数字值
     * @param formula 带查找的式子
     * @param fromIndex 操作数前的空格位序
     * @param endIndex 操作数后的空格位序
     * @return 返回数字
     */
    public static int changeNum(StringBuilder formula, int fromIndex, int endIndex) {
        int num = -1;
        //根据数字的位数进行转换
        int sum = 0, temp;
        for (int i = 1; i < (endIndex - fromIndex); i++) {
            temp = (int) Math.pow((double) 10, (double) (i - 1));
            num = (int) (formula.charAt(endIndex - i) - 48) * temp;
            sum += num;
        }
        return sum;
    }

    /**
     * 判断被减数、减数是否符合规范(true:符合；false:不符合)
     * @param numerator1 第一个操作数的分子
     * @param denominator1  第一个操作数的分母
     * @param numerator2 第二个操作数的分子
     * @param denominator2 第二个操作数的分母
     * @return 返回是否正确
     */
    public static boolean judge(int numerator1, int denominator1, int numerator2, int denominator2) {
        int numerator = numerator1 * denominator2 - numerator2 * denominator1;
        if (numerator < 0) {
            return false;
        }
        return true;
    }



    /**
     * 通过字符串将操作数的分子分母转成数字
     * @param extraCopy 进行操作的字符串
     * @param beginIndex 操作数前的空格位序
     * @return 返回数字集
     */
    public static int[] change(StringBuilder extraCopy, int beginIndex) {
		int[] num = new int[3];
        //存储空格的位序，方便找到完整的操作数
		int[] blanks = charFind(" ", extraCopy);
        //反斜杠的位置
		int indexBl = -1 ,indexBa = extraCopy.indexOf("/", beginIndex);
		for(int i=0; i<blanks.length; i++) {
			if(blanks[i]==beginIndex) {
			    //找到传入空格位序在blanks中的位置
				indexBl = i;
				break;
			}
		}
		num[0]=blanks[indexBl+1];//操作数后的空格位序
		num[1]=changeNum(extraCopy,beginIndex,indexBa);//分子
		num[2]=changeNum(extraCopy,indexBa,num[0]);//分母
		return num;
	}
}


