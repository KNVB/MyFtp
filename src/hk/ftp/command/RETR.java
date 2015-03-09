package hk.ftp.command;

import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import hk.ftp.FileManager;
import hk.ftp.FtpCommandInterface;
import hk.ftp.exception.AccessDeniedException;
import hk.ftp.exception.PathNotFoundException;
import hk.ftp.exception.QuotaExceedException;
import hk.ftp.handler.FtpSession;
import hk.ftp.util.Utility;

public class RETR implements FtpCommandInterface {

	@Override
	public String helpMessage(FtpSession fs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(FtpSession fs,ChannelHandlerContext ctx, String param, Logger logger)
	{
		FileManager fm=fs.getConfig().getFileManager();
		logger.debug("param="+param+"|");
		
		try 
		{
			fm.downloadFile(fs,ctx,param);
		} 
		catch (AccessDeniedException | PathNotFoundException | QuotaExceedException err) 
		{
			// TODO Auto-generated catch block
			Utility.sendMessageToClient(ctx,fs,err.getMessage());
		}
	}	
}
