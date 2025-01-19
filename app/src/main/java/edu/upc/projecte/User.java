package edu.upc.projecte;

public class User {
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private int age;
    private String partida;
    private int coins;
    private float JumpPotions;
    private float SpeedPotions;
    private float MaxHealthPotions;
    private float AttackSpeedPotions;

    public float getAttackSpeedPotions() {
        return AttackSpeedPotions;
    }

    public void setAttackSpeedPotions(float attackSpeedPotions) {
        AttackSpeedPotions = attackSpeedPotions;
    }

    public float getMaxHealthPotions() {
        return MaxHealthPotions;
    }

    public void setMaxHealthPotions(float maxHealthPotions) {
        MaxHealthPotions = maxHealthPotions;
    }

    public float getSpeedPotions() {
        return SpeedPotions;
    }

    public void setSpeedPotions(float speedPotions) {
        SpeedPotions = speedPotions;
    }

    public float getJumpPotions() {
        return JumpPotions;
    }

    public void setJumpPotions(float jumpPotions) {
        JumpPotions = jumpPotions;
    }


    // Default constructor
    public User() {}

    // Constructor with all parameters
    public User(String id, String username, String password, String fullName, String email, int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.coins = 0;
        this.partida = "";
    }

    // Constructor with username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }
}