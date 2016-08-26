package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
	
	// By using the @FXML annotation we link the Controllers field to what's declared in sample.fxml 
	@FXML
	private TextField nameField;
	@FXML
	private Button helloButton;
	@FXML
	private Button byeButton;
	@FXML
	private CheckBox ourCheckBox;
	@FXML
	private Label ourLabel;
	
	// EventHandler for click on the button (either button)
	@FXML
	public void onButtonClicked(ActionEvent e){
		
		if(e.getSource().equals(helloButton)){
			System.out.println("Hello, " + nameField.getText());
		}else if(e.getSource().equals(byeButton)){
			System.out.println("Bye, " + nameField.getText());
		}
		
		// We create an anonymous Runnable class : a class that is defines and created 
		// in the same block, and it's only used once
		// Any class that implements Runnable, means that is gonna be executed in another Thread
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				
				try{
					
					String s = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
					System.out.println("I'm going to sleep on the: " + s);
					// this action occurs in the background Thread
					Thread.sleep(10000);
					
					// by using of this class we indicate that once the action is finished
					// in the background thread, the modification of the scene graph will be 
					// done in the UI-Thread, to which this method passes the information
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							String s = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
							System.out.println("I'm updating the label on the : " + s);
							ourLabel.setText("Hey I'm back!!");
						}
					});
					
				}catch(InterruptedException event){
					// we don't care about this
				}
			}
		};
		
		// we invoke the Runnable class, here is where we actually use it
		new Thread(task).start();
		
		if(ourCheckBox.isSelected()){
			nameField.clear();
			helloButton.setDisable(true);
			byeButton.setDisable(true);
		}
	}
	
	@FXML
	public void handleKeyReleased() {
		String text = nameField.getText();
		boolean disableButtons = text.isEmpty() || text.trim().isEmpty();
		helloButton.setDisable(disableButtons);
		byeButton.setDisable(disableButtons);
	}
	
	public void handleChange() {
        System.out.println("The checkbox is " + (ourCheckBox.isSelected() ? "checked" : "not checked"));
    }
	
}
