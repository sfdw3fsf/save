<template src="./LoaiVatLieuKetNoiMang.html"></template>
<style src="./LoaiVatLieuKetNoiMang.scss" scoped></style>

<script>
import Vue from 'vue'
import { Query } from '@syncfusion/ej2-data'

export default Vue.extend({
  data() {
    return {
      selectionSettings: { checkboxOnly: true, type: 'Multiple' },
      tsbtnThemMoi: false,
      tsbtnGhiLai: false,
      tsbtnHuyBo: false,
      tsbtnXoa: false,
      thongso_kythuat: null,
      loai_vatlieu: null,
      loaivatlieuOptions: [],
      ten_vatlieu_ketnoi: null,
      ghichu: null,
      dsLoaiVatLieu: [],
      id: null,
      dsSelected: []
    }
  },
  async created () {
    await this.loadDanhMuc()
    await this.loadDanhSach()
  },
  mounted: async function () {
    this.setButton(1)
  },
  methods: {
    async tsbtnThemMoi_Click () {
      this.setButton(1)
    },
    async tsbtnGhiLai_Click () {
      if (!this.validateInput()) {
        return
      }
      try {
        let f = await this.save()
        if (f) {
          if (this.id)
            this.$toast.success('Cập nhật thành công.')
          else
            this.$toast.success('Nhập mới thành công.')
          this.loadDanhSach()
        } else {
          this.$toast.error('Đã phát sinh lỗi khi lưu dữ liệu.')
        }
      } catch (err) {
        console.log(err)
      }

    },
    async tsbtnHuyBo_Click () {
      this.setButton(1)
    },
    async tsbtnXoa_Click () {
      let dsSelected = this.$refs.dsHaTang.getSelectedRecords()
      console.log(dsSelected)
      if (dsSelected.length <= 0) {
        this.$toast.error('Chưa chọn danh mục cần xoá.')
        return
      }
      this.$confirm(
            'Bạn có muốn xoá các danh mục đã chọn ?',
            "Loại vật liệu kết nối mạng",
            {
              confirmButtonText: "Đồng ý",
              cancelButtonText: "Không",
            }
          )
            .then(async () => {
              await this.doDelete(dsSelected.map(x => x.ID))
            })
    },
    async deleteItem (id) {
      try {
        const rs = await this.$root.context.post(
          `/web-ecms/danhmuc/loaivatlieu-ketnoimang/delete/` + id
        )
        if (rs?.data?.result)
          return true
        return false
      } catch (error) {
        console.log(error)
        return false
      }
    },

    onCheckBoxChange () {
      let dsSelected = this.$refs.dsHaTang.getSelectedRecords()
      if (dsSelected != null && dsSelected.length > 0) {
        this.tsbtnXoa = true
      } else {
        this.tsbtnXoa = false
      }
    },

    doDelete: async function (ds) {
      let promises = []
      for (let item of ds) {
        promises.push(await this.deleteItem(item))
      }
      let res = await Promise.all(promises)
      let ok = res.filter(x => x === true)

      // console.log('OK', ok)

      if (ok.length > 0) {
        this.$toast.success(`Đã xoá thành công ${ok.length} / ${res.length} mục` )
      } else {
        this.$toast.error(`Đã phát sinh lỗi khi xoá danh mục.` )
      }
      await this.loadDanhSach();
    },

    validateInput() {
      let errors = []
      if (!this.loai_vatlieu) {
        errors.push('Vui lòng chọn Loại vật liệu.')
      }
      if (!this.ten_vatlieu_ketnoi) {
        errors.push('Vui lòng chọn nhập Tên loại vật liệu kết nối.')
      }
      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },

    async loadDanhMuc () {
      try {
        const rs = await this.$root.context.post(
          `/web-ecms/danhmuc/tinhtrang-ip/get-danhmuc`, {"ds" : "|LOAIVATLIEU"}
        );
        if (rs.data && rs.data.length > 0) {
          let ds = rs.data
          this.loaivatlieuOptions = ds.filter(x => x.LOAI === 'LOAIVATLIEU')
        } else {

        }

      } catch (error) {
      }
    },

    async loadDanhSach () {
      try {
        const rs = await this.$root.context.get(
          `/web-ecms/danhmuc/loaivatlieu-ketnoimang/get-list`
        );
        if (rs.data && rs.data.length > 0) {
          this.dsLoaiVatLieu = rs.data.map(x => ({ ...x, _CHECK: false}))
        } else {
          this.dsLoaiVatLieu = []
          this.setButton(1)
        }
      } catch (error) {
        this.dsLoaiVatLieu = []
        this.setButton(1)
      }
    },

    async save() {
      let postData = {
        Id: this.id,
        loaiVatLieuId: this.loai_vatlieu,
        tenVatLieuKetNoi: this.ten_vatlieu_ketnoi,
        ghiChu: this.ghichu,
        thongSoKyThuat: this.thongso_kythuat
      }

      try {
        const rs = await this.$root.context.post(
            `/web-ecms/danhmuc/loaivatlieu-ketnoimang/update`, { p_ds: JSON.stringify(postData)}
          );
        if (rs.error == "200")
          return true
        this.$toast.error(rs.message_detail)
        return false
      } catch (err) {
        return false
      }
    },

    setButton(type) {
      switch (type) {
        case 1: // add
          this.tsbtnThemMoi = false
          this.tsbtnGhiLai = true
          this.tsbtnHuyBo = true
          this.tsbtnXoa = false
          this.clearForm()
          break;
        case 2: // edit
          this.tsbtnThemMoi = true
          this.tsbtnGhiLai = true
          this.tsbtnHuyBo = true
          this.tsbtnXoa = true
      }
    },

    clearForm() {
      this.thongso_kythuat = null
      this.loai_vatlieu = this.loaivatlieuOptions.length > 0 ? this.loaivatlieuOptions[0].ID : null
      this.ten_vatlieu_ketnoi = null
      this.ghichu = null
      this.id = null
    },

    onSelectRowCheckbox(itemsCheck) {
      console.log('check', itemsCheck);
      this.dsSelected = itemsCheck;
    },

    onSelectRowChanged(args) {

      console.log(selected)
      // if (!selected.isHeaderCheckboxClicked && selected.isInteracted) { // không phải call by click checkbox header
      //   let f = this.dsLoaiVatLieu.find(x => x._KEY === selected.data.ID)
      //   f._CHECK = true
      // }
      let selected = args.rowData
      if (!selected) {
        this.clearForm()
        return
      }
      // if (selected.data.length > 1) {
      //   return
      // }
      this.id = selected.ID
      let item = this.dsLoaiVatLieu.find(x => x.ID === this.id)
      console.log('item', selected, item)
      this.thongso_kythuat = item.THONGSO_KYTHUAT
      this.loai_vatlieu = item.ID_LOAIVATLIEU
      this.ten_vatlieu_ketnoi = item.TEN_VATLIEUKETNOI
      this.ghichu = item.GHI_CHU

      this.setButton(2)
    },
    async onRecordClick (row) {
      this.id = row.rowData.ID
      let item = this.dsLoaiVatLieu.find(x => x.ID === this.id)
      this.thongso_kythuat = item.THONGSO_KYTHUAT
      this.loai_vatlieu = item.ID_LOAIVATLIEU
      this.ten_vatlieu_ketnoi = item.TEN_VATLIEUKETNOI
      this.ghichu = item.GHI_CHU

      this.setButton(2)
    },

    checkBoxChange (args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        console.log('click check/uncheck all', args.checked)
        this.checkAll(args.checked)
      }
    },
    checkAll (f) {
      let temp = [...this.dsLoaiVatLieu]
      for (let i = 0; i < temp.length; i++) temp[i]._CHECK = f
      this.dsLoaiVatLieu = [...temp]
    },

    onFilteringLoaiVatLieu (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.loaivatlieuOptions, query)
    },
  }
})
</script>
