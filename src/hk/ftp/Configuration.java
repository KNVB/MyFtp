package hk.ftp;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.Locale;

import hk.ftp.util.ManagersFactory;
import java.util.PropertyResourceBundle;


public class Configuration 
{
	private int serverPort;
	private FileInputStream fis=null;
	private org.apache.log4j.Logger logger=null;
	private String configFile = "conf/server-config",encoding=null;
	private String ftpMessageLocale=new String();
	private ManagersFactory mf=null;
	
	protected Stack<Integer> passivePorts=new Stack<Integer>();
	protected boolean supportPassiveMode=false,havePassivePortSpecified=false;
	private UserManager userManager=null;
	private FileManager fileManager=null;
	private int maxConnection=0,clientConnectionTimeOut=30000;
	
	private FtpMessage ftpMessage;
	public Configuration(org.apache.log4j.Logger l)
	{
		logger=l;
	}
	public void close()
	{
		userManager.close();
		fileManager.close();
	}
	public boolean load()
	{
		int i,startPort,endPort;
		String start,end;
		boolean result=false;
		try 
		{
			fis=new FileInputStream(configFile);
			PropertyResourceBundle bundle = new PropertyResourceBundle(fis);
			logger.info("FTP Server Configuration file is loaded");
			serverPort=(Integer.parseInt(bundle.getString("port")));
			maxConnection=Integer.parseInt(bundle.getString("maxConnection"));
			encoding=bundle.getString("encoding");
			ftpMessageLocale=bundle.getString("ftpMessageLocale");
			ftpMessage=new FtpMessage(new Locale(ftpMessageLocale));
			clientConnectionTimeOut=Integer.parseInt(bundle.getString("clientConnectionTimeOut"));
			supportPassiveMode=Boolean.parseBoolean(bundle.getString("supportPassiveMode"));
			logger.debug("supportPassiveMode="+supportPassiveMode);
			if (supportPassiveMode)
			{
				if (bundle.containsKey("passivePortRange"))
				{
					for(String tempStr:bundle.getString("passivePortRange").split(","))
					{
						try
						{   i=tempStr.indexOf("-");
							if (i>-1)
							{
								start=tempStr.substring(0,i);
								end=tempStr.substring(i+1);
								//logger.debug("Start port="+start+",end port="+end);
								startPort=Integer.parseInt(start);
								endPort=Integer.parseInt(end);
								if (startPort<endPort)
								{
									for (i=startPort;i<=endPort;i++)
									{
										passivePorts.add(i);
									}
								}
							}	
							else	
								passivePorts.add(Integer.parseInt(tempStr));
						}
						catch (NumberFormatException ne)
						{
							
						}
					}
					havePassivePortSpecified=(passivePorts.size()>0);
					logger.info("Passive Port range:"+passivePorts);
				}
			}
			mf=new ManagersFactory(bundle,this);
			userManager=mf.getUserManager();
			logger.info("User Manager class is loaded.");
			fileManager=mf.getFileManager();
			logger.info("File Manager class is loaded.");

			fis.close();
			result=true;
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Config. file not found.");
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("An exception occur when loading config. file.");
		}
		finally
		{
			fis=null;
		}
		return result;
	}
	public String getFtpMessage(String key)
	{
		return ftpMessage.getMessage(key);
	}
	public org.apache.log4j.Logger getLogger()
	{
		return logger;
	}
	public int getServerPort() 
	{
		return serverPort;
	}
	public String getEncoding()
	{
		return encoding;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public FileManager getFileManager()
	{
		return fileManager;
	}
	public int getMaxConnection() 
	{
		return maxConnection;
	}
	public int getClientConnectionTimeOut() 
	{
		return this.clientConnectionTimeOut;
	}

}
