package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bans")
public class Bans {

    public Bans() {

    }

    @XmlElement(required = true, name = "player")
    @JsonProperty("players")
    private List<String> bans;

    public void setPlayer(List<String> value) {
        this.bans = value;
    }

    @Override
    public String toString() {
        return "Bans{" +
                "player=" + bans +
                '}';
    }
}