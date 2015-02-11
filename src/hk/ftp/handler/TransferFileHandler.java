package hk.ftp.handler;

import java.nio.file.Path;
import hk.ftp.Configuration;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TransferFileHandler extends ChannelInboundHandlerAdapter 
{
	Path filePath;
	String txMode;
	Configuration config;	
	ChannelHandlerContext responseCtx;
	public TransferFileHandler(Path filePath,ChannelHandlerContext ctx, Configuration c,String txMode) 
	{
		// TODO Auto-generated constructor stub
		super();
		this.responseCtx=ctx;
		this.config=c;
		this.filePath=filePath;
		this.txMode=txMode;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) 
	{
		/*ctx.writeAndFlush(Unpooled.copiedBuffer(fileNameList.toString(),CharsetUtil.UTF_8));
		ctx.close();*/
	}
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) 
	{
	   cause.printStackTrace();
	   ctx.close();
	}
}
