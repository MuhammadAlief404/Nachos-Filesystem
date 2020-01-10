package nachos.proj1;

public class Task implements Runnable {
	private String name;
	private String desc;
	private String type;
	private String status;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Task(String name, String desc, String type, String status) {
		super();
		this.name = name;
		this.desc = desc;
		this.type = type;
		this.status = status;
	}
	@Override
	public void run() {
		setStatus("Done");
		System.out.println("Task with title: " + getName() + "has been mark as 'Done'");
	}
	
}
