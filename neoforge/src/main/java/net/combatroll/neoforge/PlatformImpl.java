package net.combatroll.neoforge;

import net.combatroll.Platform;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collection;

import static net.combatroll.Platform.Type.NEOFORGE;

public class PlatformImpl {
    public static Platform.Type getPlatformType() {
        return NEOFORGE;
    }

    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    public static Collection<ServerPlayerEntity> tracking(ServerPlayerEntity player) {
        return (Collection<ServerPlayerEntity>) player.getWorld().getPlayers();
    }

    public static Collection<ServerPlayerEntity> around(ServerWorld world, Vec3d origin, double distance) {
        return world.getPlayers((player) -> player.getPos().squaredDistanceTo(origin) <= (distance*distance));
    }

    public static boolean networkS2C_CanSend(ServerPlayerEntity player, Identifier packetId) {
        return true;
    }

    public static void networkS2C_Send(ServerPlayerEntity player, Identifier packetId, CustomPayload payload) {
        PacketDistributor.PLAYER.with(player).send(payload);
    }

    public static void networkC2S_Send(Identifier packetId, CustomPayload payload) {
        PacketDistributor.SERVER.with(null).send(payload);
    }
}
