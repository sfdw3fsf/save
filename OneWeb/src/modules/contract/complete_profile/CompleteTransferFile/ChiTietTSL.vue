<template>
  <div>
    <b-modal ref="popupChitietTSL" size="xl" hide-footer hide-header hide-header-close
             body-class="modal-body p-0 frm-chon-port">
      <breadcrumb :header="headerTSL"/>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="accept">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span>Chấp nhận
            </a>
          </li>
          <li>
            <a href="#" @click.prevent="hideModal">
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
            </a>
          </li>
        </ul>
      </div>
      <div class="page-content">
        <div class="box-form">
          <div class="row">

            <div class="col-sm-4 col-12">
              <div class="box-form">
                <div class="legend-title">Điểm đầu</div>

                <div class="info-row">
                  <div class="key">Slot</div>
                  <div class="value">
                    <input v-model="slot_dau" type="text" class="form-control" v-bind:readonly="txtSlot_Dau">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Port</div>
                  <div class="value">
                    <input v-model="port_dau" type="text" class="form-control" v-bind:readonly="txtPort_Dau">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">TimeSlot</div>
                  <div class="value">
                    <input v-model="timeslot_dau" type="text" class="form-control" v-bind:readonly="txtTimeSlot_Dau">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Tbị truyền dẫn</div>
                  <div class="value">
                    <input v-model="ten_tbi_dau" type="text" class="form-control" v-bind:readonly="txtTBi_Dau">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Đôi cáp</div>
                  <div class="value">
                    <input v-model="doicap_dau" type="text" class="form-control" v-bind:readonly="txtDoiCapDau">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Hộp cáp</div>
                  <div class="value">
                    <input v-model="hopcap_dau" type="text" class="form-control" v-bind:readonly="txtHopCapDau">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Cự ly</div>
                  <div class="value">
                    <input v-model="culy_dau" type="text" class="form-control" v-bind:readonly="txtCuLyDau">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Địa chỉ</div>
                  <div class="value">
                    <div class="input-more-button">
                      <button class="btn" @click="btnDiaChidau_Click">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <input v-model="diachidau" type="text" class="form-control">
                      <DiaChiKhachHangModal ref="diaChiDau" :data="dataDiaChiDau" @accept="acceptDiaChiDau"/>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Đơn vị</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt
                        dataValueField="donvi_id"
                        dataTextField="ten_dv"
                        v-model="cboDonViDau.value"
                        :dataSource="cboDonViDau.option"
                      />
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Ghi chú</div>
                  <div class="value">
                    <input type="text" class="form-control">
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="box-form">
                <div class="legend-title">Điểm trung gian</div>

                <div class="info-row">
                  <div class="key">Slot</div>
                  <div class="value">
                    <input v-model="input.slot_cn" type="text" class="form-control">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Port</div>
                  <div class="value">
                    <input v-model="input.port_cn" type="text" class="form-control">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">TimeSlot</div>
                  <div class="value">
                    <input v-model="input.timeslot_cn" type="text" class="form-control">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Mapping</div>
                  <div class="value">
                    <input v-model="input.mapping_cn" type="text" class="form-control">
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="box-form">
                <div class="legend-title">Điểm cuối</div>

                <div class="info-row">
                  <div class="key">Slot</div>
                  <div class="value">
                    <input v-model="slot_cuoi" type="text" class="form-control" v-bind:readonly="txtSlot_Cuoi">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Port</div>
                  <div class="value">
                    <input v-model="port_cuoi" type="text" class="form-control" v-bind:readonly="txtPort_Cuoi">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">TimeSlot</div>
                  <div class="value">
                    <input v-model="timeslot_cuoi" type="text" class="form-control" v-bind:readonly="txtTimeSlot_Cuoi">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Tbị truyền dẫn</div>
                  <div class="value">
                    <input v-model="ten_tbi_cuoi" type="text" class="form-control" v-bind:readonly="txtTbi_Cuoi">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Đôi cáp</div>
                  <div class="value">
                    <input v-model="doicap_cuoi" type="text" class="form-control" v-bind:readonly="txtDoiCapCuoi">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Hộp cáp</div>
                  <div class="value">
                    <input v-model="hopcap_cuoi" type="text" class="form-control" v-bind:readonly="txtHopCapCuoi">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Cự ly</div>
                  <div class="value">
                    <input v-model="culy_cuoi" type="text" class="form-control" v-bind:readonly="txtCuLyCuoi">
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Địa chỉ</div>
                  <div class="value">
                    <div class="input-more-button">
                      <button class="btn" @click="btnDiaChicuoi_Click">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <input v-model="diachicuoi" type="text" class="form-control">
                      <DiaChiKhachHangModal ref="diaChiCuoi" :data="dataDiaChiCuoi" @accept="acceptDiaChiCuoi"/>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Đơn vị</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt
                        dataValueField="donvi_id"
                        dataTextField="ten_dv"
                        v-model="cboDonViCuoi.value"
                        :dataSource="cboDonViCuoi.option"
                      />
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Nội dung giao</div>
                  <div class="value">
                    <input type="text" class="form-control">
                  </div>
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
import breadcrumb from '@/components/breadcrumb'
import api from "./api";
import DiaChiKhachHangModal from '../../../contract/setup/SurveyRequest/popups/ChonDiaChi/DiaChiKhachHangModal.vue'

export default {
  name: "ChiTietTSL",
  components: {breadcrumb, DiaChiKhachHangModal},
  props: [
    'input'
  ],
  data() {
    return {
      headerTSL: {
        title: 'Thông số chi tiết truyền số liệu',
      },
      dataEmit:{
        apcuoi_id: null,
        apdau_id: null,
        culy_cuoi: null,
        culy_dau: 0,
        diachicuoi: "",
        diachidau: "",
        doicap_cuoi: null,
        doicap_dau: null,
        donvi_cuoi_id: 0,
        donvi_dau_id: 0,
        hopcap_cuoi: null,
        hopcap_dau: null,
        mapping_cn: null,
        phocuoi_id: null,
        phodau_id: null,
        phuongcuoi_id: null,
        phuongdau_id: null,
        port_cn: null,
        port_cuoi: null,
        port_dau: null,
        quancuoi_id: null,
        quandau_id: null,
        slot_cn: null,
        slot_cuoi: null,
        slot_dau: null,
        ten_tbi_cuoi: null,
        ten_tbi_dau: null,
        timeslot_cn: null,
        timeslot_cuoi: null,
        timeslot_dau: null,
        tinhcuoi_id: null,
        tinhdau_id: null,
      },

      slot_dau: null,
      port_dau: null,
      timeslot_dau: null,
      ten_tbi_dau: '',

      slot_cuoi: null,
      port_cuoi: null,
      timeslot_cuoi: null,
      ten_tbi_cuoi: '',

      slot_cn: null,
      port_cn: null,
      timeslot_cn: null,
      mapping_cn: null,

      doicap_dau: null,
      doicap_cuoi: null,
      hopcap_dau: null,
      hopcap_cuoi: null,
      culy_dau: null,
      culy_cuoi: null,
      donvi_dau_id: null,
      donvi_cuoi_id: null,

      diachidau: '',
      khudau_id: null,
      apdau_id: null,
      phodau_id: null,
      phuongdau_id: null,
      quandau_id: null,
      tinhdau_id: null,
      sonhadau: '',

      diachicuoi: '',
      khucuoi_id: null,
      apcuoi_id: null,
      phocuoi_id: null,
      phuongcuoi_id: null,
      quancuoi_id: null,
      tinhcuoi_id: null,
      sonhacuoi: '',

      kieu: 0,

      cboDonViDau: {
        option: [],
        value: null
      },
      cboDonViCuoi: {
        option: [],
        value: null
      },
      txtCuLyDau: true,
      txtDoiCapDau: true,
      txtHopCapDau: true,
      txtSlot_Dau: true,
      txtPort_Dau: true,
      txtTimeSlot_Dau: true,
      txtTBi_Dau: true,

      txtCuLyCuoi: false,
      txtDoiCapCuoi: false,
      txtHopCapCuoi: false,
      txtSlot_Cuoi: false,
      txtPort_Cuoi: false,
      txtTimeSlot_Cuoi: false,
      txtTbi_Cuoi: false,

      dataDiaChiCuoi: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      },
      dataDiaChiDau: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      }
    }
  },
  async created() {
  },
  methods: {
    async showModal() {
      // console.log(this.input)
      await this.frmThongSoTSL_Load();
      this.$refs["popupChitietTSL"].show();
    },
    hideModal() {
      this.$refs["popupChitietTSL"].hide();
    },
    accept() {
      this.dataEmit.slot_dau = this.slot_dau;
      this.dataEmit.port_dau = this.port_dau;
      this.dataEmit.timeslot_dau = this.timeslot_dau;
      this.dataEmit.ten_tbi_dau = this.ten_tbi_dau;
      this.dataEmit.slot_cuoi = this.slot_cuoi;
      this.dataEmit.port_cuoi = this.port_cuoi;
      this.dataEmit.timeslot_cuoi = this.timeslot_cuoi;
      this.dataEmit.ten_tbi_cuoi = this.ten_tbi_cuoi;
      this.dataEmit.slot_cn = this.slot_cn;
      this.dataEmit.port_cn = this.port_cn;
      this.dataEmit.timeslot_cn = this.timeslot_cn;
      this.dataEmit.mapping_cn = this.mapping_cn;

      this.dataEmit.doicap_dau = this.doicap_dau;
      this.dataEmit.doicap_cuoi = this.doicap_cuoi;
      this.dataEmit.hopcap_dau = this.hopcap_dau;
      this.dataEmit.hopcap_cuoi = this.hopcap_cuoi;
      this.dataEmit.culy_dau = this.culy_dau;
      this.dataEmit.culy_cuoi = this.culy_cuoi;
      this.dataEmit.donvi_dau_id = this.donvi_dau_id;
      this.dataEmit.donvi_cuoi_id = this.donvi_cuoi_id;
      //Hiếu 12/09/2010
      this.dataEmit.diachidau = this.diachidau;
      this.dataEmit.tinhdau_id = this.tinhdau_id;
      this.dataEmit.quandau_id = this.quandau_id;
      this.dataEmit.phuongdau_id = this.phuongdau_id;
      this.dataEmit.phodau_id = this.phodau_id;
      this.dataEmit.apdau_id = this.apdau_id;

      this.dataEmit.diachicuoi = this.diachicuoi;
      this.dataEmit.tinhcuoi_id = this.tinhcuoi_id;
      this.dataEmit.quancuoi_id = this.quancuoi_id;
      this.dataEmit.phuongcuoi_id = this.phuongcuoi_id;
      this.dataEmit.phocuoi_id = this.phocuoi_id;
      this.dataEmit.apcuoi_id = this.apcuoi_id;
      this.$emit('accept', this.dataEmit)
      this.hideModal()
    },
    async frmThongSoTSL_Load() {

      this.slot_dau = this.input.slot_dau;
      this.port_dau = this.input.port_dau;
      this.timeslot_dau = this.input.timeslot_dau;
      this.ten_tbi_dau = this.input.ten_tbi_dau;

      this.slot_cuoi = this.input.slot_cuoi;
      this.port_cuoi = this.input.port_cuoi;
      this.timeslot_cuoi = this.input.timeslot_cuoi;
      this.ten_tbi_cuoi = this.input.ten_tbi_cuoi;

      this.slot_cn = this.input.slot_cn;
      this.port_cn = this.input.port_cn;
      this.timeslot_cn = this.input.timeslot_cn;
      this.mapping_cn = this.input.mapping_cn;

      this.doicap_dau = this.input.doicap_dau;
      this.doicap_cuoi = this.input.doicap_cuoi;
      this.hopcap_dau = this.input.hopcap_dau;
      this.hopcap_cuoi = this.input.hopcap_cuoi;
      this.culy_dau = this.input.culy_dau;
      this.culy_cuoi = this.input.culy_cuoi;
      this.donvi_dau_id = this.input.donvi_dau_id;
      this.donvi_cuoi_id = this.input.donvi_cuoi_id;
      //Hiếu bổ sung thêm một số thông tin TSL ngày 12/09/2010
      if (this.input.diachidau !== null) {
        this.diachidau = this.input.diachidau;
      }
      this.apdau_id = this.input.apdau_id;
      this.phodau_id = this.input.phodau_id;
      this.phuongdau_id = this.input.phuongdau_id;
      this.quandau_id = this.input.quandau_id;
      this.tinhdau_id = this.input.tinhdau_id;

      if (this.input.diachicuoi !== null) {
        this.diachicuoi = this.input.diachicuoi;
      }
      this.apcuoi_id = this.input.apcuoi_id;
      this.phocuoi_id = this.input.phocuoi_id;
      this.phuongcuoi_id = this.input.phuongcuoi_id;
      this.quancuoi_id = this.input.quancuoi_id;
      this.tinhcuoi_id = this.input.tinhcuoi_id;
      this.kieu = this.input.kieu;

      await this.HT_DonVi_Combobox(this.cboDonViDau);
      await this.HT_DonVi_Combobox(this.cboDonViCuoi);
      this.cboDonViDau.value = this.donvi_dau_id;
      this.cboDonViCuoi.value = this.donvi_cuoi_id;

      if (this.kieu === 1) {
        await this.HienThi_Panel(1, false);
        await this.HienThi_Panel(2, true);
      } else if (this.kieu === 2) {
        await this.HienThi_Panel(1, true);
        await this.HienThi_Panel(2, false);
      } else if (this.kieu === 3) {
        await this.HienThi_Panel(1, false);
        await this.HienThi_Panel(2, false);
      } else {
        await this.HienThi_Panel(1, true);
        await this.HienThi_Panel(2, true);
      }
    },
    async HT_DonVi_Combobox(cbo) {
      try {
        cbo.option = []
        let post = {
          "loaidv_id": 5 // TRAMVT
        }
        const response = await api.sp_ht_donvi_combobox(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].donvi_id
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log(e)
        console.log('Lấy danh sách không thành công')
      } finally {

      }
    },
    async HienThi_Panel(kieu, kt) {
      if (kieu === 1)//Panel đầu
      {
        this.txtCuLyDau = kt;
        this.txtDoiCapDau = kt;
        this.txtHopCapDau = kt;
        this.txtSlot_Dau = kt;
        this.txtPort_Dau = kt;
        this.txtTimeSlot_Dau = kt;
        this.txtTBi_Dau = kt;
      } else if (kieu === 2) {
        this.txtCuLyCuoi = kt;
        this.txtDoiCapCuoi = kt;
        this.txtHopCapCuoi = kt;
        this.txtSlot_Cuoi = kt;
        this.txtPort_Cuoi = kt;
        this.txtTimeSlot_Cuoi = kt;
        this.txtTbi_Cuoi = kt;
      }

    },
    btnDiaChidau_Click() {
      this.dataDiaChiDau.tinh_id = this.tinhdau_id;
      this.dataDiaChiDau.quan_id = this.quandau_id;
      this.dataDiaChiDau.phuong_id = this.phuongdau_id;
      this.dataDiaChiDau.pho_id = this.phodau_id;
      this.dataDiaChiDau.ap_id = this.apdau_id;
      this.dataDiaChiDau.khu_id = this.khudau_id;
      this.dataDiaChiDau.sonha = this.sonhadau;
      this.dataDiaChiDau.diachi = this.diachidau;
      this.$refs.diaChiDau.showModal()
    },
    acceptDiaChiDau(obj) {
      this.tinhdau_id = obj.tinh_id;
      this.quandau_id = obj.quan_id;
      this.phuongdau_id = obj.phuong_id;
      this.phodau_id = obj.pho_id;
      this.khudau_id = obj.ap_id;
      this.khudau_id = obj.khu_id;
      this.sonhadau = obj.sonha;
      this.diachidau = obj.diachi;
    },
    btnDiaChicuoi_Click() {
      this.dataDiaChiCuoi.tinh_id = this.tinhcuoi_id;
      this.dataDiaChiCuoi.quan_id = this.quancuoi_id;
      this.dataDiaChiCuoi.phuong_id = this.phuongcuoi_id;
      this.dataDiaChiCuoi.pho_id = this.phocuoi_id;
      this.dataDiaChiCuoi.ap_id = this.apcuoi_id;
      this.dataDiaChiCuoi.khu_id = this.khucuoi_id;
      this.dataDiaChiCuoi.sonha = this.sonhacuoi;
      this.dataDiaChiCuoi.diachi = this.diachicuoi;
      this.$refs.diaChiCuoi.showModal()
    },
    acceptDiaChiCuoi(obj) {
      this.tinhcuoi_id = obj.tinh_id;
      this.quancuoi_id = obj.quan_id;
      this.phuongcuoi_id = obj.phuong_id;
      this.phocuoi_id = obj.pho_id;
      this.apcuoi_id = obj.ap_id;
      this.khucuoi_id = obj.khu_id;
      this.sonhacuoi = obj.sonha;
      this.diachicuoi = obj.diachi;
    },
  }
}
</script>

<style scoped>
.page-content {
  top: 10px;
}

input[readonly] {
  background-color: white;
}
</style>
