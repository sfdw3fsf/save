/**
 * 
 */
package vn.vnpt.vnptit.ecms.dto.idc;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class DaiIPDto {
	private  Integer id;
	private  Integer idCha;
	private  String ten;
	private  String tenCha;
	private  String kyhieu;
	private  Integer ip_loai_id;
	private  Integer ip_phanloai_id;
	private  String subnet;
	private  Integer subnet_mask_id;
	private String ipDau;
	private String ipCuoi;
	private  String gateway;
	private  Integer idc_id;
	private Integer trangthai_id;
	private  String cancuTH;
	private  String ghichu;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date thoidiem_Khaibao;

	private  String nguoiCapnhat;
	private Integer donvi_th_id;
	private  Integer hieu_luc;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public Integer getIdCha() {
		return idCha;
	}
	public void setIdCha(Integer idCha) {
		this.idCha = idCha;
	}
	public String getTenCha() {
		return tenCha;
	}
	public void setTenCha(String tenCha) {
		this.tenCha = tenCha;
	}
	public String getKyhieu() {
		return kyhieu;
	}
	public void setKyhieu(String kyhieu) {
		this.kyhieu = kyhieu;
	}	
	public Integer getTrangthai_id() {
		return trangthai_id;
	}
	public void setTrangthai_id(Integer trangthai_id) {
		this.trangthai_id = trangthai_id;
	}
	public Integer getIp_loai_id() {
		return ip_loai_id;
	}
	public void setIp_loai_id(Integer ip_loai_id) {
		this.ip_loai_id = ip_loai_id;
	}
	public Integer getIp_phanloai_id() {
		return ip_phanloai_id;
	}
	public void setIp_phanloai_id(Integer ip_phanloai_id) {
		this.ip_phanloai_id = ip_phanloai_id;
	}
	public String getSubnet() {
		return subnet;
	}
	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}
	public Integer getSubnet_mask_id() {
		return subnet_mask_id;
	}
	public void setSubnet_mask_id(Integer subnet_mask_id) {
		this.subnet_mask_id = subnet_mask_id;
	}
	public String getIpDau() {
		return ipDau;
	}
	public void setIpDau(String ipDau) {
		this.ipDau = ipDau;
	}

	public String getIpCuoi() {
		return ipCuoi;
	}
	public void setIpCuoi(String ipCuoi) {
		this.ipCuoi = ipCuoi;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public Integer getIdc_id() {
		return idc_id;
	}
	public void setIdc_id(Integer idc_id) {
		this.idc_id = idc_id;
	}
	public String getCancuTH() {
		return cancuTH;
	}
	public void setCancuTH(String cancuTH) {
		this.cancuTH = cancuTH;
	}
	public String getGhichu() {
		return ghichu;
	}
	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}
	public Date getThoidiemKhaibao() {
		return thoidiem_Khaibao;
	}
	public void setThoidiemKhaibao(Date thoidiem_Khaibao) {
		this.thoidiem_Khaibao = thoidiem_Khaibao;
	}
	public Integer getDonvi_th_id() {
        return donvi_th_id;
    }
    public void setDonvi_th_id(Integer donvi_th_id) {
        this.donvi_th_id = donvi_th_id;
    }
	public String getNguoiCapnhat() {
		return nguoiCapnhat;
	}
	public void setNguoiCapnhat(String nguoiCapnhat) {
		this.nguoiCapnhat = nguoiCapnhat;
	}

	
	

}
