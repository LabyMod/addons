package de.raik.friendtags;

import net.labymod.api.events.RenderEntityEvent;
import net.labymod.labyconnect.user.ChatUser;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.labymod.user.group.EnumGroupDisplayType;
import net.labymod.utils.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

/**
 * The Renderer for the name tags
 * Implementing RenderEntityEvent to
 * use the render Event
 *
 * @author Raik
 * @version 1.0
 */
public class NameTagRenderer implements RenderEntityEvent {

    /**
     * The addon instance to access the settings
     */
    private final FriendTagsAddon addon;

    /**
     * The render manager of minecraft
     */
    private final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

    /**
     * The tag height of the friend tag
     */
    private static final double TAG_HEIGHT = 0.85D;

    /**
     * The height that will be added
     * if the user has a group with above head
     */
    private static final double ADDITIONAL_GROUP_HEIGHT = 0.15D;

    /**
     * The number the subtitle height will be
     * devided for the additional height
     */
    private static final double SUBTITLE_DIVIDE = 6.0D;

    /**
     * The modifier for the height if
     * there is a scoreboard in the below name section
     */
    private static final double BELOW_NAME_SCOREBOARD_MODIFIER = 1.15F * 0.02666667F;

    /**
     * Event method
     *
     * @param entity The entity that will be rendered
     * @param x X
     * @param y Y
     * @param z Z
     * @param partialTicks partialTicks
     */
    @Override
    public void onRender(Entity entity, double x, double y, double z, float partialTicks) {

        //Checks when the tag can be rendered

        if (!this.addon.isEnabled() || !this.addon.isAllowed())
            return;
        if (!(entity instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) entity;

        if (player.getUniqueID().equals(LabyMod.getInstance().getPlayerUUID()))
            return;

        if (player.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) || entity.riddenByEntity != null)
            return;
        if (player.isSneaking())
            return;

        double distance = player.getDistanceSqToEntity(renderManager.livingPlayer);

        if (distance > (double) (64.0F * 64.0F))
            return;

        this.processRendering(player, x, y, z, distance);
    }

    /**
     * Getting detailed information for the tag
     * and executing the rendering
     *
     * @param player The player the tag shall render for
     * @param x X
     * @param y Y
     * @param z Z
     * @param distance The distance of the player to the player target
     */
    private void processRendering(EntityPlayer player, double x, double y, double z, double distance) {
        String tag = null;

        //Setting tag if laby chat friend
        for (ChatUser chatUser: LabyMod.getInstance().getLabyConnect().getFriends()) {
            if (chatUser.getGameProfile().getId().equals(player.getUniqueID())) {
                tag = this.addon.getFriendFormat();
                break;
            }
        }

        //Cancel if no tag
        if (tag == null)
            return;

        this.renderFriendTag(player, x, this.modifyHeightWithUserdata(player, y, distance), z, ModUtils.translateAlternateColorCodes('&', tag));
    }

    /**
     * Changes the height of the tag
     * because userdata things could overlap
     *
     * @param player The player with the userdata
     * @param currentY The value which will be changed
     * @param distance The distance of the player to the player target
     * @return The changed value
     */
    private double modifyHeightWithUserdata(EntityPlayer player, double currentY, double distance) {
        //The labyMod userdata
        User labyModUser = LabyMod.getInstance().getUserManager().getUser(player.getUniqueID());

        if (distance < 100.0D && player.getWorldScoreboard().getObjectiveInDisplaySlot(2) != null)
            currentY += LabyMod.getInstance().getDrawUtils().fontRenderer.FONT_HEIGHT * BELOW_NAME_SCOREBOARD_MODIFIER;

        //Don't change if there isn't any user data
        if (labyModUser == null)
            return currentY;

        //Apply cosmetic modified name tag height if cosmetics enabled
        if (LabyMod.getSettings().cosmetics)
            currentY += labyModUser.getMaxNameTagHeight();

        //Add additional height if user has rank with tag
        if (labyModUser.getGroup() != null && labyModUser.getGroup().getDisplayType() == EnumGroupDisplayType.ABOVE_HEAD)
            currentY += ADDITIONAL_GROUP_HEIGHT;

        //Add additional height for subtitles
        if (labyModUser.getSubTitle() != null)
            currentY += labyModUser.getSubTitleSize() / SUBTITLE_DIVIDE;

        return currentY;
    }

    /**
     * Rendering the name tag
     *
     * @param player The player the name is rendered for
     * @param x X
     * @param y Y
     * @param z Z
     * @param friendTag The text of the rendered friend tag
     */
    private void renderFriendTag(EntityPlayer player, double x, double y, double z, String friendTag) {
        /*
         * Code Snippet to Render NameTags by LabyStudio
         */
        float fixedPlayerViewX = renderManager.playerViewX * (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1);
        FontRenderer fontRenderer = renderManager.getFontRenderer();
        GlStateManager.pushMatrix();

        GlStateManager.translate((float) x, (float) y + player.height + TAG_HEIGHT, (float) z);
        GL11.glNormal3f(0F, 1F, 0F);
        GlStateManager.rotate(-renderManager.playerViewY, 0F, 1F, 0F);
        GlStateManager.rotate(fixedPlayerViewX, 1F, 0F, 0F);
        float scale = 0.016666668F * 1.8F;
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate( 770, 771, 1, 0 );
        GlStateManager.enableTexture2D();
        fontRenderer.drawString(friendTag, -fontRenderer.getStringWidth(friendTag) / 2, 0, 553648127);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontRenderer.drawString(friendTag, -fontRenderer.getStringWidth(friendTag) / 2, 0, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color( 1.0F, 1.0F, 1.0F, 1.0F );
        GlStateManager.popMatrix();
    }

    /**
     * Renderer Constructor
     * to set the addon
     *
     * @param addon The addon
     */
    public NameTagRenderer(FriendTagsAddon addon) {
        this.addon = addon;
    }
}
