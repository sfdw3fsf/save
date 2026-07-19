<template src='./DanhSachQuyetToanVatTu.html'></template>
<style scoped src='./DanhSachQuyetToanVatTu.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import { TreeViewPlugin } from "@syncfusion/ej2-vue-navigations";
import xlsx from "xlsx";

import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/danhsachquyettoanvattu";
import {
  Freeze,
  Edit,
  Group,
  Page,
  Filter,
  Sort,
  Resize,
} from "@syncfusion/ej2-vue-grids";
import moment from "moment";
import Vue from "vue";
import { L10n } from "@syncfusion/ej2-base";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import { currency } from "@/filters/currency";
import { Query, Predicate } from "@syncfusion/ej2-data";
import { DropDownListPlugin,MultiSelectPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns';
import {previewPrint} from "../../../utils/util";

import { TooltipPlugin } from "@syncfusion/ej2-vue-popups";
import { Tooltip } from '@syncfusion/ej2-popups';

Vue.use(TooltipPlugin);
MultiSelect.Inject(CheckBoxSelection)
Vue.use(DatePickerPlugin,DropDownListPlugin,MultiSelectPlugin);
L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
    },
  },
});
Vue.use(TreeViewPlugin);
import SearchEProfile from "@/modules/search/subscriber/SearchEProfile";


export default {
  components: {
    SearchEProfile,
    breadcrumb,
  },
  name: "DanhSachQuyetToanVatTu",

  provide: {
    grid: [Freeze, Edit, Group, Page, Filter, Sort, Resize],
  },
  data() {
    return {
     
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      isBoxTieuChi: true,
      classSpanTieuChi : "icon fa fa-angle-up",
      
      thongTinNguoiDung: {
        nguoiCN: this.$root.token.getUserName(), //this.$auth.MaNguoiDung
        donViId: this.$root.token.getDonViID(), // 882
        phanVungId: this.$root.token.getPhanVungID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        nguoiDungId: this.$root.token.getNguoiDungID(),
        donViDLId : this.$root.context.user.getProperty('donvi_dl_id')
      },
      params: {
        tuNgay: new Date(),
        denNgay: new Date(),
        radioTheoNgay : "-1",
        hinhThucId : "0",
        dichVuId: 0,
        nhanVienId : "0",
        baoCaoId:"3",
        trangThaiId : ["1"],
        kieuLapDatId: 0,
        khoId: "0",
        lyDoCapId: [],
        loaiHinhId : 0,
        chuaGiao : true,
        theoNgayNT: true,
        theoNgayCap:false,
        theoNgayGiao:false,
        isQuyetToan:true
      },
      ThueBaoFocus: {},
      DSHinhThuc: [
        {
          NHOM_ID: "1",
          TEN_NHOM: "Thi công"
        },
        {
          NHOM_ID: "2",
          TEN_NHOM: "Báo hỏng"
        },
        {
          NHOM_ID: "0",
          TEN_NHOM: "Tất cả"
        },
      ],
      DSBaoCao: [
        {
          BAOCAO_ID: "3",
          TEN_BAOCAO: "3. Báo cáo chi tiết vật tư giữa tổ trưởng và tổ viên"
        },
        {
          BAOCAO_ID: "7",
          TEN_BAOCAO: "7. Báo cáo thu hồi thiết bị"
        },
      ],
      DSTrangThai: [
          {
            TRANGTHAI_ID: "1",
            TRANGTHAI: "Chưa quyết toán"
          },
          {
            TRANGTHAI_ID: "2",
            TRANGTHAI: "Đã quyết toán"
          },
          {
            TRANGTHAI_ID: "4",
            TRANGTHAI: "Hoàn thành"
          },
        ],
      dataResponse: {
        DSDichVu : [],
        DSNhanVien: [],
        DSKieuLapDat: [],
        DSKho: [],
        DSLyDoCap: [],
        DSLoaiHinh : [],
        DSThueBao: []
      },
     
      fieldsCheckBox: {
        trangThai: { text: "TRANGTHAI", value: "TRANGTHAI_ID" },
        lyDoCap: { text: "mdNew", value: "mucDichId" },
      },
     
      
      onFilteringDichVu: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("tenDVVT", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
      
      onFilteringKieuLapDat: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("tenKieuLd", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
  
      onFilteringNhanVien: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("TEN_NEW", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },

      onFilteringKho: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("TEN_K_NEW", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },

      onFilteringLoaiHinh: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("loaiHinhTB", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
      tooltip: Tooltip
    };
  },

  mounted: async function () {
    
  },
  watch: {
    
  },
  computed: {
    ...mapState("danhsachquyettoanvattu", statePropertyName),
    ...mapGetters("danhsachquyettoanvattu", getterName),
    
  },
  methods: {
    ...mapActions("danhsachquyettoanvattu", actionName),
    ...mapMutations("danhsachquyettoanvattu", mutationName),

    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },


    onClose: function(e) {
      this.tooltip.close();
    },

    onBeforeRender : function(args) {
      var result = this.$refs.cboLyDoCap.dataSource;   
      var resultKLD = this.$refs.cboKieuLapDat.dataSource;
      
      this.tooltip.content = ""  
      // Từ kho   
      
      for (var e of result) {        
        if (e.mdNew === args.target.textContent) {          
          this.tooltip.content = e.mdNew;
          this.tooltip.dataBind();          
          break;
        }
      }

      for (var e of resultKLD) {        
        if (e.tenKieuLd === args.target.textContent) {          
          this.tooltip.content = e.tenKieuLd;
          this.tooltip.dataBind();          
          break;
        }
      }
    },
    onDropdownCreate:  function(args) {        
      this.tooltip = new Tooltip({
          content: '',      
          target: '.e-list-item',     
          position: 'top center',     
          beforeRender: this.onBeforeRender
      });
      this.tooltip.appendTo('body');
    },

    hiddenBoxTieuChi(){
      this.isBoxTieuChi =  !this.isBoxTieuChi
      this.classSpanTieuChi = this.isBoxTieuChi ? "icon fa fa-angle-up" :  "icon fa fa-angle-down"
    },
    async NAP_COMBOBOX(){
      // load danh sách dịch vụ
      this.dataResponse.DSDichVu = await this.getDSDichVu()

      // load danh sách nhân viên
      this.dataResponse.DSNhanVien = await this.getDSNhanVien({
        donViId:  this.thongTinNguoiDung.donViId,
        nhanVienId : this.thongTinNguoiDung.nhanVienId
      })
      if(this.dataResponse.DSNhanVien.length > 0){
        this.dataResponse.DSNhanVien.unshift({
          CHUCDANH: "",
          DIACHI_NV: "",
          DONVI_ID: "",
          GIOITINH: "",
          MA_NV: "",
          NHANVIEN_ID: "0",
          TEN_DV: "",
          TEN_NEW: "Tất cả nhân viên",
          TEN_NV: ""
        })
      }

      // Load Danh sách kiểu lắp đặt
      this.dataResponse.DSKieuLapDat = await this.getDSKieuLapDat()
      
      //load DS Lý do cấp
      await this.getDSHinhThuc()

      //load DS Loại hình
      this.dataResponse.DSLoaiHinh = await this.getDSLoaiHinhThueBao(this.params.dichVuId)
    },
    async onChangeNhanVien(){
      try{
        this.loading(true)
        if(this.params.nhanVienId == 0){
          this.dataResponse.DSKho = [
            {
              KHO_ID: "0",
              MA_KHO: "",
              TEN_KHO: "",
              TEN_K_NEW: "Tất cả kho"
            }
          ]
        }else{
          this.dataResponse.DSKho = await this.getDSKhoTheoNV({
            donViId:  this.thongTinNguoiDung.donViId,
            nhanVienId : this.thongTinNguoiDung.nhanVienId
          })
          this.dataResponse.DSKho.unshift({
            KHO_ID: "0",
            MA_KHO: "",
            TEN_KHO: "",
            TEN_K_NEW: "Tất cả kho"
          })
          this.params.khoId = "0"
        }
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async onChangeHinhThuc(){
      try{
        this.loading(true)
        await this.getDSHinhThuc()      
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async getDSHinhThuc(){
      if(this.params.hinhThucId == 0){
        this.dataResponse.DSLyDoCap = await this.getDSLyDoCap({nhomMdIdArr : [5,6,7,8,9]})
      }else if(this.params.hinhThucId == 1){
        this.dataResponse.DSLyDoCap = await this.getDSLyDoCap({nhomMdIdArr : [6,7,8,9]})
      }else if(this.params.hinhThucId == 2){
        this.dataResponse.DSLyDoCap = await this.getDSLyDoCap({nhomMdIdArr : [5,7,8,9]})
      }
      // remove phần tử TẤT CẢ ra khỏi DS
      this.dataResponse.DSLyDoCap.pop();
    },
    async onChangeDichVu(){
      this.loading(true)
      this.dataResponse.DSLoaiHinh = await this.getDSLoaiHinhThueBao(this.params.dichVuId)
      this.loading(false)
    },

    onChangeTinhTrangGiao(){
      this.params.chuaGiao = !this.params.chuaGiao
    },
    onChangeTheoNgay(kieu){
      if(kieu == "NT"){
        this.params.theoNgayNT = true
        this.params.theoNgayCap = false
        this.params.theoNgayGiao = false
      }else if(kieu == "CAP"){
        this.params.theoNgayNT = false
        this.params.theoNgayCap = true
        this.params.theoNgayGiao = false
      }else if(kieu == "GIAO"){
        this.params.theoNgayNT = false
        this.params.theoNgayCap = false
        this.params.theoNgayGiao = true
      }
    },
    async onClickTimKiem(){
      try{
        this.loading(true)
        if(this.params.lyDoCapId.length == 0){
          this.$toast.error("Hãy chọn ít nhất 1 lý do");
          this.loading(false)
          return
        }
      
        if(this.params.trangThaiId.length == 0){
          this.$toast.error("Hãy chọn ít nhất 1 trạng thái");
          this.loading(false)
          return
        }

        var nv_all = this.thongTinNguoiDung.nhanVienId
        var all = 0
        if(this.params.nhanVienId != "0"){
          nv_all = this.params.nhanVienId
          all = 1
        }

        var ttph = "1"
        if(this.params.trangThaiId.includes("4")){
          ttph = "1,4"
        }

        var tt_giao = this.params.chuaGiao ? "1" : "2"
        
        var timNgay = "1"
        
        if(this.params.theoNgayCap){
          timNgay = "0"
        }

        if(this.params.theoNgayGiao){
          timNgay = "2"
        }


        if(this.params.baoCaoId == "3"){
          
          this.params.isQuyetToan = true
          this.dataResponse.DSThueBao = await this.getDSBaoCaoQuyetToan({
            nhomId: this.params.hinhThucId,
            dichVuVtId: this.params.dichVuId,
            loaiHdId: this.params.loaiHinhId,
            soMay: "0",
            tuNgay: this.f_DateToString(this.params.tuNgay),
            denNgay: this.f_DateToString(this.params.denNgay),
            nhanVienId: nv_all,
            ttVtId: 1,
            timNgay: timNgay,
            timKhoAll: this.params.khoId,
            timNvAll: all,
            donViId: this.thongTinNguoiDung.donViId,
            lyDo: this.params.lyDoCapId.length == this.dataResponse.DSLyDoCap.length ? "0" : this.params.lyDoCapId.toString() ,
            tt: this.params.trangThaiId.toString(),
            ttPh: ttph,
            kieuLdId: this.params.kieuLapDatId,
            ttGiao: tt_giao
          })

        }else if( this.params.baoCaoId == "7" ){
          this.params.isQuyetToan = false

          this.dataResponse.DSThueBao = await this.getDSTongHopThuHoi({
            nhomId: this.params.hinhThucId,
            dichVuVtId: this.params.dichVuId,
            loaiHdId: this.params.loaiHinhId,    
            tuNgay: this.f_DateToString(this.params.tuNgay),
            denNgay: this.f_DateToString(this.params.denNgay),
            nhanVienId: nv_all,
            ttVtId: 1,
            timNgay: timNgay,
            timKhoAll: this.params.khoId,
            timNvAll: all,
            donViId: this.thongTinNguoiDung.donViId,
            lyDo:  this.params.lyDoCapId.length == this.dataResponse.DSLyDoCap.length ? "0" : this.params.lyDoCapId.toString() ,
            status: this.params.trangThaiId.toString(),
            ttPh: ttph,
            kieuLdId: this.params.kieuLapDatId,
            ttGiao: tt_giao
          })

        }
        console.log(this.dataResponse.DSThueBao , " = this.dataResponse.DSThueBao")
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    // async onClickInBaoCao(){
    //   try{
        
    //     const baseUrl = process.env.API;
    //       await this.axios({
    //         url:
    //           `${baseUrl}/web-qlvt/api/in-phieu/getPhieuIn`,
    //         method: "POST",
    //         responseType: "blob",
    //         data: {
    //           ma_bc: "BC-13",
    //           ds_tieuchi: [{}]
    //         }
    //       }).then((response) => {
    //         previewPrint(response.data);
    //       });
    //     this.loading(false)
    //   }catch(e){
    //     this.$toast.error(e.data.message);
    //   }finally{
    //     this.loading(false);
    //   }
    // },
    async onClickInBaoCao(){
      try{
        if(this.params.lyDoCapId.length == 0){
          this.$toast.error("Hãy chọn ít nhất 1 lý do");
          return
        }
      
        if(this.params.trangThaiId.length == 0){
          this.$toast.error("Hãy chọn ít nhất 1 trạng thái");        
          return
        }

        this.loading(true)
        var nv_all = this.thongTinNguoiDung.nhanVienId
        var all = 0
        if(this.params.nhanVienId != "0"){
          nv_all = this.params.nhanVienId
          all = 1
        }

        var ttph = "1"
        if(this.params.trangThaiId.includes("4")){
          ttph = "1,4"
        }

        var tt_giao = this.params.chuaGiao ? "1" : "2"
        
        var timNgay = "1"
        
        if(this.params.theoNgayCap){
          timNgay = "0"
        }

        if(this.params.theoNgayGiao){
          timNgay = "2"
        }

        // var ma_bc = "BC-13" // baoCaoId = 3
        // if(this.params.baoCaoId == "7"){
        //   ma_bc = "BC-17"
        // }

        const baseUrl = process.env.API;
          await this.axios({
            url:
              `${baseUrl}/web-qlvt/api/bao-cao/in-ds-quyet-toan-vt?extend=pdf`,
            method: "POST",
            responseType: "blob",
            data: {
              nhomId: this.params.hinhThucId,
              dichVuVtId: this.params.dichVuId,
              loaiHdId: this.params.loaiHinhId,
              tuNgay: this.f_DateToString(this.params.tuNgay),
              denNgay: this.f_DateToString(this.params.denNgay),
              nhanVienId: nv_all,
              ttVtId: 1,
              timNgay: timNgay,
              timKhoAll: this.params.khoId,
              timNvAll:all,
              donViId: this.thongTinNguoiDung.donViId,
              lyDo: this.params.lyDoCapId.length == this.dataResponse.DSLyDoCap.length ? "0" : this.params.lyDoCapId.toString(),
              tt: this.params.trangThaiId.toString(),
              ttPh: ttph,
              kieuLdId: this.params.kieuLapDatId,
              ttGiao: tt_giao,
              phanVungId : this.$auth.getPhanVungID()
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

    async onClickXuatExcel(){
      try{
        if(this.dataResponse.DSThueBao.length == 0 ){
          this.$toast.error("Không có dữ liệu thuê bao để xuất excel !");
          return
        }
        this.loading(true)

        var NameFile = "QLVT - Danh sách quyết toán vật tư.xls"
        var DSThueBao = xlsx.utils.json_to_sheet(this.dataResponse.DSThueBao);  
        var wb = xlsx.utils.book_new();
        xlsx.utils.book_append_sheet(wb, DSThueBao, "Sheet1");
        xlsx.writeFile(wb, NameFile);
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async onClickHSDienTu(){
      if(this.dataResponse.DSThueBao.length == 0){
        this.$toast.error("Không có thuê bao!")
        return
      }
      if(this.ThueBaoFocus == {}){
        this.$toast.error("Vui lòng chọn thuê bao!")
        return
      }

      if(this.ThueBaoFocus["Số máy/Acc"] == ""){
        this.$toast.error("Không có Số máy/Acc ! Không thể tra cứu hồ sơ điện tử !");
        return
      }

      let f = this.$refs.frmTraCuuHSDT;
      f.MaTb = this.ThueBaoFocus["Số máy/Acc"]
      this.$refs.popupTraCuuHSDT.show()
    },

    async DSThueBaoFocus(row) {
      if (row) {
        this.ThueBaoFocus = row
      }else{
        this.ThueBaoFocus = {}
      }
    }
  },
  
  created: async function () {
    try{
      this.loading(true)
      this.params.radioTheoNgay = "-1"
      await this.NAP_COMBOBOX()
      this.loading(false)
    }catch(e){
      this.$toast.error(e.data.message);
    }finally{
      this.loading(false);
    }
  },
};
</script>
