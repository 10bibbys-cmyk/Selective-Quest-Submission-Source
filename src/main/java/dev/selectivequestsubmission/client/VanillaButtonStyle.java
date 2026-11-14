package dev.selectivequestsubmission.client;

import dev.ftb.mods.ftblibrary.ui.WidgetType;
import dev.ftb.mods.ftbquests.client.gui.FTBQuestsTheme;
import net.minecraft.class_332;

public final class VanillaButtonStyle {
   private VanillaButtonStyle() {
   }

   public static void draw(class_332 var0, int var1, int var2, int var3, int var4, WidgetType var5) {
      FTBQuestsTheme.INSTANCE.drawButton(var0, var1, var2, var3, var4, var5);
   }
}
