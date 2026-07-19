<template src="./index.html"></template>
<style src="./style.scss"></style>
<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import giaophieunhanvien from "@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue";
import api from "./api/index.js";
import state from "./define.js";
import ChonPort from "./popup/chon-port"
import ModalDauNoiThueBao from './popup/modal-daunoi-thuebao'
import traphieu from "@/modules/contract/setup/ReturnInstallTicket/components/traphieu.vue";
import ThongTinHenKhachHang from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue"
import TraCuuThongSoKTModal from '@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/TraCuuThongSoKT/TraCuuThongSoKTModal.vue'
import KetQuaDoKiem from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/KetQuaDoKiem/KetQuaDoKiem.vue'
import DuAnHDTB from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/DuAnHDTB/DuAnHDTB.vue'
import VatTuThueBao from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue'
import ThayDoiKenhThu from '@/modules/QLTN/ThayDoiKenhThu'
import ThayDoiKhuVucQuanLyThueBaoModal from '@/modules/contract/setup/SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import KDatePicker from "@/components/kylq_components/KDatePicker.vue";
import InBienBan from '@/modules/print/InBienBan/InBienBan.vue'
import {
  DichVuVienThong,  
  LOAI_DV,  
  LoaiHinhTB,
  TrangThaiHD,
  TRANGTHAI_PORT,
  TRANGTHAI_DONGBO,
} from "./Enums";
export default {
  components: { 
    moment,
    KDatePicker,
    breadcrumb, 
    gridview,
    giaophieunhanvien,
    ChonPort,
    ModalDauNoiThueBao,
    traphieu,
    ThongTinHenKhachHang,
    TraCuuThongSoKTModal,
    KetQuaDoKiem,
    DuAnHDTB,
    VatTuThueBao,
    ThayDoiKhuVucQuanLyThueBaoModal,
    ThayDoiKenhThu,
    InBienBan

  },
  name: "",
  props: {
    inTag: String,
    inQuytrinh: Number,    
  },
  data() {
    return {
      header: {
        title: "Thay đổi thông số MegaWan",
        list: [
          { name: "Cập nhật KQTC", link: { name: "Ui.cards" } },
          { name: "Thay đổi, khóa mở, thanh lý", link: { name: "Ui.cards" } },
          
        ],
      },
      ThHdId: 1,
      initLoaiHdArr: [],
      initDVVTArr: [],
      Tag:"",
      donvi_id: this.$root.token.getDonViID(),
      popupComponentEvts: {
        'form-close': this.popupClosed,
        'acceptChangeCTV': this.popupClosed,
        'acceptChangeNGT': this.popupClosed,
    },
      ...state
      
    };
  },
  created() {
    // dat gia trị trong ham khoi tao cua component
   // Lay gia trị tag truyen tu url   
    if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
      this.Tag = this.$route.query.tag
    }
    // Gan vao theo tham so cua form
   //  this.Tag = '4+5+8;9'
    if (this.Tag != null && this.Tag.length > 0) {
      let arr = this.Tag.split('+')
      if (arr.length >= 1) this.ThHdId = parseInt(arr[0])
      if (arr.length >= 2) this.initLoaiHdArr = arr[1].split(';')
      if (arr.length >= 3) this.initDVVTArr = arr[2].split(';')
    }

    //this.inTag = '4+5+8;9'
    if (this.inTag != null && this.inTag.length > 0) {
      let arr = this.inTag.split('+')
      if (arr.length >= 1) this.ThHdId = parseInt(arr[0])
      if (arr.length >= 2) this.initLoaiHdArr = arr[1].split(';')
      if (arr.length >= 3) this.initDVVTArr = arr[2].split(';')
    }
    //this.inQuytrinh = 38078
    if (this.inQuytrinh != null && this.inQuytrinh != 0){
      this.quytrinh_id = this.inQuytrinh
    }

    // lay combobox dich vu
    var api1 = this.layDSDVVT() 

    // api get ds nguoi GV
    let dataNGV = {
      nghiepvu: "DS_NGUOI_GIAOVIEC",
      ds_para: '[{"DONVI_ID":'+this.$root.token.getDonViID()+'}]',
    };
    api.getDanhSachNguoiGV(this.axios, dataNGV).then((res) => {      
      
      if (res.data.data && res.data.data.length > 0) {
        this.options.nguoiGV = res.data.data;
        this.formViewGiaoViec.nguoi_gv_id = this.$root.token.getNhanVienID()*1
      }
    });

    // api  lay loai kenh
    api.getLoaiKenh(this.axios).then((res) => {      
      if (res.data.data && res.data.data.length > 0) {
        this.options.loaiKenh = res.data.data;        
      }
    });

    // api get trang bi
    api.getTrangBiMEGAWAN(this.axios).then((res) => {      
      if (res.data.data && res.data.data.length > 0) {
        this.options.trangBi = res.data.data;
      }
    });

    // api get toc do
    api.getTocDoPir(this.axios).then((res) => {      
      if (res.data.data && res.data.data.length > 0) {
        let temp =[]
          $.each(res.data.data, function (key, item) {
              temp.push({TOCDO_ID:item.TOCDO_ID,TT_TOCDO:item.TOCDO+' ' + item.DONVI})
            });
        //this.options.cbx_tocdo_kenh = temp
        this.options.tocDoCir = temp;
        this.options.tocDoPir = temp;
        this.options.tocDoNix = temp;
        this.options.tocDoIsp = temp;
      }
    });
    // api get loai chu quan
    api.getChuQuanMEGAWAN(this.axios).then((res) => {      
      if (res.data.data && res.data.data.length > 0) {
        this.options.chuQuan = res.data.data;
      }
    });

    // api get loai thiet bi
    let dataLoaiTB = { nghiepvu: "DS_THIETBI_DAUCUOI", ds_para: "" };
    api.getLoaiTBWan(this.axios, dataLoaiTB).then((res) => {      
      if (res.data.data && res.data.data.length > 0) {
        this.options.loaiThietBi = res.data.data;
        this.options.cbx_thietbi = res.data.data;

      }
    });

    // api get mode,
    api.getModem(this.axios).then((res) => {      
      if (res.data.data && res.data.data.length > 0) {
        this.options.modem = res.data.data;
      }
    });

    // api get Thoi Han
    api.getThoiHanMEGAWAN(this.axios).then((res) => {      
      if (res.data.data && res.data.data.length > 0) {
        this.options.thoiHan = res.data.data;
      }
    });

    // api get Tinh ket noi
    api.getTinhKN(this.axios).then((res) => {    
      if (res.data.data && res.data.data.length > 0) {
        this.options.tinhKN = res.data.data;
      }
    });
    this.lay_thamso_cabman_v2()
    Promise.all([api1])
        .then(() => {
          this.dichvuvt_id = parseInt(this.options.dichVu[0].DICHVUVT_ID);          
          if (this.ThHdId == TrangThaiHD.DOISOAT_HOSO){
            this.enable.btnGiaoViec = false;
          }
        })
        .catch(() => {})
        .then(() => {
          //console.log('lay ds 1')
          //this.layDanhSachHDTB('')          
        })
        .finally(() => {
          this.loading(false);
          this.watcherActive = true;
        });
  },
  mounted() {
    $( "input" ).each(function( index ) {      
      $(this).attr('name','input-'+index)
    });
    $('.legend-title').not('.grid-title').click(function(){
      if ($(this).hasClass('active')){
        $(this).next().slideUp()
        $(this).removeClass('active')
      }
      else {
        $(this).addClass('active')        
        $(this).next().slideDown()
      }
    });
    $('.legend-title .grid-title-pull').click(function(){
      if ($(this).parent().hasClass('active')){
        $(this).parent().next().slideUp()
        $(this).parent().removeClass('active')
      }
      else {
        $(this).parent().addClass('active')        
        $(this).parent().next().slideDown()
      }
    });
    $("#magiaodich").focus();
    
    
    
      
    

  },
  computed: {},
  watch: {   
    optionTrangThai(){
      this.clearForm()
      this.layDanhSachHDTB('')
    },
    'dichvuvt_id': async function (newValue, oldValue) {      
      this.layQuyTrinh()
    },
    'quytrinh_id': async function (newValue, oldValue) {
      this.getLuong_Huonggiao()
      //lấy dữ liệu tương tự hàm trên getLuong_Huonggiao
      //this.layDSThaoTac(newValue)
    },
    'searchDate.checkFromDate' : async function (newValue, oldValue) {      
      this.searchDate.isEnabledFromDate = newValue
    },
    'searchDate.checkToDate' : async function (newValue, oldValue) {
      this.searchDate.isEnabledToDate = newValue      
    },
  },
  methods: {
    setActiveButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
      let index1 = this.actionsDropdown.findIndex(item => item.id === id)
      if (index1 >= 0) {
        this.actionsDropdown[index1].active = status
      }
    },
    setVisibleButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].visible = status
      }
      let index1 = this.actionsDropdown.findIndex(item => item.id === id)
      if (index1 >= 0) {
        this.actionsDropdown[index1].visible = status
      }
    },
    async lay_thamso_cabman_v2(){
      try{        
        let response = await api.lay_thamso_cabman_v2(this.axios)        
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data &&response.data.data.length>0) {          
          this.IS_USING_CABMAN_V2=true
        }else{
          this.IS_USING_CABMAN_V2=false
        }
      }catch(e){
        console.log(e)
      }
    },
    async layDSDVVT(){
      try {
        await api.getDichVu(this.axios).then((res) => {              
          if (res.data.data && res.data.data.length > 0) {       
            let arr=this.initDVVTArr          
            let temp = res.data.data.filter((x) => arr.filter((y) => y.toString() == x.DICHVUVT_ID.toString()).length > 0).map((x) => ({ DICHVUVT_ID: x.DICHVUVT_ID, TEN_DVVT: x.TEN_DVVT })) 
            this.options.dichVu = temp
          }
        });
      }catch(e){
        console.log(e)
      }
    },
    async layQuyTrinh() {
      try {
        let arr = this.initLoaiHdArr         
        let dataQuyTrinh = {
          dsloaihd_id: arr.toString(),
          dichvuvt_id: this.dichvuvt_id,
          tthd_id: this.ThHdId,
          kieu: 0,
        };
        this.options.quyTrinh=[]
        await api.getQuyTrinh(this.axios, dataQuyTrinh).then((res) => {
          if (res.data.data && res.data.data.length > 0) {
            this.options.quyTrinh = res.data.data;
            this.quytrinh_id = res.data.data[0].quytrinh_id; //todo: kiểm tra props inQuytrinh
            //this.layDSThaoTac(res.data.data[0].quytrinh_id)          
          }
          else {
            this.quytrinh_id=0
            this.huonggiao_id=0
            //this.clearForm()
            //this.layDanhSachHDTB('')
          }
        });
      } catch (error) {
        console.log(error) 
      } finally {
      }
    },
    async getLuong_Huonggiao() {
      try {
        let arr = this.initLoaiHdArr              
        let data = {
          quytrinh_id: this.quytrinh_id,        
          tthd_id: this.ThHdId,        
        };      
        await api.getLuong_Huonggiao(this.axios, data).then((res) => {
          if (res.data.data && res.data.data.length > 0) {
            // this.options.quyTrinh = res.data.data;
            // this.formViewTTKH.quytrinh_id = res.data.data[0].quytrinh_id;
            this.huonggiao_id=res.data.data[0].huonggiao_id
            this.luong_id=res.data.data[0].luong_id    
            this.header.title = res.data.data[0].huonggiao
            this.header.list.push({ name: res.data.data[0].huonggiao, link: { name: "Ui.cards" } },)
            
            this.hienthiGiaoDien(res.data.data[0].luong_id)
            this.layDanhSachHDTB('')
          }
        });
      } catch (error) {
        console.log(error) 
      } finally {
      }
    },
    async hienthiGiaoDien(luong_id) {     
      try {
        let data = {
          'luong_id': luong_id,
        };    
        /*
            tsbtnKQ_DoKiem.Visible = false;
            tsbtnVatTuMoi.Visible = false;             
        */  
        await api.lay_luong_thaotac(this.axios, data).then((res) => {
          console.log('lay_luong_thaotac')
          console.log(res)
          if (res.data.data && res.data.data.length > 0) {
            this.dsThaoTac = res.data.data
            // lấy danh sách action để load giao diện theo luồng xử lý
            let item = res.data.data.find(x=>x.code == 'KICHHOAT_TD')
            if (item) {
               this.setActiveButton('kichhoat',(item.enable==1))
            }
            else {
              this.setVisibleButton('kichhoat',false)
            }
            item = res.data.data.find(x=>x.code == 'DU_AN')
            if (item)  {
               this.setActiveButton('duan',(item.enable==1))
            }
            else {
              this.setVisibleButton('duan',false)
            }

            item = res.data.data.find(x=>x.code == 'CAPNHAT_CAP')
            if (item) this.setActiveButton('daunoi',(item.enable==1))

            item = res.data.data.find(x=>x.code == 'GIAOPHIEU')
            if (item) {
               this.setActiveButton('giaophieu',(item.enable==1))
               this.setVisibleButton('giaophieu',true)
            }
            else {
              this.setVisibleButton('giaophieu',false)
            }

            item = res.data.data.find(x=>x.code == 'HOANCONG')            
            if (item) {               
              this.setActiveButton('hoancong',(item.enable==1))
              this.setVisibleButton('hoancong',true)
            }
            else {
              this.setVisibleButton('hoancong',false)
            }

            item = res.data.data.find(x=>x.code == 'HOANTHIENHOSO')
            if (item) { 
              this.setActiveButton('hoanthien',(item.enable==1))
              this.setVisibleButton('hoanthien',true)
            }
            else {
              this.setVisibleButton('hoanthien',false)
            }

            item = res.data.data.find(x=>x.code == 'GIAOVIEC')
            if (item) { 
              this.setActiveButton('giaoviec',(item.enable==1))
              this.setVisibleButton('giaoviec',true)
            }
            else {
              this.setVisibleButton('giaoviec',false)
            }

            item = res.data.data.find(x=>x.code == 'VAT_TU')
            if (item) { 
              this.setActiveButton('vattu',(item.enable==1))
              this.setVisibleButton('vattu',true)
            }
            else {
              this.setVisibleButton('vattu',false)
            }

            item = res.data.data.find(x=>x.code == 'THAYDOI_KENHTHU')
            if (item) { 
              this.setActiveButton('suakenhthu',(item.enable==1))
              this.setVisibleButton('suakenhthu',true)
            }
            else {
              this.setVisibleButton('suakenhthu',false)
            }
            item = res.data.data.find(x=>x.code == 'TINH_CHUYEN_NET_KENH_NT')
            if (item) { 
              this.setActiveButton('giaonet',(item.enable==1))
              this.setVisibleButton('giaonet',true)
            }
            else {
              this.setVisibleButton('giaonet',false)
            }

            item = res.data.data.find(x=>x.code == 'KETQUA_DOKIEM')
            if (item) { 
              this.setActiveButton('dokiem',(item.enable==1))
              this.setVisibleButton('dokiem',true)
            }
            else {
              this.setVisibleButton('dokiem',false)
            }

            item = res.data.data.find(x=>x.code == 'CAPNHAT_PORT')
            if (item) this.enable.btnCapPort = (item.enable==1)

            item = res.data.data.find(x=>x.code == 'HUONG_KN')
            if (item) this.enable.btnHuongKN = (item.enable==1)

            item = res.data.data.find(x=>x.code == 'TSBTNGIAOPHIEU_TON')
            if (item) this.setActiveButton('henld',(item.enable==1))

          }
          
        });
      } catch (error) {
        console.log(error) 
      } finally {
      }
    },
    async layDSThaoTac(quytrinh_id){
       try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_danhsach_thaotac/"+quytrinh_id       
        );
        this.dsThaoTac = rs.data
      } catch (error) {
        return false;
      } finally {
      }
    },
    async layDanhSachHDTB(ma_gd){
      
      this.loading(true)
      try {
        let dataHDTB = {
            "dichvuvt_id":this.dichvuvt_id,
            "tthd_id":this.ThHdId,
            "nhanvien_id":this.$root.token.getNhanVienID(),
            "ma_gd":ma_gd,
            "phieu_id":0,
            "loaihd_id":this.initLoaiHdArr[0],
            "caidat_dv":0,
            "kieu_id":0,
            "huonggiao_id": this.huonggiao_id,
            "loai_id":this.optionTrangThai
        }
        await api.getDanhSachHDTB(this.axios, dataHDTB).then(res => {        
            this.clearForm()                        
            this.tableConfigDSThueBao.tableData =[]
            if (res.data.data && res.data.data.length > 0) {
              //this.tableConfigDSThueBao.tableData = res.data.data   
              
              if (res.data.data.length == 0){                
                this.setActiveButton('hoancong',false)
                this.setActiveButton('hoanthien',false)
                this.setActiveButton('giaophieu',false)                
              }                 
              let temp=res.data.data 
              if (this.search_GD !=''){
                let index = temp.findIndex(
                  (item) => item.ma_gd.trim() == this.search_GD
                );
                if (index>-1) {
                  let row = temp[index]
                  temp=[]
                  temp.push(row)
                }
              } else if (this.search_TB !=''){
                let index = temp.findIndex(
                  (item) => item.ma_tb.trim() == this.search_TB
                );
                if (index>-1) {
                  let row = temp[index]
                  temp=[]
                  temp.push(row)
                }
              } else if (this.ma_tb!='') { //nếu cả ko phải đang thao tác trên 1 dòng tìm kiếm, kiểm tra xem mã thuê bao đang cập nhật
                           
                let index = temp.findIndex(
                  (item) => item.ma_tb.trim() == this.ma_tb
                );
                if (index>-1) {
                  let row = temp[index]
                  temp.splice(index, 1)
                  temp.unshift(row)
                }                       
              }
              this.tableConfigDSThueBao.tableData = temp 
              
              this.loading(false)
            }
            else {
              this.loading(false)
            }
        })
      }
      catch(ex){
        this.loading(false)
        console.log(ex)
        this.$toast.error("Có lỗi khi lấy danh sách hợp đồng thuê bao!");
      }
    }, 
    async getDanhsachnhanvien() {
      try {
        let data = {
            phieu_id: this.formViewTTKH.phieu_id,
            nhanvien_id: this.$root.token.getNhanVienID(),
            kieu_id: 2,
        }
        await api.lay_ds_nhanvien_theo_phieu_id(this.axios,data).then((res) => {              
              if (res.data.data && res.data.data.length > 0) {                  
                this.tableConfigNGV.tableData = res.data.data 
                this.searchDate.checkFromDate = true
                this.searchDate.fromDate = res.data.data[0].ngaygiao? (moment(res.data.data[0].ngaygiao,'DD/MM/yyyy HH:mm:ss').format('DD/MM/yyyy HH:mm A')) : ''               
                this.formViewGiaoViec.nguoi_gv_id = parseInt(res.data.data[0].nhanvien_giao_id)   
                //this.$refs.dsNhanVienTH.setCurrentSelectedRow(0);  
                this.$refs.dsNhanVienTH.selectRow(0,true);            
              }
              else {                
                   this.tableConfigNGV.tableData = []
                  this.searchDate.checkFromDate = false
                  this.searchDate.fromDate = moment(new Date()).format("DD/MM/yyyy HH:mm a")
                  this.formViewGiaoViec.nguoi_gv_id = this.$root.token.getNhanVienID()
                }
            });        
      } catch (err) {
        console.log(err)        
       }
    }, 
    //lấy nasport, tendslam, port adsl, ipdslam, donvi_id - trạm port
    async lay_thongso_kt(port_id,vci_vpi_id) {       
      let data = {
        nghiepvu: "LAY_THONGSO_KT",
        ds_para: '[{"PORT_ID":'+port_id+', "VCI_VPI_ID":'+vci_vpi_id+'}]',
      };
      try {
        await api.lay_thongso_kt(this.axios, data).then((res) => {        
          if (res.data.data && res.data.data.length > 0) {
            this.formViewHoanCong.tendslam=res.data.data[0].tendslam
            this.formViewHoanCong.adsl_port=res.data.data[0].adsl_port
            this.formViewHoanCong.port_mdf=res.data.data[0].port_mdf
            this.formViewHoanCong.ipdslam=res.data.data[0].ip          
            this.formViewHoanCong.tramtb_id=res.data.data[0].donvi_id
            
            if (res.data.data[0].selfid01!=null && res.data.data[0].selfid01!='' && res.data.data[0].selfid01!='null' ) {
              this.formViewHoanCong.nasport_id = res.data.data[0].system + '/' 
                                                +res.data.data[0].selfid04 + '/'
                                                +res.data.data[0].selfid03 + '/'
                                                +res.data.data[0].selfid02 + '/'
                                                +res.data.data[0].selfid01;
            }
            else {
              this.formViewHoanCong.nasport_id = res.data.data[0].system
            } 
          }
        });
      }catch(e){
        console.log(e)
        this.$toast.error("Có lỗi khi load thông tin tên dslam, port_mdf, ipdslam!");
      }
    },
    //lấy trạm thi công
    async lay_tramthicong(hdtb_id) { 
      //hdtb_id = 1517282      
      let data = {
        nghiepvu: "LAY_DONVI_TRAMTC_HDTB",
        ds_para: '[{"HDTB_ID":'+hdtb_id+'}]',
      };
      //lay_tramthicong dùng chung SP SP_THAYDOI_TS_MGWAN_DANHMUC (api. lay_thongso_kt)
      try {
        await api.lay_thongso_kt(this.axios, data).then((res) => {        
          if (res.data.data && res.data.data.length > 0) {          
            this.formViewHoanCong.donvi_id = res.data.data[0].donvi_id
          
          }
        });
      }catch(e){
        console.log(e)
        this.$toast.error("Có lỗi khi load thông tin trạm thi công!");
      }
    },  
    //lấy tên hướng kết nối
    async lay_ten_huong_kn(thuebao_cha_id) {            
      let data = {
        nghiepvu: "LAY_TEXT_HUONG_KN",
        ds_para: '[{"THUEBAO_CHA_ID":'+thuebao_cha_id+'}]',
      };
      this.formViewHoanCong.huong_kn=""
      try {
        await api.lay_thongso_kt(this.axios, data).then((res) => {
          if (res.data && res.data.data && res.data.data.length > 0) {          
            this.formViewHoanCong.huong_kn = res.data.data[0].text_huongkn
          
          }
        });
      }catch(e){
        console.log(e)
        this.$toast.error("Có lỗi khi load tên hướng kết nối!");
      }
    },  
    async lay_tocdo_thaydoi_hopdong(hdtb_id) { 
      //hdtb_id = 5416114      
      let data = {
        "hdtb_id": hdtb_id,
      };      
      try {
        await api.lay_tocdo_thaydoi_hopdong(this.axios, data).then((res) => {        
          if (res.data.data ) {          
            this.formViewHoanCong.thaydoi_td = res.data.data         
          }
        });
      }catch(e){
        console.log(e)
        this.$toast.error("Có lỗi khi load thông tin thay đổi tốc độ!");
      }
    },      
    async lay_map_thamso_huongkn(in_thuebao_cha_id,fieldname,tablename) { 
      try {     
        let data = {
          "param": in_thuebao_cha_id,
          "type": 2,
        };      
        await api.fn_tt_db_thuebao(this.axios, data).then((res) => {        
          if (res.data.data ) {
            //lấy dữ liệu hướng kết nối          
            this.vhuongkn = res.data.data             
          }
        });
      } catch (error) {
        console.log(error) 
      } finally {
      }
    },  
    async lay_map_thamso_dslam_tinh(dslam_id,fieldname,tablename) { 
      try {     
        let data = {
          "param": dslam_id,
          "type": 3,
          };      
        await api.fn_tt_dslam(this.axios, data).then((res) => {        
          if (res.data.data ) {                      
            this.formViewHoanCong.dslam_tinh = res.data.data   
          }
        });
      } catch (error) {
        console.log(error) 
      } finally {
      }
    },  
    async sp_ht_phieu_tra(phieu_id) {       
      try {
        let data = {
          "phieu_id": phieu_id,        
          };      
        await api.sp_ht_phieu_tra(this.axios, data).then((res) => {        
          if (res.data.data && res.data.data.length>0 ) {                     
            this.formViewTTKH.nd_tra = res.data.data[0].nd_tra
          }
        });
      } catch (error) {
        console.log(error) 
      } finally {
      }
    },  
    async getThongTinNhanVienYC(nhanvien_id) {       
      try {
        let data = {
          "nhanvien_id": nhanvien_id,        
          };      
        await api.getThongTinNhanVien(this.axios, data).then((res) => {        
          if (res.data.data ) {                    
            this.formViewHoanCong.nhanvien_yc =  res.data.data
          }
        });
      } catch (error) {
        console.log(error) 
      } finally {
      }
    },  
    async HienThiTTHopDongTB(item){
      console.log('HienThiTTHopDongTB')
      if (this.tableConfigDSThueBao.tableData.length<=0){
        this.clearForm()
        return
      }      
      try {
        let formData = item;
          for (let key in formData) {
            this.$set(this.formViewHoanCong, key, formData[key]);
            this.$set(this.formViewTTKH, key, formData[key]);
         }
        this.port_id=formData.port_id
        this.vci_vpi_id=formData.vci_vpi_id
        
        this.formViewTTKH.ten_tb = this.ChuanHoaTen(formData.ten_tb)
        this.formViewTTKH.diachi_ld = this.ChuanHoaTen(formData.diachi_ld)
        
         //thông tin phiếu trả
        this.sp_ht_phieu_tra(formData.phieu_id)        
        this.getThongTinNhanVienYC(formData.nhanvien_id)
        
        if (formData.ngay_ht && formData.ngay_ht!=''){
          this.searchDate.isEnabledToDate = true
          this.searchDate.checkToDate = true
        }
        else {
                   
          //disable nút hoàn công                           
          this.setActiveButton('hoancong',false)
          this.searchDate.isEnabledToDate = false
          this.searchDate.checkToDate = false
        }

        if (formData.tocdo_pir_id && formData.tocdo_pir_id!=''){
          this.checkbox.ckbTocdoPir = true          
          this.enable.TocdoPir = true
        }
        else {
          this.checkbox.ckbTocdoPir = false
          this.enable.TocdoPir = false
        }
        if (formData.tocdo_nix && formData.tocdo_nix!=''){
          this.checkbox.ckbTocdoNix = true     
          this.enable.TocdoNix = true     
        }
        else {
          this.checkbox.ckbTocdoNix = false
          this.enable.TocdoNix = false  
        }
        if (formData.tocdo_isp && formData.tocdo_isp!=''){
          this.checkbox.ckbTocdoIsp = true  
          this.enable.TocdoIsp = true          
        }
        else {
          this.checkbox.ckbTocdoIsp = false
          this.enable.TocdoIsp = false    
        }
         
        
        this.in_thuebao_cha_id = 0;        
        this.vhuongkn = "";
        if (formData.thuebao_cha_id != null && formData.thuebao_cha_id!='')
        {
          this.in_thuebao_cha_id = parseInt(formData.thuebao_cha_id);          
          //lấy tên hướng kết nối
          this.lay_ten_huong_kn(this.in_thuebao_cha_id)

          //lấy vhuongkn
          this.lay_map_thamso_huongkn(this.in_thuebao_cha_id,'ma_tb','css.db_thuebao')
        }
        if (formData.dslam_tinh_id != null && formData.dslam_tinh_id!='')
        {          
          //lấy tendslam tỉnh
          this.lay_map_thamso_dslam_tinh(formData.dslam_tinh_id,'tendslam','css.dslam')
        }
        

        if (formData.chuquan_id == null || formData.chuquan_id=='')
          this.formViewHoanCong.chuquan_id = 0;
        if (formData.trangbi_id == null || formData.trangbi_id=='')
          this.formViewHoanCong.trangbi_id = 0;
        if (formData.thoihan_id == null || formData.thoihan_id=='')
          this.formViewHoanCong.thoihan_id = 0;        
        if (formData.tinh_kn_id == null || formData.tinh_kn_id=='')
          this.formViewHoanCong.tinh_kn_id = 0; 
        
        let vloaitb_id = parseInt(formData.loaitb_id);
        if (vloaitb_id == LoaiHinhTB.MEGAWAN_ADSL || vloaitb_id == LoaiHinhTB.MEGAWAN_SHDSL || vloaitb_id == LoaiHinhTB.MEGAWAN_ADSL_2M)
                this.formViewHoanCong.lbldslam = "Dslam";
        else
                this.formViewHoanCong.lbldslam = "Switch";
        
         this.lay_thongso_kt(formData.port_id,formData.vci_vpi_id)
         this.lay_tramthicong(formData.hdtb_id)
         
         this.lay_tocdo_thaydoi_hopdong((formData.hdtb_id))
         await this.getDanhsachnhanvien()      
         //todo hiện nút hoàn công để test
         //this.setActiveButton('hoancong',true) 
         
      } catch (ex) {
        console.log(ex)
        
      }
    },
    maGDKeyPress(){
      
      if (this.formViewTTKH.ma_gd.trim()!=''){
        let ma_gd = this.formViewTTKH.ma_gd.trim()
        this.searchOne=true
        this.search_GD=this.formViewTTKH.ma_gd.trim()
        this.clearForm()
        this.layDanhSachHDTB(ma_gd)               
      }
    },
    maTBKeyPress(){
      if (this.formViewTTKH.ma_tb.trim()!=''){ 
        this.searchOne=true
        this.search_TB=this.formViewTTKH.ma_tb.trim()
        let ma_tb = this.formViewTTKH.ma_tb.trim()
        this.clearForm()    
        this.layDanhSachHDTB(ma_tb)                         
      }
    },
    selectItem(data) {      
      console.log(data)
    },
    selectItemsChanged(records) {      
      this.selectItems=records
      console.log(records)
    },
    async selectItemThueBao(item) { 
      console.log('selectItemThueBao')
      this.ma_tb=''
      console.log(item)
      if(item.data==null || !item.data) {
        this.clearForm()
        return
      }      
      
      let dr = item.data
      this.ma_tb=dr.ma_tb
      await this.HienThiTTHopDongTB(dr)        
      
      //ẩn hiện khóa nút
      let kt_status = dr.status
      //Nếu phải kích hoạt tổng đài
      if (this.PHAILAM("KICHHOAT_TD"))
      {          
          if (dr.ngay_ht_gp !=null && dr.ngay_ht_gp != "")
          {
            
              this.searchDate.isEnabledToDate = true
              this.searchDate.toDate = moment(dr.ngay_ht_gp, "DD/MM/yyyy HH:mm:ss").format("DD/MM/YYYY hh:mm a")
              this.searchDate.checkToDate = true              
              
              if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString())
              {
                  if (this.PHAILAM("GIAOPHIEU"))
                  {
                      this.setActiveButton('giaophieu',true)
                      this.setActiveButton('kichhoat',false)                      
                  }
                  else
                  {
                      if (this.PHAILAM("HOANTHANH_PHIEU"))
                      {                          
                        this.setActiveButton('hoancong',false)
                        this.setActiveButton('hoanthien',true)
                      }
                      else
                      {
                        this.setActiveButton('hoancong',true)
                        this.setActiveButton('hoanthien',false)
                      }
                      this.setActiveButton('kichhoat',false)                      
                  }
              }
              else
              {                     
                  this.setActiveButton('giaophieu',false)
                  this.setActiveButton('kichhoat',true)                  
                  this.setActiveButton('hoancong',false)                  
                  this.setActiveButton('hoanthien',false)
              }
          }
          else
          {              
              this.searchDate.isEnabledToDate = false              
              this.searchDate.checkToDate = false
              this.searchDate.toDate = moment(new Date()).add(30, 'seconds').format("DD/MM/YYYY hh:mm a")                
              this.setActiveButton('giaophieu',false)
              this.setActiveButton('kichhoat',false)              
              this.setActiveButton('hoancong',false)              
          }
      }
      else
      {
          if (this.PHAILAM("GIAOPHIEU")) //Nếu phải giao phiếu
          {              
              if (dr.ngay_ht_gp !=null && dr.ngay_ht_gp != "")
              {                  
                  this.searchDate.isEnabledToDate = true              
                  this.searchDate.checkToDate = true                  
                  this.searchDate.toDate = moment(dr.ngay_ht_gp, "DD/MM/yyyy HH:mm:ss").format("DD/MM/YYYY hh:mm a")                   
                  this.setActiveButton('giaophieu',true)
                  this.setActiveButton('kichhoat',false)                  
                  this.setActiveButton('hoancong',false)                    
              }
              else
              {                  
                  this.searchDate.isEnabledToDate = false              
                  this.searchDate.checkToDate = false
                  this.searchDate.toDate = moment(new Date()).add(30, 'seconds').format("DD/MM/YYYY hh:mm a")                
                  this.setActiveButton('giaophieu',false)
                  this.setActiveButton('kichhoat',false)
                  this.setActiveButton('hoancong',false)                   
              }
          }
          else  // Nếu không phải giao phiếu
          {              
              if (this.PHAILAM("HOANCONG"))
              {
                  if (dr.ngay_ht_gp !=null && dr.ngay_ht_gp != "")
                  {                    
                      this.searchDate.isEnabledToDate = true              
                      this.searchDate.checkToDate = true                      
                      this.searchDate.toDate = moment(dr.ngay_ht_gp, "DD/MM/yyyy HH:mm:ss").format("DD/MM/YYYY hh:mm a")
                      
                      this.setActiveButton('hoancong',true) 

                  }
                  else
                  {           
                      this.searchDate.isEnabledToDate = false              
                      this.searchDate.checkToDate = false
                      this.searchDate.toDate = moment(new Date()).add(30, 'seconds').format("DD/MM/YYYY hh:mm a")                
                      this.setActiveButton('hoancong',false)
                  }
              }
          }
      }

      if (this.PHAILAM("HOANTHIENHOSO"))
      {
          if (dr.ngay_ht_gp !=null && dr.ngay_ht_gp != "")
          {              
              this.searchDate.isEnabledToDate = true              
              this.searchDate.checkToDate = true              
              this.searchDate.toDate = moment(dr.ngay_ht_gp, "DD/MM/yyyy HH:mm:ss").format("DD/MM/YYYY hh:mm a")              
              this.setActiveButton('hoanthien',true)
          }
          else
          {              
              this.searchDate.isEnabledToDate = false              
              this.searchDate.checkToDate = false
              this.searchDate.toDate = moment(new Date()).add(30, 'seconds').format("DD/MM/YYYY hh:mm a")                
              this.setActiveButton('hoanthien',false)
          }
      }

      if (!this.PHAILAM("GIAOVIEC"))
      {          
        console.log('this.PHAILAM("KICHHOAT_TD") 7')
          this.searchDate.isEnabledToDate = false              
          this.searchDate.checkToDate = true          
          this.searchDate.isEnabledcheckToDate = false

          this.searchDate.isEnabledFromDate = false              
          this.searchDate.checkFromDate = true
          this.searchDate.isEnabledcheckFromDate = false

      }
    },
    btnHuongKN_Click() {
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
          this.$toast.error("Bạn chưa chọn phiếu!");  
          return          
      }      
      this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popupDSHuongKN');
      this.popupComponentName = "popupDSHuongKN";
      this.popupComponentData = { kieu:9, dichvuvt_id: this.dichvuvt_id, tinhkn_id:this.formViewHoanCong.tinh_kn_id }; // O day dung enums CHUQUAN.VNPT_TINH nhung chi co 1 gia tri duy nhat = 9 nen loai bo enums luon!
      this.popupComponentEvts = {
        'form-close': this.popupClosed,
        'acceptChangeCTV': this.acceptChangeCTV,
        'acceptChangeNGT': this.acceptChangeNGT,
      }
      this.Popup('popupComponents');
    },
    acceptChangeNGT(){

    },
    acceptChangeCTV(){

    },
    //btn chọn port TODO: api lấy port theo phieu chua hd
    async chonPort() {      
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
          this.$toast.error("Hãy chọn một thuê bao để chọn port!");  
          return          
        }      
      try {
        let modal = this.$refs["ref-chon-port"];
        // let thamso = await this.axios.post(
        //   "web-thicong/thicong-internet/fn_kiemtra_lay_port_macdinh_hdtb",
        //   {
        //     'phieu_id': this.formViewTTKH.phieu_id,
        //   }
        // );

        // let thamso_data = JSON.parse(thamso.data.data);
        // if (thamso_data.ERROR_CODE == "-1") {
        //   this.$toast.error("Không tìm thấy dữ liệu!");
        // }

        modal.frmChonPort(
          this.formViewHoanCong.donvi_id,// thamso_data.RESULT.DONVI_ID,
          this.formViewHoanCong.tramtb_id,//thamso_data.RESULT.TRAMTB_ID,
          this.port_id,//thamso_data.RESULT.PORT_ID,
          0,//thamso_data.RESULT.KIEU,
          this.dichvuvt_id,//parseInt(thamso_data.RESULT.DICHVUVT_ID),
          this.formViewTTKH.loaitb_id,//parseInt(thamso_data.RESULT.LOAITB_ID),
          this.formViewTTKH.diachi_id,//thamso_data.RESULT.DIACHI_ID,
          null,//thamso_data.RESULT.THAMSO,
          this.formViewHoanCong.hdtb_id,//thamso_data.RESULT.HDTB_ID,
          this.formViewTTKH.thuebao_id,//thamso_data.RESULT.THUEBAO_ID,
          null,
          this.formViewHoanCong.tramtb_id,//thamso_data.RESULT.TRAMTB_ID
        );

        modal.show();
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi xảy ra");
      }
    },
    async onSelectPort(port){      
      try {      
        this.bras_id = port.bras_id!=null?port.bras_id:0
        this.dslam_id = port.dslam_id!=null?port.dslam_id:0
        this.formViewHoanCong.ipdslam =port.ip_dslam_tinh
        this.formViewHoanCong.adsl_port = port.adsl_port!=null?port.adsl_port.toString():''
        this.formViewHoanCong.port_mdf = port.port_mdf!=null?port.port_mdf.toString():''
        this.formViewHoanCong.slot = port.slot!=null?port.slot.toString():''
        this.formViewHoanCong.svlan = port.vpi!=null?port.vpi.toString():''
        this.formViewHoanCong.cvlan = port.vci!=null?port.vci.toString():''
        this.formViewHoanCong.system = port.system!=null?port.system.toString():''
        this.formViewHoanCong.rack = port.rack!=null?port.rack.toString():''
        this.formViewHoanCong.tendslam = port.ten_dslam

        this.formViewHoanCong.shelf = port.shelf!=null?port.shelf.toString():''

        this.formViewHoanCong.shelf01 = port.selfid01!=null?port.selfid01.toString():''
        this.formViewHoanCong.shelf02 = port.selfid02!=null?port.selfid02.toString():''
        this.formViewHoanCong.shelf03 = port.selfid03!=null?port.selfid03.toString():''
        this.formViewHoanCong.shelf04 = port.selfid04!=null?port.selfid04.toString():''

        this.formViewHoanCong.congnghe_id=port.congnghe_id!=null?port.congnghe_id:0
        
        this.port_id=port.port_id!=null?port.port_id:0 //gán 2 thông tin mới, giữ lại 2 thông tin cũ để so sánh
        this.vci_vpi_id=port.vci_vpi_id!=null?port.vci_vpi_id:0

        this.formViewHoanCong.tramtb_id=port.tramtb_id!=null?port.tramtb_id:0        
        if (port.selfid01!=null && port.selfid01!='' && port.selfid01!='null'){
          this.formViewHoanCong.nasport_id = port.system + "/"
                                + port.selfid04 + "/"
                                + port.selfid03 + "/"
                                + port.selfid02 + "/"
                                + port.selfid01
        }
        else this.formViewHoanCong.nasport_id = port.system

        await this.capnhat_port()
        // this.clearForm()
        // this.layDanhSachHDTB('')
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi xảy ra");
      }
    },
    async capnhat_port(){
      
      try {
        let vport_id=this.formViewHoanCong.port_id
        let vvci_vpi_id=this.formViewHoanCong.vci_vpi_id
        let port_id=this.port_id
        let vci_vpi_id=this.vci_vpi_id

        //gọi hàm cnhat port
        let AON_CHI_CHON_DSLAM_TINH = 0
        let IS_AON_TINH = 0 //TODO: THAM SỐ NÀY KIỂM TRA LẠI TRÊN FORM CŨ THẤY KO CÓ ĐẦU RA
        let ktra_port = await this.fn_kiemtra_chonport_tdts_mgwan(this.formViewTTKH.hdtb_id,this.luong_id)
        if (ktra_port && ktra_port.AON_CHI_CHON_DSLAM_TINH && ktra_port.AON_CHI_CHON_DSLAM_TINH==1) {
          AON_CHI_CHON_DSLAM_TINH = 1
        }

        let res = await this.fn_capnhat_port_tdts_mgwan(this.formViewTTKH.hdtb_id,this.luong_id,port_id,vci_vpi_id,vport_id,vvci_vpi_id,AON_CHI_CHON_DSLAM_TINH,IS_AON_TINH,this.dslam_id)

        await this.Capnhat_MGWAN(this.formViewTTKH.hdtb_id,1) //kieu=1: cập nhật port; 2: cập nhật chính thức
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi xảy ra");
      }

    },
    async fn_capnhat_port_tdts_mgwan(HDTB_ID, LUONG_ID, PORT_ID, VCI_VPI_ID,PORT_ID_GRID,VCI_VPI_ID_GRID,CHICANCHON_DSLAM,IS_AON_TINH,DSLAM_TINH_ID) {
      try {
          this.loading(true)
          let input= 
          {
            "HDTB_ID":HDTB_ID,
            "LUONG_ID":LUONG_ID,
            "PORT_ID":PORT_ID,
            "VCI_VPI_ID":VCI_VPI_ID,
            "PORT_ID_GRID":PORT_ID_GRID,
            "VCI_VPI_ID_GRID":VCI_VPI_ID_GRID,
            "CHICANCHON_DSLAM":CHICANCHON_DSLAM,
            "IS_AON_TINH":IS_AON_TINH,
            "DSLAM_TINH_ID":DSLAM_TINH_ID,
            "NGUOI_CN":this.$root.token.getNguoiDungID(),
            "MAY_CN":this.$root.token.getMachine()

            }                
          let data = {
            "phanvung_id":26,
            "ds_para": JSON.stringify(input)
          }
          let response = await this.$root.context.post(`/web-hopdong/thaydoi_thongso_megawan/fn_capnhat_port_tdts_mgwan`, data)

          if (response.error_code === 'BSS-00000000' && response.data != null) {
              return response.data
          }
          else {
            this.loading(false)
          }

          throw {response: { data: response}}
      } catch (err) {
          this.loading(false)
          return []
      }
    },
    //Cập nhật megawan kieu=1: cập nhật port; 2: cập nhật chính thức
    async Capnhat_MGWAN(hdtb_id, kieu) {             
      let ma_tb = this.formViewTTKH.ma_tb
      try {
        this.loading(true)
        await api.sp_lay_hdtb_mgwan_theo_hdtb_id(this.axios,  hdtb_id ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&&response.data.data.length>0) {            
            //tạo dữ liệu
            let data_hdtb =  response.data.data[0]
            let new_info = {
                  thuebao_id: this.formViewTTKH.thuebao_id,
                  hdtb_id: this.formViewTTKH.hdtb_id,
                  ma_tb: this.formViewTTKH.ma_tb,
                  port_id: this.port_id,
                  port_id_cu: !this.formViewHoanCong.port_id ? null : this.formViewHoanCong.port_id,
                  vci_vpi_id: this.vci_vpi_id,
                  vci_vpi_id_cu: !this.formViewHoanCong.vci_vpi_id ? null : this.formViewHoanCong.vci_vpi_id,
                  bras_id: this.bras_id,
                  bras_id_cu: !this.formViewHoanCong.bras_id ? null : this.formViewHoanCong.bras_id,
                  dslam_id: this.dslam_id,
                  dslam_id_cu: !this.formViewHoanCong.dslam_tinh_id ? null : this.formViewHoanCong.dslam_tinh_id,
                  trangthai_id: 1,
                  ip: this.formViewHoanCong.ipdslam,
                  //ghichu: this.formViewTTKH.ghichu_tb,
                  lan_ip_cu: !this.formViewHoanCong.lan_ip ? null : this.formViewHoanCong.lan_ip,
                  lan_ip: !this.formViewHoanCong.lan_ip ? null : this.formViewHoanCong.lan_ip,
                  wan_ip_cu: !this.formViewHoanCong.wan_ip ? null : this.formViewHoanCong.wan_ip,
                  wan_ip: !this.formViewHoanCong.wan_ip ? null : this.formViewHoanCong.wan_ip,
                  tocdo_id:this.formViewHoanCong.tocdo_id,                
                  tocdo_pir_id:this.formViewHoanCong.tocdo_pir_id!=0?this.formViewHoanCong.tocdo_pir_id:'',
                  loaimd_id:this.formViewHoanCong.loaimd_id,
                  loaikenh_id:this.formViewHoanCong.loaikenh_id,

                  thietbidc_id:this.formViewHoanCong.thietbidc_id,
                  ma_lt:this.formViewTTKH.ma_lt,
                  huong_kn:this.vhuongkn,
                  dslam:this.formViewHoanCong.tendslam,
                  chuquan_id:this.formViewHoanCong.chuquan_id,
                  trangbi_id:this.formViewHoanCong.trangbi_id,
                  thoihan_id:this.formViewHoanCong.thoihan_id,
                  ten_tbi:this.formViewHoanCong.ten_tbi,
                  cuoc_tk:this.formViewHoanCong.cuoc_tk,
                  cuoc_tc:this.formViewHoanCong.cuoc_tc,
                  svlan: !this.formViewHoanCong.svlan ? null : this.formViewHoanCong.svlan,
                  cvlan:!this.formViewHoanCong.cvlan ? null : this.formViewHoanCong.cvlan,
                  vrf:!this.formViewHoanCong.vrf ? null : this.formViewHoanCong.vrf,
                  link:!this.formViewHoanCong.link ? null : this.formViewHoanCong.link,
                  route:!this.formViewHoanCong.route ? null : this.formViewHoanCong.route,  
                  diachi_kn: this.formViewHoanCong.diachi_kn,             
                  site_id: this.formViewHoanCong.site_id,
                  tinh_kn_id:this.formViewHoanCong.tinh_kn_id,
                  dslam_tinh_id:this.formViewHoanCong.dslam_tinh_id
            }
          
            for (let key in new_info) {
              this.$set(data_hdtb, key, new_info[key]);            
            }  
            data_hdtb = this.changeKeysToUpper(data_hdtb)

            //end tạo dữ liệu
            let kqcapnhat=null
            if (kieu==1) {
              this.sp_update_hdtb_mgwan(data_hdtb)            
            }
            else {
              this.sp_hths_thaydoi_ts_mgwan_capnhat(data_hdtb)            
            }
            
          }
          else {            
            this.loading(false)     
            return null;
          }

        });
      } catch (err) {
        console.log(err);
        this.loading(false)     
        this.$toast.error("Có lỗi xảy ra");
      }
    },
    async fn_kiemtra_chonport_tdts_mgwan(hdtb_id,luong_id) { 
      try {
        let input ={
        "hdtb_id":hdtb_id,
        "luong_id":luong_id
        }
        let data = {ds_para: JSON.stringify(input)}     
        await api.fn_kiemtra_chonport_tdts_mgwan(this.axios,  data ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
              return response.data.data
          }
        });
      }
      catch(ex){
        return null
      }
    },
    async ktra_dk_port(ma_tb,  port_id,  vci_vpi_id,  kieu) {
      try {
          let data= {
              ma_tb : ma_tb,
              port_id:port_id ,
              vci_vpi_id:vci_vpi_id,
              kieu:kieu   
          }
          let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/ktra_dk_port`, data)

          if (response.error_code === 'BSS-00000000' && response.data != null) {
              return response.data
          }

          throw {response: { data: response}}
      } catch (err) {
          return []
      }
    },
    //CẬP NHẬT PORT
    async sp_update_hdtb_mgwan(ds_hdtb) {       
      let ma_tb = this.formViewTTKH.ma_tb
      
      try {
        let data = {
          "ds_hdtb_mgwan_data": JSON.stringify(ds_hdtb),        
          };      
        await api.sp_update_hdtb_mgwan(this.axios, data).then((res) => {   
          this.loading(false)  
          if (res.data && res.data.error_code=="BSS-00000000" ) {            
            // let kq = JSON.parse(res.data.data)
            // if (kq && kq.KETQUA && kq.KETQUA==1){
              this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!"); 
              this.clearForm()
              this.layDanhSachHDTB('')            
            // }
            // else {
            //   this.$toast.error("Cập nhật thất bại!");
            // }
          }
          else {
              this.$toast.error("Cập nhật thất bại!");
            }
        });
      }
      catch(err){
        this.loading(false)
        return null
      }
    },
    //CẬP NHẬT HỢP ĐỒNG
    async sp_hths_thaydoi_ts_mgwan_capnhat(ds_hdtb) { 
      let ma_tb = this.formViewTTKH.ma_tb
      
      try {        
        let input_ds_para = {
          HDTB_ID:this.formViewTTKH.hdtb_id,
          PHIEU_ID:this.formViewTTKH.phieu_id,
          LUONG_ID:this.luong_id,
          NHANVIEN_HC_ID:this.$root.token.getNhanVienID(),
          NHANVIEN_GV_ID:this.formViewGiaoViec.nguoi_gv_id,
          THUEBAO_CHA_ID:this.in_thuebao_cha_id,
          TINH_KN_ID:this.formViewHoanCong.tinh_kn_id,
          NGAY_HT:moment(this.searchDate.toDate,'DD/MM/YYYY hh:mm:ss a').format("DD/MM/YYYY HH:mm:ss"),
          HUONG_KN:this.vhuongkn,
          NGAY_GIAO:moment(this.searchDate.fromDate,'DD/MM/YYYY hh:mm a').format("DD/MM/YYYY HH:mm:ss"),
        }        
        let ds_giaophieu_nv = this.tableConfigNGV.tableData
        ds_giaophieu_nv = this.changeKeysToUpper(ds_giaophieu_nv)
        let data = {
          "ds_para":JSON.stringify(input_ds_para),
          "giaophieu_nv_data":'',        
          "hdtb_mgwan_data": JSON.stringify(ds_hdtb),        
          };      
        await api.sp_hths_thaydoi_ts_mgwan_capnhat(this.axios, data).then((res) => {   
          this.loading(false)     
          if (res.data && res.data.data ) {            
            let kq = JSON.parse(res.data.data)
            if (kq && kq.KETQUA && kq.KETQUA==1){
              this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!"); 
              this.clearForm()
              this.layDanhSachHDTB('')                      
            }
            else {
              this.$toast.error("Cập nhật thất bại!");
            }
          }
          else if (res.message_detail) {
            this.$toast.error(res.message_detail);
          }
          else {
            this.$toast.error("Có lỗi khi gọi hàm cập nhật!");
          }
        });
      }
      catch(err){
        this.loading(false)     
        return null
      }
    },
    
    PHAILAM: function (code) {
      let dr = this.dsThaoTac.filter((x) => x.enable == 1 && x.code == code);            
      if (dr.length > 0) return true;
      return false;
    },
    
    onChangeDauNoi() {
      this.clearForm()
      //load lại danh sách phiếu
      this.layDanhSachHDTB('')      
    },
    hideDauNoi() {
      this.$refs['modal-daunoi-thuebao'].hide()
    },
    async btnGiaoViec_Click() {
      if (this.tableConfigDSThueBao.tableData.length <= 0) {
        this.$toast.error("Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại! ");
        return;
      }      
      if (
        this.options.nguoiGV.length <= 0|| this.formViewGiaoViec.nguoi_gv_id==null || this.formViewGiaoViec.nguoi_gv_id==0) {
        this.$toast.error("Hãy nhập nhân viên giao việc!");
        return;
      }
      if (!this.searchDate.checkFromDate || this.searchDate.fromDate=='')
      {
        this.$toast.error("Hãy nhập ngày giao việc!");
        $("#chkngaygv").focus();
        return;
      }
      this.formViewGiaoViec.ngayGV = moment(this.searchDate.fromDate,'DD/MM/YYYY hh:mm a').format('DD/MM/YYYY hh:mm:ss')
      this.$refs.formgiaophieu.init();
      this.$refs.popupGiaoPhieuNV.show();
    },
    async btnNDThuchienClick() {
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
          this.$toast.error("Bạn chưa chọn phiếu!");  
          return          
        }
      try {
          let ma_tb= this.formViewTTKH.ma_tb                   
          if (this.formViewHoanCong.nd_thuchien && this.formViewHoanCong.nd_thuchien.trim().length>2000) {
            this.$toast.error("Nội dung thực hiện nhập dữ liệu vượt quá độ dài cho phép là 2000 ký tự!")
            return
          }
          let data = {
            "phieu_id":this.formViewTTKH.phieu_id,
            "nd_thuchien":this.formViewHoanCong.nd_thuchien
            }          
          await api.update_nd_thuchien(this.axios,  data ).then((response) => {
            if (response &&response.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
                //thông báo thành công
                this.$toast.success("Cập nhật thành công!");
                this.clearForm()
                this.layDanhSachHDTB('')
                
                //this.setFocusRow(ma_tb)
            }
          });
      }
      catch(ex){
        this.$toast.error("Có lỗi:"+ex);
        return null
      }
    },
    giaoviec_success(){      
      //load lại danh sách phiếu giao
      this.getDanhsachnhanvien()
    },
    async btnGiaoPhieu_Click() {
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
          this.$toast.error("Bạn chưa chọn phiếu!");  
          return          
        }
      try {
        if (!!this.quytrinh_id &&this.formViewTTKH.ma_gd) {
          // window.open(
          //   `/#/contract/handoverticket?quytrinh=${this.quytrinh_id}
          //         &huonggiao=${this.huonggiao_id}&madv=${this.dichvuvt_id}&loaihd=${this.formViewTTKH.loaihd_id}&magd=${this.formViewTTKH.ma_gd}`
          // );
          let input = { luong_id: this.luong_id }
      
          let ds_hg = await api.lay_huonggiao_theo_luong_id(this.axios,input)          
          if (ds_hg && ds_hg.data && ds_hg.data.data && ds_hg.data.data.length > 0) {
            console.log(ds_hg)
            //huonggiao_id_dich = ds_hg.data.data[0].huonggiao_id
            await this.$router.push({
              path: '/contract/HandoverTicket',
              query: {
                quytrinh:this.quytrinh_id,
                huonggiao: ds_hg.data.data[0].huonggiao_id,
                magd: this.formViewTTKH.ma_gd.trim(),
                madv:this.dichvuvt_id,
                loaihd:this.formViewTTKH.loaihd_id,
              }
            })
          }
          else {
            console.log('ds_hg')
            await this.$router.push({
              path: '/contract/HandoverTicket',
              query: {
                quytrinh:this.quytrinh_id,
                huonggiao: this.huonggiao_id,
                magd: this.formViewTTKH.ma_gd.trim(),
                madv:this.dichvuvt_id,
                loaihd:this.formViewTTKH.loaihd_id,
              }
            })
          }
          
        } 
      } catch (error) {
        console.log(error)
        this.$toast.error("Có lỗi xảy ra");
      }
    },
    async btnTraPhieu_Click() {
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
          this.$toast.error("Bạn chưa chọn phiếu!");  
          return          
        }
      this.$bvModal.show('formtraphieu')
    },
    // model Dau noi
    async btnDauNoi_Click(){
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
          this.$toast.error("Bạn chưa chọn phiếu!");  
          return          
        }
      let thongTinDauNoi = {
        choPhepDoiTramTbi: false,
        tramTbiId: this.formViewHoanCong.tramtb_id,
        chonTramTbiTheoTB: false,
        thueBaoId: this.formViewHoanCong.thuebao_id,
        kieu: 0
      }

      let modal = this.$refs['modal-daunoi-thuebao']
      modal.setData(thongTinDauNoi)
      modal.show()
    },
    // Kích hoạt
    async btnKichHoat_Click(){ 
       if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
          this.$toast.error("Bạn chưa chọn phiếu!");  
          return          
        }
       try {
          let ma_tb= this.formViewTTKH.ma_tb   
          let input = {
            "HDTB_ID":this.formViewTTKH.hdtb_id,
            "LUONG_ID":this.luong_id,
            "NGAY_HT":moment(new Date()).format('DD/MM/yyyy HH:mm:ss')
          }
          let data = {"ds_para":JSON.stringify(input)} 
        await api.sp_hths_thaydoi_ts_mgwan_kichhoat(this.axios,  data ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
              //thông báo thành công
              this.$toast.success("Kích hoạt thành công");
              this.clearForm()
              this.layDanhSachHDTB('')
              //this.setFocusRow(ma_tb)
          }
        });
      }
      catch(ex){
        return null
      }
      
    },
    // Giao Net
    async btnGiaoNet_Click(){ 
       if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
          this.$toast.error("Bạn chưa chọn phiếu!");  
          return          
        }
       try {
          let input = {
            "HDTB_ID":this.formViewTTKH.hdtb_id,
            "LUONG_ID":this.luong_id,
            "NGAY_HT":moment(new Date()).format('DD/MM/yyyy HH:mm:ss')
          }
          let data = {"ds_para":JSON.stringify(input)} 
        await api.sp_hths_thaydoi_ts_mgwan_giaonet(this.axios,  data ).then((response) => {
          if (res.data && res.data.data ) {                        
            let kq = JSON.parse(res.data.data)            
            if (kq && kq.KETQUA && kq.KETQUA==1){
              this.$toast.success(kq.THONGBAO);  
              this.clearForm()             
              this.layDanhSachHDTB('')
            }
            else {
              this.$toast.error(kq.THONGBAO);
            }
          } else if (res.message_detail) {
            this.$toast.error(res.message_detail);
          }
          else {
            this.$toast.error("Có lỗi khi gọi hàm giao net!");
          }
        });
      }
      catch(ex){
        return null
      }
      
    },
    async btnCapNhat_Click(){ 
      try {
          if (!this.KiemTra_DL()) return;            
          this.Capnhat_MGWAN(this.formViewTTKH.hdtb_id,2)
      }
      catch(ex){
        console.log(ex)
        return null
      }
      
    },
    async btnHoanCong_Click(){ 
      try {
         if (!this.formViewTTKH.hdtb_id || this.formViewTTKH.hdtb_id==0) return 
         //kiểm tra valid ngày, tháng
         if (!this.formViewHoanCong.ngay_ht_gp || this.formViewHoanCong.ngay_ht_gp == ""){
            this.$toast.error("Bạn phải cập nhật ngày hoàn thành trước khi hoàn công!");            
            return;
          }
          if (!this.searchDate.checkToDate){
            this.$toast.error("Bạn chưa nhập ngày hoàn thành!");
            return;
          }
          if (this.searchDate.toDate==""){
            this.$toast.error("Hãy nhập ngày hoàn thành!");
            return;
          }
        let ngay_sys = moment(new Date()).format("DD/MM/yyyy");        
        let ngay_ht = moment(this.searchDate.toDate,'DD/MM/YYYY hh:mm a').format("DD/MM/YYYY")

        if (parseInt(moment(this.searchDate.toDate,'DD/MM/YYYY hh:mm a').format("yyyyMM")) < parseInt(moment(new Date()).format("yyyyMM")))
        {          
            this.$toast.error("Tháng hoàn thành không được nhỏ hơn tháng hiện tại !");
            return;
        }      
        
        if (!this.KiemTraDK_HoanThanh(ngay_ht, ngay_sys))//Nếu ngày thanh toán > ngày hoàn thành
        {
            this.$toast.error("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");          
            return;
        }      
        //end kiểm tra valid ngày, tháng
         
         let nguoi_cn=this.$root.token.getNguoiDungID()
         let may_cn=this.$root.token.getMachine()
         let ip_cn=this.$root.token.getIP()
         let input = {
           HDTB_ID:this.formViewTTKH.hdtb_id,
           DICHVUVT_ID:this.dichvuvt_id,
           PHIEU_ID:this.formViewTTKH.phieu_id,
           NHANVIEN_ID:this.$root.token.getNhanVienID(),
           HUONGGIAO_ID:this.huonggiao_id,
           LUONG_ID:this.luong_id,
           NGAY_HT:moment(this.searchDate.toDate,'DD/MM/YYYY hh:mm a').format("DD/MM/YYYY HH:mm:ss"),
           NGAY_CN:moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
           NGUOI_CN:nguoi_cn,
           MAY_CN:may_cn,
           IP_CN:ip_cn,
           DONVI_ID:this.$root.token.getDonViID(),
         }
         let data = {"ds_para":JSON.stringify(input)} 

         let kq = null
        await api.sp_hths_thaydoi_ts_mgwan_hoancong(this.axios,  data ).then((res) => {
          if (res.data && res.data.data ) {                        
            kq = JSON.parse(res.data.data)            
            if (kq && kq.KETQUA && kq.KETQUA==1){
              this.$toast.success(kq.THONGBAO);              
              
              this.clearForm()             
              this.layDanhSachHDTB('')
            }
            else {
              this.$toast.error(kq.THONGBAO);
            }
          } else if (res.data && res.data.message_detail) {
            this.$toast.error(res.data.message_detail);
          }
          else {
            this.$toast.error("Có lỗi khi gọi hàm hoàn công!");
          }
        });
        if (this.PHAILAM("KT_HOANCONG_HD_BANCHEO") && kq && kq.KETQUA && kq.KETQUA==1){
          let res_bc = await api.hoangcong_hopdong_bancheo({
            hdtb_id:this.formViewTTKH.hdtb_id,
            action_name:'ChangeParameterMegawan',
            note:'Hoàn công TĐTĐ Megawan/Metronet'
          })
          if (res_bc){
            this.$toast.success('Hoàn công hợp đồng bán chéo thành công');  
          } else {
            this.$toast.error('Hoàn công hợp đồng bán chéo thất bại');  
          }
        }
      }
      catch(ex){
        console.log(ex)
        return null
      }
      
    },
    async btnHoanThien_Click(){ 
      try {
        if (!this.formViewTTKH.hdtb_id || this.formViewTTKH.hdtb_id==0) return 
         //kiểm tra valid ngày, tháng
        if (!this.formViewHoanCong.ngay_ht_gp || this.formViewHoanCong.ngay_ht_gp == ""){
            this.$toast.error("Bạn phải cập nhật ngày hoàn thành trước khi hoàn công!");            
            return;
        }
        
        if (!this.searchDate.checkToDate){
            this.$toast.error("Bạn chưa nhập ngày hoàn thành!");
            return;
        }
        
        let ngay_sys = moment(new Date()).format("DD/MM/yyyy");        
        let ngay_ht = moment(this.searchDate.toDate,'DD/MM/YYYY hh:mm a').format("DD/MM/YYYY")

        if (parseInt(moment(this.searchDate.toDate,'DD/MM/YYYY hh:mm a').format("yyyyMM")) < parseInt(moment(new Date()).format("yyyyMM")))
        {          
            this.$toast.error("Tháng hoàn thành không được nhỏ hơn tháng hiện tại !");
            return;
        }      

        if (!this.KiemTraDK_HoanThanh(ngay_ht, ngay_sys))//Nếu ngày thanh toán > ngày hoàn thành
        {
            this.$toast.error("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");          
            return;
        }      
        //end kiểm tra valid ngày, tháng
        
        //gọi api/sp hoàn thiện, trong sp hoàn thiện đã xử lý các điều kiện trong code cũ
        //kết quả trả về trong trường KETQUA=0/1 THONGBAO
        //thông tin Hoàn công bán chéo sẽ được tích hợp sau vào SP
         let nguoi_cn=this.$root.token.getNguoiDungID()
         let may_cn=this.$root.token.getMachine()
         let ip_cn=this.$root.token.getIP()
         let input = {
           HDTB_ID:this.formViewTTKH.hdtb_id,
           DICHVUVT_ID:this.dichvuvt_id,
           PHIEU_ID:this.formViewTTKH.phieu_id,
           NHANVIEN_ID:this.$root.token.getNhanVienID(),
           HUONGGIAO_ID:this.huonggiao_id,
           LUONG_ID:this.luong_id,
           NGAY_HT:moment(this.searchDate.toDate,'DD/MM/YYYY hh:mm a').format("DD/MM/YYYY HH:mm:ss"),
           NGAY_CN:moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
           NGUOI_CN:nguoi_cn,
           MAY_CN:may_cn,
           IP_CN:ip_cn,
           DONVI_ID:this.$root.token.getDonViID(),
         }
         let data = {"ds_para":JSON.stringify(input)} 
        let kq=null
        await api.sp_hths_thaydoi_ts_mgwan_hoanthien(this.axios,  data ).then((res) => {
          if (res.data && res.data.data ) {                        
            kq = JSON.parse(res.data.data)            
            if (kq && kq.KETQUA && kq.KETQUA==1){
              this.$toast.success(kq.THONGBAO);     
              this.clearForm()             
              this.layDanhSachHDTB('')         
            }
            else{
              this.$toast.error(kq.THONGBAO+((res.data && res.data.message && res.data.message_detail)?res.data.message+" - "+res.data.message_detail:""));
            }
          }
          else {
            this.$toast.error("Có lỗi khi hoàn thiện hồ sơ! Vui lòng thử lại sau."+((res.data && res.data.message && res.data.message_detail)?res.data.message+" - "+res.data.message_detail:""));
          }
        });
        if (this.PHAILAM("KT_HOANCONG_HD_BANCHEO") && kq && kq.KETQUA && kq.KETQUA==1){
          let res_bc = await api.hoangcong_hopdong_bancheo({
            hdtb_id:this.formViewTTKH.hdtb_id,
            action_name:'ChangeParameterMegawan',
            note:'Hoàn thiện TĐTĐ Megawan/Metronet'
          })
          if (res_bc){
            this.$toast.success('Hoàn thiện hợp đồng bán chéo thành công');  
          } else {
            this.$toast.error('Hoàn thiện hợp đồng bán chéo thất bại');  
          }
        }
      }
      catch(ex){
        console.log(ex)
        return null
      }
      
    },
    async btnHenLD_Click(){ 
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
        this.$toast.error("Bạn chưa chọn phiếu!");  
        return          
      }
      this.$refs.popupThongTinHenKhachHang.showModal()      
      
    },
    async btnXuatPhieu_Click(){ 
      if (this.tableConfigDSThueBao.tableData.length ==0) {
        this.$toast.error("Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!");  
        return          
      }
      if (!this.selectItems || this.selectItems.length ==0) {
        this.$toast.error("Bạn chưa chọn phiếu!");  
        return          
      }
       try {
        this.InPhieu()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
      
    },
     async InPhieu () {
      try {        
        let nhdkhid = []
        let nhdtbid = []
        let nphieuid = []
        nhdkhid = this.selectItems.map ((item) => item.hdkh_id)
        nhdtbid = this.selectItems.map ((item) => item.hdtb_id)
        nphieuid = this.selectItems.map ((item) => item.phieu_id)
        console.log('InPhieu:')
        console.log('nhdkhid:'+nhdkhid.join(','))
        console.log('nhdtbid:'+nhdtbid.join(','))
        console.log('nphieuid:'+nphieuid.join(','))

        this.modelIn.n_phieu_id = nphieuid.join(',')
        this.modelIn.ma_gd = this.formViewTTKH.ma_gd //todo
        this.modelIn.n_hdkh_id = nhdkhid.join(',')
        this.modelIn.n_hdtb_id = nhdtbid.join(',')
        this.modelIn.nvth_id = this.$root.token.getNhanVienID()
        this.modelIn.huonggiao_id = this.huonggiao_id
        this.modelIn.tentram_tb = ''
        this.$refs.popupInBB.showModal()
      } catch (error) {
        this.$root.$toast.error('Có lỗi khi in phiếu: ' + error.message)
      }
    },
    async btnThongSoKT_Click(){ 
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
        this.$toast.error("Bạn chưa chọn phiếu!");  
        return          
      }
      this.$refs.traCuuThongSoKTModal.showModal()      
    },
    async btnVattu_Click(){ 
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
        this.$toast.error("Bạn chưa chọn phiếu!"); 
        return           
      }
      this.$refs.popupVatTuThueBao.showModal()      
    },
    async btnDoKiem_Click(){ 
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
        this.$toast.error("Bạn chưa chọn phiếu!");  
        return          
      }
      this.$refs.popupKetQuaDoKiem.showModal()      
    },
    async btnDuan_Click(){ 
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
        this.$toast.error("Bạn chưa chọn phiếu!");  
        return          
      }
      this.$refs.popupDuan.showModal()       
    },
    async btnKhuvuc_Click(){ 
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
        this.$toast.error("Bạn chưa chọn phiếu!");  
        return          
      }
      if (this.formViewTTKH.hdtb_id==0) return
      this.dataPopupKhuVuc.hdtb_id = this.formViewTTKH.hdtb_id      
      this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal() 
    },
    async btnSuaKenhThu_Click(){ 
      if (!this.formViewTTKH.phieu_id || this.formViewTTKH.phieu_id ==0) {
        this.$toast.error("Bạn chưa chọn phiếu!");  
        return          
      }
      this.$bvModal.show('formthaydoikenhthu')
    },
    onAcceptKhuVuc(){
    },
    successUpdateThayDoiKhuVucQuanLy(){ 
    },
    onActionClick (action) {
      if (action.id === 'laythongtin') {
        this.searchOne=false
        this.search_GD=''
        this.search_TB=''
        this.clearForm()
        this.layDanhSachHDTB('')
      } else if (action.id === 'capnhat') {
        this.btnCapNhat_Click()
      } else if (action.id === 'giaoviec') {
        this.btnGiaoViec_Click()
      } else if (action.id === 'kichhoat') {
        this.btnKichHoat_Click()
      } else if (action.id === 'giaophieu') {
        this.btnGiaoPhieu_Click()
      } else if (action.id === 'daunoi') {
        this.btnDauNoi_Click()
      } else if (action.id === 'hoancong') {
        this.btnHoanCong_Click()
      }else if (action.id === 'hoanthien') {
        this.btnHoanThien_Click()
      } else if (action.id === 'traphieu') {
        this.btnTraPhieu_Click()
      } else if (action.id === 'xuatphieu') {
        this.btnXuatPhieu_Click()
      } else if (action.id === 'henlapdat') {
        this.btnHenLD_Click()
      } else if (action.id === 'thongsokythuat') {
        this.btnThongSoKT_Click()
      } else if (action.id === 'gankhuvuc') {
        this.btnKhuvuc_Click()
      } else if (action.id === 'vattu') {
        this.btnVattu_Click()
      } else if (action.id === 'duan') {
        this.btnDuan_Click()
      } else if (action.id === 'dokiem') {
        this.btnDoKiem_Click()
      } else if (action.id === 'suakenhthu') {
        this.btnSuaKenhThu_Click()
      } else if (action.id === 'giaonet') {
        this.btnGiaoNet_Click()
      } 
    },
    
    traphieu_success(){
      //load lại danh sách phiếu
      this.clearForm()
      this.layDanhSachHDTB('')
      ///clear form
    },
    KiemTraDK_HoanThanh(thangnam_tt, thangnam_sys) {      
      let result = false;
      if (parseInt(thangnam_tt.substring(6, thangnam_tt.length)) < parseInt(thangnam_sys.substring(6, thangnam_sys.length))) {
          //So sánh năm
          return true;
      }
      //Nếu năm thanh toán < năm hoàn thành thì true;
      else if (parseInt(thangnam_tt.substring(6, thangnam_tt.length)) ==parseInt(thangnam_sys.substring(6, thangnam_sys.length))) {          
          //So sánh năm
          //Nêu năm = nhau thì kiểm tra tiếp
          //Nếu tháng tt < thang ht -> true
          if (parseInt(thangnam_tt.substring(3, 5)) < parseInt(thangnam_sys.substring(3, 5))) {
              result = true;
          }
          else if (parseInt(thangnam_tt.substring(3, 5)) == parseInt(thangnam_sys.substring(3, 5))) {              
              if (parseInt(thangnam_tt.substring(0, 2)) <= parseInt(thangnam_sys.substring(0, 2))) {                  
                  //Nếu tháng & năm = nhau thì kiểm tra ngày
                  return true;
              }
              else return false;
          }
          else result = false; //tháng tt lớn hơn thang ht
      } else { 
        result = false;         
        }      

      return result;
    },
    KiemTra_DL(){      
      if (this.formViewHoanCong.hdtb_id == null || this.formViewHoanCong.hdtb_id == 0)
      {
          this.$toast.error("Không có dữ liệu để Cập nhật !");          
          return false;
      } 
      console.log('KiemTra_DL')
      console.log(this.formViewGiaoViec.nguoi_gv_id)
      if (this.formViewGiaoViec.nguoi_gv_id==0) {
        this.$toast.error("Hãy nhập người giao việc!");
        return;
      }
      if (!this.searchDate.checkFromDate)
      {
        this.$toast.error("Hãy nhập ngày giao việc!");
        $("#chkngaygv").focus();
        return;
      }      
      if (!this.searchDate.checkToDate)
      {
          this.$toast.error("Hãy nhập ngày hoàn thành!");
          $("#chkngayht").focus();         
          return false;
      }

      
      let dr={}
      if (this.formViewHoanCong) { 
        dr = this.formViewHoanCong        
      }
      else return false

      let ngay_sys = moment(new Date()).format("DD/MM/yyyy");
      //ngay_tt = dr.thangnam_tt.toString().trim();
      let ngay_ht = moment(this.searchDate.toDate,'DD/MM/YYYY hh:mm a').format("DD/MM/YYYY")
      //ngay_capnhat = dr.thangnam_sys.toString().trim();


      if (parseInt(moment(this.searchDate.toDate,'DD/MM/YYYY hh:mm a').format("yyyyMM")) < parseInt(moment(new Date()).format("yyyyMM")))
      {          
          this.$toast.error("Tháng hoàn thành không được nhỏ hơn tháng hiện tại !");
          return false;
      }      

      if (!this.KiemTraDK_HoanThanh(ngay_ht, ngay_sys))//Nếu ngày thanh toán > ngày hoàn thành
      {
          this.$toast.error("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");          
          return false;
      }      

      if (this.PHAILAM("GIAOVIEC"))
      {
          if (this.tableConfigNGV.tableData.length <= 0)
          {
              this.$toast.error("Hãy nhập nhân viên thực hiện!");
              return false;
          }
      }

      return true;      
    },
    ChuanHoaTen(ten) {
      if (!ten) return ''
      let tenNew = ''
      let strCon = ten.trim()
      let strXl = ''
      let k = 0
      let found = true
      if (strCon.length < 1) return
      strCon = strCon.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = strCon.indexOf(' ')
          if (k > 0) {
            strXl = strCon.substring(0, k)
            strCon = strCon.substring(k + 1)
            strXl = this.ChuyenChuHoa(strXl)
            if (tenNew !== '') {
              tenNew += ' ' + strXl
            } else tenNew = strXl
            found = true
          } else found = false
        }
        if (strCon !== '') {
          strXl = strCon
          strXl = this.ChuyenChuHoa(strXl)
          if (tenNew !== '') {
            tenNew = tenNew + ' ' + strXl
          } else {
            tenNew = strXl
          }
        }
        return tenNew
      } catch (e) {
        return ten
      }
    },
    changeKeysToUpper(obj) {
      var key, upKey;
      for (key in obj) {
          if (obj.hasOwnProperty(key)) {
              upKey = key.toUpperCase();
              if (upKey !== key) {
                  obj[upKey] = obj[key];
                  delete(obj[key]);
              }
              // recurse
              if (typeof obj[upKey] === "object") {
                  this.changeKeysToUpper(obj[upKey]);
              }
          }
      }
      return obj;
    },
    async Popup(modalId) { 
      this.$bvModal.show(modalId); 
    }, 
    async ClosePopup(modalId) { 
      this.$bvModal.hide(modalId); 
    }, 

    popupClosed(modal){      
      this.$bvModal.hide('popupComponents'); 
      if (this.popupComponentName == "popupDSHuongKN" && modal){
        this.formViewHoanCong.huong_kn = modal.shuongkn
        this.vhuongkn = modal.vma_tb
        this.in_thuebao_cha_id = modal.iid_sochinh
        this.formViewHoanCong.tinh_kn_id = modal.itinh_kn_id        
        
      }
    },
    setFocusRow(ma_tb){      
      let index = this.tableConfigDSThueBao.tableData.findIndex((x) => x.ma_tb == ma_tb);            
      if (index >= 0) {
        this.$refs.gridDSThueBao.selectRow(index,true);
        //this.$refs.gridDSThueBao.setCurrentSelectedRow(index);
      }
      else this.$refs.gridDSThueBao.selectRow(0,true);//this.$refs.gridDSThueBao.setCurrentSelectedRow(0);
    },

    clearForm(){
      //TODO
      this.vhuongkn=""
      this.port_id=0
      this.vci_vpi_id=0
      this.searchDate.isEnabledFromDate=false
      this.searchDate.isEnabledToDate=false
      this.searchDate.checkFromDate=false
      this.searchDate.checkToDate=false
      this.searchDate.isEnabledcheckFromDate=true
      this.searchDate.isEnabledcheckToDate=true
      this.searchDate.fromDate = moment(new Date()).format('DD/MM/yyyy hh:mm a')
      this.searchDate.toDate = moment(new Date()).format('DD/MM/yyyy hh:mm a')

      this.checkbox = {
        //btnHuongKN:false,
        ckbTocdoPir:false,
        ckbTocdoNix:false,
        ckbTocdoIsp:false,
      }
      // this.enable ={
      //   TocdoPir:false,
      //   TocdoNix:false,
      //   TocdoIsp:false,
      // },
      //xóa thông tin trên form
      this.formViewTTKH = {
        phieu_id:0,
        hdtb_id:0,
        hdkh_id:0,
        loaihd_id:0,
        dichvuvt_id: 0,
        loaitb_id: 0,
        ma_gd: "",
        ten_kieuld: "",
        quytrinh_id: 0,
        ma_tb: "",
        ma_lt: "",
        ten_tb: "",
        diachi_ld: "",
        nd_giao: "",
        nd_tra: "", //xem lại
        ghichu_tb: "",
        thuebao_id:0,
        diachi_id:0,
        ngaygiao:"",
      }

      // this.formViewGiaoViec = {
      //     ngayGV: "",
      //     ngayHT: "",
      //     nguoi_gv_id: 0,        
      // }

      this.formViewHoanCong = {
          loaikenh_id: "",
          chuquan_id: "",
          trangbi_id: "",
          thietbidc_id: "",
          loaimd_id: "",
          nhanvien_id:0,
          nhanvien_yc: "",
          tocdo_id: "",
          tocdo_pir_id: "",
          tocdo_nix: "",
          tocdo_isp: "",
          thoihan_id: "",
          thaydoi_td: "",
          diachi_kn: "",
          huong_kn: "",
          tendslam: "",
          adsl_port: "",
          nasport_id: "",
          wan_ip: "",
          lan_ip: "",
          dslam_tinh_id: "",
          dslam_tinh:"",
          link: "",
          site_id: "",        
          tinh_kn_id:0,
          port_mdf: "",
          ipdslam: "",
          svlan: "",
          cvlan: "",
          vrf: "",
          route: "",
          tramtb_id:0, //trạm port
          donvi_id:0, //trạm thi công loaidv=5
          ten_tbi:"",
          lbldslam:"Dslam",
          port_id:0,
          bras_id:0,
          slot:"",
          system:"",
          rack:"",
          shelf:"",
          shelf01:"",
          shelf02:"",
          shelf03:"",
          shelf04:"",
          congnghe_id:0,
          vci_vpi_id:0,
          cuoc_tc:0,
          cuoc_tk:0,	
          nd_thuchien:""	
        }

      this.tableConfigNGV.tableData=[]
      this.modelIn={
        phieu_id:0,
        ma_gd:'',
        hdkh_id:0,
        nvth_id:-1,
        huonggiao_id:0,
        tentram_tb:''
      }
      this.selectItems=[]
    }
  },
};
</script>
