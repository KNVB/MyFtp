package hk.ftp.command;
import io.netty.channel.ChannelHandlerContext;
import hk.ftp.*;
import hk.ftp.handler.FtpSession;
import hk.ftp.util.Utility;

import org.apache.log4j.Logger;



public class TYPE implements FtpCommandInterface {

	@Override
	public String helpMessage(FtpSession fs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(FtpSession fs, ChannelHandlerContext ctx,String param, Logger logger) 
	{
		if (fs.isLogined())
		{
			param=param.trim().toUpperCase();
			switch (param)
			{
				case "I":
				case "A":fs.setTransferMode(param);
						 Utility.sendMessageToClient(ctx,fs, fs.getConfig().getFtpMessage("200_Transfer_Set"));
						 break;
				default:
					Utility.sendMessageToClient(ctx,fs, fs.getConfig().getFtpMessage("504_Command_Not_Support_This_Parameter"));
					break;
			}
		}
	}

}

