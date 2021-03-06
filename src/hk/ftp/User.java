package hk.ftp;
 
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
public class User 
{
	int quota=0; //Quota in Kilo byte
	boolean active=false;
	String name=new String(),password=new String(),homeDir=new String();
	Hashtable<Path, String> serverPathACL=new Hashtable<Path, String>();
	Hashtable<String, String> clientPathACL=new Hashtable<String, String>();
	public Hashtable<String, String> getClientPathACL()
	{
		return clientPathACL;
	}
	public Hashtable<Path, String> getServerPathACL()
	{
		return serverPathACL;
	}
	public void addClientPathACL(String virDir,String permission)
	{
		clientPathACL.put(virDir, permission);
	}
	public void addServerPathACL(String phyDir,String permission)
	{
		serverPathACL.put(Paths.get(phyDir), permission);
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
	public String getHomeDir() 
	{
		return homeDir;
	}
	public void setHomeDir(String homeDir) 
	{
		this.homeDir = homeDir;
	}
	public int getQuota() 
	{
		return quota;
	}
	public void setQuota(int quota) 
	{
		this.quota = quota;
	}
	public boolean isActive() 
	{
		return active;
	}
	public void setActive(boolean active) 
	{
		this.active = active;
	}
	
	

}
