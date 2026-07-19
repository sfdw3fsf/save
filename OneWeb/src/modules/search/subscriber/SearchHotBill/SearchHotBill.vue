<template src="./SearchHotBill.html"></template>
<style src="./SearchHotBill.scss"></style>
<script>
import XLSX from 'xlsx'
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import api from './api.js'
import SearchSubsAccount from '@/modules/search/subscriber/SearchSubsAccount/SearchSubsAccount.vue'
export default {
  components: {
    XLSX,
    moment,
    breadcrumb,
    SearchSubsAccount
  },
  name: 'SearchHotBill',
  data() {
    return {
      loading: false,
      header: {
        title: 'Tra cứu thông tin cước nóng trên vinaphone',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      headerGridbox: [
        { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowSorting: true, textAlign: 'center', width: 100 },
        { fieldName: 'MA_TB', headerText: 'Số máy', allowFiltering: true, allowSorting: true, textAlign: 'center' },
        { fieldName: 'TEN_KM', headerText: 'Tên khoản mục', allowFiltering: true, allowSorting: true },
        { fieldName: 'CUOC', headerText: 'Cuộc gọi', allowFiltering: true, allowSorting: true, width: 150 },
        { fieldName: 'PHUT', headerText: 'Phút', allowFiltering: true, allowSorting: true, width: 150 },
        { fieldName: 'TIEN', headerText: 'Tiền', allowFiltering: true, allowSorting: true, textAlign: 'right', customAttributes: { class: 'customcss_tien' } },
        { fieldName: 'VAT', headerText: 'Vat', allowFiltering: true, allowSorting: true },
        { fieldName: 'TONGNO', headerText: 'Tổng nợ', allowFiltering: true, allowSorting: true, textAlign: 'right', customAttributes: { class: 'customcss_tongno' } }
      ],
      gridBox: {
        data: [],
        selected: null
      },
      headerGridSearch: [
        { fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, allowSorting: true },
        { fieldName: 'ma_tt_neo', headerText: 'Mã TT Neo', allowFiltering: true, allowSorting: true }
      ],
      gridSearch: {
        data: [],
        selected: null
      },
      textCaption: 'Kết quả tìm kiếm',
      rcheck: 'soMay',
      vkyhoadonHT: '',
      intSoMay: '',
      intMaTT: '',
      intTenTT: '',
      intDiaCTT: '',
      vma_tinh: '',
      intMaTT_VNP: '',
      intTongNo: 0,
      chukyno: {
        val: '',
        show: false
      },
      dropdownExpand: {
        isShow: '',
        status: 'false'
      }
    }
  },
  mounted() {},
  created() {
    //this.vkyhoadonHT = '01012021'
    this.vkyhoadonHT = "01"+ moment().subtract(1, 'months').format('MMYYYY')
    this.chukyno.val = moment().subtract(1, 'months').format('MMYYYY')
    this.vma_tinh = this.$root.token.getMaTinh()
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  methods: {
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    modelClose(dataDB) {
      this.$refs.SearchSubsAccountModal.hide()
      if (this.rcheck == 'soMay') {
        let ma_tb = dataDB.ma_tb
        this.intSoMay = ma_tb.substring(2)
      } else if (this.rcheck == 'maTT') {
        this.intMaTT = dataDB.ma_tt
      }
      this.btnSearch_Click()
    },
    rcheckChange() {
      this.intMaTT = ''
      this.intSoMay = ''
      this.intTenTT = ''
      this.intDiaCTT = ''
      this.intMaTT_VNP = ''
      this.intTongNo = 0
    },
    tracuudanhbaTB() {
      this.$refs.SearchSubsAccountModal.show()
    },
    btnSearch_Click() {
      this.loading = true
      this.intTenTT = ''
      this.intDiaCTT = ''
      this.intMaTT_VNP = ''
      try {
        if (this.rcheck == 'soMay') {
          this.TracuuSoTb()
        } else if (this.rcheck == 'maTT') {
          this.TraCuuMaTT()
        }
      } catch (error) {
        this.loading = false
        this.$toast.error('Có lỗi xảy ra trong quá trình kết nối với Sercvice VinaPhone!\n' + error)
      }
    },
    TracuuSoTb() {
      this.intTenTT = ''
      this.intDiaCTT = ''
      if (!this.intSoMay) {
        this.$toast.warning('Bạn chưa điền số máy !')
        this.loading = false
        return
      }
      this.lay_matt_neo_vnp('1')
      this.ts_danhmuc_tracuu_cuocnong('1')
    },
    async TraCuuMaTT() {
      if (!this.intMaTT) {
        this.intTenTT = ''
        this.intDiaCTT = ''
        this.$toast.warning('Bạn chưa điền mã thanh toán !')
        this.$refs.rmatt.focus()
        this.loading = false
        return
      }
      await this.lay_matt_neo_vnp('0')
      this.ts_danhmuc_tracuu_cuocnong('0')
    },
    hienthi(listdata) {
      if (listdata && listdata.length > 0) {
        this.chukyno.show = true
        let ds = []
        let tien = 0
        listdata.forEach((element, index) => {
          tien = tien + parseInt(element.TIEN)
          element.stt = index + 1
          element.TIEN = parseInt(element.TIEN).toLocaleString()
          element.TONGNO = parseInt(element.TONGNO).toLocaleString()
          ds.push(element)
        })
        this.gridBox.data = ds
        this.intTongNo = tien.toLocaleString()
        this.chukyno.show = true
        var new_date = moment(this.$auth.getNgayCapNhat(), 'DD/MM/YYYY').add(-1, 'days')
        this.textCaption = 'Thông tin cước tháng đến ngày ' + moment(new_date).format('DD/MM/YYYY')
      } else {
        this.gridBox.data = []
        this.intDiaCTT = ''
        this.intTenTT = ''
        this.intTongNo = 0
      }
      this.loading = false
    },
    async lay_matt_neo_vnp(vkieu) {
      let pvma = ''
      if (vkieu == '0') {
        pvma = this.intMaTT
      } else {
        pvma = this.intSoMay
      }
      try {
        let data = this.GetData(
          await api.lay_matt_neo_vnp(this.axios, {
            vma: pvma,
            vchuky: this.vkyhoadonHT
          })
        )
        let dt = []
        let th = []
        if (vkieu == '0') {
          if (data && data.chitiet) {
            dt = data.chitiet
            if (dt.length == 0) {
              this.$toast.warning('Không tìm thấy mã thanh toán này')
              return
            } else if (dt.length == 1) {
              this.intMaTT = dt[0].ma_tt
              this.intMaTT_VNP = dt[0].ma_tt_neo
            } else {
              this.$refs.rmatt.focus()
              this.gridSearch.data = dt
              this.dropdownExpand.isShow = 'show'
              this.dropdownExpand.status = 'true'
            }
          }
        }
        if (data && data.tonghop.length > 0) {
          th = data.tonghop
          if (th.length > 0) {
            if (vkieu == '0') {
              th.forEach((element) => {
                if (element.ma_tt_neo.toString() == this.intMaTT_VNP) {
                  this.intTenTT = element.ten_tb.toString()
                  this.intDiaCTT = element.diachi_tt.toString()
                  return
                }
              })
            }
            if (!this.intTenTT) {
              this.intTenTT = th[0].ten_tb.toString()
            }
            if (!this.intDiaCTT) {
              this.intDiaCTT = th[0].diachi_tt.toString()
            }
          }
        }
      } catch (error) {
        this.loading = false
        this.$toast.error('Lỗi khi tìm kiếm mã thanh toán\r\n' + error)
      }
    },
    ts_danhmuc_tracuu_cuocnong(vkieu) {
      let ma_vnp = ''
      if (vkieu == '0') {
        ma_vnp = this.intMaTT_VNP.trim().toUpperCase()
      } else {
        ma_vnp = '84' + this.intSoMay.trim().toUpperCase()
      }
      this.getCuocNong(ma_vnp, vkieu)
    },
    async getCuocNong(ma_vnp, vkieu) {
      try {
        const puserid = this.$root.token.getMaCCBS()
        let data = this.GetData(
          await api.ts_danhmuc_tracuu_cuocnong(this.axios, {
            ma_tinh: this.vma_tinh,
            so_tb: ma_vnp,
            kieu_tc: vkieu,
            userid: puserid
          })
        )
        this.hienthi(data)
      } catch (error) {
        this.loading = false
        this.$toast.error('Lỗi\r\n' + error)
      }
    },
    selectedItemsChangedGridbox(item) {
      try {
        let dataItem = item.rowData
        this.intMaTT = dataItem.ma_tt
        this.intMaTT_VNP = dataItem.ma_tt_neo
        this.dropdownExpand.isShow = ''
        this.dropdownExpand.status = 'true'
        this.loading = true
        this.lay_matt_neo_vnp('0')
        this.ts_danhmuc_tracuu_cuocnong('0')
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra ' + error)
      }
    }
  }
}
</script>
