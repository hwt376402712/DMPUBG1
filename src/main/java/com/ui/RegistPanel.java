package com.ui;

import com.util.HttpRequestUtil;
import org.apache.commons.codec.binary.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author: huangwentao
 * @Date: 2018/8/24 17:20
 */
public class RegistPanel extends JPanel {

    private JLabel userLable;

    private JTextField textField;

    private JLabel passwordLable;

    private JPasswordField passwordField;

    private JLabel confirmPasswordLable;

    private JPasswordField confirmPasswordField;


    private JButton jButton;


    public RegistPanel() {
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

        confirmPasswordLable = new JLabel();
        confirmPasswordLable.setBounds(38, 74, 70, 30);
        confirmPasswordLable.setText("确认密码:");
        this.add(confirmPasswordLable);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(100, 81, 120, 20);
        confirmPasswordField.setText("");
        this.add(confirmPasswordField);


        jButton = new JButton();
        jButton.setBounds(126, 114, 60, 20);
        jButton.setText("注册");
        this.add(jButton);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (null == textField.getText() || "".equals(textField.getText())) {
                    JOptionPane.showMessageDialog(null, "用户名不能为空！ ", "提示", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!new String(passwordField.getPassword()).equals( new String(confirmPasswordField.getPassword()))) {
                        System.out.print(new String(passwordField.getPassword()).equals( new String(confirmPasswordField.getPassword())));
                        JOptionPane.showMessageDialog(null, "密码不一致！ ", "提示", JOptionPane.ERROR_MESSAGE);
                    } else {

                       String res =  HttpRequestUtil.get("http://111.231.249.197:8090/user/regist?" +
                                "username=" + textField.getText().toString() + "&password=" + passwordField.getText().toString());
                        JOptionPane.showMessageDialog(null, res, "提示", JOptionPane.INFORMATION_MESSAGE);

                    }

                }


            }
        });


    }

}
