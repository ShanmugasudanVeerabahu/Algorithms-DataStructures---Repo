package elasticcomputing.model;

/* enum to define Machine categories */
public enum MachineSize implements MachineCapacity {
	SMALL(25), MEDIUM(50), LARGE(75), XLARGE(100);
	private int capacity;

	private MachineSize(int cap) {
		this.capacity = cap;
	}

	@Override
	public int getMachineCapacity() {
		// TODO Auto-generated method stub
		return this.capacity;
	}
}