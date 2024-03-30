/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items;

import com.xbuilders.engine.utils.math.MathUtils;
import java.util.Objects;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.TOP;
import processing.ui4j.UIExtensionFrame;

/**
 *
 * @author zipCoder933
 */
public class ItemQuantity {

    public ItemQuantity() {
    }

    public ItemQuantity(Item item) {
        if (item == null) {
            throw new NullPointerException("The item in an itemQuantity can't be null!");
        }
        this.item = item;
        this.quantity = (byte) item.maxStackSize();
        if (this.quantity == Item.INFINITE_RESOURCE_AMT) {
            this.quantity = 1;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.item);
        hash = 29 * hash + this.quantity;
        return hash;
    }

    @Override
    public String toString() {
        return "ItemQuantity{" + "item=" + item + ", quantity=" + quantity + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemQuantity other = (ItemQuantity) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        return Objects.equals(this.item, other.item);
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        if (isInfiniteResource())
            return;
        this.quantity = (byte) quantity;
    }

    /**
     * @param add the amount to add
     * @return the amount left over
     */
    public int addQuantity(int add) {
        if (isInfiniteResource())
            return 0;
            
        this.quantity += add;

        if (quantity > getItem().maxStackSize()) {
            int leftover = quantity - getItem().maxStackSize();
            quantity = (byte) getItem().maxStackSize();
            return leftover;
        } else if (quantity < 0) {
            quantity = 0;
        }
        return 0;
    }

    public int getLeftoverQuantity(int add) {
        if (quantity + add > getItem().maxStackSize()) {
            return (quantity + add) - getItem().maxStackSize();
        }
        return 0;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemQuantity(Item item, byte quantity) {
        if (item == null) {
            throw new NullPointerException("The item in an itemQuantity can't be null!");
        }
        this.item = item;
        this.quantity = quantity;
    }

    Item item;
    private byte quantity;

    public void drawIcon(UIExtensionFrame applet, int originX, int originY, int width) {
        ItemList.iconManager.drawItemIcon(applet, getItem(), originX, originY, width);
        applet.noStroke();
        if (item == null) {
            return;
        }
        if (!isInfiniteResource()) {
            if (item.continuousUse()) {
                if (quantity < item.maxStackSize()) {
                    applet.fill(0);
                    applet.stroke(255);
                    applet.strokeWeight(1);
                    applet.rect(originX, originY + width - 6, width, 6);
                    applet.fill(255);
                    applet.noStroke();
                    applet.rect(originX, originY + width - 6,
                            (float) MathUtils.map(quantity, 0, item.maxStackSize(), 0, width), 6);
                }
            } else {
                applet.fill(0, 150);
                applet.rect(originX + width - 25, originY + 4, 25, 14, 2);
                applet.fill(255);
                applet.textAlign(RIGHT, TOP);
                applet.textSize(10);
                if (getQuantity() > 99) {
                    applet.text("99", originX + width, originY + 5);
                } else {
                    applet.text(getQuantity() + "", originX + width, originY + 7);
                }
            }
        }
    }

    public String toFileString() {
        return item.id + "," + item.type.toString() + "," + quantity;
    }

    public void fromFileString(String str) {
        // String[] chunks = str.split(",");
        // ItemType type = ItemType.valueOf(chunks[1]);
        // int id = Integer.parseInt(chunks[0]);
        // this.quantity = Integer.parseInt(chunks[2]);
        //
        // item = item.getPointerHandler().getItems().get(type, id);
        //
        // if (item == null) {
        // throw new NullPointerException("The item in an itemQuantity can't be null!");
        // }
    }

    public boolean isInfiniteResource() {
        return getItem().isInfiniteResource();
    }
}
