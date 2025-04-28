package tempclass;

public class PromotionClass {
    private String maCTR;
    private String tenCTR;
    private String loaiCTR;
    private java.util.Date ngayBD;
    private java.util.Date ngayKT;
    private double chietKhau;

    // Constructor
    public PromotionClass() {}

    public PromotionClass(String maCTR, String tenCTR, String loaiCTR, java.util.Date ngayBD, java.util.Date ngayKT, double chietKhau) {
        this.maCTR = maCTR;
        this.tenCTR = tenCTR;
        this.loaiCTR = loaiCTR;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.chietKhau = chietKhau;
    }

    // Getters and Setters
    public String getMaCTR() {
        return maCTR;
    }

    public void setMaCTR(String maCTR) {
        this.maCTR = maCTR;
    }

    public String getTenCTR() {
        return tenCTR;
    }

    public void setTenCTR(String tenCTR) {
        this.tenCTR = tenCTR;
    }

    public String getLoaiCTR() {
        return loaiCTR;
    }

    public void setLoaiCTR(String loaiCTR) {
        this.loaiCTR = loaiCTR;
    }

    public java.util.Date getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(java.util.Date ngayBD) {
        this.ngayBD = ngayBD;
    }

    public java.util.Date getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(java.util.Date ngayKT) {
        this.ngayKT = ngayKT;
    }

    public double getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(double chietKhau) {
        this.chietKhau = chietKhau;
    }
}
