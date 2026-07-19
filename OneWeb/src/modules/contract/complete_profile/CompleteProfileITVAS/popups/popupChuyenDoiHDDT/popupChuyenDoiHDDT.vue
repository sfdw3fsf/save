<template>
  <ejs-dialog :enableResize="true" :isModal="true" allowDragging="true" zIndex="1000" :width="width" :visible="false" ref="dialogChuyenDoiHDDT" header="Chuyển đổi hoá đơn điện tử sang máy tính tiền" showCloseIcon="true" target="#app .main-wrapper">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="btnCapNhat_Click" :class="{ disabled: !btnCapNhat }"> <span class="icon one-file-plus"></span> Cập Nhật </a>
        </li>
        <li>
          <a href="#" @click.prevent="btnGuiBack_Click" :class="{ disabled: !btnGuiBackend }"> <span class="icon one-calendar-sync"></span> Gửi Backend </a>
        </li>
        <li>
          <a href="#" @click.prevent="btnRefresh_Click" :class="{ disabled: !btnRefresh }"> <span class="icon one-refresh"></span> Refresh </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="row">
        <div class="box-form">
          <div class="legend-title">Thông tin khách hàng</div>
          <div class="info-row">
            <div class="key">Tổng số lượng hoá đơn đã mua</div>
            <div class="value">
              <input type="number" class="form-control" v-model="totalInvoicesBought" disabled/>
            </div>
          </div>
          <div class="info-row">
            <div class="key">Tổng số lượng hoá đơn đã sử dụng</div>
            <div class="value">
              <input type="number" class="form-control" v-model="totalInvoicesUsed"  disabled/>
            </div>
          </div>
          <div class="info-row">
            <div class="key">Tổng số lượng hoá đơn đã chuyển đổi</div>
            <div class="value">
              <input type="number" class="form-control" v-model="totalInvoicesChanged" disabled />
            </div>
          </div>
          <div class="info-row">
            <div class="key">Tổng số lượng hoá đơn có thể chuyển đổi</div>
            <div class="value">
              <input type="number" class="form-control" v-model="totalInvoicesCanChanged" disabled/>
            </div>
          </div>
          <div class="info-row">
            <div class="key">Số lượng chuyển đổi</div>
            <div class="value">
              <input type="number" class="form-control" v-model="totalChange" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>



<script>
import api from './api'

import Vue from 'vue'

export default {
  props: {
    hdtb_id: Number
  },
  components: {
    api
  },
  name: 'PopupChuyenDoiHDDT',
  data() {
    return {
      width: '600px',
      totalInvoicesBought: 0,
      totalInvoicesUsed: 0,
      totalInvoicesChanged: 0,
      totalInvoicesCanChanged: 0,
      totalChange: 0,
      // animationSettings: { effect: 'Zoom' },

      // hdtb_id: 0,
      btnCapNhat: false,
      btnGuiBackend: false,
      btnRefresh: false,

      status: 0,
      mst: '',
    }
  },
  created: async function () {},
  mounted: function () {},
  watch: {},
  methods: {
    dialogClose() {
      this.$refs['dialogChuyenDoiHDDT'].hide()
    },
    async openDialog(data) {
     
      if (!data || !data.hdtb_id) {
        return
      }
    
      this.hdtb_id = data.hdtb_id
      this.$refs['dialogChuyenDoiHDDT'].show();
      await this.pageLoad(this.hdtb_id);

    },
    async pageLoad(hdtb_id) {
      
      try{
        this.loading(true)
        let response = await api.fn_get_status_hdtb(this.axios, {"vhdtb_id": hdtb_id});
        await this.layThongTinDaDangKy();
        if (response.data.error == 200 || response.data.errorCode === 0) {
          this.status = response.data.data;
        }
       
      }catch(err){
        console.log(err)
      }
      this.loading(false)
      
    },
    async layThongTinDaDangKy(){
      const resTTKH = await api.lay_thong_tin_da_dang_ky(this.axios, this.hdtb_id)


      if (resTTKH && resTTKH.data  &&  resTTKH.data.data && resTTKH.data.data.length > 0) {

        const tb = resTTKH.data.data

        this.totalInvoicesBought = tb[0]["khonggian_ct"];
        this.totalInvoicesUsed = tb[0]["congsuat_ct"];
        this.totalInvoicesChanged = tb[0]["id_text"];
        this.totalInvoicesCanChanged = tb[0]["vitri_tren_ct"];
        this.totalChange = tb[0]["vitri_duoi_ct"];

        if (this.status == 6) {
          this.btnRefresh = false
          this.btnCapNhat = false
          this.btnGuiBackend = false
        } else {
          this.btnCapNhat = false
          this.btnGuiBackend = true
          this.btnRefresh = true
        }
      }
       
      else await this.layThongTinKhachHang(this.hdtb_id)
    },

    async layThongTinKhachHang(hdtb_id) {
      let check_hddt = false
      let sldachuyendoi = 0
      const tsdkmtHD = this.GetDataResponse(await api.lay_thongso_dkmua_them_hd(this.axios, hdtb_id))
      //
  
      if (tsdkmtHD && tsdkmtHD.length > 0) {
        this.mst = tsdkmtHD[0].taxcode
      }

      const resKHHDDT = this.GetDataResponse(await api.lay_thong_tin_KHHDDT(this.axios, this.mst))

      const { invoices_services } = resKHHDDT

      //không tim thây thong tin hoa don dien tu
      if (invoices_services.length == 0) {
        this.btnCapNhat = false
        this.btnGuiBackend = false
        this.btnRefresh = false
        return this.$toast.error('Không tìn thấy thông tin hóa đơn điện tử. Hãy liên hệ Admin để kiểm tra lại')
      }

      for (let i = 0; i < invoices_services.length; i++) {
        const invoice = invoices_services[i]
        if (invoice.service_type == 0) {
        //kiểm tra có converted không
        if (invoice.converted && invoice.converted.length > 0) {
         
            for (let j = 0; j < invoice.converted.length; j++) {
              sldachuyendoi = sldachuyendoi + invoice.converted[j].totalNumber
            }
          }
          this.totalInvoicesBought = invoice.totalNumber
          this.totalInvoicesUsed = invoice.totalPublished
          this.totalInvoicesChanged = sldachuyendoi
          this.totalInvoicesCanChanged = invoice.totalNumber - sldachuyendoi - invoice.totalPublished
          check_hddt = true
        }
      }
   
      if (!check_hddt) {
        this.btnCapNhat = false
        this.btnGuiBackend = false
        this.btnRefresh = false
        return this.$toast.warning('Không tìn thấy thông tin hóa đơn điện tử. Hãy liên hệ Admin để kiểm tra lại')
      } else {
        this.btnCapNhat = true
        this.btnGuiBackend = false
        this.btnRefresh = false
      }
    },

    async btnCapNhat_Click() {

      if (this.status == 6) {
        return this.$toast.warning('Thuê bao đã kích hoạt thành công, không được phép thực hiện chức năng')
      }
      const check = this.validateFormInput();

      if(!check) return;

      const jsonData = {
                HDTB_ID : this.hdtb_id,
                MST_CT : this.mst,
                SL_TONG : this.totalInvoicesBought,
                SL_CONLAI : this.totalInvoicesCanChanged ,
                SL_DASD : this.totalInvoicesUsed ,
                SL_CHUYENDOI : this.totalChange ,
                SL_DACHUYENDOI : this.totalInvoicesChanged
      }
      
    try {
      this.loading(true);
      const res = this.GetDataResponse(await api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: [jsonData] }))
      

      this.loading(false)
      //todo check res
      if(res == "ok"){
        this.$toast.success("Cập nhật thông tin chuyển đổi hóa đơn thành công. Hãy nhấn nút gửi Backend để đồng bộ với hệ thống HDDT");
          this.btnGuiBackend = true;
          this.btnRefresh = true;
          this.btnCapNhat = false;
      }else{
        this.$toast.error("Cập nhật thông tin chuyển đổi số lượng hóa đơn không thành công.");
        this.btnGuiBackend = false;
        this.btnRefresh = false;
        this.btnCapNhat = true;
      }
    }catch(err){
      this.loading(false)
       this.$toast.error("Cập nhật thông tin chuyển đổi số lượng hóa đơn không thành công.");
    }
      

    },
    async btnGuiBack_Click() {
      if (this.status == 6) {
        return this.$toast.warning('Thuê bao đã kích hoạt thành công, không được phép thực hiện chức năng')
      }
      let jsondata = this.GetDataResponse(await api.tao_req_cntt(this.axios, this.hdtb_id));
      try{
        this.loading(true)
        const resMTHD = await api.MuaThemHoaDon(this.axios, jsondata)
        this.loading(false)
      //neu mua khong thanh ong
        console.log(resMTHD)
      if (resMTHD.data.errorCode == 1  ) {
       
        return this.$toast.error('Kích hoạt không thành công: ' + 'Lỗi: '+ resMTHD.faultCode)
      }
      

      const res = this.GetDataResponse(await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.hdtb_id, vstatus: 6 }))
      
      console.log(res)
      if(res == 'ok'){
        this.btnRefresh = false;
        this.loading(false);
        this.dialogClose()
        return this.$toast.success('Chuyển đổi hóa đơn thành công!');
      }

      return this.$toast.error('Kích hoạt không thành công: ' + '')

      }
      catch(err){
        console.log(err);
        this.loading(false)
        return this.$toast.error('Kích hoạt không thành công: ' + '')
      }
      
    },
    async btnRefresh_Click() {
      if (this.status == 6) {
        return this.$toast.warning('Thuê bao đã kích hoạt thành công, không được phép thực hiện chức năng')
      }
      try {
        this.loading(true)
        this.btnCapNhat = true
        this.btnGuiBackend = false
        this.btnRefresh = false
        this.totalChange = 0;
       
        await api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: null })
        await this.layThongTinKhachHang(this.hdtb_id);
        this.loading(false)
      } catch (err) {
        this.loading(false)
        return this.$toast.error('Có lỗi xảy ra ' + err.message)
      }
    },

    validateFormInput() {
      if (!this.totalChange) {
        this.$toast.warning('Chưa nhập số lượng hóa đơn chuyển đổi. Hãy nhập số lượng hóa đơn chuyển đổi')
        return false;
      }
      if (!this.totalChange >= this.totalInvoicesCanChanged) {
        this.$toast.warning('Số lượng hóa đơn chuyển đổi không được vượt quá số lượng hóa đớn cho phép chuyển đổi')
        return false
      }
      if (this.totalChange <= 0) {
        this.$toast.warning('Số lượng hóa đơn chuyển đổi phải lớn hơn 0.')
        return false
      }
      return true
    },

    GetDataResponse: function (response) {
      if (response.data.errorCode && response.data.errorCode != '' && response.data.errorCode != '0' && response.data.faultString && response.data.faultString != '') {
        throw response.data.faultString
      }

      if (response.data.error == 200 || response.data.errorCode === 0) {
        if (response.data.error_code === 'BSS-00000000') return response.data.data
        else if (response.data.errorCode === 0) return response.data.data
        else return null
      }
    }
  }
}
</script>
<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>