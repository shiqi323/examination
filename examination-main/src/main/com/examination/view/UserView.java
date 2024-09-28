package main.com.examination.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import main.com.examination.util.CreateUtil;
import main.com.examination.dao.*;

public class UserView {
    private int formulaNum;
    private int maxNum;

    int errorNum = 0;//错题计算
    int[] errorTitle;//存放错题题号

    int titleNum = 0;//题号
    boolean flag;//标志该题是否做过

    CreateUtil creatFor = new CreateUtil();

    List<StringBuilder> formulas;
    List<StringBuilder> answers;

    public UserView(int formula, int max) throws IOException {
        formulaNum = formula;
        maxNum = max;
        errorTitle = new int[formulaNum];
        formulas = IODao.readFile(new File("Exercises.txt"));
        answers = IODao.readFile(new File("Answers.txt"));
        creatFrame(1);
    }


    public JFrame creatFrame(int page) {

        JFrame frame = new JFrame("四则运算软件");
        frame.setBounds(100, 50, 500, 500);
        frame.setLocationRelativeTo(null);//居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗体，结束运行
        Container c = frame.getContentPane();
        c.setLayout(new GridLayout(11, 1));
        int i;
        for (i = 1; titleNum < formulaNum && i < 12; i++) {
            if (i == 11) {//生成按钮组件
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
                c.add(panel);
                JButton button1 = new JButton("下一页");
                panel.add(button1);
                //显示页面的面板
                JPanel panel1 = new JPanel();
                panel.add(panel1);
                panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                JLabel label1 = new JLabel("第");
                label1.setFont(new Font("宋体", Font.BOLD, 14));
                panel1.add(label1);
                JTextField text1 = new JTextField(2);
                text1.setText(String.valueOf(page));
                panel1.add(text1);
                JLabel label2 = new JLabel("页");
                label2.setFont(new Font("宋体", Font.BOLD, 14));
                panel1.add(label2);
                JLabel label3 = new JLabel("(共" + String.valueOf((formulaNum + 9) / 10) + "页)");
                label3.setFont(new Font("宋体", Font.PLAIN, 10));
                label3.setForeground(Color.GRAY);
                panel1.add(label3);
                JButton button2 = new JButton("跳转");
                panel.add(button2);
                //下一页按钮设置监听
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int k;
                        for (k = 0; k < titleNum; k++) {//判断是否有未填的题目
                            if (errorTitle[k] == 0) {
                                JDialog warning = creatWarning(frame);
                                warning.setVisible(true);
                                break;
                            }
                        }
                        if (k >= titleNum) {//全部填写完成
                            int temp = page + 1;//页面加一
                            frame.dispose();//当前页面销毁
                            creatFrame(temp);//生成新页面
                        }
                    }
                });
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int k;
                        for (k = 0; k < titleNum; k++) {
                            if (errorTitle[k] == 0) {
                                JDialog warning = creatWarning(frame);
                                warning.setVisible(true);
                                text1.setText(String.valueOf(page));
                                break;
                            }
                        }
                        if (k >= titleNum) {//全部填写完成
                            String str = text1.getText();
                            int temp = Integer.parseInt(str);//获取要跳转的页面数
                            if (temp <= (formulaNum + 9) / 10) {
                                frame.dispose();
                                creatFrame(temp);
                            } else {
                                text1.setBorder(BorderFactory.createLineBorder(Color.red));
                            }
                        }
                    }
                });
            } else {//生成题目
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                c.add(panel);
                titleNum = 10 * (page - 1) + i;
                //生成题目
                JLabel label = new JLabel(String.valueOf(titleNum) + "、" + formulas.get(titleNum - 1).toString());
                label.setFont(new Font("宋体", Font.BOLD, 18));
                panel.add(label);
                JTextField text = new JTextField(8);
                //给文本框设置焦点监听，判断答案对错
                int title = titleNum;
                text.addFocusListener(new FocusListener() {
                    @Override
                    public void focusLost(FocusEvent e) {//失去焦点
                        String str = text.getText();
                        if (str.equals(answers.get(title - 1).toString())) {//答案正确
                            if (errorTitle[title - 1] == 1) {//题目曾被记为错题
                                errorNum--;
                            }
                            errorTitle[title - 1] = 2;
                        } else {//答案错误
                            if (str.equals("")) {
                                errorTitle[title - 1] = 0;//该文本为空，则说明没有填写
                            } else {
                                if (errorTitle[title - 1] == 0 || errorTitle[title - 1] == 2) {//如果题号-1的位置上标识为0或为2，说明未被存储过
                                    errorNum++;
                                }
                                errorTitle[title - 1] = 1;//将该位置元素置为1，说明该题错误
                            }
                        }
                    }

                    @Override
                    public void focusGained(FocusEvent e) {//获取焦点
                    }
                });
                panel.add(text);
            }
        }

        for (; i < 12; i++) {
            if (i == 11) {
                JPanel endPanel = new JPanel();
                endPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
                c.add(endPanel);
                JButton button1 = new JButton("生成新题目");
                endPanel.add(button1);
                JButton button2 = new JButton("查询错题");
                endPanel.add(button2);
                JPanel panel1 = new JPanel();
                endPanel.add(panel1);
                panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                JLabel label1 = new JLabel("第");
                label1.setFont(new Font("宋体", Font.BOLD, 14));
                panel1.add(label1);
                JTextField text1 = new JTextField(2);
                text1.setText(String.valueOf(page));
                panel1.add(text1);
                JLabel label2 = new JLabel("页");
                label2.setFont(new Font("宋体", Font.BOLD, 14));
                panel1.add(label2);
                JLabel label3 = new JLabel("(共" + String.valueOf((formulaNum + 9) / 10) + "页)");
                label3.setFont(new Font("宋体", Font.PLAIN, 10));
                label3.setForeground(Color.GRAY);
                panel1.add(label3);
                JButton button3 = new JButton("跳转");
                endPanel.add(button3);
                button1.addActionListener(new ActionListener() {//生成新式子
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            creatFor.formulaNum(formulaNum, maxNum);
                            UserView frame = new UserView(formulaNum, maxNum);
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                });
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int k;
                        for (k = 0; k < titleNum; k++) {//判断是否有未填的题目
                            if (errorTitle[k] == 0) {
                                JDialog warning = creatWarning(frame);
                                warning.setVisible(true);
                                break;
                            }
                        }
                        if (k >= titleNum) {
                            creatCountFile();
                            JDialog count = creatCount(frame);
                            count.setVisible(true);
                        }
                    }
                });
                button3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int k;
                        for (k = 0; k < titleNum; k++) {
                            if (errorTitle[k] == 0) {
                                JDialog warning = creatWarning(frame);
                                warning.setVisible(true);
                                text1.setText(String.valueOf(page));
                                break;
                            }
                        }
                        if (k >= titleNum) {//全部填写完成
                            String str = text1.getText();
                            int temp = Integer.parseInt(str);//获取要跳转的页面数
                            if (temp <= (formulaNum + 9) / 10) {
                                titleNum = 0;
                                frame.dispose();
                                creatFrame(temp);
                            } else {
                                text1.setBorder(BorderFactory.createLineBorder(Color.red));
                            }
                        }
                    }
                });
            } else {
                JPanel panel = new JPanel();
                panel.setLayout(null);
                c.add(panel);
                JLabel label = new JLabel(" ");
                panel.add(label);
            }
        }
        frame.setVisible(true);
        return frame;
    }

    private JDialog creatCount(JFrame frame) {
        JDialog count = new JDialog(frame, "错误统计", true);
        count.setSize(100, 100);
        count.setResizable(false);//设置对话框大小不能改变
        count.setLocationRelativeTo(null);//居中
        count.setLayout(new GridLayout(3, 1));
        Container c = count.getContentPane();
        JLabel label1 = new JLabel("总题数：" + String.valueOf(formulaNum));
        label1.setFont(new Font("宋体", Font.BOLD, 15));
        c.add(label1);
        JLabel label2 = new JLabel("错题数：" + String.valueOf(errorNum));
        label2.setFont(new Font("宋体", Font.BOLD, 15));
        c.add(label2);
        JLabel label3 = new JLabel("正确数：" + String.valueOf(formulaNum - errorNum));
        label3.setFont(new Font("宋体", Font.BOLD, 15));
        c.add(label3);
        return count;
    }

    private JDialog creatWarning(JFrame frame) {
        JDialog warning = new JDialog(frame, "警告", true);
        warning.setSize(220, 120);
        warning.setResizable(false);//设置对话框大小不能改变
        warning.setLocationRelativeTo(null);//居中
        warning.setLayout(null);
        Container c = warning.getContentPane();
        JLabel label = new JLabel("请填写完整！！");
        label.setBounds(60, 15, 100, 30);
        c.add(label);
        JButton button1 = new JButton("确认");
        button1.setBounds(80, 50, 60, 25);
        c.add(button1);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                warning.dispose();
            }
        });
        return warning;
    }

    private void creatCountFile() {
        List<StringBuilder> result = new ArrayList<StringBuilder>();
        StringBuilder correct = new StringBuilder();
        StringBuilder error = new StringBuilder();
        correct.append("correct：" + (formulaNum - errorNum) + "(");
        error.append("error：" + (errorNum) + "(");
        for (int i = 0; i < errorTitle.length; i++) {
            if (errorTitle[i] == 2) {
                correct.append((i + 1) + "、");
            } else {
                error.append((i + 1) + "、");
            }
        }
        if(formulaNum - errorNum == 0){
            correct.append(")");
        }else{
            correct.setCharAt(correct.length() - 1, '）');
        }
        if(errorNum == 0){
            error.append(")");
        }else{
            error.setCharAt(error.length() - 1, '）');
        }
        result.add(correct);
        result.add(error);
        IODao.storageResult(result, "Grade.txt");
    }
}