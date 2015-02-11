package hk.ftp.listener;

import hk.ftp.Configuration;
import hk.ftp.util.Utility;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class TransferFileCompleteListener implements ChannelFutureListener 
{
	String clientIp;
	Configuration config;
	ChannelHandlerContext responseCtx;
	public TransferFileCompleteListener(Configuration config, String clientIp,ChannelHandlerContext ctx) 
	{
		// TODO Auto-generated constructor stub
		this.config=config;
		this.clientIp=clientIp;
		this.responseCtx=ctx;
	}

	@Override
	public void operationComplete(ChannelFuture arg0) throws Exception 
	{
		// TODO Auto-generated method stub
		config.getLogger().debug("File transfer completed!");
		Utility.sendMessageToClient(responseCtx,config.getLogger(),clientIp, config.getFtpMessage("226_Transfer_Ok"));
	}

}
