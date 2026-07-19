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
            Thông tin số thuê bao M2M
          </div>
          <div class="row">
            <!-- v-if="gridSoThueBaoM2M.value.hasOwnProperty('tai_khoan')" -->
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Trạng thái SIM
                </div>
                <div class="value">
                  <el-switch v-model="gridSoThueBaoM2M.value.trang_thai_sim" :active-value="1" :inactive-value="0"> </el-switch>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Số TB M2M
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="gridSoThueBaoM2M.value.so_thue_bao_m2m" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  SIM IMEI
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="gridSoThueBaoM2M.value.sim_imei" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  SIM Serial
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="gridSoThueBaoM2M.value.sim_serial" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Giá gói cước
                </div>
                <div class="value">
                  <input type="text"
                         class="form-control"
                         v-model="gridSoThueBaoM2M.value.gia_goi_cuoc"
                         @input="validateNumberInput"/>
                </div>
              </div>
            </div>

            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Nhà mạng
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="gridSoThueBaoM2M.value.nha_mang" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Ngày bắt đầu
                </div>
                <div class="value">
                  <vue-date v-model="gridSoThueBaoM2M.value.ngay_bat_dau_goi" format="DD/MM/YYYY hh:mm:ss" type="datetime" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  Ngày kết thúc
                </div>
                <div class="value">
                  <vue-date v-model="gridSoThueBaoM2M.value.ngay_ket_thuc_goi" format="DD/MM/YYYY hh:mm:ss" type="datetime" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Số thuê bao M2M</div>
          <div class="table-content">
            <DataGrid ref="gridSoThueBaoM2M" :columns="gridSoThueBaoM2M.header" :dataSource="gridSoThueBaoM2M.list" :showFilter="true" dataKeyField="tai_khoan" :allowPaging="true" :enablePagingServer="false" @selectedRowChanged="gridSoThueBaoM2M_FocusedRowChanged" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import Vue from 'vue'
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
      gridSoThueBaoM2M: {
        header: [
          { fieldName: 'trang_thai_sim_', headerText: 'Trạng thái SIM', allowFiltering: true },
          { fieldName: 'so_thue_bao_m2m', headerText: 'Số TB M2M', allowFiltering: true },
          { fieldName: 'sim_imei', headerText: 'SIM IMEI', allowFiltering: true },
          { fieldName: 'sim_serial', headerText: 'SIM Serial', allowFiltering: true, textAlign: 'Left' },
          { fieldName: 'gia_goi_cuoc', headerText: 'Giá gói cước', allowFiltering: true, textAlign: 'Left' },
          { fieldName: 'ngay_bat_dau_goi', headerText: 'Ngày bắt đầu', allowFiltering: true, textAlign: 'Left' },
          { fieldName: 'ngay_ket_thuc_goi', headerText: 'Ngày kết thúc', allowFiltering: true },
          { fieldName: 'nha_mang', headerText: 'Nhà mạng', allowFiltering: true }
        ],
        list: [],
        value: {
          trang_thai_sim: 1,
          so_thue_bao_m2m: '',
          sim_imei: '',
          sim_serial: '',
          gia_goi_cuoc: '',
          ngay_bat_dau_goi: null,
          ngay_ket_thuc_goi: null,
          nha_mang: '' // Vinaphone
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
    console.log('frmSoThueBaoM2M mounted')
    console.log(this.dulieu)
    await this.sp_lay_tt_thong_tin_sim_m2m()
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
      this.gridSoThueBaoM2M.value = {
        trang_thai_sim: 0,
        so_thue_bao_m2m: '',
        sim_imei: '',
        sim_serial: '',
        gia_goi_cuoc: '',
        ngay_bat_dau_goi: null,
        ngay_ket_thuc_goi: null,
        nha_mang: '' // Vinaphone
      }
      this.formState = 1
    },
    validateObject() {
      if (!this.gridSoThueBaoM2M.value.so_thue_bao_m2m.length) {
        this.$toast.warning('Vui lòng điền Số TB M2M!')
        return false
      }
      if (!this.gridSoThueBaoM2M.value.sim_imei.length) {
        this.$toast.warning('Vui lòng điền SIM IMEI!')
        return false
      }
      if (!this.gridSoThueBaoM2M.value.sim_serial.length) {
        this.$toast.warning('Vui lòng điền SIM Serial')
        return false
      }
      if (!this.gridSoThueBaoM2M.value.gia_goi_cuoc.length) {
        this.$toast.warning('Vui lòng điền Giá gói cước')
        return false
      }
      if (!this.gridSoThueBaoM2M.value.nha_mang.length) {
        this.$toast.warning('Vui lòng điền Nhà mạng')
        return false
      }
      if (!this.gridSoThueBaoM2M.value.ngay_bat_dau_goi.length) {
        this.$toast.warning('Vui lòng điền Ngày bắt đầu')
        return false
      }
      if (!this.gridSoThueBaoM2M.value.ngay_ket_thuc_goi.length) {
        this.$toast.warning('Vui lòng điền Ngày kết thúc')
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
    async sp_lay_tt_thong_tin_sim_m2m() {
      let kq = await this.callApiWrapper(api.sp_lay_tt_thong_tin_sim_m2m, {
        p_phanvung_id: this.$root.token.getPhanVungID(),
        hdtb_id: this.dulieu.vhdtb_id
      })
      console.log('sp_lay_tt_thong_tin_sim_m2m')
      console.log(kq)
      if (kq && kq.length) {
        this.gridSoThueBaoM2M.list = kq.map((e) => {
          e.trang_thai_sim_ = e.trang_thai_sim == 1 ? 'Đã kích hoạt' : 'Chưa kích hoạt'
          return e
        })
        console.log(this.gridSoThueBaoM2M.list)
        // this.gridSoThueBaoM2M.value = kq[0]
      } else {
        this.gridSoThueBaoM2M.list = []
        this.tsbtnThemMoi_Click()
      }
    },
    async fn_update_hdtb_cntt_sub(thaotac) {
      let kq = await this.callApiWrapper(api.fn_update_hdtb_cntt_sub, {
        hdtb_id: this.dulieu.vhdtb_id,
        phanvung_id: this.$root.token.getPhanVungID(),
        loaitb_id: 376,
        thongtin_bang: 'thong_tin_sim_m2m',
        thaotac: thaotac,
        json_input: JSON.stringify(this.gridSoThueBaoM2M.value)
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
        await this.sp_lay_tt_thong_tin_sim_m2m()
      } else {
        this.$toast.error(`${kq}`)
      }
    },
    async gridSoThueBaoM2M_FocusedRowChanged(e) {
      console.log('gridSoThueBaoM2M_FocusedRowChanged')
      console.log(e)
      this.gridSoThueBaoM2M.value = e
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
    },
    validateNumberInput(event) {
      // Loại bỏ tất cả các ký tự không phải là số
      this.gridSoThueBaoM2M.value.gia_goi_cuoc = event.target.value.replace(/\D/g, '');
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
