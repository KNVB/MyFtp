package hk.ftp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class FtpSession  extends SimpleChannelInboundHandler<String>
{
	private int clientDataPortNo=-1;
	private boolean isLogined=false;
	private User user=null;
	private Channel ch=null;
	
	private MyServer myServer=null;
	private Configuration config=null;
	private String commandString=null;
	
	private org.apache.log4j.Logger logger=null;
	private FtpCommandHandler ftpCommandHandler=null; 
	private String userName=new String(),clientIP=new String();
	private String currentPath=new String(),txMode="I";
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
		ftpCommandHandler.doCommand(ctx,commandString, logger);
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
	public void setTransferMode(String mode) 
	{
		// TODO Auto-generated method stub
		txMode=mode;
	}
}
