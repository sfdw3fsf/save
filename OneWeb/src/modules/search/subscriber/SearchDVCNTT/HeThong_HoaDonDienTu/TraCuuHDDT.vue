<template src="./TraCuuHDDT.html"> </template>

<script>
import api from './api_tracuu_hddt'
export default {
  data() {
    return {
      data_common: {
        ma_timkiem: '',
        tracuu_hddt: {},
        slhd_damua: '',
        slhd_sudung: '',
        slhd_chuyendoi: '',
        slhd_nhan: ''
      },
      dsThueBao: {
        header: [
          { fieldName: 'Thuê bao ID', headerText: 'Thuê bao ID', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Mã thuê bao', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Tên thuê bao', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Địa chỉ thuê bao', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Email', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Số điện thoai', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Mã số thuê', allowFiltering: true }
        ]
      },
      dsBienDong: {
        header: [
          { fieldName: 'chunhom', headerText: 'HDTB_ID', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Mã giao dịch', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Trạng thái hợp đồng', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Loại hợp đồng', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Kiểu lắp đặt', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Số lượng hóa đơn', allowFiltering: true },
          { fieldName: 'chunhom', headerText: 'Trạng thái kích hoạt', allowFiltering: true }
        ]
      },
      header: {
        title: 'TRA CỨU HỆ THỐNG HÓA ĐƠN ĐIỆN TỬ',
        list: [
          { name: 'Tra cứu', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu hệ thống hóa đơn điện tử',
            link: { name: 'Ui.buttons' }
          }
        ]
      }
    }
  },
  methods: {
    open_form() {
      this.$refs['dialogTraCuuHDDT'].show()
    },
    async Lay_TT_HDDT_Theo_MST(mst) {
      const data_timkiem = {
        hdtb_id: 0,
        vphanvung_id: 0,
        vtype: 'TRACUU_HOADON_DIENTU',
        sub_object: {
          mst: mst
        }
      }
      const get_ketqua_dynamic = await api.cntt_dynamic_api(this.axios, data_timkiem)
      console.log(get_ketqua_dynamic.data.data.data)
      this.data_common.tracuu_hddt = get_ketqua_dynamic.data.data.data
    },
    LaySL_HoaDon_DaMua() {
      return (this.data_common.slhd_damua = this.data_common.tracuu_hddt.invoices_services.filter((item) => item.service_type === 0).map((item) => item.totalNumber))
    },
    LaySl_HoaDon_DaSD() {
      return (this.data_common.slhd_sudung = this.data_common.tracuu_hddt.invoices_services.filter((item) => item.service_type === 0).map((item) => item.totalPublished))
    },
    LaySl_HoaDon_ChuyenDoi() {
      const targetObject = this.data_common.tracuu_hddt.invoices_services.find((item) => item.service_type === 0)
      const totalConvertedNumber = targetObject && targetObject.converted ? targetObject.converted.reduce((sum, item) => sum + item.totalNumber, 0) : 0
      this.data_common.slhd_chuyendoi = totalConvertedNumber
    },
    LaySl_HoaDon_Nhan() {
      const targetObject = this.data_common.tracuu_hddt.invoices_services.find((item) => item.service_type === 0)
      const totalReceivedNumber = targetObject && targetObject.received ? targetObject.received.reduce((sum, item) => sum + item.totalNumber, 0) : 0
      this.data_common.slhd_nhan = totalReceivedNumber
    },
    async Lay_DS_ThueBao_Theo_MST(mst) {
        const data_dulieu = {
            p_nghiepvu : "LAY_DB_THUEBAO_THEO_MST",
            p_ds_para :{"MST":mst}
        }
        const data_db_thubao = await api.lay_ds_nghiepvu_cntt_common(this.axios,data_dulieu,data_db_thubao)
        console.log(data_db_thubao.data.data)
    },

    async handleEnterTimKiem() {
      if (this.data_common.ma_timkiem == '' || this.data_common.ma_timkiem == null) {
        this.$toast.error('Vui lòng nhập mã số thuế hoặc chọn mã thuê bao ')
      } else {
        await this.Lay_TT_HDDT_Theo_MST(this.data_common.ma_timkiem)
        this.LaySL_HoaDon_DaMua()
        this.LaySl_HoaDon_DaSD()
        this.LaySl_HoaDon_ChuyenDoi()
        this.LaySl_HoaDon_Nhan()
        await this.Lay_DS_ThueBao_Theo_MST(this.data_common.ma_timkiem)
      }
    }
  }
}
</script>
