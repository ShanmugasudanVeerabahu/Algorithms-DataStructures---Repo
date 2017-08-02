package pso.project.algorithms;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.ui.RefineryUtilities;

public class SwarmOptimization implements PSOConstants{
	
	public static HashMap<Integer,ArrayList<Integer>> plotpBest = new HashMap<Integer,ArrayList<Integer>>();
	private static ArrayList<Particle> particles = new ArrayList<Particle>();
	
	private static void initialize()
	{
		for(int i = 0; i < PSO_PARTICLE_COUNT; i++)
	    {
	    	Particle newParticle = new Particle();
	        int newLength = new Random().nextInt(PSO_MAX_LENGTH);
	        for(int j = 0; j < newLength; j++)
	        {
	            newParticle.data(PSO_CHAR_SET[new Random().nextInt(PSO_CHAR_SET.length)]);
	        } // j
	        newParticle.pBest(PSO_MAX_LENGTH); // Any large number should do.  Algorithm will push this down to 0.
	        particles.add(newParticle);
	    } // i
	}
	
	private static void initiatePSO()   //Graphics g
	{
		int epoch = 0;
		boolean done = false;
		long startTime= System.nanoTime();
		long runTime=0;
		long endTime=0;
	    while(!done)
	    {
	        // Two conditions can end this loop:
	        //    if the maximum number of epochs allowed has been reached, or,
	        //    if the Target value has been found.
	        if(epoch < PSO_MAX_EPOCHS){

	            for(int i = 0; i < PSO_PARTICLE_COUNT; i++)
	            {
	                System.out.println(particles.get(i).toString() + " = " + testProblem(i));
	                if(testProblem(i) <= 0){
	                    done = true;
	                }
	            }
	            
	           SortValues.mySort(particles); // sort particles by their pBest scores, best to worst.

	            getVelocity();

	           ArrayList<Integer> particleChanges= updateparticles();
	            
	            System.out.println("epoch number: " + epoch);
//	            endTime =System.nanoTime();
//	            runTime+=(endTime-startTime)/1000000;	            
	  //         drawParticle(g,runTime); 
	            
	            plotpBest.put(epoch, particleChanges);
	            ScatterPlotDemo scatterPlotOfpBest = new ScatterPlotDemo("pBest ", plotpBest);
	            scatterPlotOfpBest.pack();
	            RefineryUtilities.centerFrameOnScreen(scatterPlotOfpBest);
	            scatterPlotOfpBest.setVisible(true);
	            epoch++;
	        }else{
	            done = true;
	        }
		}
	}
	
	private static int testProblem(final int index)
	{
		return LD(PSO_TARGET, particles.get(index).data());
	}
	
	private static void getVelocity()
	{
	    double worstResults = 0;
		double vValue = 0.0;
		
		// after sorting, worst will be last in list.
	    worstResults = particles.get(PSO_PARTICLE_COUNT - 1).pBest();

	    for(int i = 0; i < PSO_PARTICLE_COUNT; i++)
	    {
	        vValue = (PSO_V_MAX * particles.get(i).pBest()) / worstResults;

	        if(vValue > PSO_V_MAX){
	        	particles.get(i).setVelocity(PSO_V_MAX);
	        }else if(vValue < PSO_V_MIN){
	        	particles.get(i).setVelocity(PSO_V_MIN);
	        }else{
	        	particles.get(i).setVelocity(vValue);
	        }
	    }
	}
	
	private static ArrayList<Integer> updateparticles()
	{
		ArrayList<Integer> particleChanges= new ArrayList<Integer>();
		// Best is at index 0, so start from the second best.
	    for(int i = 1; i < PSO_PARTICLE_COUNT; i++)
	    {
    		// The higher the velocity score, the more changes it will need.
	    	int changes = (int)Math.floor(Math.abs(particles.get(i).getVelocity()));
    		System.out.println("Changes for particle " + i + ": " + changes);
    		particleChanges.add(particles.get(i).data().length);
        	for(int j = 0; j < changes; j++){
        		if(new Random().nextBoolean()){
        			randomlyArrange(i);
        		}
        		
        		if(new Random().nextBoolean()){
        			copyFromParticle(i - 1, i);	// Push it closer to it's best neighbor.
        		}else{
        			copyFromParticle(0, i);		// Push it closer to the best particle.
        		}
        	} // j
	        
	        // Update pBest value.
	        particles.get(i).pBest(LD(PSO_TARGET, particles.get(i).data()));
	    } // i
	    return particleChanges;
	}
	
	private static void copyFromParticle(final int source, final int destination)
	{
		// push destination's data points closer to source's data points.
		Particle src = particles.get(source);
		int srcLength = src.data().length;
		if(srcLength > 0){
			int destLength = particles.get(destination).data().length;
			
			int target = new Random().nextInt(srcLength); // source's character to target.
			
			if(destLength >= srcLength){
				particles.get(destination).data(target, src.data(target));
			}else{
				int i;
				if(destLength > 0){
					i = destLength - 1;
				}else{
					i = 0;
				}
				for(; i < srcLength; i++)
				{
					particles.get(destination).data(src.data(target));
				}
			}
		}
	}
	
	private static void randomlyArrange(final int index)
	{
		int dataLength = particles.get(index).data().length;
		
		// 50/50 chance of removing a character.
		if(dataLength > 0 && new Random().nextBoolean()){				// Remove one.
			int target = new Random().nextInt(dataLength);
			particles.get(index).removeDataAt(target);
		}else if(dataLength > PSO_MIN_LENGTH){
			// 50/50 chance of appending a new character.
			if(dataLength < PSO_MAX_LENGTH && new Random().nextBoolean()){	// Add one.
				particles.get(index).data(PSO_CHAR_SET[new Random().nextInt(PSO_CHAR_SET.length - 1) + 1]);
			}else{														// Change existing one.
				int target = new Random().nextInt(dataLength);
				particles.get(index).data(target, PSO_CHAR_SET[new Random().nextInt(PSO_CHAR_SET.length - 1) + 1]);
			}
		}
	}
	
	// Compute Levenshtein Distance
	private static int LD(final char[] s, final char[] t)
	{
		int n = s.length;
		int m = t.length;

		int d[][] = new int[m + 1][n + 1];
		
	    // Initialize the first row to 0,...,m
	    for(int i = 0; i <= m; i++)
	    {
	        d[i][0] = i;
	    }

	    // Initialize the first column to 0,...,n
	    for(int j = 0; j <= n; j++)
	    {
	        d[0][j] = j;
	    }

	    // Examine each character of s (j from 1 to n).
	    for(int j = 1; j <= n; j++)
	    {
	        // Examine each character of t (i from 1 to m).
	        for(int i = 1; i <= m; i++)
	        {
	            if(s[j - 1] == t[i - 1]){
	            	d[i][j] = d[i - 1][j - 1];
	            }else{
		            // Set cell d[i][j] of the matrix equal to the minimum of:
		            // The cell immediately above: d[i-1][j] + 1.
		            // The cell immediately to left: d[i][j-1] + 1.
		            // The cell diagonally above and left: d[i-1][j-1] + cost.
		            d[i][j] = minimum((d[i - 1][j]) + 1, (d[i][j - 1]) + 1, (d[i - 1][j - 1]) + 1);
	            }
	        } // i
	    } // j

	    // After the iteration steps (3, 4, 5, 6) are complete, the distance is found in cell d[n,m]
	    return d[m][n];

	}

	// Get minimum of three values.
	private static int minimum(final int a, final int b, final int c)
	{
		int mi = a;

	    if(b < mi){
	        mi = b;
	    }
	    if(c < mi){
	        mi = c;
	    }
	    
	    return mi;
	}
	
	public static void main(String[] args)
	{
		initialize();
		initiatePSO();
	}

}
