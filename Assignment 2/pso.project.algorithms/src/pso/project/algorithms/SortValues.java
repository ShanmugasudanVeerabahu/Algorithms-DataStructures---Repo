package pso.project.algorithms;

import java.util.ArrayList;

/* Sort the values of Particle based on velocity- best -> Worst */
public class SortValues {

	public static void mySort(ArrayList<Particle> particles){
		boolean done = false;
		while(!done)
		{
			int changes = 0;
			int listSize = particles.size();
			for(int i = 0; i < listSize - 1; i++)
			{
				if(particles.get(i).compareTo(particles.get(i + 1)) == 1){
					Particle temp = particles.get(i);
					particles.set(i, particles.get(i + 1));
					particles.set(i + 1, temp);
					changes++;
				}
			}
			if(changes == 0){
				done = true;
			}
		}
	}
}
