<template>
  <ejs-dialog :header="'Thông tin loại nhà khách hàng'" showCloseIcon="true" :target="'.main-wrapper'" header-class="popup-header" :visible="false" ref="popupLoaiNhaKhachHang" :position="{ X: 'center', Y: 'top' }" width="60%" id="popupLoaiNhaKhachHang">
    <div class="popup-content">
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100 nowrap">Nhóm loại nhà</div>
                <div class="value">
                  <div class="select-custom">
                    <Select2Ext ref="cboNhomLoaiNha" disable :allowFiltering="true"></Select2Ext>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100 nowrap">Số tầng</div>
                <div class="value">
                  <input ref="txtSoTang" type="number" class="form-control" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100 nowrap">Diện tích</div>
                <div class="value">
                  <input ref="txtDientich" type="number" class="form-control" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100 nowrap">Số phòng</div>
                <div class="value">
                  <input ref="txtSoPhong" type="number" class="form-control" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100 nowrap">Tên loại nhà</div>
                <div class="value">
                  <div class="select-custom">
                    <Select2Ext ref="cboTenLoaiNha" disable :allowFiltering="true"></Select2Ext>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Select2Ext from '@/modules/contract/setup/DeclareLandline/components/Select2Ext'
export default {
  components: {
    Select2Ext
  },
  data() {
    return {
      thuebao_id: 0,
      hdtb_id: 0,
      type: 0,
      validate: false,
      NVKT_BATBUOC_NHAP_LOAINHA: false
    }
  },
  methods: {
    showDialog() {
      this.init()
      this.$refs.popupLoaiNhaKhachHang.show()
    },

    async init() {
      try {
        let data = await this.axios.get('web-hopdong/lapdatmoi/sp_nhom_loainha_lay_ds')
        this.$refs.cboNhomLoaiNha.dataSource = data.data.data
        this.$refs.cboNhomLoaiNha.dataTextField = 'nhom_ln'
        this.$refs.cboNhomLoaiNha.dataValueField = 'nhomln_id'
        // if (data.data && data.data.data && data.data.data.length > 0) {
        //   this.$refs.cboNhomLoaiNha.value = data.data.data[0].nhomln_id
        // }

        data = await this.axios.get('web-thicong/capnhatthongtin/sp_loainha_lay_ds')
        this.$refs.cboTenLoaiNha.dataSource = data.data.data
        this.$refs.cboTenLoaiNha.dataTextField = 'ten_loainha'
        this.$refs.cboTenLoaiNha.dataValueField = 'loainha_id'
        // if (data.data && data.data.data && data.data.data.length > 0) {
        //   this.$refs.cboTenLoaiNha.value = data.data.data[0].loainha_id
        // }

        if (this.thuebao_id == 0) {
          console.log('test')
          data = await this.axios.get('web-hopdong/hopdong/sp_lay_diachi_thuebao_loainha', {
            params: {
              vdb_id: this.hdtb_id,
              vtype: this.type
            }
          })
        } else {
          data = await this.axios.get('web-hopdong/hopdong/sp_lay_diachi_thuebao_loainha', {
            params: {
              vdb_id: this.thuebao_id,
              vtype: this.type
            }
          })
        }
        
        if (data.data.data.length > 0) {
          this.$refs.txtSoTang.value = data.data.data[0].sotang
          this.$refs.txtDientich.value = data.data.data[0].dientich
          this.$refs.txtSoPhong.value = data.data.data[0].sophong
          this.$refs.cboNhomLoaiNha.value = data.data.data[0].nhomln_id
          this.$refs.cboTenLoaiNha.value = data.data.data[0].loainha_id
        } else {
          this.$refs.txtSoTang.value = ''
          this.$refs.txtDientich.value = ''
          this.$refs.txtSoPhong.value = ''
        }
      } catch (error) {
        console.log(error)
      }
    }
  }
}
</script>
<style scoped>
.error {
  border-color: red !important;
}
</style>
