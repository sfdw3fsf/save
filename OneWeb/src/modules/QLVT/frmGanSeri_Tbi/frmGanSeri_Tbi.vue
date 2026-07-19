<template src='./frmGanSeri_Tbi.html'></template>
<script>
import moment from "moment";
import Vue from "vue";
import breadcrumb from "@/components/breadcrumb";
import select2 from "@/components/Select2.vue";
import NhapXuatVT_V2_API from "../api/NhapXuatVT_V2_API";
import GanSeri_TbiAPI from "../api/frmGanSeri_TbiAPI";

export default {
  name: "PopupGanSeriThietBi",
  // props: {
  //   sources: Array,
  // },

  components: { breadcrumb, appSelect2: select2 },
  mounted() {},
  provide: {},
  computed: {},
  data() {
    return {
      dt: [],
      kq: [],
      dt_luu: [],
      ds_loc: [],
      ds_loc_gan: [],
      ds_: [],
      xacnhan: false,

      txtSerial: "",
      txtMaThung: "",
      grcDS_TbiChuaGan: [],
      gridThietBi: [],
      selectedRow: null,
      Visible: {
        txtMaThung: false,
        lblMaThung: false,
      },

      grcDS_TbiChuaGan_Columns: [
        { allowEditing:false ,fieldName: "LOHANG" , headerText: "Lô hàng", textAlign: 'Left', allowFiltering: true, width:120},
        { allowEditing:false ,fieldName: "MA_VT" , headerText: "Mã vật tư", textAlign: 'Left', allowFiltering: true, width:120},
        { allowEditing:false ,fieldName: "TEN_VT" , headerText: "Tên vật tư", textAlign: 'Left', allowFiltering: true, width:300},
        { allowEditing:false ,fieldName: "MA_TBI"  , headerText: "Mã thiết bị", textAlign: 'Left', allowFiltering: true,},
          
        { allowEditing:false ,fieldName: "SERIAL" , headerText: "Serial", textAlign: 'Left', allowFiltering: true, width:120},
        { allowEditing:false ,fieldName: "MATHUNG" , headerText: "Mã thùng", textAlign: 'Left', allowFiltering: true, visible: false, width:120},
        { allowEditing:true ,fieldName: "DVI_TINH" , headerText: "ĐVT", textAlign: 'Left', allowFiltering: true, width:120},
        { allowEditing:true ,fieldName: "SOLUONG" , headerText: "Số lượng", textAlign: 'Right', format: 'n3', type: 'number', allowFiltering: true,width:120},

        { allowEditing:false ,fieldName: "DONGIA" , headerText: "Thành tiền", textAlign: 'Right', format: 'n3', type: 'number', allowFiltering: true,width:120},
        { allowEditing:true ,fieldName: "TIEN" , headerText: "Đơn giá" , textAlign: 'Right', format: 'n3', type: 'number', allowFiltering: true,width:120},
      ],

      gridThietBi_Columns: [
        { allowEditing:false ,fieldName: "LOHANG" , headerText: "Lô hàng", textAlign: 'Left', allowFiltering: true,},
        { allowEditing:false ,fieldName: "MA_VT" , headerText: "Mã vật tư", textAlign: 'Left', allowFiltering: true,},
        { allowEditing:false ,fieldName: "TEN_VT" , headerText: "Tên vật tư", textAlign: 'Left', allowFiltering: true,},
        { allowEditing:false ,fieldName: "MA_TBI"  , headerText: "Mã thiết bị", textAlign: 'Left', allowFiltering: true,},
          
        { allowEditing:false ,fieldName: "SERIAL" , headerText: "Serial", textAlign: 'Left', allowFiltering: true,},
        { allowEditing:false ,fieldName: "MATHUNG" , headerText: "Mã thùng", textAlign: 'Left', allowFiltering: true, visible: false},
        { allowEditing:true ,fieldName: "DVI_TINH" , headerText: "ĐVT", textAlign: 'Left', allowFiltering: true,},
        { allowEditing:true ,fieldName: "SOLUONG" , headerText: "Số lượng", textAlign: 'Right', format: 'n3', type: 'number', allowFiltering: true,},

        { allowEditing:true ,fieldName: "DONGIA" , headerText: "Đơn giá" , textAlign: 'Right', format: 'n3', type: 'number', allowFiltering: true,},
        { allowEditing:false ,fieldName: "TIEN" , headerText: "Thành tiền", textAlign: 'Right', format: 'n3', type: 'number', allowFiltering: true,},
        { allowEditing:false ,fieldName: "GHICHU" , headerText: "Ghi chú", textAlign: 'Right', format: 'n3', type: 'number', allowFiltering: true,},

        { allowEditing:false ,fieldName: 'btnXoaVT' ,width: '70', headerText: 'Xóa', textAlign: 'Center', allowFiltering: true, template: function () {
            return {
                template: Vue.component('XoaVTTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnXoatbI_ButtonClick">
                                  <span class="-ap icon-close"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnXoatbI_ButtonClick(){
                        await this.$parent.$parent.$parent.$parent.btnXoatbI_ButtonClick(this.data)
                      }

                    }
                })
            }
        }},
      ],

      sources: [],
      
      isVisibleMaThung: false,

      ///////
      animationSettings: { effect: 'Zoom' },
      grvDS_TbiChuaGan_FocusedRow: {}

    };
  },
  methods: {
    // dataBound: function() {      
    //   this.$refs.grvDS_TbiChuaGan.grid.autoFitColumns(['LOHANG', 'MA_VT','TEN_VT','MA_TBI','SERIAL','MATHUNG','DVI_TINH','SOLUONG','DONGIA','TIEN']);
    // },
    async GanSeri_Tbi_Load() {
      this.loading(true);
      this.txtSerial = "";
      this.txtMaThung = "";

      this.ds_loc = this.dt
      let ds = this.dt.filter(l=>l["SERIAL"] == null || l["SERIAL"] == "")
      let ds_ = this.dt.filter(l=>l["SERIAL"] != null && l["SERIAL"] != "")

      

      //new change
      if (this.dt.length && Object.keys(this.dt[0]).includes("MATHUNG")){
        this.grcDS_TbiChuaGan_Columns.find(item=>item.fieldName == "MATHUNG").visible = true
        this.gridThietBi_Columns.find(item=>item.fieldName == "MATHUNG").visible = true
        this.Visible.lblMaThung = true
        this.Visible.txtMaThung = true
      }
      else{
        this.grcDS_TbiChuaGan_Columns.find(item=>item.fieldName == "MATHUNG").visible = false
        this.gridThietBi_Columns.find(item=>item.fieldName == "MATHUNG").visible = false
        this.Visible.lblMaThung = false
        this.Visible.txtMaThung = false
      }

      this.grcDS_TbiChuaGan = ds.length ? ds : []       
      this.gridThietBi = this.ds_.length ? ds_ : []
      this.loading(false);
    },
    async txtSerial_KeyPress(){
      if (this.txtSerial == "") {
        this.$toast.error("Bạn phải nhập serial");
        return;
      }

      if (this.gridThietBi.length){
        let q = this.gridThietBi.filter(element=>element.SERIAL.toUpperCase().trim() == this.txtSerial.toUpperCase().trim())
        if (q.length){
          this.$toast.error("Có " + q.length.toString() + " serial bị trùng");
          return;
        }
      }

      ///Kiểm tra luôn serial trùng trong db lúc nhấn enter, ko đợi đến khi bấm xác nhận nữa
      let kt_sr_db = await NhapXuatVT_V2_API.KiemTraSerialTonTai(this.axios, {
        serial: this.txtSerial.trim().toUpperCase(),
      });
      if (kt_sr_db.length) {
        this.$toast.error("Serial đã tồn tại trong hệ thống!");
        return;
      }

      this.grcDS_TbiChuaGan[this.grvDS_TbiChuaGan_FocusedRow.index]["SERIAL"] = this.txtSerial.toUpperCase().trim()

      //set Ma Thung doi voi truong hop co ma thung
      if (this.Visible.txtMaThung) {
        if (this.txtMaThung != "") {
          //test sinh mã thung nếu text để rỗng, hàm test ko phải hàm chính
          var maThung = await GanSeri_TbiAPI.sinhMaThung(this.axios, 0);
          this.grcDS_TbiChuaGan[this.grvDS_TbiChuaGan_FocusedRow.index]["MATHUNG"] = maThung.toString()
        } else {
          this.grcDS_TbiChuaGan[this.grvDS_TbiChuaGan_FocusedRow.index]["MATHUNG"] = this.txtMaThung.trim()
        }
      }
      this.$refs.grvDS_TbiChuaGan.grid.refreshColumns()

      this.ds_loc_gan = this.grcDS_TbiChuaGan
      let ds = []

      this.gridThietBi.length ? ds = this.gridThietBi : ""
      let ds_ = this.ds_loc_gan.filter(l=>l["SERIAL"] != null && l["SERIAL"] != "")
      let ds_2 = this.ds_loc_gan.filter(l=>l["SERIAL"] == null || l["SERIAL"] == "")

      if (ds_.length){
        ds.push(...ds_)
      }
      this.gridThietBi = ds

      if(ds_2.length){
        this.grcDS_TbiChuaGan = ds_2
      }
      else{
        this.grcDS_TbiChuaGan = []
      }

      // this.gridThietBiChuaGan = this.gridThietBiChuaGan.filter(
      //   (item) => item.STT !== this.selectedRow.STT
      // );
      // this.selectedRow.STT = this.gridThietBi.length + 1;
      // this.gridThietBi.push(this.selectedRow);
      this.txtSerial = "";
      this.loading(false);
    },

    async btnXoaALl_Click() {
      this.loading(true);
      let ds = this.gridThietBi
      if (this.gridThietBi.length > 0) {
        this.gridThietBi.forEach((element) => element.SERIAL = "" );

        if (this.grcDS_TbiChuaGan.length){
          let t = this.grcDS_TbiChuaGan
          ds.forEach(dr => t.push(dr))
          this.grcDS_TbiChuaGan = t
        }
        else{
          this.grcDS_TbiChuaGan = ds
        }

        this.gridThietBi = [];

        this.$refs.grvDS_TbiChuaGan.grid.refreshColumns()
        this.$refs.gridViewThietBi.grid.refreshColumns()
      }
      this.loading(false);
    },
    async btnXoatbI_ButtonClick(row){
      let ds = this.grcDS_TbiChuaGan
      // if (ds.length == 0 ){ ds = [] }

      let ds_2 = this.gridThietBi
      let deleteRow = this.gridThietBi.splice(row.index,1)
      deleteRow.forEach(a=>a.SERIAL = "")
      ds.push(...deleteRow)

      this.grcDS_TbiChuaGan = ds
      this.gridThietBi = ds_2
    },

    async tsbtnGhiLai_Click() {
      this.loading(true);
      this.xacnhan = false;

      this.ds_loc_gan = this.gridThietBi;

      if (this.ds_loc_gan.length == 0) {
        this.loading(false);
        this.$toast.error("Bạn chưa có thiết bị nào để ghi lại");
        return;
      }

      let ds_ = this.ds_loc_gan.filter((element) => element.SERIAL != null && element.SERIAL != "");
      if (ds_ == null || ds_.length == 0) {
        this.loading(false);
        this.$toast.error("Chưa có thiết bị nào được gán serial");
        return;
      }

      let ds = this.ds_loc_gan.filter((element) => element.SERIAL == null || element.SERIAL == "");
      if (ds.length) {
        this.loading(false);
        this.$toast.error("Vẫn còn thiết bị chưa được gán serial");
        return;
      }

      this.xacnhan = true;
      this.kq = this.ds_loc_gan;

      // this.$emit("ganThietBiData", data);
      this.loading(false);
      this.$refs.popupGanSeri_Tbi.hide();
    },


    // onGridDeleteClicked(arg) {
    //   let rs = {
    //     STT: this.gridThietBiChuaGan.length + 1,
    //     LOHANG: arg.rowData.LOHANG ? arg.rowData.LOHANG : "-",
    //     MA_VT: arg.rowData.MA_VT ? arg.rowData.MA_VT : "",
    //     TEN_VT: arg.rowData.TEN_VT ? arg.rowData.TEN_VT : "",
    //     MA_TBI: arg.rowData.MA_TBI ? arg.rowData.MA_TBI : "",
    //     SERIAL: "",
    //     MA_THUNG: arg.rowData.MA_THUNG ? arg.rowData.MA_THUNG : "",
    //     DVI_TINH: arg.rowData.DVI_TINH ? arg.rowData.DVI_TINH : "",
    //     SOLUONG: arg.rowData.SOLUONG ? arg.rowData.SOLUONG : "",
    //     DONGIA: arg.rowData.DONGIA ? arg.rowData.DONGIA : "",
    //     TIEN: arg.rowData.TIEN ? arg.rowData.TIEN : "",
    //   };
    //   this.gridThietBi = this.gridThietBi.filter(
    //     (item) => item.STT !== arg.rowData.STT
    //   );
    //   this.gridThietBiChuaGan.push(rs);
    // },

    grid_onSelectedRowChanged(data) {
      if(data == null){
        return
      }
      this.grvDS_TbiChuaGan_FocusedRow = data
      this.$refs.grvDS_TbiChuaGan.grid.getSelectedRowIndexes().length ? this.grvDS_TbiChuaGan_FocusedRow.index = this.$refs.grvDS_TbiChuaGan.grid.getSelectedRowIndexes() : ""

    },

    // chuyển Date thành type string
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    async open(){
      await this.GanSeri_Tbi_Load()
      this.$refs.popupGanSeri_Tbi.show()
    },
    async close(){
      this.$refs.popupGanSeri_Tbi.hide()
      this.$parent.closeGanSeri_Tbi()
    }
  },
  created: async function () {
    
  },
  watch: {},
  destroyed() {},
};
</script>
<style>
</style>
