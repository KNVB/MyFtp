package hk.ftp.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import hk.ftp.Configuration;
import hk.ftp.FtpSession;
import hk.ftp.listener.TransferFileCompleteListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedNioFile;

public class TransferFileNameHandler extends ChannelInboundHandlerAdapter 
{
	Configuration config;
	String filePath;
	FtpSession fs;
	String txMode;
	ChannelHandlerContext responseCtx;
	public TransferFileNameHandler(String filePath,FtpSession fs,ChannelHandlerContext rCtx)
	{
		this.config=fs.getConfig();
		this.filePath=filePath;
		this.fs=fs;
		this.txMode=fs.getTransferMode();
		this.responseCtx=rCtx;
	}
	@Override
    public void channelActive(ChannelHandlerContext ctx) 
	{
		if (txMode.equals("I"))
		{
			try 
			{
				ctx.writeAndFlush(new ChunkedNioFile(new File(filePath)), ctx.newProgressivePromise()).addListener(new TransferFileCompleteListener(fs,responseCtx));
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
