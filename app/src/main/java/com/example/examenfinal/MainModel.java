package com.example.examenfinal;
public class MainModel {
    String Marca, Estado, Retiro, imgURL;
    public MainModel(){

    }
    public MainModel(String marca, String estado, String retiro, String imgURL){
        Marca = marca;
        Estado = estado;
        Retiro = retiro;
        this.imgURL = imgURL;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getRetiro() {
        return Retiro;
    }

    public void setRetiro(String retiro) {
        Retiro = retiro;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
