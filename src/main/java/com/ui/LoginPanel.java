package com.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author: huangwentao
 * @Date: 2018/8/23 15:18
 */
public class LoginPanel extends JPanel {

    private JLabel userLable;

    private JTextField textField;

    private JLabel passwordLable;

    private JPasswordField passwordField;

    private JButton jButton;


    public LoginPanel() {
        setupUi();
    }

    private void setupUi() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(340, 150));
        userLable = new JLabel();
        userLable.setBounds(50, 20, 70, 30);
        userLable.setText("用户名:");
        this.add(userLable);
        textField = new JTextField();
        textField.setBounds(100, 27, 120, 20);
        textField.setText("");
        this.add(textField);

        passwordLable = new JLabel();
        passwordLable.setBounds(63, 47, 70, 30);
        passwordLable.setText("密码:");
        this.add(passwordLable);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 54, 120, 20);
        passwordField.setText("");
        this.add(passwordField);

        jButton = new JButton();
        jButton.setBounds(126, 84, 60, 20);
        jButton.setText("登录");
        this.add(jButton);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameForm(textField.getText().toString(),new String(passwordField.getPassword()));
                MainForm.frame.setVisible(false);
            }
        });


    }
}
