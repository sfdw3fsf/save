<template>
  <div>
    <div class="list-actions-top d-flex justify-content-start align-items-center">
      <ul class="list">
        <li @click="tsbtnSave_Click">
          <a> <span class="icon  one-save"></span> Cập nhật </a>
        </li>
      </ul>
    </div>
    <div>
      <div class="box-form">
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w70">
                Số phiếu
              </div>
              <div class="value">
                <div class="input-icon-left">
                  <input type="text" class="form-control highlight" v-model="txtSoPhieu.value" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <DataGrid
          ref="grvVattu"
          :columns="grvVattu.header"
          :enabledSelectFirstRow="true"
          :dataSource="grvVattu.list"
          :showColumnCheckbox="false"
          :showFilter="true"
          :editSettings="{ allowEditing: true, mode: 'Batch' }"
          :allowPaging="true"
          :enablePagingServer="false"
          @selectedRowChanged="grcVattuSelectedRowChanged"
          @selectedItemsChanged="grcVattuSelectedItemsChanged"
        />
      </div>

      <div class="box-form">
        <div class="box-move-select-table">
          <div class="box-col box-form">
            <div class="legend-title">Danh sách đã gán</div>
            <!-- //gridViewVatTuKhongTinhTrang -->
            <DataGrid
              :columns="grvDaGan.header"
              :dataSource="grvDaGan.list"
              :showFilter="true"
              :showColumnCheckbox="true"
              :enabledSelectFirstRow="false"
              :allowPaging="true"
              :enablePagingServer="false"
              @selectedRowChanged="grvDaGanSelectedRowChanged"
              @selectedItemsChanged="grvDaGanSelectedItemsChanged"
            />
          </div>
          <div class="actions">
            <button class="btn" @click="btnRight_Click">
              <span class="fa fa-angle-right"></span>
            </button>
            <button class="btn" @click="btnLeft_Click">
              <span class="fa fa-angle-left"></span>
            </button>
          </div>
          <div class="box-col box-form">
            <div class="legend-title">Danh sách chưa gán</div>
            <DataGrid
              :columns="grvChuaGan.header"
              :dataSource="grvChuaGan.list"
              :showFilter="true"
              :showColumnCheckbox="true"
              :enabledSelectFirstRow="false"
              :allowPaging="true"
              :enablePagingServer="false"
              @selectedRowChanged="grvChuaGanSelectedRowChanged"
              @selectedItemsChanged="grvChuaGanSelectedItemsChanged"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import moment from 'moment'
import Vue from 'vue'
import breadcrumb from '@/components/breadcrumb'
import select2 from '@/components/Select2.vue'
import api from './api'

export default {
  components: { breadcrumb, appSelect2: select2 },
  props: {
    sophieu: {
      type: [String, Number],
      default: 0,
      required: true
    },
    vchungtu_id: {
      type: [String, Number],
      default: 0,
      required: true
    },
    vma_md: {
      type: [String, Number],
      default: 0,
      required: true
    },
    dt_vt: {
      type: [Array],
      default: () => {
        return []
      },
      required: true
    }
  },
  emits: ['onBtnXacNhanClicked'],
  async mounted() {
    console.log('frmGanSeri_v2 mounted')
    this.sophieu
    await this.frmGanSeri_v2_Load()
  },
  provide: {},
  computed: {},
  data() {
    return {
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      sources: [],
      soPhieu: '',
      chungTuId: null,
      maMD: '',
      xacnhan: false,
      colTinhTrang: false,
      txtSoPhieu: { value: '' },
      grvVattu: {
        header: [
          {
            fieldName: 'LOHANG',
            headerText: 'Lô Hàng',
            allowFiltering: true,
            allowSorting: true
          },
          {
            fieldName: 'TEN_VT',
            headerText: 'Tên vật tư',
            allowFiltering: true,
            allowSorting: true
          },
          {
            fieldName: 'DVI_TINH',
            headerText: 'ĐVT',
            allowFiltering: true,
            allowSorting: true
          },
          {
            fieldName: 'SOLUONG',
            headerText: 'Số lượng',
            allowFiltering: true,
            allowSorting: true
          },
          {
            fieldName: 'DONGIA',
            headerText: 'Đơn giá',
            allowFiltering: true,
            allowSorting: true
          },
          {
            fieldName: 'TIEN',
            headerText: 'Thành tiền',
            allowFiltering: true,
            allowSorting: true
          },
          {
            fieldName: 'LOAITB_ID',
            headerText: 'Loại thành tiền',
            allowFiltering: true,
            allowSorting: true
          }
        ],
        list: [],
        selected: [],
        value: null
      },
      grvDaGan: {
        header: [
          { width: '80', fieldName: 'STT', headerText: 'STT', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'SERIAL', headerText: 'Serial', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true },
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
          { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true },
          { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true },
          { fieldName: 'SL_TON', headerText: 'Số lượng', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      grvChuaGan: {
        header: [
          { width: '80', fieldName: 'STT', headerText: 'STT', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'SERIAL', headerText: 'Serial', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true },
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
          { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true },
          { fieldName: 'SL_TON', headerText: 'Số lượng', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      selectedDaGan: [],
      selectedChuaGan: [],
      gridViewVatTuKhongTinhTrang: [
        {
          fieldName: 'LOHANG',
          headerText: 'Lô Hàng',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'TEN_VT',
          headerText: 'Tên vật tư',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'DVI_TINH',
          headerText: 'ĐVT',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'SOLUONG',
          headerText: 'Số lượng',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'DONGIA',
          headerText: 'Đơn giá',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'TIEN',
          headerText: 'Thành tiền',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'LOAITB_ID',
          headerText: 'Loại thành tiền',
          allowFiltering: true,
          allowSorting: true
        }
      ],
      gridViewVatTuCoTinhTrang: [
        {
          fieldName: 'LOHANG',
          headerText: 'Lô Hàng',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'TEN_VT',
          headerText: 'Tên vật tư',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'DVI_TINH',
          headerText: 'ĐVT',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'SOLUONG',
          headerText: 'Số lượng',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'DONGIA',
          headerText: 'Đơn giá',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'TIEN',
          headerText: 'Thành tiền',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'LOAITB_ID',
          headerText: 'Loại thành tiền',
          allowFiltering: true,
          allowSorting: true
        },
        {
          fieldName: 'TRANGTHAI_ID',
          headerText: 'Trạng thái',
          allowFiltering: true,
          allowSorting: true
        }
      ]
    }
  },
  methods: {
    /**
     * @see: private void frmGanSeri_v2_Load(object sender, EventArgs e)
     */
    async frmGanSeri_v2_Load() {
      // grvChuaGan.setAllGridView_SpaceToggleCheck(true, true, true);
      // grvDaGan.setAllGridView_SpaceToggleCheck(true, true, true);

      // let t = tdan.GET_DATA_SQL(@"select tinhtrangtbi_id, tinhtrang_tbi from {?DB2}.tinhtrang_tbi");
      // load_cboRePo(cboTinhTrang, t, "TINHTRANG_TBI", "TINHTRANGTBI_ID");

      let check = this.dt_vt.filter((e) => e.LOAITBI_ID == 2)
      if (check.length <= 0) return
      this.grvVattu.list = check.map((e) => e)
      this.txtSoPhieu.value = this.sophieu

      if (this.vma_md == 'N.TTCL') this.colTinhTrang.visible = true
    },
    // async fetchData() {
    //   this.loading(true)
    //   if (this.maMD == 'N.TTCL') {
    //     this.grvVattu.listColumn = this.gridViewVatTuCoTinhTrang
    //   } else {
    //     this.grvVattu.listColumn = this.gridViewVatTuKhongTinhTrang
    //   }

    //   this.txtSoPhieu.value = this.soPhieu
    //   let check = this.sources.filter((element) => element.LOAITBI_ID == 2)
    //   if (check.length == 0) {
    //     return
    //   }
    //   this.grvVattu.list = check
    //   this.loading(false)
    // },

    // async gridVatTuSelectedRowChanged(data) {
    //   this.grvChuaGan.list = await frmGanSeri_V2API.getSeriDieuChinh(this.axios, {
    //     chungTuId: this.chungTuId,
    //     kieu: 0,
    //     ctctId: data[CTCT_ID]
    //   })

    //   this.grvDaGan.list = await frmGanSeri_V2API.getSeriDieuChinh(this.axios, {
    //     chungTuId: this.chungTuId,
    //     kieu: 1,
    //     ctctId: data[CTCT_ID]
    //   })
    // },
    // async moveRight() {
    //   try {
    //     let kt = await NhapXuatVTAPI.capNhatSerrial(this.axios, {
    //       ds: this.selectedDaGan,
    //       kieu: 2
    //     })
    //     this.$toast.success('Thành công!')
    //   } catch (error) {
    //     this.$toast.error(error.data.message_detail)
    //   }
    //   this.grvChuaGan.list.push(this.selectedDaGan)
    // },
    // async moveLeft() {
    //   try {
    //     let kt = await NhapXuatVTAPI.capNhatSerrial(this.axios, {
    //       ds: this.selectedChuaGan,
    //       kieu: 1
    //     })
    //     this.$toast.success('Thành công!')
    //   } catch (error) {
    //     this.$toast.error(error.data.message_detail)
    //   }
    //   this.grvDaGan.list.push(this.selectedChuaGan)
    // },
    /**
     * @see: private void btnLeft_Click(object sender, EventArgs e)
     */
    async btnLeft_Click() {
      // try {
      console.log('btnLeft_Click')
      let dt = this.grvChuaGan.list
      if (dt.length > 0) {
        let temp = this.grvChuaGan.selected
        if (temp.length) {
          let soluong = this.grvVattu.value.SOLUONG
          if (soluong != temp.length + this.grvDaGan.list.length) {
            this.$toast.warning(`Serial được chọn [${temp.length}] đã vượt quá số lượng của lô hàng - ${this.grvVattu.value.LOHANG} - [${soluong}]`)
            return
          }
          console.log(this.grvChuaGan.selected)
          let temp2 = temp.map((e) => e)
          let nguoiCn = await this.$root.token.getUserName()
          let mayCn = await this.$root.token.getMachine()
          let ipCn = await this.$root.token.getIP()
          for (let i of temp2) {
            i.CTCT_ID = this.grvVattu.value.CTCT_ID
            i.DONGIA = this.grvVattu.value.DONGIA
            i.NGUOI_CN = nguoiCn
            i.MAY_CN = mayCn
            i.IP_CN = ipCn
          }
          let json = JSON.stringify(temp2)
          let action = (
            await api.fn_capnhat_seri_dieuchinh(this.axios, {
              p_data: json,
              p_kieu: 1
            })
          ).data.data
          if (action == '1') {
            await this.load_grid(this.grvVattu.value.CTCT_ID)
          } else this.$toast.warning(action)
        } else {
          this.$toast.warning('Bạn phải chọn danh sách bên phải trước!')
        }
      }
      // } catch (ex) {
      //   this.$toast.warning(`${ex}`)
      // }
    },
    /**
     * @see: private void btnRight_Click(object sender, EventArgs e)
     */
    async btnRight_Click() {
      // try {
      let dt = this.grvDaGan.list
      if (dt.length > 0) {
        let temp = this.grvDaGan.selected
        console.log('btnRight_Click')
        console.log(temp)
        console.log(this.grvDaGan.selected)
        if (temp.length) {
          let temp2 = temp.map((e) => e)
          let json = JSON.stringify(temp2)
          let action = (
            await api.fn_capnhat_seri_dieuchinh(this.axios, {
              p_data: json,
              p_kieu: 2
            })
          ).data.data
          if (action == '1') {
            await this.load_grid(this.grvVattu.value.CTCT_ID)
          } else this.$toast.warning(action)
        } else {
          this.$toast.warning('Bạn phải chọn danh sách bên trái trước!')
        }
      }
      // } catch (ex) {
      //   this.$toast.warning(`${ex}`)
      // }
    },
    /**
     * @see: private void load_grid(int ctct_id)
     */
    async load_grid(ctct_id) {
      let s1 = (
        await api.sp_lay_seri_dieuchinh(this.axios, {
          p_chungtu_id: this.vchungtu_id,
          p_kieu: 0,
          p_ctct_id: ctct_id
        })
      ).data.data
      this.grvChuaGan.list = s1
      let s2 = (
        await api.sp_lay_seri_dieuchinh(this.axios, {
          p_chungtu_id: this.vchungtu_id,
          p_kieu: 1,
          p_ctct_id: ctct_id
        })
      ).data.data
      this.grvDaGan.list = s2
    },
    async grvDaGanSelectedRowChanged(e) {
      console.log('grvDaGanSelectedRowChanged')
      console.log(e)
      this.grvDaGan.value = e
    },
    async grvDaGanSelectedItemsChanged(e) {
      console.log('grvDaGanSelectedItemsChanged')
      console.log(e)
      this.grvDaGan.selected = Array.isArray(e) ? e : [e]
    },
    async grvChuaGanSelectedRowChanged(e) {
      console.log('grvChuaGanSelectedRowChanged')
      console.log(e)
      this.grvChuaGan.value = e
    },
    async grvChuaGanSelectedItemsChanged(e) {
      console.log('grvChuaGanSelectedItemsChanged')
      console.log(e)
      this.grvChuaGan.selected = Array.isArray(e) ? e : [e]
    },
    async grcVattuSelectedRowChanged(e) {
      console.log('grcVattuSelectedRowChanged')
      console.log(e)
      this.grvVattu.value = e
      try {
        if (e) {
          let ctct_id = this.grvVattu.value.CTCT_ID
          await this.load_grid(ctct_id)
        } else {
          this.grvChuaGan.list = null
          this.grvDaGan.list = null
        }
      } catch (ex) {
        this.$toast.error(`${ex}`)
      }
    },
    async grcVattuSelectedItemsChanged(e) {
      console.log('grcVattuSelectedItemsChanged')
      console.log(e)
      this.grvVattu.selected = Array.isArray(e) ? e : [e]
    },
    async Open() {
      this.loading(true)
      await this.fetchData()
      this.$refs.popupGanSeriV2.show()
      this.loading(false)
    },
    /**
     * @see: private void tsbtnSave_Click(object sender, EventArgs e)
     */
    async tsbtnSave_Click() {
      try {
        this.xacnhan = true
        this.$emit('onBtnXacNhanClicked', { xacnhan: this.xacnhan })
      } catch (ex) {
        this.$toast.error(`${ex}`)
      }
    }
  },
  created: async function() {},
  watch: {},
  destroyed() {}
}
</script>
<style></style>
