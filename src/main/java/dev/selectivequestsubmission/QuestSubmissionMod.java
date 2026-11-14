package dev.selectivequestsubmission;

import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.ItemTask;
import dev.selectivequestsubmission.mixin.TaskSequenceInvoker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_3222;

public final class QuestSubmissionMod implements ModInitializer {
   public static final String MOD_ID = "selective_quest_submission";
   public static final class_2960 CHANNEL = new class_2960("selective_quest_submission", "selected_items");
   public static final Logger LOG = Logger.getLogger("Selective Quest Submission");
   private static final int MAX_ENTRIES = 36;

   public void onInitialize() {
      ServerPlayNetworking.registerGlobalReceiver(CHANNEL, (var0, var1, var2, var3, var4) -> {
         ArrayList var7 = new ArrayList();

         long var5;
         try {
            var5 = var3.readLong();
            short var8 = var3.readUnsignedByte();
            if (var8 < 1 || var8 > 36) {
               return;
            }

            HashSet var9 = new HashSet();

            for (int var10 = 0; var10 < var8; var10++) {
               short var11 = var3.readUnsignedByte();
               short var12 = var3.readUnsignedByte();
               class_1799 var13 = var3.method_10819();
               if (var11 >= 36 || var12 < 1 || var12 > 64 || !var9.add(Integer.valueOf(var11)) || var13.method_7960()) {
                  return;
               }

               var7.add(new QuestSubmissionMod.ChosenItem(var11, var12, var13));
            }
         } catch (RuntimeException var14) {
            LOG.warning("Rejected malformed item-submission packet");
            return;
         }

         var0.execute(() -> submit(var1, var5, var7));
      });
      LOG.info("Registered safe selected-slot FTB Quest item submissions");
   }

   static void submit(class_3222 var0, long var1, List<QuestSubmissionMod.ChosenItem> var3) {
      if (!var3.isEmpty() && var3.size() <= 36) {
         TeamData var4 = TeamData.get(var0);
         if (var4 != null && !var4.isLocked()) {
            if (var4.getFile().getTask(var1) instanceof ItemTask var6) {
               if (var6.consumesResources() && !var6.isTaskScreenOnly() && !var4.isCompleted(var6)) {
                  if (var4.canStartTasks(var6.getQuest())) {
                     if (((TaskSequenceInvoker)var6).sqs$checkSequence(var4)) {
                        if (var4.getFile() instanceof ServerQuestFile var7) {
                           class_1661 var17 = var0.method_31548();
                           long var9 = var6.getMaxProgress() - var4.getProgress(var6);
                           if (var9 > 0L) {
                              long var11 = 0L;
                              HashSet var13 = new HashSet();

                              for (QuestSubmissionMod.ChosenItem var15 : var3) {
                                 if (var15.slot() >= 0 && var15.slot() < 36 && var13.add(var15.slot())) {
                                    class_1799 var16 = (class_1799)var17.field_7547.get(var15.slot());
                                    if (var16 != null && !var16.method_7960() && class_1799.method_7973(var16, var15.snapshot())) {
                                       if (var15.count() >= 1 && var15.count() <= var16.method_7947() && var6.test(var16)) {
                                          var11 += var15.count();
                                          if (var11 > var9) {
                                             return;
                                          }
                                          continue;
                                       }

                                       return;
                                    }

                                    return;
                                 }

                                 return;
                              }

                              var7.withPlayerContext(var0, () -> {
                                 for (QuestSubmissionMod.ChosenItem var6x : var3) {
                                    class_1799 var7x = (class_1799)var17.field_7547.get(var6x.slot());
                                    class_1799 var8 = var7x.method_7972();
                                    var8.method_7939(var6x.count());
                                    class_1799 var9x = var6.insert(var4, var8, false);
                                    int var10 = var6x.count() - var9x.method_7947();
                                    if (var10 != var6x.count()) {
                                       LOG.warning("Task unexpectedly refused selected items; stopped submission");
                                       if (var10 > 0) {
                                          sqs$consumeAccepted(var17, var6x, var7x, var10);
                                       }
                                       break;
                                    }

                                    class_1799 var11x = var7x.method_7972();
                                    var11x.method_7939(var7x.method_7947() - var10);
                                    var17.field_7547.set(var6x.slot(), var11x.method_7960() ? class_1799.field_8037 : var11x);
                                 }

                                 var17.method_5431();
                                 var0.field_7512.method_7623();
                              });
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void sqs$consumeAccepted(class_1661 var0, QuestSubmissionMod.ChosenItem var1, class_1799 var2, int var3) {
      class_1799 var4 = var2.method_7972();
      var4.method_7939(Math.max(0, var2.method_7947() - var3));
      var0.field_7547.set(var1.slot(), var4.method_7960() ? class_1799.field_8037 : var4);
   }

   public record ChosenItem(int slot, int count, class_1799 snapshot) {
   }
}
