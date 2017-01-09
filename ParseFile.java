import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Vector;

public class ParseFile{

	public static void main (String[] args){

		FileWriter fw = null;

		if (args.length < 1){
			System.out.println("Please input a filename as an arguement.\n");

		}else{
			// get file name from the aguement
			String filename = args[0];
			System.out.println("Filename: " + filename);

			try{
				DeviceNameFile pmtNames = new DeviceNameFile("files/pmts_db.txt");
				LargeFile file = new LargeFile(filename);
				System.out.println("Number of Rows: " + file.getNumRows());
				double[][][] chunkedData = file.getMatrixFromData(file.getNumRows());

				for (int i = 0; i < pmtNames.getNumberOfDevices(); i++){ // 430

					//System.out.println(pmtNames.getDevices().get(i));
					String savedPathName = "data/" + pmtNames.getDevices().get(i) + ".txt";
					fw = new FileWriter(savedPathName);
					for (int j = 0; j < chunkedData[i].length; j++){ // 768
						for (int k = 0; k < chunkedData[i][j].length; k++){ // 45
							// write values to a file
							try{
								fw.write(Double.toString(chunkedData[i][j][k]) + " ");
							}catch(Exception e){ System.out.println(e.getMessage()); }
						}
						fw.write("\n");
					}
					fw.close();
				}

			}catch(Exception e){ System.out.println(e.getMessage()); }
		}

	}



}
