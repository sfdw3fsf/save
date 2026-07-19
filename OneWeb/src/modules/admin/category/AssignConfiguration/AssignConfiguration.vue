<template src="./template.html"> </template>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import {Icon} from '@iconify/vue2'
import api from './API.js'
export default {
  components: {
    breadcrumb, Icon
  },
  data() {
    return {
      cbbLoaiKH: {
        options: [],
        value: null
      },
      cbbNguonDon: {
        options: [],
        value: null
      },
      cbbDichVuVT: {
        options: [],
        value: null
      },
      cbbLoaiHinh: {
        options: [],
        value: null
      },
      cbbTinh: {
        options: [],
        value: null
      },
      cbbQuyTrinh: {
        options: [],
        value: null
      },
      cbbUuTien: {
        options: [],
        value: null
      },
      tbCauhinh: {
        columns: [
          {
            headerText: 'STT',
            fieldName: 'stt',
            width: '70'
          },
          {
            headerText: 'Tỉnh/TP',
            fieldName: 'tentinh',
            width: '160'
          },
          {
            headerText: 'Quy trình',
            fieldName: 'quytrinh',
            width: '350'
          },
          {
            headerText: 'Tên loại KH',
            fieldName: 'ten_loaikh',
            width: '250'
          },
          {
            headerText: 'Tên Ưu tiên',
            fieldName: 'ten_uutien',
            width: '400'
          },
          {
            headerText: 'SLA Phân đơn',
            fieldName: 'sla_phandon',
            width: '150'
          }
        ],
        data: []
      },
      setQuytrinh_id: null,
      setLoaihinh_id: [],
      dichvuInit: [],
      sla: '',
      btnSaveDisabled: true,
      currentRow: {},
      p_nhanvien_id: null,
      p_phanvung_id: null
    }
  },
  methods: {
    async get_combo() {
      try {
        this.loading(true)
        //get nhanvien_id

        // check nhân viên vnp
        const check = await this.axios.post('/web-tracuu/tracuu/fn_check_nhanvien_vnp', {
          p_nhanvien_id: this.p_nhanvien_id
        })

        // ========Danh sách dịch vụ===============
        let dsdichvu = await this.axios.get('/web-thicong/quanlydonhang/dsDichVu')
        if (dsdichvu.data.error_code == 'BSS-00000000') {
          this.cbbDichVuVT.options = dsdichvu.data.data.map((item) => {
            return {
              id: item.dichvuvt_id,
              text: item.ten_dvvt
            }
          })
          this.cbbDichVuVT.value = this.cbbDichVuVT.options.find((item) => item.text === 'Di động').id
        }
        let cbbTinh = []
        // ========Danh sách tỉnh/TP===============
        let dsTinh = await this.axios.get('/web-quantri/danhmuc-chung/CSS_TINH')
        if (dsTinh.data.error_code == 'BSS-00000000') {
          cbbTinh = dsTinh.data.data
            .filter((item) => item.TINH_ID <= 66)
            .map((item) => {
              return {
                id: item.TINH_ID,
                text: item.TENTINH,
                phanvung_id: item.PHANVUNG_ID
              }
            })
          if (!check || !check.data.data || check.data.data == 0) {
            this.cbbTinh.options = cbbTinh.filter((item) => item.phanvung_id == this.p_phanvung_id)
          }
          else if (check.data.data == 1) {
            this.cbbTinh.options = [...cbbTinh]
          }
          console.log(this.p_phanvung_id,cbbTinh,this.cbbTinh.options);
          if(this.cbbTinh.options.length > 0) {
            this.cbbTinh.value = this.cbbTinh.options[0].id
          }
        }

        // ========Danh sách loại khách hàng===============
        let dsLoaiKH = await this.axios.post('/web-thicong/quanlydonhang/sp_lay_ds_loai_kh', {})
        if (dsLoaiKH.data.error_code == 'BSS-00000000') {
          this.cbbLoaiKH.options = dsLoaiKH.data.data.map((item) => {
            return {
              id: item.loaikh_id,
              text: item.loai_kh
            }
          })
          this.cbbLoaiKH.value = this.cbbLoaiKH.options[0].id

          // ========Danh sách nguồn đơn===============
          let dsNguonDon = await this.axios.post('/web-thicong/quanlydonhang/sp_lay_ds_nguon_don', {})
          if (dsNguonDon.data.error_code == 'BSS-00000000') {
            this.cbbNguonDon.options = dsNguonDon.data.data.map((item) => {
              return {
                id: item.ungdung_id,
                label: item.ten_ungdung
              }
            })
            this.cbbNguonDon.value = [this.cbbNguonDon.options[0].id]
          }

          //   // ========Danh sách ưu tiên===============
          let uutien = await this.axios.get('/web-hopdong/quanlydonhang/sp_combo_uutien')
          if (uutien.data.error_code == 'BSS-00000000') {
            this.cbbUuTien.options = uutien.data.data.map((item) => {
              return {
                id: item.id,
                text: item.name
              }
            })
            this.cbbUuTien.value = this.cbbUuTien.options[0].id
          }

          // ========Danh sách cấu hình===============
          this.tbCauhinh.data = []
          let res = await this.axios.post('/web-tracuu/tracuu/sp_lay_ds_cauhinh', {
            p_nhanvien_id: this.p_nhanvien_id
          })
          if (res.data.error_code == 'BSS-00000000') {
            this.tbCauhinh.data = res.data.data.map((item, index) => {
              return {
                stt: index + 1,
                ...item
              }
            })
          }
          let api = await this.axios.get(`/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB`)
          if (api.data.error_code === 'BSS-00000000') {
            this.dichvuInit = api.data.data
          }
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    async selectRowCauHinh(row) {
      console.log(row.data)
      this.cbbTinh.value = row.data.phanvung_id
      this.setQuytrinh_id = row.data.quytrinh_id
      this.cbbLoaiKH.value = row.data.khdn
      this.cbbUuTien.value = row.data.uutien
      this.sla = row.data.sla_phandon
      this.cbbNguonDon.value = []
      this.cbbLoaiHinh.value = []
      let dsnguondon = await this.axios.post('/web-hopdong/quanlydonhang/sp_lay_ds_nguondon_theo_cauhinh', {
        p_quytrinh_id: row.data.quytrinh_id,
        p_khdn: row.data.khdn,
        p_uutien: row.data.uutien,
        p_phanvung_id: row.data.phanvung_id
      })
      if (dsnguondon.data.data) {
        dsnguondon.data.data.forEach((item) => {
          this.cbbNguonDon.value.push(item.ungdung_id)
        })
      }
      let dsloaihinh = await this.axios.post('/web-hopdong/quanlydonhang/sp_lay_ds_loaihinh_theo_cauhinh', {
        p_quytrinh_id: row.data.quytrinh_id,
        p_khdn: row.data.khdn,
        p_uutien: row.data.uutien,
        p_phanvung_id: row.data.phanvung_id
      })
      if (dsloaihinh.data.data) {
        const id = dsloaihinh.data.data[0].loaitb_id
        const dichvuvt_id = this.dichvuInit.find((item) => item.LOAITB_ID === id).DICHVUVT_ID
        this.cbbDichVuVT.value = dichvuvt_id
        dsloaihinh.data.data.forEach((item) => {
          this.cbbLoaiHinh.value.push(item.loaitb_id)
        })
      }
      this.cbbQuyTrinh.value = row.data.quytrinh_id
      this.btnSaveDisabled = false
    },
    test() {
      console.log('start')
    },
    create_cauhinh(){
      this.cbbTinh.value = this.cbbTinh.options[0].id
      this.cbbQuyTrinh.value = this.cbbQuyTrinh.options[0].id
      this.cbbLoaiKH.value = this.cbbLoaiKH.options[0].id
      this.cbbUuTien.value = this.cbbUuTien.options[0].id
      this.cbbNguonDon.value = []
      this.cbbLoaiHinh.value = []
      this.sla = ''
      this.btnSaveDisabled = false
    },
    async changeDichvu() {
      this.cbbLoaiHinh.value = []
      let res = await api.getDSLoaiHinh(this.axios)
      if (res.data.error_code == 'BSS-00000000') {
        this.cbbLoaiHinh.options = res.data.data.map((item) => {
          return {
            id: item.loaitb_id,
            label: item.loaihinh_tb
          }
        })
        // this.cbbLoaiHinh.value = [this.cbbLoaiHinh.options[0].id]
      }
    },
    async update_cauhinh() {
      if(this.cbbNguonDon.value.length == 0 && this.cbbLoaiHinh.value.length == 0){
        let del = await this.axios.post('/web-tracuu/tracuu/fn_xoa_cauhinh_donhang', {
          p_phanvung_id: null,
          p_quytrinh_id: this.cbbQuyTrinh.value,
          p_khdn: this.cbbLoaiKH.value,
          p_uutien: this.cbbUuTien.value
        })
        if(del.data.data && del.data.data === 'OK'){
          this.$toast.success('Xóa cấu hình thành công')
          this.get_combo()
          return
        }
        else {
          this.$toast.error('Xóa cấu hình thất bại:' + del.data.message)
          return
        }
      }
      if (!this.cbbNguonDon.value || this.cbbNguonDon.value.length == 0) {
        this.$toast.error('Chưa chọn nguồn đơn')
        return
      }
      if (!this.cbbLoaiHinh.value || this.cbbLoaiHinh.value.length == 0) {
        this.$toast.error('Chưa chọn loại hình')
        return
      }
      if (!this.cbbQuyTrinh.value) {
        this.$toast.error('Chưa chọn quy trình')
        return
      }
      if (!this.sla || this.sla == '' || this.sla < 0) {
        this.$toast.error('Chưa nhập SLA Phân đơn!')
        return
      }
      const check = this.cbbQuyTrinh.options.find((item) => item.id == this.cbbQuyTrinh.value)
      if (!check) {
        this.$toast.error('Vui lòng chọn quy trình !')
        return
      }
      const tt = []
      for (const nd in this.cbbNguonDon.value) {
        for (const lh in this.cbbLoaiHinh.value) {
          tt.push({
            ungdung_id: this.cbbNguonDon.value[nd],
            loaitb_id: this.cbbLoaiHinh.value[lh]
          })
        }
      }
      let res = await this.axios.post('/web-hopdong/quanlydonhang/fn_capnhat_cauhinh_donhang', {
        p_phanvung_id: this.cbbTinh.value,
        p_quytrinh_id: this.cbbQuyTrinh.value,
        p_khdn: this.cbbLoaiKH.value,
        p_uutien: this.cbbUuTien.value,
        p_sla: this.sla,
        p_json_tt: JSON.stringify(tt)
      })
      if (res.data.data === 'OK') {
        this.$toast.success('Cập nhật thành công')
        this.currentRow.uutien = this.cbbUuTien.value
        this.currentRow.khdn = this.cbbLoaiKH.value
        this.currentRow.sla_phandon = this.sla
        this.currentRow.quytrinh_id = this.cbbQuyTrinh.value
        this.currentRow.phanvung_id = this.cbbTinh.value
        await this.get_combo()
        const index = this.tbCauhinh.data.findIndex((item) => item.quytrinh_id == this.currentRow.quytrinh_id && item.khdn == this.currentRow.khdn && item.uutien == this.currentRow.uutien && item.phanvung_id == this.currentRow.phanvung_id)
        this.$refs.tbCauhinh.selectRow(index)
      } else {
        if (res.data.message.includes('value larger than')) this.$toast.error('SLA Phân đơn quá lớn! Vui lòng nhập lại!')
        else this.$toast.error('Cập nhật thất bại: ' + res.data.message)
      }
    }
  },

  async mounted() {
    ////get nhanvien_id
    const data = localStorage.getItem('OneBSS-Client')
    this.p_phanvung_id = JSON.parse(data).phanvung_id
    this.p_nhanvien_id = JSON.parse(data).id_nhanvien
    this.get_combo()
    // this.get_dsdonvi()
  },
  watch: {
    'cbbDichVuVT.value': async function(val) {
      // this.cbbLoaiHinh.value = []
      let res = await api.getDSLoaiHinh(this.axios, val)
      if (res.data.error_code == 'BSS-00000000') {
        this.cbbLoaiHinh.options = res.data.data.map((item) => {
          return {
            id: item.loaitb_id,
            label: item.loaihinh_tb
          }
        })
        // this.cbbLoaiHinh.value = [this.cbbLoaiHinh.options[0].id]
      }
    },
    // 'currentRow': function(val) {
    //   if (val!= {}) {
    //     this.btnSaveDisabled = false
    //   }
    // },
    'cbbTinh.value': async function(val) {
      let res = await this.axios.post('/web-hopdong/quanlydonhang/sp_lay_quytrinh_theo_tinh', {
        p_phanvung_id: val
      })
      if (res.data.error_code === 'BSS-00000000') {
        this.cbbQuyTrinh.options = res.data.data.map((item) => {
          return {
            id: item.quytrinh_id,
            text: item.quytrinh
          }
        })
        this.cbbQuyTrinh.value = this.setQuytrinh_id || this.cbbQuyTrinh.options[0].id
      }
    }
  }
}
</script>

<style lang="scss" scoped src="./style.scss"></style>
