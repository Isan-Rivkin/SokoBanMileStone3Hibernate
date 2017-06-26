package metadata;

import java.io.Serializable;

public class METADATA implements Serializable
{
	public String id;
	public String additionalInfo;
	
	public METADATA(){}
	public String getId() 
    {
        return id;
    }
    public String getAdditionalInfo() 
    {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) 
	{
		this.additionalInfo = additionalInfo;
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
