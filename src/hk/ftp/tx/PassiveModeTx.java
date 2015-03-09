package hk.ftp.tx;

import java.nio.file.Path;

import hk.ftp.handler.FtpSession;
import hk.ftp.util.Utility;
import io.netty.channel.ChannelHandlerContext;

public class PassiveModeTx 
{
	ChannelHandlerContext txCtx;
	public PassiveModeTx(ChannelHandlerContext ctx)
	{
		this.txCtx=ctx;
	}
	public void transFileNameList(FtpSession fs,StringBuilder fileNameList)
	{
		Utility.sendMessageToClient(txCtx,fs,fileNameList.toString());
		txCtx.close();
	}
	public void transFile(Path path, ChannelHandlerContext responseCtx,	String transferMode) 
	{
		// TODO Auto-generated method stub
		
	}
}
