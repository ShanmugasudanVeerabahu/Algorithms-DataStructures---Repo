package pso.project.algorithms;

import java.util.ArrayList;

/* Represents a PSO particle */
public class Particle implements Comparable<Particle>
{
    private ArrayList<Character> myValues = new ArrayList<Character>();
    private int mpBest = 0;
    private double mVelocity = 0.0;

    public Particle()
    {
        this.mpBest = 0;
        this.mVelocity = 0.0;
    }
    
    @Override /* Mention the logic to compare two particles */
    public int compareTo(final Particle that)
    {
    	if(this.pBest() < that.pBest()){
    		return -1;
    	}else if(this.pBest() > that.pBest()){
    		return 1;
    	}else{
    		return 0;
    	}
    }

    /* Constitutes character in data  Array list of particle -*/
    public char data(final int index)
    {
    	return this.myValues.get(index);
    }
    
    /* Represents an Array List present in PSO particle */
    public char[] data()
    {
    	char temp[] = new char[this.myValues.size()];
    	for(int i = 0; i < this.myValues.size(); i++)
    	{
    		temp[i] = this.myValues.get(i);
    	}
    	return temp;
    }
    
    public void data(final char value)
    {
    	this.myValues.add(value);
    }
    
    public void data(final int index, final char value)
    {
        this.myValues.set(index, value);
    }
    
    public void removeDataAt(final int index)
    {
    	this.myValues.remove(index);
    }

    public int pBest()
    {
    	return this.mpBest;
    }

    public void pBest(final int value)
    {
    	this.mpBest = value;
    }

    public double getVelocity()
    {
    	return this.mVelocity;
    }
    
   /* Represents velocity of a particle */
    public void setVelocity(final double velocityScore)
    {
       this.mVelocity = velocityScore;
    }
    
    @Override /* To print values in particle -*/
    public String toString()
    {
    	int dataLength = this.myValues.size();
    	StringBuffer temp = new StringBuffer();
    	for(int i = 0; i < dataLength; i++)
    	{
    		temp.append(this.myValues.get(i));
    	}
    	return temp.toString();
    }
}
