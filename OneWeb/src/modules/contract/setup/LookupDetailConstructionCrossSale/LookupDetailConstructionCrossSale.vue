<template src="./LookupDetailConstructionCrossSale.html"></template>
<style src="./LookupDetailConstructionCrossSale.css"></style>
<script>
import _ from "lodash";
import * as moment from "moment";
import breadcrumb from '@/components/breadcrumb';
import DichVuVienThong from "@/const/enums/DichVuVienThong";
import DanhSachBienDongTB from "./popups/DanhSachBienDongTB";
import SearchContractModal from '../../setup/HandoverTicket/components/SearchContractModal';
import ThongTinEmail from '../ChangeContractOwner/popup/ThongTinEmail';
import ThongTinLienHe from "../ChangeContractOwner/popup/ThongTinLienHe.vue"
import ThongTinHenKhachHangModal from './popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal';
import PortPopup from './popups/PortPopup';
import VatTuPopup from './popups/VatTuPopup';
import XuatPhieuPopup from './popups/XuatPhieuPopup';
import DiaChiLapDatPopup from './popups/DiaChiLapDatPopup';
import GoiDaDichVuPopup from './popups/GoiDaDichVuPopup';
import TonPhieuPopup from './popups/TonPhieuPopup';
import DanhSachNhanVienThucHienPopup from "./popups/DanhSachNhanVienThucHienPopup";
import api from "./api";
export default {
  name: "LookupDetailConstructionCrossSale",
  components: {
    breadcrumb,
    DanhSachBienDongTB,
    SearchContractModal,
    ThongTinEmail,
    ThongTinLienHe,
    ThongTinHenKhachHangModal,
    PortPopup,
    VatTuPopup,
    XuatPhieuPopup,
    DiaChiLapDatPopup,
    GoiDaDichVuPopup,
    TonPhieuPopup,
    DanhSachNhanVienThucHienPopup
  },
  // mounted() {
  //   this.loadDanhMuc();
  //   this.quyenduoc_hen = 1

  // },
  data() {
    return {
      header: {
        title: 'TRA CỨU CHI TIẾT ĐIỀU HÀNH THI CÔNG TỈNH KHÁC',
        list: []
      },
      isPopup: false,
      checkTinhTraCuu: false,
      cboTinhTraCuu: [],
      tinhId: null,
      formThongTinPhieuYeuCau: {
        hdkh_id: null,
        khachhang_id: null,
        dichVu: "",
        ngayYeuCau: "",
        maGiaoDich: "",
        ngayLapHD: "",
        loaiHD: "",
        kieuLD: "",
        huongKNMoi: "",
        soMayMoi: "",
        huongKNCu: "",
        soMayCu: "",
        tocDoCirOptions: [],
        tocDoCirId: null,
        checkTocDoPir: false,
        tocDoPirOptions: [],
        tocDoPirId: null,
        checkTocDoNix: false,
        tocDoNixOptions: [],
        tocDoNixId: null,
        checkTocDoIsp: false,
        tocDoIspOptions: [],
        tocDoIspId: null,
        tenKh: "",
        dienThoai: "",
        diaChiKh: "",
        checkNCCKhacChuyenSang: false,
        nguoiGiao: "",
        ngayYCHT: "",
        noiNhan: "",
        maTBTinh: "",
        nguoiLapHD: "",
        trangThaiTC: "",
        lyDoThoaiBC: "",
        ghiChu: "",
        maGdTinh: "",
      },
      hdtb_id: null,
      quenduoc_hen: '',
      ds_biendong: [],
      gridDanhSachTB: [],
      dsThueBao: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Số máy', allowfiltering: true },
          // {fieldName: 'matb_tn', headerText: 'Mã TN', allowfiltering: true},
          { fieldName: 'ma_lt', headerText: 'Số ảo', allowfiltering: true },
          // {fieldName: 'ma_vnpt', headerText: 'Mã VNPT', allowfiltering: true},
          // {fieldName: 'ma_tb_cha', headerText: 'Thuê bao cha', allowfiltering: true},
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowfiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowfiltering: true },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowfiltering: true },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowfiltering: true },
          { fieldName: 'hinhthuc', headerText: 'HT chuyển đổi', allowfiltering: true },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowfiltering: true },
          // {fieldName: 'trangthai_kh', headerText: 'Trạng thái KH', allowfiltering: true},
          // {fieldName: 'diachi_dau', headerText: 'Địa chỉ LĐ đầu', allowfiltering: true},
          // {fieldName: 'diachi_cuoi', headerText: 'Địa chỉ LĐ cuối', allowfiltering: true},
          { fieldName: 'tocdo', headerText: 'Tốc độ', allowfiltering: true },
          { fieldName: 'ten_dt', headerText: 'Đối tượng', allowfiltering: true },
          // {fieldName: 'gio_conlai', headerText: 'Giờ còn lại', allowfiltering: true},
          { fieldName: 'ngay_kt', headerText: 'Ngày hết hạn', allowfiltering: true },
          // {fieldName: 'tg_thue_tu', headerText: 'TG thuê từ', allowfiltering: true},
          // {fieldName: 'tg_thue_den', headerText: 'TG thuê đến', allowfiltering: true},
          // {fieldName: 'ghichu', headerText: 'Ghi chú', allowfiltering: true},
          // {fieldName: 'ngay_kichhoat', headerText: 'Ngày kích hoạt', allowfiltering: true},
          { fieldName: 'ngay_ht', headerText: 'Ngày HT', allowfiltering: true },
          // {fieldName: 'ngay_nghiemthu', headerText: 'Ngày nghiệm thu', allowfiltering: true},
          // {fieldName: 'congnghe', headerText: 'Công nghệ', allowfiltering: true},
        ],
        selectedIndex: 0,
        selectedItem: null,
        checked: []
      },
      gridChiTietDieuHanhThiCong: [],
      dsDieuHanhThiCong: {
        headers: [
          { fieldName: 'dvg', headerText: 'Đơn vị giao', allowFiltering: true },
          { fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true },
          { fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true },
          { fieldName: 'huonggiao', headerText: 'Hướng giao', isGroupedColumn: true, captionTemplate: "${key}" },
          { fieldName: 'nd_giao', headerText: 'Nội dung giao' },
          { fieldName: 'dvn', headerText: 'Đơn vị nhận', allowFiltering: true },
          { fieldName: 'ngay_tra', headerText: 'Ngày trả' },
          { fieldName: 'nd_tra', headerText: 'Nội dung trả', allowFiltering: true },
          { fieldName: 'lydotra', headerText: 'Lý do trả', allowFiltering: true },
          { fieldName: 'ngay_giao', headerText: 'Ngày giao' },
          { fieldName: 'ngay_th', headerText: 'Ngày TH' },
          { fieldName: 'nv_th', headerText: 'Người CN', allowFiltering: true },
          { fieldName: 'so_dt', headerText: 'DT liên hệ', allowFiltering: true },
          { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', allowFiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú' },
          // {fieldName: 'ketqua_xl', headerText: 'Kết quả XL', allowFiltering: true},
          // {fieldName: 'chuongtrinh', headerText: 'Ứng dụng', allowFiltering: true},
          { fieldName: 'nd_thuchien', headerText: 'ND thực hiện', allowFiltering: true },
          { fieldName: 'nguoi_xuly', headerText: 'Người thực hiện' }
        ],
        selectedIndex: 0,
        selectedItem: null,
        checked: []
      }
    };
  },
  async mounted() {
    this.quyenduoc_hen = 1
    this.loadDanhMuc()
  },
  methods: {
    async loadDanhMuc() {
      console.log('danh muc');

      api.lay_ds_tinh(this.axios).then(ret => {
        this.cboTinhTraCuu = ret.data.data;
        this.tinhId = this.$root.token.getPhanVungID();
      });
      // api.sp_lay_trangthai_hd(this.axios, {});
      api.lay_dm_tocdo_mgwan(this.axios, {}).then(ret => {
        const data = ret.data.data;
        this.formThongTinPhieuYeuCau.tocDoCirOptions = data;
        this.formThongTinPhieuYeuCau.tocDoPirOptions = data;
        this.formThongTinPhieuYeuCau.tocDoNixOptions = data;
        this.formThongTinPhieuYeuCau.tocDoIspOptions = data;
      });
    },
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal();
    },
    async acceptSearchContract(item) {
      console.log("LookupDetailConstructionCrossSale acceptSearchContract", JSON.parse(JSON.stringify(item)));
      this.clearKetQuaTimKiem();
      this.formThongTinPhieuYeuCau.maGiaoDich = item.ma_gd;
      this.$refs.txtMaGD.focus();
      this.onClickSearch()
    },
    onClickClearAll() {
      this.clearAll();
    },
    clearAll() {
      this.checkTinhTraCuu = false;
      this.tinhId = this.$root.token.getPhanVungID().toString();
      this.clearKetQuaTimKiem();
    },
    clearKetQuaTimKiem() {
      this.formThongTinPhieuYeuCau.hdkh_id = null;
      this.formThongTinPhieuYeuCau.khachhang_id = null;
      this.formThongTinPhieuYeuCau.dichVu = "";
      this.formThongTinPhieuYeuCau.ngayYeuCau = "";
      this.formThongTinPhieuYeuCau.maGiaoDich = "";
      this.formThongTinPhieuYeuCau.ngayLapHD = "";
      this.formThongTinPhieuYeuCau.loaiHD = "";
      this.formThongTinPhieuYeuCau.kieuLD = "";
      this.formThongTinPhieuYeuCau.huongKNMoi = "";
      this.formThongTinPhieuYeuCau.soMayMoi = "";
      this.formThongTinPhieuYeuCau.huongKNCu = "";
      this.formThongTinPhieuYeuCau.soMayCu = "";
      this.formThongTinPhieuYeuCau.tocDoCirId = null;
      this.formThongTinPhieuYeuCau.checkTocDoPir = false;
      this.formThongTinPhieuYeuCau.tocDoPirId = null;
      this.formThongTinPhieuYeuCau.checkTocDoIsp = null;
      this.formThongTinPhieuYeuCau.tocDoIspId = null;
      this.formThongTinPhieuYeuCau.tenKh = "";
      this.formThongTinPhieuYeuCau.dienThoai = "";
      this.formThongTinPhieuYeuCau.diaChiKh = "";
      this.formThongTinPhieuYeuCau.checkNCCKhacChuyenSang = false;
      this.formThongTinPhieuYeuCau.nguoiGiao = "";
      this.formThongTinPhieuYeuCau.ngayYCHT = "";
      this.formThongTinPhieuYeuCau.noiNhan = "";
      this.formThongTinPhieuYeuCau.maTBTinh = "";
      this.formThongTinPhieuYeuCau.nguoiLapHD = "";
      this.formThongTinPhieuYeuCau.trangThaiTC = "";
      this.formThongTinPhieuYeuCau.lyDoThoaiBC = "";
      this.formThongTinPhieuYeuCau.ghiChu = "";
      this.formThongTinPhieuYeuCau.maGdTinh = "";
      this.ds_biendong = [];
      this.gridDanhSachTB = [];
      this.dsThueBao.selectedIndex = 0;
      this.dsThueBao.selectedItem = 0;
      this.dsThueBao.checked = [];
      this.gridChiTietDieuHanhThiCong = [];
      this.dsDieuHanhThiCong.selectedIndex = 0;
      this.dsDieuHanhThiCong.selectedItem = null;
      this.dsDieuHanhThiCong.checked = [];
    },
    async onClickSearch() {
      this.formThongTinPhieuYeuCau.maGiaoDich = this.formThongTinPhieuYeuCau.maGiaoDich.trim()
      const ma_gd = this.formThongTinPhieuYeuCau.maGiaoDich;
      if (!ma_gd) {
        alert("Vui lòng điền mã giao dịch");
        return;
      }
      if (this.checkTinhTraCuu && !this.tinhId) {
        alert("Vui lòng chọn tỉnh tra cứu");
        return;
      }
      try {
        this.loading(true);
        if (this.checkTinhTraCuu) {
          this.ds_biendong = (await api.bancheo_lay_ds_hd_theo_ma_tb(this.axios, {
            ma_tb: ma_gd,
            tinhthicong_id: this.tinhId
          })).data.data;
        } else {
          this.tinhId = this.$root.token.getPhanVungID().toString();
          this.ds_biendong = (await api.lay_ds_hd_theo_ma_tb(this.axios, { ma_tb: ma_gd })).data.data;
          if (this.ds_biendong.length === 0 && +this.tinhId === 97 /* KHDN-Vinaphone */) {
            const phieu = (await api.giaophieu_ph(this.axios, {
              vphanvung_id: +this.$root.token.getPhanVungID(),
              ma_gd,
              ma_tb: ma_gd,
            })).data.data[0];
            if (phieu && phieu.tinh_id !== -1) {
              this.checkTinhTraCuu = true;
              this.tinhId = phieu.tinh_id.toString();
              this.ds_biendong = (await api.bancheo_lay_ds_hd_theo_ma_tb(this.axios, {
                ma_tb: ma_gd,
                tinhthicong_id: phieu.tinh_id
              })).data.data;
            }
          }
        }
        this.loading(false);
        if (this.ds_biendong.length > 1) {
          this.$bvModal.show("popupDanhSachBienDongTB");
        } else if (this.ds_biendong.length === 1) {
          this.acceptBienDong(this.ds_biendong[0]);
        } else {
          this.$toast("Không tìm thấy hợp đồng");
        }
      } catch (e) {
        this.loading(false);
      }
    },
    async acceptBienDong(item) {
      this.$bvModal.hide("popupDanhSachBienDongTB");
      console.log("LookupDetailConstructionCrossSale acceptBienDong", JSON.parse(JSON.stringify(item)));
      const searchText = this.formThongTinPhieuYeuCau.maGiaoDich;
      this.clearKetQuaTimKiem();
      /*
      {
          "ma_gd": "HPG-LD/00734518",
          "ngay_yc": "2021-04-11 08:40:44.0",
          "hdkh_id": "5348927",
          "ten_kh": "Phạm Trung Huy",
          "diachi_kh": "Cụm 4, Thôn An Bồ, Xã Dũng Tiến, Huyện Vĩnh Bảo, Hải Phòng",
          "ngaylap_hd": "2021-04-11 08:36:39.0",
          "so_dt": "0328319963",
          "ten_loaihd": "Lắp đặt mới",
          "trangthai_hd": "Đã hoàn thành",
          "ten_nv": "Phạm Thị Xuân Hương",
          "donvi_id": "250",
          "ten_dv": "Giao dịch Vĩnh Bảo",
          "khachhang_id": "1689702",
          "tthd_id": "6",
          "thuebao_id": "3059777",
          "ma_kh": "HPG-001975552",
          "ma_hd": "HDHPG0388102114",
          "chuyenmang": "0",
          "loaihd_id": "1"
      }
       */
      this.formThongTinPhieuYeuCau.hdkh_id = +item.hdkh_id || 0;
      this.formThongTinPhieuYeuCau.khachhang_id = +item.khachhang_id || 0;
      // this.formThongTinThueBao.dichVu = "";
      this.formThongTinPhieuYeuCau.ngayYeuCau = item.ngay_yc;
      this.formThongTinPhieuYeuCau.maGiaoDich = item.ma_gd;
      this.formThongTinPhieuYeuCau.ngayLapHD = item.ngaylap_hd;
      this.formThongTinPhieuYeuCau.loaiHD = item.ten_loaihd;
      // this.formThongTinThueBao.kieuLD = "";
      // this.formThongTinThueBao.huongKNMoi = "";
      // this.formThongTinThueBao.soMayMoi = "";
      // this.formThongTinThueBao.huongKNCu = "";
      // this.formThongTinThueBao.soMayCu = "";
      this.formThongTinPhieuYeuCau.tenKh = item.ten_kh;
      this.formThongTinPhieuYeuCau.dienThoai = item.so_dt;
      this.formThongTinPhieuYeuCau.diaChiKh = item.diachi_kh;
      this.formThongTinPhieuYeuCau.checkNCCKhacChuyenSang = !!item.chuyenmang;
      // this.formThongTinThueBao.nguoiGiao = "";
      // this.formThongTinThueBao.ngayYCHT = "";
      // this.formThongTinThueBao.noiNhan = "";
      // this.formThongTinThueBao.maTBTinh = "";
      // this.formThongTinThueBao.nguoiLapHD = "";
      // this.formThongTinThueBao.trangThaiTC = "";
      // this.formThongTinThueBao.lyDoThoaiBC = "";
      // this.formThongTinThueBao.ghiChu = "";
      try {
        this.loading(true);
        let dsThueBao;
        if (this.checkTinhTraCuu) {
          dsThueBao = (await api.bancheo_lay_ds_hd_tb_theo_hdkh_id(this.axios, {
            tinhthicong_id: this.tinhId,
            hdkh_id: +item.hdkh_id
          })).data.data;
        } else {
          dsThueBao = (await api.lay_ds_hd_tb_theo_hdkh_id(this.axios, {
            hdkh_id: +item.hdkh_id,
            // phanvung_id: 0
          })).data.data;

        }



        const matchSearchTextIndex = dsThueBao.findIndex(it => it.ma_tb === searchText);
        if (matchSearchTextIndex >= 0) {
          this.dsThueBao.selectedIndex = matchSearchTextIndex;
          // this.$refs.gridDanhSachTB.selectRow(matchSearchTextIndex);
        }
        this.gridDanhSachTB = dsThueBao;


      } catch (e) {
        this.$toast("Có lỗi khi lấy danh sách thuê bao");
      } finally {
        this.loading(false);
      }
    },
    async gridDsThueBao_SelectedRowChanged(item) {
      console.log(item)
      console.log(this.dsThueBao.selectedItem)
      // if (this.dsThueBao.selectedItem === item) return;

      // if (_.get(this.dsThueBao.selectedItem, "hdtb_id") === _.get(item, "hdtb_id")) return;
      //console.log("gridDsThueBao_SelectedRowChanged", this.dsThueBao.selectedItem.hdtb_id);
      this.dsThueBao.selectedItem = item;
      if (!item) {
        this.formThongTinPhieuYeuCau.kieuLD = "";
        this.formThongTinPhieuYeuCau.dichVu = "";
        this.formThongTinPhieuYeuCau.ghiChu = "";
        this.formThongTinPhieuYeuCau.maTBTinh = "";
        this.formThongTinPhieuYeuCau.trangThaiTC = "";
        return;
      }
      this.formThongTinPhieuYeuCau.soMayMoi = item.ma_tb;
      this.formThongTinPhieuYeuCau.kieuLD = item.ten_kieuld;
      this.formThongTinPhieuYeuCau.dichVu = item.ten_dvvt;
      this.formThongTinPhieuYeuCau.ghiChu = item.ghichu;
      const hdtb_id = this.dsThueBao.selectedItem.hdtb_id;
      this.loading(true);
      if (this.checkTinhTraCuu) {
        this.formThongTinPhieuYeuCau.maTBTinh = "";
        this.formThongTinPhieuYeuCau.trangThaiTC = "";
        this.formThongTinPhieuYeuCau.lyDoThoaiBC = "";
        /*
        -- API goi ham nay tai DB tinh voi bien tinh_id: hdtb_thicong = select max(hdtb_thicong)  from css.hd_bancheo where tinh_thicong = :tinh_id and loaihd_bancheo = 1 and hdtb_ban = :hdtb_id;
        -- API chay lenh nay tai phan vung dang nhap: txtMaGD_Tinh.Text = select HK.ma_gd from CSS.hd_khachhang hk, css.hd_thuebao ht where  hk.hdkh_id=  ht.hdkh_id and ht.hdtb_id = :hdtb_thicong  and phanvung_id = :phanvung_id;
        -- API chay lenh nay tai phan vung dang nhap: ma_tb_cu = select ma_tb from css.bd_thuebao where hdtb_id = :hdtb_id  and phanvung_id = :phanvung_id;
        -- API chay lenh nay tai phan vung dang nhap: ds_tocdo = select tocdo_id, tocdo_pir_id, tocdo_nix, tocdo_isp, ngay_ycht  from css.hdtb_mgwan where hdtb_id = hdtb_id and phanvung_id = :phanvung_id;
        -- API chay lenh nay tai phan vung dang nhap: txtCTV.Text = select tocdo_id, ngay_ycht  from css.hdtb_tsl where hdtb_id = hdtb_id and daucuoi_id = 1 and phanvung_id = :phanvung_id;
        */
        try {
          const hdtb_thicong = (await api.fn_tt_hd_bancheo(this.axios, {
            type: 6,
            param: item.hdtb_id,
            param1: this.tinhId,
            tinhthicong_id: this.tinhId
          })).data.data;
          console.log(1111111111111)
          console.log(hdtb_id)
          console.log(this.dsThueBao.selectedItem.hdtb_id)
          if (hdtb_id === this.dsThueBao.selectedItem.hdtb_id) {
            console.log(hdtb_thicong)
            if (!hdtb_thicong || +hdtb_thicong <= 0) {
              this.formThongTinPhieuYeuCau.maGdTinh = "-1";
            } else {
              const maGdTinh = (await api.fn_tt_hd_khachhang(this.axios, {
                /*in_id_neo: "hk.ma_gd",
                in_table: "css.hd_thuebao ht, css.hd_khachhang hk",
                in_dk: `where hk.hdkh_id = ht.hdkh_id and ht.hdtb_id = ${hdtb_thicong}`,*/
                type: 3,
                param: hdtb_thicong
              })).data.data;
              if (hdtb_id === this.dsThueBao.selectedItem.hdtb_id) {
                this.formThongTinPhieuYeuCau.maGdTinh = maGdTinh;
              }
            }
          }
        } catch (e) {
          this.$toast("Có lỗi xảy ra khi lấy thông tin mã GD tỉnh");
        }
      } else {
        try {
          this.hdtb_id = item.hdtb_id
          console.log('qqq', this.hdtb_id);

          const thongtin_hd_thicong = (await api.bancheo_lay_thongtin_hd_thicong(this.axios, {
            vhdtb_ban: +item.hdtb_id,
            phanvung_id: +this.$root.token.getPhanVungID(),
            vtinh_id: this.tinhId,
            tinhthicong_id: this.getSelectedTBProp("tinh_thicong"), // Sua lai API va tham so theo yeu cau cua NguyenTuanAnh @cikimelog ThangDM1 2023-04-21
          })).data.data[0];
          if (hdtb_id === this.dsThueBao.selectedItem.hdtb_id && thongtin_hd_thicong) {
            this.formThongTinPhieuYeuCau.maTBTinh = thongtin_hd_thicong.ma_tb;
            this.formThongTinPhieuYeuCau.trangThaiTC = thongtin_hd_thicong.trangthai_hd;
            this.formThongTinPhieuYeuCau.maGdTinh = thongtin_hd_thicong.ma_gd;
          }
        } catch (e) {
          this.$toast("Có lỗi xảy ra khi lấy thông tin hợp đồng thi công");
        }
        try {
          const ndthoai_ttkd = (await api.bancheo_lay_ndthoai_ttkd(this.axios, {
            hdtb_ban: +item.hdtb_id,
            phanvung_id: this.tinhId,
          })).data.data;
          if (hdtb_id === this.dsThueBao.selectedItem.hdtb_id && ndthoai_ttkd) {
            this.formThongTinPhieuYeuCau.lyDoThoaiBC = ndthoai_ttkd;
          }
        } catch (e) {
          this.$toast("Có lỗi xảy ra khi lấy nội dung thoái BC");
        }
      }
      if (item.dichvuvt_id === DichVuVienThong.MEGAWAN) {
        try {
          const huongkn_moi_cu = (await api.lay_huongkn_moi_cu(this.axios, {
            hdtb_id: +item.hdtb_id,
            phanvung_id: this.tinhId,
          })).data.data;
          if (hdtb_id === this.dsThueBao.selectedItem.hdtb_id) {
          }
        } catch (e) {
          this.$toast("Có lỗi xảy ra khi lấy hướng kết nối mới/cũ");
        }
      } else {
        if (hdtb_id === this.dsThueBao.selectedItem.hdtb_id) {
          this.formThongTinPhieuYeuCau.huongKNMoi = "";
          this.formThongTinPhieuYeuCau.huongKNCu = "";
        }
      }
      if (item.dichvuvt_id === DichVuVienThong.MEGAWAN) {
        try {
          const tocdo_mgwan_metronet = (await api.lay_tocdo_mgwan_metronet(this.axios, {
            hdtb_id: +item.hdtb_id,
            phanvung_id: this.tinhId,
          })).data.data;
          if (hdtb_id === this.dsThueBao.selectedItem.hdtb_id) {
          }
        } catch (e) {
          this.$toast("Có lỗi xảy ra khi lấy thông tin tốc độ");
        }
        // DataSet ds_tocdo = new DataSet();
        // ds_tocdo = bangts.getDataFromSQL(str, "tocdo");
        // if (ds_tocdo.Tables[0].Rows.Count > 0)
        // {
        //     cboTocDo.EditValue = Convert.ToInt32(ds_tocdo.Tables[0].Rows[0]["tocdo_id"].ToString());
        //     txtNgayYCHT.Text = ds_tocdo.Tables[0].Rows[0]["ngay_ycht"].ToString();
        //     if (ds_tocdo.Tables[0].Rows[0]["tocdo_pir_id"].ToString() != "")
        //     {
        //         chkTocDo_PIR.Checked = true;
        //         cboTocDo_Pir.EditValue = Convert.ToInt32(ds_tocdo.Tables[0].Rows[0]["tocdo_pir_id"].ToString());
        //     }
        //     else
        //     {
        //         chkTocDo_PIR.Checked = false;
        //     }
        //
        //
        //     if (ds_tocdo.Tables[0].Rows[0]["tocdo_nix"].ToString() != "")
        //     {
        //         chkTocDo_NIX.Checked = true;
        //         cboTocDo_NIX.EditValue = Convert.ToInt32(ds_tocdo.Tables[0].Rows[0]["tocdo_nix"].ToString());
        //     }
        //     else
        //     {
        //         chkTocDo_NIX.Checked = false;
        //     }
        //
        //     if (ds_tocdo.Tables[0].Rows[0]["tocdo_isp"].ToString() != "")
        //     {
        //         chkTocDo_ISP.Checked = true;
        //         cboTocDo_ISP.EditValue = Convert.ToInt32(ds_tocdo.Tables[0].Rows[0]["tocdo_isp"].ToString());
        //     }
        //     else
        //     {
        //         chkTocDo_ISP.Checked = false;
        //     }
        // }
        // else
        // {
        //     txtNgayYCHT.Text = "";
        //     cboTocDo.Text = "";
        //     chkTocDo_PIR.Checked = false;
        //
        //     chkTocDo_NIX.Checked = false;
        //     chkTocDo_ISP.Checked = false;
        // }
      } else if (item.dichvuvt_id === DichVuVienThong.TSL) {
        const tocdo_tsl = (await api.sp_lay_tocdo_tsl(this.axios, {
          hdtb_id: +item.hdtb_id,
          phanvung_id: this.tinhId,
        })).data.data;
        if (hdtb_id === this.dsThueBao.selectedItem.hdtb_id) {
        }
        /*
        string str = "";
        str += " select tocdo_id, ngay_ycht ";
        str += " from " + DatabaseConstants.DB2 + ".hdtb_tsl ";
        str += " where hdtb_id = " + hdtb_id;
        str += "    and daucuoi_id = 1";
        DataSet ds_tocdo = new DataSet();
        ds_tocdo = bangts.getDataFromSQL(str, "tocdo");
        if (ds_tocdo.Tables[0].Rows.Count > 0)
        {
            cboTocDo.EditValue = Convert.ToInt32(ds_tocdo.Tables[0].Rows[0]["tocdo_id"].ToString());
            txtNgayYCHT.Text = ds_tocdo.Tables[0].Rows[0]["ngay_ycht"].ToString();
        }
        else
        {
            cboTocDo.Text = "";
            txtNgayYCHT.Text = "";
        }
         */
      }
      try {
        let hdtb_id = ""
        if (this.checkTinhTraCuu == false) {
          hdtb_id = item.hdtb_thicong
        }
        else {
          hdtb_id = item.hdtb_id
        }
        if (hdtb_id != null) {
          const ret = (await api.sp_bancheo_lay_danhsach_phieuth(this.axios, {
            hdtb_id: hdtb_id,
            tinhthicong_id: item.tinh_thicong || this.tinhId
          })).data.data;
          if (this.checkTinhTraCuu == false && hdtb_id === this.dsThueBao.selectedItem.hdtb_thicong) {
            this.gridChiTietDieuHanhThiCong = ret;
          }
          else if (this.checkTinhTraCuu == true && hdtb_id === this.dsThueBao.selectedItem.hdtb_id) {
            this.gridChiTietDieuHanhThiCong = ret;
          }
        }
      } catch (e) {
        this.$toast("Có lỗi khi lấy danh sách chi tiết điều hành thi công");
      }
      this.loading(false);
    },
    async gridChiTietDieuHanhThiCong_SelectedRowChanged(item) {
      /*
      {
        "huonggiao": "1 - Tiếp nhận yêu cầu đặt mới Ftth",
        "dvg": "Khách hàng QLVT - Viễn thông Hải Phòng",
        "ngay_giao": "11/04/2021 08:40",
        "donvi_nhan_id": 250,
        "nd_giao": null,
        "dvn": "Giao dịch Vĩnh Bảo - Phòng Bán hàng KV5- So LH: ",
        "ngay_tra": null,
        "nd_tra": null,
        "lydotra": null,
        "trangthai_ph": "Đã thực hiện",
        "phieu_id": 11838334,
        "ttph_id": 4,
        "huonggiao_id": 6,
        "nv_giao": "Phạm Thị Xuân Hương",
        "ngay_th": "11/04/2021 08:40",
        "nv_th": "VNP016213 - Phạm Thị Xuân Hương",
        "nhanvien_th_id": 6897,
        "ghichu": " - 6",
        "nhiemvu": null,
        "donvi_id": 250,
        "so_dt": "0919156766",
        "nd_thuchien": " - 6 - ",
        "trangthai_hd": "Đã hoàn thành"
      }
       */
      if (this.dsDieuHanhThiCong.selectedItem === item) return;
      if (_.get(this.dsDieuHanhThiCong.selectedItem, "phieu_id") === _.get(item, "phieu_id")) return;
      this.dsDieuHanhThiCong.selectedItem = item;
      if (!item) {
        this.formThongTinPhieuYeuCau.nguoiGiao = "";
        this.formThongTinPhieuYeuCau.noiNhan = "";
        return;
      }
      this.formThongTinPhieuYeuCau.nguoiGiao = item.nv_giao;
      this.formThongTinPhieuYeuCau.noiNhan = item.dvn;
    },
    gridChiTietDieuHanhThiCong_recordDoubleClick(item) {
      this.$bvModal.show("popup-DanhSachNhanVienThucHien");
    },
    getSelectedTBProp(prop) {
      return _.get(this.dsThueBao.selectedItem, prop);
    },
    getSelectedDieuHanhThiCongProp(prop) {
      return _.get(this.dsDieuHanhThiCong.selectedItem, prop);
    },
    showPopupEmail() {
      if (this.formThongTinPhieuYeuCau.hdkh_id) {
        this.$bvModal.show("popupEmail");
      }
    },
    showPopupLienHe() {
      if (this.formThongTinPhieuYeuCau.hdkh_id) {
        this.$bvModal.show("popupThongTinLienHe");
      }
    },
    showPopupHenLD() {
      if (this.formThongTinPhieuYeuCau.hdkh_id) {
        this.$refs.popupHenLD.showModal();
      }
    },
    showPopupVatTu() {
      if (this.getSelectedTBProp('hdtb_id')) {
        this.$bvModal.show("popup-vattu");
      } else {
        this.$bvModal.msgBoxOk("Vui lòng chọn thuê bao");
      }
    },
    showPopupXuatPhieu() {
      if (this.getSelectedTBProp('hdtb_id')) {
        this.$bvModal.show("popup-xuatphieu");
      } else {
        this.$bvModal.msgBoxOk("Vui lòng chọn thuê bao");
      }
    },
    showPopupDiaChiLD() {
      if (this.getSelectedTBProp('hdtb_id')) {
        this.$bvModal.show("popup-diachilapdat");
      } else {
        this.$bvModal.msgBoxOk("Vui lòng chọn thuê bao");
      }
    },
    showPopupPort() {
      if (this.getSelectedTBProp('hdtb_id')) {
        this.$bvModal.show("popup-port");
      } else {
        this.$bvModal.msgBoxOk("Vui lòng chọn thuê bao");
      }
    },
    showPopupGoiDaDichVu() {
      if (this.getSelectedTBProp('hdtb_id')) {
        this.$bvModal.show("popup-goidadichvu");
      } else {
        this.$bvModal.msgBoxOk("Vui lòng chọn thuê bao");
      }
    },
    showPopupTonPhieu() {
      if (this.getSelectedTBProp('hdtb_id')) {
        this.$bvModal.show("popup-tonphieu");
      } else {
        this.$bvModal.msgBoxOk("Vui lòng chọn thuê bao");
      }
    }
  },
}
</script>