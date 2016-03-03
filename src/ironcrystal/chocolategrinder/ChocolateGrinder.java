package ironcrystal.chocolategrinder;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

@ScriptManifest(category = Category.MONEYMAKING, name = "Chocolate Grinder", author = "IronCrystal", version = 1.0)
public class ChocolateGrinder extends AbstractScript {

	@Override
	public void onStart() {
		log("Chocolate Grinding Started!");
	}

	@Override
	public int onLoop() {
		grindChocolate();
		return 100;
	}

	private void grindChocolate() {
		//Does player have a knife
		if (getInventory().contains(item -> item != null && item.getID() == 946)) {
			//Does player have chocolate bars
			if (getInventory().contains(item -> item != null && item.getID() == 1973)) {
				if (getInventory().get(item -> item != null && item.getID() == 946).useOn(getInventory().get(item -> item != null && item.getID() == 1973))) {
					sleep(Calculations.random(400, 800));
				}
			}else{
				if (getBank().isOpen()) {
					if (getInventory().contains(item -> item != null && item.getID() == 1975)) {
						if (getBank().depositAll(1975)) {
							sleepUntil(() -> getInventory().emptySlotCount() == 27, 5000);
						}
					}
					if (getBank().withdrawAll(1973)) {
						sleepUntil(() -> getInventory().isFull(), 5000);
					}else{
						log("Player has run out of chocolate bars, time to stop script");
						this.stop();
					}
					if (getInventory().count(1973) > 0) {
						if (getBank().close()) {
							sleepUntil(() -> !getBank().isOpen(), 5000);
						}
					}
				}else{
					if (getBank().open()) {
						sleepUntil(() -> getBank().isOpen(), 10000);
					}
				}
			}
		}else{
			if (getBank().isOpen()) {
				if (!getInventory().isEmpty()) {
					if (getBank().depositAllItems()) {
						sleepUntil(() -> getInventory().isEmpty(), 5000);
					}
				}
				if (getBank().withdraw(946)) {
					sleepUntil(() -> getInventory().contains(946), 5000);
				}
				if (getInventory().contains(746)) {
					if (getBank().close()) {
						sleepUntil(() -> !getBank().isOpen(), 5000);
					}
				}
			}else{
				if (getBank().open()) {
					sleepUntil(() -> getBank().isOpen(), 10000);
				}
			}
		}
	}

}
