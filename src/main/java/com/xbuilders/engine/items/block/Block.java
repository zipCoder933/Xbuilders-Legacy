package com.xbuilders.engine.items.block;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.blockPipeline.BlockHistory;
import com.xbuilders.engine.player.blockPipeline.BlockPipeline;
import com.xbuilders.engine.player.blockPipeline.LocalChangeRecord;
import com.xbuilders.engine.player.Player;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.raycasting.Ray;

import static com.xbuilders.engine.items.BlockList.DEFAULT_BLOCK_TYPE_ID;

import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.ItemType;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.ChunkCoords;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.wcc.WCCi;
import com.xbuilders.game.PointerHandler;
import org.joml.Vector3i;

public class Block extends Item {

    @Override
    public int breakTimeMS() {
        return 500;
    }

    public BlockTexture texture;
    public int type = DEFAULT_BLOCK_TYPE_ID;
    public boolean solid = true;
    public boolean opaque = true;
    public boolean luminous = false;
    public byte falloff = 1;
    public int animationLength = 1;

    public boolean isOpaque() {
        return opaque;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean isLuminous() {
        return luminous;
    }

    public byte getLightFalloff() {
        return falloff;
    }

    /**
     * @return the type
     */
    public int getRenderType() {
        return type;
    }

    /**
     * @param renderType the type to set
     */
    public void setRenderType(int renderType) {
        this.type = renderType;
    }


    public boolean hasTexture() {
        return texture != null;
    }


    public boolean onClickEvent(int setX, int setY, int setZ) {
        return true;
    }

    public Block(int id, String name) {
        super(id, name, ItemType.BLOCK);
        this.type = DEFAULT_BLOCK_TYPE_ID;
        this.solid = true;
        this.opaque = true;
        init();
    }

    public void init() {
    }

    public Block(int id, String name, boolean solid, boolean opaque) {
        super(id, name, ItemType.BLOCK);
        this.type = DEFAULT_BLOCK_TYPE_ID;
        this.solid = solid;
        this.opaque = opaque;
        init();
    }

    public Block(int id, String name, boolean solid, boolean opaque, int renderType) {
        super(id, name, ItemType.BLOCK);
        this.type = renderType;
        this.solid = solid;
        this.opaque = opaque;
        init();
    }

    public Block(int id, String name, BlockTexture texture, boolean solid, boolean opaque) {
        super(id, name, ItemType.BLOCK);
        this.texture = texture;
        this.type = DEFAULT_BLOCK_TYPE_ID;
        this.solid = solid;
        this.opaque = opaque;
        init();
    }

    public Block(int id, String name, BlockTexture texture, int renderType) {
        super(id, name, ItemType.BLOCK);
        setSolidOpaque(renderType);
        this.texture = texture;
        this.type = DEFAULT_BLOCK_TYPE_ID;
        this.type = renderType;
        init();
    }

    public Block(int id, String name, BlockTexture texture) {
        super(id, name, ItemType.BLOCK);
        this.texture = texture;
        this.type = DEFAULT_BLOCK_TYPE_ID;
        this.solid = true;
        this.opaque = true;
        init();
    }

    public Block(int id, String name, BlockTexture texture, boolean solid, boolean opaque, int renderType) {
        super(id, name, ItemType.BLOCK);
        this.texture = texture;
        this.type = renderType;
        this.solid = solid;
        this.opaque = opaque;
        init();
    }

    public Block(int id, String name, BlockTexture texture, boolean opaque, int renderType) {
        super(id, name, ItemType.BLOCK);
        this.texture = texture;
        this.type = renderType;
        this.solid = true;
        this.opaque = opaque;
        init();
    }

    public Block(int id, String name, BlockTexture texture, boolean opaque) {
        super(id, name, ItemType.BLOCK);
        this.texture = texture;
        this.type = DEFAULT_BLOCK_TYPE_ID;
        this.solid = true;
        this.opaque = opaque;
        init();
    }

    public Block(int id, String name, int iconAtlasX, int iconAtlasY, boolean solid, boolean opaque, int renderType) {
        super(id, name, ItemType.BLOCK);
        this.texture = null;
        this.type = renderType;
        this.solid = solid;
        this.opaque = opaque;
        setIconAtlasPosition(iconAtlasX, iconAtlasY);
        init();
    }

    public Block(int id, String name, int iconAtlasX, int iconAtlasY, BlockTexture texture, boolean solid, boolean opaque, int renderType) {
        super(id, name, ItemType.BLOCK);
        this.texture = texture;
        this.type = renderType;
        this.solid = solid;
        this.opaque = opaque;
        setIconAtlasPosition(iconAtlasX, iconAtlasY);
        init();
    }

    public Block(int id, String name, BlockTexture texture, boolean solid, boolean opaque, boolean luminous, byte falloff,
                 int animationLength, int renderType) {
        super(id, name, ItemType.BLOCK);
        this.texture = texture;
        this.type = renderType;
        this.solid = solid;
        this.opaque = opaque;
        this.luminous = luminous;
        this.falloff = falloff;
        this.animationLength = animationLength;
        init();
    }

    public Block(int id, String name, BlockTexture texture, boolean luminous, byte falloff, int animationLength, int renderType) {
        super(id, name, ItemType.BLOCK);
        this.texture = texture;
        this.type = renderType;
        setSolidOpaque(renderType);
        this.luminous = luminous;
        this.falloff = falloff;
        this.animationLength = animationLength;
        init();
    }

    public boolean setBlock(int x, int y, int z, BlockData data) {
        this.set(x, y, z, data);
        return true;
    }

    public void afterRemovalEvent(int x, int y, int z) {
    }

    /**
     * @return the forbidden ray block to set
     */
    public int playerHeadEnterBlockEvent(Player player) {
        return -1;
    }

    public void drawOverlayInPlayerHead() {
        if (this.getRenderType() == DEFAULT_BLOCK_TYPE_ID) {
            if (this.isOpaque()) {
                getPointerHandler().getApplet().tint(50);
                drawFulscreenBlockTexture(getPointerHandler(), this);
                getPointerHandler().getApplet().noTint();
            } else {
//                getPointerHandler().getApplet().fill(0, 0, 0, 80);
//                getPointerHandler().getApplet().rect(-2, -2,
//                        getPointerHandler().getApplet().width + 4,
//                        getPointerHandler().getApplet().height + 4);
            }
        }
    }

    public static void drawFulscreenBlockTexture(PointerHandler ph, Block headBlock) {
        int boxSize = Math.max(ph.getApplet().height, ph.getApplet().width);
        ph.getApplet().getGraphics().image(ItemList.blocks.textureAtlas.getImage(),
                0, 0,
                boxSize, boxSize, headBlock.texture.FRONT[0] * SubChunk.WIDTH, headBlock.texture.FRONT[1] * SubChunk.WIDTH,
                headBlock.texture.FRONT[0] * SubChunk.WIDTH + SubChunk.WIDTH, headBlock.texture.FRONT[1] * SubChunk.WIDTH + SubChunk.WIDTH);
    }

    public void onLocalChange(LocalChangeRecord record) {
    }

    public final void set(Vector3i vec) {
        set(vec.x, vec.y, vec.z, null);
    }

    public final void set(int x, int y, int z) {
        set(x, y, z, null);
    }

    public final void set(Vector3i vec, BlockData data) {
        set(vec.x, vec.y, vec.z, data);
    }

    public final void set(int x, int y, int z, BlockData data) {
        if (VoxelGame.getWorld().inPlacableBounds(y)) {
            final int chunkX = WCCi.chunkDiv(x);
            final int blockX = MathUtils.positiveMod(x, SubChunk.WIDTH);
            final int chunkZ = WCCi.chunkDiv(z);
            final int blockZ = MathUtils.positiveMod(z, SubChunk.WIDTH);
            Chunk chunk = VoxelGame.getWorld().getChunk(new ChunkCoords(chunkX, chunkZ));

            if (chunk != null) {
                final int chunkLocation = WCCi.chunkDiv(y);
                final int blockLocation = MathUtils.positiveMod(y, SubChunk.WIDTH);

                Block prevBlock = ItemList.getBlock(chunk.getSubChunks()[chunkLocation].getVoxels().getBlock(blockX, blockLocation, blockZ));

                chunk.getSubChunks()[chunkLocation].getVoxels().setBlock(this.id, blockX, blockLocation, blockZ);
                chunk.getSubChunks()[chunkLocation].getVoxels().setBlockData(data, blockX, blockLocation, blockZ);

                chunk.markAsNeedsSaving();
                if (chunk.meshesGenerated) {
                    chunk.markChunksAsNeedsRegenerating(chunkLocation, blockX, blockLocation, blockZ);
                }
                BlockPipeline.put(new Vector3i(x, y, z), new BlockHistory(prevBlock, this));
            }
        }
    }

    public boolean isAir() {
        return false;
    }

    public final boolean isLiquid() {
        return this.type == BlockRenderType.LIQUID;
    }

    public int getAnimationLength() {
        return animationLength;
    }

    @Override
    public String toString() {
        return "\"" + this.name + "\" Block (id: " + id + ")";
    }


    public BlockData getInitialBlockData(UserControlledPlayer player, Ray ray) {
        return null;
    }

    private void setSolidOpaque(int renderType) {
        if (renderType == BlockRenderType.SPRITE) {
            solid = false;
            opaque = false;
        }
    }

}
