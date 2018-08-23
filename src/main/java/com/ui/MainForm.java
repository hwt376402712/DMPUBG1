package com.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @Author: huangwentao
 * @Date: 2018/8/23 11:47
 */
public class MainForm extends JPanel {

    private JTabbedPane jTabbedpane = new JTabbedPane();// 存放选项卡的组件
    private String[] tabNames = {"登录", "充值"};
    ImageIcon icon = createImageIcon("");

    public static JFrame frame;

    public MainForm() {
        layoutComponents();
    }

    private void layoutComponents() {
        int i = 0;
        // 第一个标签下的JPanel
        JPanel jpanelFirst = new LoginPanel();
        // jTabbedpane.addTab(tabNames[i++],icon,creatComponent(),"first");//加入第一个页面
        jTabbedpane.addTab(tabNames[i++], icon, jpanelFirst, "first");// 加入第一个页面

        // 第二个标签下的JPanel
        JPanel jpanelSecond = new JPanel();
        jTabbedpane.addTab(tabNames[i++], icon, jpanelSecond, "second");// 加入第一个页面
        setLayout(new GridLayout(1, 1));
        add(jTabbedpane);

    }

    private ImageIcon createImageIcon(String path) {

        URL url = MainForm.class.getResource(path);
        if (url == null) {
            System.out.println("the image " + path + " is not exist!");
            return null;
        }
        return new ImageIcon(url);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                //JFrame.setDefaultLookAndFeelDecorated(true);// 将组建外观设置为Java外观
                frame = new JFrame("PUBG幕后玩家");
                frame.setLayout(null);
                frame.setContentPane(new MainForm());
                frame.setSize(360, 250);
                frame.setDefaultCloseOperation(3);
                frame.setLocation(550,400);
                frame.setVisible(true);
            }
        });

    }

}