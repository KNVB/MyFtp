package hk.ftp.tx;

import java.net.InetSocketAddress;
import java.nio.file.Path;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import hk.ftp.Configuration;
import hk.ftp.initializer.TransferFileInitializer;
import hk.ftp.initializer.TransferFileNameListInitializer;

public class ActiveModeTx 
{
	private final String host;
	private final int port;
	private Bootstrap b=null;
	private EventLoopGroup group=null;
	private Configuration config;
	public ActiveModeTx(String clientIp, int clientDataPortNo,Configuration c) 
	{
		group = new NioEventLoopGroup();
		b = new Bootstrap();
		b.group(group);
		b.channel(NioSocketChannel.class);
		b.remoteAddress(new InetSocketAddress(clientIp, clientDataPortNo));
		b.option(ChannelOption.TCP_NODELAY, true);
		
		this.host = clientIp;
		this.port = clientDataPortNo;
		this.config=c;
	}
	public void transferFileNameList(StringBuilder fileNameList,ChannelHandlerContext ctx) 
	{
		try 
		{
			b.handler(new TransferFileNameListInitializer(fileNameList,ctx,host,config));
			ChannelFuture f = b.connect(host, port).sync();
		}
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			group.shutdownGracefully();
		}
	}
	public void transferFile(Path path, ChannelHandlerContext ctx,String txMode) 
	{
		// TODO Auto-generated method stub
		try 
		{
			b.handler(new TransferFileInitializer(path,ctx,host,config,txMode));
			ChannelFuture f = b.connect(host, port).sync();
		}
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			group.shutdownGracefully();
		}
		
	}
}
