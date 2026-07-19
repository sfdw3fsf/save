<template src='./QLTS.html'></template>
<style scoped src='./QLTS.scss'></style>
<script>

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from '@/components/breadcrumb'
import { getterName,actionName,statePropertyName,mutationName} from '../store/qlts'
import Vue from "vue";
import xlsx from "xlsx";
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { Query, Predicate } from "@syncfusion/ej2-data";
import moment from "moment";
import EventBus from '@/utils/eventBus'
import {previewPrint} from "../../../utils/util";

Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb},
  name: "QLTS",
  mounted() {
  },
  
  data() {
    return {
      
      id_chungtu:0,
      sl_cothe_gan: 0,
      Tag: "",
      tag_form : "",
      xacnhan:0,
      luu_serial_tracuu: [],

      DSTTTS: [],
      DSSerial: [],
      DSTbiChuaGan: [],
      DSTTTS_Checked: [],
      rowSerialSelected : {},
      gridSerial_indexRow: -1,
      tsbtnTaoTbi:{
        Tag : "1",
        Text: "Tạo thiết bị"
      },
      DSOption:[
        {
          kieu_id: 1,
          kieu: 'QRCode'
        },
        {
          kieu_id: 2,
          kieu: 'BarCode'
        },
        {
          kieu_id: 3,
          kieu: 'Serial'
        },
        {
          kieu_id: 4,
          kieu: 'IDChitiet'
        },
      ],
      params:{
        txtSerial: "0052702442626A8",
        cboOption: 3,
      },

      Visible:{
        tsbtnTaoTbi: true,
        tsbtnXacNhan: true,
        tsbtnFileMau: true,
        tsbtnInTem:  true,
        tsbtnXuatQRCode: true
      },
      Enabled:{
        tsbtnExcel: true
      },
      ReadOnly:{
        txtSerial: false
      },
      colThemMaVT: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>
                          <button class="btn btn-main btn-primary" @click="themMaVT">
                              <span class="act -ap icon-chevron-thin-left"></span>
                          </button>
                      </div>`,                        
            data () { 
              return { data: {} }; 
            },
            methods: {
              themMaVT() {                  
                  EventBus.$emit('themMaVT', this.data);
              },              
            }
          })
        }
      },
      colHuyGan: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>
                          <button class="btn btn-main btn-primary" @click="huyGan">
                              <span class="act -ap icon-chevron-thin-right"></span>
                          </button>
                      </div>`,                        
            data () { 
              return { data: {} }; 
            },
            methods: {
              huyGan() {                  
                  EventBus.$emit('huyGan', this.data);
              },              
            }
          })
        }
      },

      colXoaSeri: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>
                          <button class="btn btn-main btn-danger" @click="xoaSeri">
                              <span class="-ap icon-close"></span>
                          </button>
                      </div>`,                        
            data () { 
              return { data: {} }; 
            },
            methods: {
              xoaSeri() {                  
                  EventBus.$emit('xoaSeri', this.data);
              },              
            }
          })
        }
      },

      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
    };
  },

  computed: {
    ...mapState("qlts", statePropertyName),
    ...mapGetters("qlts", getterName),

    
  },
  methods: {
    ...mapActions("qlts", actionName),
    ...mapMutations("qlts", mutationName),

    
    async openQLTS(){
      try{
        this.loading(true)
        EventBus.$on('themMaVT', this.themMaVT_Handler);
        EventBus.$on('huyGan', this.huyGan_Handler);
        EventBus.$on('xoaSeri', this.xoaSeri_Handler);
        await  this.frmQLTS_Load()
        this.$refs.popupQLTS.show();
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    closeQLTS(){
      if(this.tag_form == "1"){
        this.$parent.XacNhan_frmQLTS()
      }
      this.$refs.popupQLTS.hide();
    },
    async frmQLTS_Load(){
      this.tag_form = this.Tag
      if(this.tag_form == "1"){ // Gọi từ menu nhapxuatv2
        this.Visible.tsbtnFileMau = false
        this.Visible.tsbtnInTem = false
        this.Visible.tsbtnXuatQRCode = false
      }else{ //tag = 2 => gọi từ menu tra cứu kiểm kê ||| tag = 3 gọi từ nút in tem menu nhập xuất v2 để in tem
        this.Visible.tsbtnTaoTbi = false
        this.Visible.tsbtnXacNhan = false

        if(this.tag_form == "3"){
          this.ReadOnly.txtSerial = true
          this.Enabled.tsbtnExcel = false
          var dtThietBi = await this.getDSThietBiChungTu(this.id_chungtu)
          console.log(dtThietBi , " = dtThietBi")
          var dt_loaitbi = dtThietBi.filter(function (el) {
              return el.LOAITBI_ID == 2;
            }
          );
          var DSSerialTheoLoaiTbi = []
          dt_loaitbi.forEach(e => {
            DSSerialTheoLoaiTbi.push(e.SERIAL)
          });
 

          if(DSSerialTheoLoaiTbi.length > 0){
            var serial_tren_dhsx =  await this.getDSSerialDHSX(DSSerialTheoLoaiTbi) // gộp Serial tránh call API nhiều lần
          
            for(const i of serial_tren_dhsx){
              var row = this.tracuu_serial_tren_qlts(i , "DHSX", true)
              this.DSTTTS.push(row) // append từng dòng rồi gán tránh grid load lại nhiều lần
            } 
          }
          
        }
      }
      await this.load_ds_vattu()
    },
    tracuu_serial_tren_qlts(tracuu, hethong, is_tracuu_dhsx){
      var temp = []
      if(is_tracuu_dhsx){
        temp.push({
          maVt : tracuu.maVt,
          serial : tracuu.serial,
          qrCode : tracuu.qrCode,
        })
      }else{
        temp.push(tracuu)
      }
      temp[0].HETHONG = hethong
      return temp[0]
    },
    async load_ds_vattu(){
      this.DSTbiChuaGan = await this.getDSThietBiChuaGan(this.id_chungtu)
      console.log(this.DSTbiChuaGan , " = DSTbiChuaGan")
    },
    cboOption_EditValueChanged(){
      if(this.params.cboOption != 3){
        this.$toast.error("Chỉ hổ trợ Option Serial");
        this.params.cboOption = 3
      }
    },
    async tsbtnXacNhan_Click(){
      try{
        if(this.DSSerial.length == 0){
          this.$toast.error("Không có dữ liệu Serial");
          return
        }
        if(this.DSSerial.length != this.sl_cothe_gan){
          this.$toast.error("Chưa chọn hết số lượng serial cùa vật tư: " + this.DSSerial.length + "/" + this.sl_cothe_gan);
          return
        }
        var data = []
        for(const i of this.DSSerial){
          if(i.maVt == "" || i.maVt == null){
            this.$toast.error("Serial " + i.serial + " chưa được gán mã vật tư !!!");
            return
          }
          data.push({
            CTCT_ID: i.CTCT_ID,
            SERIAL: i.seri,
            QR_CODE: i.qrCode,
            IDCHITIET: i.IDCHITIET,
            MA_DU_AN: i.ma_du_an
          })
        }
        var ketQua = await this.capNhatSerialQLTS({
          chungTuId: this.id_chungtu,
          nguoiCn: "", // BE lấy 
          mayCn: "",
          ipCn: "",
          kieu: 0,
          ds:data
        })
        if(ketQua != "1"){
          this.$toast.error(ketQua);
          return
        }
        this.xacnhan = true
        this.closeQLTS()
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    tsbtnTaoTbi_Click(){
      if(this.tsbtnTaoTbi.Tag == "0"){
        this.tsbtnTaoTbi.Tag = "1"
        this.ReadOnly.txtSerial = true
        this.tsbtnTaoTbi.Text = "Thu gọn"
      }else{
        this.tsbtnTaoTbi.Tag = "0"
        this.ReadOnly.txtSerial = false
        this.tsbtnTaoTbi.Text = "Tạo thiết bị"
      }
    },
    tsbtnXuatQRCode_Click(){
      
      if(this.DSTTTS_Checked.length == 0){
        this.$toast.error("Không có dữ liệu để xuất excel");
        return
      }
      var DSXuatQRCODE = this.DSTTTS_Checked.filter(
        function (el) {
          return el.qrCode != "" && el.qrCode != null;
        }
      );

      if(DSXuatQRCODE.length == 0){
        this.$toast.error("Bạn chưa chọn thiết bị cần xuất ");
        return
      }
      this.loading(true)
      var DataColExcel = []
      DSXuatQRCODE.forEach(e=>{
        DataQRCODE.push({
          QR_CODE : e.qrCode
        })
      })

      var NameFile = "QR-" + this.f_DateToString(new Date()) +".xls"
      var DataExcel = xlsx.utils.json_to_sheet(DataColExcel);  
      var wb = xlsx.utils.book_new();
      xlsx.utils.book_append_sheet(wb, DataExcel, "Sheet1");
      xlsx.writeFile(wb, NameFile);
      this.loading(false)
    },
    f_DateToString: function (value, format = "YYYYMMDD") {
      return moment(value).format(format);
    },
    DSTTTSCheckox: function (dataItem) {
      this.DSTTTS_Checked = dataItem;
      console.log(this.DSTTTS_Checked , " = DSTTTS_Checked")
    },
    async txtSerial_KeyPress(){
      if(this.params.txtSerial.trim() == ""){
        this.$toast.error("Bạn chưa nhập serial !");
        return
      }
      this.loading(true)
      if(this.params.cboOption == 3){
        var kt_ton_dhsx = await this.kiemTraTonSerial(this.params.txtSerial)

        if(kt_ton_dhsx > 0){ 
          if(this.tag_form == "1"){ 
            this.$toast.error("Serial đã tồn tại trong ĐHSX !");
          }else{
            if(this.DSTTTS.length != 0){
              for(const e of this.DSTTTS){
                console.log(e.serial.toUpperCase() , " = e.serial.toUpperCase()")
                if(e.serial.toUpperCase() == this.params.txtSerial.trim().toUpperCase()){
                  this.$toast.error("Serial đã có trong danh sách serial bên dưới !");
                  this.loading(false)
                  return
                }
              }
            }else{
              var serial_tren_dhsx =  await this.getDSSerialDHSX([this.params.txtSerial.trim()]) // gộp Serial tránh call API nhiều lần
         
              for(const i of serial_tren_dhsx){
                var row = this.tracuu_serial_tren_qlts(i , "DHSX", true)
                this.DSTTTS.push(row) // append từng dòng rồi gán tránh grid load lại nhiều lần
              }   
            }
          }
          this.loading(false)
          return
        }

        if(this.sl_cothe_gan == 0 && this.tag_form == "1"){
          this.$toast.error("Serial đã có trong danh sách serial bên dưới !");
          this.loading(false)
          return
        }

        if(this.tag_form == "1"){
          if(this.DSSerial.length >= this.sl_cothe_gan){
            this.$toast.error("Chứng từ thiết bị chỉ có thể có " + this.sl_cothe_gan + " serial. Không thể tìm kiếm thêm !");
            this.loading(false)
            return
          }

          for(const e of this.DSSerial){
            if(e.serial.toUpperCase() == this.params.txtSerial.trim().toUpperCase()){
              this.$toast.error("Serial bạn tìm kiếm đã có trong danh sách serial bên dưới !");
              this.loading(false)
              return
            }
          }
        }else{
          for(const e of this.DSTTTS){
            if(e.serial.toUpperCase() == this.params.txtSerial.trim().toUpperCase()){
              this.$toast.error("Serial đã có trong danh sách serial bên dưới !");
              this.loading(false)
              return
            }
          }
        }
      }
      
      //tạm thời pass qua do google Chrome chặn
      // var response = "{\"success\": true, \"message\": \"200: Thành công\", \"data\": [{\"json_mmtb\": null, \"json_capd\": null, \"json_capq\": null, \"json_vttu\": null}]}"
      var cboOption = this.params.cboOption 
      var objOption = this.DSOption.filter(function (el) {
        return  el.kieu_id == cboOption               
      }) 
      var type = objOption.length > 0 ? objOption[0].kieu : "Serial" 
      var response = await this.getTTQLTS({
        accessToken: "",
        type: type,
        serial: this.params.txtSerial
      })
      console.log(response , " = response")
      if(response.includes("ERROR") ){
        this.$toast.error(response);
        this.loading(false)
        return
      }

      var kq  = JSON.parse(response)
      console.log(kq , " = kq")
      var detail = kq.data[0]
      if(  detail.json_capd == null 
          && detail.json_capq == null 
          && detail.json_mmtb == null 
          && detail.json_vttu == null)
      {
        if(this.tag_form != "1"){
          this.$toast.error("Serial không có trên QLTS !");
          this.loading(false)
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Serial không có trên QLTS ! Bạn có muốn tiếp tục cập nhật serial này ???", {
            "okVariant": 'success',
            "okTitle": 'Đồng ý',
            "cancelTitle": 'Hủy',
            "centered": true,
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          this.loading(false)
          return;
        }

        this.add_seri_vao_grid("",this.params.txtSerial.trim().toUpperCase(),"1","","","")
      }else{
        var news_mmtb = detail.json_mmtb;
        var news_capquang = detail.json_capq;
        var news_capdong = detail.json_capd;
        var news_vattu = detail.json_vttu;

        if(news_mmtb != null){
          if(this.tag_form == "1"){
            var kiemtra = await this.kiemtra_ma_vt_fmis(news_mmtb[0].ma_vt_hh_dv)
            if (!kiemtra){
              this.loading(false)
              return;
            }   
            
            if (news_mmtb[0].ma_so != ""){
              var kt_csht = await this.kiemTraChungTuKhoNhan(this.id_chungtu)
              if(kt_csht == 0){
                this.$toast.error("Serial " + this.params.txtSerial + " có id_chitiet: " 
                    + news_mmtb[0].ma_so + " phải nhập vào chứng từ có kho nhận có ma_csht (id:" + this.id_chungtu + ")");
                this.loading(false)
                return 
              }
            }
            this.add_seri_vao_grid(news_mmtb[0].ma_vt_hh_dv, news_mmtb[0].seri, "1", news_mmtb[0].qr_code,
                                        news_mmtb[0].ma_so, news_mmtb[0].ma_du_an)
            var ctctId = 0
            for(const i in this.DSTbiChuaGan){
              if(this.DSTbiChuaGan[i].maVt.toUpperCase() == news_mmtb[0].ma_vt_hh_dv.toUpperCase()){
                this.DSTbiChuaGan[i].soLuongChuaGan = this.DSTbiChuaGan[i].soLuongChuaGan - 1
                ctctId = this.DSTbiChuaGan[i].ctctId
              }
            }
            if(this.DSSerial.length > 0){
              this.DSSerial[this.DSSerial.length -1].CTCT_ID = ctctId
            }

          }else{
      
            for(const i of news_mmtb){
              var row = this.tracuu_serial_tren_qlts(i , "QLTS", false)
              this.DSTTTS.push(row)
            } 
          }
        }else if(news_capquang != null){
          this.$toast.error("Chưa hỗ trợ cáp quang ! Liên hệ admin!");
          this.loading(false)
          return 

        }else if(news_capdong != null){
          this.$toast.error("Chưa hỗ trợ cáp đồng ! Liên hệ admin!");
          this.loading(false)
          return 

        }else if(news_vattu != null){
          if(this.tag_form == "1"){
            var kiemtra = await this.kiemtra_ma_vt_fmis(news_vattu[0].ma_vt_hh_dv)
            if (!kiemtra){
              this.loading(false)
              return;
            } 

            if(news_vattu[0].ma_so != null){
              var kt_csht = await this.kiemTraChungTuKhoNhan(this.id_chungtu)
              if(kt_csht == 0){
                this.$toast.error("Serial " + this.params.txtSerial + " có id_chitiet: " 
                    + news_mmtb[0].ma_so + " phải nhập vào chứng từ có kho nhận có ma_csht (id:" + this.id_chungtu + ")");
                this.loading(false)
                return 
              }
            }

            this.add_seri_vao_grid(news_vattu[0].ma_vt_hh_dv, news_vattu[0].seri, "1", news_vattu[0].qr_code,
                                        news_vattu[0].ma_so, news_vattu[0].ma_du_an)
            var ctctId = 0
            for(const i in this.DSTbiChuaGan){
              if(this.DSTbiChuaGan[i].maVt.toUpperCase() == news_vattu[0].ma_vt_hh_dv.toUpperCase()){
                this.DSTbiChuaGan[i].soLuongChuaGan = this.DSTbiChuaGan[i].soLuongChuaGan - 1
                ctctId = this.DSTbiChuaGan[i].ctctId
              }
            }
            if(this.DSSerial.length > 0){
              this.DSSerial[this.DSSerial.length -1].CTCT_ID = ctctId
            }

          }else{
            for(const i of news_vattu){
              var row = this.tracuu_serial_tren_qlts(i , "QLTS", false)
              this.DSTTTS.push(row)
            } 
          }
          
        }
      }
    },

    async kiemtra_ma_vt_fmis(ma_vt_hh__){
      if(ma_vt_hh__ !=  null){
        var kt_mavt_dhsxkd = await this.kiemTraTonTaiMaVT(ma_vt_hh__.toUpperCase())
        if(kt_mavt_dhsxkd == 0){
          this.$toast.error("Mã vật tư " + ma_vt_hh__  + " không tồn tại trên hệ thống ĐHSXKD ! Không thể cập nhật serial !");
          return false
        }

        var vt_ko_co_trong_chungtu = this.DSTbiChuaGan.filter(function (el) {
          return  el.maVt.toUpperCase() ==  ma_vt_hh__         
        })
        if(vt_ko_co_trong_chungtu.length == 0){
          this.$toast.error("Mã vật tư " + ma_vt_hh__   + " không tồn tại trong chứng từ ! Hãy kiểm tra lại !(ID:" + this.id_chungtu + ")");
          return false
        }

        if(vt_ko_co_trong_chungtu[0].soLuongChuaGan == 0){
          this.$toast.error("Vật tư " + ma_vt_hh__ + " đã được gán hết số lượng ! Hãy chọn serial có vật tư khác !");
          return false
        }
      }
      return true
    },
    async add_seri_vao_grid(mavt,seri,sl,qrcode,idchitiet,ma_da){
      var ds_seri_luu = {
        ma_vt_hh_dv: mavt,
        TEN_VT: "",
        seri: seri,
        so_luong: sl,
        DONGIA: "",
        TIEN: "",
        qr_code : qrcode,
        CTCT_ID: "",
        IDCHITIET: idchitiet,
        ma_du_an: ma_da
      }
      this.DSSerial.push(ds_seri_luu)
    },
    gridSerial_selectedRow(data){    
      if(data){
        this.rowSerialSelected = data.data
        this.gridSerial_indexRow = data.rowIndex
      }
    },
    gridDS_TbiChuaGan_selectedRow(data){
      console.log(data, " = data")
    },
    rowSelected(data){
      console.log(data , " = rowSelected")
    },

    huyGan_Handler(data){
      var maVT = data.ma_vt_hh_dv
      if(maVT == "" || maVT == null){
        this.$toast.error("Serial " + data.seri + " chưa được gán vật tư ! ");
        return
      }
      for(const i in this.DSSerial){
        if(this.DSSerial[i].ma_vt_hh_dv == maVT){
          this.DSSerial[i].ma_vt_hh_dv  = ""
          this.DSSerial[i].TEN_VT  = ""
          this.DSSerial[i].CTCT_ID  = ""
          break
        }
      }
      for(const i in this.DSTbiChuaGan){
        if(this.DSTbiChuaGan[i].maVt == maVT){
          this.DSTbiChuaGan[i].soLuongChuaGan  = this.DSTbiChuaGan[i].soLuongChuaGan + 1
          break
        }
      }
    },

    themMaVT_Handler(data){
      console.log(data , " - data")
      if(data.soLuongChuaGan <= 0){
        this.$toast.error("Vật tư " + data.maVt + " đã được gán hết số lượng !");
        return
      }

      if(this.rowSerialSelected.length == {}){
        this.$toast.error("Chưa chọn dòng serial nào !");
        return
      }

      if(this.rowSerialSelected.ma_vt_hh_dv != "" && this.rowSerialSelected.ma_vt_hh_dv != null){
        this.$toast.error("Serial " + this.rowSerialSelected.seri + " đã được gán mã vật tư " + this.rowSerialSelected.seri.ma_vt_hh_dv);
        return
      }
      if(this.gridSerial_indexRow >= this.DSSerial.length){
        this.$toast.error("dòng chọn không tồn tại");
        return
      }
      this.DSSerial[this.gridSerial_indexRow].ma_vt_hh_dv = data.maVt
      this.DSSerial[this.gridSerial_indexRow].TEN_VT = data.tenVt
      this.DSSerial[this.gridSerial_indexRow].CTCT_ID = data.ctctId
      for(const i of this.DSTbiChuaGan){
        if(this.DSTbiChuaGan[i].ctctId == data.ctctId){
          this.DSTbiChuaGan[i].soLuongChuaGan = this.DSTbiChuaGan[i].soLuongChuaGan - 1
          break
        }
      }
    },
    async xoaSeri_Handler(data){
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn có chắc chán muốn xóa serial " + data.seri + " !", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        this.loading(false)
        return;
      }

      if(data.ma_vt_hh_dv != "" && data.ma_vt_hh_dv != null  ){
        for(const i in this.DSTbiChuaGan){
          if(this.DSTbiChuaGan[i].maVt == data.ma_vt_hh_dv){
            this.DSTbiChuaGan[i].soLuongChuaGan  = this.DSTbiChuaGan[i].soLuongChuaGan + 1
            break
          }
        }
      }
      var newDS = []
      for(const i in this.DSSerial){
        if(i != this.gridSerial_indexRow){
          newDS.push(this.gridSerial_indexRow[i])
        }
      }
      this.DSSerial = newDS
    },
    async tsbtnInTem_Click(){
      try{
        if(this.DSTTTS_Checked.length == 0){
          this.$toast.error("Hãy tick chọn số thẻ cần in!")
          return
        }
        var DSSeriIn = []
        for(const e of this.DSTTTS_Checked){
          if(e.qrCode == null || e.qrCode.trim() == ""){
            this.$toast.error(e.serial + " không có qr_code ! Không thể in tem !")
            return
          }
          DSSeriIn.push({
            serial: e.serial
          })
        }
      
        this.loading(true)
        const baseUrl = process.env.API;
          await this.axios({
            url:
              `${baseUrl}/web-qlvt/api/bao-cao/in-qr-code-qlts?extend=pdf`,
            method: "POST",
            responseType: "blob",
            data: DSSeriIn
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
    tsbtnExcel_Click(){
      this.$refs.fileInput1.click();
    },
    onChangeFile(e){
      try{
        const files = e.target.files;

        if (!files.length) {
          return;
        } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
          return this.$root.toastError("File excel không đúng định dạng!");
        }

        const fileReader = new FileReader();
        
        fileReader.onload = async (ev) => {
        
            const data = ev.target.result;
            const XLSX = xlsx;
            const workbook = XLSX.read(data, {
              type: "binary",
            });
            const wsname = workbook.SheetNames[0];
            const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
            console.log(ws , " = ws")

            if(ws.length == 0){
              this.$toast.error("Không có dữ liệu đầu vào");
              return
            }
            if( !('SERIAL' in ws[0])){
              this.$toast.error("Tên cột đầu tiên trong file không đúng định dạng : SERIAL");
              return
            }
            var DSSerial = []
            for(const e of ws){
              DSSerial.push(e.SERIAL)
            }
            var DSRemove = this.removeDuplicates(DSSerial)
            if(DSRemove.length < DSSerial.length){
              this.$toast.error("Có serial trùng trong file");
              return
            }

            await this.KiemTraDuLieu(DSSerial)

        };
        fileReader.readAsBinaryString(files[0]);
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    removeDuplicates(arr) {
        return arr.filter((item,
            index) => arr.indexOf(item) === index);
    },
    async KiemTraDuLieu(dt){
      var dt_error = []
      if(this.tag_form == "1"){

        if(this.sl_cothe_gan < dt.length){
          this.$toast.error("Số dòng trong file / Số lượng serial còn lại có thể gán vào chứng từ: " 
                            + dt.length + "/" + this.sl_cothe_gan);
          return
        }

        for(const e of dt){
          var kt_ton_dhsx = await this.kiemTraTonSerial(e)
          console.log(kt_ton_dhsx , " = kt_ton_dhsx")
          if(kt_ton_dhsx > 0){
            this.$toast.error("Serial đã tồn tại trong hệ thống ĐHSX: " + e);
            return
          }
        }

        if(this.DSSerial.length > 0){
          this.$toast.error("Không thể dùng chức năng file excel. Hãy xóa hết serial ở danh sách trước !");
          return
        }

        // var response = "{\"success\": true, \"message\": \"200: Thành công\", \"data\": [{\"json_mmtb\": null, \"json_capd\": null, \"json_capq\": null, \"json_vttu\": null}]}"
        
        var response = await this.getTTQLTS({
          accessToken: "",
          type: "serial",
          serial: dt.toString()
        })
        console.log(response , " = response")
        if(response.includes("ERROR") ){
          this.$toast.error(response);
          return
        }

        var kq  = JSON.parse(response)
        var detail = kq.data[0]
        if(  detail.json_capd == null 
            && detail.json_capq == null 
            && detail.json_mmtb == null 
            && detail.json_vttu == null)
        {
          this.$toast.error("Serial trong file không có trên QLTS !");
          return
        }
        var news_mmtb = detail.json_mmtb;
        var news_capquang = detail.json_capq;
        var news_capdong = detail.json_capd;
        var news_vattu = detail.json_vttu;

    
      
        var list_seri_mmtb = []
        for(const e of news_mmtb){
          list_seri_mmtb.push(e.seri)
        }

        var list_seri_vattu  = []
        for(const e of news_vattu){
          list_seri_vattu.push(e.seri)
        }

        for(const e of dt){
          if(!list_seri_mmtb.includes(e) && !list_seri_vattu.includes(e)){
            dt_error.push({
              SERIAL: e,
              NOIDUNG: "Serial không tồn tại trên QLTS !"
            })
          
          }
        }
       
      
        for(const e of news_mmtb){
          var i_seri = e.seri
          var ma_vt_hh__ = e.ma_vt_hh_dv
          if(ma_vt_hh__ != null ){

            var kt_mavt_dhsxkd = await this.kiemTraTonTaiMaVT(ma_vt_hh__)
            if(kt_mavt_dhsxkd == 0){
              this.$toast.error(ko_ton_vattu.toString() +  " Serial không tồn tại trên QLTS !");
              dt_error.push({
                SERIAL: i_seri,
                NOIDUNG: i_seri + " có mã vật tư trên QLTS: " + ma_vt_hh__   + ", , không tồn tại trên hệ thống ĐHSXKD!"
              })
              continue
            }

            var vt_ko_co_trong_chungtu = this.DSTbiChuaGan.filter(a=>a.maVt.toString() == ma_vt_hh__)
            if(vt_ko_co_trong_chungtu.length == 0){
              dt_error.push({
                SERIAL: i_seri,
                NOIDUNG: i_seri + " có mã vật tư trên QLTS: " + ma_vt_hh__  + ", không tồn tại trong chứng từ ! Hãy kiểm tra lại !(ID:" + this.id_chungtu + ")"
              })
              continue
            }

            if(vt_ko_co_trong_chungtu[0].soLuongChuaGan == 0){
              dt_error.push({
                SERIAL: i_seri,
                NOIDUNG: i_seri + " có mã vật tư trên QLTS: " + ma_vt_hh__  + ", đã được gán hết số lượng !"
              })
              continue
            }
          }

          if(e.ma_so !=  null){
            var kt_csht = await this.kiemTraChungTuKhoNhan(this.id_chungtu)
            if(kt_csht == 0){
              dt_error.push({
                SERIAL: i_seri,
                NOIDUNG: i_seri +  " có id_chitiet: " + e.ma_so + " phải nhập vào chứng từ có kho nhận có ma_csht (id:"  + this.id_chungtu + ")"
              })
              continue
            }
          }
          this.add_seri_vao_grid(ma_vt_hh__ , i_seri, "1", e.qr_code,  e.ma_so, e.ma_du_an)
          var ctctId = ""
          for(const i in this.DSTbiChuaGan){
            if(this.DSTbiChuaGan[i].maVt.toUpperCase() == ma_vt_hh__.toUpperCase()){
              this.DSTbiChuaGan[i].soLuongChuaGan = this.DSTbiChuaGan[i].soLuongChuaGan - 1
              ctctId = this.DSTbiChuaGan[i].ctctId
            }
          }
          if(this.DSSerial.length > 0){
            this.DSSerial[this.DSSerial.length -1].CTCT_ID = ctctId
          }
        }

        for(const e of news_vattu){
          var i_seri = e.seri
          var ma_vt_hh__ = e.ma_vt_hh_dv
          if(ma_vt_hh__ != null){

            var kt_mavt_dhsxkd = await this.kiemTraTonTaiMaVT(ma_vt_hh__)
            if(kt_mavt_dhsxkd == 0){
              this.$toast.error(ko_ton_vattu.toString() +  " Serial không tồn tại trên QLTS !");
              dt_error.push({
                SERIAL: i_seri,
                NOIDUNG: i_seri + " có mã vật tư trên QLTS: " + ma_vt_hh__   + ", , không tồn tại trên hệ thống ĐHSXKD!"
              })
              continue
            }

            var vt_ko_co_trong_chungtu = this.DSTbiChuaGan.filter(a=>a.maVt.toString() == ma_vt_hh__)
            if(vt_ko_co_trong_chungtu.length == 0){
              dt_error.push({
                SERIAL: i_seri,
                NOIDUNG: i_seri + " có mã vật tư trên QLTS: " + ma_vt_hh__  + ", không tồn tại trong chứng từ ! Hãy kiểm tra lại !(ID:" + this.id_chungtu + ")"
              })
              continue
            }

            if(vt_ko_co_trong_chungtu[0].soLuongChuaGan == 0){
              dt_error.push({
                SERIAL: i_seri,
                NOIDUNG: i_seri + " có mã vật tư trên QLTS: " + ma_vt_hh__  + ", đã được gán hết số lượng !"
              })
              continue
            }
          }

          if(e.ma_so !=  null){
            var kt_csht = await this.kiemTraChungTuKhoNhan(this.id_chungtu)
            if(kt_csht == 0){
              dt_error.push({
                SERIAL: i_seri,
                NOIDUNG: i_seri +  " có id_chitiet: " + e.ma_so + " phải nhập vào chứng từ có kho nhận có ma_csht (id:"  + this.id_chungtu + ")"
              })
              continue
            }
          }

          this.add_seri_vao_grid(ma_vt_hh__,i_seri, "1",e.qr_code, e.ma_so, e.ma_du_an)
          var ctctId = ""
          for(const i in this.DSTbiChuaGan){
            if(this.DSTbiChuaGan[i].maVt.toUpperCase() == ma_vt_hh__.toUpperCase()){
              this.DSTbiChuaGan[i].soLuongChuaGan = this.DSTbiChuaGan[i].soLuongChuaGan - 1
              ctctId = this.DSTbiChuaGan[i].ctctId
            }
          }
          if(this.DSSerial.length > 0){
            this.DSSerial[this.DSSerial.length -1].CTCT_ID = ctctId
          }
        }

        if(dt_error.length > 0){
          this.$toast.error("Có " + dt_error.length + " bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!");
          var NameFile = "fileSaiDuLieu.xls"
          var DataExcel = xlsx.utils.json_to_sheet(dt_error);  
          var wb = xlsx.utils.book_new();
          xlsx.utils.book_append_sheet(wb, DataExcel, "Sheet1");
          xlsx.writeFile(wb, NameFile);
          dt_error = []
        }
      }else{
        this.DSTTTS = []
        var chuoi_tb = []
        for(const i in dt){
          if(dt[i] == ""){
            continue
          }
          var loi_out = await this.insert_serial_file(dt[i])
          if(loi_out.includes("ERROR")){
            this.$toast.error(loi_out)
            return
          }
          if(loi_out.includes("NO")){
            chuoi_tb.push(dt[i])
          }
        }
        if(chuoi_tb.length > 0){
          this.$toast.error("Có serial có lỗi hoặc không tìm thấy: " + chuoi_tb.toString())
        }
      }
    },
    async insert_serial_file(serial){
      var loi_out = ""
      var kt_ton_dhsx = await this.kiemTraTonSerial(serial)
      if(kt_ton_dhsx > 0){
        var serial_tren_dhsx = await this.getDSSerialDHSX([serial])
        if(serial_tren_dhsx.length > 0){
          var row = this.tracuu_serial_tren_qlts(serial_tren_dhsx[i] , "DHSX", true)
          this.DSTTTS.push(row) 
        }
        
      }else{
        //tạm thời pass qua do google Chrome chặn
        // var response = "{\"success\": true, \"message\": \"200: Thành công\", \"data\": [{\"json_mmtb\": null, \"json_capd\": null, \"json_capq\": null, \"json_vttu\": null}]}"
        var cboOption = this.params.cboOption 
        var objOption = this.DSOption.filter(function (el) {
          return  el.kieu_id == cboOption               
        }) 
        var type = objOption.length > 0 ? objOption[0].kieu : "Serial" 
        var response = await this.getTTQLTS({
          accessToken: "",
          type: type,
          serial: serial
        })
        console.log(response , " = response")
        if(response.includes("ERROR") ){
          loi_out = response
        }

        var kq  = JSON.parse(response)
       
        var detail = kq.data[0]
        if(  detail.json_capd == null 
            && detail.json_capq == null 
            && detail.json_mmtb == null 
            && detail.json_vttu == null)
        {
          loi_out = "NO"
        }else{
          var news_mmtb = detail.json_mmtb;
          var news_vattu = detail.json_vttu;
          if(news_mmtb.length > 0){
            var row = this.tracuu_serial_tren_qlts(news_mmtb[0] , "QLTS", false)
            this.DSTTTS.push(row) 
          }
          if(news_vattu.length > 0){
            var row = this.tracuu_serial_tren_qlts(news_vattu[0] , "QLTS", false)
            this.DSTTTS.push(row) 
          }
        }

      }
      return loi_out
    }
  },
  
  destroyed () {      
    EventBus.$off('themMaVT', this.themMaVT_Handler);   
    EventBus.$off('huyGan', this.huyGan_Handler);
    EventBus.$off('xoaSeri', this.xoaSeri_Handler);
     
  },
  created: async function() {       
   
  }
 
};
</script>