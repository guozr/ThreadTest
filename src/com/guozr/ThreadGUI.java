/*
 * Copyright (c) 2018, Guo ZR. All rights reserved
 */

package com.guozr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.guozr.StartThread.*;

public class ThreadGUI {
    private JPanel mainPanel;
    private JPanel startPanel;
    private JPanel textInPanel;
    private JButton startButton;
    private JTextField textFieldS;
    private JTextField textFieldM;
    private JTextField textFieldK;
    private JTextField textFieldN;
    private JTextField textFieldT;
    private JTextField textFieldD;
    private JButton calButton;
    private JPanel showPanel;
    private JTextArea textArea1;
    private JButton clearButton;
    private CardLayout cardLayout = new CardLayout();

    private ThreadGUI() {

        mainPanel.setLayout(cardLayout);
        mainPanel.add(startPanel, "Card1");
        mainPanel.add(textInPanel, "Card2");

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "Card2");
            }
        });
        calButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                S = Integer.parseInt(textFieldS.getText());
                M = Integer.parseInt(textFieldM.getText());
                K = Integer.parseInt(textFieldK.getText());
                N = Integer.parseInt(textFieldN.getText());
                T = Integer.parseInt(textFieldT.getText());
                D = Integer.parseInt(textFieldD.getText());
                if (S * K > M * N || S < 0 || K < 0 || M < 0 || N < 0) {
                    JOptionPane.showMessageDialog(null, "输入不合法！请重新输入！");
                } else {
                    StartThread.startThread();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    textArea1.setText(Util.getText(pathName));
                }
            }
        });
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Util.clearText(pathName);
                textFieldS.setText("");
                textFieldM.setText("");
                textFieldK.setText("");
                textFieldN.setText("");
                textFieldT.setText("");
                textFieldD.setText("");
                textArea1.setText("");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ThreadGUI");
        frame.setContentPane(new ThreadGUI().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 400);
        frame.setVisible(true);
    }
}
