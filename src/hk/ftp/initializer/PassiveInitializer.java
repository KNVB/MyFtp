package hk.ftp.initializer;
import hk.ftp.FtpSession;
import hk.ftp.PassiveServer;
import hk.ftp.handler.PassiveModeTxHandler;
import hk.ftp.listener.PassiveTxCompleteListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;

public class PassiveInitializer extends ChannelInitializer 
{
	FtpSession fs;
	ChannelHandlerContext responseCtx;
	PassiveServer passiveServer;
	
	public PassiveInitializer(PassiveServer passiveServer,FtpSession fs, ChannelHandlerContext ctx) 
	{
		// TODO Auto-generated constructor stub
		this.fs=fs;
		this.passiveServer=passiveServer;
		this.responseCtx=ctx;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception 
	{
		// TODO Auto-generated method stub
		
		fs.getConfig().getLogger().debug("Initialize Passive channel");
		ch.closeFuture().addListener(new PassiveTxCompleteListener(passiveServer,fs,responseCtx));
		ch.pipeline().addLast(new PassiveModeTxHandler(fs));
	}

}
