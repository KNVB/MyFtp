package hk.ftp.tx;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.file.Path;

import hk.ftp.Configuration;
import hk.ftp.initializer.TransferFileInitializer;
import hk.ftp.initializer.TransferFileNameListInitializer;

public class ActiveModeTx_org 
{
	private final String host;
	private final int port;
	private Configuration config;
	public ActiveModeTx_org(String clientIp, int clientDataPortNo,Configuration c) 
	{
		// TODO Auto-generated constructor stub
		this.host = clientIp;
		this.port = clientDataPortNo;
		this.config=c;
	}
	public void transferFileNameList(StringBuilder fileNameList,ChannelHandlerContext ctx) 
	{
		// TODO Auto-generated method stub
		EventLoopGroup group = new NioEventLoopGroup();
		try 
		{
			Bootstrap b = new Bootstrap();
			b.group(group);
			b.channel(NioSocketChannel.class);
			b.remoteAddress(new InetSocketAddress(host, port));
			b.handler(new TransferFileNameListInitializer(fileNameList,ctx,host,config));
			b.option(ChannelOption.TCP_NODELAY, true);
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
		EventLoopGroup group = new NioEventLoopGroup();
		try 
		{
			Bootstrap b = new Bootstrap();
			b.group(group);
			b.channel(NioSocketChannel.class);
			b.remoteAddress(new InetSocketAddress(host, port));
			b.handler(new TransferFileInitializer(path,ctx,host,config,txMode));
			b.option(ChannelOption.TCP_NODELAY, true);
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