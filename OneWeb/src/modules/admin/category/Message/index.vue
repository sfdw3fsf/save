<template src="./index.html">
</template>
<script>
export default {
  data () {
    return {
      SelectedItem: [],
      MessageType: 0,
      Message: '',
      PhoneNumber: '',
      Email: '',
      Title: '',
      Content: '',
      CurrentId: 0,
      CheckInsert: 0,
      ListMessageType: {
        DataSource: [],
        Value: null
      },
      btnNew: {
        Enabled: false
      },
      btnSave: {
        Enabled: false
      },
      btnCancel: {
        Enabled: false
      },
      btnDelete: {
        Enabled: false
      },
      ListData: {
        DataSource: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'Stt',
            allowFiltering: true,
            allowSorting: true,
            width: '20'
          },
          {
            fieldName: 'ten_message',
            headerText: 'Message',
            allowFiltering: true,
            allowSorting: true,
            width: '200'
          }
        ]
      }
    }
  },
  mounted () {
    this.GetListData()
    this.GetMessageType()
    this.ListMessageType.DataSource = [...this.ListMessageType.DataSource]
	this.SetButton(2)
  },
  methods: {
    async GetListData () {
      try {
        this.data = await this.axios.post('web-quantri/cauhinh_bang_tudien/sp_lay_ds_message', {})
        this.ListData.DataSource = this.data.data.data
      } catch (err) {
        this.ListData.DataSource = []
      }
    },
    async GetMessageType () {
      try {
        this.data = await this.axios.post('web-quantri/cauhinh_bang_tudien/loai_message', {})
        this.ListMessageType.DataSource = this.data.data.data
      } catch (err) {
        this.ListMessageType.DataSource = []
      }
    },
    btnNewClick () {
      if (!this.btnNew.Enabled) return
      this.SetButton(1)
      this.Clear()
      this.CheckInsert = 1
    },
    async btnSaveClick () {
      if (!this.btnSave.Enabled) return
      if (this.Validate()) {
        let Text = 'Thêm mới'
        if (this.CheckInsert === 0) Text = 'Cập nhật'
        this.$confirm(
          `Bạn thật sự muốn ` + Text.toLowerCase() + ` không?`,
          Text + ' Message',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }
        ).then(async () => {
          try {
            this.data = await this.axios.post('web-quantri/cauhinh_bang_tudien/fn_ghilai_message',
              {
                'p_chk_new': this.CheckInsert,
                'p_message_id': this.CurrentId,
                'p_ten_message': !this.Message ? '' : this.Message.trim(),
                'p_noidung': !this.Content ? '' : this.Content.trim(),
                'p_kieu': this.MessageType,
                'p_so_dt': !this.PhoneNumber ? '' : this.PhoneNumber.trim(),
                'p_email': !this.Email ? '' : this.Email.trim(),
                'p_tieude': !this.Title ? '' : this.Title.trim()
              })
            if (this.data.data.error_code === 'BSS-00000000') {
              this.$toast.success(Text + ' Message vào cơ sở dữ liệu tỉnh thành công!')
              this.GetListData()
              this.Filldata(this.ListData.DataSource[0])
            }
          } catch (error) {
            this.$toast.error('Lỗi khi ' + Text.toLowerCase() + '!')
          }
        })
      }
    },
    btnCancelClick () {
      if (!this.btnCancel.Enabled) return
      this.SetButton(2)
      this.Filldata(this.SelectedItem[0])
    },
    async btnDeleteClick () {
      if (!this.btnDelete.Enabled) return
      this.$confirm(
        `Bạn chắc chắn muốn xóa Message không?`,
        'Xóa Message',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }
      ).then(async () => {
        try {
          this.data = await this.axios.post('web-quantri/cauhinh_bang_tudien/fn_xoa_message',
            {
              'p_message_id': this.CurrentId
            })
          if (this.data.data.error_code === 'BSS-00000000') {
            this.$toast.success('Xóa Message trên dữ liệu viễn thông tỉnh thành công!')
            this.GetListData()
            this.SetButton(2)
          }
        } catch (error) {
          this.$toast.error('Lỗi khi xóa!')
        }
      })
    },
    async ListDataFocus () {
      this.SelectedItem = await this.$refs.ListData.getSelectedRecords()
      // if (this.SelectedItem[0].message_id === this.CurrentId) return
      if (this.SelectedItem.length <= 0) {
        this.Filldata(-1)
      } else {
        this.Filldata(this.SelectedItem[0])
        this.SetButton(2)
      }
    },
    Clear () {
      this.MessageType = 1
      this.Message = ''
      this.PhoneNumber = ''
      this.Email = ''
      this.Title = ''
      this.Content = ''
    },
    SetButton (input) {
      this.btnNew.Enabled = false
      this.btnSave.Enabled = false
      this.btnCancel.Enabled = false
      this.btnDelete.Enabled = false
      switch (input) {
        case 1:
          this.btnSave.Enabled = true
          this.btnCancel.Enabled = true
          break
        case 2:
          this.btnNew.Enabled = true
          this.btnSave.Enabled = true
          this.btnCancel.Enabled = true
          this.btnDelete.Enabled = true
      }
    },
    async Filldata (item) {
      if (item === -1) {
        this.Clear()
      } else {
        this.CheckInsert = 0
        this.CurrentId = item.message_id
        this.MessageType = item.kieu
        this.Message = item.ten_message
        this.PhoneNumber = item.so_dt
        this.Email = item.email
        this.Title = item.tieude
        this.Content = item.noidung
      }
    },
    Validate () {
      if (!this.Message || this.Message.trim().length === 0) {
        this.$toast.warning('Bạn chưa nhập thông tin Message!')
        return false
      }
      if (!this.Content || this.Content.trim().length === 0) {
        this.$toast.warning('Bạn chưa nhập thông tin Nội dung!')
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
</style>
