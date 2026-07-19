<template>
  <div class="">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:;" @click="btnTraPhieu_Click"> <span class="icon one-file-arrow-right1"></span> Trả phiếu </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="info-row">
          <div class="key w110">Nội dung trả</div>
          <div class="value">
            <textarea rows="5" class="form-control" v-model="noidung_tra" />
          </div>
        </div>

        <div class="info-row">
          <div class="key w110">Ly do trả</div>
          <div class="value">
            <div class="select-custom">
              <ejs-combobox ref="cboTinh" :popupHeight="300" :filterType="'Contains'" :allowFiltering="true" :fields="fields" v-model="lydotra_id" :dataSource="lstLyDoTra"></ejs-combobox>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w110">Ghi chú</div>
          <div class="value">
            <textarea rows="5" class="form-control" v-model="ghichu" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import moment from 'moment/moment'
export default {
  name: 'frmTraPhieuCntt',
  props: {
    vhdtb_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    loaitb_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    ma_gd: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    ma_tb: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    tinh_tc: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    reload_grid: {
      type: [Function],
      defaul: null,
      require: true
    }
  },
  data() {
    return {
      noidung_tra: '',
      lydotra_id: 0,
      ghichu: '',
      fields: { text: 'lydotra', value: 'lydotra_id' },
      lstLyDoTra: [],
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        nguoi_cn: this.$root.token.getUserName(),
        ma_nd: this.$root.token.getUserName(),
        ma_tinh: this.$root.token.getMaTinh()
      }
    }
  },
  methods: {
    async traphieuit_close() {
      console.log(this.$refs)
      this.$refs.frmTraPhieuCNTT_IT.close()
      this.$refs.frmTraPhieuCNTT_IT.hide()
    },
    layDSLyDoTra() {
      console.log('sp_lay_ds_lydotra_vip popupGiaoPhieuCNTT')
      if (!this.tinh_tc) return
      try {
        return axios.get(`/web-thicong/khaibao_dichvu_net_mgw/sp_lay_ds_lydotra_vip?phanvung_id=${this.tinh_tc}`)
      } catch (e) {
        throw e
      }
    },
    traPhieuCNTT() {
      try {
        return axios.post('/web-thicong/dvcntt/tudong_tra_phieu_it_cntt', {
          phanvung_id: this.tinh_tc,
          hdtb_id: this.vhdtb_id,
          ghichu: this.ghichu,
          noidung_tra: this.noidung_tra,
          huonggiao_vipnet_it: 44444,
          vma_nd: this.tt_nd.ma_nd,
          lydotra_id: this.lydotra_id
        })
      } catch (e) {
        throw e
      }
    },
    async formLoad() {
      try {
        let kq = await this.layDSLyDoTra()
        if (kq.data.data) {
          this.lstLyDoTra = kq.data.data
        } else {
          throw Error(JSON.stringify(kq))
        }
      } catch (e) {
        let loi = JSON.stringify(e)
        // this.$toast.error(loi);
      }
    },
    async btnTraPhieu_Click() {
      try {
        let kq = await this.traPhieuCNTT()
        if (kq.data.data) {
          this.$toast.success('Trả phiếu thành công')
          this.$emit('traphieuit_close')
          console.log(JSON.stringify(kq))
        } else {
          throw Error(JSON.stringify(kq))
        }
      } catch (e) {
        let loi = JSON.stringify(e)
        this.$toast.error(loi)
      }
    },
    resetForm() {
      this.lstLyDoTra = []
      this.lydotra_id = 0
      this.noidung_tra = ''
    }
  },
  mounted() {
    this.formLoad()
  }
}
</script>

<style scoped></style>
