package ca.ualberta.backend;

import android.content.Intent;
import ca.ualberta.backend.Task;

/** Basic email class with email addresses, subject and body filled in automatically.
 * 
 * @author Peter Qin
 *
 */
public class Email {
	/** Fills in email fields (sender & recipient email addresses, subject, body).
	 *  Puts them into an intent.
	 * 
	 * @param task
	 * @return intent
	 */
	private static Intent FillEmailText(Intent intent, Task task){
		String[] emailReceipient = {task.getOwner()};
		String emailSubject;
		String taskIdMsg = task.getDescription();
		String emailBody = task.getResAnswer();
		
		//Email subject: "Task Done: task descrip..."
		//Format: "Task Done: " + first 20 characters of task description
		if (taskIdMsg.length() <= 20){
			emailSubject = "Task Done: " + taskIdMsg;
		} else {
			emailSubject = "Task Done: " + taskIdMsg.substring(0, 19);
		}
		
		
		intent.putExtra(android.content.Intent.EXTRA_EMAIL, emailReceipient);  
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);  
		intent.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
		
		return intent;
	}
	
	private static Intent AttachMedia(Intent intent, Task task){
		if (task.getReqPhoto()){
			
		}
		return intent;
	} 
	/** Parses task and creates an email from task attributes.
	 *  Lets user choose email client to use to send email.
	 * 
	 * @param  Intent - intent with info to send/call email client
	 * @param  Task - task to get text and/or media path from
	 * @author Peter Qin
	 */
	public static Intent SendEmail(Task task){
		Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		intent.setType("plain/text");
		
		//Parse task text
		intent = FillEmailText(intent, task);
		//Attach media attachments
		intent = AttachMedia(intent, task);
		
		//Choose which email client to use to handle intent
		intent = Intent.createChooser(intent, "Choose email client");
		return intent;
	}
}
