<template>
  <div class="">
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tsbtnLammoi.visible" :class="{ disabledInput: !tsbtnLammoi.enabled }" @click="tsbtnLammoi_Click">
          <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Làm mới </a>
        </li>
        <li v-if="tsbtnLammoi.visible" :class="{ disabledInput: !tsbtnLammoi.enabled }" @click="tsbtnHuySerial_Click">
          <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Xóa serial </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-move-select-table">
        <div class="box-form ">
          <div class="info-row col-sm-4 col-12">
            <div class="key w60">
              Serial
            </div>
            <div class="value">
              <input type="text" class="form-control" v-model="txtSerial.value" @keyup.enter="txtSerial_KeyPress" />
            </div>
          </div>
        </div>
      </div>
      <div class="grid-stack-box">
        <div class="box-col box-form col-sm-6 col-12">
          <div class="legend-title">
            <div class="pull-left">Danh sách serial</div>
            <div class="clearfix"></div>
          </div>
          <div class="table-content">
            <DataGrid ref="gridSerial" dataKeyField="" :columns="gridSerial.header" :dataSource="gridSerial.list" :showFilter="true" @selectedRowChanged="gridSerialSelectRow" :allowPaging="true" :enablePagingServer="false" />
          </div>
        </div>

        <div class="box-col box-form col-sm-6 col-12">
          <div class="legend-title">
            <div class="pull-left">Danh sách serial đã quét</div>
            <div class="clearfix"></div>
          </div>
          <div class="table-content">
            <DataGrid ref="gridScan" dataKeyField="" :columns="gridScan.header" :dataSource="gridScan.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" />
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
  props: {
    vchungtu_id: {
      type: [String, Number],
      default: false,
      required: false
    }
  },
  data() {
    return {
      localLoading: 0,
      tsbtnLammoi: { visible: true, enabled: true },
      tsbtnHuySerial: { visible: true, enabled: true },
      gridSerial: {
        header: [
          { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
          { fieldName: 'SERIAL', headerText: 'Serial', allowFiltering: true },
          { fieldName: 'TRANGTHAITBI_ID', headerText: 'Đối soát', allowFiltering: true }
        ],
        list: []
      },
      serialSelectRow: {},
      gridScan: {
        header: [
          { fieldName: 'SERIAL', headerText: 'Serial', allowFiltering: true },
          { fieldName: 'ERROR', headerText: 'Lỗi', allowFiltering: true },
          { fieldName: 'NGAY_CN', headerText: 'Ngày CN', allowFiltering: true }
        ],
        list: []
      },
      txtSerial: { visible: true, enabled: true, value: '' }
    }
  },
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    }
  },
  computed: {},
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data != null ? result.data.data : null
      } catch (e) {
        console.log('LOI ROI')
        console.log(e)
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    async frmDoiSoat_Load() {
      await this.lay_ds_serial()
    },
    async openDoiSoatSerial() {
      try {
        this.loading(true)
        await this.lay_ds_serial()
        this.$refs.popupDoiSoatSerial.show()
        this.loading(false)
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    async lay_ds_serial() {
      this.gridSerial.list = await this.callApiWrapper(api.ds_serial_chua_doi_soat, {
        kieu: 0,
        chungTuId: this.vchungtu_id,
        dieuKien: '0'
      })
    },
    async tsbtnLammoi_Click() {
      await this.lay_ds_serial()
    },
    gridSerialSelectRow(row) {
      if (row) {
        this.serialSelectRow = row
      }
    },
    async tsbtnHuySerial_Click() {
      try {
        if (this.gridSerial.list.length == 0) {
          this.$toast.error('Không có dữ liệu vật tư !')
          return
        }
        if (this.serialSelectRow == {}) {
          this.$toast.error('Bạn chưa focus dòng serial nào !')
          return
        }
        this.loading(true)
        var vserial = this.serialSelectRow.SERIAL
        var ds_serial = await this.callApiWrapper(api.ds_serial_chua_doi_soat, {
          kieu: 1,
          chungTuId: this.vchungtu_id,
          dieuKien: vserial
        })

        if (ds_serial.length == 0) {
          this.$toast.error('Không tìm thấy chứng từ (' + this.vchungtu_id + ')')
          this.loading(false)
          return
        }
        if (ds_serial[0].TTPH_ID == 4) {
          this.$toast.error('Chứng từ đã thay đổi trạng thái, hãy lấy lại TT chứng từ ! (4)')
          this.loading(false)
          return
        }
        if (ds_serial[0].TRANGTHAITBI_ID == 1) {
          this.$toast.error('Serial đã đối soát, không thể xóa !')
          this.loading(false)
          return
        }

        var capnhat_ = await this.callApiWrapper(api.cap_nhat_serial_doi_soat, {
          kieu: 1,
          chungTuId: this.vchungtu_id,
          serial: vserial,
          ctctId: this.serialSelectRow.CTCT_ID,
          thietBiId: this.serialSelectRow.THIETBI_ID
        })
        if (capnhat_ == '1') {
          this.$toast.success('Hủy serial: ' + vserial + ' thành công !')
          await this.lay_ds_serial()
        } else {
          this.$toast.error(capnhat_)
        }
        this.loading(false)
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    async txtSerial_KeyPress() {
      try {
        var vserial = this.txtSerial.value.trim()
        if (this.gridSerial.list.length == 0) {
          this.$toast.error('Không có dữ liệu vật tư để đối soát !')
          return
        }
        if (vserial == '') {
          this.$toast.error('Bạn phải nhập serial !')
          return
        }
        this.loading(true)
        var ds_serial = await this.callApiWrapper(api.ds_serial_chua_doi_soat, {
          kieu: 1,
          chungTuId: this.vchungtu_id,
          dieuKien: vserial
        })
        if (ds_serial.length == 0) {
          var err = 'Serial không có trong chứng từ !'
          this.$toast.error(err)
          this.gridScan.list.push({
            SERIAL: vserial,
            ERROR: err,
            NGAY_CN: this.f_DateToString(new Date())
          })
          this.txtSerial.value = ''
          this.loading(false)
          return
        }
        if (ds_serial[0].TTPH_ID == 4) {
          this.$toast.error('Chứng từ đã thay đổi trạng thái, hãy lấy lại TT chứng từ ! (4)')
          this.loading(false)
          return
        }
        if (ds_serial[0].TRANGTHAITBI_ID == 1) {
          var err = 'Serial đã đối soát, không thể đối soát tiếp !'
          this.$toast.error(err)
          this.gridScan.list.push({
            SERIAL: vserial,
            ERROR: err,
            NGAY_CN: this.f_DateToString(new Date())
          })
          this.loading(false)
          return
        }
        if (ds_serial[0].TRANGTHAITBI_ID == 0) {
          var capnhat_ = await this.callApiWrapper(api.cap_nhat_serial_doi_soat, {
            kieu: 0,
            chungTuId: this.vchungtu_id,
            serial: vserial,
            ctctId: 0,
            thietBiId: 0
          })
          if (capnhat_ == '1') {
            this.$toast.success('Đối soát thành công serial: ' + vserial)
            this.gridSerial.list.forEach((e) => {
              if (e.SERIAL == vserial) {
                e.TRANGTHAITBI_ID = 1
              }
            })
            this.$refs.gridSerial.grid.refreshColumns()
          }
        }
        this.txtSerial.value = ''
        this.loading(false)
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    lay_ds_loi() {}
  },
  async mounted() {
    await this.frmDoiSoat_Load()
  }
}
</script>
