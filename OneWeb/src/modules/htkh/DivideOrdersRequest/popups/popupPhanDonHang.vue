<template src="./popupPhanDonHang.html"></template>
<script>
import Select2Ext from '@/modules/contract/setup/DeclareLandline/components/Select2Ext'
export default {
  components: {
    Select2Ext
  },
  data() {
    return {
      phieu_ids: [],
      dataSources: [],
      treeDonvi: [],
      donvi_id: '',
      headers: [
        { fieldName: 'ma_gd', headerText: 'Mã đơn hàng', allowFiltering: true, visible: true },
        // { fieldName: 'trangthai_hd', headerText: 'Kết quả xử lý đơn hàng', allowFiltering: true, visible: true },
        { fieldName: 'ma_nv_xldh', headerText: 'Mã nhân viên XLĐH', allowFiltering: true, visible: true },
        { fieldName: 'ten_nv_xldh', headerText: 'Tên nhân viên XLĐH', allowFiltering: true, visible: true },
        { fieldName: 'ten_nv', headerText: 'Tên nhân viên phân đơn', allowFiltering: true, visible: true },
        { fieldName: 'ngaygiao', headerText: 'Ngày phân đơn', allowFiltering: true, visible: true }
      ]
    }
  },
  watch: {
    donvi_id(value) {
      if (value)
        this.getNhanVien(value)
    }
  },
  methods: {
    async tsbtnGhiLai_Click() {
      this.loading(true)
      return new Promise(async (resolve, reject) => {
        try {
          for (let i = 0; i < this.phieu_ids.length; i++) {
            let res = await this.axios.post('web-hopdong/quanlydonhang/fn_phan_donhang', {
              p_nhanvien_id: this.$root.token.getNhanVienID(),
              p_nhanvien_xldh_id: this.$refs.cboNhanvien.value,
              p_phieu_id: this.phieu_ids[i].phieu_id
            })

            if (res.data.data == 'OK') this.$toast.success(`Phân đơn hàng ${this.phieu_ids[i].ma_gd} cho ${this.$refs.cboNhanvien.text} thành công!`)
            else this.$toast.error(`Phân đơn hàng ${this.phieu_ids[i].ma_gd} cho ${this.$refs.cboNhanvien.text} không thành công!` + res.data.message)
          }
          this.$refs.popupPhanDonHang.hide()
          await this.getDonHang()

          this.$emit('success')
        } catch (error) {
          this.$toast.error('Lỗi lưu thông tin phân đơn ' + error.message)
        }
        resolve(true)
      })
      this.loading(false)
    },
    list2treeNew(list) {
        const uniqueList = Array.from(new Set(list.map(item => item.donvi_cha_id))).map(id => list.find(item => item.donvi_cha_id === id));
        const tree = uniqueList.map(item => {
            const children = list.filter(child => child.donvi_cha_id === item.donvi_cha_id);
            return {
                label: item.ten_dv_cha,
                id: "",
                children: children.map(child => ({label: child.ten_dv, id: child.donvi_id}))
            }
        });
        return tree;
        // rest of the code
    },
    async showDialog() {
      this.loading(true)
      this.$refs.cboNhanvien.dataTextField = 'ten_nv'
      this.$refs.cboNhanvien.dataValueField = 'nhanvien_id'
      await this.getDonvi()
      await this.getNhanVien(this.donvi_id)
      await this.getDonHang()
      this.$refs.popupPhanDonHang.show()
      this.loading(false)
    },
    async getDonHang() {
      return new Promise(async (resolve, reject) => {
        try {
          let res = await this.axios.post('web-hopdong/quanlydonhang/sp_lay_ds_phan_donhang', {
            p_nhanvien_id: this.$root.token.getNhanVienID()
          })
          this.dataSources = res.data.data
        } catch (error) {
          this.$toast.error('Lỗi lấy danh sách đơn hàng ' + error.message)
        }
        resolve(true)
      })
    },
    async getNhanVien(donvi_id) {
      return new Promise(async (resolve, reject) => {
        try {
          let res = await this.axios.post('web-hopdong/quanlydonhang/sp_lay_nhanvien_xldh', {
            p_donvi_id: donvi_id
          })
          this.$refs.cboNhanvien.dataSource = res.data.data
          this.$refs.cboNhanvien.value = res.data.data && res.data.data.length > 0 ? res.data.data[0].nhanvien_id : ''
        } catch (error) {
          this.$toast.error('Lỗi lấy danh sách đơn vị ' + error.message)
        }
        resolve(true)
      })
    },
    async getDonvi() {
      return new Promise(async (resolve, reject) => {
        try {
          let res = await this.axios.get('web-hopdong/quanlydonhang/sp_lay_donvi_xldh')
          this.treeDonvi = this.list2treeNew(res.data.data)
          console.log(this.treeDonvi)
          this.donvi_id = res.data.data && res.data.data.length > 0 ? res.data.data[0].donvi_id : ''
        } catch (error) {
          this.$toast.error('Lỗi lấy danh sách đơn vị ' + error.message)
        }
        resolve(true)
      })
    }
  }
}
</script>
