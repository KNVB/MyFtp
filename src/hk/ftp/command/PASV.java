package hk.ftp.command;

import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import hk.ftp.FtpCommandInterface;
import hk.ftp.FtpSession;
import hk.ftp.MyServer;
import hk.ftp.PassiveServer;
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
		int port,index;
		MyServer server=fs.getServer();
		String message=new String(),localIP=ctx.channel().localAddress().toString();
		//logger.debug("Local address:"+ctx.channel().localAddress());
		if (server.isSupportPassiveMode())
		{
			port=server.getNextPassivePort();
			if (port==-1)
				message=fs.getConfig().getFtpMessage("550_CANT_CONNECT_CLNT");
			else
			{	
				logger.debug("Port "+port+" is assigned.");
				fs.isPassiveModeTransfer=true;				 
				message=fs.getConfig().getFtpMessage("227_Enter_Passive_Mode");
				index=localIP.indexOf(":");
				localIP=localIP.substring(1,index);
				
				localIP=localIP.replaceAll("\\.", ",");
				message=message.replaceAll("%1", localIP);
				message=message.replaceAll("%2", String.valueOf(port/256));
				message=message.replaceAll("%3", String.valueOf(port % 256));
				PassiveServer ps=new PassiveServer(fs,ctx,logger,port);
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
