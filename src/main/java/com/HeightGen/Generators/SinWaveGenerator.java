package com.HeightGen.Generators;

import com.HeightGen.HeightMap;

import java.util.Arrays;

public class SinWaveGenerator {
    int xFrequency,yFrequency;
    public SinWaveGenerator(int xFrequency, int yFrequency){
        this.xFrequency = xFrequency;
        this.yFrequency = yFrequency;
    }

    public HeightMap generate(int xSize, int ySize){
        double[][] heights = new double[xSize][ySize];
        for(int x=0; x<heights.length; x++){
            System.out.println("Generating " + 100*x/heights.length + "% complete");
            for(int y=0; y<heights[0].length; y++){
                heights[x][y] =
                        Math.sin(x*xFrequency*2*Math.PI/xSize)+
                        Math.sin(y*yFrequency*2*Math.PI/ySize);
                heights[x][y] *= 128/2;
                heights[x][y] += 128;

            }
        }
        HeightMap result = new HeightMap(heights);
        return result;
    }
}
