package hk.ftp.listener;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import hk.ftp.Configuration;
import hk.ftp.util.Utility;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class ActiveTxCompleteListener implements ChannelFutureListener 
{
	Logger logger;
	String remoteIp;
	public ActiveTxCompleteListener(Logger l,String ip)
	{
		this.remoteIp=ip;
		this.logger=l;
	}
	@Override
	public void operationComplete(ChannelFuture qq) throws Exception 
	{
		// TODO Auto-generated method stub
		this.logger.debug(qq.isDone() +"," +qq.isSuccess());
		
		logger.debug("file transfer completed.");
		qq.channel().close();
		//Utility.sendMessageToClient(this.responseCtx,logger, remoteIp, config.getFtpMessage("226_Transfer_Ok")); 
	}
}
