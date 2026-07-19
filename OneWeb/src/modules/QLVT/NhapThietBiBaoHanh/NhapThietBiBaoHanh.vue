<template src='./NhapThietBiBaoHanh.html'></template>
<style scoped src='./NhapThietBiBaoHanh.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import NhapThietBiBaoHanhAPI from  '../api/NhapThietBiBaoHanhAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/nhapthietbibaohanh'
import select2 from '@/components/Select2.vue'
import { Group, Page, Filter, Sort, Resize} from "@syncfusion/ej2-vue-grids";
import { Query,Predicate } from '@syncfusion/ej2-data';
import frmNhapText from '../frmNhapText/frmNhapText.vue'

var DialogResultObj = {
    None: 0,
    OK: 1,
    Cancel: 2,
    Abort: 3,
    Retry: 4,
    Ignore: 5,
    Yes: 6,
    No: 7,
}

export default {

  components: {frmNhapText, breadcrumb, appSelect2: select2 },
  name: "NhapThietBiBaoHanh",
  mounted() {
  },
  provide: {
    grid: [ Group, Page,Filter,Sort,Resize ]
  },
  computed: {
    ...mapState("nhapthietbibaohanh", statePropertyName),
    ...mapGetters("nhapthietbibaohanh", getterName),
    p_huonggiao_id: {
      get() {
        return this.params.p_huonggiao_id
      },
      set(value) {
        return this.params.p_huonggiao_id = value
      }
    },
    p_trangthaibh: {
      get() {
        return this.params.p_trangthaibh
      },
      set(value) {
        return this.params.p_trangthaibh = value
      }
    },
    p_khogiao: {
      get() {
        return this.params.p_khogiao
      },
      set(value) {
        return this.params.p_khogiao = value
      }
    },
    p_tungay: {
      get() {
        return this.params.p_tungay
      },
      set(value) {
        return this.params.p_tungay = value
      }
    },
    p_trangthai_nk: {
      get() {
        return this.params.p_trangthai_nk
      },
      set(value) {
        return this.params.p_trangthai_nk = value
      }
    },
    p_khonhan: {
      get() {
        return this.params.p_khonhan
      },
      set(value) {
        return this.params.p_khonhan = value
      }
    },
    p_denngay: {
      get() {
        return this.params.p_denngay
      },
      set(value) {
        return this.params.p_denngay = value
      }
    },

  },
  data() {
    return {
        header: {
            title: "NHẬP THIẾT BỊ BẢO HÀNH",
            list: [
            { name: "Quản lý vật tư", link: { name: "Ui.cards" } },
            {
                name: "Nhập/Xuất/Điều chuyển vật tư",
                link: { name: "Ui.buttons" },
            },
            {
                name: "Nhập thiết bị bảo hành",
                link: { name: "Ui.buttons" },
            },
            ],
        },
        params: {
            p_huonggiao_id: "",
            p_trangthaibh: "",
            p_khogiao: 0,
            p_tungay: new Date,
            p_trangthai_nk: 1,
            p_khonhan: 0,
            p_denngay: new Date()

        },
        enable: {
          tsbtnHoanThanh: true
        },
        gridDanhSach: [],
        quyen_bh_kt: false,
        cboHuongGiao: [],
        cboTTBH: [],

        cboTrangThai: [
            {STATUS: 1, TRANG_THAI: "Chưa nhập kho"},
            {STATUS: 4, TRANG_THAI: "Đã nhập kho"},
            ],
        cboKhoGiao: [],
        cboKho: [],
        dsQuyen: [],
        dt_luong: [],
        QUYEN: {
            KETOAN_BHTB: 10102
        },
        LOAI_KHO: {
            KHO_THUHOI: 15,
            KHO_BAOHANH: 6,
            KHO_TOTRUONG: 14,
            KHO_GUI_BAOHANH: 16,
            KHO_TRA_BAOHANH: 17,
            KHO_NHA_CC: 2,
            KHO_NV: 5,

            KHO_VATTU_SUACHUA: 530,
            KHO_VATTU_BAOHANH: 540,
        },
        NHOM_QT: {
          BAOHANH_TBI: 4
        },
        HUONGGIAO_BAOHANH: {
          TIEPNHAN_BH: 1400,
          GUI_BH_TTVT: 1401,
          BHSC: 1402,
          TRA_BH_TTVT: 1403,
          HOANTHIEN_BH: 1404,

          TIEPNHAN_BH_WIFI_MESH: 1405,
          GUI_BH_TTVT_WIFI_MESH: 1406,
          BHSC_WIFI_MESH: 1407,
          TRA_BH_TTVT_WIFI_MESH: 1408,
          HOANTHIEN_BH_WIFI_MESH: 1409
        },
        gridDSChecked : [],
        BHSC_LOAICT_CHUYEN : false,
        MUCDICH_VT: {
          NHAP_BAOHANH: "1.4",
          XUAT_BAOHANH: "2.4",
          MUASAM: "N_MS",
          CHUYEN_CP: "2.XCP",
          KDV_DONGIA: "N.KDV_DG"
        },
        filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
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
        cRowSelected: function() {
          return {
            template : Vue.component('columntemplate', {
              template : '<span class="fa"></span>'
            })  // end of template
          }
        },
        checkAllPhieu: false,
        colCheckboxAllPhieu: function () {
          return {
            template: {
              template: `
                              <div class="check-action">
                                  <input type="checkbox" v-model="parent.checkAllPhieu" class="uncheck"/>
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
        rowSelectedPhieu: [], 
        colCheckboxPhieu: function () {
          return {
            template: {
              template: `
                              <div class="check-action">
                                  <input
                                      type="checkbox"
                                      class="uncheck"                                    
                                      v-model="parent.rowSelectedPhieu"
                                      :value="data.PHIEUBH_ID"/>
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
        gridDanhSachPage : {
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
    ...mapActions("nhapthietbibaohanh", actionName),
    ...mapMutations("nhapthietbibaohanh", mutationName),
    gridDanhSachPageHandler(e) {
      //this.pageinfo.page = e
      this.gridDanhSachPage.page = e.pageIndex
      this.gridDanhSachPage.maxSize = e.pageSize
    },
    // chuyển Date thành type string
    f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    setFirstData_cboHuongGiao(){
      this.cboHuongGiao.length ? this.p_huonggiao_id = this.cboHuongGiao[0].HUONGGIAO_ID : ""
    },
    setFirstData_cboTTBH(){
      this.cboTTBH.length ? this.p_trangthaibh = this.cboTTBH[0].ttBHId : ""
    },
    setFirstData_cboKhoGiao(){
      this.cboKhoGiao.length ? this.p_khogiao = this.cboKhoGiao[0].KHO_ID : ""
    },
    
    async NAP_COMBO(){
        // try {
          
            if(this.quyen_bh_kt){
                // Combo hướng giao
                this.cboHuongGiao = await this.getDSHuongGiaoBHNhapThueBao({nguoiDungId: this.$auth.getNguoiDungID()})
            }
            else{
                if(!this.dsQuyen.includes(99)){
                    let res =  await this.getDSHuongGiaoBH_Filter()
                    res.forEach(a=>{
                      a.HUONGGIAO_ID = a.huongGiaoId
                      a.HUONGGIAO = a.huongGiao
                    })
                    this.cboHuongGiao = res.filter(a=>["1401","1402","1404","1406","1407","1409"].includes(a.huongGiaoId.toString()))
                }
                else{
                    this.cboHuongGiao = [{HUONGGIAO_ID: 0, HUONGGIAO: "Mô hình bảo hành"}]
                }
            }
            this.setFirstData_cboHuongGiao()
            await this.Load_dtThaoTac()  // tạo func ,gom chung set dtThaoTac
            // COmbo trạng thái
            // Ngày tháng
            
            this.p_tungay.setMonth(this.p_tungay.getMonth() - 2 )
            // Combo Trạng thái bảo hành
            await this.Load_TTBH()
            
            
        // } catch (error) {
        //     this.$toast.error("Lỗi hàm nạp dữ liệu combo")
        // }
    },
    async Load_TTBH(){
      if(this.p_huonggiao_id.toString() == "0"){
        this.cboTTBH = await this.getDSTrangThaiBH()
      }
      else{
        this.cboTTBH = [{ttBHId: 0,ttBH: "Giao bảo hành"}]
      }
      this.setFirstData_cboTTBH()
    },
    async LOAD_QUYEN(){
        this.dsQuyen = await this.getDSQuyenNguoiDung({nguoiDungId: this.$auth.getNguoiDungID()})
        this.quyen_bh_kt = this.dsQuyen.some(a=>a.toString() == this.QUYEN.KETOAN_BHTB.toString())
    },
    async LOAD_DS_KHO(){
      try{
        this.loading(true)
        this.cboKhoGiao = []
        this.cboKho = []
        let DSKhoGiaoNhan = await this.getDSKhoBH({
          huongGiaoId:this.p_huonggiao_id,
          trangThaiBH:this.p_trangthaibh
        })
        
        this.cboKhoGiao = 'ds_khogiao' in DSKhoGiaoNhan ? DSKhoGiaoNhan.ds_khogiao : []
        this.cboKho = 'ds_khonhan' in DSKhoGiaoNhan ? DSKhoGiaoNhan.ds_khonhan : []
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    // async LOAD_DS_KHO(){
    //     // try {
    //         if((this.p_huonggiao_id.toString() != "0")){
    //             // Hướng giao
                
    //             if(this.PHAILAM("BHTB_GUI_BH_TTVT")){
    //                 // Combo kho giao
                    
    //                 let q = await this.getDSKhoGiaoTheoHG({huongGiao :"BHTB_GUI_BH_TTVT", nhanVienId: this.$auth.getNhanVienID() })
    //                 if (q.length){
    //                     if(this.$auth.getPhanVungID().toString() == "26"){
    //                         let kho = q.filter(a=>a.LOAIKHO_ID.toString() == this.LOAI_KHO.KHO_TOTRUONG.toString()
    //                             && !a.TEN_K_NEW.toString().includes(".H")
    //                         )
    //                         kho.length ? this.cboKhoGiao = kho : ""
    //                     }
    //                     else{                         
    //                         let kho = q.filter(a=>a.LOAIKHO_ID.toString() == this.LOAI_KHO.KHO_TOTRUONG.toString())
    //                         kho.length ? this.cboKhoGiao = kho : ""
    //                     }
    //                 }
    //                 // Combo kho nhận
    //                 let t = await this.getDSKhoNhanTheoHG({huongGiao: "BHTB_GUI_BH_TTVT", nhanVienId: this.$auth.getNhanVienID() })
    //                 if (!t.length){
    //                     this.$toast.error("Nhân viên chưa được phân quyền kho bảo hành, liên hệ admin phân quyền! ")
    //                     return
    //                 }
    //                 this.cboKho = t
    //             }
    //             if(this.PHAILAM("BHTB_BHSC")){
    //               // Combo kho giao
    //               let t = await this.getDSKhoGiaoTheoHG({huongGiao :"BHTB_BHSC", nhanVienId: this.$auth.getNhanVienID() })
    //               if(!t.length){
    //                 this.$toast.error("Nhân viên chưa được phân quyền kho bảo hành, liên hệ admin phân quyền!")
    //                 return
    //               }
    //               this.cboKhoGiao = t
    //             }
    //             if(this.PHAILAM("BHTB_HTHS")){
    //               // Combo kho nhận
    //               let t = await this.getDSKhoNhanTheoHG({huongGiao: "BHTB_HTHS", nhanVienId: this.$auth.getNhanVienID() })
    //               if (!t.length){
    //                 this.$toast.error("Nhân viên chưa được phân quyền các kho tổ trưởng, liên hệ admin phân quyền")
    //                 return
    //               }
    //               this.cboKho = t
    //               // Combo kho giao
    //               let q = await this.getDSKhoGiaoTheoHG({huongGiao :"BHTB_GUI_BH_TTVT", nhanVienId: this.$auth.getNhanVienID() })
    //               if (!q.length){
    //                 this.$toast.error("Nhân viên chưa được phân quyền kho bảo hành, liên hệ admin phân quyền!")
    //                 return
    //               }
    //               this.cboKhoGiao = q
    //             }
    //             this.setFirstData_cboKhoGiao()
    //         }
    //         else{
    //           // Trạng thái bảo hành
    //           if (["6","9","10","22"].includes(this.p_trangthaibh.toString())){
    //             let t = await this.getDSKhoGiaoTheoTTBH({ttbh: this.p_trangthaibh, nhanVienId: this.$auth.getNhanVienID()})
    //             if (!t.length){
    //               this.$toast.error("Nhân viên chưa được phân quyền kho nhà cung cấp, liên hệ admin phân quyền!")
    //               return
    //             }
    //             this.cboKhoGiao = t
    //             this.setFirstData_cboKhoGiao()
    //             let q = await this.getDSKhoNhanTheoTTBH({ttbh: this.p_trangthaibh, nhanVienId: this.$auth.getNhanVienID() }) 
    //             if (!q.length){
    //               this.$toast.error("Nhân viên chưa được phân quyền kho bảo hành, liên hệ admin phân quyền!")
    //               return
    //             }
    //             this.cboKho = q
    //             this.cboKho.length ? this.p_khonhan = this.cboKho[0].KHO_ID : ''
    //           }
    //         }
            
    //     // } catch (error) {
    //     //     this.$toast.error("Lỗi hàm lấy combo kho")
    //     // }
    // },
    async LOAD_DANH_SACH(kieu){
      try {
        this.loading(true)
        this.gridDanhSach = []
        this.rowSelectedPhieu = []
        this.checkAllPhieu = false
        let mohinh = 0
        if(this.p_huonggiao_id.toString() == "0"){
          mohinh = this.p_trangthaibh
        }
        let kho_chung = 0
        
        if (this.p_trangthai_nk.toString() == "1"){
          kho_chung = this.p_khogiao
        }
        else{
          kho_chung = 0
        }
        if (this.PHAILAM("BHTB_HTHS")){
          mohinh = 24
        }
        if (this.PHAILAM("BHTB_BHSC") && kieu == 1){
          this.cboKho = []
          let t = await this.getDSKhoNhanTheoKieu({nhanVienId: this.$auth.getNhanVienID(), khoGiaoId: this.p_khogiao.toString() ? this.p_khogiao : -1})
          if(!t.length){
            this.$toast.error("Nhân viên chưa được phân quyền kho chung với kho bảo hành trung tâm, liên hệ admin phân quyền!")
            return
          }
          this.cboKho = t     
        }
        
        this.gridDanhSach =  await this.getDSGiaoPhieuBH({
            "huongGiaoId": this.p_huonggiao_id,
            "nhanVienId": parseInt(this.$auth.getNhanVienID()),
            "serial": "0",
            "ttphId": 2,
            "moHinhBh": mohinh,
            "ttnkId": this.p_trangthai_nk,
            "tuNgay": this.f_DateToString(this.p_tungay,'DD/MM/yyyy'),
            "denNgay": this.f_DateToString(this.p_denngay,'DD/MM/yyyy'),
            "khoId": kho_chung,
            "khoNhanId": this.p_khonhan || 0
        })
        this.convertData_gridDanhSach()
        

        
      } catch (error) {
        this.$toast.error(error.data.message)
      }finally{
        this.loading(false)
      }
    },
    async KIEMTRA_DULIEU(){
      
      if (this.p_khonhan == 0 || !this.p_khonhan.toString()){
        this.$toast.error("Bạn chưa chọn kho nhận")
        return false
      }
     
      if (this.p_khogiao == 0 || !this.p_khogiao.toString()){
        this.$toast.error("Bạn chưa chọn kho giao")
        return false
      }
      
     
      if (!this.rowSelectedPhieu.length){
        this.$toast.error("Chưa có thiết bị nào được chọn")
        return false
      }
      
      return true
    },
    // DHSX chưa có action
    async NHAP_KHO(){
      
    },
    async KIEMTRA_HANCHOT_CT(ngay_tao,tukho,denkho,vchungtu_id){
      let vkhonhan_id = 0
      let vkhogiao_id = 0
      if (parseInt(tukho)){
        vkhogiao_id = tukho
      }
      if (parseInt(denkho)){
        vkhonhan_id = denkho
      }
      let s = await this.KiemTraHanChungTu({
          "chungTuId": vchungtu_id,
          "khoGiaoId": vkhogiao_id,
          "khoNhanId": vkhonhan_id,
          "ngayCt": ngay_tao
      })
      if (s.toString() != "1"){
        this.$toast.error("Đã quá hạn sửa chứng từ!")
        return false
      }
      return true
    },
    async NHAPKHO_BH(ds_bh, nd, d_t){
      // try {
        let vkho_giao = 0
        let vkho_nhan = 0
        let xdoc = []
        let xdoc_ct =[]
        for (let i in ds_bh){
          let row = {}
          // vong lap
          let huongGiaoStr = ds_bh[i].HUONGGIAO_ID.toString()
          if(this.p_huonggiao_id.toString() != 0){
            if (huongGiaoStr == this.HUONGGIAO_BAOHANH.GUI_BH_TTVT.toString()
              || huongGiaoStr == this.HUONGGIAO_BAOHANH.GUI_BH_TTVT_WIFI_MESH.toString()){
                row.LOAICT_ID = "3"
            }
            else if (huongGiaoStr == this.HUONGGIAO_BAOHANH.BHSC.toString()
              || huongGiaoStr == this.HUONGGIAO_BAOHANH.BHSC_WIFI_MESH.toString()  ){
                this.BHSC_LOAICT_CHUYEN ? row.LOAICT_ID = "3" : row.LOAICT_ID = "2"
            }
            else if (huongGiaoStr == this.HUONGGIAO_BAOHANH.TRA_BH_TTVT.toString()
              || huongGiaoStr == this.HUONGGIAO_BAOHANH.TRA_BH_TTVT_WIFI_MESH.toString()){
                row.LOAICT_ID = "3"
            }
          }
          else{
            row.LOAICT_ID = "2"
            if (["9","10"].includes(this.p_trangthaibh.toString())){
              row.LOAICT_ID = "1"
            }
          }
          if (huongGiaoStr != this.HUONGGIAO_BAOHANH.TRA_BH_TTVT.toString() 
            && huongGiaoStr != this.HUONGGIAO_BAOHANH.TRA_BH_TTVT_WIFI_MESH.toString()){
              let t = await this.getMucDichIDTheoMaMD({maMd: this.MUCDICH_VT.XUAT_BAOHANH})
              if (t.length) {
                row.MUCDICH_ID = t[0]
              }
              else {
                this.$toast.error("Không tìm thấy mục đích " + this.MUCDICH_VT.XUAT_BAOHANH)
                return
              }
          }
          else{
            let t = await this.getMucDichIDTheoMaMD({maMd: this.MUCDICH_VT.NHAP_BAOHANH})
            if (t.length) {
                row.MUCDICH_ID = t[0]
              }
              else {
                this.$toast.error("Không tìm thấy mục đích " + this.MUCDICH_VT.NHAP_BAOHANH)
                return
              }
          }

          row.KHO_GIAO_ID = this.p_khogiao || 0
          row.KHO_NHAN_ID = this.p_khonhan || 0

          if (huongGiaoStr != this.HUONGGIAO_BAOHANH.TRA_BH_TTVT.toString()
            && huongGiaoStr != this.HUONGGIAO_BAOHANH.TRA_BH_TTVT_WIFI_MESH.toString()){
              let vthietbi_id = await this.getThongTinTBCuaPhieuBH({baoHanhId: ds_bh[i].BAOHANH_ID})
              if (!vthietbi_id){
                this.$toast.error("Không tìm thấy thiết bị của phiếu bảo hành")
                return
              }              
              row.THIETBI_ID = vthietbi_id[0]
          }
          else{
            let vthietbi_id = ds_bh[i].THIETBI_ID
            if (!vthietbi_id){
              this.$toast.error("Không tìm thấy thiết bị của phiếu bảo hành")
              return
            }
            row.THIETBI_ID = vthietbi_id
          }

          row.SOLUONG = 1
          row.NHANVIEN_GIAO_ID = this.$auth.getNhanVienID()
          row.DONVI_GIAO_ID = this.$auth.getDonViID()
          row.DONVI_NHAN_ID = "0"
          row.ND_GIAO = nd
          let row_ct = {}
          row_ct.PHIEUBH_ID = ds_bh[i].PHIEUBH_ID
          row_ct.PHIEU_CHA_ID = ds_bh[i].PHIEU_CHA_ID
          row_ct.BAOHANH_ID = ds_bh[i].BAOHANH_ID
          row_ct.HUONGGIAO_ID = ds_bh[i].HUONGGIAO_ID
          row_ct.TTPH_ID = ds_bh[i].TTPH_ID
          row_ct.TTBH_ID = ds_bh[i].TTBH_ID
          xdoc_ct.push(row_ct)
          xdoc.push(row)
        }

        let kq = await this.TaoChungTuTruyenDan({
            "kieu": 1,
            "dspBh": xdoc_ct,
            "dsTbi": xdoc,
            "ngayHt": d_t,
            "nguoiCn": this.$auth.getUserName(), //ttnd.ma_nd
            "mayCn": this.$auth.getUserName(), //ttnd.may_cn
            "ipCn": "192.168.23.2" //ttnd.ip
        })
        if (!isNaN(parseInt(kq))){
          this.$toast.success("Thực hiện thành công")
          if (this.$auth.getMaTinh() == 'HCM'){
            this.$router.push({path: 'NhapXuatVT_v2', query: { Tag: 1 }})
          }
          else{
            this.$router.push({path: 'NhapXuatVT', query: { Tag: 1 }})
          }
          

        }else{
          this.$toast.error(kq)
        }
        await this.LOAD_DANH_SACH(0)
      // } catch (error) {
          
      // }
    },
    frmNhapXuatVT_Shown(){
      
    },
    Load_dtThaoTac(){
      this.dtThaoTac = this.dt_luong.filter(a=>a.HUONGGIAO_ID.toString() == this.p_huonggiao_id.toString())
    },
    async frmNhapTbiBH_Load(){
      this.dt_luong = await this.getDSLuongThaoTac({nhomQtId: this.NHOM_QT.BAOHANH_TBI})
      await this.LOAD_QUYEN()
      await this.NAP_COMBO()
      await this.LOAD_DS_KHO()
      await this.LOAD_DANH_SACH(0)

      let ds = await this.getThamSoMacDinh({kieuId: 0})
      if (ds.length && this.setThamSo("BHSC_LOAICT_CHUYEN",ds)){       
        this.BHSC_LOAICT_CHUYEN = true
      }      
    },
    setThamSo(str_vtemp,ds){
      let det = ds.filter(a=>a.maTS == str_vtemp)      
      if (det.length){
        if (det[0].tenTS.trim() == "1"){
          return true
        }
      }
      return false
    },
    async cboHuongGiao_EditValueChanged(){
      await this.Load_dtThaoTac()
      await this.Load_TTBH()
      await this.LOAD_DS_KHO()
      await this.LOAD_DANH_SACH(0)
    },
    PHAILAM(code){
      if (!this.dtThaoTac.length){
        return false
      }
      for(let i in this.dtThaoTac){
          if (this.dtThaoTac[i].ENABLE.toString() == "1" && this.dtThaoTac[i].CODE.toString() == code){
            return true
          }
      }
      return false
    },
    async btnChiTiet_Click(){
      await this.LOAD_DANH_SACH(0);
    },
    async tsbtnHoanThanh_Click(){
      try {
        this.loading(true)
        if (! (await this.KIEMTRA_DULIEU())){ return }
        
        var f_rm = this.$refs.frmNhapText
        await f_rm.Load_frmNhapText()
        await f_rm.frmNhapText_2("Nhập nội dung chứng từ", "Ngày chứng từ")
        f_rm.Text.title = 'Nhập thông tin chuyển kho'
        this.loading(false)
        this.$refs.popup_frmNhapText.show()
        
      } catch (e) {
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async XacNhan_frmNhapText(){
      var f_rm = this.$refs.frmNhapText
      if (f_rm.DialogResult == DialogResultObj.OK){
        try{
          this.loading(true)
          if(!(await this.KIEMTRA_HANCHOT_CT( this.f_DateTimeToString(f_rm.DateOutput,'DD/MM/yyyy HH:mm:ss'), this.p_khogiao || 0, this.p_khonhan || 0, -1))){
            return
          }

          let DSPhieuNhapKho = []
          for(let e of this.rowSelectedPhieu){
            DSPhieuNhapKho.push({PHIEUBH_ID:e})
          }
         
          let ketQua = await this.NhapThietBiBaoHanh({
            huongGiaoId: this.p_huonggiao_id,
            noiDungGiao: f_rm.Output,
            khoGiaoId: this.p_khogiao,
            khoNhanId: this.p_khonhan,
            dsNhapPhieuBh: DSPhieuNhapKho,
            ngayCt: this.f_DateTimeToString(f_rm.DateOutput,'DD/MM/yyyy HH:mm:ss'),
            trangThaiBh: this.p_trangthaibh
          })

          this.$toast.success("Thực hiện thành công")
          if (this.$auth.getMaTinh() == 'HCM'){
            this.$router.push({path: 'NhapXuatVT_v2', query: { Tag: 1 }})
          }
          else{
            this.$router.push({path: 'NhapXuatVT', query: { Tag: 1 }})
          }
          
        }catch(e){
          this.$toast.error(e.data.message)
        }finally{
          this.loading(false)
        }
      }
    },
    async cboTTBH_EditValueChanged(){
      await this.LOAD_DS_KHO()
      await this.LOAD_DANH_SACH(0)
    },
    async cboTrangThai_EditValueChanged(){
      await this.LOAD_DANH_SACH(0)
      this.enable.tsbtnHoanThanh = this.p_trangthai_nk != 4
    },
    async cboKhoGiao_EditValueChanged(dataItem){
      console.log(dataItem)
      if (dataItem == null || dataItem == ''){ return }
      this.p_khogiao = dataItem.KHO_ID
      await this.LOAD_DANH_SACH(1)
    },
    async cboKho_EditValueChanged(){
      await this.LOAD_DANH_SACH(0);
    },
    promptDlgBtnClick: function() {
          this.$refs.promptDialog.hide();
    },
    promptDlgBtnClick: function() {
            this.$refs.promptDialog.hide();
    },
    convertData_gridDanhSach(){
      this.gridDanhSach.forEach(a => {
        a.SOLUONG ? a.SOLUONG = parseInt(a.SOLUONG) : ''
      });
    }
  },
created: async function() {    
    this.clearData();
    await this.frmNhapTbiBH_Load()
  },
  watch: {
    rowSelectedPhieu: async function (value) {      
      this.rowSelectedPhieu = value;     
    },
    checkAllPhieu: async function (value) {      
      this.rowSelectedPhieu = []
      if (value) {
        for (const e of this.gridDanhSach) {
          this.rowSelectedPhieu.push(e.PHIEUBH_ID);
        }
      }     
    },
  },
  destroyed () {
  }
};
</script>