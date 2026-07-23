package io.github.lounode.ae2cs.datagen.recipes.compat;

import io.github.lounode.ae2cs.api.ids.AECSConstants;
import io.github.lounode.ae2cs.common.init.AECSBlocks;
import io.github.lounode.ae2cs.common.init.AECSItems;
import io.github.lounode.ae2cs.datagen.AECSRecipeProvider;
import io.github.lounode.ae2cs.datagen.builder.recipe.CrystalAggregatorRecipeBuilder;
import io.github.lounode.ae2cs.datagen.builder.recipe.CrystalPulverizerRecipeBuilder;

import appeng.core.definitions.AEItems;
import appeng.datagen.providers.tags.ConventionTags;
import appeng.recipes.entropy.EntropyRecipeBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class AECSCompatAE2LTRecipeProvider extends AECSRecipeProvider {

    public AECSCompatAE2LTRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public @NotNull String getName() {
        return "AECS AE2LT Compat Recipes";
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput originalOut, HolderLookup.@NotNull Provider registries) {
        var compatOut = originalOut.withConditions(modLoaded(AECSConstants.AE2LT_ID));
        super.buildRecipes(compatOut, registries);

        packAndUnpack3x3(compatOut, RecipeCategory.MISC, RecipeCategory.MISC,
                externalItem(AECSConstants.AE2LT_ID, "overload_crystal"), AECSBlocks.CHARGED_OVERLOAD_CRYSTAL_BLOCK);

        stonecutterResultFromItem(compatOut, RecipeCategory.MISC,
                externalItem(AECSConstants.AE2LT_ID, "overload_inscriber_press"), AECSItems.BLANK_PRINT_PRESS);

        EntropyRecipeBuilder.heat()
                .setInputBlock(externalBlock(AECSConstants.AE2LT_ID, "overload_crystal_block"))
                .setOutputBlock(AECSBlocks.CHARGED_OVERLOAD_CRYSTAL_BLOCK.get())
                .save(compatOut, io.github.lounode.ae2cs.AE2CrystalScience.makeId("entropy/charged_overload_crystal_block"));

        CrystalAggregatorRecipeBuilder.aggregating(AECSItems.OVERLOAD_CRYSTAL_SEED, 32, 51200)
                .require(externalItem(AECSConstants.AE2LT_ID, "overload_crystal_dust"), 16)
                .require(ConventionTags.FLUIX_DUST, 8)
                .require(AEItems.CERTUS_QUARTZ_CRYSTAL_CHARGED, 8)
                .save(compatOut, "aggregator/overload_crystal_seed");

        CrystalPulverizerRecipeBuilder.pulverizing(
                externalItem(AECSConstants.AE2LT_ID, "overload_crystal_dust"), 1, 8000)
                .require(AECSItems.PURE_OVERLOAD_CRYSTAL, 1)
                .save(compatOut, "pulverizer/overload_crystal_dust_from_pure_crystal");

        CrystalAggregatorRecipeBuilder.aggregating(
                externalItem(AECSConstants.AE2LT_ID, "pigmee_fumo"), 1, 666)
                .require(Items.PORKCHOP, 1)
                .require(Blocks.PINK_WOOL, 1)
                .save(compatOut, "aggregator/pigmee_fumo");
    }

    private static Item externalItem(String namespace, String path) {
        return BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    private static Block externalBlock(String namespace, String path) {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(namespace, path));
    }
}
