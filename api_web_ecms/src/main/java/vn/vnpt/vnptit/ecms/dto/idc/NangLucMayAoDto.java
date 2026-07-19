package vn.vnpt.vnptit.ecms.dto.idc;


public class NangLucMayAoDto {
    private Number nanglucMayaoId;
    private Number mayaoId;
    private Number slCorecpu;
    private Number dlRam;
    private Number dlGpu;
    private Number dlStorage;
    private Number dlHdd;
    private Number dlSdd;
    private Number dlNvme;
    private Number iopsId;
    private Number throughputId;
    private String ghichu;
    private String mota;

    public NangLucMayAoDto() {
    }

    // Alias for controller/general compatibility
    public Number getId() {
        return nanglucMayaoId;
    }

    public void setId(Number id) {
        this.nanglucMayaoId = id;
    }

    public Number getNanglucMayaoId() {
        return nanglucMayaoId;
    }

    public void setNanglucMayaoId(Number nanglucMayaoId) {
        this.nanglucMayaoId = nanglucMayaoId;
    }

    public Number getMayaoId() {
        return mayaoId;
    }

    public void setMayaoId(Number mayaoId) {
        this.mayaoId = mayaoId;
    }

    public Number getSlCorecpu() {
        return slCorecpu;
    }

    public void setSlCorecpu(Number slCorecpu) {
        this.slCorecpu = slCorecpu;
    }

    public Number getDlRam() {
        return dlRam;
    }

    public void setDlRam(Number dlRam) {
        this.dlRam = dlRam;
    }

    public Number getDlGpu() {
        return dlGpu;
    }

    public void setDlGpu(Number dlGpu) {
        this.dlGpu = dlGpu;
    }

    public Number getDlStorage() {
        return dlStorage;
    }

    public void setDlStorage(Number dlStorage) {
        this.dlStorage = dlStorage;
    }

    public Number getDlHdd() {
        return dlHdd;
    }

    public void setDlHdd(Number dlHdd) {
        this.dlHdd = dlHdd;
    }

    public Number getDlSdd() {
        return dlSdd;
    }

    public void setDlSdd(Number dlSdd) {
        this.dlSdd = dlSdd;
    }

    public Number getDlNvme() {
        return dlNvme;
    }

    public void setDlNvme(Number dlNvme) {
        this.dlNvme = dlNvme;
    }

    public Number getIopsId() {
        return iopsId;
    }

    public void setIopsId(Number iopsId) {
        this.iopsId = iopsId;
    }

    public Number getThroughputId() {
        return throughputId;
    }

    public void setThroughputId(Number throughputId) {
        this.throughputId = throughputId;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
