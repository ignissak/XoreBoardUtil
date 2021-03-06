package net.minecord.xoreboardutil.bukkit;

import net.minecord.xoreboardutil.PostConstruct;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@lombok.Generated
public class XoreBoardUtil extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {

    @org.jetbrains.annotations.TestOnly
    private static @NotNull HashMap<String, XoreBoard> xoreBoards = new HashMap<String, XoreBoard>();

    private static org.bukkit.scoreboard.Objective objective;
    private static @NotNull org.bukkit.scoreboard.Scoreboard scoreboard;
    private static org.bukkit.scoreboard.Team.OptionStatus nameTag, collisions;

    private static int name, iterator = 0;

    private static Random random = new Random();


    @Override
    @PostConstruct
    public void onEnable() {
        continueEnabling(this);
    }

    /**
     * private void continueEnabling(@NotNull XoreBoardUtil plugin)
     * @param plugin XoreBoardUtil {@link XoreBoardUtil}
     */

    private void continueEnabling(@NotNull XoreBoardUtil plugin) {

        scoreboard = org.bukkit.Bukkit.getScoreboardManager().getMainScoreboard();

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        destroy();

        java.lang.Runtime.getRuntime().gc();
    }

    @org.bukkit.event.EventHandler
    public void on(final @NotNull org.bukkit.event.player.PlayerQuitEvent event) {
        for(@NotNull XoreBoard xoreBoard : XoreBoardUtil.getXoreBoards().values()) {
            if(xoreBoard.getPlayers().contains(event.getPlayer()) == false) continue;
            @NotNull XorePlayer xorePlayer = xoreBoard.getPlayer(event.getPlayer());

            xoreBoard.getPlayers().remove(event.getPlayer());
    }}

    /**
     * public static XoreBoard getXoreBoard()
     * @return XoreBoard
     */

    @NotNull
    public static XoreBoard getXoreBoard() {
        return getXoreBoard(name ++ + "");
    }

    /**
     * public static XoreBoard getXoreBoard(@NotNull String boardName)
     * @param boardName String {@link String}
     * @return XoreBoard
     */

    @NotNull
    public static XoreBoard getXoreBoard(@NotNull String boardName) {
        if(xoreBoards.containsKey(boardName)) return xoreBoards.get(boardName);
        else return createXoreBoard(boardName);
    }

    /**
     * public static XoreBoard getNextXoreBoard()
     * @return XoreBoard
     */

    @NotNull
    public static XoreBoard getNextXoreBoard() {
        return getXoreBoard(name ++ + "");
    }

    /**
     * public static XoreBoard createXoreBoard(@NotNull String boardName)
     * @param boardName String {@link String}
     * @return XoreBoard
     */

    @NotNull
    public static XoreBoard createXoreBoard(@NotNull String boardName) {
        if(xoreBoards.containsKey(boardName)) return xoreBoards.get(boardName);
        else {
            xoreBoards.put(boardName, new XoreBoard(scoreboard, random.nextInt(100) + "." + getRandomPacketID(), boardName));
            return xoreBoards.get(boardName);
    }}

    /**
     * public static void removeXoreBoard(@NotNull String boardName)
     * @param boardName String {@link String}
     */

    public static void removeXoreBoard(@NotNull String boardName) {
        if(xoreBoards.containsKey(boardName) == false) return;
                getXoreBoard(boardName).destroy();
        xoreBoards.remove(boardName);
    }

    /**
     * public static void removeXoreBoard(@NotNull String boardName)
     * @param xoreBoard XoreBoard {@link String}
     */

    public static void removeXoreBoard(@NotNull XoreBoard xoreBoard) {
        if(xoreBoards.containsKey(xoreBoard.getName()) == false) return;
        xoreBoards.remove(xoreBoard.getName());

        xoreBoard.destroy();
    }

    /**
     * public static HashMap<String, XoreBoard> getXoreBoards()
     * @return HashMap<String, XoreBoard>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public static HashMap<String, XoreBoard> getXoreBoards() {
        return xoreBoards;
    }

    /**
     * public static org.bukkit.scoreboard.Scoreboard getScoreboard()
     * @return org.bukkit.scoreboard.Scoreboard
     */

    public static org.bukkit.scoreboard.Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * public static void setCollisionsRule(@NotNull org.bukkit.scoreboard.Team.OptionStatus value)
     * @param value OptionStatus {@link org.bukkit.scoreboard.Team.OptionStatus}
     */

    public static void setCollisionsRule(@NotNull org.bukkit.scoreboard.Team.OptionStatus value) {
        if(value == null) return;

            collisions = value;
        for(@NotNull org.bukkit.scoreboard.Team team : scoreboard.getTeams()) team.setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, value);
    }

    public static void disableCollisionsRule() {
        collisions = org.bukkit.scoreboard.Team.OptionStatus.NEVER;
        for(@NotNull org.bukkit.scoreboard.Team team : scoreboard.getTeams()) team.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
    }

    /**
     * public static void setBellowName(@NotNull String displayName)
     * @param displayName String {@link String}
     */

    public static void setBellowName(@NotNull String displayName) {
        if(displayName == null) return;
        objective = scoreboard.getObjective(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME);
        if(objective == null) {
            objective = scoreboard.registerNewObjective("bellow", "name");
                objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME);
                    objective.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName));
        }
        else objective.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName));
    }

    /**
     * public static void updateBellowName(@NotNull org.bukkit.entity.Player player, int value)
     * @param player Player {@link org.bukkit.entity.Player}
     * @param value int {@link Integer}
     */

    public static void updateBellowName(@NotNull org.bukkit.entity.Player player, int value) {
        if(objective == null || player == null || player.isOnline() == false) return;
        scoreboard.getObjective(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME).getScore(player).setScore(value);
    }

    /**
     * public static org.bukkit.scoreboard.Team getTeam(@NotNull String teamName)
     * @param teamName String {@link String}
     * @return org.bukkit.scoreboard.Team
     */

    public static org.bukkit.scoreboard.Team getTeam(@NotNull String teamName) {
        org.bukkit.scoreboard.Team team = scoreboard.getTeam(teamName) != null ? scoreboard.getTeam(teamName) : scoreboard.registerNewTeam(teamName);
        if(nameTag == null) team.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, nameTag);
            if(collisions == null) team.setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, collisions);
        return team;
    }

    /**
     * public static void setNameTagVisibility(@NotNull org.bukkit.scoreboard.Team.OptionStatus value)
     * @param value org.bukkit.scoreboard.Team.OptionStatus {@link org.bukkit.scoreboard.Team.OptionStatus}
     */

    public static void setNameTagVisible(@NotNull org.bukkit.scoreboard.Team.OptionStatus value) {
        if(value == null) return;

        nameTag = value;
        for(@NotNull org.bukkit.scoreboard.Team team : scoreboard.getTeams()) team.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, value);
    }

    public static void destroy() {
        if(scoreboard != null) {
            for(@NotNull org.bukkit.scoreboard.Team team : scoreboard.getTeams()) {
                for(@NotNull String entryName : team.getEntries()) {
                    team.removeEntry(entryName);
        }}}

        @NotNull ArrayList<XoreBoard> xoreBoards = new ArrayList<XoreBoard>(getXoreBoards().values());
        for(@NotNull XoreBoard xoreBoard : xoreBoards) removeXoreBoard(xoreBoard);
    }

    /**
     * private static int getRandomPacketID()
     * @return int
     */

    private static int getRandomPacketID() {
        return iterator ++;
    }


    /**
     * public static XoreBoardUtil getPlugin()
     * @return XoreBoardUtil
     */

    public static XoreBoardUtil getPlugin() {
        return XoreBoardUtil.getPlugin(XoreBoardUtil.class);
}}