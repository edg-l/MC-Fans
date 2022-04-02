package com.edgarluque.fabric.fans;

import com.edgarluque.fabric.fans.blocks.FanBlock;
import com.edgarluque.fabric.fans.entities.FanBlockEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.world.WorldTickCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.TypeFilterableList;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fans implements ModInitializer {
    public static final String MOD_ID = "fans";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Item FABRIC_ITEM = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Block FAN_BLOCK = new FanBlock(FabricBlockSettings.of(Material.STONE).strength(3.5f));

    public static BlockEntityType<FanBlockEntity> FAN_BLOCK_ENTITY;

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(Fans.MOD_ID, "fabric_item"), FABRIC_ITEM);
        Registry.register(Registry.BLOCK, new Identifier(Fans.MOD_ID, "fan_block"), FAN_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(Fans.MOD_ID, "fan_block"),
                new BlockItem(FAN_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));

        FAN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MOD_ID + "fan_block_entity",
                FabricBlockEntityTypeBuilder.create(FanBlockEntity::new, FAN_BLOCK).build(null));

        ServerTickEvents.START_WORLD_TICK.register((world) -> {
            //world.getBlockTickScheduler().
            //world.getEntitiesByType(MobEntity.class)
        });
    }
}
