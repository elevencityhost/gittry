package com.redn.connect.jenkins.vo;

public class Application {
	
	String name;
	
	String domain;
	
	String state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Application [name=" + name + ", domain=" + domain + ", state=" + state + "]";
	}

	
}
