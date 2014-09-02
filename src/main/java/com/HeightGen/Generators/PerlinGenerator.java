package com.HeightGen.Generators;

import com.HeightGen.HeightMap;

import java.util.Random;

/**
 * Created by Eracoy on 9/1/2014.
 */
public class PerlinGenerator {

    double[][] randomHeights;
    int randomHeightsXSize, randomHeightsYSize;

    public PerlinGenerator(int randomHeightsXSize, int randomHeightsYSize){
        this.randomHeightsXSize = randomHeightsXSize;
        this.randomHeightsYSize = randomHeightsYSize;
    }

    public void initializeRandomHeights(int xSize, int ySize){
        randomHeights = new double[xSize][ySize];
        Random rand = new Random();
        for(int x = 0; x<xSize; x++){
            for(int y=0; y<ySize; y++){
                randomHeights[x][y] = rand.nextDouble()%255;
            }
        }
    }

    private double interpolate(double lower, double upper, double x){
        return (upper-lower)*(x * x * x * (x * (x * 6.0 - 15.0) + 10.0) + lower);
    }

    private double noise(double x, double y){
        double hInterLow = interpolate(randomHeights[(int)x][(int)y], randomHeights[(int)Math.ceil(x)%randomHeightsXSize][(int)Math.ceil(y)%randomHeightsYSize], x%1);
        double hInterHigh = interpolate(randomHeights[(int)x][(int)y], randomHeights[(int)Math.ceil(x)%randomHeightsXSize][(int)Math.ceil(y)%randomHeightsYSize], x%1);
        return interpolate(hInterLow, hInterHigh, y%1);
    }

    public double octavedNoise(double x, double y, int octaves, double roughness, double scale) {
        double noiseSum = 0;
        double layerFrequency = scale;
        double layerWeight = 1;
        double weightSum = 0;
        initializeRandomHeights(1024, 1024);

        for (int octave = 0; octave < octaves; octave++) {
            noiseSum += noise(x * layerFrequency, y * layerFrequency) * layerWeight;
            layerFrequency *= 2;
            weightSum += layerWeight;
            layerWeight *= roughness;
        }
        return noiseSum / weightSum;
    }

    public HeightMap generate(int xSize, int ySize){
        double[][] heights = new double[xSize][ySize];
        for(int x=0; x<heights.length; x++){
            System.out.println("Generating " + 100*x/heights.length + "% complete");
            for(int y=0; y<heights[0].length; y++){
                heights[x][y] = octavedNoise(x, y, 1, 0.5, 2);

            }
        }
        HeightMap result = new HeightMap(heights);
        return result;
    }
}
