<template>
  <div class="main-wrapper">
    <breadcrumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li>
          <a style="cursor: pointer;" @click="txtSoMay_KeyPress">
            <span class="icon one-search"></span> Tìm kiếm
          </a>
        </li>
      </ul>
    </vue-nav>

    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Tra cứu lịch sử nợ của khách hàng</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Từ kỳ HĐ</div>
              <div class="value">
                <div class="select-custom" ref="tuKyHDRef">
                  <SelectExt
                    v-model="lueTuKyHDId"
                    dataValueField="dangchu"
                    dataTextField="dangchu"
                    :settings="{ dropdownParent: $refs['tuKyHDRef'] }"
                    :dataSource="lueTuKyHD"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Đến kỳ HĐ</div>
              <div class="value">
                <div class="select-custom" ref="denKyHDRef">
                  <SelectExt
                    v-model="lueDenKyHDId"
                    dataValueField="dangchu"
                    dataTextField="dangchu"
                    :settings="{ dropdownParent: $refs['denKyHDRef'] }"
                    :dataSource="lueDenKyHD"
                  />
                </div>
              </div>
            </div>
          </div>

          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Mã KH</div>
              <div class="value">
                <input
                  v-model.trim="txtMaKH"
                  type="text"
                  class="form-control highlight"
                  @keyup.enter="txtSoMay_KeyPress"
                >
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Số máy</div>
              <div class="value">
                <input
                  v-model.trim="txtSoMay"
                  type="text"
                  class="form-control"
                  @keyup.enter="txtSoMay_KeyPress"
                >
              </div>
            </div>
          </div>
          <div class="col-12">
            <div class="info-row">
              <div class="key w100">Tên KH</div>
              <div class="value">
                <input
                  v-model.trim="txtTenKH"
                  type="text"
                  class="form-control"
                >
              </div>
            </div>
          </div>
          <div class="col-12">
            <div class="info-row">
              <div class="key w100">Địa chỉ KH</div>
              <div class="value">
                <input
                  v-model.trim="txtDiaChiKH"
                  type="text"
                  class="form-control"
                >
              </div>
            </div>
          </div>
        </div>
        <div class="legend-title mart20">Thông tin lịch sử nợ</div>
        <div class="table-content">
          <DataGrid
            :columns="columns"
            :dataSource="dataSource"
            :showColumnCheckbox="false"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import moment from 'moment'

export default {
  name: 'WebDebtPaymentHistory',
  components: { breadcrumb },
  data () {
    return {
      header: {
        title: 'Tra cứu lịch sử nợ thanh toán',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      columns: [
        { fieldName: 'phieu_id', headerText: 'Phiếu ID', allowFiltering: true },
        { fieldName: 'ma_kh', headerText: 'Mã KH', allowFiltering: true },
        { fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true },
        { fieldName: 'chukyno', headerText: 'Kỳ trả', allowFiltering: true },
        { fieldName: 'tradk', headerText: 'Trả DK', allowFiltering: true },
        { fieldName: 'traps', headerText: 'Trả PS', allowFiltering: true },
        { fieldName: 'tongtra', headerText: 'Tổng', allowFiltering: true },
        { fieldName: 'seri', headerText: 'Seri', allowFiltering: true },
        { fieldName: 'soseri', headerText: 'Số seri', allowFiltering: true },
        { fieldName: 'hinhthuc', headerText: 'Hình thức', allowFiltering: true },
        { fieldName: 'ngay_tt', headerText: 'Ngày TT', allowFiltering: true },
        { fieldName: 'buucuc', headerText: 'Bưu cục thu', allowFiltering: true },
        { fieldName: 'nguoigach', headerText: 'Mã TN', allowFiltering: true },
        { fieldName: 'ten_tn', headerText: 'Tên TN', allowFiltering: true },
        { fieldName: 'tra_cacthang', headerText: 'Tháng', allowFiltering: true }
      ],
      dataSource: [],
      lueTuKyHD: [],
      lueDenKyHD: [],
      txtSoMay: '',
      txtTenKH: '',
      txtMaKH: '',
      txtDiaChiKH: '',
      lueTuKyHDId: '',
      lueDenKyHDId: ''
    }
  },
  computed: {
    gioiHanTraCuu () {
      const dateBdau = moment(this.lueTuKyHDId, 'MM/YYYY')
      const dateKthuc = moment(this.lueDenKyHDId, 'MM/YYYY')
      if (dateKthuc.subtract(6, 'months').isBefore(dateBdau)) return 1
      return 0
    }
  },
  async created () {
    this.loading(true)
    try {
      const [dsChuKyNo, kyHDHienTai] = await Promise.all([
        await this.getDanhSachChuKyNo(),
        this.getKyHDHienTai()
      ])
      this.lueTuKyHD = dsChuKyNo
      this.lueDenKyHD = dsChuKyNo
      this.setKyHDHienTai(dsChuKyNo, kyHDHienTai)
    } catch (e) {

    } finally {
      this.loading(false)
    }
  },
  methods: {
    async getDanhSachChuKyNo () {
      try {
        const response = await api.getDanhSachChuKyNo(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách chu kỳ nợ')
        return []
      }
    },
    async getKyHDHienTai () {
      try {
        const response = await api.getKyHDHienTai(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data.result : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy kỳ hóa đơn hiện tại')
        return ''
      }
    },
    setKyHDHienTai (dsChuKyNo, value) {
      const inKyhd = +value.substring(3, 7) * 13 + +value.substring(0, 2)
      const found = dsChuKyNo.find(element => +element.chukyno_vnp === inKyhd)
      if (found) {
        this.lueTuKyHDId = found.dangchu
        this.lueDenKyHDId = found.dangchu
      }
    },
    async txtSoMay_KeyPress () {
      if (this.gioiHanTraCuu === 0) {
        this.$root.$toast.warning('Chỉ tra cứu trong khoảng 6 tháng trở lại !!!')
        return
      }
      if (!this.txtSoMay) {
        this.$root.$toast.warning('Số máy không được bỏ trống !!!')
        return
      }
      await this.traCuu()
    },
    async traCuu () {
      this.loading(true)
      try {
        const data = await this.traCuuLSTra()
        this.dataSource = data
        if (data.length > 0) {
          const tttb = await this.traCuuNoTongHop(data[0].ma_kh, data[0].ma_tb)
          if (tttb.length > 0) {
            this.txtMaKH = tttb[0].ma_kh
            this.txtTenKH = tttb[0].ten_tt
            this.txtDiaChiKH = tttb[0].diachi_tt
          } else {
            this.txtTenKH = ''
            this.txtDiaChiKH = ''
          }
        }
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    async traCuuLSTra () {
      try {
        const response = await api.traCuuLSTra(this.axios, {
          den_kyhoadon: this.lueDenKyHDId.replace('/', ''),
          ma_kh: this.txtMaKH,
          so_tb: this.txtSoMay,
          tu_kyhoadon: this.lueTuKyHDId.replace('/', '')
        })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin lịch sử trả')
        return []
      }
    },
    async traCuuNoTongHop (maKH, maTB) {
      try {
        const response = await api.traCuuNoTongHop(this.axios, {
          kyhoadon: this.lueDenKyHDId.replace('/', ''),
          ma_kh: maKH,
          so_tb: maTB
        })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin tra cứu')
        return []
      }
    }
  }
}
</script>
