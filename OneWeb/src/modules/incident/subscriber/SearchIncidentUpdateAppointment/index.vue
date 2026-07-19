<template>
        <div class="main-wrapper">
        <div class="breadcrumb-top">
            <div class="main-title">Tiếp nhận yêu cầu khoá máy cố định</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">Lắp đặt mới</li>
            </ul>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#">
                        <span class="icon ui-1_circle-add nc-icon-glyph"></span> Nhập mới(F5)
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon media-1_sd nc-icon-glyph"></span> Ghi lại(F9)
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Hủy
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon ui-1_trash nc-icon-glyph"></span> Xóa HĐ (F8)
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon files_single-content-02 nc-icon-glyph"></span> Hồ sơ
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon shopping_credit-card nc-icon-glyph"></span> Thanh toán
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon ui-1_circle-add nc-icon-glyph"></span> Thêm TT
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon ui-1_trash nc-icon-glyph"></span> Xóa TT
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon ui-1_circle-add nc-icon-glyph"></span>Thêm TB
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon ui-1_trash nc-icon-glyph"></span>Xóa TB
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon objects_support-17 nc-icon-glyph"></span>Chăm sóc KH
                    </a>
                </li>
            </ul>
            <div class="more">
                <a href="#">
                    <span class="icon -ap  icon-dots-three-vertical"></span>
                </a>
            </div>
        </div>
        <div class="page-content">
            <a @click="ShowPopUp" data-toggle="modal" class="btn btn-action">Show modal</a>
        </div>
        <b-modal id="my-modal1" class="modal-dialog"   size="lg" hide-footer :no-close-on-backdrop="true">
             <div slot="modal-title" class="popup-header">
               <div class="title"><span class="icon f20 nc-icon-outline ui-1_settings"></span> Thay đổi thông tin báo hỏng</div>
            </div>
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="#" @click.prevent="CapNhat">
                                <span class="icon one-reload1"></span>Cập nhật
                            </a>
                        </li>
                        <li>
                            <a href="#"  @click.prevent="Xoa">
                                <span class="icon one-trash"></span>Xoá
                            </a>
                        </li>
                        <li>
                            <a href="#"  @click.prevent="TroGiup">
                                <span class="icon one-user-mess"></span>Trợ giúp
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">
                                        Số máy/Acc
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="txtSoMay" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">
                                        Tên thuê bao
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control"  v-model="txtTenThueBao" >
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">
                                        Dịch vụ
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control"  v-model="txtDichVu" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">
                                        Ngày báo hỏng
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                             <date-picker  :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="txtNgayBaoHong" :type="typeFormat">
                                            <template #icon-calendar>
                                                <span class="icon one-calendar"></span>
                                              </template>
                                        </date-picker>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="legend-title">Thông tin cũ</div>
                                <div class="info-row">
                                    <div class="key w100">
                                        Người BH
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="txtNguoiBH">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">
                                        Điện thoại LH
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control"  v-model="txtDienThoaiLH">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">
                                        Địa chỉ LĐ
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control"   v-model="txtDiaChiLD">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="legend-title">Thông tin mới</div>
                                <div class="info-row">
                                    <div class="key w100">
                                        Người BH
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="txtNguoiBHNew">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">
                                        Điện thoại LH
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control"  v-model="txtDienThoaiLHNew">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">
                                        Địa chỉ LĐ
                                    </div>
                                    <div class="value">
                                        <input type="text" class="form-control"  v-model="txtDiaChiLDNew">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
        </div>
        </b-modal>
    </div>
</template>
<script>
import gridView from '@/modules/complaint/ResovleComplaintAfterSales/components/gridview.vue'
// import gridView from '@/components/Shared/gridview.vue'
import breadcrumb from '@/components/breadcrumb'
import XLSX from 'xlsx'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import api from './Api'
export default {
  components: {
    breadcrumb,
    gridView,
    XLSX,
    moment,
    api,
    DatePicker
  },
  name: 'SearchIncidentUpdateAppointment',
  async created () {
    this.Loading = true
    try {
      Promise.all([
        this.getDsDonViChuaGan()
      ])
      this.Loading = false
    } catch (ex) {
      this.Loading = false
    }
    this.Loading = false
  },
  watch: {
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  data () {
    return {
      header: {
        title: 'Tra cứu',
        list: [
          { name: ' TRA CỨU KHÁCH HÀNG QUAN TÂM OA', link: { name: 'Ui.cards' } },
          {
            name: 'TRA CỨU KHÁCH HÀNG QUAN TÂM OA',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
      typeFormat: 'date',
      txtSoMay: '',
      txtTenThueBao: '',
      txtDichVu: '',
      txtNgayBaoHong: '',
      txtNguoiBH: '',
      txtDienThoaiLH: '',
      txtDiaChiLD: '',
      txtNguoiBHNew: '',
      txtDienThoaiLHNew: '',
      txtDiaChiLDNew: ''

    }
  },
  methods: {
    ShowPopUp: function () {
      this.$bvModal.show('my-modal1')
    },
    getDsDonViChuaGan: async function () {

    },
    CapNhat: function () {

    },
    Xoa: function () {

    },
    TroGiup: function () {
      this.$bvModal.hide('my-modal1')
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      alert(msg)
    }

  }
}
</script>
<style>
.mx-datepicker {
    width: 100% !important;
  }
  .mx-icon-calendar {
    font-style: normal !important;
  }
  .modal-dialog{
    min-width:1200px;
  }
</style>
