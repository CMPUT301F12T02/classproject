package ca.ualberta.backend;

import android.content.Intent;
import ca.ualberta.backend.Task;

//http://mobile.tutsplus.com/tutorials/android/android-email-intent/
public class Email {
	public static Intent CreateEmailIntent(Task task){
		String[] emailReceipient = {task.getOwner()};
		String emailSubject;
		String taskIdMsg = task.getDescription();
		String emailBody = task.getResAnswer();
		
		//Email subject: "Task Done: task descrip..."
		if (taskIdMsg.length() <= 20){
			emailSubject = "Task Done: " + taskIdMsg;
		} else {
			emailSubject = "Task Done: " + taskIdMsg.substring(0, 19);
		}
		
		Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		intent.setType("plain/text");
		intent.putExtra(android.content.Intent.EXTRA_EMAIL, emailReceipient);  
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);  
		intent.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
		
		return intent;
	}
}
