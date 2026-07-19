<template src="./template.html"></template>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'

import ThongTinDatCocTraTruoc from './components/OBGHTT/ThongTinKhachHang/ThongTinDatCocTraTruoc.vue'
import moment from 'moment'
import api from './API.js'
import { Icon } from '@iconify/vue2'
import util from './util'
import NhanCongOB from './components/OBGHTT/NhanCong/NhanCongOB.vue'
import ThongTinTBKhachHang from './components/OBGHTT/NhanCong/ThongTinTBKhachHang.vue'
import ThongTinKhachHangAutoCall from './components/OBGHTT/AutoCall/MediaAutoCall.vue'
import ThongTinChiTietOB from './components/OBGHTT/ThongTinChiTietOB.vue'
import ThongKeOB from './components/ThongKe/ThongKe.vue'
import apiHelper from '../../EXTS/HOPDONG/InHopDong/CreateEInvoiceModal/api.helper'
import _ from 'lodash'

export default {
  components: {
    ThongTinDatCocTraTruoc,
    NhanCongOB,
    ThongTinTBKhachHang,
    ThongTinKhachHangAutoCall,
    ThongTinChiTietOB,
    ThongKeOB,
    breadcrumb,
    Icon
  },
  data() {
    return {
      header: {
        title: 'Gia Hạn Trả Trước',
        list: [{ name: 'Gia Hạn Trả Trước', link: { name: 'Ui.cards' } }]
      },
      currentCustomer: {},
      autoCall: 0,
      khachhang_id: 0,
      ma_kh: '',
      ten_kh: '',
      thang_kt: '',
      phanvung_id: this.$root.token.getPhanVungID(),
      ma_tb: 0,
      selected_item: {
        thuebao_id: 0,
        dichvuvt_id: 0,
        ma_tb: 0,
        ob_id: 0,
      },
      items_kt: [],
      querySate: '',
      tracuuOB: null, // use in Tab Nhan cong
      list_thuebao_selected: [] ,//
    }
  },
  methods: {
    async OutBoundCall_Click() {
      this.$toast.warning('Chức năng đang xây dựng')
    },
    viewReportDetail_Click() {
      this.$toast.warning('Chức năng đang xây dựng')
    },

    async btnCapNhat_click() {
      try {
        this.loading(true)

        let resultUpdate = 'ok'
        let data
        if (this.autoCall == 1) {
          //call function UpdateResultOB
          let response = await this.$refs.refThongTinKhachHangAutoCallComponent.UpdateResultOB()
          resultUpdate = response.message
          data = response.data
        } else {
          //call function UpdateResultOB of component ThongTinTraCuu
          let response = (resultUpdate = await this.$refs.refThongTinTraCuuOBComponent.UpdateResultOB())
          resultUpdate = response.message
          data = response.data
        }
        if (resultUpdate != 'ok') {
          this.$toast.error(resultUpdate)
          return
        }
        this.loading(false)
        this.$toast.success('Cập nhật OB thành công')

        //xử lý sau khi cập nhật OB
        if (data.TT_KETNOI == 1) await this.handleResult_UserPickUp(data)
        else await this.handleResult_DoestNotUserPickUp(data)
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
        await this.$refs.refThongTinChiTietOBlComponent.getListResultOB(this.phanvung_id, this.khachhang_id)
      }
    },
    async handleOnRowThongTinTraCuuSelected(data) {
      this.currentCustomer = data
      this.khachhang_id = data.khachhang_id
      this.ma_kh = data.ma_kh
      this.ma_tb = data.ma_tb
      this.ten_kh = data.ten_kh
    },

    async btnLayThongTin_Click() {
      await this.$refs.refThongTinTraCuuOBComponent.getListInfoCustomer()
    },
    async checkUserLoginIPCC(vphanvung_id, vuser_ipcc) {
      function logOut(sef) {
        sef.$root.context.logOut()
        sessionStorage.setItem('currentPath', `/htkh/OBGiaHanTraTruoc?Ref=${sef.querySate}`)
        sef.$router.push({ path: '/auth/login' })
      }

      try {
        let response = await api.getUserMapIPccInfo(this.axios, { vphanvung_id, vuser_ipcc })

        let users = apiHelper.getDataFromResponseApiReturnArray(response)
        if (!users.length) {
          this.$toast.warning('Không tìm thấy account được phân công!')
          logOut(this)
          return
        }
        let user = users.filter((e) => e.ma_nd == this.$root.token.getUserName())
        if (!user.length) {
          this.$toast.warning(`Tài khoản ${this.$root.token.getUserName()} không phải là tài khoản được phân công!`)
          logOut(this)
          return
        }
      } catch (error) {
        this.$toast.warning('Không tìm thấy account được phân công!')
        logOut(this)

        console.log(error)
      }
    },
    async getCustomerInfo(vphanvung_id, vkhachhang_id) {
      let response = await api.getInfoCustomer(this.axios, { vphanvung_id, vkhachhang_id })
      let data = apiHelper.getDataFromResponseApiReturnArray(response)
      if (data.length > 0) {
        return data[0]
      }
      return {}
    },

    handleThongTinDatCocSelected(items) {
      this.list_thuebao_selected = items.map(e => e.thuebao_id) ; 
      console.log('CSKH list_thuebao_selected', items)
      if (items.length > 0) {
        let index = 0
        for (let i = 0; i < items.length; i++) {
          const item = items[i]
          if (item.loaihinh_tb?.toLowerCase() == 'fiber') {
            index = i
            break
          }
        }
        this.selected_item = items[index]
        
        this.items_kt = _.filter(items, (_, key) => {
          return key !== index
        })
      } else {
        this.selected_item = {
          thuebao_id: 0,
          dichvuvt_id: 0,
          ma_tb: 0,
          ob_id: 0
        }
        this.items_kt = []
      }
    },

    async handleResult_UserPickUp(data) {
      let { KQ_OB, MA_TB, LOAITB_ID, OB_ID } = data
      const maTb = MA_TB ? MA_TB : this.selected_item?.ma_tb
      const obId = OB_ID ? OB_ID : this.selected_item?.ob_id

     // KH hẹn thanh toán/ hẹn liên lạc lại sau  || KH yêu cầu chuyển sang trả sau không tạm thu
      if (Number(KQ_OB) == 9 || Number(KQ_OB) == 12 ) return;
      
      //KH yêu cầu thanh lý
      if (Number(KQ_OB) == 8) {
        let strErr = await this.handleChamSocKH_BaoDuong(obId, data.LIST_TB_SELECTED)

        if (strErr == 'ok') {
          this.$toast.success('Tạo phiếu chăm sóc khách hàng thành công.')
          return
        }
        this.$toast.error(strErr)

        return
      }

      //"KH không hài lòng về chất lượng dịch vụ"
      if(Number(KQ_OB) == 6){
        //todo call function api
        await this.handleBaoHongTuDong(obId)
        return;
      }


      //KH không hài lòng về cước
      if(Number(KQ_OB) == 7){
        let { NGAYHEN_DEN, NGAY_HEN, CHUDEKN_ID } = data
        await this.handleDieuKNTuOBGTT({ vob_id: obId , vhenkn_tu: NGAY_HEN, vhenkn_den: NGAYHEN_DEN, vchudekn_id: CHUDEKN_ID})
        return;
      }

      //Không đúng chủ thuê bao 
      if (Number(KQ_OB) == 13) {

        //call api tao phieu bao duong
        const response = await api.chamSocKhachHangBaoDuongV2(this.axios, {
          vob_id: obId,
          vds_thuebao_id: data.LIST_TB_SELECTED.join(';')
        })

        if (!apiHelper.isResponseApiSuccess(response)) {
          this.$toast.error(apiHelper.getSrtCodeError(response))
          return
        } 

        this.$toast.success('Tạo phiếu chăm sóc khách hàng thành công!')
        return;
      }

       //"KH đồng ý gia hạn" call auto gia han 
      if(Number(KQ_OB) == 1){
        let query = { ma_tb: maTb, loaitb_id: LOAITB_ID, dichvuvt_id: this.selected_item?.dichvuvt_id, ob_id: obId }
        const routeData = this.$router.resolve({
            name: 'Cap-Nhat-Thue-Bao-Dat-Coc',
            query: query,
            path: '/ext-hopdong/Cap-Nhat-Thue-Bao-Dat-Coc'
          })
        window.open(routeData.href, '_blank')

        const listThueBao = this.list_thuebao_selected.join(",");
        await this.handleAutoGiaHan(listThueBao)
        return;
      }
     

      const confirm = await this.$confirm('Thông báo', 'Bạn có muốn mở sang cửa sổ mới không?', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'})

      if (confirm) {
        let query = { ma_tb: maTb, loaitb_id: LOAITB_ID, dichvuvt_id: this.selected_item?.dichvuvt_id, ob_id: obId }

        let routeData
        switch (Number(KQ_OB)) {
          case 2: //"KH yêu cầu thay đổi tốc độ"
            routeData = this.$router.resolve({
              name: 'ThayDoiTocDO',
              query: query,
              path: '/contract/ChangeInternetSpeed'
            })
            window.open(routeData.href, '_blank')
            break
          case 3: //"KH yêu cầu thanh toán gia hạn tại tháng T+1;T+2"
            routeData = this.$router.resolve({
              name: 'Cap-Nhat-Thue-Bao-Dat-Coc',
              query: query,
              path: '/ext-hopdong/Cap-Nhat-Thue-Bao-Dat-Coc'
            })
            window.open(routeData.href, '_blank')
            break
          case 4: //"KH yêu cầu chuyển sang trả sau"
            routeData = this.$router.resolve({
              name: 'CollectIncurredDebt',
              query: query,
              path: '/contract/CollectIncurredDebt'
            })
            window.open(routeData.href, '_blank')
            break
          case 5: //"KH yêu cầu tạm ngưng"
            routeData = this.$router.resolve({
              name: 'ChangeService',
              query: query,
              path: '/contract/ChangeService'
            })
            window.open(routeData.href, '_blank')
            break
          case 10: // Tiếp nhận đơn hàng"
            if (this.ma_kh) query['ma_kh'] = this.ma_kh
            routeData = this.$router.resolve({
              name: 'Order',
              query: query,
              path: '/htkh/Order'
            })
            window.open(routeData.href, '_blank')
            break
          case 11: // "Tạo yêu cầu lắp đặt"
            routeData = this.$router.resolve({
              name: 'InstallNewSubs',
              query: query,
              path: 'contract/InstallNewSubs'
            })
            window.open(routeData.href, '_blank')
            break
          case 15: //  KH yêu cầu thay đổi đặt cọc 
          routeData = this.$router.resolve({
            name: 'ThayDoiDatCoc',
            query: query,
            path: 'ext-hopdong/thaydoi-datcoc'
          })
          window.open(routeData.href, '_blank')
          break

          default:
        }
      }
    },
    async handleResult_DoestNotUserPickUp(data) {
      let titePopup = 'Bạn có muốn tạo phiếu CSKH bảo dưỡng cho NVKT địa bàn không?'

      const confirm = await this.$confirm('Xác nhận', titePopup, {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })

      if (confirm) {
        const obId = data.OB_ID ? data.OB_ID : this.selected_item?.ob_id;

        //update 15/5/2024
        let strErr = await this.handleChamSocKH_BaoDuong(obId, data.LIST_TB_SELECTED)
        if (strErr == 'ok') {
          this.$toast.success('Tạo phiếu chăm sóc khách hàng thành công.')
          return
        }
        this.$toast.error(strErr)
      }
    },

    async handleChamSocKH_BaoDuong(ob_id,listTBSelected ) {
      let strErr = 'ok'
      try {
        const ngayKt = this.thang_kt.length > 12 ? moment(this.thang_kt, 'dd/MM/YYYY HH:mm:ss').format('yyyyMMDD') : moment(this.thang_kt, 'MM/DD/YYYY').format('yyyyMMDD')
        //check ham phieu bao duong truoc
        const check = await this.handleCheckPhieuDieuChamSocBaoDuong({
          vngay_ktdc: ngayKt,
          vthuebao_id: this.selected_item?.thuebao_id
        })

        if (!check) return

        // const response = await api.chamSocKhachHangBaoDuong(this.axios, ob_id)
        const response = await api.chamSocKhachHangBaoDuongV2(this.axios, {
          vob_id: ob_id,
          vds_thuebao_id: listTBSelected.join(';')
        })
        debugger
        if (!apiHelper.isResponseApiSuccess(response)) {
          strErr = apiHelper.getSrtCodeError(response)
        }
      } catch (error) {
        console.log(error)
        strErr = 'Co loi xay ra' + error.message
      }
      return strErr
    },


    async handleCheckPhieuDieuChamSocBaoDuong({ vngay_ktdc, vthuebao_id }) {
      try {
        let strErr = 'ok'
        //check ham phieu bao duong truoc
        const response = await api.checkPhieuChamSoc_BaoDuong(this.axios, { vngay_ktdc, vthuebao_id })
        if (!apiHelper.isResponseApiSuccess(response)) {
          strErr = apiHelper.getSrtCodeError(response)
          this.$toast.error(strErr)
        }

        if (strErr == 'ok') return true
      } catch (error) {
        console.log(error)
        return false
      }
      return false
    },


    async handleBaoHongTuDong(obId){
      if(!obId) return;
      try {
        let strErr = 'ok'
        const response = await api.dieuBaoHongOb(this.axios, obId )
        if (!apiHelper.isResponseApiSuccess(response)) {
          strErr = apiHelper.getSrtCodeError(response)
        }
        if (strErr != 'ok') {
          this.$toast.error(strErr)
          return
        }
        this.$toast.success('Tạo phiếu báo hỏng thành công.')
      } catch (error) {
        console.log(error)
      }
    },

    async handleDieuKNTuOBGTT({ vob_id, vhenkn_tu, vhenkn_den, vchudekn_id }){
      try {
        if(!vob_id || !vchudekn_id) return;
        let strErr = 'ok'
        const response = await api.dieuKNTuOBGHTT(this.axios, { vob_id, vhenkn_tu, vhenkn_den, vchudekn_id } )
        if (!apiHelper.isResponseApiSuccess(response)) {
          strErr = apiHelper.getSrtCodeError(response)
        }
        if (strErr != 'ok') {
          this.$toast.error(strErr)
          return
        }
        this.$toast.success('Tạo phiếu báo hỏng thành công.')
      } catch (error) {
        console.log(error)
      }
    },

    async handleAutoGiaHan(p_thuebao_ids){
      try {
        let strErr = 'ok'
        const response = await api.autoGiaHan(this.axios, p_thuebao_ids)
        if (!apiHelper.isResponseApiSuccess(response)) {
          strErr = apiHelper.getSrtCodeError(response)
        }
        if (strErr != 'ok') {
          strErr = `Gia hạn không thành công với các thuê bao ${p_thuebao_ids}. Vui lòng tạo phiếu gia hạn thủ công.`
          this.$toast.warning(strErr)
          return
        }
      } catch (error) {
        console.log(error)
      }
    },

    async hanldeTapNhanCongChange(data) {
      this.tracuuOB = data
    },
  },

  created() {},
  async mounted() {
    const query = this.$route.query

    if (query.Ref) {
      this.querySate = query.Ref
      let data = util.decodeQuery(query.Ref)
      this.khachhang_id = data.KHACHHANG_ID
      this.ma_kh = data.MA_TB
      this.ten_kh = data.TEN_KH
      this.diachi = data.DIACHI
      this.thang_kt = data.NGAY_KTDC
      this.autoCall = Number(data.auto_call)
      this.phanvung_id = data.PHANVUNG_ID
      this.ma_tb = data.MA_TB
      //get customer info
      let user = await this.getCustomerInfo(data.PHANVUNG_ID, data.KHACHHANG_ID)

      console.log('autoCall: ', this.autoCall)
      this.currentCustomer = Object.assign(data, user)
      //check user login IPCC
      this.checkUserLoginIPCC(data.PHANVUNG_ID, data.user_ipcc)
    }

    if (this.autoCall != 1) {
      await this.$refs.refThongTinTraCuuOBComponent.initComponent()
    } else {
      await this.$refs.refThongTinKhachHangAutoCallComponent.initComponent(this.currentCustomer)
    }
  },
  computed: {
    thang_kt_cpt() {
      if (this.thang_kt) {
        let data = this.thang_kt.length > 12 ? moment(this.thang_kt, 'dd/MM/YYYY HH:mm:ss').format('YYYYMM') : moment(this.thang_kt, 'MM/DD/YYYY').format('YYYYMM')

        return data
      }
    },
    matb_kt_cpt() {
      let ma_kt = this.items_kt.map((e) => e.ma_tb)
      return ma_kt.join(';')
    }
  }
}
</script>

<style scoped lang="scss">
.current-kh {
  font-style: italic;
  font-weight: bold;
  // color: #F9E1A9
}

li {
  cursor: pointer;
}
</style>
