package dinodungeons.game.data.map.objects;

public class SpikeMapObject extends MapObject {
	
	int spikeType;
	
	public SpikeMapObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		return getSpikeName(spikeType);
	}

	public int getSpikeType() {
		return spikeType;
	}

	public void setSpikeType(int spikeType) {
		this.spikeType = spikeType;
	}
	
	public static String getSpikeName(int spikeType){
		switch (spikeType) {
		case 0:
			return "MetalSpikes";
		case 1:
			return "WoodenSpikes";
		}
		return "Other Spikes";
	}
}
