<template src='./TongHopNhuCauDangKyMuaSam.html'></template>
<style scoped src='./TongHopNhuCauDangKyMuaSam.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/tonghopnhucaudangkymuasam";
import moment from "moment";
import Vue from "vue";

import { Freeze, Edit, Group, Page, Filter, Sort, Resize,GridPlugin, Aggregate, Toolbar} from "@syncfusion/ej2-vue-grids";
import DotTongHop from "../DotTongHop/DotTongHop.vue";
import {previewPrint} from "../../../utils/util";
Vue.use(GridPlugin);

export default {
  components: { DotTongHop,breadcrumb },
  name: "TongHopNhuCauDangKyMuaSam",

  data() {
    return {
      header: {
        title: "TỔNG HỢP NHU CẦU ĐĂNG KÝ VẬT TƯ",

        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tổng hợp nhu cầu đăng ký vật tư",
            link: { name: "Ui.buttons" },
          },   
        ],
      },

      params: {
        chuKy: 0,
        thoiGian: 0,
        nam: moment(new Date()).format("YYYY"),
        dotDangKy: [],
        donVi: [],
        mucDich: [],
        thangTongHop: moment(new Date()).format("MM/YYYY"),
        dotTongHop: "",
        ngayDuyet: "",
        ghiChu: "",
        chonNhanhChuaTH:false,
        chonNhanhDaTH:false,
        loaiTH: null
      },
      cboLoaiTongHop: [
        {
          loaiTHId : "",
          TenTongHop: "Tổng hợp mua sắm tại đơn vị"      
        }, 
        {
          loaiTHId : "3",
          TenTongHop: "Tổng hợp mua sắm tập trung"      
        },
      ],
      lbThoiGian: "Tháng",
      dataThoiGian: {
        listThang: [
          {
            value: 0,
            title: "1",
          },
          {
            value: 1,
            title: "2",
          },
          {
            value: 2,
            title: "3",
          },
          {
            value: 3,
            title: "4",
          },
          {
            value: 4,
            title: "5",
          },
          {
            value: 5,
            title: "6",
          },
          {
            value: 6,
            title: "7",
          },
          {
            value: 7,
            title: "8",
          },
          {
            value: 8,
            title: "9",
          },
          {
            value: 9,
            title: "10",
          },
          {
            value: 10,
            title: "11",
          },
          {
            value: 11,
            title: "12",
          },
        ],
        listQuy: [
          {
            value: 0,
            title: "I",
          },
          {
            value: 1,
            title: "II",
          },
          {
            value: 2,
            title: "III",
          },
          {
            value: 3,
            title: "IV",
          },
        ],
        list6Thang: [
          {
            value: 0,
            title: "đầu",
          },
          {
            value: 1,
            title: "cuối",
          },
        ],
      },
      thongTinNguoiDung: {
        ngayCN: new Date(), //this.$auth.ngayCN
        nguoiCN: this.$root.token.getUserName(), //this.$auth.MaNguoiDung
        mayCN: "", //this.$auth.MAYCN
        ipCN: "", //this.$auth.IPCN
      },
      dataRequest: {
        tuThang: 0,
        denThang: 0,
      },
      DSDangKy : [],
      DSTongHop : [],
      disabledInput: false,
      disabledTongHop: true,
      disabledHuyTongHop: true,
      disabledGhiChu: true,
      disabledInPhieu: true,
      isKHDN: false,
      listThoiGian: [],
      rowSelected: {
        tongHop: [],
        dangKy: [],
      },
      fieldsCheckBox: {
        dotDK: { text: "dotDk", value: "dotDkId" },
        donVi: { text: "tenDonVi", value: "donViId" },
        mucDich: { text: "mucDich", value: "mucDichId" },
      },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      editSettings: { allowEditing: true, mode:'Batch'},
      selectionSettings: {  type: 'Single', checkboxOnly: true },
      rowsCheckedChuaTH : [],
      rowsCheckedDaTH : [],
      columnsCheckboxChuaTH: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.rowsCheckedChuaTH"
                                    :value="data.VTDK_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: { }
              }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              },
            }
          }
        }
      },

      columnsCheckboxDaTH: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.rowsCheckedDaTH"
                                    :value="data.VTTH_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: { }
              }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              },
            }
          }
        }
      },
    };
  },

  computed: {
    ...mapState("tonghopnhucaudangkymuasam", statePropertyName),
    ...mapGetters("tonghopnhucaudangkymuasam", getterName),
  },

  methods: {
    ...mapActions("tonghopnhucaudangkymuasam", actionName),
    ...mapMutations("tonghopnhucaudangkymuasam", mutationName),

    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },
    setTuThang() {
      if (this.params.chuKy == 0) {
        this.dataRequest.tuThang =
          this.params.nam * 100 + this.params.thoiGian + 1;
      } else if (this.params.chuKy == 1) {
        this.dataRequest.tuThang =
          this.params.nam * 100 + this.params.thoiGian * 3 + 1;
      } else {
        this.dataRequest.tuThang =
          this.params.nam * 100 + this.params.thoiGian * 6 + 1;
      }
    },
    onChangeNgayDuyet() {
     
    },
    setDenThang() {
      if (this.params.chuKy == 0) {
        this.dataRequest.denThang =
          this.params.nam * 100 + this.params.thoiGian + 1;
      } else if (this.params.chuKy == 1) {
        this.dataRequest.denThang =
          this.params.nam * 100 + this.params.thoiGian * 3 + 3;
      } else {
        this.dataRequest.denThang =
          this.params.nam * 100 + this.params.thoiGian * 6 + 6;
      }
    },

    async onClickTimKiem() {
      try{
        await this.NAP_DS_DANGKY();
        if (this.DSDangKy.length == 0) {
          this.$toast.error("Không tìm thấy đăng ký nào phù hợp");
        }
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async onClickLamMoi() {
      try{
        this.params.thangTongHop = moment(new Date()).format("MM/YYYY");
        this.params.dotTongHop = "";
        this.params.ngayDuyet = "";
        this.params.ghiChu = "";
        this.clearData();    
        this.loading(true);
        await this.getDotDangKy()
        await this.getDSDonVi();
        await this.getDSMucDich();
        await this.getDataTongHop();
        this.loading(false);
        this.NAP_DS_DANGKY();
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },

    onClickRefreshDangKy() {
      this.rowsCheckedChuaTH = []
      this.NAP_DS_DANGKY();
    },
    CAPNHAT_BUTTON_THDK(){
      this.disabledHuyTongHop = this.DSTongHop.length > 0 ? false : true
      this.disabledTongHop = this.DSDangKy.length > 0 ? false : true
      this.disabledInPhieu = this.disabledHuyTongHop
    },
    async NAP_DS_DANGKY(){
      try{
        this.loading(true)
        this.setTuThang();
        this.setDenThang();
        var DSDangKy = await this.getDSDangKy({
          dot: this.params.dotDangKy.toString(),
          donViId: this.params.donVi.toString(),
          munDichId: this.params.mucDich.toString(),
          thangDKTu: this.dataRequest.tuThang,
          thangDKDen: this.dataRequest.denThang,
        });
        DSDangKy.forEach(e=>{
          e.SOLUONG = e.SOLUONG == null || e.SOLUONG == "" ? 0 :  parseFloat(e.SOLUONG)
          e.SL_DUYET =  e.SL_DUYET == null || e.SL_DUYET == "" ? 0 :  parseFloat(e.SL_DUYET)
          e.SL_DUYET_KT =  e.SL_DUYET_KT == null || e.SL_DUYET_KT == "" ? 0 :  parseFloat(e.SL_DUYET_KT)
        })
        this.DSDangKy = DSDangKy
        this.rowsCheckedChuaTH = [] // reset lại checkbox
        this.CAPNHAT_BUTTON_THDK()
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async NAP_DS_TONGHOP(){
      this.DSTongHop = []
      var DSTongHop = await this.getDSTongHop(this.params.dotTongHop);
      DSTongHop.forEach(e=>{
        e.SOLUONG = e.SOLUONG == null || e.SOLUONG == "" ? 0 :  parseFloat(e.SOLUONG)
      })
      this.DSTongHop = DSTongHop
      this.rowsCheckedDaTH = [] // reset lại checkbox
      this.CAPNHAT_BUTTON_THDK()
    },
    async onClickRefreshTongHop() {
      this.loading(true);
      this.rowsCheckedDaTH = []
      await this.NAP_DS_TONGHOP()
      this.loading(false);
    },

    onChangeChuKy() {
      if (this.params.chuKy == 0) {
        this.lbThoiGian = "Tháng";
        this.listThoiGian = this.dataThoiGian.listThang;
        this.params.thoiGian = this.listThoiGian[0].value
      } else if (this.params.chuKy == 1) {
        this.lbThoiGian = "Quý";
        this.listThoiGian = this.dataThoiGian.listQuy;
        this.params.thoiGian = this.listThoiGian[0].value
      } else {
        this.lbThoiGian = "6 Tháng";
        this.listThoiGian = this.dataThoiGian.list6Thang;
        this.params.thoiGian = this.listThoiGian[0].value
      }
      this.NAP_DS_DANGKY();
    },

    async onChangeThangTH() {
      try{
        this.loading(true);
        if (!this.validDate(this.params.thangTongHop)) {
          this.$toast.error("Tháng tổng hợp sai đinh dạng MM/YYYY");
          return;
        }    
        
        await this.getDataTongHop();        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
    },

    async onChangeDotTH() {
      try{
        this.loading(true);
        await this.NAP_DS_TONGHOP()
        var changeNgayDuyet = this.DSDotTongHop.filter(
          (e) => e.dotTHId == this.params.dotTongHop
        );
        if(changeNgayDuyet.length > 0){
          if(this.$auth.getPhanVungID() == 97){
            this.params.loaiTH = changeNgayDuyet[0].kieuTH == null ? "" : changeNgayDuyet[0].kieuTH        
          }
          this.params.ngayDuyet = changeNgayDuyet[0].ngayDuyet != null ? new Date(changeNgayDuyet[0].ngayDuyet) : "";
          this.params.ghiChu = changeNgayDuyet[0].ghiChu;
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
    },

    onChangeDonVi() {
      this.NAP_DS_DANGKY();
    },
    onChangeMucDich() {
      this.NAP_DS_DANGKY();
    },
    onChangeDotDangKy() {
      this.NAP_DS_DANGKY();
    },
    async onChangeThoiGian() {      
      await this.getDotDangKy()
      await this.NAP_DS_DANGKY();
    },

    async getDataTongHop() {
      var thangTongHop = moment(this.params.thangTongHop, "MM/YYYY").format(
        "YYYYMM"
      );
      await this.getDSDotTongHop(thangTongHop); //201901
      if (this.DSDotTongHop.length > 0) {
        this.disabledInput = false;
        this.params.dotTongHop = this.DSDotTongHop[0].dotTHId;      
        await this.onChangeDotTH()   
        await this.NAP_DS_TONGHOP()
      } else {
        this.disabledInput = true;
        this.DSTongHop = []
      }
     
    },

    onClickDuyet: async function () {
      try{
      // Kiểm tra tổng hợp ID
        if (this.params.dotTongHop == "") {
          this.$toast.error("Bạn chưa chọn đợt tổng hợp");
          return;
        }
        
        if (this.params.ngayDuyet == "" ||  this.params.ngayDuyet == null) {
          this.$toast.error("Bạn chưa chọn ngày duyệt");
          return;
        }
        
        // Xác Thực
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có thực sự muốn duyệt đợt tổng hợp đang chọn không ?",
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

        if (isBoxConfirm) {
          this.loading(true);
          await this.kiemTraYeuCauTongHop(this.params.dotTongHop);
          if (this.kTTongHop < 1) {
            this.$toast.error("Bạn chưa tổng hợp vật tư");
            this.loading(false);
            return;
          }

          await this.kiemTraNgayDuyet(this.params.dotTongHop);
          if (this.kTNgayDuyet != 0) {
            this.$toast.error("Đợt tổng hợp đã duyệt, không thể duyệt thêm");
            this.loading(false);
            return;
          }

          await this.duyetHuyDuyetTongHop({
            ngayDuyet: this.f_DateToString(this.params.ngayDuyet),
            ghiChu: this.params.ghiChu == null ? "" : this.params.ghiChu,
            ngayCN: this.f_DateTimeToString(this.thongTinNguoiDung.ngayCN),
            nguoiCN: this.thongTinNguoiDung.nguoiCN,
            mayCN: this.thongTinNguoiDung.mayCN,
            ipCN: this.thongTinNguoiDung.ipCN,
            tongHopId: this.params.dotTongHop,
            type: "DUYET",
          });

          if (this.kqDuyetHuyDuyet == 1) {
            // this.params.dotTongHop = "";
            var thangTongHop = moment(this.params.thangTongHop, "MM/YYYY").format(
              "YYYYMM"
            );
            await this.getDSDotTongHop(thangTongHop); //201901
            if (this.DSDotTongHop.length > 0) {
              this.disabledInput = false;
              // this.params.dotTongHop = this.DSDotTongHop[0].dotTHId;
              await this.onChangeDotTH()
            } else {
              this.disabledInput = true;
            }            
            this.$toast.success("Duyệt thành công");
        
          }else{
            this.$toast.error("Duyệt thất bại");
          }          
        }
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    validDate(dValue) {
      var isCheck = true;
      dValue = dValue.split("/");
      var patternMM = /^\d{2}$/;
      var patternYYYY = /^\d{4}$/;

      if (dValue[0] < 1 || dValue[0] > 12) isCheck = false;

      if (!patternMM.test(dValue[0]) || !patternYYYY.test(dValue[1]))
        isCheck = false;

      if (dValue[2]) isCheck = false;

      return isCheck;
    },
    onClickHuyDuyet: async function () {
      try{
        // Kiểm tra tổng hợp ID
        if (this.params.dotTongHop == "") {
          this.$toast.error("Bạn chưa chọn đợt tổng hợp");
          return;
        }

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
        this.loading(true);
        if (isBoxConfirm) {
          await this.kiemTraNgayDuyet(this.params.dotTongHop);
          if (this.kTNgayDuyet == 0) {
            this.$toast.error("Đợt tổng hợp chưa duyệt, không thể hủy duyệt");
            this.loading(false);
            return;
          }

          await this.kiemTraHopDongDaLap(this.params.dotTongHop);
          if (this.kiemTraLapHopDong.length > 0) {
            this.$toast.error("Bạn đã lập hợp đồng không thể hủy duyệt");
            this.loading(false);
            return;
          }

          await this.kiemTraChungTuDaLap(this.params.dotTongHop);
          if (this.kiemTraLapChungTu.length > 0) {
            this.$toast.error("Bạn đã lập chứng từ không thể hủy duyệt ");
            this.loading(false);
            return;
          }

          await this.duyetHuyDuyetTongHop({
            ngayDuyet: this.f_DateToString(this.params.ngayDuyet),
            ghiChu: this.params.ghiChu == null ? "" : this.params.ghiChu,
            ngayCN: this.f_DateTimeToString(this.thongTinNguoiDung.ngayCN),
            nguoiCN: this.thongTinNguoiDung.nguoiCN,
            mayCN: this.thongTinNguoiDung.mayCN,
            ipCN: this.thongTinNguoiDung.ipCN,
            tongHopId: this.params.dotTongHop,
            type: "HUYDUYET",
          });

          if (this.kqDuyetHuyDuyet == 1) {
            // this.params.dotTongHop = "";
            var thangTongHop = moment(this.params.thangTongHop, "MM/YYYY").format(
              "YYYYMM"
            );
            await this.getDSDotTongHop(thangTongHop); //201901
            if (this.DSDotTongHop.length > 0) {
              this.disabledInput = false;
              // this.params.dotTongHop = this.DSDotTongHop[0].dotTHId;
              await this.onChangeDotTH()
            } else {
              this.disabledInput = true;
            }
            this.$toast.success("Hủy duyệt thành công");
            
          }else{
            this.$toast.error("Hủy duyệt thất bại");
          }          
        }
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
   
    async onClickSaveGhiChu() {
      var dataRequest = [];
      
      var rowsCheckedDaTH = this.rowsCheckedDaTH
      let rowSelectedTongHop = this.DSTongHop.filter(function (e) {
        return rowsCheckedDaTH.includes(e.VTTH_ID);
      });
      if (rowSelectedTongHop.length == 0) {
        this.$toast.error("Hãy chọn tổng hợp để cập nhật ghi chú");
        return;
      }

      for (const i in rowSelectedTongHop) {
        dataRequest.push({
          vtthId: rowSelectedTongHop[i].VTTH_ID,
          ghiChu: rowSelectedTongHop[i].GHICHU,
        });
      }

      this.loading(true);
      await this.capNhatGhiChu({ listGhiChuDKVTIn: dataRequest });
      if (this.ktCapNhatGhiChu == 1) {
        this.$toast.success("Cập nhật thành công");
        this.loading(false);
        return;
      }
      this.loading(false);
      this.$toast.error("Lỗi khi cập nhật ghi chú");
    },

    onClickTongHop: async function () {
      try{
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có thực sự muốn tổng hợp các đơn đăng ký đang chọn không ?",
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
          return;
        }

        var rowsCheckedChuaTH = this.rowsCheckedChuaTH
        let rowSelectedDangKy = this.DSDangKy.filter(function (e) {
          return rowsCheckedChuaTH.includes(e.VTDK_ID);
        });
        var listVTDKId = [];
        var data = [];
        for(const i in rowSelectedDangKy){
          listVTDKId.push(rowSelectedDangKy[i].VTDK_ID)
          data.push({
            VTDK_ID : rowSelectedDangKy[i].VTDK_ID,
            SL_DUYET : rowSelectedDangKy[i].SL_DUYET
          })
        }
      
        var sLCheck = rowSelectedDangKy.length;
        listVTDKId = listVTDKId.toString();
    
        if (listVTDKId == "") {
          this.$toast.error("Hãy chọn đơn đăng ký để tổng hợp");
          return;
        }

        this.loading(true);
        await this.kiemTraNgayDuyet(this.params.dotTongHop);
        if (this.kTNgayDuyet != 0) {
          this.$toast.error("Đợt tổng hợp đã duyệt, không thể thêm tổng hợp");
          this.loading(false);
          return;
        }

        await this.kiemTraCungChuKy({
          listVTDKId: listVTDKId,
          tongHopId: this.params.dotTongHop,
        });
        if (this.ktCungChuKy[0] != 0 && this.ktCungChuKy[0] != 1) {
          this.$toast.error(
            "Các đơn đăng ký phải cùng chu kỳ mới được tổng hợp"
          );
          this.loading(false);
          return;
        }

        await this.tongHopNhuCauDangKy({
          slCheck: sLCheck,
          vtdkId: listVTDKId,
          data: JSON.stringify(data),
          ngayCN: this.f_DateTimeToString(this.thongTinNguoiDung.ngayCN),
          nguoiCN: this.thongTinNguoiDung.nguoiCN,
          mayCN: this.thongTinNguoiDung.mayCN,
          ipCN: this.thongTinNguoiDung.ipCN,
          tongHopId: this.params.dotTongHop,
        });

        if (this.ktKetQuaTongHop == 1) {
          this.NAP_DS_TONGHOP();
          this.NAP_DS_DANGKY();
          this.loading(false);
          this.$toast.success("Tổng hợp đăng ký nhu cầu vật tư thành công");
          this.rowsCheckedChuaTH = []
          this.rowsCheckedDaTH = []
          return;
        }
        this.$toast.error("Lỗi khi lưu tổng hợp");
        this.loading(false);
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },

    async onClickHuyTongHop() {
      try{
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có thực sự muốn xóa tổng hợp đang chọn không ?", {
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

        var rowsCheckedDaTH = this.rowsCheckedDaTH
        let rowSelectedTongHop = this.DSTongHop.filter(function (e) {
          return rowsCheckedDaTH.includes(e.VTTH_ID);
        });
        var listVTDKId = [];
        rowSelectedTongHop.forEach((item) => listVTDKId.push(item.VTTH_ID));
        var listVTDKId = listVTDKId.toString();

        if (listVTDKId == "") {
          this.$toast.error("Hãy chọn tổng hợp để xóa");
          return;
        }

        this.loading(true);
        await this.kiemTraNgayDuyet(this.params.dotTongHop);
        if (this.kTNgayDuyet != 0) {
          this.$toast.error("Đợt tổng hợp đã duyệt, không thể xóa");
          this.loading(false);
          return;
        }

        await this.xoaTongHopDangKy(listVTDKId);
        if (this.ktXoaTongHop == 1) {
          this.$toast.success("Xóa tổng hợp đăng ký nhu cầu vật tư thành công");
          this.NAP_DS_TONGHOP();        
          this.NAP_DS_DANGKY();
          this.loading(false);
          this.rowsCheckedDaTH = []
          this.rowsCheckedChuaTH = []
          return;
        }
        this.loading(false);
        this.$toast.success("Lỗi khi xóa tổng hợp");
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    
    async onClickDotTongHop(){
      let frm = this.$refs.frmDotTongHop
      // frm.tagFrom = this.params.loaiTH 
      frm.params.Thang = new Date(moment(this.params.thangTongHop, "MM/YYYY").format("YYYY/MM"));
      await frm.Open()
    },
    async closeDotTongHop(){
      
      let frm = this.$refs.frmDotTongHop
      if(frm.params.tongHopId != -1){        
        this.params.loaiTH = frm.params.loaiTH
        this.params.thangTongHop = moment(frm.params.Thang).format("MM/YYYY");
        var thangTongHop = moment(frm.params.Thang, "MM/YYYY").format("YYYYMM");
        await this.getDSDotTongHop(thangTongHop); //201901
        this.disabledInput = this.DSDotTongHop.length == 0
        this.params.dotTongHop = frm.params.tongHopId
        this.params.ghiChu = frm.params.ghiChu
        await this.onChangeDotTH()
      }
    },
  
    onClickChonNhanhChuaTH(){
      this.rowsCheckedChuaTH = []
      if(!this.params.chonNhanhChuaTH){
        this.DSDangKy.forEach( e => {             
          e.SL_DUYET = e.SOLUONG
          this.rowsCheckedChuaTH.push(e.VTDK_ID)
        })     
      }else{
        this.DSDangKy.forEach( e => {             
          e.SL_DUYET = 0
        })    
      }
      this.$refs.gridDangKy.grid.refreshColumns()
    },
    
    onClickChonNhanhDaTH(){
      this.rowsCheckedDaTH = []
      if(!this.params.chonNhanhDaTH){
        this.DSTongHop.forEach( e => {             
          this.rowsCheckedDaTH.push(e.VTTH_ID)
        })     
      }
    },
    async editGridChuaTH(arg) {

      let gridDangKy = this.$refs.gridDangKy
      let changes = gridDangKy.getBatchChanges()    
   
      if(changes.changedRecords.length == 0){
        return
      }
      if(arg.columnName == "SL_DUYET" ){

        if(changes.changedRecords[0].SL_DUYET > changes.changedRecords[0].SOLUONG ){

          changes.changedRecords[0].SL_DUYET = 0         
          this.rowsCheckedChuaTH = this.removeElementInArray(this.rowsCheckedChuaTH , changes.changedRecords[0].VTDK_ID)        
          this.$toast.error("Số lượng duyệt không được lớn hơn số lượng hiện tại!")

        }else if(changes.changedRecords[0].SL_DUYET < 0){

          changes.changedRecords[0].SL_DUYET = 0 
          this.rowsCheckedChuaTH = this.removeElementInArray(this.rowsCheckedChuaTH , changes.changedRecords[0].VTDK_ID)
          this.$toast.error("Số lượng duyệt không được âm!")

        }else if(changes.changedRecords[0].SL_DUYET == 0){

          this.rowsCheckedChuaTH = this.removeElementInArray(this.rowsCheckedChuaTH , changes.changedRecords[0].VTDK_ID)

        }else{   
          if(!this.rowsCheckedChuaTH.includes(changes.changedRecords[0].VTDK_ID)){
            this.rowsCheckedChuaTH.push(changes.changedRecords[0].VTDK_ID)
          }          
        }
      }
     
      gridDangKy.batchUpdate(changes)
      this.$refs.gridDangKy.grid.refreshColumns()
    },
    async editGridDaTH(arg) {
      let gridTongHop = this.$refs.gridTongHop
      if(changes.changedRecords.length == 0){
        return
      }
      let changes = gridTongHop.getBatchChanges()    
      gridTongHop.batchUpdate(changes)
      this.$refs.gridTongHop.grid.refreshColumns()
    },

    removeElementInArray(array,element){
      const index = array.indexOf(element);
      if (index > -1) {
        array.splice(index, 1);
      }
      return array
    },
    async onClickPhieuThang(){
      // let test = await this.getParamsBaoCao("TH_NC_VT")
      // console.log(test , " = test")
      try{
        this.loading(true)
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-qlvt/api/in-phieu/getPhieuIn?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            ma_bc: "TH_NC_VT",
            ds_tieuchi: [
              { 
                name: 'p_phanvung_id',
                value: this.$auth.getPhanVungID()
              },
              {
                name: 'p_dotthang',
                value: moment(this.params.thangTongHop, "MM/YYYY").format("YYYYMM")
              },
              {
                name: 'p_kieu',
                value: 0
              }
            ]
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
    },
    async onClickPhieuDot(){
      try{
        if(this.params.dotTongHop == ""){
          this.$toast.error("Hãy chọn đợt tổng hợp để in")
          return
        }
        this.loading(true)
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-qlvt/api/in-phieu/getPhieuIn?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {           
            ma_bc: "TH_NC_VT",
            ds_tieuchi: [
              { 
                name: 'p_phanvung_id',
                value: this.$auth.getPhanVungID()
              },
              {
                name: 'p_dotthang',
                value: this.params.dotTongHop
              },
              {
                name: 'p_kieu',
                value: 1
              }          
            ]
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
    },
    // async onClickPhieuThang(){
    //   try{
    //     this.loading(true)
    //     const baseUrl = process.env.API;
    //     await this.axios({
    //       url:
    //         `${baseUrl}/web-qlvt/api/bao-cao/tong-hop-dang-ky-vt-theo-thang?extend=pdf`,
    //       method: "POST",
    //       responseType: "blob",
    //       data: {
    //         dotThang: moment(this.params.thangTongHop, "MM/YYYY").format("YYYYMM"),
    //         kieu: "0",
    //       }
    //     }).then((response) => {
    //       previewPrint(response.data);
    //     });
    //     this.loading(false)
    //   }catch(e){
    //     this.$toast.error(e.data.message);
    //   }finally{
    //     this.loading(false);
    //   }
    // },
    // async onClickPhieuDot(){
    //   try{
    //     if(this.params.dotTongHop == ""){
    //       this.$toast.error("Hãy chọn đợt tổng hợp để in")
    //       return
    //     }
    //     this.loading(true)
    //     const baseUrl = process.env.API;
    //     await this.axios({
    //       url:
    //         `${baseUrl}/web-qlvt/api/bao-cao/tong-hop-dang-ky-vt-theo-dot?extend=pdf`,
    //       method: "POST",
    //       responseType: "blob",
    //       data: {
    //         tongHopId: this.params.dotTongHop,
    //         kieu: "1",
    //       }
    //     }).then((response) => {
    //       previewPrint(response.data);
    //     });
    //     this.loading(false)
    //   }catch(e){
    //     this.$toast.error(e.data.message);
    //   }finally{
    //     this.loading(false);
    //   }
    // },
    async onChangeNam(){
      console.log("onChangeNam")
      this.params.nam = this.params.nam < 1970 ? 1970 : this.params.nam
      await this.getDotDangKy()
    },
    async getDotDangKy(){
      this.setTuThang();
      this.setDenThang();
      await this.getDSDotDangKy({
        tuThang: this.dataRequest.tuThang,
        denThang: this.dataRequest.denThang,
      });
    },
  },

  watch:{
    "rowsCheckedChuaTH": function(value){     
      this.rowsCheckedChuaTH = value 
      this.DSDangKy.forEach(e=>{
        if(this.rowsCheckedChuaTH.includes(e.VTDK_ID) && e.SL_DUYET == 0 ){          
         e.SL_DUYET = e.SOLUONG
        }else if(!this.rowsCheckedChuaTH.includes(e.VTDK_ID)){
          e.SL_DUYET = 0
        }
      })
      this.$refs.gridDangKy.grid.refreshColumns()
    },

    "rowsCheckedDaTH": function(value){     
      this.rowsCheckedDaTH = value  
    },
   
  },
  created: async function () {
    try{
      this.loading(true);
      this.isKHDN = this.$auth.getPhanVungID() == 97
      this.clearData();
      this.listThoiGian = this.dataThoiGian.listThang;
      await this.getDotDangKy()
      await this.getDSDonVi();
      await this.getDSMucDich();
      await this.getDataTongHop();
      this.loading(false);
      this.NAP_DS_DANGKY();
    }catch(e){
      this.$toast.error(e.data.message);
    }finally{
      this.loading(false);
    }
  },
  provide: {
    grid: [ Freeze, Edit,Group, Page,Filter,Sort,Resize ,Aggregate,Toolbar]
  },
};
</script>
