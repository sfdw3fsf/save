<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="btnXemTbi.visible" :class="{ disabledInput: !btnXemTbi.enabled }" @click="btnXemTbi_ItemClick">
          <a> <span class="icon one-reload1"></span>Xem thiết bị</a>
        </li>
        <!-- <li @click="btnFilter_Click">
            <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Lấy Serial </a>
          </li> -->
      </ul>
    </div>
    <div class="popup-body">
      <DataGrid ref="grcDSThueBao" v-bind:columns="grcDSThueBao.header" v-bind:dataSource="grcDSThueBao.list" :showFilter="true" :showColumnCheckbox="false" :enabledSelectFirstRow="false" :allowPaging="true" :enablePagingServer="false" @rowSelected="grcDSThueBao_rowSelected" />
    </div>
    <ejs-dialog
      ref="ejsDialog"
      :visible="false"
      :isModal="true"
      target="#frmGiaoViecVIP"
      :enableResize="ejsDialog.config.enableResize"
      :resizeHandles="['All']"
      :header="ejsDialog.header"
      showCloseIcon="true"
      :width="ejsDialog.config.width"
      :height="ejsDialog.config.height"
      :minHeight="ejsDialog.config.minHeight"
      :allowDragging="ejsDialog.config.allowDragging"
      :position="ejsDialog.config.position"
      :animationSettings="ejsDialog.config.animationSettings"
      @beforeOpen="ejsDialogBeforeOpen"
      @beforeClose="ejsDialogBeforeClose"
    >
      <frmDanhSachThietBI_IDC v-if="ejsDialog.name == 'frmDanhSachThietBI_IDC'" ref="frmDanhSachThietBI_IDC" v-bind="frmDanhSachThietBI_IDC.input" />
    </ejs-dialog>
  </div>
</template>
<script>
import api from './api'

export default {
  components: {
    frmDanhSachThietBI_IDC: () => import('../frmDanhSachThietBI_IDC')
  },
  props: {
    hdtb_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    tinh_id: {
      type: [String, Number],
      defaul: null,
      require: false
    }
    // thuebao_id: {
    //   type: [String, Number],
    //   defaul: null,
    //   require: false
    // },
  },
  data() {
    return {
      localLoading: 0,
      btnXemTbi: { visible: true, enabled: true },
      grcDSThueBao: {
        header: [
          { fieldName: 'ma_tb', headerText: 'Mã TB (ĐHSX)', allowFiltering: true, allowSorting: true, freeze: 'Left' },
          { fieldName: 'ten_tb', headerText: 'Tên TB', width: 250, allowFiltering: true, allowSorting: true, textAlign: 'Right' },
          { fieldName: 'ma_phu', headerText: 'ID Khách hàng (IDC)', width: 650, allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_chuan', headerText: 'ID U/RACK (IDC)', width: 650, allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: []
      },
      ejsDialog: {
        header: 'Tiêu đề',
        name: null,
        visible: false,
        config: {
          width: '95%',
          height: 'auto',
          minHeight: '150',
          enableResize: true,
          allowDragging: true,
          position: { X: 'center', Y: 'center' },
          // position: { X: 420, Y: 14 },
          animationSettings: { effect: 'Fade' }
        }
      },
      frmDanhSachThietBI_IDC: { visible: false, input: {} }
    }
  },
  computed: {},
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    }
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        console.log('result')
        console.log(result.data.data)
        return result.data.data ? result.data.data : null
      } catch (e) {
        console.log('LOI ROI')
        console.log(e)
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    ejsDialogBeforeOpen() {
      let width = null
      if (this.ejsDialog.name == 'frmNhapText') {
        width = 'auto'
      } else {
        width = '95%'
      }
      if (this.ejsDialog.name == 'frmTraCuuHopDong') {
        this.ejsDialog.config.height = 800
      } else {
        this.ejsDialog.config.height = 'auto'
      }

      this.ejsDialog.config = {
        ...this.ejsDialog.config,
        width: width,
        // position: { X: 100, Y: 100 }
        position: { X: 'center', Y: 'center' }
      }
      // this.ejsDialog.config.height = 'auto'
      // this.$refs.ejsDialog.refreshPosition()
      // this.ejsDialog.config.enableResize = false
      // this.ejsDialog.config.allowDragging = false
      // this.ejsDialog.config.position = { X: 'center', Y: 'center' }
      // this.ejsDialog.config.position = { X: 420, Y: 14 }
      // }, 3000)
    },
    ejsDialogBeforeClose() {
      this.ejsDialog.name = null
      // this.$refs.ejsDialog.refreshPosition()
      this.ejsDialog.config.width = '0'
      this.ejsDialog.config.height = '0'
      this.ejsDialog.config.position = { X: 'center', Y: 'center' }
    },
    async frmDanhSachThueBaoCaiTaoHaTang_IDC_Load() {
      let dt = await this.callApiWrapper(api.lay_ds_thuebao_ctht_idc, {
        vphanvung_id: this.tinh_id,
        vhdtb_id: this.hdtb_id
      })
      // DataTable dt = GET_DATA_PROC("{?DB2}.LAY_DS_THUEBAO_CTHT_IDC", "vhdtb_id", hdtb_id);
      this.grcDSThueBao.list = dt
    },
    btnXemTbi_ItemClick() {
      if (this.grcDSThueBao.value == null) {
        this.$toast.warning('Vui lòng chọn thuê bao!')
        return
      }
      // long thuebao_id = Convert.ToInt64(gdvDSThueBao.GetFocusedRowCellValue("THUEBAO_ID").ToString());
      // frmDanhSachThietBI_IDC f = new frmDanhSachThietBI_IDC();
      // f.thuebao_id = thuebao_id;
      // f.tinh_id = this.tinh_id;
      // f.hdtb_id = 0;
      // f.ShowDialog();

      this.frmDanhSachThietBI_IDC.input = {
        thuebao_id: this.grcDSThueBao.value.thuebao_id,
        tinh_id: this.tinh_id,
        hdtb_id: 0
      }
      this.ejsDialog.name = 'frmDanhSachThietBI_IDC'
      this.ejsDialog.header = 'Danh sách thiết bị đã đăng ký'
      this.$refs.ejsDialog.show()
    },
    async grcDSThueBao_rowSelected(e) {
      console.log('grcDSThueBao_rowSelected')
      console.log(e)
      if (e.data) {
        this.grcDSThueBao.value = e.data
      }
    }
  },
  async mounted() {
    await this.frmDanhSachThueBaoCaiTaoHaTang_IDC_Load()
  }
}
</script>
<style scoped></style>
