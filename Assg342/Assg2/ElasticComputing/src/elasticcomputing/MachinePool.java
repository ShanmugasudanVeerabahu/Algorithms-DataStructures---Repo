/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elasticcomputing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 *
 * @author ruchirapatil
 */
public class MachinePool {
	/* enum to define Machine categories */
	enum MachineSize {
		SMALL(25), MEDIUM(50), LARGE(75), XLARGE(100);
		private int capacity;

		private MachineSize(int cap) {
			this.capacity = cap;
		}

		public int getCapacity() {
			return this.capacity;
		}
	}

	protected static ArrayList<Machine> machinePoolList = new ArrayList<Machine>();


	/* Create New Machines */
	public static void createNewMachines() {
		for (int i = 0; i < 10; i++) {
			machinePoolList.add(new Machine(MachineSize.SMALL.getCapacity()));
			machinePoolList.add(new Machine(MachineSize.MEDIUM.getCapacity()));
			machinePoolList.add(new Machine(MachineSize.LARGE.getCapacity()));
			machinePoolList.add(new Machine(MachineSize.XLARGE.getCapacity()));
		}
	}

	/* Get the machines for Dispatcher */
	public static void getMachinesForDispatcher(int isMacCount,int imMacCount,int ilMacCount,int ixlMacCount ) {
		int sMacCount = isMacCount, mMacCount = imMacCount, lMacCount = ilMacCount, xlMacCount = ixlMacCount;
		for (Machine mc : machinePoolList) {
			if (sMacCount > 0 && mMacCount > 0 && lMacCount > 0 && xlMacCount > 0) {
				switch (mc.getProcessingCapacity()) {

				case 25:
					sMacCount--;
					mc.setMachineInMachinePool(false);
				case 50:
					mMacCount--;
					mc.setMachineInMachinePool(false);
				case 75:
					lMacCount--;
					mc.setMachineInMachinePool(false);
				case 100:
					xlMacCount--;
					mc.setMachineInMachinePool(false);
				}
			} else {
				break;
			}
		}
		// return deleteFromPool;
	}
	/*
	 * Request from Dispatcher to get Machines public ArrayList<Machine>
	 * getMachines(ArrayList<Machine> deleteFromPool,int count, int pSize) { for
	 * (Machine mc : machinePool) { if (count > 0) { if
	 * (mc.getProcessingCapacity() == pSize) { deleteFromPool.add(mc); count--;
	 * } }else{ break; } } return deleteFromPool; }
	 * 
	 * /* Delete the instances that are provided to Dispatcher public boolean
	 * deleteFromMachinePool(ArrayList<Machine> deleteFromPool){ for(int i=0; i<
	 * deleteFromPool.size(); i++){ machinePool.remove(deleteFromPool.get(i)); }
	 * return true; }
	 */
}
