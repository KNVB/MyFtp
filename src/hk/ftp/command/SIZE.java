package hk.ftp.command;

import org.apache.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import hk.ftp.*;
import hk.ftp.exception.AccessDeniedException;
import hk.ftp.exception.PathNotFoundException;
import hk.ftp.handler.FtpSession;
import hk.ftp.util.Utility;

public class SIZE implements FtpCommandInterface 
{
	@Override
	public String helpMessage(FtpSession fs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(FtpSession fs,ChannelHandlerContext ctx, String param, Logger logger) 
	{
		FileManager fm=fs.getConfig().getFileManager();
		String message=new String();
		try 
		{
			long size=fm.getPathSize(fs, param);
			message=fs.getConfig().getFtpMessage("213_File_Size");
			message=message.replaceAll("%1", String.valueOf(size));
			Utility.sendMessageToClient(ctx.channel(),logger,fs.getClientIp(),message);
		} 
		catch (AccessDeniedException | PathNotFoundException e) 
		{
			// TODO Auto-generated catch block
			Utility.sendMessageToClient(ctx.channel(),logger,fs.getClientIp(),e.getMessage());
		}
	}

}
