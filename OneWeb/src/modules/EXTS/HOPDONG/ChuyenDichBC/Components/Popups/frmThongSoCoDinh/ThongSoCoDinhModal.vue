<template>
    <b-modal
    ref="popupThongSoCoDinh"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Thông số cố định/IMS
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">

        <div class="grid-stack-box">
          <div class="box-col box-form" style="width: calc(50% - 8px);">
            <div class="legend-title">Thông tin dịch vụ tỉnh Bán</div>

            <div class="info-row">
                <div class="key w120"></div>
                <div class="value" style="height: 32px !important; padding: 0.375rem 0.75rem; font-size: 1rem;">
                </div>
            </div>
            <div class="info-row">
                <div class="key w120"></div>
                <div class="value" style="height: 32px !important; padding: 0.375rem 0.75rem; font-size: 1rem;">
                </div>
            </div>

            <div class="info-row">
                <div class="key w120">Công nghệ</div>
                <div class="value">
                    <div class="select-custom" ref="ds_congnghe_ban">
                      <select2 :settings="{ dropdownParent: $refs['ds_congnghe_ban'] }" ref="cboCongNgheBan"
                          v-model="thongso_bancheo.congnghe_id" class="select2"
                          :options="ds_congnghe_ban.map(e=> ({id: e.congnghe_id, text: e.congnghe}))"
                          >
                      </select2>
                  </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Đối tượng</div>
                <div class="value">
                  <div class="select-custom" ref="ds_doituong_ban">
                      <select2 :settings="{ dropdownParent: $refs['ds_doituong_ban'] }" ref="cboDoiTuongBan"
                          v-model="thongso_bancheo.doituong_id" class="select2"
                          :options="ds_doituong_ban.map(e=> ({id: e.doituong_id, text: e.ten_dt}))"
                          @change="changeDoiTuongBan"
                          >
                      </select2>
                  </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Trang bị</div>
                <div class="value">
                  <div class="select-custom" ref="ds_trangbi_ban">
                      <select2 :settings="{ dropdownParent: $refs['ds_trangbi_ban'] }" ref="cboTrangBiBan"
                          v-model="thongso_bancheo.trangbi_id" class="select2"
                          @change="changeTrangBiBan"
                          :options="ds_trangbi_ban.map(e=> ({id: e.trangbi_id, text: e.tentrangbi}))"
                          >
                      </select2>
                  </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Mức cước TB</div>
                <div class="value">
                    <div class="select-custom" ref="ds_muccuoctb_ban">
                      <select2 :settings="{ dropdownParent: $refs['ds_muccuoctb_ban'] }" ref="cboMucCuocTBBan"
                          v-model="thongso_bancheo.muccuoctb_id" class="select2"
                          :options="ds_muccuoctb_ban.map(e=> ({id: e.muccuoctb_id, text: e.tenmuccuoc}))"
                          >
                      </select2>
                  </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Chủ quản</div>
                <div class="value">
                  <div class="select-custom" ref="ds_chuquan_ban">
                      <select2 :settings="{ dropdownParent: $refs['ds_chuquan_ban'] }" ref="cboChuQuanBan"
                          v-model="thongso_bancheo.chuquan_id"
                          class="select2"
                          :disabled="!chuquan_ban_enabled"
                          :options="ds_chuquan_ban.map(e=> ({id: e.chuquan_id, text: e.tenchuquan}))"
                          >
                      </select2>
                  </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Đơn vị TC</div>
                <div class="value">
                    <div class="select-custom" ref="ds_donvi_tc">
                      <select2 :settings="{ dropdownParent: $refs['ds_donvi_tc'] }" ref="cboDonViTC"
                          v-model="thongso_bancheo.chuquanld_id"
                          class="select2"
                          :options="ds_chuquan_ban.map(e=> ({id: e.chuquan_id, text: e.tenchuquan}))"
                          >
                      </select2>
                  </div>
                </div>
            </div>
          </div>
          <div class="gutter gutter-horizontal" style="width: 16px;"></div>
          <div class="box-col box-form" style="width: calc(50% - 8px);">
            <div class="legend-title">Thông tin dịch vụ tỉnh Thi Công</div>

            <div class="info-row">
                <div class="key w120">Kiểu lắp đặt</div>
                <div class="value">
                    <div class="select-custom" ref="ds_kieuld">
                      <select2 :settings="{ dropdownParent: $refs['ds_kieuld'] }" ref="cboKieuLD"
                          v-model="thongso_thicong.kieuld_id" class="select2"
                          :options="ds_kieuld.map(e=> ({id: e.kieuld_id, text: e.ten_kieuld}))"
                          @change="changeKieuLD"
                          >
                      </select2>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Mã TN</div>
                <div class="value">
                    <input type="text" class="form-control tright bold" :value="input_matn" @change="e=>input_matn=e.target.value" readonly/>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Công nghệ</div>
                <div class="value">
                    <div class="select-custom" ref="ds_congnghe_tc">
                      <select2 :settings="{ dropdownParent: $refs['ds_congnghe_tc'] }" ref="cboCongNgheTC"
                          v-model="thongso_thicong.congnghe_id" class="select2"
                          :options="ds_congnghe_tc.map(e=> ({id: e.congnghe_id, text: e.congnghe}))"
                          >
                      </select2>
                  </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Đối tượng</div>
                <div class="value">
                  <div class="select-custom" ref="ds_doituong_tc">
                      <select2 :settings="{ dropdownParent: $refs['ds_doituong_tc'] }" ref="cboDoiTuongTC"
                          v-model="thongso_thicong.doituong_id" class="select2"
                          @change="changeDoiTuongTC"
                          :options="ds_doituong_tc.map(e=> ({id: e.doituong_id, text: e.ten_dt}))"
                          >
                      </select2>
                  </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Trang bị</div>
                <div class="value">
                  <div class="select-custom" ref="ds_trangbi_tc">
                      <select2 :settings="{ dropdownParent: $refs['ds_trangbi_tc'] }" ref="cboTrangBiTC"
                          v-model="thongso_thicong.trangbi_id" class="select2"
                          @change="changeTrangBiTC"
                          :options="ds_trangbi_tc.map(e=> ({id: e.trangbi_id, text: e.tentrangbi}))"
                          >
                      </select2>
                  </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Mức cước TB</div>
                <div class="value">
                    <div class="select-custom" ref="ds_muccuoctb_tc">
                      <select2 :settings="{ dropdownParent: $refs['ds_muccuoctb_tc'] }" ref="cboMucCuocTBTC"
                          v-model="thongso_thicong.muccuoctb_id" class="select2"
                          :options="ds_muccuoctb_tc.map(e=> ({id: e.muccuoctb_id, text: e.tenmuccuoc}))"
                          >
                      </select2>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Chủ quản</div>
                <div class="value">
                    <div class="select-custom" ref="ds_chuquan_tc">
                      <select2 :settings="{ dropdownParent: $refs['ds_chuquan_tc'] }" ref="cboChuQuanTC"
                          v-model="thongso_thicong.chuquan_id"
                          class="select2"
                          :disabled="!chuquan_tc_enabled"
                          :options="ds_chuquan_tc.map(e=> ({id: e.chuquan_id, text: e.tenchuquan}))"
                          >
                      </select2>
                  </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w120">Đơn vị TC</div>
                <div class="value">
                    <div class="select-custom" ref="ds_donvi_tc_1">
                      <select2 :settings="{ dropdownParent: $refs['ds_donvi_tc_1'] }" ref="cboDonViTC1"
                          v-model="thongso_thicong.chuquanld_id"
                          class="select2"
                          :options="ds_chuquan_tc.map(e=> ({id: e.chuquan_id, text: e.tenchuquan}))"
                          >
                      </select2>
                    </div>
                </div>
            </div>

          </div>
        </div>
      </div>
      <!-- Modal -->
      <DSTBCungDoiCapBCModal ref="dSTBCungDoiCapBCModal"
        :khachhang_id="Number(khachhang_id)"
        :tinh_tc="Number(tinh_tc)"
        :kieuld_id="Number(thongso_thicong.kieuld_id)"
        @accept="acceptDSTBCungDoiCapBC"
      />
      <DSTBLapKemBCModal ref="dSTBLapKemBCModal"
        :hdkh_id="Number(hdkh_id)"
        :tinh_tc="Number(tinh_tc)"
        :kieuld_id="Number(thongso_thicong.kieuld_id)"
        @accept="acceptDSTBLapKemBC"
      />

    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import moment from 'moment'
import DSTBCungDoiCapBCModal from './DSTBCungDoiCapBCModal.vue'
import DSTBLapKemBCModal from './DSTBLapKemBCModal.vue'
export default {
  name: 'ThongSoCoDinhModal',
  props: {
    // tham số
    khachhang_id: {
      type: Number,
      default: 0
    },
    hdkh_id: {
      type: Number,
      default: 0
    },
    thongso_ban: {
      type: Object,
      default: () => {}
    },
    thongso_tc: {
      type: Object,
      default: () => {}
    }

  },
  components: {
    ActionTop,
    DSTBCungDoiCapBCModal,
    DSTBLapKemBCModal
  },
  data () {
    return {
      actions: [
        {
          id: 'capnhat',
          name: 'Cập nhật',
          active: true,
          icon_class: 'one-save'
        }
        // {
        //     id:'test',
        //     name:'Test',
        //     active: true,
        //     icon_class:'one-save'
        // }
      ],
      tinh_tc: 0,
      ploaitb_ld: 0,
      ploaitb: 0,
      thuebao_id_ban: 0,
      thuebao_id_thicong: 0,
      loaihd_id: 0,
      thongso_thicong: {},
      thongso_bancheo: {},
      ds_doituong_ban: [],
      ds_doituong_tc: [],
      ds_trangbi_ban: [],
      ds_trangbi_tc: [],

      ds_chuquan_ban: [],
      ds_chuquan_tc: [],
      ds_congnghe_ban: [],
      ds_congnghe_tc: [],
      diachi_ban: null,
      diachi_thicong: null,
      ds_muccuoctb_ban: [],
      ds_muccuoctb_tc: [],
      ds_kieuld: [],
      input_matn: '',

      chuquan_ban_enabled: true,
      chuquan_tc_enabled: true

    }
  },
  methods: {
    showModal (tinh_tc, ploaitb_ld, ploaitb, thuebao_id_ban, thuebao_id_thicong, loaihd_id) {
      console.log('tinh_tc', tinh_tc)
      this.tinh_tc = tinh_tc
      this.ploaitb_ld = ploaitb_ld
      this.ploaitb = ploaitb
      this.thuebao_id_ban = thuebao_id_ban
      this.thuebao_id_thicong = thuebao_id_thicong
      this.loaihd_id = loaihd_id
      this.$refs['popupThongSoCoDinh'].show()
    },
    hideModal () {
      this.$refs['popupThongSoCoDinh'].hide()
    },
    onActionClick (action) {
      if (action.id == 'capnhat') {
        this.CapNhat()
      } else if (action.id == 'test') {
        this.$refs.dSTBLapKemBCModal.showModal()
      }
    },
    acceptDSTBCungDoiCapBC (data) {
      this.thongso_thicong.ma_tn = data.ma_tn
      this.thongso_thicong.ma_doicap = data.ma_doicap
      this.input_matn = data.ma_tn ? data.ma_tn.toString() : ''
    },
    acceptDSTBLapKemBC (data) {
      this.thongso_thicong.ma_tn = data.ma_tn
      this.thongso_thicong.ma_doicap = data.ma_doicap
      this.input_matn = data.ma_tn ? data.ma_tn.toString() : ''
    },
    async CapNhat () {
      let trangBiTC = this.ds_trangbi_tc.find(x => x.trangbi_id == this.thongso_thicong.trangbi_id)
      if (!trangBiTC) {
        this.thongso_thicong.trangbi_id = 0
        this.$toast.error('Bạn phải chọn trang bị cho 2 đơn vị')
        return
      }
      let trangBiBan = this.ds_trangbi_ban.find(x => x.trangbi_id == this.thongso_bancheo.trangbi_id)
      if (!trangBiBan) {
        this.thongso_bancheo.trangbi_id = 0
        this.$toast.error('Bạn phải chọn trang bị cho 2 đơn vị')
        return
      }
      let muccuocTBTC = this.ds_muccuoctb_tc.find(x => x.muccuoctb_id == this.thongso_thicong.muccuoctb_id)
      if (!muccuocTBTC) {
        this.thongso_thicong.muccuoctb_id = 0
        this.$toast.error('Bạn phải mức cước thuê bao cho 2 đơn vị')
        return
      }
      let mucuocTBBan = this.ds_muccuoctb_ban.find(x => x.muccuoctb_id == this.thongso_bancheo.muccuoctb_id)
      if (!mucuocTBBan) {
        this.thongso_bancheo.muccuoctb_id = 0
        this.$toast.error('Bạn phải mức cước thuê bao cho 2 đơn vị')
        return
      }

      let doiTuongTC = this.ds_doituong_tc.find(x => x.doituong_id == this.thongso_thicong.doituong_id)
      if (!doiTuongTC) {
        this.thongso_thicong.doituong_id = 0
        this.$toast.error('Bạn phải đối tượng thuê bao cho 2 đơn vị')
        return
      }
      let doiTuongBan = this.ds_doituong_ban.find(x => x.doituong_id == this.thongso_bancheo.doituong_id)
      if (!doiTuongBan) {
        this.thongso_bancheo.doituong_id = 0
        this.$toast.error('Bạn phải đối tượng thuê bao cho 2 đơn vị')
        return
      }
      // Bổ sung chặn theo yêu cầu ANHNT_HPG
      if (this.input_matn.trim() == '') {
        let kieu = Number(await this.fn_map_kieu_ld(this.tinh_tc, this.thongso_thicong.kieuld_id, 1, '0'))
        if (kieu == 2 || kieu == 4) {
          // Lắp mới trên đường có sẵn
          this.$toast.error('Chưa chọn mã truy nhập lắp trên đường có sẵn !')
          return
        } else if (kieu == 1) {
          // Lắp kèm
          this.$toast.error('Chưa chọn mã truy nhập lắp kèm !')
          return
        }
      }

      this.$emit('accept', {
        thongso_ban: this.thongso_bancheo,
        thongso_tc: this.thongso_thicong
      })
      this.hideModal()
    },
    async initDuLieu () {
      Object.assign(this.thongso_thicong, this.thongso_tc)
      Object.assign(this.thongso_bancheo, this.thongso_ban)
      console.log('loaihd_id', this.loaihd_id)
      console.log('thongso_thicong', this.thongso_thicong)
      console.log('thongso_ban', this.thongso_ban)
      this.input_matn = ''
      this.chuquan_ban_enabled = true
      this.chuquan_tc_enabled = true
      // Bổ sung theo yêu cầu AnhNT
      if (this.loaihd_id == 1) {
        this.chuquan_ban_enabled = true
        this.chuquan_tc_enabled = true
      } else {
        this.chuquan_ban_enabled = false
        this.chuquan_tc_enabled = false
      }
      // Đối tượng
      this.ds_doituong_ban = await this.sp_lay_ds_doituong_theo_ban()
      if (this.ds_doituong_ban.length > 0) {
        if (this.thongso_bancheo.doituong_id == 0) {
          this.thongso_bancheo.doituong_id = this.ds_doituong_ban[0].doituong_id
        }
      }
      this.ds_doituong_tc = await this.sp_lay_ds_doituong_theo_thicong(this.tinh_tc)
      if (this.ds_doituong_tc.length > 0) {
        if (this.thongso_thicong.doituong_id == 0) {
          this.thongso_thicong.doituong_id = this.ds_doituong_tc[0].doituong_id
        }
      }
      // Công nghệ theo kiểu ld
      this.ds_congnghe_ban = await this.sp_lay_ds_congnghe_theo_kieuld_id_ban(this.thongso_bancheo.kieuld_id)
      this.ds_congnghe_tc = await this.get_kieu_ld_cn_congnghe(this.tinh_tc, this.thongso_thicong.kieuld_id)

      // Trang bị
      this.ds_trangbi_ban = await this.sp_lay_ds_trangbi_ban()
      if (this.ds_trangbi_ban.length > 0) {
        if (this.thongso_bancheo.trangbi_id == 0) {
          this.thongso_bancheo.trangbi_id = this.ds_trangbi_ban[0].trangbi_id
        }
      }
      this.ds_trangbi_tc = await this.sp_lay_ds_trangbi_tc(this.tinh_tc)
      if (this.ds_trangbi_tc.length > 0) {
        if (this.thongso_thicong.trangbi_id == 0) {
          this.thongso_thicong.trangbi_id = this.ds_trangbi_tc[0].trangbi_id
        }
      }
      // Chủ quản bán, vs đơn vị tc
      this.ds_chuquan_ban = await this.lay_ds_chuquan_ban()
      let det = this.ds_chuquan_ban.filter(x => x.tinh_id && x.tinh_id == this.$root.token.getPhanVungID())
      if (det.length > 0) {
        this.thongso_bancheo.chuquan_id = det[0].chuquan_id
      }
      // chủ quản ld là donvi_tc
      let detDVTC = this.ds_chuquan_ban.filter(x => x.tinh_id && x.tinh_id == this.tinh_tc)
      if (detDVTC.length > 0) {
        this.thongso_bancheo.chuquanld_id = detDVTC[0].chuquan_id
      }
      // Chủ quản TC
      this.ds_chuquan_tc = await this.lay_ds_chuquan_tc(this.tinh_tc)
      let dut = this.ds_chuquan_tc.filter(x => x.tinh_id && x.tinh_id == this.$root.token.getPhanVungID())
      if (dut.length > 0) {
        this.thongso_thicong.chuquan_id = dut[0].chuquan_id
      }
      let dutDVTC = this.ds_chuquan_tc.filter(x => x.tinh_id && x.tinh_id == this.tinh_tc)
      if (dutDVTC.length > 0) {
        this.thongso_thicong.chuquanld_id = dutDVTC[0].chuquan_id
      }
      // Kiểu LD
      if (this.loaihd_id != 6) {
        this.ds_kieuld = await this.get_quytrinh({
          tinhthicong_id: this.tinh_tc,
          vloaihd_id: this.loaihd_id,
          vloaitb_id: this.ploaitb_ld
        })
      } else {
        this.ds_kieuld = await this.load_dm_kieuld({
          ploaitb: this.ploaitb,
          ploaitb_ld: this.ploaitb_ld,
          tinhthicong_id: this.tinh_tc,
          vloaihd: 6
        })
      }
      if (this.ds_kieuld.length > 0) {
        // if(this.thongso_thicong.kieuld_id==0){
        //     this.thongso_thicong.kieuld_id=this.ds_kieuld[0].kieuld_id
        // }
      } else {
        this.$toast.error('Không tìm thấy kiểu lắp đặt tại tỉnh thi công, liên hệ admin để xử lý.')
      }
      // Địa chỉ bán, địa chỉ thi công
      console.log('thuebao_id_ban', this.thuebao_id_ban)
      if (this.thuebao_id_ban != 0) {
        let object = await this.lay_diachi_dbtb_bancheo(this.thuebao_id_ban, this.tinh_tc, 1)
        if (object) {
          this.diachi_ban = this.resetDiaChi()
          this.diachi_ban.diachi_id = object.diachi_id ? object.diachi_id : 0
          this.diachi_ban.diachi = object.diachi ? object.diachi : ''
          this.diachi_ban.quan_id = object.quan_id ? object.quan_id : 0
          this.diachi_ban.phuong_id = object.phuong_id ? object.phuong_id : 0
          this.diachi_ban.pho_id = object.pho_id ? object.pho_id : 0
          this.diachi_ban.ap_id = object.ap_id ? object.ap_id : 0
          this.diachi_ban.khu_id = object.khu_id ? object.khu_id : 0
          this.diachi_ban.dacdiem_id = object.dacdiem_id ? object.dacdiem_id : 0
        } else {
          this.diachi_ban = this.resetDiaChi()
        }
      } else {
        this.diachi_ban = this.resetDiaChi()
      }
      console.log('thuebao_id_thicong', this.thuebao_id_thicong)
      if (this.thuebao_id_thicong != 0) {
        let object = await this.lay_diachi_dbtb_bancheo(this.thuebao_id_thicong, this.tinh_tc, 2)
        if (object) {
          this.diachi_thicong = this.resetDiaChi()
          this.diachi_thicong.diachi_id = object.diachi_id ? object.diachi_id : 0
          this.diachi_thicong.diachi = object.diachi ? object.diachi : ''
          this.diachi_thicong.quan_id = object.quan_id ? object.quan_id : 0
          this.diachi_thicong.phuong_id = object.phuong_id ? object.phuong_id : 0
          this.diachi_thicong.pho_id = object.pho_id ? object.pho_id : 0
          this.diachi_thicong.ap_id = object.ap_id ? object.ap_id : 0
          this.diachi_thicong.khu_id = object.khu_id ? object.khu_id : 0
          this.diachi_thicong.dacdiem_id = object.dacdiem_id ? object.dacdiem_id : 0
        } else {
          this.diachi_thicong = this.resetDiaChi()
        }
      } else {
        this.diachi_thicong = this.resetDiaChi()
      }
      // Mức cước TB
      this.ds_muccuoctb_ban = await this.lay_ds_mucuoc_tb_v2_ban({
        khuld_id: this.diachi_ban.khu_id,
        apld_id: this.diachi_ban.ap_id,
        phold_id: this.diachi_ban.pho_id,
        phuongld_id: this.diachi_ban.phuong_id,
        loaitb_id: this.ploaitb,
        doituong_id: this.thongso_bancheo.doituong_id,
        tocdo_id: 0,
        muccuoc_id: 0,
        ngay: moment(new Date()).format('DD/MM/YYYY'),
        trangbi_id: this.thongso_bancheo.trangbi_id
      })
      if (this.ds_muccuoctb_ban.length > 0) {
        if (this.thongso_bancheo.muccuoctb_id == 0) {
          this.thongso_bancheo.muccuoctb_id = this.ds_muccuoctb_ban[0].muccuoctb_id
        }
      }

      this.ds_muccuoctb_tc = await this.lay_ds_mucuoc_tb_v2_tc({
        tinhthicong_id: this.tinh_tc,
        khuld_id: this.diachi_thicong.khu_id,
        apld_id: this.diachi_thicong.ap_id,
        phold_id: this.diachi_thicong.pho_id,
        phuongld_id: this.diachi_thicong.phuong_id,
        loaitb_id: this.ploaitb,
        doituong_id: this.thongso_thicong.doituong_id,
        tocdo_id: 0,
        muccuoc_id: 0,
        ngay: moment(new Date()).format('DD/MM/YYYY'),
        trangbi_id: this.thongso_thicong.trangbi_id
      })
      if (this.ds_muccuoctb_tc.length > 0) {
        if (this.thongso_thicong.muccuoctb_id == 0) {
          this.thongso_thicong.muccuoctb_id = this.ds_muccuoctb_tc[0].muccuoctb_id
        }
      }

      // if (_vloaihd == LoaiHopDong.CHUYEN_QUYEN || _vloaihd == LoaiHopDong.DI_CHUYEN)
      // {
      //     cboDoiTuongBan.Enabled = false;
      //     cboDoiTuongTC.Enabled = false;
      //     cboMucCuocTBBan.Enabled = false;
      //     cboMucCuocTBTC.Enabled = false;

      // }

      this.input_matn = this.thongso_thicong.ma_tn ? this.thongso_thicong.ma_tn.toString() : ''
    },
    handleShowModal () {
      this.thongso_thicong = {}
      this.thongso_bancheo = {}
      this.ds_doituong_ban = []
      this.ds_doituong_tc = []
      this.ds_trangbi_ban = []
      this.ds_trangbi_tc = []
      this.ds_chuquan_ban = []
      this.ds_chuquan_tc = []
      this.ds_congnghe_ban = []
      this.ds_congnghe_tc = []
      this.diachi_ban = null
      this.diachi_thicong = null
      this.ds_muccuoctb_ban = []
      this.ds_muccuoctb_tc = []
      this.ds_kieuld = []

      setTimeout(() => {
        this.initDuLieu()
      }, 500)
    },
    resetDiaChi () {
      return {
        diachi_id: 0,
        diachi: '',
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0
      }
    },
    async changeDoiTuongBan () {
      // load lại mức cước tb bán
      this.ds_muccuoctb_ban = await this.lay_ds_mucuoc_tb_v2_ban({
        khuld_id: this.diachi_ban.khu_id,
        apld_id: this.diachi_ban.ap_id,
        phold_id: this.diachi_ban.pho_id,
        phuongld_id: this.diachi_ban.phuong_id,
        loaitb_id: this.ploaitb,
        doituong_id: this.thongso_bancheo.doituong_id,
        tocdo_id: 0,
        muccuoc_id: 0,
        ngay: moment(new Date()).format('DD/MM/YYYY'),
        trangbi_id: this.thongso_bancheo.trangbi_id
      })
      this.thongso_bancheo.muccuoctb_id = 0
    },
    async changeDoiTuongTC () {
      this.ds_muccuoctb_tc = await this.lay_ds_mucuoc_tb_v2_tc({
        tinhthicong_id: this.tinh_tc,
        khuld_id: this.diachi_thicong.khu_id,
        apld_id: this.diachi_thicong.ap_id,
        phold_id: this.diachi_thicong.pho_id,
        phuongld_id: this.diachi_thicong.phuong_id,
        loaitb_id: this.ploaitb,
        doituong_id: this.thongso_thicong.doituong_id,
        tocdo_id: 0,
        muccuoc_id: 0,
        ngay: moment(new Date()).format('DD/MM/YYYY'),
        trangbi_id: this.thongso_thicong.trangbi_id
      })
      this.thongso_thicong.muccuoctb_id = 0
    },
    async changeTrangBiBan () {
      // load lại mức cước tb bán
      this.ds_muccuoctb_ban = await this.lay_ds_mucuoc_tb_v2_ban({
        khuld_id: this.diachi_ban.khu_id,
        apld_id: this.diachi_ban.ap_id,
        phold_id: this.diachi_ban.pho_id,
        phuongld_id: this.diachi_ban.phuong_id,
        loaitb_id: this.ploaitb,
        doituong_id: this.thongso_bancheo.doituong_id,
        tocdo_id: 0,
        muccuoc_id: 0,
        ngay: moment(new Date()).format('DD/MM/YYYY'),
        trangbi_id: this.thongso_bancheo.trangbi_id
      })
      this.thongso_bancheo.muccuoctb_id = 0
    },
    async changeTrangBiTC () {
      this.ds_muccuoctb_tc = await this.lay_ds_mucuoc_tb_v2_tc({
        tinhthicong_id: this.tinh_tc,
        khuld_id: this.diachi_thicong.khu_id,
        apld_id: this.diachi_thicong.ap_id,
        phold_id: this.diachi_thicong.pho_id,
        phuongld_id: this.diachi_thicong.phuong_id,
        loaitb_id: this.ploaitb,
        doituong_id: this.thongso_thicong.doituong_id,
        tocdo_id: 0,
        muccuoc_id: 0,
        ngay: moment(new Date()).format('DD/MM/YYYY'),
        trangbi_id: this.thongso_thicong.trangbi_id
      })
      this.thongso_thicong.muccuoctb_id = 0
    },
    async changeKieuLD () {
      this.ds_congnghe_tc = await this.get_kieu_ld_cn_congnghe(this.tinh_tc, this.thongso_thicong.kieuld_id)
      this.thongso_thicong.congnghe_id = 0
      if (this.ds_congnghe_tc.length > 0) {
        this.thongso_thicong.congnghe_id = this.ds_congnghe_tc[0].congnghe_id
      }
      await this.layMaTN()
    },
    async layMaTN () {
      let kieu = Number(await this.fn_map_kieu_ld(this.tinh_tc, this.thongso_thicong.kieuld_id, 1, '0'))
      if (kieu == 2 || kieu == 4) {
        console.log('layMaTN khachhang_id', this.khachhang_id)
        /// /Lắp mới trên đường có sẵn
        if (this.khachhang_id > 0) {
          // WinUI.WinUIBanCheo.frmDSTBCungDoiCapBC
          this.$refs.dSTBCungDoiCapBCModal.showModal()
        } else {
          this.$toast.error('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !')
        }
      } else if (kieu == 1) {
        // Lắp kèm
        // WinUI.WinUIBanCheo.frmDSTBLapKemBC
        if (this.hdkh_id > 0) {
          this.$refs.dSTBLapKemBCModal.showModal()
        } else {
          this.$toast.error('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !')
        }
      }
    },
    async sp_lay_ds_doituong_theo_ban () {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-hopdong/thaydoitocdoadsl/sp_lay_ds_doituong_theo_dieukien', {})
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async sp_lay_ds_doituong_theo_thicong (tinh_tc) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-bancheo/danhmuc/load_dm_doi_tuong', {
          tinhthicong_id: tinh_tc
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async sp_lay_ds_trangbi_ban () {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-hopdong/thaydoitocdoadsl/sp_lay_ds_trangbi_theo_dieukien', {})
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async sp_lay_ds_trangbi_tc (tinh_tc) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-bancheo/danhmuc/load_dm_trang_bi', {
          tinhthicong_id: tinh_tc
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data.sort((a, b) => a.trangbi_id - b.trangbi_id)
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async lay_ds_chuquan_ban () {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-hopdong/TocDoMucCuoc/lay_ds_chuquan', {
          cq_tc: 0
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async lay_ds_chuquan_tc (tinh_tc) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-bancheo/tracuu/get_chuquan', {
          tinhthicong_id: tinh_tc
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    //
    async sp_lay_ds_congnghe_theo_kieuld_id_ban (kieuld_id) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-bancheo/tracuu/sp_lay_ds_congnghe_theo_kieuld_id', {
          p_kieuld_id: kieuld_id
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async get_kieu_ld_cn_congnghe (tinh_tc, kieuld_id) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-bancheo/tracuu/get_kieu_ld_cn_congnghe', {
          tinhthicong_id: tinh_tc,
          vkieuld_id: kieuld_id
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    // https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=E6343
    async lay_diachi_dbtb_bancheo (thuebao_id, tinhthicong_id, kieu_tinh) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-bancheo/danhmuc/lay_diachi_dbtb_bancheo', {
          tinhthicong_id: tinhthicong_id,
          kieu_tinh: kieu_tinh,
          thuebao_id: thuebao_id
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return null
        }
      } catch (e) {
        this.loading(false)
        return null
      }
    },
    async lay_ds_mucuoc_tb_v2_ban (data) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2', data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async lay_ds_mucuoc_tb_v2_tc (data) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-bancheo/tracuu/lay_ds_mucuoc_tb_v2', data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async load_dm_kieuld (data) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-bancheo/danhmuc/load_dm_kieuld', data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async get_quytrinh (data) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-bancheo/tracuu/get_quytrinh', data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async fn_map_kieu_ld (tinh_tc, kieuld_id, type, defaultValue) {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-bancheo/tracuu/fn_tt_kieu_ld', {
          type: type,
          param: kieuld_id,
          tinhthicong_id: tinh_tc
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return defaultValue
        }
      } catch (e) {
        this.loading(false)
        return defaultValue
      }
    }

  }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>
