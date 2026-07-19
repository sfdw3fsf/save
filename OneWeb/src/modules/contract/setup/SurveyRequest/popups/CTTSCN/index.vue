<template src="./index.html"></template>

<script>
import api from './api'
import moment from 'moment'

export default {
  props: {
    modalId: [Number, String],
    tinhId: [Number, String],
    hdtbId: [Number, String],
    huongGiaoId: [Number, String],
    loaitb_id: [Number, String]
  },
  data() {
    return {
      title: 'Cập nhật dịch vụ CNTT',
      gridEditors: [],
      ma_tb: null
    }
  },
  methods: {
    async callApiWrapper(action, body, options = { showError: true }) {
      let result = null
      try {
        result = await action(this.axios, body)

        return result.data.data != null ? result.data.data : null
      } catch (e) {
        if (options.showError) {
          if (e.data != null) {
            this.$toast.error(`${e.data.message}`)
          } else {
            this.$toast.error(`Có lỗi gọi API`)
          }
        }
        return 'error'
      }
      return null
    },
    onHiddenModal() {
      console.log(this.modalId, 'onHiddenModal')
    },
    showModal() {
      this.$bvModal.show('modalId')
      console.log(this.hdtbId)
      console.log(this.loaitb_id)
    },
    async onShownModal() {
      console.log('onShownModal')
      this.gridEditors = await this.callApiWrapper(api.fn_lay_thuoctinh_dong_hdtb_cntt, {
        vphanvung_id: 0,
        vtinh_id: this.tinhId,
        vhdtb_id: this.hdtbId,
        voptions: 'huonggiao_id:' + this.huongGiaoId
      })

      this.gridEditors.forEach((e) => {
        e.doAction = function() {
          console.log(e.EDIT_VALUE)
          e.CHANGED = 1
        }
      })

      if (this.gridEditors.length == 0) {
        this.$bvModal.hide(this.modalId)
        this.$toast.warning('Không có thông tin nào được phép điều chỉnh.')
      }
      this.gridEditors.forEach((item) => {
        console.log(1)
        if (item.FIELD_NAME == 'MA_TB') {
          this.ma_tb = item.EDIT_VALUE
          console.log('this.ma_tb')
          console.log(this.ma_tb)
        }
      })

      this.gridEditors.forEach((item) => {
        if (item.EDIT_VALUE !== null && item.ATT == 10) {
          if (typeof item.EDIT_VALUE === 'string') {
            item.EDIT_VALUE = item.EDIT_VALUE.split(',').map(Number)
          } else if (typeof item.EDIT_VALUE === 'number') {
            item.EDIT_VALUE = [item.EDIT_VALUE]
          }
        }
      })

      console.log(this.gridEditors)
    },
    async onClickBtnChapNhan() {
      try {
        console.log(this.loaitb_id)
        console.log(this.ma_tb)
        console.log(this.gridEditors)
        let updateData = []
        let data = {}
        this.gridEditors.forEach((e) => {
          if (e.CHANGED == 1) {
            data = {
              EDIT_VALUE: e.EDIT_VALUE,
              DATA_NAME: e.FIELD_NAME,
              CAPTION: e.CAPTION,
              ATT: e.ATT,
              DATA_TYPE: e.DATA_TYPE,
              TBL_NAME: e.TABLE_NAME
            }

            if (e.ATT == 2) data.EDIT_VALUE = moment(e.EDIT_VALUE).format('DD/MM/YYYY')
            else if (e.ATT == 9) data.EDIT_VALUE = moment(e.EDIT_VALUE).format('DD/MM/YYYY HH:MI:SS')
            else data.EDIT_VALUE = e.EDIT_VALUE

            updateData.push(data)
          }
        })
        if (updateData.length == 0) {
          this.$toast.warning('Không có thông tin nào thay đổi.')
          return
        }
        if (this.loaitb_id == 2114 || this.loaitb_id == 359 || this.loaitb_id == 351) {
          console.log(this.ma_tb)
          if (this.ma_tb == null) {
            const data_matb = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
              p_nghiepvu: 'LAY_MA_THUEBAO_BAOHIEUQT',
              p_ds_para: '{"LOAITB_ID":' + this.loaitb_id + '}'
            })
            this.ma_tb = data_matb.data.data[0].ketqua
          }
          var matb_data = {
            EDIT_VALUE: this.ma_tb,
            DATA_NAME: 'MA_TB',
            CAPTION: 'MA_TB',
            ATT: 0,
            DATA_TYPE: 'MA_TB',
            TBL_NAME: 'HDTB_CNTT'
          }
          updateData.push(matb_data)
        }

        let rs = await this.callApiWrapper(api.sp_capnhat_thuoctinh_dong_hdtb_cntt, {
          vtinh_id: this.tinhId,
          vhdtb_id: this.hdtbId,
          vjs_data: JSON.stringify(updateData)
        })

        if (rs == null || rs == 'OK') {
          this.$toast.success('Cập nhật thành công')
          this.$emit('xacnhan', true)
        }
      } catch (error) {
      } finally {
        this.$emit('loadGrid')
      }
    }
  }
}
</script>
