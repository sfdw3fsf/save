<template src="./DotTongHop.html"></template>
<style scoped src="./DotTongHop.scss"></style>
<script>
import Vue from "vue";
import moment from "moment";
import { getterName,actionName,statePropertyName,mutationName} from '../../store/dottonghop'
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import {
  GridPlugin,
  Filter,
  Resize,
  ExcelExport,
  Sort,
  DetailRow,
  Page,
  Group,
} from "@syncfusion/ej2-vue-grids";

Vue.use(GridPlugin);
export default Vue.extend({
  name: "DotTongHop",

  data() {
    return {
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",       
        formatMonth: "MM/yyyy",
        waterMark: "Chọn ngày",
      },

      params:{
        Thang: new Date(),
        ngayDuyet: new Date(),
        ngayDuyetConfirm: new Date(),
        ngayVB: '',
        tagFrom: '',
        dsDK: 0,     
        viewAllTHDK: "0",
        tongHopId: -1,
        dotCu : -1,
        Dot: -1,
        ghiChu: "",
        maTH: "",
        fileCV : ""
      },
      themMoi: false,
      thongTinNguoiDung:{
        nhanVienId: 4373,//this.$auth.getNhanVienID() 
        nguoiCN: "admin", //this.$auth.MaNguoiDung
        mayCN: "DESKTOP-IDG67LD", //this.$auth.MAYCN
        ipCN: "10.59.90.128", //this.$auth.IPCN
        donViId : 855, //this.$auth.getDonViID(),
      },
      dataSource:{
        DSDotTongHop: [],
        DSDotDK : [],
        DSDKDaGan:  [],
        DSDKChuaGan: [],
        DSFile: []
      },
      Enabled:{
        Duyet:true,
        huyDuyet : true,
        ghiLai : true,
        Huy : true,
        Xoa :true,
        FileCV:true,
        nhapMoi:true,
      },
      dotTongHopFocus : [],
      dotTongHopChecked : [],
      rowFocus : {}
    };
  },
  computed: {
    ...mapState("dottonghop", ["DSTongHopThang"]),
    ...mapGetters("dottonghop", getterName),
   
  },
  methods: {
    ...mapActions("dottonghop", actionName),
    ...mapMutations("dottonghop", mutationName),

    f_MonthToString: function (value, format = "YYYYMM") {
      return moment(value).format(format);
    },
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },

    async gridDotTongHop_FocusedRowChanged(row) {
      if (row) {
        this.rowFocus = row;
        this.setParamsFocus(row);   
      }
    },

    gridDotTongHop_selected: function (dataItem) {      
      this.dotTongHopChecked = dataItem;   
      console.log(this.dotTongHopChecked , " = Checked "); 
      this.BAT_TAT_DUYET_TH();
    },

    onChangeThang(){
      this.loading(true);
      this.NAP_DS_TONGHOP();
      this.loading(false);
    },

    async CAPNHAT_FILE_CV(urlFile,kieu){
      if(kieu == 1){
        await this.capNhapFileCV({
          tongHopId: this.params.tongHopId,
          fileCv: urlFile,
          ngayCn: this.f_DateTimeToString(new Date()),
          nguoiCn:this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
          kieu: kieu
        })
        this.NAP_DS_FILE_CV()
      }else if(kieu == 2){
        await this.capNhapFileCV({
          tongHopId: this.params.tongHopId,
          fileCv: urlFile,
          ngayCn: this.f_DateTimeToString(new Date()),
          nguoiCn:this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
          kieu: kieu
        })
        this.NAP_DS_FILE_CV()
        this.XOA_FILE(urlFile)
      }
      else if(kieu == 3){
        await this.capNhapFileCV({
          tongHopId: this.params.tongHopId,
          fileCv: urlFile,
          ngayCn: this.f_DateTimeToString(new Date()),
          nguoiCn:this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
          kieu: kieu
        })
        this.NAP_DS_FILE_CV()
      }
    },
    async NAP_DS_TONGHOP(){

      let DSTongHopThang = await this.getDSTongHopThang({
        dataRequest: {
          thangDk: '201704',//this.f_MonthToString(this.params.Thang),
          tagFrm : this.params.tagFrom,
          kieuTh: this.params.dsDK,
          donViId : this.thongTinNguoiDung.donViId,
          viewAll: this.params.viewAllTHDK
        },
        pageNo: 0,
        pageSize : 100
      })
      
      this.dataSource.DSDotTongHop = DSTongHopThang.data
      if(this.dataSource.DSDotTongHop.length > 0){    
        this.rowFocus =  this.dataSource.DSDotTongHop[0]
        this.setParamsFocus(this.rowFocus);
      }else{
        this.Enabled.ghiLai = false;
        this.Enabled.Huy = false;
        this.Enabled.Xoa = false;
  
        this.params.Dot = -1;
        this.params.ghiChu = "";
        this.params.ngayDuyet = "";
        this.params.ngayVB = "";
        this.params.maTH = "";

        this.dataSource.DSDKDaGan = [],
        this.dataSource.DSDKChuaGan = []
      }
      this.themMoi = false
      this.Enabled.nhapMoi = true;

      console.log(this.dataSource.DSDotTongHop , " = DSDotTongHop")
      this.BAT_TAT_DUYET_TH()
    },

    showPopupNhapNgayDuyet(){
      console.log("popupNhapNgayDuyet")
      this.$bvModal.show("popupNhapNgayDuyet");  
    },
    async onClickDuyet(){
      this.$bvModal.hide("popupNhapNgayDuyet");  
     
      var listDuyetId = []
      for(const i in this.dotTongHopChecked){
        var tongHopId = this.dotTongHopChecked[i]
        for(const j in this.dataSource.DSDotTongHop){
          if(this.dataSource.DSDotTongHop[j].TONGHOP_ID == tongHopId && this.dataSource.DSDotTongHop[j].NGAYDUYET == null){
            listDuyetId.push(tongHopId)
          }
        }      
      }
    
      if(listDuyetId.length == 0){
        this.$toast.error("Hãy chọn danh sách tổng hợp muốn duyệt");
        return;
      }
      let KTDotTongHop = await this.kiemTraDotTongHop({tongHopIdArr : listDuyetId})
      if(KTDotTongHop[0] == 0){
        this.$toast.error("Đợt" + KTDotTongHop[0] + " chưa tổng hợp, không thể duyệt");
        return;
      }

      let duyetTongHop = await this.duyetDotTongHop({
        tongHopIdArr : listDuyetId,
        ngayDuyet: this.f_DateTimeToString(this.params.ngayDuyetConfirm),
        ngayCn: this.f_DateTimeToString(new Date()),
        nguoiCn: this.thongTinNguoiDung.nguoiCN,
        mayCn: this.thongTinNguoiDung.mayCN,
        ipCn: this.thongTinNguoiDung.ipCN
      })
  
      if(duyetTongHop == 1){
        NAP_DS_TONGHOP();
        this.$toast.success("Duyệt thành công");
        return;
      }
    },

    async onClickDeleteFile(){
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm(
          "Bạn có muốn xóa file không ?",
          {
            title: "Xác nhận hành động",
            centered: true,
            size: "md",
          }
        )
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return
      }
      this.CAPNHAT_FILE_CV(this.params.FileCV,2)
    },
    async onClickHuyDuyet(){
      // Xác Thực
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm(
          "Bạn có thực sự muốn hủy duyệt đợt tổng hợp đang chọn không ?",
          {
            title: "Xác nhận hành động",
            centered: true,
            size: "md",
          }
        )
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return
      }
      
      var listHuyDuyetId = []
      for(const i in this.dotTongHopChecked){
        var tongHopId = this.dotTongHopChecked[i]
        for(const j in this.dataSource.DSDotTongHop){        
          if(this.dataSource.DSDotTongHop[j].TONGHOP_ID == tongHopId && this.dataSource.DSDotTongHop[j].NGAYDUYET != null){
            listHuyDuyetId.push(tongHopId)
          }
        }      
      }
      if(listHuyDuyetId.length == 0){
        this.$toast.error("Hãy chọn danh sách tổng hợp muốn hủy duyệt");
        return;
      }

      var KTHopDongDaLap = await this.kiemTraHopDongDaLap(this.params.tongHopId) 
      console.log(KTHopDongDaLap , " = KTHopDongDaLap")
      if(KTHopDongDaLap > 0){
        this.$toast.error("Bạn đã lập hợp đồng không thể hủy duyệt");
        return;
      }

      var KTChungTuDaLap = await this.kiemTraChungTuDaLap(this.params.tongHopId) 
      console.log(KTChungTuDaLap , " = KTChungTuDaLap")
      if(KTChungTuDaLap.length > 0){
        this.$toast.error("Bạn đã lập chứng từ không thể hủy duyệt ");
        return;
      }

      let duyetTongHop = await this.duyetDotTongHop({
        tongHopIdArr : listHuyDuyetId,
        ngayDuyet: "",
        ngayCn: this.f_DateTimeToString(new Date()),
        nguoiCn: this.thongTinNguoiDung.nguoiCN,
        mayCn: this.thongTinNguoiDung.mayCN,
        ipCn: this.thongTinNguoiDung.ipCN
      })
      console.log(duyetTongHop , " = duyetTongHop")
      if(duyetTongHop == 1){
        NAP_DS_TONGHOP();
        this.$toast.success("Hủy duyệt thành công");
        return;
      }
    },

    async NAP_DS_DKTH(){
      this.dataSource.DSDKDaGan = []
      this.dataSource.DSDKChuaGan = []
      if(this.params.tongHopId != -1){
        this.dataSource.DSDKDaGan = await this.getDSDangKyTongHop({
          tuThangDk: "0",
          denThangDk: "0",
          ttDkId: 1,
          donViId: -1,
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
          tuNgay: "0",
          denNgay: "0",
          tagfrm: this.params.dsDK,
          tongHopId: this.params.tongHopId
        }) 

        this.dataSource.DSDKChuaGan = await this.getDSDangKyTongHop({
          tuThangDk: "0",
          denThangDk: "0",
          ttDkId: 2,
          donViId: -1,
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
          tuNgay: "0",
          denNgay: "0",
          tagfrm: this.params.dsDK,
          tongHopId: this.params.tongHopId
        }) 
      }
    },
    async XOA_FILE(fileCV){
      fileCV = fileCV.replace("http://10.70.52.164:8081/", "");
      console.log(fileCV , " = fileCV")
      try {
        var linkDeleteFileCV = "http://10.70.52.164:8081/FILE/UploadFile.aspx?user=dauxanh&pass=rauma&action=delete&filePath=" + fileCV;
        var request = new XMLHttpRequest();
        request.open("GET", linkDeleteFileCV, true);
        request.send();
      } catch (error) {
        
      }
    },
    async NAP_DS_FILE_CV(){
      this.dataSource.DSFile = await this.getDSFileCV(this.params.tongHopId)
      console.log(this.dataSource.DSFile , " = DSFile")
    },
    async onClickXoa(){
      // Xác Thực
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm(
          "Bạn có thực sự muốn xóa đợt tổng hợp đang chọn không ?",
          {
            title: "Xác nhận hành động",
            centered: true,
            size: "md",
          }
        )
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return
      }
      
      var kiemTraVTTongHop = await this.kiemTraVatTuTongHop(this.params.tongHopId)  
      console.log(kiemTraVTTongHop , " = kiemTraVTTongHop")
      if(kiemTraVTTongHop != 0){
        this.$toast.error("Đợt tổng hợp đang có vật tư liên quan, không thể xóa");
        return;
      }
      console.log(this.params.tongHopId , " = this.params.tongHopId")
      var DSFileCV = await this.getDSFileCVTongHop(this.params.tongHopId) 
     
      if (DSFileCV.length > 0 ){
        var listFile = DSFileCV[0].split(",");
        for(const i in listFile){
          if(listFile[i] != null){
            this.XOA_FILE(listFile[i])
          }
        }
      }
      
      var ngayDuyet = this.params.ghiChu != "" ? this.f_DateTimeToString(new Date()) : "";
      console.log(this.params.ngayVB , " = this.params.ngayVB")
      var ngayVB = this.params.ngayVB != "" ? this.f_DateTimeToString(this.params.ngayVB) : "";
      var kiemTraCapNhat = await this.capNhapDotTongHop({
          kieuId:3,
          tongHopId : this.params.tongHopId,
          ngayDuyet : ngayDuyet,
          dot: this.params.Dot,
          thangTh: this.f_MonthToString(this.params.Thang),
          ghiChu: this.params.ghiChu,
          fileCv: this.params.fileCV,
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
          donViId: this.thongTinNguoiDung.donViId,
          ngayCn: this.f_DateTimeToString(new Date()),
          nguoiCn: this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
          ngayVb: ngayVB,
          tagFrm: this.params.tagFrom,
          dsDk: this.params.dsDK
        })
        console.log(kiemTraCapNhat , " = kiemTraCapNhat")
        if(kiemTraCapNhat == 1){
          this.$toast.success("Xóa thành công");
          await this.NAP_DS_TONGHOP();
        }

    },
    onClickNhapMoi(){
      this.themMoi = true
      this.Enabled.nhapMoi = false;
      this.Enabled.ghiLai = true;
      this.Enabled.Huy = true;
      this.Enabled.Xoa = false;
      this.Enabled.Duyet = false;
      this.Enabled.huyDuyet = false;
      this.Enabled.FileCV = false;

      this.params.Dot = -1;
      this.params.ghiChu = "";
      this.params.ngayDuyet = "";
      this.params.ngayVB = "";
      this.params.maTH = "";

      this.dataSource.DSFile = [],
      this.dataSource.DSDKDaGan = [],
      this.dataSource.DSDKChuaGan = []
    },
    async onClickHuy(){
      this.Enabled.nhapMoi = true;
      this.Enabled.ghiLai = false;
      this.Enabled.Huy = false;
      this.Enabled.Xoa = false;
      this.themMoi = false
      this.Enabled.nhapMoi = true;
      this.setParamsFocus(this.rowFocus);
    },
    async onClickGhiLai(){
      this.loading(true);
      var kiemTra = await this.KIEMTRA()  
      if(kiemTra){
        var tongHopId = this.params.tongHopId;
        if(this.themMoi){
          tongHopId = await this.getKey({
            keyName:"TONGHOP_ID",
            numBlockSize: 0,
            numRetry: 0,
          })
        }
        var ngayDuyet = this.params.ghiChu != "" ? this.f_DateTimeToString(new Date()) : "";
        var ngayVB = this.params.ngayVB != "" ? this.f_DateTimeToString(this.params.ngayVB) : "";
        var kiemTraCapNhat = await this.capNhapDotTongHop({
          kieuId:this.themMoi ? 1: 2,
          tongHopId : tongHopId,
          ngayDuyet : ngayDuyet,
          dot: this.params.Dot,
          thangTh: this.f_MonthToString(this.params.Thang),
          ghiChu: this.params.ghiChu,
          fileCv: "",
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
          donViId: this.thongTinNguoiDung.donViId,
          ngayCn: this.f_DateTimeToString(new Date()),
          nguoiCn: this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
          ngayVb: ngayVB,
          tagFrm: this.params.tagFrom,
          dsDk: this.params.dsDK
        })
        if(kiemTraCapNhat == 1){
          this.$toast.success("Cập nhật thành công");
          await this.NAP_DS_TONGHOP();
        }
        this.loading(false);
        console.log(kiemTraCapNhat , " = kiemTraCapNhat")
      }
    },
    async KIEMTRA(){
      if(this.params.Dot == -1){    
        this.$toast.error("Chưa chọn đợt tổng hợp");
        this.loading(false);
        return false
      }

      let KTDotTongHop = await this.getDotTongHop({
        dot : this.params.Dot,
        thangTh: this.f_MonthToString(this.params.Thang),
        tag: this.params.tagFrom,
        kieuTh: this.params.dsDK,
        donViId: this.thongTinNguoiDung.donViId
      })

      if(this.themMoi){
        if(KTDotTongHop.length > 0){
          this.$toast.error("Đợt tổng hợp có rồi");
          this.loading(false);
          return false
        }
        console.log(KTDotTongHop , " = KTDotTongHop")
      }else{
        if(KTDotTongHop.length > 0 && this.params.Dot != this.params.dotCu){
          this.$toast.error("Đợt tổng hợp có rồi");
          this.loading(false);
          return false
        }
      }
      console.log(this.params.ngayDuyet);
      if(this.params.ngayDuyet != "" && this.params.ngayDuyet != null){
        this.$toast.error("Đợt tổng hợp đã đc duyệt bạn không được sửa");
        this.loading(false);
        return false
      }
      return true

    },
    setParamsFocus(row){      
      this.dotTongHopFocus = row
      this.params.tongHopId = row.TONGHOP_ID
      this.params.Dot = row.DOT
      this.params.ghiChu = row.GHICHU
      this.params.dotCu = row.DOT
      this.params.maTH = row.MA_TH   
      this.params.ngayDuyet = row.NGAYDUYET != "" ? new Date(row.NGAYDUYET) : ""; 
      this.params.ngayVB = row.NGAY_VB != "" ? new Date(row.NGAY_VB) : ""; 
      this.Enabled.ghiLai = true
      this.Enabled.Huy = true
      this.Enabled.FileCV = true
      this.Enabled.Xoa = row.NGAYDUYET == null ? true : false

    },

    BAT_TAT_DUYET_TH(){
      var checkEnableHuyDuyet = []
      for(const i in this.dotTongHopChecked){
        var tongHopId = this.dotTongHopChecked[i]
        checkEnableHuyDuyet = this.dataSource.DSDotTongHop.filter(row => row.TONGHOP_ID == tongHopId && row.NGAYDUYET != null);        
      }
      this.Enabled.Duyet = this.dotTongHopChecked.length != 0 ? true : false;
      this.Enabled.huyDuyet = checkEnableHuyDuyet.length != 0 ? true :false;   
    },
    
    async loadDataDotTongHop(){
      this.dataSource.DSDotDK = await this.getDSDotDk();
      await this.NAP_DS_TONGHOP(); 
      this.NAP_DS_FILE_CV()
    }

  },

  async created() {    
    this.loading(true);
    this.loadDataDotTongHop();
    this.loading(false)
       
  },
  mounted() {
    
  },
  destroyed () {
  },
  computed: {},
  
});
</script>
