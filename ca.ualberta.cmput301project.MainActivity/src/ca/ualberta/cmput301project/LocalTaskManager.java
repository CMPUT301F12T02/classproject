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

import android.content.Context;

public class LocalTaskManager {
	
	//saves a local task to file
	public static void saveLocalTask(Task task, Context context) {
		String filename = "/localtask";
		saveToFile(task, context, filename);
	}
	
	//saves a draft to file
	public static void saveDraft(Task task, Context context) {
		String filename = "/draft";
		saveToFile(task, context, filename);
	}
	
	//opens a file and appends a serialized object to it
	private static void saveToFile(Task task, Context context, String filename) {
		try {
			ArrayList<Task> tasks = readFromFile(context, filename);
			tasks.add(task);
			
			File file = new File(context.getFilesDir().getPath().toString() + filename);
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			for (Task taskInList: tasks) {
				oos.writeObject(taskInList);
			}
			
			oos.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//retrieves a list of all local tasks
	public static ArrayList<Task> loadLocalTasks(Context context) {
		String filename = "/localtask";
		return readFromFile(context, filename);
	}
	
	//retrieves a list of all saved drafts
	public static ArrayList<Task> loadDrafts(Context context) {
		String filename = "/draft";
		return readFromFile(context, filename);
	}
	
	//opens a file and reads all serialized objects in it
	private static ArrayList<Task> readFromFile(Context context, String filename) {
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		try {
			File file = new File(context.getFilesDir().getPath().toString() + filename);
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
	
	//deletes a local task from the file
	public static void deleteLocalTask(Task task, Context context) {
		String filename = "/localtask";
		deleteTask(task, context, filename);
	}
	
	//deletes a draft from the file
	public static void deleteDraft(Task task, Context context) {
		String filename = "/draft";
		deleteTask(task, context, filename);
	}
	
	//retrieves the list of all tasks in a file, and then writes back all tasks
	//except the one that is equivalent to the task passed in by the user
	private static void deleteTask(Task task, Context context, String filename) {
		ArrayList<Task> tasks = readFromFile(context, filename);
		
		try {
			File file = new File(context.getFilesDir().getPath().toString() + filename);
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			for (Task taskInList: tasks) {
				if (taskInList.equals(task) == false) {
					oos.writeObject(taskInList);
				}
			}
			
			oos.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}