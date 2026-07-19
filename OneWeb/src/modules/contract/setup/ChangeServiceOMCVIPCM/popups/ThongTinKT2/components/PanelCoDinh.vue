<template>
  <div class="row">
    <div class="col-sm-4 col-12">
      <div class="info-row">
        <div class="key w80">Mã truy nhập</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtSoMayTN" class="form-control highlight">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Loại cáp</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtLoaiCap" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Cáp gốc (*)</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtCapGoc" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Tủ cáp gốc (*)</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtTuCapGoc" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Đôi cáp gốc (*)</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtDoiCapGoc" class="form-control">
        </div>
      </div>
    </div>
    <div class="col-sm-4 col-12">
      <div class="info-row">
        <div class="key w80">Lắp kèm</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtTBLapKem" class="form-control highlight">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Liên tủ</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtLienTu" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Cáp ngọn (*)</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtCapNgon" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Tủ cáp ngọn</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtTuCapNgon" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Đôi cáp ngọn (*)</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtDoiCapNgon" class="form-control">
        </div>
      </div>
    </div>
    <div class="col-sm-4 col-12">
      <div class="info-row">
        <div class="key w80">Ne</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtNE" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Hộp cáp ngọn</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtHopCapNgon" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Tuyến cáp</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtTuyenCap" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Vệ tinh</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtTramNE" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Cự ly (*)</div>
        <div class="value">
          <input type="text" v-model="pnlCoDinh.form.txtSoMetDay" class="form-control">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PanelCoDinh',
  data () {
    return {
      pnlCoDinh: {
        visible: false,
        config: {
          txtSoMayTN: '',
          txtTBLapKem: '',
          txtNE: '',
          txtHopCapNgon: '',
          txtTuyenCap: '',
          txtTramNE: '',
          txtSoMetDay: '',
          txtDoiCapNgon: '',
          txtTuCapNgon: '',
          txtCapNgon: '',
          txtLienTu: '',
          txtDoiCapGoc: '',
          txtTuCapGoc: '',
          txtCapGoc: '',
          txtLoaiCap: ''
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
