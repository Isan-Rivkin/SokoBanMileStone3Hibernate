package model.data.itemGeneratos;

import java.util.HashMap;

import javax.swing.plaf.synth.SynthSpinnerUI;

/**
 * Generate the right item
 * @author Daniel Hake & Isan Rivkin 
 *
 */

public class FactoryItemLoader {
	HashMap<String,Creator> itemsCreators;
	public FactoryItemLoader() {
	   itemsCreators=new HashMap<String,Creator>();
		itemsCreators.put("A", new PlayerGeneratorCreator());
		itemsCreators.put("@", new BoxGeneratorCreator());
		itemsCreators.put("o", new TargetGeneratorCreator());
		itemsCreators.put(" ", new FloorGeneratorCreator());
		itemsCreators.put("#", new WallGeneratorCreator());
		itemsCreators.put("*", new PlayerOnTargetGeneratorCreator());
		itemsCreators.put("$", new BoxOnTargetGenaratorCreator());
	}
	
	//creator interface
	private interface Creator{
		public IitemGenerator create();
	}
	
	// box on target generate creator
	private class BoxOnTargetGenaratorCreator implements Creator{
		@Override
		public IitemGenerator create() {
			return new BoxOnTragetGenerator();
		}
	
	}
	//Wall generator creator
	private class WallGeneratorCreator implements Creator {
		public IitemGenerator create() {	
		return new WallGenerator();}
	}
	//PLayer generator creator
	private class PlayerGeneratorCreator implements Creator {
		public IitemGenerator create() {	
		return new PlayerGenerator();}
	}
	// Target generator creator
	private class TargetGeneratorCreator implements Creator {
		public IitemGenerator create() {	
		return new TargetGenerator();}
}
	//Box generator creator
	private class BoxGeneratorCreator implements Creator {
		public IitemGenerator create() {	
		return new BoxGenerator();}
}
	//Floor generator creator
	private class FloorGeneratorCreator implements Creator {
		public IitemGenerator create() {	
		return new FloorGenerator();}
}

	//player-on-target generator creator
	private class PlayerOnTargetGeneratorCreator implements Creator{

		@Override
		public IitemGenerator create() {
			return new PlayerOnTargetGenerator();
		}
		
	}

//return an item generator
	public IitemGenerator getItemGenerator(char ch){
		String key=Character.toString(ch);
		IitemGenerator generator=this.itemsCreators.get(key).create();
		if(generator != null)
			return generator;
		else
			return null;

	}
}
