package tempclass;
public class ComputerClass {
    private String ram;
    private String rom;
    private String cpu;
    private String vga;
    private int soMay;
    private String trangThai;
    private String maKv;

    // Constructor
    public ComputerClass() {}

    public ComputerClass(String ram, String rom, String cpu, String vga, int soMay, String trangThai, String maKv) {
        this.ram = ram;
        this.rom = rom;
        this.cpu = cpu;
        this.vga = vga;
        this.soMay = soMay;
        this.trangThai = trangThai;
        this.maKv = maKv;
    }

    // Getters and Setters
    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }
    
    public int getSoMay() {
        return soMay;
    }


    public void setSoMay(int soMay) {
        this.soMay = soMay;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaKv() {
        return maKv;
    }

    public void setMaKv(String maKv) {
        this.maKv = maKv;
    }
}