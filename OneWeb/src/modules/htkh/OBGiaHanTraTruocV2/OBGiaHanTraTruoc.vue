<template src="./template.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb';
import moment from 'moment'
import { Icon } from '@iconify/vue2'
import ThongTinKHAutoCall from './components/OBGHTT/AutoCall/MediaAutoCall.vue'
import ThongTinKetQuaOB from './components/OBGHTT/ThongTinKetQuaOB.vue'
import ThongTinTBKH from './components/OBGHTT/ThongTinTBKH/index.vue'
import apiHelper from './api.helper'
import util from './util'
import _ from 'lodash'
import api from './API.js'
import handleResultOBManger from './ob.statery.pattern'
export default {
  components: {
    ThongTinTBKH ,
    ThongTinKHAutoCall,
    ThongTinKetQuaOB,
    breadcrumb,
    Icon
  },
  data() {
    return {
      header: {
        title: 'Gia Hạn Trả Trước - V2',
        list: [{ name: 'Gia Hạn Trả Trước - V2', link: { name: 'Ui.cards' } }]
      },
      actions: [
        { id: 'btnRefresh', icon: 'icon one-reload', name: 'Lấy Thông Tin', visible: true, enable: true },
        { id: 'btnSave', icon: 'icon one-save', name: 'Ghi lại', visible: true, enable: true },
      ],
      autoCall: 0,
      thang_kt: '',
      soTB: 0,

      //checking
      selected_item: {
        KHACHHANG_ID: 0,
        THUEBAO_ID: 0,
        MA_TB: "",
        MA_KH: "",
        NGAY_KTDC:"",
      },
      querySate: '',
      //update 2/7/2024
      tab_tb_kh_active: 0,
      phanvung_kh_id: 0,
      user_ipcc: "",
      obId: 0,
      khachhang_ids: "",
      cbKqOBValue: 0,

    }
  },
  methods: {

    async onClickAction(id) {
      switch (id) {
        case 'btnSave':
          await this.btnCapNhat_Click()
          break
        case 'btnRefresh':
          await this.btnRefresh_Click()
          break
        default:
          console.log('click Actions')
          break
      }
    },

    async btnRefresh_Click(){
      try {
        this.loading(true)
        if(this.tab_tb_kh_active == 1){
          await this.$refs.refThongTinTBKhachHang.getListThueBao_TraTruoc()
        }
        if(this.tab_tb_kh_active == 2){
          await this.$refs.refThongTinTBKhachHang.getListThueBao_TraSau()
        }
        if(this.tab_tb_kh_active == 3){
          await this.$refs.refThongTinTBKhachHang.getListDS_HD_Chua_ThanhToan()
        }
        await this.$refs.refThongTinKhachHangAutoCallComponent.getCustomerInfo(this.phanvung_kh_id, this.selected_item.KHACHHANG_ID)
       
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    
    async btnCapNhat_Click() {
      if(this.tab_tb_kh_active == 3) return;

      try {
        this.loading(true)
        const listThueBaoSelected =  this.$refs.refThongTinTBKhachHang.getListThueBaoSlected();
        await this.$refs.refThongTinKhachHangAutoCallComponent.UpdateResultOB(_.unionBy(listThueBaoSelected,"THUEBAO_ID") )
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
        await this.$refs.refThongTinChiTietOBlComponent.getListResultOB(this.phanvung_kh_id, this.khachhang_ids)
      }
    },

    async checkUserLoginIPCC(vphanvung_id, vuser_ipcc) {
      
      const logOut = (sef) => {
        sef.$root.context.logOut();
        sessionStorage.setItem('currentPath', `/htkh/OBGiaHanTraTruoc?Ref=${sef.querySate}`);
        sef.$router.push({ path: '/auth/login' });
      };

      try {
        const response = await api.getUserMapIPccInfo(this.axios, { vphanvung_id, vuser_ipcc })

        const users = apiHelper.getDataFromResponseApiReturnArray(response)
        if (!users.length) {
          this.$toast.warning('Không tìm thấy account được phân công!')
          logOut(this)
          return
        }
        const user = users.find((e) => e.ma_nd === this.$root.token.getUserName());
        if (!user) {
          this.$toast.warning(`Tài khoản ${this.$root.token.getUserName()} không phải là tài khoản được phân công!`)
          logOut(this)
          return
        }
      } catch (error) {
        this.$toast.warning('Không tìm thấy account được phân công!')
        logOut(this)
        console.error(error);
      }
    },

    handleTabTBKHActive(activeTab){
      this.onSetButtonEnable('btnSave', false)
      if(!activeTab) return;
      this.tab_tb_kh_active = activeTab;
      if(activeTab == 1 || activeTab == 2){
        this.onSetButtonEnable('btnSave', true)
      }
    },

    handleSelectingTB(item){
      const data = item.data;
      console.log("selecting item", data)
      if(!data) return ;
      this.thang_kt = data.NGAY_KTDC
      this.selected_item = data;
    },

    handleKhachHangTraTruocChange(data){
      this.khachhang_ids = data.kh_ids
    },
    handleKetQuaOBChange(data){
      this.cbKqOBValue = data 
    },

    onSetButtonVisible (id, isVisible) {
      this.actions.find(item => item.id === id).visible = isVisible
    },
    onSetButtonEnable (id, isActive) {
      this.actions.find(item => item.id === id).enable = isActive
    },
  },

  created() {},
  async mounted() {
    try {
      const query = this.$route.query

      if (query.Ref) {
        this.querySate = query.Ref
        let data = util.decodeQuery(query.Ref)
        this.thang_kt = data.NGAY_KTDC
        this.autoCall = Number(data.auto_call)
        this.phanvung_kh_id = data.PHANVUNG_ID
        this.user_ipcc = data.user_ipcc;
        this.obId = data.OB_ID
        this.soTB = data.SoThueBao
        // this.soTB = "0889849368"    
        // this.phanvung_kh_id = 52; //fixing to test
        //check user IPCC
        //  this.checkUserLoginIPCC(data.PHANVUNG_ID, data.user_ipcc)
      }
    } catch (error) {
      console.log(error)
    } finally {
      this.loading(false)
    }
    
 

    
     
    
  },
  computed: {

  }
}
</script>

<style scoped lang="scss">
</style>
