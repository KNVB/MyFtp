package hk.ftp.initializer;

import java.nio.file.Path;

import hk.ftp.Configuration;
import hk.ftp.handler.TransferFileHandler;
import hk.ftp.handler.TransferFileNameListHandler;
import hk.ftp.listener.TransferFileCompleteListener;
import hk.ftp.listener.TransferFileNameListCompleteListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;

public class TransferFileInitializer extends ChannelInitializer 
{
	
	Path filePath;
	String clientIp,txMode;
	Configuration config;
	ChannelHandlerContext responseCtx;
	
	public TransferFileInitializer(Path filePath, ChannelHandlerContext ctx,String clientIp, Configuration config, String txMode) 
	{
		// TODO Auto-generated constructor stub
		this.txMode=txMode;
		this.config=config;
		this.responseCtx=ctx;
		this.clientIp=clientIp;
		this.filePath=filePath;
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, Throwable arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initChannel(Channel ch) throws Exception 
	{
		// TODO Auto-generated method stub
		ch.closeFuture().addListener(new TransferFileCompleteListener(config,this.clientIp,responseCtx));
		ch.pipeline().addLast(new TransferFileHandler(filePath,responseCtx,config,txMode));	
	}

}
