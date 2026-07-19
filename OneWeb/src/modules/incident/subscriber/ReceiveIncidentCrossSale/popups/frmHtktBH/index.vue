<template src="./index.html">

</template>

<script>

import moment from "moment";
import config from "./config";
import API from "../../API";
import api from "./api";
import {DichVuVienThong} from "../../../../../../const/enums";
import FormTraCuuTB from '../../popups/frmTraCuuThueBao';
import SearchAccount from '@/modules/search/subscriber/SearchAccount'

export default {
  name: "frmHtktBH",
  components: {
    FormTraCuuTB,
    SearchAccount
  },
  watch: {
    'cboDichVuVTValue': function (newVal, oldVal) {
      this.cboDichVuVT_SelectedValueChanged();
    },
    'cboLoaiTBValue': function (newVal, oldVal) {
      this.cboLoaiTB_SelectedValueChanged();
    }
  },
  created() {
    window.addEventListener('keyup', (e) => {
      if (e.key === 'F9') {
        this.Capnhat();
      }
    });
  },
  data() {
    return {
      headerTitle: 'Hỗ trợ kỹ thuật',
      cols: config.cols,
      dichvuvt_id: 0,
      loaitb_id: 0,
      thuebao_id: 0,
      vkyhoadon: '',
      sdt_lh: '',
      ma_lt: '',
      sdt_goi: '',
      vma_tb: '',
      dsnhomDV: '',
      nhomDV: '',
      doituong_id: 0,
      nguoidung_id: 0,
      dgiai: '',
      cboDichVuVT: [],
      cboDichVuVTValue: 0,
      cboLoaiTB: [],
      cboLoaiTBValue: 0,
      cboLoaiTN: [],
      cboHotro: [],
      txtTKMaTB: '',
      txtNguoiLH: '',
      txtTrangThaiTB: '',
      txtDienThoaiLH: '',
      txtCuocPS: '',
      txtNoidungHotro: '',
      txtNoidungYeucau: '',
      cboLoaiTNValue: 0,
      cboHotroValue: 0,
      tskt: [],
      gridHTKT: [],
      itemFocused: null,
      kieu_frm: 0,
      dsThueBao: []
    }
  },
  methods: {
    handleShowModal() {
    },

    hideModal() {
      this.$refs.frmHtktBH.hide()
    },

    async initData(data) {

    },

    async showModal() {
      this.$refs.frmHtktBH.show();
      await this.frmHtktBH_Load()
    },

    rowDeselected() {
      this.itemFocused = null;
    },

    selectedRowChanged(item) {
      this.itemFocused = item;
      this.$refs.formTraCuuTB.hideModal();

      this.txtTKMaTB = this.vma_tb = item.ma_tb;
      this.txtTKMaTB_KeyPress();
    },

    frmHtktBH(matb_tk) {
      this.txtTKMaTB = matb_tk;
    },

    async frmHtktBH_Load() {
      await this.getKyHoaDon();
      if (this.vkyhoadon == '') {
        this.$toast.error('Không lấy được thông tin kỳ hóa đơn');
        this.hideModal();
        return;
      }

      //Lấy Loại nhân viên được gán quyền
      this.dsnhomDV = await this.nhomhtkt(this.$root.token.getNguoiDungID());
      if (this.dsnhomDV === "0") {
        this.$toast.error('Bạn chưa được gán quyền');
        this.hideModal();
        return;
      }

      this.loadCbDichVu();

      this.loai_tiepnhan(this.dsnhomDV);
      this.noidung_hotro(this.dsnhomDV);
      this.HienThi_Danhsach_htkt();
      if (this.kieu_frm === 1) {
        this.txtTKMaTB_KeyPress();
      }
    },

    async getKyHoaDon() {
      try {
        let rs = await api.getKyHoaDon(this.axios);
        let data = this.getDataFromAPI(rs);
        if (data != null) {
          this.vkyhoadon = data[0].KYCUOC;
        }
      } catch (e) {
        console.log('Khong load duoc ky hoa don');
      }
    },

    getDataFromAPI(rs) {
      if (rs.data != null && rs.data.data != null && (typeof rs.data.data == 'number' || rs.data.data.length > 0)) {
        return rs.data.data;
      }

      return null;
    },

    async loadCbDichVu() {
      try {
        let rs = await api.loadCbDichVu(this.axios);
        let data = this.getDataFromAPI(rs);
        this.cboDichVuVT = data != null ? data : [];
        if (this.cboDichVuVT.length > 0 && this.cboDichVuVTValue === 0) {
          this.cboDichVuVTValue = DichVuVienThong.CO_DINH
        }
      } catch (e) {
        console.log('Khong load duoc dich vu');
      }
    },

    nhomhtkt(nguoidung_id) {
      //thieu api
      return "121";
    },

    loai_tiepnhan(vNhomDV) {
      //thieu api
      // try {
      //   let rs = await api.loai_tiepnhan(this.vNhomDV);
      //   let data = this.getDataFromAPI(rs);
      //   this.cboDichVuVT = data != null ? data : [];
      //   if (this.cboDichVuVT.length > 0 && this.cboDichVuVTValue === 0) {
      //     this.cboDichVuVTValue = DichVuVienThong.CO_DINH
      //   }
      // } catch (e) {
      //   console.log('Khong load duoc dich vu');
      // }
    },

    noidung_hotro(vNhomDV) {
      //thieu api
    },

    HienThi_Danhsach_htkt() {
      //thieu api
    },

    HienThiThongTinThueBao(data, index) {
      try {
        if (data.length >= index) {

          this.txtTKMaTB = data[index]["ma_tb"];
          this.thuebao_id = parseInt(data[index]["thuebao_id"]);
          this.doituong_id = parseInt(data[index]["doituong_id"]);
          this.dichvuvt_id = parseInt(data[index]["dichvuvt_id"]);
          this.dgiai = "";

          let {dichvuvt_id, doituong_id} = this;
          if (dichvuvt_id === 8 || dichvuvt_id === 9) {
            if (doituong_id === 47 || doituong_id === 48) {
              this.dgiai = "BTS";
              this.nhomDV = "123";
            } else {
              this.dgiai = "TSL";
              this.nhomDV = "122";
            }
          } else {
            this.dgiai = "800126";
            this.nhomDV = "121";
          }

          if (this.dsnhomDV.includes(this.nhomDV)) {
            this.loai_tiepnhan(this.nhomDV);
            this.noidung_hotro(this.nhomDV);
            this.HienThiChiTietBH(data, index);
          } else {
            this.$toast.error("Thuê bao này thuộc nhóm hỗ trợ kỹ thuật " + this.dgiai);
            this.Clear();
            if (this.txtTKMaTB === "" && this.vma_tb !== "") {
              this.txtTKMaTB = this.vma_tb;
              $('#htkt_matb').focus();
            }
            this.tskt = null;
          }
        }
      } catch (exp) {
        console.log(exp);
        this.$toast.error(exp);
      }
    },

    HienThiChiTietBH(data, index) {
      this.thuebao_id = parseInt(data[index]["thuebao_id"]);
      this.ma_lt = data[index]["ma_lt"];
      this.sdt_lh = data[index]["so_dt"];
      this.dichvuvt_id = parseInt(data[index]["dichvuvt_id"]);
      this.cboDichVuVTValue = this.dichvuvt_id;
      this.cboLoaiTBValue = data[index]["loaitb_id"];
      this.loaitb_id = 0;
      if (this.dichvuvt_id === 4) {
        this.loaitb_id = parseInt(this.cboLoaiTBValue);
      }
      this.txtTrangThaiTB = data[index]["trangthai_tb"];
      this.HT_THONGSO_KYTHUAT(this.thuebao_id, this.dichvuvt_id);
      this.txtDienThoaiLH = data[index]["so_dt"];
      this.LAY_TONGNOPS_THUEBAO(this.txtTKMaTB, this.vkyhoadon);
    },

    async LAY_TONGNOPS_THUEBAO(vma_tb, vkyhoadon) {
      try {
        let rs = await api.lay_tongnops_thuebao(this.axios, {
          vma_tb,
          vkyhoadon
        });
        if (rs.data != null && rs.data.error === '200') {
          this.txtCuocPS = this.formatMoney(rs.data.data);
          return rs.data.data;
        }
      } catch (e) {
        console.log('Khong load LAY_TONGNOPS_THUEBAO');
      }

      return 0;
    },

    async HT_THONGSO_KYTHUAT(thuebao_id, dichvuvt_id) {
      try {
        let rs = await api.lay_ds_thongtin_kythuat(this.axios, thuebao_id);
        let data = this.getDataFromAPI(rs);
        this.tskt = data != null ? data : [];
      } catch (e) {
        console.log('Khong load HT_THONGSO_KYTHUAT');
      }

    },

    formatMoney(x) {
      return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    },

    Clear() {
      this.txtTKMaTB = "";
      this.txtTrangThaiTB = "";
      this.txtCuocPS = "";
      this.txtNguoiLH = "";
      this.txtDienThoaiLH = "";
      this.cboLoaiTNValue = 0;
      this.cboHotroValue = 0;
      this.txtNoidungYeucau = "";
      this.txtNoidungHotro = "";
      this.tskt = null;
    },

    cboDichVuVT_SelectedValueChanged() {
      let {cboDichVuVTValue, cboDichVuVT} = this;
      try {
        if (cboDichVuVT.length <= 0) return;
        this.dichvuvt_id = cboDichVuVTValue;
        this.loadCbLoaiHinhTB(this.dichvuvt_id);
      } catch (e) {

      }
    },

    async loadCbLoaiHinhTB(dichvuvt_id) {
      try {
        let rs = await api.lay_loaihinhtb_theo_dvvt(this.axios, dichvuvt_id);
        let data = this.getDataFromAPI(rs);
        this.cboLoaiTB = data != null ? data : [];
        if (this.cboLoaiTB.length > 0 && this.cboLoaiTBValue === 0) {
          this.cboLoaiTBValue = data[0].ID;
        }
      } catch (e) {
        console.log('Khong load loadCbLoaiHinhTB');
      }
    },

    async cboLoaiTB_SelectedValueChanged() {
      this.loaitb_id = parseInt(this.cboLoaiTBValue);
    },

    async Capnhat() {
      if (await this.KiemTra_htkt()) {
        try {
          //thieu api; them tiep nhan
          // new BusinessFacade.CabmanFacade.TruyenDanFacade().EXECUTE_PROC(DatabaseConstants.DB7 + ".THEM_TIEPNHAN", "p_thuebao_id", thuebao_id, "p_ma_tb", txtTKMaTB.Text, "p_nguoi_lh", txtNguoiLH.Text, "p_sdt_lh", txtDienThoaiLH.Text, "p_sdt_goi", sdt_goi, "p_nhanvien_tn_id", tt_nd.nhanvien_id, "p_donvi_tn_id", tt_nd.donvi_id, "p_loaitn_id", cboLoaiTN.SelectedValue, "p_noi_dung", txtNoidungYeucau.Text, "p_hotro_id", cboHotro.SelectedValue, "p_noi_dung_hotro", txtNoidungHotro.Text);

          this.Clear();
          this.HienThi_Danhsach_htkt();
          this.hideModal();
        } catch (exp) {
          this.$toast.error(exp);
        }
      }
    },

    KiemTra_htkt() {
      if (this.txtTKMaTB === "") {
        this.$toast.error("Bạn hãy nhập mã thuê bao!");
        return false;
      }
      if (this.txtNguoiLH === "") {
        this.$toast.error("Bạn hãy nhập tên người liên hệ!");
        return false;
      }
      if (this.txtDienThoaiLH === "") {
        this.$toast.error("Hãy nhập số điện thoại liên hệ.");
        return false;
      }
      if (this.txtDienThoaiLH.length < 7) {
        this.$toast.error("Số điện thoại liên hệ không được ít hơn 7 ký tự.");
        return false;
      }
      if (this.txtDienThoaiLH.length > 10) {
        this.$toast.error("Số điện thoại liên hệ không được quá 10 ký tự.\nBạn hãy bỏ số 0 ở đầu (nếu có)!");
        return false;
      }
      if (!Number.isInteger(this.txtDienThoaiLH)) {
        this.$toast.error("Số điện thoại liên hệ phải là số!");
        return false;
      }
      let _r = new RegExp(/^(\d{10}(,\d{10})*)?$/);
      if (!_r.test(this.txtDienThoaiLH)) {
        this.$root.$toast.error("Số điện thoại liên hệ chưa hợp lệ!");
        return false;
      }
      return true;

    },

    tsbtnCapNhat_Click() {
      this.Capnhat();
    },

    async txtTKMaTB_KeyPress() {
      if (this.txtTKMaTB !== '') {
        let rs = await api.lay_danhba_theo_matb(this.axios, this.txtTKMaTB);
        let data = this.getDataFromAPI(rs);
        let index = 0;
        if (data != null) {
          if (data.length === 1) {
            this.dsThueBao = [];
            index = 0;
          } else {
            this.dsThueBao = [...data];
            let rs = await this.$refs.formTraCuuTB.showModal();
            if (rs.index != -1) {
              index = rs.index;
            }
          }

          await this.HienThiThongTinThueBao(data, index);
        } else {
          this.$toast.error("Không tìm thấy thông tin thuê bao " + this.txtTKMaTB);
          this.Clear();
          if (this.txtTKMaTB === "" && this.vma_tb !== "") {
            this.txtTKMaTB = this.vma_tb;
            $('#htkt_matb').focus();
          }
          this.tskt = null;
        }
      }
    },

    showFormTraCuuDanhBa() {
      this.$refs['popupSearchAccount'].show()
    },

    acceptSearchAccount(item) {
      this.txtTKMaTB = item.ma_tb;
      this.$refs['popupSearchAccount'].hide();
    },
  },

}
</script>

<style>
.formTraCuuTB .page-content {
  position: inherit;
}
</style>
