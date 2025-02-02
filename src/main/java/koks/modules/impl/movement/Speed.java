package koks.modules.impl.movement;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.modules.impl.utilities.Wrapper;
import koks.utilities.MovementUtil;
import koks.utilities.SpeedUtil;
import koks.utilities.Timer;
import koks.utilities.value.values.ModeValue;
import net.minecraft.item.ItemSword;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:15
 */
public class Speed extends Module {
    public ModeValue<String> mode = new ModeValue<>("Mode", "Mineplex", new String[]{"Mineplex", "AAC 3.2.2", "Hypixel", "MCCentral", "Minemora", "Verus"}, this);
    public boolean canSpeed;
    public MovementUtil movementUtil = new MovementUtil();
    public TargetStrafe targetStrafe = new TargetStrafe();
    Timer timer = new Timer();
    boolean isWalking = false;

    public Speed() {
        super("Speed", "you are fast af boyy", Category.MOVEMENT);
        addValue(mode);
    }


    @Override
    public void onEvent(Event event) {
        switch (mode.getSelectedMode()) {
            case "Hypixel":
                if (targetStrafe.allowStrafing()) {
                    targetStrafe.strafe(event, movementUtil.baseSpeed());
                }
                break;
            case "Mineplex":
                if (targetStrafe.allowStrafing()) {
                    if (mc.thePlayer.onGround)
                        mc.thePlayer.motionY = 0.42;
                    targetStrafe.strafe(event, 0.4543);
                }
                break;
        }

        if (event instanceof EventUpdate) {
            setModuleInfo(mode.getSelectedMode());
            switch (mode.getSelectedMode()) {
                case "Hypixel":
                    if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                            movementUtil.setSpeed(movementUtil.baseSpeed() + 0.005);
                        } else {
                            if (!targetStrafe.allowStrafing() && mc.thePlayer.movementInput.moveForward != 0 || mc.thePlayer.movementInput.moveStrafe != 0)
                                movementUtil.setSpeed(movementUtil.baseSpeed());
                            mc.thePlayer.jumpMovementFactor = 0.035F;
                        }
                    }
                    break;
                case "Mineplex":
                    if (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0 && !mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.motionY = 0.42;
                        } else {
                            if (mc.thePlayer.fallDistance < 0.5 && !targetStrafe.allowStrafing()) {
                                movementUtil.setSpeed(0.4543);
                                mc.thePlayer.jumpMovementFactor = 0.024F;
                            }
                        }
                    }
                    break;
                case "AAC 3.2.2":
                    if (mc.thePlayer.moveForward != 0 && !mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (mc.thePlayer.fallDistance > 0.4 && !canSpeed)
                            canSpeed = true;
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                        } else if (canSpeed) {
                            mc.thePlayer.motionY -= 0.0249;
                            mc.thePlayer.jumpMovementFactor = 0.033F;
                        }
                    } else {
                        canSpeed = false;
                    }
                    break;
                case "MCCentral":
                    if ((mc.thePlayer.isUsingItem() && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) || mc.thePlayer.moveForward == 0 || mc.gameSettings.keyBindJump.isKeyDown())
                        return;
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = 0.37;
                    }
                    movementUtil.setSpeed(0.45F);
                    break;
                case "Verus":
                    Wrapper.mc.timer.timerSpeed = 1F;
                    if(isWalking && Wrapper.mc.thePlayer.onGround) {
                        if(timer.hasTimeElapsed(100, true)) {
                            Wrapper.mc.thePlayer.jump();
                        }
                    }
                    if(Wrapper.mc.thePlayer.isSprinting()) {
                        if(Wrapper.mc.thePlayer.onGround) {


                            if(Wrapper.mc.thePlayer.moveForward > 0) {
                                SpeedUtil.setSpeed(0.19F);
                            } else {
                                SpeedUtil.setSpeed(0.14F);
                            }
                        } else {
                            if(Wrapper.mc.thePlayer.moveForward > 0) {
                                SpeedUtil.setSpeed(0.295F);
                            } else {
                                SpeedUtil.setSpeed(0.29F);
                            }

                        }

                    } else {
                        if(Wrapper.mc.thePlayer.onGround) {

                            //mc.thePlayer.jump();
                            if(Wrapper.mc.thePlayer.moveForward > 0) {
                                SpeedUtil.setSpeed(0.16F);
                            } else {
                                SpeedUtil.setSpeed(0.14F);
                            }
                        } else {
                            if(Wrapper.mc.thePlayer.moveForward > 0) {
                                SpeedUtil.setSpeed(0.25F);
                            } else {
                                SpeedUtil.setSpeed(0.2F);
                            }

                        }
                    }
                        break;
                        case "Minemora":
                            if (mc.gameSettings.keyBindForward.isKeyDown()) {
                                if (mc.thePlayer.onGround) {
                                    mc.thePlayer.jump();
                                    movementUtil.setSpeed(0.42F);
                                }
                                break;
                            }

                    }
            }
        }


    @Override
    public void onEnable() {
        if (targetStrafe != null)
            this.targetStrafe = Koks.getKoks().moduleManager.getModule(TargetStrafe.class);
        mc.timer.timerSpeed = 1.0;
        mc.thePlayer.jumpMovementFactor = 0.02F;
        canSpeed = false;
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0;
    }

}
