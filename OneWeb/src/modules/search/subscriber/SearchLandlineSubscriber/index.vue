<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <div class="main-title">TRA CỨU THÔNG TIN THUÊ BAO CỐ ĐỊNH TRÊN TỔNG ĐÀI</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Tìm kiếm</a></li>
        <li class="breadcrumb-item active"></li>Tra cứu thông tin thuê bao cố định trên tổng đài</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="TraCuu"> <span class="icon one-search"></span>Tra cứu </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin tra cứu</div>
        <div class="info-row">
          <div class="key w90">Số máy <span style='color: #dc3545'> (*)</span></div>
          <div class="value">
            <input type="text" class="form-control highlight boldSoMay" v-on:keyup.enter="TraCuu" ref="SoMay" v-model="txtSoMay" />
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Kết quả trả về</div>
        <textarea v-model="txtKetQua" name="" readonly="true" class="form-control" style="height: 400px; resize: none"></textarea>
      </div>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import XLSX from 'xlsx'
import moment from 'moment'
import api from './Api'
export default {
  components: {
    breadcrumb,
    api,
    XLSX,
    moment
  },
  name: 'SearchLandlineSubscriber',
  created() {},
  mounted() {
    this.$refs.SoMay.focus()
  },
  watch: {
    Loading: function (val) {
      this.$root.showLoading(val)
    }
  },
  data() {
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
      txtSoMay: '',
      txtKetQua: '',
      data: [],
      SubStatus: {
        KhongLayDuocThongTin: 0,
        ThueBaoDangHoatDong: 1,
        ThueBaoKhongKetNoi: 2,
        ThueBaoKhongTonTai: 3,
        KhongPhaiThueBaoThuong: 4,
        ThueBaoDangKhoa: 5
      }
    }
  },
  methods: {
    TraCuu: async function () {
      if (this.txtSoMay.length <= 0) {
        this.ShowWarning(`Chưa nhập số máy tra cứu !`)
        this.$refs.SoMay.focus()
        return
      }
      let ma_tinh = this.$root.token.getMaTinh()
      if (ma_tinh != null && ma_tinh.toLowerCase() != 'hpg') {
        console.log("🚀 ~ file: index.vue ~ line 94 ~ ma_tinh", ma_tinh)
        this.ShowError('Bạn không có quyền thực hiện chức năng này !')
        return
      }
      this.Loading = true
      var input = { maThueBao: this.txtSoMay }
      try {
        this.data = this.GetData(await api.TraCuu(this.axios, input))
        if (this.data != null && this.data.length > 0) {
          let ketquaText = ''
          let result = {}
          try {
            let resultRes = await this.kiemTraThongTin(this.data[0].donvi_id, this.txtSoMay)
            console.log('kiemTraThongTin', resultRes)
            if (resultRes.data.errorCode == 0) {
              result = resultRes.data.data
            } else {
              this.ShowError(`Lỗi kết nối đến API kiểm tra thông tin !: ${resultRes.faultString}`)
            }
            console.log('kiemTraThongTin', result)
          } catch (err) {
            this.ShowError(`Lỗi kết nối đến API kiểm tra thông tin !`)
            this.Loading = false
            return
          }

          if (result.trangThai.value.toLowerCase() == 'ThueBaoDangHoatDong'.toLowerCase()) {
            if (result.tenTongDai == null) result.tenTongDai = ' '
            ketquaText = `- Trạng thái thuê bao: Thuê bao đang hoạt động.\n`
            ketquaText = ketquaText + `- Tổng đài:  ${result.tenTongDai} . Vị trí: ${result.viTri} \n`
            ketquaText = ketquaText + `- Dịch vụ đang sử dụng: \n`
            if (result.tenDichVu != null && result.tenDichVu.length > 0) {
              result.tenDichVu.forEach(function (item) {
                ketquaText = ketquaText + `         + ${item} \n`
              })
            }
          } else if (result.trangThai.value.toLowerCase() == 'KhongLayDuocThongTin'.toLowerCase()) {
            let tt = 'Không lấy được thông tin thuê bao.'
            ketquaText = ketquaText + ` - Trạng thái thuê bao: ${tt}  \n`
          } else if (result.trangThai.value.toLowerCase() == 'KhongPhaiThueBaoThuong'.toLowerCase()) {
            let tt = 'Không phải thuê bao thường.'
            ketquaText = ketquaText + ` - Trạng thái thuê bao: ${tt}  \n`
          } else if (result.trangThai.value.toLowerCase() == 'ThueBaoKhongKetNoi'.toLowerCase()) {
            let tt = 'Thuê bao không kết nối.'
            ketquaText = ketquaText + ` - Trạng thái thuê bao: ${tt}  \n`
          } else {
            let tt = 'Thuê bao không tồn tại.'
            ketquaText = ketquaText + ` - Trạng thái thuê bao: ${tt}  \n`
          }

          /*
           var list = Object.getOwnPropertyNames(this.data[0])
          this.data.forEach(function (x) {
            for (var item of list) {
              if (item == '__ob__') continue
              // to resolve a problem with special prop name
              else {
                if (x[item] != null) {
                  ketquaText = ketquaText + `${item}: ${x[item]}  \n`
                }
              }
            }
          })
          */
          this.txtKetQua = ketquaText
        } else {
          this.ShowError(`Không tìm thấy thông tin thuê bao `)
          this.txtKetQua = ''
        }

        this.Loading = false
      } catch (ex) {
        console.log('Lỗi tra cứu ', ex)
        this.ShowError(ex.statusText)
        this.Loading = false
      }
    },
    kiemTraThongTin: async function (donviId, maTb) {
      let input = {
        soMay: maTb,
        tongDai: parseInt(donviId)
      }
      let data = await api.kiemTraThongTin(this.axios, input)
      return data
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowWarning: function (message) {
      this.$toast.error(message)
    },
    ShowError: function (message) {
      this.$toast.error(message)
    },
    ShowMessage: function (message) {
      this.$toast.success(message)
      // alert(message)
    }
  }
}
</script>
<style scoped>
.breadcrumb-top .breadcrumb {
  position: absolute;
  right: 15px;
}
.boldSoMay {
  font-weight: bold;
}
</style>
