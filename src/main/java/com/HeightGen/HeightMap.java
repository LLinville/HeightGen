package com.HeightGen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeightMap {
    int xSize, ySize;
    float heights[][];

    public HeightMap(int width, int height){
        this.heights = new float[width][height];
        xSize=width;
        ySize=height;
    }

    public HeightMap(float[][] heights){
        this.heights = heights;
        ySize=heights.length;
        xSize=heights[0].length;
    }

    public boolean saveAsImage(String filepath){
        if(heights == null){
            return false;
        }
        BufferedImage bi = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_ARGB);
        for(int x=0; x<heights.length; x++){
            for(int y=0; y<heights[0].length; y++){
                int h=(int)heights[x][y];
                bi.setRGB(x,y,(h<<16|h<<8|h)+50);
            }
        }
        try {
            ImageIO.write(bi, "PNG", new File(filepath));
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
