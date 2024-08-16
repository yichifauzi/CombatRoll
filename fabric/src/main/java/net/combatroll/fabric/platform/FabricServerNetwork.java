package net.combatroll.fabric.platform;

import net.combatroll.network.Packets;
import net.combatroll.network.ServerNetwork;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class FabricServerNetwork {
    public static void init() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            sender.sendPacket(Packets.ConfigSync.ID, (new Packets.ConfigSync(ServerNetwork.configSerialized)).write());
        });

        ServerPlayNetworking.registerGlobalReceiver(Packets.RollPublish.ID, (server, player, handler, buf, responseSender) -> {
            var packet = Packets.RollPublish.read(buf);
            ServerNetwork.handleRollPublish(packet, server, player);
        });
    }
}
