<template src="./TraCuuVatTuThueBao.html"></template>
<style scoped src="./TraCuuVatTuThueBao.scss"></style>
<script>
//Clone @/modules/QLVT/components/TraCuuVatTuThueBao/TraCuuVatTuThueBao.vue sài b-modal
import Vue from "vue";

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../../../../../../QLVT/store/tracuuvattuthuebao";
export default Vue.extend({
  name: "TraCuuVatTuThueBao",

  data() {
    return {
      // khách hàng
      p_ma_kh: "", //HPG-000046353
      p_ten_kh: "",
      p_dia_chi_kh: "",

      // thanh toán
      p_ma_tt: "", //HPG-01-986323
      p_ten_tt: "",
      p_dia_chi_tt: "",

      // thuê bao
      p_dich_vu_id: 0,
      p_dich_vu: "",
      p_loai_hinh_id: 0,
      p_loai_hinh: "",
      p_so_may: "", //toan3667
      p_ten_tb: "",
      p_dia_chi_tb: "",
      showLoaiHinh: 'none',
      showDichVu: 'none',
      data_dich_vu: {
        p_nguoi_dung_id: this.$auth.getNguoiDungID(), //this.$auth.getNguoiDungID()
        p_ten_bang: "DICHVU_VT",
      },

      list_dich_vu: [],
      list_loai_hinh: [],

      phanVungId: this.$auth.getPhanVungID(), //this.$auth.getPhanVungID();
      bienDongVTIsVisible: false,
      tabSearch: "thuebao",

      thanhtoan_id: 0,
      thuebao_id: 0,
      khachhang_id: 0,
      tabActive: "vattu",

      // data result
      DSThueBao: [],
      DSVatTu: [],
      DSBienDong: [],
      DSLichSu: [],
      DetailThueBao: [],

      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,        
      },

      selectionOptions: {
        type: "Single",
        checkboxOnly: true
      },
      templateOptions : {
        create: function (args) {
          
          return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
        },
        write: function (args) {
        }
      },
      pageSettings: { pageSize: 10 },
      
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',

      parentData: this.DSThueBao,
      childGrid: {
        dataSource: this.DetailThueBao,
        queryString: "THUEBAO_ID",
        allowResizing:true,
        columns: [
          {
            field: "NGAY_SD",
            headerText: "Ngày SD",
            textAlign: "Right",        
            headerTextAlign : 'Center',
            width : 150
          },
          {
            field: "MA_TB",
            headerText: "Mã Tbị",
            textAlign: "Left",       
            headerTextAlign :'Center',
            width : 150
          },
          {
            field: "LOAI_TBI",
            headerText: "Thiết bị",
            textAlign: "Left",         
            headerTextAlign :'Center',  
            width : 250    
          },
          {
            field: "KIEU_TBI",
            headerText: "Kiểu thiết bị",
            textAlign: "Left",        
            headerTextAlign :'Center',
            width : 150
          },
          {
            field: "TINHTRANG_TBI",
            headerText: "Tình Trạng",
            textAlign: "Left",        
            headerTextAlign :'Center',
            width : 150
          },
          {
            field: "SL",
            headerText: "Số Lượng",
            textAlign: "Right",          
            headerTextAlign : 'Center',          
            width : 150
          },
          {
            field: "TENTRANGBI",
            headerText: "Trang bị",
            textAlign: "Left",            
            headerTextAlign :'Center',
            width : 150
          },
          {
            field: "SERI",
            headerText: "Seri",
            textAlign: "Left",
            headerTextAlign:'Center',         
            width : 150
          },
        ],
      },
    };
  },
  methods: {
    ...mapActions("tracuuvattuthuebao", actionName),
    ...mapMutations("tracuuvattuthuebao", mutationName),

    dataBound: function() {
      this.$refs.grid.autoFitColumns(['MA_TB', 'NGAY_SD']);
      console.log("dataBound")
    },
    rowDataBound(args){     
      let THUEBAO_ID = args.data.THUEBAO_ID;     
      var childrecord = this.DetailThueBao.filter(function (el) {
          return el.THUEBAO_ID == THUEBAO_ID;
        });      
      if(childrecord.length == 0) {
        args.row.querySelector('td').innerHTML=" ";
        args.row.querySelector('td').className = "e-customizedExpandcell";
      }
    },

    load: function() {
      let rowHeight = this.$refs.grid.ej2Instances.getRowHeight();  //height of the each row
      let gridHeight = this.$refs.grid.height;  //grid height
      let pageSize = this.$refs.grid.pageSettings.pageSize;   //initial page size
      let pageResize = (gridHeight - (pageSize * rowHeight)) / rowHeight; //new page size is obtained here
      this.$refs.grid.pageSettings = {pageSize: pageSize + Math.round(pageResize)};
    },
    async resetData(){
      // khách hàng
      this.p_ma_kh= "", //HPG-03-786313
      this.p_ten_kh= "",
      this.p_dia_chi_kh= "",

      // thanh toán
      this.p_ma_tt= "", //1461825
      this.p_ten_tt= "",
      this.p_dia_chi_tt= "",

      // thuê bao
      this.p_dich_vu_id = this.list_dich_vu[0].dichVuId;
      this.p_dich_vu = this.list_dich_vu[0].dichVu;
      this.list_loai_hinh = await this.getDSLoaiHinhThueBao(this.p_dich_vu_id)
     
      if (this.list_loai_hinh.length > 0) {
        this.p_loai_hinh_id = this.list_loai_hinh[0].loaiTBId;
        this.p_loai_hinh = this.list_loai_hinh[0].loaiHinhTB;
      } else {
        this.p_loai_hinh_id = 0;
        this.p_loai_hinh = ""
      }
 
      // this.p_loai_hinh_id= this.list_loai_hinh[0].loaiTBId,
      this.p_so_may= "", //toan311294 DV 4
      this.p_ten_tb= "",
      this.p_dia_chi_tb= "",     

      // this.list_dich_vu= [],
      // this.list_loai_hinh= [],
      
      this.tabSearch= "thuebao",

      this.thanhtoan_id= 0,
      this.thuebao_id= 0,
      this.khachhang_id= 0,
      this.tabActive= "vattu",

      // data result
      this.DSThueBao= [],
      this.DSVatTu= [],
      this.DSBienDong= [],

      this.DetailThueBao= []
    },

    onDetailDataBound(args) {
      args.detailElement.querySelector(".e-grid").ej2_instances[0].dataSource = this.DetailThueBao; // assign data source for child grid.
    },

    async loadDSDichVuVienThong() {   
      this.list_dich_vu = await this.getDSDichVuVienThong(this.data_dich_vu)
      if(this.list_dich_vu.length > 0 ){
        
        var list_dich_vu_temp = []
        this.list_dich_vu.forEach((element) => { 
          list_dich_vu_temp.push({
            dichVuId: element[0],
            dichVu: element[1],
          })
        })    
        this.list_dich_vu = list_dich_vu_temp
        this.p_dich_vu_id = this.list_dich_vu[0].dichVuId;
        this.p_dich_vu = this.list_dich_vu[0].dichVu;
        this.loadDSLoaiHinhThueBao();
      } 
      else {
        this.$toast.error("Không tìm thấy dịch vụ viễn thông");
      }     
    },

    async loadDSLoaiHinhThueBao() {
      this.loading(true);
      this.list_loai_hinh = await this.getDSLoaiHinhThueBao(this.p_dich_vu_id)
        
      if (this.list_loai_hinh.length > 0) {
        this.p_loai_hinh_id = this.list_loai_hinh[0].loaiTBId;
        this.p_loai_hinh = this.list_loai_hinh[0].loaiHinhTB;
      } 
      else {
        this.$toast.error("Không tìm thấy loại hình thuê bao");
      }
      this.loading(false);
        
    },

    loadTraCuuVatTuThueBao() {
      this.loadDSDichVuVienThong();
      if (this.phanVungId == "21") {
        this.bienDongVTIsVisible = true;
      }
    },
   
    onChangeTabSearch: function (tab) {
      this.tabSearch = tab;
      if (this.tabSearch == "khachhang") {
        setTimeout(() => {
            document.getElementById("maKhachHang").focus(); 
        }, 500);    
      }else if(this.tabSearch == "thanhtoan") {
     
        setTimeout(() => {
            document.getElementById("maThanhToan").focus(); 
        }, 500); 
      }else if(this.tabSearch == "thuebao") {

        setTimeout(() => {
            document.getElementById("soMay").focus(); 
        }, 500);  
      }
    },

    
    async onChangeDichVu(row) {
      if(this.p_dich_vu_id !=  row.dichVuId){
        this.p_dich_vu_id  = row.dichVuId;
        this.p_dich_vu = row.dichVu;
        this.loadDSLoaiHinhThueBao();
      }
    },
    async onChangeLoaiHinh(row) {
      this.p_loai_hinh_id  = row.loaiTBId;
      this.p_loai_hinh = row.loaiHinhTB;
    },
    // Tra Cứu Vật Tư Thuê Bao
    btnTraCuu: async function () {
      // Thanh Toán
      console.log(this.tabSearch , " = tabSearch")
      if (this.tabSearch == "thanhtoan") {
        if (this.p_ma_tt == "") {
          this.resetData()
          setTimeout(() => {
              document.getElementById("maThanhToan").focus(); 
          }, 500); 
          this.$toast.error("Vui lòng nhập mã thanh toán!");
          return;
        }
        this.loading(true);
        var DSDanhBaThueBao =  await this.getDanhBaTheoMaThueBao({
          maThueBao: this.p_ma_tt,
          donViDL: 0,
          dichVuVT: 0,
        })
        console.log(DSDanhBaThueBao , " = DSDanhBaThueBao")
        if (DSDanhBaThueBao.length > 0) {
          let isCheck = false;
          for (const index in DSDanhBaThueBao) {
            if (DSDanhBaThueBao[index].MA_TT === this.p_ma_tt) {

              isCheck = true;
              this.thanhtoan_id = DSDanhBaThueBao[index].THANHTOAN_ID;
              this.p_ten_tt = DSDanhBaThueBao[index].TEN_TT;
              this.p_dia_chi_tt = DSDanhBaThueBao[index].DIACHI_TT;
              this.tabActive = "thuebao";
              this.DSVatTu = [];
              var DSDanhBaThanhToan = await this.getDanhBaTheoMaThanhToan({maThanhToan: this.thanhtoan_id})
                
              if (DSDanhBaThanhToan.length > 0) {
                this.DetailThueBao = await this.getDSVatTuThueBao({                  
                  ma_tim_kiem : this.thanhtoan_id,
                  kieu :1
                })
                this.DSThueBao = DSDanhBaThueBao
                
              }else{
                this.resetData()
              }
              break;
            }
          }
          this.loading(false);
          if (!isCheck) {
            this.$toast.error("Không tìm thấy thông tin thuê bao");
            this.resetData()       
          }
        } else {
          this.$toast.error("Không tìm thấy thông tin thuê bao");
          this.resetData()   
          this.loading(false);
        }
                  

        // Thuê Bao
      } else if (this.tabSearch == "thuebao") {
        if (this.p_so_may == "") {
          this.resetData()
          this.$toast.error("Vui lòng nhập Số máy/Acc!");
          setTimeout(() => {
            document.getElementById("soMay").focus(); 
          }, 500);
          return;
        }
        this.loading(true);
        var DSDanhBaMaThueBao = await this.getDanhBaTheoMaThueBao({
          maThueBao: this.p_so_may,
          donViDL: 0,
          dichVuVT: this.p_dich_vu_id,
        })
          
            if (DSDanhBaMaThueBao.length > 0) {
              this.thuebao_id = DSDanhBaMaThueBao[0].THUEBAO_ID;
              this.p_ten_tb = DSDanhBaMaThueBao[0].TEN_TB;
              this.p_loai_hinh_id = DSDanhBaMaThueBao[0].LOAITB_ID;
              this.p_dia_chi_tb = DSDanhBaMaThueBao[0].DIACHI_TB;
              this.DSThueBao = []
              this.DetailThueBao = []
              this.DSVatTu = await this.getDSVatTuThueBao({
                ma_tim_kiem :this.thuebao_id,
                kieu : 2
              })
              if(this.DSVatTu.length > 0){
                this.tabActive = "vattu";
              }
              
              this.loading(false);

              if (this.bienDongVTIsVisible) {
                this.DSBienDong = await this.getBienDongVatTuThueBao({
                    ma_thue_bao : this.p_so_may,
                    kieu: 0
                })
              }  
            } else {
              this.$toast.error("Không tìm thấy thông tin thuê bao");
              this.resetData()
              this.loading(false);             
            }
                 
        //Khách Hàng
      } else if (this.tabSearch == "khachhang") {
        if (this.p_ma_kh == "") {
          this.resetData()
          this.$toast.error("Vui lòng nhập mã khách hàng!");
          setTimeout(() => {
            document.getElementById("maKhachHang").focus(); 
          }, 500);
          return;
        }
        this.loading(true);
        var DSDanhBaTheoMaThueBao = await this.getDanhBaTheoMaThueBao({
          maThueBao: this.p_ma_kh,
          donViDL: 0,
          dichVuVT: 0,
        })
          
        if (DSDanhBaTheoMaThueBao.length > 0) {
          this.khachhang_id = DSDanhBaTheoMaThueBao[0].KHACHHANG_ID;
          this.p_ten_kh = DSDanhBaTheoMaThueBao[0].TEN_KH;
          this.p_dia_chi_kh = DSDanhBaTheoMaThueBao[0].DIACHI_KH;
          this.DSVatTu = []
          var DSDanhBa = await this.getDanhBaTheoKhachHangId(
            this.khachhang_id
          )
            if (DSDanhBa.length > 0 ) {
              
              this.tabActive = "thuebao";
              // this.DSThueBao = response_danhba.data.data;

              this.DetailThueBao = await this.getDSVatTuThueBao({
                  ma_tim_kiem : this.khachhang_id,
                  kieu: 0
              })
              this.DSThueBao = DSDanhBa        
            } else {
              this.tabActive = "thuebao";  
              this.resetData()                 
            }
            this.loading(false);

        }else {
          this.$toast.error("Không tìm thấy thông tin thuê bao");
          this.resetData()
          this.loading(false);
        }   
      }
    },
    async frmTraCuuVatTuTB(thuebao_id){   
      this.thuebao_id = thuebao_id // 1543309
      if(this.thuebao_id != 0){
        this.DSVatTu = await this.getDSVatTuThueBao({
          ma_tim_kiem :this.thuebao_id,
          kieu : 2
        })
        if(this.DSVatTu.length > 0){
          this.tabActive = "vattu";
        }else{
          this.DSVatTu = []
        }
      }      
    },
    async gridViewVatTuTB_FocusedRowChanged(row){   
      if(row){
        var vkieu = row.SERI != "" ? 1 : 0     
        if(row.VATTU_ID  !== undefined){
          this.DSLichSu = await this.getDSLichSuVatTu({
            kieu : vkieu,
            vatTuId: row.VATTU_ID 
          })
        }
      }
    },
    // async dialogOpen(){
    //   await this.loadTraCuuVatTuThueBao();
    //   this.$refs.popupTraCuuVTTB.show()
    // }
  },

  created() {   
    this.loadTraCuuVatTuThueBao();
  },
  mounted: async function ()  {
    document.addEventListener('click', event => {
      if (typeof (this.$refs["title-loai-hinh"]) == 'undefined') {
          return
      }
      if (this.$refs["title-loai-hinh"].contains(event.target)) {
          this.showLoaiHinh = this.showLoaiHinh == 'none' ? 'block' : 'none'
      } else if (this.$refs["table-loai-hinh"].contains(event.target)) {
          if (event.target.tagName.toLowerCase() == 'td') {
              this.showLoaiHinh = 'none'
          } else {
              this.showLoaiHinh = 'block'
          }
      } else {
          this.showLoaiHinh = 'none'
      }

      if (this.showLoaiHinh == 'block') {
          this.popper = new Popper(this.$refs["title-loai-hinh"], this.$refs["table-loai-hinh"], {
              placement: 'bottom-start',
              modifiers: [{
                  name: 'offset',
                  options: {
                      offset: [0, 50]
                  },
              }]
          })
      } else {
          if (this.popper) {
              this.popper.destroy()
          }
          this.popper = null
      }
    })

    document.addEventListener('click', event => {
      if (typeof (this.$refs["title-dich-vu"]) == 'undefined') {
          return
      }
      if (this.$refs["title-dich-vu"].contains(event.target)) {
          this.showDichVu = this.showDichVu == 'none' ? 'block' : 'none'
      } else if (this.$refs["table-dich-vu"].contains(event.target)) {
          if (event.target.tagName.toLowerCase() == 'td') {
              this.showDichVu = 'none'
          } else {
              this.showDichVu = 'block'
          }
      } else {
          this.showDichVu = 'none'
      }

      if (this.showDichVu == 'block') {
          this.popper = new Popper(this.$refs["title-dich-vu"], this.$refs["table-dich-vu"], {
              placement: 'bottom-start',
              modifiers: [{
                  name: 'offset',
                  options: {
                      offset: [0, 50]
                  },
              }]
          })
      } else {
          if (this.popper) {
              this.popper.destroy()
          }
          this.popper = null
      }
    })
  },
  destroyed () {
  },
  computed: {
    ...mapState("tracuuvattuthuebao", statePropertyName),
    ...mapGetters("tracuuvattuthuebao", getterName),
  },
  provide: {
  },
});
</script>
