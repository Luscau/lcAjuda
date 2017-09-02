package eu.luscau;

import eu.luscau.commands.CommandAjuda;
import eu.luscau.commands.CommandResponder;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class Ajuda extends JavaPlugin{


    public static HashMap<Player, String> hash = new HashMap<>();
    public static ArrayList<Player> pendent = new ArrayList<>();
    private ConsoleCommandSender c = this.getServer().getConsoleSender();

    @Override
    public void onEnable() {
        sendMessages();
        setupCommands();
        setupConfig();
    }

    private void sendMessages(){
        c.sendMessage(" ");
        c.sendMessage("§alcAjuda - Plugin habilitado com sucesso.");
        c.sendMessage("§aVersão 1.0 - by Luscau");
        c.sendMessage(" ");
    }

    private void setupCommands(){
        this.getCommand("ajuda").setExecutor(new CommandAjuda());
        this.getCommand("responder").setExecutor(new CommandResponder());
    }

    private void setupConfig(){
        saveDefaultConfig();
        saveConfig();
    }

    public static Plugin getPlugin(){
        return getPlugin(Ajuda.class);
    }

}
