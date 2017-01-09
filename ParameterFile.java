import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.io.File;

public class ParameterFile{

  public static void main(String[] args){

    //System.out.println("Working Directory = " + System.getProperty("user.dir"));
    ParameterFile file = new ParameterFile("Text_Files/parameter_list.txt");
    System.out.println(file.numParameters);
    Vector<String> parameters = file.getParameters();

    for (String para : parameters){

      System.out.println(para);
    }

  }

  private int numParameters; // how many parameters the file holds
  private Vector<String> parameters; // the name of the parameters
  private String filepath; // name of the filepath

  public ParameterFile(String filename){

    File file = new File(filename);

    if (file.canRead() == false){

      System.out.println("Error. File cannot be read.");
    }else{
      this.filepath = filename;
      this.initParameters();
      this.initNumParameters();
    }
  }

  private void initNumParameters(){

    this.numParameters = this.parameters.size();
  }

  private void initParameters(){

    String thisLine = null;
    this.parameters = new Vector<String>();
      try{
         // open input stream test.txt for reading purpose.
         FileReader fileReader =  new FileReader(this.filepath);
         BufferedReader br = new BufferedReader(fileReader);
         while ((thisLine = br.readLine()) != null) {
            this.parameters.add(thisLine);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
  }

  public int getNumberOfParameters(){

    return this.numParameters;
  }

  public Vector<String> getParameters(){

    return this.parameters;
  }

}
