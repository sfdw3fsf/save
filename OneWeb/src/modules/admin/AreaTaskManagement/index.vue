<template>
  <div class="main-wrapper">
    <div>
      <breadcrumb />
      <div class="list-actions-top">
        <ul class="list">
          <li style="cursor: pointer;" @click="get_data">
            <a> <Icon icon="pepicons-pop:refresh" width="1.5rem" height="1.5rem" /> Lấy dữ liệu </a>
          </li>
        </ul>
      </div>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">PCT hàng ngày</div>
        <div class="row">
          <div class="col-sm-6 col-md-3 col-12">
            <div class="info-row">
              <div class="key">Loại PCT</div>
              <div class="value">
                <MultiSelect v-model="loaiphieu_value" :options="loaiphieu_options" />
              </div>
            </div>
          </div>
        </div>
        <div class="pct_section">
          <div class="box_pct" v-show="loaiphieu_value.includes(1) || loaiphieu_value[0] === '-1'">
            <div class="box-form">
              <div class="legend-title">Phiếu CCDV</div>
              <div class="data-content">
                <div class="data-content__list"><ListChart :dataSource="listCCDV" /></div>
                <div class="data-content__chart">
                  <PieChart :dataSource="listCCDV" />
                </div>
              </div>
            </div>
          </div>
          <div class="box_pct" v-show="loaiphieu_value.includes(2) || loaiphieu_value[0] === '-1'">
            <div class="box-form">
              <div class="legend-title">Phiếu CSKH</div>
              <div class="data-content">
                <div class="data-content__list"><ListChart :dataSource="listCSKH" /></div>
                <div class="data-content__chart">
                  <PieChart :dataSource="listCSKH" />
                </div>
              </div>
            </div>
          </div>
          <div class="box_pct" v-show="loaiphieu_value.includes(3) || loaiphieu_value[0] === '-1'">
            <div class="box-form">
              <div class="legend-title">Phiếu khác</div>
              <div class="data-content">
                <div class="data-content__list"><ListChart :dataSource="listkhac" /></div>
                <div class="data-content__chart">
                  <PieChart :dataSource="listkhac" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// tổ trưởng: 15641: lắp mới, dịch chuyển, tđtđ, báo hỏng, bảo dưỡng, cskh ghtt
// nvkt: 10711: chuyển đổi cđ sang ims, báo hỏng, bảo dưỡng, cskh ghtt
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import SelectChart from './Components/SelectChart.vue'
import ListChart from './Components/ListChart.vue'
import BarChart from './Components/BarChart.vue'
import PieChart from './Components/PieChart.vue'
import MultiSelect from '@/components/Controls/QT_Components/MultiSelect/index.vue'
import { Icon } from '@iconify/vue2'
export default {
  components: {
    breadcrumb,
    SelectChart,
    ListChart,
    BarChart,
    PieChart,
    Icon,
    MultiSelect
  },
  data() {
    return {
      loaiphieu_options: [
        { id: 1, label: 'Phiếu CCDV' },
        { id: 2, label: 'Phiếu CSKH' },
        { id: 3, label: 'Phiếu khác' }
      ],
      loaiphieu_value: ['-1'],
      listCCDV: [
        { value: 37, label: 'Lắp mới', code: 'SL_LM' },
        { value: 10, label: 'Dịch chuyển', code: 'SL_DC' },
        { value: 0, label: 'Thay đổi tốc độ', code: 'SL_TDTD' },
        { value: 0, label: 'Chuyển đổi cố định sang IMS', code: 'SL_CD_IMS' },
        { value: 10, label: 'Thu hồi thiết bị đầu cuối',code: 'SL_THTBDC'  },
        { value: 0, label: 'Phiếu khôi phục', code: 'SL_KP' },
        { value: 3, label: 'Huỷ', code:'SL_HUY' },
        { value: 0, label: 'Phiếu khác', code:'' }
      ],
      listCSKH: [
        { value: 8, label: 'Báo hỏng', code: 'SL_BH' },
        { value: 0, label: 'Bảo dưỡng', code: 'SL_BD' },
        { value: 240, label: 'CSKH GHTT', code: 'SL_CSKH_GHTT' },
        { value: 0, label: 'Tạm dừng', code: 'SL_TD' },
        // { value: 0, label: 'MyTV KTT', code: 'SL_MYTV_KTT' },
        // { value: 0, label: 'KPSLL', code: 'SL_KPSLL' }
      ],
      listkhac: [{ value: 0, label: 'Thu đơn hàng chưa TT', code: 'SL_DONHANG_CHUA_TT' }],
      // machitieu: ['SL_LM', 'SL_DC', 'SL_TDTD', 'SL_CD_IMS', 'SL_THTBDC', 'SL_KP', 'SL_HUY', 'SL_BH', 'SL_BD', 'SL_CSKH_GHTT', 'SL_TD', 'SL_MYTV_KTT', 'SL_KPSLL', 'SL_DONHANG_CHUA_TT']
    }
  },
  methods: {
    async get_data() {
      try {
        this.loading(true)
        let listAll = [...this.listCCDV, ...this.listCSKH, ...this.listkhac].filter(item => item.code);
        let res = await Promise.all(
        listAll.map(item => 
          this.axios.post('/web-quantri/dashboard/sp_lay_chitieu_dashboard_dieuhanh', {
            ma_chitieu: item.code
          }).then(response => {
            return { 
              ...item, 
              value: response.data.data ? response.data.data[0].sl : null
            };
          })
        ))
        this.listCCDV = this.listCCDV.map(item=>{
          if (!item.code) {
            return item
          }
          return {
            ...item,
            value: res.find(i => i.code === item.code).value
          }
        })
        this.listCSKH = this.listCSKH.map(item=>{
          return {
            ...item,
            value: res.find(i => i.code === item.code).value
          }
        })
        this.listkhac = this.listkhac.map(item=>{
          return {
            ...item,
            value: res.find(i => i.code === item.code).value
          }
        })
        
      } catch (error) {
        this.$toast.error('Lỗi lấy dữ liệu: ' + error.message)
      } finally {
        this.loading(false)
      }
    }
  },
  mounted() {
    this.get_data()
  },
}
</script>

<style lang="scss" scoped>
.pct_section {
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  gap: 10px;
  row-gap: 5px;
  margin-top: 10px;
  .box_pct {
    width: calc(50% - 10px);
    .box-form {
      margin-bottom: 20px;
      height: 500px;
    }
  }
}
.data-content {
  width: 100%;
  height: 100%;
  position: relative;
  &__list {
    position: absolute;
    width: 350px;
    max-width: 40%;
    top: 0;
    right: 0;
    z-index: 2;
  }
  &__chart {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 1;
    width: 100%;
    height: 100%;
    overflow: hidden;
    .pie_chart {
      transform: translate(-20%, 10%);
      @media screen and (max-width: 1400px) {
        transform: translate(-20%, 20%);
      }
    }
  }
}
</style>
