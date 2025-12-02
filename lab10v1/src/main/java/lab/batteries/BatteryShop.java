package lab.batteries;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BatteryShop {

	private List<Battery> batteries;

	public BatteryShop() {
		this.batteries = new LinkedList<>();
		batteries.add(new Battery(Battery.types.get(0), 700, 150));
		batteries.add(new Battery(Battery.types.get(0), 1500, 200));
		batteries.add(new Battery(Battery.types.get(1), 3000, 35));
		batteries.add(new Battery(Battery.types.get(3), 600, 140).discharge());
		batteries.add(new Battery(Battery.types.get(2), 240, 10));
		batteries.add(new Battery(Battery.types.get(4), 850, 50));
		batteries.add(new Battery(Battery.types.get(0), 4000, 300));
	}

	public void iterateBatteries(BatteryEvaluator evaluator) throws IOException {
		for (Battery battery : batteries) {
			if(evaluator.check(battery)) {
				evaluator.log(battery);
			}
		}

	}

	public interface BatteryEvaluator{
		void log(Battery battery) throws IOException;
		boolean check(Battery battery);
	}

}
