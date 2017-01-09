import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * A class that is used to open and manipulate a text file within a single object.
 * This class is good for reading in text files and turning them into String arrays representing a given number of rows,
 * a row and its columns or a combination of both.
 *
 */
public class LargeFile{

	String filename;
	private FileReader fr;
	private BufferedReader br;
  private int numRows;

    /**
     * Constructor for a LargeFile object. Invoking this constructor initializes a FileReader and BufferedReader object
     * as well as initializes the name of the file.
     *
     */
	public LargeFile(String filename) throws FileNotFoundException{

		try{
			// init fields
			this.fr = new FileReader(filename);
			this.br = new BufferedReader(fr);
			this.filename = filename;
			this.numRows = this.setNumRows();
		}catch(Exception e){

			System.out.println(e.getMessage());
		}
	}

	/**
	 * Returns the number of rows the file contains. If an error occurs, this method will return -1, so it's up to the client
	 * to check if the returned value is -1.
	 *
	 * @return the number of rows in the file, -1 if an error occurs.
	 */
	public int setNumRows(){
		int count = 0;
		String row;

		try{
			FileReader fr_two = new FileReader(this.filename);
			BufferedReader br_two = new BufferedReader(fr_two);

			while((row = br_two.readLine()) != null){
				count++;
			}
			br_two.close();
			fr_two.close();
			return count;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return -1;
		}

	}

	/**
	 * Returns the number of rows in the file.
	 *
	 * @return the number of rows in the file.
	 */
	public int getNumRows(){

		return this.numRows;
	}

	/**
	 * Breaks a String of words into an array of type double, where array[row][col] corresponds to the
	 * value at (row, col). This method calls a function parseRow that takes a row
	 *
	 * @param str_data an array of Strings corresponding to a row of data.
	 * @return returns a 2D array of doubles.
	 */
	public double[] separateRow(String row){

		String[] data = new String[row.length()];
		double[] double_data;
		row = row.substring(2); // start the string two spaces up
		data = row.split("  ");	// split each word separated by "  " (two spaces)
		double_data = parseRow(data); // parse string data into double array.

		return double_data;
	}

	/**
	 * Returns the next line, ie. row, of the file as a string.
	 * This can be used to iterate through the file line by line.
	 *
	 * @return returns the next line in the file as a String.
	 */
	public String nextLine(){

		try{
			String nextLine;
			if ((nextLine = this.br.readLine()) != null){

				return nextLine;
			}
			return null;
		}catch (Exception e){

			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Parses a string into a double if possible by calling Double.parseDouble().
	 *
	 * @param string the string that you want to turn into a double.
	 * @return returns the double representation of the String passed as the parameter.
	 */
	public double parseDouble(String string){

			return Double.parseDouble(string);
	}

	/**
	 * Parses an array Strings into an array of doubles.
	 *
	 * @param row the array of Strings you wish to parse into doubles.
	 * @return returns double array representation of the Strings passes as the parameter.
	 */
	public double[] parseRow(String[] row){

		double[] data = new double[row.length];

		for (int i = 0; i < row.length; i++){

			data[i] = parseDouble(row[i]);
		}
		return data;
	}

	/**
	 * Returns an amount of rows from a file at a given starting point.
	 *
	 * @param chunkSize the amount of rows you want to get back from the file.
	 * @param startingPos the place in the file to start at. This value should start at zero if you are representing the first line in the file.
	 * @return returns an array of Strings representing the rows.
	 */
	public String[] getRowsFromFile(int chunkSize, int startingPos){

		int endingPos = startingPos + chunkSize; // find ending positon
		int counter = startingPos;
		String[] rows = new String[chunkSize]; // FIXME: Variable number of rows.
		int i = 0;

		try{
			// skip ahead some spaces
			for (int j = 0; j < startingPos; j++){
				String row = this.br.readLine();
			}
			while (counter < endingPos){
				// get rows you want
				String row = this.br.readLine();
				rows[i] = row;
				counter++;
				i++;
			}
			System.out.println(rows.length);
			return rows;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Closes the file stream.
	 */
	public void close() {

		try{
			this.br.close(); // close br
			this.fr.close(); // close fr
		}catch(Exception e){

			System.out.println(e.getMessage());
		}
	}

	/**
	 * Returns a 2D array of doubles representing a number of rows from a file at a given position in the file.
	 *
	 * @param chunkSize the amount of rows you want to get back from the file.
	 * @param startingPositon the place in the file to start at. This value should start at zero if you are representing the first line in the file.
	 * @return returns an array of doubles representing the rows and columns of information in a file.
	 */
	public double[][] getMatrixFromData(int chunkSize, int startingPositon){

		double data[][] = new double[chunkSize][];

		String[] rows = getRowsFromFile(chunkSize, startingPositon);

		for (int i = 0; i < rows.length; i++){

			data[i] = separateRow(rows[i]);
		}

		return data;
	}

	public double[][][] getMatrixFromData(int chunkSize){

		double[][][] data = new double[430][768][45];
		String[] rows = getRowsFromFile(chunkSize); //330240 rows
		double[][] split_rows = new double[chunkSize][]; //330240 x 45

		int total = 0; // index for split_rows

		// iterate through each row
		// split into 330240 x 45 array
		for(int j = 0; j < rows.length; j++){ split_rows[j] = splitRows(rows[j]); }

		// move data into array
		for(int pmt = 0; pmt < 430; pmt++){
			for(int measurement = 0; measurement < 768; measurement++){
				for (int parameter = 0; parameter < 45; parameter++){
					data[pmt][measurement][parameter] = split_rows[total][parameter];
				}
				total++;
			}
		}
		return data;
	}

	public double[] splitRows(String row){

		String[] data = new String[row.length()];
		double[] double_data;
		data = row.split(" ");	// split each word separated by " " (one space)

		double_data = parseRow(data); // parse string data into double array.

		return double_data;
	}

	public String[] getRowsFromFile(int chunkSize){

		String[] rows = new String[chunkSize];
		int i = 0;
		String row;

		try{
			while((row = this.br.readLine()) != null){
				rows[i] = row;
				i++;
			}
			return rows;
		}catch(Exception e){

			System.out.println(e.getMessage());
			return null;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		LargeFile file = new LargeFile("/Users/Andrew/Downloads/gold.dat");

	}
}
