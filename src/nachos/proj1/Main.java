package nachos.proj1;

import java.util.Vector;

import nachos.machine.FileSystem;
import nachos.machine.Machine;
import nachos.machine.OpenFile;
import nachos.threads.KThread;

public class Main {
	
	MyConsole console = new MyConsole();
	Vector<Task> tasks = new Vector<>();

	public Main() {
		readFile();
		int choose = 0;
		do {
			console.printLn("1. Insert Task");
			console.printLn("2. View Task");
			console.printLn("3. Complete Task");
			console.printLn("4. Exit");
			try {
				choose = console.scanInt();	
			} catch (Exception e) {
				choose = 0;
			}
			switch(choose) {
			case 1 :
				insert();
				break;
			case 2 :
				listView();
				break;
			case 3 :
				complateTask();
				break;
			case 4 :
				console.print("Ticks of time: " + Machine.timer().getTime()/10000000 + "second(s)");
				writeFile();
				break;
			}
		}while(choose != 4);
		
	}
	
	public void insert() {
		String name = "";
		String desc = "";
		String type = "";
		do {
			console.print("Input Task [5 - 15] :");
			name = console.scan();
		}while(name.length() < 5 || name.length() > 15);
		do {
			console.print("Input Task Description [More than 1 word] : ");
			desc = console.scan();
		} while(desc.length() <= 1);
		do {
			console.print("Input task type [Important | Unimportant] : ");
			type = console.scan();
		} while(!type.equals("Important") && !type.equals("Unimportant"));
		console.printLn("Task have been add sucsesfully");
		tasks.add(new Task(name, desc, type, "Not Done"));
	}
	
	public void listView() {
		console.printLn("Task List");
		if (tasks.isEmpty()) {
			console.printLn("There is no Tasks");
		} else {
			for (int i = 0; i < tasks.size(); i++) {
				console.printLn("No. "+i);
				console.printLn("Name : "+tasks.get(i).getName());
				console.printLn("Umur : "+tasks.get(i).getDesc());
				console.printLn("Type : "+tasks.get(i).getType());
				console.printLn("Type : "+tasks.get(i).getStatus()+"\n");
			}
		}
	}
	
	public void complateTask() {
		Vector<Task> tasksNotDone = new Vector<>();
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getStatus() == "Not Done") {
				tasksNotDone.add(tasks.get(i));
			}
		}
		if(tasksNotDone.isEmpty()) {
			console.printLn("There is no task");
		} else {
			for (int i = 0; i < tasksNotDone.size(); i++) {
				console.printLn("No. "+i);
				console.printLn("Name : "+tasksNotDone.get(i).getName());
				console.printLn("Umur : "+tasksNotDone.get(i).getDesc());
				console.printLn("Type : "+tasksNotDone.get(i).getType());
				console.printLn("Type : "+tasksNotDone.get(i).getStatus()+"\n");
			}
			int choose = 0;
			console.print("Input task number [1..] : ");
			choose = console.scanInt();
			Task task = tasksNotDone.get(choose - 1);
			new KThread(task).fork();
		}
	}
	
	public void writeFile() {
		FileSystem fileSystem = Machine.stubFileSystem();
		OpenFile file = fileSystem.open("task.txt", true);
		String allString = "";
		for (int i = 0; i < tasks.size(); i++) {
			allString += tasks.get(i).getName()+"#"+tasks.get(i).getDesc()+"#"+tasks.get(i).getType()+"#"+tasks.get(i).getStatus()+"\n";
		}
		byte []arr = allString.getBytes();
		file.write(arr, 0, arr.length);
	}
	
	public void readFile() {
		FileSystem fileSystem = Machine.stubFileSystem();
		OpenFile file = fileSystem.open("task.txt", false);
		if(file == null) {
			
		}else {
			byte []allByte = new byte[9999];
			file.read(allByte, 0, file.length());
			String allString = new String(allByte);
			String []string = allString.split("\n");
			for (int i = 0; i < string.length-1; i++) {
				String []attr = string[i].split("#");
				tasks.add(new Task(attr[0], attr[1], attr[2], attr[3]));
			}	
		}
		
	}
}
