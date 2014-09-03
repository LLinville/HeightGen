package com.HeightGen.Generators;

import com.HeightGen.HeightMap;

import java.util.Random;

/**
 * Created by Eracoy on 9/1/2014.
 */
public class PerlinGenerator {

    int numOctaves;
    double[] octaveWeights;

    public PerlinGenerator(int numOctaves, double[] octaveWeights){
        this.numOctaves = numOctaves;
        this.octaveWeights = octaveWeights;
    }

    private double interpolate(double lower, double upper, double x){
        //take absolute value of upper-lower for a cool effect
        return (upper-lower)*(x * x * x * (x * (x * 6.0 - 15.0) + 10.0)) + lower;
    }

    private double[][] enlarge(double[][] small, int factor){
        int smallXSize = small.length;
        int smallYSize = small[0].length;
        int largeXSize = smallXSize*factor;
        int largeYSize = smallYSize*factor;

        double[][] large = new double[largeXSize][largeYSize];

        for(int y=0; y<largeYSize; y+=factor) {
            for (int x = 0; x < largeXSize; x++) {
                if (x % factor == 0) { //we are on a point provided by the small grid
                    large[x][y] = small[x/factor][y/factor];
                } else{
                    //System.out.printf("x %d, y %d\n", x, y);
                    //System.out.printf("Interpolating from %d,%d to %d,%d\n\n",x/factor,y/factor,(x/factor+1)%(smallXSize),y/factor);
                    large[x][y] = interpolate(
                            small[x/factor][y/factor],
                            small[(x/factor+1)%(smallXSize)][y/factor],
                            (double)(x%factor)/factor);
                }
            }
        }
        for(int x=0; x<largeXSize; x++){
            for(int y=0; y<largeYSize; y++){
                if(y%factor == 0){
                    continue;
                }
                //System.out.printf("x %d, y %d\n", x, y);

                large[x][y] = interpolate(
                        large[x][y-y%factor],
                        large[x][(y-y%factor+factor)%largeYSize],
                        (double)(y%factor)/factor);
            }
        }
        return large;
    }

    public double[][] getOctaveNoise(int size, int enlargementFactor){
        double[][] heights = new double[size][size];
        Random rand = new Random();
        for(int x=0; x<heights.length; x++){
            if(x%10==0) System.out.println("initializing random grid " + 100*x/heights.length + "% complete");
            for(int y=0; y<heights[0].length; y++){
                heights[x][y] = rand.nextDouble()*255;
            }
        }
        heights = enlarge(heights, enlargementFactor);
        return heights;
    }
    public HeightMap generate(int xSize, int ySize){
        double[][] heights = new double[xSize][ySize];
        double[][] currentOctave;

        int startingOctave = 5;
        for(int octave = startingOctave; octave<numOctaves; octave++) {
            currentOctave = getOctaveNoise((int) Math.pow(2,octave), (int)(xSize/Math.pow(2,octave)));
            for(int x=0; x<xSize; x++){
                for(int y=0; y<ySize; y++){
                    heights[x][y] += currentOctave[x][y]/Math.pow(2, octave-startingOctave+1);
                }
            }
        }

        HeightMap result = new HeightMap(heights);
        return result;
    }
}
