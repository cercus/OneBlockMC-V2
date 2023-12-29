package fr.cercusmc.oneblockmc.utils;

import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Logger {

    private String prefix;
    private String message;
    private java.util.logging.Logger log;

    public Logger() {
        this("", "");
    }

    public Logger(String prefix, String message) {
        this.prefix = prefix;
        this.message = message;
        this.log = Bukkit.getLogger();
    }

    public Logger(String message) {
        this("", message);
    }

    public void info() {
        info(this.message);
    }

    public void warn() {
        warn(this.message);
    }

    public void error() {
        error(this.message);
    }

    public void log(Level level) {
        this.log.log(level, this.message);
    }

    public void log(Level level, String message) {
        this.log.log(level, message);
    }

    /**
     * Send a message in console with level info
     * @param message the message
     */
    public void info(String message){
        this.log.log(Level.INFO, () -> this.prefix + " " + message);
    }

    /**
     * Send a message in console with level warning
     * @param message the message
     */
    public void warn(String message){
        this.log.log(Level.WARNING, () -> this.prefix + " " + message);
    }

    /**
     * Send a message in console with level error
     * @param message the message
     */
    public void error(String message){
        this.log.log(Level.SEVERE, () -> this.prefix + " " + message);
    }

}
