package Code;

public class Entry {

	private String TeamInitial;
	
	private String Team;
	
	private Entry next;
	
	
	public Entry(String _init, String _team) {
		this.setInitial(_init);
	}
	

	public Entry getNext() {
	
		return this.next;
	}
	
	
	public void setNext(Entry e) {
			this.next = e;
	}
	
	public void setInitial(String _init) {
		this.TeamInitial = _init;
		
	}
	
	public String getInitial() {
		return this.TeamInitial;
		
	}
	
	public void setTeam(String _team) {
		this.Team = _team;
	}
	
	public String getTeam() {
		return this.Team;
	}
	
	
}
