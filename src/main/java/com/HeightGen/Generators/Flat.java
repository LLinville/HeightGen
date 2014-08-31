package com.HeightGen.Generators;

import com.HeightGen.Generator;
import com.HeightGen.HeightMap;

/**
 * Created by Eracoy on 8/31/2014.
 */
public class Flat implements Generator {
    public HeightMap generate(int xSize, int ySize){
        HeightMap result = new HeightMap(new float[xSize][ySize]);
        return result;
    }
}
