package info.simplecloud.scimproxy.compliance;

import java.util.ArrayList;


public class Schema {
	
	// TODO: add support for subAttributes and canonicalValues
	
	
	private ArrayList<String> required = new ArrayList<String>();

	public void setRequired(ArrayList<String> required) {
		this.required = required;
	}

	public ArrayList<String> getRequired() {
		return required;
	}
	
	public void addItem(String s) {
		this.required.add(s);
	}
	
}
