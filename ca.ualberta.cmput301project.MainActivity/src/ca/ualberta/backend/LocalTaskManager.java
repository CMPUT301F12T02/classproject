package ca.ualberta.backend;

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

/** The purpose of this class is to enable the saving, loading, and deleting of
 * tasks on the local storage. The only issue is that the caller has to supply
 * the context, which is used to find the absolute directory path to where the
 * application can write. If the context wasn't supplied, the methods below
 * would try to write to the root directory, which is read-only and would throw
 * exceptions. I chose to hide the constructor and make the methods static
 * because this class consists only of helper methods; it wouldn't make sense
 * to instantiate a LocalTaskManager object.
 * 
 * @author Mircea Gabriel Caciula
 * @version 1.0
 */
public class LocalTaskManager {
	private static String localtaskFilename = "/localtask";
	private static String draftFilename = "/draft";
	private static String favouriteFilename = "/favourite";
	
	private LocalTaskManager() {}
	
	/** Saves a local task to file
	 * 
	 *  @param task					The task that the user wishes to save
	 *  @param context				The context of the application, so the method knows the directory path
	 */
	public static void saveLocalTask(Task task, Context context) {
		saveToFile(task, context, localtaskFilename);
	}
	
	/** Saves a draft to file
	 * 
	 * @param task					The task that the user wishes to save
	 * @param context				The context of the application, so the method knows the directory path
	 */
	public static void saveDraft(Task task, Context context) {
		saveToFile(task, context, draftFilename);
	}
	
	/** Saves a favourite task to file
	 * 
	 * @param task					The task that the user wishes to save
	 * @param context				The context of the application, so the method knows the directory path
	 */
	public static void saveFavourite(Task task, Context context) {
		saveToFile(task, context, favouriteFilename);
	}
	
	/** Obtains the list of all tasks saved to file, appends the user inputted task
	 * to the list, and then saves the resulting list back to file
	 * 
	 * @param task					The task that the user wishes to save
	 * @param context				The context of the application, so the method knows the directory path
	 * @param filename				The name of the file being saved to
	 */
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
	
	/** Retrieves a list of all local tasks
	 * 
	 * @param context				The context of the application, so the method knows the directory path
	 * @return						An array list of all local tasks
	 */
	public static ArrayList<Task> loadLocalTasks(Context context) {
		return readFromFile(context, localtaskFilename);
	}
	
	/** Retrieves a list of all saved drafts
	 * 
	 * @param context				The context of the application, so the method knows the directory path
	 * @return						An array list of all saved drafts
	 */
	public static ArrayList<Task> loadDrafts(Context context) {
		return readFromFile(context, draftFilename);
	}
	
	/** Retrieves a list of all favourite tasks
	 * 
	 * @param context				The context of the application, so the method knows the directory path
	 * @return						An array list of all favourite tasks
	 */
	public static ArrayList<Task> loadFavourites(Context context) {
		return readFromFile(context, favouriteFilename);
	}
	
	/** Opens a file and reads all serialized objects in it
	 * 
	 * @param context				The context of the application, so the method knows the directory path
	 * @param filename				The name of the file being loaded from
	 * @return						An array list of all tasks saved to the file specified by filename
	 */
	private static ArrayList<Task> readFromFile(Context context, String filename) {
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		try {
			File file = new File(context.getFilesDir().getPath().toString() + filename);
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
			
			//keeps reading from the file until the EOF is hit, at which point all objects have been read
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
	
	/** Deletes a local task from the file
	 * 
	 * @param task					The local task that the user wishes to delete
	 * @param context				The context of the application, so the method knows the directory path
	 */
	public static void deleteLocalTask(Task task, Context context) {
		deleteTask(task, context, localtaskFilename);
	}
	
	/** Deletes a draft from the file
	 * 
	 * @param task					The draft that the user wishes to delete
	 * @param context				The context of the application, so the method knows the directory path
	 */
	public static void deleteDraft(Task task, Context context) {
		deleteTask(task, context, draftFilename);
	}
	
	/** Deletes a favourite task from the file
	 * 
	 * @param task					The favourite task that the user wishes to delete
	 * @param context				The context of the application, so the method knows the directory path
	 */
	public static void deleteFavourite(Task task, Context context) {
		deleteTask(task, context, favouriteFilename);
	}
	
	/** Retrieves the list of all tasks in a file, and then writes back all tasks
	 * except the one that is equivalent to the task passed in by the user
	 * 
	 * @param task					The task that the user wishes to delete
	 * @param context				The context of the application, so the method knows the directory path
	 * @param filename				The name of the file from which the task is being deleted from
	 */
	private static void deleteTask(Task task, Context context, String filename) {
		ArrayList<Task> tasks = readFromFile(context, filename);
		
		try {
			File file = new File(context.getFilesDir().getPath().toString() + filename);
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			for (Task taskInList: tasks) {
				if (taskInList.isEqualTo(task) == false) {
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
	
	/** Allows the "saving over" of an old local task with its updated version, keeping its same
	 * position in the local task file
	 * 
	 * @param oldTask				The old local task that is being overwritten
	 * @param newTask				The new local task that is overwriting its older version
	 * @param context				The context of the application, so the method knows the directory path
	 */
	public static void replaceLocalTask(Task oldTask, Task newTask, Context context) {
		replaceTask(oldTask, newTask, context, localtaskFilename);
	}
	
	/** Allows the "saving over" of an old draft with its updated version, keeping its same
	 * position in the draft file
	 * 
	 * @param oldTask				The old draft that is being overwritten
	 * @param newTask				The new draft that is overwriting its older version
	 * @param context				The context of the application, so the method knows the directory path
	 */
	public static void replaceDraft(Task oldTask, Task newTask, Context context) {
		replaceTask(oldTask, newTask, context, draftFilename);
	}
	
	/** Allows the "saving over" of an old favourite task with its updated version, keeping its same
	 * position in the favourite file
	 * 
	 * @param oldTask				The old favourite task that is being overwritten
	 * @param newTask				The new favourite task that is overwriting its older version
	 * @param context				The context of the application, so the method knows the directory path
	 */
	public static void replaceFavourite(Task oldTask, Task newTask, Context context) {
		replaceTask(oldTask, newTask, context, favouriteFilename);
	}
	
	/** Retrieves the list of all tasks in a file, and then writes back all tasks to the file. Once the
	 * task specified by oldTask is found, though, the task specified by newTask is written in its place
	 * 
	 * @param oldTask				The old task that is being overwritten
	 * @param newTask				The new task that is overwriting its older version
	 * @param context				The context of the application, so the method knows the directory path
	 * @param filename				The name of the file that the task is being overwritten to
	 */
	private static void replaceTask(Task oldTask, Task newTask, Context context, String filename) {
		ArrayList<Task> tasks = readFromFile(context, filename);
		
		try {
			File file = new File(context.getFilesDir().getPath().toString() + filename);
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			for (Task taskInList: tasks) {
				if (taskInList.isEqualTo(oldTask) == false) {
					oos.writeObject(taskInList);
				} else {
					oos.writeObject(newTask);
				}
			}
			
			oos.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static boolean existsLocalTask(Task task, Context context) {
		return existsTask(task, context, localtaskFilename);
	}
	
	public static boolean existsDraft(Task task, Context context) {
		return existsTask(task, context, draftFilename);
	}
	
	public static boolean existsFavourite(Task task, Context context) {
		return existsTask(task, context, favouriteFilename);
	}
	
	public static boolean existsTask(Task task, Context context, String filename) {
		ArrayList<Task> tasks = readFromFile(context, filename);
		
		for (Task taskInList: tasks) {
			if (task.isEqualTo(taskInList)) {
				return true;
			}
		}
		
		return false;
	}
}