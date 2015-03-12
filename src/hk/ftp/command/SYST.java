package hk.ftp.command;
import io.netty.channel.ChannelHandlerContext;
import hk.ftp.handler.FtpSession;
import hk.ftp.util.Utility;

import org.apache.log4j.Logger;
 
public class SYST implements hk.ftp.FtpCommandInterface
{
	@Override
	public String helpMessage(FtpSession fs) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void execute(FtpSession fs, ChannelHandlerContext ctx,String param,Logger logger) 
	{
		// TODO Auto-generated method stub
		Utility.sendMessageToClient(ctx.channel(),logger,fs.getClientIp(), fs.getConfig().getFtpMessage("215_System_Type")+" "+ Utility.getSystemType(logger));
	}



	

}
