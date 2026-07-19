<template>
  <div class="main-wrapper">
    <breadcrumb :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li @click="lookupVisa">
          <a style="cursor: pointer;">
            <span class="icon one-file-attach"></span> Tra cứu (F3)
          </a>
        </li>
        <li :class="{disabled: dataSources.length==0}">
          <a style="cursor: pointer;">
            <download-excel name="danh-sach-khach-hang" :data="dataSources" >
              <span class="icon one-xlsx-import"></span> Xuất excel
            </download-excel>
          </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <ThongTinTraCuu
        @onCusinfoVdcChange="onCusinfoVdcChange"
        @onOperationVdcChange="onOperationVdcChange"
        @onChangeValue="onChangeValue"
       />
      <KetQuaTimKiem :dataSources="dataSources" />
    </div>
    <Footer />
  </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ThongTinTraCuu from './ThongTinTraCuu.vue'
import KetQuaTimKiem from './KetQuaTimKiem.vue'
import PrefixOfCountyAPI from './PrefixOfCountyAPI'

export default {
  name: "PrefixOfCounty",
  components: {
    ThongTinTraCuu,
    KetQuaTimKiem,
    Footer,
    breadcrumb
  },

  data() {
    return {

      header: {
        title: "Thống kê khách hàng visa theo thông tin khách hàng",
        list: [
          { name: "Lập hợp đồng", 
          link: { name: "Ui.cards" },
          active: false
          },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" },
            active: true
          },
        ],
      },
      dataSources:[],
      tracuuObject:{
        key: '',
        opt: '',
        search_term: ''
      }
    };
  },
  methods:{
      async lookupVisa(){
        try{
          this.loading(true)
          let response=await PrefixOfCountyAPI.tracuu_visa_thongtin_khachhang(this.axios, this.tracuuObject)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.dataSources=response.data.data
          }else{
            this.dataSources=[]
            //this.initDataTest()
          }
        }catch(e){
          this.$toast.error('Không load được dữ liệu')
        }finally{
           this.loading(false)
        }      
      },
      onCusinfoVdcChange(value){
          this.tracuuObject.key=value
      },
      onOperationVdcChange(value){
          this.tracuuObject.opt=value
      },
      onChangeValue(value){
          this.tracuuObject.search_term=value.trim()
      },

      initDataTest(){
        let tentaikhoan=[
          'minhtuy485',
          'minhtuytieu',
          'tieunhi90',
          'manhthan12',
          'trungnhattg',
          'manhmelen',
          'dauthuong',
          'khoaimon',
        ]
        let phones=[
          '0903248743',
          '0340348763',
          '0843248743',
          '0734294593',
          '0278432754',
          '0378324944',
          '0832849549',
        ]
        let loaitaikhoan =[
          'Vip',
          'Guest',
          'Pro'
        ]
        let trangthai=[
          'Hoạt động',
          'Tạm ngưng',
          'Huỷ'
        ]
        for (var i = 1; i <= 600; i++) {
          
          this.dataSources.push({
            TEN_TAIKHOAN:tentaikhoan[Math.floor(Math.random()*tentaikhoan.length)],
            TEN_DANGKY:tentaikhoan[Math.floor(Math.random()*tentaikhoan.length)],
            DIACHI:'Số 1 võ văn ngân '+tentaikhoan[Math.floor(Math.random()*tentaikhoan.length)],
            LOAI_TAIKHOAN:loaitaikhoan[Math.floor(Math.random()*loaitaikhoan.length)],
            SDT:phones[Math.floor(Math.random()*phones.length)],
            THU_DIENTU:tentaikhoan[Math.floor(Math.random()*tentaikhoan.length)]+'@gmail.com',
            TRANG_THAI:trangthai[Math.floor(Math.random()*trangthai.length)],
            NGAY_TAO:'23/4/2021'
          })
        
        }
      }
  },
  created(){
    //this.initDataTest()
  }
  
};
</script>
<style scoped>
.disabled{
    pointer-events:none !important;
    opacity:0.7 !important;
}
</style>