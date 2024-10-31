package com.test.sniptcode.model;

public class Model_1 {
    private long   IDsach   ;
    private String Tensach  ;
    private String NgayXB   ;
    private String Theloai  ;
    private String idTacgia ;

    public Model_1(String tensach, String ngayXB, String theloai, String idTacgia) {
        this.Tensach = tensach;
        this.NgayXB = ngayXB;
        this.Theloai = theloai;
        this.idTacgia = idTacgia;
    }

    public long getIDsach() {
        return IDsach;
    }

    public void setIDsach(long IDsach) {
        this.IDsach = IDsach;
    }

    public String getTensach() {
        return Tensach;
    }

    public void setTensach(String tensach) {
        this.Tensach = tensach;
    }

    public String getNgayXB() {
        return NgayXB;
    }

    public void setNgayXB(String ngayXB) {
        this.NgayXB = ngayXB;
    }

    public String getTheloai() {
        return Theloai;
    }

    public void setTheloai(String theloai) {
        this.Theloai = theloai;
    }

    public String getIdTacgia() {
        return idTacgia;
    }

    public void setIdTacgia(String idTacgia) {
        this.idTacgia = idTacgia;
    }
}
