package com.z.loa.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Android设备性能叠加层
 * 功能：实时显示FPS、Java堆内存、本地堆内存
 */
public class AndroidDeviceOverlay {
    private Stage stage;
    private Table overlayTable;
    private Label fpsLabel;
    private Label javaHeapLabel;
    private Label nativeHeapLabel;
    
    private long lastUpdateTime = 0;
    private long frameCount = 0;
    private float averageFPS = 0;
    
    private boolean visible = true;
    private Color normalColor = Color.GREEN;
    private Color warningColor = Color.YELLOW;
    private Color criticalColor = Color.RED;
    
    // Android性能阈值
    private float fpsWarningThreshold = 30f;      // FPS警告阈值
    private float fpsCriticalThreshold = 20f;     // FPS危险阈值
    private float memoryWarningMB = 200f;         // 内存警告阈值(MB)
    private float memoryCriticalMB = 300f;        // 内存危险阈值(MB)
    
    public AndroidDeviceOverlay(Stage stage) {
        this.stage = stage;
        createOverlayUI();
    }
    
    private void createOverlayUI() {
        // 使用默认字体
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont font = new BitmapFont();
        font.getData().scale(2.0f);
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        
        // 创建叠加层表格
        overlayTable = new Table();
        overlayTable.setFillParent(true);
        overlayTable.top().left();
        overlayTable.pad(10);
        
        // 创建标签
        fpsLabel = new Label("FPS: 0", labelStyle);
        javaHeapLabel = new Label("Java: 0 MIB", labelStyle);
        nativeHeapLabel = new Label("Native: 0 MIB", labelStyle);
        
        // 布局
        overlayTable.add(fpsLabel).padRight(20).row();
        overlayTable.add(javaHeapLabel).padRight(20).row();
        overlayTable.add(nativeHeapLabel);
        
        // 添加背景
        overlayTable.setBackground(createBackgroundDrawable());
        
        
    }
    
    public void addSt() {
    	stage.addActor(overlayTable);
    }
    
    private Drawable createBackgroundDrawable() {
        Pixmap pixmap = new com.badlogic.gdx.graphics.Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.0f);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
    }
    
    
    public void update() {
        if (!visible) return;
        
        long currentTime = TimeUtils.nanoTime();
        
        // 计算FPS
        frameCount++;
        if (currentTime - lastUpdateTime >= 1000000000L) { // 1秒 = 1,000,000,000纳秒
            averageFPS = frameCount;
            frameCount = 0;
            lastUpdateTime = currentTime;
            
            // 获取Android内存信息
            long javaHeapMB = Gdx.app.getJavaHeap() / 1024 / 1024;
            long nativeHeapMB = Gdx.app.getNativeHeap() / 1024 / 1024;
            
            // 更新标签
            updateLabels(averageFPS, javaHeapMB, nativeHeapMB);
            
            // 根据阈值更新颜色
            updateLabelColors(averageFPS, javaHeapMB, nativeHeapMB);
        }
    }
    
    private void updateLabels(float fps, long javaHeapMB, long nativeHeapMB) {
        fpsLabel.setText(String.format("FPS: %.1f", fps));
        javaHeapLabel.setText(String.format("Java: %d MB", javaHeapMB));
        nativeHeapLabel.setText(String.format("Native: %d MB", nativeHeapMB));
    }
    
    private void updateLabelColors(float fps, long javaHeapMB, long nativeHeapMB) {
        // 设置FPS颜色
        if (fps < fpsCriticalThreshold) {
            fpsLabel.setColor(criticalColor);
        } else if (fps < fpsWarningThreshold) {
            fpsLabel.setColor(warningColor);
        } else {
            fpsLabel.setColor(normalColor);
        }
        
        // 设置Java堆内存颜色
        if (javaHeapMB > memoryCriticalMB) {
            javaHeapLabel.setColor(criticalColor);
        } else if (javaHeapMB > memoryWarningMB) {
            javaHeapLabel.setColor(warningColor);
        } else {
            javaHeapLabel.setColor(normalColor);
        }
        
        // 设置本地堆内存颜色
        if (nativeHeapMB > memoryCriticalMB) {
            nativeHeapLabel.setColor(criticalColor);
        } else if (nativeHeapMB > memoryWarningMB) {
            nativeHeapLabel.setColor(warningColor);
        } else {
            nativeHeapLabel.setColor(normalColor);
        }
    }
    
    // ==================== 公共方法 ====================
    
    /**
     * 设置叠加层可见性
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        overlayTable.setVisible(visible);
    }
    
    /**
     * 切换叠加层可见性
     */
    public void toggleVisibility() {
        setVisible(!visible);
    }
    
    public void setPosition(float x, float y) {
        overlayTable.setPosition(x, y);
    }
    
    /**
     * 设置背景透明度
     * @param alpha 透明度 (0.0-1.0)
     */
    public void setBackgroundAlpha(float alpha) {
        Color color = overlayTable.getColor();
        color.a = alpha;
        overlayTable.setColor(color);
    }
    
    /**
     * 清理资源
     */
    public void dispose() {
        overlayTable.remove();
    }
    
    // ==================== Android特定方法 ====================
    
    /**
     * 获取Android设备总内存（MB）
     * 需要在Android主线程调用
     */
    public long getTotalDeviceMemoryMB() {
        // 此方法需要在Android环境中实现
        // 这里返回一个估计值，实际项目中应该通过Android API获取
        return 2048; // 假设2GB
    }
    
    /**
     * 获取当前内存使用率
     */
    public float getMemoryUsagePercentage() {
        long javaHeap = Gdx.app.getJavaHeap();
        long nativeHeap = Gdx.app.getNativeHeap();
        long totalUsed = javaHeap + nativeHeap;
        long maxMemory = Runtime.getRuntime().maxMemory();
        
        return (float) totalUsed / maxMemory;
    }
}
