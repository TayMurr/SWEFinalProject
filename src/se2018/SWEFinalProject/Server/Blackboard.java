package se2018.SWEFinalProject.Server;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Hashtable;

public class Blackboard implements Externalizable {
	
	Integer totStories = 0;
	Hashtable<Integer, Story> productBacklog;
	Hashtable<Integer, Story> sprintBacklog;
	Hashtable<Integer, Story> stories;
	Integer time = 0;
	Hashtable<Integer, Integer> burndown;
	
	public Blackboard() {
		productBacklog = new Hashtable <Integer, Story>();
		sprintBacklog = new Hashtable <Integer, Story>();
		stories = new Hashtable <Integer, Story>();
		burndown = new Hashtable<Integer, Integer>();
	}
	
	public synchronized void addStory(Story story) {
		// add story to hashtable and increment count
		stories.put(totStories, story);
		story.setStoryID(totStories);
		totStories = totStories + 1;
	}
	
	public synchronized void deleteStory(Integer storyId) {
		// add story to hashtable and decrement count
		stories.remove(storyId);
		totStories = totStories - 1;
	}
	
	public synchronized void editStory(Integer storyId, Story eStory) {
		Story oldStory = stories.get(storyId);
		oldStory.setAuthor(eStory.getAuthor());
		oldStory.setDesc(eStory.getDescription());
		oldStory.setStatus(eStory.getStatus());
		oldStory.setStoryPoints(eStory.getStoryPoints());
	}
	
	public synchronized Hashtable <Integer, Story> getStories() {
		return stories;
	}
	
	public synchronized Story getStory(Integer id) {
		System.out.println("in blackboard class: " +stories.get(id));

		return stories.get(id);
	}

	@Override
	public String toString() {
		return null;
		
	}
	// save the sprint
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		for (Story s: stories.values()) {
			out.writeUTF(s.toString_());
		}
		
		
	}
	// upload the sprint
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		Story story;
		String storyStr = in.readLine();
		
		while (storyStr != null) {
			String[] fields = storyStr.split(",");
   		  	Integer storyID = Integer.parseInt(fields[0]);
   		  	String author = fields[1];
   		  	String title = fields[2];
   		  	String desc = fields[3];
   		  	String status = fields[4];
   		  	Integer storyPoints = Integer.parseInt(fields[5]);
   		  	story = new Story(storyID, author, title, desc,  storyPoints);
   		  	story.setStatus(status);
   		  	stories.put(story.getStoryID(), story);
   		  	storyStr = in.readLine();
		}
		
	}
	
	public void completeStory(int points) {
		// update points of finishing story over time 
		burndown.put(time, points);
		time += 1;
	}
	
	public Hashtable<Integer, Integer> getBurndown() {
		return burndown;
	}
}

