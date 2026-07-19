<template src="./frmDSTBCungDoiCap_KH_Khac.html">

</template>

<script>
import Enum from '../../../../Enum'
export default {
  name: "frmDSTBCungDoiCap_KH_Khac",
  data: function () {
    return {
      loaitb_id: null,
      resolvePromise: null,
      ma_tn: null,
      ma_doicap: null,
      cboDKTraCuu: {
        DataSource: [],
        Value: null,
      },
      txTextBoxTC: null,
      chkDichVuVT: false,
      chkDonVi: false,
      chkDK2: false,
      cboDK2: {
        DataSource: [],
        Value: null,
      },
      txtTraCuu2: null,
      chkDK3: false,
      cboDK3: {
        DataSource: [],
        Value: null,
      },
      txtDK3: null,
      txtMaTB: null,
      txtMaKH: null,
      txtMa_lt: null,
      cboDichVuVT: {
        DataSource: [],
        Value: null,
      },
      cboDonVi: {
        DataSource: [],
        Value: null,
      },
      dtHDKH: [],
      dtHDTB: [],
      gridKhachHang: [],
      gridThueBao: [],
      tsbtnTimKiem: true,
      tsbtnCapNhan: true,
      khachhang_id: null,
      kieuld_id: null,

      vsvlan: "",
      vcvlan: "",
      vsite_id: "",
      label2: "Mã KH",
      trangthaitb_id: 0,
      txtTrangThaiTB: "",
      chapnhan: false,
    }
  },
  methods: {
    async onModalShown() {
      await this.frmDSTBCungDoiCap_KH_Khac_Load()
    },
    // onModalHidden() {
    //   if (this.chapnhan) return
    //   this.chapnhan = false;
    //   this.ma_tn = "";
    //   this.resolvePromise({chapnhan: false})
    // },
    show() {
      return new Promise((resolve, reject) => {
        this.resolvePromise = resolve
        this.$refs.DSTBCungDoiCap_kh_khac.show()
      })
    },
    hide(data) {
      this.resolvePromise(!data || !data.chapnhan ? { chapnhan: false } : data)
      this.Close()
      this.$refs.DSTBCungDoiCap_kh_khac.hide()
    },
    onSelectedRowChanged_KH(item) {
      if (item) {
        this.khachhang_id = item.khachhang_id
        this.txtMaKH = item.ma_kh
        this.HienThi_DS_ThueBao()
      }
    },
    onSelectedRowChanged_TB(item) {
      if (item) {
        this.txtMa_lt = item.ma_lt
        this.trangthaitb_id = item.trangthaitb_id
        this.txtTrangThaiTB = item.trangthai_tb

        this.txtMaTB = item.ma_tb
        this.ma_tn = item.ma_tb
        this.ma_doicap = item.madoicap
        this.loaitb_id = item.loaitb_id
      }
    },
    Close() {
      this.loaitb_id = null
      this.ma_tn = null
      this.ma_doicap = null
      this.cboDKTraCuu = {
        DataSource: [],
        Value: null
      }
      this.txTextBoxTC = null
      this.chkDichVuVT = false
      this.chkDonVi = false
      this.chkDK2 = false
      this.cboDK2 = {
        DataSource: [],
        Value: null
      }
      this.txtTraCuu2 = null
      this.chkDK3 = false
      this.cboDK3 = {
        DataSource: [],
        Value: null
      }
      this.txtDK3 = null
      this.txtMaTB = null
      this.txtMaKH = null
      this.txtMa_lt = null
      this.cboDichVuVT = {
        DataSource: [],
        Value: null
      }
      this.cboDonVi = {
        DataSource: [],
        Value: null
      }
      this.dtHDKH = []
      this.dtHDTB = []
      this.gridKhachHang = []
      this.gridThueBao = []
      this.tsbtnTimKiem = true
      this.tsbtnCapNhan = true
      this.khachhang_id = null
      this.kieuld_id = null

      this.vsvlan = ""
      this.vcvlan = ""
      this.vsite_id = ""
      this.label2 = "Mã KH"
      this.trangthaitb_id = 0
      this.txtTrangThaiTB = ""
    },
    async tsbtnChapNhan_Click() {
      if (this.gridThueBao.length == 0) {
        this.$root.toastError("Không có thuê bao trên lưới. Bạn không thể Chấp nhận")
        return
      }
      //Kiểm tra xem có cùng đôi cáp hay không?
      let vdvvt_id = await this.lay_dichvuvt_id_theo_loaitb_id(this.loaitb_id)
      if (vdvvt_id == Enum.DichVuVienThong.ANTOAN_BAOMAT_TT || vdvvt_id == Enum.DichVuVienThong.DICHVU_CNTT ||
          vdvvt_id == Enum.DichVuVienThong.TRUNGTAM_DULIEU || vdvvt_id == Enum.DichVuVienThong.HOINGHI_TRUYENHINH) {
        if (this.trangthaitb_id != Enum.TrangThaiTB.THUONG)
          this.$root.toastError(`Mã thuê bao ${this.ma_tn} đang ở trạng thái ${this.txtTrangThaiTB}`)
        // this.chapnhan = true
        // this.resolvePromise({chapnhan: this.chapnhan})
        this.hide({
          chapnhan: true,
          ma_tn: this.ma_tn,
          ma_doicap: this.ma_doicap
        })
      } else {
        if (await this.KIEMTRA_MATB_TRUYNHAP(this.ma_doicap, this.loaitb_id, Enum.KIEULAP.LAP_LINE_SAN) == 0 ||
            this.kieuld_id == Enum.KieuLapDat.TACH_VLAN) {
          if (this.trangthaitb_id != Enum.TrangThaiTB.THUONG)
            this.$root.toastError(`Mã thuê bao ${this.ma_tn} đang ở trạng thái ${this.txtTrangThaiTB}`)
          // this.chapnhan = true
          // this.resolvePromise({chapnhan: this.chapnhan})
          this.hide({
            chapnhan: true,
            ma_tn: this.ma_tn,
            ma_doicap: this.ma_doicap
          })
        } else {
            if (this.kieuld_id != Enum.KieuLapDat.TACH_VLAN) {
              let thongbao = "";
              thongbao += "Mã thuê bao " + this.ma_tn + " đang có cùng đôi cáp với thuê bao khác. Bạn có muốn chọn không?";
              let kq = await this.$bvModal.msgBoxConfirm(thongbao,
                {
                  title: "Thông báo",
                  centered: true,
                  size: "sm",
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Hủy',
                })
              if (!kq) return
              // this.chapnhan = true;
              // this.resolvePromise({chapnhan: this.chapnhan})
              this.hide({
                chapnhan: true,
                ma_tn: this.ma_tn,
                ma_doicap: this.ma_doicap
              })
            }
        }
      }

    },
    async tsbtnTimKiem_Click() {
      try {
        this.loading(true)
        await this.HienThi_DS_Khachhang()
      }catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async HienThi_DS_Khachhang() {
      try {
        // let dieukien = 0, dieukien1 = 0, dieukien2 = 0, dieukien3 = 0, dichvuvt_id, donvi_id
        let kieutk_id1 = this.cboDKTraCuu.Value, kieutk_id2 = 0, kieutk_id3 = 0, tracuu1 = null, tracuu2 = null, tracuu3 = null, dichvuvt_id, donvi_id
        this.txTextBoxTC = this.txTextBoxTC ? this.txTextBoxTC.trim() : ""
        if (this.txTextBoxTC == "")
          tracuu1 = null
        else tracuu1 = this.txTextBoxTC
        if (this.chkDK2) {
          this.txtTraCuu2 = this.txtTraCuu2 ? this.txtTraCuu2.trim() : ""
          if (this.txtTraCuu2 == "")
            tracuu2 = null
          else tracuu2 = this.txtTraCuu2
        } else kieutk_id2 = this.cboDK2.Value
        if (this.chkDK3) {
          this.txtDK3 = this.txtDK3 ? this.txtDK3.trim() : ""
          if (this.txtDK3 == "")
            tracuu3 = null
          else tracuu3 = this.txtDK3
        } else kieutk_id3 = this.cboDK3.Value
        // if (dieukien1 == 0)
        //   if (dieukien2 == 0)
        //     if (dieukien3 == 0)
        //       dieukien = 0
        //     else dieukien = dieukien3
        //   else {
        //     if (dieukien3 == 0)
        //       dieukien = dieukien2
        //     else dieukien = dieukien2 + " and " + dieukien3
        //   }
        // else {
        //   if (dieukien2 == 0)
        //     if (dieukien3 == 0)
        //       dieukien = dieukien1;
        //     else
        //       dieukien = dieukien1 + " and " + dieukien3;
        //   else if (dieukien3 == 0)
        //     dieukien = dieukien1 + " and " + dieukien2;
        //   else
        //     dieukien = dieukien1 + " and " + dieukien2 + " and " + dieukien3;
        // }
        if (this.chkDichVuVT)
          dichvuvt_id = this.cboDichVuVT.Value
        else dichvuvt_id = 0
        if (this.chkDonVi)
          donvi_id = this.cboDonVi.Value
        else donvi_id = 0
        this.vsvlan = ""
        this.vcvlan = ""
        this.vsite_id = ""
        let tracuu = this.cboDKTraCuu.DataSource.find(x => x.kieutk_id == this.cboDKTraCuu.Value)
        if (tracuu && tracuu.kieu_tk == "Svlan/Cvlan") {
          this.vsvlan = this.txTextBoxTC ? this.txTextBoxTC.trim() : ""
          this.vcvlan = this.txtMaKH ? this.txtMaKH.trim() : ""
          if (this.vsvlan == "" && this.vcvlan == "") {
            this.$root.toastError("Bạn chưa nhập thông tin Svlan và Cvlan để tra cứu !")
            return
          }
          let ds_mgwan = await this.lay_ds_mgwan_svlan_cvlan(this.vsvlan, this.vcvlan)
          if (ds_mgwan.length > 0) {
            this.dtHDKH = ds_mgwan
            this.label2 = "Mã KH"
          } else {
            this.$root.toastError("Không tìm thấy thông tin trong danh bạ !")
            this.gridKhachHang = []
            this.gridThueBao = []
          }
        } else if (tracuu && tracuu.kieu_tk == "Site ID") {
          this.vsite_id = this.txTextBoxTC ? this.txTextBoxTC.trim() : ""
          if (this.vsite_id == "") {
            this.$root.toastError("Bạn chưa nhập thông tin Site ID để tra cứu !")
            return
          }
          let ds_mgwan = await this.lay_ds_mgwan_siteid(this.vsite_id)
          if (ds_mgwan.length > 0) {
            this.dtHDKH = ds_mgwan
          } else {
            this.$root.toastError("Không tìm thấy thông tin trong danh bạ !")
            this.gridKhachHang = []
            this.gridThueBao = []
          }
        } else {
          if (tracuu && tracuu.kieu_tk == "Số ảo")
            this.dtHDKH = await this.LAY_DB_THEO_MATB(this.txTextBoxTC ? this.txTextBoxTC.trim() : "", 0, 0)
          else this.dtHDKH = await this.TRA_CUU_DANH_BA(kieutk_id1, kieutk_id2, kieutk_id3, tracuu1, tracuu2, tracuu3, dichvuvt_id, donvi_id)
        }
        if (this.dtHDKH.length > 0) {
          this.gridKhachHang = this.dtHDKH
        } else {
          this.$root.toastError("Không tìm thấy thông tin trong danh bạ !")
          this.gridKhachHang = []
          this.gridThueBao = []
        }
      } catch (e) {
        throw  e
      }
    },
    async HienThi_DS_ThueBao() {
      try {
        this.loading(true)
        this.dtHDTB = await this.LAY_DS_TB_CUNGDOICAP_THEO_KHID(this.khachhang_id, this.kieuld_id)
        this.gridThueBao = this.dtHDTB
        if (this.gridThueBao.length == 0) {
          this.txtMaTB = ""
          this.txtMa_lt = ""
        }
      }catch (e) {

      }finally {
        this.loading(false)
      }
    },
    async frmDSTBCungDoiCap_KH_Khac_Load() {
      try {
        this.loading(true)
        await this.HT_DICHVU_VT_COMBO_DEV(this.cboDichVuVT, 2)
        await this.HT_Kieu_TK_Cbo_Dev(this.cboDKTraCuu, 2)
        await this.HT_Kieu_TK_Cbo_Dev(this.cboDK2, 2)
        await this.HT_Kieu_TK_Cbo_Dev(this.cboDK3, 2)
        await this.HT_DaiVT_Combobox_DEV(this.cboDonVi, 0)
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }

    },
    async KIEMTRA_MATB_TRUYNHAP() {

    },
    async HT_DICHVU_VT_COMBO_DEV(cbo, kieu) {
      let rs = await this.$root.context.get("web-hopdong/chuyendich-bancheo/dstb-cungdoicap-kh-khac/ht_dichvu_vt_combo_dev", {
        kieu: kieu
      })
      cbo.DataSource = rs.data
      if (cbo.DataSource.length > 0) cbo.Value = rs.data[0].dichvuvt_id
    },
    async HT_Kieu_TK_Cbo_Dev(cbo, nhom_id) {
      let rs = await this.$root.context.get("web-hopdong/chuyendich-bancheo/dstb-cungdoicap-kh-khac/ht_kieu_tk_cbo_dev", {
        nhom_id: nhom_id
      })
      cbo.DataSource = rs.data
      if (cbo.DataSource.length > 0) cbo.Value = rs.data[0].kieutk_id
    },
    async HT_DaiVT_Combobox_DEV(cbo, donvi_dl_id) {
      let rs = await this.$root.context.get("web-hopdong/chuyendich-bancheo/dstb-cungdoicap-kh-khac/ht_daivt_combobox_dev", {
        donvi_dl_id: donvi_dl_id
      })
      cbo.DataSource = rs.data
      if (cbo.DataSource.length > 0) cbo.Value = rs.data[0].donvi_id
    },
    async LAY_DS_TB_CUNGDOICAP_THEO_KHID(khachhang_id, kieuld_id) {
      let rs = await this.$root.context.get("web-hopdong/chuyendich-bancheo/dstb-cungdoicap-kh-khac/lay_ds_tb_cungdoicap_theo_khid", {
        khachhang_id: khachhang_id ? khachhang_id : 0,
        kieuld_id: kieuld_id ? kieuld_id : 0
      })
      return rs.data
    },
    async LAY_DB_THEO_MATB(ma_tb, donvi_dl_id, dichvuvt_id) {
      let rs = await this.$root.context.get("web-hopdong/chuyendich-bancheo/dstb-cungdoicap-kh-khac/lay_db_theo_matb", {
        ma_tb: ma_tb,
        donvi_dl_id: donvi_dl_id,
        dichvuvt_id: dichvuvt_id
      })
      return rs.data
    },
    async TRA_CUU_DANH_BA(kieutk_id1, kieutk_id2, kieutk_id3, tracuu1, tracuu2, tracuu3, dichvuvt_id, donvi_id) {
      let rs = await this.$root.context.post("web-hopdong/chuyendich-bancheo/dstb-cungdoicap-kh-khac/tra_cuu_danh_ba", {
        kieutk_id1: kieutk_id1,
        kieutk_id2: kieutk_id2 ? kieutk_id2 : 0,
        kieutk_id3: kieutk_id3 ? kieutk_id3 : 0,
        tracuu1: tracuu1,
        tracuu2: tracuu2,
        tracuu3: tracuu3,
        donvi_id: donvi_id,
        dichvuvt_id: dichvuvt_id
      })
      return rs.data
    },
    async lay_dichvuvt_id_theo_loaitb_id(loaitb_id) {
      let rs = await this.$root.context.get("web-hopdong/chuyendich-bancheo/dstb-cungdoicap-kh-khac/lay_dichvuvt_id_theo_loaitb_id", {
        loaitb_id: loaitb_id
      })
      if (rs.data.length > 0) {
        return rs.data[0].dichvuvt_id
      } else return null
    },
    async lay_ds_mgwan_svlan_cvlan(svlan, cvlan) {
      let rs = await this.$root.context.get("web-hopdong/chuyendich-bancheo/dstb-cungdoicap-kh-khac/lay_ds_mgwan_svlan_cvlan", {
        svlan: svlan,
        cvlan: cvlan
      })
      return rs.data
    },
    async lay_ds_mgwan_siteid(siteid) {
      let rs = await this.$root.context.get("web-hopdong/chuyendich-bancheo/dstb-cungdoicap-kh-khac/lay_ds_mgwan_siteid", {
        siteid: siteid
      })
      return rs.data
    }
  },
}
</script>

<style>
.frm-ds-thuebao-cung-doicap-kh-khac .disable-btn:hover {
  background-color: white !important;
}

.frm-ds-thuebao-cung-doicap-kh-khac .disable-btn a {
  color: #6c757d !important;
}

.frm-ds-thuebao-cung-doicap-kh-khac .disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
.frm-ds-thuebao-cung-doicap-kh-khac .select2-selection--single .select2-selection__rendered {
  white-space: initial !important;
  height: 28px;
}
</style>
