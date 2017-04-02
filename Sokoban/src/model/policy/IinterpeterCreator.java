package model.policy;

import java.io.Serializable;

public interface IinterpeterCreator extends Serializable {
	public Iinterpeter loadInterpeter(String path);
	public void saveInterpeter(Iinterpeter i,String path);
}
