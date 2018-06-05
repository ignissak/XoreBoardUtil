package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.Sidebar;
import net.minecord.xoreboardutil.SidebarType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class XoreBoardPlayerSidebar implements Sidebar {

    private @NotNull final org.bukkit.entity.Player player;

    private @NotNull XoreBoard xoreBoard;
    private String displayName;
    private ConcurrentHashMap<String, Integer> lineKeys = new ConcurrentHashMap<String, Integer>();

    private boolean showedStatus, showedGlobalStatus = false;

    XoreBoardPlayerSidebar(@NotNull XoreBoard xoreboard, @NotNull org.bukkit.entity.Player player) {
        this.xoreBoard = xoreboard;
        this.player = player;
    }

    /**
     * public XoreBoard getXoreBoard()
     * @return XoreBoard
     */

    @NotNull
    public XoreBoard getXoreBoard() {
        return this.xoreBoard;
    }

    /**
     * public final Player getPlayer()
     * @return Player
     */

    @NotNull
    public final Player getPlayer() {
        return this.player;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public void setDisplayName(@NotNull String displayName) {
        if(getPlayer().isOnline() == false) return;
        String tempDisplayName = org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName);
        if(getDisplayName().equals(tempDisplayName)) return;
        if(displayName.length() > 32) {
            return;
        }

        // Packet

        this.displayName = tempDisplayName;
    }

    @Override
    public void putLine(@NotNull String lineKey, int value) {
        if(getPlayer().isOnline() == false) return;

        // Packet

        this.lineKeys.put(lineKey, value);
    }

    @Override
    public void setLines(HashMap<String, Integer> lineKeys) {
        if(getPlayer().isOnline() == false) return;
        this.lineKeys.forEach((lineKey, value) -> {
            if(lineKeys.containsKey(lineKey) && lineKeys.get(lineKey).equals(value)) lineKeys.remove(lineKey);
        });

        // Packet

        this.lineKeys.putAll(lineKeys);
    }

    @Override
    public HashMap<String, Integer> getLines() {
        return new HashMap<String, Integer>(this.lineKeys);
    }

    @Override
    public void rewriteLine(@NotNull String lineKey, int value) {
        if(getPlayer().isOnline() == false) return;
        if(this.lineKeys.containsKey(lineKey)) {

        } else putLine(lineKey, value);
    }

    @Override
    public void rewriteLines(HashMap<String, Integer> lineKeys) {
        if(getPlayer().isOnline() == false) return;
    }

    @Override
    public void clearLine(@NotNull String lineKey) {
        if(getPlayer().isOnline() == false) return;
    }

    @Override
    public void clearLines() {
        if(getPlayer().isOnline() == false) return;
    }

    @Override
    public void hideSidebar() {
        if(getPlayer().isOnline() == false) return;
    }

    @Override
    public void showSidebar() {
        if(getPlayer().isOnline() == false) return;
    }

    @Override
    public boolean isShowed() {
        return getPlayer().isOnline() && this.showedStatus;
    }

    public boolean isShowedGlobal() {
        return getPlayer().isOnline() && this.showedGlobalStatus;
    }

    @Override
    public void setShowedSidebar(boolean showedStatus) {
        if(getPlayer().isOnline() == false) return;

        this.showedStatus = showedStatus;
    }

    /**
     * public void setShowedGlobalSidebar(boolean showedGlobalStatus)
     * @param showedGlobalStatus boolean {@link Boolean {@value showedGlobalStatus}}
     */

    public void setShowedGlobalSidebar(boolean showedGlobalStatus) {
        if(getPlayer().isOnline() == false) return;

        this.showedGlobalStatus = showedGlobalStatus;
    }

    @Override
    public SidebarType getType() {
        return SidebarType.PLAYER;
}}