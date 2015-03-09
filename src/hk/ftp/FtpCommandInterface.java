package hk.ftp;

import hk.ftp.handler.FtpSession;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.*;
public  interface FtpCommandInterface 
{
 	public String helpMessage(FtpSession fs);
	public void execute (FtpSession fs,ChannelHandlerContext ctx,String param,Logger logger); 
}
