<template>
  <div class="box-form">
    <div class="legend-title">Thông tin phiếu</div>
    <div class="row">
      <div class="col-sm-7 col-12">
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w80">Dịch vụ</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control">
                    <option value=""></option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Quy trình</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control">
                    <option value=""></option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w80">Mã GD</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" @keypress.enter="onEnterMaGD" class="form-control highlight" />
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Kiểu yêu cầu</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control">
                    <option value=""></option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w80">Người GV</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control">
                    <option value=""></option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Ngày GV</div>
              <div class="value w16 padt7">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="checkBoxs.ngay_gv"/>
                  <span class="name"></span>
                </div>
              </div>
              <div class="value">
                <div class="input-icon-right">
                    <ejs-datetimepicker class="form-control" :enabled="checkBoxs.ngay_gv" v-model="ngay_gv" :format="dateFormat" :showClearButton="false"/>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w80">Account</div>
              <div class="value">
                <input type="text" class="form-control highlight" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w80">Mật khẩu</div>
              <div class="value">
                <input type="text" class="form-control highlight" />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Ngày HT</div>
              <div class="value w16 padt7">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="checkBoxs.ngay_ht" />
                  <span class="name"></span>
                </div>
              </div>
              <div class="value">
                <div class="input-icon-right">
                    <ejs-datetimepicker :enabled="checkBoxs.ngay_ht" class="form-control" v-model="ngay_ht" :format="dateFormat" :showClearButton="false"/>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w80">Tốc độ mới</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control">
                    <option value=""></option>
                  </select>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w80">Tốc độ cũ</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control">
                    <option value=""></option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Mức cước mới</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control">
                    <option value=""></option>
                  </select>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Mức cước cũ</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control">
                    <option value=""></option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="info-row">
          <div class="key w80">Tốc độ thực</div>
          <div class="value">
            <div class="select-custom">
              <select name="" id="" class="form-control">
                <option value=""></option>
              </select>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w80">Bras</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control">
                    <option value=""></option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="list-checks mart7">
              <div class="item">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="checkBoxs.giu_ip" />
                  <span class="name">Giữ IP</span>
                </div>
              </div>
              <div class="item">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="checkBoxs.tu_dong"/>
                  <span class="name">Tự động</span>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">IPv4 Lan</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" class="form-control" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w80">Dslam</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control">
                    <option value=""></option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w80">Port MDF</div>
              <div class="value">
                <input type="text" class="red form-control fw6" />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">IPv4 Wan</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" class="form-control" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-2 col-12">
            <div class="info-row">
              <div class="key w40">Rack</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn">
                    <span class="-ap icon-check2"></span>
                  </button>
                </div>
                <div class="input-more-button">
                  <input type="text" class="form-control red" />
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w60">Shelf</div>
              <div class="value">
                <input type="text" class="red form-control fw6" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w80">Trạm T.Bị</div>
              <div class="value">
                <input type="text" class="red form-control fw6" />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">IPv6 Lan</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input type="text" class="form-control" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-2 col-12">
            <div class="info-row">
              <div class="key w80">Slot</div>
              <div class="value">
                <input type="text" class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w60">Port</div>
              <div class="value">
                <input type="text" class="form-control fw6" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w80">System</div>
              <div class="value">
                <input type="text" class="red form-control fw6" />
              </div>
            </div>
          </div>
          <div class="col-sm-2 col-12">
            <div class="info-row">
              <div class="key w100">Vpi/Svlan</div>
              <div class="value">
                <input type="text" class="red form-control fw6" />
              </div>
            </div>
          </div>
          <div class="col-sm-2 col-12">
            <div class="info-row">
              <div class="key w60">Vci/Cvlan</div>
              <div class="value">
                <input type="text" class="red form-control fw6" />
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-2 col-12">
            <div class="info-row">
              <div class="key w80">Self</div>
              <div class="value">
                <input type="text" class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w60">OLT Slot</div>
              <div class="value w50">
                <input type="text" class="form-control" />
              </div>
              <div class="key w60 padl5 nowrap">OLT Port</div>
              <div class="value">
                <input type="text" class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w80">ONUID</div>
              <div class="value">
                <input type="text" class="form-control fw6" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-5 col-12">
        <div class="info-row">
          <div class="key w100">Tên thuê bao</div>
          <div class="value">
            <input type="text" class="form-control" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Địa chỉ lắp đặt</div>
          <div class="value">
            <input type="text" class="form-control" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Nội dung giao</div>
          <div class="value">
            <input type="text" class="form-control" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Nội dung trả</div>
          <div class="value">
            <input type="text" class="form-control" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Ghi chú</div>
          <div class="value">
            <input type="text" class="form-control" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Nội dung TH</div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn">
                <span class="nc-icon-glyph ui-1_edit-76 f16"></span>
              </button>
              <input type="text" class="form-control" />
            </div>
          </div>
        </div>
        <div class="legend-title mart10">Danh sách nhân viên thực hiện</div>
        <k-table :columns="columns" :dataSources="dataSources" :allowFilter="true"/>
      </div>
    </div>
  </div>
</template>
<script>
import KTable from '@/components/kylq_components/KTable.vue'
import moment from 'moment'
import Vue from "vue";
import { DateTimePickerPlugin } from "@syncfusion/ej2-vue-calendars";
Vue.use(DateTimePickerPlugin);
export default {
  name: "ThongTinPhieu",
  components: {
      KTable,
      DateTimePickerPlugin
  },
  data() {
    return {
        columns:[
            {
                field: "ten_nv",
                label: "Tên nhân viên",
                allowFilter: true
            },
            {
                field: "dienthoai",
                label: "Điện thoại",
                allowFilter: true
            },
            {
                field: "nhiemvu",
                label: "Nhiệm vụ",
                allowFilter: true
            },
            {
                field: "ghichu",
                label: "Ghi chú",
                allowFilter: true
            },

        ],
        dataSources:[
            
        ],
        checkBoxs:{
            ngay_gv:false,
            ngay_ht:false,
            giu_ip:false,
            tu_dong:false
        },
        ngay_gv: moment(new Date()).format('DD/MM/YYYY HH:mm'),
        ngay_ht: moment(new Date()).format('DD/MM/YYYY HH:mm'),
        dateFormat: "dd/MM/yyyy HH:mm",

    }
  },
  methods:{
    onEnterMaGD(){
      
    }
  },
  mounted(){
       $(".table-content").css('max-height', '135px')
  }
}
</script>