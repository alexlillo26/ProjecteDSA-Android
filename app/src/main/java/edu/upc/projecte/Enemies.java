package edu.upc.projecte;

public class Enemies {
    public String _itemName; // Campo privado con prefijo _
    public float _PosX, _PosY;
    public float life;

    public String get_itemName() {
        return _itemName;
    }

    public void set_itemName(String _itemName) {
        this._itemName = _itemName;
    }

    public float get_PosX() {
        return _PosX;
    }

    public void set_PosX(float _PosX) {
        this._PosX = _PosX;
    }

    public float get_PosY() {
        return _PosY;
    }

    public void set_PosY(float _PosY) {
        this._PosY = _PosY;
    }

    public float getLife() {
        return life;
    }

    public void setLife(float life) {
        this.life = life;
    }


    public Enemies(String name, float posX, float posY, float life)
    {
        _itemName = name;  // Asignación al campo privado
        _PosX = posX;
        _PosY = posY;
        this.life = life;
    }
}
