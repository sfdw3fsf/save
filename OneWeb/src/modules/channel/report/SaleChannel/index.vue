<template src="./index.html"></template>
<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import Select2Ext from '@/modules/contract/setup/DeclareLandline/components/Select2Ext'
import DataGridCustom from '@/modules/contract/setup/DeclareLandline/components/DataGridCustom'
import VueDateExt from '@/modules/contract/setup/DeclareLandline/components/VueDateExt'
import downloadexcel from '@/modules/report/BI/JsonExcel'
import moment from 'moment'

export default {
  data() {
    return {
      dataExport: [],
      fieldExport: {
        'Thời gian' : "thang" ,
        'Đơn vị' : "ten_dv",
        'Kênh bán' : "ten_kenhban" ,
        'Nội dung' : "noidung" ,
        'Công nợ đầu kỳ' : "congno_dauky" ,
        'Công nợ phát sinh' : "congno_phatsinh" ,
        'Đã chi trả' : "tientra" ,
        'Còn nợ' : "con_no" 
      }
    }
  },
  components: {
    breadcrumb,
    Select2Ext,
    DataGridCustom,
    VueDateExt,
    downloadexcel
  },
  mounted() {
    this.form_load()
  },
  methods: {
    async form_load() {
      this.loading(true)
      try {
        let dsDonvi = await this.axios.post('web-khdn/lichsuchitra/dsDonVi', {})
        this.$refs.cboDonVi.dataSource = dsDonvi.data ? dsDonvi.data.data : []
        this.$refs.cboDonVi.dataTextField = 'ten_dv'
        this.$refs.cboDonVi.dataValueField = 'donvi_id'

        let dsKenhban = await this.axios.post('web-khdn/lichsuchitra/dsKenhBan', {})
        this.$refs.cboKenhBan.dataSource = dsKenhban.data ? dsKenhban.data.data : []
        this.$refs.cboKenhBan.dataTextField = 'ten_kb'
        this.$refs.cboKenhBan.dataValueField = 'nhanvien_id'
      } catch (err) {}
      this.loading(false)
    },
    async tsbtnXemSoLieu_Click() {
      this.loading(true)
      try {
        let data = await this.axios.post('/web-khdn/lichsuchitra/dsLichSuChiTra', {
          "donvi_id":this.$refs.cboDonVi.value,
          "kenhban_id":this.$refs.cboKenhBan.value,
          "thang": this.$refs.txtThoiGianTu.value ? this.$refs.txtThoiGianTu.value : moment().format("YYYYMM")
        //   kenhban_id: '1',
        //   donvi_id: '2973',
        //   thang: '202307'
        })
        this.$refs.dtGrid.dataSource = data.data.data
        this.dataExport = data.data.data
      } catch (err) {
        this.$refs.dtGrid.dataSource = []
        console.log(err)
      }
      this.loading(false)
    },
    tsbtnExport_Click() {
      console.log('Xuất excel')
    }
  }
}
</script>