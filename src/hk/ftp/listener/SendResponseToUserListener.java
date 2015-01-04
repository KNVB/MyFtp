package hk.ftp.listener;

import org.apache.log4j.Logger;

import hk.ftp.FtpSession;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class SendResponseToUserListener implements ChannelFutureListener 
{
	FtpSession fs;
	Logger logger;
	String message,remoteIp;
	
	public SendResponseToUserListener(FtpSession fs,String msg)
	{
		this.fs=fs;
		this.message=msg;
		this.logger=fs.getConfig().getLogger();
		this.remoteIp=fs.getClientIp();
	}
	public SendResponseToUserListener(Logger l,String remoteIp, String msg) 
	{
		// TODO Auto-generated constructor stub
		this.message=msg;
		this.logger=l;
		this.remoteIp=remoteIp;
	}
	@Override
	public void operationComplete(ChannelFuture arg0) throws Exception 
	{
		// TODO Auto-generated method stub
		logger.info("Message:"+message+", sent to:"+remoteIp);
	}

}
