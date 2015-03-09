package hk.ftp.command;

import org.apache.log4j.Logger;

import java.net.InetSocketAddress;

import io.netty.channel.ChannelHandlerContext;
import hk.ftp.*;
import hk.ftp.handler.FtpSession;
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
		int port;
		String message=new String(),localIp=new String();
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
				localIp=((InetSocketAddress)ctx.channel().localAddress()).getAddress().getHostAddress();
				fs.isPassiveModeTransfer=true;						
				PassiveServer ps=new PassiveServer(fs,ctx,logger,localIp,port);
				ps.start();
			}
		}
		else
		{
			message=fs.getConfig().getFtpMessage("502_Command_Not_Implemeneted");
		}
		Utility.sendMessageToClient(ctx,fs, message);	
	}

}
