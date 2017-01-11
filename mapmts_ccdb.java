import java.util.HashMap;
import java.util.Vector;
import java.util.List;
import org.jlab.ccdb.CcdbPackage;
import org.jlab.ccdb.JDBCProvider;
import org.jlab.ccdb.SQLiteProvider;
import org.jlab.ccdb.MySqlProvider;
import org.jlab.ccdb.Assignment;
import java.util.Scanner;
import java.io.Console;
import org.jlab.ccdb.*;

public class mapmts_ccdb{

	public static void main (String[] args){

		HashMap<String, Integer> pmtNameHash = new HashMap<String, Integer>();
		HashMap<String, Integer> pmtStartingPos = new HashMap<String, Integer>();

		if (args[0].equals("-s") == false){
			if (args.length < 4) {
				System.out.println("Run from command line: java -cp ccdb.jar: mapmts_ccdb [rich] [pmt name] [pixel] [pixel] [parameter]");
			}else{

				initHashMaps(pmtNameHash, pmtStartingPos);

				String tablePathName = "/test/rich/mapmts_3";
				String connectionStr = "mysql://clas12reader@clasdb.jlab.org/clas12";

				JDBCProvider provider = CcdbPackage.createProvider(connectionStr);
				provider.connect();
				if (provider.getIsConnected()){
					System.out.println("Connection to : " + connectionStr + ".");
				}

				String richNum = args[0].toUpperCase();
				String pmtName = args[1].toUpperCase();
				String pixelNumber = args[2].toUpperCase();
				String parameter = args[3].toLowerCase();

				Integer sectorNum = Integer.parseInt(richNum);
				System.out.println("Sector: " + sectorNum);

				Integer pmtNum = pmtNameHash.get(pmtName);
				System.out.println("Layer: " + pmtNum);

				Integer pixelNum = Integer.parseInt(pixelNumber);
				System.out.println("Component: " + pixelNum);

				System.out.println("Column: " + parameter);

				// get the data for the mapmt table
				Assignment asgmt = provider.getData(tablePathName);

				// get the column value from the hased value
				//Vector<String> data  = asgmt.getColumnValuesString(colNum);

				//System.out.println(data.size());

				getPMT_atPixel(asgmt, pmtName, pixelNum, parameter, pmtStartingPos);


			}
		}else if (args[0].equals("-s")){

			initHashMaps(pmtNameHash, pmtStartingPos);

			String tablePathName = "/test/rich/mapmts_3";
			String connectionStr = "mysql://clas12reader@clasdb.jlab.org/clas12";

			JDBCProvider provider = CcdbPackage.createProvider(connectionStr);
			provider.connect();
			System.out.println("Running Shell Mode");
			Boolean exitProgram = false;
			String cmd = "";

			Console console = System.console();

			while(cmd.equals("exit") == false){

					cmd = console.readLine("> ");
					String[] splitCmd = cmd.split(" ");

					if (cmd.equals("exit") == false){

						String richNum = splitCmd[0].toUpperCase();
						String pmtName = splitCmd[1].toUpperCase();
						String pixelNumber = splitCmd[2].toUpperCase();
						String parameter = splitCmd[3].toLowerCase();

						Integer sectorNum = Integer.parseInt(richNum);
						System.out.println("Sector: " + richNum);

						Integer pmtNum = pmtNameHash.get(pmtName);
						System.out.println("Layer: " + pmtNum);

						Integer pixelNum = Integer.parseInt(pixelNumber);
						System.out.println("Component: " + pixelNum);

						System.out.println("Column: " + parameter);

						// get the data for the mapmt table
						Assignment asgmt = provider.getData(tablePathName);

						getPMT_atPixel(asgmt, pmtName, pixelNum, parameter, pmtStartingPos);
					}
			}
		}
	}


	private static Double getPMT_atPixel(Assignment asgmt, String pmtName, Integer pixelNum, String parameter, HashMap<String, Integer> startingPos){

		System.out.println(parameter);
		Vector<Double> data = new Vector<Double>();
		Vector<Double> fullData = asgmt.getColumnValuesDouble(parameter);

		Integer startingInds = startingPos.get(pmtName);
		//System.out.println(startingInds);

		//FIXME: 'i' should start at the starting position for the given PMT..
		int counter = 0;
		for (int i = startingInds; i < startingInds + 64; i++){
			//System.out.println(i);
			data.add(fullData.get(i));
		}

		//System.out.println(data.size());
		System.out.println(data.get(pixelNum - 1));

		return data.get(pixelNum - 1);
	}

	private static void initHashMaps(HashMap<String, Integer> pmtNameHash, HashMap<String, Integer> startingPosHash){

		DeviceNameFile pmtNames = new DeviceNameFile("files/pmts_db.txt");
		ParameterFile paraFile = new ParameterFile("files/parameters.txt");

		for (int i = 0; i < pmtNames.getNumberOfDevices(); i++){
			pmtNameHash.put(pmtNames.getDevices().get(i), i+1);
		}

		for (int i = 0; i < pmtNames.getNumberOfDevices(); i++){

			startingPosHash.put(pmtNames.getDevices().get(i), i*64);
			//System.out.println(pmtNames.getDevices().get(i) + " : " + startingPosHash.get(pmtNames.getDevices().get(i)));

		}

	}

}
