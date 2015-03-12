package hk.ftp.command;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import hk.ftp.Configuration;
import hk.ftp.FtpCommandInterface;
import hk.ftp.handler.FtpSession;
import hk.ftp.util.Utility;
public class QUIT implements FtpCommandInterface
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
		Utility.sendMessageToClient(ctx.channel(),logger,fs.getClientIp(),fs.getConfig().getFtpMessage("221_Logout_Ok"));
		ctx.close().addListener(ChannelFutureListener.CLOSE);
	}
}
