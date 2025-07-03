package com.dotter;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import javax.swing.*;

public class DotterMain {
    private static AutoClicker autoClicker;
    private static Thread clickThread;
    private static boolean isRunning = false;
    private static String DEFAULT_TRIGGER_KEY = "F6";
    private static String DEFAULT_CLICK_KEY = ";";
    private static String DEFAULT_FREQ = "10";

    public static void main(String[] args){
        // 创建窗口
        JFrame frame = new JFrame("简易连点器");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350,200);
        frame.setLayout(null);  // 居中

        // 触发键
        JLabel triggerLabel = new JLabel("触发键：");
        triggerLabel.setBounds(20, 20, 60, 25);
        frame.add(triggerLabel);

        JTextField triggerField = new JTextField();
        triggerField.setBounds(90, 20, 100, 25);
        triggerField.setText(DEFAULT_TRIGGER_KEY);
        frame.add(triggerField);

        // 连点按键
        JLabel clickKeyLabel = new JLabel("连点按键：");
        clickKeyLabel.setBounds(20, 60, 60, 25);
        frame.add(clickKeyLabel);

        JTextField clickKeyField = new JTextField();
        clickKeyField.setBounds(90, 60, 100, 25);
        clickKeyField.setText(DEFAULT_CLICK_KEY);
        frame.add(clickKeyField);

        // 频率
        JLabel freqLabel = new JLabel("频率(次/秒)：");
        freqLabel.setBounds(20, 100, 80, 25);
        frame.add(freqLabel);

        JTextField freqField = new JTextField();
        freqField.setBounds(110, 100, 80, 25);
        freqField.setText(DEFAULT_FREQ);
        frame.add(freqField);

        // 开始按钮
        JButton startButton = new JButton("开始");
        startButton.setBounds(220, 60, 80, 40);
        frame.add(startButton);

        // 开始按钮监听器
        startButton.addActionListener(e -> {
            if(!isRunning){
                String freqText = freqField.getText();
                int freq = 10;
                try{
                    freq = Integer.parseInt(freqText);
                    if(freq <= 0) freq = 10;
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(frame, "请输入有效的频率");
                    return;
                }

                String clickKey = clickKeyField.getText();
                if(clickKey == null || clickKey.trim().isEmpty()){
                    // 默认左键
                    clickKey = "mouse_left";
                }

                // 在这里应该需要

                // 启动连点
                autoClicker = new AutoClicker(freq,clickKey);
                clickThread = new Thread(autoClicker);
                clickThread.start();
                isRunning = true;
                startButton.setText("停止");
            }else{
                // 停止连点
                if(autoClicker != null){
                    autoClicker.stop();
                }
                isRunning = false;
                startButton.setText("开始");
            }
        });

        // 显示窗口
        frame.setVisible(true);

        try{
            String triggerKey = triggerField.getText().trim();
            if(triggerKey.isEmpty()) triggerKey = DEFAULT_TRIGGER_KEY;

            GlobalScreen.registerNativeHook();
            GlobalHotketListener hotketListener = new GlobalHotketListener(triggerKey, () ->{
                // 这里切换开始/停止
                SwingUtilities.invokeLater(() -> startButton.doClick());
            });
            GlobalScreen.addNativeKeyListener(hotketListener);
        }catch (NativeHookException ex){
            ex.printStackTrace();
        }
    }
}
