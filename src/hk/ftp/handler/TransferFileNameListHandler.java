package hk.ftp.handler;

import hk.ftp.Configuration;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;

@Sharable
public class TransferFileNameListHandler extends ChannelInboundHandlerAdapter
{
	StringBuilder fileNameList;
	Configuration config;	
	ChannelHandlerContext responseCtx;
	public TransferFileNameListHandler(StringBuilder fileNameList2,ChannelHandlerContext ctx, Configuration c) 
	{
		// TODO Auto-generated constructor stub
		super();
		this.fileNameList=fileNameList2;
		this.responseCtx=ctx;
		this.config=c;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) 
	{
		ctx.writeAndFlush(Unpooled.copiedBuffer(fileNameList.toString(),CharsetUtil.UTF_8));
		ctx.close();
	}
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) 
	{
	   cause.printStackTrace();
	   ctx.close();
	}
}
