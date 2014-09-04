package com.HeightGen.Filters;

import com.HeightGen.Filter;
import com.HeightGen.HeightMap;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

/**
 * Created by Eracoy on 9/3/2014.
 */
public class ReverseThermalErosionFilter implements Filter {

    double tAngle; //Talus angle. In the reversed filter, soil migrates if on a greater slope than this. Represented as a difference
    int numIterations; //Number of times to apply the erosion step
    double solubility; //how much of the slope to erode each iteration

    public ReverseThermalErosionFilter(int numIterations, double tAngle, double solubility){
        this.numIterations = numIterations;
        this.tAngle = tAngle;
        this.solubility = solubility;
    }

    @Override
    public HeightMap applyTo(HeightMap... heightMaps){
        double[][] finalHeights = heightMaps[0].getHeights();
        double[][] newHeights = heightMaps[0].getHeights();
        int xSize = finalHeights.length;
        int ySize = finalHeights[0].length;
        for(int iteration = 0; iteration<numIterations; iteration++) {
            if (iteration % 10 == 0) System.out.println("AverageFilter " + 100 * iteration / numIterations + "% complete");
            finalHeights = newHeights;
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    //for non-reversed, move a quantity equal to (maxDifference-tAngle)/2
                    double heightDifference = maxDifference(
                            finalHeights[x][y],
                            finalHeights[(x+xSize-1)%xSize][(y+ySize-1)%ySize],
                            finalHeights[(x+xSize-1)%xSize][(y+1)%ySize],
                            finalHeights[(x+1)%xSize][(y+ySize-1)%ySize],
                            finalHeights[(x+1)%xSize][(y+1)%ySize]
                            );

                    //if the maximum difference is less than the talus angle,
                    //find the neighbor with the most difference and give it some of the center's soil
                    if(heightDifference<tAngle){
                        //System.out.println("\nHeight difference: "+heightDifference);
                        //System.out.println("Height before: "+finalHeights[x][y]);
                        if(abs(finalHeights[(x+xSize-1)%xSize][(y+ySize-1)%ySize] - finalHeights[x][y])  == heightDifference){
                            newHeights[x][y] -= heightDifference*solubility;
                            newHeights[(x+xSize-1)%xSize][(y+ySize-1)%ySize]+=heightDifference*solubility;
                        }
                        else if(abs(finalHeights[(x+xSize-1)%xSize][(y+ySize+1)%ySize] - finalHeights[x][y])  == heightDifference){
                            newHeights[x][y] -= heightDifference*solubility;
                            newHeights[(x+xSize-1)%xSize][(y+1)%ySize]+=heightDifference*solubility;
                        }
                        else if(abs(finalHeights[(x+xSize+1)%xSize][(y+ySize-1)%ySize] - finalHeights[x][y])  == heightDifference){
                            newHeights[x][y] -= heightDifference*solubility;
                            newHeights[(x+1)%xSize][(y+ySize-1)%ySize]+=heightDifference*solubility;
                        }
                        else if(abs(finalHeights[(x+xSize+1)%xSize][(y+ySize+1)%ySize] - finalHeights[x][y])  == heightDifference){
                            newHeights[x][y] -= heightDifference*solubility;
                            newHeights[(x+1)%xSize][(y+1)%ySize]+=heightDifference*solubility;
                        }
                        //System.out.println("Height after: "+finalHeights[x][y]);
                    }
                }
            }
        }
        return new HeightMap(finalHeights);
    }

    //returns the largest difference between the height of the center and four other heights
    public double maxDifference(double center, double h1, double h2, double h3, double h4){
        return max(
                max(abs(center-h1), abs(center-h2)),
                max(abs(center-h3), abs(center-h4))
        );
    }

}
