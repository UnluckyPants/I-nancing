package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DataAccessClass {
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private static DataAccessClass DAC = new DataAccessClass();
	
	private DataAccessClass() {}
	
	public static DataAccessClass getInstance() {
		return DAC;
	}

	public void WriteData(String fileName, String Data) {
		try {	
			//adds a new line to the end of the data string to separate data
			Data += "\n";
			//appends the new data into the specified file
			Files.write(Paths.get(fileName), Data.getBytes(), StandardOpenOption.APPEND);
			//splits the data again
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void rewriteData(String fileName, int index, String Data) {
		try {
			File inputFile = new File(fileName);
			String tempFile = "resources/data/temp.txt";
			File temp = new File(tempFile);
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String curLine = reader.readLine();
			int count = 0;
			while (curLine != null) {
				if (count == index) writer.write(Data);
				else writer.write(curLine);
				writer.newLine();
				curLine = reader.readLine();
				count++;
			}
			reader.close();
			writer.close();
			inputFile.delete();
			temp.renameTo(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initialization() throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("resources/data/AccountList.txt"))){
			//if the file is made then save an array of the account names
			String curLine = br.readLine();
			while (curLine != null) {
				String[] segmentedLine = curLine.split(",");
				commonObjs.addAccInformation(segmentedLine);
				curLine = br.readLine();
			}
        } catch (IOException e) { //if not make the file and folder
			new File("resources/data").mkdirs();
			new File("resources/data/AccountList.txt").createNewFile();
		}
		//transactions types
		try (BufferedReader br = new BufferedReader(new FileReader("resources/data/TransactionTypeList.txt"))) {
			String curLine = br.readLine();
			while (curLine != null) {
				commonObjs.addTransactionType(curLine);
				curLine = br.readLine();
			}
        } catch(IOException e) {
			new File("resources/data/TransactionTypeList.txt").createNewFile();
		}
		//transactions
		try (BufferedReader br = new BufferedReader(new FileReader("resources/data/TransactionsList.txt"))) {
			String curLine = br.readLine();
			while (curLine != null) {
				commonObjs.addTransactionInformation(curLine.split("~"));
				curLine = br.readLine();
			}
        } catch(IOException e) {
			new File("resources/data/TransactionsList.txt").createNewFile();
		}
		//schedule transactions
		try (BufferedReader br = new BufferedReader(new FileReader("resources/data/ScheduledTransactionsList.txt"))) {
			String curLine = br.readLine();
			while (curLine != null) {
				commonObjs.addScheduledTransactionInformation(curLine.split("~"));
				curLine = br.readLine();
			}
        } catch(IOException e) {
			new File("resources/data/ScheduledTransactionsList.txt").createNewFile();
		}
	}
}
