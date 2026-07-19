<template src="./index.html">
</template>

<script>
import Enum from './Enum'

export default {
  name: "TimNhanVienThuCuoc",
  props: {
    id: String,
    input: Object,
  },
  data: function () {
    return {
      ds: [],
      nhanvienSelected: null,
      // khai bao
      donviql_id: -1,
      dia_chi: "",
      tentuyen: "",
      ten_nvtc: "",
      nhanvientc_id: 0,
      httt_id: -1,
      kt_load: true,
      vkieu: 1, //1: Mặc định tìm kiếm theo đại lý, 2: cho phép tìm theo nhân viên thu nợ (NV Viễn thông).

      MA_NVTC: "",
      MA_TUYEN: "",
      quan_id: 0,
      phuong_id: 0,
      pho_id: 0,
      ap_id: 0,
      khu_id: 0,
      dacdiem_id: 0,
      tuyenthu_id: 0,
      kt_kylai: false,

      // variable
      chkLocNVThuNo: {
        Enabled: false,
        Checked: false,
      },
      cboDonviQL: {
        Value: null,
        DataSource: [],
        Enabled: true
      },
      cboQuan: {
        Value: null,
        DataSource: [],
        Enabled: false,
        Checked: false,
      },
      cboPhuong: {
        Value: null,
        DataSource: [],
        Enabled: false,
        Checked: false,
      },
      chkPhuongEnable: true,
      cboPho: {
        Value: null,
        DataSource: [],
        Enabled: false,
        Checked: false,
      },
      chkPhoEnable: true,
      cboNgo: {
        Value: null,
        DataSource: [],
        Enabled: false,
        Checked: false,
      },
      chkNgoEnable: true,
      cboKhu: {
        Value: null,
        DataSource: [],
        Enabled: false,
        Checked: false,
      },
      chkKhuEnable: true,
      cboDacDiem: {
        Value: null,
        DataSource: [],
        Enabled: false,
        Checked: false,
      },
      chkDacDiemEnable: true,
      cboHTTT: {
        Value: null,
        DataSource: [],
        Enabled: false,
        Checked: false,
        Disabled: false,
      },
      txtMaNVTC: "",
      txtSoDT: "",
      txtTenNVTC: "",
    }
  },
  methods: {
    async onShownModal() {
      if (this.input) {
        if (this.input.QUAN_ID) {
          this.quan_id = this.input.QUAN_ID
        }
        if (this.input.PHUONG_ID) {
          this.phuong_id = this.input.PHUONG_ID
        }
        if (this.input.PHO_ID) {
          this.pho_id = this.input.PHO_ID
        }
        if (this.input.KHU_ID) {
          this.khu_id = this.input.KHU_ID
        }
        if (this.input.AP_ID) {
          this.ap_id = this.input.AP_ID
        }
        if (this.input.DACDIEM_ID) {
          this.dacdiem_id = this.input.DACDIEM_ID
        }
        if (this.input.NHANVIENTC_ID) {
          this.nhanvientc_id = this.input.NHANVIENTC_ID
        }
        if (this.input.TUYENTHU_ID) {
          this.tuyenthu_id = this.input.TUYENTHU_ID
        }
        if (this.input.DONVIQL_ID) {
          this.donviql_id = this.input.DONVIQL_ID
        }
        if (this.input.HTTT_ID) {
          this.httt_id = this.input.HTTT_ID
        }
        if(typeof(this.input.KT_KYLAI) !== 'undefined' || this.input.KT_KYLAI !== null) {
          this.kt_kylai = this.input.KT_KYLAI
        }
        if(this.input.DisableHTTT) this.cboHTTT.Disabled = this.input.DisableHTTT;
      }
      await this.frmTK_NVTC_Load()
    },
    onHiddenModal() {

    },
    async frmTK_NVTC_Load() {
      try {
        if (this.vkieu == 2) {
          this.chkLocNVThuNo.Enabled = true;
        }
        await this.HT_DonVi_loaidv_Combobox(this.cboDonviQL, 0, Enum.LOAI_DV.DONVIQL_TT)
        await this.HT_Quan_Combobox(this.cboQuan)
        if (this.quan_id != 0) {
          this.cboQuan.Value = this.quan_id;
          this.cboQuan.Checked = true;
          this.cboQuan.Enabled = true;
        } else {
          this.cboQuan.Checked = false;
          this.cboQuan.Enabled = false;
        }
        await this.HT_Phuong_Combobox(this.cboPhuong, this.cboQuan.Value)
        if (this.phuong_id != 0) {
          this.cboPhuong.Value = this.phuong_id;
          this.cboPhuong.Checked = true;
          this.cboPhuong.Enabled = true;
        } else {
          this.cboPhuong.Checked = false;
          this.cboPhuong.Enabled = false;
        }
        await this.HT_Pho_Combobox(this.cboPho, this.cboPhuong.Value, 1)
        if (this.pho_id != 0) {
          this.cboPho.Value = this.pho_id;
          this.cboPho.Checked = true;
          this.cboPho.Enabled = true;
        } else {
          this.cboPho.Checked = false;
          this.cboPho.Enabled = false;
        }
        await this.HT_Pho_Combobox(this.cboNgo, this.cboPhuong.Value, 2)
        if (this.ap_id != 0) {
          this.cboNgo.Value = this.ap_id;
          this.cboNgo.Checked = true;
          this.cboNgo.Enabled = true;
        } else {
          this.cboNgo.Checked = false;
          this.cboNgo.Enabled = false;
        }
        await this.HT_Pho_Combobox(this.cboKhu, this.cboPhuong.Value, 3)
        if (this.khu_id != 0) {
          this.cboKhu.Value = this.khu_id;
          this.cboKhu.Checked = true;
          this.cboKhu.Enabled = true;
        } else {
          this.cboKhu.Checked = false;
          this.cboKhu.Enabled = false;
        }
        if (this.dacdiem_id != 0) {
          this.cboDacDiem.Checked = true;
          this.cboDacDiem.Value = this.dacdiem_id;
          this.cboDacDiem.Enabled = true;
        } else {
          this.cboDacDiem.Checked = false;
          this.cboDacDiem.Enabled = false;
        }
        if (this.donviql_id != -1) {
          let ds = this.cboDonviQL.DataSource
          if (ds && ds.length > 0 && ds.filter(x => x.DONVI_ID == this.donviql_id).length == 0) {
            this.$root.toastError(`Không lấy được đơn vị quản lý: donviql_id = ${this.donviql_id}`)
            return
          }
          this.cboDonviQL.Value = this.donviql_id
        }
        await this.HT_HinhThucTT_Combobox(this.cboHTTT)
        if (this.httt_id != -1) {
          this.cboHTTT.Value = this.httt_id
        }
        this.kt_load = false
        if (this.kt_kylai == false) {
          await this.HT_DanhSach_NVTC(this.MA_NVTC, "", this.cboDonviQL.Value, this.quan_id, this.phuong_id, this.pho_id, this.ap_id, this.khu_id, this.dacdiem_id)
        } else
          await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value, this.quan_id, this.phuong_id, this.pho_id, this.ap_id, this.khu_id, this.dacdiem_id)
      } catch (e) {
        console.log(e)
      }
    },
    async HT_DonVi_loaidv_Combobox(cbo, donvi_id, loaidv_id) {
      // await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-loaidv', {loaidv_id: loaidv_id}).then(
      // await this.$root.context.get('/web-thuno/api/thu-no/gach-no/ds-don-vi-quan-ly-dia-chi-thanh-toan').then(
        await this.$root.context.get('/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=2').then(        
        res => {
          if (res.data.length) {
            cbo.Value = res.data[0].DONVI_ID
          }
          cbo.DataSource = res.data
        }
      )
    },
    async HT_Quan_Combobox(cbo) {
      let rs = await this.$root.context.get(
        "/web-cabman/bando_tuyencot/lay_ds_quan_huyen/" + (this.input && this.input.TINH_ID?this.input.TINH_ID:this.$auth.getPhanVungID()) // Dieu chnh cho phan KHDN
      )
      cbo.DataSource = rs.data
      if (cbo.DataSource.length > 0) {
        cbo.Value = cbo.DataSource[0].QUAN_ID
      }
    },
    async HT_Phuong_Combobox(cbo, quan_id) {
      let rs = await this.$root.context.get(
        "/web-cabman/bando_tuyencot/lay_ds_phuong_xa/" + quan_id
      )
      cbo.DataSource = rs.data
      if (cbo.DataSource.length > 0)
        cbo.Value = cbo.DataSource[0].PHUONG_ID
    },
    async HT_Pho_Combobox(cbo, phuong_id, nhompho_id) {
      let rs = await this.$root.context.get(
        "/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/" +
        phuong_id
      );
      switch (nhompho_id) {
        case 1:
          cbo.DataSource = rs.data.dsPho
          break
        case 2:
          cbo.DataSource = rs.data.dsAp
          break
        case 3:
          cbo.DataSource = rs.data.dsKhu
          if (cbo.DataSource.length > 0)
            cbo.Value = cbo.DataSource[0].PHO_ID
      }
    },
    async HT_DacDiem_Combobox(pho_id, ap_id, khu_id, phuong_id) {
      let rs = await this.$root.context.get(
        "/web-cabman/bando_tuyencot/lay_ds_dacdiem",
        {
          phuong_id: phuong_id,
          pho_id: pho_id,
          ap_id: ap_id,
          khu_id: khu_id,
        }
      );
      return rs.data
    },
    async HT_HinhThucTT_Combobox(cbo) {
      let rs = await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-httt')
      cbo.DataSource = rs.data
      if (cbo.DataSource.length > 0)
        cbo.Value = cbo.DataSource[0].httt_id
    },
    async HT_DanhSach_NVTC(manv_tc, tennv_tc, donvi_id, quan_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id) {
      try {
        this.loading(true);
        if (!this.chkLocNVThuNo.Checked) {
          if (this.httt_id != -1) {
            this.ds = await this.Lay_ds_nhanvien_tc_v2(manv_tc, tennv_tc, donvi_id, quan_id, phuong_id, pho_id, ap_id,
              khu_id, dacdiem_id, this.httt_id)
          } else {
            this.ds = await this.LAY_DS_NHANVIEN_TC(manv_tc, tennv_tc, donvi_id, quan_id, phuong_id, pho_id, ap_id,
              khu_id, dacdiem_id)
          }
        } else {
          if (this.httt_id != -1) {
            this.ds = await this.Lay_ds_nhanvien_thuno_v2(manv_tc, tennv_tc, donvi_id, quan_id, phuong_id, pho_id, ap_id,
              khu_id, dacdiem_id, this.httt_id)
          } else {
            this.ds = await this.LAY_DS_NHANVIEN_THUNO(manv_tc, tennv_tc, donvi_id, quan_id, phuong_id, pho_id, ap_id,
              khu_id, dacdiem_id)
          }
        }
        if (this.ds.length > 0) {
          if (this.tuyenthu_id != 0) {
            let index = this.ds.findIndex(x => x.tuyenthu_id == this.tuyenthu_id)
            if (index != -1) {
              this.$nextTick(() => this.$refs.dsNhanVien.setCurrentSelectedRow(index))
            }
          }
        } else {
          this.ds = []
          this.txtMaNVTC = ""
          this.txtSoDT = ""
          this.txtTenNVTC = ""
        }
      } catch (error) {
        throw error;
      } finally {
        this.loading(false);
      }
    },
    async txtMaNVTC_KeyPress() {
      await this.HT_DanhSach_NVTC(this.txtMaNVTC, "", this.cboDonviQL.Value,
        0, 0, 0, 0, 0, 0)
      if (this.ds.length <= 0) {
        this.$root.toastError("Không tìm thấy thông tin")
      }
    },
    async txtTenNVTC_KeyPress() {
      await this.HT_DanhSach_NVTC("", this.txtTenNVTC, this.cboDonviQL.Value,
        0, 0, 0, 0, 0, 0)
    },
    async cboDonviQL_SelectionChangeCommitted() {
      if (this.cboDonviQL.DataSource.length > 0) {
        if (!this.cboDonviQL.Value) return
        if (this.kt_load) return
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value;
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value;
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value;
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value;
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value;
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.Value;
        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id)
      }
    },
    async tsbtnTimKiem_Click() {
      try {
        this.loading(true);
        let quantt_id = 0, phuongtt_id = 0, phott_id = 0, aptt_id = 0, khutt_id = 0, dacdiemtt_id = 0;
        if (this.cboQuan.Checked)
          quantt_id = this.cboQuan.Value;
        if (this.cboPhuong.Checked)
          phuongtt_id = this.cboPhuong.Value;
        if (this.cboPho.Checked)
          phott_id = this.cboPho.Value;
        if (this.cboNgo.Checked)
          aptt_id = this.cboNgo.Value;
        if (this.cboKhu.Checked)
          khutt_id = this.cboKhu.Value;
        if (this.cboDacDiem.Checked)
          dacdiemtt_id = this.cboDacDiem.Value;
        await this.HT_DanhSach_NVTC(this.txtMaNVTC, this.txtTenNVTC, this.cboDonviQL.Value,
          quantt_id, phuongtt_id, phott_id, aptt_id, khutt_id, dacdiemtt_id)
      } catch (error) {

      } finally {
        this.loading(false);
      }
    },
    tsbtnCapNhan_Click() {
      if (this.ds.length > 0) {
        if (this.nhanvienSelected) {
          this.nhanvientc_id = this.nhanvienSelected.nhanvien_id
          this.MA_NVTC = this.nhanvienSelected.manv_tc
          this.MA_TUYEN = this.nhanvienSelected.ma_tuyenthu
          this.tuyenthu_id = this.nhanvienSelected.tuyenthu_id
          this.dia_chi = this.nhanvienSelected.dia_chi
          this.ten_nvtc = this.nhanvienSelected.tennv_tc
          this.tentuyen = this.nhanvienSelected.tentuyen
        }
      } else {
        this.MA_NVTC = ""
      }
      let data = {
        nhanvientc_id: this.nhanvientc_id,
        ma_nvtc: this.MA_NVTC,
        ma_tuyen: this.MA_TUYEN,
        tuyenthu_id: this.tuyenthu_id,
        dia_chi: this.dia_chi,
        ten_nvtc: this.ten_nvtc,
        tentuyen: this.tentuyen,
        donviql_id: this.cboDonviQL.Value
      }
      this.$emit('response-data', data)
      this.$bvModal.hide(this.id)
    },
    async chkQuan_CheckedChanged() {
      if (this.cboQuan.Checked == true) {
        this.cboQuan.Enabled = true
        this.chkPhuongEnable = true
        this.chkPhoEnable = false
        this.chkNgoEnable = false
        this.chkKhuEnable = false
        this.chkDacDiemEnable = false

        this.cboPhuong.Checked = false
        this.cboPho.Checked = false
        this.cboNgo.Checked = false
        this.cboKhu.Checked = false
        this.cboDacDiem.Checked = false
      } else {
        this.cboQuan.Enabled = false;
        await this.cboDonviQL_SelectionChangeCommitted();
        //Nếu bỏ chọn quận thì bỏ chọn phường, phố
        this.cboPhuong.Checked = false;
        this.cboPhuong.Enabled = false;

        this.cboPho.Checked = false;
        this.cboPho.Enabled = false;

        this.cboNgo.Checked = false;
        this.cboNgo.Enabled = false;

        this.cboKhu.Enabled = false;
        this.cboKhu.Enabled = false;
      }
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.Value

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    async chkPhuong_CheckedChanged() {
      if (this.cboPhuong.Checked == true) {
        this.cboPhuong.Enabled = true;
        this.chkPhoEnable = true;
        this.chkNgoEnable = true;
        this.chkKhuEnable = true;
      } else {
        this.cboPhuong.Enabled = false;

        this.chkPhoEnable = false;
        this.cboPho.Checked = false;
        this.cboPho.Enabled = false;

        this.chkNgoEnable = false;
        this.cboNgo.Checked = false;
        this.cboNgo.Enabled = false;

        this.chkKhuEnable = false;
        this.cboKhu.Checked = false;
        this.cboKhu.Enabled = false;
      }
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.Value

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    async chkPho_CheckedChanged() {
      this.cboPho.Enabled = this.cboPho.Checked
      await this.lay_dacdiem()
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.Value

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    async chkNgo_CheckedChanged() {
      this.cboNgo.Enabled = this.cboNgo.Checked
      await this.lay_dacdiem()
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.Value

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    async chkKhu_CheckedChanged() {
      this.cboKhu.Enabled = this.cboKhu.Checked
      await this.lay_dacdiem()
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.Value
        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id)
      }
    },
    async chkDacDiem_CheckedChanged() {
      this.cboDacDiem.Enabled = this.cboDacDiem.Checked
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.Value
        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id)
      }
    },
    async cboDacDiem_SelectedValueChanged() {
      if (this.cboDacDiem.DataSource.length > 0) {
        if (this.cboDacDiem.Value == null) return
      }
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.SelectedValue

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    async cboPhuong_SelectedIndexChanged() {
      if (this.cboPhuong.DataSource.length > 0) {
        if (this.cboPhuong.Value == null) return
        await this.HT_Pho_Combobox(this.cboPho, this.cboPhuong.Value, 1)
        await this.HT_Pho_Combobox(this.cboNgo, this.cboPhuong.Value, 2)
        await this.HT_Pho_Combobox(this.cboKhu, this.cboPhuong.Value, 3)
      } else {
        this.cboPho.DataSource = []
        this.cboNgo.DataSource = []
        this.cboKhu.DataSource = []
      }
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.SelectedValue

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    async cboQuan_SelectedIndexChanged() {
      if (this.cboQuan.DataSource.length > 0) {
        if (this.cboQuan.Value == null) return
        await this.HT_Phuong_Combobox(this.cboPhuong, this.cboQuan.Value)
      } else this.cboPhuong.DataSource = []
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.SelectedValue

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    async cboPho_SelectedIndexChanged() {
      if (this.cboPho.DataSource.length > 0) {
        if (this.cboPho.Value == null) return
      }
      await this.lay_dacdiem()
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.SelectedValue

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    async cboNgo_SelectedIndexChanged() {
      if (this.cboNgo.DataSource.length > 0) {
        if (this.cboNgo.Value == null) return
      }
      await this.lay_dacdiem()
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.SelectedValue

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    async cboKhu_SelectedIndexChanged() {
      if (this.cboKhu.DataSource.length > 0) {
        if (this.cboKhu.Value == null) return
      }
      await this.lay_dacdiem()
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.SelectedValue

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    async chkNVThuNo_CheckedChanged() {
      let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
      if (this.cboQuan.Checked)
        vquan_id = this.cboQuan.Value
      if (this.cboPhuong.Checked)
        vphuong_id = this.cboPhuong.Value
      if (this.cboPho.Checked)
        vpho_id = this.cboPho.Value
      if (this.cboNgo.Checked)
        vap_id = this.cboNgo.Value
      if (this.cboKhu.Checked)
        vkhu_id = this.cboKhu.Value
      if (this.cboDacDiem.Checked)
        vdacdiem_id = this.cboDacDiem.SelectedValue

      await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
        vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
    },
    async chkHTTT_CheckedChanged() {
      this.cboHTTT.Enabled = this.cboHTTT.Checked;
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.SelectedValue

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    async cboHTTT_SelectedValueChanged() {
      if (this.cboHTTT.DataSource.length > 0) {
        if (this.cboHTTT.Value == null) return
      }
      if (!this.kt_load) {
        let vquan_id = 0, vphuong_id = 0, vpho_id = 0, vap_id = 0, vkhu_id = 0, vdacdiem_id = 0;
        if (this.cboQuan.Checked)
          vquan_id = this.cboQuan.Value
        if (this.cboPhuong.Checked)
          vphuong_id = this.cboPhuong.Value
        if (this.cboPho.Checked)
          vpho_id = this.cboPho.Value
        if (this.cboNgo.Checked)
          vap_id = this.cboNgo.Value
        if (this.cboKhu.Checked)
          vkhu_id = this.cboKhu.Value
        if (this.cboDacDiem.Checked)
          vdacdiem_id = this.cboDacDiem.SelectedValue

        await this.HT_DanhSach_NVTC("", "", this.cboDonviQL.Value,
          vquan_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id);
      }
    },
    dsNV_selectedRowChanged(item) {
      if (item) {
        this.nhanvienSelected = item
        this.nhanvientc_id = item.nhanvien_id
        this.txtMaNVTC = item.manv_tc
        this.txtTenNVTC = item.tennv_tc
        this.txtSoDT = item.so_dt
      }
    },
    async Lay_ds_nhanvien_tc_v2(manv_tc, tennv_tc, donvi_id, quan_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id, vhttt_id) {
      let rs = await this.$root.context.post('web-hopdong/thaydoithongtinkhuyenmai/lay_ds_nhanvien_tc_v2', {
        vmanv_tc: manv_tc,
        vtennv_tc: tennv_tc,
        vdonvi_id: donvi_id,
        vquan_id: quan_id,
        vphuong_id: phuong_id,
        vpho_id: pho_id,
        vap_id: ap_id,
        vkhu_id: khu_id,
        vdacdiem_id: dacdiem_id,
        vhttt_id: vhttt_id,
      })
      return rs.data
    },
    async LAY_DS_NHANVIEN_TC(manv_tc, tennv_tc, donvi_id, quan_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id) {
      let rs = await this.$root.context.post('web-hopdong/thaydoithongtinkhuyenmai/lay_ds_nhanvien_tc', {
        vmanv_tc: manv_tc,
        vtennv_tc: tennv_tc,
        vdonvi_id: donvi_id,
        vquan_id: quan_id,
        vphuong_id: phuong_id,
        vpho_id: pho_id,
        vap_id: ap_id,
        vkhu_id: khu_id,
        vdacdiem_id: dacdiem_id,
      })
      return rs.data
    },
    async Lay_ds_nhanvien_thuno_v2(manv_tc, tennv_tc, donvi_id, quan_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id, vhttt_id) {
      let rs = await this.$root.context.post('web-hopdong/thaydoithongtinkhuyenmai/lay_ds_nhanvien_thuno_v2', {
        vmanv_tc: manv_tc,
        vtennv_tc: tennv_tc,
        vdonvi_id: donvi_id,
        vquan_id: quan_id,
        vphuong_id: phuong_id,
        vpho_id: pho_id,
        vap_id: ap_id,
        vkhu_id: khu_id,
        vdacdiem_id: dacdiem_id,
        vhttt_id: vhttt_id,
      })
      return rs.data
    },
    async LAY_DS_NHANVIEN_THUNO(manv_tc, tennv_tc, donvi_id, quan_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id) {
      let rs = await this.$root.context.post('web-hopdong/thaydoithongtinkhuyenmai/lay_ds_nhanvien_thuno', {
        vmanv_tc: manv_tc,
        vtennv_tc: tennv_tc,
        vdonvi_id: donvi_id,
        vquan_id: quan_id,
        vphuong_id: phuong_id,
        vpho_id: pho_id,
        vap_id: ap_id,
        vkhu_id: khu_id,
        vdacdiem_id: dacdiem_id,
      })
      return rs.data
    },
    async lay_dacdiem() {
      let ipho_id = 0, ikhu_id = 0, iap_id = 0, iphuong_id = 0;
      if (this.cboPhuong.Value == null || this.cboPhuong.Value == 0)
        iphuong_id = 0;
      else
        iphuong_id = this.cboPhuong.Value;
      if (this.cboPho.DataSource.length > 0 && this.cboPho.Checked) {
        if (this.cboPho.Value == null || this.cboPho.Value == 0)
          ipho_id = 0;
        else
          ipho_id = this.cboPho.Value;
      }
      if (this.cboNgo.DataSource.length > 0 && this.cboNgo.Checked) {
        if (this.cboNgo.Value == null || this.cboNgo.Value == 0)
          iap_id = 0;
        else
          iap_id = this.cboNgo.Value;
      }
      if (this.cboKhu.DataSource.length > 0 && this.cboKhu.Checked) {
        if (this.cboKhu.Value == null || this.cboKhu.Value == 0)
          ikhu_id = 0;
        else
          ikhu_id = this.cboKhu.Value;
      }
      if (ipho_id == 0 && iap_id == 0 && ikhu_id == 0) {
        this.cboDacDiem.DataSource = []
        this.cboDacDiem.Checked = false
        this.chkDacDiemEnable = false
        this.cboDacDiem.Enabled = false
      } else {
        let ds_dd = await this.HT_DacDiem_Combobox(ipho_id, iap_id, ikhu_id, iphuong_id)
        if (ds_dd.length > 0) {
          this.cboDacDiem.DataSource = ds_dd
          this.cboDacDiem.Value = ds_dd[0].DACDIEM_ID
          this.cboDacDiem.Checked = false
          this.chkDacDiemEnable = true
          this.cboDacDiem.Enabled = false
        } else {
          this.cboDacDiem.DataSource = []
          this.cboDacDiem.Checked = false
          this.chkDacDiemEnable = false
          this.cboDacDiem.Enabled = false
        }
      }
    }
  }
}
</script>

<style>
.form-nvtc .select2-container .select2-selection--single  {
    overflow: hidden;
}
.form-nvtc .select2-container .select2-selection--single .select2-selection__rendered {
    white-space: initial;
}
.disabled { pointer-events: none; color:lightgray; border-color:lightgray; }
</style>
