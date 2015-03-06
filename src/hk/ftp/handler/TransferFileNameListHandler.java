package hk.ftp.handler;

import hk.ftp.Configuration;
import hk.ftp.FtpSession;
import hk.ftp.listener.TransferFileCompleteListener;
import hk.ftp.listener.TransferFileNameListCompleteListener;
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
	FtpSession fs;
	ChannelHandlerContext responseCtx;
	public TransferFileNameListHandler(StringBuilder fileNameList2,ChannelHandlerContext ctx,FtpSession fs) 
	{
		// TODO Auto-generated constructor stub
		super();
		this.fs=fs;
		this.fileNameList=fileNameList2;
		this.responseCtx=ctx;
		this.config=fs.getConfig();
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) 
	{
		ctx.writeAndFlush(Unpooled.copiedBuffer(fileNameList.toString(),CharsetUtil.UTF_8)).addListener(new TransferFileNameListCompleteListener(fs,responseCtx));
	}
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) 
	{
	   cause.printStackTrace();
	   ctx.close();
	}
}
