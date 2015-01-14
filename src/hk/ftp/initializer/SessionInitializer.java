package hk.ftp.initializer;

import java.net.InetSocketAddress;

import hk.ftp.Configuration;
import hk.ftp.FtpSession;
import hk.ftp.MyServer;
import hk.ftp.listener.SessionClosureListener;
import hk.ftp.util.Utility;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

public class SessionInitializer extends ChannelInitializer 
{
	MyServer s;
	Configuration config;
	public SessionInitializer(MyServer t)
	{
		s=t;
		config=s.getConfig();
	}
	@Override
	protected void initChannel(Channel ch) throws Exception 
	{
		String remoteIp=((InetSocketAddress) ch.remoteAddress()).getAddress().getHostAddress();
		// TODO Auto-generated method stub
		if (s.isOverConnectionLimit())
		{
			Utility.sendMessageToClient(ch,s.getConfig().getLogger(),remoteIp,config.getFtpMessage("330_Connection_Full"));
			ch.close().addListener(ChannelFutureListener.CLOSE);
		}
		else
		{	
			FtpSession fs=new FtpSession(s,remoteIp);
			ch.closeFuture().addListener(new SessionClosureListener(s,fs));
			
			ch.pipeline().addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
			ch.pipeline().addLast(fs);
			
			Utility.sendMessageToClient(ch,s.getConfig().getLogger(),remoteIp,"220 "+config.getFtpMessage("Greeting_Message"));
		}		
	}

}
