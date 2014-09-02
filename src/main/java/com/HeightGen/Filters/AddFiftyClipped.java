package com.HeightGen.Filters;

import com.HeightGen.Filter;
import com.HeightGen.HeightMap;

/**
 * Created by Eracoy on 9/1/2014.
 */
public class AddFiftyClipped implements Filter {
    @Override
    public void applyTo(HeightMap heightMap){
        float[][] heights = heightMap.getHeights();
        for(int x=0; x<heights.length; x++){
            for(int y=0; y<heights[0].length; y++){
                heights[x][y] +=50;
                if(heights[x][y] > 255){
                    heights[x][y] = 255;
                }
            }
        }
    }
}
