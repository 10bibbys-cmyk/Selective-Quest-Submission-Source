package dev.selectivequestsubmission.mixin;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbquests.net.SubmitTaskMessage;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.ItemTask;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SubmitTaskMessage.class, remap = false)
public abstract class SubmitTaskMessageMixin {
   @Shadow
   @Final
   private long taskId;

   @Inject(method = "handle", at = @At("HEAD"), cancellable = true, remap = false)
   private void sqs$rejectAutomaticConsumingSubmissions(PacketContext var1, CallbackInfo var2) {
      TeamData var3 = TeamData.get(var1.getPlayer());
      if (var3 != null) {
         if (var3.getFile().getTask(this.taskId) instanceof ItemTask var5 && var5.consumesResources()) {
            if (var5.isTaskScreenOnly()) {
               return;
            }

            var2.cancel();
         }
      }
   }
}
