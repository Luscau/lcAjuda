package eu.luscau.commands;

import eu.luscau.Ajuda;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;

public class CommandAjuda implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;

        if(args.length == 0){
            p.sendMessage(Ajuda.getPlugin().getConfig().getString("Mensagens.Ajuda.use-ajuda")
                    .replace("&", "§"));
            return true;
        }

        if(args[0].equalsIgnoreCase("reload") && p.hasPermission("ajuda.reload")){
            Ajuda.getPlugin().reloadConfig();
            p.sendMessage("§aConfig recarregada com sucesso.");
            return true;
        }

        if(Ajuda.pendent.contains(p)){
            p.sendMessage(Ajuda.getPlugin().getConfig().getString("Mensagens.Ajuda.duvida-pendente")
                    .replace("&", "§"));
            return true;
        }

        String allArgs = String.join(" ", args);
        Ajuda.hash.put(p, allArgs);
        Ajuda.pendent.add(p);
        List<String> a = Ajuda.getPlugin().getConfig().getStringList("Mensagens.Ajuda.duvida-enviada");
        for(String l : a){
            p.sendMessage(l.replace("&", "§"));
        }

        for(Player on : Bukkit.getOnlinePlayers()){
            if(on.hasPermission("ajuda.receber")){
                List<String> b = Ajuda.getPlugin().getConfig().getStringList("Mensagens.Ajuda.duvida-recebida");
                for(String m : b){
                    on.sendMessage(m.replace("&", "§")
                            .replace("{autor}", p.getName())
                            .replace("{duvida}", Ajuda.hash.get(p)));
                }
            }
        }

        return false;
    }

}
