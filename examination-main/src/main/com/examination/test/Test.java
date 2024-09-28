package main.com.examination.test;

import main.com.examination.util.CreateUtil;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        CreateUtil c = new CreateUtil();
//		System.out.print("请输入生成式子的数目：");
//		Scanner scanner = new Scanner(System.in);
//		int num = scanner.nextInt();
        c.formulaNum(100, 10);
//		IODao.readFile(new File("D:\\idea_maven\\examination-system\\Exercises.txt"));
    }
}

