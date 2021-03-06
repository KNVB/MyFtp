package hk.ftp.handler;

import hk.ftp.Configuration;
import hk.ftp.FtpCommandHandler;
import hk.ftp.MyServer;
import hk.ftp.PassiveServer;
import hk.ftp.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class FtpSession  extends SimpleChannelInboundHandler<String>
{
	private int clientDataPortNo=-1;
	private boolean isLogined=false;
	private User user=null;
	private MyServer myServer=null;
	private Configuration config=null;
	private String commandString=null;
	
	private org.apache.log4j.Logger logger=null;
	private FtpCommandHandler ftpCommandHandler=null; 
	private String userName=new String(),clientIP=new String();
	private String currentPath=new String(),txMode="I";
	private ChannelHandlerContext passiveChannelContext=null;
	private PassiveServer passiveServer=null;
	public boolean isPassiveModeTransfer;
	public FtpSession(MyServer mS,String remoteIp)
	{
		super();
		this.myServer=mS;
		this.config=myServer.getConfig();
		this.logger=config.getLogger();
		this.clientIP=remoteIp;
		this.ftpCommandHandler=new FtpCommandHandler(this);
		this.isPassiveModeTransfer=false;
		this.currentPath="/";
		logger.debug("Client IP:"+clientIP);
	}
	public void channelRead0(ChannelHandlerContext ctx, String msg) 
	{
		// TODO Auto-generated method stub
		commandString=msg.trim();
		logger.info("commandString="+commandString);
		String commands[]=commandString.split("\n");
		for (String command:commands)
		{
			logger.debug("command:"+command);
			ftpCommandHandler.doCommand(ctx,command.trim(), logger);
		}
	}
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) 
    {
        System.out.println(FtpSession.class.getName()+" exception occur:");
    	cause.printStackTrace();
    }
 	public Configuration getConfig()
	{
		return this.config;
	}
    public void setIsLogined(boolean l)
	{
		isLogined=l;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String s)
	{
		userName=s;
	}	
	public User getUser()
	{
		return user;
	}
	public void setUser(User u)
	{
		user=u;		
	}
	public void setCurrentPath(String p)
	{
		currentPath=p;
	}
	public String getCurrentPath()
	{
		return currentPath;
	}
	public boolean isLogined() 
	{
		// TODO Auto-generated method stub
		return isLogined;
	}
	public int getClientDataPortNo() {
		return clientDataPortNo;
	}
	public void setClientDataPortNo(int clientDataPortNo) {
		this.clientDataPortNo = clientDataPortNo;
	}
	public String getClientIp()
	{
		return clientIP;
	}
	public String getTransferMode()
	{
		return txMode;
	}
	public void setTransferMode(String mode) 
	{
		// TODO Auto-generated method stub
		txMode=mode;
	}
	public void setPassiveChannelContext(ChannelHandlerContext  ctx) 
	{
		// TODO Auto-generated method stub
		this.passiveChannelContext=ctx;
	}	
	public ChannelHandlerContext getPassiveChannelContext() 
	{
		// TODO Auto-generated method stub
		return this.passiveChannelContext;
	}
	public void returnPassivePort(int port) 
	{
		// TODO Auto-generated method stub
		myServer.returnPassivePort(port);
	}	
	public void setPassiveServer(PassiveServer passiveServer) 
	{
		// TODO Auto-generated method stub
		this.passiveServer=passiveServer;
	}
	public PassiveServer getPassiveServer() 
	{
		// TODO Auto-generated method stub
		return this.passiveServer;
	}
	
	public MyServer getServer() 
	{
		return myServer;
	}
}
