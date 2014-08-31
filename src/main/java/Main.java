import com.HeightGen.Generators.Flat;

public class Main{
    public static void main(String[] args){
        Flat flat = new Flat();
        flat.generate(256,256).saveAsImage("output.png");
    }
}