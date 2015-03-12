package hk.ftp.command;

import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import hk.ftp.Configuration;
import hk.ftp.FtpCommandInterface;
import hk.ftp.User;
import hk.ftp.UserManager;
import hk.ftp.exception.*;
import hk.ftp.handler.FtpSession;
import hk.ftp.util.Utility;

public class PASS implements FtpCommandInterface 
{

	@Override
	public String helpMessage(FtpSession fs) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(FtpSession fs, ChannelHandlerContext ctx, String param,	Logger logger) 
	{
		// TODO Auto-generated method stub
		Configuration config=fs.getConfig();
		String message=new String();
		if ((param==null) || (param.isEmpty()))
		{
			message=config.getFtpMessage("500_Null_Command");
		}
		else
		{
			UserManager um=config.getUserManager();
			try 
			{
				User user=um.login(fs.getUserName(), param);
				message=config.getFtpMessage("230_Login_Ok").replaceAll("%1", fs.getUserName());
				fs.setUser(user);
				fs.setIsLogined(true);
			} 
			catch (AccessDeniedException | InvalidHomeDirectoryException | LoginFailureException e) 
			{
				// TODO Auto-generated catch block
				message=e.getMessage();
			}
		}
		Utility.sendMessageToClient(ctx.channel(),logger,fs.getClientIp(), message);
	}

}
