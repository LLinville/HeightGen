package com.HeightGen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeightMap {
    int xSize, ySize;
    float heights[][];
    BufferedImage colorLookup;

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

    public float[][] getHeights(){
        return heights;
    }

    public void setHeights(float[][] heights){
        if(this.heights.length != heights.length || this.heights[0].length != heights[0].length) {
            throw new IndexOutOfBoundsException("Size of given heights does not match previous");
        } else {
            this.heights = heights;
        }
    }

    public void setColorLookup(String colorLookupFilePath) throws IOException{

        colorLookup = ImageIO.read(new File(colorLookupFilePath));

    }

    public boolean saveAsImage(String filepath)throws IOException{
        if(heights == null){
            return false;
        }
        BufferedImage bi = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_ARGB);
        for(int x=0; x<heights.length; x++){
            for(int y=0; y<heights[0].length; y++){
                int h=(int)heights[x][y];
                bi.setRGB(x,y,colorLookup.getRGB(h,0));
            }
        }
        ImageIO.write(bi, "PNG", new File(filepath));

        return true;

    }
}
