<template>
  <ejs-dialog
    :enableResize="true"
    :allowDragging="true"
    :visible="false"
    ref="dialogGiaoPhieuBHNV"
    :position="{ X: 'center', Y: 'top' }"
    :close="closeDialogChuyenTo"
    :header="'Giao phiếu nhân viên'"
    showCloseIcon="true"
    width="80%"
    target="#app .main-wrapper"
  >
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click.prevent="tsbtnChapNhan_Click">
            <span class="icon one-save"></span> Ghi lại
          </a>
        </li>

        <li>
          <a @click.prevent="tsbtnXoaNVGiao_Click">
            <span class="icon one-trash"></span> Xoá giao
          </a>
        </li>
        <li>
          <a @click.prevent="tsbtnSMS_Click">
            <span class="icon fa fa-comments"></span> Gửi SMS
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon one-circle-question"></span> Trợ giúp
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">Danh sách nhân viên</div>
          <div class="pull-right">
            <span class="red">F2: Kiểm tra và xử lý</span>
            <span class="text-main normal"
              >Cấu trúc nhắn tin phản hồi: BH [cách] Mã tỉnh [cách] ID
              phiếu</span
            >
          </div>
          <div class="clearfix"></div>
        </div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key">
                <div>
                  <ejs-checkbox
                    label=" "
                    v-model="model.chkDonViQL"
                    :checked="model.chkDonViQL"
                    @change="chkDonViQL_CheckedChanged"
                  ></ejs-checkbox>
                  <span class="name">Trung tâm</span>
                </div>
              </div>
              <ejs-dropdownlist
                :enabled="enabled.trungtam"
                locale="vi-VN"
                v-model="model.donvi_id"
                :dataSource="options.trungtam"
                :allowFiltering="true"
                :fields="{ value: 'donvi_id', text: 'ten_dv' }"
                :change="cboDonVi_EditValueChanged"
              />
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w90">
                <div>
                  <ejs-checkbox
                    label=" "
                    v-model="model.chkToTC"
                    :checked="model.chkToTC"
                    :change="chkToTC_CheckedChanged"
                  ></ejs-checkbox>
                  <span class="name">Tổ TC</span>
                </div>
              </div>
              <ejs-dropdownlist
                :enabled="enabled.toTC"
                locale="vi-VN"
                v-model="model.totc_id"
                :dataSource="options.toTC"
                :allowFiltering="true"
                popupWidth="auto"
                :fields="{ value: 'donvi_id', text: 'ten_dv' }"
                :change="cboTramTC_EditValueChanged"
              />
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w90">
                <span class="name">Mã NV</span>
              </div>
              <div class="value">
                <input type="text" class="form-control highlight" />
              </div>
            </div>
          </div>
        </div>
        <div class="table-content mart10">
          <DataGrid
            v-bind:columns="columnsDS_NV"
            v-bind:dataSource="options.dsNhanvien"
            :showFilter="true"
            :showColumnCheckbox="true"
            ref="dsNv"
            :allowPaging="true" :enablePagingServer="false"
          >
          </DataGrid>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách nhân viên đã giao nhiệm vụ</div>
        <div class="table-content">
          <ejs-grid
            :dataSource="options.dsNhanvienDG"
            :allowSelection="true"
            :editSettings="editSettings"
            :commandClick="commandClick"
            :rowSelected="rowSelected_dsNhanvienDG"
            :allowPaging="true" :enablePagingServer="false"
          >
            <e-columns>
              <e-column field="ten_dv" headerText="Đơn vị"></e-column>
              <e-column field="ngaygiao" headerText="Ngày giao"></e-column>
              <e-column field="ma_nv" headerText="Mã nhân viên"></e-column>
              <e-column field="ten_nv" headerText="Tên nhân viên"></e-column>
              <e-column field="so_dt" headerText="Điện thoại"></e-column>
              <e-column field="nhiemvu" headerText="Nhiệm vụ"></e-column>
              <e-column field="trangthai" headerText="Trạng thái"></e-column>
              <e-column field="ghichu" headerText="Ghi chú"></e-column>
              <e-column
                field="Commands"
                headerText=""
                :commands="commands"
                textAlign="center"
              >
              </e-column>
            </e-columns>
          </ejs-grid>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import GiaoPhieuNhanVien from "@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue";
import { LOAI_DV } from "@/const/enums";
import API from "../../API";
import Vue from "vue";
import {
  GridPlugin,
  Page,
  Toolbar,
  Edit,
  CommandColumn,
} from "@syncfusion/ej2-vue-grids";

Vue.use(GridPlugin);

export default {
  components: {
    GiaoPhieuNhanVien,
  },
  props: [
    "phieu_id",
    "nguoigv_id",
    "ngay_giao",
    "ma_tb",
    "hdtb_id",
    "vdichvuvt_id",
  ],
  data() {
    return {
      queries: {
        phieu_id: null,
        nguoigv_id: null,
        ngay_giao: null,
        ma_tb: null,
        hdtb_id: null,
        vdichvuvt_id: null,
        thuebao_id: null,
        lstChon: [],
      },
      options: {
        trungtam: [],
        toTC: [],
        dsNhanvien: [],
        dsNhanvienDG: [],
        dsnhanviengp: [],
      },
      model: {
        donvi_id: 0,
        donvibh_id: 0,
        totc_id: 0,
        loainv_id: 0,
        chkDonViQL: false,
        chkToTC: false,
      },
      GIAOVIEC_PHANCONG_THAYTHE: -1,
      QUYEN_XOA_GIAOVIEC_BH: -1,
      KHONG_THAYDOI_TT_GIAOVIEC_BH: -1,
      tudong_sms_giaoviec: 0,
      columnsDS_NV: [
        { fieldName: "ten_dv", headerText: "Đơn vị" },
        { fieldName: "ma_nv", headerText: "Mã NV" },
        { fieldName: "ten_nv", headerText: "Tên NV" },
        { fieldName: "so_dt", headerText: "Điện thoại" },
        { fieldName: "nhiemvu", headerText: "Nhiệm vụ" },
        { fieldName: "ghichu", headerText: "Ghi chú" },
      ],
      kt_load: true,
      enabled: {
        trungtam: false,
        toTC: false,
      },
      editSettings: {
        allowEditing: true,
        allowDeleting: true,
      },
      commands: [
        {
          buttonOption: {
            content: "X",
            cssClass: "Commands btn-primary color-red",
          },
        },
      ],
      nhanvienDG: null,
    };
  },
  provide: {
    grid: [Page, Edit, Toolbar, CommandColumn],
  },
  methods: {
    closeDialogChuyenTo() {
      this.$refs.dialogGiaoPhieuBHNV.hide();
    },
    async showDialog(params) {
      this.queries = { ...params, lstChon: params.lstChon || [] };
      await this.init();
      this.$nextTick(() => {
        this.$refs.dialogGiaoPhieuBHNV.show();
      });
    },
    async init() {
      try {
        Promise.all([
          this.getDsTrungTam(),
          this.getGridDsnhanvien(),
          this.getGridDsnhanvienDagiaoNV(),
        ]);

        const ds1 = await API.LAY_DS_THAMSO_MD_MATS({
          ma_ts: "TUDONG_SMS_GIAOVIEC",
        });
        if (ds1.length) {
          if (ds1[0].ten_ts.toString().trim() != null) {
            this.tudong_sms_giaoviec = ds1[0].ten_ts.toString().trim();
          }
        }
        let ds_kt1 = await API.LAY_DS_THAMSO_MD();
        if (ds_kt1.length > 0) {
          let str_vtemp = "GIAOVIEC_PHANCONG_THAYTHE";
          let det = ds_kt1.filter((x) => x["ma_ts"].toString() == str_vtemp);
          if (det.length > 0) {
            let row = det[0];
            if (row["ten_ts"].toString().trim() == "1")
              this.GIAOVIEC_PHANCONG_THAYTHE = 1;
          }

          str_vtemp = "KHONG_THAYDOI_TT_GIAOVIEC_BH";
          det = ds_kt1.filter((x) => x["ma_ts"].toString() == str_vtemp);
          if (det.length > 0) {
            let row = det[0];
            if (row["ten_ts"].toString().trim() == "1")
              this.KHONG_THAYDOI_TT_GIAOVIEC_BH = 1;
          }

          str_vtemp = "QUYEN_XOA_GIAOVIEC_BH";
          det = ds_kt1.filter((x) => x["ma_ts"].toString() == str_vtemp);
          if (det.length > 0) {
            let row = det[0];
            if (row["ten_ts"].toString().trim() == "1")
              this.QUYEN_XOA_GIAOVIEC_BH = 1;
          }
          this.kt_load = false;
        }
      } catch (e) {
        this.$toast.error("Khởi tạo thất bại");
      }
    },
    async getDsTrungTam() {
      this.options.trungtam = await API.HT_DonVi_loaidv_Combobox({
        p_loaidv_id: this.vdichvuvt_id == 15 ? 660 : LOAI_DV.DONVIQL_LD,
        p_donvi_id: 0,
      });
      if (this.options.trungtam.length > 0) {
        this.model.donvi_id = this.options.trungtam[0].donvi_id;
      }
    },
    async getGridDsnhanvien() {
      try {
        this.options.dsNhanvien = await API.LAY_DS_NHANVIEN_THEO_PHIEU_ID({
          phieu_id: this.queries.phieu_id,
          kieu_id: 1,
        });
      } catch (e) {
        this.options.dsNhanvien = [];
      }
    },
    async getGridDsnhanvienDagiaoNV() {
      try {
        this.options.dsNhanvienDG = await API.LAY_DS_NHANVIEN_THEO_PHIEU_ID({
          phieu_id: this.queries.phieu_id,
          kieu_id: 2,
        });
        this.options.dsNhanvienDG.length && (this.nhanvienDG = this.options.dsNhanvienDG[0]);
      } catch (e) {
        this.options.dsNhanvienDG = [];
      }
    },
    chkToTC_CheckedChanged() {
      this.enabled.toTC = this.model.chkToTC;
      if (this.options.toTC.length) {
        this.model.totc_id = this.options.toTC[0].donvi_id;
        this.Hienthi_DS_cangiao_v2();
      }
    },
    chkDonViQL_CheckedChanged() {
      this.enabled.trungtam = this.model.chkDonViQL;
    },
    async cboDonVi_EditValueChanged() {
      let GIAOPHIEU_BH_NHOM_XLSC = -1;
      const ds1 = await API.LAY_DS_THAMSO_MD_MATS({
        ma_ts: "GIAOPHIEU_BH_NHOM_XLSC",
      });
      if (ds1.length) {
        if (ds1[0].ten_ts.toString().trim() != null) {
          GIAOPHIEU_BH_NHOM_XLSC = ds1[0].ten_ts.toString().trim();
        }
      }
      this.options.toTC = [];
      let ldv = "";
      if (GIAOPHIEU_BH_NHOM_XLSC == 1) ldv += LOAI_DV.NHOM_XLSC.toString();
      else ldv += LOAI_DV.TRAM_VT.toString();
      this.options.toTC = await API.HT_DonViCon_loaidv_Combobox({
        donvi_id: this.model.donvi_id,
        loaidv_id: ldv,
        phanvung_id: this.$root.token.getPhanVungID(),
      });
      if (this.options.toTC.length) {
        this.model.totc_id = this.options.toTC[0].donvi_id;
      }

      if (this.model.chkDonViQL || this.model.chkToTC)
        this.Hienthi_DS_cangiao_v2();
    },
    async Hienthi_DS_cangiao_v2() {
      if (this.model.chkToTC && this.model.totc_id) {
        this.options.dsNhanvien = await API.LAY_DS_NHANVIEN_THEO_PHIEU_V2({
          vdonvi_id: this.model.donvi_id,
          vkieu_id: 1,
          vphieu_id: this.queries.phieu_id,
        });
      } else {
        if (this.model.chkDonViQL && this.model.totc_id) {
          this.options.dsNhanvien = await API.LAY_DS_NHANVIEN_THEO_PHIEU_V2({
            vdonvi_id: this.model.donvi_id,
            vkieu_id: 2,
            vphieu_id: this.queries.phieu_id,
          });
        } else this.options.dsNhanvien = [];
      }
    },
    async tsbtnXoaNVGiao_Click() {
      if (!this.options.dsNhanvienDG.length) {
        this.$toast.error("Không có dữ liệu nhân viên đã giao để xóa!");
        return;
      }
      let isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn thật sự muốn xóa tất cả các phiếu giao việc nhân viên không?`,
        {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        }
      );
      if (isConfirm) {
        try {
          this.$root.showLoading(true);
          const param = this.options.dsNhanvienDG.map((x) => x.phieu_id).join();
          await API.Delete_giaophieu_nv_all({
            p_ds_phieu_id: param,
          });
          this.dsNhanvienDG.length = 0;
          if (this.model.chkDonViQL) this.Hienthi_DS_cangiao_v2();
          else this.Hienthi_DS_cangiao_v1();
          this.$root.showLoading(false);
          this.$toast.success("Xóa thành công!");
        } catch (error) {
          this.$toast.error("Xóa thất bại!");
        }
      }
    },
    async Hienthi_DS_cangiao_v1() {
      this.dsNhanvien = await API.LAY_DS_NHANVIEN_THEO_PHIEU_ID({
        vdonvi_id: this.model.donvi_id,
        vkieu_id: 1,
        vphieu_id: this.queries.phieu_id,
      });
    },
    commandClick: async function (args) {
      try {
        let item;
        if (args.target.classList.contains("Commands")) {
          item = args.rowData;
        }

        this.$root.showLoading(true);
        await API.Delete_giaophieu_nv({
          p_ds_nhanvien_th_id: item.nhanvien_th_id,
          phieu_id: this.queries.phieu_id,
        });
        this.getGridDsnhanvienDagiaoNV();
        if (this.model.chkDonViQL) this.Hienthi_DS_cangiao_v2();
        else this.Hienthi_DS_cangiao_v1();

        this.$root.showLoading(false);
        this.$toast.success("Xóa thành công!");
      } catch (error) {
        this.$toast.error("Xóa thất bại!");
      }
    },
    async tsbtnChapNhan_Click() {
      try {
        let lstChon = this.options.dsNhanvien.filter((x) => {
          if (x.nhiemvu_id) return x;
        });
        // this.options.dsNhanvien.map((x) => {
        //   if (x.so_dt && x.so_dt.length > 15) {
        //     this.$toast.warning("Số điện thoại không được quá 15 ký tự!");
        //     return;
        //   }
        // });
        console.log(lstChon);
        if (!lstChon.length) {
          this.$toast.warning("Hãy nhập nhiệm vụ cho nhân viên!");
          return;
        }
        let vds_nhanvien_id = "";
        for (let i = 0; i < lstChon.length; i++) {
          vds_nhanvien_id += lstChon[i].nhanvien_th_id + ",";
        }
        // this.queries.lstChon.length -> frmGiaoPhieuBHNV_v2
        if (this.queries.lstChon.length) {
          for (const iterator of this.queries.lstChon) {
            await API.Delete_giaophieu_nv({
              p_ds_nhanvien_th_id: vds_nhanvien_id,
              phieu_id: iterator.phieu_id,
            });
          }
          for (const nvien of this.options.dsNhanvien) {
            if (
              nvien.nhiemvu_id.toString() != "" ||
              nvien.ghichu.toString() != ""
            ) {
              for (const iterator of this.queries.lstChon) {
                this.options.dsnhanviengp.push({
                  PHIEU_ID: iterator.phieu_id,
                  NHANVIEN_TH_ID: nvien.nhanvien_th_id,
                  SO_DT: nvien.so_dt,
                  NHIEMVU: nvien.nhiemvu_id,
                  GHICHU: nvien.ghichu,
                  NHANVIEN_GIAO_ID: this.queries.nguoigv_id,
                  NGAYGIAO: this.queries.ngay_giao,
                });
              }
            }
          }
        } else {
          await API.Delete_giaophieu_nv({
            p_ds_nhanvien_th_id: vds_nhanvien_id,
            phieu_id: this.queries.phieu_id,
          });
          for (const nvien of this.options.dsNhanvien) {
            if (
              nvien.nhiemvu_id.toString() != "" ||
              nvien.ghichu.toString() != ""
            ) {
              let arr_nhiemvu_id = nvien.nhiemvu_id.toString().split(",");
              for (const nv of arr_nhiemvu_id) {
                this.options.dsnhanviengp.push({
                  PHIEU_ID: this.queries.phieu_id,
                  NHANVIEN_TH_ID: this.nhanvienDG.nhanvien_th_id,
                  SO_DT: this.nhanvienDG.so_dt,
                  NHIEMVU: this.nhanvienDG.nhiemvu_id,
                  GHICHU: this.nhanvienDG.ghichu,
                  NHANVIEN_GIAO_ID: this.queries.nguoigv_id,
                  NGAYGIAO: this.queries.ngay_giao,
                  NHIEMVU_ID: nv ? nv.toString() : undefined,
                });
              }
            }
          }
        }

        await API.insert_giaophieu_nv({
          json_giaophieu_nv: JSON.stringify(this.options.dsnhanviengp),
        });
        if (this.tudong_sms_giaoviec == 1) {
          for (const item of this.options.dsnhanviengp) {
            // this.queries.lstChon.length -> frmGiaoPhieuBHNV_v2
            if (this.queries.lstChon.length) {
              for (const iterator of this.queries.lstChon) {
                let vnoidung = await API.LAY_NOIDUNG_SMS_GIAOVIEC_V2({
                  vghichu: item.GHICHU,
                  vhdtb_id: 0,
                  vkieu: 2,
                  vma_tb: iterator.ma_tb,
                  vnhiemvu: item.NHIEMVU,
                  vphieu_id: iterator.phieu_id,
                  vthuebao_id: iterator.thuebao_id,
                });
                await API.SendSMS({
                  ghichu: item.GHICHU,
                  nguoi_cn: this.$root.token.getNhanVienID(),
                  noidung: vnoidung,
                  so_dt: item.SO_DT,
                });
                await API.CapNhat_status({
                  p_nhanvien_th_id: this.nhanvienDG.nhanvien_th_id,
                  p_phieu_id: iterator.phieu_id,
                });
              }
            } else {
              let vnoidung = await API.LAY_NOIDUNG_SMS_GIAOVIEC_V2({
                vghichu: item.GHICHU,
                vhdtb_id: 0,
                vkieu: 2,
                vma_tb: this.queries.ma_tb,
                vnhiemvu: item.NHIEMVU,
                vphieu_id: this.queries.phieu_id,
                vthuebao_id: this.queries.thuebao_id,
              });
              await API.SendSMS({
                ghichu: item.GHICHU,
                nguoi_cn: this.$root.token.getNhanVienID(),
                noidung: vnoidung,
                so_dt: item.SO_DT,
              });
              await API.CapNhat_status({
                p_nhanvien_th_id: this.nhanvienDG.nhanvien_th_id,
                p_phieu_id: this.queries.phieu_id,
              });
            }
          }
        }
        this.getGridDsnhanvienDagiaoNV();

        this.$toast.success("Cập nhật thành công!");
      } catch (e) {
        this.$toast.error("Có lỗi trong quá trình giao việc nhân viên!");
      }
    },
    cboTramTC_EditValueChanged() {
      if (!this.kt_load) {
        this.Hienthi_DS_cangiao_v2();
      }
    },
    rowSelected_dsNhanvienDG(item) {
      this.nhanvienDG = item.data;
    },
    async tsbtnSMS_Click() {
      try {
        if (!this.options.dsNhanvienDG.length) {
          this.$toast.warning("Chưa có thông tin giao việc. Hãy kiểm tra lại!");
          return;
        }
        let isConfirm = await this.$bvModal.msgBoxConfirm(
          `Bạn có chắc chắn gửi SMS đến các nhân viên thực hiện nhiệm vụ không?`,
          {
            title: "Thông báo",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        );
        if (isConfirm) {
          let vnoidung = "";
          for (const nvien of this.options.dsNhanvienDG) {
            try {
              if (nvien.so_dt) {
                if (this.queries.lstChon.length) {
                  for (const iterator of this.queries.lstChon) {
                    vnoidung = await API.LAY_NOIDUNG_SMS_GIAOVIEC_V2({
                      vghichu: nvien.ghichu,
                      vhdtb_id: 0,
                      vkieu: 2,
                      vma_tb: iterator.ma_tb,
                      vnhiemvu: nvien.nhiemvu,
                      vphieu_id: iterator.phieu_id,
                      vthuebao_id: iterator.thuebao_id,
                    });
                    await API.SendSMS({
                      ghichu: nvien.ghichu,
                      nguoi_cn: this.$root.token.getNhanVienID(),
                      noidung: vnoidung,
                      so_dt: nvien.so_dt,
                    });
                  }
                } else {
                  vnoidung = await API.LAY_NOIDUNG_SMS_GIAOVIEC_V2({
                    vghichu: nvien.ghichu,
                    vhdtb_id: 0,
                    vkieu: 2,
                    vma_tb: this.queries.ma_tb,
                    vnhiemvu: nvien.nhiemvu,
                    vphieu_id: this.queries.phieu_id,
                    vthuebao_id: this.queries.thuebao_id,
                  });
                  await API.SendSMS({
                    ghichu: nvien.ghichu,
                    nguoi_cn: this.$root.token.getNhanVienID(),
                    noidung: vnoidung,
                    so_dt: nvien.so_dt,
                  });
                }
              }
            } catch (error) {
              this.$toast.error("Có lỗi trong quá trình giao việc nhân viên!");
              return;
            }
          }
        }
        await API.CapNhat_status({
          p_nhanvien_th_id: this.nhanvienDG.nhanvien_th_id,
          p_phieu_id: this.queries.phieu_id,
        });
        this.getGridDsnhanvienDagiaoNV();
        this.$toast.success("Đã gửi tin nhắn đến các nhân viên thực hiện!");
      } catch (error) {}
    },
  },
};
</script>

