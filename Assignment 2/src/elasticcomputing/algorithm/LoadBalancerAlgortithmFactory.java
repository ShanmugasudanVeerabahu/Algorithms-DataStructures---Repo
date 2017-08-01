package elasticcomputing.algorithm;

public class LoadBalancerAlgortithmFactory {
	private LoadBalancerAlgorithm lba;

	public static LoadBalancerAlgorithm getLoadBalancerAlgorithmInstance(String lbaType) {

		LoadBalancerAlgorithm lba = null;
		switch (lbaType) {

		case "LeastConnection":
			lba = new LeastConnection();
			break;

		}
		return lba;
	}
}
