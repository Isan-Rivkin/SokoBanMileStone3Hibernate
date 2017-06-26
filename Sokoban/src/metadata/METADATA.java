package metadata;

import java.io.Serializable;

public class METADATA implements Serializable
{
	public String id;
	public METADATA(){}
	public String getId() 
    {
        return id;
    }
    public METADATA(String id)
    {
		super();
		this.id = id;
	}
	public void setId(String id)
    {
        this.id = id;
    }
}
