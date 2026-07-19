<template>
  <div>
    <div>
      <!-- <breadcrumb :header="header" /> -->
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="tsbtnThemMoi.visible" :class="{ disabledInput: !tsbtnThemMoi.enabled }" @click="tsbtnThemMoi_Click()">
            <a> <span class="icon one-search"></span>Thêm mới</a>
          </li>
          <li v-if="tsbtnCapNhat.visible" :class="{ disabledInput: !tsbtnCapNhat.enabled }" @click="tsbtnCapNhat_Click()">
            <a> <span class="icon one-search"></span>Cập nhật</a>
          </li>
          <li v-if="tsbtnXoa.visible" :class="{ disabledInput: !tsbtnXoa.enabled }" @click="tsbtnXoa_Click()">
            <a> <span class="icon one-search"></span>Xóa</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            Thông tin nhận cảnh báo hệ thống
          </div>
          <div class="row">
            <!-- v-if="gridCanhBao.value.hasOwnProperty('tai_khoan')" -->
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Tài khoản
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="gridCanhBao.value.tai_khoan" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  SĐT
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="gridCanhBao.value.so_dien_thoai_tai_khoan" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Tên tài khoản
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="gridCanhBao.value.ten_tai_khoan" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Email
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="gridCanhBao.value.email_tai_khoan" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Vai trò
                </div>
                <div class="value">
                  <SelectExt ref="cboVaiTro" v-model="gridCanhBao.value.vai_tro" :dataSource="cboVaiTro.list" dataTextField="text" dataValueField="id" />
                  <!-- <input type="text" class="form-control" v-model="gridCanhBao.value.vai_tro" /> -->
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Địa chỉ
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="gridCanhBao.value.dia_chi" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  CCCD
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="gridCanhBao.value.cccd" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Trạng thái
                </div>
                <div class="value">
                  <el-switch v-model="gridCanhBao.value.trang_thai" :active-value="1" :inactive-value="0"> </el-switch>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Luật bắt buộc
                </div>
                <div class="value">
                  <el-switch v-model="gridCanhBao.value.luat_bat_buoc" :active-value="1" :inactive-value="0"> </el-switch>
                </div>
              </div>
            </div>

            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Cảnh báo cháy
                </div>
                <div class="value">
                  <el-switch v-model="gridCanhBao.value.canh_bao_chay" :active-value="1" :inactive-value="0"> </el-switch>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Cảnh báo thiết bị
                </div>
                <div class="value">
                  <el-switch v-model="gridCanhBao.value.canh_bao_thiet_bi" :active-value="1" :inactive-value="0"> </el-switch>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Kiểm tra định kỳ
                </div>
                <div class="value">
                  <el-switch v-model="gridCanhBao.value.kiem_tra_dinh_ky" :active-value="1" :inactive-value="0"> </el-switch>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Cảnh báo SMS
                </div>
                <div class="value">
                  <el-switch v-model="gridCanhBao.value.canh_bao_sms" :active-value="1" :inactive-value="0"> </el-switch>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Cảnh báo call
                </div>
                <div class="value">
                  <el-switch v-model="gridCanhBao.value.canh_bao_call" :active-value="1" :inactive-value="0"> </el-switch>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Cảnh báo app
                </div>
                <div class="value">
                  <el-switch v-model="gridCanhBao.value.canh_bao_app" :active-value="1" :inactive-value="0"> </el-switch>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Cảnh báo email
                </div>
                <div class="value">
                  <el-switch v-model="gridCanhBao.value.canh_bao_email" :active-value="1" :inactive-value="0"> </el-switch>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Cảnh báo hệ thống</div>
          <div class="table-content">
            <DataGrid ref="gridCanhBao" :columns="gridCanhBao.header" :dataSource="gridCanhBao.list" :showFilter="true" dataKeyField="tai_khoan" :allowPaging="true" :enablePagingServer="false" @selectedRowChanged="gridCanhBao_FocusedRowChanged" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import api from './api'

export default {
  components: { breadcrumb },
  emits: ['onBtnHoanThanhClicked'],
  props: {
    dulieu: {
      type: [Object],
      require: true
    }
  },
  data() {
    return {
      localLoading: 0,
      header: {
        title: 'ĐƠN HÀNG ĐÃ NGHIỆM THU',
        list: [
          {
            name: 'Quản lí vật tư',
            link: { name: 'Ui.buttons' }
          },
          {
            name: 'Đơn hàng đã nghiệm thu',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      // 0 - idle || 1 - thêm mới || 2 - cập nhật
      formState: 0,
      tsbtnThemMoi: { enabled: true, visible: true },
      tsbtnCapNhat: { enabled: true, visible: true },
      tsbtnXoa: { enabled: true, visible: true },
      cboVaiTro: { list: [] },
      gridCanhBao: {
        header: [
          { fieldName: 'tai_khoan', headerText: 'Tài khoản', allowFiltering: true },
          { fieldName: 'so_dien_thoai_tai_khoan', headerText: 'SĐT', allowFiltering: true },
          { fieldName: 'ten_tai_khoan', headerText: 'Tên tài khoản', allowFiltering: true },
          { fieldName: 'email_tai_khoan', headerText: 'Email', allowFiltering: true, textAlign: 'Left' },
          { fieldName: 'vaitro_', headerText: 'Vai trò', allowFiltering: true, textAlign: 'Left' },
          { fieldName: 'trang_thai_', headerText: 'Trạng thái', allowFiltering: true, textAlign: 'Left' },
          { fieldName: 'luat_bat_buoc_', headerText: 'Luật bắt buộc', allowFiltering: true },
          { fieldName: 'dia_chi', headerText: 'Địa chỉ', allowFiltering: true },
          { fieldName: 'cccd', headerText: 'CCCD', allowFiltering: true },
          { fieldName: 'canh_bao_chay_', headerText: 'Cảnh báo cháy', allowFiltering: true },
          { fieldName: 'canh_bao_thiet_bi_', headerText: 'Cảnh báo thiết bị', allowFiltering: true },
          { fieldName: 'kiem_tra_dinh_ky_', headerText: 'Kiểm tra định kỳ', allowFiltering: true },
          { fieldName: 'canh_bao_sms_', headerText: 'Cảnh báo SMS', allowFiltering: true },
          { fieldName: 'canh_bao_call_', headerText: 'Cảnh báo call', allowFiltering: true },
          { fieldName: 'canh_bao_app_', headerText: 'Cảnh báo app', allowFiltering: true },
          { fieldName: 'canh_bao_email_', headerText: 'Cảnh báo email', allowFiltering: true }
        ],
        list: [],
        value: {
          tai_khoan: '',
          so_dien_thoai_tai_khoan: '',
          ten_tai_khoan: '',
          email_tai_khoan: '',
          vai_tro: 0,
          trang_thai: 1,
          luat_bat_buoc: 0,
          dia_chi: '',
          cccd: '',
          canh_bao_chay: 1,
          canh_bao_thiet_bi: 1,
          kiem_tra_dinh_ky: 1,
          kiem_tra_nut_nhan: 1,
          canh_bao_sms: 1,
          canh_bao_call: 0,
          canh_bao_app: 1,
          canh_bao_email: 1
        }
      }
    }
  },
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
    formState(val) {
      console.log('formState val')
      this.tsbtnThemMoi.enabled = !(val == 1)
    }
  },
  async mounted() {
    console.log('frmNhanCanhBaoHeThong mounted')
    console.log(this.dulieu)
    this.cboVaiTro.list = [
      { id: 1, text: 'Chủ cơ sở' },
      { id: 2, text: 'Tổ trưởng PCCC' },
      { id: 3, text: 'Tổ viên PCCC' }
    ]
    await this.sp_lay_tt_tai_khoan_canh_bao()
  },
  computed: {},
  methods: {
    async callApiWrapper(action, body, options = { showError: true }) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data ? result.data.data : null
      } catch (e) {
        if (options.showError) {
          if (e.data != null) {
            this.$toast.error(`${e.data.message}`)
          } else {
            this.$toast.error(`Có lỗi gọi API`)
          }
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    async tsbtnThemMoi_Click() {
      console.log('tsbtnThemMoi_Click')
      console.log(this.gridCanhBao.value)
      this.gridCanhBao.value = {
        tai_khoan: '',
        so_dien_thoai_tai_khoan: '',
        ten_tai_khoan: '',
        email_tai_khoan: '',
        vai_tro: 0,
        trang_thai: 0,
        luat_bat_buoc: 0,
        dia_chi: '',
        cccd: '',
        canh_bao_chay: 0,
        canh_bao_thiet_bi: 0,
        kiem_tra_dinh_ky: 0,
        kiem_tra_nut_nhan: 0,
        canh_bao_sms: 0,
        canh_bao_call: 0,
        canh_bao_app: 0,
        canh_bao_email: 0
      }
      this.formState = 1
    },
    validateObject() {
      // if (!this.gridCanhBao.value.tai_khoan.length) {
      //   this.$toast.warning('Vui lòng điền Tài khoản!')
      //   return false
      // }
      // if (this.gridCanhBao.value.tai_khoan.length < 6) {
      //   this.$toast.warning('Tài khoản phải có tối thiểu 6 ký tự!')
      //   return false
      // }
      if (this.gridCanhBao.value.tai_khoan) {
        const val = this.gridCanhBao.value.tai_khoan
        const hasSpace = /\s/.test(val); // Kiểm tra dấu cách
        const hasDiacritics = /^[a-zA-Z0-9]+$/.test(val); // Kiểm tra dấu không dấu
        if (hasSpace || !hasDiacritics) {
          this.$toast.warning('Giá trị tài khoản không được chứa dấu cách và phải không dấu');
          return false
        }
      }
      if (!this.gridCanhBao.value.so_dien_thoai_tai_khoan.length) {
        this.$toast.warning('Vui lòng điền SĐT!')
        return false
      }
      if (!this.gridCanhBao.value.ten_tai_khoan.length) {
        this.$toast.warning('Vui lòng điền Tên tài khoản')
        return false
      }
      // if (!this.gridCanhBao.value.email_tai_khoan.length) {
      //   this.$toast.warning('Vui lòng điền Email')
      //   return false
      // }
      if (!this.gridCanhBao.value.vai_tro.length) {
        this.$toast.warning('Vui lòng Vai trò')
        return false
      }
      if (!this.gridCanhBao.value.dia_chi.length) {
        this.$toast.warning('Vui lòng điền Địa chỉ')
        return false
      }
      if (!this.gridCanhBao.value.cccd.length) {
        this.$toast.warning('Vui lòng điền CCCD')
        return false
      }
      return true
    },
    async tsbtnCapNhat_Click() {
      if (!this.validateObject()) return
      if (this.formState == 1) {
        await this.fn_update_hdtb_cntt_sub(1)
      } else {
        await this.fn_update_hdtb_cntt_sub(2)
      }
    },
    async tsbtnXoa_Click() {
      if (!confirm('Bạn có chắc chắn muốn xóa')) return
      await this.fn_update_hdtb_cntt_sub(3)
    },
    async sp_lay_tt_tai_khoan_canh_bao() {
      let kq = await this.callApiWrapper(api.sp_lay_tt_tai_khoan_canh_bao, {
        p_phanvung_id: this.$root.token.getPhanVungID(),
        hdtb_id: this.dulieu.vhdtb_id
      })
      console.log('sp_lay_tt_tai_khoan_canh_bao')
      console.log(kq)
      if (kq && kq.length) {
        this.gridCanhBao.list = kq.map((e) => {
          console.log('e.vai_tro')
          console.log(e.vai_tro)
          switch (e.vai_tro) {
            case '1':
              e.vaitro_ = 'Chủ cơ sở'
              break
            case '2':
              console.log('e.vaitro_a')
              e.vaitro_ = 'Tổ trưởng PCCC'
              break
            case '3':
              e.vaitro_ = 'Tổ viên PCCC'
              break
          }
          e.trang_thai_ = e.trang_thai == 1 ? 'Bật' : 'Tắt'
          e.luat_bat_buoc_ = e.luat_bat_buoc == 1 ? 'Bật' : 'Tắt'
          e.canh_bao_chay_ = e.canh_bao_chay == 1 ? 'Bật' : 'Tắt'
          e.canh_bao_thiet_bi_ = e.canh_bao_thiet_bi == 1 ? 'Bật' : 'Tắt'
          e.kiem_tra_dinh_ky_ = e.kiem_tra_dinh_ky == 1 ? 'Bật' : 'Tắt'
          e.kiem_tra_nut_nhan_ = e.kiem_tra_nut_nhan == 1 ? 'Bật' : 'Tắt'
          e.canh_bao_sms_ = e.canh_bao_sms == 1 ? 'Bật' : 'Tắt'
          e.canh_bao_call_ = e.canh_bao_call == 1 ? 'Bật' : 'Tắt'
          e.canh_bao_app_ = e.canh_bao_app == 1 ? 'Bật' : 'Tắt'
          e.canh_bao_email_ = e.canh_bao_email == 1 ? 'Bật' : 'Tắt'
          return e
        })
        console.log(this.gridCanhBao.list)
        this.gridCanhBao.value = kq[0]
      } else {
        this.gridCanhBao.list = []
        this.gridCanhBao.value = {
          tai_khoan: '',
          so_dien_thoai_tai_khoan: '',
          ten_tai_khoan: '',
          email_tai_khoan: '',
          vai_tro: 0,
          trang_thai: 0,
          luat_bat_buoc: 0,
          dia_chi: '',
          cccd: '',

          canh_bao_chay: 1,
          canh_bao_thiet_bi: 1,
          kiem_tra_dinh_ky: 1,
          kiem_tra_nut_nhan: 1,
          canh_bao_sms: 1,
          canh_bao_call: 0,
          canh_bao_app: 1,
          canh_bao_email: 1
        }
        this.tsbtnThemMoi_Click()
      }
    },
    async fn_update_hdtb_cntt_sub(thaotac) {
      let kq = await this.callApiWrapper(api.fn_update_hdtb_cntt_sub, {
        hdtb_id: this.dulieu.vhdtb_id,
        phanvung_id: this.$root.token.getPhanVungID(),
        loaitb_id: 376,
        thongtin_bang: 'tai_khoan_canh_bao',
        thaotac: thaotac,
        json_input: JSON.stringify(this.gridCanhBao.value)
      })
      if (kq == 'OK') {
        switch (thaotac) {
          case 1:
            this.$toast.success('Thêm mới thành công')
            break
          case 2:
            this.$toast.success('Cập nhật thành công')
            break
          case 3:
            this.$toast.success('Xóa thành công')
            break
        }
        await this.sp_lay_tt_tai_khoan_canh_bao()
      } else {
        this.$toast.error(`${kq}`)
      }
    },
    async gridCanhBao_FocusedRowChanged(e) {
      console.log('gridCanhBao_FocusedRowChanged')
      console.log(e)
      this.gridCanhBao.value = e
      this.formState = 2
      // try {
      if (e) {
      } else {
      }
      // } catch (ex) {
      //   this.$toast.error(`${ex}`)
      // }
    },
    //helper function
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    }
  }
}
</script>
<style scoped>
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}

.disabledInput > a {
  color: #666 !important;
}
</style>
