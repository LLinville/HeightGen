import com.HeightGen.Filters.*;
import com.HeightGen.Generators.FlatGenerator;
import com.HeightGen.Generators.PerlinGenerator;
import com.HeightGen.Generators.SinWaveGenerator;
import com.HeightGen.HeightMap;

import java.io.IOException;

public class Main{
    public static void main(String[] args){
        FlatGenerator flatGenerator = new FlatGenerator();
        SinWaveGenerator sinWaveGenerator = new SinWaveGenerator(4,4);
        PerlinGenerator perlinGenerator = new PerlinGenerator(8,4);
        AddFiftyClipped filter = new AddFiftyClipped();
        AverageFilter averageFilter = new AverageFilter();
        ThermalErosionFilter thermalErosionFilter = new ThermalErosionFilter(10, 10, 0.9);
        ReverseThermalErosionFilter reverseThermalErosionFilter = new ReverseThermalErosionFilter(100,5,0.1);
        GaussianBlurFilter gaussianBlurFilter = new GaussianBlurFilter(100, 0.1);
        try {
            flatGenerator.setHeight(100);
            HeightMap heightMapPerlin = perlinGenerator.generate(512,512);
            HeightMap finalHeightMap = gaussianBlurFilter.applyTo(heightMapPerlin);
            finalHeightMap.setColorLookup("src\\main\\resources\\LookupLand.png");
            //filter.applyTo(heightMap);
            //filter.applyTo(heightMap);
            finalHeightMap.saveAsImage("output.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}