<template>
  <div class="box-form">
    <div class="row">
      <div class="col-sm-4 col-12">
        <div class="info-row marb0">
          <div class="key w30 nowrap">NV tiếp thị</div>
          <div class="value">
            <input type="text" class="form-control text-red" :value="formThongTinChuyenDoi.nhanVienTiepThi"/>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row marb0">
          <div class="key w30 nowrap">Kiểu HĐ</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                :enabled="false"
                :dataSource="danhSachKieuHopDong"
                :allowFiltering='true'
                :fields="{ text: 'kieu_hd', value: 'kieuhd_id' }"
                v-model="formThongTinChuyenDoi.selectedKieuHopDong"
                @filtering="onFilteringKieuHopDong"
              ></ejs-dropdownlist>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-2 col-12">
        <div class="info-row marb0">
          <div class="key w30 nowrap">Kênh thu</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                :enabled="false"
                :dataSource="danhSachKenhThu"
                :allowFiltering='true'
                :fields="{ text: 'kenhthu', value: 'kenhthu_id' }"
                v-model="formThongTinChuyenDoi.selectedKenhThu"
                @filtering="onFilteringKenhThu"
              ></ejs-dropdownlist>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row marb0">
          <div class="key w30 nowrap">Tổng tiền</div>
          <div class="value">
            <ejs-numerictextbox
              style="color: red; padding-right: 5px"
              locale='vi-VN'
              format="n"
              :showSpinButton="false"
              class="tright"
              v-model="formThongTinChuyenDoi.tongTien"></ejs-numerictextbox>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { fetchDanhSachKenhThu, fetchKieuHopDong } from '../api/apiThongTinChuyenDoi'
import { Query } from '@syncfusion/ej2-data'
export default {
  components: {},
  created () {
    this.loading(true)
    Promise.all([
      fetchDanhSachKenhThu(this.axios),
      fetchKieuHopDong(this.axios)
    ]).then(response => {
      this.danhSachKenhThu = response[0]
      this.danhSachKieuHopDong = response[1]
    }).finally(() => {
      this.loading(false)
    })
  },
  props: {
    formThongTinChuyenDoi: Object
  },
  data: () => ({
    danhSachKenhThu: [],
    danhSachKieuHopDong: []
  }),
  computed: {},
  methods: {
    onFilteringKenhThu (e) {
      var query = new Query()
      query =
        e.text !== ''
          ? query.where('kenhthu', 'contains', e.text, true)
          : query
      e.updateData(this.danhSachKenhThu, query)
    },
    onFilteringKieuHopDong (e) {
      var query = new Query()
      query =
        e.text !== ''
          ? query.where('kieu_hd', 'contains', e.text, true)
          : query
      e.updateData(this.danhSachKieuHopDong, query)
    }
  }
}
</script>


<style scoped>
.text-red {
  color: red;
}
</style>

