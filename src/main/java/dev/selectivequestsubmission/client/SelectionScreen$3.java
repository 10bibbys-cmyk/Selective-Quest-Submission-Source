package dev.selectivequestsubmission.client;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import net.minecraft.class_2561;
import net.minecraft.class_332;

final class SelectionScreen$3 extends SimpleTextButton {
   final SelectionScreen this$0;

   SelectionScreen$3(SelectionScreen var1, Panel var2, class_2561 var3, Icon var4) {
      super(var2, var3, var4);
      this.this$0 = var1;
   }

   public void onClicked(MouseButton var1) {
      this.playClickSound();
      this.this$0.sqs$showEligibleItems();
   }

   public boolean renderTitleInCenter() {
      return true;
   }

   public void drawBackground(class_332 var1, Theme var2, int var3, int var4, int var5, int var6) {
      VanillaButtonStyle.draw(var1, var3, var4, var5, var6, this.getWidgetType());
   }
}
