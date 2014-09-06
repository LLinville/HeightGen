package com.HeightGen.Filters;

import com.HeightGen.Filter;
import com.HeightGen.HeightMap;

/**
 * Created by Eracoy on 9/4/2014.
 */
public class GaussianBlurFilter implements Filter {

    double numIterations; //Talus angle. In the reversed filter, soil migrates if on a greater slope than this. Represented as a difference
    double intensity;

    public GaussianBlurFilter(int numIterations, double intensity){
        this.numIterations = numIterations;
        this.intensity = intensity;
    }
    
    @Override
    public HeightMap applyTo(HeightMap... heightMaps){
        double[][] finalHeights = heightMaps[0].getHeights();
        double[][] smoothedHeights = heightMaps[0].getHeights();
        int xSize = finalHeights.length;
        int ySize = finalHeights[0].length;
        for(int iteration = 0; iteration<numIterations; iteration++) {
            if (iteration % 10 == 0) System.out.println("GaussianBlurFilter " + 100 * iteration / numIterations + "% complete");

            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < finalHeights[0].length; y++) {
                    smoothedHeights[x][y] -= (averageNeighborHeight(finalHeights,x,y,xSize,ySize)-finalHeights[x][y]) * intensity;
                }
            }
            finalHeights = smoothedHeights;
        }
        return new HeightMap(finalHeights);
    }
    
    private double averageNeighborHeight(double[][] heights, int x, int y, int xSize, int ySize){
        return heights[(x+xSize-1)%xSize][y]+
                heights[(x+1)%xSize][y]+
                heights[x][(y+ySize-1)%ySize]+
                heights[(x+xSize-1)%xSize][(y+ySize-1)%ySize]+
                heights[(x+1)%xSize][(y+ySize-1)%ySize]+
                heights[(x+1)%xSize][(y+1)%ySize]+
                heights[(x+xSize-1)%xSize][(y+1)%ySize]+
                heights[x][(y+1)%ySize] / 8;
    }
}
