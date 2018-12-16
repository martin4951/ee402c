package ee402a;

import java.io.*;			// contains the Serializable interface

public class Fish implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String type;

	public Fish(String name, String type)
	{
		this.name = name;
		this.type = type;
	}

	public String getName() { return this.name;	}

	public String getType() { return this.type;	}
}