package datamodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;


/**
 * This class is a Singleton
 *
 */
public class TodoData {
	
	private static TodoData instance = new TodoData();
	private static String fileName = "TodoListItems.txt";
	
	private List<TodoItem> todoItems;
	private DateTimeFormatter formatter;
	
	// it's via this unique static method, that an external class
	// will get the unique instance of this Singleton
	public static TodoData getInstance(){
		return instance;
	}
	
	// the private constructor, that makes the class non-instantiable
	private TodoData(){
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}
	
	public void setTodoItems(List<TodoItem> todoItems){
		this.todoItems = todoItems;
	}
	
	public void loadTodoItems() throws IOException{
		
		todoItems = FXCollections.observableArrayList();
		Path path = Paths.get(fileName);
		BufferedReader br = Files.newBufferedReader(path);
		
		String input;
		
		try{
			while((input = br.readLine()) != null){
				
				String[] itemPieces = input.split("\t");
				
				String shortDescription = itemPieces[0];
				String details = itemPieces[1];
				String dateString = itemPieces[2];
				
				LocalDate date = LocalDate.parse(dateString, formatter);
				TodoItem todoItem = new TodoItem(shortDescription, details, date);
				todoItems.add(todoItem);
			}
			
			
		}finally{
			if(br != null){
				br.close();
			}
		}
	}
	
	public void storeTodoItems() throws IOException{
		
		Path path = Paths.get(fileName);
		BufferedWriter bw = Files.newBufferedWriter(path);
		
		// FIXME : define properly TodoItem class
		try{
			Iterator<TodoItem> iterator = todoItems.iterator();
			while(iterator.hasNext()){
				TodoItem item = iterator.next();
				bw.write(String.format("%s\t%s\t%s", 
						null,
						null,
						null));
				
				
			}
		}finally{
			if(bw != null){
				bw.close();
			}
		}
	}
	
	
	

}
