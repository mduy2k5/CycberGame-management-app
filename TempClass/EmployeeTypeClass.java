package tempclass;

public class EmployeeTypeClass {
    private String maLoaiNV;
    private String tenLoaiNV;
    private String moTa;

    // Constructor
    public EmployeeTypeClass() {}

    public EmployeeTypeClass(String maLoaiNV, String tenLoaiNV, String moTa) {
        this.maLoaiNV = maLoaiNV;
        this.tenLoaiNV = tenLoaiNV;
        this.moTa = moTa;
    }

    // Getters and Setters
    public String getMaLoaiNV() {
        return maLoaiNV;
    }

    public void setMaLoaiNV(String maLoaiNV) {
        this.maLoaiNV = maLoaiNV;
    }

    public String getTenLoaiNV() {
        return tenLoaiNV;
    }

    public void setTenLoaiNV(String tenLoaiNV) {
        this.tenLoaiNV = tenLoaiNV;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
