package com.dotter;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class GlobalHotketListener implements NativeKeyListener {
    private String triggerKey;
    private Runnable onHotkeyPressed;

    public GlobalHotketListener(String triggerKey, Runnable onHotkeyPressed){
        this.triggerKey = triggerKey;
        this.onHotkeyPressed = onHotkeyPressed;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e){
        // 判断按下的键是否为设置的触发键
        if(NativeKeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(triggerKey)){
            onHotkeyPressed.run();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}


}
