package nachos.proj1;

import nachos.machine.Machine;
import nachos.machine.SerialConsole;
import nachos.threads.Semaphore;

public class MyConsole {
	
	SerialConsole console = Machine.console();
	Semaphore semaphore = new Semaphore(0);

	public MyConsole() {
		Runnable handler = new Runnable() {
			
			@Override
			public void run() {
				semaphore.V();
				
			}
		};
		console.setInterruptHandlers(handler, handler);
	}
	
	public void print(String string) {
		for (int i = 0; i < string.length(); i++) {
			console.writeByte(string.charAt(i));
			semaphore.P();
		}
	}
	
	public void printLn(String string) {
		print(string+'\n');
	}
	
	public String scan() {
		char input = 0;
		String string = "";
		do {
			semaphore.P();
			input = (char) console.readByte();
			if(input != '\n') {
				string += input;
			}
		}while(input != '\n');
		return string;
	}
	
	public int scanInt() {
		return Integer.parseInt(scan());
	}

}
