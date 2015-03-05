package hk.ftp.tx;

import java.nio.file.Path;

import hk.ftp.Configuration;
import hk.ftp.FtpSession;
import hk.ftp.handler.ActiveModeFileTransferHandler;
import hk.ftp.listener.ActiveTxCompleteListener;

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

public class ActiveModeFileTx 
{
	public ActiveModeFileTx(FtpSession fs,Path filePath, ChannelHandlerContext responseCtx)
	{
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        final ActiveModeFileTransferHandler abc=new ActiveModeFileTransferHandler(fs,filePath,responseCtx);
        final TransferFileNameListCompleteListener t=new TransferFileNameListCompleteListener(fs.getConfig(),fs.getClientIp(),responseCtx);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     ch.closeFuture().addListener(t);
                     p.addLast("streamer",new ChunkedWriteHandler());
                     p.addLast("handler",abc);
                 }
             });

            // Start the client.
            ChannelFuture f = b.connect(fs.getClientIp(), fs.getClientDataPortNo()).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } 
        catch (InterruptedException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
		
		
    }
}
