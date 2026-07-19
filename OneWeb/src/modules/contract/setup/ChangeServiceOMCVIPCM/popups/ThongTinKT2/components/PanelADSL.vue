<template>
  <div class="row">
    <div class="col-sm-4 col-12">
      <div class="info-row">
        <div class="key w80">Bras</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtBrasADSL" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Rack</div>
        <div class="value">
          <input type="text"  v-model="pnlADSL.form.txtRack" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Slot</div>
        <div class="value">
          <input type="text"  v-model="pnlADSL.form.txtSlot" class="form-control">
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w80">Vpi/Svlan</div>
            <div class="value">
              <input type="text"  v-model="pnlADSL.form.txtVPIADSL" class="form-control">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w60">Vd/Cvlan</div>
            <div class="value">
              <input type="text" v-model="pnlADSL.form.txtVCIADSL" class="form-control">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w80">Self</div>
            <div class="value">
              <input type="text" v-model="pnlADSL.form.txtSelfid01" class="form-control">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w60">OLT Slot</div>
            <div class="value">
              <input type="text" v-model="pnlADSL.form.txtSelfid02" class="form-control">
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Loại cáp</div>
        <div class="value">
          <input type="text"  v-model="pnlADSL.form.txtLoaiCapADSL"  class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Cáp gốc</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtcapgoc_adsl"  class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Tủ cáp gốc</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtTuCapGocADSL" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Đôi cáp gốc</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtDoiCapGocADSL" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Slot QLT</div>
        <div class="value">
          <input type="text"  v-model="pnlADSL.form.txtSlotOLT_Adsl" class="form-control">
        </div>
      </div>
    </div>
    <div class="col-sm-4 col-12">
      <div class="info-row">
        <div class="key w90">Dslam</div>
        <div class="value">
          <input type="text"  v-model="pnlADSL.form.txtDslamADSL" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Shelf</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtShelf" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Port</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtPort" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Adsl Port</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtADSL_Port" class="form-control">
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7 col-12">
          <div class="info-row">
            <div class="key w90">OLT Port</div>
            <div class="value">
              <input type="text" v-model="pnlADSL.form.txtSelfid03" class="form-control">
            </div>
          </div>
        </div>
        <div class="col-sm-5 col-12">
          <div class="info-row">
            <div class="key w40">ONUID</div>
            <div class="value">
              <input type="text" v-model="pnlADSL.form.txtSelfid04" class="form-control">
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Hộp cáp ngọn</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtHopCapNgonADSL" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Cáp ngọn</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtCapNgonADSL" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Tủ cáp ngọn</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtTuCapNgonADSL" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Đôi cáp ngọn</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtDoiCapNgonADSL" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w90">Port OLT</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtPortOLT_Adsl" class="form-control">
        </div>
      </div>
    </div>
    <div class="col-sm-4 col-12">
      <div class="info-row">
        <div class="key w70">IP Dslam</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtIpDsLam"  class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">Trạm T.Bị</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtTramTB" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">System</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtSystem" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">Port MDF</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtPortMDF" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">NasportID</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtNasportID" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">Số ảo</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtMaLT" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">Tuyến cáp</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtTuyenCapADSL" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">Liên tủ</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtLienTuADSL" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">Cự ly</div>
        <div class="value">
          <input type="text" v-model="pnlADSL.form.txtCuLyADSL" class="form-control">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PanelADSL',
  props: {

  },
  data () {
    return {
      pnlADSL: {
        visible: false,
        form: {
          txtBrasADSL: '',
          txtRack: '',
          txtSlot: '',
          txtVPIADSL: '',
          txtVCIADSL: '',
          txtSelfid01: '',
          txtSelfid02: '',
          txtLoaiCapADSL: '',
          txtcapgoc_adsl: '',
          txtTuCapGocADSL: '',
          txtDoiCapGocADSL: '',
          txtSlotOLT_Adsl: '',
          txtDslamADSL: '',
          txtShelf: '',
          txtPort: '',
          txtADSL_Port: '',
          txtSelfid03: '',
          txtSelfid04: '',
          txtHopCapNgonADSL: '',
          txtCapNgonADSL: '',
          txtTuCapNgonADSL: '',
          txtDoiCapNgonADSL: '',
          txtPortOLT_Adsl: '',
          txtIpDsLam: '',
          txtTramTB: '',
          txtSystem: '',
          txtPortMDF: '',
          txtNasportID: '',
          txtMaLT: '',
          txtTuyenCapADSL: '',
          txtLienTuADSL: '',
          txtCuLyADSL: 0
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
