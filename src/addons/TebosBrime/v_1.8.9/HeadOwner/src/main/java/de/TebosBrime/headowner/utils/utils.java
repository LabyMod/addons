package de.TebosBrime.headowner.utils;

import com.mojang.authlib.properties.Property;
import de.TebosBrime.headowner.HeadOwnerAddon;
import net.labymod.core.LabyModCore;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;

import java.util.Iterator;
import java.util.UUID;

public class utils {

    public static Skull getSkullLooking(){
        TileEntity tileEntity = getTileEntityLooking();

        return new Skull(tileEntity);
    }

    private static TileEntity getTileEntityLooking(){
        try {
            MovingObjectPosition movingObjectPosition = LabyModCore.getMinecraft().getPlayer().rayTrace(HeadOwnerAddon.getSettings().getLength(), 1.0F);
            if (movingObjectPosition == null){
                return null;
            }

            BlockPos blockPos = movingObjectPosition.getBlockPos();
            if (blockPos == null){
                return null;
            }

            return LabyModCore.getMinecraft().getWorld().getTileEntity(blockPos);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static class Skull {

        private int type = -1;
        private String username = null;
        private UUID uuid = null;
        private String value = null;

        public Skull(TileEntity tileEntity){
            if(!(tileEntity instanceof TileEntitySkull))
                return;

            TileEntitySkull tileEntitySkull = (TileEntitySkull) tileEntity;
            this.type = tileEntitySkull.getSkullType();

            if(tileEntitySkull.getPlayerProfile() != null){
                this.username = tileEntitySkull.getPlayerProfile().getName();
                this.uuid = tileEntitySkull.getPlayerProfile().getId();

                Iterator<Property> propertyIterator = tileEntitySkull.getPlayerProfile().getProperties().get("textures").iterator();
                while(propertyIterator.hasNext()){
                    this.value = propertyIterator.next().getValue();
                }
            }
        }

        private String getSkullTypeName(){
            switch (this.type){
                case 0:
                    return "skeleton skull";
                case 1:
                    return "wither skeleton skull";
                case 2:
                    return "zombie head";
                case 3:
                    return "player head";
                case 4:
                    return "creeper head";
                case 5:
                    return "dragon head";
                default:
                    return "unknown skull type: " + this.type;
            }
        }

        public String getDisplay(){
            if(this.username != null)
                return this.username;

            if(this.value != null){
                if(HeadOwnerAddon.getSettings().getCopyKey() != -1)
                    return "unknown head (created by texture value, press " + Keyboard.getKeyName(HeadOwnerAddon.getSettings().getCopyKey()) + " to copy skull data)";

                return "unknown head (created by texture value)";
            }

            return getSkullTypeName();
        }

        public String getCopy(){
            String uuid = this.uuid == null ? "unknown" : this.uuid.toString();
            String username = this.username == null ? "unknown" : this.username;
            String value = this.value == null ? "unknown" : this.value;
            String type = getSkullTypeName();

            StringBuilder resultBuilder = new StringBuilder();

            resultBuilder.append("skull type: " + type + " (" + this.type + ")");
            resultBuilder.append(", ");
            resultBuilder.append("username: " + username);
            resultBuilder.append(", ");
            resultBuilder.append("uuid: " + uuid);
            resultBuilder.append(", ");
            resultBuilder.append("texture value: " + value);

            return resultBuilder.toString();
        }

        public boolean isShown() {
            return (type != -1);
        }
    }
}
