package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="members")
public class Members {

    @XmlElement(required = true, name="player")
    @JsonProperty("players")
    private List<String> bans;

    public List<String> getPlayer() {
        return bans;
    }

    public void setPlayer(List<String> value) {
        this.bans = value;
    }

    @Override
    public String toString() {
        return "Members{" +
                "player=" + bans +
                '}';
    }
}
