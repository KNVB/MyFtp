package hk.ftp.initializer;

import hk.ftp.FtpSession;
import hk.ftp.handler.TransferFileNameHandler;

import hk.ftp.listener.TransferFileCompleteListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;

public class TransferFileInitializer extends ChannelInitializer<SocketChannel>
{
	FtpSession fs;
	ChannelHandlerContext responseCtx;
	String serverPath;
	public TransferFileInitializer(String sp,ChannelHandlerContext rCtx,FtpSession fs)
	{
		this.responseCtx=rCtx;
		this.serverPath=sp;
		this.fs=fs;
	}
	protected void initChannel(SocketChannel ch) throws Exception 
	{
		ch.pipeline().addLast();
		
		ChannelPipeline p = ch.pipeline();
        //ch.closeFuture().addListener(new TransferFileCompleteListener( fs, responseCtx));
        p.addLast("streamer",new ChunkedWriteHandler());
        p.addLast("handler",new TransferFileNameHandler(serverPath,fs,responseCtx));
		
	}
}
