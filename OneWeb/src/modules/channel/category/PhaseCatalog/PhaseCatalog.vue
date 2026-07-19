<template src="./PhaseCatalog.html"></template>
<style src="./PhaseCatalog.scss"></style>
<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import api from './API'
export default {
  data() {
    return {
      params: {
        ma_congdoan: null,
        last_selected_cd: null,
        congdoan_id: null,
        ten_congdoan: '',
        phase: null,
        ds_doituongban: [],
        doituongban: null,
        phase_options: [],
        channel: null,
        channel_options: [],
        status: 'Đang sử dụng',
        status_options: ['Đang sử dụng', 'Không sử dụng'],
        tyle_td: null,
        sotien: null,
        table_columns: [
          { fieldName: 'ma_congdoan', headerText: 'Mã công đoạn', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_congdoan', headerText: 'Tên công đoạn', allowFiltering: true, allowSorting: true },
          { fieldName: 'nhom_congdoan', headerText: 'Nhóm công đoạn', allowFiltering: true, allowSorting: true },
          { fieldName: 'kenh', headerText: 'Kênh bán', allowFiltering: true, allowSorting: true },
          { fieldName: 'tinhtrang', headerText: 'Tình trạng', allowFiltering: true, allowSorting: true }
        ],
        table_dataSource: [],
        seach_cd: {
          p_nhomcongdoan_id: '',
          p_macongdoan: '',
          p_tencongdoan: '',
          p_kenhban_id: '',
          p_doituongban_id: '',
          p_trangthai: ''
        }
      },
      buttonStatus: 'init'
    }
  },
  components: {
    breadcrumb
  },
  methods: {
    selectRowCD(row) {
      const r = row.data
      this.params.last_selected_cd = row;
      this.params.ma_congdoan = r.ma_congdoan
      this.params.ten_congdoan = r.ten_congdoan
      this.params.phase = r.nhomcd_id
      this.params.channel = r.kenhban_id
      this.params.doituongban = r.doituongban_id
      this.params.status = r.tinhtrang
      this.params.congdoan_id = r.id
      this.buttonStatus = 'init'
    },
    async getDSCongDoan() {
      try {
        this.loading(true)
        await this.getDSNhomCongDoan()
        const res = await api.getDSCongDoan(this.axios, this.params.seach_cd)
        if (res.data.message == 'Success') {
          // console.log(res.data.data.length)
          for (const i of res.data.data) {
            const di = {}
            di.id = i.congdoan_id
            i.trangthai == 1 ? (di.tinhtrang = 'Đang sử dụng') : (di.tinhtrang = 'Không sử dụng')
            di.ma_congdoan = i.ma_congdoan
            di.ten_congdoan = i.ten_congdoan
            for (const option of this.params.phase_options) {
              if (option.id == i.nhomcd_id) {
                di.nhom_congdoan = option.text
                break
              } else {
                di.nhom_congdoan = ''
              }
            }
            di.nhomcd_id = i.nhomcd_id
            for (const option of this.params.channel_options) {
              if (option.id == i.kenhban_id) {
                di.kenh = option.text
                break
              } else {
                di.kenh = ''
              }
            }
            di.kenhban_id = i.kenhban_id
            di.doituongban_id = i.doituongban_id
            this.params.table_dataSource.push(di)
          }
        } else this.$toast.error('Lỗi khi lấy danh sách công đoạn')
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    getDSKenhBan() {
      api.getDSKenhBan(this.axios, {}).then((res) => {
        if (res.data.message == 'Success') {
          this.params.channel_options = res.data.data.map((i) => {
            return {
              id: i.nhom_ldv_id,
              text: i.nhom_ldv
            }
          })
          this.params.channel = this.params.channel_options[0].id
        } else this.$toast.error('Lỗi khi lấy danh sách kênh bán')
      })
    },
    async getDSNhomCongDoan() {
      api
        .getDSNhomCongDoan(this.axios, {
          p_trangthai: '1'
        })
        .then((res) => {
          if (res.data.message == 'Success') {
            this.params.phase_options = res.data.data.map((i) => {
              return {
                id: i.nhomcd_id,
                text: i.ten_nhom
              }
            })
          } else this.$toast.error('Lỗi khi lấy danh sách nhóm công đoạn')
        })
    },
    startCreate() {
      this.buttonStatus = 'add'
      this.params.ma_congdoan = ''
      this.params.ten_congdoan = ''
      this.params.phase = ''
      this.params.channel = ''
      this.params.status = ''
      this.params.tyle_td = ''
      this.params.sotien = ''
      const inputElement = document.getElementById('input_ten_congdoan')
      inputElement.focus()
    },
    cancelTask() {
      this.buttonStatus = 'init'
      this.params.ma_congdoan = ''
      this.params.ten_congdoan = ''
      this.params.phase = ''
      this.params.channel = ''
      this.params.status = ''
      this.params.tyle_td = ''
      this.params.sotien = ''
      this.selectRowCD(this.params.last_selected_cd)
    },
    savePhase() {
      this.params.ten_congdoan = this.params.ten_congdoan.trim()
      if (!this.params.ten_congdoan) {
        this.$toast.error('Tên công đoạn không được để trống !')
      } else if (!this.params.phase) {
        this.$toast.error('Vui lòng chọn nhóm công đoạn !')
      } else if (!this.params.channel) {
        this.$toast.error('Vui lòng chọn kênh bán !')
      } else if (!this.params.status) {
        this.$toast.error('Vui lòng chọn trạng thái !')
      } 
      else if (this.params.sotien && this.params.sotien <= 0) this.$toast.error('Số tiền được hưởng không hợp lệ, vui lòng kiểm tra lại !')
      else {
        if (this.params.ma_congdoan) {
          //cập nhật công đoạn
          api
            .updateCongDoan(this.axios, {
              p_id_congdoan: this.params.congdoan_id + '',
              p_nhomcongdoan_id: this.params.phase + '',
              p_tencongdoan: this.params.ten_congdoan,
              p_kenhban_id: this.params.channel,
              p_doituongban_id: this.params.doituongban,
              p_trangthai: this.params.status == 'Đang sử dụng' ? '1' : '0'
            })
            .then((res) => {
              if (res.data.data == 'ok') {
                this.$toast.success('Cập nhật công đoạn thành công !')
                this.params.table_dataSource = []
                this.getDSCongDoan()
                this.cancelTask()
              } else this.$toast.error(res.data.data)
            })
        } else {
          //thêm mới
          api
            .insertCongDoan(this.axios, {
              p_tencongdoan: this.params.ten_congdoan,
              p_nhomcongdoan_id: this.params.phase,
              p_kenhban_id: this.params.channel,
              p_trangthai: this.params.status == 'Đang sử dụng' ? '1' : '0',
              p_doituongban_id: this.params.doituongban + '',
            })
            .then((res) => {
              if (res.data.data == 'ok') {
                this.$toast.success('Thêm công đoạn thành công !')
                this.params.table_dataSource = []
                this.getDSCongDoan()
                this.cancelTask()
              } else this.$toast.error(res.data.data)
            })
        }
      }
    },
    deletePhase() {
      if (!this.params.ma_congdoan) this.$toast.error('Vui lòng chọn công đoạn cần xóa !')
      else {
        this.$confirm(`Bạn có chắc chắn muốn xóa công đoạn này ?
        Mã công đoạn: ${this.params.ma_congdoan}`, 'Xác nhận', {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy bỏ',
          type: 'warning'
        })
          .then(() => {
            this.loading(true)
            api
              .deleteCongDoan(this.axios, {
                p_id_congdoan: this.params.congdoan_id
              })
              .then((res) => {
                if (res.data.data == 'ok') {
                  this.$toast.success('Xóa công đoạn thành công !')
                  this.params.table_dataSource = []
                  this.getDSCongDoan()
                  this.cancelTask()
                } else this.$toast.error(res.data.data)
              })
          })
          .catch(() => {
            this.$toast.info('Đã hủy xóa công đoạn')
          })
          .finally(() => {
            this.loading(false)
          })
      }
    },
    editPhase() {
      if (!this.params.ma_congdoan) this.$toast.error('Vui lòng chọn công đoạn cần chỉnh sửa !')
      else {
        this.buttonStatus = 'edit'
        const inputElement = document.getElementById('input_ten_congdoan')
        inputElement.focus()
      }
    }
  },
  async mounted() {
    this.getDSKenhBan()
    let res = await api.getDSDoiTuongBan(this.axios, {})
    this.params.ds_doituongban = res.data.data.map((i) => {
      return {
        id: i.dt_ban_id,
        text: i.doituongban
      }
    })

    this.getDSCongDoan()
  }
}
</script>
