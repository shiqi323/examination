package main.com.examination.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.*;

import java.util.List;

import main.com.examination.dao.IODao;
import main.com.examination.util.CreateUtil;

@SuppressWarnings("all")
public class MainView {
	private String pathFormula;//式子文件路径
	private String pathAnswer;//答案文件路径

	JFrame frame = new JFrame();

	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	Container c;

	CreateUtil creatFor = new CreateUtil();

	private JLabel label1 = new JLabel("四则运算");
	private JLabel label2 = new JLabel("请输入生成式子的数目：");
	private JLabel label3 = new JLabel("请输入操作数的最大值：");

	private JButton button1 = new JButton("生成题目");
	private JButton button2 = new JButton("操作说明");

	private JTextField text1 = new JTextField(5);
	private JTextField text2 = new JTextField(5);

	private JDialog creatWarning() {
		JDialog warning = new JDialog(frame,"警告",true);
		warning.setSize(220, 120);
		warning.setResizable(false);//设置对话框大小不能改变
		warning.setLocationRelativeTo(null);//居中
		warning.setLayout(null);
		Container c = warning.getContentPane();
		JLabel label = new JLabel("请正确填写！！");
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

	private JDialog creatExpression() {
		JDialog expression = new JDialog(frame,"说明",true);
		expression.setSize(300, 250);
		expression.setResizable(false);//设置对话框大小不能改变
		expression.setLocationRelativeTo(null);//居中
		expression.setLayout(new GridLayout(6,1));
		Container c = expression.getContentPane();
		JLabel label1 = new JLabel("1、本系统只支持10以内的四则运算");
		JLabel label2 = new JLabel("2、答案必须是化简后的结果");
		JLabel label3 = new JLabel("3、答案不可出现假分数");
		JLabel label4 = new JLabel("4、本系统支持统计错题");
		JLabel label5 = new JLabel("5、本系统仅支持英文输入法");
		c.add(label1);
		c.add(label2);
		c.add(label3);
		c.add(label4);
		c.add(label5);
		return expression;
	}

	public MainView() {
		frame.setTitle("四则运算软件");
		frame.setSize(500, 300);
		frame.setLocationRelativeTo(null);//居中
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗体，结束运行

		c =frame. getContentPane();//frame的容器
		c.setLayout(new GridLayout(3, 1));
		c.add(panel1);
		c.add(panel2);
		c.add(panel3);
		panel1.setLayout(new GridBagLayout());//设置网格组布局
		panel2.setLayout(new GridBagLayout());//设置网格组布局
		panel3.setLayout(new GridBagLayout());//设置网格组布局
		init();
		frame.setVisible(true);
	}

	private void init() {
		GridBagConstraints gbc = new GridBagConstraints();
		label1.setFont(new Font("楷体",Font.BOLD,30));
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel1.add(label1, gbc);

		label2.setFont(new Font("黑体",Font.BOLD,15));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel2.add(label2, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		panel2.add(text1, gbc);

		label3.setFont(new Font("黑体",Font.BOLD,15));
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel2.add(label3, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		panel2.add(text2, gbc);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.insets = new Insets(0, 0, 0, 30);
		panel3.add(button2, gbc);

		gbc.gridx = 20;
		gbc.gridy = 10;
		panel3.add(button1, gbc);

		//生成式子按钮监听
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Pattern pattern = Pattern.compile("[0-9]+?");
				String str1 = text1.getText();
				String str2 = text2.getText();
				if(pattern.matcher(str1).matches()&&pattern.matcher(str2).matches()) {
					int formulaNum = Integer.parseInt(str1);
					int maxNum = Integer.parseInt(str2);
					if(maxNum>10) {
						text2.setBorder(BorderFactory.createLineBorder(Color.red));
						JDialog warning = creatWarning();
						warning.setVisible(true);
					}
					else {
						try {
							creatFor.formulaNum(formulaNum, maxNum);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						creatTips(frame, formulaNum, maxNum);
					}
				}
				else {
					JDialog warning = creatWarning();
					warning.setVisible(true);
					if(!pattern.matcher(str1).matches()) {
						text1.setBorder(BorderFactory.createLineBorder(Color.red));
					}
					else {
						text1.setBorder(BorderFactory.createLineBorder(Color.black));
					}
					if(!pattern.matcher(str2).matches()) {
						text2.setBorder(BorderFactory.createLineBorder(Color.red));
					}
					else{
						text2.setBorder(BorderFactory.createLineBorder(Color.black));
					}
				}
			}
		});

		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog expression = creatExpression();
				expression.setVisible(true);
			}
		});
	}

	private JDialog creatDao(JFrame frame) {
		JDialog dao = new JDialog(frame, "提示", true) ;
		dao.setSize(300,200);
		dao.setLocationRelativeTo(null);
		dao.setResizable(false);
		dao.setLayout(null);
		Container c = dao.getContentPane();
		JLabel label1 = new JLabel("请输入存放式子文件的绝对路径:",JLabel.LEFT);
		label1.setBounds(5, 5, 200, 25);
		c.add(label1);
		JTextField text1 = new JTextField(250);
		text1.setBounds(5, 35, 280, 25);
		c.add(text1);
		JLabel label2 = new JLabel("请输入存放答案文件的绝对路径:",JLabel.LEFT);
		label2.setBounds(5, 65, 200, 25);
		c.add(label2);
		JTextField text2 = new JTextField(250);
		text2.setBounds(5, 95, 280, 25);
		c.add(text2);
		JButton button = new JButton("确认");
		button.setBounds(210,135,60,25);
		c.add(button);
		text1.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {//失去焦点
				pathFormula = text1.getText();
			}
			@Override
			public void focusGained(FocusEvent e) {//获取焦点

			}
		});
		text2.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {//失去焦点
				pathAnswer = text2.getText();
			}
			@Override
			public void focusGained(FocusEvent e) {//获取焦点

			}
		});
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File file1 = new File(pathFormula);
				if(!file1.exists() || file1.isDirectory()){
					JDialog warning = creatWarning();
					warning.setVisible(true);
					return;
				}
				List<StringBuilder> formulas = IODao.readFile(file1);
				if(formulas==null) {
					text1.setBorder(BorderFactory.createLineBorder(Color.red));
				}
				else {
					IODao.storageFile(formulas, "Exercises.txt");
				}
				File file2 = new File(pathAnswer);
				if(!file2.exists() || file2.isDirectory()){
					JDialog warning = creatWarning();
					warning.setVisible(true);
					return;
				}
				List<StringBuilder> answers = IODao.readFile(file2);
				if(formulas==null) {
					text1.setBorder(BorderFactory.createLineBorder(Color.red));
				}
				else {
					IODao.storageFile(answers, "Answers.txt");
					frame.dispose();
					try {
						UserView f = new UserView(answers.size(),10);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		dao.setVisible(true);
		return dao;
	}

	private JDialog creatTips(JFrame frame, int formulaNum, int maxNum) {
		JDialog tips = new JDialog(frame, "提示", true);
		tips.setSize(220,120);
		tips.setLocationRelativeTo(null);
		tips.setResizable(false);
		tips.setLayout(new GridLayout(2,1));
		Container c = tips.getContentPane();
		JLabel label = new JLabel("是否导入题目？",JLabel.CENTER);
		c.add(label);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,32,0));
		c.add(panel);
		JButton button1 = new JButton("确认");
		button1.setSize(60, 25);
		panel.add(button1);
		JButton button2 = new JButton("取消");
		button2.setSize(60, 25);
		panel.add(button2);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tips.dispose();
				creatDao(frame);

			}
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				try {
					UserView f = new UserView(formulaNum, maxNum);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		tips.setVisible(true);
		return tips;
	}
}
