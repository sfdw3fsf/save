<template src='./DuyetNhuCauDangKyVatTu.html'></template>
<style scoped src='./DuyetNhuCauDangKyVatTu.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/duyetnhucaudangkyvattu";
import moment from "moment";
import Vue from "vue";
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { currency } from '@/filters/currency'
import {previewPrint} from "../../../utils/util";
Vue.use(DatePickerPlugin)
export default {
  components: { breadcrumb },
  name: "DuyetNhuCauDangKyVatTu",

  data() {
    return {
      header: {
        title: "DUYỆT NHU CẦU ĐĂNG KÝ VẬT TƯ",
        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Duyệt nhu cầu đăng ký vật tư",
            link: { name: "Ui.buttons" },
          },         
        ],
      },

      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        formatMonth: "MM/yyyy",
        waterMark: "Chọn ngày",
      },
      showTableDuAn: 'none',
      params: {
        p_trang_thai: -1,
        p_don_vi: -1,
        p_ten_don_vi: "",
        p_tu_ngay: new Date(),
        p_den_ngay: new Date(),
        p_tu_thang: new Date(),
        p_den_thang: new Date(),
        p_nd_tra: ""
      },
      DSDonViTinh : [],
      DSTrangThaiDangKy : [],
      DSDonViDangKy: [],
      DSNhuCauDangKy : [],
      DSChiTietVatTu : [],
      ktTrangThai :  "",
      capNhatNhuCauDK : "",
      checkBox: {
        isTuThang: false,
        isDenThang: false,
        isTuNgay: false,
        isDenNgay: false,
        isDonVi: false,
      },
      isFirst : true,
      txt_btn_huy: "Không duyệt",
      dang_ky_id: -1,
      visible: {
        btnNhanDK: true,
        btnTraPhieu: true,
        btnKhongDuyet: true,
        btnHuy: false,
      },
      thongTinNguoiDung: {
        nguoiCN: this.$root.token.getUserName(), //this.$auth.MaNguoiDung
        mayCN: "DESKTOP-IDG67LD", //this.$auth.MAYCN
        ipCN: "10.59.90.128", //this.$auth.MAYCN
        donViId: this.$root.token.getDonViID(), // 882
        phanVungId: this.$root.token.getPhanVungID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        nguoiDungId: this.$root.token.getNguoiDungID(),
      },
      bodyNhuCauDangKy: {
        tuThangDk: "0",
        denThangDk: "0",
        ttdkId: -1,
        donViId: -1,
        tuNgay: "0",
        denNgay: "0",
      },
      selectedRow: {},
      listItemChecked: [],
      checkAllDangKy: false,
      customTemplate: function() {
        return  { template : Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data () { return { data: {data: {}}}; }
          })
        }
      },

      CheckboxAllDangKy: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllDangKy" class="uncheck"/>
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
      CheckboxDangKy: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.listItemChecked"
                                    :value="data.DANGKY_ID"/>
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
    };
  },

  mounted: async function () {
    document.addEventListener('click', event => {
      if (typeof (this.$refs["ql-taisan-select-duan"]) == 'undefined') {
          return
      }
      if (this.$refs["ql-taisan-select-duan"].contains(event.target)) {
          this.showTableDuAn = this.showTableDuAn == 'none' ? 'block' : 'none'
      } else if (this.$refs["ql-taisan-dropdown-select-duan"].contains(event.target)) {
          if (event.target.tagName.toLowerCase() == 'td') {
              this.showTableDuAn = 'none'
          } else {
              this.showTableDuAn = 'block'
          }
      } else {
          this.showTableDuAn = 'none'
      }

      if (this.showTableDuAn == 'block') {
          this.popper = new Popper(this.$refs["ql-taisan-select-duan"], this.$refs["ql-taisan-dropdown-select-duan"], {
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
  watch: {
    listItemChecked: function (value) {
      this.listItemChecked = value;  
    },
    checkAllDangKy: function (value) {
      if (value) {
        for (const e of this.DSNhuCauDangKy) {
          this.listItemChecked.push(e.DANGKY_ID);
        }
      } else {
        this.listItemChecked = [];
      }     
    },
  },
  computed: {
    ...mapState("duyetnhucaudangkyvattu", statePropertyName),
    ...mapGetters("duyetnhucaudangkyvattu", getterName),

    PTAggregatesColumns () {
      return [        
        { field: 'SOLUONG', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.getTotalSL },
      ]
    },
  },

  methods: {
    ...mapActions("duyetnhucaudangkyvattu", actionName),
    ...mapMutations("duyetnhucaudangkyvattu", mutationName),

    getTotalSL() {
      var Tong = 0
      this.DSChiTietVatTu.forEach(e => {
        e.SOLUONG =  e.SOLUONG == "" || e.SOLUONG == null ?  0 : parseFloat(e.SOLUONG)       
        Tong += e.SOLUONG
      })
      return Tong;
    },

    async loadDataCombo() {
      this.DSTrangThaiDangKy = await this.getDSTrangThaiDangKy();
      this.DSTrangThaiDangKy = this.DSTrangThaiDangKy.slice(0, this.DSTrangThaiDangKy.length - 1)
      this.DSDonViDangKy = await this.getDSDonViDangKy(); 
      this.addDSDonViDangKyCha(this.DSDonViDangKy)
      this.params.p_trang_thai = this.DSTrangThaiDangKy[0].ttdkid;
      this.setVisibleBtn();
      await this.getDataTheoDieuKien();
    },
    addDSDonViDangKyCha(data) {
        // thêm đơn vị cha
        var listDVCha = []
        for(const i in data){
            listDVCha.push({
                DONVI_ID: data[i].DONVI_CHA_ID,
                TEN_DV: data[i].TEN_CHA_DV,
            })
        }
        
        var obj = {};

        for ( var i=0, len=listDVCha.length; i < len; i++ )
            obj[listDVCha[i]['DONVI_CHA_ID']] = listDVCha[i];

        listDVCha = new Array();
        for ( var key in obj )
            data.push(obj[key]);   
        // this.DSDonViDangKy = data    
    },
    validateNgayThang() {
      
      if(this.bodyNhuCauDangKy.tuThangDk  == "Invalid date" ||
          this.bodyNhuCauDangKy.denThangDk  == "Invalid date" ||
          this.bodyNhuCauDangKy.tuNgay  == "Invalid date" ||
          this.bodyNhuCauDangKy.denNgay  == "Invalid date" 
      ){
        return false
      }
      if (
        this.checkBox.isTuThang &&
        this.checkBox.isDenThang &&
        this.bodyNhuCauDangKy.tuThangDk > this.bodyNhuCauDangKy.denThangDk
      ) {
        this.$toast.error(" Tháng bắt đầu phải nhỏ hơn tháng kết thúc ");
        return false
      }
      if (
        this.checkBox.isTuNgay &&
        this.checkBox.isDenNgay &&
        this.bodyNhuCauDangKy.tuNgay > this.bodyNhuCauDangKy.denNgay
      ) {
        this.$toast.error(" Ngày bắt đầu phải nhỏ hơn ngày kết thúc ");
        return false
      }
      return true
    },
    async getDataTheoDieuKien() {
      try{
        this.loading(true);
        this.bodyNhuCauDangKy = {
          tuThangDk: "0",
          denThangDk: "0",
          ttdkId: this.params.p_trang_thai,
          donViId: -1,
          tuNgay: "0",
          denNgay: "0",
        };

        if (this.checkBox.isTuThang) {
          this.bodyNhuCauDangKy.tuThangDk = this.f_MonthToString(
            this.params.p_tu_thang
          );
        }

        if (this.checkBox.isDenThang) {
          this.bodyNhuCauDangKy.denThangDk = this.f_MonthToString(
            this.params.p_den_thang
          );
        }

        if (this.checkBox.isTuNgay) {
          this.bodyNhuCauDangKy.tuNgay = this.f_DateToString(
            this.params.p_tu_ngay
          );
        }

        if (this.checkBox.isDenNgay) {
          this.bodyNhuCauDangKy.denNgay = this.f_DateToString(
            this.params.p_den_ngay
          );
        }

        if (this.checkBox.isDonVi) {
          this.bodyNhuCauDangKy.donViId = this.params.p_don_vi;
        }

        if(!this.validateNgayThang()){
          this.loading(false);
          return
        } 
        var DSNhuCauDangKy = await this.getDSNhuCauDangKy(this.bodyNhuCauDangKy);
        if (DSNhuCauDangKy.length > 0) {
          DSNhuCauDangKy.forEach((element) => { 
            element.TEN_KHO = element.TEN_KHO == "" || element.TEN_KHO == null  ? "-" :element.TEN_KHO
            element.NGAYDUYET = element.NGAYDUYET.replaceAll("-","/"),
            element.NGAY_DK = element.NGAY_DK.replaceAll("-","/")
          })
          // this.dang_ky_id = this.DSNhuCauDangKy[0].DANGKY_ID;
          // this.DSChiTietVatTu = await this.getDSChiTietVatTu(this.dang_ky_id);
          // this.DSChiTietVatTu.forEach(e=>{
          //   e.SOLUONG = e.SOLUONG == "" || e.SOLUONG == null ?  0 : parseInt(e.SOLUONG) 
          //   e.SL_TON_DK = e.SL_TON_DK == "" || e.SL_TON_DK == null ?  0 : parseInt(e.SL_TON_DK)
          // })
        } else {
          this.DSChiTietVatTu = []
        }
        this.listItemChecked = []
        this.DSNhuCauDangKy = DSNhuCauDangKy
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },


    setVisibleBtn: function () {
      if (this.params.p_trang_thai == 1) {
        (this.visible.btnNhanDK = true), (this.visible.btnTraPhieu = true);
        (this.visible.btnKhongDuyet = true), (this.visible.btnHuy = false);
      } else if (this.params.p_trang_thai == 2) {
        (this.visible.btnNhanDK = false),
          (this.visible.btnTraPhieu = false),
          (this.visible.btnKhongDuyet = false),
          (this.visible.btnHuy = true);
        this.txt_btn_huy = "Hủy duyệt";
      } else if (this.params.p_trang_thai == 3) {
        (this.visible.btnNhanDK = false),
          (this.visible.btnTraPhieu = false),
          (this.visible.btnKhongDuyet = false),
          (this.visible.btnHuy = true);
        this.txt_btn_huy = "Hủy trạng thái";
      }
    },
    f_MonthToString: function (value, format = "YYYYMM") {
      return moment(value).format(format);
    },
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },

    async checkTuThang() {
      await this.getDataTheoDieuKien();
    },
    async onChangeTuThang() {
      await this.getDataTheoDieuKien();
    },
    async checkDenThang() {
      await this.getDataTheoDieuKien();
    },
    async onChangeDenThang() {
      await this.getDataTheoDieuKien();
    },
    async checkTuNgay() {
      await this.getDataTheoDieuKien();
    },
    async onChangeTuNgay() {
      await this.getDataTheoDieuKien();
    },
    async checkDenNgay() {
      await this.getDataTheoDieuKien();
    },
    async onChangeDenNgay() {
      await this.getDataTheoDieuKien();
    },
    async checkDonVi() {
      await this.getDataTheoDieuKien();
    },
    // onChangeDonVi: function (e) {
    //   this.getDataTheoDieuKien();
    // },
    async onChangeTrangThai() {
      this.setVisibleBtn();
      await this.getDataTheoDieuKien();
    },
    async gridviewThongTin_FocusedRowChanged(row) {
      if (row) {
        this.loading(true);
        console.log(row , " = row")
        var DSChiTietVatTu = await this.getDSChiTietVatTu(row.DANGKY_ID);
        DSChiTietVatTu.forEach(e=>{
          e.SOLUONG = e.SOLUONG == "" || e.SOLUONG == null ?  0 : parseFloat(e.SOLUONG) 
          e.SL_TON_DK = e.SL_TON_DK == "" || e.SL_TON_DK == null ?  0 : parseFloat(e.SL_TON_DK)
        })
        this.loading(false);
        this.DSChiTietVatTu = DSChiTietVatTu
      }
    },
    async onChangeDonVi(row) {
      if (row && !this.isFirst) {
        console.log("onChangeDonVi")      
        this.params.p_don_vi = row.DONVI_ID;
        this.params.p_ten_don_vi = row.TEN_DV;
        await this.getDataTheoDieuKien();      
      }else{
        this.isFirst = false
      }
    },
    async btnTimKiem() {
      await this.getDataTheoDieuKien();
    },
    btnLamMoi() {
      (this.checkBox.isTuNgay = false),
        (this.checkBox.isDenNgay = false),
        (this.checkBox.isDonVi = false),
        (this.params.p_don_vi = -1),
        (this.params.p_tu_ngay = new Date()),
        (this.params.p_den_ngay = new Date()),
        this.params.p_tu_thang= new Date(),
        this.params.p_den_thang= new Date(),
        this.clearData();
    },

    async btnNhanDK() {
      try{
        if (this.DSNhuCauDangKy.length > 0) {
          var str_dangky_id = this.getStrDangKyId();
          if (str_dangky_id == "") {
            this.$toast.error("Bạn chưa chọn danh sách đăng ký để duyệt");
            return
          }
        
          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Bạn có muốn duyệt các phiếu đăng ký này không?", {
              title: "Xác nhận hành động",
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

        
          this.loading(true);
          this.ktTrangThai = await this.kiemTraTrangThai({
            listDKId: str_dangky_id,
            ttdkId: this.params.p_trang_thai,
          });
          if (this.ktTrangThai != "1") {
            this.$toast.error(this.ktTrangThai);
          }

          this.capNhatNhuCauDK = await this.capNhatNhuCauDangKy({
            kieu: 1,
            chuoiDk: str_dangky_id,
            ndTra: "",
            ngayCN: this.f_DateTimeToString(new Date()),
            nguoiCN: this.thongTinNguoiDung.nguoiCN,
            mayCN: this.thongTinNguoiDung.mayCN,
            ipCN: this.thongTinNguoiDung.ipCN,
          });
          this.loading(false);

          if (this.capNhatNhuCauDK == "1") {
            this.$toast.success("Duyệt đăng ký thành công");
            await this.getDataTheoDieuKien();
          } else {
            this.$toast.error("Có lỗi " + this.capNhatNhuCauDK);
          }
        
        } else {
          this.$toast.error("Không có danh sách đăng ký để duyệt");
        }
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },

    async btnKhongDuyet() {
      try{
        if (this.DSNhuCauDangKy.length > 0) {
          var str_dangky_id = this.getStrDangKyId();
          if (str_dangky_id == "") {
            this.$toast.error("Bạn chưa chọn danh sách đăng ký để duyệt");
            return
          }
            
          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Bạn có chắc chắn không duyệt các phiếu đăng ký này không?", {
              title: "Xác nhận hành động",
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
            
          this.loading(true);
          this.ktTrangThai =await this.kiemTraTrangThai({ listDKId: str_dangky_id, ttdkId: "1" });
          if (this.ktTrangThai != "1") {
            this.$toast.error(this.ktTrangThai);
          }

          this.capNhatNhuCauDK = await this.capNhatNhuCauDangKy({
            kieu: 3,
            chuoiDk: str_dangky_id,
            ndTra: "",
            ngayCN: this.f_DateTimeToString(new Date()),
            nguoiCN: this.thongTinNguoiDung.nguoiCN,
            mayCN: this.thongTinNguoiDung.mayCN,
            ipCN: this.thongTinNguoiDung.ipCN,
          });

          if (this.capNhatNhuCauDK == "1") {
            this.$toast.success("Không duyệt đăng ký thành công");
          } else {
            this.$toast.error("Có lỗi " + this.capNhatNhuCauDK);
          }
          this.loading(false);
          await this.getDataTheoDieuKien();
          
        } else {
          this.$toast.error("Không có danh sách đăng ký để duyệt");
        }
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    getStrDangKyId() {
      var str_dangky_id = this.listItemChecked.toString();
      return str_dangky_id;
    },

    async btnHuy() {
      try{
        if (this.params.p_trang_thai == "2") {
          if (this.DSNhuCauDangKy.length > 0) {
            var str_dangky_id = this.getStrDangKyId();
            if (str_dangky_id == "") {
              this.$toast.error("Bạn chưa chọn danh sách đăng ký hủy duyệt");
              return
            }
              
            var isBoxConfirm = false;
            await this.$bvModal
              .msgBoxConfirm("Bạn có muốn hủy duyệt các phiếu đăng ký này không?", {
                title: "Xác nhận hành động",
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

            this.loading(true);
            this.ktTrangThai = await this.kiemTraTrangThai({
              listDKId: str_dangky_id,
              ttdkId: "2",
            });
            if (this.ktTrangThai != "1") {
              this.$toast.error(this.ktTrangThai);
              this.loading(false);
              return
            }

            this.capNhatNhuCauDK = await this.capNhatNhuCauDangKy({
              kieu: 2,
              chuoiDk: str_dangky_id,
              ndTra: "",
              ngayCN: this.f_DateTimeToString(new Date()),
              nguoiCN: this.thongTinNguoiDung.nguoiCN,
              mayCN: this.thongTinNguoiDung.mayCN,
              ipCN: this.thongTinNguoiDung.ipCN,
            });

            if (this.capNhatNhuCauDK == "1") {
              this.$toast.success("Hủy duyệt đăng ký thành công");
            } else {
              this.$toast.error("Có lỗi " + this.capNhatNhuCauDK);              
            }
            this.loading(false);
            await this.getDataTheoDieuKien();
            
          } else {
            this.$toast.error("Không có danh sách đăng ký để hủy");
          }
        } else if (this.params.p_trang_thai == "3") {
          if (this.DSNhuCauDangKy.length > 0) {

            var str_dangky_id = this.getStrDangKyId();
            if (str_dangky_id == "") {
              this.$toast.error("Bạn chưa chọn danh sách đăng ký hủy trạng thái");
              return
            }
              
            var isBoxConfirm = false;
            await this.$bvModal
              .msgBoxConfirm("Bạn có muốn hủy trạng thái các phiếu đăng ký này không?", {
                title: "Xác nhận hành động",
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

            
            this.loading(true);
            this.capNhatNhuCauDK = await this.capNhatNhuCauDangKy({
              kieu: 4,
              chuoiDk: str_dangky_id,
              ndTra: "",
              ngayCN: this.f_DateTimeToString(new Date()),
              nguoiCN: this.thongTinNguoiDung.nguoiCN,
              mayCN: this.thongTinNguoiDung.mayCN,
              ipCN: this.thongTinNguoiDung.ipCN,
            });

            if (this.capNhatNhuCauDK == "1") {
              this.$toast.success("Hủy trạng thái không duyệt thành công");
            } else {
              this.$toast.error("Có lỗi " + this.capNhatNhuCauDK);
            }
            this.loading(false);
            await this.getDataTheoDieuKien();
            this.loading(false);
          
          } else {
            this.$toast.error("Không có danh sách đăng ký để hủy trạng thái");
          }
        }
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async selectedData_checkbox(dataItem){
      console.log(dataItem, " = dataItem")

      this.listItemChecked = dataItem;
      if(this.DSNhuCauDangKy.length == 0){
        this.listItemChecked = []
      }
      
      // if (this.listItemChecked.length > 0) {
      //   this.loading(true);
      //   var DANGKY_ID = this.listItemChecked[this.listItemChecked.length - 1]
      //   var DSChiTietVatTu = await this.getDSChiTietVatTu(DANGKY_ID);
      //   DSChiTietVatTu.forEach(e=>{
      //     e.SOLUONG = e.SOLUONG == "" || e.SOLUONG == null ?  0 : parseInt(e.SOLUONG) 
      //     e.SL_TON_DK = e.SL_TON_DK == "" || e.SL_TON_DK == null ?  0 : parseInt(e.SL_TON_DK)
      //   })
      //   this.DSChiTietVatTu = DSChiTietVatTu
      //   this.loading(false);
      // }else{
      //   this.DSChiTietVatTu = []
      // }
     
    },
    async btnTraPhieu() {
      if (this.DSNhuCauDangKy.length > 0) {

        var str_dangky_id = this.getStrDangKyId();
        if (str_dangky_id == "") {
          this.$toast.error("Bạn chưa chọn danh sách đăng ký trả lại");
          return
        }
          
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có muốn trả lại các phiếu đăng ký này không?", {
            title: "Xác nhận hành động",
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
    
        this.$bvModal.show("popupNoiDungTra");
       
      } else {
        this.$toast.error("Không có danh sách đăng ký trả lại");
      }
    },

    async onClickXacNhan(){
      try{
        this.$bvModal.hide("popupNoiDungTra");     
        if(this.params.p_nd_tra == ""){
            this.$toast.error("Bạn chưa nhập nội dung trả phiếu");
            return
        }

        this.loading(true);
        var str_dangky_id = this.getStrDangKyId();
        
        this.ktTrangThai = await this.kiemTraTrangThai({ listDKId: str_dangky_id, ttdkId: "1" });
        if (this.ktTrangThai != "1") {
          this.$toast.error(this.ktTrangThai);
        }

        this.capNhatNhuCauDK = await this.capNhatNhuCauDangKy({
          kieu: 0,
          chuoiDk: str_dangky_id,
          ndTra: this.params.p_nd_tra,
          ngayCN: this.f_DateTimeToString(new Date()),
          nguoiCN: this.thongTinNguoiDung.nguoiCN,
          mayCN: this.thongTinNguoiDung.mayCN,
          ipCN: this.thongTinNguoiDung.ipCN,
        });

        
        if (this.capNhatNhuCauDK == "1") {
          this.$toast.success("Trả lại phiếu đăng ký thành công");
        } else {
          this.$toast.error("Có lỗi " + this.capNhatNhuCauDK);
        }
        this.params.p_nd_tra = ""
        this.loading(false);
        await this.getDataTheoDieuKien(); 
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    clearData(){
      this.DSDonViTinh = [];
      this.DSNhuCauDangKy = [];
      this.DSChiTietVatTu = [];
      this.ktTrangThai = "",
      this.capNhatNhuCauDK = "";
      this.listItemChecked = []
    },
    async btnInPhieu(){
      try{
        if(this.listItemChecked.toString() == ""){
          this.$toast.error("Vui lòng chọn nhu cầu đăng ký");
          return
        }
        this.loading(true)
        const baseUrl = process.env.API;
          await this.axios({
            url:
              `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-in-phieu-vt-v2?extend=pdf`,
            method: "POST",
            responseType: "blob",
            data: {
              dangKyId: this.listItemChecked.toString(),
              nhanVienId: this.$auth.getNhanVienID(),
            }
          }).then((response) => {
            previewPrint(response.data);
          });
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    }
  },
  created: async function () {
    try{
      this.loading(true);
      this.clearData();
      await this.loadDataCombo();
    }catch(e){
      this.$toast.error(e.data.message);
    }finally{
      this.loading(false);
    }    
  },
};
</script>
