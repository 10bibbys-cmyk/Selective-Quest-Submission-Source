package dev.selectivequestsubmission.mixin;

import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftbquests.quest.task.ItemTask;
import dev.selectivequestsubmission.client.SelectionScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemTask.class, remap = false)
public abstract class ItemTaskButtonMixin {
   @Inject(method = "onButtonClicked", at = @At("HEAD"), cancellable = true, remap = false)
   private void sqs$openInventorySelection(Button var1, boolean var2, CallbackInfo var3) {
      ItemTask var4 = (ItemTask)(Object)this;
      if (var2 && var4.consumesResources() && !var4.isTaskScreenOnly()) {
         var1.playClickSound();
         new SelectionScreen(var4).openGui();
         var3.cancel();
      }
   }
}
