package edu.upc.projecte;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class Partida
{
    public float playerPosX, playerPosY, playerPosZ; // Posición del jugador
    public List<Float> Playerstats = new ArrayList<>(2);
    public float respawnPosX, respawnPosY, respawnPosZ;
    public int coinsCount; // Monedas del jugador
    public String dificulty;
    public float JumpPotions;
    public float SpeedPotions;
    public float MaxHealthPotions;
    public float AttackSpeedPotions;
    public float velocity;
    public float jumpForce;
    public float attackSpeed;

    public List<MapItem> MapItems = new ArrayList<>(5); // Items del mapa
    public List<Enemies> enemies = new ArrayList<>(1);

    // Constructor para inicializar los datos de la partida

    public Partida(int coins, String dificulty, List<MapItem> inventory, List<Enemies> enemies, List<Float> playerstats, float playerposX, float playerposY, float playerposZ, float respawnposX, float respawnposY, float respawnposZ, float jumpPotions, float speedPotions, float maxHealthPotions, float attackSpeedPotions, float velocity, float jumpForce, float attackSpeed)
    {
        this.coinsCount = coins;
        this.respawnPosX = respawnposX;
        this.respawnPosY = respawnposY;
        this.respawnPosZ = respawnposZ;
        this.MapItems = inventory;
        this.enemies = enemies;
        this.dificulty = dificulty;
        this.playerPosX = playerposX;
        this.playerPosY = playerposY;
        this.playerPosZ = playerposZ;
        this.Playerstats = playerstats;
        JumpPotions = jumpPotions;
        SpeedPotions = speedPotions;
        MaxHealthPotions = maxHealthPotions;
        AttackSpeedPotions = attackSpeedPotions;
        this.velocity = velocity;
        this.jumpForce = jumpForce;
        this.attackSpeed = attackSpeed;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
