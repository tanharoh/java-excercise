//安卓中的布局与之不同，但是按钮的监听方式、组件的属性都非常类似。
//基本要求：实现整数四则运算。优化细节。
//拓展要求：小数点，退格键。
//加分项目：连续运算。

/*
建议：
1.界面看看JButton及其事件监听（重点），JLabel。
2.基础要看基本数据类型（重点），运算符，顺序-分支-循环结构，String相关。
3.可以多用System.out.println(相关值)来了解情况，找出哪一步开始出问题了
4.多考虑各种特殊情况
5.后期逻辑非常容易把自己绕进去，可以先在纸上写写，理顺思维。
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame {// 继承自窗体类
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 控件初始化
    private JLabel lab_show; // 标签（最上方显示框）
    private JButton[] btn_num = new JButton[10];// 数字按钮0-9
    private JButton btn_dot, btn_equal;// 小数点、等号
    private JButton btn_add, btn_sub, btn_mul, btn_div;// 加减乘除
    private JButton btn_back, btn_ce, btn_ac, btn_off;// 功能按钮
    // 按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字
    private boolean firstDigit = true;
    // 当前运算的运算符
    private String operator = "=";
    // 操作是否合法
    private boolean operateValidFlag = true;
    // 计算的中间结果。
    private double resultNum = 0.0;

    // 构造器
    public Calculator() {
        super("计算器");// 设置标题为“计算器”
        initView();// 初始化界面
    }

    private void initView() {
        // 实例化上方显示框
        lab_show = new JLabel("0");// 初始值为0
        lab_show.setBorder(BorderFactory.createLineBorder(Color.black));// 黑色边框
        lab_show.setHorizontalAlignment(JLabel.RIGHT);// 内容右对齐
        lab_show.setFont(new Font("Serif", Font.PLAIN, 80));// 字体字号
        // 实例化按钮
        for (int i = 0; i < 10; i++) {
            btn_num[i] = new JButton(i + "");// 0-9
        }
        btn_dot = new JButton(".");// 其他按钮
        btn_equal = new JButton("=");
        btn_add = new JButton("＋");
        btn_sub = new JButton("－");
        btn_mul = new JButton("×");
        btn_div = new JButton("÷");
        btn_back = new JButton("←");
        btn_ce = new JButton("CE");
        btn_ac = new JButton("AC");
        btn_off = new JButton("Off");
        // 下方按钮布局
        JPanel pan_bottom = new JPanel();// 下方面板
        pan_bottom.setLayout(new GridLayout(5, 4));// 5x4网格布局
        JButton[] btn_temp = { btn_off, btn_ac, btn_ce, btn_back, btn_num[7], btn_num[8], btn_num[9], btn_div,
                btn_num[4], btn_num[5], btn_num[6], btn_mul, btn_num[1], btn_num[2], btn_num[3], btn_sub, btn_dot,
                btn_num[0], btn_equal, btn_add };// 布局辅助临时变量
        for (int i = 0; i < btn_temp.length; i++) {// 循环遍历所有按钮
            btn_temp[i].setFont(new Font("Serif", Font.PLAIN, 40));// 设置字体
            pan_bottom.add(btn_temp[i]);// 添加到面板中
            btn_temp[i].addActionListener(new ButtonHandler());
        }
        // 将按钮与显示框组合
        JSplitPane fg1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);// 分隔面板
        fg1.setDividerSize(0);// 分隔线宽度
        fg1.setDividerLocation(100);// 分隔线位置
        fg1.setTopComponent(lab_show);// 分隔线上方，显示框
        fg1.setBottomComponent(pan_bottom);// 分隔线下方，按钮面板
        // 窗体设定
        this.add(fg1);// 添加分隔面板
        this.setBounds(480, 90, 404, 628);// 位置和大小
        this.setResizable(false); // 不能调大小
        this.setVisible(true);// 窗体可见性
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);// 退出模式：完全退出
    }

    public class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String key = e.getActionCommand();
            if (key.equals("←"))
                handleBackspace();
            else if (key.equals("CE"))
                handleCE();
            else if (key.equals("AC"))
                handleAC();
            else if (key.equals("Off"))
                handleOff();
            else if ("0123456789.".indexOf(key) >= 0)
                handleNumber(key);
            else
                handleOperator(key);
        }

        private void handleBackspace() {
            String text = lab_show.getText();
            int i = text.length();
            if (i > 0) {
                // 退格，将文本最后一个字符去掉
                text = text.substring(0, i - 1);
                if (text.length() == 0) {
                    // 如果文本没有了内容，则初始化计算器的各种值
                    lab_show.setText("0");
                    firstDigit = true;
                    operator = "=";
                } else {
                    // 显示新的文本
                    lab_show.setText(text);
                }
            }
        }

        private void handleCE() {
            // 初始化计算器的输入值
            lab_show.setText("0");
        }

        private void handleAC() {
            // 初始化计算器的各种值
            lab_show.setText("0");
            firstDigit = true;
            operator = "=";
        }

        private void handleOff() {
            System.exit(0);
        }

        // TODO:如果前面都是零，则输入零只显示一个零
        private void handleNumber(String key) {
            if (firstDigit) {// 输入的第一个数字
                if (key.equals("."))
                    lab_show.setText("0.");
                else if (true)
                    lab_show.setText(key);
            } else if ((key.equals(".")) && (lab_show.getText().indexOf(".") < 0)) {
                // 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
                lab_show.setText(lab_show.getText() + ".");
            } else if (!key.equals(".")) {
                // 如果输入的不是小数点，则将数字附在结果文本框的后面
                lab_show.setText(lab_show.getText() + key);
            }
            // 以后输入的肯定不是第一个数字了
            firstDigit = false;
        }

        private void handleOperator(String key) {
            if (operator.equals("÷")) {
                // 除法运算
                // 如果当前结果文本框中的值等于0
                if (getNumberFromText() == 0.0) {
                    // 操作不合法
                    operateValidFlag = false;
                    lab_show.setText("Error");
                } else {
                    resultNum /= getNumberFromText();
                }
            } else if (operator.equals("＋")) {
                // 加法运算
                resultNum += getNumberFromText();
            } else if (operator.equals("－")) {
                // 减法运算
                resultNum -= getNumberFromText();
            } else if (operator.equals("×")) {
                // 乘法运算
                resultNum *= getNumberFromText();
            } else if (operator.equals("=")) {
                // 赋值运算
                resultNum = getNumberFromText();
            }
            if (operateValidFlag) {
                // 浮点数的运算
                long t1;
                double t2;
                t1 = (long) resultNum;
                t2 = resultNum - t1;
                if (t2 == 0) {
                    lab_show.setText(String.valueOf(t1));
                } else {
                    lab_show.setText(String.valueOf(resultNum));
                }
            }
            // 运算符等于按的按钮
            operator = key;
            firstDigit = true;
            operateValidFlag = true;
        }

        private double getNumberFromText() {
            double result = 0;
            try {
                result = Double.valueOf(lab_show.getText()).doubleValue();
            } catch (NumberFormatException e) {
            }
            return result;
        }
    }

    // 主方法----------------------------------------------------------------
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Calculator();// 实例化“计算器”类

    }
}
