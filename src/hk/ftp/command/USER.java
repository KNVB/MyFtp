package hk.ftp.command;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import hk.ftp.Configuration;
import hk.ftp.FtpCommandInterface;
import hk.ftp.handler.FtpSession;
import hk.ftp.util.Utility;
public class USER implements FtpCommandInterface
{

	@Override
	public String helpMessage(FtpSession fs) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(FtpSession fs,ChannelHandlerContext ctx, String param, Logger logger) 
	{
		// TODO Auto-generated method stub
		Configuration config=fs.getConfig();
		String message=new String();
		if (param ==null)
		{
			message=config.getFtpMessage("500_Null_Command");
		}
		else
		{
			message=config.getFtpMessage("331_Password_Required");
			message=message.replaceAll("%1", param);
			fs.setUserName(param);
		}
		Utility.sendMessageToClient(ctx,fs, message);
	}

}
