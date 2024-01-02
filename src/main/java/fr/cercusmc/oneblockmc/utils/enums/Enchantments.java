package fr.cercusmc.oneblockmc.utils.enums;

import org.bukkit.enchantments.Enchantment;

public enum Enchantments {

    ARROW_DAMAGE(Enchantment.ARROW_DAMAGE),
    ARROW_FIRE(Enchantment.ARROW_FIRE),
    ARROW_INFINITE(Enchantment.ARROW_INFINITE),
    ARROW_KNOCKBACK(Enchantment.ARROW_KNOCKBACK),
    BINDING_CURSE(Enchantment.BINDING_CURSE),
    CHANNELING(Enchantment.CHANNELING),
    DAMAGE_ALL(Enchantment.DAMAGE_ALL),
    DAMAGE_ARTHROPODS(Enchantment.DAMAGE_ARTHROPODS),
    DAMAGE_UNDEAD(Enchantment.DAMAGE_UNDEAD),
    DEPTH_STRIDER(Enchantment.DEPTH_STRIDER),
    DIG_SPEED(Enchantment.DIG_SPEED),
    DURABILITY(Enchantment.DURABILITY),
    FIRE_ASPECT(Enchantment.FIRE_ASPECT),
    FROST_WALKER(Enchantment.FROST_WALKER),
    IMPALING(Enchantment.IMPALING),
    KNOCKBACK(Enchantment.KNOCKBACK),
    LOOT_BONUS_BLOCKS(Enchantment.LOOT_BONUS_BLOCKS),
    LOOT_BONUS_MOBS(Enchantment.LOOT_BONUS_MOBS),
    LOYALTY(Enchantment.LOYALTY),
    LUCK(Enchantment.LUCK),
    LURE(Enchantment.LURE),
    MENDING(Enchantment.MENDING),
    MULTISHOT(Enchantment.MULTISHOT),
    OXYGEN(Enchantment.OXYGEN),
    PIERCING(Enchantment.PIERCING),
    PROTECTION_ENVIRONMENTAL(Enchantment.PROTECTION_ENVIRONMENTAL),
    PROTECTION_EXPLOSIONS(Enchantment.PROTECTION_EXPLOSIONS),
    PROTECTION_FALL(Enchantment.PROTECTION_FALL),
    PROTECTION_FIRE(Enchantment.PROTECTION_FIRE),
    PROTECTION_PROJECTILE(Enchantment.PROTECTION_PROJECTILE),
    QUICK_CHARGE(Enchantment.QUICK_CHARGE),
    RIPTIDE(Enchantment.RIPTIDE),
    SILK_TOUCH(Enchantment.SILK_TOUCH),
    SOUL_SPEED(Enchantment.SOUL_SPEED),
    SWEEPING_EDGE(Enchantment.SWEEPING_EDGE),
    SWIFT_SNEAK(Enchantment.SWIFT_SNEAK),
    THORNS(Enchantment.THORNS),
    VANISHING_CURSE(Enchantment.VANISHING_CURSE),
    WATER_WORKER(Enchantment.WATER_WORKER);

    private Enchantment ench;
    Enchantments(Enchantment ench) {
        this.ench = ench;
    }

    public Enchantment getEnchantment() {
        return ench;
    }
}
