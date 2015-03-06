package hk.ftp.listener;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import hk.ftp.Configuration;
import hk.ftp.FtpSession;
import hk.ftp.util.Utility;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class TransferFileCompleteListener implements ChannelFutureListener 
{
	Logger logger;
	String remoteIp;
	Configuration config;
	ChannelHandlerContext responseCtx;
	public TransferFileCompleteListener(FtpSession fs,ChannelHandlerContext rCtx)
	{
		this.remoteIp=fs.getClientIp();
		this.logger=fs.getConfig().getLogger();
		this.responseCtx=rCtx;
		this.config=fs.getConfig();
	}
	@Override
	public void operationComplete(ChannelFuture qq) throws Exception 
	{
		// TODO Auto-generated method stub
		//this.logger.debug(qq.isDone() +"," +qq.isSuccess());
		
		logger.debug("file transfer completed.");
		qq.channel().close();
		Utility.sendMessageToClient(this.responseCtx,logger, remoteIp, config.getFtpMessage("226_Transfer_Ok")); 
	}
}
