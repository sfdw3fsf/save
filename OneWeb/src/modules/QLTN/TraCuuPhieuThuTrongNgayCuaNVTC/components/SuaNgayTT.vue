<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgThayDoiNgayTT"
        header="Thay đổi ngày thanh toán"
        :animationSettings='animationSettings'
        showCloseIcon=true width='50%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
    >
            <div class="">
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href='javascript: void(0)' @click="onClickAccept" title="Chấp nhận">
                                <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)" @click="close" title="Thoát">
                                <span class="icon one-export"></span> Thoát
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="popup-tdntt">
                            <div class="icon">
                                <img src="../../../../assets/img/calendar-full.svg" alt="">
                            </div>
                            <div class="tdntt-content">
                                <span class="note red fw6  block">
                                    <span class="-ap icon-warning3 text-warning f20 mar10"></span> Chỉ được chuyển trong cùng kỳ hóa đơn
                                </span>
                                <div class="row">
                                    <div class="col-md-8 col-12">
                                        <div class="info-row">
                                            <div class="key text-main">Ngày TT cũ</div>
                                            <div class="value">
                                                <ejs-datepicker
                                                    v-model="ngayTTCu"
                                                    :value="ngayTTCu"
                                                    :format="'dd/MM/y'"
                                                    :disabled="true"
                                                >
                                                </ejs-datepicker>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-12">
                                        <div class="label-text text-main">{{getChuKyCu}}</div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-8 col-12">
                                        <div class="info-row">
                                            <div class="key">Ngày TT mới</div>
                                            <div class="value">
                                                <ejs-datepicker
                                                    v-model="ngayTTMoi"
                                                    :value="today"
                                                    :format="'dd/MM/y'"
                                                    :showClearButton="false"
                                                >
                                                </ejs-datepicker>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-12">
                                        <div class="label-text red">{{getChuKyMoi}}</div>
                                    </div>
                                </div>
                                <div class="row" v-show="kiemtra_matkhau">
                                    <div class="col-md-8 col-12">
                                        <div class="info-row">
                                            <div class="key">Mật khẩu</div>
                                            <div class="value">
                                                <input :type="typeInput" autofocus  @keyup.enter="onClickAccept" v-model="password" placeholder="" class="form-control highlight">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-12">
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
import moment from 'moment'
import XLAPI from '../../api/XuLyPhieuThanhToanAPI.js'

export default {
  name: 'SuaNgayTT',

  props: {
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
    getChuKyCu () {
      let kyhd = moment(this.kycuoc).format('MMYYYY')
      let chuky = moment(this.kycuoc).format('DD')
      return 'Kỳ: ' + kyhd + '. Lần: ' + chuky
    },
    getChuKyMoi () {
      let kyhd = moment(this.ngayTTMoi).subtract(1,'months').endOf('month').format('MMYYYY')
      let chuky = '01'
      return 'Kỳ: ' + kyhd + '. Lần: ' + chuky
    },
    getPhieu () {
      return this.dsPhieu[0]
    }
  },

  data () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      ngayTTCu: new Date(),
      ngayTTMoi: new Date(),
      today: new Date(),
      password: 'XXX',
      result: false,
      typeInput: 'text'
    }
  },

  methods: {
    dialogOpen () {
      this.typeInput = 'password'
    },

    dialogClose () {
      this.typeInput = 'text'
    },

    openDialog () {
      this.password = ''
      this.$refs.dlgThayDoiNgayTT.show()
    },

    close () {
      this.ngayTTMoi = new Date()
      this.today = new Date()
      this.$refs.dlgThayDoiNgayTT.hide()
    },

    onClickAccept () {
      this.changeDateProcess()
    },

    async changeDateProcess () {
      let phieuCount = 0
      let errors = []
      if (this.ngayTTMoi === null) errors.push('Chưa nhập ngày thanh toán mới!')
      if (this.kiemtra_matkhau) {
        if (this.password == null || this.password.trim() === '') errors.push('Chưa nhập xác nhận mật khẩu!')
      }
      if (errors.length > 0) return this.$toast.error(errors.join('\r\n'))

      let promises = []
      try {
        for (let item of this.dsPhieu) {
          if (item.PHIEU_ID_NEO !== '' && item.PHIEU_ID_NEO > 0) {
            return this.$toast.error(`Bạn không được cập nhật ngày thanh toán của mã ${item.MA_TT} do đã gạch trên VNP!`)
          }
          let apiBody = {
            pKyCuoc: this.kycuoc,
            pPhieuId: item.PHIEU_ID,
            pNgayTT: moment(this.ngayTTMoi).format('DD/MM/YYYY'),
            pPassword: this.kiemtra_matkhau ? this.password : 'XX'
          }
          promises.push(this.updateNgayThanhToan(apiBody))
        }
        this.loading(true)
        const res = await Promise.all(promises)
        this.loading(false)

        phieuCount = res.filter(x => x === true).length
        if (phieuCount === this.dsPhieu.length) {
          this.$toast.success('Cập nhật thành công ' + phieuCount + ' phiếu')
          this.result = true
          this.close()
        } else {
          this.$toast.success('Đã có ' + (this.dsPhieu.length - phieuCount) + ' phiếu bị lỗi khi cập nhật')
          this.result = false
        }
        this.$emit('acceptChange', this.result)
      } catch (error) {
        this.$toast.error(error)
      } finally {
        this.loading(false)
      }
    },

    async updateNgayThanhToan (apiBody) {
      try {
        const response = await XLAPI.updateNgayThanhToan(this.axios, apiBody)
        const data = await response.data
        return data.error_code === 'BSS-00000000'
      } catch (error) {
        this.$toast.error('Không thể cập nhật ngày thanh toán của phiếu ' + apiBody.pPhieuId + ' : ' + error.data.message_detail)
        return false
      } finally {}
    }
  },
  watch: {
    dsPhieu: {
      handler: function (new_value, old_value) {
        // console.log(new_value)
        if (new_value !== null)
          this.ngayTTCu = moment(new_value[0].NGAY_TT, 'DD/MM/YYYY').toDate()
      },
      deep: true
    }
  }
}
</script>
