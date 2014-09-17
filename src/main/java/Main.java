import com.HeightGen.Filters.*;
import com.HeightGen.Generators.FlatGenerator;
import com.HeightGen.Generators.PerlinGenerator;
import com.HeightGen.Generators.SinWaveGenerator;
import com.HeightGen.HeightMap;

import java.io.File;
import java.io.IOException;

public class Main{
    public static void main(String[] args){
        FlatGenerator flatGenerator = new FlatGenerator();
        SinWaveGenerator sinWaveGenerator = new SinWaveGenerator(4,4);
        PerlinGenerator perlinGenerator = new PerlinGenerator(10,4);
        AddFiftyClipped filter = new AddFiftyClipped();
        AverageFilter averageFilter = new AverageFilter();
        ThermalErosionFilter thermalErosionFilter = new ThermalErosionFilter(20, 5, 0.1);
        ReverseThermalErosionFilter reverseThermalErosionFilter = new ReverseThermalErosionFilter(50,5,0.1);
        GaussianBlurFilter gaussianBlurFilter = new GaussianBlurFilter(100, 0.1);
        try {
            flatGenerator.setHeight(100);
            HeightMap heightMapPerlin = perlinGenerator.generate(1024,1024);
            //HeightMap finalHeightMap = reverseThermalErosionFilter.applyTo(heightMapPerlin);
            HeightMap finalHeightMap = heightMapPerlin;
            finalHeightMap.setColorLookup("src\\main\\resources\\LookupHeat.png");
            //filter.applyTo(heightMap);
            //filter.applyTo(heightMap);
            saveAsLowestVersion(finalHeightMap, "output%d.png");
            finalHeightMap.saveAsImage("output.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAsLowestVersion(HeightMap heightMap, String saveFileNameFormat) throws IOException{
        int saveFileVersion = 1;
        while(new File(String.format(saveFileNameFormat, saveFileVersion)).exists()){
            saveFileVersion++;
        }
        heightMap.saveAsImage(String.format(saveFileNameFormat, saveFileVersion));
    }
}