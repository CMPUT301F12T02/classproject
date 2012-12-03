package ca.ualberta.backend;

import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;
import android.content.Context;

/* This is what the JUnit test case would look like for the LocalTaskManager if it
 * would run properly. The reason it won't work is because it needs the context from
 * the Android application in order to know where exactly it can write files to; the
 * root directory, among others, are read-only. Additionally, the operations pertaining
 * to LocalTasks and Drafts are exactly the same (the two public methods call the same
 * private method) so there is only need to test one of them.
 */
public class LocalTaskManagerTest extends TestCase {
	private Task task1;
	private Task task2;
	private Task task3;
	
	private ArrayList<Task> tasks123;
	private ArrayList<Task> tasks13;
	
	private Context context;
	
	protected void setUp() {
		task1 = new Task("John Doe", "description 1", true);
		task2 = new Task("John Smith", "description 2", true);
		task3 = new Task("John Doe", "description 3", false);
		
		tasks123.add(task1);
		tasks123.add(task2);
		tasks123.add(task3);
		
		tasks13.add(task1);
		tasks13.add(task3);
		
		context = null;
	}
	
	public void testSavingTask() {
		LocalTaskManager.saveDraft(task1, context);
		LocalTaskManager.saveDraft(task2, context);
		LocalTaskManager.saveDraft(task3, context);
		
		ArrayList<Task> retrievedTasks = LocalTaskManager.loadDrafts(context);
		
		for (int i = 0; i < 3; i++) {
			Task createdTask = tasks123.get(i);
			Task retrievedTask = retrievedTasks.get(i);
			Assert.assertTrue(createdTask.isEqualTo(retrievedTask));
		}
	}
	
	public void testDeletingTask() {
		LocalTaskManager.saveDraft(task1, context);
		LocalTaskManager.saveDraft(task2, context);
		LocalTaskManager.saveDraft(task3, context);
		
		LocalTaskManager.deleteDraft(task2, context);
		
		ArrayList<Task> retrievedTasks = LocalTaskManager.loadDrafts(context);
		
		for (int i = 0; i < 2; i++) {
			Task createdTask = tasks13.get(i);
			Task retrievedTask = retrievedTasks.get(i);
			Assert.assertTrue(createdTask.isEqualTo(retrievedTask));
		}
	}
	
	public void testReplacingTask() {
		LocalTaskManager.saveDraft(task1, context);
		LocalTaskManager.saveDraft(task2, context);
		
		LocalTaskManager.replaceDraft(task2, task3, context);
		
		ArrayList<Task> retrievedTasks = LocalTaskManager.loadDrafts(context);
		
		for (int i = 0; i < 2; i++) {
			Task createdTask = tasks13.get(i);
			Task retrievedTask = retrievedTasks.get(i);
			Assert.assertTrue(createdTask.isEqualTo(retrievedTask));
		}
	}
}
