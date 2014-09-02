import com.HeightGen.Filters.AddFiftyClipped;
import com.HeightGen.Generators.FlatGenerator;
import com.HeightGen.Generators.PerlinGenerator;
import com.HeightGen.Generators.SinWaveGenerator;
import com.HeightGen.HeightMap;

import java.io.IOException;

public class Main{
    public static void main(String[] args){
        FlatGenerator flatGenerator = new FlatGenerator();
        SinWaveGenerator sinWaveGenerator = new SinWaveGenerator(4,4);
        PerlinGenerator perlinGenerator = new PerlinGenerator(1024, 1024);
        AddFiftyClipped filter = new AddFiftyClipped();
        try {
            flatGenerator.setHeight(100);
            HeightMap heightMap = perlinGenerator.generate(128,128);
            heightMap.setColorLookup("src\\main\\resources\\Lookup.png");
            //filter.applyTo(heightMap);
            //filter.applyTo(heightMap);
            heightMap.saveAsImage("output.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}