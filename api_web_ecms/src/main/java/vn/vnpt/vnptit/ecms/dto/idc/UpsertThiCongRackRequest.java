package vn.vnpt.vnptit.ecms.dto.idc;

public class UpsertThiCongRackRequest {
    private ThiCongRackDto thiCongRack;
    private ThongTinRackDto thongTinRack;

    public ThiCongRackDto getThiCongRack() {
        return thiCongRack;
    }

    public void setThiCongRack(ThiCongRackDto thiCongRack) {
        this.thiCongRack = thiCongRack;
    }

    public ThongTinRackDto getThongTinRack() {
        return thongTinRack;
    }

    public void setThongTinRack(ThongTinRackDto thongTinRack) {
        this.thongTinRack = thongTinRack;
    }
}
