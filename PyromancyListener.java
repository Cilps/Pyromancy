package net.avataryourney.Pyromancy;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;


public class PyromancyListener implements Listener {

		
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		
		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		if(bPlayer == null) return;
		
		if (bPlayer.canBend(CoreAbility.getAbility(Pyromancy.class))) {
			new Pyromancy(player, event.getClickedBlock(), event.getBlockFace(), event.getMaterial());
		}
	}
	
	
}