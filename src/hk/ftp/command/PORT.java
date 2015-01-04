package hk.ftp.command;

import hk.ftp.FtpSession;
import hk.ftp.util.Utility;
import org.apache.log4j.Logger;
import hk.ftp.FtpCommandInterface;
import io.netty.channel.ChannelHandlerContext;

public class PORT implements FtpCommandInterface 
{
	String temp=new String();
	@Override
	public String helpMessage(FtpSession fs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(FtpSession fs, ChannelHandlerContext ctx, String param,Logger logger) 
	{
		// TODO Auto-generated method stub
		param=param.trim();
		String[] p=param.split(",");
		if (p.length!=6)
		{
			Utility.sendMessageToClient(ctx,fs, fs.getConfig().getFtpMessage("500_Null_Command"));
		}
		else
		{
			int clientPort;
			int high = Integer.parseInt(p[4]);
		    int low = Integer.parseInt(p[5]);
		    if (high < 0 || high > 255 || low < 0 || low > 255)
			{
		    	Utility.sendMessageToClient(ctx,fs, fs.getConfig().getFtpMessage("500_Null_Command"));
			}
		    else
		    {	 
		    	clientPort=(high << 8) + low;
		    	logger.debug("Port="+clientPort);
		    	fs.isPassiveModeTransfer=false;
		    	fs.setClientDataPortNo(clientPort);
		    	Utility.sendMessageToClient(ctx,fs, fs.getConfig().getFtpMessage("200_Port_Ok"));
		    }
		}
	}
}
