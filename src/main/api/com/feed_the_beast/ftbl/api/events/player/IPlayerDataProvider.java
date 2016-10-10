package com.feed_the_beast.ftbl.api.events.player;

import com.feed_the_beast.ftbl.api.IForgePlayer;
import com.feed_the_beast.ftbl.lib.INBTData;

/**
 * Created by LatvianModder on 10.10.2016.
 */
public interface IPlayerDataProvider
{
    INBTData getPlayerData(IForgePlayer player);
}
