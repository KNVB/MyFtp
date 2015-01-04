package hk.ftp;
import hk.ftp.initializer.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyServer 
{
	private int port=21;
	private String ipAddress="0.0.0.0";
	
	private EventLoopGroup bossGroup=null,workerGroup = null;
    
	private Logger logger=null;
	private Configuration config=null;
	private ArrayList<Integer> passivePorts;
	private Hashtable<Integer,ServerSocket> passiveServerSocketInUse;

	static int maxConnectionCount=1,connectionCount=0;
	public void init()
	{
		if (initLogger())
		{
			config=new Configuration(logger);
			if (config.load())
			{
				logger.info("FTP Server Initialization completed.");
				if ((config.supportPassiveMode) && (config.havePassivePortSpecified))
				{
					passivePorts=config.passivePorts;
					passiveServerSocketInUse=new Hashtable<Integer,ServerSocket>();
					//logger.debug(passivePorts==null);
				}
			}
			maxConnectionCount=config.getMaxConnection();
		}
	}
	public MyServer()
	{
		init();
	}
    public MyServer(String ip,int p) 
	{
		ipAddress=ip;
		port=p;
		init();
	}
    
	private boolean initLogger()
	{
		boolean result=false;
		Properties logp = new Properties();
		try 
		{	
			logp.load(new FileReader("conf/log4j.properties"));
			PropertyConfigurator.configure(logp);
			logger=Logger.getLogger("My Ftp server");
			logger.info("Log4j is ready.");			
			result=true;	
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("file log4j.properties not found:"+e.getMessage());
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("An exception occur when loading file log4j.properties.");
		}
		return result;	
	}

	public synchronized boolean isOverConnectionLimit()
	{
		if (connectionCount<maxConnectionCount)
		{	
			connectionCount++;
			return false;
		}
		else			
			return true;
	}
	public synchronized void sessionClose()
	{
		connectionCount--;
	}
	public void start()
	{	// TODO Auto-generated method stub
        try 
        {
            ServerBootstrap bootStrap = new ServerBootstrap();
            bossGroup=new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            bootStrap.group(bossGroup, workerGroup);
            bootStrap.channel(NioServerSocketChannel.class);
            bootStrap.childHandler(new SessionInitializer(this));
            InetSocketAddress inSocketAddress=new InetSocketAddress(ipAddress,port);         
            //Channel ch = bootStrap.bind(inSocketAddress).sync().channel();
            bootStrap.bind(inSocketAddress);
            logger.info("Server Started");
            logger.info("Server listening " +ipAddress+":" + port);
        } 
        catch (Exception e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.stop();
		}
        finally 
        {
        	
        }
	}
	public void stop()
	{
		bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        System.out.println("Server shutdown gracefully.");
	}
	public Configuration getConfig()
	{
		return config;
	}
	public static void main(String[] args) throws Exception 
	{
		MyServer m=new MyServer();
		m.start();
	}
}
