package com.xbuilders.game.items.blocks.vehicle;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameScene;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.game.items.other.BlockGrid;
import com.xbuilders.game.items.other.boundaryBlocks.BoundingBox;
import org.joml.Vector3i;

import java.util.HashSet;

import static com.xbuilders.game.items.GameItems.BLOCK_JET_THRUSTER;

public class DriversSeat extends Block {

    public DriversSeat() {
        super(261, "Drivers Seat", new BlockTexture(2, 24, 2, 26), false, BlockRenderType.SLAB);
        setIconAtlasPosition(0, 9);
        tags.add("vehicle");
    }

    static BoundingBox box;
    static AABB aabb = new AABB();
    static BlockGrid blocks;
    public static final int VEHICLE_MAX_SIZE = 200;
    public static byte speed = -1;
    public static boolean canFly, canFloatOnWater, allTerrain;
    public static byte direction = 0;

    private boolean findWheels(AABB aabb, int x, int y, int z) {
        boolean foundWheels = false;
        HashSet<Vector3i> area = new HashSet<>();
        HashSet<Vector3i> checkedNodes = new HashSet<>();
        aabb.setPosAndSize(x, y, z, 0, 0, 0);
        area.add(new Vector3i(x, y, z));
        long startTime = System.currentTimeMillis();
        while (!area.isEmpty()
                && System.currentTimeMillis() - startTime < 1000) {
            Vector3i pos = area.iterator().next();
            area.remove(pos);
            int x2 = (int) pos.x;
            int y2 = (int) pos.y;
            int z2 = (int) pos.z;
            Block block = VoxelGame.getWorld().getBlock(x2, y2, z2);

            if (pos.distance(x, y, z) > VEHICLE_MAX_SIZE || checkedNodes.contains(pos)) {
                continue;
            }
            checkedNodes.add(pos);
            if (block instanceof WheelBlock) {
                foundWheels = true;
                System.out.println("Found a wheel at " + y2);
                if (block.type == BlockRenderType.WHEEL) {
                    allTerrain = true;
                } else if (block == GameItems.BOYANCY_BASE) {
                    canFloatOnWater = true;
                }
                if (y2 >= aabb.maxPoint.y) {
                    aabb.maxPoint.y = y2;
                }
            }
            checkNeighbour(area, aabb, x2 + 1, y2, z2, false);
            checkNeighbour(area, aabb, x2 - 1, y2, z2, false);
            checkNeighbour(area, aabb, x2, y2 + 1, z2, false);
            checkNeighbour(area, aabb, x2, y2 - 1, z2, false);
            checkNeighbour(area, aabb, x2, y2, z2 + 1, false);
            checkNeighbour(area, aabb, x2, y2, z2 - 1, false);
        }
        return foundWheels;
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData data) {
        if (box == null) {//There can only be one bounding box
            box = new BoundingBox();
            VoxelGame.getWorld().hologramList.add(box);
        }

        this.set(x, y, z, data);

        (new Thread() {
            public void run() {
                computeVehicle(x, y, z);
            }
        }).start();

        return true;
    }

    private void computeVehicle(int x, int y, int z) {
        VoxelGame.getGame().alert("Computing vehicle...");
        HashSet<Vector3i> area = new HashSet<>();
        HashSet<Vector3i> checkedNodes = new HashSet<>();
        speed = -1;
        direction = 0;
        canFly = false;
        allTerrain = false;
        canFloatOnWater = false;
        boolean foundWheel = findWheels(aabb, x, y, z);
        if (foundWheel) {
            area.add(new Vector3i(x, y, z));
            long startTime = System.currentTimeMillis();
            while (!area.isEmpty()) {
                if (System.currentTimeMillis() - startTime > 5000) {
                    VoxelGame.getGame().alert("Vehicle compute timed out.");
                    return;
                }

                Vector3i pos = area.iterator().next();
                area.remove(pos);
                int x2 = (int) pos.x;
                int y2 = (int) pos.y;
                int z2 = (int) pos.z;
                Block block = VoxelGame.getWorld().getBlock(x2, y2, z2);

                if (pos.distance(x, y, z) > VEHICLE_MAX_SIZE || checkedNodes.contains(pos) || y2 > aabb.maxPoint.y) {
                    continue;
                }

                checkedNodes.add(pos);

                if (block instanceof Engine) {
                    Engine engine = (Engine) block;
                    speed = (byte) MathUtils.clamp(engine.horsepower, 0, 127);
                    System.out.println("Found an engine with " + engine.horsepower + "hp");
                    try {
                        direction = (byte) VoxelGame.getWorld().getBlockData(x2, y2, z2).get(0);
                    } catch (Exception e) {
                        System.out.println(e);
                        direction = 0;
                    }
                } else if (block == BLOCK_JET_THRUSTER
                        || block instanceof HelecopterBladeBlock) {
                    canFly = true;
                } else if (block == GameItems.BLOCK_WATER_PROPELLER) {
                    canFly = true;
                    canFloatOnWater = true;
                }

                checkNeighbour(area, aabb, x2 + 1, y2, z2, true);
                checkNeighbour(area, aabb, x2 - 1, y2, z2, true);
                if (y2 <= aabb.maxPoint.y) {
                    checkNeighbour(area, aabb, x2, y2 + 1, z2, true);
                }
                checkNeighbour(area, aabb, x2, y2 - 1, z2, true);
                checkNeighbour(area, aabb, x2, y2, z2 + 1, true);
                checkNeighbour(area, aabb, x2, y2, z2 - 1, true);
            }

            System.out.println("Trying to create vehicle with speed: " + speed + " and direction: " + direction + " found wheel: " + foundWheel);
            if (speed > -1 && foundWheel) {
                aabb.maxPoint.add(1, 1, 1);

                box.set(aabb);
                //Set the blocks
                blocks = new BlockGrid((int) aabb.getXLength(), (int) aabb.getYLength(), (int) aabb.getZLength());
                for (Vector3i pos : checkedNodes) {
                    int normX = (int) (pos.x - aabb.minPoint.x);
                    int normY = (int) (pos.y - aabb.minPoint.y);
                    int normZ = (int) (pos.z - aabb.minPoint.z);
                    blocks.set(VoxelGame.getWorld().getBlock(pos.x, pos.y, pos.z).id, normX, normY, normZ);
                    blocks.setBlockData(VoxelGame.getWorld().getBlockData(pos.x, pos.y, pos.z), normX, normY, normZ);
                }
                box.setMesh(blocks, true, true);
                box.show();
                VoxelGame.getGame().alert("Click the drivers seat to create vehicle");
                System.out.println("Vehicle created with speed: " + speed + " and direction: " + direction + " found wheel: " + foundWheel
                        + "\n AABB: min: " + MiscUtils.printVector(aabb.minPoint) + " size: " + aabb.getXLength() + " x " + aabb.getYLength() + " x " + aabb.getZLength());
            } else {
                box.hide();
                if (speed == -1) {
                    VoxelGame.getGame().alert("No engine found! The vehicle cannot be created!");
                }
                BlockList.BLOCK_AIR.set(x, y, z, null);
            }
        } else {
            VoxelGame.getGame().alert("No wheels found! The vehicle cannot be created!");
        }
    }

    boolean isPartOfVehicle(Block block) {
        return block.isSolid();
    }

    @Override
    public boolean onClickEvent(int setX, int setY, int setZ) {
        if (box != null && box.isShown() && blocks != null) {
            System.out.println("Removing the vehicle...");
            for (int i = 0; i < blocks.size.x; i++) {//Erase the old vehicle
                for (int j = 0; j < blocks.size.y; j++) {
                    for (int k = 0; k < blocks.size.z; k++) {
                        int x = (int) (i + aabb.minPoint.x);
                        int y = (int) (j + aabb.minPoint.y);
                        int z = (int) (k + aabb.minPoint.z);
                        if (isPartOfVehicle(ItemList.getBlock(blocks.get(i, j, k)))) {
                            BlockList.BLOCK_AIR.set(x, y, z, null);
                        }
                    }
                }
            }
            CustomVehicleEntityLink.CustomVehicle entity = (CustomVehicleEntityLink.CustomVehicle) GameItems.ENTITY_CUSTOM_VEHICLE.placeNew(
                    (int) aabb.minPoint.x, (int) aabb.minPoint.y - 1, (int) aabb.minPoint.z,
                    null, true, true);
            entity.blocks = blocks;
            entity.direction = direction;
            entity.speed = speed;
            entity.canFly = canFly;
            entity.canFloatOnWater = canFloatOnWater;
            entity.allTerrain = allTerrain;
            entity.initialize(null, true);
            box.hide();
            VoxelGame.getGame().alert("vehicle created!");
        } else {
            VoxelGame.getGame().alert("The vehicle cannot be created!");
        }
        return false;
    }

    @Override
    public void afterRemovalEvent(int x, int y, int z) {
        if (box != null) {
            box.hide(); //Don't remove the box, just hide it
        }
    }

    private void checkNeighbour(HashSet<Vector3i> area, AABB aabb, int x, int y, int z, boolean expandAABB) {
        Block block = VoxelGame.getWorld().getBlock(x, y, z);
        if (isPartOfVehicle(block)) {
            area.add(new Vector3i(x, y, z));
            if (expandAABB) {
                if (x < aabb.minPoint.x) {
                    aabb.minPoint.x = x;
                }
                if (x > aabb.maxPoint.x) {
                    aabb.maxPoint.x = x;
                }
                if (y < aabb.minPoint.y) {
                    aabb.minPoint.y = y;
                }
                if (z < aabb.minPoint.z) {
                    aabb.minPoint.z = z;
                }
                if (z > aabb.maxPoint.z) {
                    aabb.maxPoint.z = z;
                }
            }
        }
    }
}
