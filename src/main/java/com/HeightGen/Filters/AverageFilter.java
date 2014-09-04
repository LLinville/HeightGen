package com.HeightGen.Filters;

import com.HeightGen.Filter;
import com.HeightGen.HeightMap;

/**
 * Created by Eracoy on 9/1/2014.
 */
public class AverageFilter implements Filter {
    @Override
    public HeightMap applyTo(HeightMap... heightMaps){
        double[][] finalHeights = new double[heightMaps[0].getHeights().length][heightMaps[0].getHeights()[0].length];
        for(HeightMap heightMap: heightMaps) {
            double[][] currentHeights = heightMap.getHeights();
            for (int x = 0; x < finalHeights.length; x++) {
                if (x % 10 == 0) System.out.println("AverageFilter" + 100 * x / finalHeights.length + "% complete");
                for (int y = 0; y < finalHeights[0].length; y++) {
                    finalHeights[x][y] += currentHeights[x][y] / heightMaps.length;
                }
            }
        }
        return new HeightMap(finalHeights);
    }
}
