<template>
  <b-modal
    ref="popupChiTietTienTheoKhoanMuc"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="frmCT_Tien_KM_Load"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Chi tiết tiền theo khoản mục</div>
        <div class="close -ap icon-close" @click="hideModal">
        </div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="tsbtnChapNhan_Click">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Chi tiết tiền theo khoản mục</div>
          <div class="row" v-for="(item, index) in m_dsTienKM" :key="index">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w140">{{ item.tentat_kmtt }}</div>
                <div class="value">
                  <vue-numeric
                    class="form-control tright"
                    separator=","
                    placeholder="0"
                    output-type="Number"
                    :value="item.tongtien"
                    :disabled="item.flag===0"
                    @blur="onBlurTongTien(index)"
                    @change="onChangeTongTien($event, index)"
                  ></vue-numeric>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w40">Tiền</div>
                <div class="value">
                  <vue-numeric
                    class="form-control tright"
                    separator=","
                    placeholder="0"
                    output-type="Number"
                    :value="item.tien"
                    :disabled="item.flag===0"
                    @change="onChangeTien($event, index)"
                  ></vue-numeric>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w40">Vat</div>
                <div class="value">
                  <vue-numeric
                    class="form-control tright"
                    separator=","
                    placeholder="0"
                    output-type="Number"
                    disabled
                    :value="item.vat"
                    @change="onChangeVat($event, index)"
                  ></vue-numeric>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import api from '../api'
import { LoaiHopDong } from '../../../../../utils/Enum'
import { KHOANMUC_TT } from '../../../../../constants'

export default {
  name: 'TienKMCuPopup',
  props: {
    loaihd_id: {
      type: Number,
      default: 0
    },
    ds_tien_caidat: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      m_dsTienKM: [],
      KHONGXOA_TONGTIEN_TDTD: -1
    }
  },
  methods: {
    async frmCT_Tien_KM_Load () {
      this.loading(true)
      try {
        const ds = await this.getDanhSachThamSoMacDinh()
        if (ds.length > 0) {
          const khongXoaTongTien = this.getThamSoByMaThamSo(ds, 'KHONGXOA_TONGTIEN_TDTD')
          if (khongXoaTongTien && khongXoaTongTien.ten_ts.trim() === '1') this.KHONGXOA_TONGTIEN_TDTD = 1
        }
        await this.HT_GIAODIEN_KMTT()
        this.HT_TIEN_KHOANMUC()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachThamSoMacDinh () {
      try {
        const response = await api.getDanhSachThamSoMacDinh(this.axios, { p_kieu_id: -1 })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách tham số mặc định')
        return []
      }
    },
    getThamSoByMaThamSo (ds, maTS) {
      return ds.find(elm => elm.ma_ts === maTS)
    },
    async HT_GIAODIEN_KMTT () {
      this.m_dsTienKM = await this.lay_ds_tien_kmtt()
      if (this.KHONGXOA_TONGTIEN_TDTD === 1) {
        for (const element of this.m_dsTienKM) {
          if (this.loaihd_id === LoaiHopDong.THAY_DOI_TOCDO_ADSL && +element.khoanmuctt_id === KHOANMUC_TT.KMTT_DATCOC) {
            element.flag = 0
          }
        }
      }
    },
    async lay_ds_tien_kmtt () {
      try {
        const response = await api.getKhoanMucByLoaiHD(this.axios, { vloaihd_id: this.loaihd_id })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách thiết bị')
        return []
      }
    },
    HT_TIEN_KHOANMUC () {
      for (const km of this.m_dsTienKM) {
        for (const cd of this.ds_tien_caidat) {
          if (km.khoanmuctt_id.toString() === cd.khoanmuctt_id.toString()) {
            const tien = Math.abs(+cd.tien)
            const vat = Math.abs(+cd.vat)
            km.tien = tien
            km.vat = vat
            km.tongtien = tien + vat
          }
        }
      }
    },
    onChangeTongTien (event, index) {
      this.m_dsTienKM[index].tongtien = +event.target.value
    },
    onChangeTien (event, index) {
      const value = +event.target.value
      this.m_dsTienKM[index].tien = value
      this.m_dsTienKM[index].vat = Math.round(value / 10)
      this.m_dsTienKM[index].tongtien = Math.round(value * 11 / 10)
    },
    onChangeVat (event, index) {
      this.m_dsTienKM[index].vat = +event.target.value
    },
    onBlurTongTien (index) {
      this.m_dsTienKM[index].tien = Math.round(this.m_dsTienKM[index].tongtien * 10 / 11)
      this.m_dsTienKM[index].vat = Math.round(this.m_dsTienKM[index].tongtien / 11)
    },
    tsbtnChapNhan_Click () {
      this.$emit('accept', this.m_dsTienKM)
      this.hideModal()
    },
    showModal () {
      this.$refs.popupChiTietTienTheoKhoanMuc.show()
    },
    hideModal () {
      this.$refs.popupChiTietTienTheoKhoanMuc.hide()
    }
  }
}
</script>
