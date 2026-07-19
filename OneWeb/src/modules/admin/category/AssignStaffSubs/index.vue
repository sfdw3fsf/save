<template src="./template.html"></template>
<script>
import state from "./define.js";
import { Edit } from "@syncfusion/ej2-vue-grids";
import magd_popup from "./magd_popup.vue"
export default {
  mounted() {
    this.rbTrangThaiDL.value = this.$route.params.type == 'danhba' ? 2 : 1
    this.frmGanNVQL_TB_Load();
  },
  components: {
    magd_popup
  },
  data() {
    return {
      ...state,
    };
  },
  watch: {
    "cboLoaiNV.value"(val) {
      let selected = this.$refs.grvDS_DBHD.getSelectedRecords();
      if (selected.length > 0 && val)
        this.NAP_DS_DVI_LINE(selected[0].donvi_qlid, val);
      this.getNhanvienChuagan(val);
    },
  },
  provide: {
    grid: [Edit],
  },
  methods: {
    CLEAR() {
      this.grvDS_DBHD.DataSource = [];
      this.grcNVQL_ChuaGan.DataSource = [];
      this.grcDS_NVPT.DataSource = [];
      this.grcDS_NVPT_HT.DataSource = [];
      this.grcBienDong.DataSource = [];
    },
    async frmGanNVQL_TB_Load() {
      try {
        this.getcboLoaiNV();
      } catch (err) { }
    },
    async NAP_DS_DVI_LINE(donvi_ql, loai_nv) {
      try {
        let data = await this.axios.post(
          "web-thicong/thuebao/lay_ds_combobox_loai_nhanvien",
          {
            dvi_qly_id: donvi_ql,
            loainv_id: loai_nv,
          }
        );

        this.cboDonvi.DataSource = data.data.data.dsDonVi;
      } catch (err) {
        this.loading(false);
      }
    },
    async grvDS_DBHD_FocusedRowChanged(item) {
      if (item.length > 0 && this.cboLoaiNV.value) {
        this.NAP_DS_DVI_LINE(item[0].donvi_qlid, this.cboLoaiNV.value);
      }
    },
    async getcboLoaiNV() {
      this.loading(true);
      try {
        let data = await this.axios.get(
          "web-quantri/danhmuc-chung/ADMIN_LOAI_NV"
        );
        this.cboLoaiNV.DataSource = data.data.data;
      } catch (err) {
        this.cboLoaiNV.DataSource = [];
        this.loading(false);
      }
      this.loading(false);
    },
    async chooseData(data) {
      let item = data.rowData
      let rs = await this.axios.get(
        "web-thicong/thuebao/load_ds_hopdong_thuebao",
        {
          params: {
            hdkhId: item.hdkhId,
          },
        }
      );
      this.grvDS_DBHD.DataSource = rs.data.data.ds_dbhd
      let dt_hd = rs.data.data.dt_hd
      this.grcNVPTM.DataSource = dt_hd.filter((item) => item.loai == 1);
      this.grcNVPTM_hotro.DataSource = dt_hd.filter((item) => item.loai == 2);
      this.grcNVPTM_duytri.DataSource = dt_hd.filter((item) => item.loai == 3);
    },
    async getNhanvienChuagan(loai_nv) {
      try {
        let data = [];
        if (this.cboLoaiNV.value == "449") {
          data = await this.axios.get(
            "web-thicong/thuebao/lay_ds_nhanvien_chuagan"
          );
        } else {
          let hopdong = this.$refs.grvDS_DBHD.getSelectedRecords();
          if (hopdong.length > 0) {
            data = await this.axios.get(
              "web-thicong/thuebao/lay_ds_nhanvien_chuagan_v2",
              {
                params: {
                  loaiNvId: loai_nv,
                  dvQlId: hopdong[0].donvi_qlid,
                },
              }
            );
          }
        }

        this.grcNVQL_ChuaGan.DataSource = data.data.data;
      } catch (e) { }
    },
    async getDanhsachHopdong(loai_nv) {
      this.CLEAR()

      if (this.rbTrangThaiDL.value == 2) {
        let data = await this.axios.get(
          "web-thicong/thuebao/timkiem_ds_hopdong",
          {
            params: {
              kieu: this.rbTrangThaiDL.value,
              maTb: this.txtMaGD.value,
            },
          }
        );
        this.grvDS_DBHD.DataSource = data.data.data.dt_hd_tb

        let dt_hd = data.data.data.dt_hd
        this.grcNVPTM.DataSource = dt_hd.filter((item) => item.loai == 1);
        this.grcNVPTM_hotro.DataSource = dt_hd.filter((item) => item.loai == 2);
        this.grcNVPTM_duytri.DataSource = dt_hd.filter((item) => item.loai == 3);
      }
      if (this.rbTrangThaiDL.value == 1) {
        let check = await this.axios.get("web-thicong/thuebao/lay_ds_hopdong_theo_mathuebao", { params: { maTb: this.txtMaGD.value } })
        this.popup_data = check.data.data
        this.$refs.magd_popup.showModal()
        //this.grvDS_DBHD.DataSource = data.data.data.dt_hd_tb
      }

    },
    toLeft() {
      let dataSelected = this.$refs.grcNVQL_ChuaGan.getSelectedRecords();

      if (this.tab == 1) {
        this.grcNVPTM.DataSource =
          this.grcNVPTM.DataSource.concat(dataSelected);
      }
      if (this.tab == 2) {
        this.grcNVPTM_hotro.DataSource =
          this.grcNVPTM_hotro.DataSource.concat(dataSelected);
      }
      if (this.tab == 3) {
        this.grcNVPTM_duytri.DataSource =
          this.grcNVPTM_duytri.DataSource.concat(dataSelected);
      }
    },
    toRight() {
      if (this.chkToanBoTB.Checked) {
        this.$confirm("Bạn đang gỡ nhân viên khỏi toàn bộ thuê bao. Bạn có chắc chắn muốn gỡ tất cả không?", {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "warning",
        }).then(async () => {
            if (this.tab == 1) {
              this.grcNVPTM.DataSource = []
              this.grcNVPTM.DataSource = [...this.grcNVPTM.DataSource]
            }
            if (this.tab == 2) {
              this.grcNVPTM_hotro.DataSource = []
              this.grcNVPTM_hotro.DataSource = [...this.grcNVPTM_hotro.DataSource]
            }
            if (this.tab == 3) {
              this.grcNVPTM_duytri.DataSource = []
              this.grcNVPTM_duytri.DataSource = [...this.grcNVPTM_duytri.DataSource]
            }
        });
      } else {
        if (this.tab == 1) {
          let item = this.$refs.grcNVPTM.getSelectedRecords();
          let index = this.grcNVPTM.DataSource.indexOf(item[0]);
          this.grcNVPTM.DataSource.splice(index, 1);
          this.grcNVPTM.DataSource = [...this.grcNVPTM.DataSource]
        }
        if (this.tab == 2) {
          let item = this.$refs.grcNVPTM_hotro.getSelectedRecords();
          let index = this.grcNVPTM_hotro.DataSource.indexOf(item[0]);
          this.grcNVPTM_hotro.DataSource.splice(index, 1);
          this.grcNVPTM_hotro.DataSource = [...this.grcNVPTM_hotro.DataSource]
        }
        if (this.tab == 3) {
          let item = this.$refs.grcNVPTM_duytri.getSelectedRecords();
          let index = this.grcNVPTM_duytri.DataSource.indexOf(item[0]);
          this.grcNVPTM_duytri.DataSource.splice(index, 1);
          this.grcNVPTM_duytri.DataSource = [...this.grcNVPTM_duytri.DataSource]
        }
      }
      
      
    },
    async tsbtnXacNhan_Click() {
      let selected = this.$refs.grvDS_DBHD.getSelectedRecords();
      let item = selected[0];
      let id = this.rbTrangThaiDL.value == 1 ? item.hdkh_id_ql : item.hdtb_id;
      let danhsach = [];

      this.grcNVPTM.DataSource.forEach((nvptm) => {
        danhsach.push({
          ISCHECK: "0",
          THUEBAO_ID: item.thuebao_id,
          NHANVIEN_ID: nvptm.nhanvien_id,
          LOAINV_ID: nvptm.loainv_id,
          TYLE: nvptm.tyle,
          LOAI: 1,
          NGAY_INS: nvptm.ngay_ins,
          NGAY_CN: nvptm.ngay_cn,
          MAY_CN: nvptm.may_cn,
          NGUOI_CN: nvptm.nguoi_cn,
          IP_CN: nvptm.ip_cn,
          MA_NV: nvptm.ma_nv,
          TEN_NV: nvptm.ten_nv,
          LOAI_NV: nvptm.loai_nv,
          DONVI_ID: nvptm.donvi_id,
          TEN_DV: nvptm.ten_dv,
        });
      });

      this.grcNVPTM_hotro.DataSource.forEach((nvptm) => {
        danhsach.push({
          ISCHECK: "0",
          THUEBAO_ID: item.thuebao_id,
          NHANVIEN_ID: nvptm.nhanvien_id,
          LOAINV_ID: nvptm.loainv_id,
          TYLE: nvptm.tyle,
          LOAI: 2,
          NGAY_INS: nvptm.ngay_ins,
          NGAY_CN: nvptm.ngay_cn,
          MAY_CN: nvptm.may_cn,
          NGUOI_CN: nvptm.nguoi_cn,
          IP_CN: nvptm.ip_cn,
          MA_NV: nvptm.ma_nv,
          TEN_NV: nvptm.ten_nv,
          LOAI_NV: nvptm.loai_nv,
          DONVI_ID: nvptm.donvi_id,
          TEN_DV: nvptm.ten_dv,
        });
      });

      this.grcNVPTM_duytri.DataSource.forEach((nvptm) => {
        danhsach.push({
          ISCHECK: "0",
          THUEBAO_ID: item.thuebao_id,
          NHANVIEN_ID: nvptm.nhanvien_id,
          LOAINV_ID: nvptm.loainv_id,
          TYLE: nvptm.tyle,
          LOAI: 3,
          NGAY_INS: nvptm.ngay_ins,
          NGAY_CN: nvptm.ngay_cn,
          MAY_CN: nvptm.may_cn,
          NGUOI_CN: nvptm.nguoi_cn,
          IP_CN: nvptm.ip_cn,
          MA_NV: nvptm.ma_nv,
          TEN_NV: nvptm.ten_nv,
          LOAI_NV: nvptm.loai_nv,
          DONVI_ID: nvptm.donvi_id,
          TEN_DV: nvptm.ten_dv,
        });
      });


      let rs = await this.axios.post(
        "web-thicong/thuebao/gan_nhanvienql_dbhd",
        {
          kieu: this.rbTrangThaiDL.value,
          id: id,
          khachhang_id: item.khachhang_id ? item.khachhang_id : 0,
          nhanvien_id_tt: this.$root.token.getNhanVienID(),
          danhsach: JSON.stringify(danhsach),
        }
      );

      this.$toast.success(rs.data.data.ketqua);
    },
  },
};
</script>
