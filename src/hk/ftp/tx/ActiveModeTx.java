package hk.ftp.tx;

import java.nio.file.Path;

import hk.ftp.Configuration;
import hk.ftp.handler.FtpSession;
import hk.ftp.initializer.TransferFileInitializer;
import hk.ftp.initializer.TransferFileNameListInitializer;
import hk.ftp.listener.TransferFileCompleteListener;
import hk.ftp.listener.TransferFileNameListCompleteListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ActiveModeTx 
{
	private Bootstrap b=null;
	private FtpSession fs=null;
	private Configuration config=null;
	private EventLoopGroup group =null;
		
	public ActiveModeTx(FtpSession fs)
	{
        // Configure the client.
		this.fs=fs;
		this.config=fs.getConfig();
		b = new Bootstrap();
		group=new NioEventLoopGroup();
		b.group(group);
		b.channel(NioSocketChannel.class);
		b.option(ChannelOption.TCP_NODELAY, true);
    }
	public void transferFileNameList(StringBuilder fileNameList,ChannelHandlerContext ctx)
	{
        ChannelFuture f=null;
		try 
		{
			b.handler(new TransferFileNameListInitializer(fileNameList,ctx,fs));
			// Start the client.
			f = b.connect(fs.getClientIp(), fs.getClientDataPortNo()).sync();
		    // Wait until the connection is closed.
	        f.channel().closeFuture().sync();

		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
            // Shut down the event loop to terminate all threads.
            if (b!=null)
            	b=null;
			if (group!=null)
            {	
            	group.shutdownGracefully();
            	group=null;
            }
            if (f!=null)
            {
            	f=null;
            }
        }
 	}
	public void transferFile(String serverPath,ChannelHandlerContext ctx)
	{
        ChannelFuture f=null;
		try 
		{
			b.handler(new TransferFileInitializer(serverPath,ctx,fs));
			// Start the client.
			f = b.connect(fs.getClientIp(), fs.getClientDataPortNo()).sync();
		    // Wait until the connection is closed.
	        f.channel().closeFuture().sync();

		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
            // Shut down the event loop to terminate all threads.
            if (b!=null)
            	b=null;
			if (group!=null)
            {	
            	group.shutdownGracefully();
            	group=null;
            }
            if (f!=null)
            {
            	f=null;
            }
        }
		
	}
}
