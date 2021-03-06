package com.superlord.dungeoncraft.entities;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;

import com.superlord.dungeoncraft.util.handlers.SoundsHandler;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityGnoll extends EntityMob
{

    public EntityGnoll(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
    }

     protected void initEntityAI()
        {
            this.tasks.addTask(0, new EntityAISwimming(this));
            this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
            this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
            this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
            this.tasks.addTask(8, new EntityAILookIdle(this));
            this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0F, true));
            this.applyEntityAI();
        }

        protected void applyEntityAI()
        {
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
            this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
            this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityFriendlySkeleton.class, true));
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityGuardV.class, true));

        }
        
        protected SoundEvent getAmbientSound()
        {
            return SoundsHandler.GNOLL_IDLE;
        }

        protected SoundEvent getHurtSound(DamageSource p_184601_1_)
        {
            return SoundsHandler.GNOLL_HURT;
        }

        protected SoundEvent getDeathSound()
        {
            return SoundsHandler.GNOLL_HURT;
        }
        
        protected void applyEntityAttributes()
        {
            super.applyEntityAttributes();
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
            this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(22.0D);
        }
        
        @Nullable
        public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
        {
            livingdata = super.onInitialSpawn(difficulty, livingdata);
            float f = difficulty.getClampedAdditionalDifficulty();
            this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);

            if (livingdata == null)
            {
                livingdata = new EntityGnoll.GroupData(this.world.rand.nextFloat() < net.minecraftforge.common.ForgeModContainer.zombieBabyChance);
            }

            if (livingdata instanceof EntityGnoll.GroupData)
            {
                EntityGnoll.GroupData entityzombie$groupdata = (EntityGnoll.GroupData)livingdata;

             
            }

            this.setEquipmentBasedOnDifficulty(difficulty);
            this.setEnchantmentBasedOnDifficulty(difficulty);

            if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())
            {
                Calendar calendar = this.world.getCurrentDate();

                if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
                {
                    this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                    this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
                }
            }

            this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
            double d0 = this.rand.nextDouble() * 1.5D * (double)f;

            if (d0 > 1.0D)
            {
                this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
            }

            if (this.rand.nextFloat() < f * 0.05F)
            {
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
            }

            return livingdata;
        }

        
        protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
        {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
        }
        
        class GroupData implements IEntityLivingData
        {
            public boolean isChild;

            private GroupData(boolean p_i47328_2_)
            {
                this.isChild = p_i47328_2_;
            }
        }
 
}