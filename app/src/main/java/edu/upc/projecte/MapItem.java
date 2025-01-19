package edu.upc.projecte;

public class MapItem {
    public String _itemName; // Campo privado con prefijo _
    public float _PosX, _PosY;
    public Boolean active;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MapItem(String name, int qty, float posX, float posY, boolean active)
    {
        _itemName = name;  // Asignación al campo privado
        _PosX = posX;
        _PosY = posY;
        this.active = active;
    }
}
