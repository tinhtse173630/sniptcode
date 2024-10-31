package com.test.sniptcode.model;

public class Model_2 {
    private long   IDTacgia       ;
    private String TenTacgia    ;
    private String Email        ;
    private String Diachi       ;
    private String dienthoai    ;

    public Model_2(String tenTacgia, String email, String diachi, String dienthoai) {
        this.TenTacgia = tenTacgia;
        this.Email = email;
        this.Diachi = diachi;
        this.dienthoai = dienthoai;
    }

    public long getIDTacgia() {
        return IDTacgia;
    }

    public void setIDTacgia(long IDTacgia) {
        this.IDTacgia = IDTacgia;
    }

    public String getTenTacgia() {
        return TenTacgia;
    }

    public void setTenTacgia(String tenTacgia) {
        this.TenTacgia = tenTacgia;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        this.Diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }
}
