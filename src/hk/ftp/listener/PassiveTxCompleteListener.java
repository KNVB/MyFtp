package hk.ftp.listener;

import hk.ftp.Configuration;
import hk.ftp.FtpSession;
import hk.ftp.PassiveServer;
import hk.ftp.util.Utility;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class PassiveTxCompleteListener implements ChannelFutureListener 
{
	
	PassiveServer passiveServer;
	ChannelHandlerContext responseCtx;
	FtpSession fs;
	public PassiveTxCompleteListener(PassiveServer passiveServer,FtpSession fs,ChannelHandlerContext ctx)
	{
		this.passiveServer=passiveServer;
		this.responseCtx=ctx;
		this.fs=fs;
	}
	@Override
	public void operationComplete(ChannelFuture arg0) throws Exception
	{
		// TODO Auto-generated method stub
		this.passiveServer.stop();
		Utility.sendMessageToClient(responseCtx,fs, fs.getConfig().getFtpMessage("226_Transfer_Ok")); 
		//Utility.sendMessageToClient(responseCtx,config.getLogger(),clientIp, config.getFtpMessage("226_Transfer_Ok"));
	}
	
}
