package hk.ftp.command;

import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import hk.ftp.FtpCommandInterface;
import hk.ftp.FtpSession;
import hk.ftp.MyServer;
import hk.ftp.util.Utility;

public class PASV implements FtpCommandInterface 
{
	
	@Override
	public String helpMessage(FtpSession fs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(FtpSession fs, ChannelHandlerContext ctx, String param,	Logger logger) 
	{
		// TODO Auto-generated method stub
		int port;
		MyServer server=fs.getServer();
		String message=new String();
		
		if (server.isSupportPassiveMode())
		{
			port=server.getNextPassivePort();
			logger.debug("Port "+port+" is assigned.");
		}
		else
		{
			message=fs.getConfig().getFtpMessage("502_Command_Not_Implemeneted");
		}
		Utility.sendMessageToClient(ctx,fs, message);
	}

}
