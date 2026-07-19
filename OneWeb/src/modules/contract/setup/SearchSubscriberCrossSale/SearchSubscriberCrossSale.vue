<template src="./SearchSubscriberCrossSale.html"></template>
<style scoped src="./SearchSubscriberCrossSale.scss"></style>
<script>
import _ from 'lodash'
import breadcrumb from '@/components/breadcrumb'
import SearchAccountModal from './SearchAccountModal'
import api from './api'
// import MapChuQuanModal from './MapChuQuanModal.vue'
export default {
  name: 'SearchSubscriberCrossSale', 
  components: {
    breadcrumb,
    SearchAccountModal,
    // MapChuQuanModal,
  },
  mounted() {},
  data() {
    return {
      // ma_tb: '',
      chuQuanId: -1,
      DsChuQuan:[],
      header: {
        title: 'Tra cứu thuê bao bán chéo ',
        list: [
        { name: 'Tra cứu thuê bao bán chéo', link: { name: 'contract' } },
          {name:'Tra cứu thuê bao bán chéo',
            link:{name: 'Ui.buttons'},
          active:true,

          }
        ]
      },
      gridDanhSachTB: [],
      dsThueBao: {
        headers: [
          { fieldName: 'ma_tb_tinhban', headerText: 'Mã TB tỉnh bán', allowfiltering: true },
          { fieldName: 'tentinh_ban', headerText: 'Tỉnh bán', allowfiltering: true },
          { fieldName: 'ma_tb_tinhtc', headerText: 'Mã TB tỉnh thi công', allowfiltering: true },
          { fieldName: 'tentinh_tc', headerText: 'Tỉnh thi công', allowfiltering: true },
          { fieldName: 'diachi_tb', headerText: 'Địa chỉ', allowfiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình thuê bao', allowfiltering: true },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowfiltering: true },
        ],
        selectedIndex: 0,
        selectedItem: null,
        // checked: []
      },
      txtMaTB: { enabbled: false, value: '' }
    }
  },
  methods: {
    showPopupSearchAccount() {
      this.$refs.popupSearchAccount.showModal()
    },
    async onClickSearch() {
      try {
        this.loading(true)
        this.gridDanhSachTB=[]
        if(!this.txtMaTB.value){
          this.$toast.error("Chưa chọn mã thuê bao")
          return
        }

        let input ={
          p_ma_tb:this.txtMaTB.value
        }
        let kq=await api.tracuu_TB_BC(input)
        this.$toast.success("Tìm thấy "+ kq.data.data.length + " thuê bao")
        if (kq.data.data && kq.data.data.length) {
          this.gridDanhSachTB = kq.data.data 
         
          console.log('kq',kq.data)       
        } else {
          this.gridDanhSachTB = []
        }
         
      } catch (e) {
        console.log("err", e)
        if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }
      } finally {
        this.loading(false) 
      }     
    },
    mapThueBao() {
      this.$refs['MapChuQuanModal'].showModal()
    },
    async searchAccountModalClose(data) {
      this.txtMaTB.value = data.ma_tb
      this.onClickSearch()
     
    },
    
  }
} 
</script>
