<template src="./index.html">
</template>
<script>
export default {
  name: "ChiTietKetNoiVFI",
  props:{
    id:String,
    ma_tk:String,
    tag:String,
  },
  data:function (){
    return {
      vma_tk:"",
      vkieu:0,
      dt_sc:[],
      dt_sp:[],
      dsRouterPE:[],
      dsLoaiCap:[],

      txtVRF:null,
      isEnabledTxtVFR:true,

      cboRouterChinh:null,
      cboRouterPhu:null,
      cboLoaiCap_Chinh:null,
      cboLoaiCap_Phu:null,

      txtSlotPE_Phu:null,
      txtPortPE_Phu:null,
      txtDSLAMWan_Phu:null,
      txtTramTB_Phu:null,
      txtTenTbi_Phu:null,
      txtSvLan_Phu:null,
      txtCvLan_Phu:null,
      txtVRF_Phu:null,
      txtPort_Phu:null,
      txtRoute_Phu:null,
      txtLink_Phu:null,
      txtSerial_Phu:null,
      txtLAN_IP_Phu:null,
      txtWAN_IP_Phu:null,

      txtHopCapNgon_Phu:null,
      txtTuCapGoc_Phu:null,
      txtTuCapNgon_Phu:null,
      txtTuyenCap_Phu:null,
      txtDoiCapGoc_Phu:null,
      txtDoiCapNgon_Phu:null,
      txtLienTu_Phu:null,
      txtCapGoc_Phu:null,
      txtCapNgon_Phu:null,
      txtSoMetDay_Phu:null,

      txtSlotPE_Chinh:null,
      txtPortPE_Chinh:null,
      txtDSLAMWan_Chinh:null,
      txtTramTB_Chinh:null,
      txtTenTbi_Chinh:null,
      txtSvLan_Chinh:null,
      txtCvLan_Chinh:null,
      txtVRF_Chinh:null,
      txtPort_Chinh:null,
      txtRoute_Chinh:null,
      txtLink_Chinh:null,
      txtSerial_Chinh:null,
      txtLAN_IP_Chinh:null,
      txtWAN_IP_Chinh:null,

      txtHopCapNgon_Chinh:null,
      txtTuCapGoc_Chinh:null,
      txtTuCapNgon_Chinh:null,
      txtTuyenCap_Chinh:null,
      txtDoiCapGoc_Chinh:null,
      txtDoiCapNgon_Chinh:null,
      txtLienTu_Chinh:null,
      txtCapGoc_Chinh:null,
      txtCapNgon_Chinh:null,
      txtSoMetDay_Chinh:null,

    }
  },
  methods:{
    onHiddenModal(){

    },
    async onShownModal() {
      try {
        this.loading(true)
        this.isEnabledTxtVFR = true
        //Combo Router PE Chính, Phụ
        this.dsRouterPE = await this.comboRouterPE()
        this.cboRouterChinh = this.dsRouterPE.length > 0 ? this.dsRouterPE[0].pe_id : null
        this.cboRouterPhu = this.dsRouterPE.length > 0 ? this.dsRouterPE[0].pe_id : null
        // Combo Loại cáp Chính, Phụ
        this.dsLoaiCap = await this.comboLoaiCap()
        this.cboLoaiCap_Chinh = this.dsLoaiCap.length > 0 ? this.dsLoaiCap[0].loaicap_id : null
        this.cboLoaiCap_Phu = this.dsLoaiCap.length > 0 ? this.dsLoaiCap[0].loaicap_id : null

        this.txtVRF = this.vma_tk
        await this.HTDS_SoChinh()
        await this.HTDS_SoPhu()
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    HTChiTiet_SoPhu(item){
      if (item.pe_id)
        this.cboRouterPhu = item.pe_id
      this.txtSlotPE_Phu = item.slot_pe
      this.txtPortPE_Phu = item.port_pe
      this.txtDSLAMWan_Phu = item.dslam
      this.txtTramTB_Phu = item.tram_tbi
      this.txtTenTbi_Phu = item.ten_tbi
      this.txtSvLan_Phu = item.svlan
      this.txtCvLan_Phu = item.cvlan
      this.txtVRF_Phu = item.vrf
      this.txtPort_Phu = item.port
      this.txtRoute_Phu = item.route
      this.txtLink_Phu = item.link
      this.txtSerial_Phu = item.serial
      this.txtLAN_IP_Phu = item.lan_ip
      this.txtWAN_IP_Phu = item.wan_ip
      if (item.loaicap_id)
        this.cboLoaiCap_Phu = item.loaicap_id
      this.txtHopCapNgon_Phu = item.hopcap_p
      this.txtTuCapGoc_Phu = item.tucap_goc
      this.txtTuCapNgon_Phu = item.tucap_p
      this.txtTuyenCap_Phu = item.tuyencap
      this.txtDoiCapGoc_Phu = item.doicap_goc
      this.txtDoiCapNgon_Phu = item.doicap_p
      this.txtLienTu_Phu = item.lientu
      this.txtCapGoc_Phu = item.cap_goc
      this.txtCapNgon_Phu = item.cap_p
      this.txtSoMetDay_Phu = item.culy
    },
    HTChiTiet_SoChinh(item){
      if (item.pe_id)
        this.cboRouterChinh = item.pe_id
      this.txtSlotPE_Chinh = item.slot_pe
      this.txtPortPE_Chinh = item.port_pe
      this.txtDSLAMWan_Chinh = item.dslam
      this.txtTramTB_Chinh = item.tram_tbi
      this.txtTenTbi_Chinh = item.ten_tbi
      this.txtSvLan_Chinh = item.svlan
      this.txtCvLan_Chinh = item.cvlan
      this.txtVRF_Chinh = item.vrf
      this.txtPort_Chinh = item.port
      this.txtRoute_Chinh = item.route
      this.txtLink_Chinh = item.link
      this.txtSerial_Chinh = item.serial
      this.txtLAN_IP_Chinh = item.lan_ip
      this.txtWAN_IP_Chinh = item.wan_ip
      if (item.loaicap_id)
        this.cboLoaiCap_Chinh = item.loaicap_id
      this.txtHopCapNgon_Chinh = item.hopcap_p
      this.txtTuCapGoc_Chinh = item.tucap_goc
      this.txtTuCapNgon_Chinh = item.tucap_p
      this.txtTuyenCap_Chinh = item.tuyencap
      this.txtDoiCapGoc_Chinh = item.doicap_goc
      this.txtDoiCapNgon_Chinh = item.doicap_p
      this.txtLienTu_Chinh = item.lientu
      this.txtCapGoc_Chinh = item.cap_goc
      this.txtCapNgon_Chinh = item.cap_p
      this.txtSoMetDay_Chinh = item.culy
    },
    async tsbtnLayTT_Click() {
      try {
        this.loading(true)
        await this.HTDS_SoChinh()
        await this.HTDS_SoPhu()
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    async HTDS_SoChinh() {
      try {
        if (this.txtVRF) {
          this.dt_sc = await this.getDsMGWAN_VRF(this.txtVRF.trim(), 2)
        }else this.dt_sc = []
      } catch (e) {
        console.log(e)
      }
    },
    async HTDS_SoPhu(){
      try {
        if (this.txtVRF) {
          this.dt_sp = await this.getDsMGWAN_VRF(this.txtVRF.trim(), 1)
        }else this.dt_sp = []
      } catch (e) {
        console.log(e)
      }
    },
    async getDsMGWAN_VRF(vrf, kieu){
      let rs = await this.$root.context.get("web-quantri/chitiet-ketnoi-vfi/lay-ds-mgwan-vrf",{
        vrf:vrf,
        kieu:kieu,
      })
      return rs.data
    },
    async comboRouterPE(){
      let rs = await this.$root.context.get("web-quantri/chitiet-ketnoi-vfi/combo-router-pe")
      return rs.data
    },
    async comboLoaiCap(){
      let rs = await this.$root.context.get("web-quantri/chitiet-ketnoi-vfi/combo-loaicap")
      return rs.data
    }
  }
}
</script>

<style>
@media (min-width: 1200px){
  .modal-xl {
    max-width: 90% !important;
  }
}
</style>
