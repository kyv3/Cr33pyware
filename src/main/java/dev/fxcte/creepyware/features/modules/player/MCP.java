package dev.fxcte.creepyware.features.modules.player;

import dev.fxcte.creepyware.features.modules.Module;
import dev.fxcte.creepyware.features.setting.Setting;
import dev.fxcte.creepyware.util.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public
class MCP
        extends Module {
    private final Setting <Mode> mode = this.register(new Setting <>("Speed", "Mode", 0.0, 0.0, Mode.MIDDLECLICK, 0));
    private final Setting <Boolean> stopRotation = this.register(new Setting <>("Speed", "Rotation", 0.0, 0.0, true, 0));
    private final Setting <Boolean> antiFriend = this.register(new Setting <>("Speed", "AntiFriend", 0.0, 0.0, true, 0));
    private final Setting <Integer> rotation = this.register(new Setting <Object>("Delay", 10, 0, 100, v -> this.stopRotation.getValue()));
    private boolean clicked = false;

    public
    MCP() {
        super("MCP", "Throws a pearl", Module.Category.PLAYER, false, false, false);
    }

    @Override
    public
    void onEnable() {
        if (! MCP.fullNullCheck() && this.mode.getValue() == Mode.TOGGLE) {
            this.throwPearl();
            this.disable();
        }
    }

    @Override
    public
    void onTick() {
        if (this.mode.getValue() == Mode.MIDDLECLICK) {
            if (Mouse.isButtonDown(2)) {
                if (! this.clicked) {
                    this.throwPearl();
                }
                this.clicked = true;
            } else {
                this.clicked = false;
            }
        }
    }

    private
    void throwPearl() {
        boolean offhand;
        Entity entity;
        RayTraceResult result;
        if (this.antiFriend.getValue() && (result = MCP.mc.objectMouseOver) != null && result.typeOfHit == RayTraceResult.Type.ENTITY && (entity = result.entityHit) instanceof EntityPlayer) {
            return;
        }
        int pearlSlot = InventoryUtil.findHotbarBlock(ItemEnderPearl.class);
        boolean bl = offhand = MCP.mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL;
        if (pearlSlot != - 1 || offhand) {
            int oldslot = MCP.mc.player.inventory.currentItem;
            if (! offhand) {
                InventoryUtil.switchToHotbarSlot(pearlSlot, false);
            }
            MCP.mc.playerController.processRightClick(MCP.mc.player, MCP.mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (! offhand) {
                InventoryUtil.switchToHotbarSlot(oldslot, false);
            }
        }
    }

    public
    enum Mode {
        TOGGLE,
        MIDDLECLICK

    }
}

