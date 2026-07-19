<template src="./DeclareMegawanByOMC.html"></template>
<style scoped src="./DeclareMegawanByOMC.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import popupHuongKN from "./popupHuongKN.vue";
import popupChiTietKetNoi from "./popupChiTietKetNoi.vue"
import api from '../../../CABMAN/DanhSachCotCap/api';
export default {
  components: { breadcrumb, SearchContractModal, popupHuongKN, popupChiTietKetNoi},
  data: function () {
    return {
       dateconfig: {
        altFormat: "d/m/Y h:i:S K",
        altInput: true,
        dateFormat: "d/m/Y",
        allowInput: true,
      },
      header: {
        title: "OMC - Khai báo Megawan",
      },
      istitle: "1",
      list_TTKH:{
        loaihdpl_options:[],
        loaihdpl: null,
        quytrinh_options:[],
        quytrinh: null,
        magiaodich: "VTLD-16/0614265",
      },
      list_TTHoanCong:{
        nguoigiaoviec_options:[],
        nguoigiaoviec: null,
        ngaygiaoviec: new Date(),
        ngayhoanthanh: new Date,
        thoihan_options:[],
        thoihan: null,
        loaikenh_options:[],
        loaikenh: null,
        loaimodem_options:[],
        loaimodem: null,
        trangbi_options:[],
        trangbi: null,
        loainode_options: [],
        loainode: null,
        loaitbi_options: [],
        loaitbi: null,
        chuquan_options: [],
        chuquan: null,
        doitac_options: [],
        doitac: null,
        tocdoCIR_options: [],
        tocdoCIR: null,
        tocdoCIR_options: [],
        tocdoCIR: null,
        tocdoNIX_options: [],
        tocdoNIX: null,
        tocdoPIR_options: [],
        tocdoPIR: null,
        tocdoISP_options: [],
        tocdoISP: null,
      },
      list_TTPE:{
        router_options: [],
        router: null,
        tenthietbi_options: [],
        tenthietbi: null,
        loaicap_options: [],
        loaicap: null,

      },
      params: {
       p_kieuHD:null,
       p_kieuHD_options:[],
       p_kenhThu_options:[],
       p_kenhThu: null,
       rdoPhieuMoi: 0,
       huonggiao_id: null,
      },
      ischkDoiTac:false,
      isTocDoNIX:false,
      isTocDoPIR:false,
      isTocDoISP:false,
      isngayGV:false,
      isngayHT: false,
      ds_NVThucHien_table:[],
      ds_HDThuebao_table: [],
    };
  },
  created() {
   this.loadKieuHD();
   this.loadKenhThu();
   this.loadLoaiHDPL();
   this.loadNguoiGiaoViec();
   this.loadLoaiKenh();
   this.loadModem();
   this.loadTrangBi();
   this.loadLoaiNode();
   this.loadThietBi();
   this.loadChuQuan();
   this.loadDoiTac();
   this.loadThoiHan();
   this.getDSNhanVienTH();
   this.loadTocDo(0);
   this.loadRouter();
   this.loadThietBiDC();
   this.loadLoaiCap();
  },
  computed: {
    chkTocDoNIX: {
      get() {
        return this.isTocDoNIX;
      },
        set(value) {
        this.isTocDoNIX = value;
        if(value==true){
           this.loadTocDo(1);//nix
        }else{
          this.list_TTHoanCong.tocdoNIX_options = [];
          this.list_TTHoanCong.tocdoNIX = null;
        }
      },
    },
    chkDTocDoPIR: {
      get() {
        return this.isTocDoPIR;
      },
        set(value) {
        this.isTocDoPIR = value;
         if(value==true){
           this.loadTocDo(2);//pir
        }else{
          this.list_TTHoanCong.tocdoPIR_options = [];
          this.list_TTHoanCong.tocdoPIR = null;
        }
      },
    },
    chkTocDoISP: {
      get() {
        return this.isTocDoISP;
      },
        set(value) {
        this.isTocDoISP = value;
         if(value==true){
            this.loadTocDo(3); //isp
        }else{
          this.list_TTHoanCong.tocdoISP_options = [];
          this.list_TTHoanCong.tocdoISP = null;
        }
      },
    },
    chkDoiTac: {
      get() {
        return this.ischkDoiTac;
      },
        set(value) {
        this.ischkDoiTac = value;
      },
    },
    ngaygv:{
        get() {
        return this.isngayGV;
      },
        set(value) {
        this.isngayGV = value;
      },
    },
    ngayht:{
       get() {
        return this.isngayHT;
      },
        set(value) {
        this.isngayHT = value;
      },
    }
  },
  methods: {
    async loadKieuHD() {
      API.getKieuHD(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.kieuhd_id, text: item.kieu_hd });
              });
              this.params.p_kieuHD_options = items;
              this.params.p_kieuHD = items[0].id;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },
    async loadKenhThu() {
      let apiParams={
         sql: "select * from css.kenhthu"
       };
      this.loading(true);
      API.getKenhThu(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.kenhthu_id, text: item.kenhthu });
              });
               this.params.p_kenhThu_options = items;
              this.params.p_kenhThu = items[0].id;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    async tongTien(hdkhid){
         API.getTongTien(this.axios, {hdkh_id: hdkhid})
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
           
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },
    //#region  Thông tin khách hàng
   async loadLoaiHDPL() {
      API.getLoaiHDPL(this.axios, [1,3,6])
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.ID, text: item.NAME });
              });
               this.list_TTKH.loaihdpl_options = items;
              this.list_TTKH.loaihdpl = items[0].id;
              this.loadQuyTrinh(items[0].id);
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },
    async loadQuyTrinh(loaihd) {
      let apiParams={
        vloaihd_id: loaihd,
        vtthd_id: 1, //tthd,
        vkieu_yc: 0,  //kieuyc,
        vdichvuvt_id: 8,  //dvvt
      };
      API.getQuyTrinh(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.quytrinh_id, text: item.quytrinh });
              });
               this.list_TTKH.quytrinh_options = items;
               this.list_TTKH.quytrinh = items[0].id;
               this.DSHuongGiaoTheoQuyTrinh(items[0].id, 1)
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    }, 
    showPopupSearchContract(){   
      this.$refs.popupSearchContract.showModal();
    },
    acceptSearchContract(item){
      this.list_TTKH.magiaodich = item.ma_gd;
      if(this.list_TTKH.magiaodich && this.list_TTKH.magiaodich.trim()!="")
        this.MaGDChanged();
    },
    MaGDChanged() {
      if( this.list_TTKH.magiaodich.length ==0 || this.list_TTKH.magiaodich == "" ){
        this.$alert("Không tìm thấy thuê bao", "Thông báo", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
      }else{
        this.HienThiDanhSachHDTB(0, this.params.huonggiao_id, 0,this.params.rdoPhieuMoi, this.list_TTHoanCong.loaihdpl, list_TTKH.magiaodich.trim(), 0, 1 )
        // caidat,huonggiao, kieuid, loaiid, loaidhdid, magd, phieuid, tthd
      }
      
    },
//#endregion
//#region Thông tin hoàn công
    async layThongSoThiCong(matb, madoicap,kieu) {
      let apiParams={
        vma_tb: "3636706", //matb,
        vmadoicap: 118346,  //madoicap,
        vkieu: 123  //kieu
      };
      API.getThongSoThiCong(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
                       
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    }, 
    async loadNguoiGiaoViec(donvi) {
      let apiParams={
        p_donvi_id: 0, //donvi
      };
      API.getNhanVienTH(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.nhanvien_id, text: item.ten_nv });
              });
               this.list_TTHoanCong.nguoigiaoviec_options = items;
               this.list_TTHoanCong.nguoigiaoviec = 235622; //nhân viên thao tác
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },
    async loadThoiHan() {
      let apiParams={
        nghiepvu: "ds_thoihan",
        ds_para: ""
      };
      API.getThoiHan(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.thoihan_id, text: item.tenthoihan });
              });
               this.list_TTHoanCong.thoihan_options = items;
               this.list_TTHoanCong.thoihan = items[0].id;
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },
    async loadLoaiKenh() {
      let apiParams={
        schema: "css",
        list_of_cols: "loaikenh_id, loai_kenh",
        order:  "loaikenh_id",
        table_name: "loai_kenh",
        where: ""
      };
      API.getLoaiKenh(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.loaikenh_id, text: item.loai_kenh });
              });
               this.list_TTHoanCong.loaikenh_options = items;
               this.list_TTHoanCong.loaikenh = items[0].id;
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },
    async loadModem() {
      let apiParams={
        schema: "css",
        list_of_cols:  "loaimd_id, loai_md",
        order:  "loaimd_id",
        table_name: "loai_modem",
        where: ""
      };
      API.getLoaiModem(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.loaimd_id, text: item.loai_md });
              });
              this.list_TTHoanCong.loaimodem_options = items;
              this.list_TTHoanCong.loaimodem = items[0].id;
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },    
    async loadTrangBi() {
      let apiParams={
        schema: "css",
        list_of_cols:  "trangbi_id,tentrangbi",
        order:  "trangbi_id",
        table_name: "trangbi",
        where: ""
      };
      API.getTrangBi(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.trangbi_id, text: item.tentrangbi });
              });
              this.list_TTHoanCong.trangbi_options = items;
              this.list_TTHoanCong.trangbi = items[0].id;
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },   
    async loadLoaiNode() {
      API.getLoaiNodeMGW(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.node_id, text: item.loai_node });
              });
              this.list_TTHoanCong.loainode_options = items;
              this.list_TTHoanCong.loainode = items[0].id;
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },
    async loadThietBi() {
      let apiParams={
        nghiepvu: "ds_loai_tb_mgwan",
        ds_para: ""
      };
      API.getLoaiTbiMGW(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.thietbidc_id, text: item.ten_tbi });
              });
              this.list_TTHoanCong.loaitbi_options = items;
              this.list_TTHoanCong.loaitbi = items[0].id;
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    }, 
    async loadChuQuan() {
      let apiParams={
        schema: "css",
        list_of_cols: "chuquan_id,tenchuquan ",
        order:  "chuquan_id",
        table_name: "chuquan",
        where: ""
      };
      API.getChuQuan(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.chuquan_id, text: item.tenchuquan });
              });
              this.list_TTHoanCong.chuquan_options = items;
              this.list_TTHoanCong.chuquan = items[0].id;
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },
    async loadDoiTac() {
      let apiParams={
        list_of_cols: "doitac_id,ten_dt",
        order: "ten_dt",
        schema: "css",
        table_name: "doitac",
        where: ""
      };
      API.getDoiTac(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.doitac_id, text: item.ten_dt });
              });
              this.list_TTHoanCong.doitac_options = items;
              this.list_TTHoanCong.doitac = items[0].id;
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    }, 
    async loadTocDo(loaitocdo) {
      let apiParams={
        list_of_cols: "tocdo_id,tocdo||' '||donvi toc_do",
        order: "tocdo",
        schema: "css",
        table_name: "tocdo_kenh",
        where: ""
      };
      API.getTocDo(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.tocdo_id, text: item.toc_do });
              });
              if(loaitocdo ==0){
                this.list_TTHoanCong.tocdoCIR_options = items;
                this.list_TTHoanCong.tocdoCIR = items[0].id;
              }
               if(loaitocdo ==1){
                this.list_TTHoanCong.tocdoNIX_options = items;
                this.list_TTHoanCong.tocdoNIX = items[0].id;
              }
               if(loaitocdo ==2){
                this.list_TTHoanCong.tocdoPIR_options = items;
                this.list_TTHoanCong.tocdoPIR = items[0].id;
              }
              if(loaitocdo ==3){
                this.list_TTHoanCong.tocdoISP_options = items;
                this.list_TTHoanCong.tocdoISP = items[0].id;
              }
              
              
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },
    async loadRouter() {
      let apiParams={
        list_of_cols: "pe_id,pe",
        order: "pe_id",
        schema: "css",
        table_name: "pe",
        where: ""
      };
      API.getRouterPE(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.pe_id, text: item.pe });
              });
              this.list_TTPE.router_options = items;
              this.list_TTPE.router = items[0].id;
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    }, 
    async loadThietBiDC() {
      let apiParams={
        nghiepvu: "DS_THIETBI_DAUCUOI",
        ds_para: ""
      };
      API.getTenTbi(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.thietbidc_id, text: item.ten_tbi });
              });
              this.list_TTPE.tenthietbi_options = items;
              this.list_TTPE.tenthietbi = items[0].id;
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    }, 
    async loadLoaiCap() {
      API.getLoaiCap(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.loaicap_id, text: item.loaicap });
              });
              this.list_TTPE.loaicap_options = items;
              this.list_TTPE.loaicap = items[0].id;
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },       
    async DSHuongGiaoTheoQuyTrinh(quytrinhid, tthdid) {
      let apiParams = {
        quytrinh_id: quytrinhid,
        tthd_id: tthdid
      };
      API.getDSHuongGiaoTheoQuyTrinh(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            if (response.data.data !== undefined) {
              this.params.huonggiao_id = response.data.data[0].huonggiao_id;
              this.$toast.error(response.data.data[0].huonggiao_id +" - "+response.data.data[0].huonggiao)
            }      
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },       

//#endregion

//#region danh sách nhân viên thực hiện
  async getDSNhanVienTH(phieuid, kieuid) {
   this.ds_NVThucHien_table=[];
      let apiParams={
        phieu_id: 10850393, // phieuid,
        kieu_id: 2 // kieuid
      };
      API.getDSNhanVienTH(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.ds_NVThucHien_table=response.data.data; 
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
  },
  //#endregion

  //#region danh sách thuê bao
  async HienThiDanhSachHDTB(caidat,huonggiao, kieuid, loaiid, loaidhdid, magd, phieuid, tthd) {
      this.ds_HDThuebao_table=[];
      let apiParams={
        vcaidat_dv:  caidat,
        vhuonggiao_id:  huonggiao,
        vkieu_id:  kieuid,
        vloai_id: loaiid, //0: mới, 1:phiếu trả
        vloaihd_id: loaidhdid, 
        vma_gd:  magd,
        vphieu_id:  phieuid,
        vtthd_id: tthd
      };
      API.getDSHDThueBao(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.ds_HDThuebao_table = response.data.data; 
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    }, 
   async getPhieuTra(phieuid) {
      this.ds_HDThuebao_table=[];
      let apiParams={
        phieu_id: 303787, // phieuid,
      };
      API.getPhieuTra(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.ds_HDThuebao_table = response.data.data; 
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },    
    async rdioCKb_EditValueChanged(){
      
       this.$toast.error(this.params.rdoPhieuMoi+"")
      
    }, 
//#endregion
    onChangeKieuHD(data){
      
    },
    onChangeLoaiHDPL(data){
      this.loadQuyTrinh(this.list_TTKH.loaihdpl);
      this.istitle = data.toString();
      this.$toast.error(data)
    },
    onChangeQuyTrinh(data){
      this.DSHuongGiaoTheoQuyTrinh(data, 1);
    },
    clickLayTT(){
      this.$toast.error(this.list_TTHoanCong.tocdoNIX+"2")
    },
    clickCapNhat(){
      this.$toast.error(this.list_TTHoanCong.ngaygiaoviec)
    },
    clickHuongKN(){
      this.$refs.popuphuongkn.showModal();
    },
    btnClickHuongKN(){
      this.$refs.popuphuongkn.showModal();
    },
    onHuongKN(value) {

    },
    clickKetNoi(){
      this.$refs.popuchitietkn.showModal();
    },
    onChiTietKN(){

    },
  },
};
</script>