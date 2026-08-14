package kamkeel.plugin.Client;

import kamkeel.plugin.Config.ConfigCompat;
import kamkeel.plugin.Network.MessageSwitchOrientation;
import kamkeel.plugin.Network.MessageTransformItem;
import kamkeel.plugin.Network.NetworkHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler{
    private Keybindings getPressedKey(){
        for(Keybindings key : Keybindings.values()) {
            if(key.isPressed()) return key;
        }
        return null;
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event){
        if(ConfigCompat.Keybindings){
            Keybindings key = getPressedKey();
            if(key != null) {
                switch(key){
                    case ORIENT:
                        NetworkHandler.sendToServer(new MessageSwitchOrientation());
                        break;
                    case TRANSFORM:
                        NetworkHandler.sendToServer(new MessageTransformItem());
                        break;
                }
            }
        }
    }
}
