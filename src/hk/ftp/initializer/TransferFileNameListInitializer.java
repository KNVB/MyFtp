package hk.ftp.initializer;

import hk.ftp.Configuration;
import hk.ftp.listener.TransferFileNameListCompleteListener;
import hk.ftp.handler.TransferFileNameListHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;

public class TransferFileNameListInitializer  extends ChannelInitializer 
{
	String clientIp;
	Configuration config;
	ChannelHandlerContext responseCtx;
	
	StringBuilder fileNameList=new StringBuilder();
	
	public TransferFileNameListInitializer (StringBuilder fileNameList2,ChannelHandlerContext c,String clientIp,Configuration config)
	{
		this.responseCtx=c;
		this.fileNameList=fileNameList2;
		this.config=config;
		this.clientIp=clientIp;
	}
	@Override
	protected void initChannel(Channel ch) throws Exception 
	{
		// TODO Auto-generated method stub
		ch.closeFuture().addListener(new TransferFileNameListCompleteListener(config,this.clientIp,responseCtx));
		ch.pipeline().addLast(new TransferFileNameListHandler(fileNameList,responseCtx,config));
	}

}
