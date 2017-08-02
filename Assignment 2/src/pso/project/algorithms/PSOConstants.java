package pso.project.algorithms;

/* An Interface to declare the parameters involved in PSO */
public interface PSOConstants {

	public static final int PSO_MAX_LENGTH = 3;
	public static final int PSO_MIN_LENGTH = 3;
	public static final char[] PSO_TARGET = new char[]{'p', 's', 'o'};
	public static final char[] PSO_CHAR_SET = new char[]{'a','p', 's', 'o'};
	public static final int PSO_PARTICLE_COUNT = 10;
	public static final double PSO_V_MAX = 4.0;             // Maximum velocity change allowed.
	public static final double PSO_V_MIN = 1.0;

	public static final int PSO_MAX_EPOCHS = 200;
}
