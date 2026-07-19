<template>
  <div>
    <div class="legend-title">Thông tin mức cước thuê bao</div>
    <div class="info-row">
      <div class="key w100">Dịch vụ</div>
      <div class="value">
        <div class="select-custom">
          <select name="" id="" class="form-control">
            <option value=""></option>
          </select>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w100">Mức cước TB</div>
      <div class="value">
        <input type="text" class="form-control" />
      </div>
    </div>

    <div class="info-row">
      <vue-select
        :labelWidth="'100'"
        :options="moneyLevelType"
        id=""
        labelField="name"
        valueField="id"
        label="Kiểu mức tiền"
      ></vue-select>
     <!-- <div class="key w100">Kiểu mức tiền</div>
      <div class="value">
        <div class="select-custom">
          <select name="" id="" class="form-control">
            <option value=""></option>
          </select>
        </div>
      </div> -->
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Từ ngày</div>
          <div class="value">
            <date-picker
              style="width: 100%"
              placeholder="dd/mm/yyyy"
              :formatter="momentFormat"
              valueType="format"
            ></date-picker>
          </div>
          <!--   <div class="value">
            <div class="input-icon-right">
              <input type="text" class="form-control" />
              <span class="icon one-calendar"></span>
            </div>
          </div> -->
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Từ ngày</div>
          <div class="value">
            <date-picker
              style="width: 100%"
              placeholder="dd/mm/yyyy"
              :formatter="momentFormat"
              valueType="format"
            ></date-picker>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Cước TB</div>
          <div class="value">
            <input type="text" class="form-control" value="0" />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Cước TG</div>
          <div class="value">
            <input type="text" class="form-control" value="0" />
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Cước LL</div>
          <div class="value">
            <input type="text" class="form-control" value="0" />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Cước TG từ</div>
          <div class="value">
            <input type="text" class="form-control" value="0" />
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Cước trần</div>
          <div class="value">
            <input type="text" class="form-control" value="0" />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Cước TG đến</div>
          <div class="value">
            <input type="text" class="form-control" value="0" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import DatePicker from 'vue2-datepicker'
export default {
  components: {
    DatePicker
  },
  data () {
    return {
      momentFormat: {
        stringify: (date) => {
          return date ? moment(date).format('D/M/YYYY') : ''
        },
        parse: (value) => {
          return value ? moment(value, 'D/M/YYYY').toDate() : null
        }
      },
      moneyLevelType: [{name: 'test', id: 1}, {name: 'test2', id: 2}]
    }
  }
}
</script>
