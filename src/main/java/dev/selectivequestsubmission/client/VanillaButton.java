package dev.selectivequestsubmission.client;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import net.minecraft.class_2561;
import net.minecraft.class_332;

public class VanillaButton extends SimpleTextButton {
   private final Runnable action;

   public VanillaButton(Panel var1, String var2, Runnable var3) {
      super(var1, class_2561.method_43470(var2), Color4I.empty());
      this.action = var3;
   }

   public void onClicked(MouseButton var1) {
      this.playClickSound();
      this.action.run();
   }

   public boolean renderTitleInCenter() {
      return true;
   }

   public void drawBackground(class_332 var1, Theme var2, int var3, int var4, int var5, int var6) {
      VanillaButtonStyle.draw(var1, var3, var4, var5, var6, this.getWidgetType());
   }
}
