package net.avataryourney.Pyromancy;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import com.projectkorra.projectkorra.Element.SubElement;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.FireAbility;
import com.projectkorra.projectkorra.Element;

public class Pyromancy extends FireAbility implements AddonAbility {

	
	
	private PyromancyListener listener;
	
	private Block fire;

	private boolean applyPhysics;

	public Pyromancy(Player player, Block block, BlockFace blockFace, Material material) {
		super(player);
		
		bPlayer.addCooldown(this); 
		setOnFire(block, blockFace, material);
		start();
		
	}

	private void setOnFire(Block block, BlockFace blockFace, Material material) {
		createTempFire(block, blockFace, material);
		if (getStartTime() + 20000 < System.currentTimeMillis()) {
			revertTempFire(block, blockFace, material);
		}
	}
	
	
	private void createTempFire(Block block, BlockFace blockFace, Material material) {
		fire = block.getRelative(blockFace);
		Location blockUnderFire = block.getLocation().add(0.5, 0.5, 0.5);
		
		// if (material.isBurnable()) {
		//	block.getLocation().add(1.5, 1.5, 1.5).setType(Material.FIRE);
		//}
		
			if (bPlayer.canUseSubElement(SubElement.BLUE_FIRE)) {
				if (blockUnderFire.getBlock().getType().isSolid()) {
					fire.setType(Material.SOUL_FIRE, setApplyPhysics(false));
				}
			}
			
			if (bPlayer.hasElement(Element.FIRE) && !bPlayer.canUseSubElement(SubElement.BLUE_FIRE) == true) {
		
					fire.setType(Material.FIRE);
			}
	}
	
	private void revertTempFire(Block block, BlockFace blockFace, Material material) {
		fire = block.getRelative(blockFace);
		fire.setType(material);
	}
	
	@Override
	public void progress() {
		
	}

	@Override
	public long getCooldown() {
		return 0;
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public void load() {
		listener = new PyromancyListener();
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(listener, ProjectKorra.plugin);
		
		Permission perm = new Permission("bending.ability.pyromancy");
		perm.setDefault(PermissionDefault.OP);
		ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
		
		ProjectKorra.log.info("Successfully enabled " + getName() + " by " + getAuthor());
		
		
	}

	@Override
	public void stop() {
		Permission perm = new Permission("bending.ability.pyromancy");
		HandlerList.unregisterAll(listener);
		ProjectKorra.plugin.getServer().getPluginManager().removePermission(perm);
		
	}
	
	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public String getName() {
		return "Pyromancy";
	}
	
	@Override
	public String getAuthor() {
		return "Avatarjourney.net";
	}

	@Override
	public String getVersion() {
		return "V1.0";
	}

	public boolean isApplyPhysics() {
		return applyPhysics;
	}

	public boolean setApplyPhysics(boolean applyPhysics) {
		this.applyPhysics = applyPhysics;
		return applyPhysics;
	}

}
