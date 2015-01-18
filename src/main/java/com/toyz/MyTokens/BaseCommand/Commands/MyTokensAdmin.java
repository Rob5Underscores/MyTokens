package com.toyz.MyTokens.BaseCommand.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.toyz.MyTokens.MyTokens;
import com.toyz.MyTokens.BaseCommand.BaseCommand;
import com.toyz.MyTokens.BaseCommand.Handler.IssueCommands;
import com.toyz.MyTokens.sql.SQLhandler;

public class MyTokensAdmin extends BaseCommand {
	private static IssueCommands _cmd = null;
	private static String _Permission = "mytokens.admin";
	private static String _invaidUsage = "Invalid Args - usage:";
	
	public static Boolean Fire(IssueCommands info){
		_cmd = info;
		if(_cmd.isPlayer()){
			if(_cmd.getPlayer().hasPermission(_Permission))
			{
				Trigger();
				return true;
			}else{
				sendMessage("You do not have permission to run this command");
			}
		}
		if(_cmd.isConsole()){
			Trigger();
		}
		return true;
	}
	
	private static void Trigger(){
		if((_cmd.getArgs().length <= 0 || _cmd.getArg(0).equalsIgnoreCase("?") || _cmd.getArg(0).equalsIgnoreCase("help"))){
			for(String msg : MyTokens.HELP_COMMANDS_ADMIN){
				sendMessage(MyTokens.getAPI().getMessageHelper().format(null, msg));
			}
			return;
		}
		if(_cmd.getArg(0).equalsIgnoreCase("bal")){
			if(_cmd.isPlayer()){
				if(!_cmd.getPlayer().hasPermission(_Permission + ".userbal")){
					if(!_cmd.getPlayer().isOp()){
						sendMessage("You do not have permission to run this command.");
						return;
					}
				}
			}
			
			if(_cmd.getArgs().length >= 2){
				Player toGet = Bukkit.getPlayer(_cmd.getArg(1));
				
				if(toGet != null)
					sendMessage(ChatColor.BLUE + toGet.getName() + " (Nick: " + toGet.getDisplayName()+ ") "
							+ ChatColor.WHITE + "has " + MyTokens.getAPI().getSqlHandler().GetBalance(toGet) + " Tokens.");
				else{
					sendMessage(MyTokens.getAPI().getMessageHelper().format(null, "&4Player is currently offline"));
				}
			}else{
				sendMessage(_invaidUsage + " /mytokens bal user");
			}
		}
		
		if(_cmd.getArg(0).equalsIgnoreCase("reload")){
			if(_cmd.isPlayer()){
				if(!_cmd.getPlayer().hasPermission(_Permission + ".reload")){
					if(!_cmd.getPlayer().isOp()){
						sendMessage("You do not have permission to run this command");
						return;
					}
				}
			}
			sendMessage("Reloading MyTokens Config");
			MyTokens.getAPI().reload();
			sendMessage("MyTokens Config reloaded");
		}
		
		if(_cmd.getArg(0).equalsIgnoreCase("give")){
			if(_cmd.isPlayer()){
				if(!_cmd.getPlayer().hasPermission(_Permission + ".give")){
					if(!_cmd.getPlayer().isOp()){
						sendMessage("You do not have permission to run this command");
						return;
					}
				}
			}
				if(_cmd.getArgs().length >= 3){
					Player givee = Bukkit.getPlayer(_cmd.getArg(1));
					if(givee != null){
						SQLhandler sql = MyTokens.getAPI().getSqlHandler();
						int Giving = 0;
						try{
							Giving = Integer.valueOf(_cmd.getArg(2)).intValue();
						}catch (Exception e){
							sendMessage("Must be a number");
							return;
						}
						
						if(Giving <= 0){
							sendMessage(MyTokens.getAPI().getMessageHelper().format(null, "&4You must send more then 0 tokens!"));
							return;
						}
						
						int Current = sql.GetBalance(givee);
						Current = Giving + Current;

						sql.SetBalance(givee, Current);
                        String message = MyTokens.getAPI().getConfig().getString("settings.command-messages.console-give");
						givee.sendMessage(MyTokens.getAPI().getMessageHelper().format(null, message, Giving + ""));
                        String title = ChatColor.translateAlternateColorCodes('&', MyTokens.getAPI().getConfig().getString("title"));

                        if ((givee.getOpenInventory().getTitle() != null) && (givee.getOpenInventory().getTitle().equalsIgnoreCase(title)))
                        {
                            givee.closeInventory();
                        }
					}else{
						sendMessage(MyTokens.getAPI().getMessageHelper().format(null, "&4Player is currently offline"));
					}
				}else{
					sendMessage(_invaidUsage + " /mytokens give user amount");
				}
			}
		if(_cmd.getArg(0).equalsIgnoreCase("take")){
			if(_cmd.isPlayer()){
				if(!_cmd.getPlayer().hasPermission(_Permission + ".take")){
					if(!_cmd.getPlayer().isOp()){
						sendMessage("You do not have permission to run this command");
						return;
					}
				}
			}
				if(_cmd.getArgs().length >= 3){
					Player givee = Bukkit.getPlayer(_cmd.getArg(1));
					if(givee != null){
						SQLhandler sql = MyTokens.getAPI().getSqlHandler();
						int Giving = 0;
						try{
							Giving = Integer.valueOf(_cmd.getArg(2)).intValue();
						}catch (Exception e){
							sendMessage("Must be a number");
							return;
						}
						
						if(Giving <= 0){
							sendMessage(MyTokens.getAPI().getMessageHelper().format(null, "&4You must send more then 0 tokens!"));
							return;
						}
						
						int Current = sql.GetBalance(givee);
						if(Current < Giving){
							Giving = Current;
						}
						Current = Current - Giving;
						
						//System.out.println(givee.getUniqueId().toString() + " = " + givee.getName() + " - Giving: " + Giving + " - Total: " + Current + "");
						//MyTokens.UserTokens.getConfig().set(givee.getUniqueId().toString(), Current);
						sql.SetBalance(givee, Current);
                        String message = MyTokens.getAPI().getConfig().getString("settings.command-messages.console-take");
						givee.sendMessage(MyTokens.getAPI().getMessageHelper().format(null, message, Giving + ""));
                        String title = ChatColor.translateAlternateColorCodes('&', MyTokens.getAPI().getConfig().getString("title"));

                        if ((givee.getOpenInventory().getTitle() != null) && (givee.getOpenInventory().getTitle().equalsIgnoreCase(title)))
                        {
                            givee.closeInventory();
                        }
					}else{
						sendMessage(MyTokens.getAPI().getMessageHelper().format(null, "&4Player is currently offline"));
					}
				}else{
					sendMessage(_invaidUsage + " /mytokens give user amount");
				}
			}
		if(_cmd.getArg(0).equalsIgnoreCase("reset")){
			if(_cmd.isPlayer()){
				if(!_cmd.getPlayer().hasPermission(_Permission + ".reset")){
					if(!_cmd.getPlayer().isOp()){
						sendMessage("You do not have permission to run this command");
						return;
					}
				}
			}
				if(_cmd.getArgs().length >= 2){
					SQLhandler sql = MyTokens.getAPI().getSqlHandler();
					Player givee = Bukkit.getPlayer(_cmd.getArg(1));

                    if(givee == null){
                        sendMessage(MyTokens.getAPI().getMessageHelper().format(null, "&4Player is currently offline"));
                        return;
                    }

					//MyTokens.UserTokens.getConfig().set(givee.getUniqueId().toString(), 0); 
					sql.SetBalance(givee, 0);
					givee.sendMessage(MyTokens.getAPI().getMessageHelper().format(null, "Your Tokens have been reset to 0"));
                    String title = ChatColor.translateAlternateColorCodes('&', MyTokens.getAPI().getConfig().getString("title"));

                    if ((givee.getInventory().getTitle() != null) && (givee.getInventory().getTitle().equalsIgnoreCase(title)))
                    {
                       givee.closeInventory();
                    }
                }
		}
	}
}