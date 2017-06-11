package planning.planner;

public class Predicate 
{
	protected String type,id,value;
	public Predicate(String type, String id, String value) 
	{
	this.type=type;
	this.id=id;
	this.value=value;
	}
	public boolean satisfies(Predicate pr)
	{
		return (type.equals(pr.type) && (id.equals(pr.id) || pr.id.equals("?")) && value.equals(pr.value));
	}
	public boolean contradicts(Predicate pr)
	{
		return (type.equals(pr.type) && id.equals(pr.id) && !value.equals(pr.value));
	}
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	@Override
	public String toString() 
	{
		return type+"-"+id+"="+value;
	}
	public boolean equals(Predicate p)
	{
		return (type.equals(p.type) && id.equals(p.id) && value.equals(p.value));
	}
	public String getType()
	{
		return type;
	}
	public String getValue()
	{
		return value;
	}
	public String id()
	{
		return id;
	}
}
