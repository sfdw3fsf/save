<template src='./frmChonPhieuDeNghi.html'></template>
<script>
import moment from "moment";
import Vue from "vue";
import breadcrumb from "@/components/breadcrumb";
import select2 from "@/components/Select2.vue";
import ChonPhieuDeNghiAPI from "../api/frmChonPhieuDeNghiAPI";
import EventBus from "@/utils/eventBus";

export default {
  name: "PopupGanSeriThietBi",

  components: { breadcrumb, appSelect2: select2 },
  mounted() {},
  provide: {},
  computed: {},
  data() {
    return {
      dateFormat: "dd/MM/yyyy",
      ma_phieu : "",
      xacnhan : false,
      loaiphieu_id : 0,

      txtLoaiPhieu: 0,
      txtMaPhieu: "",
      txtDenNgay: "",
      txtTuNgay: "",

      cboLoaiPhieu: [],
      grvDSPhieu: [],
      grvDSVatTu: [],
      grvDSFile: [],

      selectedPhieu: null,
      denghi_id: 0,

      onXemTemplate: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div><button class="btn btn-second button-not-bg lh14 pad4" @click="onXem" title="Xem"><span class="icon one-search"></span></button></div>`,
            data() {
              return { data: {} };
            },
            methods: {
              onXem() {
                EventBus.$emit("onXem", this.data);
              },
            },
          }),
        };
      },
      onTaiTemplate: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div><button class="btn btn-second button-not-bg lh14 pad4" @click="onTai" title="Tải"><span class="icon  one-save"></span></button></div>`,
            data() {
              return { data: {} };
            },
            methods: {
              onTai() {
                EventBus.$emit("onTai", this.data);
              },
            },
          }),
        };
      },
    };
  },
  methods: {
    async onClickTimKiem() {
      this.loading(true);
      this.Load_DanhSach_DL();
      this.Load_DL_CT();
      this.loading(false);
    },

    async onClickChonPhieu() {
      if (this.selectedPhieu == null) {
        this.$root.$toast.error("Chưa có phiếu được chọn");
        return;
      }

      var isBoxConfirm = false;              
      await this.$bvModal
        .msgBoxConfirm(`Bạn có chắc chắn muốn chọn phiếu đề nghị này?`, {
          title: "Thông báo",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return;
      }
      this.ma_phieu = this.selectedPhieu.MA_DN;
      this.denghi_id = this.selectedPhieu.DENGHI_ID;
      this.xacnhan = true;
      this.dialogClose()
    },

    async LAY_DS_LOAIPHIEU() {
      let res = await ChonPhieuDeNghiAPI.layDanhMucQuanLyDeNghi(this.axios, {
        nhanVienId: 0,
        kieu: 5,
        param1: "",
        param2: "",
      });
      this.cboLoaiPhieu = res.data.data;
    },

    async Load_DanhSach_DL(isTimPhieu) {
      let res = await ChonPhieuDeNghiAPI.traCuuPhieuDeNghi(this.axios, {
        maDn: isTimPhieu ? this.txtMaPhieu : "",
        loaiPhieuId: this.txtLoaiPhieu == null ? 0 : this.txtLoaiPhieu,
        tuNgay: moment(this.txtTuNgay).format("DD/MM/yyyy"),
        denNgay: moment(this.txtDenNgay).format("DD/MM/yyyy"),
      });
      this.grvDSPhieu = res.data.data;
      if(this.grvDSPhieu.length == 0){
        this.grvDSVatTu = []
        this.grvDSFile = []
      }
    },

    async Load_DL_CT() {
      this.grvDSVatTu = [];
      if (this.selectedPhieu == null) {
        return;
      }

      this.txtMaPhieu = this.selectedPhieu.MA_DN;
      if (this.selectedPhieu.MOTA) {
        var temp = new Array(JSON.parse(this.selectedPhieu.MOTA)[0]);
        this.grvDSFile = temp;
      }

      this.denghi_id = this.selectedPhieu.DENGHI_ID;
      let res = await ChonPhieuDeNghiAPI.layDSDeNghiChiTiet(
        this.axios,
        this.denghi_id
      );
      this.grvDSVatTu = res.data.data;
    },

    onChangeGrvPhieu(data) {
      if (data) {
        this.selectedPhieu = data;
        this.Load_DL_CT();
      }
    },

    async onEnterMaPhieu() {
      this.Load_DanhSach_DL(true);      
    },

    async onXemHandler(item) {
      console.log(item , " = item")
      let file = JSON.parse(this.selectedPhieu.MOTA);
      let url = file[0].url;
      console.log(url , " = url")

      var checkFile = (await ChonPhieuDeNghiAPI.checkFileExist(this.axios,{fileSource : url}))
      let linkFile = ""
      if(checkFile.data.message == "Success" && checkFile.data.data.data){  
        // Download file minio        
        var getLink = (await ChonPhieuDeNghiAPI.getLinkFile(this.axios,{fileSource : url}))        
        linkFile = getLink.data.data.name                  
      }else{
        //Download file DHSX
        linkFile = 'http://10.70.52.164:8081/FILE/' + url             
      }
      const downloadLink = document.createElement("a");      
      downloadLink.href = linkFile;
      const filePath = url.split('/');
      const fileName = filePath[filePath.length - 1];
      downloadLink.download = fileName;    
      downloadLink.target = "_blank";
      downloadLink.click(); 
    },

    async onTaiHandler(item) {
      let file = JSON.parse(this.selectedPhieu.MOTA);
      let url = file[0].url;
      let filename = url.slice(url.lastIndexOf("/") + 1);
      this.axios({
        url: url,
        method: "GET",
        responseType: "blob",
      })
        .then((response) => {
          console.log("object", response);
          var fileURL = window.URL.createObjectURL(new Blob([response.data]));
          var fURL = document.createElement("a");

          fURL.href = fileURL;
          fURL.setAttribute("download", filename);
          document.body.appendChild(fURL);

          fURL.click();
        })
        .catch((error) => {
          this.$root.$toast.error("File không tồn tại!");
        });
    },
    
    Clear(){
      this.ma_phieu = "";
      this.xacnhan = false;
      this.loaiphieu_id = 0;

      this.txtLoaiPhieu= 0;
      this.txtMaPhieu= "";
      this.txtDenNgay= "";
      this.txtTuNgay= "";

      this.cboLoaiPhieu= [];
      this.grvDSPhieu= [];
      this.grvDSVatTu= [];
      this.grvDSFile= [];

      this.selectedPhieu= null;
      this.denghi_id= 0;
    },
    async frmChonPhieuDeNghi_Load(){
      try{
        this.loading(true)        
        EventBus.$on("onXem", this.onXemHandler);
        EventBus.$on("onTai", this.onTaiHandler);

        var lastMonth = new Date();
        lastMonth.setMonth(lastMonth.getMonth()-1);
        this.txtTuNgay = lastMonth;
        this.txtDenNgay = new Date();
        await this.LAY_DS_LOAIPHIEU();
        await this.Load_DanhSach_DL(true);
        // await this.Load_DL_CT();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
      
     
    },
    async dialogClose(){
      console.log(this.$parent , " = .$parent")
      await this.$parent.$parent.XacNhan_frmChonPhieuDeNghi()      
    }
  },
  created: async function () {
    //Đức rem load created anh hưởng load form cha, load khi open form
    // this.loading(true);
    // EventBus.$on("onXem", this.onXemHandler);
    // EventBus.$on("onTai", this.onTaiHandler);

    // var lastMonth = new Date();
    // lastMonth.setMonth(lastMonth.getMonth()-1);
    // this.txtTuNgay = lastMonth;
    // this.txtDenNgay = new Date();
    // this.LAY_DS_LOAIPHIEU();
    // this.Load_DanhSach_DL();
    // this.Load_DL_CT();
    // this.loading(false);
  },
  watch: {},
  destroyed() {
    EventBus.$off("onXem", this.onXemHandler);
    EventBus.$off("onTai", this.onTaiHandler);
  },
};
</script>
<style>
</style>