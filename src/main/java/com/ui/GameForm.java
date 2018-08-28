package com.ui;


import com.inter.IStartF10Listen;
import com.myloader.MyloaderConstruct;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @Author: huangwentao
 * @Date: 2018/8/14 11:25
 */
public class GameForm {

    public static JLabel jLabel;

    public static JFrame frame;
    private JCheckBox jCheckBox1;
    private JLabel jLabel1;

    public static volatile boolean quickPick = false;

    private JCheckBox jCheckBox2;
    private JLabel jLabel2;

    public static volatile boolean leftYaoshe = false;

    private JCheckBox jCheckBox3;
    private JLabel jLabel3;

    public static volatile boolean M16Lianfa = false;


    public GameForm() {
        frame = new JFrame("F10开/关(一定要在游戏窗口中按热键开关!)");
        frame.setLayout(null);
        frame.setContentPane(getJpanel());
        frame.setSize(450, 250);
        frame.setDefaultCloseOperation(3);
        frame.setLocation(550, 400);
        frame.setVisible(true);

        MainForm.frame.setVisible(false);





    }

    public JPanel getJpanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setPreferredSize(new Dimension(340, 150));
        jLabel = new JLabel();
        jLabel.setText("已关闭");
        jLabel.setBounds(20, 10, 200, 200);
        jLabel.setFont(new java.awt.Font("Dialog", 1, 25));
        jLabel.setForeground(Color.black);
        jPanel.add(jLabel);

        jCheckBox1 = new JCheckBox();
        jCheckBox1.setBounds(130, 30, 30, 30);
        jPanel.add(jCheckBox1);
        jCheckBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jCheckBox1.isSelected()) {
                    quickPick = true;
                } else {
                    quickPick = false;
                }
            }
        });

        jLabel1 = new JLabel();
        jLabel1.setBounds(160, 30, 170, 30);
        jLabel1.setText("光速拾取(鼠标中键)");
        jPanel.add(jLabel1);


        //左键腰射
        jCheckBox2 = new JCheckBox();
        jCheckBox2.setBounds(130, 60, 30, 30);
        jPanel.add(jCheckBox2);
        jCheckBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jCheckBox2.isSelected()) {
                    leftYaoshe = true;
                } else {
                    leftYaoshe = false;
                }
            }
        });

        jLabel2 = new JLabel();
        jLabel2.setBounds(160, 60, 270, 30);
        jLabel2.setText("左键腰射+开火（只针对右键长按开镜的玩家）");
        jPanel.add(jLabel2);

        //m16连发
        jCheckBox3 = new JCheckBox();
        jCheckBox3.setBounds(130, 90, 30, 30);
        jPanel.add(jCheckBox3);
        jCheckBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jCheckBox3.isSelected()) {
                    M16Lianfa = true;
                } else {
                    M16Lianfa = false;
                }
            }
        });

        jLabel3 = new JLabel();
        jLabel3.setBounds(160, 90, 270, 30);
        jLabel3.setText("M16连发（单点模式下）");
        jPanel.add(jLabel3);

        return jPanel;
    }

}
