package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Island {

    public Island() {

    }

    @JsonProperty("bans")
    private Bans bans;


    @JsonProperty("members")
    private Members members;

    @JsonProperty("biome")
    private String biome;

    @JsonProperty("customName")
    private String customName;

    @JsonProperty("locations")
    private Locations locations;

    @JsonProperty("stats")
    private Stats stats;

    private String id;

    public Bans getBans() { return bans; }
    public void setBans(Bans value) { this.bans = value; }

    public Members getMembers() { return members; }
    public void setMembers(Members value) { this.members = value; }

    public String getBiome() { return biome; }
    public void setBiome(String value) { this.biome = value; }

    public String getCustomName() { return customName; }
    public void setCustomName(String value) { this.customName = value; }

    public Locations getLocations() { return locations; }
    public void setLocations(Locations value) { this.locations = value; }

    public Stats getStats() { return stats; }
    public void setStats(Stats value) { this.stats = value; }


    public String getId() { return id; }
    public void setId(String value) { this.id = value; }

    @Override
    public String toString() {
        return "Island{" +
                "bans=" + bans +
                ", members=" + members +
                ", biome='" + biome + '\'' +
                ", customName='" + customName + '\'' +
                ", locations=" + locations +
                ", stats=" + stats +
                ", id='" + id + '\'' +
                '}';
    }
}


