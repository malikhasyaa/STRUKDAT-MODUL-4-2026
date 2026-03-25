import java.util.ArrayList;
import java.util.Scanner;

abstract class Tugas {
    protected String namaTugas;
    protected String mataKuliah;
    protected String deadline;
    protected boolean selesai;

    public Tugas(String namaTugas, String mataKuliah, String deadline){
        this.namaTugas = namaTugas;
        this.mataKuliah= mataKuliah;
        this.deadline = deadline;
        this.selesai = selesai;
    }

    public abstract void  tampilkaninfo();
    public abstract int hitungTotalkerjaan();

    public void tandaiSelesai(){
        this.selesai = true;
        System.out.println(" Tugas [" + namaTugas + "] selesai!!");
    }

    public boolean isSelesai(){
        return selesai;
    }
    public String getNamaTugas(){
        return namaTugas;
    }
    public String getDeadline(){
        return deadline;
    }    
    public String getMatakuliah(){
        return mataKuliah;
    }
}

class TugasPrioritas extends Tugas {
    private String catatan;

    public TugasPrioritas(String namaTugas, String mataKuliah, String deadline, String catatan){
        super (namaTugas, mataKuliah, deadline);
        this.catatan = catatan;
    }

    @Override
    public void tampilkaninfo(){
        System.out.println("Tugas Prioritas" + namaTugas);
        System.out.println("   Mata Kuliah : " + mataKuliah);
        System.out.println("   Deadline    : " + deadline);
        System.out.println("   Catatan     : " + catatan);
        System.out.println("   Status      : " + (selesai ? "Selesai" : "Belum - SEGERA KERJAKAN!"));
    }
    @Override
    public int hitungTotalkerjaan(){return 3;}
    public String getCatatan(){
        return catatan;
    }
}

class TugasTanggatLama extends Tugas{
    private String catatan;

    public TugasTanggatLama(String namaTugas, String mataKuliah, String deadline, String catatan){
        super(namaTugas, mataKuliah, deadline);
        this.catatan= catatan;
    }
    @Override
    public void tampilkaninfo() {
        System.out.println(" Tidak Prioritas " + namaTugas);
        System.out.println("   Mata Kuliah : " + mataKuliah);
        System.out.println("   Deadline    : " + deadline);
        System.out.println("   Catatan     : " + catatan);
        System.out.println("   Status      : " + (selesai ? "Selesai" : "Belum - masih ada waktu."));
    }
    @Override
    public int hitungTotalkerjaan(){return 1;}
    public String getCatatan(){
        return catatan;
    }
}

abstract class Lokasi{
    protected String namaLokasi;
    protected int biaya;
    protected int energiTerkuras;
    protected boolean Wifi;

    public Lokasi(String namaLokasi, int biaya, int energiTerkuras, boolean Wifi){
        this.namaLokasi=namaLokasi;
        this.biaya=biaya;
        this.energiTerkuras=energiTerkuras;
        this.Wifi=Wifi;
    }

    public abstract void deskripsiLokasi();
    public int getBiaya(){
        return biaya;
    }
    public int getEnergiTerkuras(){
        return energiTerkuras;
    }
    public String getNamaLokasi(){
        return namaLokasi;
    }
    public boolean isWifi(){
        return Wifi;
    }
}

class Kampus extends Lokasi{
    public Kampus(){
        super ("Kampus", 0, 30, true);
    }
    @Override
    public void deskripsiLokasi(){
        System.out.println("KAMPUS - Gratis, WiFi cepat, tapi capek jalan kaki.");
    }
}

class Kos extends Lokasi{
    public Kos(){
        super("Kos", 0, 10, false);
    }
    @Override 
    public void deskripsiLokasi(){
        System.out.println("KOS    - Gratis, hemat energi, tapi WiFi lemot banget.......");
    }
}

class Cafe extends Lokasi{
    public Cafe(){
        super("Cafe", 25000, 15, true);
    }
    @Override
    public void deskripsiLokasi(){
        System.out.println("CAFE   - Bayar Rp25.000, WiFi cepat, suasana nyaman.");
    }
}

class Mahasiswa {
    private String nama;
    private int saldo;
    private int energi;
    private ArrayList<Tugas> daftarTugas;
 
    public Mahasiswa(String nama, int saldo){
        this.nama = nama;
        this.saldo = saldo;
        this.energi = 100; 
        this.daftarTugas= new ArrayList<>();
    }

    public void tambahTugas(Tugas t){
        daftarTugas.add(t);
        System.out.println("Tugas [" + t.getNamaTugas() + "] ditamabhkan ke daftar.");
    }
    public void kerjakanTugas(Tugas t, Lokasi lokasi){
        System.out.println("\n--- MENGERJAKAN TUGAS ---");
        t.tampilkaninfo();
        System.out.println("Lokasi : ");
        lokasi.deskripsiLokasi();

        if (saldo < lokasi.getBiaya()){
             System.out.println("Saldo tidak cukup untuk ke " + lokasi.getNamaLokasi() + "!");
             return;
        }

        if (energi < 20){
            System.out.println("Energi terlalu rendah! Istirahat dulu.");
            return;
        }

        saldo -=lokasi.getBiaya();
        energi -=lokasi.getEnergiTerkuras();

        if (!lokasi.isWifi()){
            System.out.println("WiFi lemot... loading lama, tapi akhirnya berhasil submit!");
        } else {
            System.out.println(">> WiFi lancar! Submit berhasil dengan cepat.");   
        }

        t.tandaiSelesai();
        System.out.println("Beban kerjaan tugas ini: " + t.hitungTotalkerjaan() + "/3");
    }
    public void tampilaknSemuaTugas(){
        System.out.println("\n=== DAFTAR TUGAS " + nama.toUpperCase() + " ===");

        if (daftarTugas.isEmpty()){
            System.out.println("Tidak ada Tugas");
            return;
        }
        for (int i = 0; i < daftarTugas.size(); i++){
            System.out.println("\n[" + (i + 1) + "]");
            daftarTugas.get(i).tampilkaninfo(); 
        }
    }
    public void tampilkanStatus(){
        int selesai = 0;
        for (Tugas t : daftarTugas) if (t.isSelesai()) selesai++;

        System.out.println("\n=== STATUS " + nama.toUpperCase() + " ===");
        System.out.println("Saldo  : Rp " + saldo);
        System.out.println("Energi : " + energi + "%");
        System.out.println("Tugas  : " + selesai + "/" + daftarTugas.size() + " selesai");

        if (energi < 30) {
            System.out.println(">> Kamu kelelahan, istirahat dulu!");
         }else if (energi < 60) {
            System.out.println(">> Energi mulai berkurang, jaga kondisi.");
         }else {
            System.out.println(">> Energi masih oke, semangat!");
        }
    }
    public String getNama(){
        return nama;
    }
    public int getSaldo(){
        return saldo;
    }
    public int getEnergi(){
        return energi;
    }
    public ArrayList<Tugas> getDaftarTugas(){
        return daftarTugas;
    }
}

public class SurvivalKid{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

         System.out.println("============================================");
        System.out.println("   SISTEM MANAJEMEN TUGAS ANAK KOS          ");
        System.out.println("============================================");

        System.out.print("\nMasukkan namamu: ");
        String nama = sc.nextLine();
        System.out.print("Saldo awal (contoh: 100000): Rp ");
        int saldo = Integer.parseInt(sc.nextLine());
 
        Mahasiswa mhs = new Mahasiswa(nama, saldo);
        // Input Tugas
        System.out.println("\n--- TAMBAH TUGAS ---");
        System.out.print("Nama tugas: ");
        String namaTugas = sc.nextLine();
        System.out.print("Mata kuliah: ");
        String mataKuliah = sc.nextLine();
        System.out.print("Deadline: ");
        String deadline = sc.nextLine();
        System.out.print("Catatan (tekan Enter untuk skip): ");
        String catatan = sc.nextLine();
        if (catatan.isEmpty()) catatan = "-";

        System.out.println("Tingkat prioritas tugas ini?");
        System.out.println("1. Prioritas (deadline mepet, harus segera dikerjakan)");
        System.out.println("2. Tidak Prioritas (deadline masih lama, bisa diatur)");
        System.out.print("Pilih (1/2): ");
        int prioritas = Integer.parseInt(sc.nextLine());

        Tugas tugas;
        if (prioritas == 1) {
            tugas = new TugasPrioritas(namaTugas, mataKuliah, deadline, catatan);
        } else {
            tugas = new TugasTanggatLama(namaTugas, mataKuliah, deadline, catatan);
        }
        mhs.tambahTugas(tugas);
        // Menampilakn semua tugas & status
        mhs.tampilaknSemuaTugas();
        mhs.tampilkanStatus();

        System.out.println("\n============================================");
        System.out.println("   PILIH LOKASI MENGERJAKAN TUGAS           ");
        System.out.println("============================================");
 
        Lokasi[] pilihanLokasi = { new Kampus(), new Kos(), new Cafe() };
        for (int i = 0; i < pilihanLokasi.length; i++) {
            System.out.print((i + 1) + ". ");
            pilihanLokasi[i].deskripsiLokasi();
    }
    
        System.out.print("\nPilih lokasi untuk ngerjain [" + tugas.getNamaTugas() + "] (1/2/3): ");
        int pil = Integer.parseInt(sc.nextLine()) - 1;
 
        if (pil < 0 || pil > 2) {
            System.out.println(">> Pilihan tidak valid.");
        } else {
            mhs.kerjakanTugas(tugas, pilihanLokasi[pil]);
        }
 
        mhs.tampilkanStatus();
 
        System.out.println("\n============================================");
        System.out.println("   Semangat ngerjain tugasnya!              ");
        System.out.println("============================================");
 
        sc.close();

    }

}
