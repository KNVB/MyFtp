package hk.ftp.initializer;

import hk.ftp.handler.FtpSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class ActiveModeTxInitializer extends ChannelInitializer 
{
	FtpSession fs=null;
	public ActiveModeTxInitializer(FtpSession fs)
	{
		this.fs=fs;
	}
	@Override
	protected void initChannel(Channel ch) throws Exception 
	{
		// TODO Auto-generated method stub
		
	}

}
