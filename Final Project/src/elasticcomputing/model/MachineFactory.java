/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elasticcomputing.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author shanmugasudanv
 */
public class MachineFactory {

	protected static ArrayList<Machine> machinePoolList = new ArrayList<Machine>();

	/* Create New Machines */
	public static void createNewMachines() {
		for (int i = 0; i < 20; i++) {
			machinePoolList.add(new Machine(MachineSize.SMALL.getMachineCapacity(), "S"));
			machinePoolList.add(new Machine(MachineSize.MEDIUM.getMachineCapacity(), "M"));
			machinePoolList.add(new Machine(MachineSize.LARGE.getMachineCapacity(), "L"));
			machinePoolList.add(new Machine(MachineSize.XLARGE.getMachineCapacity(), "XL"));
		}
	}

	/* Get the machines for Dispatcher */
	public static Set<Machine> getMachinesForDispatcher(int isMacCount, int imMacCount, int ilMacCount,
			int ixlMacCount) {
		int sMacCount = isMacCount, mMacCount = imMacCount, lMacCount = ilMacCount, xlMacCount = ixlMacCount;
		Set<Machine> machineName = new HashSet<Machine>();
		for (Machine mc : machinePoolList) {
			if (mc.getProcessingCapacity() == 25 && sMacCount > 0 && mc.isMachineInMachinePool() == true) {
				sMacCount--;
				mc.setMachineInMachinePool(false);
				machineName.add(mc);
			} else if (mc.getProcessingCapacity() == 50 && mMacCount > 0 && mc.isMachineInMachinePool() == true) {
				mMacCount--;
				mc.setMachineInMachinePool(false);
				machineName.add(mc);
			} else if (mc.getProcessingCapacity() == 75 && lMacCount > 0 && mc.isMachineInMachinePool() == true) {
				lMacCount--;
				mc.setMachineInMachinePool(false);
				machineName.add(mc);
			} else if (mc.getProcessingCapacity() == 100 && xlMacCount > 0 && mc.isMachineInMachinePool() == true) {
				xlMacCount--;
				mc.setMachineInMachinePool(false);
				machineName.add(mc);
			}
		}
		Set<Machine> mchineName = new HashSet<Machine>();
		mchineName = machinePoolList.stream().filter(mc -> mc.getProcessingCapacity() == 25)
		.filter(mc -> mc.isMachineInMachinePool() == true)
		.collect(Collectors.toSet());
		boolean isdeleteFromMachinePoolSuccess = (machineName != null) ? deleteFromMachinePool(machineName) : false;
		return machineName;
	}

	/*
	 * Request from Dispatcher to get Machines public ArrayList<Machine>
	 * getMachines(ArrayList<Machine> deleteFromPool,int count, int pSize) { for
	 * (Machine mc : machinePool) { if (count > 0) { if
	 * (mc.getProcessingCapacity() == pSize) { deleteFromPool.add(mc); count--;
	 * } }else{ break; } } return deleteFromPool; }
	 */

	/* Delete the instances that are provided to Dispatcher */
	public static boolean deleteFromMachinePool(Set<Machine> deleteFromPool) {
		/* Java 8 */
		deleteFromPool.forEach(mc ->machinePoolList.remove(mc));
		
		
/*		for (Machine mc : deleteFromPool) {
			machinePoolList.remove(mc);
		}  */
		return true;
	}
}
