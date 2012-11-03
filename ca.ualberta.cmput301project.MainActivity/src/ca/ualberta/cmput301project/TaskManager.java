package ca.ualberta.cmput301project;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
	
	//saves a local task to file
	public void saveLocalTask(Task task) {
		String file = "localtask";
		saveToFile(task, file);
	}
	
	//saves a draft to file
	public void saveDraft(Task task) {
		String file = "draft";
		saveToFile(task, file);
	}
	
	//opens a file and appends a serialized object to it
	private void saveToFile(Task task, String file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
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
	public List<Task> loadLocalTasks() {
		String file = "localtask";
		return readFromFile(file);
	}
	
	//retrieves a list of all saved drafts
	public List<Task> loadDrafts() {
		String file = "draft";
		return readFromFile(file);
	}
	
	//opens a file and reads all serialized objects in it
	private List<Task> readFromFile(String file) {
		List<Task> tasks = new ArrayList<Task>();
		
		try {
			FileInputStream fin = new FileInputStream(file);
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
	
	public void sendCommunityTask() { //JSON
		
	}
	
	public void loadListOfTasks() { //JSON
		
	}
	
	public void loadSingleTask() { //JSON
		
	}
}