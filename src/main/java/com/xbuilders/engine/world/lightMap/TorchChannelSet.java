// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.engine.world.lightMap;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TorchChannelSet {
    private final ReentrantReadWriteLock rwl;
    public HashMap<Byte, Byte> list;

    public boolean isEmpty() {
        this.rwl.readLock().lock();
        try {
            if (this.list.isEmpty())
                return true;
            for (final Byte value : this.list.values()) {
                if (value > 0)
                    return false;
            }
            return true;
        } finally {
            this.rwl.readLock().unlock();
        }
    }

    public TorchChannelSet() {
        this.rwl = new ReentrantReadWriteLock();
        this.list = new HashMap<Byte, Byte>();
    }

    public int getChannelWithHighestValue() {
        this.rwl.readLock().lock();
        try {
            int maxIndex = 0;
            int maxValue = -1;
            int i = 0;
            for (final Byte value : this.list.values()) {
                if (value > maxValue) {
                    maxValue = value;
                    maxIndex = i;
                }
                ++i;
            }
            return maxIndex;
        } finally {
            this.rwl.readLock().unlock();
        }
    }

    public byte getBrightestChannel() {
        this.rwl.readLock().lock();
        try {
            byte maxValue = 0;
            for (final Byte value : this.list.values()) {
                if (value > maxValue) {
                    maxValue = value;
                }
            }
            return maxValue;
        } finally {
            this.rwl.readLock().unlock();
        }
    }

    public byte get(final byte channel) {
        this.rwl.readLock().lock();
        try {
            if (this.list.containsKey(channel)) {
                return this.list.get(channel);
            }
            return 0;
        } finally {
            this.rwl.readLock().unlock();
        }
    }

    public int size() {
        return this.list.size();
    }

    public boolean hasChannel(final byte channel) {
        this.rwl.readLock().lock();
        try {
            return this.list.containsKey(channel);
        } finally {
            this.rwl.readLock().unlock();
        }
    }

    protected void set(final byte channel, final byte value) {
        this.rwl.writeLock().lock();
        try {
            this.list.put(channel, value);
        } finally {
            this.rwl.writeLock().unlock();
        }
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
