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

    private JCheckBox jCheckBox1;
    private JLabel jLabel1;

    public static volatile boolean quickPick = false;


    public GameForm() {
        JFrame frame = new JFrame("F10开/关");
        frame.setLayout(null);
        frame.setContentPane(getJpanel());
        frame.setSize(360, 250);
        frame.setDefaultCloseOperation(3);

        IStartF10Listen listen = MyloaderConstruct.getStartF10Listen();
        listen.start();

        frame.setLocation(550,400);
        frame.setVisible(true);


    }

    public JPanel getJpanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setPreferredSize(new Dimension(340, 150));
        jLabel = new JLabel();
        jLabel.setText("已关闭");
        jLabel.setBounds(20, 10, 200, 200);
        jLabel.setFont(new   java.awt.Font("Dialog",   1,   25));
        jLabel.setForeground(Color.black);
        jPanel.add(jLabel);

        jCheckBox1 = new JCheckBox();
        jCheckBox1.setBounds(150,30,30,30);
        jPanel.add(jCheckBox1);
        jCheckBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(jCheckBox1.isSelected()){
                   quickPick= true;
               }
               else{
                   quickPick= false;
               }
            }
        });

        jLabel1 = new JLabel();
        jLabel1.setBounds(180,30,170,30);
        jLabel1.setText("光速拾取(鼠标中键)");
        jPanel.add(jLabel1);
        return jPanel;
    }

}
