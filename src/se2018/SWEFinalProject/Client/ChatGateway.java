package se2018.SWEFinalProject.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import se2018.SWEFinalProject.Server.Story;

public class ChatGateway implements se2018.SWEFinalProject.Chat.ChatConstants {

    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private TextArea textArea;

    // Establish the connection to the server.
    public ChatGateway(TextArea textArea) {
        this.textArea = textArea;
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an output stream to send data to the server
            outputToServer = new PrintWriter(socket.getOutputStream());

            // Create an input stream to read data from the server
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Exception in gateway constructor: " + ex.toString() + "\n"));
        }
    }

    // Start the chat by sending in the user's handle.
    public void sendHandle(String handle) {
        outputToServer.println(SEND_HANDLE);
        outputToServer.println(handle);
        outputToServer.flush();
    }

    // Send a new comment to the server.
    public void sendComment(String comment) {
        outputToServer.println(SEND_COMMENT);
        outputToServer.println(comment);
        outputToServer.flush();
    }
    
    // send a new user story to server
    public void sendStory(String story) {
    	outputToServer.println(SEND_STORY);
    	outputToServer.println(story);
    	outputToServer.flush();
    	System.out.println("in gateway: " + story);
    }
    
    public Story getStory(int id) {
    	outputToServer.println(GET_STORY);
    	// add logic to select a story
    	outputToServer.println(Integer.toString(id));
    	outputToServer.flush();
    	String storyJSON = "";
    	try {
    		System.out.println("expecting input from server");
			storyJSON = inputFromServer.readLine();
    		System.out.println("after input from server");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			storyJSON = "Error Fetching Story: " + e;
            Platform.runLater(() -> System.out.println("final"));

		}
    	
    	System.out.println("getting story in gateway: " + storyJSON);
    	ObjectMapper mapper = new ObjectMapper();
  	  	Story s = null;
		try {
			s = mapper.readValue(storyJSON, se2018.SWEFinalProject.Server.Story.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
  	  	
    	return s;
    }

    // Ask the server to send us a count of how many comments are
    // currently in the transcript.
    public int getCommentCount() {
        outputToServer.println(GET_COMMENT_COUNT);
        outputToServer.flush();
        int count = 0;
        try {
            count = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getCommentCount: " + ex.toString() + "\n"));
        }
        return count;
    }

    // Fetch comment n of the transcript from the server.
    public String getComment(int n) {
        outputToServer.println(GET_COMMENT);
        outputToServer.println(n);
        outputToServer.flush();
        String comment = "";
        try {
            comment = inputFromServer.readLine();
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getComment: " + ex.toString() + "\n"));
        }
        return comment;
    }
    

}