<template>
  <div class="box-form">
    <div class="legend-title padb0">
        <div class="pull-left mart10">
            Thông tin phiếu
        </div>
        <app-ky-cuoc></app-ky-cuoc>
        <div class="clearfix"></div>
    </div>
    <div class="row">
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w80">
                    Bước xử lý
                </div>
                <div class="value">
                    <div class="select-custom">
                        <b-form-select
                          id="id-buocxuly-no"
                          v-model="f_buoc_xuly_no"
                          value-field="BUOCXLN_ID"
                          text-field="BUOC_XLN"
                          :options="p_buocxyly_no_options"
                          @input="onBuocXuLyNoChange"
                          class="form-control">
                        </b-form-select>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-2 col-12">
            <div class="info-row">
                <div class="key w90">
                  <div class="">
                    <b-form-checkbox
                      id = "p-chk-ngaygiao-tungay"
                      v-model = "f_chk_ngaygiao"
                      class = "check"
                      value = '1'
                      unchecked-value = '0'
                    >
                      <span class="name">Ngày giao</span>
                    </b-form-checkbox>
                  </div>
                </div>
                <div class="value">
                    <div class="input-icon-right">
                        <flat-pickr
                          id="id-ngaygiao-tungay"
                          v-model="f_ngaygiao_tungay"
                          class="form-control"
                          :config="config.dateConfig"
                          :disabled="f_ngaygiao_disabled"
                        />
                        <span class="icon one-calendar"></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-2 col-12">
            <div class="info-row">
                <div class="key w80">
                    Đến ngày
                </div>
                <div class="value">
                    <div class="input-icon-right">
                        <flat-pickr
                          v-model="f_ngaygiao_denngay"
                          class="form-control"
                          :config="config.dateConfig"
                          :disabled="f_ngaygiao_disabled"
                        />
                        <span class="icon one-calendar"></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w90">
                  <div class="">
                    <b-form-checkbox
                      id="p-chk-lantao"
                      v-model="f_chk_lantao"
                      class="check"
                      value="1"
                      unchecked-value="0"
                    >
                      <span class="name">Lần tạo</span>
                    </b-form-checkbox>
                  </div>
                </div>
                <div class="value">
                    <div class="select-custom">
                        <b-form-select
                          id="id-lantao-xuly-no"
                          v-model="f_lantao_xyly_no"
                          :options="p_lantao_xyly_no_options"
                          value-field="LANTAO_ID"
                          text-field="LANTAO_ID"
                          :disabled="f_lantao_disabled"
                          class="form-control">
                        </b-form-select>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w80">
                    <div class="">
                      <b-form-checkbox
                        id="p-chk-loai-nhanvien"
                        v-model="f_chk_loai_nhanvien"
                        class="check"
                        value="1"
                        unchecked-value="0"
                      >
                        <span class="name">Loại NV</span>
                      </b-form-checkbox>
                    </div>
                </div>
                <div class="value">
                    <div class="select-custom">
                      <div class="select-custom">
                        <b-form-select
                          id = "id_loai_nhanvien"
                          v-model = "f_loai_nhanvien"
                          :options = "p_loai_nhanvien_options"
                          value-field = "ID"
                          text-field = "TEN"
                          class="form-control"
                          :disabled="f_loainhanvien_disabled"
                          @input="onLoaiNhanvienChange"
                          >
                        </b-form-select>
                      </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w90">
                    <div class="">
                      <b-form-checkbox
                        v-model="f_chk_nhanvien"
                        class="check"
                        value="1"
                        unchecked-value="0"
                        :disabled="f_loainhanvien_disabled"
                      >
                      <span class="name">Nhân viên</span>
                      </b-form-checkbox>
                    </div>
                </div>
                <div class="value">
                    <div class="select-custom">
                      <app-select2
                        :options = "p_nhanvien_options"
                        v-model = "f_nhanvien"
                        :disabled="f_nhanvien_disabled"></app-select2>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w90">
                    <div class="">
                      <b-form-checkbox
                        id="p-chk-luotgiao"
                        v-model="f_chk_luotgiao"
                        class="check"
                        value="1"
                        unchecked-value="0"
                      >
                        <span class="name">Lượt giao</span>
                      </b-form-checkbox>
                    </div>
                </div>
                <div class="value">
                    <div class="select-custom">
                      <b-form-select
                        id = "id_luotgiao"
                        v-model = "f_luotgiao"
                        :options = "p_luotgiao_options"
                        value-field = "ID"
                        text-field = "TEN"
                        class="form-control"
                        :disabled="f_luotgiao_disabled">
                      </b-form-select>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<script>
  import KyCuoc from '@/components/KyCuoc'
  import select2 from '@/components/Select2.vue'
  import DanhMucAPI from '../../api/DanhMucAPI'

  export default {
    components: { appSelect2 : select2, appKyCuoc : KyCuoc },
    props: {
      config: {
        type: Object,
        required: true
      }
    },
    created() {
      if(this.f_buoc_xuly_no !== undefined) {
        this.loadBuocXuLyNo(this.f_buoc_xuly_no);
      }
      if(this.f_loai_nhanvien !== undefined) {
        this.loadNhanVienTheoLoai(this.f_loai_nhanvien);
      }
    },
    data () {
      return {
        p_buocxyly_no_options: [],
        p_lantao_xyly_no_options: [],
        p_loai_nhanvien_options: [
          {"ID" : "39", "TEN" : "Nhân viên thu nợ"},
          {"ID" : "34", "TEN" : "Nhân viên pháp lý"},
          {"ID" : "25", "TEN" : "Nhân viên thu cước"}
        ],
        p_nhanvien_options:  [],
        p_luotgiao_options: [
          {"ID" : "1", "TEN" : "Lượt 1"},
          {"ID" : "2", "TEN" : "Lượt 2"},
          {"ID" : "3", "TEN" : "Lượt 3"}
        ]
      }
    },
    mounted() {
      DanhMucAPI.getDMBuocXuLyNo(this.axios).then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data.body !== undefined
              && response.data.data.body.length > 0) {
              this.p_buocxyly_no_options = response.data.data.body
            } else {
              this.p_buocxyly_no_options = []
            }
        }
      )
    },
    computed: {
      f_buoc_xuly_no: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_buocxyly_no
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setBuocXuLyNo', value)
        }
      },
      f_chk_ngaygiao: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_chk_ngaygiao
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setCheckNgayGiao', value)
        }
      },
      f_ngaygiao_tungay: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_ngaygiao_tungay
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setNgayGiaoTuNgay', value)
        }
      },
      f_ngaygiao_denngay: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_ngaygiao_denngay
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setNgayGiaoDenNgay', value)
        }
      },
      f_ngaygiao_disabled () {
        return this.f_chk_ngaygiao !== '1'
      },
      f_chk_lantao: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_chk_lantao
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setCheckLanTao', value)
        }
      },
      f_lantao_disabled () {
        return this.f_chk_lantao !== '1'
      },
      f_chk_loai_nhanvien: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_chk_loainhanvien
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setCheckLoaiNhanVien', value)
        }
      },
      f_loainhanvien_disabled () {
        return this.f_chk_loai_nhanvien !== '1'
      },
      f_loai_nhanvien: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_loai_nhanvien
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setLoaiNhanVien', value)
        }
      },
      f_chk_nhanvien: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_chk_nhanvien
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setCheckNhanVien', value)
        }
      },
      f_nhanvien: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_nhanvien
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setNhanVien', value)
        }
      },
      f_lantao_xyly_no: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_lantao_xyly_no
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setLanTaoXuLyNo', value)
        }
      },
      f_nhanvien_disabled () {
        return this.f_chk_loai_nhanvien !== '1' || this.f_chk_nhanvien !== '1'
      },
      f_chk_luotgiao: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_chk_luotgiao
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setCheckLuotGiao', value)
        }
      },
      f_luotgiao: {
        get() {
          return this.$store.state.qltn.storeTraCuuPhieuDaGiao.formData.p_luotgiao
        },
        set(value) {
          this.$store.commit('storeTraCuuPhieuDaGiao/setLuotGiao', value)
        }
      },
      f_luotgiao_disabled () {
        return this.f_chk_luotgiao !== '1'
      }
    },
    methods: {
      loadBuocXuLyNo: function(buoc_xu_ly_no) {
        DanhMucAPI.getLanTaoBuocXuLyNo(this.axios, buoc_xu_ly_no)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data.body !== undefined
                && response.data.data.body.data.length > 0) {
                this.p_lantao_xyly_no_options = response.data.data.body.data
              } else {
                this.p_lantao_xyly_no_options = []
              }
          }
        )
        .catch(function (error){
          // this.$toast.error(error)
        })
      },
      loadNhanVienTheoLoai: function(loai_nhanvien) {
        DanhMucAPI.getNhanVienTheoLoai(this.axios, loai_nhanvien)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000') {
              let items = []
              if (response.data.data.body !== undefined) {
                this.p_nhanvien_options = []
                response.data.data.body.forEach( function (item) {
                  items.push({ id : item.NHANVIEN_ID, text : item.TEN_HIENTHI })
                })
                this.p_nhanvien_options = items
              }
            }
          }
        )
        .catch(function() {

        })
        .finally(function() {

        })
      },
      onBuocXuLyNoChange: function() {
        this.loadBuocXuLyNo(this.f_buoc_xuly_no);
      },
      onLoaiNhanvienChange: function() {
        this.loadNhanVienTheoLoai(this.f_loai_nhanvien)
      }

    }
  }
</script>
