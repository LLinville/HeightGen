import com.HeightGen.Filters.AddFiftyClipped;
import com.HeightGen.Filters.AverageFilter;
import com.HeightGen.Filters.ReverseThermalErosionFilter;
import com.HeightGen.Filters.ThermalErosionFilter;
import com.HeightGen.Generators.FlatGenerator;
import com.HeightGen.Generators.PerlinGenerator;
import com.HeightGen.Generators.SinWaveGenerator;
import com.HeightGen.HeightMap;

import java.io.IOException;

public class Main{
    public static void main(String[] args){
        FlatGenerator flatGenerator = new FlatGenerator();
        SinWaveGenerator sinWaveGenerator = new SinWaveGenerator(4,4);
        PerlinGenerator perlinGenerator = new PerlinGenerator(6,5);
        AddFiftyClipped filter = new AddFiftyClipped();
        AverageFilter averageFilter = new AverageFilter();
        ReverseThermalErosionFilter erosionFilter = new ReverseThermalErosionFilter(1000, 10, 0.9);
        try {
            flatGenerator.setHeight(100);
            HeightMap heightMapPerlin = perlinGenerator.generate(512,512);
            HeightMap finalHeightMap = erosionFilter.applyTo(heightMapPerlin);
            finalHeightMap = heightMapPerlin;
            finalHeightMap.setColorLookup("src\\main\\resources\\LookupBlackWhite.png");
            //filter.applyTo(heightMap);
            //filter.applyTo(heightMap);
            finalHeightMap.saveAsImage("output.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}