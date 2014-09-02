import com.HeightGen.Filters.AddFiftyClipped;
import com.HeightGen.Generators.Flat;
import com.HeightGen.HeightMap;

import java.io.IOException;

public class Main{
    public static void main(String[] args){
        Flat flat = new Flat();
        AddFiftyClipped filter = new AddFiftyClipped();
        try {
            HeightMap heightMap = flat.generate(256, 256);
            heightMap.setColorLookup("C:\\Users\\Eracoy\\Documents\\GitHub\\HeightGen\\src\\main\\resources\\Lookup.png");
            filter.applyTo(heightMap);
            filter.applyTo(heightMap);
            heightMap.saveAsImage("output.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}