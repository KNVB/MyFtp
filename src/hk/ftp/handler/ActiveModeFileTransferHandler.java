package hk.ftp.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import hk.ftp.Configuration;
import hk.ftp.FtpSession;
import hk.ftp.listener.ActiveTxCompleteListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedNioFile;

public class ActiveModeFileTransferHandler extends ChannelInboundHandlerAdapter 
{
	Configuration config;
	Path filePath;
	FtpSession fs;
	String txMode;
	ChannelHandlerContext responseCtx;
	public ActiveModeFileTransferHandler(FtpSession fs,Path filePath,ChannelHandlerContext responseCtx)
	{
		this.config=fs.getConfig();
		this.filePath=filePath;
		this.fs=fs;
		this.txMode=fs.getTransferMode();
		this.responseCtx=responseCtx;
	}
	@Override
    public void channelActive(ChannelHandlerContext ctx) 
	{
		if (txMode.equals("I"))
		{
			try 
			{
				//ctx.writeAndFlush(new DefaultFileRegion(filePath.toFile(),0,Files.size(filePath)), ctx.newProgressivePromise()).addListener(new ABCListener(ctx,responseCtx,config));
				ctx.writeAndFlush(new ChunkedNioFile(filePath.toFile()), ctx.newProgressivePromise()).addListener(new ActiveTxCompleteListener(config.getLogger(),fs.getClientIp()));
				//ctx.close();
				//ctx.channel().writeAndFlush(new ChunkedNioFile(filePath.toFile())).addListener(new ABCListener(ctx,responseCtx,config));
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
