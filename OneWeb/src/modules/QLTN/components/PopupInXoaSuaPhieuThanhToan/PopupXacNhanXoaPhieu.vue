<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgXoaPhieuTT"
        header="Xác nhận xóa phiếu"
        :animationSettings='animationSettings'
        showCloseIcon=true width='50%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
        :beforeClose="onBeforeClose"
        :beforeOpen="onBeforeOpen"
    >
        <div class="">
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="javascript: void(0)" @click="onClickAccept">
                                <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)" @click="close">
                                <span class="icon one-export"></span> Thoát
                            </a>
                        </li>


                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="popup-tdntt">
                            <div class="icon">
                                <img src="../../../../assets/img/account.svg" alt="">
                            </div>
                            <div class="tdntt-content">
                                <span class="note red fw6 bold block">
                                    Bạn có đồng ý xóa {{ tongPhieu | currency }} phiếu với tổng tiền: {{ tongTien | currency }} ?
                                </span>
                                <br>
                                <div class="row" v-show="kiemtra_matkhau">
                                    <div class="col-md-10 col-12">
                                        <div class="info-row">
                                            <div class="key w80">Mật khẩu</div>
                                            <div class="value">
                                                <input :type="typeInput" @keyup.enter="onClickAccept" ref='xacnhanxoaphieu_password' autofocus placeholder="" class="form-control highlight" v-model="password">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </ejs-dialog>
</template>
<script>
import XLAPI from '../../api/XuLyPhieuThanhToanAPI.js'

export default {
  name: 'PopupXacNhanXoaPhieu',

  props: {
    // input: Object,
    dsPhieu: {
      type: Array
    },
    kycuoc: {
      type: String
    },
    kiemtra_matkhau: {
      type: Boolean,
      default: true
    }
  },

  computed: {
    ds_phieu () {
      return this.dsPhieu
    }
  },

  data () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      password: '',
      result: false,
      tongPhieu: 0,
      tongTien: 0,
      typeInput: 'text',
      // kt_matkhau: true
    }
  },
  methods: {
    dialogOpen () {
      this.typeInput = 'password'
    },

    dialogClose () {
      this.typeInput = 'text'
      this.$emit('acceptChange', this.result)
    },

    openDialog () {
      // console.log(this.kiemtra_matkhau)
      // this.kt_matkhau = this.kiemtra_matkhau
      this.password = ''
      this.$refs.dlgXoaPhieuTT.show()
    },

    onClickAccept () {
      /*
        let postData = {
          password: this.password
        }

        this.$emit('acceptChange', postData);
        this.close();
        */
      this.deleteProcess()
    },

    close () {
      this.$emit('acceptChange', this.result)
      this.$refs.dlgXoaPhieuTT.hide()
    },

    kiemTraNgayGachPhieu (actionType, item, value = null) {
      return true
    },

    async deleteProcess (args) {
      // let phieuCount = 0
      if (this.dsPhieu.length <= 0) {
        this.$toast.error('Không tìm thấy danh sách phiếu cần xóa.')
        return
      }
      if (this.kiemtra_matkhau) {
        if (this.password === null || this.password.trim === '' || this.password.trim() === '') {
          this.$toast.error('Chưa xác nhận mật khẩu.')
          return
        }
      }        
      this.loading(true)
      try {
        let apiBody = {
          pKyCuoc: this.kycuoc,
          pMayHuy: '-',
          pNguoiHuy: this.$auth.getUserName(),
          pPhieuId: 0,
          pPassword: this.kiemtra_matkhau ? this.password : 'ONEBSS'
        }

        let promises = []
        for (let item of this.dsPhieu) {
          if (this.kiemTraNgayGachPhieu(0, item)) {
            apiBody.pPhieuId = item.PHIEU_ID
            promises.push(this.deletePhieuThu(apiBody))
          }
        }
        const res = await Promise.all(promises)
        const success = res.filter(x => x === true).length

        if (success === res.length) {
          this.result = true
          this.$toast.success('Đã xoá thành công ' + res.filter(x => x === true).length + '/' + res.length)
        } else {
          this.$toast.error('Đã xoá thành công ' + res.filter(x => x === true).length + '/' + res.length)
          this.result = false
        }
        this.close()

        /*

          if (phieuCount === this.dsPhieu.length) {
            this.$toast.success('Xóa phiếu thanh toán thành công!');
            this.result =  true
            this.close();
          } else
          {
            this.result =  false
          }
          */
        // await this.doSearch();
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async deletePhieuThu (apiBody) {
      try {
        let response = await XLAPI.deletePhieuThanhToan(this.axios, apiBody)
        return response.data.error_code === 'BSS-00000000'
      } catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      } finally {}
    },
    onBeforeOpen (args) {
      this.$emit('beforeOpen', args)
    },
    onBeforeClose (args) {
      this.$emit('beforeClose', args)
    }
  },
  watch: {
    ds_phieu: {
      handler: function (new_value, old_value) {
        this.tongPhieu = 0
        this.tongTien = 0
        if (new_value.length > 0) {
          this.tongPhieu = new_value.length
          new_value.forEach((item) => {
            this.tongTien = this.tongTien + (item.SOTIENTRA !== undefined) ? item.SOTIENTRA : (item.TIEN_TRA !== undefined) ? item.TIEN_TRA : 0
          })
        }
      },
      deep:true
    }
    // kiemtra_matkhau: {
    //   handler: function (new_value, old_value) {
    //     console.log(new_value)
    //     this.kt_matkhau = new_value
    //   }
    // }
  }
}
</script>
