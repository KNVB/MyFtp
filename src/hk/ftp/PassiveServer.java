package hk.ftp;
import org.apache.log4j.Logger;

import hk.ftp.initializer.PassiveInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class PassiveServer 
{
	private int port;
	
	private FtpSession fs;
	private boolean stopped=true;
	private Logger logger=null;
	private NioEventLoopGroup bossGroup;
	private NioEventLoopGroup workerGroup ;
	private String ipAddress=null;
	private ChannelHandlerContext responseCtx;
	public PassiveServer(FtpSession fs,ChannelHandlerContext ctx, Logger l,String localIp,int port)
	{
		this.fs=fs;
		this.logger=l;
		this.port=port;
		this.responseCtx=ctx;
		this.fs.setPassiveServer(this);
		this.ipAddress=localIp;
	}
	public int getPort() 
	{
		// TODO Auto-generated method stub
		return this.port;
	}

	public void start()
	{
		 try 
	        {
	            ServerBootstrap bootStrap = new ServerBootstrap();
	            bossGroup=new NioEventLoopGroup();
	            workerGroup = new NioEventLoopGroup();
	            bootStrap.group(bossGroup, workerGroup);
	            bootStrap.channel(NioServerSocketChannel.class);
	            bootStrap.childHandler(new PassiveInitializer(this,fs,responseCtx));
	            InetSocketAddress inSocketAddress=new InetSocketAddress(ipAddress,port);         
	            //Channel ch = bootStrap.bind(inSocketAddress).sync().channel();
	            bootStrap.bind(inSocketAddress);
	            logger.info("Passive Server Started");
	            logger.info("Passive Server listening " +ipAddress+":" + port);
	            stopped=false;
	        } 
	        catch (Exception e) 
	        {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.stop();
			}
	}
	public void stop() 
	{
		// TODO Auto-generated method stub
		if (bossGroup!=null)
		{
			bossGroup.shutdownGracefully();
			bossGroup=null;
		}
		if (workerGroup!=null)
        {
			workerGroup.shutdownGracefully();
			workerGroup=null;
        }
        fs.returnPassivePort(port);
        logger.debug("Passive Server shutdown gracefully.");
        stopped=true;
	}
	public boolean isStopped()
	{
		return stopped;
	}
}
