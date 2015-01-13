package hk.ftp.listener;

import hk.ftp.Configuration;
import hk.ftp.util.Utility;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class TransferFileNameListCompleteListener implements ChannelFutureListener 
{
	String clientIp;
	Configuration config;
	ChannelHandlerContext responseCtx;
	public TransferFileNameListCompleteListener(Configuration c,String clientIp,ChannelHandlerContext ctx)
	{
		this.config=c;
		this.clientIp=clientIp;
		this.responseCtx=ctx;
	}
	@Override
	public void operationComplete(ChannelFuture ch) throws Exception 
	{
		// TODO Auto-generated method stub
		config.getLogger().debug("File name list transfered to "+clientIp+" Completed.");
		Utility.sendMessageToClient(responseCtx,config.getLogger(),clientIp, config.getFtpMessage("226_Transfer_Ok"));
	}

}
