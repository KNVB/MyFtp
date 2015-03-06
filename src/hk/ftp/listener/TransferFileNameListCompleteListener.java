package hk.ftp.listener;

import hk.ftp.Configuration;
import hk.ftp.FtpSession;
import hk.ftp.util.Utility;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class TransferFileNameListCompleteListener implements ChannelFutureListener 
{
	String clientIp;
	FtpSession fs;
	Configuration config;
	ChannelHandlerContext responseCtx;
	public TransferFileNameListCompleteListener(FtpSession fs,ChannelHandlerContext ctx)
	{
		this.fs=fs;
		this.config=fs.getConfig();
		this.clientIp=fs.getClientIp();
		this.responseCtx=ctx;
	}
	@Override
	public void operationComplete(ChannelFuture ch) throws Exception 
	{
		// TODO Auto-generated method stub
		config.getLogger().debug("File name list transfered to "+clientIp+" Completed.");
		ch.channel().close();
		Utility.sendMessageToClient(responseCtx,config.getLogger(),clientIp, config.getFtpMessage("226_Transfer_Ok"));
	}

}
