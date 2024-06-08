/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.gui.game.inventory;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameScene;
import com.xbuilders.engine.gui.Button;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.gui.game.GameMenu;
import com.xbuilders.engine.gui.game.GameMenuPage;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.utils.InventoryUtils;
import com.xbuilders.engine.world.World;

import java.io.IOException;
import java.util.ArrayList;

import processing.core.KeyCode;

import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

import processing.core.PConstants;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.ui4j.components.TextBox;

/**
 * @author zipCoder933
 */
public class Inventory extends GameMenuPage {

    final int width = 780;
    final int height = 575 + 55 + 45;

    ItemGrid<Item> itemsUIList;
    ItemGrid<ItemQuantity> backpackUIList;
    DragHandler<Integer> drag;
    TextBox search;
    Button clearButton, organizeButton;

    public boolean isWillingToClose() {
        return !search.isFocused();
    }


    private boolean containsItem(ArrayList<ItemQuantity> items, ItemQuantity item) {
        for (ItemQuantity j : items) {
            if (j.getItem().equals(item.getItem())) {
                return true;
            }
        }
        return false;
    }

    public Inventory(GameMenu parent) throws IOException {
        super(parent);
        drag = new DragHandler();
        search = new TextBox(getGameScene(), "Search for block");
        search.setBorderWidth(1);
        search.setTextSize(14);
        clearButton = new Button(parent);
        organizeButton = new Button(parent);


        itemsUIList = new ItemGrid<Item>(this, drag) {
            @Override
            public void buttonClicked(Item selectedItem, int index) {
                if (InventoryUtils.isRoomForItem(backpackUIList.itemList, new ItemQuantity(selectedItem))) {
                    InventoryUtils.appendBlocksToInventory(backpackUIList.itemList, new ItemQuantity(selectedItem));
                } else {
                    for (int i = 0; i < backpackUIList.itemList.length; i++) {
                        if (backpackUIList.itemList[i] != null && backpackUIList.itemList[i].isInfiniteResource()) {
                            backpackUIList.itemList[i] = null;
                            break;
                        }
                    }
                    InventoryUtils.appendBlocksToInventory(backpackUIList.itemList, new ItemQuantity(selectedItem));
                }
            }

            @Override
            public String getTooltipForItem(Item t) {
                return t.name;
            }

            @Override
            public void drawIcon(Item t, int buttonX, int buttonY, int buttonSize) {
                ItemList.iconManager.drawItemIcon(this.getParentFrame(), t, buttonX, buttonY, buttonSize);
            }

            @Override
            public void onItemDraggedHere(Object originObject, int newIndex, int originIndex) {
            }

            @Override
            public void onItemDroppedOutsideOfGrid(Object o, int i) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
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

        };
        itemsUIList.customCriteria = (i) -> {
            if (search.getValue() == null
                    || search.getValue().isEmpty()
                    || search.getValue().isBlank()) {
                return true;
            }
            return i.name.toLowerCase().contains(search.getValue().toLowerCase())
                    || i.containsPieceOfTag(search.getValue());
        };

        backpackUIList = new ItemGrid<ItemQuantity>(this, drag) {

            @Override
            public void buttonClicked(ItemQuantity selectedItem, int index) {
                GameScene gameScene = getGameScene();
                gameScene.player.blockPanel.setCurrentItemIndex(index);
            }

            @Override
            public void drawButtonBorder(int buttonX, int buttonY, int buttonSize, int itemIndex) {
                GameScene gameScene = getGameScene();
                if (itemIndex == gameScene.player.blockPanel.getCurrentItemIndex()) {
                    noFill();
                    stroke(255, 200);
                    strokeWeight(3);
                    rect(buttonX - 2, buttonY - 2, buttonSize + 4, buttonSize + 4);
                }
            }

            @Override
            public String getTooltipForItem(ItemQuantity t) {
                return t.getItem().name;
            }

            @Override
            public void drawIcon(ItemQuantity t, int buttonX, int buttonY, int buttonSize) {
                if (t != null) {
                    t.drawIcon(getParentFrame(), buttonX, buttonY, buttonSize);
                }
            }

            @Override
            public void onItemDraggedHere(Object o, int old, int target) {
                if (o == this) {
                    if (itemList[target] == null) {
                        itemList[target] = itemList[old];
                        itemList[old] = null;
                    } else if (itemList[old].getItem() == itemList[target].getItem()) {
                        itemList[old].setQuantity(itemList[target].addQuantity(itemList[old].getQuantity()));
                    }
                } else {
                    Item menuItem = itemsUIList.itemList[old];
                    System.out.println("Dropping" + menuItem.toString());
                    ItemQuantity iq = new ItemQuantity(menuItem);
                    this.itemList[target] = iq;
                }
            }

            @Override
            public void onItemDroppedOutsideOfGrid(Object o, int old) {
                if (o == this && itemList[old] != null) {
                    if (itemList[old].isInfiniteResource()) {
                        itemList[old].getItem().onDropEvent();
                        itemList[old] = null;
                    }
                }
            }


            @Override
            public void keyPressed(KeyEvent ke) {
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

        };

        clearButton.setAction(() -> {
            ItemQuantity[] backpack = VoxelGame.getWorld().infoFile.getInfoFile().backpack;
            for (int i = 0; i < backpack.length; i++) {
                backpack[i] = null;
            }
            VoxelGame.getPlayer().blockPanel.setCurrentItemIndex(0);
        });

        organizeButton.setAction(() -> {
            //If an item is an infinite resource, there should only be 1 item in the backpack
            //Shift all items in the backpack to the top
            ItemQuantity[] backpack = VoxelGame.getWorld().infoFile.getInfoFile().backpack;

            ArrayList<ItemQuantity> items = new ArrayList<>();

            for (int i = 0; i < backpack.length; i++) {
                ItemQuantity item = backpack[i];
                backpack[i] = null;

                if (item != null) {
                    if (item.getItem().isInfiniteResource()) {
                        if (!containsItem(items, item)) items.add(item);
                    } else {
                        items.add(item);
                    }
                }
            }
            VoxelGame.getPlayer().blockPanel.setCurrentItemIndex(0);
            for (int i = 0; i < items.size(); i++) {
                if (i < items.size()) {
                    backpack[i] = items.get(i);
                }
            }
        });

        addToFrame();
    }

    @Override
    public void initialize(World world) {
        Item[] arr = new Item[getGameScene().gameItems.size()];
        backpackUIList.setDimensions(world.infoFile.getInfoFile().backpack, 12, 2);
        itemsUIList.setDimensions(getGameScene().gameItems.toArray(arr), 12, 5);
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (getParent().getPointerHandler().getWorld().isOpen()) {
            GameScene gameScene1 = getGameScene();
            int curItemIndex = gameScene1.player.blockPanel.getCurrentItemIndex();
            ItemQuantity iq = backpackUIList.itemList[curItemIndex];

            if (keyIsPressed(KeyCode.Q) && iq != null) {
                if (!iq.isInfiniteResource()) {
                    GameScene gameScene = getGameScene();
                    gameScene.player.dropItem(iq);
                }
                iq.getItem().onDropEvent();
                backpackUIList.itemList[curItemIndex] = null;
            }

        }
    }

    @Override
    public void render() {
        if (getParent().getPointerHandler().getWorld().isOpen()) {
            int x1 = (getParentFrame().width / 2) - (width / 2);
            int menuX = x1 + 30;
            int y1 = (getParentFrame().height / 2) - (height / 2);
            getParent().menuBackground("Item Selection", x1, y1, width, height);
            y1 += 80;
            textAlign(LEFT, TOP);
            textSize(11);
            fill(255);
            text("All Items", menuX, y1);
            y1 += 25;
            if (!search.isOver()) {
                getParent().getParentFrame().cursor(PConstants.ARROW);
            }
            search.render(menuX, y1, width - 60, 30);
            y1 += 40;
            itemsUIList.render(menuX, y1);
            y1 += 310;
            textAlign(LEFT, TOP);
            textSize(11);
            fill(255);
            text("Your Items", menuX, y1);
            y1 += 25;
            int w = clearButton.draw("Clear", menuX, y1);
            organizeButton.draw("Organize", menuX + w + 5, y1);
            y1 += 45;
            backpackUIList.render(menuX, y1);
        }
    }

    @Override
    public void onShow() {
    }

    @Override
    public void onHide() {
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

    @Override
    public void mouseEvent(MouseEvent me) {
    }

}
