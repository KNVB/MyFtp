package hk.ftp.command;

import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import hk.ftp.handler.FtpSession;
import hk.ftp.util.Utility;

public class PWD implements hk.ftp.FtpCommandInterface {

	@Override
	public String helpMessage(FtpSession fs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(FtpSession fs, ChannelHandlerContext ctx, String param,Logger logger)	
	{
		Utility.sendMessageToClient(ctx,fs, fs.getConfig().getFtpMessage("257_PWD").replaceAll("%1", fs.getCurrentPath()));
	}
	
}
