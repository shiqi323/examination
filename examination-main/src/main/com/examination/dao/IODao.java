package main.com.examination.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IODao {

    private static final String PATH = System.getProperty("user.dir");

    /**
     * 将结果存储到文件中
     * @param list 存储集
     * @param fileName 文件名
     * @return 返回是否成功
     */
    public static boolean storageResult(List<StringBuilder> list, String fileName)  {
        File file = new File(PATH + "\\" +fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file,false);
            String content = "";
            for (int i =0 ;i<list.size();i++){
                content = content + list.get(i).toString() + "\n";
            }
            fileOutputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 输出信息导文件中
     * @param list 输入的内容
     * @param fileName 输入的文件名
     * @return 返回是否成功
     */
    public static boolean storageFile(List<StringBuilder> list, String fileName)  {
        File file = new File(PATH + "\\" +fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file,false);
            String content = "";
            for (int i =0 ;i<list.size();i++){
                content = content + (i+1) + "、" + list.get(i).toString() + "\n";
            }
            fileOutputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 阅读用户传入的文件
     * @param file 传入的文件
     * @return 返回一个List集合
     */
    public static List<StringBuilder> readFile(File file) {
        List<StringBuilder> list = new ArrayList<>();
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        //简单判断文件类型是否正确
        if (!file.exists() || !file.getName().contains("txt")){
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String raw;
            for (int i = 0; null != (raw = bufferedReader.readLine()); i++) {
                //文件内容是否有、，分情况输出
                if (raw.contains("、")) {
                    list.add(new StringBuilder(raw.substring(raw.indexOf("、") + 1, raw.length())));
                    System.out.println(new StringBuilder(raw.substring(raw.indexOf("、") + 1, raw.length())));
                } else {
                    list.add(new StringBuilder(raw));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                fileInputStream.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
