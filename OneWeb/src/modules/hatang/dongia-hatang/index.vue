<template src="./index.html"></template>

<script>
import { mapActions, mapState, mapGetters } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";

export default {
  data: function () {
    return {
      dataSource: [],
      dsSelected: [],
      dsCongNghe: [],
      dsHaTangPhanLoai: [],
      dsHaTangPhanLoaiTT: [],
      dateFormat: 'dd/MM/y',
      currentItem: {
        id: 0,
        congNgheId: 0,
        haTangPhanLoaiId:0,
        haTangPhanLoaiTtId: 0,
        batDau: new Date(),
        ketThuc: new Date(),
        soVanBan: null,
        ngayVanBan: new Date(),
        donGiaCPU: null,
        donGiaRAM: null,
        donGiaDISK: null,
        tyLeGiam: null,
        ghiChu: null,
        hieuLuc: 1,
      },
      controls: {
        cboCongNghe: {
          enabled: false,
          invalid: false,
        },
        cboPhanLoai: {
          enabled: false,
          invalid: false,
        },
        cboPhanLoaiTT: {
          enabled: false,
          invalid: false,
        },
        txtDonGiaCPU: {
          enabled: false,
          invalid: false,
        },
        txtDonGiaRAM: {
          enabled: false,
          invalid: false,
        },
        txtDonGiaDisk: {
          enabled: false,
          invalid: false,
        },
        txtSoVanBan: {
          enabled: false,
          invalid: false,
        },
        txtNgayVanBan: {
          enabled: false,
          invalid: false,
        },
        txtTuNgay: {
          enabled: false,
          invalid: false,
        },
        txtDenNgay: {
          enabled: false,
          invalid: false,
        },
        txtGiamGia: {
          enabled: false,
          invalid: false,
        },
        chkHieuLuc: {
          enabled: false,
        },
        txtGhiChu: {
          enabled: false,
        },
      },
      buttons: {
        btnAddNew: {
          enabled: true,
        },
        btnSave: {
          enabled: false,
        },
        btnCancel: {
          enabled: false,
        },
        btnEdit: {
          enabled: false,
        },
        btnDelete: {
          enabled: false,
        },
      },
    };
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems;
    },
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
    "currentItem.congNgheId": function (value) {
      this.controls.cboCongNghe.invalid = value == 0;
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      await this.apiDanhMucCongNghe();
      await this.apiDanhMucPhanLoai();
      await this.apiDanhMucPhanLoaiTinhToan();
      this.dataSource = await this.apiListDanhSach();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    getHieuLucText: function (value) {
      return value == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
    getColumnHieuLucTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getHieuLucText(this.data.HIEU_LUC);
              },
            },
          },
        };
      };
    },

    apiDanhMucCongNghe: async function() {
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-cong-nghe");
        this.dsCongNghe = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },
    apiDanhMucPhanLoai: async function (){
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-phan-loai");
        this.dsHaTangPhanLoai = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },
    apiDanhMucPhanLoaiTinhToan: async function () {
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-phan-loai-tinh-toan");
        this.dsHaTangPhanLoaiTT = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },
    apiListDanhSach: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/don-gia-ha-tang/danh-sach");
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiSave: async function (para) {
      let result = false;
      try {
        let postData ={
          id: para.id,
          congNgheId: para.congNgheId,
          haTangPhanLoaiId: para.haTangPhanLoaiId,
          haTangPhanLoaiTtId: para.haTangPhanLoaiTtId,
          soVanBan: para.soVanBan,
          donGiaCPU: para.donGiaCPU,
          donGiaRAM: para.donGiaRAM,
          donGiaDISK: para.donGiaDISK,
          tyLeGiam: para.tyLeGiam,
          ghiChu: para.ghiChu,
          hieuLuc: para.hieuLuc
        };
        if(para.batDau!=null ){
          postData.batDau=moment(para.batDau).format("DD/MM/YYYY");
        }
        if(para.ketThuc!=null){
          postData.ketThuc=moment(para.ketThuc).format("DD/MM/YYYY");
        }
        if(para.ngayVanBan!=null){
          postData.ngayVanBan=moment(para.ngayVanBan).format("DD/MM/YYYY");
        }
        let rs = await this.$root.context.post("/web-ecms/danhmuc/don-gia-ha-tang/cap-nhat", postData );
        result = rs.error_code=='BSS-00000000';
      } catch(e) {
          let msgErr="Lỗi : \n";
          if (e.response.data.message_detail==null)
          {
            if (e.response.data.data==null){
              msgErr=e.response.data.message;
            }else{
              let jsonObj=e.response.data.data;
              for (var key  in jsonObj) {
                msgErr+=" - " + jsonObj[key] + "\n";
              }
            }
          }else{
            msgErr=e.response.data.message_detail;
          }
          this.$root.toastError(msgErr);
          result =false;
      }
      return result;
    },
    apiDelete: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/don-gia-ha-tang/xoa", {
          id: id,
        });
        result = rs.error_code=='BSS-00000000';
      } catch(e) {
        this.$root.toastError(e.response.data.message_detail);
            result =false;
      }
      return result;
    },
    enableControls: function (args) {
      this.controls.cboCongNghe.enabled = args;
      this.controls.cboPhanLoai.enabled = args;
      this.controls.cboPhanLoaiTT.enabled = args;
      this.controls.txtTuNgay.enabled = args;
      this.controls.txtDenNgay.enabled = args;
      this.controls.txtSoVanBan.enabled = args;
      this.controls.txtNgayVanBan.enabled = args;
      this.controls.txtDonGiaCPU.enabled = args;
      this.controls.txtDonGiaRAM.enabled = args;
      this.controls.txtDonGiaDisk.enabled = args;
      this.controls.txtGiamGia.enabled = args;
      this.controls.chkHieuLuc.enabled = args;
      this.controls.txtGhiChu.enabled = args;

      this.buttons.btnSave.enabled = args;
      this.buttons.btnAddNew.enabled = !args;
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
      this.buttons.btnDelete.enabled = !args;

      if (this.currentItem.id > 0)
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
    },
    isNumeric(str) {
      if (typeof str != "string") return false;
      return !isNaN(str) && !isNaN(parseFloat(str));
    },
    fnToDate(value, format = 'DD/MM/YYYY') {
      return moment(value, format).toDate()
    },
    setCurrentItem: function (item) {

      this.currentItem.id = item.ID;
      this.currentItem.congNgheId = item.CONGNGHE_ID;
      this.currentItem.haTangPhanLoaiId = item.HATANG_PHANLOAI_ID;
      this.currentItem.haTangPhanLoaiTtId= item.HATANG_PHANLOAITT_ID;
      if (item.BATDAU!=null )
        this.currentItem.batDau= this.fnToDate(item.BATDAU);
      else
        this.currentItem.batDau=null
      if (item.KETTHUC!=null )
        this.currentItem.ketThuc= this.fnToDate(item.KETTHUC);
      else
        this.currentItem.ketThuc= null;

      this.currentItem.soVanBan= item.SOVANBAN;

      if (item.NGAYVANBAN!=null )
        this.currentItem.ngayVanBan= this.fnToDate(item.NGAYVANBAN);
      else
        this.currentItem.ngayVanBan= null;
      this.currentItem.donGiaCPU=item.DONGIA_CPU;
      this.currentItem.donGiaRAM= item.DONGIA_RAM;
      this.currentItem.donGiaDISK= item.DONGIA_DISK;
      this.currentItem.tyLeGiam= item.TYLE_GIAM;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: 0,
        CONGNGHE_ID: 0,
        HATANG_PHANLOAI_ID: 0,
        HATANG_PHANLOAITT_ID:0,
        BATDAU:null,
        KETTHUC:null,
        SOVANBAN:"",
        NGAYVANBAN:null,
        DONGIA_CPU: null,
        DONGIA_RAM: null,
        DONGIA_DISK: null,
        TYLE_GIAM: null,
        GHI_CHU: "",
        HIEU_LUC: 1,
      });
    },

    validateControls: function () {
      let msg = "";

      if (this.currentItem.congNgheId<1) {
        msg+= ", công nghệ sử dụng";
      }
      if (this.currentItem.haTangPhanLoaiId<1) {
        msg+= ", phân loại hạ tầng";
      }
      if (this.currentItem.haTangPhanLoaiTtId<1) {
        msg+= ", phân loại hạ tầng tính toán";
      }
      if (msg != ""){
        msg="Bạn chưa chọn : " + msg.substring(2) + "\n";
      }
        if (this.currentItem.batDau > this.currentItem.ketThuc && this.currentItem.ketThuc!=null) {
          msg=msg + "Từ ngày không được lớn hơn đến ngày!\n";
        }

      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },

    grdItems_RowSelected: async function (args) {
      if (this.buttons.btnSave.enabled) {
        return;
      }
      this.setCurrentItem(args.data);
    },
    grdItems_RowDeselected: async function (args) {
      if (this.buttons.btnSave.enabled) {
        return;
      }
      let items = this.grdItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0]);
      } else {
        this.setCurrentItemDefault();
      }
    },
    onAddNew: async function (args) {
      this.enableControls(true);
      this.setCurrentItemDefault();
    },
    onSave: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "Bạn có muốn lưu thông tin này không ?",
          "Xác nhận",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
          });
        if (flag) {
          try {
            this.$root.showLoading(true);
            let rs = await this.apiSave(this.currentItem);
            if (rs) {
                this.dataSource = await this.apiListDanhSach();
                this.$root.toastSuccess("Cập nhật thông tin thành công ");
            }
            this.enableControls(!rs);
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },

    onCancel: async function (args) {
      let items = this.grdItems.getSelectedRecords();
      let item = null;
      if (!(items == null || items.length == 0)) {
        if(this.currentItem.id == 0){
          item = items[0];
        }
        else {
          item = items.find(x => x.ID == this.currentItem.id);
        }
      }
      if (item != null) {
        this.setCurrentItem(item);
      } else {
        this.setCurrentItemDefault();
      }
      this.enableControls(false);
    },
    onEdit: async function (args) {
      this.enableControls(true);
    },
    onDelete: async function (args) {
      //this.dsSelected.length
      let items = this.grdItems.getSelectedRecords();
      let msg=`Bạn có muốn xóa ${items.length} mẫu tin đã chọn không?`
      let flag = await this.$confirm(msg,"Xác nhận",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
          dangerouslyUseHTMLString: true,
        }
      )
        .then((s) => true)
        .catch((e) => {
          return false;
        });
      if (flag) {
        try {
          this.$root.showLoading(true);
          let  isSuccess=false;
          for(const item of items){
            isSuccess = await this.apiDelete(item.ID);
            if (!isSuccess){ break;}
          }
          if (isSuccess) {
            this.setCurrentItemDefault();
            this.dataSource = await this.apiListDanhSach();
            this.enableControls(false);
            this.$root.toastSuccess("Xóa thông tin thành công");
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
  },
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
