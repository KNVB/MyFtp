package hk.ftp.command;
import hk.ftp.*;
import hk.ftp.handler.FtpSession;
import hk.ftp.util.Utility;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;


public class EPRT implements FtpCommandInterface {

	@Override
	public String helpMessage(FtpSession fs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(FtpSession fs, ChannelHandlerContext ctx,String param, Logger logger) 
	{
		// TODO Auto-generated method stub
		String temp[]=param.trim().split("\\|");
		//fs.setClientDataPort(Integer.parseInt(temp[temp.length-1]));
		if (temp.length!=4)
			Utility.sendMessageToClient(ctx.channel(),logger,fs.getClientIp(), fs.getConfig().getFtpMessage("500_Null_Command"));
		else
		{	
			try
			{
				int portNo=Integer.parseInt(temp[temp.length-1]);
				fs.setClientDataPortNo(portNo);
				Utility.sendMessageToClient(ctx.channel(),logger,fs.getClientIp(), fs.getConfig().getFtpMessage("200_Port_Ok"));
			}
			catch (Exception e)
			{
				Utility.sendMessageToClient(ctx.channel(),logger,fs.getClientIp(), fs.getConfig().getFtpMessage("550_CANT_CONNECT_CLNT"));
			}
		}
	}

}
