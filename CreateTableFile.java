import java.util.HashMap;
import java.io.FileWriter;

public class CreateTableFile{

  public static void main(String[] args){

    if (args.length < 1){
      System.out.println("Please input a filename as an arguement.\n");
    }else{

      String filename = args[0];
      System.out.println("Filename: " + filename);

      try{
        LargeFile file = new LargeFile(filename);
        double[][][] chunkedData = file.getMatrixFromData(file.getNumRows());
        int numPixels = 64;
        FileWriter fw = new FileWriter("pmt_ccdb.txt");

        System.out.println("Number of Rows: " + file.getNumRows());

        // create array of gains index 12
        for (int i = 0; i < chunkedData.length; i++){ // 430 pmts
          //System.out.println(i);
          for (int j = 0; j < numPixels; j++){ // 64 pixels
              fw.write("1 " + Double.toString(i+1) + " " + Double.toString(j+1) + " " + Double.toString(chunkedData[i][j][12]) + "\n");
          }
        }
        fw.close(); // close FileWriter
        file.close(); // close LargeFile

        System.out.println("File Created: " + "pmt_ccdb.txt");
      }catch(Exception e){ System.out.println(e.getMessage()); }

    }
  }
}
