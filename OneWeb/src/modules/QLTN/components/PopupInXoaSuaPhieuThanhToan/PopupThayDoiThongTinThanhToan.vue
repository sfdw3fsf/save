<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgThayDoiThongTinTT"
        header="Thay đổi thông tin thanh toán"
        :animationSettings='animationSettings'
        showCloseIcon=true width='80%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
    >
            <div class="">
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="javascript: void(0)" @click="onClickAccept" title="Chấp nhận">
                                <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
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
                        <div class="legend-title">
                            <div class="pull-left">
                                <div class="check-action check-color">
                                    <input type="checkbox" checked="" class="check" v-model="thongTinTT.thanhToanChecked">
                                    <span class="name" style="font-weight: 600!important">Thông tin thanh toán mới</span>
                                </div>
                            </div>
                            <div class="pull-right normal">
                                <span class="one-alert text-warning f20 inline vcenter"></span>
                                <span class="text-main italic normal">Click chọn checkbox nếu chắc chắn muốn thay đổi thông tin</span>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w110">Lượt TT</div>
                                    <div class="value">
                                        <vue-numeric currency='' separator=',' v-model="thongTinTT.lanTra" class="form-control tright" :disabled="!thongTinTT.thanhToanChecked"></vue-numeric>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-9 col-12">
                                <div class="info-row">
                                    <div class="key w80">Thu ngân</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select class="form-control" :disabled="!thongTinTT.thanhToanChecked" v-model="thongTinTT.thuNganId">
                                                <option v-for="item in danhSachNhanVienThuNganGetter" :value="item.NHANVIEN_ID" :key="item.NHANVIEN_ID">{{ item.TEN_NV }}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key w110">Hình thức</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select2
                                                :options = "hinhThucTT_Options"
                                                v-model = "thongTinTT.HTTTId"
                                                :disabled = "!thongTinTT.thanhToanChecked"
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            <div class="pull-left">
                                <div class="check-action check-color">
                                    <input type="checkbox" checked="" class="check" v-model="thongTinTT.chuyenKhoanChecked">
                                    <span class="name" style="font-weight: 600!important">Thông tin chuyển khoản mới</span>
                                </div>
                            </div>
                            <div class="pull-right normal">
                                <span class="one-alert text-warning f20 inline vcenter"></span>
                                <span class="text-main italic normal">Click chọn checkbox nếu chắc chắn muốn thay đổi thông tin</span>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w110">Ngày ngân hàng</div>
                                    <div class="value">
                                        <ejs-datepicker
                                            v-model="thongTinTT.ngayNganHang"
                                            :value="today"
                                            :format="'dd/MM/y'"
                                            :enabled="thongTinTT.chuyenKhoanChecked"
                                            :showClearButton="false"
                                        >
                                        </ejs-datepicker>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w80">Chứng từ</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="thongTinTT.chungTu" :disabled="!thongTinTT.chuyenKhoanChecked" ref="chungTu" :class="{'is-invalid': $v.thongTinTT.chungTu.$error }">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w80">Chi phí</div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <vue-numeric currency='' separator=',' v-model="thongTinTT.chiPhi" class="form-control tright tien-tra font-weight-bold" :disabled="!thongTinTT.chuyenKhoanChecked"></vue-numeric>
                                            <span class="icon one-calculator" @click="isCalculatorShow = !isCalculatorShow && thongTinTT.chuyenKhoanChecked"></span>
                                            <Calculator @display="thongTinTT.chiPhi = $event" :value="thongTinTT.chiPhi" :is-show="isCalculatorShow" @close="isCalculatorShow = !isCalculatorShow"></Calculator>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key w110">Ngân hàng</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select2
                                                :options = "nganhang_options"
                                                v-model = "thongTinTT.nganHangId"
                                                :disabled = "!thongTinTT.chuyenKhoanChecked"
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            <div class="pull-left">
                                <div class="check-action check-color">
                                    <input type="checkbox" checked="" class="check" v-model="thongTinTT.ghiChuChecked">
                                    <span class="name" style="font-weight: 600!important">Thông tin ghi chú mới</span>
                                </div>
                            </div>
                            <div class="pull-right normal">
                                <span class="one-alert text-warning f20 inline vcenter"></span>
                                <span class="text-main italic normal">Click chọn checkbox nếu chắc chắn muốn thay đổi thông tin</span>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key w110">Ghi chú</div>
                                    <div class="value">
                                        <textarea name="" id="" cols="30" rows="10"
                                            v-model="thongTinTT.ghiChu"
                                            :disabled="!thongTinTT.ghiChuChecked"
                                            class="form-control"
                                            style="height: 160px;resize: vertical;"
                                            ref="ghiChu"
                                            :class="{'is-invalid': $v.thongTinTT.ghiChu.$error }">
                                        </textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" v-show="kiemtra_matkhau">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w110">Xác nhận MK</div>
                                    <div class="value">
                                        <input :type="typeInput" class="form-control" v-model="thongTinTT.matKhau" autofocus ref="matKhau" :class="{'is-invalid': $v.thongTinTT.matKhau.$error }">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="note mart5">
                                    <span class="one-alert text-warning f20 inline vcenter"></span>
                                    <span class="red italic normal">Chỉ được chuyển trong một kỳ hóa đơn, yêu cầu xác nhận mật khẩu</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </ejs-dialog>
</template>
<style scoped src="../../TraCuuPhieuTraTheoNgay/TraCuuPhieuTraTheoNgay.scss"></style>
<script>
import API from '../../api/TraCuuPhieuTraTheoNgayAPI.js'
import { mapGetters, mapActions } from 'vuex'
import { required, maxLength } from 'vuelidate/lib/validators'
import Calculator from '@/components/Calculator'
import CommonsAPI from '../../api/Commons'
import moment from 'moment'
export default {
  components: { Calculator },
  name: 'PopupThayDoiThongTinThanhToan',

  props: {
    phieu: {
      type: Object
    },
    kiemtra_matkhau: {
      type: Boolean,
      default: true
    }
  },

  data () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      today: new Date(),
      isCalculatorShow: false,
      getInput: null,
      typeInput: 'text',
      nganhang_options: [],
      thongTinTT: {
        thanhToanChecked: false,
        chuyenKhoanChecked: false,
        ghiChuChecked: false,
        ghiChu: '',
        HTTTId: 0,
        lanTra: 0,
        chungTu: '',
        chiPhi: 0,
        ngayNganHang: new Date(),
        nganHangId: 0,
        thuNganId: 0,
        matKhau: ''
      }
    }
  },

  validations: {
    thongTinTT: {
      chungTu: {
        maxLength: maxLength(50)
      },
      ghiChu: {
        maxLength: maxLength(500)
      },
      matKhau: {
        required,
        maxLength: maxLength(50)
      }
    }
  },

  computed: {
    ...mapGetters('qltnCommon', [
      // 'danhSachNganHangGetter',
      'danhSachHinhThucThanhToanGetter',
      'danhSachNhanVienThuNganGetter'
    ]),

    dsHTTT () {
      return this.danhSachHinhThucThanhToanGetter.filter(item => item.NHOM_HTTT_ID === this.thongTinTT.nhomHTTTId)
    },

    // nganHang_Options () {
    //   let ds = [];
    //   this.danhSachNganHangGetter.forEach(item => {
    //     ds.push({ id: item.NGANHANG_ID, text: item.TEN_NH });
    //   });
    //   return ds;
    // },

    hinhThucTT_Options () {
      let ds = []
      this.danhSachHinhThucThanhToanGetter.forEach(item => {
        ds.push({ id: item.HTTT_ID, text: item.HTTT })
      })
      return ds
    }
  },

  methods: {
    ...mapActions('qltnCommon', [
    //   'getDanhSachNganHang',
      'getDanhSachHinhThucThanhToan',
      'getDanhSachNhanVienThuNgan'
    ]),

    dialogOpen () {
      this.typeInput = 'password'
    },

    dialogClose () {
      this.typeInput = 'text'
    },

    async openDialog () {
      this.getInput = this.phieu
      if (this.nganhang_options.length <= 0) {
        this.nganhang_options = await this.getDSNganHang()
      }
      this.$refs.dlgThayDoiThongTinTT.show()
    },

    close () {
      this.getInput = null
      this.thongTinTT = {
        thanhToanChecked: false,
        chuyenKhoanChecked: false,
        ghiChuChecked: false,
        ghiChu: '',
        HTTTId: 0,
        lanTra: 0,
        chungTu: '',
        chiPhi: 0,
        ngayNganHang: new Date(),
        nganHangId: 0,
        thuNganId: 0,
        matKhau: ''
      },
      this.$v.$reset()
      this.$refs.dlgThayDoiThongTinTT.hide()
    },

    async getDSNganHang () {
      try {
        const res = await CommonsAPI.getDSNganHangGachNo(this.axios)
        const data = await res.data

        let ds = []

        if (data.error_code === 'BSS-00000000' && data.data !== undefined) {
           if (data.data.length > 0) {
            data.data.forEach(item => {
                ds.push({ id: item.NGANHANG_ID, text: item.TEN_NH })
            })
           }
        }
        return ds
      } catch (error) {
        if (error.data !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else {
          this.$toast.error(error.data)
        }
        return []
      } finally {}
    },

    validateData () {
      this.$v.$touch()
      let errorMessage = []
      let focusInput = []

      if (!this.$v.thongTinTT.chungTu.maxLength) {
        errorMessage.push('Trường chứng từ dữ liệu vượt quá độ dài cho phép!')
        focusInput.push('chungTu')
      }

      if (!this.$v.thongTinTT.ghiChu.maxLength) {
        errorMessage.push('Trường ghi chú dữ liệu vượt quá độ dài cho phép!')
        focusInput.push('ghiChu')
      }


      if (this.kiemtra_matkhau) {
        if (!this.$v.thongTinTT.matKhau.required) {
          errorMessage.push('Trường mật khẩu bắt buộc nhập dữ liệu!')
          focusInput.push('matKhau')
        } else if (!this.$v.thongTinTT.matKhau.maxLength) {
          errorMessage.push('Trường mật khẩu dữ liệu vượt quá độ dài cho phép!')
          focusInput.push('matKhau')
        }
      }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\r\n'))
        this.$refs[focusInput[0]].focus()
        return false
      } else return true
    },

    async onClickAccept () {
      if (this.validateData()) {
        if (this.kiemtra_matkhau) {
          let rs = await this.postKiemTraMatKhau()
          if (!rs) {
            this.$refs.matKhau.focus()
            return this.$toast.error('Mật khẩu xác nhận không đúng, vui lòng kiểm tra lại!')
          }
        }
      } else return

      this.$emit('acceptChange', this.thongTinTT)
      this.close()
    },

    async postKiemTraMatKhau () {
      try {
        this.loading(true)
        let apiBody = {
          password: this.thongTinTT.matKhau
        }
        let response = await API.postKiemTraMatKhau(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000' && response.data.data != null) {
          return response.data.data.tontai === 1
        } return false
      }
      catch(error) {
        console.log(error)
        return false
      }
      finally {
        this.loading(false)
      }
    },
  },

  async created () {
    // if (this.danhSachNganHangGetter == null || this.danhSachNganHangGetter.length === 0)
    //   await this.getDanhSachNganHang();

    if (this.danhSachHinhThucThanhToanGetter == null || this.danhSachHinhThucThanhToanGetter.length === 0)
      await this.getDanhSachHinhThucThanhToan()

    if (this.danhSachNhanVienThuNganGetter == null || this.danhSachNhanVienThuNganGetter.length === 0)
      await this.getDanhSachNhanVienThuNgan()
  },
  watch: {
    getInput : {
      handler: function (new_value, old_value) {
        if (new_value) {
          this.thongTinTT.ghiChu = new_value.GHICHU,
          this.thongTinTT.HTTTId = new_value.HTTT_ID,
          this.thongTinTT.lanTra = new_value.LANTRA,
          this.thongTinTT.chungTu = new_value.CHUNGTU,
          this.thongTinTT.chiPhi = new_value.CHIPHI,
          this.thongTinTT.ngayNganHang = new_value.NGAYNGANHANG ? moment(new_value.NGAYNGANHANG, 'DD/MM/YYYY hh:mm:ss').toDate() : new Date(),
          this.thongTinTT.nganHangId = new_value.NGANHANG_ID,
          this.thongTinTT.thuNganId = this.danhSachNhanVienThuNganGetter.length > 0 ? this.danhSachNhanVienThuNganGetter[0].NHANVIEN_ID : 0,
          this.thongTinTT.matKhau = ''
        }
      }
    },
  }
}
</script>
