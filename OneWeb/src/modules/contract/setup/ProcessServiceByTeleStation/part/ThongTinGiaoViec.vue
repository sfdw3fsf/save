<template>
<div>
    <div class="legend-title">Thông tin giao việc</div>
    <div class="row">
        <div class="col-sm-6 col-12">
            <div class="info-row">
                <vue-select
                  :labelWidth="'90'"
                  :options="empList"
                  required
                  labelField="ten_nv"
                  valueField="nhanvien_id"
                  label="Người GV"
                  v-model="form.nguoi_gv"
                ></vue-select>
            </div>
        </div>
        <div class="col-sm-3 col-12">
            <div class="info-row">
                <vue-date
                    :labelWidth="'90'"
                    :required="true"
                    label="Ngày GV"
                    :checkbox="true"
                    v-model="form.ngay_gv"
                ></vue-date>
            </div>
        </div>
        <div class="col-sm-3 col-12">
            <div class="info-row">
                <vue-date
                    :labelWidth="'90'"
                    :required="true"
                    label="Ngày HT"
                    :checkbox="true"
                    v-model="form.ngay_ht"
                ></vue-date>
            </div>
        </div>
    </div>
    <div class="table-content" style="height: 120px;">
         <vue-table ref="TTGV_DSNV"
            :config="tableConfig"
            :unique="'nhanvien_th_id'"
            :multiple="false">
        </vue-table>
    </div>
</div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  props: ['formProps', 'optionsProps'],
  data: () => ({
    options: [{id: 1, name: 'dat 1'}, {id: 2, name: 'dat 2'}, {id: 3, name: 'dat 3'}],
    tableConfig: [
      {
        label: 'Tên nhân viên',
        filterAble: true,
        key: 'ten_nv'
      },
      {
        label: 'Số điện thoại',
        filterAble: true,
        key: 'so_dt'
      },
      {
        label: 'Nhiệm vụ',
        filterAble: true,
        key: 'nhiemvu'
      },
      {
        label: 'Ghi chú',
        filterAble: true,
        key: 'ghichu'
      }
    ],
    form: {
      ngay_gv: null,
      ngay_ht: null,
      nguoi_gv: null
    }
  }),
  computed: {
    empList () {
      return this.$store.getters['processServiceByTeleStation/getEmployeeList'] || []
    }
  },
  mounted () {
    this.$store.dispatch('processServiceByTeleStation/getEmployeeList', { donvi_id: 10031 })
  },
  methods: {
    ...mapActions('processServiceByTeleStation', [
      'getEmployeeList'
    ])
  }
}
</script>

<style>
</style>
