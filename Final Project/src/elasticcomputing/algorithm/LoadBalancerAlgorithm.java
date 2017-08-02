package elasticcomputing.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import elasticcomputing.exceptions.ResourceExhaustedException;
import elasticcomputing.model.Dispatcher;
import elasticcomputing.model.Machine;

public interface LoadBalancerAlgorithm extends Comparator<Machine> {

	public Machine lookForAvailability(Dispatcher dispatcher, int rq) throws ResourceExhaustedException;

	public void requestMachinesFromMachinePool() throws ResourceExhaustedException;
}
