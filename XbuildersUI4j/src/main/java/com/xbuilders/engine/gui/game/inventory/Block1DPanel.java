/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.gui.game.inventory;

import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.game.items.GameItemList;
import processing.core.KeyCode;

import static processing.core.PConstants.BOTTOM;
import static processing.core.PConstants.CENTER;

import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.ui4j.UIExtension;

/**
 * @author zipCoder933
 */
public class Block1DPanel extends UIExtension {

    /**
     * @param currentItemIndex the currentItemIndex to set
     */
    public void setCurrentItemIndex(int currentItemIndex) {
        this.currentItemIndex = MathUtils.clamp(currentItemIndex, 0, itemList.length - 1);
    }

    /**
     * @return the currentItemIndex
     */
    public int getCurrentItemIndex() {
        return currentItemIndex;
    }

    /**
     * @return the numberOfBoxes
     */
    public int getNumberOfBoxes() {
        return numberOfBoxes;
    }

    /**
     * @param numberOfBoxes the numberOfBoxes to set (must be an even number)
     */
    public void setNumberOfBoxes(int numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
        if (this.numberOfBoxes % 2 != 0) {//If it is not an even number, make it one.
            this.numberOfBoxes++;
        }
    }

    private int currentItemIndex = 0;

    public void initialize() {
        currentItemIndex = 0;
    }

    public boolean curItemEquals(Item item) {
        if (getCurItem() != null) {
            Item curItem = getCurItem().getItem();
            return curItem.type == item.type && (curItem.id == item.id);
        }
        return false;
    }

    /**
     * @return the currentItemIndex
     */
    public ItemQuantity getCurItem() {
        if (itemList.length == 0) {
            return null;
        }
        return itemList[getCurrentItemIndex()];
    }

    /**
     * @return if the current item is empty (null)
     */
    public boolean curItemIsNull() {
        return getCurItem() == null || getCurItem().getItem() == null;
    }

    public void setCurItem(Item item) {
        if (GameItemList.isVisibleToGameList(item)) {
            for (int i = 0; i < itemList.length; i++) {
                if (itemList[i] != null && itemList[i].getItem() == item) {
                    currentItemIndex = i;
                    return;
                }
            }
            if (item.isInfiniteResource()) {
                for (int i = 0; i < itemList.length; i++) { //find an empty spot
                    if (itemList[i] == null) {
                        currentItemIndex = i;
                        break;
                    }
                }

                itemList[currentItemIndex] = new ItemQuantity(item);
            }
        }
    }

    public ItemQuantity[] itemList;
    UserControlledPlayer player;

    public Block1DPanel(ItemQuantity[] itemList, UserControlledPlayer player) {
        super(player);
        this.itemList = itemList;
        this.player = player;
        addToFrame();
    }

    private int boxSize = 55;
    private int numberOfBoxes = 12; //this must be an even number.

    public void render(PGraphics graphics) {
        int strokeWeight = 3;
        int pad = 4;
        graphics.fill(0, 100, 255);
        graphics.strokeWeight(strokeWeight);

        graphics.noFill();
        int start = getCurrentItemIndex() - (getNumberOfBoxes() / 2);
        int end = getCurrentItemIndex() + (getNumberOfBoxes() / 2);
        if (start < 0) {
            start = 0;
            end = getNumberOfBoxes();
        }
        if (end > itemList.length) {
            end = itemList.length;
            start = itemList.length - getNumberOfBoxes();
        }

        int indx = 0;
        int size = (getNumberOfBoxes() * boxSize) / 2;
        int offset = (getParentFrame().width / 2) - size;
        for (int i = start; i < end; i++) {
            graphics.noFill();
            graphics.stroke(190);
            graphics.rect(offset + (indx * boxSize + pad),
                    graphics.height - (boxSize + pad) - 5, boxSize, boxSize);

            if (indx < itemList.length && itemList[i] != null) {//Draw icon
                int xPos = offset + (indx * boxSize + pad);
                int yPos = graphics.height - (boxSize + pad) - 5;
                drawIcon(itemList[i], xPos, yPos, boxSize - 5);
                graphics.strokeWeight(strokeWeight);
            }

            if (getCurrentItemIndex() == i) {
                graphics.noFill();
                graphics.stroke(255);
                graphics.rect(offset + (indx * boxSize + pad) - 5,
                        graphics.height - (boxSize + pad) - 5 - 5, boxSize + 10, boxSize + 10);
            }

            indx++;
        }

        ItemQuantity curItem = getCurItem();

        if (curItem != null) {
            graphics.fill(255);
            graphics.textAlign(CENTER, BOTTOM);
            Item item = curItem.getItem();
            graphics.text(item.name, graphics.width / 2, graphics.height - 80);
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (keyIsPressed(KeyCode.Q) && itemList[getCurrentItemIndex()] != null) {
            itemList[getCurrentItemIndex()].getItem().onDropEvent();
            if (!itemList[getCurrentItemIndex()].isInfiniteResource()) {
                player.dropItem(itemList[getCurrentItemIndex()]);
            }
            itemList[getCurrentItemIndex()] = null;
        } else if (keyIsPressed(KeyCode.COMMA)) {
            changeSelection(-1);
        } else if (keyIsPressed(KeyCode.PERIOD)) {
            changeSelection(1);
        }
    }

    /**
     * @param item
     * @param xPos    the x origin point
     * @param yPos    the y origin point
     * @param boxSize the size (width) of the icon
     */
    public void drawIcon(ItemQuantity item, int xPos, int yPos, int boxSize) {
        item.drawIcon(getParentFrame(), xPos, yPos, boxSize);
    }

    public void setCurItemFromWorld(EntityLink link) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    }

    public void changeSelection(int count) {
        player.blockModes.resetBlockMode();
        setCurrentItemIndex(getCurrentItemIndex() + count);
        if (getCurrentItemIndex() > itemList.length - 1) {
            setCurrentItemIndex(0);
        } else if (getCurrentItemIndex() < 0) {
            setCurrentItemIndex(itemList.length - 1);
        }
    }

    @Override
    public void mouseEvent(MouseEvent me) {
    }

}
