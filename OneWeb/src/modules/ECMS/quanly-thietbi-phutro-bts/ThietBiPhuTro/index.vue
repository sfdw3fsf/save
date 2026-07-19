<template src="./index.template.html"></template>
<script>
import FormAnhChup from "../AnhChup";
import FormBaoDuong from "../BaoDuongDinhKyTBPhuTro";
import VanHanhThuongXuyen from "./VanHanhThuongXuyen/VanHanhThuongXuyen";
import TiepNhanLuuKho from "../TiepNhanLuuKho";
import FormDiaChi from "../../../CABMAN/nhap-cot-doancong-becap-theofile/DiaChi/FormDiaChi.vue";
import FormToaDo from "../../../CABMAN/nhap-cot-doancong-becap-theofile/ToaDo/FormToaDo.vue";
import TiepNhanDieuChuyen from "../TiepNhanDieuChuyen";
import FrmThemThietBi from "../frmThemThietBi/index.vue";
import FrmCapNhatGiaTriTT from "../frmThemThietBi/frmCapNhatGiaTriTT";
import FormSuaChuaThietBiPhuTro from "../TiepNhanSuaChuaThietBiPhuTro";
export default {
  components: {
    FormAnhChup,
    FormBaoDuong,
    VanHanhThuongXuyen,
    TiepNhanLuuKho,
    FormDiaChi,
    TiepNhanDieuChuyen,
    FrmThemThietBi,
    FormToaDo,
    FrmCapNhatGiaTriTT,
    FormSuaChuaThietBiPhuTro,
  },
  props: {
    Tag: {
      type: String,
      default: "3+1+1",
    },
  },
  data: function () {
    return {
      currentItem: {},
      currentThietBi: {},
      thamSo: {
        CAPNHAT_KT_GIATRI_TT: false,
        ECMS_UPDATE_MATS: false,
      },
      quyenSua: false,
      dsThuocTinh: [],
      dsLichSuVanHanhTBI: [],
      dsThietBi: [],
      dsThietBiCon: [],
      gridDsThietBiCon: {
        queryString: "THIETBI_CHA_ID",
        locale: "vi-VN",
        columns: [
          { field: "MA_VT", headerText: "Mã vật tư" },
          { field: "TEN_VT", headerText: "Đối tượng" },
          { field: "THUTU", headerText: "Thứ tự", textAlign: "right" },
          { field: "THIETBI_ID", headerText: "Ký hiệu", textAlign: "right" },
          { field: "MOTA", headerText: "Mô tả" },
          { field: "GHICHU", headerText: "Vị trí điểm đặt" },
        ],
        load: function () {
          this.parentDetails.parentKeyFieldValue =
            this.parentDetails.parentRowData["THIETBI_ID"];
        },
        rowSelected: this.gridDsThietBi_rowSelected.bind(this),
        contextMenuClick: this.gridDsThietBi_ContextMenuClick.bind(this),
      },
      duLieuDsThuocTinh: [],
      currentThuocTinhHinhAnh: null,
      currentThuocTinhDiaChi: null,
      currentThuocTinhGiaTri: null,
      contextMenuThietBi: [
        {
          text: "Vận hành thường xuyên",
          target: ".e-row",
          id: "menuItemVHTX",
          iconCss: "fa fa-file-archive-o",
        },
        {
          text: "Bảo dưỡng định kỳ",
          target: ".e-row",
          id: "menuItemBDDK",
          iconCss: "fa fa-check",
        },
        {
          text: "Sửa chữa",
          target: ".e-row",
          id: "menuItemSuaChua",
          iconCss: "fa fa-exclamation-triangle",
        },
        {
          text: "Điều chuyển",
          target: ".e-row",
          id: "menuItemDieuChuyen",
          iconCss: "fa fa-arrow-right",
        },
        {
          text: "Lưu kho",
          target: ".e-row",
          id: "menuItemLuuKho",
          iconCss: "fa fa-archive",
        },
      ],
    };
  },
  computed: {
    kieu: function () {
      let rs = 0;
      if (this.Tag != null) {
        let r = this.Tag.split("+");
        if (r.length >= 3) {
          rs = parseInt(r[2]);
        }
      }
      return rs;
    },
  },
  methods: {
    getColumnGiaTriTemplate: function () {
      return function () {
        return {
          template: {
            template: `
            <ejs-textbox v-model="data.GIATRI" @change="updateData" v-if="kieuDuLieu == 0"></ejs-textbox>
            <ejs-datepicker format="dd/MM/yyyy" v-model="data.GIATRI"" @change="updateData" v-else-if="kieuDuLieu == 1"></ejs-datepicker>
            <ejs-dropdownlist :allowFiltering="true" locale="vi-VN" :dataSource="list" :fields="{ text: 'giatri', value: 'gttt_id' }" @change="updateData" v-model="data.GTTT_ID" v-else-if="kieuDuLieu == 2"></ejs-dropdownlist>
            <b-input-group v-else-if="kieuDuLieu == 4">
              <b-form-input readonly v-model="data.GIATRI" style="background: transparent;"></b-form-input>
              <b-input-group-append>
                <b-button variant="link" @click="buttonGiaTriTT_Click"><span class="fa fa-ellipsis-h"></span></b-button>
              </b-input-group-append>
            </b-input-group>
            <div class="input-group" v-else-if="isTTHinhAnh">
              <span class="form-control" style="padding-top: 4px; padding-left: 8px;">{{soHinhAnhText}}</span>
              <div class="input-group-append">
                <button class="btn btn-link" type="button" @click="showModalAnhChup"><span class="fa fa-ellipsis-h"></span></button>
              </div>
            </div>
            <b-input-group v-else-if="isTTDiaChi">
              <b-form-input readonly v-model="data.GIATRI" style="background: transparent;"></b-form-input>
              <b-input-group-append>
                <b-button variant="link" @click="buttonDiaChi_Click"><span class="fa fa-ellipsis-h"></span></b-button>
              </b-input-group-append>
            </b-input-group>
            <b-input-group v-else-if="isTTToaDo">
              <b-form-input readonly v-model="giaTriToaDo" style="background: transparent;"></b-form-input>
              <b-input-group-append>
                <b-button variant="link" @click="buttonToaDo_Click"><span class="fa fa-ellipsis-h"></span></b-button>
              </b-input-group-append>
            </b-input-group>
            `,
            created: function () {
              if (this.kieuDuLieu == 2)
                this.list = this.parent.getDuLieuThuocTinh(this.data.THUOCTINH);
              //console.log(this);
            },
            data: function () {
              return {
                list: [],
              };
            },
            computed: {
              parent: function () {
                return this.$parent.$parent.$parent;
              },
              kieuDuLieu: function () {
                let rs = 0; //textbox
                if (
                  this.data.TEN_LOAI == "Thang" ||
                  this.data.TEN_LOAI == "Ngay"
                ) {
                  //DateTime
                  rs = 1;
                } else if (
                  this.data.TEN_LOAI == "Combobox" ||
                  this.data.TEN_LOAI == "ComboBox Seach"
                ) {
                  //List
                  //CauLenh
                  rs = 2;
                } else if (this.data.TEN_LOAI == "Button") {
                  //text
                  //ThuocTinh
                  if (this.data.THUOCTINH == "Địa chỉ") {
                    rs = 3;
                  } else if (this.parent.thamSo.CAPNHAT_KT_GIATRI_TT) {
                    if (
                      this.data.THUOCTINH == "Lực căng của dây co" ||
                      this.data.THUOCTINH ==
                      "Khoảng cách móng néo tới tâm cột (m)" ||
                      this.data.THUOCTINH ==
                      "Vị trí móng néo (lệch so với chính Bắc)"
                    ) {
                      rs = 4;
                    }
                  }
                } else if (this.data.TEN_LOAI == "Anh") {
                  //text
                  //ThuocTinh
                  rs = 5;
                } else if (this.data.TEN_LOAI == "Textbox") {
                  //text
                  //ThuocTinh
                  if (this.parent.thamSo.CAPNHAT_KT_GIATRI_TT) {
                    if (this.data.THUOCTINH == "Tọa độ Longitude") {
                      rs = 6; //Kinh do
                    } else if (this.data.THUOCTINH == "Tọa độ Latitude") {
                      rs = 7;
                    }
                  }
                }
                return rs;
              },
              isTTHinhAnh: function () {
                return this.kieuDuLieu == 5;
              },
              soHinhAnh: function () {
                let rs = 0;
                if (
                  !(this.data.GIATRI == undefined || this.data.GIATRI == null)
                ) {
                  if (this.isTTHinhAnh) {
                    rs = this.data.GIATRI.toString().split("|").length;
                  }
                }
                return rs;
              },
              soHinhAnhText: function () {
                let rs = "Không có hình ảnh";
                if (this.soHinhAnh > 0) {
                  rs = this.soHinhAnh + " hình";
                }
                return rs;
              },
              isTTDiaChi: function () {
                return this.kieuDuLieu == 3;
              },
              isTTToaDo: function () {
                return this.kieuDuLieu == 6 || this.kieuDuLieu == 7;
              },
              ttKinhDo: function () {
                return this.parent.dsThuocTinh.find(
                  (x) => x.THUOCTINH == "Tọa độ Longitude"
                );
              },
              ttViDo: function () {
                return this.parent.dsThuocTinh.find(
                  (x) => x.THUOCTINH == "Tọa độ Latitude"
                );
              },
              giaTriToaDo: function () {
                let rs = 0;
                if (this.kieuDuLieu == 6) {
                  let v = this.ttKinhDo;
                  if (v != null) rs = v.GIATRI;
                } else if (this.kieuDuLieu == 7) {
                  let v = this.ttViDo;
                  if (v != null) rs = v.GIATRI;
                }
                return rs;
              },
            },
            methods: {
              updateData: function (args) {
                if (this.kieuDuLieu == 2) {
                  let item = this.list.find(
                    (x) => x.gttt_id == this.data.GTTT_ID
                  );
                  if (item != null) {
                    this.data.GIATRI = item.giatri;
                  }
                }
                this.parent.updateThuocTinh(this.data);
              },
              showModalAnhChup: function () {
                this.parent.showModalAnhChup(this.data);
              },
              buttonDiaChi_Click: function () {
                this.parent.showModalDiaChi(this.data);
              },
              buttonToaDo_Click: function () {
                this.parent.showModalToaDo(this.data);
              },
              buttonGiaTriTT_Click: async function () {
                await this.parent.showModalGiaTriThuocTinh(this.data);
              },
            },
          },
        };
      };
    },
    getColumnMaTBTemplate: function () {
      return function () {
        return {
          template: {
            template: `<ejs-textbox v-model="data.MA_TBI" @change="updateData"></ejs-textbox>`,
            computed: {
              parent: function () {
                return this.$parent.$parent.$parent;
              },
            },
            methods: {
              updateData: async function (args) {
                await this.parent.updateThietBi(this.data, "MA_TBI");
              },
            },
          },
        };
      };
    },
    getColumnGhiChuTemplate: function () {
      return function () {
        return {
          template: {
            template: `<ejs-textbox v-model="data.GHICHU" @change="updateData"></ejs-textbox>`,
            computed: {
              parent: function () {
                return this.$parent.$parent.$parent;
              },
            },
            methods: {
              updateData: async function (args) {
                await this.parent.updateThietBi(this.data, "GHICHU");
              },
            },
          },
        };
      };
    },
    getDuLieuThuocTinh: function (name) {
      let rs = [];
      let item = this.duLieuDsThuocTinh.find((x) => x.name == name);
      if (item != null) rs = item.list;
      return rs;
    },
    getDsThamSo: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/getDsThamSo"
      );
      return rs.data;
    },
    loadDsThamSo: async function () {
      let rs = await this.getDsThamSo();
      this.thamSo.ECMS_UPDATE_MATS =
        rs.filter((x) => x.MA_TS == "ECMS_UPDATE_MATS" && x.TEN_TS == "1")
          .length > 0;
      this.thamSo.CAPNHAT_KT_GIATRI_TT =
        rs.filter((x) => x.MA_TS == "CAPNHAT_KT_GIATRI_TT" && x.TEN_TS == "1")
          .length > 0;
    },
    getDsThietBi: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/getDsThietBi",
        {
          donViID: this.currentItem.itemID,
          kieu: this.kieu,
        }
      );
      return rs.data;
    },
    loadDsThietBi: async function () {
      this.duLieuDsThuocTinh = [];
      this.dsThuocTinh = [];
      await this.loadDsThietBiCon();
      this.dsThietBi = await this.getDsThietBi();
    },
    getDsThietBiCon: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/getDsThietBiCon",
        {
          id: this.currentItem.itemID,
        }
      );
      return rs.data;
    },
    loadDsThietBiCon: async function () {
      this.dsThietBiCon = await this.getDsThietBiCon();
    },
    getDsQuyenNhanVien: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/getDsQuyenNhanVien",
        {
          nhanVienID: this.$root.token.getNhanVienID(),
          donViID: this.$root.token.getDonViID(),
        }
      );
      return rs.data;
    },
    loadDsQuyenNhanVien: async function () {
      if (this.kieu == 1) {
        let rs = await this.getDsQuyenNhanVien();
        if (!(rs == null || rs == undefined)) this.quyenSua = rs.length > 0;
      }
    },
    getDsThuocTinh: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/getDsThuocTinh",
        {
          vatTuID: this.currentThietBi.VATTU_ID,
          thietBiID: this.currentThietBi.THIETBI_ID,
        }
      );
      return rs.data;
    },
    loadDsThuocTinh: async function () {
      this.duLieuDsThuocTinh = [];
      this.dsThuocTinh = [];
      let rs = await this.getDsThuocTinh();
      let r = [];
      if (!(rs == null || rs == undefined)) {
        if (!(rs.DS_THUOCTINH == null || rs.DS_THUOCTINH == undefined)) {
          if (rs.DS_THUOCTINH.length > 0) {
            rs.DS_THUOCTINH.forEach((item) => {
              let t = {
                VATTU_ID: item.VATTU_ID,
                THUOCTINH: item.THUOCTINH,
                TEN_LOAI:
                  item.THUOCTINH == "Địa chỉ" ? "Button" : item.TEN_LOAI,
                NGUOI_CN: item.NGUOI_CN,
                CAULENH: item.CAULENH,
                KIEUTT_ID: item.KIEUTT_ID,
                NHOM_TT: item.NHOM_TT,
                GIATRI: item.GIATRI,
                GIATRI_CU: item.GIATRI,
                TTVT_ID: item.TTVT_ID,
                GTTT_ID: item.GTTT_ID,
              };
              if (
                (this.thamSo.CAPNHAT_KT_GIATRI_TT &&
                  t.THUOCTINH == "Lực căng của dây co") ||
                t.THUOCTINH == "Khoảng cách móng néo tới tâm cột (m)" ||
                t.THUOCTINH == "Vị trí móng néo (lệch so với chính Bắc)"
              ) {
                t.TEN_LOAI = "Button";
              }
              r.push(t);
            });
          }
        }
        let keys = Object.keys(rs);
        keys.forEach((key) => {
          if (key != "DS_THUOCTINH") {
            this.duLieuDsThuocTinh.push({
              name: key,
              list: rs[key],
            });
          }
        });
      }
      this.dsThuocTinh = r;
      await this.loadDsLichSuVanHanh();
    },
    getDsLichSuVanHanh: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/getDsLichSuVanHanh",
        {
          id: this.currentThietBi.THIETBI_ID,
        }
      );
      return rs.data;
    },
    loadDsLichSuVanHanh: async function () {
      this.dsLichSuVanHanhTBI = await this.getDsLichSuVanHanh();
    },
    kiemTraQuyenTBPT: async function () {
      let r = false;
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/kiemTraQuyenTBPT",
        {
          nguoiDungID: this.$root.token.getNguoiDungID(),
          quyenID: 2252,
        }
      );
      if (!(rs.data == null || rs.data == undefined)) {
        r = rs.data.result > 0;
      }
      return r;
    },
    xoaThietBiPhuTro: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/xoaThietBiPhuTro",
        {
          id: this.currentThietBi.THIETBI_ID,
        }
      );
      return rs.data.result;
    },
    capNhatVatTuThuocTinh: async function (thietBiID, ttvtID, gtttID, giaTri) {
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/capNhatVatTuThuocTinh",
        {
          thietBiID: thietBiID,
          ttvtID: ttvtID,
          gtttID: gtttID,
          giaTri: giaTri,
        }
      );
      return rs.data.result;
    },
    capNhatDiaChiThietBi: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/capNhatDiaChiThietBi",
        {
          thietBiID: this.currentThietBi.THIETBI_ID,
          diaChiID: this.currentThietBi.DIACHI_ID,
          tinhID: this.currentThietBi.TINH_ID,
          quanID: this.currentThietBi.QUAN_ID,
          phuongID: this.currentThietBi.PHUONG_ID,
          phoID: this.currentThietBi.PHO_ID,
          khuID: this.currentThietBi.KHU_ID,
          apID: this.currentThietBi.AP_ID,
          dacDiemID: this.currentThietBi.DACDIEM_ID,
          soNha: this.currentThietBi.SONHA,
          diaChi: this.currentThietBi.DIACHI,
          kinhDo: this.currentThietBi.KINHDO,
          viDo: this.currentThietBi.VIDO,
        }
      );
      return rs.data.result;
    },
    capNhatThietBiMaTBI: async function (thietBiID, maTBI) {
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/capNhatThietBiMaTBI",
        {
          thietBiID: thietBiID,
          noiDung: maTBI,
        }
      );
      return rs.data.result;
    },
    capNhatThietBiGhiChu: async function (thietBiID, ghiChu) {
      let rs = await this.$root.context.post(
        "/web-ecms/quanlythietbiphutro/capNhatThietBiGhiChu",
        {
          thietBiID: thietBiID,
          noiDung: ghiChu,
        }
      );
      return rs.data.result;
    },
    bindData: async function (args) {
      this.$root.showLoading(true);
      try {
        this.currentItem = args;
        await this.loadDsThamSo();
        await this.loadDsQuyenNhanVien();
        await this.loadDsThietBi();
        this.$emit("bindingComplete", this.getBindingCompleteArgs());
      } finally {
        this.$root.showLoading(false);
      }
    },
    getBindingCompleteArgs: function () {
      let rs = {
        nhapMoi: true,
        ghiLai: true,
        huyBo: true,
        xoa: true,
      };
      if (this.kieu == 1) {
        rs = {
          nhapMoi: this.quyenSua,
          ghiLai: this.quyenSua,
          huyBo: this.quyenSua,
          xoa: this.quyenSua
            ? this.$root.token.getUserName() == "admin"
            : false,
        };
      }
      return rs;
    },
    createData: async function (args) {
      let TTVTvsTO = this.$parent.getTTVTvsTO(this.currentItem);
      let modal = this.$refs.frmThemThietBi;
      modal.donvi_id = TTVTvsTO.ttvtID;
      modal.to_id = TTVTvsTO.toID;
      modal.nhatram_id = this.currentItem.itemID;
      let result = await modal.show();

      if (result.xacnhan) {
        await this.loadDsThietBi();
      }
    },
    saveData: async function (args) {
      this.$root.showLoading(true);
      try {
        // let toaDo = {
        //   kinhDo: 0,
        //   viDo: 0,
        // };
        let msg = "";
        let count = 0;
        let thayDoiDc = false;
        for (let i = 0; i < this.dsThuocTinh.length; i++) {
          const thuocTinh = this.dsThuocTinh[i];
          if (thuocTinh.GIATRI != thuocTinh.GIATRI_CU) {
            if (
              this.thamSo.CAPNHAT_KT_GIATRI_TT &&
              thuocTinh.THUOCTINH == "Tọa độ Longitude"
            ) {
              if (!this.validateLongitude(thuocTinh.GIATRI)) {
                msg =
                  "Giá trị kinh độ không hợp lệ. Giá trị hợp lệ [-180.0 đến 180.0]";
              }
              // else {
              //   toaDo.kinhDo = thuocTinh.GIATRI;
              // }
            }
            if (
              this.thamSo.CAPNHAT_KT_GIATRI_TT &&
              thuocTinh.THUOCTINH == "Tọa độ Latitude"
            ) {
              if (!this.validateLatitude(thuocTinh.GIATRI)) {
                msg =
                  "Giá trị vĩ độ không hợp lệ. Giá trị hợp lệ [-90.0 đến 90.0]";
              }
              // else {
              //   toaDo.viDo = thuocTinh.GIATRI;
              // }
            }

            if (msg != "") {
              this.$root.toastError(msg);
              break;
            } else {
              let rs = await this.capNhatVatTuThuocTinh(
                this.currentThietBi.THIETBI_ID,
                thuocTinh.TTVT_ID,
                thuocTinh.GTTT_ID != null ? thuocTinh.GTTT_ID : 0,
                thuocTinh.GIATRI
              );
              if (parseInt(rs) == -1) {
                msg =
                  "Thuộc tính " +
                  thuocTinh.THUOCTINH +
                  " với giá trị " +
                  thuocTinh.GIATRI +
                  " không phù hợp";
                break;
              } else {
                thuocTinh.GIATRI_CU = thuocTinh.GIATRI;
                count++;
              }
              if (thayDoiDc == false)
                thayDoiDc = thuocTinh.THUOCTINH == "Địa chỉ";
            }
          }
        }
        if (
          thayDoiDc &&
          this.currentThietBi.TINH_ID > 0 &&
          this.currentThietBi.QUAN_ID > 0
        )
          await this.capNhatDiaChiThietBi();
        if (count > 0)
          this.$root.toastSuccess(count + " Thuộc tính đã được cập nhật");
      } finally {
        this.$root.showLoading(false);
      }
    },
    cancelForm: async function (args) {
      //Form phu tro khong lam gi het
      //console.log("cancelForm: ", args);
    },
    deleteData: async function (args) {
      this.$root.showLoading(true);
      try {
        if (this.kiemTraQuyenTBPT()) {
          if (this.currentThietBi == null || this.currentThietBi == undefined || this.currentThietBi.THIETBI_ID == null || this.currentThietBi.THIETBI_ID == undefined) {
            this.$root.toastError("Bạn chưa chọn thiết bị!");
          }
          else {
            this.$bvModal
              .msgBoxConfirm("Bạn chắc chắn muốn xóa thiết bị này?", {
                title: "Thông báo",
                size: "sm",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
                centered: true,
              })
              .then(async (v) => {
                if (v) {
                  let t = await this.xoaThietBiPhuTro();
                  if (t) {
                    this.$root.toastSuccess("Xóa thiết bị thành công");
                    await this.loadDsThietBi();
                  }
                }
              });
          }
        } else {
          this.$root.toastError("Bạn không có quyền xóa!");
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    validateLongitude: function (value) {
      return isFinite(value) && Math.abs(value) <= 180;
    },
    validateLatitude: function (value) {
      return isFinite(value) && Math.abs(value) <= 90;
    },
    checkIsNumber: function (value) {
      let regNumber = /^[-+]?(?!0\d)\d*(\.\d+)?$/;
      return (
        value.toString().match(regNumber) ||
        (typeof value === "number" && isFinite(value))
      );
    },
    gridDsThietBi_rowSelected: async function (args) {
      this.currentThietBi = {
        VATTU_ID: args.data.VATTU_ID,
        THIETBI_ID: args.data.THIETBI_ID,
        DIACHI_ID: args.data.DIACHI_ID,
        TINH_ID: args.data.TINH_ID,
        QUAN_ID: args.data.QUAN_ID,
        PHUONG_ID: args.data.PHUONG_ID,
        PHO_ID: args.data.PHO_ID,
        AP_ID: args.data.AP_ID,
        KHU_ID: args.data.KHU_ID,
        DACDIEM_ID: args.data.DACDIEM_ID,
        SONHA: args.data.SONHA,
        DIACHI: args.data.DIACHI,
        VIDO: args.data.VIDO,
        KINHDO: args.data.KINHDO,
        THIETBI_CHA_ID:
          args.data.THIETBI_CHA_ID == undefined
            ? null
            : args.data.THIETBI_CHA_ID,
      };
      await this.loadDsThuocTinh();
    },
    gridDsThietBi_DetailDataBound: function (args) {
      args.childGrid.dataSource = this.dsThietBiCon;
      args.childGrid.contextMenuItems = this.contextMenuThietBi;
    },
    gridDsThietBi_RowDataBound: function (args) {
      let tag = args.row.querySelector("td");
      tag.className += " e-customizedExpandcell";
      let hasChild = false;
      if (!(this.dsThietBiCon == null || this.dsThietBiCon == undefined)) {
        let items = this.dsThietBiCon.filter(
          (x) => x.THIETBI_CHA_ID == args.data.THIETBI_ID
        );
        hasChild = items.length > 0;
      }
      if (!hasChild) {
        tag.className = "e-customizedExpandcell";
        tag.innerHTML =
          "<span class='e-icons e-dtdiagonalright e-icon-grightarrow'></span>";
      }
    },
    gridDsThietBi_ContextMenuClick: async function (args) {
      if (args.item.id == "menuItemVHTX") {
        console.log("menuItemVHTX");
        if (this.currentThietBi.THIETBI_ID < 0) return;
        this.$refs.frmVanHanhThuongXuyen.ma_ts =
          this.currentThietBi.THIETBI_ID + "";
        this.$refs.frmVanHanhThuongXuyen._vthietbi_id =
          this.currentThietBi.THIETBI_ID;
        this.$refs.frmVanHanhThuongXuyen.Kieugoi = 1;
        this.$bvModal.show("frmVanHanhThuongXuyen");
      } else if (args.item.id == "menuItemBDDK") {
        this.$root.$bvModal.show("formBaoDuong");
        console.log("menuItemBDDK");
      } else if (args.item.id == "menuItemSuaChua") {
        this.$root.$bvModal.show("popupSuaChuaThietBiPhuTro");
      } else if (args.item.id == "menuItemDieuChuyen") {
        console.log("menuItemDieuChuyen");
        if (this.currentThietBi.THIETBI_ID < 0) return;
        this.$refs.frmDieuChuyen.ma_ts =
          this.currentThietBi.THIETBI_ID.toString();
        this.$refs.frmDieuChuyen.vthietbi_id = this.currentThietBi.THIETBI_ID;
        this.$refs.frmDieuChuyen.kieuGoi = 1;
        this.$refs.frmDieuChuyen.chuyen_tbcon =
          this.currentThietBi.THIETBI_CHA_ID == null ? false : true;
        this.$bvModal.show("frmDieuChuyen");
      } else if (args.item.id == "menuItemLuuKho") {
        if (this.currentThietBi.THIETBI_ID < 0) return;
        this.$bvModal.show("frmTiepNhanLuuKho");
      }
    },
    showModalAnhChup: async function (args) {
      this.currentThuocTinhHinhAnh = args;
      this.$refs.modalAnhChup.show();
    },
    modalAnhChup_Show: async function (args) {
      let imageRelativeUrls = [];
      if (this.currentThuocTinhHinhAnh != null) {
        if (this.currentThuocTinhHinhAnh.GIATRI != null) {
          imageRelativeUrls =
            this.currentThuocTinhHinhAnh.GIATRI.toString().split("|");
        }
      }
      await this.$refs.formAnhChup.bindImages(imageRelativeUrls);
    },
    btnThemFile_Click: function () {
      this.$refs.formAnhChup.themFiles();
    },
    btnXoaFile_Click: function () {
      this.$refs.formAnhChup.xoaFiles();
    },
    formAnhChup_FilesChanged: function (args) {
      let s = "";
      if (args.length > 0) {
        for (let i = 0; i < args.length; i++) {
          const file = args[i];
          if (!(file.relativeUrl == null || file.relativeUrl == undefined)) {
            if (s != "") s += "|";
            s += file.relativeUrl;
          }
        }
      }
      this.currentThuocTinhHinhAnh.GIATRI = s;
      this.updateThuocTinh(this.currentThuocTinhHinhAnh);
    },
    updateThuocTinh: function (args) {
      let thuocTinh = this.dsThuocTinh.find(
        (x) =>
          x.THUOCTINH == args.THUOCTINH &&
          x.TEN_LOAI == args.TEN_LOAI &&
          x.NHOM_TT == args.NHOM_TT
      );
      if (thuocTinh != null) {
        thuocTinh.GIATRI = args.GIATRI;
        thuocTinh.GTTT_ID = args.GTTT_ID;
      }
    },
    updateThietBi: async function (args, fieldName) {
      let msg = null;
      if (fieldName == "MA_TBI") {
        msg = "Bạn có chắc chắn muốn cập nhật mã tài sản ?";
        if (this.thamSo.ECMS_UPDATE_MATS) {
          if (!this.kiemTraQuyenTBPT()) msg = "";
        }
      } else if (fieldName == "GHICHU") {
        msg = "Bạn có chắc chắn muốn cập nhật ví trí điểm đặt ?";
      }
      if (msg != null) {
        if (msg == "") {
          this.$root.toastError("Bạn không có quyền cập nhật mã tài sản");
        } else {
          this.$bvModal
            .msgBoxConfirm(msg, {
              title: "Thông báo",
              size: "sm",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            })
            .then(async (v) => {
              if (v) {
                let t = false;
                if (fieldName == "MA_TBI") {
                  t = await this.capNhatThietBiMaTBI(
                    args.THIETBI_ID,
                    args.MA_TBI
                  );
                } else if (fieldName == "GHICHU") {
                  t = await this.capNhatThietBiGhiChu(
                    args.THIETBI_ID,
                    args.GHICHU
                  );
                }
                if (t) {
                  this.$root.toastSuccess("Cập nhật thành công");
                }
              }
            });
        }
      }
    },
    showModalDiaChi: function (args) {
      this.currentThuocTinhDiaChi = args;
      this.$refs.modalDiaChi.show();
    },
    modalDiaChi_Ok: function (args) {
      if (
        this.$refs.formDiaChi.PHO_ID > 0 ||
        this.$refs.formDiaChi.AP_ID > 0 ||
        this.$refs.formDiaChi.KHU_ID > 0
      ) {
        this.currentThietBi.DIACHI_ID = this.$refs.formDiaChi.DIACHI_ID;
        this.currentThietBi.TINH_ID = this.$refs.formDiaChi.TINH_ID;
        this.currentThietBi.QUAN_ID = this.$refs.formDiaChi.QUAN_ID;
        this.currentThietBi.PHUONG_ID = this.$refs.formDiaChi.PHUONG_ID;
        this.currentThietBi.PHO_ID = this.$refs.formDiaChi.PHO_ID;
        this.currentThietBi.AP_ID = this.$refs.formDiaChi.AP_ID;
        this.currentThietBi.KHU_ID = this.$refs.formDiaChi.KHU_ID;
        this.currentThietBi.DACDIEM_ID = this.$refs.formDiaChi.DACDIEM_ID;
        this.currentThietBi.SONHA = this.$refs.formDiaChi.SONHA;
        this.currentThietBi.DIACHI = this.$refs.formDiaChi.DIACHI;

        if (this.currentThietBi.TINH_ID == 0)
          this.currentThietBi.TINH_ID = null;
        if (this.currentThietBi.QUAN_ID == 0)
          this.currentThietBi.QUAN_ID = null;
        if (this.currentThietBi.PHUONG_ID == 0)
          this.currentThietBi.PHUONG_ID = null;
        if (this.currentThietBi.PHO_ID == 0) this.currentThietBi.PHO_ID = null;
        if (this.currentThietBi.AP_ID == 0) this.currentThietBi.AP_ID = null;
        if (this.currentThietBi.KHU_ID == 0) this.currentThietBi.KHU_ID = null;
        if (this.currentThietBi.DACDIEM_ID == 0)
          this.currentThietBi.DACDIEM_ID = null;
        if (this.currentThietBi.DIACHI_ID == 0)
          this.currentThietBi.DIACHI_ID = null;
        if (this.currentThietBi.SONHA == "") this.currentThietBi.SONHA = null;
        if (this.currentThietBi.DIACHI == "") this.currentThietBi.DIACHI = null;
        this.currentThuocTinhDiaChi.GIATRI = this.currentThietBi.DIACHI;
        this.updateThuocTinh(this.currentThuocTinhDiaChi);
      } else {
        args.preventDefault();
        this.$bvModal.msgBoxOk("Hãy chọn Phố, Ấp, Khu!", {
          title: "Thông báo",
          size: "sm",
          centered: true,
          okTitle: "Đóng",
          buttonSize: "sm",
        });
      }
    },
    modalDiaChi_Show: async function (args) {
      await this.$refs.formDiaChi.bindData(this.currentThietBi);
    },
    showModalToaDo: function (args) {
      this.$refs.modalToaDo.show();
    },
    modalToaDo_Ok: function (args) {
      this.currentThietBi.KINHDO = this.$refs.formToaDo.lng;
      this.currentThietBi.VIDO = this.$refs.formToaDo.lat;
      let kinhDo = this.dsThuocTinh.find(
        (x) => x.THUOCTINH == "Tọa độ Longitude"
      );
      if (kinhDo != null) {
        kinhDo.GIATRI = this.currentThietBi.KINHDO;
        this.updateThuocTinh(kinhDo);
      }
      let viDo = this.dsThuocTinh.find((x) => x.THUOCTINH == "Tọa độ Latitude");
      if (viDo != null) {
        viDo.GIATRI = this.currentThietBi.VIDO;
        this.updateThuocTinh(viDo);
      }
    },
    modalToaDo_Show: async function (args) {
      let config = this.$root.context.user.getMapConfigDefault();
      let arg = {
        KINHDO: 0,
        VIDO: 0,
        DIACHI: this.currentThietBi.DIACHI,
      };
      if (!(config == null || config == undefined)) {
        if (!(config.longtitus == null || config.longtitus == undefined)) {
          arg.KINHDO = config.longtitus;
        }
        if (!(config.latitus == null || config.latitus == undefined)) {
          arg.VIDO = config.latitus;
        }
      }
      if (
        !(
          this.currentThietBi.KINHDO == null ||
          this.currentThietBi.KINHDO == undefined
        )
      ) {
        if (this.currentThietBi.KINHDO != 0) {
          arg.KINHDO = this.currentThietBi.KINHDO;
        }
      }
      if (
        !(
          this.currentThietBi.VIDO == null ||
          this.currentThietBi.VIDO == undefined
        )
      ) {
        if (this.currentThietBi.VIDO != 0) {
          arg.VIDO = this.currentThietBi.VIDO;
        }
      }
      await this.$refs.formToaDo.bindData(arg.KINHDO, arg.VIDO, arg.DIACHI);
    },
    showModalGiaTriThuocTinh: async function (args) {
      this.currentThuocTinhGiaTri = args;
      let modal = this.$refs.frmCapNhatGiaTriTT;
      modal.TenThuocTinh = this.currentThuocTinhGiaTri.THUOCTINH;
      modal.GiaTri = this.currentThuocTinhGiaTri.GIATRI;
      modal.Type =
        this.currentThuocTinhGiaTri.THUOCTINH == "Lực căng của dây co" ? 1 : 2;
      await modal.frmCapNhatGiaTriTT_Load();
      let result = await modal.show();
      if (result.ChapNhan) {
        this.currentThuocTinhGiaTri.GIATRI = result.GiaTri;
        this.updateThuocTinh(this.currentThuocTinhGiaTri);
      }
    },
  },
};
</script>
<style>
.tbi-ptro .grid-ext .e-row[aria-selected="true"] .e-customizedExpandcell {
  background-color: #f9e1a9 !important;
}

.tbi-ptro .grid-ext .e-row[aria-selected="true"]:hover .e-customizedExpandcell {
  background-color: transparent !important;
}

.tbi-ptro .grid-ext .e-row .e-customizedExpandcell {
  border-width: 1px 1px 0 1px;
  border-color: #dee2e6;
  text-align: center;
}

.tbi-ptro .grid-ext .e-grid .e-customizedExpandcell .e-icons {
  font: normal normal normal 14px/1 FontAwesome;
}

.tbi-ptro .grid-ext .e-grid .e-customizedExpandcell span.e-icons {
  color: #ccc;
}

.tbi-ptro .grid-ext .e-grid .e-customizedExpandcell .e-icon-grightarrow::before {
  content: "\f196";
}

.tbi-ptro .grid-ext .e-grid .e-customizedExpandcell .e-icon-gdownarrow::before {
  content: "\f147";
}

.tbi-ptro .grid-ext .e-grid .e-row .e-rowcell {
  padding-top: 1px;
  padding-bottom: 1px;
}
</style>
