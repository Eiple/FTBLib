package com.feed_the_beast.ftbl.net;

import com.feed_the_beast.ftbl.api.IForgePlayer;
import com.feed_the_beast.ftbl.api.IForgeTeam;
import com.feed_the_beast.ftbl.api.IUniverse;
import com.feed_the_beast.ftbl.client.teamsgui.GuiMyTeam;
import com.feed_the_beast.ftbl.client.teamsgui.TeamInst;
import com.feed_the_beast.ftbl.lib.net.LMNetworkWrapper;
import com.feed_the_beast.ftbl.lib.net.MessageToClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.Collection;
import java.util.HashSet;

public class MessageMyTeamGuiResponse extends MessageToClient<MessageMyTeamGuiResponse>
{
    private TeamInst teamInfo;
    private Collection<String> perms;

    public MessageMyTeamGuiResponse()
    {
    }

    public MessageMyTeamGuiResponse(IUniverse universe, IForgeTeam team, IForgePlayer player)
    {
        teamInfo = new TeamInst(universe, team);
        perms = team.getPermissions(player.getProfile().getId(), false);
    }

    @Override
    public LMNetworkWrapper getWrapper()
    {
        return FTBLibNetHandler.NET;
    }

    @Override
    public void fromBytes(ByteBuf io)
    {
        teamInfo = new TeamInst(io);
        int s = io.readInt();
        perms = new HashSet<>(s);
        while(--s >= 0)
        {
            perms.add(ByteBufUtils.readUTF8String(io));
        }
    }

    @Override
    public void toBytes(ByteBuf io)
    {
        teamInfo.write(io);
        io.writeInt(perms.size());
        for(String id : perms)
        {
            ByteBufUtils.writeUTF8String(io, id);
        }
    }

    @Override
    public void onMessage(MessageMyTeamGuiResponse m, EntityPlayer player)
    {
        new GuiMyTeam(m.teamInfo, m.perms).openGui();
    }
}