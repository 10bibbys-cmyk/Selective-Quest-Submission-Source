package dev.selectivequestsubmission.client;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.WidgetType;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftblibrary.util.client.PositionedIngredient;
import dev.ftb.mods.ftbquests.client.gui.FTBQuestsTheme;
import dev.ftb.mods.ftbquests.client.gui.quests.ValidItemsScreen;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.ItemTask;
import dev.selectivequestsubmission.QuestSubmissionMod;
import java.util.Optional;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;

public final class SelectionScreen extends BaseScreen {
   private final ItemTask task;
   private final int[] quantities = new int[36];
   private final class_1799[] snapshots = new class_1799[36];
   private static final Color4I OUTLINE = Color4I.rgb(66, 210, 112);
   private static final Color4I UI_COLOR_0 = Color4I.BLACK.withAlpha(168);
   private static final Color4I UI_COLOR_1 = Color4I.WHITE.withAlpha(78);
   private static final Color4I UI_COLOR_2 = Color4I.BLACK.withAlpha(90);
   private static final Color4I UI_COLOR_3 = Color4I.BLACK.withAlpha(100);
   private static final Color4I UI_COLOR_4 = Color4I.WHITE.withAlpha(45);
   private static final Color4I UI_COLOR_5 = Color4I.BLACK.withAlpha(70);

   public SelectionScreen(ItemTask var1) {
      this.task = var1;
      this.setWidth(250);
      this.setHeight(232);
   }

   private class_1661 inventory() {
      return class_310.method_1551().field_1724.method_31548();
   }

   private long remaining() {
      TeamData var1 = TeamData.get(class_310.method_1551().field_1724);
      return var1 == null ? this.task.getMaxProgress() : Math.max(0L, this.task.getMaxProgress() - var1.getProgress(this.task));
   }

   private int selectedTotal() {
      int var1 = 0;

      for (int var5 : this.quantities) {
         var1 += var5;
      }

      return var1;
   }

   private void toggle(int var1, boolean var2) {
      if (this.quantities[var1] > 0) {
         this.quantities[var1] = 0;
         this.snapshots[var1] = null;
      } else {
         class_1799 var3 = (class_1799)this.inventory().field_7547.get(var1);
         if (var3 != null && !var3.method_7960() && this.task.test(var3)) {
            if (this.quantities[var1] > 0) {
               this.quantities[var1] = 0;
               this.snapshots[var1] = null;
            } else {
               int var4 = (int)Math.min(2147483647L, this.remaining() - this.selectedTotal());
               if (var4 > 0) {
                  this.quantities[var1] = Math.min(Math.min(var3.method_7947(), var4), var2 ? 1 : 64);
                  this.snapshots[var1] = var3.method_7972();
               }
            }
         }
      }
   }

   private void sendSelection() {
      int var1 = 0;
      class_1661 var2 = this.inventory();

      for (int var3 = 0; var3 < 36; var3++) {
         if (this.quantities[var3] != 0) {
            class_1799 var4 = (class_1799)var2.field_7547.get(var3);
            if (!class_1799.method_7973(var4, this.snapshots[var3]) || !this.task.test(var4)) {
               return;
            }

            var1++;
         }
      }

      if (var1 >= 1 && this.selectedTotal() <= this.remaining()) {
         class_2540 var5 = PacketByteBufs.create();
         var5.writeLong(this.task.id);
         var5.writeByte(var1);

         for (int var6 = 0; var6 < 36; var6++) {
            if (this.quantities[var6] != 0) {
               var5.writeByte(var6);
               var5.writeByte(this.quantities[var6]);
               var5.method_10793(this.snapshots[var6]);
            }
         }

         ClientPlayNetworking.send(QuestSubmissionMod.CHANNEL, var5);
         this.onBack();
      }
   }

   public void addWidgets() {
      for (int var1 = 0; var1 < 36; var1++) {
         int var2 = var1 < 27 ? var1 + 9 : var1 - 27;
         int var3 = var1 % 9;
         int var4 = var1 / 9;
         SelectionScreen.SlotButton var5 = new SelectionScreen.SlotButton(this, var2);
         this.add(var5);
         var5.setPosAndSize(18 + var3 * 24, 38 + var4 * 24 + (var4 == 3 ? 6 : 0), 22, 22);
      }

      SimpleTextButton var6 = new SimpleTextButton(this, class_2561.method_43470("Submit Selected"), Color4I.empty()) {
         public void onClicked(MouseButton var1) {
            this.playClickSound();
            SelectionScreen.this.sendSelection();
         }

         public WidgetType getWidgetType() {
            return !SelectionScreen.this.questsubmission$hasEnough() ? WidgetType.DISABLED : super.getWidgetType();
         }

         public boolean renderTitleInCenter() {
            return true;
         }

         public void drawBackground(class_332 var1, Theme var2, int var3, int var4, int var5, int var6x) {
            VanillaButtonStyle.draw(
               var1, var3, var4, var5, var6x, !SelectionScreen.this.questsubmission$hasEnough() ? WidgetType.DISABLED : this.getWidgetType()
            );
         }
      };
      var6.setPosAndSize(123, 188, 114, 20);
      this.add(var6);
      SimpleTextButton var7 = new SimpleTextButton(this, class_2561.method_43470("Cancel"), Color4I.empty()) {
         public void onClicked(MouseButton var1) {
            this.playClickSound();
            SelectionScreen.this.onBack();
         }

         public boolean renderTitleInCenter() {
            return true;
         }

         public void drawBackground(class_332 var1, Theme var2, int var3, int var4, int var5, int var6) {
            VanillaButtonStyle.draw(var1, var3, var4, var5, var6, this.getWidgetType());
         }
      };
      var7.setPosAndSize(17, 188, 98, 20);
      this.add(var7);
      SelectionScreen$3 var8 = new SelectionScreen$3(this, this, class_2561.method_43470("View Eligible Items"), Color4I.empty());
      var8.setPosAndSize(17, 165, 220, 18);
      this.add(var8);
   }

   public void alignWidgets() {
   }

   public Theme getTheme() {
      return FTBQuestsTheme.INSTANCE;
   }

   public void drawForeground(class_332 var1, Theme var2, int var3, int var4, int var5, int var6) {
      super.drawForeground(var1, var2, var3, var4, var5, var6);
      UI_COLOR_0.draw(var1, var3, var4, 250, 2);
      UI_COLOR_0.draw(var1, var3, var4 + 230, 250, 2);
      UI_COLOR_0.draw(var1, var3, var4, 2, 232);
      UI_COLOR_0.draw(var1, var3 + 248, var4, 2, 232);
      UI_COLOR_1.draw(var1, var3 + 2, var4 + 2, 246, 1);
      UI_COLOR_1.draw(var1, var3 + 2, var4 + 2, 1, 225);
      UI_COLOR_2.draw(var1, var3 + 2, var4 + 226, 246, 1);
      UI_COLOR_2.draw(var1, var3 + 247, var4 + 2, 1, 225);
      UI_COLOR_3.draw(var1, var3 + 14, var4 + 35, 222, 1);
      UI_COLOR_3.draw(var1, var3 + 14, var4 + 139, 222, 1);
      UI_COLOR_3.draw(var1, var3 + 14, var4 + 35, 1, 105);
      UI_COLOR_3.draw(var1, var3 + 235, var4 + 35, 1, 105);
      UI_COLOR_4.draw(var1, var3 + 15, var4 + 36, 220, 1);
      UI_COLOR_4.draw(var1, var3 + 15, var4 + 36, 1, 103);
      UI_COLOR_5.draw(var1, var3 + 15, var4 + 138, 220, 1);
      UI_COLOR_5.draw(var1, var3 + 234, var4 + 36, 1, 103);
      var2.drawString(var1, "Choose items to submit", var3 + 17, var4 + 9, Color4I.WHITE, 0);
      var2.drawString(var1, "Selected: " + this.selectedTotal() + " / " + this.remaining() + " remaining", var3 + 17, var4 + 24, Color4I.LIGHT_GREEN, 0);
      var2.drawString(var1, "Left-click: stack    Right-click: one item", var3 + 17, var4 + 142, Color4I.WHITE, 0);
      var2.drawString(var1, "Click a selected item again to deselect", var3 + 17, var4 + 153, Color4I.WHITE, 0);
   }

   void sqs$showEligibleItems() {
      new ValidItemsScreen(this.task, this.task.getValidDisplayItems(), false).openGui();
   }

   public boolean questsubmission$hasEnough() {
      return this.selectedTotal() > 0 && this.selectedTotal() >= this.remaining();
   }

   private final class SlotButton extends Button {
      private final int slot;
      private static final Color4I UI_COLOR_0 = Color4I.BLACK.withAlpha(100);
      private static final Color4I UI_COLOR_1 = Color4I.GRAY.withAlpha(-71);
      private static final Color4I UI_COLOR_2 = Color4I.WHITE.withAlpha(45);
      private static final Color4I UI_COLOR_3 = Color4I.BLACK.withAlpha(95);

      SlotButton(Panel nullx, int nullxx) {
         super(nullx);
         this.slot = nullxx;
      }

      private class_1799 stack() {
         return (class_1799)SelectionScreen.this.inventory().field_7547.get(this.slot);
      }

      public void onClicked(MouseButton var1) {
         SelectionScreen.this.toggle(this.slot, var1.isRight());
         this.playClickSound();
      }

      public WidgetType getWidgetType() {
         if (SelectionScreen.this.quantities[this.slot] > 0) {
            return super.getWidgetType();
         }

         class_1799 var1 = this.stack();
         return var1 != null && !var1.method_7960() && SelectionScreen.this.task.test(var1) ? super.getWidgetType() : WidgetType.DISABLED;
      }

      public void draw(class_332 var1, Theme var2, int var3, int var4, int var5, int var6) {
         super.draw(var1, var2, var3, var4, var5, var6);
         UI_COLOR_0.draw(var1, var3, var4, var5, var6);
         UI_COLOR_1.draw(var1, var3 + 1, var4 + 1, var5 - 2, var6 - 2);
         UI_COLOR_2.draw(var1, var3 + 1, var4 + 1, var5 - 2, 1);
         UI_COLOR_2.draw(var1, var3 + 1, var4 + 1, 1, var6 - 2);
         UI_COLOR_3.draw(var1, var3 + 1, var4 + var6 - 2, var5 - 2, 1);
         UI_COLOR_3.draw(var1, var3 + var5 - 2, var4 + 1, 1, var6 - 2);
         class_1799 var7 = this.stack();
         if (var7 != null && !var7.method_7960()) {
            ItemIcon.getItemIcon(var7).draw(var1, var3 + 3, var4 + 3, 16, 16);
         }

         if (var7 != null && !var7.method_7960() && this.getWidgetType() == WidgetType.DISABLED) {
            Color4I.BLACK.withAlpha(155).draw(var1, var3 + 1, var4 + 1, var5 - 2, var6 - 2);
            Color4I.BLACK.withAlpha(38).draw(var1, var3 + 3, var4 + 3, 16, 16);
         }

         if (SelectionScreen.this.quantities[this.slot] > 0) {
            SelectionScreen.OUTLINE.draw(var1, var3, var4, var5, 2);
            SelectionScreen.OUTLINE.draw(var1, var3, var4 + var6 - 2, var5, 2);
            SelectionScreen.OUTLINE.draw(var1, var3, var4, 2, var6);
            SelectionScreen.OUTLINE.draw(var1, var3 + var5 - 2, var4, 2, var6);
            var2.drawString(
               var1,
               String.valueOf(SelectionScreen.this.quantities[this.slot]),
               var3 + (SelectionScreen.this.quantities[this.slot] >= 10 ? 8 : 13),
               var4 + 12,
               Color4I.WHITE,
               0
            );
         }
      }

      public Optional<PositionedIngredient> getIngredientUnderMouse() {
         return Optional.empty();
      }

      public void addMouseOverText(TooltipList var1) {
      }
   }
}
