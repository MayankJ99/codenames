package Code;

public class Location {
	private String codename;
	private String Person;
	private	boolean Revealed;
	
	public Location() {
		codename = "";
		Person = "";
		Revealed = false;
	}
	
	public Location(String C, String P) {
		codename = C;
		Person = P;
	}
	
	public void setCodename(String c) {
		codename = c;
	}
		
	public String getCodename() {
		return codename;
	}
	
	public void setPerson(String p) {
		Person = p;
	}

	public String getPerson() {
		return Person;
	}

	public void setRevealed(boolean r) {
		Revealed = r;
	}
	
	public boolean getRevealed() {
		return Revealed;
	}


}
