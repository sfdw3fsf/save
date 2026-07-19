<template src="./frmGiaoPhieuNV.template.html">
</template>
<script>
import Select2Ext from "../components/Select2Ext";
import DataGridCustom from "../components/DataGridCustom";
import GiaoViecGrid from "./components/GiaoViecGrid.vue"
import DaGiaocGrid from "./components/DaGiaoGrid.vue"
import Vue from "vue";
const EventBus = new Vue()
import {
  LOAI_DV
} from "@/const/enums";

export default {
  components: {
    Select2Ext,
    GiaoViecGrid,
    DataGridCustom,
    DaGiaocGrid
  },
  props: ['tinh_id',],
  data() {
    return {
      objgiaophieu: [],
      dsnhanviengp: [],
      tt_nd: {},
      bangts: [],
      ds_nhanvien: [],
      vphieu: 0,
      vdonvi: 0,
      vnhanvienid: 0, vLoaiNV_ID: 0,
      vnoidung: 0,
      vghichu: 0,
      vnhanvien_giao_id: 0,
      vngaygiao: "0",
      kt_load: true,
      cp: null,
      vma_tb: "",
      vhdtb_id: 0,
      _dsphieuGV: 0,
      vdsphieu: [],
      dsma_tb: 0,
      dshdtb_id: [],
      _vloaihd: 0,
      vkieugiao: 0,  // Hoangpkn : 03/06/2017 .0 : giao việc bình thường, 1: giao việc thu hồi vật tư
      huonggiao_id: 0,//SonNMP 28 May 2019: https://cntt.vnpt.vn/browse/PM3DHSXKD-2093
      dt_nvu: 0,
      dsNhiemvu: [],
      collumns_dg: [
        {
          fieldName: "ma_nv",
          headerText: "Mã NV",
          allowFiltering: true,
          allowSorting: false,
          width: 100
        },
        {
          fieldName: "ten_nv",
          headerText: "Tên nhân viên",
          isGroupedColumn: true,
          captionTemplate: '${key} - ${count} nhiệm vụ',
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        {
          fieldName: "so_dt",
          headerText: "Điện thoại",
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        {
          fieldName: "nhiemvu",
          headerText: "Nhiệm vụ",
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        {
          fieldName: "kqxl_id",
          headerText: "Kết quả XL",
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        {
          fieldName: "trangthai",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        {
          fieldName: "ghichu",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: false,
          width: 140
        }
      ],
    }
  },
  mounted() {
    console.log("Vào mount")
    $('.modal-content').removeAttr('tabindex');
  },
  methods: {
    delete_1_row(){
      this.Hienthi_DS_cangiao()
      this.HienThiDanhSach_Dagiao_NV()
      this.$emit("giaoviec_success")
    },
    async getDsNhiemVu() {
      let dataNhiemvu = await Vue.axios.post(
        "web-thicong/hoinghi_truyenhinh/lay_ds_nhiemvu_hg_v2", {
          vhuonggiao_id: this.huonggiao_id
        }
      );
      this.dsNhiemvu = dataNhiemvu.data.data ? dataNhiemvu.data.data.map(item => ({...item, nhiemvu: item.nhiemvu.replaceAll(",", ";")})) : [];
    },

    frnGiaoPhieuNV(_vdsphieu, _vdonvi, _nhanvien_giao_id, _ngaygiao) {

      if (Array.isArray(_vdsphieu) && _vdsphieu.length > 0) {
        this.vphieu = _vdsphieu[0];
        this.vdsphieu = _vdsphieu;
      } else {
        this.vdsphieu = [_vdsphieu];
        this.vphieu = _vdsphieu
      }
      this.vdonvi = _vdonvi;
      this.vnhanvien_giao_id = _nhanvien_giao_id;
      this.vngaygiao = _ngaygiao;
      this.vLoaiNV_ID = -1;
    },
    async frnGiaoPhieuNV_Load() {
      this.getDsNhiemVu()
      let rs = await this.axios.post("web-thicong/hoinghi_truyenhinh/lay_ds_loainv")
      if (this.vkieugiao == 1)
        this.$refs.chk_LoaiNV.disabled = true;
      this.$refs.cbo_LoaiNV.dataSource = rs.data ? rs.data.data : []
      this.$refs.cbo_LoaiNV.dataValueField = "loainv_id"
      this.$refs.cbo_LoaiNV.dataTextField = "ten"
      this.$refs.cbo_LoaiNV.value = this.$refs.cbo_LoaiNV.dataSource.length > 0 ? this.$refs.cbo_LoaiNV.dataSource[0].loainv_id : null
      //   let rs = await this.axios.post("web-thicong/hoinghi_truyenhinh/sp_lay_ds_nhanvien_giaoviec_frmgiaoviecnv", {
      //     "vphieu_id": this.vphieu,
      //     "vkieugiao": this.vkieugiao,
      //     "vloainv": this.vLoaiNV_ID,
      //     "vnhanvien_id": this.vnhanvien_giao_id
      //   })
      //   this.ds_nhanvien = rs.data ? rs.data.data : []

      //   if (this.ds_nhanvien.length > 0) {
      //     this.$refs.gridNhanVien.dataSource = this.ds_nhanvien
      //   }

      this.Hienthi_DS_cangiao()
      //   } else {
      //     this.$toast.error("Không có nhân viên nào !");
      //     return;
      //   }

      this.HienThiDanhSach_Dagiao_NV();

      //region Combo Donvi
      let rs_cboDonVi = await this.axios.post("web-ccdv/tien_trinh_bao_hong/sp_lay_donvi_quanly_ldv", { "p_loaidv_id": LOAI_DV.DONVIQL_LD, "p_donvi_id": 0,"p_phanvung_id":this.tinh_id })
      this.$refs.cboDonVi.HT_COMBOBOX(rs_cboDonVi.data ? rs_cboDonVi.data.data : [], "ten_dv", "donvi_id")
      //endregion

      //region Combo Phòng bán hàng
      let rs_cboPhongBH = await this.axios.post("web-ccdv/tien_trinh_bao_hong/sp_lay_donvi_quanly_ldv", { "p_loaidv_id": LOAI_DV.PHONG_BANHANG_KV, "p_donvi_id": 0,"p_phanvung_id": this.tinh_id  })
      this.$refs.cboPhongBH.HT_COMBOBOX(rs_cboPhongBH.data ? rs_cboPhongBH.data.data : [], "ten_dv", "donvi_id")
      //endregion
      this.kt_load = false;
    },
    async Hienthi_DS_cangiao() {
      let nv_id = 0
      if (this.$refs.chkDonViQL.checked == true) {
        nv_id = this.$refs.cboTramTC.value
      }
      if (this.$refs.chkPBH.checked == true)
        nv_id = this.$refs.cboPhongBH.value

      console.log(nv_id)

      let rs_gridNhanVien

      if(this.$refs.chkDonViQL.checked == false && this.$refs.chkPBH.checked == false && this.$refs.chk_LoaiNV.checked == false) {
        rs_gridNhanVien = await this.axios.post("web-thicong/hoinghi_truyenhinh/sp_lay_ds_nhanvien_giaoviec_frmgiaoviecnv", {
          "vphieu_id": this.vphieu,
          "vkieugiao": this.vkieugiao,
          "vloainv": "-1",
          "vnhanvien_id": this.vnhanvien_giao_id
        })
      } else {
        rs_gridNhanVien = await this.axios.post("web-thicong/hoinghi_truyenhinh/hienthi_danhsach_cangiao", {
          "chk_donviql": (this.$refs.chkDonViQL.checked || this.$refs.chkPBH.checked )? 1 : 0,
          "kieugiao": this.vkieugiao,
          "phieu_id": this.vphieu,
          "donvi_id": nv_id,
          "loainv_id": this.$refs.chk_LoaiNV.checked ? this.$refs.cbo_LoaiNV.value : "-1",
          "nhanvien_id": this.vnhanvien_giao_id
        })
      }


      let data = rs_gridNhanVien.data ? rs_gridNhanVien.data.data : [];
      let revert = {}
      data.forEach(item => {
        if(revert[item.ma_nv]) {
          revert[item.ma_nv].nhiemvu += "," + item.nhiemvu
          revert[item.ma_nv].ghichu = revert[item.ma_nv].ghichu
        } else {
          revert[item.ma_nv] = item
        }
        revert[item.ma_nv].dsNhiemvu = this.dsNhiemvu
      })
      this.$refs.gridNhanVien.dataSource = Object.values(revert)
    }, cbo_LoaiNV_EditValueChanged() {
      this.vLoaiNV_ID = -1;
      if (!this.$refs.cbo_LoaiNV.value)
        return;
      this.vLoaiNV_ID = this.$refs.cbo_LoaiNV.value;
      this.Hienthi_DS_cangiao()
    },
    tsbtnChapNhan_Click() {
      this.CapNhat()
    },
    async CapNhat() {
      try {
        let kt = false;
        let lstChon = []
        for (let i = 0; i < this.$refs.gridNhanVien.dataSource.length; i++) {
          if (this.$refs.gridNhanVien.dataSource[i].nhiemvu) {
            kt = true;
            lstChon.push(this.$refs.gridNhanVien.dataSource[i])
          }
          if (this.$refs.gridNhanVien.dataSource[i].so_dt && this.$refs.gridNhanVien.dataSource[i].so_dt.Length > 15) {
            this.$toast.error("Số điện thoại không được quá 15 ký tự !");
            return;
          }
        }

        if (!kt) {
          this.$toast.error("Hãy nhập nhiệm vụ cho nhân viên!");
          return;
        }

        let data = [];
        if (this.dsphieuGV && this.dsphieuGV.length > 0) {
          this.$refs.gridNhanVien.dataSource.forEach((item) => {
            item.nhiemvu.split(",").forEach((nv_text) => {
              let nhiemnhu = this.dsNhiemvu.find(
                (nv) => nv.nhiemvu == nv_text
              );
              this.dsphieuGV.forEach(async (phieu_item) => {
                await this.axios.post("/web-thicong/hoinghi_truyenhinh/delete_giaophieu_nv", {
                  phieu_id: phieu_item,
                });
                if (nhiemnhu) {
                  data.push({
                    PHIEU_ID: phieu_item,
                    NHANVIEN_TH_ID: item.nhanvien_th_id,
                    NHIEMVU: nhiemnhu.nhiemvu,
                    GHICHU: item.ghichu,
                    NHANVIEN_GIAO_ID: this.nguoigv_id,
                    NGAYGIAO: this.formatDate(this.ngaygiao),
                    SO_DT: item.so_dt,
                    STATUS: 0,
                    NHIEMVU_ID: nhiemnhu.nhiemvu_id,
                    KQXL_ID: null,
                  });
                }
              });
            });
          });
        } else {
          this.$refs.gridNhanVien.dataSource.forEach((item) => {
            if (item.nhiemvu) {
              item.nhiemvu.split(",").forEach((nv_text) => {
                let nhiemnhu = this.dsNhiemvu.find(
                  (nv) => nv.nhiemvu == nv_text
                );
                if (nhiemnhu) {
                  data.push({
                    PHIEU_ID: this.vphieu,
                    NHANVIEN_TH_ID: item.nhanvien_th_id,
                    NHIEMVU: nhiemnhu.nhiemvu,
                    GHICHU: item.ghichu,
                    NHANVIEN_GIAO_ID: this.vnhanvien_giao_id,
                    NGAYGIAO: this.vngaygiao,
                    SO_DT: item.so_dt,
                    STATUS: 0,
                    NHIEMVU_ID: nhiemnhu.nhiemvu_id,
                    KQXL_ID: null,
                  });
                }
              });

            }
          });

          await this.axios.post("/web-thicong/hoinghi_truyenhinh/delete_giaophieu_nv", {
            phieu_id: this.vphieu,
          });
        }

        let check = await this.axios.post("/web-thicong/hoinghi_truyenhinh/fn_an_capnhat_giaophieu_nv", {
          json_giaophieu_nv: data,
        });
        this.HienThiDanhSach_Dagiao_NV();
        if(check.data && check.data.data && check.data.data == "OK")
          this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");
        else
          this.$toast.error(check.data.data ? check.data.data : check.data.message);
        this.$emit("giaoviec_success")
      }
      catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi trong quá trình giao việc nhân viên " + err.message);
        return;
      }
    },
    async HienThiDanhSach_Dagiao_NV() {
      // hoangpkn : 16/06/2017
      //DataSet ds = new TCHopDongFacade().LAY_DS_NHANVIEN_THEO_PHIEU_ID(vphieu, tt_nd.nhanvien_id, 3);
      let ds = await this.axios.post("web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id", {
        "phieu_id": this.vphieu,
        "kieugiao": 3,
        "nhanvien_id": this.vnhanvien_giao_id
      })

      let data = ds.data ? ds.data.data : [];
      data.map((item, key) => {
        item.kqxl = item.kqxl_id ? (item.kqxl_id == 1 ? "Tốt" : "Không tốt") : null
        item.stt =  key + 1
      })
      this.$refs.gridDaGiao.dataSource = ds.data ? ds.data.data : [];
    },
    chkDonViQL_CheckedChanged() {
      if (this.$refs.chkDonViQL.checked) {
        this.$refs.chkDonViQL.checked = true;
        this.$refs.cboDonVi.disabled = false;
        this.$refs.cboTramTC.disabled = false;
        this.$refs.chkPBH.checked = false;
        this.$refs.cboPhongBH.disabled = true
        this.Hienthi_DS_cangiao();
      }
      else {
        this.$refs.chkDonViQL.checked = false;
        this.$refs.cboDonVi.disabled = true
        this.$refs.cboTramTC.disabled = true
        if (!this.$refs.chkPBH.checked) {
          this.Hienthi_DS_cangiao();
        }
      }
      this.cboDonVi_EditValueChanged()
    },
    async cboDonVi_EditValueChanged() {
      try {
        this.$refs.cboTramTC.dataSource = []
        let ldv = "";
        if (this.vkieugiao == 1 && this.$root.token.getPhanVungID() == "21")
          ldv += LOAI_DV.TRAM_VT;
        else {
          ldv += LOAI_DV.TRAM_VT;
        }

        let dt = await this.axios.post("web-thicong/hoinghi_truyenhinh/ht_donvicon_loaidv_combobox",
        {
            "loaidv_id": ldv,
            "donvi_id": this.$refs.cboDonVi.value
        })

        this.$refs.cboTramTC.HT_COMBOBOX(dt.data ? dt.data.data : [], "ten_dv", "donvi_id")
        //endregion
      }
      catch (err) {
        console.log(err)
        this.$toast.error("Có lỗi : " + err.message);
      }
    },
    cboTramTC_EditValueChanged() {
      if (!this.kt_load) {
        this.Hienthi_DS_cangiao();
      }
    },
    async tsbtnSMS_Click() {
      try {
        if (this.$refs.gridDaGiao.length <= 0) {
          this.$toast.error("Chưa có thông tin giao việc. Hãy kiểm tra lại!");
          return;
        }
        this.$confirm("Bạn có chắc chắn gửi SMS đến các nhân viên thực hiện nhiệm vụ không?").then(async () => {
          var kt = false;
          var vnoidung = "";
          var vdsnoidung = [];
          var vghichu = "";
          var vnhiemvu = "";
          var ds = [];
          var rs = await this.axios.post("web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id", {
            "phieu_id": this.vphieu,
            "nhanvien_id": this.$root.token.getNhanVienID(),
            "kieu_id": 2
          })
          ds = rs.data ? rs.data.data : []
          console.log(ds, "ds đây")
          if (ds && ds.length > 0) {
            for (let j = 0; j < ds.length; j++) {
              if (ds[j]["so_dt"]) {
                try {
                  try {
                    vghichu = ds[j]["ghichu"];
                    vnhiemvu = ds[j]["nhiemvu"];
                    if (this.vhdtb_id == 0) throw new Exception("");
                    if (!this.vdsphieu) {
                      let rs_noidung = await this.axios.post("web-thicong/hoinghi_truyenhinh/lay_noidung_sms_giaoviec_v2", {
                        "vkieu": 1,
                        "vhdtb_id": this.vhdtb_id,
                        "vthuebao_id": 0,
                        "vma_tb": this.vma_tb,
                        "vnhiemvu": vnhiemvu,
                        "vghichu": vghichu,
                        "vphieu_id": this.vphieu
                      })
                      vnoidung = rs_noidung.data ? rs_noidung.data.data : [];
                    } else {
                      
                      for (let i = 0; i < this.vdsphieu.length; i++) {
                        let rs_noidung = await this.axios.post("web-thicong/hoinghi_truyenhinh/lay_noidung_sms_giaoviec_v2", {
                          "vkieu": 1,
                          "vhdtb_id": this.dshdtb_id.length>0?this.dshdtb_id[i]:this.vhdtb_id?this.vhdtb_id:0,
                          "vthuebao_id": 0,
                          "vma_tb": this.dsma_tb?this.dsma_tb[i]:'',
                          "vnhiemvu": vnhiemvu,
                          "vghichu": vghichu,
                          "vphieu_id": this.vdsphieu[i]
                        })
                        vdsnoidung.push(rs_noidung.data ? rs_noidung.data.data : "");
                      }
                    }
                  }
                  catch (err) {
                    vnoidung = this.vma_tb + " : " + vghichu + " ; " + vnhiemvu;
                  }
                  if (vdsnoidung.length == 0) {
                    await this.axios.post("/tichhop/sms/send", {msisdn: ds[j]["so_dt"],content: vnoidung});
                  } else {
                    for (let i = 0; i < vdsnoidung.length; i++)
                      await this.axios.post("/tichhop/sms/send", {msisdn: ds[j]["so_dt"],content: vdsnoidung[i]});

                  }
                  kt = true;

                } catch (error) {
                  console.log(error)
                }
              }
            }

            if(kt) {
              await this.axios.post("web-thicong/hoinghi_truyenhinh/fn_capnhat_trangthai_giaoviec_nv", {
                "vphieu_id": this.vphieu,
                "vstatus": 1
              });
            }
            this.$toast.success("Đã gửi tin nhắn đến các nhân viên thực hiện!");
            this.HienThiDanhSach_Dagiao_NV();
          }
        })
        // if(kt)
        //objgiaophieu.CapNhat_status(vphieu);
      } catch (err) {
        this.$toast.error("" + err.message);
      }
    },
    chkPBH_CheckedChanged() {
      if (this.$refs.chkPBH.checked) {
        this.$refs.cboPhongBH.disabled = false;
        this.$refs.chkDonViQL.checked = false;
        this.$refs.cboDonVi.disabled = true
        this.$refs.cboTramTC.disabled = true
        this.Hienthi_DS_cangiao();
      } else {
        this.$refs.cboPhongBH.disabled = true
        if (!this.$refs.chkDonViQL.checked) {
          if (this.vkieugiao == 0) {
            this.Hienthi_DS_cangiao()
          }
          else {
            this.$refs.gridNhanVien.dataSource = [];
          }
        }
      }
    }, cboPhongBH_EditValueChanged() {
      this.Hienthi_DS_cangiao();
    }
    ,
    async tsbtnKQXL_Click() {
      try {
        let kt = false;
        if (this.$refs.gridDaGiao.dataSource.length <= 0) {
          this.$toast.error("Chưa có nhân viên được giao nhiệm vụ !");
          return;
        }
        let data = this.TaoDuLieu_Update_KQXL()
        await this.axios.post("web-thicong/hoinghi_truyenhinh/capnhat_kqxl_giaoviec", {
          phieu_id: this.vphieu,
          "json_giaophieu_nv": data
        })
        this.HienThiDanhSach_Dagiao_NV();
        this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");
      }
      catch (err) {
        this.$toast.error("Có lỗi trong quá trình cập nhật Kết quả xử lý " + err.message);
        return;
      }
    }, TaoDuLieu_Update_KQXL() {
      let dsnhanviengp = [];
      for (let i = 0; i < this.$refs.gridDaGiao.dataSource.length; i++) {
        let row = {};
        let item = this.$refs.gridDaGiao.dataSource[i]
        row.PHIEU_ID = this.vphieu;
        row.NHANVIEN_TH_ID = item.nhanvien_th_id;
        row.SO_DT = item.so_dt;
        row.NHIEMVU = item.nhiemvu;
        row.GHICHU = item.ghichu;
        row.NHANVIEN_GIAO_ID = this.vnhanvien_giao_id;
        row.NGAYGIAO = item.ngaygiao;
        row.STATUS = item.status;
        row.NHIEMVU_ID = item.nhiemvu_id;
        row.KQXL_ID = item.kqxl ? (item.kqxl == "Tốt" ? 1 : 3) : null
        dsnhanviengp.push(row);
      }

      return dsnhanviengp
    },
    async tsbtnXoaNVGiao_Click() {
      try {
        this.$confirm(`Bạn thật sự muốn xóa tất cả các phiếu giao việc nhân viên không?`, {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }).then(async () => {
          try {
            if (this.vdsphieu && this.vdsphieu.length > 0) {
              this.vdsphieu.forEach(async (phieuid) => {
                await this.axios.post("/web-thicong/hoinghi_truyenhinh/delete_giaophieu_nv", {
                  phieu_id: phieuid,
                });
              });
            } else {
              await this.axios.post("/web-thicong/hoinghi_truyenhinh/delete_giaophieu_nv", {
                phieu_id: this.phieu_id,
              });
            }
            this.$toast.success("Xóa giao việc thành công");
            setTimeout(() => {
              this.Hienthi_DS_cangiao()
              this.HienThiDanhSach_Dagiao_NV()
            }, 2000)
            this.$emit("giaoviec_success")
          } catch (e) {
            console.log(e);
            this.$toast.error("Xóa giao việc thất bại");
          }
        });
      }
      catch (err) {
        this.$toast.error("" + err.message);
      }
    },
    chk_LoaiNV_CheckedChanged() {
      this.$refs.cbo_LoaiNV.disabled = !this.$refs.chk_LoaiNV.checked;
      this.cbo_LoaiNV_EditValueChanged();
    },
    load() {
      this.$nextTick(() => {
        $('.modal-content').removeAttr('tabindex');
        console.log($('.modal-content'))
        this.frnGiaoPhieuNV_Load()
      })
    }
  }
}
</script>
<style>
</style>
