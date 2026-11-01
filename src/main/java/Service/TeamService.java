/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.util.ArrayList;
import model.*;

/**
 *
 * @author piannn
 */
public class TeamService {

    private static TeamService instance;

    private ArrayList<Team> teams;
    private int nextId = 1;

    private UserService userService;

    private TeamService() {

        teams = new ArrayList<>();
        userService = UserService.getInstance();
    }

    public static TeamService getInstance() {

        if (instance == null) {
            instance = new TeamService();
        }

        return instance;
    }

    // ================= CREATE TEAM =================
    public void createTeam(String teamName, String leaderUsername) {

        Team team = new Team(
                nextId++,
                teamName,
                leaderUsername
        );

        teams.add(team);

        userService.promoteToLeader(leaderUsername);

        System.out.println("Team berhasil dibuat.");
    }

    // ================= JOIN TEAM =================
    public boolean joinTeam(int teamId, String username) {

        Team team = getTeamById(teamId);

        if (team == null) {

            System.out.println("Team tidak ditemukan.");
            return false;
        }

        if (team.getMembers().contains(username)) {

            System.out.println("User sudah menjadi member.");
            return false;
        }

        team.addMember(username);

        userService.promoteToMember(username);

        System.out.println("Berhasil join team.");

        return true;
    }

    // ================= LEAVE TEAM =================
    public void leaveTeam(int teamId, String username) {

        Team team = getTeamById(teamId);

        if (team == null) {

            System.out.println("Team tidak ditemukan.");
            return;
        }

        if (!team.getMembers().contains(username)) {

            System.out.println("User bukan member.");
            return;
        }

        team.removeMember(username);

        userService.backToUser(username);

        System.out.println("Berhasil keluar dari team.");
    }

    // ================= ADD MEMBER =================
    public void addMember(int teamId, String username) {

        Team team = getTeamById(teamId);

        if (team == null) {

            System.out.println("Team tidak ditemukan.");
            return;
        }

        if (team.getMembers().contains(username)) {

            System.out.println("Member sudah ada.");
            return;
        }

        team.addMember(username);

        userService.promoteToMember(username);

        System.out.println("Member berhasil ditambahkan.");
    }

    // ================= REMOVE MEMBER =================
    public void removeMember(int teamId, String username) {

        Team team = getTeamById(teamId);

        if (team == null) {

            System.out.println("Team tidak ditemukan.");
            return;
        }

        if (!team.getMembers().contains(username)) {

            System.out.println("Member tidak ditemukan.");
            return;
        }

        team.removeMember(username);

        userService.backToUser(username);

        System.out.println("Member berhasil dihapus.");
    }

    // ================= LIST TEAM =================
    public void listTeams() {

        System.out.println("\n========================================================================================================");
        System.out.printf("| %-4s | %-20s | %-20s | %-10s |\n",
                "ID",
                "TEAM",
                "LEADER",
                "MEMBER");
        System.out.println("========================================================================================================");

        if (teams.isEmpty()) {

            System.out.printf("| %-100s |\n", "BELUM ADA TEAM");
            System.out.println("========================================================================================================");
            return;
        }

        for (Team team : teams) {

            System.out.printf("| %-4d | %-20s | %-20s | %-10d |\n",
                    team.getId(),
                    team.getTeamName(),
                    team.getLeaderUsername(),
                    team.getMembers().size());
        }

        System.out.println("========================================================================================================");
    }

    // ================= LIST MEMBER =================
    public void listMembers(int teamId) {

        Team team = getTeamById(teamId);

        if (team == null) {

            System.out.println("Team tidak ditemukan.");
            return;
        }

        System.out.println("\n==============================================================");
        System.out.println("TEAM : " + team.getTeamName());
        System.out.println("==============================================================");

        if (team.getMembers().isEmpty()) {

            System.out.println("Belum ada member.");
            return;
        }

        System.out.printf("| %-3s | %-25s |\n",
                "NO",
                "MEMBER");

        System.out.println("--------------------------------------------------------------");

        int no = 1;

        for (String member : team.getMembers()) {

            System.out.printf("| %-3d | %-25s |\n",
                    no++,
                    member);
        }

        System.out.println("==============================================================");
    }

    // ================= GET MEMBER =================
    public ArrayList<String> getMembers(int teamId) {

        Team team = getTeamById(teamId);

        if (team == null) {

            return new ArrayList<>();
        }

        return team.getMembers();
    }

    // ================= GET TEAM BY LEADER =================
    public Team getTeamByLeader(String username) {

        for (Team team : teams) {

            if (team.getLeaderUsername().equalsIgnoreCase(username)) {

                return team;
            }
        }

        return null;
    }

    // ================= GET TEAM BY MEMBER =================
    public Team getTeamByMember(String username) {

        for (Team team : teams) {

            if (team.isMember(username)) {

                return team;
            }
        }

        return null;
    }

    // ================= GET TEAM BY ID =================
    public Team getTeamById(int id) {

        for (Team team : teams) {

            if (team.getId() == id) {

                return team;
            }
        }

        return null;
    }

    // ================= GET ALL TEAM =================
    public ArrayList<Team> getTeams() {

        return teams;
    }
}
