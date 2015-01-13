package hk.ftp.tx;

import hk.ftp.FtpSession;
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
}
