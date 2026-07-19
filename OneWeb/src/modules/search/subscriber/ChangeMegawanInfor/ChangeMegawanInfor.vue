<template src="./ChangeMegawanInfor.html"></template>
<style scoped src="./ChangeMegawanInfor.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import Vue from "vue";
import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns";
import KTable from "@/components/KTable.vue";
import ActionTop from "@/components/ActionTop.vue";
//import SearchContractModal from '../../../contract/setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import searchAccount from '@/modules/search/subscriber/SearchAccount'
//import SearchContract from '@/modules/search/subscriber/SearchContract'
import moment from 'moment'
import ChonPort from "@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/chon-port"
import {TRANGTHAI_PORT} from './ThamSo'

Vue.use(DropDownListPlugin);  
export default {
  components: { breadcrumb, KTable, ActionTop, SearchContractModal,searchAccount, ChonPort  },
  data: function () {
    return {
      header: {
        title: 'Thay đổi thông số kỹ thuật Megawan ',
        list: [
          { 
            name: 'Quản lý Db ', 
            link: { name: 'Ui.cards' } 
          },
          {
            name: 'Thay đổi thông số kỹ thuật',
            link: { name: 'Ui.buttons' }
          },
          {
            name: 'Thay đổi thông số kỹ thuật Megawan/Metronet',
            link: { name: 'Ui.buttons' }
          },
        ]
      },
       params: {
        // MaTB: null,
        p_divhvuvt_option: [],
        p_divhvuvt: "8",
        p_loaihinhtb_option: [],
        loaihinhtb: null,

        p_router_cu_option: [],
        p_router_cu: null,
        p_router_moi_option: [],
        p_router_moi: null,

        p_cboLoaiKenh_cu: null,
        p_LoaiKenh_option_cu: [],
        p_cboLoaiKenh_moi: null,
        p_LoaiKenh_option_moi: [],

        
        p_nodechinh_moi: null,
        p_nodechinh_option_moi: [],
        p_nodechinh_cu: null,
        p_nodechinh_option_cu: [],

        
        p_loaitbi_cu: null,
        p_loaitbi_option_cu: [],
        p_loaitbi_moi: null,
        p_loaitbi_option_moi: [],
        
        p_loaitcap_cu: null,
        p_loaitcap_option_cu: [],
        p_loaitcap_moi: null,
        p_loaitcap_option_moi: [],

        TrangThaiID:1,
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        PopUpCongTacVien_Thoat: this.popupClosed,
        PopUpNguoiGioiThieu_Thoat: this.popupClosed
      },
      popupComponentName: '',
       ds_lichsuthaydoi_table: [],
       txtMaTB: '',
       Tinh_KN_ID:0,
       txtThueBaoID: 0,
       txtThueBao_cha_id:0,
       txtthuebaoCha_id_moi:0,
       txtSoAo:"",
       txtTrangThaiTB:"",
       txtTrangThaiTB_ID:0,
       txtSomayTN:"",
       txtTenThueBao:"",
       txtDiaChiLD:"",
       vkieu:"",
       tinhkh_id:"",
       db_hd:"",
       khachhang_id:"",
       TrangThai_ID:0,
       ThongTinCu:{
            txtSlot_Cu:"",
            txtportFE_cu:"",
            txtTramTB_cu:"",
            txtSiteID_cu:"",
            txtSoPLHD_cu:"",
            txtHuongKN_cu:"",
            txtdslam_Cu:"",
            txtADSL_Cu:"",
            txtPortMDF_cu:"",
            txtNasportID_cu:"",
            txtSvlan_cu:"",
            ttxWanID_cu:"",
            txtCVlan_Cu:"",
            txtLanIP_Cu:"",
            txtVrf_cu:"",
            txtLink_cu:"",
            txtRoute_cu:"",
            txtTenTbi_Cu:"",
            txtSerial_cu:"",
            txtLientu_Cu:"",
            txtTucapgoc_cu:"",
            txtTucapngon_cu:"",
            txthopcapngon_cu:"",
            txtdoicaogoc_cu:"",
            txtdoicapngon_cu:"",
            txttuyencap_cu:"",
            txtCapgoc_cu:"",
            txtcapngon_cu:"",
            txtculy_cu:""
       },
        ThongTinMoi:{
            txtSlot_moi:"",
            txtportFE_moi:"",
            txtTramTB_moi:"",
            txtSiteID_moi:"",
            txtSoPLHD_moi:"",
            txtHuongKN_moi:"",
            txtdslam_moi:"",
            txtADSL_moi:"",
            txtPortMDF_moi:"",
            txtNasportID_moi:"",
            txtSvlan_moi:"",
            ttxWanID_moi:"",
            txtCVlan_moi:"",
            txtLanIP_moi:"",
            txtVrf_moi:"",
            txtLink_moi:"",
            txtRoute_moi:"",
            txtTenTbi_moi:"",
            txtSerial_moi:"",
            txtLientu_moi:"",
            txtTucapgoc_moi:"",
            txtTucapngon_moi:"",
            txthopcapngon_moi:"",
            txtdoicaogoc_moi:"",
            txtdoicapngon_moi:"",
            txttuyencap_moi:"",
            txtCapgoc_moi:"",
            txtcapngon_moi:"",
            txtculy_moi:""
       },

       dichvuvt_id: 1,
       position: { X: 'center', Y: 'top' },
       ngayYC: moment(new Date()).format('DD/MM/YYYY'),
       txtHuongKn: '',
       DichVuVienThong: {
        METRONET: 7,
        MEGAWAN: 8,
      },

      tramtb_id: 0,
      port_cu_id: 0,
      vci_vpi_cu_id: 0,
      port_moi_id: 0,
      vci_vpi_moi_id: 0,
      vhuong_kn:'',
      vhuong_kn_cu:'',
      tinh_kn_id: 0,
      tinh_kn_id_cu: 0,
      hdtb_id: 0,
      hdtb_cha_id: 0,
      hdtb_cha_id_cu: 0,
      vdiachi_kn:'',
      vloaihd_id:0,
      hdkh_id: 0,
      dichvuvt_id: 0,
      TrangThaiHD: {
        HOAN_THANH: 6,
        THOAITRA: 7
      },
      LoaiHopDong: {
        DAT_MOI: 1,
        DI_CHUYEN: 3,
        CHUYENDOI_LH: 6,
        KHOIPHUC_SD: 30,
        THAYDOI_THONGSO_MEGAWAN:5
      },
      vloaitb_id:0,
      txtSiteID_Cu_ReadOnly:false,
      txtSiteID_Moi_ReadOnly:false,

    };
  },
  validations: {},
  computed: {
  },
  watch: {},
  created() {
       this.Tinh_KN_ID= this.$root.token.getPhanVungID(),
      this.load_DS_DichVu_VT();
      this.load_DS_LoaiHinh_TB();
      this.load_DS_Router_Cu();
      this.load_DS_Router_Moi();
      this.load_DS_LoaiKenh_cu();
      this.load_DS_LoaiKenh_moi();
      this.load_loai_node();
      this.load_DS_LoaiThietbi_cu();
      this.load_loai_cap_cu_moi();
  },
  async mounted() {},
  methods: {
       async load_DS_DichVu_VT() {
           let res = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                          "p_id": "TDTSKT_MEGAWAN_DICHVUVT",
                          "p_para1": null,
                          "p_para2": null,
                          "p_para3": null,
                          "p_para4": null,
                          "p_para5": null,
             })
              if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                    let items = [];
                    if (res.data.data !== undefined) {
                        res.data.data.forEach(function (item) {
                            items.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT });
                    });
                        this.params.p_divhvuvt_option = items;
                        this.params.p_divhvuvt = items[0].id;
                    }
              }
    },
    onChangeDichVuVT(agrs){
      this.params.p_divhvuvt=agrs.id;
      this.load_DS_LoaiHinh_TB();
    },
    async load_DS_LoaiHinh_TB() {
            let res = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                          "p_id": "TDTSKT_MEGAWAN_LOAIHINH_TB",
                          "p_para1": this.params.p_divhvuvt,
                          "p_para2": null,
                          "p_para3": null,
                          "p_para4": null,
                          "p_para5": null,
             })
              if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                    let items = [];
                    if (res.data.data !== undefined) {
                        res.data.data.forEach(function (item) {
                            items.push({ id: item.LOAITB_ID, text: item.LOAIHINH_TB });
                    });
                        this.params.p_loaihinhtb_option = items;
                        this.params.p_loaihinhtb = items[0].id;
                    }
              }
    },
    async load_DS_Router_Cu() {
         let res = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                          "p_id": "TDTSKT_MEGAWAN_PE",
                          "p_para1": null,
                          "p_para2": null,
                          "p_para3": null,
                          "p_para4": null,
                          "p_para5": null,
             })
              if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                    let items = [];
                    if (res.data.data !== undefined) {
                        res.data.data.forEach(function (item) {
                            items.push({ id: item.PE_ID, text: item.PE });
                    });
                        this.params.p_router_cu_option = items;
                        this.params.p_router_cu = items[0].id;
                    }
              }
       
    },
    async load_DS_Router_Moi() {
         let res = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                          "p_id": "TDTSKT_MEGAWAN_PE",
                          "p_para1": null,
                          "p_para2": null,
                          "p_para3": null,
                          "p_para4": null,
                          "p_para5": null,
             })
              if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                    let items = [];
                    if (res.data.data !== undefined) {
                        res.data.data.forEach(function (item) {
                            items.push({ id: item.PE_ID, text: item.PE });
                    });
                        this.params.p_router_moi_option = items;
                        this.params.p_router_moi = items[0].id;
                    }
              }
       
    },
    async load_DS_LoaiKenh_cu()  {
        let res = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                          "p_id": "TDTSKT_MEGAWAN_LOAIKENH",
                          "p_para1": null,
                          "p_para2": null,
                          "p_para3": null,
                          "p_para4": null,
                          "p_para5": null,
             })
              if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                    let items = [];
                    if (res.data.data !== undefined) {
                        res.data.data.forEach(function (item) {
                            items.push({ id: item.LOAIKENH_ID, text: item.LOAI_KENH });
                    });
                        this.params.p_LoaiKenh_option_cu = items;
                        this.params.p_cboLoaiKenh_cu = items[0].id;
                    }
              }
       
       
      
    }, 
    async load_DS_LoaiKenh_moi()  {
        let res = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                                  "p_id": "TDTSKT_MEGAWAN_LOAIKENH",
                                  "p_para1": null,
                                  "p_para2": null,
                                  "p_para3": null,
                                  "p_para4": null,
                                  "p_para5": null,
                    })
                      if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                            let items = [];
                            if (res.data.data !== undefined) {
                                res.data.data.forEach(function (item) {
                                    items.push({ id: item.LOAIKENH_ID, text: item.LOAI_KENH });
                            });
                                this.params.p_LoaiKenh_option_moi = items;
                                this.params.p_cboLoaiKenh_moi = items[0].id;
                            }
                      }

      
    },
     async load_loai_node() {
           let res = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                          "p_id": "TDTSKT_MEGAWAN_NODE",
                          "p_para1": null,
                          "p_para2": null,
                          "p_para3": null,
                          "p_para4": null,
                          "p_para5": null,
             })
              if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                    let items = [];
                    if (res.data.data !== undefined) {
                        res.data.data.forEach(function (item) {
                            items.push({ id: item.LOAINODE_ID, text: item.LOAINODE });
                    });
                       this.params.p_nodechinh_option_moi = items;
                      this.params.p_nodechinh_moi = items[0].id;
                      this.params.p_nodechinh_option_cu = items;
                      this.params.p_nodechinh_cu = items[0].id;
                    }
              }
    },
    async load_loai_cap_cu_moi() {
            let res = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                          "p_id": "TDTSKT_MEGAWAN_LOAICAP",
                          "p_para1": null,
                          "p_para2": null,
                          "p_para3": null,
                          "p_para4": null,
                          "p_para5": null,
             })
              if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                    let items = [];
                    if (res.data.data !== undefined) {
                        res.data.data.forEach(function (item) {
                            items.push({ id: item.LOAICAP_ID, text: item.LOAICAP });
                    });
                        this.params.p_loaitcap_option_cu = items;
                        this.params.p_loaitcap_cu = items[0].id;

                        this.params.p_loaitcap_option_moi = items;
                        this.params.p_loaitcap_moi = items[0].id;

                    }
              }
    },
    async load_DS_LoaiThietbi_cu()  {
            let res = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                                      "p_id": "TDTSKT_MEGAWAN_LOAITBI",
                                      "p_para1": this.params.p_divhvuvt,
                                      "p_para2": null,
                                      "p_para3": null,
                                      "p_para4": null,
                                      "p_para5": null,
                        })
                          if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                                let items = [];
                                if (res.data.data !== undefined) {
                                    res.data.data.forEach(function (item) {
                                        items.push({ id: item.THIETBIDC_ID, text: item.TEN_TBI });
                                });
                                    this.params.p_loaitbi_option_cu = items;
                                    this.params.p_loaitbi_cu = items[0].id;

                                    
                                    this.params.p_loaitbi_option_moi = items;
                                    this.params.p_loaitbi_moi = items[0].id;
                                }
                          }
    },
    
    SearchDanhBa: function () {
      if(this.params.TrangThaiID==1){
        this.$refs.searchAccountDialog.show()
      }else
      {
        this.$refs.popupFrmTraCuuHopDong.show()
      }
      
    },
    Clear(){
      this.port_cu_id= 0,
      this.vci_vpi_cu_id= 0,
      this.port_moi_id= 0,
      this.vci_vpi_moi_id= 0

      this.Tinh_KN_ID=0
      this.txtThueBaoID= 0
      this.txtThueBao_cha_id=0
      this.txtthuebaoCha_id_moi=0
      this.txtSoAo=""
      this.txtTrangThaiTB=""
      this.txtTrangThaiTB_ID=0
      this.txtSomayTN=""
      this.txtTenThueBao=""
      this.txtDiaChiLD=""
      this.vkieu=""
      this.tinhkh_id=""
      this. db_hd=""
       this.khachhang_id=""
       this.ThongTinCu={
            txtSlot_Cu:"",
            txtportFE_cu:"",
            txtTramTB_cu:"",
            txtSiteID_cu:"",
            txtSoPLHD_cu:"",
            txtHuongKN_cu:"",
            txtdslam_Cu:"",
            txtADSL_Cu:"",
            txtPortMDF_cu:"",
            txtNasportID_cu:"",
            txtSvlan_cu:"",
            ttxWanID_cu:"",
            txtCVlan_Cu:"",
            txtLanIP_Cu:"",
            txtVrf_cu:"",
            txtLink_cu:"",
            txtRoute_cu:"",
            txtTenTbi_Cu:"",
            txtSerial_cu:"",
            txtLientu_Cu:"",
            txtTucapgoc_cu:"",
            txtTucapngon_cu:"",
            txthopcapngon_cu:"",
            txtdoicaogoc_cu:"",
            txtdoicapngon_cu:"",
            txttuyencap_cu:"",
            txtCapgoc_cu:"",
            txtcapngon_cu:"",
            txtculy_cu:""
       },
      this.ThongTinMoi={
            txtSlot_moi:"",
            txtportFE_moi:"",
            txtTramTB_moi:"",
            txtSiteID_moi:"",
            txtSoPLHD_moi:"",
            txtHuongKN_moi:"",
            txtdslam_moi:"",
            txtADSL_moi:"",
            txtPortMDF_moi:"",
            txtNasportID_moi:"",
            txtSvlan_moi:"",
            ttxWanID_moi:"",
            txtCVlan_moi:"",
            txtLanIP_moi:"",
            txtVrf_moi:"",
            txtLink_moi:"",
            txtRoute_moi:"",
            txtTenTbi_moi:"",
            txtSerial_moi:"",
            txtLientu_moi:"",
            txtTucapgoc_moi:"",
            txtTucapngon_moi:"",
            txthopcapngon_moi:"",
            txtdoicaogoc_moi:"",
            txtdoicapngon_moi:"",
            txttuyencap_moi:"",
            txtCapgoc_moi:"",
            txtcapngon_moi:"",
            txtculy_moi:""
       }
      this.vhuong_kn=''
      this.vhuong_kn_cu=''
      this.tinh_kn_id=0
      this.tinh_kn_id_cu=0
      this.hdtb_id= 0,
      this.hdtb_cha_id=0,
      this.hdtb_cha_id_cu=0
      this.vdiachi_kn=''
      this.vloaihd_id=0
      this.hdkh_id = 0
      this.dichvuvt_id = 0
      this.vloaitb_id=0
    },

    async HienThiThongTin() {
      //Load theo danh bạ
      if(this.params.TrangThaiID==1){
        let ds=await this.lay_danhba_theo_matb({
          in_ma_tb: this.txtMaTB.trim(),//"MW001041336",
          in_dichvuvt_id: this.params.p_divhvuvt,//8,
          in_donvi_dl_id: 0
        })
        if(ds.length>0){
          this.txtSoAo ="";
          this.txtTrangThaiTB=ds[0].trangthai_tb;
          this.txtSomayTN="";
          this.txtTenThueBao=ds[0].ten_tb;
          this.txtDiaChiLD=ds[0].diachi_ld;
          this.ThongTinCu.txtSoPLHD_cu=ds[0].ma_hd;
          this.ThongTinCu.txtLink_cu="";
          this.txtThueBaoID=ds[0].thuebao_id;
          this.txtThueBao_cha_id= ds[0].thuebao_cha_id;
          this.params.p_loaitbi_cu =ds[0].loaitb_id;
          this.txtTrangThaiTB_ID=ds[0].trangthaitb_id;
          this.params.p_loaihinhtb=ds[0].loaitb_id
          await this.lay_ds_db_megawan_theo_thuebao_id();
        }else{
          this.$toast.error("Không có dữ liệu")
        }
      }else{
        //Trường hợp cho hợp đồng
        let ds=await this.lay_ds_hopdong_theo_matb({
          maTb:this.txtMaTB.trim(),
          loaiHdId:0,
          ttHdId:0,
          donViDlId:0,
          dichVuVtId:this.params.p_divhvuvt,
          loaiTbId:0
        })
        console.log('lay_ds_hopdong_theo_matb ds', ds)
        if(ds.length>0){
          let item=ds[0]
            if(item.tthd_id!=this.TrangThaiHD.HOAN_THANH&&item.tthd_id!=this.TrangThaiHD.THOAITRA){
            this.vloaihd_id=item.loaihd_id?item.loaihd_id:0

            if(this.vloaihd_id == this.LoaiHopDong.DAT_MOI || this.vloaihd_id == this.LoaiHopDong.DI_CHUYEN ||
              this.vloaihd_id == this.LoaiHopDong.CHUYENDOI_LH || this.vloaihd_id == this.LoaiHopDong.KHOIPHUC_SD ||
              this.vloaihd_id == this.LoaiHopDong.THAYDOI_THONGSO_MEGAWAN){
                this.txtMaTB=item.ma_tb?item.ma_tb.toString():''
                this.txtTenThueBao=item.ten_tb?item.ten_tb.toString():''
                this.txtDiaChiLD=item.diachi_ld?item.diachi_ld.toString():''
                this.hdtb_id=item.hdtb_id?item.hdtb_id:0
                this.hdkh_id=item.hdkh_id?item.hdkh_id:0
                this.dichvuvt_id=item.dichvuvt_id?item.dichvuvt_id:0
                this.params.p_divhvuvt=this.dichvuvt_id
                this.vloaitb_id=item.loaitb_id?item.loaitb_id:0
                this.params.p_loaihinhtb=this.vloaitb_id

                this.txtTrangThaiTB=item.trangthai_hd?item.trangthai_hd:''+'. Mã GD : '+item.ma_gd?item.ma_gd:''
                if(item.hdtb_cha_id&&item.hdtb_cha_id!=''||item.thuebao_cha_id&&item.thuebao_cha_id!=''){
                  if(item.hdtb_cha_id&&item.hdtb_cha_id!=''){
                    this.hdtb_cha_id=item.hdtb_cha_id?item.hdtb_cha_id:0
                    this.hdtb_cha_id_cu=item.hdtb_cha_id?item.hdtb_cha_id:0
                    let huong_kn=await this.fn_lay_text_huongketnoi1({
                      vid: this.hdtb_cha_id, 
                      vloai:0
                    })
                    console.log('huong_kn', huong_kn)
                    this.ThongTinCu.txtHuongKN_cu=huong_kn?huong_kn.toString():''
                    this.ThongTinMoi.txtHuongKN_moi=huong_kn?huong_kn.toString():''
                  }
                  if(item.thuebao_cha_id&&item.thuebao_cha_id!=''){
                    this.thuebao_cha_id=item.thuebao_cha_id?item.thuebao_cha_id:0
                    this.thuebao_cha_id_cu=item.thuebao_cha_id?item.thuebao_cha_id:0
                    //         int loaisochinh = 1;
                //         txthuong_kn.Text = txtHuong_KN_Moi.Text = bangts.Lay_text_huongketnoi(thuebao_cha_id, loaisochinh);
                    let huong_kn=await this.fn_lay_text_huongketnoi1({
                      vid: this.thuebao_cha_id, 
                      vloai:1
                    })
                    console.log('huong_kn', huong_kn)
                    this.ThongTinCu.txtHuongKN_cu=huong_kn?huong_kn.toString():''
                    this.ThongTinMoi.txtHuongKN_moi=huong_kn?huong_kn.toString():''
                  }
                }else{
                  this.ThongTinCu.txtHuongKN_cu=''
                  this.ThongTinMoi.txtHuongKN_moi=''
                }
                if(this.vloaihd_id == this.LoaiHopDong.DAT_MOI || this.vloaihd_id == this.LoaiHopDong.DI_CHUYEN ){
                  this.txtSiteID_Cu_ReadOnly = false
                  this.txtSiteID_Moi_ReadOnly = false
                }else{
                  this.txtSiteID_Cu_ReadOnly = true
                  this.txtSiteID_Moi_ReadOnly = true
                }
                //
                let ds_mgwan=await this.lay_ds_hopdong_thuebao_mgwan(this.hdtb_id)
                this.port_cu_id = 0
                this.vci_vpi_cu_id = 0
                this.port_moi_id = 0
                this.vci_vpi_moi_id = 0
                console.log('ds_mgwan', ds_mgwan)
                if(ds_mgwan.length>0){
                  this.params.p_cboLoaiKenh_cu=ds_mgwan[0].loaikenh_id
                  this.params.p_cboLoaiKenh_moi=ds_mgwan[0].loaikenh_id

                  this.params.p_nodechinh_moi=ds_mgwan[0].loainode_id
                  this.params.p_nodechinh_cu=ds_mgwan[0].loainode_id

                  this.ThongTinCu.txtSoPLHD_cu=ds_mgwan[0].ma_pl?ds_mgwan[0].ma_pl.toString():''
                  this.ThongTinMoi.txtSoPLHD_moi=ds_mgwan[0].ma_pl?ds_mgwan[0].ma_pl.toString():''

                  this.params.p_loaitbi_cu=this.params.p_loaitbi_moi=ds_mgwan[0].thietbidc_id
                  this.vhuong_kn=this.vhuong_kn_cu=ds_mgwan[0].huong_kn?ds_mgwan[0].huong_kn.toString():''

                  this.ThongTinCu.txtSiteID_cu=this.ThongTinMoi.txtSiteID_moi=ds_mgwan[0].site_id?ds_mgwan[0].site_id.toString():''

                  this.ThongTinMoi.txttuyencap_moi=this.ThongTinCu.txttuyencap_cu=ds_mgwan[0].tuyencap?ds_mgwan[0].tuyencap.toString():''
                  this.ThongTinCu.txtLientu_Cu=this.ThongTinMoi.txtLientu_moi=ds_mgwan[0].lientu?ds_mgwan[0].lientu.toString():''
                  this.ThongTinCu.txtTucapngon_cu=this.ThongTinMoi.txtTucapngon_moi=ds_mgwan[0].tucap_p?ds_mgwan[0].tucap_p.toString():''

                  this.tinh_kn_id=this.tinh_kn_id_cu=ds_mgwan[0].tinh_kn_id?ds_mgwan[0].tinh_kn_id:0

                  this.ThongTinCu.txtdoicapngon_cu=this.ThongTinMoi.txtdoicapngon_moi=ds_mgwan[0].doicap_p?ds_mgwan[0].doicap_p.toString():''

                  this.ThongTinCu.txthopcapngon_cu=this.ThongTinMoi.txthopcapngon_moi=ds_mgwan[0].hopcap_p?ds_mgwan[0].hopcap_p.toString():''

                  this.params.p_loaitcap_moi=this.params.p_loaitcap_cu=ds_mgwan[0].loaicap_id

                  this.ThongTinCu.txtCapgoc_cu=this.ThongTinMoi.txtCapgoc_moi=ds_mgwan[0].cap_goc?ds_mgwan[0].cap_goc.toString():''
                  this.ThongTinCu.txtcapngon_cu=this.ThongTinMoi.txtcapngon_moi=ds_mgwan[0].cap_p?ds_mgwan[0].cap_p.toString():''
                  this.ThongTinCu.txtdoicaogoc_cu=this.ThongTinMoi.txtdoicaogoc_moi=ds_mgwan[0].doicap_goc?ds_mgwan[0].doicap_goc.toString():''

                  this.ThongTinCu.txtTucapgoc_cu=this.ThongTinMoi.txtTucapgoc_moi=ds_mgwan[0].tucap_goc?ds_mgwan[0].tucap_goc.toString():''

                  this.ThongTinCu.txtculy_cu=this.ThongTinMoi.txtculy_moi=ds_mgwan[0].culy?ds_mgwan[0].culy.toString():''

                  this.txtSomayTN=ds_mgwan[0].matb_tn?ds_mgwan[0].matb_tn.toString():''

                  this.params.p_router_cu=this.params.p_router_moi=ds_mgwan[0].pe_id?ds_mgwan[0].pe_id:0

                  this.ThongTinCu.txtportFE_cu=this.ThongTinMoi.txtportFE_moi=ds_mgwan[0].port_pe?ds_mgwan[0].port_pe.toString():''
                  this.ThongTinCu.txtSlot_Cu=this.ThongTinMoi.txtSlot_moi=ds_mgwan[0].slot_pe?ds_mgwan[0].slot_pe.toString():''

                  this.ThongTinCu.ttxWanID_cu=this.ThongTinMoi.ttxWanID_moi=ds_mgwan[0].wan_ip?ds_mgwan[0].wan_ip.toString():''
                  this.ThongTinCu.txtLanIP_Cu=this.ThongTinMoi.txtLanIP_moi=ds_mgwan[0].lan_ip?ds_mgwan[0].lan_ip.toString():''

                  //     string str = "";
                  //     str += " select c.loai_tbi ";
                  //     str += " from " + DatabaseConstants.DB2 + ".giaophieu_tbi a, ";
                  //     str += DatabaseConstants.DB2 + ".loai_tbi c,  ";
                  //     str += DatabaseConstants.DB2 + ".giaophieu f ";
                  //     str += " where a.loaitbi_id = c.loaitbi_id ";
                  //     str += "    and a.phieu_id = f.phieu_id ";
                  //     str += "    and f.hdtb_id = " + hdtb_id;
                  //     str += "    and a.seri is not null ";
                  //     DataSet ds_tbi = new DataSet();
                  //     ds_tbi = bangts.getDataFromSQL(str, "thietbi");
                  //     if (ds_tbi.Tables[0].Rows.Count > 0)
                  //         txtTenTbi.Text = txtTenTbi_Moi.Text = ds_tbi.Tables[0].Rows[0]["loai_tbi"].ToString();
                  //     else
                  //         txtTenTbi.Text = txtTenTbi_Moi.Text = "";

                  this.ThongTinCu.txtSvlan_cu=this.ThongTinMoi.txtSvlan_moi=ds_mgwan[0].svlan?ds_mgwan[0].svlan.toString():''
                  this.ThongTinCu.txtCVlan_Cu=this.ThongTinMoi.txtCVlan_moi=ds_mgwan[0].cvlan?ds_mgwan[0].cvlan.toString():''
                  this.ThongTinCu.txtVrf_cu=this.ThongTinMoi.txtVrf_moi=ds_mgwan[0].vrf?ds_mgwan[0].vrf.toString():''
                  this.ThongTinCu.txtSerial_cu=this.ThongTinMoi.txtSerial_moi=ds_mgwan[0].serial?ds_mgwan[0].serial.toString():''
                  this.ThongTinCu.txtLink_cu=this.ThongTinMoi.txtLink_moi=ds_mgwan[0].link?ds_mgwan[0].link.toString():''
                  this.ThongTinCu.txtRoute_cu=this.ThongTinMoi.txtRoute_moi=ds_mgwan[0].route?ds_mgwan[0].route.toString():''
                  
                  //     tramvt_id = Convert.ToInt32(checkdata.MAP_ID("donvi_id", DatabaseConstants.DB2 + ".dbtb_dv", "where thuebao_id = " + thuebao_id + " and loaidv_id = " + LOAI_DV.TRAM_VT));
                  if(ds_mgwan[0].port_id){
                    this.port_cu_id=this.port_moi_id=ds_mgwan[0].port_id?ds_mgwan[0].port_id:0
                    this.vci_vpi_cu_id=this.vci_vpi_moi_id=ds_mgwan[0].vci_vpi_id?ds_mgwan[0].vci_vpi_id:0
                    let dsip = await this.sp_lay_thongso_kt({
                      port_id: this.port_cu_id,
                      vci_vpi_id: this.vci_vpi_cu_id
                    })
                    this.tramtb_id = 0
                    if(dsip.length>0){
                      console.log('dsip', dsip)
                      this.ThongTinCu.txtdslam_Cu=this.ThongTinMoi.txtdslam_moi=dsip[0].tendslam?this.ThongTinMoi.txtdslam_moi=dsip[0].tendslam.toString():''
                      if(dsip[0].selfid01){
                        this.ThongTinCu.txtNasportID_cu=this.ThongTinMoi.txtNasportID_moi=dsip[0].system+'/'+dsip[0].rack+'/'+dsip[0].shelf+'/'+dsip[0].slot
                        +'/'+dsip[0].port+'/'+dsip[0].vpi+'/'+dsip[0].vci+'/'+dsip[0].selfid04+'/'+dsip[0].selfid03+'/'+dsip[0].selfid02+'/'+dsip[0].selfid01

                      }else{
                        this.ThongTinCu.txtNasportID_cu=this.ThongTinMoi.txtNasportID_moi=dsip[0].system+'/'+dsip[0].rack+'/'+dsip[0].shelf+'/'+dsip[0].slot
                        +'/'+dsip[0].port+'/'+dsip[0].vpi+'/'+dsip[0].vci
                      }
                      this.ThongTinCu.txtPortMDF_cu=this.ThongTinMoi.txtPortMDF_moi=dsip[0].port_mdf
                      this.ThongTinCu.txtADSL_Cu=this.ThongTinMoi.txtADSL_moi=dsip[0].adsl_port
                      if(dsip[0].donvi_id){
                        this.ThongTinCu.txtTramTB_cu=this.ThongTinMoi.txtTramTB_moi=await this.fn_tt_donvi({
                          param: dsip[0].donvi_id, 
                          type: 2 
                        })
                        this.tramtb_id=dsip[0].donvi_id
                      }else{
                        this.ThongTinCu.txtTramTB_cu=this.ThongTinMoi.txtTramTB_moi=''
                        this.tramtb_id=''
                      }
                    }else{
                      this.ThongTinCu.txtdslam_Cu=this.ThongTinMoi.txtdslam_moi=''
                      this.ThongTinCu.txtTramTB_cu=this.ThongTinMoi.txtTramTB_moi=''
                      this.ThongTinCu.txtNasportID_cu=this.ThongTinMoi.txtNasportID_moi=''
                      this.ThongTinCu.txtPortMDF_cu=this.ThongTinMoi.txtPortMDF_moi=''
                      this.ThongTinCu.txtADSL_Cu=this.ThongTinMoi.txtADSL_moi=''

                    }
                  }else{
                      this.port_cu_id= 0,
                      this.vci_vpi_cu_id= 0,
                      this.port_moi_id= 0,
                      this.vci_vpi_moi_id= 0
                      this.ThongTinCu.txtTramTB_cu=this.ThongTinMoi.txtTramTB_moi=""
                      this.ThongTinCu.txtNasportID_cu=this.ThongTinMoi.txtNasportID_moi=""
                      this.ThongTinCu.txtPortMDF_cu=this.ThongTinMoi.txtPortMDF_moi=""
                      this.ThongTinCu.txtADSL_Cu=this.ThongTinMoi.txtADSL_moi=""
                  }
                 
                  //     SetButton(2);

                }else{
                  this.$toast.error(`Không tìm thấy thông tin về thuê bao: ${this.txtMaTB.trim()} \nĐề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT !`)
                  this.Clear()
                  return
                }

                
              }else{
                this.$toast.error('Bạn chỉ được đổi thông số kỹ thuật của thuê bao với loại HĐ/PL : \nLắp đặt mới, Di chuyển, Chuyển đổi loại hình và Khôi phục thuê bao thanh lý !')
              }

          }
        }else{
          this.$toast.error(`Không tìm thấy thông tin hợp đồng của thuê bao : ${this.txtMaTB.trim()}`)
          this.Clear()
          return
        }

      }
    },
     async lay_ds_db_megawan_theo_thuebao_id()
    {
           try {
          this.Loading = true
           let res = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/lay_ds_db_megawan_theo_tbid_v2", {
                                      "in_thuebao_id":  this.txtThueBaoID //3079661
                        })
                if(res.data.error_code == "BSS-00000000" ) {
                    this.params.p_router_cu = this.params.p_router_moi=res.data.data[0].pe_id;
                    this.ThongTinCu.txtSlot_Cu = this.ThongTinMoi.txtSlot_moi=res.data.data[0].slot_pe;
                    this.ThongTinCu.txtportFE_cu = this.ThongTinMoi.txtportFE_moi=res.data.data[0].port_pe;
                    this.ThongTinCu.txtTramTB_cu = this.ThongTinMoi.txtTramTB_moi= res.data.data[0].tramtb_id;
                    this.params.p_cboLoaiKenh_cu = this.params.p_cboLoaiKenh_moi= res.data.data[0].loaikenh_id;
                    this.ThongTinCu.txtSiteID_cu = this.ThongTinMoi.txtSiteID_moi= res.data.data[0].site_id;
                    this.params.p_nodechinh_cu = this.params.p_nodechinh_moi= res.data.data[0].loainode_id;
                    this.ThongTinCu.txtHuongKN_cu = this.ThongTinMoi.txtHuongKN_moi= res.data.data[0].huong_kn;
                    this.ThongTinCu.txtdslam_Cu = this.ThongTinMoi.txtdslam_moi= res.data.data[0].dslam;
                    this.ThongTinCu.txtPortMDF_cu = this.ThongTinMoi.txtPortMDF_moi= res.data.data[0].port_id;
                    this.ThongTinCu.txtNasportID_cu = this.txtNasportID_moi= res.data.data[0].port_pe;
                    this.ThongTinCu.txtSvlan_cu = this.ThongTinMoi.txtSvlan_moi= res.data.data[0].svlan;
                    this.ThongTinCu.ttxWanID_cu = this.ThongTinMoi.ttxWanID_moi= res.data.data[0].wan_ip;
                    this.ThongTinCu.txtCVlan_Cu = this.ThongTinMoi.txtCVlan_moi= res.data.data[0].cvlan;
                    this.ThongTinCu.txtLanIP_Cu = this.ThongTinMoi.txtLanIP_moi= res.data.data[0].lan_ip;
                    this.ThongTinCu.txtVrf_cu = this.ThongTinMoi.txtVrf_moi= res.data.data[0].vrf;
                    this.ThongTinCu.txtLink_cu = this.ThongTinMoi.txtLink_moi= res.data.data[0].link;
                    this.ThongTinCu.txtRoute_cu = this.ThongTinMoi.txtRoute_moi= res.data.data[0].route;
                    this.ThongTinCu.txtTenTbi_Cu = this.ThongTinMoi.txtTenTbi_moi= res.data.data[0].ten_tbi;
                    this.ThongTinCu.txtSerial_cu = this.ThongTinMoi.txtSerial_moi= res.data.data[0].serial;
                    this.params.p_loaitcap_cu = this.params.p_loaitcap_moi= res.data.data[0].loaicap_id;
                    this.ThongTinCu.txtLientu_Cu = this.ThongTinMoi.txtLientu_moi= res.data.data[0].lientu;
                    this.ThongTinCu.txtTucapgoc_cu = this.ThongTinMoi.txtTucapgoc_moi= res.data.data[0].tucap_goc;
                    this.ThongTinCu.txtTucapngon_cu = this.ThongTinMoi.txtTucapngon_moi= res.data.data[0].tucap_p;
                    this.ThongTinCu.txthopcapngon_cu = this.ThongTinMoi.txthopcapngon_moi= res.data.data[0].hopcap_p;
                    this.ThongTinCu.txtdoicaogoc_cu = this.ThongTinMoi.txtdoicaogoc_moi= res.data.data[0].doicap_goc;
                    this.ThongTinCu.txtdoicapngon_cu = this.ThongTinMoi.txtdoicapngon_moi= res.data.data[0].doicap_p;
                    this.ThongTinCu.txttuyencap_cu = this.ThongTinMoi.txttuyencap_moi= res.data.data[0].tuyencap;
                    this.ThongTinCu.txtCapgoc_cu = this.ThongTinMoi.txtCapgoc_moi= res.data.data[0].cap_goc;
                    this.ThongTinCu.txtcapngon_cu = this.ThongTinMoi.txtcapngon_moi =res.data.data[0].cap_p;
                    this.ThongTinCu.txtculy_cu = this.ThongTinMoi.txtculy_moi= res.data.data[0].culy;

                    this.vhuong_kn=this.vhuong_kn_cu=res.data.data[0].huong_kn?res.data.data[0].huong_kn.toString():''
                    this.tinh_kn_id=this.tinh_kn_id_cu=res.data.data[0].tinh_kn_id?res.data.data[0].tinh_kn_id:0

                    //if(res.data.data[0].tinh_kn_id){
                    this.params.p_loaitbi_cu=this.params.p_loaitbi_moi=res.data.data[0].thietbidc_id
                    //}
                    //
                    if(res.data.data[0].port_id){
                      //sp_lay_thongso_kt
                      this.port_cu_id = this.port_moi_id = res.data.data[0].port_id
                      this.vci_vpi_cu_id = this.vci_vpi_moi_id = res.data.data[0].vci_vpi_id

                      let dsip = await this.sp_lay_thongso_kt({
                        port_id: this.port_cu_id,
                        vci_vpi_id: this.vci_vpi_cu_id
                      })
                      this.tramtb_id = 0
                      if(dsip.length>0){
                        console.log('dsip', dsip)
                        this.ThongTinCu.txtdslam_Cu=this.ThongTinMoi.txtdslam_moi=dsip[0].tendslam?this.ThongTinMoi.txtdslam_moi=dsip[0].tendslam.toString():''
                        if(dsip[0].selfid01){
                          this.ThongTinCu.txtNasportID_cu=this.ThongTinMoi.txtNasportID_moi=dsip[0].system+'/'+dsip[0].rack+'/'+dsip[0].shelf+'/'+dsip[0].slot
                          +'/'+dsip[0].port+'/'+dsip[0].vpi+'/'+dsip[0].vci+'/'+dsip[0].selfid04+'/'+dsip[0].selfid03+'/'+dsip[0].selfid02+'/'+dsip[0].selfid01

                        }else{
                          this.ThongTinCu.txtNasportID_cu=this.ThongTinMoi.txtNasportID_moi=dsip[0].system+'/'+dsip[0].rack+'/'+dsip[0].shelf+'/'+dsip[0].slot
                          +'/'+dsip[0].port+'/'+dsip[0].vpi+'/'+dsip[0].vci
                        }
                        this.ThongTinCu.txtPortMDF_cu=this.ThongTinMoi.txtPortMDF_moi=dsip[0].port_mdf
                        this.ThongTinCu.txtADSL_Cu=this.ThongTinMoi.txtADSL_moi=dsip[0].adsl_port
                        if(dsip[0].donvi_id){
                          this.ThongTinCu.txtTramTB_cu=this.ThongTinMoi.txtTramTB_moi=await this.fn_tt_donvi({
                            param: dsip[0].donvi_id, 
                            type: 2 
                          })
                          this.tramtb_id=dsip[0].donvi_id
                        }else{
                          this.ThongTinCu.txtTramTB_cu=this.ThongTinMoi.txtTramTB_moi=''
                          this.tramtb_id=''
                        }
                      }

                    }else{
                      this.port_cu_id= 0,
                      this.vci_vpi_cu_id= 0,
                      this.port_moi_id= 0,
                      this.vci_vpi_moi_id= 0
                      this.ThongTinCu.txtTramTB_cu=this.ThongTinMoi.txtTramTB_moi=""
                      this.ThongTinCu.txtNasportID_cu=this.ThongTinMoi.txtNasportID_moi=""
                      this.ThongTinCu.txtPortMDF_cu=this.ThongTinMoi.txtPortMDF_moi=""
                      this.ThongTinCu.txtADSL_Cu=this.ThongTinMoi.txtADSL_moi=""
                    }
                } 
        
      } catch (err) {
      } finally {
        this.Loading = false
      }
    },
    searchAccountDialogClose(data) {
      if (data != null) {
        this.txtMaTB= data.ma_tb;
        this.HienThiThongTin();
      }
      this.$refs.searchAccountDialog.hide()
    },
     async acceptSearchContract (data) {
      if (data != null) {
        this.txtMaTB= data.ma_tb;
        this.HienThiThongTin();
      }
      this.$refs.popupFrmTraCuuHopDong.hide()
    },
    
    searchHuongKNDialogClose(data) {
      if (data != null) {
        this.fn_lay_text_huongketnoi();
      }
      this.$refs.searchHuongKN.hide()
    },
    async chonPort(){
      let modal = this.$refs["ref-chon-port"]

      let dsdonvi=await this.sp_tt_dbtb_dv({
        p_param: this.txtThueBaoID, 
        p_type:1
      })
      let tramvt=dsdonvi.filter(x=>x.loaidv_id==5)
      var tramvt_id=0
      var diachi_id=-1
      if(tramvt){
        tramvt_id=tramvt.donvi_id
      }
      if (tramvt_id == 0){
        this.$toast.error('Chưa có thông tin về trạm viễn thông!')
        return
      }
      console.log('params.TrangThaiID: ', this.params.TrangThaiID)
      if(this.params.TrangThaiID==1){
        diachi_id=await this.fn_tt_diachi_tb({
          param: this.txtThueBaoID, 
          type: 1
        })
      }else{
        diachi_id=await this.fn_tt_diachi_hdtb({
          param: this.hdtb_id, 
          type: 3
        })
      }

      //this.hdtb_id=0 chưa làm phần hợp đồng
      modal.frmChonPort(tramvt_id, this.tramtb_id, this.port_cu_id, 3, 8, this.params.p_loaihinhtb,diachi_id,'-1',0,this.txtThueBaoID,null,this.tramtb_id)
      modal.show()
    },
    async onSelectPort(port){

      this.ThongTinMoi.txtPortMDF_moi=port.port_mdf?port.port_mdf.toString():''
      this.ThongTinMoi.txtADSL_moi=port.vadsl_port?port.vadsl_port.toString():''
      this.ThongTinMoi.txtdslam_moi=port.ten_dslam?port.ten_dslam.toString():''
      if(port.selfid01){
        this.ThongTinMoi.txtNasportID_moi=port.system?port.system.toString():''+'/'
                                          +port.selfid04?port.selfid04.toString():''+'/'
                                          +port.selfid03?port.selfid03.toString():''+'/'
                                          +port.selfid02?port.selfid02.toString():''+'/'
                                          +port.selfid01?port.selfid01.toString():''+'/'
      }else{
        this.ThongTinMoi.txtNasportID_moi=port.system?port.system.toString():''
      }
      if(port.tramtb_id){
        this.ThongTinMoi.txtTramTB_moi=await this.fn_tt_donvi({
           param: port.tramtb_id, 
           type: 2 
        })
      }else{
        this.ThongTinMoi.txtTramTB_moi=''
      }

      this.port_moi_id = port.port_id
      this.vci_vpi_moi_id = port.vci_vpi_id
      this.tramtb_id = port.tramtb_id

      this.ThongTinMoi.txtSvlan_moi=port.vpi?port.vpi.toString():''
      this.ThongTinMoi.txtCVlan_moi=port.vci?port.vci.toString():''

    },
    async btnCapNhat_click() {

      let data={
        "thuebao_id": this.txtThueBaoID,
        "thuebao_cha_id": this.txtthuebaoCha_id_moi,
        "thuebao_cha_id_cu":this.txtThueBao_cha_id,
        "hdtb_id": this.hdtb_id,
        "hdtb_cha_id": this.hdtb_cha_id,
        "hdtb_cha_id_cu": this.hdtb_cha_id_cu,
        "huong_kn": this.ThongTinMoi.txtHuongKN_moi,
        "huong_kn_cu": this.ThongTinCu.txtHuongKN_cu,
        "ma_tb": this.txtMaTB,
        "donvi_id": this.$root.token.getDonViID(),
        "trangthai_id": this.params.TrangThaiID==1?1:2,
        "ma_pl": this.ThongTinMoi.txtSoPLHD_moi,
        "port_id": this.port_moi_id,
        "vci_vpi_id": this.vci_vpi_moi_id,
        "tuyencap": this.ThongTinMoi.txttuyencap_moi,
        "lientu":this.ThongTinMoi.txtLientu_moi,
        "tucap_p": this.ThongTinMoi.txtTucapngon_moi,
        "doicap_p": this.ThongTinMoi.txtdoicapngon_moi,
        "hopcap_p": this.ThongTinMoi.txthopcapngon_moi,
        "cap_p": this.ThongTinMoi.txtcapngon_moi,
        "doicap_goc": this.ThongTinMoi.txtdoicaogoc_moi,
        "tucap_goc": this.ThongTinMoi.txtTucapgoc_moi,
        "culy": this.ThongTinMoi.txtculy_moi,
        "port_pe": this.ThongTinMoi.txtportFE_moi,
        "slot_pe": this.ThongTinMoi.txtSlot_moi,
        "wan_ip": this.ThongTinMoi.ttxWanID_moi,
        "lan_ip": this.ThongTinMoi.txtLanIP_moi,
        "ten_tbi": this.ThongTinMoi.txtTenTbi_moi,
        "svlan": this.ThongTinMoi.txtSvlan_moi,
        "cvlan": this.ThongTinMoi.txtCVlan_moi,
        "vrf": this.ThongTinMoi.txtVrf_moi,
        "serial": this.ThongTinMoi.txtSerial_moi,
        "link": this.ThongTinMoi.txtLink_moi,
        "route": this.ThongTinMoi.txtRoute_moi,
        "dslam": this.ThongTinMoi.txtdslam_moi,
        "pe_id":this.params.p_router_moi,
        "thietbidc_id": this.params.p_loaitbi_moi,
        "ma_pl_cu": this.ThongTinCu.txtSoPLHD_cu,
        "port_id_cu": this.port_cu_id,
        "vci_vpi_id_cu": this.vci_vpi_cu_id,
        "tuyencap_cu": this.ThongTinCu.txttuyencap_cu,
        "lientu_cu": this.ThongTinCu.txtLientu_Cu,
        "tucap_p_cu": this.ThongTinCu.txtTucapngon_cu,
        "doicap_p_cu": this.ThongTinCu.txtdoicaogoc_cu,
        "hopcap_p_cu": this.ThongTinCu.txthopcapngon_cu,
        "cap_goc_cu": this.ThongTinCu.txtCapgoc_cu,
        "cap_p_cu": this.ThongTinCu.txtcapngon_cu,
        "doicap_goc_cu": this.ThongTinCu.txtdoicaogoc_cu,
        "tucap_goc_cu": this.ThongTinCu.txtTucapgoc_cu,
        "culy_cu": this.ThongTinCu.txtculy_cu,
        "port_pe_cu": this.ThongTinCu.txtportFE_cu,
        "slot_pe_cu": this.ThongTinCu.txtSlot_Cu,
        "wan_ip_cu":this.ThongTinCu.ttxWanID_cu,
        "lan_ip_cu": this.ThongTinCu.txtLanIP_Cu,
        "ten_tbi_cu": this.ThongTinCu.txtTenTbi_Cu,
        "svlan_cu": this.ThongTinCu.txtSvlan_cu,
        "cvlan_cu": this.ThongTinCu.txtCVlan_Cu,
        "vrf_cu": this.ThongTinCu.txtVrf_cu,
        "serial_cu": this.ThongTinCu.txtSerial_cu,
        "link_cu": this.ThongTinCu.txtLink_cu,
        "route_cu": this.ThongTinCu.txtRoute_cu,
        "dslam_cu": this.ThongTinCu.txtdslam_Cu,
        "pe_id_cu": this.params.p_router_cu,
        "thietbidc_id_cu":this.params.p_loaitbi_cu,
        "site_id_cu": this.ThongTinCu.txtSiteID_cu,
        "site_id_moi": this.ThongTinMoi.txtSiteID_moi,
        "loaikenh_id": this.params.p_cboLoaiKenh_moi,
        "loaikenh_id_cu": this.params.p_cboLoaiKenh_cu,
        "loainode_id": this.params.p_nodechinh_moi,
        "loainode_id_cu": this.params.p_nodechinh_cu,
        "tinh_kn_id": this.tinh_kn_id,
        "tinh_kn_id_cu": this.tinh_kn_id_cu
      }
      let result=await this.fn_taodulieu_mgwan(data)
      if(result.code==1){
        //capnhat_doi_tskt_mgwan_v3
        let datatskt={
          "vthuebao_id": this.txtThueBaoID,
          "vhdtb_id": this.hdtb_id,
          "vtramtb_id": this.tramtb_id,
          "vport_id": this.port_moi_id,
          "vvci_vpi_id": this.vci_vpi_moi_id,
          "vtuyencap": this.ThongTinMoi.txttuyencap_moi,
          "vlientu": this.ThongTinMoi.txtLientu_moi,
          "vtucap_p": this.ThongTinMoi.txtTucapngon_moi,
          "vdoicap_p": this.ThongTinMoi.txtdoicapngon_moi,
          "vhopcap_p": this.ThongTinMoi.txthopcapngon_moi,
          "vcap_goc": this.ThongTinMoi.txtCapgoc_moi,
          "vcap_p": this.ThongTinMoi.txtcapngon_moi,
          "vdoicap_goc": this.ThongTinMoi.txtdoicaogoc_moi,
          "vtucap_goc": this.ThongTinMoi.txtTucapgoc_moi,
          "vculy": this.ThongTinMoi.txtculy_moi,
          "vport_pe": this.ThongTinMoi.txtportFE_moi,
          "vslot_pe": this.ThongTinMoi.txtSlot_moi,
          "vwan_ip": this.ThongTinMoi.ttxWanID_moi,
          "vlan_ip": this.ThongTinMoi.txtLanIP_moi,
          "vten_tbi": this.ThongTinMoi.txtTenTbi_moi,
          "vsvlan": this.ThongTinMoi.txtSvlan_moi,
          "vcvlan": this.ThongTinMoi.txtCVlan_moi,
          "vvrf": this.ThongTinMoi.txtVrf_moi,
          "vserial": this.ThongTinMoi.txtSerial_moi,
          "vlink": this.ThongTinMoi.txtLink_moi,
          "vroute":this.ThongTinMoi.txtRoute_moi,
          "vdslam": this.ThongTinMoi.txtdslam_moi,
          "vpe_id":this.params.p_router_moi,
          "vtrangthai_dl": this.params.TrangThaiID,
          "vthuebao_cha_id": this.thuebao_cha_id,
          "vhdtb_cha_id": this.hdtb_cha_id,
          "vhuong_kn": this.vhuong_kn,
          "vthietbidc_id": this.params.p_loaitbi_moi,
          "vsite_id": this.ThongTinMoi.txtSiteID_moi,
          "vma_pl": this.ThongTinMoi.txtSoPLHD_moi,
          "vloaikenh_id": this.params.p_cboLoaiKenh_moi,
          "vloainode_id": this.params.p_nodechinh_moi,
          "vtinh_kn_id": this.tinh_kn_id,
          "vdiachi_kn": this.vdiachi_kn
        }
        let result1=await this.capnhat_doi_tskt_mgwan_v3(datatskt)
        if(result1.code==1){
          await this.CapNhatTrangThaiPort()
          this.$toast.success('Cập nhật thông số trên dữ liệu viễn thông tỉnh thành công !')
        }else{
          this.$toast.error(result1.message)
        }
      }else{
        this.$toast.error(result.message)
      }
    }, 
    async CapNhatTrangThaiPort() {
      
      if(this.port_cu_id!=this.port_moi_id||this.vci_vpi_cu_id!=this.vci_vpi_moi_id){
        //#region Cập nhật port cũ
        let dsktradb=await this.sp_ktra_dk_port({
            kieu: 1,
            ma_tb: this.txtMaTB.trim(),
            port_id: this.port_cu_id,
            vci_vpi_id: this.vci_vpi_cu_id
        })
        let dsktrahd=await this.sp_ktra_dk_port({
            kieu: 2,
            ma_tb:this.txtMaTB.trim(),
            port_id: this.port_cu_id,
            vci_vpi_id: this.vci_vpi_cu_id
        })
        if(dsktradb.length==0&&dsktrahd.length==0){
          await this.fn_capnhat_trangthai_port({
              port_id:this.port_cu_id,
              vci_vpi_id:this.vci_vpi_cu_id,
              trangthai:TRANGTHAI_PORT.CHUA_SD
          })
          await this.fn_capnhat_trangthai_portid({
              port_id:this.port_cu_id,
              tt_port_id:TRANGTHAI_PORT.CHUA_SD,
              may_cn:"localhost",
              nguoi_cn:this.$root.token.getUserName()
          })
          await this.fn_capnhat_ds_matb({
              ds_matb:"",
              port_id:this.port_cu_id,
              vci_vpi_id:this.vci_vpi_cu_id
          })
        }else if(dsktradb.length!=0&&dsktrahd.length==0){
          var vds_matb=''
          for(let i=0;i<dsktradb.length;i++){
              vds_matb=vds_matb+dsktradb[i].ma_tb.toString()+'; '
              if(vds_matb.length>3000){
                  break
              }
          }
          if(vds_matb!=''){
              vds_matb=vds_matb.substring(0, vds_matb.length-2)
          }

          await this.fn_capnhat_trangthai_port({
              port_id:this.port_cu_id,
              vci_vpi_id:this.vci_vpi_cu_id,
              trangthai:TRANGTHAI_PORT.DA_SD
          })
          await this.fn_capnhat_trangthai_portid({
              port_id:this.port_cu_id,
              tt_port_id:TRANGTHAI_PORT.DA_SD,
              may_cn:"localhost",
              nguoi_cn:this.$root.token.getUserName()
          })
          await this.fn_capnhat_ds_matb({
              ds_matb:vds_matb,
              port_id:this.port_cu_id,
              vci_vpi_id:this.vci_vpi_cu_id
          })
        }else if(dsktradb.length==0&&dsktrahd.length!=0){
          var vds_matb=''
          for(let i=0;i<dsktrahd.length;i++){
              vds_matb=vds_matb+dsktrahd[i].ma_tb.toString()+'; '
              if(vds_matb.length>3000){
                  break
              }
          }
          if(vds_matb!=''){
              vds_matb=vds_matb.substring(0, vds_matb.length-2)
          }
          await this.fn_capnhat_trangthai_port({
              port_id:this.port_cu_id,
              vci_vpi_id:this.vci_vpi_cu_id,
              trangthai:TRANGTHAI_PORT.TAM_CAP
          })
          await this.fn_capnhat_trangthai_portid({
              port_id:this.port_cu_id,
              tt_port_id:TRANGTHAI_PORT.DA_SD,
              may_cn:"localhost",
              nguoi_cn:this.$root.token.getUserName()
          })
          await this.fn_capnhat_ds_matb({
              ds_matb:vds_matb,
              port_id:this.port_cu_id,
              vci_vpi_id:this.vci_vpi_cu_id
          })
        }else if(dsktradb.length!=0&&dsktrahd.length!=0){
          var vds_matb=''
          for(let i=0;i<dsktradb.length;i++){
              vds_matb=vds_matb+dsktradb[i].ma_tb.toString()+'; '
              if(vds_matb.length>3000){
                  break
              }
          }
          for(let i=0;i<dsktrahd.length;i++){
              vds_matb=vds_matb+dsktrahd[i].ma_tb.toString()+'; '
              if(vds_matb.length>3000){
                  break
              }
          }
          if(vds_matb!=''){
              vds_matb=vds_matb.substring(0, vds_matb.length-2)
          }
          await this.fn_capnhat_trangthai_port({
              port_id:this.port_cu_id,
              vci_vpi_id:this.vci_vpi_cu_id,
              trangthai:TRANGTHAI_PORT.DA_SD
          })
          await this.fn_capnhat_trangthai_portid({
              port_id:this.port_cu_id,
              tt_port_id:TRANGTHAI_PORT.DA_SD,
              may_cn:"localhost",
              nguoi_cn:this.$root.token.getUserName()
          })
          await this.fn_capnhat_ds_matb({
              ds_matb:vds_matb,
              port_id:this.port_cu_id,
              vci_vpi_id:this.vci_vpi_cu_id
          })

        }
        //#endregion

        //#region Cập nhật port mới
        let dsktradb_m=await this.sp_ktra_dk_port({
            kieu: 1,
            ma_tb: this.txtMaTB,
            port_id: this.port_moi_id,
            vci_vpi_id: this.vci_vpi_moi_id
        })
        let dsktrahd_m=await this.sp_ktra_dk_port({
            kieu: 2,
            ma_tb: this.txtMaTB.trim(),
            port_id: this.port_moi_id,
            vci_vpi_id: this.vci_vpi_moi_id
        })
        if(dsktradb_m.length==0&&dsktrahd_m.length==0){
          //nếu ko có thuê bao nào sử dụng port thì update về chưa sử dụng
          if(this.params.TrangThaiID==1){
            await this.fn_capnhat_trangthai_port({
                port_id:this.port_moi_id,
                vci_vpi_id:this.vci_vpi_moi_id,
                trangthai:TRANGTHAI_PORT.DA_SD
            })
          }else{
            await this.fn_capnhat_trangthai_port({
                port_id:this.port_moi_id,
                vci_vpi_id:this.vci_vpi_moi_id,
                trangthai:TRANGTHAI_PORT.TAM_CAP
            })
          }
          
          await this.fn_capnhat_trangthai_portid({
              port_id:this.port_moi_id,
              tt_port_id:TRANGTHAI_PORT.DA_SD,
              may_cn:"localhost",
              nguoi_cn:this.$root.token.getUserName()
          })
          await this.fn_capnhat_ds_matb({
              ds_matb:this.txtMaTB.trim(),
              port_id:this.port_moi_id,
              vci_vpi_id:this.vci_vpi_moi_id
          })

        }else if(dsktradb_m.length!=0&&dsktrahd_m.length==0){
          var vds_matb=''
          for(let i=0;i<dsktradb_m.length;i++){
              vds_matb=vds_matb+dsktradb_m[i].ma_tb.toString()+'; '
              if(vds_matb.length>3000){
                  break
              }
          }
          
          vds_matb=vds_matb+this.txtMaTB.trim()

          await this.fn_capnhat_trangthai_port({
              port_id:this.port_moi_id,
              vci_vpi_id:this.vci_vpi_moi_id,
              trangthai:TRANGTHAI_PORT.DA_SD
          })
          await this.fn_capnhat_trangthai_portid({
              port_id:this.port_moi_id,
              tt_port_id:TRANGTHAI_PORT.DA_SD,
              may_cn:"localhost",
              nguoi_cn:this.$root.token.getUserName()
          })
          await this.fn_capnhat_ds_matb({
              ds_matb:vds_matb,
              port_id:this.port_moi_id,
              vci_vpi_id:this.vci_vpi_moi_id
          })
        }else if(dsktradb_m.length==0&&dsktrahd_m.length!=0){
          var vds_matb=''
          for(let i=0;i<dsktrahd_m.length;i++){
              vds_matb=vds_matb+dsktrahd_m[i].ma_tb.toString()+'; '
              if(vds_matb.length>3000){
                  break
              }
          }
          vds_matb=vds_matb+this.txtMaTB.trim()
          
          await this.fn_capnhat_trangthai_port({
              port_id:this.port_moi_id,
              vci_vpi_id:this.vci_vpi_moi_id,
              trangthai:TRANGTHAI_PORT.TAM_CAP
          })
          await this.fn_capnhat_trangthai_portid({
              port_id:this.port_moi_id,
              tt_port_id:TRANGTHAI_PORT.DA_SD,
              may_cn:"localhost",
              nguoi_cn:this.$root.token.getUserName()
          })
          await this.fn_capnhat_ds_matb({
              ds_matb:vds_matb,
              port_id:this.port_moi_id,
              vci_vpi_id:this.vci_vpi_moi_id
          })
        }else if(dsktradb_m.length!=0&&dsktrahd_m.length!=0){
          var vds_matb=''
          for(let i=0;i<dsktradb_m.length;i++){
              vds_matb=vds_matb+dsktradb_m[i].ma_tb.toString()+'; '
              if(vds_matb.length>3000){
                  break
              }
          }
          for(let i=0;i<dsktrahd_m.length;i++){
              vds_matb=vds_matb+dsktrahd_m[i].ma_tb.toString()+'; '
              if(vds_matb.length>3000){
                  break
              }
          }
          
          vds_matb=vds_matb+this.txtMaTB.trim()

          await this.fn_capnhat_trangthai_port({
              port_id:this.port_moi_id,
              vci_vpi_id:this.vci_vpi_moi_id,
              trangthai:TRANGTHAI_PORT.DA_SD
          })
          await this.fn_capnhat_trangthai_portid({
              port_id:this.port_moi_id,
              tt_port_id:TRANGTHAI_PORT.DA_SD,
              may_cn:"localhost",
              nguoi_cn:this.$root.token.getUserName()
          })
          await this.fn_capnhat_ds_matb({
              ds_matb:vds_matb,
              port_id:this.port_moi_id,
              vci_vpi_id:this.vci_vpi_moi_id
          })
        }
      }

      
    },
    async sp_tt_dbtb_dv(data){
        try{
            this.loading(true)
            let response = await API.sp_tt_dbtb_dv(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async fn_tt_diachi_tb(data){
        try{
            this.loading(true)
            let response = await API.fn_tt_diachi_tb(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return Number(response.data.data)
            }else{
                return -1
            }
        }catch(e){
            this.loading(false)
            return -1
        }
    },
    async fn_tt_diachi_hdtb(data){
        try{
            this.loading(true)
            let response = await API.fn_tt_diachi_hdtb(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return Number(response.data.data)
            }else{
                return -1
            }
        }catch(e){
            this.loading(false)
            return -1
        }
    },
    async sp_lay_thongso_kt(data){
        try{
            this.loading(true)
            let response = await API.sp_lay_thongso_kt(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async fn_tt_donvi(data){
        try{
            this.loading(true)
            let response = await API.fn_tt_donvi(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return ''
            }
        }catch(e){
            this.loading(false)
            return ''
        }
    },
    async fn_capnhat_trangthai_port(data){
        try{
            this.loading(true)
            await API.fn_capnhat_trangthai_port(this.axios, data)
            this.loading(false)
        }catch(e){
            this.loading(false)
        }
    },
    async fn_capnhat_trangthai_portid(data){
        try{
            this.loading(true)
            await API.fn_capnhat_trangthai_portid(this.axios, data)
            this.loading(false)
        }catch(e){
            this.loading(false)
        }
    },
    async fn_capnhat_ds_matb(data){
        try{
            this.loading(true)
            await API.fn_capnhat_ds_matb(this.axios, data)
            this.loading(false)
        }catch(e){
            this.loading(false)
        }
    },
    async Capnhat_port_vatly(data){
        try{
            this.loading(true)
            await API.Capnhat_port_vatly(this.axios, data)
            this.loading(false)
        }catch(e){
            this.loading(false)
        }
    },
    async sp_ktra_dk_port(data){
        try{
            this.loading(true)
            let response = await API.sp_ktra_dk_port(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async fn_taodulieu_mgwan(data){
        try{
            this.loading(true)
            let response=await API.fn_taodulieu_mgwan(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                if(response.data.data=='OK'){
                  return {
                    code:1,
                    message:'Thành công'
                  }
                }else{
                  return {
                    code:0,
                    message:response.data.data
                  }
                }
            }else{
                if(response&&response.data&&response.data.message){
                    return {
                      code:0,
                      message:response.data.message
                    }
                }else{
                  return {
                    code:0,
                    message:'Đã xảy ra lỗi'
                  }
                }
            }
        }catch(e){
            this.loading(false)
            if(e.data&&e.data.message){
              return {
                code:0,
                message:e.data.message
              }
            }else if(e.response&&e.response.data&&e.response.data.message){
              return {
                code:0,
                message:e.response.data.message
              }
            }else{
              return {
                code:0,
                message:'Đã xảy ra lỗi'
              }
            }
        }
    },
    async capnhat_doi_tskt_mgwan_v3(data){
        try{
            this.loading(true)
            let response=await API.capnhat_doi_tskt_mgwan_v3(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                return {
                  code:1,
                  message:'Thành công'
                }
            }else{
                if(response&&response.data&&response.data.message){
                    return {
                      code:0,
                      message:response.data.message
                    }
                }else{
                  return {
                    code:0,
                    message:'Đã xảy ra lỗi'
                  }
                }
            }
        }catch(e){
            this.loading(false)
            if(e.data&&e.data.message){
              return {
                code:0,
                message:e.data.message
              }
            }else if(e.response&&e.response.data&&e.response.data.message){
              return {
                code:0,
                message:e.response.data.message
              }
            }else{
              return {
                code:0,
                message:'Đã xảy ra lỗi'
              }
            }
        }
    },
    async lay_danhba_theo_matb(data){
        try{
            this.loading(true)
            let response = await API.lay_danhba_theo_matb(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async lay_ds_hopdong_theo_matb(data){
        try{
            this.loading(true)
            let response = await API.lay_ds_hopdong_theo_matb(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async lay_ds_hopdong_thuebao_mgwan(hdtb_id){
        try{
            this.loading(true)
            let response = await API.lay_ds_hopdong_thuebao_mgwan(this.axios, hdtb_id)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async fn_lay_text_huongketnoi1(data){
        try{
            this.loading(true)
            let response = await API.fn_lay_text_huongketnoi1(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return ''
            }
        }catch(e){
            this.loading(false)
            return ''
        }
    },

    
    
    async btnHuy_click() {
       
    }, 
    async btnthoat_click() {
       this.close();
    }, 
    async fn_lay_text_huongketnoi() {
      let input = { vid: this.txtThueBaoID, vloai: 1 }
      let data = this.GetData(await API.fn_lay_text_huongketnoi(this.axios, input))
      this.ThongTinMoi.txtHuongKN_moi= data;
      return data
    },
     async TrangThaiID(){
      try {
        this.Loading = true
        //await this.HienThiDanhSach()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
     popupDSHuongKN() {
      let vkieu = 1
      let tinhkh_id = this.Tinh_KN_ID
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDSHuongKNV2')
      this.popupComponentName = 'popupDSHuongKN'
      this.popupComponentData = {
        kieu: vkieu,
        dichvuvt_id: this.params.p_divhvuvt,
        tinhkn_id: tinhkh_id,
        db_hd: 1,
        vid: this.khachhang_id
      } 
      this.Popup('popupComponentMGs')
    },  
     
     async popupClosed(val) {
      if (!val) {
        this.ClosePopup('popupComponentMGs')
        return
      }
      this.loading(true)
      try {
         switch (this.popupComponentName) {
            case 'popupDSHuongKN':
            this.ThongTinMoi.txtHuongKN_moi = val.shuongkn
            this.txtthuebaoCha_id_moi = val.iid_sochinh
          //  txtHuong_KN_Moi.Text = frm.shuongkn;
            this.vhuong_kn = val.shuongkn?val.shuongkn:''
            this.thuebao_cha_id = 0
            this.hdtb_cha_id = 0
            this.tinh_kn_id = frm.itinh_kn_id
            this.vdiachi_kn = val.diachi_ld?val.diachi_ld:''
            if(val.iloai==1){
              this.thuebao_cha_id= val.iid_sochinh
            }else{
              this.hdtb_cha_id = val.iid_sochinh
            }
            break
           
         }
       } catch (ex) {
      } finally {
        this.ClosePopup('popupComponentMGs')
        this.loading(false)
      }
     },
     Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },

    MaGD_KeyUp:async function() {
      try{
        this.Loading =true
        this.Clear()
        if (this.txtMaTB && this.txtMaTB != ''){
            this.HienThiThongTin();  
        }             
      }
      catch(ex){
        this.Loading =false
      }
    },

   
  },
};
</script>
