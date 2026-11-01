/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author piannn
 */
public class Team {
    private int id;
    private String teamName;
    private String leaderUsername;
    private ArrayList<String> members;

    public Team(int id,
                String teamName,
                String leaderUsername) {

        this.id = id;
        this.teamName = teamName;
        this.leaderUsername = leaderUsername;
        this.members = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getLeaderUsername() {
        return leaderUsername;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void addMember(String username) {
        members.add(username);
    }

    public void removeMember(String username) {
        members.remove(username);
    }
    
    public boolean isMember(String username){
        return members.contains(username);
    }
}
