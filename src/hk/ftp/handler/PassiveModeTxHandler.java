package hk.ftp.handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class PassiveModeTxHandler extends ChannelInboundHandlerAdapter
{
	FtpSession fs;
	public PassiveModeTxHandler(FtpSession fs)
	{
		this.fs=fs;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) 
	{
		fs.setPassiveChannelContext(ctx); 
	}
}
