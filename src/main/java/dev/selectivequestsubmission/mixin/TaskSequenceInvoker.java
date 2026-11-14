package dev.selectivequestsubmission.mixin;

import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.Task;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Task.class, remap = false)
public interface TaskSequenceInvoker {
   @Invoker(value = "checkTaskSequence", remap = false)
   boolean sqs$checkSequence(TeamData var1);
}
