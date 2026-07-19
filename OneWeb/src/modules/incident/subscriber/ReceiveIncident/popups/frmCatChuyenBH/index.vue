<template src="./index.html">

</template>

<script>

import api from "./api";
import moment from "moment";

export default {
  name: "frmCatChuyenBH",
  data() {
    return {
      headerTitle: 'Nhập thông tin báo hỏng khi cắt chuyển cáp',
      formData: {
        vdkcc_id: -1,
        vthuebao_id: -1,
        vma_tb: '',
        vdichvuvt_id: -1,
        vloaitb_id: -1,
        vccbh_id: 0,

        txtNgayKT: '',
        txtSDT: '',
        txtNgayYC: '',
        txtNgayBD: '',
        txtTenNV: '',
        txtDonVi: '',
        txtDiaChiLD: '',
        txtTenTB: '',
        txtLoaiHinh: '',
        txtMaTB: '',
        txtDienThoai: '',
        txtNguoiBH: '',
      },
      dsNhanHong: [],
      cols: [
        {
          fieldName: "ngay_bh",
          headerText: "Ngày báo hỏng",
          allowFiltering: true,
          allowSorting: false,
          format: 'dd/MM/yyyy HH:mm:ss',
          type: 'date',
          textAlign: 'right'
        },
        {
          fieldName: "nguoi_bh",
          headerText: "Người báo hỏng",
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: "dienthoai_lh",
          headerText: "Điện thoại LH",
          allowFiltering: true,
          allowSorting: false,
          textAlign: 'right'
        },
        {
          fieldName: "ten_nv",
          headerText: "Nhân viên",
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: "ten_dv",
          headerText: "Đơn vị",
          allowFiltering: true,
          allowSorting: false
        },
      ],
      tsbtnNhapMoi: true,
      tsbtnCapNhat: true,
      tsbtnXoa: true,
      tsbtnHuyBo: true,
      dsCatChuyen_BH: [],
      itemFocused: null,
      YYYYMMDD: 'YYYY-MM-DD HH:mm:ss',
      DDMMYYYY: 'DD/MM/YYYY HH:mm:ss'
    }
  },
  methods: {
    handleShowModal() {
    },

    hideModal() {
      this.formData = {
        ...this.formData,
        vdkcc_id: -1,
        vthuebao_id: -1,
        vma_tb: '',
        vdichvuvt_id: -1,
        vloaitb_id: -1,
        txtNgayKT: '',
        txtSDT: '',
        txtNgayYC: '',
        txtNgayBD: '',
        txtTenNV: '',
        txtDonVi: '',
        txtDiaChiLD: '',
        txtTenTB: '',
        txtLoaiHinh: '',
        txtMaTB: '',
        txtDienThoai: '',
        txtNguoiBH: '',
      }

      this.$refs.frmCatChuyenBH.hide();
    },

    initData(vdkcc_id, vthuebao_id, vma_tb, vdichvuvt_id, vloaitb_id) {
      this.formData = {
        ...this.formData,
        vdkcc_id,
        vthuebao_id,
        vma_tb,
        vdichvuvt_id,
        vloaitb_id
      }
    },

    async showModal() {
      this.$refs.frmCatChuyenBH.show();
      this.loading(true);
      try {
        // hiển thị thông tin cắt chuyển

        let ds_cc = await this.sp_ds_catchuyen_bh(this.formData.vthuebao_id, this.formData.vdkcc_id)
        if (ds_cc.length > 0) {
          let cc = ds_cc[0];
          this.formData = {
            ...this.formData,
            txtMaTB: cc["ma_tb"],
            txtTenTB: cc["ten_tb"],
            txtDiaChiLD: cc["diachi_ld"],
            txtLoaiHinh: cc["loaihinh_tb"],
            txtDonVi: cc["ten_dv"],
            txtNgayYC: moment(cc["ngay_yc"], this.YYYYMMDD).format(this.DDMMYYYY),
            txtTenNV: cc["ten_nv"],
            txtSDT: cc["so_dt"],
            txtNgayBD: cc["ngaybd_dk"],
            txtNgayKT: cc["ngaykt_dk"],
          }
        }

        this.SetButton(-1);
        await this.LoadGrid();
      } catch (ex) {
        this.$toast.error("Có lỗi : " + ex);
      } finally {
        this.loading(false);
      }
    },

    async sp_ds_catchuyen_bh(vthuebao_id, vdkcc_id) {
      try {
        let rs = await api.sp_ds_catchuyen_bh(this.axios, {
          vthuebao_id,
          vdkcc_id
        })

        return rs.data.data != null ? rs.data.data : [];
      } catch (e) {
        console.log(e);
      }

      return [];
    },

    //set button
    SetButton(kieu) {
      this.tsbtnNhapMoi = false;
      this.tsbtnCapNhat = false;
      this.tsbtnXoa = false;
      this.tsbtnHuyBo = false;

      if (kieu === -1)//Bat dau
      {
        this.tsbtnCapNhat = true;
        this.tsbtnHuyBo = true;
      }

      if (kieu === 0)//Bat dau
      {
        this.tsbtnNhapMoi = true;
        this.Clear();
      }

      if (kieu === 1)//Them moi
      {
        this.tsbtnCapNhat = true;
        this.tsbtnHuyBo = true;
        this.Clear();
      }

      if (kieu === 2)//Huy
      {
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = true;
        this.Clear();
      }

      if (kieu === 3)//Edit
      {
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = true;
        this.tsbtnCapNhat = true;
        this.tsbtnHuyBo = true;
      }
    },

    Clear() {
      this.formData.txtNguoiBH = "";
      this.formData.txtDienThoai = "";
    },

    //loadgrid
    async LoadGrid() {
      try {
        await this.lay_ds_catchuyen_baohong(this.formData.vthuebao_id, this.formData.vdkcc_id)

        if (this.dsNhanHong != null && this.dsNhanHong.length > 0) {
        } else {
          this.Clear();
          this.SetButton(1);
        }
      } catch (ex) {
        this.$toast.error("Có lỗi : " + ex);
      }
    },

    async lay_ds_catchuyen_baohong(vthuebao_id, vdkcc_id) {
      try {
        let rs = await api.lay_ds_catchuyen_baohong(this.axios, {
          vthuebao_id,
          vdkcc_id
        })

        this.dsNhanHong = rs.data.data;
        return;
      } catch (e) {
        console.log(e);
      }

      this.dsNhanHong = [];
    },

    tsbtnNhapMoi_Click() {
      this.SetButton(1);
    },

    tsbtnHuyBo_Click() {
      this.SetButton(0);
    },

    async tsbtnCapNhat_Click() {
      try {
        if (!this.tsbtnNhapMoi) {
          if (!await this.kt_dulieu(1))
            return;
          await this.TaoDuLieu(true);
          let kt = await this.Insert_CatChuyen_BH(JSON.stringify(this.dsCatChuyen_BH));
          if (kt !== '1') {
            this.$toast.error(kt);
            return;
          }
          this.$alert("Thêm mới báo hỏng thuê bao cắt chuyển thành công !");
        } else {
          if (!await this.kt_dulieu(2))
            return;
          await this.TaoDuLieu(false);
          let kt = await this.Update_CatChuyen_BH(JSON.stringify(this.dsCatChuyen_BH));
          if (kt !== '1') {
            this.$toast.error(kt);
            return;
          }
          this.$alert("Cập nhật báo hỏng thuê bao cắt chuyển thành công !");
        }

        this.LoadGrid();
      } catch (ex) {
        this.$toast.error("Có lỗi : " + ex);
      }
    },

    kt_dulieu(vkieu) {// vkieu == 1 : Thêm mới, vkieu == 2 : Cập nhật, vkieu == 3 : Xóa
      if (vkieu === 1 || vkieu === 2) {
        if (this.formData.txtNguoiBH == "") {
          this.$toast.error("Bạn chưa nhập Người báo hỏng !");
          return false;
        }

        if (this.formData.txtDienThoai == "") {
          this.$toast.error("Bạn chưa nhập Số điện thoại liên hệ !");
          return false;
        }

        let _r = new RegExp(/^(\d{10}(,\d{10})*)?$/);
        if (!_r.test(this.formData.txtDienThoai)) {
          this.$root.$toast.error("Số điện thoại liên hệ chưa hợp lệ!");
          return false;
        }
      }

      if (vkieu === 2) {
        if (this.$root.token.getNguoiDungID() != this.itemFocused.nguoidung_id) {
          this.$toast.error("Bạn không được cập nhật thông tin do người dùng khác tạo !");
          return false;
        }
      }
      if (vkieu === 3) {
        if (this.$root.token.getNguoiDungID() != this.itemFocused.nguoidung_id) {
          this.$toast.error("Bạn không được xóa thông tin do người dùng khác tạo !");
          return false;
        }
      }
      return true;
    },

    async TaoDuLieu(themmoi) {
      let dsCatChuyen_BH = [];
      let rowccbh = this.newRowCHBH();
      if (themmoi) {
        this.formData.vccbh_id = await this.GetKey("CCBH_ID");
        rowccbh.CCBH_ID = this.formData.vccbh_id;
        rowccbh.NGAY_BH = moment().format('DD/MM/YYYY HH:mm:ss')
      } else {
        rowccbh.CCBH_ID = this.formData.vccbh_id
        rowccbh.NGAY_BH = this.itemFocused.ngay_bh
      }

      rowccbh.DKCC_ID = this.formData.vdkcc_id;
      rowccbh.THUEBAO_ID = this.formData.vthuebao_id;
      rowccbh.MA_TB = this.formData.vma_tb;
      rowccbh.DICHVUVT_ID = this.formData.vdichvuvt_id;
      rowccbh.LOAITB_ID = this.formData.vloaitb_id;
      rowccbh.NGUOI_BH = this.formData.txtNguoiBH;
      rowccbh.DIENTHOAI_LH = this.formData.txtDienThoai;
      rowccbh.DONVI_ID = this.$root.token.getDonViID();
      rowccbh.NHANVIEN_ID = this.$root.token.getNhanVienID();
      rowccbh.NGUOIDUNG_ID = this.$root.token.getNguoiDungID();
      rowccbh.MAY_CN = await this.$root.token.getMachine();
      rowccbh.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss')
      rowccbh.NGUOI_CN = this.$root.token.getUserName();
      rowccbh.IP_CN = await this.$root.token.getIP();

      dsCatChuyen_BH.push(rowccbh);
      this.dsCatChuyen_BH = [...dsCatChuyen_BH];
      return dsCatChuyen_BH;
    },

    newRowCHBH() {
      return {
        CCBH_ID: null,
        DKCC_ID: null,
        THUEBAO_ID: null,
        MA_TB: null,
        DICHVUVT_ID: null,
        LOAITB_ID: null,
        NGAY_BH: null,
        NGUOI_BH: null,
        DIENTHOAI_LH: null,
        DONVI_ID: null,
        NHANVIEN_ID: null,
        NGUOIDUNG_ID: null,
        MAY_CN: null,
        NGAY_CN: null,
        NGUOI_CN: null,
        IP_CN: null
      }
    },

    async GetKey(keyname) {
      let key = ''
      await api.POST_GET_KEYS(this.axios, {
        keyname: keyname
      }).then(response => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          key = response.data.data
        }
      });

      return key;
    },

    async Insert_CatChuyen_BH(p_json_catchuyen_bh) {
      try {
        let rs = await api.sp_insert_catchuyen_bh(this.axios, {p_json_catchuyen_bh})
        return rs.data.error === '200' ? '1' : rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return 'Lỗi';
    },

    async Update_CatChuyen_BH(p_json_catchuyen_bh) {
      try {
        let rs = await api.sp_update_catchuyen_bh(this.axios, {p_json_catchuyen_bh})
        return rs.data.error === '200' ? '1' : rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return 'Lỗi';
    },

    tsbtnXoa_Click() {
      try {
        this.$confirm("Bạn chắc chắn muốn xóa thông tin báo hỏng không?",
          "Thông báo", {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
          if (!await this.kt_dulieu(3))
            return;

          let kt = await this.Delete_CatChuyen_BH(this.formData.vccbh_id);
          if (kt !== '1') {
            this.$toast.error(kt);
            return;
          }

          this.$alert("Xóa cắt chuyển báo hỏng thành công!");
          this.LoadGrid();
        })
      } catch (ex) {
        this.$toast.error("Có lỗi : " + ex);
      }
    },

    async Delete_CatChuyen_BH(vccbh_id) {
      try {
        let rs = await api.sp_update_catchuyen_bh(this.axios, {vccbh_id})
        return rs.data.error === '200' ? '1' : rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return 'Lỗi';
    },

    selectedRowChanged(item) {
      this.loading(true);
      this.itemFocused = item;
      try {
        this.formData.txtNguoiBH = item.nguoi_bh;
        this.formData.txtDienThoai = item.dienthoai_lh;
        this.formData.vccbh_id = item.ccbh_id;
        this.SetButton(3);
      } catch (ex) {
        this.$toast.error("Có lỗi : " + ex);
      } finally {
        this.loading(false);
      }
    }
  }
}
</script>

<style scoped>
.disabled {
  pointer-events: none;
  opacity: 0.7;
}

.disabled_color {
  color: gray !important;
}
</style>
