<template src="./template.html"></template>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import api from './API.js'
  export default {
    components: {
      breadcrumb
    },
    data() {
      return {
        nguoidung_id: '',
        phanvung_id: '',
        dichvuvt_options: [],
        loaihinhraw_options: [],
        dichvuvt_id: '',
        cbbLoaiHD: {
          enabled: true,
          value: '',
          options: []
        },
        expadedKetQua: true,
        cbbKieuLD: {
          enabled: false,
          value: '',
          options: []
        },
        cbbDichVu: {
          enabled: true,
          value: '',
          options: []
        },
        cbbLoaiHinh: {
          enabled: false,
          value: '',
        },
        cbbTocDo: {
          enabled: false,
          value: '',
          options: []
        },
        cbbNhomQuyTrinh: {
          enabled: false,
          value: '',
          options: []
        },
        tbDSQuyTrinh: {
          data: [],
          columns: [
            {fieldName: 'stt', headerText: 'STT', width: '50px'},
            {fieldName: 'quytrinh_id', headerText: 'ID', width: '50px'},
            {fieldName: 'ten_loaihd', headerText: 'Loại HĐ', width: '200px'},
            {fieldName: 'quytrinh', headerText: 'Quy trình'},
          ]
        },
        tbChiTietQuyTrinh: {
          data: [],
          columns: [
            {fieldName: 'stt', headerText: 'STT', width: '70px'},
            {fieldName: 'huonggiao', headerText: 'Hướng giao',isGroupedColumn: true},
            {fieldName: 'loaidv_nguon', headerText: 'Loại đơn vị nguồn'},
            {fieldName: 'loaidv_dich', headerText: 'Loại đơn vị đích'},
          ]
        }

      }
    },
    async mounted() {
      this.nguoidung_id = await this.$root.token.getNguoiDungID();
      this.phanvung_id = await this.$root.token.getPhanVungID();
      await this.load_data();
    },
    methods: {
      async load_data(){
        let rs = await api.ds3cbb(this.axios,{
          p_ds_para: JSON.stringify({
            NGUOIDUNG_ID: this.nguoidung_id,
          })
        })
        const dt = JSON.parse(rs.data.data).RESULT
        console.log(JSON.parse(rs.data.data))
        this.cbbDichVu.options = dt.DVVT.map(item => {
          return {
            id: item.DICHVUVT_ID,
            text: item.TEN_DVVT
          }
        });
        this.cbbDichVu.value = dt.DVVT[0].DICHVUVT_ID;
        this.loaihinhraw_options = dt.LOAI_TB.map(item => {
          return {
            id: item.LOAITB_ID,
            text: item.LOAIHINH_TB,
            dvvt: item.DICHVUVT_ID
          }
        });
        this.cbbLoaiHD.options = dt.LOAI_HD.map(item => {
          return {
            id: item.LOAIHD_ID,
            text: item.TEN_LOAIHD
          }
        });
        this.cbbNhomQuyTrinh.options = dt.NHOM_QT.map(item => {
          return {
            id: item.NHOM_QT_ID,
            text: item.TEN_NHOM
          }
        });
        this.cbbNhomQuyTrinh.value = this.cbbNhomQuyTrinh.options[0].id;
        this.cbbLoaiHD.options = this.cbbLoaiHD.options.sort((a,b) => a.text.split('-')[0] - b.text.split('-')[0])
        this.cbbLoaiHD.value = this.cbbLoaiHD.options[0].id;
        let res2 = await api.getDSKieuLD(this.axios);
        console.log(res2.data.data)
        this.cbbKieuLD.options = res2.data.data.map(item => {
          return {
            id: item.KIEULD_ID,
            text: item.TEN_KIEULD,
            loaihd_id: item.LOAIHD_ID
          }
        });
        // this.cbbKieuLD.options = this.cbbKieuLD.options.filter(item => item.loaihd_id == this.cbbLoaiHD.value)
        console.log(this.cbbLoaiHD.value);
        // this.cbbKieuLD.value = this.cbbKieuLD.options[0].id;
            },
        async timkiem_click(){
          if(!this.cbbDichVu.enabled && !this.cbbLoaiHinh.enabled && !this.cbbLoaiHD.enabled && !this.cbbKieuLD.enabled && !this.cbbTocDo.enabled){
            this.$toast.error('Vui lòng chọn ít nhất 1 điều kiện tìm kiếm')
            return
          }
          this.tbDSQuyTrinh.data = [];
          try {
            this.loading(true)
            let rs = await api.getDSQuyTrinh(this.axios, {
            p_phanvung_id: this.phanvung_id*1,
            p_dichvuvt_id: this.cbbDichVu.enabled?this.cbbDichVu.value*1:0,
            p_loaitb_id: this.cbbLoaiHinh.enabled&&this.cbbDichVu.enabled?this.cbbLoaiHinh.value*1:0,
            p_loaihd_id: this.cbbLoaiHD.enabled?this.cbbLoaiHD.value*1:0,
            p_kieuld_id:  this.cbbKieuLD.enabled&&this.cbbLoaiHD.enabled?this.cbbKieuLD.value*1:0,
            p_tocdo_id:   this.cbbTocDo.enabled&&this.cbbLoaiHinh.enabled&&this.cbbDichVu.enabled?this.cbbTocDo.value*1:0,
            p_nhom_qt_id: this.cbbNhomQuyTrinh.enabled?this.cbbNhomQuyTrinh.value*1:0,
          })
          this.tbDSQuyTrinh.data = rs.data.data;
          } catch (error) {
            
          } finally {
            this.loading(false)
          }                         
        },
        async dsQuyTrinh_rowSelected(row){
          this.tbChiTietQuyTrinh.data = [];
          console.log(row.data);
          let rs = await api.getChiTietQuyTrinh(this.axios, {
            p_quytrinh_id: row.data.quytrinh_id,
            p_phanvung_id: this.phanvung_id*1
          })
          if(rs.data.error_code="BSS-00000000" && rs.data.data.length > 0){
            this.tbChiTietQuyTrinh.data = rs.data.data.sort((a,b) => a.stt - b.stt)
          }
          else{
            this.$toast.error('Không có dữ liệu chi tiết quy trình')
          }
        },
    },
    computed: {
      loaihinh_options: function () {
        return this.loaihinhraw_options.filter(item => item.dvvt == this.cbbDichVu.value)
      },
      cbbKieuLD_options: function () {
        return this.cbbKieuLD.options.filter(item => item.loaihd_id == this.cbbLoaiHD.value)
      }
    },
    watch:{
      loaihinh_options: async function () {
        this.cbbLoaiHinh.value = this.loaihinh_options[0].id
        
      },
      cbbKieuLD_options: function () {
        this.cbbKieuLD.value = this.cbbKieuLD_options[0].id
      },
      'cbbLoaiHinh.value': async function () {
        let rs = await api.getDSTocDo(this.axios, {
          p_phanvung_id: this.phanvung_id*1,
     p_dichvuvt_id: this.cbbDichVu.value*1,
     p_loaitb_id: this.cbbLoaiHinh.value*1
        })
        this.cbbTocDo.options = rs.data.data.map(item => {
          return {
            id: item.tocdo_id,
            text: item.thuonghieu
          }
        })
        this.cbbTocDo.value = this.cbbTocDo.options[0].id
      },
    }
  }
</script>

<style lang="scss" scoped></style>
