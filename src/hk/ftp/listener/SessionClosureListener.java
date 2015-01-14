package hk.ftp.listener;

import hk.ftp.FtpSession;
import hk.ftp.MyServer;
import hk.ftp.PassiveServer;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class SessionClosureListener implements ChannelFutureListener 
{
	MyServer s;
	FtpSession fs;
	public SessionClosureListener(MyServer t, FtpSession fs) 
	{
		// TODO Auto-generated constructor stub
		s=t;
		this.fs=fs;
	}

	@Override
	public void operationComplete(ChannelFuture future) throws Exception 
	{
		// TODO Auto-generated method stub
	  	PassiveServer passiveServer=fs.getPassiveServer();
		if (passiveServer!=null)
	  	{
	  		if (!passiveServer.isStopped())
	  		{
	  			passiveServer.stop();
	  			fs.returnPassivePort(passiveServer.getPort());
	  		}
	  	}
		s.sessionClose();
    	s.getConfig().getLogger().debug("This session is closed! " + future.channel().toString());
	}

}
