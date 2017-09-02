package eu.luscau.commands;

import eu.luscau.Ajuda;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandResponder implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;

        String allArgs = String.join(" ", args);
    if(p.hasPermission("ajuda.responder")) {
        if (args.length == 0) {
            List<String> a = Ajuda.getPlugin().getConfig()
                    .getStringList("Mensagens.Responder.modo-de-uso");
            for (String b : a) {
                p.sendMessage(b.replace("&", "§"));
            }
            return true;
        }

        Player on = Bukkit.getPlayer(args[0]);
        if (on == null) {
            p.sendMessage(Ajuda.getPlugin().getConfig()
                    .getString("Mensagens.Responder.player-offline")
                    .replace("&", "§"));
            return true;
        }

        if (!Ajuda.pendent.contains(on) || !Ajuda.hash.containsKey(on)) {
            p.sendMessage(Ajuda.getPlugin().getConfig()
                    .getString("Mensagens.Responder.nao-pediu-ajuda")
                    .replace("&", "§"));
            return true;
        }

        if (args.length == 1) {
            List<String> a = Ajuda.getPlugin().getConfig()
                    .getStringList("Mensagens.Responder.informacoes");
            for (String b : a) {
                p.sendMessage(b
                        .replace("&", "§")
                        .replace("{autor}", on.getName())
                        .replace("{duvida}", Ajuda.hash.get(on)));
            }
        } else {
            List<String> a = Ajuda.getPlugin().getConfig()
                    .getStringList("Mensagens.Responder.respondida-staff");
            for (String b : a) {
                p.sendMessage(b
                        .replace("&", "§")
                        .replace("{autor}", on.getName())
                        .replace("{duvida}", Ajuda.hash.get(on))
                        .replace("{resposta}", allArgs.replace(on.getName(), "")));
            }
            List<String> c = Ajuda.getPlugin().getConfig()
                    .getStringList("Mensagens.Responder.respondida-autor");
            for (String d : c) {
                on.sendMessage(d
                        .replace("&", "§")
                        .replace("{duvida}", Ajuda.hash.get(on))
                        .replace("{resposta}", allArgs.replace(on.getName(), "")));
            }

            Ajuda.hash.remove(on);
            Ajuda.pendent.remove(on);
        }
    }
        return false;
    }

}
