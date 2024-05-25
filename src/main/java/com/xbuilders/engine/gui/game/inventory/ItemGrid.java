/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.gui.game.inventory;

import com.xbuilders.engine.utils.math.MathUtils;
import processing.ui4j.UIExtension;
import com.xbuilders.engine.gui.GuiUtils;

import java.util.ArrayList;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;

import processing.event.MouseEvent;

/**
 * @param <T>
 * @author zipCoder933
 */
public abstract class ItemGrid<T> extends UIExtension {

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return the windowCols
     */
    public int getWindowCols() {
        return windowCols;
    }

    /**
     * @return the windowRows
     */
    public int getWindowRows() {
        return windowRows;
    }

    public ItemGrid(Inventory parent, DragHandler<Integer> drag) {
        super(parent);
        this.drag = drag;
        addToFrame();
    }

    DragHandler<Integer> drag;
    public T[] itemList;
    private int windowCols;
    private int windowRows;

    public void setDimensions(T[] itemList, int windowCols, int windowRows) {
        columns = 0;
        this.itemList = itemList;
        buttons = new ArrayList<>();
        this.windowCols = windowCols;
        this.windowRows = windowRows;
        this.innerHeight = (windowRows * boxSize) + 6;
        this.innerWidth = (windowCols * boxSize) + 6;

        for (int i = 0; i < itemList.length; i++) {
            buttons.add(new InventoryBlockButton(this));
        }
        calculateRows(itemList.length);
    }

    private void calculateRows(int itemsBeingDrawn) {
        int bx = 0;
        int by = 0;
        int maxRows2 = 0;

        for (int i = 0; i < itemsBeingDrawn; i++) {
            bx++;
            if ((bx * boxSize) + boxSize > innerWidth) {
                if (columns == 0) {
                    columns = bx;
                }
                bx = 0;
                by++;
                if ((by * boxSize) + boxSize < innerHeight) {
                    maxRows2++;
                }
            }
        }
        rows = by - maxRows2;
//        needsScrolling = windowRows <= rows;
        needsScrolling=true; //The scrollbar is allready going to be hidden if we don't need it
    }

    private boolean needsScrolling;
    protected int highlightedItem = 0;
    boolean buttonHovered = false;
    protected ArrayList<InventoryBlockButton> buttons;
    int innerHeight = 0;
    int innerWidth = 0;
    protected int columns = 0;
    private int scrollAmt = 0;
    private int rows = 0;
    public CustomCriteria<T> customCriteria;

    @FunctionalInterface
    public static interface CustomCriteria<T> {
        public boolean test(T item);
    }

    protected final int boxPadding = 5;
    protected final int boxSize = 57;
    protected final int buttonSize = boxSize - boxPadding;
    int lastNumberOfTotalItems = 0;
    int innerX1, innerY1;

    public void render(int innerX1, int innerY1) {
        //Recalculate the rows if needed
        int totalItems = 0;
        for (int i = 0; i < itemList.length; i++) {
            if (customCriteria != null && !customCriteria.test(itemList[i])) continue;
            totalItems++;
        }
        if (totalItems != lastNumberOfTotalItems) {
//            System.out.println("Recalculating Rows: " + totalItems);
            lastNumberOfTotalItems = totalItems;
            scrollAmt = 0;
            calculateRows(totalItems);
        }


        fill(0, 100);
        noStroke();
        this.innerX1 = innerX1;
        this.innerY1 = innerY1;

        rect(innerX1 - 5, innerY1 - 5, innerWidth + 10, innerHeight + 10);
        int bx = 0;
        int by = scrollAmt;

        boolean foundBlock = false;
        for (int i = 0; i < itemList.length; i++) {
            if (customCriteria != null && !customCriteria.test(itemList[i])) continue;
            if ((by * boxSize) + boxSize > 0) {
                int buttonX = (innerX1 + (bx * boxSize)) + 4;
                int buttonY = (innerY1 + (by * boxSize) + 4);

                drawButtonBorder(buttonX, buttonY, buttonSize, i);
                if (itemList[i] != null
                        && !(drag.isHoldStarted() && drag.getOriginObject() == this && (int) drag.getOriginProperties() == i)) {
                    drawIcon(itemList[i], buttonX, buttonY, buttonSize);
                }
                buttons.get(i).draw(buttonX - 3, buttonY - 3, buttonSize + 6, buttonSize + 6);
                if (buttons.get(i).isOver()) {
                    foundBlock = true;
                    buttonHovered = true;
                    highlightedItem = i;
                }
            }

            if (!foundBlock) {
                buttonHovered = false;
            }

            bx++;
            if ((bx * boxSize) + boxSize > innerWidth) {
                bx = 0;
                by++;
            }
            if ((by * boxSize) + boxSize > innerHeight) {
                break;
            }
        }


        noStroke();
        if (needsScrolling) {
            GuiUtils.drawScrollbar(this.getParentFrame(), innerX1 + innerWidth + 15, innerY1 - 5, innerHeight + 10,
                    0 - scrollAmt, getRows());
        }
        if (buttonHovered && itemList[highlightedItem] != null) {
            tooltip(getTooltipForItem(itemList[highlightedItem]));
            if (getParentFrame().mousePressed && mouseHasMoved() && !drag.isHoldStarted()) {
                drag.startHold(this, highlightedItem);
            }
        }

        if (drag.isHoldStarted() && drag.getOriginObject() == this) {
            drawIcon(itemList[(int) drag.getOriginProperties()], getParentFrame().mouseX - buttonSize / 2, getParentFrame().mouseY - buttonSize / 2, buttonSize);
            if (!getParentFrame().mousePressed && releaseHit) {
                drag.endHold();
                releaseHit = false;
                onItemDroppedOutsideOfGrid(drag.getOriginObject(), drag.getOriginProperties());
            }
        }
    }

    boolean releaseHit = false;

    protected void tooltip(String text) {
        fill(0);
        textSize(11);
        textAlign(LEFT, CENTER);
        rect(getParentFrame().mouseX, getParentFrame().mouseY,
                getParentFrame().textWidth(text) + 20, 30);
        fill(255);
        text(text, getParentFrame().mouseX + 10, getParentFrame().mouseY + 15);
    }

    public boolean mouseOver() {
        return getParentFrame().mouseX > innerX1 && getParentFrame().mouseY > innerY1
                && getParentFrame().mouseX < innerX1 + innerWidth
                && getParentFrame().mouseY < innerY1 + innerHeight;
    }

    public int getMaxScrollAmount() {
        return 0 - getRows();
    }

    public void setScrollAmount(int val) {
        scrollAmt = val;
        if (scrollAmt > 0) {
            scrollAmt = 0;
        }
        int maxScroll2 = getMaxScrollAmount();
        if (scrollAmt < maxScroll2) {
            scrollAmt = maxScroll2;
        }
    }

    public int getScrollAmount() {
        return scrollAmt;
    }

    @Override
    public void mouseEvent(MouseEvent me) {
        if (mouseOver()) {
            if (me.getAction() == MouseEvent.WHEEL && needsScrolling) {
                setScrollAmount(scrollAmt - me.getCount());
            } else if (me.getAction() == MouseEvent.RELEASE) {
                if (buttonHovered) {
                    if (drag.isHoldStarted()) {
                        onItemDraggedHere(drag.getOriginObject(), (int) drag.getOriginProperties(), highlightedItem);
                        drag.endHold();
                    }
                    buttonClicked(itemList[highlightedItem], highlightedItem);
                }
            }
        }

        if (me.getAction() == MouseEvent.RELEASE) {
            releaseHit = true;
        }
    }

    public abstract void buttonClicked(T item, int index);

    public abstract String getTooltipForItem(T name);

    //ItemList.iconList.drawItemIcon(this.getParentFrame(), itemList[i], buttonX, buttonY, buttonSize);
    public abstract void drawIcon(T name, int buttonX, int buttonY, int buttonSize);

    /**
     * @param buttonX
     * @param buttonY
     * @param buttonSize
     * @param itemIndex  the index of the item
     */
    public void drawButtonBorder(int buttonX, int buttonY, int buttonSize, int itemIndex) {
//        if (search.getValue() != null
//                && itemList[itemIndex] != null
//                && !search.getValue().isEmpty()
//                && !search.getValue().isBlank()
//                && itemList[itemIndex].getName().toLowerCase().contains(
//                search.getValue().toLowerCase())) {
//            noFill();
//            stroke(255, 200);
//            strokeWeight(3);
//            rect(buttonX - 2, buttonY - 2, buttonSize + 4, buttonSize + 4);
//        }
    }

    private boolean mouseHasMoved() {
        return getParentFrame().mouseX != getParentFrame().pmouseX
                || getParentFrame().mouseY != getParentFrame().pmouseY;
    }

    /**
     * @param originObject the origin object that once had the item
     * @param originIndex  the item index of the origin box
     * @param newIndex     the item index of the target box (here)
     */
    public abstract void onItemDraggedHere(Object originObject, int newIndex, int originIndex);

    /**
     * @param originObject the object that once had the item
     * @param i            the item index
     */
    public abstract void onItemDroppedOutsideOfGrid(Object originObject, int i);
}
