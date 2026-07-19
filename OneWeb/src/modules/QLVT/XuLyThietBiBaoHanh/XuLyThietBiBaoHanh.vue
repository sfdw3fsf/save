<template src='./XuLyThietBiBaoHanh.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import breadcrumb from '@/components/breadcrumb'
import XuLyThietBiBaoHanhAPI from  '../api/XuLyThietBiBaoHanhAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/xulythietbibaohanh'
import select2 from '@/components/Select2.vue'
import { L10n } from '@syncfusion/ej2-base'
import { Freeze, Group, Page, Filter, Sort, Resize, ForeignKey} from "@syncfusion/ej2-vue-grids";
import XLSX from "xlsx";
import {previewPrint} from "@/utils/util"
import PhieuBaoHanhPSIS from "../PhieuBaoHanhPSIS";
import TraCuuTheoSoLoPhieuPSIS from "../TraCuuTheoSoLoPhieuPSIS";
L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

export default {

  components: { XLSX, breadcrumb, appSelect2: select2,PhieuBaoHanhPSIS,TraCuuTheoSoLoPhieuPSIS },
  name: "XuLyThietBiBaoHanh",
  mounted() {
  },
  provide: {
    grid: [ Freeze, Group, Page,Filter,Sort,Resize, ForeignKey ]
  },
  computed: {
    ...mapState("xulythietbibaohanh", statePropertyName),
    ...mapGetters("xulythietbibaohanh", getterName),
    p_mathuebao: {
      get() {
        return this.params.p_mathuebao
      },
      set(value) {
        return this.params.p_mathuebao = value
      }
    },
    p_tenthuebao: {
      get() {
        return this.params.p_tenthuebao
      },
      set(value) {
        return this.params.p_tenthuebao = value
      }
    },
    p_diachi: {
      get() {
        return this.params.p_diachi
      },
      set(value) {
        return this.params.p_diachi = value
      }
    },
    p_donvi: {
      get() {
        return this.params.p_donvi
      },
      set(value) {
        return this.params.p_donvi = value
      }
    },
    p_nhanvien: {
      get() {
        return this.params.p_nhanvien
      },
      set(value) {
        return this.params.p_nhanvien = value
      }
    },
    p_ngay_tn: {
      get() {
        return this.params.p_ngay_tn
      },
      set(value) {
        return this.params.p_ngay_tn = value
      }
    },
    p_dienthoai_lh: {
      get() {
        return this.params.p_dienthoai_lh
      },
      set(value) {
        return this.params.p_dienthoai_lh = value
      }
    },
    p_kieubh: {
      get() {
        return this.params.p_kieubh
      },
      set(value) {
        return this.params.p_kieubh = value
      }
    },
    p_loai_tb: {
      get() {
        return this.params.p_loai_tb
      },
      set(value) {
        return this.params.p_loai_tb = value
      }
    },
    p_mac: {
      get() {
        return this.params.p_mac
      },
      set(value) {
        return this.params.p_mac = value
      }
    },
    p_soseri: {
      get() {
        return this.params.p_soseri
      },
      set(value) {
        return this.params.p_soseri = value
      }
    },
    p_serialmoi: {
      get() {
        return this.params.p_serialmoi
      },
      set(value) {
        return this.params.p_serialmoi = value
      }
    },
    p_tinhtrang: {
      get() {
        return this.params.p_tinhtrang
      },
      set(value) {
        return this.params.p_tinhtrang = value
      }
    },
    p_phukien: {
      get() {
        return this.params.p_phukien
      },
      set(value) {
        return this.params.p_phukien = value
      }
    },
    p_phibh: {
      get() {
        return this.params.p_phibh
      },
      set(value) {
        return this.params.p_phibh = value
      }
    },
    p_ghichu: {
      get() {
        return this.params.p_ghichu
      },
      set(value) {
        return this.params.p_ghichu = value
      }
    },
    p_linhkien: {
      get() {
        return this.params.p_linhkien
      },
      set(value) {
        return this.params.p_linhkien = value
      }
    },
    p_ketquabh: {
      get() {
        return this.params.p_ketquabh
      },
      set(value) {
        return this.params.p_ketquabh = value
      }
    },
    p_trangthaibhdich: {
      get() {
        return this.params.p_trangthaibhdich
      },
      set(value) {
        return this.params.p_trangthaibhdich = value
      }
    },
    p_kho: {
      get() {
        return this.params.p_kho
      },
      set(value) {
        return this.params.p_kho = value
      }
    },
    p_tukho: {
      get() {
        return this.params.p_tukho
      },
      set(value) {
        return this.params.p_tukho = value
      }
    },
    p_baohanh_id: {
      get() {
        return this.params.p_baohanh_id
      },
      set(value) {
        return this.params.p_baohanh_id = value
      }
    },
    checkedNguonItems: {
      get() {
        return this.$refs.tableNguon.getSelectedRecords()
      }
    },
    checkedDichItems: {
      get() {
        return this.$refs.tableDich.getSelectedRecords()
      },
    }
  },
  data() {
    return {
        header: {
            title: "XỬ LÝ THIẾT BỊ BẢO HÀNH",
            list: [
            { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
            {
                name: "Lắp đặt mới",
                link: { name: "Ui.buttons" },
            },
            ],
        },
        params: {
            p_mathuebao:'',
            p_tenthuebao:'',
            p_diachi: '',
            p_donvi: '',
            p_nhanvien: '',
            p_ngay_tn: '',
            p_dienthoai_lh: '',
            p_kieubh: '',
            p_loai_tb: '',
            p_mac: '',
            p_soseri: '',
            p_serialmoi: '',
            p_tinhtrang: '',
            p_phukien: '',
            p_phibh: '',
            p_ghichu:'',
            p_linhkien:'',
            p_ketquabh: '',
            p_trangthaibhdich: '',
            p_kho: 0,
            p_tukho: '',
            p_baohanh_id:'',

        },
        disable: {
            serialMoi: true,
            phiBH: true,
            linhKien: true
        },
        visible: {
            tuKho: true,
        },
        LOAI_KHO: {
            KHO_NHA_CC: 2
        },
        vDoiSerial: false,
        vPhiBH: false,
        // checkedNguonItems: [],
        // checkedDichItems: [],
        isRenderBaoHanhPSIS : false,
        isRenderTraCuuPSIS: false,
        isRenderColPSIS : false,
        DsTuKho: [],
        DanhSachNguon: [],
        DanhSachDich: [],
        selectedRow: {},        
        selectionOptions: {
          type: 'Single'
        },
        animationSettings: { effect: 'Zoom' },
        cRowSelected: function() {
          return {
            template : Vue.component('columntemplate', {
              template : '<span></span>'
            })  // end of template
          }
        },

        checkAllBaoHanh: false,
        colCheckboxAllBaoHanh: function () {
          return {
            template: {
              template: `
                              <div class="check-action">
                                  <input type="checkbox" v-model="parent.checkAllBaoHanh" class="uncheck"/>
                                  <span class="name"></span>
                              </div>
                          `,
              data: function () {
                return {};
              },            
              computed: {
                parent() {
                  return this.$parent.$parent.$parent;
                },
              },
            },
          };
        },
        listBaoHanhChecked : [],
        colCheckboxBaoHanh: function () {
          return {
            template: {
              template: `
                              <div class="check-action">
                                  <input
                                      type="checkbox"
                                      class="uncheck"                                    
                                      v-model="parent.listBaoHanhChecked"
                                      :value="data.KEY"/>
                                  <span class="name"></span>
                              </div>
                          `,
              data() {
                return {
                  data: {},
                };
              },
              computed: {
                parent() {
                  return this.$parent.$parent.$parent;
                },
              },
            },
          };
        },


        checkAllBaoHanhDich: false,
        colCheckboxAllBaoHanhDich: function () {
          return {
            template: {
              template: `
                              <div class="check-action">
                                  <input type="checkbox" v-model="parent.checkAllBaoHanhDich" class="uncheck"/>
                                  <span class="name"></span>
                              </div>
                          `,
              data: function () {
                return {};
              },            
              computed: {
                parent() {
                  return this.$parent.$parent.$parent;
                },
              },
            },
          };
        },
        listBaoHanhDichChecked : [],
        colCheckboxBaoHanhDich: function () {
          return {
            template: {
              template: `
                              <div class="check-action">
                                  <input
                                      type="checkbox"
                                      class="uncheck"                                    
                                      v-model="parent.listBaoHanhDichChecked"
                                      :value="data.KEY"/>
                                  <span class="name"></span>
                              </div>
                          `,
              data() {
                return {
                  data: {},
                };
              },
              computed: {
                parent() {
                  return this.$parent.$parent.$parent;
                },
              },
            },
          };
        },
        filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
        format:{ format:'N3' , useGrouping: true},
        templateOptions : {
          create: function (args) {
            if(['STT'].includes(args.column.field)){
              return '<span class="fa input-icon-right"></span>'
            } 
            
            return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
          },
          write: function (args) {
          }
        },
        pageSettings: { pageSizes: ['10','20','50','100','All'], pageSize: 10 },
        DanhSachNguonPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
        DanhSachDichPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
    };
  },
  methods: {
    ...mapActions("xulythietbibaohanh", actionName),
    ...mapMutations("xulythietbibaohanh", mutationName),
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    DanhSachNguonPageHandler(e) {
      this.DanhSachNguonPage.page = e.pageIndex
      this.DanhSachNguonPage.maxSize = e.pageSize
    },
    DanhSachDichPageHandler(e) {
      this.DanhSachDichPage.page = e.pageIndex
      this.DanhSachDichPage.maxSize = e.pageSize
    },
    async HienThiGiaoDien(trangthaibh_id){
        await this.getThongTinTrangThaiBH({ttbh_id:trangthaibh_id})
        if (this.ThongTinTrangThaiBH){
            this.vDoiSerial = parseInt(this.ThongTinTrangThaiBH[0].doiSerial) == 1;
            this.vPhiBH = parseInt(this.ThongTinTrangThaiBH[0].phiBH) == 1
        }
        this.disable.serialMoi = !this.vDoiSerial;
        this.disable.phiBH = !this.vPhiBH;
        if (trangthaibh_id == 6){
            this.visible.tuKho = true;
            await this.getDSKhoNhaCungCap({
                loaikho_id: this.LOAI_KHO.KHO_NHA_CC, 
                nhanvien_id: this.$auth.getNhanVienID(), //452
            })
            if (!this.DSKhoNhaCungCap.length ){
                this.$toast.error("Nhân viên chưa được phân quyền kho nhà cung cấp, liên hệ admin phân quyền! ")
                this.DsTuKho = [{khoId: '',tenKNew: ''}]
            }
            this.DsTuKho = this.DSKhoNhaCungCap            
            
        }
        else {
          this.visible.tuKho = false;
        }
        if (this.trangthaibh_id_n.toString() == "7" || this.trangthaibh_id_n.toString() == "8"){
          this.disable.linhKien = false
        }
    },
    async onTrangThaiBHChange(){
      await this.HienThiDanhSachDich()
    },
    // HienThiDanhSachNguon
    async HienThiDanhSachNguon(){
      var DanhSachNguon = await this.getDSBaoHanhTBHienTai({
        nhanvien_id: this.$auth.getNhanVienID(),
        ttbh_id: this.trangthaibh_id_n,
        quytrinh_id: this.quytrinh_id
      })
      
      this.listBaoHanhChecked = []
      this.checkAllBaoHanh =  false
      this.showData_DanhSachNguon(DanhSachNguon)      
    },
    showData_DanhSachNguon(DanhSachNguon){
      var self = this
      DanhSachNguon.forEach(a=>{
        a.NGAY_TN ? a.NGAY_TN = self.f_DateToString(self.f_StringToDate(a.NGAY_TN,'DD-MM-YYYY hh:mm:ss'),'DD/MM/YYYY') : ""
        a.NGAYGIAO ? a.NGAYGIAO = self.f_DateToString(self.f_StringToDate(a.NGAYGIAO,'DD-MM-YYYY hh:mm:ss'),'DD/MM/YYYY') : ""

        a.DONGIA.toString() ? a.DONGIA = parseInt(a.DONGIA) : ""
        a.SOLUONG.toString() ? a.SOLUONG = parseInt(a.SOLUONG) : ""
        a.KEY = a.PHIEUBH_ID.toString() + a.BAOHANH_ID.toString()
      })
      this.DanhSachNguon = DanhSachNguon
    },
    // HienThiDanhSachDich
    async HienThiDanhSachDich(){
      if (!this.p_trangthaibhdich){ return }
      this.DanhSachDich = await this.getDSBaoHanhTBHienTai({
              nhanvien_id: this.$auth.getNhanVienID(),
              ttbh_id: this.p_trangthaibhdich,
              quytrinh_id: this.quytrinh_id
          })
      this.checkAllBaoHanhDich = false
      this.listBaoHanhDichChecked = []
      this.showData_DanhSachDich()
      // HienThiGiaoDien
      await this.HienThiGiaoDien(this.p_trangthaibhdich)
    },
    showData_DanhSachDich(){
      var self = this
      this.DanhSachDich.forEach(a=>{
        a.NGAY_TN ? a.NGAY_TN = self.f_DateToString(self.f_StringToDate(a.NGAY_TN,'DD-MM-YYYY hh:mm:ss'),'DD/MM/YYYY') : ""
        a.NGAYGIAO ? a.NGAYGIAO = self.f_DateToString(self.f_StringToDate(a.NGAYGIAO,'DD-MM-YYYY hh:mm:ss'),'DD/MM/YYYY') : ""

        a.DONGIA.toString() ? a.DONGIA = parseInt(a.DONGIA) : ""
        a.SOLUONG.toString() ? a.SOLUONG = parseInt(a.SOLUONG) : ""
        a.KEY = a.PHIEUBH_ID.toString() + a.BAOHANH_ID.toString()
      })
    },
    async btnNext_Click(){
      try{
        this.loading(true)
        if (!this.p_trangthaibhdich){
          this.$toast.error("Chưa chọn trạng thái bảo hành để cập nhật !")
          return;
        }
        this.params.kho = 0;
        if (this.p_trangthaibhdich == "6" ){
          if(this.params.p_tukho == null || this.params.p_tukho == ""){
            this.$toast.error("Kho nhà cung cấp không được trống")
            return
          }
        }
        if(!this.KiemTraDuLieuNhap(true)){return;}
        
        var DSNguonChecked = this.DanhSachNguon.filter(a=> this.listBaoHanhChecked.includes(a.KEY))      
        if (DSNguonChecked.length > 0){
          for(let e of DSNguonChecked){
            if(await this.Chuyen_trangthai_bh(parseInt(e.BAOHANH_ID),parseInt(e.PHIEUBH_ID),this.params.p_tukho || 0) != "1"){
              await this.HienThiDanhSachNguon();
              await this.HienThiDanhSachDich();
              return;
            }
          }
        }
        else{
          this.$toast.error("Bạn chưa chọn thiết bị muốn cập nhật trạng thái !")
        }
        await this.HienThiDanhSachNguon();
        await this.HienThiDanhSachDich();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    // btnNextAll_Click
    async btnNextAll_Click(){
      try{
        this.loading(true)
        if(!this.p_trangthaibhdich){
          this.$toast.error("Chưa chọn trạng thái bảo hành để cập nhật !")
          return;
        }
        if(!this.KiemTraDuLieuNhap(true)){return;}
        if(!this.DanhSachNguon.length){return;}
        for (let i in this.DanhSachNguon){
          if(await this.Chuyen_trangthai_bh(parseInt(this.DanhSachNguon[i].BAOHANH_ID),parseInt(this.DanhSachNguon[i].PHIEUBH_ID), this.p_tukho ) != "1"){
            await this.HienThiDanhSachNguon();
            await this.HienThiDanhSachDich();
            return;
          }
        }
        await this.HienThiDanhSachNguon();
        await this.HienThiDanhSachDich();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    // btnPrevie_Click
    async btnPrevie_Click(){
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm('Bạn có chắc chắn muốn hủy chuyển trạng thái ' + this.DSTrangThaiBHDich.find(a=>a.TTBH_ID == this.p_trangthaibhdich).TRANGTHAI_BH, {
          "title": '',
          "size": 'lg',
          "buttonSize": 'lg',
          "okVariant": 'danger',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy bỏ',
          "hideHeaderClose": false,
          "centered": true,
          "modal-class": ["f18","text-center"],
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
      try{    
        this.loading(true)    
        let DSBHDichChecked = this.DanhSachDich.filter(e=>this.listBaoHanhDichChecked.includes(e.KEY)) 
        for (let e of DSBHDichChecked){
          let res = await this.Huychuyen_trangthai_bh(e.BAOHANH_ID,e.PHIEUBH_ID )
          if( res !="1" ){
            
            await this.HienThiDanhSachNguon();
            await this.HienThiDanhSachDich();
            return;
          }
        }
        await this.HienThiDanhSachNguon();
        await this.HienThiDanhSachDich();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    // btnPrevieAll_Click
    async btnPrevieAll_Click(){
      await this.btnPrevie_Click()
    },
    // Huychuyen_trangthai_bh
    async Huychuyen_trangthai_bh(baohanh_id,PHIEUBH_ID){
      let serial_moi = ""
      let phi_bh = 0
      if (this.vPhiBH){
        phi_bh = this.p_phibh
      }

      // quy them dk maxlength cho ghichu, ketqua
      if (this.p_ketquabh.length > 500){
        this.$toast.error("Trường ghi chú dữ liệu vượt quá độ dài cho phép")
        return
      }

      let huyChuyen = {
          "baoHanhTBId" :baohanh_id ,
          "phieuId" : PHIEUBH_ID,
          "nhanVienId" : this.$auth.getNhanVienID(),
          // "serialMoi" : this.p_serialmoi,
          "serialMoi" : "",
          "phiBH" : phi_bh,
          "ghiChu" : this.p_ketquabh,
          "mayCN" : this.$auth.getUserName(),
          "nguoiCN" : this.$auth.getUserName(),
          "ipCN" : '10.59.90.123'
      }
      let resHuy = await this.huyChuyenTrangThaiBH(huyChuyen)
      if (resHuy.data.data != '1'){
        this.$toast.error(resHuy.data.data)
      }
      return resHuy.data.data
    },
    // Chuyen_trangthai_bh
    async Chuyen_trangthai_bh(baohanh_id,PHIEUBH_ID,KHO_ID){
      let serial_moi = ""
      let phi_bh = 0
      if (this.vDoiSerial){
          serial_moi = this.p_serialmoi
      }
      // quy them dk maxlength cho ghichu, ketqua
      if (this.p_ghichu.length > 500){
        this.$toast.error("Trường ghi chú dữ liệu vượt quá độ dài cho phép")
        return
      }

      if (this.p_ketquabh.length > 500){
        this.$toast.error("Trường kết quả bảo hành dữ liệu vượt quá độ dài cho phép")
        return
      }
    
      if (this.vPhiBH){
          phi_bh = this.p_phibh
      }

      let resChuyen = await this.chuyenTrangThaiBaoHanh({
          "baoHanhTBId" : baohanh_id ,
          "phieuId" : PHIEUBH_ID,
          "ttbhId" : this.p_trangthaibhdich,
          "nhanVienId" : this.$auth.getNhanVienID(),
          "serialMoi" : serial_moi,
          "phiBH" : parseInt(phi_bh) || 0,
          "ghiChu" : this.p_ghichu || '',
          "ketQua" : this.p_ketquabh || '',
          "mayCN" : this.$auth.getUserName(),
          "nguoiCN" : this.$auth.getUserName(),
          "ipCN" : '10.59.90.123',//this.$auth.getIP()
          "khoId" : KHO_ID,
          "linhKien" : this.selectedRow.LINHKIEN || ''
      })
      if (resChuyen.data.data != '1'){
        this.$toast.error(resChuyen.data.data)
      }
      return resChuyen.data.data
    },
   
    onSelectedRowNguon(row){
      if (row){
        this.selectedRow = row
        this.gridviewHienTai_FocusedRowChanged()
      }
    },
    
    onSelectedRowDich(row){
      if (row){
        this.selectedRow = row
        this.gridviewHienTai_FocusedRowChanged()
      }
    },
    
    gridviewHienTai_FocusedRowChanged(){
      if (this.selectedRow){
        this.p_donvi = this.selectedRow.TEN_DV
        this.p_nhanvien = this.selectedRow.TEN_NV
        this.p_ngay_tn = this.selectedRow.NGAY_YC
        this.p_kieubh = this.selectedRow.KIEU_BH
        this.p_loai_tb = this.selectedRow.LOAI_TBI
        this.p_soseri = this.selectedRow.SERIAL
        this.p_serialmoi = this.selectedRow.SERIAL_MOI
        this.p_ghichu = this.selectedRow.GHICHU
        this.p_ketquabh = this.selectedRow.KETQUA
        this.p_tinhtrang = this.selectedRow.TINHTRANG
        this.p_phukien = this.selectedRow.PHUKIEN
        this.p_dienthoai_lh = this.selectedRow.DIENTHOAI_LH
        this.p_mac = this.selectedRow.MAC
        this.p_phibh = this.selectedRow.PHI_BH
        this.p_baohanh_id = parseInt(this.selectedRow.BAOHANH_ID)
      }
    },
    // KiemTraDuLieuNhap
    KiemTraDuLieuNhap(chuyen){
      if (chuyen){
        if(this.vDoiSerial && !this.p_serialmoi.toString().trim()){
          this.$toast.error("Bạn chưa nhập serial mới cần chuyển!")
          return false;
        }
        if(this.vPhiBH && !this.p_phibh){
          this.$toast.error("Bạn chưa nhập phí bảo hành!")
          return false;
        }
        
        if(this.trangthaibh_id_n == 5){ // check Kho PSIS
          let dsChon = this.DanhSachNguon.filter(a=> this.listBaoHanhChecked.includes(a.KEY))
  
          for(let i in dsChon){
            if(dsChon[0].KHO_ID_PSIS != dsChon[i].KHO_ID_PSIS){
              this.$toast.error("Trong danh sách có thiết bị khác Kho PSIS với các thiết bị còn lại!")
              return false;
            }
            if(dsChon[i].KHO_ID_PSIS != null && dsChon[i].KHO_ID_PSIS != "" && dsChon[i].KHO_ID_PSIS != this.params.p_tukho){
              this.$toast.error("Trong danh sách có thiết bị không đúng với kho đang chọn!")
              return false;
            }
          }          
        }
        if(this.trangthaibh_id_n == 6){ // check Kho PSIS
          let dsChon = this.DanhSachNguon.filter(a=> this.listBaoHanhChecked.includes(a.KEY))
       
          for(let e of dsChon){
            if(e.KHO_ID_PSIS != null && e.KHO_ID_PSIS != ""){
              this.$toast.error("Trong danh sách có thiết bị kho PSIS, phải dùng chức năng Nhận thiết bị từ đối tác!")
              return false;
            }            
          }          
        }
      }
      return true
    },
    async ChonSerialTheoKhoPSIS(){
      this.listBaoHanhChecked = [];     
      for (const e of this.DanhSachNguon) {
        if(e.KHO_ID_PSIS == this.params.p_tukho && this.params.p_tukho != null && this.params.p_tukho != ""){
          this.listBaoHanhChecked.push(e.KEY);
        }        
      }
      if(this.listBaoHanhChecked.length > 0){
        this.$toast.success("Đã chọn " + this.listBaoHanhChecked.length + " thiết bị thuộc kho nhà cung cấp!" )
      }else{
        this.$toast.error("Không tìm thấy thiết bị thuộc kho nhà cung cấp!" )
      }
     
    },
    ChonSerialTheoKhoPSISBHDich(){
      this.listBaoHanhDichChecked = [];     
      for (const e of this.DanhSachDich) {
        if(e.KHO_ID_PSIS == this.params.p_tukho && this.params.p_tukho != null && this.params.p_tukho != ""){
          this.listBaoHanhDichChecked.push(e.KEY);
        }        
      }
      if(this.listBaoHanhDichChecked.length > 0){
        this.$toast.success("Đã chọn " + this.listBaoHanhDichChecked.length + " thiết bị thuộc kho nhà cung cấp!" )
      }else{
        this.$toast.error("Không tìm thấy thiết bị thuộc kho nhà cung cấp!" )
      }
    },
    async NhanThietBiTuDoiTac(){
      this.$refs.popup_TraCuuTheoSoLoPhieuPSIS.show()
    },
    async Close_TraCuuTheoSoLoPhieuPSIS(){
      try{
        this.loading(true)
        await this.HienThiDanhSachNguon();
        await this.HienThiDanhSachDich();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
      
    },
    async TaoPhieuBHPSIS(){     
      this.$refs.popup_PhieuBaoHanhPSIS.show()
    },
    async Close_PhieuBaoHanhPSIS(){
      try{
        this.loading(true)
        await this.HienThiDanhSachNguon();
        await this.HienThiDanhSachDich();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }      
    },
    // btnCapNhatKetQua_Click
    async btnCapNhatKetQua_Click(){
      try{
        this.loading(true)
        if (this.listBaoHanhChecked.length){
          // quy them dk maxlength cho ketqua
          if (this.p_ketquabh.length > 500){
            this.$toast.error("Trường kết quả bảo hành dữ liệu vượt quá độ dài cho phép")
            return
          }
          var DSNguonChecked = this.DanhSachNguon.filter(a=> this.listBaoHanhChecked.includes(a.KEY))
          let updateBH = []
          DSNguonChecked.forEach(a=>{
            updateBH.push({
              baoHanhId: a.BAOHANH_ID,
              ketQua: this.p_ketquabh
            })
          })
          await this.capNhatKetQuaBH(updateBH)
          this.$toast.success("Cập nhật thành công")
          
        }
        else{
          this.$toast.error("Bạn chưa chọn thiết bị muốn cập nhật kết quả !")
        }
        
        await this.HienThiDanhSachNguon()
        await this.HienThiDanhSachDich()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    resetParams(){
      this.p_donvi = ''
      this.p_nhanvien = ''
      this.p_ngay_tn = ''
      this.p_kieubh = ''
      this.p_loai_tb = ''
      this.p_soseri = ''
      this.p_serialmoi = ''
      this.p_ghichu = ''
      this.p_ketquabh = ''
      this.p_tinhtrang = ''
      this.p_phukien = ''
      this.p_dienthoai_lh = ''
      this.p_mac = ''
      this.p_phibh = ''
      this.p_baohanh_id = 0
    },
    // txtSeriCu_KeyPress
    txtSeriCu_KeyPress(){
      if (this.p_soseri && this.DanhSachNguon.length){
        let item = this.DanhSachNguon.find(a=>a.SERIAL.toString() == this.p_soseri.toString());
        var DSNguonChecked = this.DanhSachNguon.filter(a=> this.listBaoHanhChecked.includes(a.KEY))
        if(item && !DSNguonChecked.some(a=>parseInt(a.BAOHANH_ID) == parseInt(item.BAOHANH_ID))){
          let index = this.DanhSachNguon.findIndex(a=>a.SERIAL.toString() == this.p_soseri.toString());
          this.$refs.tableNguon.selectRow(index)
          this.onSelectedRowNguon(item)
        }
      }
    },
    // txtLinkKien_DoubleClick
    txtLinkKien_DoubleClick(){

    },
    async btnExcel_Click(){
      var DSNguonChecked = this.DanhSachNguon.filter(a=> this.listBaoHanhChecked.includes(a.KEY))
      let data1 = XLSX.utils.json_to_sheet(DSNguonChecked);
      let wb = XLSX.utils.book_new(); // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, "BaoHanh"); // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, "BaoHanhThietBi.xlsx");
    },
    async btnInPhieu_Click(){
      if (this.trangthaibh_id_n.toString() != "23"){
        
        if (this.params.p_trangthaibhdich == null || this.params.p_trangthaibhdich == ""){
          this.$toast.error("Chưa chọn trạng thái bảo hành để cập nhật !")
          return
        }
        let kieu = 0
        // nếu giao sang 6
        if (this.params.p_trangthaibhdich.toString() == "6"){
          kieu = 1;
          let xdoc = []

          // quy them
          if (this.listBaoHanhDichChecked.length == 0){
            this.$toast.error("Bạn chưa chọn thiết bị bảo hành đích")
            return
          }
          let DSBHDichChecked = this.DanhSachDich.filter(e=>this.listBaoHanhDichChecked.includes(e.KEY))
          for (let item of DSBHDichChecked){
            // có serial và có BAOHANH_ID
            if (item.SERIAL != null && item.SERIAL != '' && item.BAOHANH_ID != null && item.BAOHANH_ID != ''){
              xdoc.push({"TBI" : item.BAOHANH_ID})
            }
          }
          // tt_nd.ten_dv_dl, tt_nd.ten_dv
          await this.frmXemBaoCao(
            xdoc, 
            this.$auth.getClientInfoFromStorage().donvi_id, 
            this.$auth.getClientInfoFromStorage().donvi_dl_id, 
            "0", 
            this.$auth.getClientInfoFromStorage().ten_dv,
            this.$auth.getClientInfoFromStorage().ten_dv_dl, )

        }

        if (this.trangthaibh_id_n.toString() == "10" || this.trangthaibh_id_n.toString() == "9" || this.trangthaibh_id_n.toString() == "22"){
          kieu = this.trangthaibh_id_n;
          let xdoc = []
          // quy them
          var DSNguonChecked = this.DanhSachNguon.filter(a=> this.listBaoHanhChecked.includes(a.KEY))
          if (DSNguonChecked.length == 0){
            this.$toast.error("Bạn chưa chọn thiết bị bảo hành hiện tại")
            return
          }

          for (let item of DSNguonChecked){
            // có serial và có BAOHANH_ID
            if (item.SERIAL != null && item.SERIAL != '' && item.BAOHANH_ID != null && item.BAOHANH_ID != ''){
              xdoc.push({"TBI" : item.BAOHANH_ID})
            }
          }
          // tt_nd.ten_dv_dl, tt_nd.ten_dv
          await this.frmXemBaoCao(
            xdoc, 
            this.$auth.getClientInfoFromStorage().donvi_id, 
            this.$auth.getClientInfoFromStorage().donvi_dl_id, 
            kieu, 
            this.$auth.getClientInfoFromStorage().ten_dv,
            this.$auth.getClientInfoFromStorage().ten_dv_dl, )

        }
      }
      else{
        let kieu = this.trangthaibh_id_n;
        let xdoc = []
        // quy them
        var DSNguonChecked = this.DanhSachNguon.filter(a=> this.listBaoHanhChecked.includes(a.KEY))
        if (DSNguonChecked.length == 0){
          this.$toast.error("Bạn chưa chọn thiết bị bảo hành hiện tại")
          return
        }
        for (let item of DSNguonChecked){
          // có serial và có BAOHANH_ID
          if (item.SERIAL != null && item.SERIAL != '' && item.BAOHANH_ID != null && item.BAOHANH_ID != ''){
            xdoc.push({"TBI" : item.BAOHANH_ID})
          }
        }
        // tt_nd.ten_dv_dl, tt_nd.ten_dv
        await this.frmXemBaoCao(
          xdoc, 
          this.$auth.getClientInfoFromStorage().donvi_id, 
          this.$auth.getClientInfoFromStorage().donvi_dl_id, 
          kieu, 
          this.$auth.getClientInfoFromStorage().ten_dv,
          this.$auth.getClientInfoFromStorage().ten_dv_dl, )
      }
      

    },
    async frmXemBaoCao(data, tenDv, tenDvQl, kieu, donViGiao, donViNhan){
      this.$root.showLoading(true);
      let response = await this.xemBaoCao({
        "data": data,
        "tenDv": tenDv,
        "tenDvQl": tenDvQl,
        "kieu": kieu,
        "donViGiao": donViGiao,
        "donViNhan": donViNhan
      })

      this.$root.showLoading(false);
      previewPrint(response.data)
    },
  },
created: async function() {
    try{
      this.loading(true)
      this.clearData();
      this.resetParams();
      this.header.title = this.trangthai_bh_n
      // frmXuLyThietBiBaoHanh_Load    
      this.isRenderBaoHanhPSIS = this.trangthaibh_id_n == 5
      this.isRenderTraCuuPSIS = this.trangthaibh_id_n == 6
      this.isRenderColPSIS = this.trangthaibh_id_n == 5 || this.trangthaibh_id_n == 6
      await this.getDSTrangThaiBHDich({luong_id:this.luong_id,ttbh_nguon_id:this.trangthaibh_id_n})
      if (this.DSTrangThaiBHDich.length){
          this.p_trangthaibhdich = this.DSTrangThaiBHDich[0].TTBH_ID
      }
      await this.HienThiDanhSachNguon()      
      await this.HienThiDanhSachDich()
    }catch(e){
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false)
    }

  },
  watch: {
    listBaoHanhChecked: function (value) {
      this.listBaoHanhChecked = value;      
    },
    checkAllBaoHanh: function (value) {          
      this.listBaoHanhChecked = [];
      if (value) {
        for (const e of this.$refs.tableNguon.filteredDataSource) {
          this.listBaoHanhChecked.push(e.KEY);
        }
      }   
    },
    listBaoHanhDichChecked: function (value) {
      this.listBaoHanhDichChecked = value;      
    },
    checkAllBaoHanhDich: function (value) {          
      this.listBaoHanhDichChecked = [];
      if (value) {
        for (const e of this.$refs.tableDich.filteredDataSource) {
          this.listBaoHanhDichChecked.push(e.KEY);
        }
      }   
    },
  },
  destroyed () {
  }
};
</script>
<style>
  .xulythietbibaohanh .e-grid tr.e-emptyrow td {
      text-align: center !important;
    }
</style>