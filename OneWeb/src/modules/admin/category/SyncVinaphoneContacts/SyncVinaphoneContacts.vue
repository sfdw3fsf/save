<template src="./SyncVinaphoneContacts.html"></template>
<style scoped src="./SyncVinaphoneContacts.scss"></style>
<script>
import API from './API.js'
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import moment from 'moment'
import DataGrid3 from '@/components/Controls/DataGrid3'
import DatePicker from 'vue2-datepicker'
import VueNumeric from 'vue-numeric'
import DataGrid from './components/DataGrid'
import ModalDsDiaDanhLoi from './components/ModalDsDiaDanhLoi'
import ModalChuanHoaTuyenThu from './components/ModalChuanHoaTuyenThu'
import ModalSuaGiayTo from './components/ModalSuaGiayTo'
import ModalTongHop from './components/ModalTongHop'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal'
import SearchAccountModal from './components/SearchAccountModal.vue'
export default {
  name: 'SyncVinaphoneContacts',
  components: {
    breadcrumb,
    DataGrid,
    DataGrid3,
    DatePicker,
    VueNumeric,
    ModalDsDiaDanhLoi,
    ModalChuanHoaTuyenThu,
    ModalSuaGiayTo,
    ModalTongHop,
    ExportDataModal,
    SearchAccountModal
  },
  data() {
    return {
      load: true,
      daDongBo: false,
      enableCbDonVi: true,
      choPhepSuaSoGt: true,
      chuyenForm: false,
      header: {
        title: "Đồng bộ dữ liệu danh bạ Vinaphone về tỉnh",
        list: [
          //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
        ]
      },
      donViQl: null,
      donViQlNeo: null,
      dsDonViQl: [],
      dateFormat: "DD/MM/YYYY",
      timeVnp: "",
      startDt: null,
      denNgay: moment(new Date()).format("DD/MM/YYYY"),
      loaiDl: 1,
      dsLoaiDl: [],
      soLuong: 10000,
      bienDong: 0,
      txtMaTB: "",
      boQuaLoi: 0,
      tongSo: 0,
      tieuDeBang: 'Danh sách thuê bao biến động',
      dsThueBao: [],
      dsThanhToan: [],
      selectedThueBaoItem:[],
      selectedThanhToanItem:[],
      dsDiaDanhLoi: [],
      dsTuyenThuLoi: [],
      dsGiayTo: [],
      dsXuatDl: [],
      dtDanhSach: [],
      dtcheck: [],
      soMay: [],
      maKh: [],
      firstLoad:true,
    }
  },
  async mounted () {
    this.loading(true)
    try{
      await API.fn_load_dl_dongbo_vnp(this.axios, {"ds_para": ""}).then(response => {
        this.loading(false)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
          let res = JSON.parse(response.data.data)
          if(res.ERROR_CODE == 1){
            this.dsDonViQl = res.RESULT.DS_DVI
            this.donViQl = Number(this.$root.token.getDonViDuLieuID())
            // console.log('donViId:' + this.$root.token.getDonViDuLieuID())
            // bo kiem tra donvi, show all theo yeu cau a Hieu
            // if(this.donViQl === 0) //neu bi loi combo disable va k co du lieu thi do donViQl cua user != 0 va k nam trong danh sach donvi_id
            //   this.enableCbDonVi = true
            // else
            //   this.enableCbDonVi = false
            const dsDonvi = this.dsDonViQl.map(x => x.DONVI_ID)
            if(!dsDonvi.includes(this.donViQl) && dsDonvi.length > 0)
              this.donViQl = dsDonvi[0]
            this.enableCbDonVi = true
            this.dsLoaiDl = res.RESULT.LOAI_DL
            this.timeVnp = res.RESULT.TIME_VNP
            this.startDt = moment(this.timeVnp, 'YYYYMMDDHHmmss')
            if(this.maTinh == 'HCM')
              this.choPhepSuaSoGt = false
            else
              this.choPhepSuaSoGt = true  
            
            this.load = false   
          }
          else
            throw res.MESSAGE
        }
        else
          throw response.data.message
      })
    } catch(e){
      this.$toast.error(e.data.message_detail)
      this.loading(false)
    }
  },
  watch:{
    async donViQl(newVal, oldVal){
      try{
        this.donViQlNeo = this.mapDonViNeo(newVal)
        if (this.firstLoad == true) {
          this.firstLoad = false
          return
        }
        if(!this.load){
          if (!this.daDongBo)
            await this.hienThiDanhSach()
          else
            await this.hienThiDanhSachDaDongBo()
        }
      }catch(e){
        this.$toast.error(e)
      }
    },
    async loaiDl(newVal, oldVal){
      try{
        if(newVal === 1){
          this.tieuDeBang = 'Danh sách thuê bao biến động'
          if(this.load != false)
            this.bienDong = 0
        }
        else{
          this.tieuDeBang = 'Danh sách khách hàng biến động'
          this.bienDong = 0
        }
        if(!this.load){
          if (!this.daDongBo)
            await this.hienThiDanhSach()
          else
            await this.hienThiDanhSachDaDongBo()
        }
      }catch(e){
        this.$toast.error(e)
      }
    },
    async daDongBo(newVal){
      try{
        if(newVal == true){
          this.dsThueBao = []
          this.dsThanhToan = []
          this.tongSo = 0
          await this.hienThiDanhSachDaDongBo()
        }
        else{
          this.dsThueBao = []
          this.dsThanhToan = []
          this.tongSo = 0
          await this.hienThiDanhSach()
        }
      }catch(e){
        this.$toast.error(e)
      }
    },
    soLuong(newVal){
      if(newVal < 0)
        this.soLuong = 0
    }
  },
  computed: {
    maTinh(){
      return this.$root.token.getMaTinh()
    },
    tungay(){
      let dt = moment(this.denNgay + " 23:59:59", 'DD/MM/YYYY HH:mm:ss').subtract(1, 'months')
      if (dt.isSame(this.startDt, 'year')){
        if(dt.isBefore(this.startDt, 'month') || dt.isSame(this.startDt, 'month'))
          return this.startDt.format('DD/MM/YYYY HH:mm:ss')
        else
          return '01/' + dt.format('MM/YYYY') + ' 00:00:00'
      }
      else
        return '01/' + moment(this.denNgay, 'DD/MM/YYYY').format('MM/YYYY') + ' 00:00:00'
        // return '01/' + dt.format('MM/YYYY') + ' 00:00:00'
    },
    denngay(){
      return this.denNgay + " 23:59:59"
    }
  },
  methods:{
    async layDl(){
      try{
        if(this.donViQlNeo === null){
          this.$toast.error('Đơn vị quản lý NULL không thể lấy dữ liệu !')
          return
        }
        if(this.loaiDl === 1){ //THUE BAO
          if(!await this.layDuLieuTbKh(1)) return
          if(!await this.layDuLieuTbKh(2)) return
        }
        else {
          if(!await this.layDuLieuTbKh(2)) return
        }
        await this.hienThiDanhSach()
      } catch(e){
        this.$toast.error(e)
      }
    },
    showPopupSearchAccount() {
            this.$refs.searchAccountModal.showModal();
    },
    async acceptSearchAccount(item) {
            this.txtMaTB = item.ma_tb;
        },
    //Nhấn nút hiện popup số máy
    async txtMaTB_KeyPress() {
            if (this.txtMaTB != "") {
                try{
                  if(this.donViQlNeo === null){
                    this.$toast.error('Đơn vị quản lý NULL không thể lấy dữ liệu !')
                    return
                  }
                  if(this.loaiDl === 1){ //THUE BAO
                    if(!await this.layDuLieuTbKh(1)) return
                    if(!await this.layDuLieuTbKh(2)) return
                  }
                  else {
                    if(!await this.layDuLieuTbKh(2)) return
                  }
                  await this.hienThiDanhSach()
              } catch(e){
                this.$toast.error(e)
              }
            }
            
      },
    mapDonViNeo(donViId){
      let donVi = this.dsDonViQl.filter(obj => {
        return obj.DONVI_ID == donViId
      })
      return donVi.length > 0 ? donVi[0].DONVI_ID_NEO : null
    },
    kiemTraLayDl(){
      let denNgayM = moment(this.denNgay, 'DD/MM/YYYY')
      if(denNgayM < this.startDt){
        this.$toast.error('Không được lấy dữ liệu nhỏ hơn ngày '+this.startDt.format('DD/MM/YYYY HH:mm:ss')+'. Bạn hãy kiểm tra lại!')
        return false
      }
      if(denNgayM > moment(new Date())){
        this.$toast.error('Chỉ được đồng bộ các thuê bao/khách hàng đến ngày hiện tại!')
        return false
      }
      return true
    },
    async layDuLieuTbKh(vloai){
      try{
        let valid = this.kiemTraLayDl()
        if(!valid) return
        let tungay
        let dt = moment(this.denNgay + " 23:59:59", 'DD/MM/YYYY HH:mm:ss').subtract(1, 'months')
        if (dt.isBefore(this.startDt, 'year')){
          tungay = this.startDt.format('DD/MM/YYYY HH:mm:ss')
        }
        else if (dt.isSame(this.startDt, 'year')){
          if(dt.isBefore(this.startDt, 'month') || dt.isSame(this.startDt, 'month'))
            tungay = this.startDt.format('DD/MM/YYYY HH:mm:ss')
          else if(dt.isSame(this.startDt.add(1, 'months'), 'month'))
            tungay = this.startDt.format('DD/MM/YYYY HH:mm:ss')
          else
            tungay = '01/' + dt.format('MM/YYYY') + ' 00:00:00'
        }
        else
          tungay = '01/' + dt.format('MM/YYYY') + ' 00:00:00'
          
        let denngay = this.denNgay + " 23:59:59"
        this.loading(true)
        return await API.lay_dl_danhba_vnp(this.axios, {
          "vloai_id": vloai, //this.loaiDl,
          "vtungay": tungay,
          "vdenngay": denngay,
          "vdonvi_id_neo": this.donViQlNeo,
          "vma_tb":this.txtMaTB
        }).then(response => {
          this.loading(false)
          if(response && response.data && response.data.error_code === 'BSS-00000000'){
            return true
          }
          else{
            throw response.data.message_detail
          }
        })
      } catch(e) {
        this.loading(false)
        throw e.data.message_detail
      }
      finally{
        this.loading(false)
      }
    },
    async hienThiDanhSach(){
      this.loading(true)
      this.chuyenForm = false
      // this.dsThueBao = []
      // this.dsThanhToan = []
      // this.tongSo = 0
      try{
        let params = {
          "TUNGAY": this.tungay,
          "DENNGAY": this.denngay,
          "MAY_CN": this.$root.token.getMaNhanVien(),//getMachine(),
          "NGUOI_CN": this.$root.token.getUserName(), //getIP(),
          "DONVI_ID_NEO": this.donViQlNeo,
          "LOAIDL_ID":this.loaiDl,
          "KIEU": this.bienDong == 1 ? 0 : this.bienDong,
          "SOLUONG": this.soLuong
        }
        await API.fn_ht_ds_dongbo_vnp(this.axios, {
          "ds_para": JSON.stringify(params)
        }).then(async response => {
          this.loading(false)
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
            let res = JSON.parse(response.data.data)
            // console.log(res)
            if(res.ERROR_CODE === -1 || res.ERROR_CODE === 0){
              this.$toast.error(res.MESSAGE)
            }
            else if(res.ERROR_CODE === -2){
              if(res.MESSAGE === 'frmDiaDanhLoi'){
                const h = this.$createElement
                await this.$bvModal.msgBoxConfirm([
                  h('div',`Tìm thấy: ${res.RESULT.DS_CHUANHOA.length} địa danh có trên Vinaphone nhưng không có trong CSDL của tỉnh !`),
                  h('div',`Bạn có muốn chuẩn hoá dữ liệu không ?`)], {
                  title: 'Thông báo',
                  size: 'lg',
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Không',
                }).then(async v => {
                  if(!v) {
                    return;
                  }
                  this.dsDiaDanhLoi = res.RESULT.DS_CHUANHOA
                  this.chuyenForm = true
                  this.diaDanh()
                });
              }
              else if(res.MESSAGE === 'frmCapNhatTuyenThu'){
                const h = this.$createElement
                await this.$bvModal.msgBoxConfirm([
                  h('div',`Tìm thấy: ${res.RESULT.DS_CHUANHOA.length} khách hàng từ VinaPhone chưa có tuyến thu hoặc tuyến thu không có trong CSDL của tỉnh !`),
                  h('div',`Bạn có muốn chuẩn hoá dữ liệu không ?`)], {
                  title: 'Thông báo',
                  size: 'lg',
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Không',
                }).then(async v => {
                  if(!v) {
                    return;
                  }
                  this.dsTuyenThuLoi = res.RESULT.DS_CHUANHOA
                  this.chuyenForm = true
                  this.tuyenThu()
                });
              }
              else if(this.maTinh !== 'HCM' && res.MESSAGE === 'frmCapNhatSGT'){
                const h = this.$createElement
                await this.$bvModal.msgBoxConfirm([
                  h('div',`Tìm thấy: ${res.RESULT.DS_CHUANHOA.length} khách hàng trong hệ thống tỉnh trùng số giấy tờ với khách hàng trên VinaPhone !`),
                  h('div',`Bạn có muốn chuẩn hoá dữ liệu không ?`)], {
                  title: 'Thông báo',
                  size: 'lg',
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Không',
                }).then(async v => {
                  if(!v) {
                    return;
                  }
                  this.dsGiayTo = res.RESULT.DS_CHUANHOA
                  this.chuyenForm = true
                  this.suaGt()
                });
              }
              else
                this.$toast.error(res.MESSAGE)
            }
            else if(res.ERROR_CODE === 1){
              if(this.loaiDl == 1){
                this.dtDanhSach = res.RESULT.DT_DS
                if(res.RESULT.DT_DS.length > 0){
                  this.dtDanhSach.sort(function (a, b) {
                    return a.SOMAY < a.SOMAY || b.DATE_SYNC < a.DATE_SYNC
                  })
                }
                this.dtcheck = res.RESULT.DS_VNP
                this.soMay = this.dtcheck.map(obj => obj.SOMAY)
                if(this.dtcheck.length > 0){
                  await this.$bvModal.msgBoxConfirm(`Có: ${this.dtcheck.length} / ${this.dtDanhSach.length} thuê bao không có trong danh bạ. Bạn có chắc muốn xuất dữ liệu ra file Excel không ?`, {
                    title: 'Thông báo',
                    size: 'lg',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Không',
                  }).then(async v => {
                    if(!v) {
                      return;
                    }
                    this.dsXuatDl = this.dtcheck
                    this.$refs.popupExportData.showModal()
                  })
                  if(this.bienDong != 1){
                    let dttb = this.dtDanhSach.filter(obj => {
                      return !this.soMay.includes(obj.SOMAY) 
                    })
                    if(dttb.length > 0){
                      dttb.sort(function (a, b) {
                        return a.SOMAY < a.SOMAY || b.DATE_SYNC < a.DATE_SYNC
                      })
                    }
                    this.dsThueBao = dttb
                    this.tongSo = this.dsThueBao.length
                  }
                  else {
                    // doan nay trong c# k hieu xu ly cai gi, khong thay dung
                    // var ls = dtDanhSach.AsEnumerable().GroupBy(o => o["somay"]).Where(g => g.Count() == 1).Select(o => new { Somay = o.Key.ToString() });
                    //         var dt1 = (from DataRow row in dtDanhSach.AsEnumerable()
                    //                    join lst in ls
                    //                        on row["somay"].ToString() equals lst.Somay
                    //                    select row).CopyToDataTable();
                    //         var dttb = (from DataRow row in dt1.AsEnumerable()
                    //                     where !so_may.Contains(row["somay"].ToString())
                    //                     select row).CopyToDataTable();
                  }
                }
                else{ //dtcheck
                  if(this.bienDong != 1){
                    this.dsThueBao = this.dtDanhSach
                    this.tongSo = this.dsThueBao.length
                  }
                  else if (this.bienDong == 1){
                    //loc dtDanhSach nhung truong hop SOMAY k bi lap lai
                    let dttb = []
                    this.dtDanhSach.forEach(obj => {
                      if(this.getOccurrence(this.dtDanhSach, obj.SOMAY) == 1)
                        dttb.push(obj)
                    })
                    if(dttb.length > 0){
                      dttb.sort(function (a, b) {
                        return a.SOMAY < a.SOMAY || b.DATE_SYNC < a.DATE_SYNC
                      })
                    }
                    this.dsThueBao = dttb
                    this.tongSo = this.dsThueBao.length
                  }
                }
              }
              else{ //loaiDL line 888 c#
                this.dtDanhSach = res.RESULT.DT_DS
                if(this.dtDanhSach.length > 0){
                  this.dtDanhSach.sort(function (a, b) {
                    return a.MA_KH < a.MA_KH || b.DATE_SYNC < a.DATE_SYNC
                  })
                }
                this.dtcheck = res.RESULT.DS_VNP
                this.maKh = this.dtcheck.map(obj => obj.MA_KH)
                if(this.dtcheck.length > 0){
                  await this.$bvModal.msgBoxConfirm(`Có: ${this.dtcheck.length} / ${this.dtDanhSach.length} mã TT có trong danh bạ.Bạn có chắc muốn xuất dữ liệu ra file Excel không ?`, {
                    title: 'Thông báo',
                    size: 'lg',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Không',
                  }).then(async v => {
                    if(!v) {
                      return;
                    }
                    this.dsXuatDl = this.dtcheck
                    this.$refs.popupExportData.showModal()
                  })
                  //Doan xoa trong C# không thấy có api xử lý
                  let dtkh = this.dtDanhSach.filter(obj => {
                    return !this.maKh.includes(obj.MA_KH)
                  })
                  if(dtkh.length > 0){
                    dtkh.sort(function (a, b) {
                      return a.MA_KH < a.MA_KH || b.DATE_SYNC < a.DATE_SYNC
                    })
                    this.dsThanhToan = dtkh
                    this.tongSo = this.dsThanhToan.length
                  }
                  else{
                    this.dsThanhToan = []
                    this.tongSo = this.dsThanhToan.length
                  }
                }
                else{ //dtcheck
                  this.dsThanhToan = this.dtDanhSach
                  this.tongSo = this.dsThanhToan.length
                }
              }
            }
          }
          else {
            this.$toast.error(response.data.message_detail)
          }
        })
      } catch(e) {
        this.loading(false)
        // this.$toast.error(e.data.message_detail)
      }
    },
    getOccurrence(array, value) {
        return array.filter((v) => (v === value)).length
    },
    async hienThiDanhSachDaDongBo(){
      this.loading(true)
      this.chuyenForm = false
      // this.dsThueBao = []
      // this.dsThanhToan = []
      // this.tongSo = 0
      try{
        await API.lay_dl_danhba_vnp_da_dongbo(this.axios, {
          "vloai_id": this.loaiDl,
          "vtungay": this.tungay,
          "vdenngay": this.denngay,
          "vdonvi_id_neo": this.donViQlNeo
        }).then(async response => {
          this.loading(false)
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
            //TODO: chưa có dl, DB - Cuong bao chua dùng hàm này, để chỉnh sau
            
            this.dtDanhSach = response.data.data
            if (this.loaiDl == 1){
              this.dsThueBao = [] //this.dtDanhSach
              this.tongSo = this.dsThueBao.length
            }
            else{
              this.dsThanhToan = [] //this.dtDanhSach
              this.tongSo = this.dsThanhToan.length
            }
          }
          else{
            this.$toast.error(response.data.message_detail)
          }
        })
      } catch(e) {
        this.loading(false)
        // this.$toast.error(e.data.message_detail)
      }
      finally{
        this.loading(false)
      }
    },
    async dongBo(){
      this.loading(true)
      try{
        if(this.loaiDl == 1){ // dong bo thue bao
          if(this.dsThueBao.length == 0){
            this.$toast.error('Không có dữ liệu về biến động thông tin thuê bao !')
            return
          }
          if(this.selectedThueBaoItem.length > 0){
            // console.log(this.selectedThueBaoItem)
            let data = {
              "DS_DONGBO": this.selectedThueBaoItem,
              "LOAIDL_ID":this.loaiDl,
              "BOQUA_LOI": this.boQuaLoi,
              "MAY_CN": this.$root.token.getMaNhanVien(),//getMachine(),
              "NGUOI_CN": this.$root.token.getUserName(),
              "IP_CN": "127.0.0.1", //this.$root.token.getIP(),
              "TUNGAY": this.tungay
            }
            await API.fn_thuchien_dongbo_vnp(this.axios, {
              "ds_para": JSON.stringify(data)
            }).then(async response => {
              this.loading(false)
              if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                let res = JSON.parse(response.data.data)
                if(res.ERROR_CODE === 1){
                  this.$toast.success(`Đã đồng bộ được ${this.selectedThueBaoItem.length} / ${this.dsThueBao.length}`)
                  if (!this.daDongBo)
                    await this.hienThiDanhSach()
                  else
                    await this.hienThiDanhSachDaDongBo()
                }
                else{
                  this.$toast.error(res.MESSAGE)
                }
              }
            })
          }
          else{
            this.$toast.error('Hãy chọn thuê bao để đồng bộ!')
            return
          }
        }
        else{ // dong bo thanh toan
          if(this.dsThanhToan.length == 0){
            this.$toast.error('Không có dữ liệu về biến động thông tin thanh toán!')
            return false
          }
          if(this.selectedThanhToanItem.length > 0){
            // console.log(this.selectedThanhToanItem)
            let data = {
              "DS_DONGBO": this.selectedThanhToanItem,
              "LOAIDL_ID":this.loaiDl,
              "BOQUA_LOI": this.boQuaLoi,
              "MAY_CN": this.$root.token.getMaNhanVien(),//getMachine(),
              "NGUOI_CN": this.$root.token.getUserName(),
              "IP_CN": "127.0.0.1", //this.$root.token.getIP(),
              "TUNGAY": this.tungay
            }
            await API.fn_thuchien_dongbo_vnp(this.axios, {
              "ds_para": JSON.stringify(data)
            }).then(async response => {
              this.loading(false)
              if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                let res = JSON.parse(response.data.data)
                if(res.ERROR_CODE === 1){
                  this.$toast.success(`Đã đồng bộ được ${this.selectedThanhToanItem.length} / ${this.dsThanhToan.length}`)
                  if (!this.daDongBo)
                    await this.hienThiDanhSach()
                  else
                    await this.hienThiDanhSachDaDongBo()
                }
                else{
                  this.$toast.error(res.MESSAGE)
                }
              }
            })
          }
          else{
            this.$toast.error('Hãy chọn thông tin thanh toán để đồng bộ!')
            return
          }
        }
      } catch(e) {
        this.$toast.error(e.data.message_detail)
      }
      finally{
        this.loading(false)
      }
    },
    diaDanh(){
      let valid = this.kiemTraLayDl()
      if(valid){
        this.$bvModal.show('popupDsDiaDanhLoi')
      }
    },
    tuyenThu(){
      let valid = this.kiemTraLayDl()
      if(valid){
        this.$bvModal.show('popupChuanHoaTuyenThu')
      }
    },
    suaGt(){
      let valid = this.kiemTraLayDl()
      if(valid){
        this.$bvModal.show('popupSuaGiayTo')
      }
    },
    tongHop(){
      this.$bvModal.show('popupTongHop')
    },
    async xuatDl(){
      this.loading(true)
      try{
        await API.sp_lay_tt_xuatdl_dongbo_vnp(this.axios, {
          "kieu": 2,
          "tungay": this.tungay,
          "denngay": this.denngay,
          "donvi_id_neo": this.donViQlNeo
        }).then(response => {
          this.loading(false)
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
              //TODO: chưa có dl, DB - Cuong bao để chỉnh sau
              this.dsXuatDl = response.data.data
              this.$refs.popupExportData.showModal()
          }
        })
      } catch(e){
        this.$toast.error(e.data.message_detail)
        this.loading(false)
      }
    },
    async layXuatDl(){
      this.loading(true)
      try{
          return await API.sp_lay_tt_xuatdl_dongbo_vnp(this.axios, {
              "kieu": 2,
              "tungay": this.tungay, 
              "denngay": this.denngay,
              "donvi_id_neo": this.donViQlNeo
          }).then(response => {
              if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                  return response.data.data
                  this.loading(false)
              }
          })
      } catch(e){
          this.$toast.error(e.data.message_detail)
          this.loading(false)
      }
      this.loading(false)
    },
    async selectedThueBaoChanged(data) {
      this.selectedThueBaoItem = this.$refs.gridThueBao.getSelectedRecords()
    },
    async selectedThanhToanChanged(data) {
      this.selectedThanhToanItem = this.$refs.gridThanhToan.getSelectedRecords()
    },
    tangSoLuong(){
      this.soLuong += 10000
    },
    giamSoLuong(){
        this.soLuong -= 10000
    }
  },
};
</script>