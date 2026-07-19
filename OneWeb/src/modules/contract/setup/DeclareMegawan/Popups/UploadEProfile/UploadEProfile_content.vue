<template src="./UploadEProfile.html"></template>
<style src="./UploadEProfile.scss" scoped></style>
<script>
import Vue from "vue";
import treemenu from "./treemenu";
import gridview from "@/components/Shared/gridview";
import {
  Page,
  Filter,
  CommandColumn,
  Resize,
  Sort,
  Freeze,
} from "@syncfusion/ej2-vue-grids";

//import gridview from './treegridview'
//import VueFlatPickr from 'vue-flatpickr-component';
import DSBienDong from "./DSBienDong";
import SearchEProfile from "@/modules/search/subscriber/SearchEProfile";

import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import UploadEProfileAPI from "./UploadEProfileAPI";
//CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
//JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";
//
export default {
  components: { treemenu, gridview, DatePicker, DSBienDong, SearchEProfile },
  name: "UploadEProfile",
  provide: {
    grid: [Filter, Page, CommandColumn, Resize, Sort, Freeze],
  },
  props: {
    Tag: {
      type: String,
      default: "0",
    },
    ma_thue_bao: {
      type: String,
      default: "",
    },
    // quytrinh: {
    //   type: String,
    //   default: "",
    // },
    baoHong: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    // Split boxes
    Split(["#boxLeft", "#boxMid", "#boxRight"], {
      sizes: [20, 40, 40],
    });
    // END Split boxes
    this.tt_nd.nhanvien_id = this.$root.token.getNhanVienID();
    this.tt_nd.ngay_cn = this.$auth.getNgayCapNhat();
    this.tt_nd.donvi_dl_id = this.$root.token.getDonViID();
    this.tt_nd.ma_nv = this.$root.token.getMaNhanVien();
    this.tt_nd.ma_nd = this.$root.token.getUserName();
    this.tt_nd.phanvung_id = this.$root.token.getPhanVungID();
    this.tt_nd.nhom_nd_id = this.$root.context.user.getProperty("nhom_nd_id");
    UploadEProfileAPI.get_danhsach_nhom_file_hoso(this.axios, {}).then(
      (response) => {
        if (
          response.data.error_code &&
          response.data.error_code === this.success_code
        ) {
          this.listbox.danhsach_nhom_file_hoso.list = response.data.data
            .filter((x) => x.ID > 0)
            .map((x) => ({ id: x.ID, text: x.NAME }));
        }
      }
    );
    UploadEProfileAPI.get_danhsach_loai_file_hoso(this.axios, {
      nhomfile_id: 1,
    }).then((response) => {
      if (
        response.data.error_code &&
        response.data.error_code === this.success_code
      ) {
        this.listbox.danhsach_loai_file_hoso.list = response.data.data
          .filter((x) => x.loaifile_id > 0)
          .map((x) => ({ id: x.loaifile_id, text: x.loai_file, kieu: x.kieu }));
      }
    });
    UploadEProfileAPI.get_danhsach_kieu_file_hoso(this.axios, {
      p_nghiepvu: "KIEU_FILE_HS",
      p_ds_para: "",
    }).then((response) => {
      if (
        response.data.error_code &&
        response.data.error_code === this.success_code
      ) {
        this.listbox.danhsach_kieu_file_hoso.list = response.data.data
          .filter((x) => x.kieufile_id > 0)
          .map((x) => ({ id: x.kieufile_id, text: x.kieu_file }));
      }
    });
    this.listbox.ds_file_hs.list = [];
    this.listbox.danhsach_thuebao_theo_makh.list = [];
    this.getPropsData();
    this.refreshTree();
  },
  computed: {
    baohong: {
      get() {
        return this.isBaoHong;
      },
      set(value) {
        this.isBaoHong = value;
        this.lblMa_GD_TB = "Mã thuê bao";
      },
    },
    hopdongmoi: {
      get() {
        return !this.isBaoHong;
      },
      set(value) {
        this.isBaoHong = !value;
        this.lblMa_GD_TB = "Mã giao dịch";
      },
    },
    loaiFileName() {
      let loai = this.listbox.danhsach_loai_file_hoso.list.find(
        (i) => i.id == this.listbox.ds_file_hs.value.loaifile_id
      );
      return loai ? loai.text : "";
    },
    loaiFileKieu() {
      let loai = this.listbox.danhsach_loai_file_hoso.list.find(
        (i) => i.id == this.listbox.ds_file_hs.value.loaifile_id
      );
      return loai ? loai.kieu : "";
    },
    formAllowFileType() {
      if (this.Tag != "2") {
        return ".doc,.docx,.xls,.xlsx,.pdf,.jpg,.jpeg,.png";
      } else {
        return ".jpg,.jpeg,.png";
      }
    },
  },
  data() {
    return {
      param: {},
      groupByKey: {
        keyName: "Nhóm file",
        keyValue: "nhom_file",
      },
      tt_nd: {
        nhanvien_id: "",
        ngay_cn: "",
        donvi_dl_id: "",
        ma_nv: "",
        ma_nd: "",
        may_cn: "",
        phanvung_id: "",
        ip: "",
        nhom_nd_id: "",
      },
      isBaoHong: false,
      isAddNew: false,
      isUpdate: false,
      isHuy: false,
      btnLayTTEnable: false,
      btnNewEnable: false,
      btnUpdateEnable: false,
      btnHuyBoEnable: false,
      btnXoaFileEnable: false,
      btnXoaTBEnable: false,
      bsort: 1, //1: a->z, -1: z->a
      lblMa_GD_TB: "Mã giao dịch",
      txtDoiTuong: "",
      listbox: {
        doituong_hoso: {
          list: [],
          value: "-1",
          isEnabled: false,
          rootClick: false,
        },
        hopdong_theo_matb: {
          list: [],
          value: "",
          isEnabled: false
        },
        danhsach_hoso_icon: {
          list: [],
          value: "",
          isEnabled: false
        },
        lichsu_baohong_theo_matb: {
          list: [],
          value: "",
          isEnabled: false,
        },
        danhsach_loai_file_hoso: {
          list: [],
          value: "",
          isEnabled: false
        },
        danhsach_nhom_file_hoso: {
          list: [],
          value: "",
          isEnabled: false
        },
        danhsach_kieu_file_hoso: {
          list: [],
          value: "",
          isEnabled: false,
        },
        danhsach_thuebao_theo_makh: {
          list: [],
          header: [
            {
              headerText: "",
              width: "70px",
              allowFiltering: false,
              textAlign: "Center",
              headerTemplate: () => {
                return {
                  template: Vue.component("action-check-all", {
                    template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.listbox.danhsach_thuebao_theo_makh.isCheckAll" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
                    data: function () {
                      return { data: {} };
                    },
                    computed: {
                      parent() {
                        return this.$parent.$parent.$parent;
                      },
                    },
                  }),
                };
              },
              template: () => {
                return {
                  template: Vue.component("action-check", {
                    template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.listbox.danhsach_thuebao_theo_makh.checked"
                                    @change="onChangeProcessed(data)"
                                    :value="data.thuebao_id"
                                    />
                                <span class="name"></span>
                            </div>
                        `,
                    data() {
                      return { data: {} };
                    },
                    methods: {
                      onChangeProcessed(value) {
                        var obj =
                          this.parent.listbox.danhsach_thuebao_theo_makh.list.find(
                            (f) => f.thuebao_id == value.thuebao_id
                          );
                        obj.checked = true;
                      },
                    },
                    computed: {
                      parent() {
                        return this.$parent.$parent.$parent;
                      },
                    },
                  }),
                };
              },
            },
            {
              fieldName: "ma_tb",
              headerText: "Số máy/Acc",
              allowFiltering: true,
              width: 100,
            },
            {
              fieldName: "loaihinh_tb",
              headerText: "Loại hình",
              allowFiltering: true,
              width: 100,
            },
            {
              fieldName: "trangthai_tb",
              headerText: "Trạng thái",
              allowFiltering: true,
              allowHtml: true,
              width: 100,
            },
            {
              fieldName: "",
              headerText: "Ngày báo hỏng",
              allowFiltering: true,
              allowHtml: true,
              width: 100,
            },
            {
              fieldName: "",
              headerText: "Người báo hỏng",
              allowFiltering: true,
              allowHtml: true,
              width: 100,
            },
            {
              fieldName: "",
              headerText: "Ngày nghiệm thu",
              allowFiltering: true,
              allowHtml: true,
              width: 100,
            },
            {
              fieldName: "loaihinh_tb",
              headerText: "Nhóm file",
              allowFiltering: true,
              allowHtml: true,
              isGroupedColumn: true,
            },
            {
              // Phần này cần file_clob xml đợi hỏi
              fieldName: "chon",
              headerText: "Chọn",
              template: () => {
                return {
                  template: Vue.component("custom-button", {
                    template: `<button style="background-color: rgb(1, 118, 255);border: 0px;border-radius: 3px;padding: 0px 20px;color: #FFFF;" @click="onChonThueBaoClick"><i class="fa fa-check"></i></button>`,
                    data: function () {
                      return {
                        data: {},
                      };
                    },
                    computed: {},
                    methods: {
                      onChonThueBaoClick(listbox) {
                        var listbox = this.$parent.$parent.$parent.listbox;
                        this.$bvModal
                          .msgBoxConfirm(
                            "Bạn có chắc chắn muốn gán các thuê bao cho danh sách file hồ sơ đã chọn?",
                            {
                              title: "",
                              size: "sm",
                              okTitle: "Đồng ý",
                              cancelTitle: "Hủy",
                            }
                          )
                          .then(async (v) => {
                            if (v) {
                              this.loading(true);
                              var ptocheck = [
                                "FILE_ID",
                                "THUEBAO_ID",
                                "THANHTOAN_ID",
                                "MA_TB",
                                "HDTB_ID",
                                "MA_KH",
                                "BAOHONG_ID",
                                "MA_GD",
                                "HDKH_ID",
                              ];
                              let ds_hs_chon = listbox.ds_file_hs.list.filter(
                                (i) => i.checked == true && i.kieu != 1
                              );
                              if (!ds_hs_chon || ds_hs_chon.length == 0) {
                                this.$toast.warning(
                                  "Bạn chưa chọn file cần gáng thông tin thuê bao."
                                );
                                this.loading(false);
                                return;
                              }

                              var file_clob = [];
                              for (var file of ds_hs_chon) {
                                var vstb = {};
                                for (var p of ptocheck)
                                  if (this.$data.data[p.toLowerCase()])
                                    vstb[p.toUpperCase()] =
                                      this.$data.data[p.toLowerCase()];
                                  else vstb[p.toUpperCase()] = null;
                                vstb["FILE_ID"] = file.file_id;
                                file_clob.push(vstb);
                              }
                              var pdata = {
                                vfile_cn: this.$data.data.file_id,
                                vhstb_clob: JSON.stringify(file_clob),
                                vhoso_id: -1,
                                vkieu: 4,
                                vurl: "",
                              };

                              this.loading(true);
                              UploadEProfileAPI.cap_nhat_file_hoso(
                                this.axios,
                                pdata
                              ).then((response) => {
                                this.loading(false);
                                if (
                                  response.data.error_code == "BSS-00000000"
                                ) {
                                  this.$toast.success(
                                    "Gắn thuê bao với file hồ sơ thành công!"
                                  );

                                  this.$parent.$parent.$parent.isAddNew = false;
                                  this.$parent.$parent.$parent.isUpdate = false;
                                  this.$parent.$parent.$parent.listbox.ds_file_hs.value =
                                    {
                                      url: "",
                                    };
                                  this.$parent.$parent.$parent.listbox.danhsach_thuebao_theo_makh.value =
                                    {};
                                  this.$parent.$parent.$parent.treeClicked(
                                    this.$parent.$parent.$parent.listbox
                                      .doituong_hoso.value
                                  );
                                } else this.$toast.error(response.data.message);
                              });
                              // .catch(function() {
                              //   this.$toast.error(
                              //     "Gắn thuê bao với file hồ sơ không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.",
                              //     2
                              //   );
                              // });
                            }
                            this.loading(false);
                          });
                      },
                    },
                  }),
                };
              },
              allowHtml: true,
              width: 100,
            },
          ],
          value: {},
          khData: {},
          isEnabled: false,
          isCheckAll: false,
          checked: [],
        },
        chitiet_file_hs: {
          list: [],
          value: "",
          isEnabled: false
        },
        ds_file_hs: {
          list: [],
          header: [
            {
              headerText: "",
              width: "70px",
              allowFiltering: false,
              textAlign: "Center",
              headerTemplate: () => {
                return {
                  template: Vue.component("action-check-all", {
                    template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.listbox.ds_file_hs.isCheckAll" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
                    data: function () {
                      return { data: {} };
                    },
                    computed: {
                      parent() {
                        return this.$parent.$parent.$parent;
                      },
                    },
                  }),
                };
              },
              template: () => {
                return {
                  template: Vue.component("action-check", {
                    template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.listbox.ds_file_hs.checked"
                                    @change="onChangeProcessed(data)"
                                    :value="data.file_id"
                                    />
                                <span class="name"></span>
                            </div>
                        `,
                    data() {
                      return { data: {} };
                    },
                    methods: {
                      onChangeProcessed(value) {
                        var obj = this.parent.listbox.ds_file_hs.list.find(
                          (f) => f.file_id == value.file_id
                        );
                        obj.checked = true;
                      },
                    },
                    computed: {
                      parent() {
                        return this.$parent.$parent.$parent;
                      },
                    },
                  }),
                };
              },
            },

            {
              fieldName: "ma_tb",
              headerText: "Số máy/Acc",
              allowFiltering: true,
              allowSorting: true,
              width: 100,
            },
            {
              fieldName: "loai_file",
              headerText: "Loại file",
              allowFiltering: true,
              allowSorting: true,

              width: 120,
            },
            {
              fieldName: "ten_file",
              headerText: "Tên file",
              allowFiltering: true,
              allowSorting: true,
              allowHtml: true,
              width: 120,
            },
            {
              fieldName: "loai_hd",
              headerText: "Loại HĐ/PL",
              allowFiltering: true,
              allowSorting: true,
              allowHtml: true,
              width: 120,
            },
            {
              fieldName: "ma_gd",
              headerText: "Mã GD",
              allowFiltering: true,
              allowSorting: true,
              allowHtml: true,
              width: 120,
            },
            {
              fieldName: "ma_kh",
              headerText: "Mã KH",
              allowFiltering: true,
              allowSorting: true,
              allowHtml: true,
              width: 120,
            },
            {
              fieldName: "ngay_yc",
              headerText: "Ngày YC",
              allowFiltering: true,
              allowSorting: true,
              allowHtml: true,
              width: 120,
            },
            {
              fieldName: "nguoi_cn",
              headerText: "Người CN",
              allowFiltering: true,
              allowSorting: true,
              allowHtml: true,
              width: 120,
            },
            {
              fieldName: "ngay_cn",
              headerText: "Ngày CN",
              allowFiltering: true,
              allowSorting: true,
              allowHtml: true,
              width: 120,
            },
            {
              fieldName: "file_id_hni",
              headerText: "File_ID_HNI",
              allowSorting: true,
              allowFiltering: true,
              allowHtml: true,
              width: 120,
            },
            {
              fieldName: "hni",
              headerText: "Thuê bao HNI",
              allowSorting: true,
              allowFiltering: true,
              allowHtml: true,
              width: 120,
            },
            {
              fieldName: "url",
              headerText: "Xem/Tải về",
              width: 100,
              template: () => {
                return {
                  template: Vue.component("action-button", {
                    template: `<div><a style="background-color: rgb(1, 118, 255);border: 0px;border-radius: 3px;padding: 0px 10px;color: #FFFF;" @click="onPreviewlick"><i class='fa fa-search'></i></a>
                    <a style="background-color: rgb(1, 118, 255);border: 0px;border-radius: 3px;padding: 0px 10px;color: #FFFF;" @click="onDownloadlick"><i class='fa fa-download'></i></a></div>`,
                    data: function () {
                      return {
                        data: {},
                      };
                    },
                    methods: {
                      onPreviewlick() {
                        this.$parent.$parent.$parent.downloadFile(
                          1,
                          this.$data.data.url
                        );
                      },
                      onDownloadlick() {
                        this.$parent.$parent.$parent.downloadFile(
                          0,
                          this.$data.data.url
                        );
                      },
                    },
                  }),
                };
              },
            },
            {
              fieldName: "nhom_file",
              headerText: "Nhóm file",
              allowFiltering: true,
              allowHtml: true,
              isGroupedColumn: true,
              width: 200,
            },
          ],
          //,{name:'loai_hd',text:'Loại HĐ/PL'},{name:'ma_gd',text:'Mã GD'},{name:'ma_kh',text:'Mã KH'},{name:'ngay_yc',text:'Ngày YC'},{name:'nguoi_cn',text:'Người CN'},{name:'ngay_cn',text:'Ngày CN'}
          value: {},
          isEnabled: false,
          isCheckAll: false,
          checked: [],
          tableKey: 0, // re-render table
        },

        ds_hd_tb_theo_hdkh_id: {
          list: [],
          value: "",
          isEnabled: false
        },
        ds_bien_dong: {
          list: [],
          value: {},
        },
      },
      dateConfig: {
        altInput: true,
        //dateFormat: 'DD-MM-YYYY',
        altFormat: "d/m/Y",
      },
      dateFormat: "DD-MM-YYYY",
      from_date: new Date(),
      to_date: new Date(),
      success_code: "BSS-00000000",
      //Popup
      popupComponent: null,
      popupComponentName: "",
      popupTitle: "",
      ma_kh: "",
      txtMaGD_txtMa_TB: "",
      txtMaTBSearchEProfile: "",
    };
  },
  watch: {
    "listbox.ds_file_hs.isCheckAll": {
      handler: function (value) {
        console.log("istbox.ds_file_hs.isCheckAll");
        if (value) {
          this.listbox.ds_file_hs.list.forEach((f) => {
            f.checked = true;
          });
          this.listbox.ds_file_hs.checked = this.listbox.ds_file_hs.list.map(
            (f) => f.file_id
          );
          return;
        } else {
          this.listbox.ds_file_hs.list.forEach((f) => {
            f.checked = false;
          });
          this.listbox.ds_file_hs.checked = [];
          return;
        }
      },
      deep: true,
    },
    "listbox.ds_file_hs.checked": {
      handler: function (value) {
        console.log("istbox.ds_file_hs.checked");
        this.listbox.ds_file_hs.isCheckAll =
          value.length > 0 &&
          value.length === this.listbox.ds_file_hs.list.length;
      },
      deep: true,
    },
    "listbox.danhsach_thuebao_theo_makh.isCheckAll": {
      handler: function (value) {
        console.log("istbox.danhsach_thuebao_theo_makh.isCheckAll");
        if (value) {
          this.listbox.danhsach_thuebao_theo_makh.list.forEach((f) => {
            f.checked = true;
          });
          this.listbox.danhsach_thuebao_theo_makh.checked =
            this.listbox.danhsach_thuebao_theo_makh.list.map(
              (f) => f.thuebao_id
            );
          return;
        } else {
          this.listbox.danhsach_thuebao_theo_makh.list.forEach((f) => {
            f.checked = false;
          });
          this.listbox.danhsach_thuebao_theo_makh.checked = [];
          return;
        }
      },
      deep: true,
    },
    "listbox.danhsach_thuebao_theo_makh.checked": {
      handler: function (value) {
        console.log("istbox.danhsach_thuebao_theo_makh.checked");
        this.listbox.danhsach_thuebao_theo_makh.isCheckAll =
          value.length > 0 &&
          value.length === this.listbox.danhsach_thuebao_theo_makh.list.length;
      },
      deep: true,
    },
  },

  methods: {
    getPropsData() {
      // get propsdata
      if (this.param) {
        if (
          this.param.value &&
          (this.param.type == "MA_TB" || this.param.type == "MA_GD")
        ) {
          this.txtMaGD_txtMa_TB = this.param.value;
          this.txtMaGD_txtMa_TB_Enter();
        }
        if (this.param.value && this.param.type == "MA_KH") {
          this.ma_kh = this.param.value;
          this.onMaKHEnter();
        }
      } else if (this.ma_thue_bao) {
        this.txtMaGD_txtMa_TB = this.ma_thue_bao;
        this.txtMaGD_txtMa_TB_Enter();
      }
    },
    ShowAlert(mesage, val) {
      if (val == 0) {
        this.$toast.success(mesage);
      } else if (val == 1) {
        this.$toast.warning(mesage);
      } else if (val == 2) {
        this.$toast.error(mesage);
      }
    },

    refreshTree() {
      this.loading(true);
      let postData = {
        vtungay: this.from_date.toLocaleString("en-GB").substring(0, 10), //.getDate(),
        vdenngay: this.to_date.toLocaleString("en-GB").substring(0, 10), //.getDate()
      };
      if (this.Tag != "0") {
        postData.vtungay = this.tt_nd.nhom_nd_id;
        postData.vdenngay = "0";
      }
      UploadEProfileAPI.get_danhsach_doituong_hoso(this.axios, postData).then(
        (response) => {
          this.loading(false);
          if (
            response.data.error_code &&
            response.data.error_code === this.success_code
          ) {
            var dsdoituong = response.data.data;

            var tree = dsdoituong.filter((a) => a.hoso_cha_id == null);
            function addTree(item, sort, sfilter) {
              if (item.doituong == null) item.doituong = "Root";
              var dsitems = dsdoituong.filter(
                (a) => a.hoso_cha_id === item.hoso_id
              );
              if (sfilter != "") {
                dsitems = dsdoituong.filter(
                  (a) =>
                    a.hoso_cha_id === item.hoso_id &&
                    a.doituong.indexOf(sfilter) >= 0
                );
              }
              if (dsitems && dsitems.length > 0) {
                for (var i = 0; i < dsitems.length; i++)
                  dsitems[i] = addTree(dsitems[i]);
                item.$nodes = dsitems.sort((a, b) =>
                  a.doituong > b.doituong
                    ? sort
                    : b.doituong > a.doituong
                    ? -sort
                    : 0
                );
              }
              return item;
            }
            for (var j = 0; j < tree.length; j++)
              tree[j] = addTree(tree[j], this.bsort, this.txtDoiTuong);
            this.listbox.doituong_hoso.list = tree[0];
          }
        }
      );
    },
    treeClicked(val) {
      console.log("treeClicked: " + val)
      this.loading(true);
      this.listbox.doituong_hoso.value = val;
      if (val == null) {
        this.listbox.doituong_hoso.rootClick = true;
        val = -1;
      } else if (val == -1) {
        this.listbox.doituong_hoso.rootClick =
          !this.listbox.doituong_hoso.rootClick;
      }
      UploadEProfileAPI.get_ds_file_hs(this.axios, { hoso_id: val }).then(
        (response) => {
          this.loading(false);
          if (
            response.data.error_code &&
            response.data.error_code === this.success_code
          ) {
            this.listbox.ds_file_hs.list = response.data.data;
            this.listbox.ds_file_hs.list.forEach((f) => {
              f.checked = false;
            });
            if (this.listbox.ds_file_hs.list.length > 0) {
              this.fileHSChanged(this.listbox.ds_file_hs.list[0]);
            } else {
              this.listbox.danhsach_thuebao_theo_makh.value = {};
              this.listbox.ds_file_hs.value = {};
            }
          } else {
            this.listbox.ds_file_hs.list = [];
            this.listbox.danhsach_thuebao_theo_makh.value = {};
            this.listbox.ds_file_hs.value = {};
          }
        }
      );
    },

    fileHSChanged(obj) {
      if (!obj) {
        this.KHOAMO_FORM(false);
      } else {
        this.KHOAMO_FORM(true);
        this.btnUpdateEnable = true;
        this.btnHuyBoEnable = true;
        this.btnXoaFileEnable = true;
        this.btnXoaTBEnable = true;
        this.btnNewEnable = true;
      }
      if (!obj) {
        this.isUpdate = false;
        this.isHuy = false;
        return;
      } else {
        this.isUpdate = true;
        this.isHuy = true;
      }
      var loaifile_changed =
        this.listbox.ds_file_hs.value.nhomfile_id != obj.nhomfile_id;
      this.listbox.ds_file_hs.value = { ...obj }; // Clone object to avoid reverse update to grid data.
      if (loaifile_changed) this.loadLoaiFile();
      if (
        !this.listbox.danhsach_thuebao_theo_makh.value ||
        !this.listbox.danhsach_thuebao_theo_makh.value.ma_tb
      ) {
        this.listbox.danhsach_thuebao_theo_makh.value.ma_tb = obj.ma_tb;
        this.listbox.danhsach_thuebao_theo_makh.value.ma_kh = obj.ma_kh;
      }
      this.listbox.ds_file_hs.list.map(i => (i.checked = 0));
      // checkedItems.forEach(checkItem => {
      //   let hs = this.listbox.ds_file_hs.list.find(
      //     i => i.file_id == checkItem.file_id
      //   );
      //   if (!hs) {
      //     return;
      //   }
      //   hs.checked = 1;
      // });
    },

    dsFileHSChanged(checkedItems) {
      if (checkedItems.length == 0) {
        this.listbox.ds_file_hs.value = { url: "" };
      }
      this.listbox.ds_file_hs.list.map((i) => (i.checked = 0));
      checkedItems.forEach((checkItem) => {
        let hs = this.listbox.ds_file_hs.list.find(
          (i) => i.file_id == checkItem.file_id
        );
        if (!hs) {
          return;
        }
        hs.checked = 1;
      });
    },

    loadLoaiFile() {
      UploadEProfileAPI.get_danhsach_loai_file_hoso(this.axios, {
        nhomfile_id: this.listbox.ds_file_hs.value.nhomfile_id,
      }).then((response) => {
        if (
          response.data.error_code &&
          response.data.error_code === this.success_code
        ) {
          let data = [];
          response.data.data.forEach(function (item) {
            data.push({ id: item.loaifile_id, text: item.loai_file });
          });
          this.listbox.danhsach_loai_file_hoso.list = response.data.data
            .filter((x) => x.loaifile_id > 0)
            .map((x) => ({
              id: x.loaifile_id,
              text: x.loai_file,
              kieu: x.kieu,
            }));
        }
      });
    },

    btnSort() {
      this.bsort = this.bsort * -1;
      this.refreshTree();
    },

    onEnter() {
      this.refreshTree();
    },

    btnLayTT() {
      this.treeClicked(null); // focus root node
    },
    btnRefresh() {
      this.listbox.ds_file_hs.value = { url: "" };
      this.treeClicked(this.listbox.doituong_hoso.value);
    },
    btnFilter() {
      this.listbox.ds_file_hs.header.forEach((h) => {
        if (h.fieldName == "nhom_file") {
          h.isGroupedColumn = !h.isGroupedColumn;
        }
      });
      this.listbox.ds_file_hs.tableKey += 1;
    },
    btnNewClicked() {
      this.listbox.ds_file_hs.value = { url: "" };
      this.listbox.danhsach_thuebao_theo_makh.value = {};

      this.isAddNew = true;
      // this.isUpdate = false;
      // this.isHuy = false;
      this.btnHuyBoEnable = true;
      this.btnUpdateEnable = true;
      this.btnNewEnable = false;
    },
    btnHuyBoClicked() {
      this.listbox.ds_file_hs.value = { url: "" };
      this.listbox.danhsach_thuebao_theo_makh.value = {};
      this.isAddNew = false;
      // this.isUpdate = false;
      this.btnUpdateEnable = false;
      this.btnHuyBo = false;
      this.btnXoaFileEnable = false;
      this.btnXoaTBEnable = false;
      this.btnNewEnable = true;
    },

    async btnUpdateClicked() {
      this.loading(true);
      var vhstb_clob = [];
      var ptocheck = [
        "THUEBAO_ID",
        "THANHTOAN_ID",
        "MA_TB",
        "HDTB_ID",
        "MA_KH",
        "BAOHONG_ID",
      ];
      var pdata = {};
      if (
        !this.listbox.ds_file_hs.value.loaifile_id ||
        !this.listbox.ds_file_hs.value.kieu ||
        !this.listbox.ds_file_hs.value.url
      ) {
        this.loading(false);
        return;
      }
      if (this.isAddNew) {
        pdata.vhoso_id = (this.listbox.doituong_hoso.value != undefined && this.listbox.doituong_hoso.value != null) ? this.listbox.doituong_hoso.value : -1;
        pdata.vloaifile_id = this.listbox.ds_file_hs.value.loaifile_id;
        pdata.vurl = this.listbox.ds_file_hs.value.url;
        pdata.vghichu = this.listbox.ds_file_hs.value.ghichu;
        pdata.vfile_cn = 0;
        pdata.vkieu = 1;
        (pdata.vkieufile_id = this.listbox.ds_file_hs.value.kieu),
          (pdata.vfile_clob = 0);
        pdata.thuebao_id = 0;
        pdata.vhstb_clob = [];
        // lấy danh sách thuê bao nếu có
        var dsthuebaochon = this.listbox.danhsach_thuebao_theo_makh.list.filter(
          (a) => a.checked
        );
        for (var tb of dsthuebaochon) {
          var vstb = {};
          for (var p of ptocheck)
            if (tb[p.toLowerCase()])
              vstb[p.toUpperCase()] = tb[p.toLowerCase()];
            else vstb[p.toUpperCase()] = null;
          vhstb_clob.push(vstb);
        }

        if (vhstb_clob && vhstb_clob.length > 0)
          pdata.vhstb_clob = JSON.stringify(vhstb_clob);
        else pdata.vhstb_clob = null;

        // loai file co kieu = 1 bat buoc gan thuê bao
        if (!pdata.vhstb_clob && pdata.vkieufile_id == 1) {
          this.ShowAlert(
            "Bạn bắt buộc phải gán thuê bao với loại file " + this.loaiFileName,
            1
          );
          this.loading(false);
          return;
        }

        UploadEProfileAPI.cap_nhat_file_hoso(this.axios, pdata)
          .then((response) => {
            this.loading(false);
            if (response.data.error_code == this.success_code) {
              this.ShowAlert("Thêm mới thành công!", 0);
              this.isAddNew = false;
              this.isUpdate = false;
              this.listbox.ds_file_hs.value = { url: "" };
              this.listbox.danhsach_thuebao_theo_makh.value = {};
              $("#upload-file").val(null);

              this.treeClicked(this.listbox.doituong_hoso.value);
            } else this.ShowAlert(response.data.request_id, 2);
          })
          .catch(function () {
            this.ShowAlert(
              "Thêm mới hồ sơ không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.",
              2
            );
          });
      } // Update
      else {
        if (
          this.listbox.ds_file_hs.value.nguoi_cn != this.tt_nd.ma_nd &&
          this.tt_nd.ma_nd != "admin"
        ) {
          this.ShowAlert(
            "Bạn không được quyền sửa xóa file hồ sờ của người dùng khác",
            1
          );
          return;
        }

        // kiểm tra nếu không upload lại file => gán url=0
        let file = this.listbox.ds_file_hs.list.find(
          (f) => f.file_id == this.listbox.ds_file_hs.value.file_id
        );
        pdata.vurl =
          this.listbox.ds_file_hs.value.url == file.url
            ? 0
            : this.listbox.ds_file_hs.value.url;

        pdata.vhoso_id = this.listbox.doituong_hoso.value;
        pdata.vloaifile_id = this.listbox.ds_file_hs.value.loaifile_id;
        pdata.vghichu = this.listbox.ds_file_hs.value.ghichu;
        pdata.vfile_cn = this.listbox.ds_file_hs.value.file_id;
        (pdata.vkieufile_id = this.listbox.ds_file_hs.value.kieu),
          (pdata.vkieu = 2);
        pdata.vfile_clob = 0;
        pdata.thuebao_id = 0;
        pdata.vhstb_clob = "";

        UploadEProfileAPI.cap_nhat_file_hoso(this.axios, pdata)
          .then((response) => {
            this.loading(false);
            if (
              response.data.error_code &&
              response.data.error_code === this.success_code
            ) {
              this.ShowAlert("Cập nhật thành công!", 0);
              this.isAddNew = false;
              this.isUpdate = false;
              this.listbox.ds_file_hs.value = { url: "" };
              this.listbox.danhsach_thuebao_theo_makh.value = {};
              this.treeClicked(this.listbox.doituong_hoso.value);
            } else this.ShowAlert(response.data.message, 2);
          })
          .catch(function () {
            this.ShowAlert(
              "Cập nhật hồ sơ không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.",
              2
            );
          });
      }
    },

    btnDeleteClicked() {
      var ds_file_hs = this.listbox.ds_file_hs.list.filter(
        (f) => f.checked == true
      );
      var mess = "Bạn có chắc chắn muốn xoá file hồ sơ này không?";

      if (!ds_file_hs) {
        this.ShowAlert("Bạn phải chọn một file hồ sơ xóa", 1);
        return;
      }
      if (ds_file_hs.length > 1) {
        mess = "Bạn có chắc chắn xóa nhiều file hồ sơ được chọn không?";
      }
      this.$bvModal
        .msgBoxConfirm(mess, {
          title: "",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            var pdata;
            // xoá 1
            if (ds_file_hs.length == 1) {
              if (
                this.tt_nd.ma_nd != this.listbox.ds_file_hs.value.nguoi_cn &&
                this.tt_nd.ma_nd != "admin"
              ) {
                this.ShowAlert(
                  "Bạn không được quyền sửa xóa file hồ sờ của người dùng khác",
                  1
                );
                return;
              }

              if (this.listbox.ds_file_hs.file_id_hni == 1) {
                this.ShowAlert(
                  "File hồ sơ đã Upload lên HNI. Bạn không thể xóa !",
                  1
                );
                return;
              }
              pdata = {
                vkieu: 3,
                vhoso_id: -1,
                url: "",
                vghichu: "",
                vdl_file_id: this.listbox.ds_file_hs.value.file_id,
                vloaifile_id: this.listbox.ds_file_hs.value.loaifile_id,
                vfile_cn: this.listbox.ds_file_hs.value.file_id,
                vfile_clob: "0",
              };
            }
            // xoá nhiều
            else {
              var check_nguoi_dung = ds_file_hs.find(
                (f) => f.nguoi_cn != this.tt_nd.ma_nd
              );
              if (check_nguoi_dung && this.tt_nd.ma_nd != "admin") {
                this.ShowAlert(
                  "Bạn không được quyền sửa xóa file hồ sờ của người dùng khác",
                  1
                );
                return;
              }
              if (ds_file_hs.find((f) => f.file_id_hni == 1)) {
                this.ShowAlert(
                  "Tồn tại file đã được upload lên HNI. Bạn hãy kiểm tra lại",
                  1
                );
                return;
              }
              // json vfile_clob
              var vfile_clob = [];
              ds_file_hs.forEach((file) => {
                vfile_clob.push({
                  FILE_ID: file.file_id,
                });
              });
              pdata = {
                vkieu: 3,
                vhoso_id: 0,
                url: "",
                vghichu: "",
                vfile_cn: this.listbox.ds_file_hs.value.file_id,
                vloaifile_id: this.listbox.ds_file_hs.value.loaifile_id,
                vfile_clob: JSON.stringify(vfile_clob),
              };
            }

            this.loading(true);
            UploadEProfileAPI.cap_nhat_file_hoso(this.axios, pdata)
              .then((response) => {
                if (
                  response.data.error_code &&
                  response.data.error_code === this.success_code
                ) {
                  this.ShowAlert("Xóa file hồ sơ thành công!", 0);
                  this.isAddNew = false;
                  this.isUpdate = false;
                  this.listbox.ds_file_hs.value = { url: "" };
                  this.listbox.danhsach_thuebao_theo_makh.value = {};
                  this.treeClicked(this.listbox.doituong_hoso.value);
                } else this.ShowAlert(response.data.message, 0);
              })
              .catch(function () {
                this.ShowAlert(
                  "Xoá hồ sơ không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.",
                  2
                );
              });
            this.loading(false);
          }
        });
    },
    btnDeleteTBClicked() {
      if (!this.listbox.ds_file_hs.value) {
        this.ShowAlert("Bạn phải chọn một file hồ sơ xóa", 1);
        return;
      }

      var ds_file_hs = this.listbox.ds_file_hs.list.filter(
        (tb) => tb.checked == true
      );
      var check_nguoi_dung = ds_file_hs.find(
        (f) => f.nguoi_cn != this.tt_nd.ma_nd
      );

      if (check_nguoi_dung && this.tt_nd.ma_nd != "admin") {
        this.ShowAlert(
          "Bạn không được quyền sửa xóa file hồ sờ của người dùng khác",
          1
        );
        return;
      }

      var mess = "Bạn có chắc chắn xóa nhiều thuê bao được chọn không?";

      if (ds_file_hs.length > 1) {
        mess =
          "Bạn có chắc chắn xóa thuê bao " +
          this.listbox.danhsach_thuebao_theo_makh.value.ten_tb +
          " khỏi file hồ sơ này?";
      }
      this.$bvModal
        .msgBoxConfirm(
          "Bạn có chắc chắn muốn xoá liên kết thuê bao với hồ sơ này không?",
          {
            title: "",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        )
        .then(async (v) => {
          if (v) {
            var pdata = {};
            // xoa 1
            if (ds_file_hs.length == 1) {
              if (this.listbox.ds_file_hs.value.kieu == 1) {
                this.ShowAlert(
                  "File bạn chọn không cho phép xóa mã thuê bao",
                  1
                );
                return;
              }
              pdata.vkieu = 3;
              pdata.vhoso_id = !this.listbox.ds_file_hs.value.hdtb_id
                ? this.listbox.ds_file_hs.value.thuebao_id
                : this.listbox.ds_file_hs.value.hdtb_id;
              pdata.url = "";
              pdata.vfile_clob = "0";
              pdata.vghichu = "";
              pdata.vloaifile_id = !this.listbox.ds_file_hs.value.hdtb_id
                ? 1
                : 0;
              pdata.vfile_cn = this.listbox.ds_file_hs.value.file_id;
            }
            // xoa nhieu
            else {
              if (ds_file_hs.find((f) => f.kieu == 1)) {
                this.ShowAlert(
                  "File bạn chọn không cho phép xóa mã thuê bao",
                  1
                );
                return;
              }
              var vfile_clob = [];
              ds_file_hs.forEach((f) => {
                vfile_clob.push({
                  FILE_ID: f.file_id,
                  HDTB_ID: f.hdtb_id,
                  THUEBAO_ID: f.thuebao_id,
                  TRANGTHAI: 2,
                });
              });

              pdata.vkieu = 3;
              pdata.vhoso_id = -1;
              pdata.url = "";
              pdata.vghichu = "";
              pdata.vloaifile_id = this.listbox.ds_file_hs.value.loaifile_id;
              pdata.vfile_cn = this.listbox.ds_file_hs.value.file_id;
              pdata.vfile_clob = JSON.stringify(vfile_clob);
            }
            this.loading(true);
            UploadEProfileAPI.cap_nhat_file_hoso(this.axios, pdata)
              .then((response) => {
                this.loading(false);
                if (
                  response.data.error_code &&
                  response.data.error_code === this.success_code
                ) {
                  this.ShowAlert(
                    "Xóa liên kết thuê bao với hồ sơ thành công!",
                    0
                  );
                  this.isAddNew = false;
                  this.isUpdate = false;
                  this.treeClicked(this.listbox.doituong_hoso.value);
                } else this.ShowAlert(response.data.message, 0);
              })
              .catch(function () {
                this.ShowAlert(
                  "Xoá hồ sơ không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.",
                  2
                );
              });
          }
        });
    },
    filterDocFiles() {
      this.listbox.ds_file_hs.list = this.listbox.ds_file_hs.list.filter(
        (a) =>
          a.ma_tb == null ||
          a.ma_tb
            .toLowerCase()
            .indexOf(this.listbox.ds_file_hs.value.ma_tb.toLowerCase()) >= 0
      );
    },
    UploadFileHoSo() {
      let formData = new FormData();
      for (var file of this.$refs.files.files) {
        formData.append("files", file);
      }
      this.loading(true);
      UploadEProfileAPI.upload_file_hoso(this.axios, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      })
        .then((response) => {
          this.loading(false);
          if (
            response.data.error_code &&
            response.data.error_code === this.success_code
          ) {
            this.listbox.ds_file_hs.value.url = response.data.data.join("|");
          }
        })
        .catch(function () {
          console.log("File upload failed!");
        });
    },
    onMaKHEnter() {
      if (!this.ma_kh) {
        return;
      }
      this.TRACUU_MAKH();
    },
    onMaTBEnter() {
      this.txtMaTBSearchEProfile = this.listbox.ds_file_hs.value.ma_tb;
      this.$bvModal.show("searchEProfile-modal");
    },
    txtMaGD_txtMa_TB_Enter() {
      console.log("txtMaGD_txtMa_TB_Enter")
      this.TIM_GD();
    },
    btnMaKH_Click() {
      this.popupComponent = () =>
        import("@/modules/search/subscriber/SearchAccount");
      this.popupComponentName = "SearchAccount";
      try {
        this.popupComponent.data.isPopup = true;
      } catch (ex) {}
      this.$bvModal.show("popupComponents");
    },
    onTimNhanhHoSoClick() {
      if (
        !this.listbox.danhsach_thuebao_theo_makh.list.find(
          (t) => t.checked == 1 || t.checked == true
        )
      ) {
        this.ShowAlert("Bạn chưa chọn thuê bao", 1);
        return;
      }
      var tb = this.listbox.danhsach_thuebao_theo_makh.list.find(
        (t) => t.checked == 1 || t.checked == true
      );
      this.txtMaTBSearchEProfile = tb.ma_tb;
      this.$bvModal.show("searchEProfile-modal");
    },
    chapNhanFormSearchAcount(val) {
      this.ma_kh = val.ma_kh;
      this.$bvModal.hide("popupComponents");
      this.TRACUU_MAKH(val.ma_tb);
    },
    chapNhanFormDSBienDong(val) {
      this.listbox.danhsach_thuebao_theo_makh.value = val;
      this.txtMaGD_txtMa_TB = val.ma_gd;
      this.NAP_DS_HOPDONGTB(val.hdkh_id, 0, val.tenloai_hd);
      this.$bvModal.hide("DSBienDong-modal");
    },
    thoatFormDSBienDong() {
      this.listbox.danhsach_thuebao_theo_makh.value = {};
      this.$bvModal.hide("DSBienDong-modal");
    },
    async downloadFile(isPreview, url) {
      const res = await UploadEProfileAPI.link_view_file(this.axios, {
        list_file: url,
      }).catch((err) => {
        this.loading(false);
        this.ShowAlert("Lỗi", 2);
      });
      if (res.data.data.length == 0) {
        this.ShowAlert("Lỗi", 2);
        return;
      }
      try {
        var old_url = new URL(res.data.data[0].file_name);
        console.log("old_url", old_url);
      } catch (_) {
        old_url = null;
      }
      if (isPreview) {
        window.open(old_url ? old_url : res.data.data[0].file_url, "_blank");
      } else {
        var file_name = url.split("/").pop();
        var instance = this.axios.create();
        instance.baseURL = "";
        instance
          .get(old_url ? old_url : res.data.data[0].file_url, {
            responseType: "blob",
          })
          .then((response) => {
            console.log(response);
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement("a");
            link.href = url;
            link.setAttribute("download", file_name);
            document.body.appendChild(link);
            link.click();
          })
          .catch((err) => {
            this.ShowAlert("Lỗi tải file : " + url, 2);
          });
      }
    },
    CLEAR() {
      this.listbox.danhsach_thuebao_theo_makh.value = {};
      this.listbox.danhsach_thuebao_theo_makh.list = [];
    },
    async NAP_DS_HOPDONGTB(hdkh_id, loaihd_id, tenloai_hd) {
      this.loading(true);
      const res = await UploadEProfileAPI.get_ds_hd_tb_theo_hdkh_id(
        this.axios,
        { vhdkh_id: hdkh_id }
      );
      if (res.data.error_code == this.success_code) {
        this.listbox.danhsach_thuebao_theo_makh.list = res.data.data;
        this.listbox.danhsach_thuebao_theo_makh.value.ten_kh =
          res.data.data[0].ten_tb;
      }
      this.loading(false);
    },
    async TRACUU_MAGD() {
      this.loading(true);
      this.txtMaGD_txtMa_TB = this.txtMaGD_txtMa_TB.trim();
      const resHDTB = await UploadEProfileAPI.get_danhsach_hopdong_theo_matb(
        this.axios,
        { ma_tb: this.txtMaGD_txtMa_TB }
      ).catch((err) => {
        this.loading(false);
        this.ShowAlert("Lỗi", 2);
      });
      this.loading(false);
      if (
        (resHDTB.data.data && resHDTB.data.data.length == 0) ||
        !resHDTB.data.data
      ) {
        this.ShowAlert(
          "Không tìm thấy thông tin của mã " + this.txtMaGD_txtMa_TB,
          1
        );
        this.listbox.danhsach_thuebao_theo_makh.list = [];
        this.listbox.danhsach_thuebao_theo_makh.value = {};
        return;
      } else if (resHDTB.data.data.length == 1) {
        this.listbox.danhsach_thuebao_theo_makh.value = resHDTB.data.data[0];
        this.NAP_DS_HOPDONGTB(
          resHDTB.data.data[0].hdkh_id,
          0,
          resHDTB.data.data[0].tenloai_hd
        );
      } else {
        // show popup danh sach bien dong
        this.listbox.ds_bien_dong.list = resHDTB.data.data;
        this.$bvModal.show("DSBienDong-modal");
      }
    },
    async TRACUU_BAOHONG() {
      this.loading(true);
      const resDanhBa = await UploadEProfileAPI.get_db_theo_matb(this.axios, {
        ma_tb: this.txtMaGD_txtMa_TB,
        donvi_dl_id: 0,
      });
      if (resDanhBa.data.error_code != this.success_code) {
        this.ShowAlert(
          "Không tìm thấy thông tin báo hỏng của thuê bao " +
            this.txtMaGD_txtMa_TB,
          1
        );
        this.listbox.danhsach_thuebao_theo_makh.list = [];
        this.listbox.danhsach_thuebao_theo_mak.value = {};
        this.loading(false);
        return;
      }
      const resHDTB =
        await UploadEProfileAPI.get_danhsach_lichsu_baohong_theo_matb(
          this.axios,
          { thuebao_id: resDanhBa.data.data[0].thuebao_id }
        ).catch((err) => {
          this.loading(false);
          this.ShowAlert("Lỗi", 2);
          return;
        });
      if (
        resHDTB.data.error_code &&
        resHDTB.data.error_code === this.success_code
      ) {
        this.listbox.danhsach_thuebao_theo_makh.list = resHDTB.data.data;
      } else {
        this.listbox.danhsach_thuebao_theo_makh.list = [];
        this.ShowAlert(
          "Không tìm thấy thông tin báo hỏng của thuê bao " +
            this.txtMaGD_txtMa_TB,
          1
        );
      }
      this.loading(false);
    },
    async TRACUU_MAKH(ma_tb) {
      this.CLEAR();
      // this.txtMaGD_txtMa_TB = "";
      this.loading(true);
      var ma_kh = this.ma_kh.trim();
      let _this = this;
      // tra cứu mã kh
      await UploadEProfileAPI.tracuu_makh(this.axios, { ma_kh: this.ma_kh })
        .then((res) => {
          if (res.data.error_code == "BSS-00000204") {
            this.ShowAlert("Không tìm thấy thông tin của mã " + this.ma_kh, 1);
          } else {
            _this.ma_kh = res.data.data[0].ma_kh;
          }
        })
        .catch((err) => {
          this.ShowAlert("Lỗi", 2);
        });
      // root node
      if (this.listbox.doituong_hoso.value == -1) {
        const res = await UploadEProfileAPI.get_danhsach_thuebao_theo_makh(
          this.axios,
          { ma_kh: ma_kh }
        ).catch((err) => {
          this.loading(false);
          this.ShowAlert("Lỗi", 2);
        });
        if (res.data.error_code && res.data.error_code != this.success_code) {
          this.ShowAlert("Không tìm thấy thông tin của mã " + this.ma_kh, 1);
          this.loading(false);
          return;
        }
        this.listbox.danhsach_thuebao_theo_makh.list = res.data.data;
        this.listbox.danhsach_thuebao_theo_makh.value = res.data.data[0];
      }
      // child
      else {
        // tìm theo mã tb
        if (ma_tb) {
          const res = await UploadEProfileAPI.get_danhsach_hopdong_theo_matb(
            this.axios,
            { ma_tb: ma_tb ? ma_tb : ma_kh }
          ).catch((err) => {
            this.loading(false);
            this.ShowAlert("Lỗi", 2);
          });
          if (res.data.error_code && res.data.error_code != this.success_code) {
            this.ShowAlert("Không tìm thấy thông tin của mã " + this.ma_kh, 1);
            this.loading(false);
            return;
          }
          this.listbox.ds_bien_dong.list = res.data.data;
        }
        // tìm theo mã kh
        else {
          const res = await UploadEProfileAPI.get_danhsach_hopdong_theo_makh(
            this.axios,
            { vma_kh: _this.ma_kh }
          ).catch((err) => {
            this.loading(false);
            this.ShowAlert("Lỗi", 2);
          });
          if (res.data.error_code && res.data.error_code != this.success_code) {
            this.ShowAlert("Không tìm thấy thông tin của mã " + this.ma_kh, 1);
            this.loading(false);
            return;
          }
          this.listbox.ds_bien_dong.list = res.data.data;
        }

        this.$bvModal.show("DSBienDong-modal");
      }
      this.loading(false);
    },
    TIM_GD() {
      if (!this.isBaoHong) {
        this.TRACUU_MAGD();
      } else {
        this.TRACUU_BAOHONG();
      }
    },
    KHOAMO_FORM(value) {
      this.listbox.danhsach_nhom_file_hoso.isEnabled = value;
      this.listbox.danhsach_kieu_file_hoso.isEnabled = value;
      this.listbox.danhsach_loai_file_hoso.isEnabled = value;
      this.listbox.ds_file_hs.isEnabled = value;
    },
    init(param) {
        this.param = param
        // Split boxes
        Split(["#boxLeft", "#boxMid", "#boxRight"], {
          sizes: [20, 40, 40],
        });
        // END Split boxes
        this.tt_nd.nhanvien_id = this.$root.token.getNhanVienID();
        this.tt_nd.ngay_cn = this.$auth.getNgayCapNhat();
        this.tt_nd.donvi_dl_id = this.$root.token.getDonViID();
        this.tt_nd.ma_nv = this.$root.token.getMaNhanVien();
        this.tt_nd.ma_nd = this.$root.token.getUserName();
        this.tt_nd.phanvung_id = this.$root.token.getPhanVungID();
        this.tt_nd.nhom_nd_id = this.$root.context.user.getProperty("nhom_nd_id");
        UploadEProfileAPI.get_danhsach_nhom_file_hoso(this.axios, {}).then(
          (response) => {
            if (
              response.data.error_code &&
              response.data.error_code === this.success_code
            ) {
              this.listbox.danhsach_nhom_file_hoso.list = response.data.data
                .filter((x) => x.ID > 0)
                .map((x) => ({ id: x.ID, text: x.NAME }));
            }
          }
        );
        UploadEProfileAPI.get_danhsach_loai_file_hoso(this.axios, {
          nhomfile_id: 1,
        }).then((response) => {
          if (
            response.data.error_code &&
            response.data.error_code === this.success_code
          ) {
            this.listbox.danhsach_loai_file_hoso.list = response.data.data
              .filter((x) => x.loaifile_id > 0)
              .map((x) => ({ id: x.loaifile_id, text: x.loai_file, kieu: x.kieu }));
          }
        });
        UploadEProfileAPI.get_danhsach_kieu_file_hoso(this.axios, {
          p_nghiepvu: "KIEU_FILE_HS",
          p_ds_para: "",
        }).then((response) => {
          if (
            response.data.error_code &&
            response.data.error_code === this.success_code
          ) {
            this.listbox.danhsach_kieu_file_hoso.list = response.data.data
              .filter((x) => x.kieufile_id > 0)
              .map((x) => ({ id: x.kieufile_id, text: x.kieu_file }));
          }
        });
        this.listbox.ds_file_hs.list = [];
        this.listbox.danhsach_thuebao_theo_makh.list = [];
        this.getPropsData();
        this.refreshTree();
    }
  },
};
</script>
<style scoped></style>
