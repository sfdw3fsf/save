<template src="./index.html">
</template>
<script>
export default {
  data () {
    return {
      SelectedItem: [],
      txtContent: '',
      txtFromIncome: 0,
      txtToIncome: 0,
      txtUnitPrice: 0,
      CurrentId: 0,
      CheckInsert: 0,
      SuccessCode: 'BSS-00000000',
      data: Object,
      dataKHDChuaGan: Object,
      dataKHDDaGan: Object,
      dataLNVChuaGan: Object,
      dataLNVDaGan: Object,
      dataKVChuaGan: Object,
      dataKVDaGan: Object,
      btnNew: {
        Enabled: true
      },
      btnSave: {
        Enabled: true
      },
      btnCancel: {
        Enabled: true
      },
      btnDelete: {
        Enabled: true
      },
      ListData: {
        DataSource: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            allowSorting: true,
            width: '20'
          },
          {
            fieldName: 'noidung_tps',
            headerText: 'Nội dung',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'dongia',
            headerText: 'Đơn giá',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          }
        ]
      },
      grvKHDChuaGan: {
        DataSource: []
      },
      grvKHDDaGan: {
        DataSource: []
      },
      grvLNVChuaGan: {
        DataSource: []
      },
      grvLNVDaGan: {
        DataSource: []
      },
      grvKVChuaGan: {
        DataSource: []
      },
      grvKVDaGan: {
        DataSource: []
      }
    }
  },
  async mounted () {
    this.GetListData()
    debugger;
    await this.List_KHD_ChuaGan("")
    await this.List_LNV_ChuaGan("")
    await this.List_KV_ChuaGan("")


  },
  methods: {
    async GetListData () {
      try {
        // this.data = await this.axios.post('web-quantri/khuyenmai/lay_ds_thamso_md', {
        //   'list_of_cols': 'rownum stt, a.*',
        //   'order': 'a.dongia_tps_id',
        //   'phanvung_id': 26,
        //   'schema': 'QLTN',
        //   'table_name': 'dongia_tps a',
        //   'where': ''
        // })

        //Fix atbm

        this.data = await this.axios.post('/web-hopdong/laydulieu/sp_tt_dongia_tps', JSON.stringify({
          "p_param": ""
          , "p_type":1
          })
        )

        
        this.ListData.DataSource = this.data.data.data
      } catch (err) {
        this.ListData.DataSource = []
      }
    },
    async List_KHD_ChuaGan (Id) {
      try {
        this.dataKHDChuaGan = await this.axios.post('web-quantri/tylethulaoquahan/sp_dongia_layds_theo_chukyno', {
          'p_dongia_tps_id': Id,
          'p_loai': 0
        })
      } catch (err) {
        this.dataKHDChuaGan.data.data = []
      }
    },
    async List_KHD_DaGan (Id) {
      try {
        this.dataKHDDaGan = await this.axios.post('web-quantri/tylethulaoquahan/sp_dongia_layds_theo_chukyno', {
          'p_dongia_tps_id': Id,
          'p_loai': 1
        })
      } catch (err) {
        this.dataKHDDaGan.data.data = []
      }
    },
    async List_LNV_ChuaGan (Id) {
      try {
        this.dataLNVChuaGan = await this.axios.post('web-quantri/tylethulaoquahan/sp_dongia_layds_theo_loainv', {
          'p_dongia_tps_id': Id,
          'p_loai': 0
        })
      } catch (err) {
        this.dataLNVChuaGan.data.data = []
      }
    },
    async List_LNV_DaGan (Id) {
      try {
        this.dataLNVDaGan = await this.axios.post('web-quantri/tylethulaoquahan/sp_dongia_layds_theo_loainv', {
          'p_dongia_tps_id': Id,
          'p_loai': 1
        })
      } catch (err) {
        this.dataLNVDaGan.data.data = []
      }
    },
    async List_KV_ChuaGan (Id) {
      try {
        this.dataKVChuaGan = await this.axios.post('web-quantri/tylethulaoquahan/sp_dongia_layds_theo_khuvuc', {
          'p_dongia_tps_id': Id,
          'p_loai': 0
        })
      } catch (err) {
        this.dataKVChuaGan.data.data = []
      }
    },
    async List_KV_DaGan (Id) {
      try {
        this.dataKVDaGan = await this.axios.post('web-quantri/tylethulaoquahan/sp_dongia_layds_theo_khuvuc', {
          'p_dongia_tps_id': Id,
          'p_loai': 1
        })
      } catch (err) {
        this.dataKVDaGan.data.data = []
      }
    },
    async ListDataFocus () {
      this.SelectedItem = this.$refs.ListData.getSelectedRecords()
      if (this.SelectedItem[0].dongia_tps_id === this.CurrentId) return
      if (this.SelectedItem.length <= 0) {
        this.txtContent = ''
        this.txtFromIncome = ''
        this.txtToIncome = ''
        this.txtUnitPrice = ''
      } else {
        this.CurrentId = this.SelectedItem[0].dongia_tps_id
        this.CheckInsert = 0
        await this.List_KHD_ChuaGan(this.CurrentId)
        await this.List_KHD_DaGan(this.CurrentId)
        await this.List_LNV_ChuaGan(this.CurrentId)
        await this.List_LNV_DaGan(this.CurrentId)
        await this.List_KV_ChuaGan(this.CurrentId)
        await this.List_KV_DaGan(this.CurrentId)
        this.Filldata(this.SelectedItem[0])
        this.SetButton(2)
      }
    },
    Filldata (item) {
      this.txtContent = item.noidung_tps
      this.txtFromIncome = item.cps_tu
      this.txtToIncome = item.cps_den
      this.txtUnitPrice = item.dongia
      this.grvKHDChuaGan.DataSource = this.dataKHDChuaGan.data.data
      this.grvKHDChuaGan.DataSource = [...this.dataKHDChuaGan.data.data]
      this.grvKHDDaGan.DataSource = this.dataKHDDaGan.data.data
      this.grvKHDDaGan.DataSource = [...this.grvKHDDaGan.DataSource]
      this.grvLNVChuaGan.DataSource = this.dataLNVChuaGan.data.data
      this.grvLNVChuaGan.DataSource = [...this.dataLNVChuaGan.data.data]
      this.grvLNVDaGan.DataSource = this.dataLNVDaGan.data.data
      this.grvLNVDaGan.DataSource = [...this.grvLNVDaGan.DataSource]
      this.grvKVChuaGan.DataSource = this.dataKVChuaGan.data.data
      this.grvKVChuaGan.DataSource = [...this.dataKVChuaGan.data.data]
      this.grvKVDaGan.DataSource = this.dataKVDaGan.data.data
      this.grvKVDaGan.DataSource = [...this.grvKVDaGan.DataSource]
    },
    SetButton (input) {
      this.btnNew.Enabled = false
      this.btnSave.Enabled = false
      this.btnCancel.Enabled = false
      this.btnDelete.Enabled = false
      switch (input) {
        // Click new
        case 1:
          this.btnSave.Enabled = true
          this.btnCancel.Enabled = true
          break
        // Click save
        case 2:
          this.btnNew.Enabled = true
          this.btnSave.Enabled = true
          this.btnCancel.Enabled = true
          this.btnDelete.Enabled = true
      }
    },
    Clear () {
      this.txtContent = ''
      this.txtFromIncome = 0
      this.txtToIncome = 0
      this.txtUnitPrice = 0
      this.NextAll(1)
      this.NextAll(2)
      this.NextAll(3)
    },
    // dd
    btnNewClick () {
      if (!this.btnNew.Enabled) return
      this.CurrentId = 0
      this.SetButton(1)
      this.Clear()
      this.CheckInsert = 1
    },
    btnCancelClick () {
      if (!this.btnCancel.Enabled) return
      this.SetButton(2)
      this.CheckInsert = 0
      this.Filldata(this.SelectedItem[0])
    },
    async btnSaveClick () {
      if (!this.btnSave.Enabled) return
      if (this.Validate()) {
        let Text = 'Thêm mới'
        if (this.CheckInsert === 0) Text = 'Cập nhật'
        this.$confirm(
          `Bạn thật sự muốn ` + Text.toLowerCase() + ` không?`,
          Text + ' Đơn giá thù lao thu cước theo mức doanh thu',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }
        ).then(async () => {
          try {
            let arrKHD = []
            let arrLNV = []
            let arrKV = []
            this.grvKHDDaGan.DataSource.forEach(item => {
              arrKHD.push({
                'CHUKYNO': item.id
              })
            })
            this.grvLNVDaGan.DataSource.forEach(item => {
              arrLNV.push({
                'LOAINV_ID': item.loainv_id
              })
            })
            this.grvKVDaGan.DataSource.forEach(item => {
              arrKV.push({
                'KHUVUC_ID': item.khuvuc_id
              })
            })
            let ObjDonGia = {
              PHANVUNG_ID: this.$root.token.getPhanVungID(),
              DONGIA_TPS_ID: this.CurrentId,
              NOIDUNG_TPS: this.txtContent.trim(),
              CPS_TU: this.txtFromIncome,
              CPS_DEN: this.txtToIncome,
              DONGIA: this.txtUnitPrice
            }
            this.data = await this.axios.post('web-quantri/tylethulaoquahan/sp_dongia_insert_or_update',
              {
                'p_is_insert': this.CheckInsert,
                'p_dongia_tps_id': this.CurrentId,
                'p_json_dongia': JSON.stringify(ObjDonGia),
                'p_json_dongia_kv': JSON.stringify(arrKV),
                'p_json_dongia_lnv': JSON.stringify(arrLNV),
                'p_json_dongia_ckn': JSON.stringify(arrKHD)
              })
            if (this.data.data.error_code === this.SuccessCode) {
              this.$toast.success(Text + ' Đơn giá thù lao thu cước theo mức doanh thu vào cơ sở dữ liệu tỉnh thành công!')
              this.GetListData()
              console.log('this.data.data.error_code: ', this.data.data.error_code)
            }
          } catch (error) {
            this.$toast.error('Lỗi khi ' + Text.toLowerCase() + '!')
          }
        })
      }
    },
    async btnDeleteClick () {
      if (!this.btnDelete.Enabled) return
      this.$confirm(
        `Bạn chắc chắn muốn xóa Đơn giá thù lao thu cước theo mức doanh thu không?`,
        'Xóa Đơn giá thù lao thu cước theo mức doanh thu',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }
      ).then(async () => {
        try {
          this.data = await this.axios.post('web-quantri/tylethulaoquahan/sp_dongia_delete',
            {
              'p_dongia_tps_id': this.CurrentId
            })
          console.log('this.CurrentId: ', this.CurrentId)
          console.log('this.data: ', this.data)
          if (this.data.data.error_code === this.SuccessCode) {
            this.$toast.success('Xóa Đơn giá thù lao thu cước theo mức doanh thu trên dữ liệu viễn thông tỉnh thành công!')
            this.GetListData()
            this.SetButton(2)
          }
        } catch (error) {
          this.$toast.error('Lỗi khi xóa!')
        }
      })
    },
    Next (type) {
      switch (type) {
        case 1:
          this.Selected = this.$refs.grvKHDDaGan.getSelectedRecords()
          if (this.Selected.length <= 0) return
          this.grvKHDChuaGan.DataSource = this.addArrayElement(this.grvKHDChuaGan.DataSource, this.Selected)
          this.grvKHDDaGan.DataSource = this.removeArrayElement(this.grvKHDDaGan.DataSource, this.Selected)
          this.grvKHDDaGan.DataSource = [...this.grvKHDDaGan.DataSource]
          break
        case 2:
          this.Selected = this.$refs.grvLNVDaGan.getSelectedRecords()
          if (this.Selected.length <= 0) return
          this.grvLNVChuaGan.DataSource = this.addArrayElement(this.grvLNVChuaGan.DataSource, this.Selected)
          this.grvLNVDaGan.DataSource = this.removeArrayElement(this.grvLNVDaGan.DataSource, this.Selected)
          this.grvLNVDaGan.DataSource = [...this.grvLNVDaGan.DataSource]
          break
        case 3:
          this.Selected = this.$refs.grvKVDaGan.getSelectedRecords()
          if (this.Selected.length <= 0) return
          this.grvKVChuaGan.DataSource = this.addArrayElement(this.grvKVChuaGan.DataSource, this.Selected)
          this.grvKVDaGan.DataSource = this.removeArrayElement(this.grvKVDaGan.DataSource, this.Selected)
          this.grvKVDaGan.DataSource = [...this.grvKVDaGan.DataSource]
          break
      }
    },
    NextAll (type) {
      switch (type) {
        case 1:
          if (this.grvKHDDaGan.DataSource.length === 0) return
          this.grvKHDChuaGan.DataSource = this.addArrayElement(this.grvKHDChuaGan.DataSource, this.grvKHDDaGan.DataSource)
          this.grvKHDDaGan.DataSource = []
          break
        case 2:
          if (this.grvLNVDaGan.DataSource.length === 0) return
          this.grvLNVChuaGan.DataSource = this.addArrayElement(this.grvLNVChuaGan.DataSource, this.grvLNVDaGan.DataSource)
          this.grvLNVDaGan.DataSource = []
          break
        case 3:
          if (this.grvKVDaGan.DataSource.length === 0) return
          this.grvKVChuaGan.DataSource = this.addArrayElement(this.grvKVChuaGan.DataSource, this.grvKVDaGan.DataSource)
          this.grvKVDaGan.DataSource = []
      }
    },
    Previous (type) {
      switch (type) {
        case 1:
          this.Selected = this.$refs.grvKHDChuaGan.getSelectedRecords()
          console.log('this.Selected: ', this.Selected)
          if (this.Selected.length <= 0) return
          this.grvKHDDaGan.DataSource = this.addArrayElement(this.grvKHDDaGan.DataSource, this.Selected)
          this.grvKHDChuaGan.DataSource = this.removeArrayElement(this.grvKHDChuaGan.DataSource, this.Selected)
          this.grvKHDChuaGan.DataSource = [...this.grvKHDChuaGan.DataSource]
          break
        case 2:
          this.Selected = this.$refs.grvLNVChuaGan.getSelectedRecords()
          if (this.Selected.length <= 0) return
          this.grvLNVDaGan.DataSource = this.addArrayElement(this.grvLNVDaGan.DataSource, this.Selected)
          this.grvLNVChuaGan.DataSource = this.removeArrayElement(this.grvLNVChuaGan.DataSource, this.Selected)
          this.grvLNVChuaGan.DataSource = [...this.grvLNVChuaGan.DataSource]
          break
        case 3:
          this.Selected = this.$refs.grvKVChuaGan.getSelectedRecords()
          if (this.Selected.length <= 0) return
          this.grvKVDaGan.DataSource = this.addArrayElement(this.grvKVDaGan.DataSource, this.Selected)
          this.grvKVChuaGan.DataSource = this.removeArrayElement(this.grvKVChuaGan.DataSource, this.Selected)
          this.grvKVChuaGan.DataSource = [...this.grvKVChuaGan.DataSource]
      }
    },
    PreviousAll (type) {
      switch (type) {
        case 1:
          if (this.grvKHDChuaGan.DataSource.length === 0) return
          this.grvKHDDaGan.DataSource = this.addArrayElement(this.grvKHDDaGan.DataSource, this.grvKHDChuaGan.DataSource)
          this.grvKHDChuaGan.DataSource = []
          break
        case 2:
          if (this.grvLNVChuaGan.DataSource.length === 0) return
          this.grvLNVDaGan.DataSource = this.addArrayElement(this.grvLNVDaGan.DataSource, this.grvLNVChuaGan.DataSource)
          this.grvLNVChuaGan.DataSource = []
          break
        case 3:
          if (this.grvKVChuaGan.DataSource.length === 0) return
          this.grvKVDaGan.DataSource = this.addArrayElement(this.grvKVDaGan.DataSource, this.grvKVChuaGan.DataSource)
          this.grvKVChuaGan.DataSource = []
      }
    },
    addArrayElement (arr, items) {
      return items.concat(arr)
    },
    removeArrayElement (arr, items) {
      items.forEach(item => {
        let index = arr.indexOf(item)
        if (index >= 0) {
          arr.splice(index, 1)
        }
      })
      return arr
    },
    Validate () {
      if (!this.txtContent || this.txtContent.trim().length === 0) {
        this.$toast.warning('Bạn chưa nhập "Nội dung"!')
        return false
      }
      if (!this.txtFromIncome && this.txtFromIncome !== 0) {
        this.$toast.warning('Bạn chưa nhập "Doanh thu từ"!')
        return false
      }
      if (this.txtFromIncome < 0) {
        this.$toast.warning('"Doanh thu từ" phải >= 0!')
        return false
      }
      if (!this.txtToIncome && this.txtToIncome !== 0) {
        this.$toast.warning('Bạn chưa nhập "Doanh thu đến"!')
        return false
      }
      if (this.txtToIncome < 0) {
        this.$toast.warning('"Doanh thu đến" phải >= 0!')
        return false
      }
      if (this.txtToIncome > 0) {
        if (this.txtFromIncome > this.txtToIncome) {
          this.$toast.warning('"Doanh thu đến" phải >= "Doanh thu từ"!')
          return false
        }
      }

      if (!this.txtUnitPrice && this.txtUnitPrice !== 0) {
        this.$toast.warning('Bạn chưa nhập "Đơn giá"')
        return false
      }
      if (this.txtUnitPrice <= 0) {
        this.$toast.warning('"Đơn giá" phải > 0!')
        return false
      }
      if (this.grvKHDDaGan.DataSource.length === 0) {
        this.$toast.warning('Bạn chưa gán "Kỳ hòa đơn"')
        return false
      }
      if (this.grvLNVDaGan.DataSource.length === 0) {
        this.$toast.warning('Bạn chưa gán "Loại nhân viên"')
        return false
      }
      if (this.grvKVDaGan.DataSource.length === 0) {
        this.$toast.warning('Bạn chưa gán "Khu vực"')
        return false
      }
      return true
    }
  }
}
</script>
<style>
.non-ative a {
  color: #d3d3d3 !important;
}
.no-header .e-columnheader {
  display: none;
}
</style>
