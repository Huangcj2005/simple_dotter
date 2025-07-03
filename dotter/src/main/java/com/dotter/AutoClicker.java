package com.dotter;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClicker implements Runnable{
    private int freq;   // 每秒点击次数
    private String clickKey;    // 连点按键
    private volatile boolean running = false;

    public AutoClicker(int freq, String clickKey){
        this.freq = freq;
        this.clickKey = clickKey;
    }
    @Override
    public void run() {
        running = true;
        try{
            Robot robot = new Robot();
            int delay = Math.min(10,1000/freq); // 控制最大频率为 100次/s
            while (running){
                Thread.sleep(delay);
                if("mouse_left".equalsIgnoreCase(clickKey)){
                    // 鼠标左键
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }else if("mouse_right".equalsIgnoreCase(clickKey)){
                    // 鼠标右键
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                }else{
                    // 输入其他按键
                    int keyCode = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(
                            clickKey.toUpperCase().charAt(0)
                    );
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                }

                System.out.println("点击成功");
                Thread.sleep(delay);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
        running = false;
    }
}
