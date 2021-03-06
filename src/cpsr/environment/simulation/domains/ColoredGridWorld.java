package cpsr.environment.simulation.domains;

import java.util.Random;

import cpsr.environment.ModelQualityExperiment;
import cpsr.environment.components.Observation;
import cpsr.planning.PSRPlanningExperiment;

public class ColoredGridWorld extends GridWorld 
{
	protected static final char[][] GRID_WORLD = {
		{'x','x','x','x','x','x','x','x','x','x','x','x','x','x'},
		{'x',' ',' ',' ','x',' ','x',' ',' ',' ',' ',' ',' ','x'},
		{'x',' ',' ',' ',' ',' ','x',' ',' ','x','x','G',' ','x'},
		{'x','x','x','x',' ',' ','x','x',' ',' ','x',' ',' ','x'},
		{'x',' ',' ',' ',' ',' ',' ',' ',' ',' ','x',' ',' ','x'},
		{'x',' ',' ',' ','x',' ',' ',' ',' ',' ','x',' ',' ','x'},
		{'x',' ',' ',' ','x',' ',' ',' ','x',' ',' ',' ',' ','x'},
		{'x',' ',' ',' ','x',' ',' ',' ','x',' ','x',' ',' ','x'},
		{'x',' ',' ',' ','x',' ',' ',' ','x',' ','x',' ',' ','x'},
		{'x','x','x','x','x','x','x','x','x','x','x','x','x','x'}};

	protected static char[][] GRID_WORLD_COLORS = {
		{'x','x','x','x','x','x','x','x','x','x','x','x','x','x'},
		{'x',' ',' ',' ','x',' ','x',' ',' ',' ',' ',' ',' ','x'},
		{'x',' ',' ',' ',' ',' ','x',' ',' ','x','x','G',' ','x'},
		{'x','x','x','x',' ',' ','x','x',' ',' ','x',' ',' ','x'},
		{'x',' ',' ',' ',' ',' ',' ',' ',' ',' ','x',' ',' ','x'},
		{'x',' ',' ',' ','x',' ',' ',' ',' ',' ','x',' ',' ','x'},
		{'x',' ',' ',' ','x',' ',' ',' ','x',' ',' ',' ',' ','x'},
		{'x',' ',' ',' ','x',' ',' ',' ','x',' ','x',' ',' ','x'},
		{'x',' ',' ',' ','x',' ',' ',' ','x',' ','x',' ',' ','x'},
		{'x','x','x','x','x','x','x','x','x','x','x','x','x','x'}};


	public static void main(String args[])
	{
		ColoredGridWorld follow = new ColoredGridWorld(10);
		
//		PSRPlanningExperiment experiment = new PSRPlanningExperiment(args[0], args[1], follow);
//		experiment.runExperiment();
//		
		ModelQualityExperiment experiment = new ModelQualityExperiment(args[0], args[1], follow);
		experiment.runExperiment(10);
		experiment.publishResults(args[2]);
	}
	
	public ColoredGridWorld(long seed, int maxRunLength)
	{
		super(seed, maxRunLength);
		setColors();
	}

	public ColoredGridWorld(long seed)
	{
		super(seed);
		setColors();
	}



	private void setColors()
	{
		Random rando = new Random(seed);
		for(int i = 0;  i < GRID_WORLD_COLORS.length; i++)
		{
			for(int j = 0; j < GRID_WORLD_COLORS[i].length; j++)
			{
				GRID_WORLD_COLORS[i][j] = Integer.toString(rando.nextInt(3)).charAt(0);
			}
		}
	}
	
	@Override
	protected int getNumberOfObservations() 
	{
		return (int)Math.pow(2,8);
	}

	@Override
	protected Observation getCurrentObservation() 
	{
		boolean[] walls = new boolean[4];

		boolean[] binRep = new boolean[8];
		if(GRID_WORLD[yPos-1][xPos] == 'x')
		{
			if(GRID_WORLD_COLORS[yPos-1][xPos] == '1')
			{
				binRep[0] = false;
				binRep[1] = false;
			}
			else if(GRID_WORLD_COLORS[yPos-1][xPos] == '2')
			{
				binRep[0] = true;
				binRep[1] = false;
			}
			else
			{
				binRep[0] = false;
				binRep[1] = true;
			}
		}
		if(GRID_WORLD[yPos][xPos+1] == 'x')
		{
			if(GRID_WORLD_COLORS[yPos][xPos+1] == '1')
			{
				binRep[2] = false;
				binRep[3] = false;
			}
			else if(GRID_WORLD_COLORS[yPos][xPos+1] == '2')
			{
				binRep[2] = true;
				binRep[3] = false;
			}
			else
			{
				binRep[2] = false;
				binRep[3] = true;
			}
		}		
		if(GRID_WORLD[yPos+1][xPos] == 'x')
		{
			if(GRID_WORLD_COLORS[yPos+1][xPos] == '1')
			{
				binRep[4] = false;
				binRep[5] = false;
			}
			else if(GRID_WORLD_COLORS[yPos+1][xPos] == '2')
			{
				binRep[4] = true;
				binRep[5] = false;
			}
			else
			{
				binRep[4] = false;
				binRep[5] = true;
			}
		}
		if(GRID_WORLD[yPos][xPos-1] == 'x')
		{
			if(GRID_WORLD_COLORS[yPos][xPos-1] == '1')
			{
				binRep[6] = false;
				binRep[7] = false;
			}
			else if(GRID_WORLD_COLORS[yPos][xPos-1] == '2')
			{
				binRep[6] = true;
				binRep[7] = false;
			}
			else
			{
				binRep[6] = false;
				binRep[7] = true;
			}
		}

		int obsID = 0;
		for(int i = 0; i < binRep.length; i++)
		{
			if(binRep[i])
				obsID += Math.pow(2, i);
		}

		return new Observation(obsID);
	}
}
