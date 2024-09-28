package main.com.examination.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("all")
public class CheckUtil {

    /**
     * 将StringBuilder拆分成string数组
     * @param stringBuilder
     * @return 返回string[] 数组
     */
    public static String[] spiltStringBuilderToArray(StringBuilder stringBuilder){
        return stringBuilder.toString().split("\\s+");
    }

    /**
     * 将StringBuilder拆分成List数组
     * @param stringBuilder string串串
     * @return 返回List数组
     */
    public static List<String> spiltStringBuilderToList(StringBuilder stringBuilder){
        return Arrays.asList(spiltStringBuilderToArray(stringBuilder));
    }

    /**
     * 将StringBuilder拆分成有序的 List 数组
     * @param stringBuilder string串串
     * @return 返回List数组
     */
    public static List<String> spiltStringBuilderToOrderList(StringBuilder stringBuilder){
        List<String> stringList = spiltStringBuilderToList(stringBuilder);
        Collections.sort(stringList);
        return stringList;
    }

    /**
     * 判断内容是否有重复
     * @param formula 判断的式子
     * @param lists 排序完的全部 list
     * @param answer 答案
     * @param answerLists 答案集
     * @return 返回是否重复
     */
    public static boolean judgeRepeat(StringBuilder formula, List<List<String>> lists,StringBuilder answer,List<StringBuilder> answerLists){
        List<String> formulaList = spiltStringBuilderToOrderList(formula);
        int i;
        for (i = 0;i<lists.size();i++){
            if(lists.get(i).equals(formulaList) && answer.toString().equals(answerLists.get(i).toString())){
                return true;
            }
        }
        lists.add(formulaList);
        return false;
    }

}
