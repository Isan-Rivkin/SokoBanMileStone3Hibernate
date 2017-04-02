package model.policy;

import common_data.level.Level;
/**
 * Specific level structure policy
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class PolicyLevelStructure {
	Policy policy;
	public PolicyLevelStructure(Policy p) {policy=p;}
	
	public boolean isLevelLegal(Level l){
		if(l.getBoxes().size() == l.getTargets().size() && l.getPlayers().size() <= policy.howManyPlayers() && l.getPlayers().size() >0)
			return true;
		return false;
	}

}
