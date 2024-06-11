package com.springboot.processors.entities;

public class SerializationExample {
	
	private String name;
    private int value;

    public SerializationExample(String name, int value) {
        this.name = name;
        this.value = value;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SerializationExample [name=" + name + ", value=" + value + "]";
	}
    
    
}
