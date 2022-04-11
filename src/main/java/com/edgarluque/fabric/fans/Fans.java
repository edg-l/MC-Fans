package com.edgarluque.fabric.fans;

import com.edgarluque.fabric.fans.blocks.FanBlock;
import com.edgarluque.fabric.fans.entities.FanBlockEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fans implements ModInitializer {
    public static final String MOD_ID = "fans";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Block FAN_BLOCK = new FanBlock(FabricBlockSettings.of(Material.STONE).strength(3.5f), 0.2, 10);
    public static final Block FAN_BLOCK_STRONG = new FanBlock(FabricBlockSettings.of(Material.STONE).strength(3.5f), 0.4, 10);

    public static BlockEntityType<FanBlockEntity> FAN_BLOCK_ENTITY;

    private void addFanItem(Item item, String id) {
        Registry.register(Registry.ITEM, new Identifier(Fans.MOD_ID, id), item);
    }

    private void addFanBlock(Block block, String id) {
        Registry.register(Registry.BLOCK, new Identifier(Fans.MOD_ID, id), block);
        addFanItem(new BlockItem(block, new FabricItemSettings().group(ItemGroup.MISC)), id);
    }

    private BlockEntityType<FanBlockEntity> addFanBlockEntity(String id, Block... blocks) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, MOD_ID + String.format(":%s", id),
                FabricBlockEntityTypeBuilder.create(FanBlockEntity::new, blocks).build(null));
    }

    @Override
    public void onInitialize() {
        addFanBlock(FAN_BLOCK, "fan_block");
        addFanBlock(FAN_BLOCK_STRONG, "fan_block_strong");

        FAN_BLOCK_ENTITY = addFanBlockEntity("fan_block_entity", FAN_BLOCK, FAN_BLOCK_STRONG);
    }
}
