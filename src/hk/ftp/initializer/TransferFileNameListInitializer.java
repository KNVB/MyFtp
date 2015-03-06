package hk.ftp.initializer;

import hk.ftp.FtpSession;
import hk.ftp.handler.TransferFileNameListHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class TransferFileNameListInitializer extends ChannelInitializer<SocketChannel>
{
	
	FtpSession fs;
	ChannelHandlerContext responseCtx;
	StringBuilder fileNameList=new StringBuilder();
	
	public TransferFileNameListInitializer(StringBuilder fileNameList2,ChannelHandlerContext c,FtpSession fs)
	{
		this.responseCtx=c;
		this.fileNameList=fileNameList2;
		this.fs=fs;
		
	}
	protected void initChannel(SocketChannel ch) throws Exception 
	{
		// TODO Auto-generated method stub
		ch.pipeline().addLast(new TransferFileNameListHandler(fileNameList,responseCtx,fs));
	}
}
