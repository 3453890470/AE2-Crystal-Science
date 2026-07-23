package io.github.lounode.ae2cs.common.item;

import io.github.lounode.ae2cs.common.init.AECSMenus;

import appeng.menu.locator.ItemMenuHostLocator;

import de.mari_023.ae2wtlib.api.terminal.ItemWT;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;

public class WirelessResonantTerminalItem extends ItemWT {

    @Override
    public MenuType<?> getMenuType(ItemMenuHostLocator locator, Player player) {
        return AECSMenus.RESONANT_TEMPLATE_CODING_TERM_MENU.get();
    }
}
