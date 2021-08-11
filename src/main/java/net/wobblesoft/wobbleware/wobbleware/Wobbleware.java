package net.wobblesoft.wobbleware.wobbleware;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.wobblesoft.wobbleware.wobbleware.blocks.CrystalElevator;
import net.wobblesoft.wobbleware.wobbleware.blocks.CrystalElevatorEntity;
import net.wobblesoft.wobbleware.wobbleware.items.CrystalPearl;

public class Wobbleware implements ModInitializer {

    // Blocks
    public static final CrystalElevator CRYSTAL_ELEVATOR = new CrystalElevator(FabricBlockSettings.of(Material.AMETHYST).hardness(2.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK));
    public static BlockEntityType<CrystalElevatorEntity> CRYSTAL_ELEVATOR_ENTITY;

    // Item groups
    public static final ItemGroup WOBBLEWARE_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("wobbleware", "general"))
            .icon(() -> new ItemStack(Wobbleware.CRYSTALPEARL))
            .build();

    // Items
    public static final Item CRYSTALPEARL = new Item(new FabricItemSettings().group(Wobbleware.WOBBLEWARE_GROUP));


    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("wobbleware", "crystal_pearl"), CRYSTALPEARL);

        Registry.register(Registry.BLOCK, new Identifier("wobbleware", "crystal_elevator"), CRYSTAL_ELEVATOR);
        Registry.register(Registry.ITEM, new Identifier("wobbleware", "crystal_elevator"), new BlockItem(CRYSTAL_ELEVATOR, new Item.Settings().group(Wobbleware.WOBBLEWARE_GROUP)));
        CRYSTAL_ELEVATOR_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wobbleware:crystal_elevator_entity", FabricBlockEntityTypeBuilder.create(CrystalElevatorEntity::new, CRYSTAL_ELEVATOR).build(null));

        System.out.println("WobbleWare Initialized");
    }
}
