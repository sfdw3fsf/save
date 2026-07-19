<template src="./AssignKPIList.html"> </template>
<style src="./AssignKPIList.scss"></style>

<script>
import breadcrumb from "@/components/breadcrumb";
import moment from 'moment'
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import DataGrid from "@/components/Controls/DataGrid";
import api from './API.js'

export default {
  name: 'AssignKPIList',
  components: {
    breadcrumb,
    DatePicker,
    DataGrid
  },
  data: () => ({
    header: {
      title: "Phân giao KPI",
      list: [
        {
          name: 'Phân giao KPI',
          link: { 
            name: 'Ui.buttons',
            active: true 
          }
        }
      ],
    },
    popupComponentName: '',
    popupComponentHeader: '',
    popupComponent: null,
    popupComponentData: null,
    popupComponentAttr: {},
    popupComponentEvts: {
      
    },
    tt_nd: {
      phanvung_id: 0, //this.$root.token.getPhanVungID(),
      donvi_id: 0, //this.$root.token.getDonviID(),
      nhanvien_id: 0, //this.$root.token.getNhanVienID(),
      ip_cn: "",
      may_cn: "",
      nguoi_cn: "",
    },
    dateFormat: "MM/YYYY",
    file_refresh: 0,    
    kpi_id: 0,
    cboPhongBHKV: {text: 'Phòng BHKV', value: 0, Items: []},
    dtpThangPG: {text: 'Tháng phân giao', value: moment(new Date()).format('MM/YYYY')},
    txtFilePG: {text: 'File phân giao KPI', value: '', fullFile: []},
    txtFileMau: {text: 'Tải file mẫu phân giao', value: 'Link File Mẫu'},
    grdAssignKPIList: {
      list: [],
      header: [
        {
          fieldName: "ID",
          headerText: "ID",
          allowFiltering: true,
        },
        {
          fieldName: "PHONG_BHKV",
          headerText: "Phòng BHKV",
          allowFiltering: true,
        },
        {
          fieldName: "MA_NV",
          headerText: "Mã nhân viên",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_NV",
          headerText: "Tên nhân viên",
          allowFiltering: true,
        },
        {
          fieldName: "LOAI_NV",
          headerText: "Loại nhân viên",
          allowFiltering: true,
        },
        {
          fieldName: "THANG_PG",
          headerText: "Tháng phân giao",
          allowFiltering: true,
        },
        {
          fieldName: "THOIGIAN_PG",
          headerText: "Thời gian phân giao",
          allowFiltering: true,
        },
        {
          fieldName: "NGUOI_PG",
          headerText: "Người phân giao",
          allowFiltering: true,
        },
        {
          fieldName: "action",
          headerText: "Thao tác",
          textAlign: "center",
          template: () => {
            return {
              template: {
                template: `<div>
                    <button type="button" class="btn btn-outline-primary" style="width: 50px; height: 30px;" @click="onbtnChitiet_Click">
                      <span class="icon one-eye"></span>
                    </button>
                    <button type="button" class="btn btn-outline-primary" style="width: 50px; height: 30px;" @click="onbtnEdit_Click">
                     <span class="icon one-edit"></span>
                    </button>
                    <button type="button" class="btn btn-outline-danger" style="width: 50px; height: 30px;" @click="onbtnXoa_Click">
                      <span class="icon one-trash"></span>
                    </button>
                  </div>`,
                data: function () {
                  return {
                    data: {},
                  };
                },
                methods: {
                  onbtnChitiet_Click() {
                    this.$parent.$parent.$parent.btnChitiet_click(this.data);
                  },
                  onbtnEdit_Click() {
                    // this.$parent.$parent.$parent.btnChitiet_click(this.data);
                  },
                  onbtnXoa_Click() {
                    this.$parent.$parent.$parent.btnXoa_click(this.data);
                  },
                },
              },
            };
          },
        },
      ],
      value: {},
      isEnabled: true,
      checked: [],
    },
  }),
  created: async function () {
  },
  watch: {},
  computed: {},
  mounted () {
    this.formLoad()
  },
  methods: {
    formLoad: async function () {
      this.tt_nd = {
        phanvung_id: Number(await this.$root.token.getPhanVungID()),
        donvi_id: Number(await this.$root.token.getDonViID()),
        nhanvien_id: Number(await this.$root.token.getNhanVienID()),
        ip_cn: await this.$root.token.getIP(),
        may_cn: await this.$root.token.getMachine(),
        nguoi_cn: await this.$root.token.getUserName(),
      };
      this.getDSPBH();
      this.grdAssignKPIList.list = [
        { ID: "1", PHONG_BHKV: "Phòng BH GSW", PBH_ID: 1, MA_NV: 30, TEN_NV: 'G', LOAI_NV: 'GDV', THANG_PG: '05/2023', THOIGIAN_PG: "01/05/2023 10:10:10", NGUOI_PG: 'admin'},
        { ID: "2", PHONG_BHKV: "Phòng BH LAL", PBH_ID: 2, MA_NV: 6, TEN_NV: 'F', LOAI_NV: 'GDV', THANG_PG: '05/2023', THOIGIAN_PG: "01/05/2023 10:10:10", NGUOI_PG: 'admin'},
        { ID: "3", PHONG_BHKV: "Phòng BH BKN", PBH_ID: 3, MA_NV: 7, TEN_NV: 'F', LOAI_NV: 'GDV', THANG_PG: '05/2023', THOIGIAN_PG: "01/05/2023 10:10:10", NGUOI_PG: 'admin'},
        { ID: "4", PHONG_BHKV: "Phòng BH MIL", PBH_ID: 4, MA_NV: 34, TEN_NV: 'F', LOAI_NV: 'GDV', THANG_PG: '05/2023', THOIGIAN_PG: "01/05/2023 10:10:10", NGUOI_PG: 'admin'},
      ]

      // await this.getDSKichBanBNM();
    },
    getDSPBH: function () {
      this.cboPhongBHKV.Items = [
        { PBH_ID: 1, PHONG_BHKV: "Phòng BH GSW"},
        { PBH_ID: 2, PHONG_BHKV: "Phòng BH LAL"},
        { PBH_ID: 3, PHONG_BHKV: "Phòng BH BKN"},
        { PBH_ID: 4, PHONG_BHKV: "Phòng BH MIL"},
      ]
    },    
    kiemtra_capnhat: function () {
      if (!this.cboPhongBHKV.value) {
        this.ShowError("Bạn chưa chọn Phòng bán hàng khu vực!");
        return false
      }

      if (!this.txtFilePG.fullFile || this.txtFilePG.fullFile.length == 0 || !this.txtFilePG.value) {
        this.ShowError("Bạn chưa chọn File phân giao KPI!");
        return false
      }

      return true
    },
    CAPNHAT: async function () {
      try {
        var input = {
          phanvung_id: this.tt_nd.phanvung_id,
          user: this.tt_nd.nguoi_cn
        };
        // let response = await api.update_kichBanBNM(this.axios, input);
        // if (
        //   response &&
        //   response.data &&
        //   response.data.error_code == "BSS-00000000"
        // ) {
        //   this.ShowSuccess('Ghi lại kịch bản thành công!')
        // } else {
        //   this.ShowError('Lỗi ghi lại: ' + response.data.message_detail)
        // }
      } catch (ex) {
        console.log(ex);
        this.ShowError("Lỗi ghi lại: " + ex);
      }
    },
    changeThangPG: function (value_) {
      if (!moment(value_, "MM/YYYY").isValid()) {
        this.dtpThangPG.value = "01/2000";
      } else {
        this.dtpThangPG.value = value_;
      }
    },
    txtFilePG_ButtonClick(event) {
      try {
        this.file_refresh += 1
        this.txtFilePG.value = event.target.files[0].name
        this.txtFilePG.fullFile = event.target.files                
      } catch (e) {
        this.$toast.error('Lỗi import file: ' + e)
      }
    },
    grdAssignKPIList_SelectionChanged: function (item) {
      if (item && item.length > 0) {
        this.kpi_id = item[0].ID
        this.cboPhongBHKV.value = item[0].PBH_ID
        this.dtpThangPG.value = item[0].THANG_PG
      }      
    },
    clearForm: function () {
      this.kpi_id = 0
      this.cboPhongBHKV.value = 0
      this.dtpThangPG.value = moment(new Date()).format('MM/YYYY')
      this.grdAssignKPIList.list = []

      // import file
      this.file_refresh = 0;
      this.txtFilePG.value = ''
      this.txtFilePG.fullFile = []
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup("popupComponents");
        return;
      }
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case "popup_DSYeuCauTaoScript": 
            console.log(val)
            break;
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.$toast.error("" + ex.data.message_detail);
        } else {
          this.$toast.error("" + ex);
        }
        
        if (this.debug) console.error(ex);
      } finally {
        this.ClosePopup("popupComponents");
        this.loading(false);
      }
    },
    btnChitiet_click: function (item) {
      this.popupComponent = () =>
        import(
          "./popupKPIDetail.vue"
        );
      this.popupComponentName = "popupKPIDetail";
      this.popupComponentHeader = 'Chi tiết Phân giao KPI';
      this.popupComponentData = {
        isPopup: true,
        ...item  
      }; 
      this.Popup("popupComponents");
    },
    btnLayTT_click: function () {
      this.formLoad();
    },
    btnNhapMoi_click: function () {
      this.clearForm();
    },
    btnGhiLai_click: function () {
      if (!this.kiemtra_capnhat()) return;
      var thongbao = "ghi lại";
      this.$bvModal
        .msgBoxConfirm("Bạn có muốn " + thongbao + " danh sách phân giao KPI không?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            this.$toast.warning("Chức năng đang được xây dựng!")
            // await this.CAPNHAT();
            this.loading(false);
          }
        })
        .catch((e) => {
          console.log(e);
          this.ShowError("Lỗi ghi lại dữ liệu");
          this.loading(false);
        });
    },
    btnXoa_click: function (data) {
      if (data) {
        this.kpi_id = data.ID
      }
      if (!this.kpi_id || this.kpi_id == 0) {
        this.ShowError('Bạn chưa chọn Phân giao KPI để xóa!')
        return
      }
      this.$bvModal
        .msgBoxConfirm("Bạn có muốn xóa Phân giao KPI " + this.kpi_id + " không?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            this.$toast.warning("Chức năng đang được xây dựng!")
            this.loading(false);
          }
        })
        .catch((e) => {
          console.log(e);
          this.ShowError("Lỗi ghi lại dữ liệu");
          this.loading(false);
        });
    },
  }
}
</script>



