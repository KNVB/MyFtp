package hk.ftp.listener;

import hk.ftp.MyServer;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class SessionClosureListener implements ChannelFutureListener 
{
	MyServer s;
	public SessionClosureListener(MyServer t) 
	{
		// TODO Auto-generated constructor stub
		s=t;
	}

	@Override
	public void operationComplete(ChannelFuture future) throws Exception 
	{
		// TODO Auto-generated method stub
	  	s.sessionClose();
    	s.getConfig().getLogger().debug("This session is closed! " + future.channel().toString());
	}

}
