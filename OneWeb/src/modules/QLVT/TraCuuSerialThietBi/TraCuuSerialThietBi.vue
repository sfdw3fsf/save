<template src='./TraCuuSerialThietBi.html'></template>
<style scoped src='./TraCuuSerialThietBi.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/tracuuserialthietbi";
import select2 from "@/components/Select2.vue";
import Vue from "vue";

import EventBus from '@/utils/eventBus'
import TraCuuDeNghi from "../TraCuuDeNghi/TraCuuDeNghi.vue";
import CheckPort from "@/modules/search/subscriber/CheckPort";

export default {
  components: { breadcrumb, appSelect2: select2,TraCuuDeNghi,CheckPort },
  name: "TraCuuSerialThietBi",
  mounted() {},

  data() {
    return {
      header: {
        title: "TRA CỨU SERIAL / MÃ THIẾT BỊ",
        list: [
          {
            name: "Quản lý vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tra cứu serial / Mã thiết bị",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      DHSX_SOHOA_QLVT: false,
      CAPNHAT_TT_SERIAL : false,
      Visible:{
        gcolChucNang: false
      },
      checkPortDialog: {
        isVisiable: false,
        input: {
          vma_tb: null,
          vthuebao_id: null,
          vkieu: null,
        }
      },
      params: {
        isDieuKien: true,
        dieuKien: "",
        // mã thiết bị: 2017/001675 , 2017/000769
        // Serial: 00513181F3B9996 , 00312251F0CA93B , 03126448FFC9BB8 mã này có tra cứu đề nghị
        // Serial GP: VNPT0077332C, ZTEGC0A720CC
        timKiem: "", // 2017/000769
        capGoc: "",
        cuonCap: "",
        tuKho: "",
        thongTinTbi: "",
        txtCuonCap : ""
      },
      thongTinNguoiDung: {
        ngayCN: new Date(), //this.$auth.ngayCN
        nguoiCN: this.$root.token.getUserName(), //this.$auth.MaNguoiDung       
        nhanVienId: this.$root.token.getNhanVienID(), //this.$root.token.getNhanVienID()
        donviId: this.$root.token.getDonViID(),
        maNhanVien:this.$root.token.getMaNhanVien(),
        maPhanVung: this.$root.token.getMaTinh(),
      },
      dataSource: {
        DSDieuKien: [],
        DSNhapXuat: [],
        DSTamUng: [],
        DSThueBao: [],
        DSPhieu: [],
        DSDKHopDong: [],
        DSTongHop: [],
        DSSerialGP: [],
        DSTTCuonCap: [],
      },
      widthKetQua: "100%",
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      disabledInput: {
        isChiTiet: false,
      },
      Enabled:{
        XemQT :false
      },
      rowThueBaoSelected: {},
      selectedNodes: [],
      fields: {
        dataSource: [],
        id: "MA_TBI",
        text: "title",
        child: "nodeChild",
      },
      
      Visible:{
        boxDieuKienLoc: true,
        boxNhapXuatDieuChuyen : true,
        boxTamUng : true,
        boxThueBao : true,
        boxCuonCap:true,
        XemQT :false
      },
      classIconDieuKienLoc : "icon fa fa-angle-up",
      classIconNhapXuatDieuChuyen : "icon fa fa-angle-up",
      classIconTamUng : "icon fa fa-angle-up",
      classIconThueBao : "icon fa fa-angle-up",
      classIconCuonCap : "icon fa fa-angle-up",
      urlImage : "",
      groupThueBao: { showDropArea: false, columns: ["TEN_TB"] },
      onClickTimSerial: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>
                        <button class="btn btn-main lh14 pad3" @click="TimSerial" title="Tìm Serial">
                          <span class="act -ap  icon  one-search text-white"></span>  
                        </button>
                      </div>`,                        
            data () { 
              return { data: {} }; 
            },
            methods: {
              TimSerial() {                  
                  EventBus.$emit('TimSerial', this.data);
              },              
            }
          })
        }
      },
      onClickCapNhatTrangThai: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>
                        <button class="btn btn-main" @click="openFrmCapNhatTrangThai" title="Cập nhật trạng thái Serial">
                          <span class="icon one-save" ></span>
                        </button>
                      </div>`,                        
            data () { 
              return { data: {} }; 
            },
            methods: {
              openFrmCapNhatTrangThai() {                  
                  EventBus.$emit('openFrmCapNhatTrangThai', this.data);
              },              
            }
          })
        }
      },
      onClickXemAnh: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>
                        <button class="btn btn-main" @click="openPopupXemAnh" title="Xem ảnh">
                          <span class="nc-icon-outline design_image" ></span>
                          Xem ảnh
                        </button>
                      </div>`,                        
            data () { 
              return { data: {} }; 
            },
            methods: {
              openPopupXemAnh() {                  
                  EventBus.$emit('openPopupXemAnh', this.data);
              },              
            }
          })
        }
      },
    };
  },

  computed: {
    ...mapState("tracuuserialthietbi", statePropertyName),
    ...mapGetters("tracuuserialthietbi", getterName),
  },
  methods: {
    ...mapActions("tracuuserialthietbi", actionName),
    ...mapMutations("tracuuserialthietbi", mutationName),

    async onClickChiTiet() {
      try{
        this.params.timKiem = this.params.timKiem.trim();
        if (this.params.timKiem == "") {
          this.$toast.error("Bạn chưa điền thông tin");
          return;
        }

        if (!this.params.isDieuKien) {
          this.$toast.error("Chưa chọn tiêu chí");
          return;
        }

        this.loading(true);      
        this.widthKetQua = "65%";
        this.disabledInput.isChiTiet = true;
        
        var checkSerialGP = this.dataSource.DSDieuKien.filter(a=> a.kieuTkId == this.params.dieuKien)
        if(checkSerialGP.length == 0){
          this.$toast.error("Chọn điều kiện lọc xảy ra lỗi, vui lòng chọn lại");
          return;
        }
        this.loading(true);   
       
        if (checkSerialGP[0].dieuKien.includes('tb.serial') ) {
          this.dataSource.DSSerialGP = await this.getDSSerialGP({
            timKiem: this.params.timKiem,
            kieuTkId: this.params.dieuKien    
          });
          console.log(this.dataSource.DSSerialGP, " =  DSSerialGP");
          if (this.dataSource.DSSerialGP.length == 0) {
            this.$toast.error("Không tim thấy serial gp này");
          }
        }
        this.loading(false);
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },

    onClickClose() {
      this.widthKetQua = "100%";
      this.disabledInput.isChiTiet = false;
    },
    async loadData() {
      this.dataSource.DSDieuKien = await this.getDSTimKiem();
      console.log(this.dataSource.DSDieuKien , " = this.dataSource.DSDieuKien")
      this.params.dieuKien =
        this.dataSource.DSDieuKien.length > 1
          ? this.dataSource.DSDieuKien[1].kieuTkId
          : "";
    },
    onClickLamMoi() {
      this.params.isDieuKien = true;
      this.params.dieuKien =
        this.dataSource.DSDieuKien.length > 1
          ? this.dataSource.DSDieuKien[1].dieuKien
          : "";
      this.params.timKiem = "";
      this.params.thongTinTbi = "";
      this.dataSource.DSNhapXuat = [];
      this.dataSource.DSTamUng = [];
      this.dataSource.DSThueBao = [];
    },
    async getDataThongTinCuonCap() {
      try {
        this.loading(true)
        if (this.params.cuonCap == "VNPT") {
          return;
        }
        var timDieuKien = this.dataSource.DSDieuKien.filter(a=>a.dieuKien.includes("tb.ma_tbi"))
        if(timDieuKien.length == 0){
          this.$toast.error("Có lỗi khi chọn điều kiện, vui lòng chọn lại");
          return
        }
       
        var DSTTCuonCap = await this.getDSChungTu({
          timKiem: this.params.cuonCap,
          kieuTkId: timDieuKien[0].kieuTkId    
        });
        DSTTCuonCap.forEach(e=>{
          e.NGAY_CT = e.NGAY_CT.replaceAll("-","/").slice(0,10)
          e.NGAY_HT = e.NGAY_HT.replaceAll("-","/").slice(0,10)
        })
        this.dataSource.DSTTCuonCap = DSTTCuonCap
      } catch (error) {
        this.dataSource.DSTTCuonCap = [];
        this.$toast.error(error.data.message);
      }finally{
        this.loading(false)
      }
    },
    maTbiSelect(item) {
      var treeObj = document.getElementById("treeview").ej2_instances[0];
      this.params.cuonCap = treeObj.selectedNodes[0];      
      this.params.txtCuonCap = this.params.cuonCap == "VNPT" ? "" : this.params.cuonCap
      this.loading(true);
      this.getDataThongTinCuonCap();
      this.loading(false);
    },
   
    getDataTree(dataTree) {
      
      for (const i in dataTree) {
        
        var MA_TBI = dataTree[i].MA_TBI;
        var MA_TBI_CHA = dataTree[i].MA_TBI_CHA;

        var arrChild = dataTree.filter(function (el) {
          return el.MA_TBI_CHA == MA_TBI;
        });
        
        if (arrChild.length > 0) {
          dataTree[i].hasChild = true;
          dataTree[i].expanded = true
        }
        
        // remove MA_TBI_CHA
        var arrParrent = dataTree.filter(function (el) {
          return el.MA_TBI == MA_TBI_CHA;
        });
        if (arrParrent.length == 0) {
          delete dataTree[i].MA_TBI_CHA;
        }
      }
      return dataTree
    },
    async onClickTimKiem() {
      try{
        this.params.capGoc = ""
        this.params.tuKho = ""
        this.params.timKiem = this.params.timKiem.trim();
        if (this.params.timKiem == "") {
          this.$toast.error("Bạn chưa điền thông tin");
          return;
        }

        if (!this.params.isDieuKien) {
          this.$toast.error("Chưa chọn tiêu chí");
          return;
        }
           
      
        var checkSerialGP = this.dataSource.DSDieuKien.filter(a=> a.kieuTkId == this.params.dieuKien)
        if(checkSerialGP.length == 0){
          this.$toast.error("Chọn điều kiện lọc xảy ra lỗi, vui lòng chọn lại");
          return;
        }
        this.loading(true);   
        if (!checkSerialGP[0].dieuKien.includes('tb.serial_gp')) {
          try {
            var DSNhapXuat = await this.getDSChungTu({           
              timKiem: this.params.timKiem,
              kieuTkId: this.params.dieuKien
            });
            DSNhapXuat.forEach(e=>{
              e.NGAY_CT = e.NGAY_CT.replaceAll("-","/")
              e.NGAY_HT = e.NGAY_HT.replaceAll("-","/").slice(0,10)
              e.DONGIA = e.DONGIA == null || e.DONGIA == "" ? 0 : parseInt(e.DONGIA)
              e.SOLUONG = e.SOLUONG == null || e.SOLUONG == "" ? 0 : parseInt(e.SOLUONG)
            })
            this.dataSource.DSNhapXuat = DSNhapXuat
          } catch (error) {
            this.dataSource.DSNhapXuat = [];
            this.$toast.error("Lỗi khi tra cứu nhập xuất điều chuyển vật tư");
            this.loading(false);
          }

          try {
            var DSTamUng = await this.getDSTamUng({
              timKiem: this.params.timKiem,
              kieuTkId: this.params.dieuKien
            });
            DSTamUng.forEach(e=>{
              e.NGAY_CT = e.NGAY_CT.replaceAll("-","/").slice(0,10)
              e.NGAYGIAO = e.NGAYGIAO.replaceAll("-","/")
            })
            this.dataSource.DSTamUng = DSTamUng
          } catch (error) {
            this.dataSource.DSTamUng = [];
            this.$toast.error("Lỗi khi tra cứu tạm ứng vật tư");
            this.loading(false);
          }

          try {
            var DSThueBao = await this.getDSThueBao({
              timKiem: this.params.timKiem,
              kieuTkId: this.params.dieuKien
            });
            DSThueBao.forEach(e=>{
              e.NGAY_CAP = e.NGAY_CAP.replaceAll("-","/").slice(0,10)
            })
            this.dataSource.DSThueBao = DSThueBao
          } catch (error) {
            this.dataSource.DSThueBao = [];
            this.$toast.error(
              "Lỗi khi tra cứu chi tiết cấp/thu hồi vật tư thuê bao"
            );
            this.loading(false);
          }

          try {
            this.dataSource.DSDKHopDong = await this.getDSDangKyHopDong({
              timKiem: this.params.timKiem,
              kieuTkId: this.params.dieuKien
            });
            console.log(this.dataSource.DSDKHopDong , " = this.dataSource.DSDKHopDong")
            this.params.thongTinTbi = ""
            if (this.dataSource.DSDKHopDong.length > 0) {
              this.params.thongTinTbi = this.dataSource.DSDKHopDong[0].THONGTIN;
            }
          } catch (error) {
            this.dataSource.DSDKHopDong = [];
            this.$toast.error("Lỗi khi tra cứu thông tin vật tư");
            this.loading(false);
          }
          try {
            if (checkSerialGP[0].dieuKien.includes('tb.ma_tbi')) {
              this.dataSource.DSMaThietBi = await this.getDSMaThietBi(
                this.params.timKiem
              );

              console.log(this.dataSource.DSMaThietBi, " =  DSMaThietBi");

              if (this.dataSource.DSMaThietBi.length > 0) {
                for (const i in this.dataSource.DSMaThietBi) {
                  if(this.dataSource.DSMaThietBi[i].MA_TBI_CHA == "VNPT"){
                    this.params.capGoc = this.dataSource.DSMaThietBi[i].MA_TBI
                    break
                  }
                  
                }
                this.dataSource.DSTongHop = await this.getDSTongHop({
                  vatTuId: "",
                  loHang: this.params.capGoc,
                  kieu: 11,
                });
                if (this.dataSource.DSTongHop.length > 0) {
                  this.params.tuKho = this.dataSource.DSTongHop[0];
                }
                console.log(this.dataSource.DSTongHop, " =  DSTongHop");
              }
              (this.fields = {
                dataSource: this.getDataTree(this.dataSource.DSMaThietBi),
                id: "MA_TBI",
                parentID: "MA_TBI_CHA",
                text: "MA_TBI",
                hasChildren: "hasChild",
                selected: "isSelected",
              }),
              (this.params.cuonCap = this.dataSource.DSMaThietBi[0].MA_TBI);
              this.params.txtCuonCap = this.params.cuonCap == "VNPT" ? "" : this.params.cuonCap
              this.getDataThongTinCuonCap();
            }
          } catch (error) {          
            this.$toast.error("Lỗi khi tra cứu cuộn cáp");
            this.loading(false);
          }
        } else {
          this.dataSource.DSSerialGP = await this.getDSSerialGP({
            timKiem: this.params.timKiem,
            kieuTkId: this.params.dieuKien          
          });
          console.log(this.dataSource.DSSerialGP, " =  DSSerialGP");
          if (this.dataSource.DSSerialGP.length > 0) {
            this.widthKetQua = "65%";
            this.disabledInput.isChiTiet = true;
          } else {
            this.$toast.error("Không tim thấy serial gp này");
          }

        }

        if(this.DHSX_SOHOA_QLVT && this.dataSource.DSNhapXuat.length > 0){
          this.Enabled.XemQT = true
        }else{
          this.Enabled.XemQT = false
        }
        this.loading(false);
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async frmTraCuuSerialTbi_Load(){
      var DSTSMD = await this.getDSThamSoMD(-1)
      for(const i in DSTSMD){        
        if(DSTSMD[i].maTS == "DHSX_SOHOA_QLVT" && (DSTSMD[i].tenTS == "1" || DSTSMD[i].tenTS == "2")){
          this.DHSX_SOHOA_QLVT = true
          this.Visible.XemQT = true
        }

        if(DSTSMD[i].maTS == "CAPNHAT_TT_SERIAL" && DSTSMD[i].tenTS == "1"){
          this.CAPNHAT_TT_SERIAL = true
          this.Visible.gcolChucNang = true;
        }
      }
   
    },
    async TimSerial(item){
      this.loading(true);
      this.disabledInput.isChiTiet = false
      this.widthKetQua = "100%";

      var checkSerialGP = this.dataSource.DSDieuKien.filter(a=> a.kieuTkId == this.params.dieuKien)
      if(checkSerialGP.length == 0){
        this.$toast.error("Chọn điều kiện lọc xảy ra lỗi, vui lòng chọn lại");
        return;
      }      
      
      var timDieuKien = this.dataSource.DSDieuKien.filter(a=>a.dieuKien.includes("tb.serial"))
      

      if(checkSerialGP[0].dieuKien.includes('tb.serial')){
        if(timDieuKien.length == 0){
          this.$toast.error("Có lỗi khi chọn điều kiện, vui lòng chọn lại");
          return
        }

        try {
          
          var DSNhapXuat = await this.getDSChungTu({
            timKiem: item.SERIAL,
            kieuTkId: timDieuKien[0].kieuTkId
          });
         
          DSNhapXuat.forEach(e=>{
            e.NGAY_CT = e.NGAY_CT.replaceAll("-","/")
            e.NGAY_HT = e.NGAY_HT.replaceAll("-","/").slice(0,10)
            e.DONGIA = e.DONGIA == null || e.DONGIA == "" ? 0 : parseInt(e.DONGIA)
            e.SOLUONG = e.SOLUONG == null || e.SOLUONG == "" ? 0 : parseInt(e.SOLUONG)
          })
          this.dataSource.DSNhapXuat = DSNhapXuat
        } catch (error) {
          this.dataSource.DSNhapXuat = [];
          this.$toast.error("Lỗi khi tra cứu nhập xuất điều chuyển vật tư");
          this.loading(false);
        }

        try {
          var DSTamUng = await this.getDSTamUng({
            timKiem: item.SERIAL,
            kieuTkId: timDieuKien[0].kieuTkId
          });
          DSTamUng.forEach(e=>{
            e.NGAY_CT = e.NGAY_CT.replaceAll("-","/").slice(0,10)
          })
          this.dataSource.DSTamUng = DSTamUng
        } catch (error) {
          this.dataSource.DSTamUng = [];
          this.$toast.error("Lỗi khi tra cứu tạm ứng vật tư");
          this.loading(false);
        }

        try {
          var DSThueBao = await this.getDSThueBao({
            timKiem: item.SERIAL,
            kieuTkId: timDieuKien[0].kieuTkId
          });
          DSThueBao.forEach(e=>{
            e.NGAY_CAP = e.NGAY_CAP.replaceAll("-","/").slice(0,10)
          })
          this.dataSource.DSThueBao = DSThueBao
        } catch (error) {
          this.dataSource.DSThueBao = [];
          this.$toast.error(
            "Lỗi khi tra cứu chi tiết cấp/thu hồi vật tư thuê bao"
          );
          this.loading(false);
        }

        try {
          this.dataSource.DSDKHopDong = await this.getDSDangKyHopDong({
            timKiem: item.SERIAL,
            kieuTkId: timDieuKien[0].kieuTkId
          });
          console.log(this.dataSource.DSDKHopDong , " = this.dataSource.DSDKHopDong")
          this.params.thongTinTbi = ""
          if (this.dataSource.DSDKHopDong.length > 0) {
            this.params.thongTinTbi = this.dataSource.DSDKHopDong[0].THONGTIN;
          }
        } catch (error) {
          this.dataSource.DSDKHopDong = [];
          this.$toast.error("Lỗi khi tra cứu thông tin vật tư");
          this.loading(false);
        }

      }
      this.loading(false);
    },
    DSThueBaoSelectRow(data) {
      if(data){
        this.rowThueBaoSelected = data
      }
    },
    async onClickShowPort(){
      // let f = this.$refs.frmTestPort;
      // f.vkieu = 0
      var somay = this.rowThueBaoSelected.MA_TB
      // f.txtSoMayAcc = somay == undefined || somay == null ? "" : somay
      this.checkPortDialog.input.vma_tb = somay == undefined || somay == null ? "" : somay
      this.checkPortDialog.input.vkieu = 0
      this.checkPortDialog.isVisiable = true
      this.$refs.popupTestPortV2.show()
    },
    async onClickPhieuQT(){
      var DSMaQT = await this.getThongTinQuyetToanSerial(this.params.timKiem.trim())
      if(DSMaQT.length == 0){
        this.$toast.error("Không có thông tin phiếu quyết toán cho serial: " + this.params.timKiem.trim());
        return
      }
      var maQT = DSMaQT[0].maQT
      let f = this.$refs.frmTraCuuDeNghi;
      f.params.maPhieu = maQT
      f.dialogOpen()
      this.$refs.popupTraCuuDenghi.show()
      
    },
    onClickDieuKienLoc(){
      this.classIconDieuKienLoc = this.Visible.boxDieuKienLoc ?  "icon fa fa-angle-down" : "icon fa fa-angle-up"
      this.Visible.boxDieuKienLoc = this.Visible.boxDieuKienLoc ? false : true
    },

    onClickNhapXuatDieuChuyen(){
      this.classIconNhapXuatDieuChuyen = this.Visible.boxNhapXuatDieuChuyen ?  "icon fa fa-angle-down" : "icon fa fa-angle-up"
      this.Visible.boxNhapXuatDieuChuyen = this.Visible.boxNhapXuatDieuChuyen ? false : true
    },
    onClickTamUng(){
      this.classIconTamUng = this.Visible.boxTamUng ?  "icon fa fa-angle-down" : "icon fa fa-angle-up"
      this.Visible.boxTamUng = this.Visible.boxTamUng ? false : true
    },
    onClickThueBao(){
      this.classIconThueBao = this.Visible.boxThueBao ?  "icon fa fa-angle-down" : "icon fa fa-angle-up"
      this.Visible.boxThueBao = this.Visible.boxThueBao ? false : true
    },

    onClickCuonCap(){
      this.classIconCuonCap = this.Visible.boxCuonCap ?  "icon fa fa-angle-down" : "icon fa fa-angle-up"
      this.Visible.boxCuonCap = this.Visible.boxCuonCap ? false : true
    },

    async openFrmCapNhatTrangThai(item){
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn có đồng ý cập nhật trạng thái cho Serial: " + this.params.timKiem + " không?", {
          title: "Thông báo",
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
          centered: true,
          size: "md",
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
      console.log(item)
      var phieuVTId = item.PHIEUVT_ID == null || item.PHIEUVT_ID == "" ? 0 : item.PHIEUVT_ID
      var tinhTrangTbiId = item.TINHTRANGTBI_ID == null || item.TINHTRANGTBI_ID == "" ? 0 : item.TINHTRANGTBI_ID
      var kq = await this.capNhatTrangThaiSerial({
        phieuVtId: phieuVTId,
        serial: this.params.timKiem.trim(),
        tinhTrangTbiId: tinhTrangTbiId,
        nhanVienThId: this.thongTinNguoiDung.nhanVienId,
        nguoiCn: this.thongTinNguoiDung.nguoiCN,
        mayCn: "",
        ipCn: ""
      })
      console.log(kq , " = kq")
      if(kq == "OK"){
        this.$toast.success("Cập nhật dữ liệu thành công")
      }else{
        this.$toast.error(kq)
      }
    },

    async openPopupXemAnh(item){       
      // let phieuvt_id = item.ID.toString() + item.LOAI.toString()
      let data = await this.getHinhAnh(item.ID)
      if(data == null){
        this.$toast.error("Không có thông tin hình ảnh!")
        return
      }   
      var checkFile = await this.checkFileExist({fileSource : data})        
 
      if(checkFile.message == "Success" && checkFile.data.data){
        let getLink = await this.getLinkFile({fileSource : data}) 
        this.urlImage = getLink.data.name                 
      }else{  
        let BASE_URL = "http://10.70.52.164:8081/FILE/" + this.$auth.getMaTinh()  + "_VT/"     
        this.urlImage = BASE_URL + data.split("|")[0]  
      }     
      const downloadLink = document.createElement("a");      
      downloadLink.href = this.urlImage;    
      downloadLink.download = data;    
      downloadLink.target = "_blank";
      downloadLink.click();   
      // this.$bvModal.show('popupXemAnh')
    },


    // Không dùng cho form này
    list_to_tree(list) {

      // tìm node gốc
      var list_origin = []
      for(const i in list){
        var VATTU_CHA_ID = list[i].VATTU_CHA_ID
        var listNodeParent = list.filter(
          function (el) {
            return el.VATTU_ID == VATTU_CHA_ID;
          }
        );
        if(listNodeParent.length == 0){
          list_origin.push(list[i].VATTU_CHA_ID)
        }
      }

      var map = {}, node, roots = [], i;
      
      for (i = 0; i < list.length; i += 1) {
        map[list[i].VATTU_ID] = i; // initialize the map
        list[i].children = []; // initialize the children
      }
      
      for (i = 0; i < list.length; i += 1) {
        node = list[i];
      
        if (!list_origin.includes(node.VATTU_CHA_ID)) {
          // if you have dangling branches check that map[node.parentId] exists
          list[map[node.VATTU_CHA_ID]].children.push(node);
        } else {
          roots.push(node);
        }
      
      }
      return roots;
    },
    // không dùng cho form này
    async filterTree(){
      // CODE FILTER TREE CHO VIÊN
      var dataTree = await this.testTree()
      console.log(dataTree.length , " = dataTree")
      console.log(dataTree , " = dataTree")
      var VATTU_CHA_ID = 5608
      var VATTU_ID = 6230
      var dataFilter = []
    
      // nếu focus vào gốc
      if(VATTU_CHA_ID == ""){
        dataFilter = dataTree
      }else{

        // Lấy node focus
        var nodeFocus = dataTree.filter(
          function (el) {
            return el.VATTU_ID == VATTU_ID;
          }
        );
        if(nodeFocus.length > 0){
          dataFilter.push(nodeFocus[0])
        }

        // tìm node cha của node focus
        var isNodeOrigin = false
        var VATTU_CHA_ID_Filter = VATTU_CHA_ID
        while(!isNodeOrigin){
          var listNodeParent = dataTree.filter(
            function (el) {
              return el.VATTU_ID == VATTU_CHA_ID_Filter;
            }
          );
          if(listNodeParent.length > 0){
            dataFilter.push(listNodeParent[0])
            VATTU_CHA_ID_Filter = listNodeParent[0].VATTU_CHA_ID
          }
          // đã tìm thấy node gốc
          if(VATTU_CHA_ID_Filter == ""){
            isNodeOrigin = true
          }
        }
        // tìm node con của node focus
        var listNodeChildID = [VATTU_ID]
        var isNodeChildExist = true
        while(isNodeChildExist){

          var listNodeChildId = []
          for(const i in listNodeChildID){

            //Lấy data node con
            var listNodeChild = dataTree.filter(
              function (el) {
                return el.VATTU_CHA_ID == listNodeChildID[i];
              }
            );
            // thêm vào node con vào true và lấy ID của node con
        
            console.log(listNodeChild , " = listNodeChild")
            for(const i in listNodeChild){
              dataFilter.push(listNodeChild[i])            
              listNodeChildId.push(listNodeChild[i].VATTU_ID)
            }
          }
          //gán lại node con để tìm tiếp
          listNodeChildID = listNodeChildId
          //hết node con
          if(listNodeChildId.length == 0){
            isNodeChildExist =  false
          }
        }
        console.log(dataFilter , " = dataFilter")
      }
      var test = this.list_to_tree(dataFilter)
      console.log(test , " = test")
    }
  },
  created: async function () {
    try{
      this.loading(true);
      EventBus.$on('TimSerial', this.TimSerial);
      EventBus.$on('openFrmCapNhatTrangThai', this.openFrmCapNhatTrangThai);
      EventBus.$on('openPopupXemAnh', this.openPopupXemAnh);
      await this.loadData();
      await this.frmTraCuuSerialTbi_Load()
      this.loading(false);
    }catch(e){
      this.$toast.error(e.data.message);
    }finally{
      this.loading(false);
    }
  },
  destroyed(){
    EventBus.$off('TimSerial', this.TimSerial); 
    EventBus.$off('openFrmCapNhatTrangThai', this.openFrmCapNhatTrangThai); 
    EventBus.$off('openPopupXemAnh', this.openPopupXemAnh); 
  },
 
};
</script>

<style>
 .popup-show-port .page-content {
  position: inherit !important;
}

.popup-show-port .breadcrumb-top{
  display: none !important;
}
</style>