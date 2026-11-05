package com.lefrosheh.ielsocompat.blockentity;

import com.lefrosheh.ielsocompat.block.ElectricHeaterBlock;
import com.lefrosheh.ielsocompat.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElectricHeaterBlockEntity extends BlockEntity {
    // Tweak these to balance it.
    public static final int CAPACITY = 20_000;
    public static final int MAX_RECEIVE = 1_000;   // FE per tick accepted
    public static final int FE_PER_TICK = 20;      // FE used per tick while heating

    private final HeaterEnergy energy = new HeaterEnergy();
    private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);

    public ElectricHeaterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ELECTRIC_HEATER.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ElectricHeaterBlockEntity be) {
        boolean shouldBeLit = be.energy.consume(FE_PER_TICK);
        if (shouldBeLit) {
            be.setChanged();
        }
        if (shouldBeLit != state.getValue(ElectricHeaterBlock.LIT)) {
            level.setBlock(pos, state.setValue(ElectricHeaterBlock.LIT, shouldBeLit), Block.UPDATE_ALL);
        }
    }

    // Energy can only be inserted through the top face.
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY && side == Direction.UP) {
            return energyCap.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyCap.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Energy", energy.getEnergyStored());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        energy.setStored(tag.getInt("Energy"));
    }

    private class HeaterEnergy extends EnergyStorage {
        HeaterEnergy() {
            super(CAPACITY, MAX_RECEIVE, 0); // can receive, cannot be drained from outside
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int received = super.receiveEnergy(maxReceive, simulate);
            if (received > 0 && !simulate) {
                setChanged();
            }
            return received;
        }

        boolean consume(int amount) {
            if (energy < amount) {
                return false;
            }
            energy -= amount;
            return true;
        }

        void setStored(int value) {
            energy = Mth.clamp(value, 0, capacity);
        }
    }
}