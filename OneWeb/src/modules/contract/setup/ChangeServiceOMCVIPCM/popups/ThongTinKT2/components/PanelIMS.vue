<template>
  <div class="row">
    <div class="col-sm-8 col-12">
      <div class="row">
        <div class="col-12">
          <div class="info-row">
            <div class="key w80">Bras</div>
            <div class="value">
              <input type="text" class="form-control">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w80">Dslam</div>
            <div class="value">
              <input type="text" class="form-control">
            </div>
          </div>
          <div class="info-row">
            <div class="key w80">Shelf</div>
            <div class="value">
              <input type="text" class="form-control">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w80">Dslam</div>
            <div class="value">
              <input type="text" class="form-control">
            </div>
          </div>
          <div class="info-row">
            <div class="key w80">Shelf</div>
            <div class="value">
              <input type="text" class="form-control">
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-sm-4 col-12">
      <div class="info-row">
        <div class="key w80">Dslam</div>
        <div class="value">
          <input type="text" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Shelf</div>
        <div class="value">
          <input type="text" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Port</div>
        <div class="value">
          <input type="text" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Adsl Port</div>
        <div class="value">
          <input type="text" class="form-control">
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7 col-12">
          <div class="info-row">
            <div class="key w80">OLT Port</div>
            <div class="value">
              <input type="text" class="form-control">
            </div>
          </div>
        </div>
        <div class="col-sm-5 col-12">
          <div class="info-row">
            <div class="key w40">ONUID</div>
            <div class="value">
              <input type="text" class="form-control">
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Hộp cáp ngọn</div>
        <div class="value">
          <input type="text" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Cáp ngọn</div>
        <div class="value">
          <input type="text" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Tủ cáp ngọn</div>
        <div class="value">
          <input type="text" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Đôi cáp ngọn</div>
        <div class="value">
          <input type="text" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Port OLT</div>
        <div class="value">
          <input type="text" class="form-control">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PanelIMS',
  data () {
    return {
      pnlIMS: {
        visible: false,
        config: {
          txtBras: '',
          txtDslam_IMS: '',
          txtUsername: '',
          txtSystemIMS: '',
          txtadsl_port_ims: '',
          txtMaTruyNhapIMS: '',
          txtTuCapGocIMS: '',
          txtDoiCapGocIMS: '',
          txtCapGocIMS: '',
          txtSlotOLT_IMS: '',
          txtSlotIMS: '',
          txtPassword: '',
          txtRackIMS: '',
          txtSvLanIMS: '',
          txtLoaiCapIMS: '',
          txtTuCapNgonIMS: '',
          txtDoiCapNgonIMS: '',
          txtCapNgonIMS: '',
          txtPortOLT_IMS: '',
          txtThietBi: '',
          txtPortIMS: '',
          txtVPI: '',
          txtVCI: '',
          txtSelfIMS: '',
          txtPortMdf_IMS: '',
          txtCvLanIMS: '',
          txtHopCapIMS: '',
          txtTuyenCapIMS: '',
          txtLienTuIMS: '',
          txtCuLyIMS: 0
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
