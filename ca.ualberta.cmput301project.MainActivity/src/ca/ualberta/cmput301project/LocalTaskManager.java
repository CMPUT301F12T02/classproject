package ca.ualberta.cmput301project;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LocalTaskManager {
	
	//saves a local task to file
	public static void saveLocalTask(Task task) {
		String filename = "localtask";
		saveToFile(task, filename);
	}
	
	//saves a draft to file
	public static void saveDraft(Task task) {
		String filename = "draft";
		saveToFile(task, filename);
	}
	
	//opens a file and appends a serialized object to it
	private static void saveToFile(Task task, String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(task);
			oos.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//retrieves a list of all local tasks
	public static List<Task> loadLocalTasks() {
		String filename = "localtask";
		return readFromFile(filename);
	}
	
	//retrieves a list of all saved drafts
	public static List<Task> loadDrafts() {
		String filename = "draft";
		return readFromFile(filename);		//compare all tasks to the input task
		//write all tasks back except the one that matches input task
	}
	
	//opens a file and reads all serialized objects in it
	private static List<Task> readFromFile(String filename) {
		List<Task> tasks = new ArrayList<Task>();
		
		try {
			FileInputStream fin = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fin);
			
			//keeps reading from the file until the EOF is hit, at which point
			//all objects have been read
			while (true) {
				try {
					tasks.add((Task) ois.readObject());
				} catch (EOFException eofe) {
					break;
				}
			}
			
			ois.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		
		return tasks;
	}
	
	public static void deleteLocalTask(Task task) {
		String filename = "localtask";
		deleteTask(task, filename);
	}
	
	public static void deleteDraft(Task task) {
		String filename = "draft";
		deleteTask(task, filename);
	}
	
	private static void deleteTask(Task task, String filename) {
		List<Task> tasks = readFromFile(filename);
		
		File file = new File(filename);
		file.delete();
		
		for (Task taskInList : tasks) {
			if (taskInList.equals(task) == false) {
				saveToFile(taskInList, filename);
			}
		}
	}
	
}