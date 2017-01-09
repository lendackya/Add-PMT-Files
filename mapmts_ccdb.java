import java.util.HashMap;
import java.util.Vector;
import org.jlab.ccdb.CcdbPackage;
import org.jlab.ccdb.JDBCProvider;
import org.jlab.ccdb.SQLiteProvider;
import org.jlab.ccdb.MySqlProvider;
import org.jlab.ccdb.Assignment;
import org.jlab.ccdb.*;

public class mapmts_ccdb{


	public static void main (String[] args){

		HashMap<String, Integer> pmtNameHash = new HashMap<String, Integer>();
		HashMap<String, Integer> parameterHash = new HashMap<String, Integer>();

		if (args.length < 4){
			System.out.println("Insufficient command line arguements.");
		}else{

					initHashMaps(pmtNameHash, parameterHash);

					String tablePathName = "/test/rich/ca7452";
					String connectionStr = "mysql://clas12reader@clasdb.jlab.org/clas12";

					JDBCProvider provider = CcdbPackage.createProvider(connectionStr);
					provider.connect();

					if (provider.getIsConnected()){
						System.out.println("Connection to : " + connectionStr + ".");
					}

					String richNum = args[0].toUpperCase();
					String pmtName = args[1].toUpperCase();
					String pixelNumber = args[2].toUpperCase();
					String parameter = args[3].toUpperCase();

					Integer sectorNum = Integer.parseInt(richNum);
					System.out.println("Sector: " + sectorNum);

					Integer pmtNum = pmtNameHash.get(pmtName);
					System.out.println("Layer: " + pmtNum);

					Integer pixelNum = Integer.parseInt(pixelNumber);
					System.out.println("Component: " + pixelNum);

					Integer colNum = parameterHash.get(parameter);
					System.out.println("Column: " + colNum);

					// get the data for the mapmt table
					Assignment asgmt = provider.getData(tablePathName);

					// get the column value from the hased value
					Vector<String> data  = asgmt.getColumnValuesString(colNum);

					System.out.println(data.size());

		}
	}


	private static void initHashMaps(HashMap<String, Integer> pmtNameHash, HashMap<String, Integer> parameterHash){

		DeviceNameFile pmtNames = new DeviceNameFile("files/pmts_db.txt");
		ParameterFile paraFile = new ParameterFile("files/parameters.txt");

		for (int i = 0; i < paraFile.getNumberOfParameters(); i++ ){
			parameterHash.put(paraFile.getParameters().get(i), i+1);

		}


		for (int i = 0; i < pmtNames.getNumberOfDevices(); i++){
			pmtNameHash.put(pmtNames.getDevices().get(i), i+1);
		}
	}

}
