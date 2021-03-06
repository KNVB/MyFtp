package hk.ftp.util;

import hk.ftp.Configuration;
import hk.ftp.FileManager;
import hk.ftp.UserManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.PropertyResourceBundle;

import org.apache.log4j.Logger;
public class ManagersFactory 
{
	Logger logger;
	PropertyResourceBundle bundle;
	Configuration config;
	//Class<UserManager> userManager=null;
	public ManagersFactory(PropertyResourceBundle b,Configuration c)
	{
		bundle=b;
		logger=c.getLogger();
		config=c;
	}
	public FileManager getFileManager()
	{
		FileManager fm=null;
		try 
		{
			fm = (FileManager) getManager("fileManager.classname").newInstance(this.config);
		}
		catch (IllegalArgumentException | InvocationTargetException |InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fm;
	}

	public UserManager getUserManager()
	{
		UserManager um=null;
		try 
		{
			um = (UserManager) getManager("userManager.classname").newInstance(this.config);
		}
		catch (InstantiationException |IllegalArgumentException | InvocationTargetException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return um;
	}
	private Constructor getManager(String key)
	{
		Constructor c=null;
		try
		{
			c=Class.forName(bundle.getString(key)).getConstructor(Configuration.class);
			return c;
		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) 
		{
			// TODO Auto-generated catch block
			System.out.println(e.getMessage()+" not found.");
		}
		return null;				
	}
}
