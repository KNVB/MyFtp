package hk.ftp.command;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.Logger;

import hk.ftp.*;
import hk.ftp.util.Utility;

public class EPSV implements FtpCommandInterface {

	@Override
	public String helpMessage(FtpSession fs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(FtpSession fs,ChannelHandlerContext ctx, String param, Logger logger) 
	{
		// TODO Auto-generated method stub
		int port,index;
		String message=new String();
		MyServer server=fs.getServer();
		if (server.isSupportPassiveMode())
		{
			port=server.getNextPassivePort();
			if (port==-1)
				message=fs.getConfig().getFtpMessage("550_CANT_CONNECT_CLNT");
			else
			{	
				message=fs.getConfig().getFtpMessage("229_EPSV_Ok");
				message=message.replaceAll("%1", String.valueOf(port));
			}
		}
		else
		{
			message=fs.getConfig().getFtpMessage("502_Command_Not_Implemeneted");
		}
		Utility.sendMessageToClient(ctx,fs, message);	
	}

}
