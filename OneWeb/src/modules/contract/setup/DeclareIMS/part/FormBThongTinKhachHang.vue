<template>
  <div class="box-form">
    <div class="legend-title">
      <div class="pull-left">
        <span
          class="icon fa fa-angle-up"
          :class="{'fa-angle-down': !expanded}"
          @click.prevent="expanded = !expanded"></span> Thông tin khách hàng
      </div>
      <div class="pull-right">
        <div class="check-action normal">
          <input type="checkbox" class="check disabled" disabled
                 :checked="formThongTinKhachHang.checkedLapDatTheoYeuCau"/>
          <span class="name">Lắp đặt theo yêu cầu</span>
        </div>
      </div>
      <div class="clearfix"></div>
    </div>
    <b-collapse class="row" v-model="expanded">
      <div class="col-sm-6 col-12">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Loại HĐ/PL</div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist
                    :dataSource="danhSachHDPL"
                    :enable="false"
                    :allowFiltering="true"
                    :fields="{ text: 'NAME', value: 'ID' }"
                    v-model="formThongTinKhachHang.selectedLoaiHDPL"
                    @change="onChangeLoaiHDPL"
                    @filtering="onfilteringDropDownList($event, danhSachHDPL, 'NAME')"
                  ></ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Quy trình</div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist
                    ref="danhSachQuyTrinh"
                    :enabled="formThongTinKhachHang.selectedLoaiHDPL != null"
                    :dataSource="danhSachQuyTrinh"
                    :allowFiltering="true"
                    popupWidth="auto"
                    :fields="{ text: 'quytrinh', value: 'quytrinh_id' }"
                    v-model="formThongTinKhachHang.selectedQuyTrinh"
                    @change="onChangeQuyTrinh"
                    @filtering="onfilteringDropDownList($event, danhSachQuyTrinh, 'quytrinh')"
                  ></ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Mã giao dịch</div>
              <div class="value">
                <ejs-textbox cssClass="highlight" v-model="formThongTinKhachHang.maGiaoDich"></ejs-textbox>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Số máy</div>
              <div class="value">
                <ejs-textbox cssClass="highlight" v-model="formThongTinKhachHang.maThueBao"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Loại hình</div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist
                    :enabled="false"
                    ref="danhSachLoaiHinh"
                    :dataSource="danhSachLoaiHinh"
                    :allowFiltering="true"
                    :fields="{ text: 'LOAIHINH_TB', value: 'LOAITB_ID' }"
                    v-model="formThongTinKhachHang.selectedLoaiHinh"
                    @filtering="onfilteringDropDownList($event, danhSachLoaiHinh, 'LOAIHINH_TB')"
                  ></ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Kiểu LĐ</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinKhachHang.tenKieuLD"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Nhân viên YC</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinKhachHang.nhanVienYC" :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Lắp kèm</div>
              <div class="value">
                <ejs-textbox
                  cssClass="highlight"
                  style="background-color: #FFF9EB;"
                  v-model="formThongTinKhachHang.thueBaoLapKem"
                  :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w100">Tên thuê bao</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinKhachHang.tenThueBao"></ejs-textbox>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Khu vực</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinKhachHang.tenKhuVuc"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Địa chỉ TB</div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn" @click="openModalChonToaDo(2)">
                <span class="one-map-location f20"></span>
              </button>
              <ejs-textbox v-model="formThongTinKhachHang.diaChiThueBao"></ejs-textbox>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Địa chỉ LĐ</div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn" @click="openModalChonToaDo(1)">
                <span class="one-map-location f20"></span>
              </button>
              <ejs-textbox v-model="formThongTinKhachHang.diaChiLapDat"></ejs-textbox>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Địa chỉ LĐ cũ</div>
          <div class="value">
            <input type="text" class="form-control" :value="formThongTinKhachHang.diaChiLapDatCu"/>
          </div>
        </div>
      </div>
    </b-collapse>
    <ModalChonToaDo
      idModalChonToaDo="ModalChonToaDo"
      @XacNhanToaDo="XacNhanToaDo"
      :position="toaDoThueBao"
      :diachi="mapInfor.diaChi"
      :ten_kc="mapInfor.title">
    </ModalChonToaDo>
  </div>
</template>

<script>
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
import {
  getLoaiHinhThueBao,
  getLoaiHD,
  fetchDanhSachQuyTrinhTheoLoaiHD,
  getThongTinBanDo, updateToaDoThueBao, getThongTinHuongGiaoKhiThayDoiComboboxQuyTrinh
} from '../api/apiThongTinKhachHang'
import { getDanhSachThaoTac } from '../api'
import { TrangThaiHD } from '../consts'

const LAP_DAT = 1
const THUE_BAO = 2

export default {
  name: 'FormBThongTinKhachHang',
  mixins: [pagingAndFilter],
  components: {
    ModalChonToaDo
  },

  props: {
    formThongTinKhachHang: Object,
    hopDongTheuBaoId: Number,
    tagForm: {
      type: Object,
      default: () => ({
        tthd_id: TrangThaiHD.DANG_THI_CONG,
        dsloaihd_id: '1',
        dsdichvuvt_id: '11',
        flagTSKT: '',
        flagTTTB_IMS: 0
      })
    },
    thongTinHuongGiao: Object
  },
  data: () => ({
    danhSachQuyTrinh: [],
    danhSachHDPL: [],
    danhSachLoaiHinh: [],
    toaDoThueBao: {
      lat: 0, // vĩ độ
      lng: 0 // kinh độ
    },
    mapInfor: {
      diaChi: '',
      title: ''
    },
    toaDoCanUpdate: THUE_BAO,
    expanded: true
  }),
  computed: {},
  async mounted () {
    await this.loadData()
  },
  methods: {
    async loadData () {
      await Promise.all([
        getLoaiHinhThueBao(this.axios),
        getLoaiHD(this.axios, this.tagForm.dsloaihd_id)])
        .then(respone => {
          this.danhSachLoaiHinh = respone[0]
          this.danhSachHDPL = respone[1]
          this.formThongTinKhachHang.selectedLoaiHDPL = this.danhSachHDPL[0].ID
        })
    },
    async onChangeLoaiHDPL () {
      this.danhSachQuyTrinh=[]
      this.$refs.danhSachQuyTrinh.showSpinner()
      await fetchDanhSachQuyTrinhTheoLoaiHD(this.axios, {
        dsloaihd_id: this.formThongTinKhachHang.selectedLoaiHDPL,
        dsdichvuvt_id: this.tagForm.dsdichvuvt_id,
        dstthd_id: this.tagForm.tthd_id,
        kieu: 0 // fixed = 0
      }).then(data => {
        this.danhSachQuyTrinh = data
        console.log('onChangeLoaiHDPL danhSachQuyTrinh', this.danhSachQuyTrinh)
        this.formThongTinKhachHang.selectedQuyTrinh = this.danhSachQuyTrinh[0].quytrinh_id
      }).finally(() => {
        this.$refs.danhSachQuyTrinh.hideSpinner()
      })
    },
    async openModalChonToaDo (mode) {
      if (this.hopDongTheuBaoId === -1) {
        return
      }
      this.toaDoCanUpdate = mode
      let lng = await getThongTinBanDo(this.axios, this.hopDongTheuBaoId, `kinhdo${mode === 1 ? '_ld' : ''}`)
      let lat = await getThongTinBanDo(this.axios, this.hopDongTheuBaoId, `vido${mode === 1 ? '_ld' : ''}`)
      if (Number(lng) * Number(lat) !== 0 || this.formThongTinKhachHang.diaChiLapDat) {
        this.toaDoThueBao = {
          lat: Number(lat),
          lng: Number(lng)
        }
        this.mapInfor = {
          diaChi: this.formThongTinKhachHang.diaChiLapDat,
          title: this.formThongTinKhachHang.diaChiLapDat
        }
        this.$nextTick(() => {
          this.$bvModal.show('ModalChonToaDo')
        })
      } else {
        this.$toast.error('Chưa có thông tin về bản đồ địa chỉ thuê bao')
      }
    },
    async onChangeQuyTrinh () {
      const huongGiao = await getThongTinHuongGiaoKhiThayDoiComboboxQuyTrinh(this.axios, this.formThongTinKhachHang.selectedQuyTrinh, this.tagForm.tthd_id)
      if (huongGiao) {
        this.thongTinHuongGiao.luong_id = huongGiao.luong_id
        this.thongTinHuongGiao.huonggiao_id = huongGiao.huonggiao_id
        this.thongTinHuongGiao.huonggiao = huongGiao.huonggiao
        if (this.thongTinHuongGiao.luong_id) {
          this.thongTinHuongGiao.danhSachThaoTac = await getDanhSachThaoTac(this.axios, this.thongTinHuongGiao.luong_id)
          this.$emit('quyTrinhChange')
        }
      } else {
        this.$toast.error('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
      }
    },
    XacNhanToaDo (value) {
      const param = {
        lat: value.lat,
        lng: value.lng,
        hdtb_id: this.hopDongTheuBaoId,
        mode: this.toaDoCanUpdate
      }
      this.loading(true)
      updateToaDoThueBao(this.axios, param).then(() => {
        this.$toast.success('Cập nhật thông tin địa chỉ thuê bao thành công')
      }).catch(() => {
        this.$toast.error('Cập nhật thông tin địa chỉ thuê bao không thành công')
      }).finally(() => {
        this.loading(false)
      })
    }
  },
  watch: {}
}
</script>
