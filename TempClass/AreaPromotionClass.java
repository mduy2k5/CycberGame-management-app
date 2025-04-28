package tempclass;

public class AreaPromotionClass {
    private String maKMKV;
    private String maKV;
    private String maCTR;

    // Constructor
    public AreaPromotionClass() {}

    public AreaPromotionClass(String maKMKV, String maKV, String maCTR, boolean isDelete) {
        this.maKMKV = maKMKV;
        this.maKV = maKV;
        this.maCTR = maCTR;
    }

    // Getters and Setters
    public String getMaKMKV() {
        return maKMKV;
    }

    public void setMaKMKV(String maKMKV) {
        this.maKMKV = maKMKV;
    }

    public String getMaKV() {
        return maKV;
    }

    public void setMaKV(String maKV) {
        this.maKV = maKV;
    }

    public String getMaCTR() {
        return maCTR;
    }

    public void setMaCTR(String maCTR) {
        this.maCTR = maCTR;
    }
}
