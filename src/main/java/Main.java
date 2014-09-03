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
        double[] octaveWeights = {0.25, 0.25, 0.25, 0.25};
        PerlinGenerator perlinGenerator = new PerlinGenerator(10, octaveWeights);
        AddFiftyClipped filter = new AddFiftyClipped();
        try {
            flatGenerator.setHeight(100);
            HeightMap heightMap = perlinGenerator.generate(4096, 4096);
            heightMap.setColorLookup("src\\main\\resources\\LookupLand.png");
            //filter.applyTo(heightMap);
            //filter.applyTo(heightMap);
            heightMap.saveAsImage("output.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}