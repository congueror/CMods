package net.congueror.cgalaxy.blocks.sealed_cable;

import net.congueror.cgalaxy.blocks.sealed_cable.client.SealedCableBakedModel;
import net.congueror.clib.blocks.machine_base.TickingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class SealedCableBlockEntity extends BlockEntity implements TickingBlockEntity {
    protected Block block = Blocks.STONE;

    public SealedCableBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    public void setBlock(Block block) {
        this.block = block;
        this.setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    protected void loadBlock(CompoundTag nbt) {
        block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(nbt.getString("Block")));
    }

    protected void saveBlock(CompoundTag tag) {
        tag.putString("Block", block.getRegistryName().toString());
    }

    @Override
    public void load(CompoundTag nbt) {
        loadBlock(nbt);
        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        saveBlock(pTag);
        super.saveAdditional(pTag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveBlock(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            loadBlock(tag);
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        Block oldBlock = this.block;
        CompoundTag tag = pkt.getTag();
        handleUpdateTag(tag);

        if (!Objects.equals(this.block, oldBlock)) {
            ModelDataManager.requestModelDataRefresh(this);
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @NotNull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder().withInitial(SealedCableBakedModel.BLOCK_PROPERTY, this.block.defaultBlockState()).build();
    }
}
