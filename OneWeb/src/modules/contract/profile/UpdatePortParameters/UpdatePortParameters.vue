<template>
    <div>
        <div class="breadcrumb-top">
            <div class="main-title">Cập nhật thông số cổng</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ
                    </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">Lắp đặt mới</li>
            </ul>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="syncPttpToVisa">
                        <span class="icon one-sync1"></span>Đồng bộ từ PTTB lên Visa
                    </a>
                </li>
                <li>
                    <a @click="syncVisaToPttb">
                        <span class="icon one-sync1"></span>Đồng bộ từ Visa về PTTB
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">Thông tin thuê bao</div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Account</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="subcriberCode"
                                            v-on:keyup="onInputSubcriberCode">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Số ảo</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="virtualNumber">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Trạng thái TB</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="subcriberStatus">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Số máy TN</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="maTN">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Tên thuê bao</div>
                            <div class="value">
                                <input type="text" class="form-control"
                                    v-model="subcriberName">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Địa chỉ LĐ</div>
                            <div class="value">
                                <input type="text" class="form-control"
                                    v-model="addressLD">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <vue-select
                                        v-model="selectedServiceId"
                                        :required="false"
                                        :labelWidth="'90'"
                                        :disable="false"
                                        :options="getServices"
                                        :valueField="'DICHVUVT_ID'"
                                        :labelField="'TEN_DVVT'"
                                        label="Dịch vụ"
                                        :checkbox="false"
                                        v-on:input="onSelectedService"
                                    ></vue-select>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Mã đôi cáp</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="maDoiCap">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <vue-select
                                v-model="selectedSubcriberTypeId"
                                :required="false"
                                :labelWidth="'90'"
                                :disable="true"
                                :options="getSubcribertypes"
                                :valueField="'LOAITB_ID'"
                                :labelField="'LOAIHINH_TB'"
                                label="Loại hình"
                                :checkbox="false"
                                v-on:input="onSelectedType"
                            ></vue-select>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Tốc độ</div>
                            <div class="value">
                                <input type="text" class="form-control"
                                    v-model="speed">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Ghi chú</div>
                            <div class="value">
                                <input type="text" class="form-control"
                                    v-model="note">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông số PTTB</div>
                        <div class="info-row">
                            <vue-select
                                v-model="brasId"
                                :required="false"
                                :labelWidth="'90'"
                                :disable="true"
                                :options="getBrasList"
                                :valueField="'bras_id'"
                                :labelField="'tenbras'"
                                label="Bras"
                                :checkbox="false"
                                v-on:input="onSelectedType"
                            ></vue-select>
                        </div>
                        <div class="info-row">
                            <vue-select
                                v-model="dsLamId"
                                :required="false"
                                :labelWidth="'90'"
                                :disable="true"
                                :options="getDsLam"
                                :valueField="'DSLAM_ID'"
                                :labelField="'TENDSLAM'"
                                label="Dslam Visa"
                                :checkbox="false"
                            ></vue-select>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Dslam tỉnh</div>
                            <div class="value">
                                <input type="text" class="form-control"
                                    v-model="dsLamTinh">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">System</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="system">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Ip Dslam</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="ipDsLam">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Rack</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                         v-model="rack">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Shelf</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="shelf">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Slot</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="slotADSL">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Port</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="port">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Vpi/Svlan</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="vpiADSL">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Vci/Cvlan</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="vciADSL">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Self</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="selfId01">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">QLT Slot</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="qltSlot">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">OLT Port</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="oltPort">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">ONUID</div>
                                    <div class="value">
                                        <input type="text" class="form-control"
                                            v-model="onUID">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông số Visa</div>
                        <div class="info-row">
                            <vue-select
                                v-model="brasIdVisa"
                                :required="false"
                                :labelWidth="'90'"
                                :disable="true"
                                :options="getBrasList"
                                :valueField="'bras_id'"
                                :labelField="'tenbras'"
                                label="Bras"
                                :checkbox="false"
                            ></vue-select>
                        </div>
                        <div class="info-row">
                            <vue-select
                                v-model="dsLamIdVisa"
                                :required="false"
                                :labelWidth="'90'"
                                :disable="true"
                                :options="getDsLam"
                                :valueField="'DSLAM_ID'"
                                :labelField="'TENDSLAM'"
                                label="Dslam Visa"
                                :checkbox="false"
                            ></vue-select>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Dslam tỉnh</div>
                            <div class="value">
                                <input type="text" class="form-control highlight">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">System</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="systemVisa">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Ip Dslam</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="ipDsLamVisa">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Rack</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="rackVisa">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Shelf</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="shelfVisa">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Slot</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="slotADSLVisa">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Port</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="portVisa">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Vpi/Svlan</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="vpiADSLVisa">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Vci/Cvlan</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="vciADSLVisa">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Self</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="selfId01Visa">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">QLT Slot</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="qltSlotVisa">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">OLT Port</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">ONUID</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight"
                                            v-model="onUIDVisa">
                                    </div>
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
import api from './api/index'
import axios from 'axios'
import { mapActions, mapGetters } from 'vuex'
import * as moment from 'moment'

export default {
  data () {
    return {
      selectedServiceId: null,
      selectedSubcriberTypeId: null,
      subcriberCode: '',
      subcriberName: '',
      addressLD: '',
      subcriberStatus: '',
      speed: null,
      virtualNumber: null,
      maDoiCap: null,
      note: null,
      vciADSL: null,
      vciADSLVisa: null,
      vpiADSL: null,
      vpiADSLVisa: null,
      slotADSL: null,
      slotADSLVisa: null,
      port: null,
      portVisa: null,
      brasId: null,
      brasIdVisa: null,
      dsLamId: null,
      dsLamIdVisa: null,
      dsLamTinh: null,
      system: null,
      systemVisa: null,
      rack: null,
      rackVisa: null,
      shelf: null,
      shelfVisa: null,
      selfId01: null,
      selfId01Visa: null,
      qltSlot: null,
      qltSlotVisa: null,
      oltPort: null,
      oltPortVisa: null,
      onUID: null,
      onUIDVisa: null,
      ipDsLam: null,
      ipDsLamVisa: null,
      maTN: null
    }
  },
  computed: {
    ...mapGetters('portParametersCommon', [
      'getServices',
      'getSubcribertypes',
      'getSpeedList',
      'getBrasList',
      'getDsLam'
    ])
  },
  methods: {
    ...mapActions('portParametersCommon', [
      'setSubcriberTypes',
      'setBrasList',
      'setServices',
      'setDsLam',
      'setSpeedList'
    ]),
    onSelectedService: function (e) {
    },
    onSelectedType: function (e) {
    },
    onInputSubcriberCode: async function (e) {
      // check enter is pressed
      if (e.keyCode === 13) {
        this.$root.showLoading(true)
        try {
          // HienThiThongTin
          const res = await api.getSubcriberInforList(axios, {
            maTb: this.subcriberCode,
            dichVuVtId: this.selectedServiceId,
            donViDlId: 0
          })

          if (res.data.data) {
            const selectedRow = res.data.data
            this.selectedServiceId = selectedRow.dichvuvt_id
            this.subcriberName = selectedRow.ten_tb
            this.addressLD = selectedRow.diachi_ld
            this.selectedSubcriberTypeId = selectedRow.loaitb_id
            this.subcriberStatus = selectedRow.trangthai_tb
            const speed = this.getSpeedList.find(item => item.TOCDO_ID === selectedRow.tocdo_id)
            if (speed) {
              this.speed = speed.THUONGHIEU
            }
            this.virtualNumber = selectedRow.ma_lt
            this.note = selectedRow.ghichu

            const resDsAdsl = await api.getDsAdslBySubcriberId(axios, {
              thueBaoId: selectedRow.thuebao_id
            })

            if (resDsAdsl.data.data && resDsAdsl.data.data.length > 0) {
              const selectedAdsl = resDsAdsl.data.data[0]
              this.maDoiCap = selectedAdsl.madoicap
              this.vciADSL = selectedAdsl.vci
              this.vpiADSL = selectedAdsl.vpi
              this.slotADSL = selectedAdsl.slot
              this.port = selectedAdsl.port
              this.brasId = selectedAdsl.bras_id
              this.dsLamId = selectedAdsl.dslam_id
              this.dsLamTinh = selectedAdsl.tendslam
              this.system = selectedAdsl.system
              this.rack = selectedAdsl.rack
              this.shelf = selectedAdsl.shelf
              this.selfId01 = selectedAdsl.selfid01 !== null ? selectedAdsl.selfid01 : '1'
              this.qltSlot = selectedAdsl.selfid02
              this.oltPort = selectedAdsl.selfid03
              this.onUID = selectedAdsl.selfid04
              this.ipDsLam = selectedAdsl.ip_dslam
              this.maTN = selectedAdsl.matb_tn
            }
          } else {
            this.$toast.error(`Không tìm thấy thông tin về thuê bao: ${this.subcriberCode}. Đề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT !`)
          }

          // TraCuuTB_ADSL
          const resCheckSubcriber = await api.checkSubcriber(axios, {
            accountName: this.subcriberCode
          })
          if (resCheckSubcriber.data.data && resCheckSubcriber.data.data &&
          resCheckSubcriber.data.data.length > 0) {
            let accountModel = null
            let customerModel = null
            let accountServiceModel = null
            let attributes = null
            let reserveModel = null
            for (let i = 0; i < resCheckSubcriber.data.data.length; i++) {
              switch (i) {
                case 0: // 'AccountModel'
                  accountModel = resCheckSubcriber.data.data[i]
                  break
                case 1: // 'CustomerModel'
                  customerModel = resCheckSubcriber.data.data[i]
                  break
                case 2: // 'AccountServiceModel'
                  accountServiceModel = resCheckSubcriber.data.data[i]
                  attributes = accountServiceModel.attributes
                  break
                case 3: // 'ReserveModel'
                  reserveModel = resCheckSubcriber.data.data[i]
                  break
              }
            }

            for (let i = 0; i < attributes.length; i++) {
              switch (attributes[i].type) {
                case 'BRAS-ID':
                  this.brasIdVisa = attributes[i].value
                  break
                case 'BRAS-IPADDRESS':
                  this.ipDsLamVisa = attributes[i].value
                  break
                case 'DSLAM-ID':
                  this.dsLamIdVisa = parseInt(attributes[i].value)
                  break
                case 'SLOT':
                  this.slotADSLVisa = attributes[i].value
                  break
                case 'PORT':
                  this.portVisa = attributes[i].value
                  break
                case 'VPI':
                  this.vpiADSLVisa = attributes[i].value
                  break
                case 'VCI':
                  this.vciADSLVisa = attributes[i].value
                  break
                case 'SYSTEMID':
                  this.systemVisa = attributes[i].value
                  break
                case 'RACKID':
                  this.rackVisa = attributes[i].value
                  break
                case 'SELFID':
                  this.shelfVisa = attributes[i].value
                  break
                case 'SELFID01':
                  this.shelfVisa = attributes[i].value
                  break
                case 'SELFID02':
                  this.qltSlotVisa = attributes[i].value
                  break
                case 'SELFID03':
                  this.oltPortVisa = attributes[i].value
                  break
                case 'SELFID04':
                  this.onUIDVisa = attributes[i].value
                  break
              }
            }
          }
        } catch (error) {
          console.log(error)
          this.$toast.error(error.toString())
        }
        this.$root.showLoading(false)
      }
    },
    syncPttpToVisa: async function () {
      this.$root.showLoading(true)
      try {
        const res = await api.syncVisaAndPttb(axios, {
          p_kieu: this.selectedSubcriberTypeId,
          p_matb: this.subcriberCode,
          p_dslam_pttb: this.dsLamId,
          p_dslam_visa: this.dsLamIdVisa !== undefined && this.dsLamIdVisa !== null ? this.dsLamIdVisa : '1',
          p_bras_pttb: this.brasId,
          p_bras_visa: this.brasIdVisa !== undefined && this.brasIdVisa !== null ? this.brasIdVisa : '1',
          p_slot_pttb: this.slotADSL,
          p_port_pttb: this.port,
          p_vci_pttb: this.vciADSL,
          p_vpi_pttb: this.vpiADSL,
          p_rack_pttb: this.rack,
          p_shelf_pttb: this.shelf,
          p_system_pttb: this.system,
          p_slot_visa: this.slotADSLVisa !== undefined && this.slotADSLVisa !== null ? this.slotADSLVisa : '1',
          p_port_visa: this.portVisa !== undefined && this.portVisa !== null ? this.portVisa : '',
          p_vci_visa: this.vciADSLVisa !== undefined && this.vciADSLVisa !== null ? this.vciADSLVisa : '1',
          p_vpi_visa: this.vpiADSLVisa !== undefined && this.vpiADSLVisa !== null ? this.vpiADSLVisa : '1',
          p_rack_visa: this.rackVisa !== undefined && this.rackVisa !== null ? this.rackVisa : '1',
          p_shelf_visa: this.shelfVisa !== undefined && this.shelfVisa !== null ? this.shelfVisa : '1',
          p_system_visa: this.systemVisa !== undefined && this.systemVisa !== null ? this.systemVisa : '1',
          p_selfid01_visa: this.selfId01Visa !== undefined && this.selfId01Visa !== null ? this.selfId01Visa : '1',
          p_selfid02_visa: this.qltSlotVisa !== undefined && this.qltSlotVisa !== null ? this.qltSlotVisa : '1',
          p_selfid03_visa: this.oltPortVisa !== undefined && this.oltPortVisa !== null ? this.oltPortVisa : '1',
          p_selfid04_visa: this.onUIDVisa !== undefined && this.onUIDVisa !== null ? this.onUIDVisa : '1',
          p_selfid01_pttb: this.selfId01 !== undefined && this.selfId01 !== null ? this.selfId01 : '1',
          p_selfid02_pttb: this.qltSlot !== undefined && this.qltSlot !== null ? this.qltSlot : '1',
          p_selfid03_pttb: this.oltPort !== undefined && this.oltPort != null ? this.oltPort : '1',
          p_selfid04_pttb: this.onUID !== undefined && this.onUID !== null ? this.onUID : '1',
          p_nguoi_cn: this.$root.token.getUserName(),
          p_ngay_cn: moment(new Date()).format('DD/MM/YYYY'),
          p_may_cn: '',
          p_ghichu: this.note !== undefined && this.note !== null ? this.note : '',
          p_option: 'pttb_to_visa'
        })

        if (res.data.message_detail) {
          throw res.data.message_detail
        }

        if (res.data.data.toString() === '1') {
          this.$toast.success(`Đồng bộ account ${this.subcriberCode} lên Visa thành công !`)
        } else {
          throw res.data.message_detail
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    syncVisaToPttb: async function () {
      this.$root.showLoading(true)
      try {
        const res = await api.syncVisaAndPttb(axios, {
          p_kieu: this.selectedSubcriberTypeId,
          p_matb: this.subcriberCode,
          p_dslam_pttb: this.dsLamId,
          p_dslam_visa: this.dsLamIdVisa !== undefined && this.dsLamIdVisa !== null ? this.dsLamIdVisa : '1',
          p_bras_pttb: this.brasId,
          p_bras_visa: this.brasIdVisa !== undefined && this.brasIdVisa !== null ? this.brasIdVisa : '1',
          p_slot_pttb: this.slotADSL,
          p_port_pttb: this.port,
          p_vci_pttb: this.vciADSL,
          p_vpi_pttb: this.vpiADSL,
          p_rack_pttb: this.rack,
          p_shelf_pttb: this.shelf,
          p_system_pttb: this.system,
          p_slot_visa: this.slotADSLVisa !== undefined && this.slotADSLVisa !== null ? this.slotADSLVisa : '1',
          p_port_visa: this.portVisa !== undefined && this.portVisa !== null ? this.portVisa : '',
          p_vci_visa: this.vciADSLVisa !== undefined && this.vciADSLVisa !== null ? this.vciADSLVisa : '1',
          p_vpi_visa: this.vpiADSLVisa !== undefined && this.vpiADSLVisa !== null ? this.vpiADSLVisa : '1',
          p_rack_visa: this.rackVisa !== undefined && this.rackVisa !== null ? this.rackVisa : '1',
          p_shelf_visa: this.shelfVisa !== undefined && this.shelfVisa !== null ? this.shelfVisa : '1',
          p_system_visa: this.systemVisa !== undefined && this.systemVisa !== null ? this.systemVisa : '1',
          p_selfid01_visa: this.selfId01Visa !== undefined && this.selfId01Visa !== null ? this.selfId01Visa : '1',
          p_selfid02_visa: this.qltSlotVisa !== undefined && this.qltSlotVisa !== null ? this.qltSlotVisa : '1',
          p_selfid03_visa: this.oltPortVisa !== undefined && this.oltPortVisa !== null ? this.oltPortVisa : '1',
          p_selfid04_visa: this.onUIDVisa !== undefined && this.onUIDVisa !== null ? this.onUIDVisa : '1',
          p_selfid01_pttb: this.selfId01 !== undefined && this.selfId01 !== null ? this.selfId01 : '1',
          p_selfid02_pttb: this.qltSlot !== undefined && this.qltSlot !== null ? this.qltSlot : '1',
          p_selfid03_pttb: this.oltPort !== undefined && this.oltPort != null ? this.oltPort : '1',
          p_selfid04_pttb: this.onUID !== undefined && this.onUID !== null ? this.onUID : '1',
          p_nguoi_cn: this.$root.token.getUserName(),
          p_ngay_cn: moment(new Date()).format('DD/MM/YYYY'),
          p_may_cn: '',
          p_ghichu: this.note !== undefined && this.note !== null ? this.note : '',
          p_option: 'visa_to_pttb'
        })

        if (!res.data.data) {
          throw res.data.message_detail
        }

        if (res.data.data.toString() === '1') {
          this.$toast.success(`Cập nhật thành công`)
        } else {
          throw res.data.message_detail
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    }
  },
  created: async function () {
    this.$root.showLoading(true)
    try {
      const resSubcriberTypes = await api.getSubcriberTypes(axios)
      if (resSubcriberTypes.data.data) {
        this.setSubcriberTypes(resSubcriberTypes.data.data)
      }

      const resBrasList = await api.getBrasList(axios, {})
      if (resBrasList.data.data) {
        this.setBrasList(resBrasList.data.data)
      }

      const resServices = await api.getServices(axios)
      if (resServices.data.data) {
        this.setServices(resServices.data.data)
      }

      const resDsLam = await api.getDsLam(axios)
      if (resDsLam.data.data) {
        this.setDsLam(resDsLam.data.data)
      }

      const resSpeedList = await api.getSpeedList(axios)
      if (resSpeedList.data.data) {
        this.setSpeedList(resSpeedList.data.data)
      }
    } catch (error) {
      console.log(error)
      this.$toast.error(error.toString())
    }
    this.$root.showLoading(false)
  }
}
</script>

<style scoped>
a:hover {
    cursor: pointer;
}
</style>