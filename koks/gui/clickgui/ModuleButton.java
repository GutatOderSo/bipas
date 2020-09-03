package koks.gui.clickgui;

import koks.Koks;
import koks.gui.clickgui.elements.Element;
import koks.gui.clickgui.elements.ElementBoolean;
import koks.gui.clickgui.elements.ElementSlider;
import koks.modules.Module;
import koks.utilities.RenderUtils;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 08:41
 */
public class ModuleButton {

    public Module module;
    public float x, y, width, height;
    public RenderUtils renderUtils = new RenderUtils();
    public float yMaxElements;

    private final List<Element> elementList = new ArrayList<>();

    public boolean extended;

    public ModuleButton(Module module) {
        this.module = module;
        Koks.getKoks().valueManager.getValues().forEach(value -> {
            if (value.getModule().equals(module)) {
                if (value instanceof BooleanValue) {
                    this.elementList.add(new ElementBoolean((BooleanValue) value));
                }
                if (value instanceof NumberValue) {
                    this.elementList.add(new ElementSlider((NumberValue) value));
                }
            }
        });
    }

    public void drawScreen(int mouseX, int mouseY) {
        if (extended) {
            float[] yTest = {0};
            this.elementList.forEach(element -> {
                element.setPosition(x + 3, this.y + height + yTest[0], width - 6, height - 2);
                element.drawScreen(mouseX, mouseY);
                yTest[0] += height;
            });
            yMaxElements = yTest[0];
        } else {
            yMaxElements = 0F;
        }
        renderUtils.drawOutlineRect(x, y, x + width, y + height + yMaxElements, 1, new Color(40, 39, 42, 255));
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(module.getModuleName(), x + 3F, y - 2, -1);
    }

    public void mouseReleased() {

    }

    public void keyTyped(int keyCode) {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.extended = !this.extended;
        }
        this.elementList.forEach(element -> {
            element.mouseClicked(mouseX, mouseY, mouseButton);
        });
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public RenderUtils getRenderUtils() {
        return renderUtils;
    }

    public void setRenderUtils(RenderUtils renderUtils) {
        this.renderUtils = renderUtils;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public void setInformation(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}