package hk.ftp.initializer;

import hk.ftp.Configuration;
import hk.ftp.listener.TransferFileNameListCompleteListener;
import hk.ftp.handler.TransferFileNameListHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;

public class TransferFileNameListInitializer  extends ChannelInitializer 
{
	Configuration config;
	ChannelHandlerContext responseCtx;
	String fileNameList=new String(),clientIp;
	
	public TransferFileNameListInitializer (String fileNameList,ChannelHandlerContext c,String clientIp,Configuration config)
	{
		this.responseCtx=c;
		this.fileNameList=fileNameList;
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
