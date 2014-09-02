package com.HeightGen.Generators;

import com.HeightGen.Generator;
import com.HeightGen.HeightMap;

import java.util.Arrays;

/**
 * Created by Eracoy on 8/31/2014.
 */
public class FlatGenerator implements Generator {
    int height = 0;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }



    public HeightMap generate(int xSize, int ySize){
        double[][] heights = new double[xSize][ySize];
        for (double[] row: heights) {
            Arrays.fill(row, height);
        }
        HeightMap result = new HeightMap(heights);
        return result;
    }
}
