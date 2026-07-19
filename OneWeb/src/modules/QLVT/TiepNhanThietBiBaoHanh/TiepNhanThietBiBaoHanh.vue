<template src='./TiepNhanThietBiBaoHanh.html'></template>
<style scoped src='./TiepNhanThietBiBaoHanh.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import breadcrumb from '@/components/breadcrumb'
import TiepNhanThietBiBaoHanhAPI from  '../api/TiepNhanThietBiBaoHanhAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/tiepnhanthietbibaohanh'
import select2 from '@/components/Select2.vue'

import { MultiSelectPlugin, CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns";
export default {
  provide: {
        multiselect: [CheckBoxSelection]
    },
  components: {breadcrumb, appSelect2: select2 },
  name: "TiepNhanThietBiBaoHanh",
  mounted() {
  },
  computed: {
    ...mapState("tiepnhanthietbibaohanh", statePropertyName),
    ...mapGetters("tiepnhanthietbibaohanh", getterName),
   
    p_kieubh_id: {
      get() {
        return this.params.p_kieubh_id
      },
      set(value) {
        return this.params.p_kieubh_id = value
      }
    },

    p_tinhtrang_id: {
      get() {
        return this.params.p_tinhtrang_id
      },
      set(value) {
        return this.params.p_tinhtrang_id = value
      }
    },

    p_nhomtb_id: {
      get() {
        return this.params.p_nhomtb_id
      },
      set(value) {
        return this.params.p_nhomtb_id = value
      }
    },

    p_loaitb_id: {
      get() {
        return this.params.p_loaitb_id
      },
      set(value) {
        return this.params.p_loaitb_id = value
      }
    },

    // p_vattu_id: {
    //   get() {
    //     return this.params.p_vattu_id
    //   },
    //   set(value) {
    //     return this.params.p_vattu_id = value
    //   }
    // },

    p_serial: {
      get() {
        return this.params.p_serial
      },
      set(value) {
        return this.params.p_serial = value
      }
    },

    p_ngay_yc: {
      get() {
        return this.params.p_ngay_yc
      },
      set(value) {
        return this.params.p_ngay_yc = value
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

    p_soluong: {
      get() {
        return this.params.p_soluong
      },
      set(value) {
        return this.params.p_soluong = value
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

    p_mac: {
      get() {
        return this.params.p_mac
      },
      set(value) {
        return this.params.p_mac = value
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

    v_VatTuDisable: {
      get() {
        return this.stateButton.v_VatTuDisable
      },
      set(value) {
        return this.stateButton.v_VatTuDisable = value
      }
    },

    v_NhapMoiDisable: {
      get() {
        return this.stateButton.v_NhapMoiDisable
      },
      set(value) {
        return this.stateButton.v_NhapMoiDisable = value
      }
    },

    v_GhiLaiDisable: {
      get() {
        return this.stateButton.v_GhiLaiDisable
      },
      set(value) {
        return this.stateButton.v_GhiLaiDisable = value
      }
    },

    v_XoaDisable: {
      get() {
        return this.stateButton.v_XoaDisable
      },
      set(value) {
        return this.stateButton.v_XoaDisable = value
      }
    },

    v_HuyBoDisable: {
      get() {
        return this.stateButton.v_HuyBoDisable
      },
      set(value) {
        return this.stateButton.v_HuyBoDisable = value
      }
    },
  },
  data() {
    return {
      header: {
        title: "TIẾP NHẬN THIẾT BỊ BẢO HÀNH",
        list: [
          { name: "Quản lý vật tư", link: { name: "Ui.cards" } },
          {
            name: "Bảo hành thiết bị",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tiếp nhận thiết bị bảo hành",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      p_donvi_id: "",
      p_vattu_id: 0,
      params: {
          p_donvi_id: "",
          p_kieubh_id: "",
          p_tinhtrang_id: [],
          p_nhomtb_id: 0,
          p_loaitb_id: 0,
          // p_vattu_id: 0,
          p_kieu_id: 0,
          p_serial: "",
          p_ngay_yc:"",
          p_dienthoai_lh:"",
          p_soluong:1,
          p_phukien: "",
          p_mac: "",
          p_ghichu:"",
          p_baohanh_id: 0,
          p_ttbh_id: 0,
          p_baohong_id: 0,
          p_hdtb_id: 0,
          p_thietbi_id: 0,
          p_phieuvt_id: 0,
          p_quytrinh_id: 0
      },

      stateButton: {
        v_VatTuDisable: false,
        v_NhapMoiDisable: false,
        v_GhiLaiDisable: false,
        v_XoaDisable: false,
        v_HuyBoDisable: false,
        v_SoLuongDisable: false,
        // v_LayDSDisable: true,
      },
      cay_dvi_khac: false,
      phanvungSTG: false,
      new_DSBH_Data:[],
      new_DSBH_DV_Data: [],
      config: {
        dateConfig: {
          default: new Date(),
          // max:  moment(new Date()).format('DD/MM/YYYY 23:59:59') ,
          format: 'dd/MM/yyyy'
        }
      },
      LOAI_DV: {
        TRAM_VT: 5,
        KHO_NHAN_TBI_BAOHANH: 80,
        TO_BHSC: 81,
        TTKD_BAOHANH_VT: 998,
        HOANTHIEN_BAOHANH: 83,
        KHO_NHAN_TBI_BAOHANH_XONG: 82
      },
      NHOM_QT:{
        BAOHANH_TBI: 4
      },
      
      
    };
  },
  methods: {
    ...mapActions("tiepnhanthietbibaohanh", actionName),
    ...mapMutations("tiepnhanthietbibaohanh", mutationName),
    ...mapMutations("giaophieubaohanh",["setTag","setQuyTrinh","setHuongGiao"]),
    async TiepNhanBHPageHandler(e) {
      this.setTiepNhanBHPage({page: e.pageIndex, maxSize: e.pageSize})
      // this.TiepNhanBHPage.page = e.pageIndex
      // this.TiepNhanBHPage.maxSize = e.pageSize
      await this.LoadDanhSachClick()
    },
    onKieuBHChange(){
      if (this.p_kieubh_id.toString() == '3'){
        this.stateButton.v_SoLuongDisable = false;
      }
      else{
        this.stateButton.v_SoLuongDisable = true;
        this.p_soluong = 1
      }
    },

    async onChungLoaiChange(){
      this.$refs.selectLoaiTB.clear()
      if (this.p_nhomtb_id != 0){
        await this.getDSLoaiThietBi(this.p_nhomtb_id)
      }
      else{
        this.clearLoaiTB();
      }
      this.p_loaitb_id = 0;
    },
    // HienThi_ThongTin_Tbi
    async onSelectedRow(item){
      if (item){

        this.params.p_thietbi_id = item.THIETBI_ID ? parseInt(item.THIETBI_ID) : 0;
        this.params.p_phieuvt_id = item.PHIEUVT_ID ?  parseInt(item.PHIEUVT_ID) : 0;
        this.params.p_baohanh_id = item.BAOHANH_ID ?  parseInt(item.BAOHANH_ID) : 0;

        if (item.VATTU_ID){
          console.log(item.VATTU_ID , " = item.VATTU_ID")
          this.p_vattu_id = parseInt(item.VATTU_ID) || 0
          this.stateButton.v_VatTuDisable = true
        }
        else {
          this.stateButton.v_VatTuDisable = false
          // let mapParams = {
          //       "inIdNeo" : "vattu_id",
          //       "inTable" : "css.baohanh",
          //       "inDk" : `where baohanh_id = ${this.params.p_baohanh_id}`
          //   }
          let mapParams= {baoHanhId: this.params.p_baohanh_id}
          await this.getMapID(mapParams)
          console.log(this.vvt_id , " = vvt_id")
          if (this.vvt_id.toString() != "-1"){
              this.p_vattu_id = parseInt(this.vvt_id)
          }
          else {
              this.p_vattu_id = ''
          }
        }

        this.params.p_quytrinh_id = item.QUYTRINH_ID ? parseInt(item.QUYTRINH_ID) : 0;
        this.p_ttbh_id = parseInt(item.TTBH_ID) 
        this.params.p_ngay_yc = this.p_ttbh_id == 0 ? this.f_nowDatetime() : this.f_String_ToDate(item.NGAY_YC,"DD-MM-YYYY HH:mm:ss")

        this.p_kieubh_id =  item.KIEUBH_ID ? parseInt(item.KIEUBH_ID) : '';
        this.p_serial =  item.SERIAL || '';
        this.p_mac =  item.MAC || '';
        this.p_dienthoai_lh =  item.DIENTHOAI_LH || '';
        this.p_ghichu =  item.GHICHU || '';

        let tinhTrangStr = this.danhSachTinhTrang.map(a=>a.split(" - ")[1])
        let tinhTrangArr = tinhTrangStr.filter(b=>b.includes(item.TINHTRANG)) || [];
        // this.p_tinhtrang_id =  tinhTrangArr;
        await this.setTinhTrang(item.BAOHANH_ID)
        this.p_phukien = item.PHUKIEN || '';
        this.p_nhomtb_id =  item.NHOM_TBI_ID ? parseInt(item.NHOM_TBI_ID) : 0
        await this.onChungLoaiChange()
        this.p_loaitb_id =  item.LOAITBI_ID ? parseInt(item.LOAITBI_ID) : 0
        this.params.p_hdtb_id = item.HDTB_ID ? parseInt(item.HDTB_ID) : 0
        this.params.p_baohong_id = item.BAOHONG_ID ? parseInt(item.BAOHONG_ID) : 0       
        this.p_donvi_id =  item.DONVI_ID ? item.DONVI_ID : ''
        this.p_soluong =  item.SOLUONG ? parseInt(item.SOLUONG) : 1
        this.p_kieu_id =  0

        await this.getThongTinBaoHanhTB({
          hdtbId: this.params.p_hdtb_id , // this.selectedRow.HDTB_ID
          baoHongId: this.params.p_baohong_id // this.selectedRow.BAOHONG_ID
        })

        this.setButton(3)
      }
    },
    f_String_ToDate(value,format){
      return moment(value,format).toDate();
    },
    f_nowDatetime(){
      return moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
    },
    // chuyển Date thành type string
     f_DateToString: function (value,format){
      return moment(value).format(format)
    },
    async setTinhTrang(baohanh_id){
      await this.getTinhTrangBHTheoID(baohanh_id)
      let tinhTrangIds = this.ttBH.map(a=>a.tinhTrangBHId.toString())
      this.p_tinhtrang_id = this.danhSachTinhTrang.filter(b=>tinhTrangIds.includes(b.split(' - ')[0].trim()))
    },
    // ClearTextBox
    clearText(){
      this.p_serial = '';
      this.p_dienthoai_lh = '';
      this.p_phukien = '';
      this.p_mac = '';
      this.p_ghichu = '';
    },
    // Clear
    resetParams() {
      this.clearText();
      this.clearThongTinThueBao();
      this.p_nhomtb_id = 0;
      this.params.p_baohanh_id = 0;
      this.p_kieubh_id = 1;
      this.p_ngay_yc = new Date();
      this.params.ttbd_id = -1
      this.params.p_baohong_id = 0
      this.params.p_hdtb_id = 0
      this.params.p_thietbi_id = 0
      this.params.p_phieuvt_id = 0
      this.p_tinhtrang_id = [];
      this.p_ttbh_id = -1
    },
    // SetButton
    setButton(kieu){
      this.v_NhapMoiDisable = true;
      this.v_GhiLaiDisable = true;
      this.v_XoaDisable = true;
      this.v_HuyBoDisable = true;
      if (kieu == -1){
        this.v_GhiLaiDisable = false;
        this.v_HuyBoDisable = false
      }
      // Bat dau
      if(kieu == 0){
        this.$refs.inputSerial.focus()
        this.v_NhapMoiDisable = false;
        this.resetParams()
      }
      // Them moi
      if (kieu == 1){
        this.$refs.inputSerial.focus()
        this.v_GhiLaiDisable = false;
        this.v_HuyBoDisable = false;
        this.resetParams()
      }
      // Huy
      if(kieu == 2){
        this.v_NhapMoiDisable = false;
        this.v_XoaDisable = false;
        this.resetParams()
      }
      // Edit
      if(kieu == 3){
        this.v_NhapMoiDisable = false;
        this.v_XoaDisable = false;
        this.v_GhiLaiDisable = false;
        this.v_HuyBoDisable = false;
      }
    },
    // LoadDanhSach
    async LoadDanhSachClick(){
      this.$root.showLoading(true)
      let dsTiepNhanParams = {
        nhanVienId: this.$auth.getNhanVienID(),
        serial: '0'
      }
      await this.getDSTiepNhanBH({
        params: dsTiepNhanParams,
        pageNo: this.TiepNhanBHPage.page,
        pageSize: this.TiepNhanBHPage.maxSize,
      })
      this.$root.showLoading(false)
      // this.$refs.tableTiepNhanBH ? this.$refs.tableTiepNhanBH.setCurrentSelectedRow(0) : ''
    },
    // tsbtnNhapMoi_Click
    NhapMoiClick(){
      this.setButton(1)
    },
    // tsbtnGhiLai_Click
    async GhiLaiClick(){
      try {
        
        if (this.phanvungSTG == true){
          this.p_nhomtb_id = 1;
          this.p_loaitb_id = 1;
        }
        //await this.GhiLai()
        await this.GhiLaiTiepNhanBaoHanh()
      }catch(err){
        console.log(err.data.message)
      }finally{
        this.loading(false)
      }
    },
    async GhiLaiTiepNhanBaoHanh(){
      if ( this.p_serial.length > 500 || this.p_ghichu.length > 500 ||
        this.p_phukien.length > 500 || this.p_mac.length > 500 || 
        this.p_dienthoai_lh.length > 500 ){
        this.$toast.error("Trường dữ liệu quá dài, vượt quá 500 ký tự!")
        return
      }

      let mess_pop = this.stateButton.v_NhapMoiDisable ? "Bạn có muốn thêm mới dữ liệu không?" : "Bạn có muốn cập nhật dữ liệu không?"
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm(
          mess_pop,
          {
            title: "Xác nhận hành động",
            centered: true,
            size: "md",
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
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
        return
      } 
      try {
        this.loading(true)       
        await this.tiepNhanBaoHanh({
          baoHanhId: this.stateButton.v_NhapMoiDisable ? "" : this.params.p_baohanh_id, // Nhập mới thì k truyền BAOHANH_ID
          serial: this.p_serial.trim(),
          vatTuId: parseInt(this.p_vattu_id),
          loaiTBId: parseInt(this.params.p_loaitb_id),
          thietBiId: this.params.p_thietbi_id ?  this.params.p_thietbi_id : 0,
          dsTinhTrang: this.p_tinhtrang_id.map(a=>parseInt(a.split(' - ')[0].trim())),
          phuKien: this.p_phukien,
          mac: this.p_mac.trim(),
          soLuong: this.p_kieubh_id.toString() == '3' ? parseInt(this.p_soluong) : 1,
          ngayYC: this.f_DateToString(this.params.p_ngay_yc,'DD/MM/YYYY HH:mm:ss'),
          dienThoaiLH: this.p_dienthoai_lh,
          ghiChu: this.p_ghichu,
          kieuBHId: parseInt(this.p_kieubh_id),
          donViNhanId: parseInt(this.p_donvi_id)
        })

        let mess = this.stateButton.v_NhapMoiDisable ? "Tạo mới thành công!" : "Cập nhật dữ liệu thành công!"
        this.$toast.success(mess)
        await this.LoadDanhSachClick()
      }catch(err){   
        this.$toast.error(err.data.message)
      }finally{
        this.loading(false)
      }
    },
    async GhiLai(){
      // quy them dk maxlength cho ghichu, ketqua
      if ( this.p_serial.length > 500 || this.p_ghichu.length > 500 ||
         this.p_phukien.length > 500 || this.p_mac.length > 500 || 
         this.p_dienthoai_lh.length > 500 ){
        this.$toast.error("Trường dữ liệu quá dài, vượt quá 500 ký tự!")
        return
      }

      // update
      if(!this.stateButton.v_NhapMoiDisable){
        if(!(await this.KiemTraDuLieu(false))){
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có muốn cập nhật dữ liệu không?",
            {
              title: "Xác nhận hành động",
              centered: true,
              size: "md",
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
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
          return
        }

        await this.TaoDuLieu(false);
        await this.TaoDuLieu_BaoHanh_DV();
        await this.updateDSBaoHanh({listUpdateDSBaoHanhIn: this.new_DSBH_Data})
        await this.xoaBaoHanhDV(this.params.p_baohanh_id)
        await this.insertBaoHanhDV({listBaoHanhDVIn:this.new_DSBH_DV_Data})
        await this.CapNhatTinhTrang()
        await this.giaophieu_tn_baohanh()
        this.$toast.success("Cập nhật thành công!")
        await this.LoadDanhSachClick();
        // await this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật dữ liệu không', {
        //   "okVariant": 'danger',
        //   "okTitle": 'Đồng ý',
        //   "cancelTitle": 'Hủy',
        //   "centered": true,
        // })
        //   .then(async(value) => {
        //     if (value){
        //       await this.TaoDuLieu(false);
        //       await this.TaoDuLieu_BaoHanh_DV();
        //       await this.updateDSBaoHanh({listUpdateDSBaoHanhIn: this.new_DSBH_Data})
        //       await this.xoaBaoHanhDV(this.params.p_baohanh_id)
        //       await this.insertBaoHanhDV({listBaoHanhDVIn:this.new_DSBH_DV_Data})
        //       await this.CapNhatTinhTrang()
        //       await this.giaophieu_tn_baohanh()
        //       this.$toast.success("Cập nhật thành công!")
        //       await this.LoadDanhSachClick();
        //     }
        //   })
        //   .catch(err => {
        //     // An error occurred
        //   })
      }
      // Insert
      else{
        if(! (await this.KiemTraDuLieu(true))){
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có muốn cập nhật dữ liệu không?",
            {
              title: "Xác nhận hành động",
              centered: true,
              size: "md",
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
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
          return
        }
        await this.TaoDuLieu(true);
        await this.TaoDuLieu_BaoHanh_DV();
        // await this.updateDSBaoHanh({listUpdateDSBaoHanhIn: this.new_DSBH_Data}) // thiếu API insert 23/3/22
        await this.insertDSBaoHanh(this.new_DSBH_Data[0]).then(result => {
          this.params.p_baohanh_id = result.data.data
        })
        await this.xoaBaoHanhDV(this.params.p_baohanh_id)
        await this.insertBaoHanhDV({listBaoHanhDVIn:this.new_DSBH_DV_Data})
        await this.CapNhatTinhTrang()
        await this.giaophieu_tn_baohanh()
        this.$toast.success("Thêm mới thành công!")
        await this.LoadDanhSachClick()
        // await this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật dữ liệu không', {
        //   "okVariant": 'danger',
        //   "okTitle": 'Đồng ý',
        //   "cancelTitle": 'Hủy',
        //   "centered": true,
        // })
        //   .then(async(value) => {
        //     if (value){
        //       await this.TaoDuLieu(true);
        //       await this.TaoDuLieu_BaoHanh_DV();
        //       // await this.updateDSBaoHanh({listUpdateDSBaoHanhIn: this.new_DSBH_Data}) // thiếu API insert 23/3/22
        //       await this.insertDSBaoHanh(this.new_DSBH_Data[0]).then(result => {
        //         this.params.p_baohanh_id = result.data.data
        //       })
        //       await this.xoaBaoHanhDV(this.params.p_baohanh_id)
        //       await this.insertBaoHanhDV({listBaoHanhDVIn:this.new_DSBH_DV_Data})
        //       await this.CapNhatTinhTrang()
        //       await this.giaophieu_tn_baohanh()
        //       this.$toast.success("Cập nhật thành công!")
        //       await this.LoadDanhSachClick()
        //     }
        //   })
        //   .catch(err => {
        //     // An error occurred
        //   })
        
      }
    },
    // KiemTraDuLieu
    async KiemTraDuLieu(themmoi){
      if(!this.p_serial.trim()){
        this.$toast.error("Bạn chưa nhập serial!")
        this.$refs.inputSerial.focus();
        return false;
      }
      if(!this.p_dienthoai_lh.trim()){
        this.$toast.error("Bạn chưa nhập số điện thoại liên hệ!");
        this.$refs.inputSDT.focus()
        return false;
      }
      if (!this.p_tinhtrang_id.length){
        this.$toast.error("Bạn chưa nhập tình trạng thiết bị!");
        this.$refs.selectTinhTrang.focusIn();
        return false;
      }
      if (this.p_kieubh_id.toString() == "3"){
        if(!this.p_soluong){
          this.$toast.error("Bạn chưa nhập số lượng nguồn/phụ kiện!");
          this.$refs.inputSoLuong.focus();
          return false;
        }
      }
      console.log(this.p_vattu_id , " = p_vattu_id")
      if (!this.p_vattu_id ){
        this.$toast.error("Bạn chưa chọn vật tư!");
        return false;
      }
      let KTSerialTNParams ={
        serial: this.p_serial,
        donViId: this.p_donvi_id 
      }
      let resKTTN = await this.kiemTraSerialChoTiepNhan(KTSerialTNParams)

      if (resKTTN && resKTTN.data.data.length){
        this.$toast.error("Serial đang được chờ tiếp nhận bảo hành tại đơn vị khác, bạn không thể tiếp nhận!");
        return fallse;
      }

      if (themmoi){
        let resKTBH = await this.kiemTraSerialBH(this.p_serial);
        if (resKTBH && resKTBH.data.data.length){
          this.$toast.error("Serial đang được bảo hành, bạn không thể nhập bảo hành!");
          return false;
        }
      }

      if(!this.p_nhomtb_id && this.params.p_thietbi_id == 0 ){
        this.$toast.error("Bạn chưa chọn nhóm thiết bị");
        this.$refs.selectNhomTB.focusIn()
        return false;
      }

      if(!this.p_loaitb_id && this.params.p_thietbi_id == 0){
        this.$toast.error("Bạn chưa chọn loại thiết bị")
        this.$refs.selectLoaiTB.focusIn();
        return false;
      }
      return true
    },
    // TaoDuLieu
    async TaoDuLieu(themmoi){
      this.new_DSBH_Data = [];
      let row = {}
      if(themmoi){
        let getKeyParams = {
          "keyName" : "BAOHANH_ID",
          "numBlockSize" : 1,
          "numRetry":  10
        }
        let resKey = await this.getKey(getKeyParams);
        this.params.p_baohanh_id = resKey;
        // this.params.p_baohanh_id = 0;
      }
      
      row.baoHanhId = this.params.p_baohanh_id

      let ds = [];
      this.params.p_baohong_id = 0
      this.params.p_hdtb_id = 0;
      await TiepNhanThietBiBaoHanhAPI.getDSTiepNhanBH(this.axios, {
        params: { nhanVienId: this.$auth.getNhanVienID(),
                  serial: this.p_serial, },
        pageNo: 0, // check bảo hành của serial => pageSize cố định cũng dc
        pageSize: 10 })
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000') {
            let items = []
            if (response.data.data !== undefined) {
              ds = response.data.data[0]
              this.params.p_baohong_id = ds.BAOHONG_ID ? parseInt(ds.BAOHONG_ID) : 0;
              this.params.p_hdtb_id = ds.HDTB_ID ? parseInt(ds.HDTB_ID) : 0;
            }
          }
        }
      )
      .catch(function() {

      })
      .finally(function() {

      })
      if (this.params.p_baohong_id){
        row.baoHongId = this.params.p_baohong_id
      }

      if (this.params.p_hdtb_id){
        row.hdtbId = this.params.p_hdtb_id
      }
      row.dienThoaiLH = this.p_dienthoai_lh;
      row.vatTuId = parseInt(this.p_vattu_id);

      let tinhTrangArr = this.p_tinhtrang_id.map(a=>a.split(' - ')[1] );
      row.tinhTrang = tinhTrangArr.join(';');
      if (this.params.p_phieuvt_id){
        row.phieuVTId = this.params.p_phieuvt_id
      }

      row.thietBiId = this.params.p_thietbi_id ?  this.params.p_thietbi_id : 0;
      row.phuKien = this.p_phukien;
      row.ngayYC = this.f_DateToString(this.p_ngay_yc,'DD/MM/YYYY hh:mm:ss')
      row.ttbhId = 1;
      row.ghiChu = this.p_ghichu;
      row.kieuBH = parseInt(this.p_kieubh_id);
      row.serial = this.p_serial.trim();
      row.mac = this.p_mac.trim();
      row.loaiTbiId = parseInt(this.params.p_loaitb_id);
      let resQT = await this.getQuyTrinhBH(this.p_vattu_id);
      if (resQT && resQT.data.data){
        this.params.p_quytrinh_id =  parseInt(resQT.data.data[0].QUYTRINH_ID)
      }
      row.quiTrinhId = this.params.p_quytrinh_id;
      row.nguoiCN = this.$auth.getUserName()
      row.ngayCN = this.f_nowDatetime();
      row.mayCN = this.$auth.getUserName()
      row.ipCN = this.$auth.getUserName()//this.$auth.getIP()
      row.soLuong = this.p_kieubh_id.toString() == '3' ? parseInt(this.p_soluong) : 1;
      row.donViId = this.p_donvi_id;
      row.nhanVienId = this.$auth.getNhanVienID()
      this.new_DSBH_Data.push(row)
      // if (themmoi == false){
      // }

    },
    async getDonVi(kieu,vdonvi_id,tentat,loai_dv){
      let res = [];
      if(this.cay_dvi_khac){
        let DVBH_Params = {
          kieu: kieu,
          donViId: vdonvi_id,// this.p_donvi_id 
          tenTinh: tentat // this.$auth.getTenTat_CCBS()
        }
        res = await this.getDonViBH(DVBH_Params);
      }
      else{
        let DVTheoKho_Params = {
          loaiDVId: loai_dv,
          donViId: vdonvi_id,//this.p_donvi_id
        }
        res = await this.getDonViTheoKho(DVTheoKho_Params)
      }
      return res
    },
    setBHDV_row(baohanh_id,loai_dv,donvi_id,kieudv_id){
      let row = {
        baoHanhId: baohanh_id,
        loaiDVId: loai_dv,
        donViId: donvi_id,
        kieuDvId: kieudv_id
      }
      return row
    },
    // TaoDuLieu_BaoHanh_DV
    async TaoDuLieu_BaoHanh_DV(){
      this.new_DSBH_DV_Data = [];
      // Đơn vị tiếp nhận tổ VT
      this.new_DSBH_DV_Data.push(this.setBHDV_row(this.params.p_baohanh_id,this.LOAI_DV.TRAM_VT,this.p_donvi_id,2));
      // Kho nhận thiết bị đi bảo hành
      let resDVBH = await this.getDonVi(0,this.p_donvi_id,"",this.LOAI_DV.KHO_NHAN_TBI_BAOHANH)
      
      if (resDVBH.length){
        this.new_DSBH_DV_Data.push(this.setBHDV_row(this.params.p_baohanh_id,this.LOAI_DV.KHO_NHAN_TBI_BAOHANH,resDVBH[0].DONVI_ID,2));

      }
      // Tổ bảo hành sửa chữa
      let resBHSC =await this.getToBHSuaChua(this.LOAI_DV.TO_BHSC);
      if (resBHSC.length){
          this.new_DSBH_DV_Data.push(this.setBHDV_row(this.params.p_baohanh_id,this.LOAI_DV.TO_BHSC,resBHSC[0].DONVI_ID,2));
      }
      // TTKD - Bảo hành vật tư
      let resBHVT = await this.getToBHSuaChua(this.LOAI_DV.TTKD_BAOHANH_VT)
      if(resBHVT.length){
        this.new_DSBH_DV_Data.push(this.setBHDV_row(this.params.p_baohanh_id,this.LOAI_DV.TTKD_BAOHANH_VT,resBHVT[0].DONVI_ID,2));
      }
      // Kho nhận thiết bị bảo hành xong
      let resTBBH = await this.getDonVi(1,this.p_donvi_id,"",this.LOAI_DV.KHO_NHAN_TBI_BAOHANH_XONG)
    
      if (resTBBH.length){
        this.new_DSBH_DV_Data.push(this.setBHDV_row(this.params.p_baohanh_id,this.LOAI_DV.KHO_NHAN_TBI_BAOHANH_XONG,resTBBH[0].DONVI_ID,2));
      }
      // Đơn vị hoàn thiện bảo hành
      this.new_DSBH_DV_Data.push(this.setBHDV_row(this.params.p_baohanh_id,this.LOAI_DV.HOANTHIEN_BAOHANH,this.p_donvi_id,2));
      console.log(this.new_DSBH_DV_Data);
    },
    // CapNhatTinhTrang
    async CapNhatTinhTrang(){
      
      await this.xoaBaoHanhTinhTrang(this.params.p_baohanh_id)
      let BHTTr_params = [];
      this.getTinhTrang(this.p_tinhtrang_id).forEach(a=> BHTTr_params.push({
        baoHanhId: this.params.p_baohanh_id,
        tinhTrangBaoHanhId: a
      }))
      await this.insertBaoHanhTinhTrang({listTinhTrangBaoHanhIn:BHTTr_params});
    },
    // getTinhTrang
    getTinhTrang(){
      return this.p_tinhtrang_id.map(a=>a.split(' - ')[0].trim())
    },
    // giaophieu_tn_baohanh
    async giaophieu_tn_baohanh(){
      await this.giaoPhieuTNBaoHanh(this.params.p_baohanh_id)
    },
    // tsbtnHuyBo_Click
    HuyBoClick(){
      this.setButton(2);
    },
    // tsbtnXoa_Click
    async XoaClick(){
      if(this.params.p_baohanh_id <= 0){
        return
      }

      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn xóa dữ liệu ? ", {
          title: "Thông báo",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
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
      await this.xoaBaoHanh(this.params.p_baohanh_id)
      this.$toast.success("Xóa thành công");
      await this.LoadDanhSachClick()
      // if (this.phanvungSTG && this.params.p_phieuvt_id){
      //   this.$toast.error("Bạn không thể xóa, do bảo hành được thu hồi về với trạng thái hỏng. Cần bảo hành!")
      //   return
      // }
      // if (this.params.p_ttbh_id != 1 && this.params.p_ttbh_id != 0){
      //   this.$toast.error("Bạn không thể xóa, kiểm tra lại trạng thái bảo hành!")
      //   return 
      // }
      // await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu không?', {
      //     "title": '',
      //     "size": 'lg',
      //     "buttonSize": 'lg',
      //     "okVariant": 'danger',
      //     "okTitle": 'Đồng ý',
      //     "cancelTitle": 'Hủy',
      //     "hideHeaderClose": false,
      //     "centered": true,
      //     "body-class": ["f18","text-center"],
      //   })
      //     .then(async(value) => {
      //       if (value){
      //         await this.updateBaoHanh({
      //           "nguoiCN": this.$auth.getUserName(),
      //           "ipcn": this.$auth.getUserName(),//this.$auth.getIP()
      //           "baoHanhId": this.params.p_baohanh_id
      //         })
      //         this.$toast.success("Xóa thành công");
      //         await this.LoadDanhSachClick()
      //       }
      //     })
      //     .catch(err => {
      //       // An error occurred
      //     })
    },
    // txtSerial_Leave
    async LeaveSerial(){     
      await this.Find_serial()
    },
    // txtSerial_KeyPress
    async EnterSerial(){
      this.$refs.selectNhomTB.focusIn()
      await this.Find_serial()
    },
    // Find_serial
    async Find_serial(){
      if (this.p_serial.trim() == "0" || !this.p_serial){
        return
      }
      this.params.p_thietbi_id = 0 // gán lại p_thietbi_id = 0
      // Kiểm tra serial tiếp nhận
      var response = await TiepNhanThietBiBaoHanhAPI.getDSTiepNhanBH(this.axios, {
          params: { nhanVienId: this.$auth.getNhanVienID(), 
                    serial: this.p_serial.trim().toUpperCase()},
          pageNo: 0, //Kiểm tra serial tiếp nhận => pageSize cố định cũng dc
          pageSize: 10 })
      if (response.data.error_code === 'BSS-00000000') {
        let items = []
        if (response.data.data !== undefined && response.data.data.length > 0) {
          let ds = response.data.data[0]
          let rowIndex = this.danhSachTiepNhanBH.findIndex(a=>a.BAOHANH_ID.toString() == ds.BAOHANH_ID.toString()
          && a.BAOHONG_ID.toString() == ds.BAOHONG_ID.toString())
          this.$refs.tableTiepNhanBH.setCurrentSelectedRow(rowIndex)
          // await this.onSelectedRow(ds)

        }else{
          let resVT = await this.getThongTinVTTheoSerial(this.p_serial.trim().toUpperCase());
          if (resVT && resVT.data.data !== undefined && resVT.data.data.length > 0  ){
            
            let response = resVT.data.data[0]
            this.params.p_thietbi_id = parseInt(response.THIETBI_ID);
            if (response.VATTU_ID.toString() && (response.VATTU_ID.toString() != '0')){
              this.p_vattu_id = parseInt(response.VATTU_ID);
              this.stateButton.v_VatTuDisable = true;
            }
            else{
              this.stateButton.v_VatTuDisable = false;
            }
          }
          else{
            this.stateButton.v_VatTuDisable = false;
          }
        }
      }
    },
    // tsbtnGiaoPhieu_Click
    async GiaoPhieuClick(){
      if(this.danhSachTiepNhanBH.length <= 0){
        this.$toast.error("Không có thiết bị để giao phiếu!");
        return
      }
      if (this.params.p_quytrinh_id != 0){
        let resHGQT = await this.getHuongGiaoQuyTrinh({quyTrinhId:this.params.p_quytrinh_id,thuTuId:2})
        let resQuyenHG = await this.getDKChoBang({
            "nguoiDungId" : this.$auth.getNguoiDungID(),
            "tenBang" : "HUONGGIAO"
        })        
        
        let QuyenHGString = resQuyenHG && resQuyenHG.data.data && resQuyenHG.data.data.length > 0  && resQuyenHG.data.data[0] != null ?  resQuyenHG.data.data.join(',') : "-1"
      
        let resHG = await this.getDanhSachHuongGiao({
          "quyenHuongGiao" : QuyenHGString,
          "nhomQTId" : this.NHOM_QT.BAOHANH_TBI, 
          "quyTrinhId" : 0,
          "loaiHDId" : 0,
          "dichVuVTId" : 0
        })
        let huongGiaoId = resHGQT && resHGQT.data.data ? resHGQT.data.data[0].huongGiaoId : 0
        if (resHG && resHG.data.data != undefined && resHG.data.data){
          let hg_id = resHG.data.data.find(a=>a.HUONGGIAO_ID.toString() == huongGiaoId.toString())
          if (hg_id){
            if (huongGiaoId.toString() != '0'){
              this.$router.push('GiaoPhieuBaoHanh')
              this.setTag("1401") // Hướng giao cho thủ kho
              this.setQuyTrinh(this.params.p_quytrinh_id);
              this.setHuongGiao(huongGiaoId) 
              await this.LoadDanhSachClick()
            }
          }
          else{
            this.$router.push('GiaoPhieuBaoHanh')
            this.setTag("1401");// Hướng giao cho thủ kho
            await this.LoadDanhSachClick();
          }
        }
      }
      else{
        this.$router.push('GiaoPhieuBaoHanh');
        this.setTag("1401");// Hướng giao cho thủ kho
        await this.LoadDanhSachClick();
      }
    },

    async frmTiepNhanThietBiBH_Load(){
      this.clearData();
      let donViParams = {
          nhanVienId: this.$auth.getNhanVienID(),
          loaiDVId : "5"
      }
      await this.getDSDonViTheoNVLoaiDV(donViParams)

      await this.getDSKieuBaoHanh()

      await this.getDSTinhTrang()

      await this.getDSNhomThietBi()

      await this.getDSVatTu()
      
      await this.getDSThamSoMacDinh(this.params.p_kieu_id);

      // Lấy TS mặc định
      let row = this.danhSachThamSoMD.filter(a=>a.ma_ts == 'TREE_DV_BAOHANH')
      if (row.length && row.some(a=>a.ten_ts.trim() == '1')){
        this.cay_dvi_khac = true
      }

      if (this.$auth.getPhanVungID.toString() == "47"){
        this.phanvungSTG = true
      }

      await this.onChungLoaiChange(this.p_nhomtb_id)

      await this.LoadDanhSachClick()
    },
    isNumber: function(evt) {
      evt = (evt) ? evt : window.event
      var charCode = (evt.which) ? evt.which : evt.keyCode;
      if ((charCode > 31 && (charCode < 48 || charCode > 57))) {// 46 is dot
        evt.preventDefault()
      } else {
        return true
      }
    }
  },
  
  async created() {
    await this.frmTiepNhanThietBiBH_Load()
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>