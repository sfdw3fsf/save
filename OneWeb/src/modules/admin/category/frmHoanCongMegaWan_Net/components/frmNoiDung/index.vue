<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="btnGhiLai.visible" :class="{ disabledInput: !btnGhiLai.enabled }" @click="btnGhiLai_Click">
          <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Ghi lại </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <textarea name="" v-model="txtNoiDung.value" class="form-control" style="height: 300px; resize: none;" :readonly="!txtNoiDung.enabled"></textarea>
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
    vtientrinhtc_id: {
      type: [Number, String],
      default: null,
      required: true
    },
    row: {
      type: [Object],
      default: null,
      required: true
    }
  },
  data() {
    return {
      localLoading: 0,
      tinh_khdn: [97, 100],
      btnGhiLai: { visible: true, enabled: true },
      txtNoiDung: { value: '', visible: true, enabled: true }
    }
  },
  watch: {
    localLoading(val) {
      console.log('localLoading')
      console.log(val)
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
        this.localLoading--
      }
      return null
    },
    async btnGhiLai_Click() {
      let vhdtb_id = this.row.hdtb_tc
      let _tinh_id = this.row.tinh_id
      let tinh_tc_id = 0
      if (this.tinh_khdn.includes(_tinh_id)) tinh_tc_id = this.row.tinh_thicong
      else tinh_tc_id = this.row.tinh_id
      if (vhdtb_id < 0) {
        tinh_tc_id = 100
        vhdtb_id = this.row.hdtb_id
      }
      // Tinh_TC _tinh_tc = new BanCheoBase().tinh_thicong(tinh_tc_id);

      // let btnGhiLai = sender as ToolStripButton;
      // let frmNoiDung = btnGhiLai.Owner.FindForm();
      // let txtNoiDung = frmNoiDung.Controls.Find("txtNoiDung", true)[0] as System.Windows.Forms.TextBox;
      // gridviewTienTrinh.SetRowCellValue(gridviewTienTrinh.FocusedRowHandle, "NOIDUNG", txtNoiDung.Text);
      try {
        let vdonvi_id = -1
        let vnhanvien_id = -1
        let vnguoidung_id = -1
        // DataTable dt = bancheo.GET_DATA_OBJECT_PL(_tinh_tc, "{?DB2}.SP_TT_NGUOIDUNG", "vreturnds", "vma_nd", tt_nd.ma_nd);
        let dt = await this.callApiWrapper(api.sp_tt_nguoidung, {
          phanvung_id: this.$root.token.getPhanVungID(),
          ma_nd: this.$root.token.getUserName()
        })
        if (dt.length > 0) {
          vnhanvien_id = dt[0].nhanvien_id
          vnguoidung_id = dt[0].nguoidung_id
          dt = await this.callApiWrapper(api.sp_tt_nhanvien_2, {
            vnhanvien_id: vnhanvien_id
          })
          if (dt.length > 0) {
            vdonvi_id = dt[0].donvi_id
          }
        }
        // bancheo.EXECUTE_NON_QUERY(_tinh_tc, "{?DB2}.sp_capnhat_key_tientrinh_tc");

        let vtientrinhtc_id = this.vtientrinhtc_id
        let vnoidung = this.txtNoiDung.value
        let vngay_cn = this.$root.token.getNgayCapNhat()
        let vnguoi_cn = this.$root.token.getUserName()
        let vip_cn = await this.$root.token.getIP()

        if (vtientrinhtc_id == null) {
          // await this.callApiWrapper(api.insert_tientrinh_tc, {
          //   vphanvung_id: this.$root.token.getPhanVungID(),
          //   vhdtb_id: vhdtb_id,
          //   vdonvi_id: vdonvi_id,
          //   vnhanvien_id: vnhanvien_id,
          //   vnguoidung_id: vnguoidung_id,
          //   vnoidung: vnoidung,
          //   vngay_cn: vngay_cn,
          //   vnguoi_cn: vnguoi_cn,
          //   vip_cn: vip_cn
          // })
          let kq = await this.callApiWrapper(api.sp_khdn_capnhat_tientrinh_tc, {
            vphanvung_id: this.$root.token.getPhanVungID(),
            vtinh_id: this.row.tinh_id,
            vhdtb_id: this.row.hdtb_id,
            vtientrinhtc_id: this.vtientrinhtc_id,
            vnoidung: `${this.txtNoiDung.value}`
          })

          let kq_update_hdtb_tientrinh_tc = await this.callApiWrapper(api.update_hdtb_tientrinh_tc, {
            vphanvung_id: this.$root.token.getPhanVungID(),
            vhdtb_id: vhdtb_id
          })
          if (kq_update_hdtb_tientrinh_tc == 'ok') {
            this.$toast.success('Thành công')
          } else {
            this.$toast.error('Thất bại')
          }
          this.$emit('btnGhiLai_Click')
          // await this.NAP_GRD_TIENTRINH()
        } else {
          await this.callApiWrapper(api.update_tientrinh_tc, {
            vphanvung_id: this.row.tinh_id,
            vtientrinhtc_id: vtientrinhtc_id,
            vdonvi_id: vdonvi_id,
            vnhanvien_id: vnhanvien_id,
            vnguoidung_id: vnguoidung_id,
            vnoidung: vnoidung,
            vngay_cn: vngay_cn,
            vnguoi_cn: vnguoi_cn,
            vip_cn: vip_cn
          })
          this.$emit('btnGhiLai_Click')
          // await this.NAP_GRD_TIENTRINH()
        }
      } catch (ex) {
        this.$toast.error(`${ex}`)
      }
    }
  },
  async mounted() {
    console.log('this.txtNoiDung.value')
    console.log(this.row)
    if (this.vtientrinhtc_id != null) {
      this.txtNoiDung.value = this.row.noidung
    } else {
      if (this.row) {
        this.txtNoiDung.value = `${this.row.ma_tb}|${this.row.ten_tb}|${this.row.diachi_ld}`
      } else {
        this.txtNoiDung.value = ''
      }
    }
  }
}
</script>
