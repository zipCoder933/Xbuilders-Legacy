package processing.ui4j.components;

///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package test.com.ui4j.components;
//
//import test.com.ui4j.EventAction;
//import test.com.ui4j.utils.UIUtils;
//import test.com.ui4j.UIExtension;
//import test.com.ui4j.UIFrame;
//import java.awt.Color;
//import java.text.DecimalFormat;
//import static processing.core.PConstants.CENTER;
//import static processing.core.PConstants.LEFT;
//import static processing.core.PConstants.RIGHT;
//import processing.event.KeyEvent;
//import processing.event.MouseEvent;
//
///**
// *
// * @author zipCoder933
// */
//public class Slider extends UIComponent {
//
//    /**
//     * @return the eventFireInterval
//     */
//    public long getEventFireInterval() {
//        return eventFireInterval;
//    }
//
//    /**
//     * @param eventFireInterval the eventFireInterval to set
//     */
//    public void setEventFireInterval(long eventFireInterval) {
//        this.eventFireInterval = eventFireInterval;
//    }
//
//    /**
//     * @param onchangeEvent the onchangeEvent to set
//     */
//    public void setOnchangeEvent(EventAction<Slider> onchangeEvent) {
//        this.onchangeEvent = onchangeEvent;
//    }
//
//    /**
//     * @return the wheelTuneInterval
//     */
//    public double getWheelTuneInterval() {
//        return wheelTuneInterval;
//    }
//
//    /**
//     * @param wheelTuneInterval the wheelTuneInterval to set
//     */
//    public void setWheelTuneInterval(double wheelTuneInterval) {
//        this.wheelTuneInterval = wheelTuneInterval;
//    }
//
//    /**
//     * @return the scrollTuningEnabled
//     */
//    public boolean wheelTuningEnabled() {
//        return scrollTuningEnabled;
//    }
//
//    /**
//     * @param scrollTuningEnabled the scrollTuningEnabled to set
//     */
//    public void enableWheelTuning(boolean scrollTuningEnabled) {
//        this.scrollTuningEnabled = scrollTuningEnabled;
//    }
//
//    /**
//     * @return the height
//     */
//    public int getHeight() {
//        return height;
//    }
//
//    /**
//     * @param height the height to set
//     */
//    public void setHeight(int height) {
//        this.height = height;
//    }
//
//    /**
//     * @return the label
//     */
//    public String getLabel() {
//        return label;
//    }
//
//    /**
//     * @param label the label to set
//     */
//    public void setLabel(String label) {
//        this.label = label;
//    }
//
//    /**
//     * @return the scrollSensitivity
//     */
//    public double getScrollSensitivity() {
//        return scrollSensitivity;
//    }
//
//    /**
//     * @param scrollSensitivity the scrollSensitivity to set
//     */
//    public void setScrollSensitivity(double scrollSensitivity) {
//        this.scrollSensitivity = UIUtils.clamp(scrollSensitivity, 0.1, 10);
//    }
//
//    /**
//     * @return the min
//     */
//    public double getMin() {
//        return min;
//    }
//
//    /**
//     * @param min the min to set
//     */
//    public void setBounds(double min, double max) {
//        if (max < min) {
//            min = max - 1;
//        }
//        this.min = min;
//        this.max = max;
//    }
//
//    /**
//     * @return the max
//     */
//    public double getMax() {
//        return max;
//    }
//
//    /**
//     * @return the step
//     */
//    public double getStep() {
//        return step;
//    }
//
//    /**
//     * @param step the step to set
//     */
//    public void setStep(Integer step) {
//        this.step = step;
//    }
//
//    /**
//     * @return the value
//     */
//    public double getValue() {
//        return value;
//    }
//
//    /**
//     * @param value the value to set
//     */
//    public void setValue(double value) {
//        setValueInternal(value);
//        lastValue = this.value;
//    }
//
//    private void setValueInternal(double value) {
//        if (value < min && (limitBounds == 0 || limitBounds == 2)) {
//            this.value = min;
//        } else if (value > max && (limitBounds == 1 || limitBounds == 2)) {
//            this.value = max;
//        } else {
//            if (step == null) {
//                this.value = value;
//            } else {
//                this.value = Math.round(value * step) / step;
//            }
//        }
//    }
//
//    private String defaultDecFormat = "0.000";
//
//    public Slider(UIFrame frame) {
//        super(frame);
//        addToFrame();
//        numberFormat = new DecimalFormat(defaultDecFormat);
//        setBounds(0, 10);
//    }
//
//    public Slider(UIExtension comp) {
//        super(comp);
//        addToFrame();
//        numberFormat = new DecimalFormat(defaultDecFormat);
//        setBounds(0, 10);
//    }
//
//    public Slider(UIFrame frame, double min, double max) {
//        super(frame);
//        addToFrame();
//        numberFormat = new DecimalFormat(defaultDecFormat);
//        setBounds(min, max);
//    }
//
//    public Slider(UIExtension comp, double min, double max) {
//        super(comp);
//        addToFrame();
//        numberFormat = new DecimalFormat(defaultDecFormat);
//        setBounds(min, max);
//    }
//
//    public boolean isOver() {
//        double mouseX = getMouseTransCoords().x;
//        double mouseY = getMouseTransCoords().y;
//        return x <= mouseX && mouseX <= x + width
//                && y <= mouseY && mouseY <= y + getHeight();
//    }
//
//    private int height = 28;
//    int x, y, width;
//    private double value;
//    private DecimalFormat numberFormat;
//
//    public void noStep() {
//        step = null;
//    }
//
//    private double min = 0;
//    private double max = 0;
//
//    int limitBounds = -1;
//
//    /**
//     * clamps the value to minimum
//     */
//    public void limitLowerBound() {
//        limitBounds = 0;
//    }
//
//    /**
//     * clamps the value to maximum
//     */
//    public void limitUpperBound() {
//        limitBounds = 1;
//    }
//
//    /**
//     * clamps the value to minimum and maximum
//     */
//    public void limitUpperAndLowerBounds() {
//        limitBounds = 2;
//    }
//
//    /**
//     * does not clamp the value
//     */
//    public void dontLimitBounds() {
//        limitBounds = -1;
//    }
//
//    private Integer step = null;
//
//    public void render(int x, int y) {
//        render(x, y, 200);
//    }
//
//    /**
//     *
//     * @param x
//     * @param y
//     * @param w the width
//     * @param h the height
//     */
//    public void render(int x, int y, int w, int h) {
//        this.height = h;
//        render(x, y, w);
//    }
//
//    /**
//     *
//     * @param x
//     * @param y
//     * @param w the width
//     */
//    public void render(int x, int y, int w) {
//        this.x = x;
//        this.y = y;
//        this.width = w;
//
//        fill(UIUtils.color(245, 245, 245));
//
//        if ((scrollMode || isOver()) && isEnabled()) {
//            getParentFrame().strokeWeight(1.05f);
////            setCursor(UIFrame.Cursor.HAND);
//            stroke(UIComponent.ACTIVE);
//        } else {
//            getParentFrame().strokeWeight(0.6f);
//            if (UIComponent.isDarkMode()) {
//                getParentFrame().stroke(60, 60, 60);
//            } else {
//                getParentFrame().stroke(150, 150, 150);
//            }
//        }
//        rect(x, y, w, getHeight(), 4);
//
//        if (UIComponent.isDarkMode()) {
//            fill(new Color(50, 50, 50));
//        } else {
//            fill(new Color(200, 200, 200));
//        }
//
//        noStroke();
//        rect(x, y, getParentFrame().map((float) UIUtils.clamp(value, min, max), (float) min, (float) max, 0, width), getHeight(), 4);
//
//        if (isEnabled()) {
//            fill(UIUtils.color(new Color(5, 5, 5)));
//        } else {
//            fill(UIUtils.color(new Color(40, 40, 40)));
//        }
//
//        String number = getDecimalFormat().format(getValue());
//
//        textSize(15);
//        if (getLabel() == null) {
//            textAlign(CENTER, CENTER);
//            text(number, x + (w / 2), y + (getHeight() / 2));
//        } else {
//            textAlign(RIGHT, CENTER);
//            text(getLabel(), x + (w / 2) - 3, y + (getHeight() / 2));
//            textAlign(LEFT, CENTER);
//            text(number, x + (w / 2) + 3, y + (getHeight() / 2));
//        }
//        over = isOver();
//        if (onchangeEvent != null) {
//            if (System.currentTimeMillis() - lastEvent
//                    > getEventFireInterval() || getEventFireInterval() <= 0) {
//                if (lastValue != value) {
//                    onchangeEvent.run(this);
//                    lastValue = value;
//                    lastEvent = System.currentTimeMillis();
//                }
//            }
//        }
//    }
//
//    boolean over = false;
//    boolean scrollMode = false;
//    double startScrollVal = 0;
//    float startMouseXPos = 0;
//    private double scrollSensitivity = 2;
//    private String label = null;
//    private boolean scrollTuningEnabled = true;
//    private double wheelTuneInterval = 1;
//
//    private double lastValue = 0;
//    private long lastEvent;
//
//    private long eventFireInterval = 10;
//    private EventAction<Slider> onchangeEvent;
//
//    @Override
//    public void mouseEvent(MouseEvent event) throws Exception {
//        if (event.getAction() == MouseEvent.PRESS) {
//            if (over && scrollMode == false) {
//                scrollMode = true;
//                startScrollVal = getValue();
//                startMouseXPos = getParentFrame().mouseX;
//            }
//        } else if (event.getAction() == MouseEvent.DRAG) {
//            if (scrollMode) {
//                float scrollLength = (float) (max - min);
//                setValueInternal(startScrollVal + getParentFrame().map(getParentFrame().mouseX,
//                        (float) (startMouseXPos - width * getScrollSensitivity()),
//                        (float) (startMouseXPos + width * getScrollSensitivity()),
//                        0 - scrollLength, scrollLength));
//            }
//        } else if (event.getAction() == MouseEvent.RELEASE) {
//            scrollMode = false;
//        } else if (event.getAction() == MouseEvent.WHEEL) {
//            if (over && wheelTuningEnabled()) {
//                if (event.getCount() < 0) {
//                    setValueInternal(getValue() + wheelTuneInterval);
//                } else {
//                    setValueInternal(getValue() - wheelTuneInterval);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void keyEvent(KeyEvent event) throws Exception {
//    }
//
//    @Override
//    public void onFrameClose() throws Exception {
//    }
//
//    /**
//     * @return the numberFormat
//     */
//    public DecimalFormat getDecimalFormat() {
//        return numberFormat;
//    }
//
//    /**
//     * @param format the numberFormat to set
//     */
//    public void setDecimalFormat(DecimalFormat format) {
//        this.numberFormat = format;
//    }
//
//}
