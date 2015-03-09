package hk.ftp.command;
import hk.ftp.*;
import hk.ftp.util.*;
import hk.ftp.handler.FtpSession;
import hk.ftp.impl.DbOp;
import hk.ftp.exception.*;

import java.io.File;

import org.apache.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
public class CWD implements FtpCommandInterface 
{

	@Override
	public String helpMessage(FtpSession fs) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(FtpSession fs, ChannelHandlerContext ctx,String param, Logger logger)
	{
		// TODO Auto-generated method stub
		FileManager fm=fs.getConfig().getFileManager();
		logger.debug("param="+param+"|");
		
		try 
		{
			fm.changeDirectory(fs,param);
			Utility.sendMessageToClient(ctx,fs,fs.getConfig().getFtpMessage("200_Ok"));
		} 
		catch (AccessDeniedException | PathNotFoundException err) 
		{
			// TODO Auto-generated catch block
			Utility.sendMessageToClient(ctx,fs,err.getMessage());
		}
	}
}
