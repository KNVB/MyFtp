package hk.ftp.impl;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;

import io.netty.channel.ChannelHandlerContext;



import hk.ftp.Configuration;
import hk.ftp.FileManager;
import hk.ftp.FtpSession;
import hk.ftp.User;
import hk.ftp.exception.AccessDeniedException;
import hk.ftp.exception.PathNotFoundException;
import hk.ftp.initializer.TransferFileNameListInitializer;
import hk.ftp.tx.ActiveModeTx;
import hk.ftp.util.Utility;

public class MyFileManager extends FileManager 
{
	DbOp dbo=null;
	ResultSet rs=null;
	String strSql=new String();
	
	public MyFileManager(Configuration c) 
	{
		super(c);
		try 
		{
			dbo=new DbOp(c);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			dbo=null;
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public BufferedReader getTextFileContent(User u, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedOutputStream getBinaryFileContent(User u, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeDirectory(FtpSession fs,String inPath) throws AccessDeniedException, PathNotFoundException
	{
		String clientPath=null,serverPath;
		User user=fs.getUser();
		clientPath=Utility.resolveClientPath(logger,fs.getCurrentPath(),inPath);
		
		if (isReadableClientDir(user.getClientPathACL(),clientPath))
		{
			serverPath=dbo.getRealPath(fs.getUserName(),clientPath,FileManager.READ_PERMISSION);
			if (isReadableServerDir(user.getServerPathACL(),Paths.get(serverPath)))
				fs.setCurrentPath(clientPath);
			else
				throw new AccessDeniedException(config.getFtpMessage("550_Permission_Denied"));
		}
		else
		{	
			throw new AccessDeniedException(config.getFtpMessage("550_Permission_Denied"));
		}
		
	}
	@Override
	public void showFileNameList(FtpSession fs, ChannelHandlerContext ctx,String inPath) throws AccessDeniedException, PathNotFoundException 
	{
		// TODO Auto-generated method stub
		String serverPath=new String(),fileNameList=new String();
		String clientPath=Utility.resolveClientPath(logger,fs.getCurrentPath(), inPath);
		config=fs.getConfig();
		serverPath=dbo.getRealPath(fs.getUserName(),clientPath,FileManager.READ_PERMISSION);
		logger.debug("Server Path="+serverPath);
		logger.debug("Server Path ACL="+fs.getUser().getServerPathACL());
		fileNameList=getFileNameList(fs.getUser(),serverPath);
		//fileNameList=getFileNameList(fs.getUserName(),serverPath);
		if (fs.isPassiveModeTransfer)
		{
			logger.debug("Passive mode");
			Utility.sendMessageToClient(ctx,fs,config.getFtpMessage("502_Command_Not_Implemeneted"));
		}
		else
		{
			logger.debug("Active mode");
			Utility.sendMessageToClient(ctx,fs,config.getFtpMessage("150_Open_Data_Conn"));
			ActiveModeTx aTx=new ActiveModeTx(fs.getClientIp(),fs.getClientDataPortNo(), fs.getConfig());
			aTx.transferFileNameList(fileNameList, ctx);
		}
	}
	@Override
	public void showFullDirList(FtpSession fs, ChannelHandlerContext ctx,String inPath) throws AccessDeniedException, PathNotFoundException 
	{
		// TODO Auto-generated method stub
		String serverPath=new String(),fileNameList=new String();
		String clientPath=Utility.resolveClientPath(logger,fs.getCurrentPath(), inPath);
		config=fs.getConfig();
		serverPath=dbo.getRealPath(fs.getUserName(),clientPath,FileManager.READ_PERMISSION);
		logger.debug("Server Path="+serverPath);
		logger.debug("Server Path ACL="+fs.getUser().getServerPathACL());
		fileNameList=getFullDirList(fs.getUser(),serverPath);
		//fileNameList=getFileNameList(fs.getUserName(),serverPath);
		if (fs.isPassiveModeTransfer)
		{
			logger.debug("Passive mode");
			Utility.sendMessageToClient(ctx,fs,config.getFtpMessage("502_Command_Not_Implemeneted"));
		}
		else
		{
			logger.debug("Active mode");
			Utility.sendMessageToClient(ctx,fs,config.getFtpMessage("150_Open_Data_Conn"));
			ActiveModeTx aTx=new ActiveModeTx(fs.getClientIp(),fs.getClientDataPortNo(), fs.getConfig());
			aTx.transferFileNameList(fileNameList, ctx);
		}
	}
	private String getFullDirList(User user, String serverPath) 
	{
		// TODO Auto-generated method stub
		String resultString=new String(),serverDir,permission;
		Hashtable<String, String> serverPathACL=user.getServerPathACL();
		logger.debug("Server Path ACL size="+serverPathACL.size());
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(serverPath))) 
		{
			for (Path path : directoryStream) 
            {
	            logger.debug(path.toString()+","+isReadableServerDir(serverPathACL,path));
          		if (Files.isDirectory(path))
        		 {
         			if (isReadableServerDir(serverPathACL,path))
         				resultString+=Utility.formatPathName(path);
        		 }
        		 else
        		 {
        			 if (isReadableServerFile(serverPathACL,path))
          				resultString+=Utility.formatPathName(path);
        		 }
            } 
		}
        catch (IOException ex) 
    	{}
    	return resultString;
	}
	private String getFileNameList(User user, String serverPath) 
	{
		// TODO Auto-generated method stub
		String resultString=new String(),serverDir,permission;
		Hashtable<String, String> serverPathACL=user.getServerPathACL();
		logger.debug("Server Path ACL size="+serverPathACL.size());
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(serverPath))) 
		{
			for (Path path : directoryStream) 
            {
	            logger.debug(path.toString()+","+isReadableServerDir(serverPathACL,path));
          		if (Files.isDirectory(path))
        		 {
         			if (isReadableServerDir(serverPathACL,path))
         				resultString+=(path.getFileName().toString())+"\r\n";
        		 }
        		 else
        		 {
        			 if (isReadableServerFile(serverPathACL,path))
          				resultString+=(path.getFileName().toString())+"\r\n";
        		 }
            } 
		}
        catch (IOException ex) 
    	{}
    	return resultString;
	}
	private boolean isReadableServerFile(Hashtable<String, String> serverPathACL,Path p)
	{
		boolean result=true;
		String temp,permission;
		Enumeration<String> serverPaths=serverPathACL.keys();
		while (serverPaths.hasMoreElements())
		{
			temp=serverPaths.nextElement();
			logger.debug("temp="+temp);
			if (p.toString().equals(temp))
			{
				permission=serverPathACL.get(temp);
				if (permission.indexOf(FileManager.NO_ACCESS)>-1)
				{
					result=false;
					break;
				}
			}
		}
		return result;
	}
	private boolean isReadableServerDir(Hashtable<String, String> serverPathACL,Path p)
	{
		boolean result=true;
		String temp,permission;
		Enumeration<String> serverPaths=serverPathACL.keys();
		while (serverPaths.hasMoreElements())
		{
			temp=serverPaths.nextElement();
			if (p.toString().indexOf(temp)==0)
			{
				permission=serverPathACL.get(temp);
				logger.debug("Path="+p+",temp="+temp+",permission="+permission);
				if (permission.indexOf(FileManager.NO_ACCESS)>-1)
				{
					result=false;
					break;
				}
			}
		}
		return result;
	}
	private boolean isReadableClientDir(Hashtable<String, String> clientPathACL,String inPath)
	{
		boolean result=true;
		return result;
	}
	public void close() 
	{
		// TODO Auto-generated method stub
		if (dbo!=null)
		{
			try 
			{
				dbo.close();
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dbo=null;
	}
}
