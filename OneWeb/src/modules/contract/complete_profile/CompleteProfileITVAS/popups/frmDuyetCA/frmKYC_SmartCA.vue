<template>
  <div>
    <div>
      <!-- <breadcrumb :header="header" /> -->
      <div class="list-actions-top">
        <ul class="list">
          <li   @click="tsbtnThemMoi_Click()">
            <a> <span class="icon one-search"></span>Ký</a>
          </li>
          <li   @click="tsbtnCapNhat_Click()">
            <a> <span class="icon one-search"></span>Hủy</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <div class=''>
              <div> Thông tin SmartCA</div>

            </div>
          </div>
          <div class="row">
            <!-- v-if="gridSoThueBaoM2M.value.hasOwnProperty('tai_khoan')" -->
            <div class=" col-12">
              <div class="info-row">
                <div class="key">
                  UID
                </div>
                <div class="value">
                  <input :disabled="disable_input.UID" type="text" class="form-control" v-model="fromData.UID" />
                </div>
              </div>
            </div>
            <div class="col-12">
              <div class="info-row">
                <div class="key">
                  Serial
                </div>
                <div class="value">
                  <SelectExt ref="cboVaiTro" v-model="fromData.Serial" :dataSource="serial.list" dataTextField="text" dataValueField="id" />
                </div>
              </div>
            </div>
            <div class="col-12">
              <div class="info-row">
                <div class="key">
                  Secret Pass
                </div>
                <div class="value">
                  <input :disabled="disable_input.Pass" type="text" class="form-control" v-model="fromData.Pass" />
                </div>
              </div>
            </div>
            <div class="col-12">
              <div class="info-row">
                <div class="key">
                  OTP
                </div>
                <div class="value">
                  <input :disabled="disable_input.OTP" type="text" class="form-control" v-model="fromData.OTP" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--        <div class="box-form">-->
        <!--          <div class="legend-title">Số thuê bao M2M</div>-->
        <!--          <div class="table-content">-->
        <!--            <DataGrid ref="gridSoThueBaoM2M" :columns="gridSoThueBaoM2M.header" :dataSource="gridSoThueBaoM2M.list" :showFilter="true" dataKeyField="tai_khoan" :allowPaging="true" :enablePagingServer="false" @selectedRowChanged="gridSoThueBaoM2M_FocusedRowChanged" />-->
        <!--          </div>-->
        <!--        </div>-->
      </div>
    </div>
  </div>
</template>
<script>
// import breadcrumb from '@/components/breadcrumb'
// import moment from 'moment'
// import Vue from 'vue'
import api from './DuyetCA_API'

export default {
  emits: ['onBtnDuyetClicked'],
  props: {
    dulieu: {
      type: [Object],
      require: true
    }
  },

  data()  {
    return {
      rbtn_Check: 'CA',
      fromData: {
        UID: '',
        Serial: '',
        OTP: '',
        Pass: '',
      },
      serial: {
        list: [],
        selected: ''
      },
      disable_input: {
        UID: false,
        serial: false,
        Pass: false,
      }
    }
  },

  watch: {
    rbtn_Check(val) {
      if (val == 'CA') {
        this.disable_input = {
          UID: true,
          serial: false,
          pass_OTP: true,
        }
      }
      else {
        this.disable_input = {
          UID: false,
          serial: false,
          pass_OTP: false,
        }
      }
    },
  },
}
</script>
<style scoped>
</style>
