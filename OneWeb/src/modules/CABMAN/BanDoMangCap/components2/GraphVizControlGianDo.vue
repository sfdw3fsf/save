<template>
  <div>
    <ModalChonTramThietBiExt
      ref="ModalChonTramThietBiExt2"
      modalID="ModalChonTramThietBiExt2"
    />
    <TraCuuTBaoKetCuoi id="popupTraCuuTBaoKetCuoiDauNoi" :doiTuong="doiTuongKetCuoi" />
    <ModalDauNoiThueBao
      ref="ModalDauNoiThueBao"
      @change="onChangeDauNoiThueBao"
      @hidden="onHideDauNoiThueBao"
    />
    <ModalDanDoCapTheoKetCuoi
      modalID="ModalDanDoCapTheoKetCuoiDauNoi"
      ref="ModalDanDoCapTheoKetCuoiDauNoi"
    />
    <ModalQLKetCuoiExt modalID="ModalQLKetCuoiExt2" ref="ModalQLKetCuoiExt2" />
    <ModalTracnghiemExt ref="ModalTracnghiemExt3" modalID="ModalTracnghiemExt3" />
    <ModalChonDSKetCuoiExt
      modalID="ModalChonDSKetCuoiExt2"
      ref="ModalChonDSKetCuoiExt2"
    />
    <!-- <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:void(0)" @click="loadData()">
            <span class="icon one-reload"></span>Làm mới
          </a>
        </li>
        <template v-if="!readonly">
          <li>
            <a href="javascript:void(0)" @click="btnThemKC()">
              <span class="icon nc-icon-glyph ui-1_circle-add"></span>
              {{ getbtnThemKC_Caption }}
            </a>
          </li>
          <li>
            <a href="javascript:void(0)" @click="NHAP_PORT_AO">
              <span class="icon one-portao"></span> Port ảo
            </a>
          </li>
          <li>
            <a href="javascript:void(0)" @click="loadData()">
              <span class="icon nc-icon-glyph business_hierarchy-54"></span> Tối ưu giản
              đồ
            </a>
          </li>
          <li>
            <a href="javascript:void(0)" @click="dauNoiNhanh()">
              <span class="icon one-daunoi2"></span> Đấu nối nhanh
            </a>
          </li>
        </template>
      </ul>
    </div> -->

    <!-- <ejs-splitter id="splitter12" ref="splitterObj12" width="100%" height="100%">
      <e-panes>
        <e-pane content="#content1" min="100px" size="70%"></e-pane>
        <e-pane content="#content2" min="100px" size="30%"></e-pane>
      </e-panes>
    </ejs-splitter> -->

    <div id="content1" style="padding: 10px">
      <div class="sitemap-chart-canvas relative" style="height: 100%">
        <div class="map-options">
          <a href="javascript:void(0)" class="btn btn-main" @click="zoomIn()">
            <span class="nc-icon-outline ui-1_zoom-in"></span>
          </a>
          <a href="javascript:void(0)" class="btn btn-main" @click="zoomOut()">
            <span class="nc-icon-outline ui-1_zoom-out"></span>
          </a>
          <a href="javascript:void(0)" class="btn btn-main" @click="zoomDefault()">
            <span class="one-search"></span>
          </a>
          <a href="javascript:void(0)" class="btn btn-main" @click="save()">
            <span class="one-print"></span>
          </a>
        </div>
        <script
          src="https://unpkg.com/@hpcc-js/wasm@1.20.1/dist/index.min.js"
          type="javascript/worker"
        ></script>
        <div
          class="graph-container"
          style="width: 100%; height: calc(100vh - 270px - 2 * 12px)"
        >
          <div class="graph-oneBSS" :id="graphID" />
          <vue-simple-context-menu
            elementId="menuNodeDauNoi"
            :options="getoptionsNodeMenu"
            ref="menuNodeDauNoi"
            @option-clicked="menuNode_Clicked"
          />
          <vue-simple-context-menu
            elementId="menuEdgeDauNoi"
            :options="getoptionsEdgeMenu"
            ref="menuEdgeDauNoi"
            @option-clicked="menuEdge_Clicked"
          />
          <vue-simple-context-menu
            elementId="menuClusterDauNoi"
            :options="getoptionsClusterMenu"
            ref="menuClusterDauNoi"
            @option-clicked="menuCluster_Clicked"
          />
          <el-dialog
            @closed="closeDialog"
            title="Thay đổi trạng thái port"
            width="20%"
            :visible.sync="dialogVisible"
          >
            <section>
              <div
                class="info-row"
                v-for="(item, index) in ['Bình thường', 'Hỏng', 'Dự phòng']"
              >
                <div class="item">
                  <div class="check-action">
                    <input type="radio" class="check" v-model="option" :value="index" />
                    <span class="name"> {{ item }}</span>
                  </div>
                </div>
              </div>
            </section>
            <span slot="footer" class="dialog-footer">
              <el-button size="small" type="primary" @click="dialogVisible = false"
                >Xác nhận</el-button
              >
            </span>
          </el-dialog>
        </div>
      </div>
    </div>
    <!-- <div id="content2" style="display: none; padding: 10px">
      <div class="legend-title">Danh sách kết cuối con</div>
      <DataGrid
        ref="grvDsKetCuoiCon"
        v-bind:columns="[
          {
            fieldName: 'KYHIEU',
            headerText: 'Ký hiệu',
            allowFiltering: true,
            width: 50,
            allowSorting: false,
          },
        ]"
        :commands="[
          {
            name: 'SuaKetCuoiCon',
            cssClass: 'btn btn-main -ap icon-more_horiz',
            width: 40,
            text: '',
            title: 'Sửa thông tin kết cuối con',
          },
          {
            name: 'XoaKetCuoiCon',
            cssClass: 'btn btn-main -ap icon-close',
            width: 40,
            text: '',
            title: 'Xóa kết cuối con',
          },
        ]"
        :commandColumn="{
          headerText: '',
          width: 50,
          cellCssClass: 'css1',
          firstColumn: false,
          show: true,
        }"
        v-bind:dataSource="KETCUOI"
        @commandClicked="commandClick"
        :enabledSelectFirstRow="true"
        :showFilter="true"
        @selectedRowChanged="grid_selectedRowChanged"
        :visibleHeader="false"
      >
      </DataGrid>
      <div class="legend-title mart20">Thông số kết cuối</div>
      <span v-html="TEN_KETCUOI"></span>

      <template v-if="!readonly">
        <a
          v-show="CHUYEN_SPLITTER !== null"
          href="javascript:void(0)"
          @click="chuyenSangKetCuoiKhac()"
        >
          *Chuyển SP/Ugain sang kết cuối khác
        </a>
        <br />
        <a
          v-show="CHUYEN_SPLITTER !== null"
          href="javascript:void(0)"
          @click="chuyenVaoTrongTongDai()"
        >
          *Chuyển SP/Ugain vào trong tổng đài
        </a>
        <button
          v-show="btnMapOLTPort.Visible"
          class="btn btn-main fullw"
          @click="btnMapOLTPort_Click"
        >
          Gán splitter vào port OLT
        </button>
      </template>
    </div> -->
  </div>
</template>

<script>
import DotGraph from './dot'
import { graphviz } from 'd3-graphviz'
import {
  select as d3_select,
  selectAll as d3_selectAll,
  pointer as d3_pointer
} from 'd3-selection'
import { transition as d3_transition } from 'd3-transition'
import { dispatch as d3_dispatch } from 'd3-dispatch'
import {
  zoomIdentity as d3_zoomIdentity,
  zoomTransform as d3_zoomTransform
} from 'd3-zoom'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import TraCuuTBaoKetCuoi from '../../tracuu-thuebao-theo-ketcuoi/TraCuuTBao'
import ModalDauNoiThueBao from '../../TraCuuThueBaoChuaNhapCap/Modal/DauNoiThueBao'
import ModalDanDoCapTheoKetCuoi from '../modal/DanDoCapTheoKetCuoi'
import ModalQLKetCuoiExt from '../modal/QLKetCuoiExt2'
import EventBus from '@/utils/eventBus'
import ModalTracnghiemExt from '../modal/TracNghiem'
import ModalChonDSKetCuoiExt from '../modal/ChonDSKetCuoiExt'
import ModalChonTramThietBiExt from '../modal/ChonTramThietBiExt'
const scale = 0.8
const px2pt = 3 / 4
export default {
  props: {
    graphID: String,
    defaultEdgeAttributes: {
      type: Object,
      default: null
    },
    transitionDuration: {
      type: Number,
      default: 1
    },
    fit: {
      type: Boolean,
      default: true
    },
    dotSrc: {
      type: String,
      default: `digraph G
      {
        a -> b;
      }`
    }
  },
  components: {
    'vue-simple-context-menu': VueSimpleContextMenu,
    TraCuuTBaoKetCuoi,
    ModalDauNoiThueBao,
    ModalDanDoCapTheoKetCuoi,
    ModalQLKetCuoiExt,
    ModalTracnghiemExt,
    ModalChonDSKetCuoiExt,
    ModalChonTramThietBiExt
  },
  computed: {
    getbtnThemKC_Caption () {
      return this.btnThemKC_Caption
    },

    getoptionsClusterMenu () {
      return [
        {
          name: '<span class="icon text-main f20 one-trash"></span> Xóa kết cuối',
          slug: 'cluster_xoa_ket_cuoi'
        }
      ]
    },
    getoptionsEdgeMenu () {
      if (this.selectedComponents === null) return []
      var menu = [
        {
          name: '<span class="icon text-main f20 one-trash"></span> Xóa kết nối',
          slug: 'edge_xoa_ket_noi'
        },
        {
          name: '<span class="icon text-main f20 one-daochieu"></span> Đảo chiều',
          slug: 'edge_dao_chieu'
        }
      ]
      var menu2 = []
      try {
        var vdaunoi_id = this.selectedComponents.node().id
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 72 ~ getoptionsEdgeMenu ~ vdaunoi_id',
          vdaunoi_id
        )
        if (vdaunoi_id.startsWith('dn')) {
          var id = vdaunoi_id.substring(3).split('_')
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 74 ~ getoptionsEdgeMenu ~ id',
            id
          )
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 77 ~ getoptionsEdgeMenu ~ this.KETCUOI',
            this.KETCUOI
          )
          var q1 = this.KETCUOI.filter(
            (e) => e.KETCUOI_ID.toString() === id[0].toString()
          )
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 78 ~ getoptionsEdgeMenu ~ q1',
            q1
          )
          var q2 = this.KETCUOI.filter(
            (e) => e.KETCUOI_ID.toString() === id[3].toString()
          )
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 80 ~ getoptionsEdgeMenu ~ q2',
            q2
          )
          var f1 =
            q2.length + q1.length > 0 &&
            (id[0] !== id[3] ||
              q1[0]['LOAIKC_ID'] !== q2[0]['LOAIKC_ID'] ||
              q1[0]['LOAIKC_ID'] !== 5)
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 82 ~ getoptionsEdgeMenu ~ f1',
            f1
          )
          var f2 =
            q1.length > 0 &&
            q2.length > 0 &&
            (id[0] !== id[3] ||
              q1[0]['LOAIKC_ID'] !== q2[0]['LOAIKC_ID'] ||
              q1[0]['LOAIKC_ID'] !== 5)
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 84 ~ getoptionsEdgeMenu ~ f2',
            f2
          )
          if (f1) {
            menu2.push(menu[0])
          }
          if (f2) {
            menu2.push(menu[1])
          }
        } else {
          if (vdaunoi_id.startsWith('dv') || vdaunoi_id.startsWith('dr')) {
            menu2.push(menu[0])
          }
        }
      } catch (error) {}
      return menu2
    },
    getoptionsNodeMenu () {
      if (this.selectedComponents === null) return []
      var menu = [
        {
          type: 'divider'
        },
        {
          name: '<span class="icon text-main f20 one-daucuoi"></span> Kết nối từ',
          slug: 'node_ket_noi_tu'
        },
        {
          name: '<span class="icon text-main f20 one-daucuoi"></span> Kết nối đến',
          slug: 'node_ket_noi_den'
        },
        {
          name: '<span class="icon text-main f20 one-chuyencap"></span> Đấu thuê bao',
          slug: 'node_dau_thue_bao'
        },
        {
          name:
            '<span class="icon text-main f20 one-taodiemuon"></span> Danh sách thuê bao',
          slug: 'node_ds_thue_bao'
        },
        {
          name: '<span class="icon text-main f20 -ap icon-info"></span> Tra dẫn độ',
          slug: 'node_tra_dan_do'
        },
        {
          name: '<span class="icon text-main f20 one-daochieu"></span> Trạng thái',
          slug: 'node_trang_thai'
        },
        {
          name: '<span class="icon text-main f20 one-daochieu"></span> Hủy tạo kết nối',
          slug: 'node_huy_tao_ket_noi'
        }
      ]
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 89 ~ getoptionsNodeMenu ~ this.startNode',
        this.startNode
      )
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 100 ~ getoptionsNodeMenu ~ this.endNode',
        this.endNode
      )
      var menu2 = []

      try {
        var id = this.selectedComponents.node() && this.selectedComponents.node().id
        if (id === null) return []
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 112 ~ getoptionsNodeMenu ~ id',
          id
        )
        if (id.startsWith('sv') || id.startsWith('sr')) {
          var dtDauNoiCap = id.startsWith('sv')
            ? this.DAUNOI_CAP_VAO
            : this.DAUNOI_CAP_RA
          var svRecord = dtDauNoiCap.filter(
            (e) => `${e.CAP_ID}_${e.MIDSPAN_ID}_${e.SOI}` === id.substring(3)
          )
          if (svRecord.length === 0) return []
          if (svRecord[0].THIETBI_D_ID !== null && id.startsWith('sv')) return []
          if (svRecord[0].THIETBI_N_ID !== null && id.startsWith('sr')) return []
          if (svRecord[0].READONLY && svRecord[0].READONLY === 1) return []
          if (this.startNode === null) {
            menu2.push(menu[1])
          } else {
            if (this.endNode === null) {
              menu2.push(menu[2])
              menu2.push(menu[0])
              menu2.push(menu[7])
            }
          }
        } else {
          if (this.startNode === null) {
            if (id.startsWith('vt')) {
              menu2.push(menu[1])
              var vvitri_id = id.substring(3).split('_')
              var q = this.KETCUOI.filter(
                (e) => e.KETCUOI_ID.toString() === vvitri_id[0].toString()
              )
              var f =
                q.length > 0 &&
                (q[0]['LOAIKC_ID'].toString() !== '5' || vvitri_id[2].toString() === '2')
              q = this.VITRI.filter(
                (e) =>
                  e.KETCUOI_ID.toString() === vvitri_id[0].toString() &&
                  e.VITRI.toString() === vvitri_id[1].toString() &&
                  e.MAT.toString() === vvitri_id[2].toString()
              )
              if (q.length > 0 && q[0]['DNC'].toString() !== '1') {
                menu2.push(menu[0])
                menu2.push(menu[3])
              }
              menu2.push(menu[0])
              menu2.push(menu[5])
              menu2.push(menu[4])

              if (f) {
                menu2.push(menu[0])
                menu2.push(menu[6])
              }
            }
          } else {
            if (this.endNode === null) {
              menu2.push(menu[2])
              menu2.push(menu[0])
              menu2.push(menu[7])
            }
          }
        }
      } catch (error) {
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 114 ~ getoptionsNodeMenu ~ error',
          error
        )
      }
      return menu2
    }
  },
  data () {
    return {
      readonly: false,
      CHUYEN_SPLITTER: null,
      OLT_PORT_ID: null,
      TEN_KETCUOI: null,
      btnMapOLTPort: {
        Visible: false,
        Tag: null
      },
      vitri_id: null,
      dialog: null,
      dialogVisible: false,
      doiTuongKetCuoi: {
        ketcuoi_id: '',
        vitri: -1,
        mat: 2
      },
      dispatch: null,
      point: {},
      DAUNOI_CAP_VAO: [],
      DAUNOI_CAP_RA: [],
      KETCUOI: [],
      VITRI: [],
      node: null,
      latestEdgeAttributes: {},
      edgeIndex: null,
      graph0: null,
      dataGraphviz: null,
      totalMemory: 16777216 * 2,
      svg: null,
      selectedComponents: null,
      div: null,
      graphviz: null,
      renderGraphReady: false,
      rendering: false,
      pendingUpdate: false,
      prevFit: false,
      selectRects: null,
      selectNames: null,
      prelDotGraph: null,
      dotGraph: null,
      state: {
        busy: false
      },
      isDrawingEdge: false,
      originalViewBox: {},
      startNode: null,
      endNode: null,
      btnThemKC_Caption: 'Thêm kết cuối con',
      option: 0
    }
  },
  mounted () {
    this.svg = d3_select(null)
    this.selectedComponents = d3_selectAll(null)
    this.div = d3_select(`#${this.graphID}`)
    this.selectRects = d3_select(null)
  },
  watch: {
    dataGraphviz (value) {
      this.createGraph(value)
    },
    'state.busy' (value) {
      this.$root.showLoading(value)
    }
  },
  methods: {
    viewOnly (v) {
      this.readonly = v
    },
    save () {
      var data = document.getElementById(`#${this.graphID}`).children[0].outerHTML
      var filename = `gian_do_${this.node.KETCUOI_ID.split('_')[1]}.svg`
      var type = 'image/svg+xml'
      var file = new Blob([data], { type: type })
      if (window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(file, filename)
      } else {
        var a = document.createElement('a')
        var url = URL.createObjectURL(file)
        a.href = url
        a.download = filename
        document.body.appendChild(a)
        a.click()
        setTimeout(function () {
          document.body.removeChild(a)
          window.URL.revokeObjectURL(url)
        }, 0)
      }
    },
    zoomDefault () {
      this.handleZoomOutMapButtonClick()
    },
    zoomIn () {
      let scale = d3_zoomTransform(this.graphviz.zoomSelection().node()).k
      scale = scale * 1.2
      this.setZoomScale(scale)
    },
    zoomOut () {
      let scale = d3_zoomTransform(this.graphviz.zoomSelection().node()).k
      scale = scale / 1.2
      this.setZoomScale(scale)
    },
    dauNoiNhanh () {
      this.$emit('dauNoiNhanh', this.node)
    },
    async chuyenSangKetCuoiKhac () {
      try {
        var qt = await this.$confirm(
          'Chức năng dùng để chuyển Splitter/Ugain hiện tại sang tủ cáp(kết cuối) khác. Mọi thông tin đấu nối của Splitter/Ugain sẽ bị xóa bỏ để đấu nối lại. Bạn có chắc muốn thực hiện ?',
          'Thông báo',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }
        )
        if (qt !== 'confirm') return
        var tracnghiem = {
          title: 'Thông tin thêm.',
          caption:
            'Bước tiếp theo cần làm là xác định kết cuối muốn chuyển Splitter/Ugain tới. Nhập vào ký hiệu hoặc tên kết cuối để tìm kiếm và chọn từ danh sách.',
          options: ['Đã rõ.', 'Thôi! để sau.'],
          data: null
        }
        var result = await this.$refs.ModalTracnghiemExt3.show(tracnghiem)
        console.log(
          '🚀 ~ file: GraphVizControl1.vue ~ line 397 ~ chuyenSangKetCuoiKhac ~ result',
          result
        )
        if (result.ok) {
          this.$refs.ModalChonDSKetCuoiExt2.showWithText(
            0,
            'Chọn 1 kết cuối đã có đấu cáp để chuyển Splitter/Ugain sang'
          ).then(async (result) => {
            if (result.ok) {
              console.log(
                'Chọn 1 kết cuối đã có đấu cáp để chuyển Splitter/Ugain sang',
                result
              )
              var vketcuoi_cha_id = result.selectedRow.KETCUOI_ID
              var KETCUOI_ID = this.node.KETCUOI_ID.split('_')[1]
              console.log(
                '🚀 ~ file: GraphVizControl1.vue ~ line 405 ~ this.$refs.ModalChonDSKetCuoiExt2.showWithText ~ this.node.KETCUOI_ID',
                this.node.KETCUOI_ID
              )
              console.log(
                '🚀 ~ file: GraphVizControl1.vue ~ line 405 ~ this.$refs.ModalChonDSKetCuoiExt2.showWithText ~ KETCUOI_ID',
                KETCUOI_ID
              )
              if (vketcuoi_cha_id.toString() === KETCUOI_ID.toString()) {
                this.$alert(
                  'Splitter/Ugain hiện đang nằm trong kết cuối đã chọn, không cần chuyển.',
                  'Thông báo',
                  {
                    confirmButtonText: 'Đóng',
                    type: 'warning'
                  }
                )
              } else {
                let vketcuoi_id = this.CHUYEN_SPLITTER
                vketcuoi_id &&
                  (await this.CHUYEN_KETCUOI_CHA(vketcuoi_id, vketcuoi_cha_id))
              }
            } else {
              console.log('Cancel')
            }
          })
        }
      } catch (error) {}
    },
    async CHUYEN_KETCUOI_CHA (vketcuoi_id, vketcuoi_cha_id) {
      try {
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/chuyen-ketcuoi-cha?ketcuoi_id=' +
            vketcuoi_id +
            '&ketcuoi_cha_id=' +
            vketcuoi_cha_id
        )
        console.log(
          '🚀 ~ file: GraphVizControl1.vue ~ line 437 ~ CHUYEN_KETCUOI_CHA ~ rs',
          rs
        )
        await this.$alert('Chuyển Splitter/Ugain thành công.', 'Thông báo', {
          confirmButtonText: 'Đóng',
          type: 'success'
        })
        await this.loadData()
      } catch (error) {
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
      }
    },
    async CHUYEN_SPLITTER_VAO_TRAMTBI (vketcuoi_id, vtramtbi_id, vtoql_id) {
      try {
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/chuyen-splitter?ketcuoi_id=' +
            vketcuoi_id +
            '&tramtbi_id=' +
            vtramtbi_id +
            '&toql_id=' +
            vtoql_id
        )
        console.log(
          '🚀 ~ file: GraphVizControl1.vue ~ line 500 ~ CHUYEN_SPLITTER_VAO_TRAMTBI ~ rs',
          rs
        )
        await this.$alert('Chuyển Splitter/Ugain thành công.', 'Thông báo', {
          confirmButtonText: 'Đóng',
          type: 'success'
        })
        await this.loadData()
      } catch (error) {
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
      }
    },
    async chuyenVaoTrongTongDai () {
      try {
        var qt = await this.$confirm(
          'Chức năng dùng để chuyển Splitter/Ugain hiện tại vào bên trong tổng đài. Mọi thông tin đấu nối của Splitter/Ugain sẽ bị xóa bỏ để đấu nối lại. Bạn có chắc muốn thực hiện ?',
          'Thông báo',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }
        )
        if (qt !== 'confirm') return false
        var tracnghiem = {
          title: 'Thông tin thêm.',
          caption:
            'Bước tiếp theo cần làm là xác định tổng đài muốn chuyển Splitter/Ugain tới.',
          options: ['Đã rõ.', 'Thôi! để sau.'],
          data: null
        }
        var result = await this.$refs.ModalTracnghiemExt3.showWithSetChecked(
          tracnghiem,
          0
        )
        if (result.ok) {
          var chon = await this.$refs.ModalChonTramThietBiExt2.show(null)
          console.log(
            '🚀 ~ file: GraphVizControl1.vue ~ line 504 ~ chuyenVaoTrongTongDai ~ chon',
            chon
          )
          if (chon.ok) {
            var vtramtbi_id = chon.selectedRow.DONVI_ID.toString()
            var rs = await this.$root.context.get(
              '/web-cabman/ban-do-mang-cap/danh-muc',
              { loai: 'DS_TOKT_QL', option: vtramtbi_id }
            )
            var vtoql_id = '0'
            if (rs.data.length === 0) {
              await this.$alert(
                'Trạm thiết bị chưa được phân tổ kỹ thuật quản lý. Không được phép chuyển Splitter/Ugain vào.',
                'Thông báo',
                {
                  confirmButtonText: 'Đóng',
                  type: 'error'
                }
              )
              return
            }
            if (rs.data.length === 1) {
              vtoql_id = rs.data[0].TOKT_ID.toString()
            } else {
              var dsTo = []
              rs.data.forEach((dr) => {
                var id = dr.TOKT_ID.toString()
                if (!dsTo.includes(id)) {
                  dsTo.push({ value: id, text: dr.TOKT })
                }
              })
              if (dsTo.length > 0) {
                try {
                  tracnghiem = {
                    title: 'Thông tin thêm.',
                    caption:
                      'Bước tiếp theo cần làm là xác định tổng đài muốn chuyển Splitter/Ugain tới.',
                    options: dsTo,
                    data: null
                  }
                  console.log(
                    '🚀 ~ file: GraphVizControl1.vue ~ line 559 ~ chuyenVaoTrongTongDai ~ tracnghiem',
                    tracnghiem
                  )
                  result = await this.$refs.ModalTracnghiemExt3.showWithSetChecked(
                    tracnghiem,
                    dsTo[0].value
                  )
                  console.log(
                    '🚀 ~ file: GraphVizControl1.vue ~ line 536 ~ chuyenVaoTrongTongDai ~ result',
                    result
                  )
                  vtoql_id = result.option.toString()
                } catch (error) {
                  console.log(
                    '🚀 ~ file: GraphVizControl1.vue ~ line 568 ~ chuyenVaoTrongTongDai ~ error',
                    error
                  )
                  vtoql_id = dsTo[0].id.toString()
                }
              }
            }
            let vketcuoi_id = this.CHUYEN_SPLITTER
            await this.CHUYEN_SPLITTER_VAO_TRAMTBI(vketcuoi_id, vtramtbi_id, vtoql_id)
          }
        }
      } catch (error) {
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
      }
    },
    async btnMapOLTPort_Click () {
      try {
        var data = {
          VPORTVL_ID: this.btnMapOLTPort.Tag,
          VNGUOI_CN: this.$root.token.getUserName()
        }
        console.log(
          '🚀 ~ file: GraphVizControl1.vue ~ line 386 ~ btnMapOLTPort_Click ~ data',
          data
        )
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sp_auto_gan_port_olt_splitter', data)
        console.log(
          '🚀 ~ file: GraphVizControl1.vue ~ line 390 ~ btnMapOLTPort_Click ~ rs',
          rs
        )
        if (rs.data.length > 0) {
          var tenCardGpon = rs.data[0].CARD_GP
          var slSP = rs.data[0].SL_SP
          this.$root.$toast.success(
            'Thành công\r\n' +
              'GPON: ' +
              tenCardGpon +
              '\r\nCấu hình xong: ' +
              slSP +
              ' splitter'
          )
          await this.loadData()
        }
      } catch (error) {
        console.log(
          '🚀 ~ file: GraphVizControl1.vue ~ line 392 ~ btnMapOLTPort_Click ~ error',
          error
        )
      }
    },
    async xoaKetcuoi (id) {
      try {
        var confirm = await this.$confirm(
          `Bạn có muốn xóa kết cuối đang chọn không ?`,
          'Giản đồ mạng cáp',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }
        )
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 377 ~ xoaKetcuoi ~ confirm',
          confirm
        )
        if (confirm === 'confirm') {
          try {
            var rs = await this.$root.context.post(
              `/web-cabman/ban-do-mang-cap/xoa-ketcuoi?ketcuoi_id=${id}&xoa_batbuoc=1`
            )
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 380 ~ xoaKetcuoi ~ rs',
              rs
            )
            if (rs.data.result) {
              await this.$alert('Xóa kết cuối thành công !', 'Thông báo', {
                confirmButtonText: 'Đóng'
              })
              await this.loadData()
            }
          } catch (error) {
            if (error.response && error.response.status === 400) {
              await this.$alert(error.response.data.message_detail, 'Thông báo', {
                confirmButtonText: 'Đóng',
                type: 'error'
              })
            }
          }
        }
      } catch (error) {}
    },
    async commandClick (name, args) {
      console.log('commandClick', name, args)
      if (this.readonly) return
      if (name === 'XoaKetCuoiCon') {
        if (
          args.KETCUOI_ID.toString() === this.node.KETCUOI_ID.split('_')[1].toString()
        ) {
          await this.$alert('Không được phép xóa kết cuối này', 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
          return
        }
        await this.xoaKetcuoi(args.KETCUOI_ID)
        return
      }
      if (name === 'SuaKetCuoiCon') {
        var dataQLKetCuoi = {}
        dataQLKetCuoi.cmd = 'SuaKetcuoi'
        dataQLKetCuoi.data = {
          ketcuoi_id: args.KETCUOI_ID,
          phanloaikc_id: args.PHANLOAIKC_ID
        }
        this.$refs.ModalQLKetCuoiExt2.show(dataQLKetCuoi).then((result) => {
          if (result.ok) {
            console.log('Sửa kết cuối', result)
            // EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
          } else {
          }
        })
      }
    },
    async grid_selectedRowChanged (dataItem) {
      console.log('🚀 ~ file: GraphVizControl1.vueA :933 ~ grid_selectedRowChanged ~ this.KETCUOI', this.KETCUOI)
      if (!this.renderGraphReady) return
      if (dataItem === null) return
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 399 ~ grid_selectedRowChanged ~ dataItem',
        dataItem
      )
      try {
        this.setState({busy: true})
        var kcid = dataItem.KETCUOI_ID
        var id = 'kc_' + kcid
        var q = this.svg.select(`#${id}`)
        if (q) {
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 408 ~ grid_selectedRowChanged ~ q',
            q
          )
          q.classed('selectedKC', true)
          var kcRows = this.KETCUOI.filter(
            (e) => e.KETCUOI_ID.toString() === kcid.toString()
          )
          console.log('🚀 ~ file: GraphVizControl1.vue:933 ~ grid_selectedRowChanged ~ kcRows', kcRows)

          var kcRows_khac = this.KETCUOI.filter(
            (e) => e.KETCUOI_ID.toString() !== kcid.toString()
          )
          kcRows_khac.forEach((element) => {
            let id = 'kc_' + element.KETCUOI_ID
            let q = this.svg.select(`#${id}`)
            if (q) {
              q.classed('selectedKC', false)
            }
          })
          if (kcRows.length === 0) {
            this.btnMapOLTPort.Visible = false
            this.btnMapOLTPort.Tag = null
          } else {
            this.btnMapOLTPort.Visible = parseInt(kcRows[0].OLT_PORT_ID) > 0
            if (this.btnMapOLTPort.Visible) {
              this.btnMapOLTPort.Tag = parseInt(kcRows[0].OLT_PORT_ID)
            }
          }
          try {
            var data = {
              P_KETCUOI_ID: kcid,
              VKIEU: 2
            }
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 428 ~ grid_selectedRowChanged ~ data',
              data
            )
            var rs = await this.$root.context.post(
              '/web-cabman/ban-do-mang-cap/lay_ten_ketcuoi',
              data
            )
            if (rs.data.length > 0) {
              var html = rs.data[0].TEN_KETCUOI
              if (
                kcid.toString() !== this.node.KETCUOI_ID.split('_')[1].toString() &&
                kcRows.length > 0 &&
                (kcRows[0].LOAIKC_ID.toString() === '5' ||
                  kcRows[0].LOAIKC_ID.toString() === '9')
              ) {
                this.CHUYEN_SPLITTER = kcid
              } else {
                this.CHUYEN_SPLITTER = null
              }
              this.TEN_KETCUOI = html
            }
            // this.$root.showLoading(false)
          } catch (error) {
            // this.$root.showLoading(false)
          }
        }
      } catch (error) {
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 413 ~ grid_selectedRowChanged ~ error',
          error
        )
      } finally {
        this.setState({busy: false})
      }
    },
    async closeDialog () {
      if (this.vitri_id !== null && this.vitri_id.length > 0) {
        try {
          var data = {
            P_KETCUOI_ID: this.vitri_id[0],
            P_VITRI: this.vitri_id[1],
            P_MAT: this.vitri_id[2],
            P_TRANGTHAI: this.option + 1
          }
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 333 ~ closeDialog ~ data',
            data
          )
          var rs = await this.$root.context.post(
            '/web-cabman/ban-do-mang-cap/capnhat_tt_vitri',
            data
          )
          console.log('🚀 ~ file: GraphVizControl.vue ~ line 337 ~ closeDialog ~ rs', rs)
          if (rs.data && rs.data.length > 0) {
            if (rs.data[0].KETQUA > 0) {
              if (this.option + 1 === 1) {
                await this.$alert(
                  'Chuyển trạng thái cổng sang hoạt động bình thường thành công',
                  'Thông báo',
                  {
                    confirmButtonText: 'Đóng'
                  }
                )
              }
              if (this.option + 1 === 2) {
                await this.$alert(
                  'Chuyển trạng thái cổng sang hỏng thành công',
                  'Thông báo',
                  {
                    confirmButtonText: 'Đóng'
                  }
                )
              }
              if (this.option + 1 === 3) {
                await this.$alert(
                  'Chuyển trạng thái cổng sang dự phòng thành công',
                  'Thông báo',
                  {
                    confirmButtonText: 'Đóng'
                  }
                )
              }
              await this.loadData()
            }
          }
        } catch (error) {
          if (error.response && error.response.status === 400) {
            await this.$alert(error.response.data.message_detail, 'Thông báo', {
              confirmButtonText: 'Đóng',
              type: 'error'
            })
          }
        }
      }
    },
    onChangeDauNoiThueBao () {},
    onHideDauNoiThueBao () {
      this.loadData()
    },
    async menuCluster_Clicked (event) {},
    async menuNode_Clicked (event) {
      if (event.option.slug === 'node_ket_noi_tu') {
        this.startNode = event.item
        return
      }
      if (event.option.slug === 'node_ket_noi_den') {
        this.endNode = event.item
        this.DAU_NOI()
        return
      }
      if (event.option.slug === 'node_huy_tao_ket_noi') {
        this.startNode = null
        this.endNode = null
        return
      }

      if (event.option.slug === 'node_ds_thue_bao') {
        let vvitri_id = event.item.node().id
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 310 ~ menuNode_Clicked ~ vvitri_id',
          vvitri_id
        )
        if (vvitri_id.startsWith('vt')) {
          let id = vvitri_id.substring(3).split('_')
          this.doiTuongKetCuoi.ketcuoi_id = parseInt(id[0])
          this.doiTuongKetCuoi.vitri = parseInt(id[1])
          this.doiTuongKetCuoi.mat = parseInt(id[2])
          console.log('🚀 ~ file: GraphVizControl1.vue ~ line 1084 ~ menuNode_Clicked ~ this.doiTuongKetCuoi', this.doiTuongKetCuoi)
          this.$bvModal.show('popupTraCuuTBaoKetCuoiDauNoi')
        }
        return
      }
      if (event.option.slug === 'node_dau_thue_bao') {
        let vvitri_id = event.item.node().id
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 310 ~ menuNode_Clicked ~ vvitri_id',
          vvitri_id
        )
        if (vvitri_id.startsWith('vt')) {
          let id = vvitri_id.substring(3).split('_')
          let thongTinDauNoi = {
            ketCuoiId: parseInt(id[0]),
            viTri: parseInt(id[1]),
            mat: parseInt(id[2])
          }
          let modal = this.$refs['ModalDauNoiThueBao']
          modal.setData(thongTinDauNoi)
          modal.show()
        }
        return
      }

      if (event.option.slug === 'node_tra_dan_do') {
        let vvitri_id = event.item.node().id
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 310 ~ menuNode_Clicked ~ vvitri_id',
          vvitri_id
        )
        if (vvitri_id.startsWith('vt')) {
          let id = vvitri_id.substring(3).split('_')
          var q = this.KETCUOI.filter(
            (e) => e.KETCUOI_ID.toString() === id[0].toString()
          )
          if (q.length > 0) {
            this.$refs.ModalDanDoCapTheoKetCuoiDauNoi.show({
              KYHIEU: q[0].KYHIEU,
              VITRI: parseInt(id[1]),
              MAT: parseInt(id[2])
            }).then((result) => {
              if (result.ok) {
              } else {
              }
            })
          }
        }
        return
      }

      if (event.option.slug === 'node_trang_thai') {
        let vvitri_id = event.item.node().id
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 310 ~ menuNode_Clicked ~ vvitri_id',
          vvitri_id
        )
        if (vvitri_id.startsWith('vt')) {
          this.vitri_id = vvitri_id.substring(3).split('_')
          try {
            var data = {
              P_KETCUOI_ID: this.vitri_id[0],
              P_VITRI: this.vitri_id[1],
              P_MAT: this.vitri_id[2]
            }
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 333 ~ closeDialog ~ data',
              data
            )
            var rs = await this.$root.context.post(
              '/web-cabman/ban-do-mang-cap/get_tt_vitri',
              data
            )
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 337 ~ closeDialog ~ rs',
              rs
            )
            if (rs.data && rs.data.length > 0) {
              this.option = parseInt(rs.data[0].TRANGTHAI) - 1
            }
          } catch (error) {
            this.option = 0
          }
          this.dialogVisible = true
        }
      }
    },
    async menuEdge_Clicked (event) {
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 167 ~ menuEdge_Clicked ~ event',
        event
      )
      if (event.option.slug === 'edge_dao_chieu') {
        if (!this.CHECK_PERMIT_AND_MESSAGE()) return
        var vdaunoi_id = event.item.node().id
        var ketqua = await this.DAOCHIEU_KETNOI(vdaunoi_id.substring(3).split('_'))
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 180 ~ menuEdge_Clicked ~ ketqua',
          ketqua
        )
        if (ketqua) {
          await this.$alert('Đảo chiều kết nối thành công', 'Thông báo', {
            confirmButtonText: 'Đóng'
          })
          await this.loadData()
        } else {
        }
        return
      }
      if (event.option.slug === 'edge_xoa_ket_noi') {
        if (!this.CHECK_PERMIT_AND_MESSAGE()) return
        var vdaunoi_id = event.item.node().id
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 691 ~ XOA_DAU_NOI ~ vdaunoi_id',
          vdaunoi_id
        )
        if (vdaunoi_id.startsWith('dn')) {
          var ketqua = await this.XOA_DAU_NOI(vdaunoi_id.substring(3).split('_'))
          if (ketqua) {
            this.deleteSelectedComponents(event.item)
            await this.loadData()
          }
        } else if (vdaunoi_id.startsWith('dv') || vdaunoi_id.startsWith('dr')) {
          var errorCount = 0
          var src = []
          var dst = []
          var vcap_id,
            vmidspan_id,
            soi,
            vitri_n = 'null',
            vitri_d = 'null'
          if (vdaunoi_id.startsWith('dv')) {
            // Gỡ cáp vào
            dst.push('MAT')
            dst.push('null')
            dst.push('null')
            vitri_d = 'null'
          } else {
            // Gỡ cáp ra
            src.push('MAT')
            src.push('null')
            src.push('null')
            vitri_n = 'null'
          }
          var dnInfo = vdaunoi_id.substring(3).split('_')
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 197 ~ menuEdge_Clicked ~ dnInfo',
            dnInfo
          )
          vcap_id = dnInfo[0]
          vmidspan_id = dnInfo[1]
          soi = dnInfo[2]

          var data = []
          data.push(
            `select soi, vitri_n, vitri_d from cabman.daunoi_cap where phanvung_id=${this.$root.token.getPhanVungID()} and 1 = 0`
          )
          data.push(` union all select ${soi}, ${vitri_n}, ${vitri_d} from dual`)
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 641 ~ handleDblClickNode ~ data',
            data
          )
          if (!this.CHECK_PERMIT_AND_MESSAGE()) return
          ketqua = await this.DAUNOI_CAP_V2(
            vcap_id,
            vmidspan_id,
            // data.join(' '),
            soi,
            vitri_n,
            vitri_d,
            src,
            dst,
            true
          )
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 207 ~ menuEdge_Clicked ~ ketqua',
            ketqua
          )
          if (ketqua) {
            await this.loadData()
            this.deleteSelectedComponents(event.item)
          } else {
          }
        }
      }
    },
    async NHAP_PORT_AO () {
      if (!this.CHECK_PERMIT_AND_MESSAGE()) {
        return
      }
      try {
        var data = {
          P_KETCUOI_ID: this.node.KETCUOI_ID.split('_')[1]
        }
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 354 ~ NHAP_PORT_AO ~ data',
          data
        )
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/get_port_ao',
          data
        )
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 358 ~ NHAP_PORT_AO ~ rs', rs)

        var v = await this.$prompt('Nhập số lượng port ảo', 'Port ảo', {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Không',
          inputValue: rs.data[0].JUMPER_AO.toString(),
          inputType: 'number'
        })
        if (v.action === 'confirm') {
          var port_ao = parseInt(v.value)
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 372 ~ NHAP_PORT_AO ~ port_ao',
            port_ao
          )
          if (port_ao < 0) {
            await this.$alert('Số lượng nhập vào phải là số nguyên dương.', 'Thông báo', {
              confirmButtonText: 'Đóng',
              type: 'error'
            })
            return
          }
          if (port_ao > 100) {
            await this.$alert('Chỉ được tạo tối đa 100 port ảo', 'Thông báo', {
              confirmButtonText: 'Đóng',
              type: 'error'
            })
            return
          }
          data = {
            VKETCUOI_ID: this.node.KETCUOI_ID.split('_')[1],
            VSL_PORT: port_ao
          }
          rs = await this.$root.context.post(
            '/web-cabman/ban-do-mang-cap/capnhat_port_ao',
            data
          )
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 381 ~ NHAP_PORT_AO ~ rs',
            rs
          )
          if (rs.data.length > 0) {
            if (rs.data[0].KETQUA > 0) {
              await this.loadData()
            }
          }
        }
      } catch (error) {
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 351 ~ NHAP_PORT_AO ~ error',
          error
        )
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
      }
    },
    async CHUANHOA_SPLITTER_CHA () {
      try {
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/chuanhoa_splitter_cha'
        )
      } catch (error) {}
    },
    async GET_TT_KETCUOI (KETCUOI_ID) {
      try {
        var data = {
          P_KETCUOI_ID: KETCUOI_ID
        }
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_get_tt_ketcuoi',
          data
        )
        return rs.data
      } catch (error) {
        return []
      }
    },
    async btnThemKC () {
      if (!this.CHECK_PERMIT_AND_MESSAGE()) {
        return
      }
      try {
        this.$refs.ModalQLKetCuoiExt2.TaoKetCuoiCon(
          this.node.KETCUOI_ID.split('_')[1]
        ).then(async (result) => {
          if (result.ok) {
            console.log('Tạo kết cuối mới', result.data.KETCUOI_ID)
            if (result.data.KETCUOI_ID !== null) {
              var ketcuoi_id_new = result.data.KETCUOI_ID
              if (this.btnThemKC_Caption === 'Thêm khay ODF') {
                await this.CHUANHOA_SPLITTER_CHA()
                var data = await this.GET_TT_KETCUOI(this.node.KETCUOI_ID.split('_')[1])
                if (data.length > 0) {
                  if (
                    data[0].KETCUOI_CHA_ID !== null &&
                    ketcuoi_id_new === data[0].KETCUOI_CHA_ID
                  ) {
                    this.node.KETCUOI_ID = ketcuoi_id_new
                    EventBus.$emit('NAP_GIANDO_V2', 'NAP_GIANDO_V2')
                  }
                }
              }
              await this.loadData()
            }
          } else {
          }
        })
      } catch (error) {}
    },
    deleteSelectedComponents (component) {
      this.selectedComponents.style('display', 'none')
      let self = this
      var componentName = component.select('title').text()
      if (component.attr('class') === 'node') {
        self.dotGraph.deleteNode(componentName)
      } else {
        self.dotGraph.deleteEdge(componentName)
      }
      this.unSelectComponents()
    },
    setNode (node) {
      this.node = node
    },
    compareNumbers (a, b) {
      return parseInt(b.split('_')[2]) - parseInt(a.split('_')[2])
    },
    compareNumbers2 (a, b) {
      return b.VITRI - a.VITRI
    },
    compareNumbers3 (a, b) {
      return parseInt(b.split('_')[1]) - parseInt(a.split('_')[1])
    },
    Format (fmtstr) {
      var args = Array.prototype.slice.call(arguments, 1)
      return fmtstr.replace(/\{(\d+)\}/g, function (match, index) {
        return args[index]
      })
    },
    loadImage (nodesString) {
      const imageTags = (nodesString.match(/image=[^;]*(files\/\d*|jpg)/g) || []).filter((value, index, self) => {
        return self.indexOf(value) === index
      })
      const imagePaths = imageTags.map(keyvaluepaire => {
        return keyvaluepaire.substr(7)
      })
      console.log('🚀 ~ file: GraphVizControlGianDo.vue:1438 ~ imagePaths ~ imagePaths:', imagePaths)
      imagePaths.forEach(image => {
        this.graphviz.addImage(image, '48px', '48px')
      })
    },
    async createNode (node, NODE_LABEL) {
      console.log('🚀 ~ file: GraphVizControlGianDo.vue:1432 ~ createNode ~ node:', node)
      try {
        let image = node.shape.source
        let tooltip_content = node.tooltip.content.replaceAll('<br>', '\n').replaceAll('<strong>', '').replaceAll('</strong>', '').replaceAll('->', '[').replaceAll('<-', ']').replaceAll('<b>', '').replaceAll('</b>', '')

        let content = node.annotations[0].content.replaceAll('\n', '<br/>').replaceAll('->', '[').replaceAll('<-', ']')
        let label = NODE_LABEL.replace('NODE_LABEL', content)
        console.log('🚀 ~ file: GraphVizControlGianDo.vue:1452 ~ createNode ~ label:', label)
        let data = `"${node.id}" [id="${node.id}" tooltip="${tooltip_content}";label=<${label}>;image="${image}"]`
        return data
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControlGianDo.vue:1435 ~ createNode ~ error:', error)
        return null
      }
    },
    async createEdge (edge, EDGE_LABEL) {
      console.log('🚀 ~ file: GraphVizControlGianDo.vue:1455 ~ createEdge ~ edge:', edge)
      try {
        let tooltip = edge.tooltip.content.replaceAll('->', '[').replaceAll('<-', ']')
        let content = edge.tooltip.content.replaceAll('\n', '<br/>').replaceAll('->', '[').replaceAll('<-', ']')
        let label = EDGE_LABEL.replace('EDGE_LABEL', content)
        let penwidth = edge.style.strokeWidth - 1
        let style = edge.style.strokeDashArray === '' ? '' : 'dashed'
        let data = `"${edge.sourceID}" -> "${edge.targetID}" [id="${edge.id}" label=<<b>${content}</b>> style="${style}" tooltip="${tooltip}" weight=1, penwidth=${penwidth}]`
        return data
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControlGianDo.vue:1459 ~ createEdge ~ error:', error)
        return null
      }
    },
    async loadData (giandoConfig, nodes, edges) {
      try {
        if (nodes === null || edges === null) return
        this.setState({busy: true})
        this.startNode = null
        this.endNode = null
        this.selectedComponents = d3_selectAll(null)

        let GRAPH_RANKDIR = giandoConfig.GRAPH_RANKDIR
        let EDGE_FONTNAME = giandoConfig.EDGE_FONTNAME
        let EDGE_FONTSIZE = giandoConfig.EDGE_FONTSIZE
        let EDGE_MINLEN = giandoConfig.EDGE_MINLEN
        let GRAPH_NODESEP = giandoConfig.GRAPH_NODESEP
        let GRAPH_RANKSEP = giandoConfig.GRAPH_RANKSEP
        let NODE_FONTNAME = giandoConfig.NODE_FONTNAME
        let NODE_FONTSIZE = giandoConfig.NODE_FONTSIZE
        let NODE_HEIGHT = giandoConfig.NODE_HEIGHT
        let NODE_SHAPE = giandoConfig.NODE_SHAPE
        let NODE_WIDTH = giandoConfig.NODE_WIDTH
        let GRAPH_SPLINES = giandoConfig.GRAPH_SPLINES
        let GRAPH_SEP = giandoConfig.GRAPH_SEP
        let EDGE_COLOR = giandoConfig.EDGE_COLOR
        let GRAPH_OVERLAP = giandoConfig.GRAPH_OVERLAP
        let NODE_LABEL = giandoConfig.NODE_LABEL
        let EDGE_LABEL = giandoConfig.EDGE_LABEL

        let arrNode = []
        let arrEdge = []
        for (let index = 0; index < nodes.length; index++) {
          if (!nodes[index].isDiemuon) {
            let node = await this.createNode(nodes[index], NODE_LABEL)
            arrNode.push(node)
          }
        }
        let dataNodes = arrNode.join(';\n')

        for (let index = 0; index < edges.length; index++) {
          let edge = await this.createEdge(edges[index], EDGE_LABEL)
          arrEdge.push(edge)
        }
        let dataEdges = arrEdge.join(';\n')

        var sw = `digraph G\n`
        sw = sw + `{\n rankdir=${GRAPH_RANKDIR}; \n`
        sw =
          sw +
          ` graph [ranksep=${GRAPH_RANKSEP},nodesep=${GRAPH_NODESEP},splines=${GRAPH_SPLINES},sep=\"${GRAPH_SEP}\",overlap=${GRAPH_OVERLAP}];\n`
        sw =
          sw +
          ` subgraph cluster_space {\n`

        sw =
          sw +
          ` graph [peripheries=0]\n`

        sw =
          sw +
          ` node[shape=${NODE_SHAPE} width=${NODE_WIDTH} height=${NODE_HEIGHT} fixedsize=true fontname=\"${NODE_FONTNAME}\" fontsize=${NODE_FONTSIZE}];\n`
        sw =
          sw +
          ` edge [fontname=\"${EDGE_FONTNAME}\" fontsize=${EDGE_FONTSIZE} minlen=${EDGE_MINLEN} color="${EDGE_COLOR}"];\n`

        sw = sw + dataNodes
        sw = sw + dataEdges

        sw = sw + '}\n}\n'

        console.log('🚀 ~ file: GraphVizControl.vue ~ setDataGraphviz', sw)
        this.setDataGraphviz(sw)
        this.setState({busy: false})
      } catch (error) {
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 717 ~ loadData ~ error',
          error
        )
        this.setDataGraphviz(null)
        this.setState({ busy: false })
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
      }
    },
    setDataGraphviz (data) {
      this.dataGraphviz = data
    },
    createGraph (renderGraph) {
      this.graphviz = graphviz(`#${this.graphID}`, {
        useWorker: true,
        totalMemory: this.totalMemory
      })
      this.loadImage(renderGraph)
      this.graphviz
        .onerror(() => this.handleError())
        .on('initEnd', () => this.renderGraph(renderGraph))
    },
    unFitGraph () {
      let width = this.div.node().parentElement.clientWidth
      let height = this.div.node().parentElement.clientHeight
      this.svg.attr('viewBox', `0 0 ${(width * 3) / 4} ${(height * 3) / 4}`)
    },
    fitGraph () {
      this.svg.attr(
        'viewBox',
        `0 0 ${this.originalViewBox.width} ${this.originalViewBox.height}`
      )
    },
    setZoomScale (scale, center = false, reset = false) {
      let viewBox = this.svg.attr('viewBox').split(' ')
      let bbox = this.graph0.node().getBBox()
      let { x, y, k } = d3_zoomTransform(this.graphviz.zoomSelection().node())
      let [x0, y0, scale0] = [x, y, k]
      let xOffset0 = x0 + bbox.x * scale0
      let yOffset0 = y0 + bbox.y * scale0
      let xCenter = viewBox[2] / 2
      let yCenter = viewBox[3] / 2
      let xOffset
      let yOffset
      if (center) {
        xOffset = (viewBox[2] - bbox.width * scale) / 2
        yOffset = (viewBox[3] - bbox.height * scale) / 2
      } else if (reset) {
        xOffset = 0
        yOffset = 0
      } else {
        xOffset = xCenter - ((xCenter - xOffset0) * scale) / scale0
        yOffset = yCenter - ((yCenter - yOffset0) * scale) / scale0
      }
      x = -bbox.x * scale + xOffset
      y = -bbox.y * scale + yOffset
      let transform = d3_zoomIdentity.translate(x, y).scale(scale)
      this.graphviz
        .zoomSelection()
        .call(this.graphviz.zoomBehavior().transform, transform)
    },
    setState (state) {
      this.state = state
    },
    handleError (errorMessage) {
      if (!errorMessage) return
      let line = errorMessage.replace(/.*error in line ([0-9]*) .*\n/, '$1')
      this.$emit('onError', { message: errorMessage, line: line })
      this.rendering = false
      this.setState({ busy: false })
      if (this.pendingUpdate) {
        this.pendingUpdate = false
        this.$forceUpdate()
      }
    },
    renderGraph (dataGraphviz) {
      let fit = this.fit
      if (dataGraphviz === null || dataGraphviz.length === 0) {
        this.svg.remove()
        this.svg = d3_select(null)
        this.$emit('onError', null)
        this.renderGraphReady = false
        this.setState({ busy: false })
        return
      }
      if (this.rendering) {
        this.pendingUpdate = true
        return
      }
      if (this.fit !== this.prevFit) {
        if (this.renderGraphReady) {
          if (this.prevFit) {
            this.unFitGraph()
            this.setZoomScale(1, true)
          } else {
            this.setZoomScale(1, false, true)
            this.fitGraph()
          }
        }
        this.prevFit = this.fit
      }
      this.prevDataGraphviz = dataGraphviz
      // console.log(
      //   '🚀 ~ file: GraphVizControl.vue ~ line 162 ~ renderGraph ~ this.prevDataGraphviz',
      //   this.prevDataGraphviz
      // )
      try {
        this.prelDotGraph = new DotGraph(dataGraphviz)
        this.$emit('onError', null)
        // console.log(
        //   '🚀 ~ file: DotLanguage.vue ~ line 251 ~ renderGraph ~ this.prelDotGraph',
        //   this.prelDotGraph.ast.children
        // )
      } catch (error) {
        this.setState({ busy: false })
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 165 ~ renderGraph ~ error',
          error
        )
        if (!error.location) {
          throw error
        }
        let {
          location: {
            start: { line }
          },
          message
        } = error
        this.$emit('onError', { message: message, line: line })
      }
      this.rendering = true
      this.setState({ busy: true })
      // this.graphviz
      //   .fit(fit)
      //   .dot(dataGraphviz, () => this.handleDotLayoutReady())
      //   .on('renderEnd', () => this.handleRenderStaged())
      //   .on('end', () => this.handleEnd())
      //   .render(() => this.handleRenderGraphReady())
      this.graphviz
        .fit(fit)
        .dot(dataGraphviz, () => this.handleDotLayoutReady())
        .on('renderEnd', () => this.handleRenderStaged())
        .on('end', () => this.handleEnd())
        .render(() => this.handleRenderGraphReady())
    },
    handleEnd () {
      console.log('handleEnd')
    },
    addEventHandlers () {
      let self = this
      // this.graphviz.zoomBehavior().filter(function () {
      //   if (event.type === 'mousedown' && !event.ctrlKey) {
      //     if (self.isDrawingEdge) {
      //       return true
      //     } else {
      //       return false
      //     }
      //   } else {
      //     return true
      //   }
      // })
      var nodes = this.svg.selectAll('g.node')
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 196 ~ addEventHandlers ~ nodes',
        nodes
      )
      var edges = this.svg.selectAll('g.edge')
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 199 ~ addEventHandlers ~ edges',
        edges
      )

      var clusters = this.svg.selectAll('g[id*="kc_"].cluster')
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 1225 ~ addEventHandlers ~ clusters',
        clusters
      )

      d3_select(window).on('resize', (d) => this.resizeSVG(d))
      d3_select(document).on('keydown', (d, i, nodes) =>
        this.handleKeyDownDocument(d, i, nodes)
      )

      if (!this.readonly) {
        this.div.on('click', (d) => this.handleClickDiv())
        this.div.on('contextmenu', (d) => this.handleRightClickDiv(d))

        nodes.on('click mousedown', (d, i) => this.handleClickNode(d, i))
        nodes.on('contextmenu', (d, i) => this.handleRightClickNode(d, i))
        nodes.on('dblclick', (d, i) => this.handleDblClickNode(d, i))

        edges.on('click mousedown', (d, i) => this.handleClickEdge(d, i))
        edges.on('contextmenu', (d, i) => this.handleRightClickEdge(d, i))

        clusters.on('click', (d, i) => this.handleClickCluster(d, i))
        clusters.on('contextmenu', (d, i) => this.handleRightCluster(d, i))
      }
    },
    handleKeyDownDocument (d, i, nodes) {
      if (event.target.nodeName !== 'BODY') {
        return
      }
      if (event.key === 'Escape') {
        this.graphviz.removeDrawnEdge()
        this.unSelectComponents()
        this.startNode = null
        this.endNode = null
      }
      event.preventDefault()
      this.isDrawingEdge = false
    },
    resizeSVG (d) {
      // let width = this.div.node().parentElement.clientWidth
      // let height = this.div.node().parentElement.clientHeight
      // let fit = this.fit
      // this.svg.attr('width', width).attr('height', height)
      // if (!fit) {
      //   this.unFitGraph()
      // }
    },
    handleClickCluster (d, i) {
      event.preventDefault()
      event.stopPropagation()
    },
    handleRightCluster (d, i) {
      event.preventDefault()
      event.stopPropagation()
      var e = event
    },
    handleRightClickEdge (d, i) {
      document.activeElement.blur()
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 636 ~ handleRightClickEdge ~ d',
        d
      )
      var e = event
      event.preventDefault()
      event.stopPropagation()
      this.unSelectComponents()
      this.graphviz.removeDrawnEdge()

      var id = i.attributes.id
      if (!id.startsWith('dn') && !id.startsWith('dv') && !id.startsWith('dr')) {
        return
      }

      if (event.which === 3) {
        // let extendSelection = event.ctrlKey || event.shiftKey
        let extendSelection = false
        this.selectComponents(d3_select(`#${i.attributes.id}`), extendSelection)
      }
      setTimeout(() => {
        this.dispatch.call('contextmenuEdge', this, {
          e: e,
          node: d3_select(`#${i.attributes.id}`)
        })
      }, 10)
    },
    handleClickEdge (d, i) {
      console.log('handleClickEdge', `#${i.attributes.id}`)
      document.activeElement.blur()
      event.preventDefault()
      event.stopPropagation()

      var nodes = this.svg.selectAll('g.node')
      var edges = this.svg.selectAll('g.edge')
      nodes.classed('selected', false)
      edges.classed('selected', false)

      // var id = i.attributes.id
      // if (!id.startsWith('dn') && !id.startsWith('dv') && !id.startsWith('dr')) {
      //   return
      // }
      this.dispatch.apply('start', this, [event])
      // let extendSelection = event.ctrlKey || event.shiftKey
      let extendSelection = false
      this.selectComponents(d3_select(`#${i.attributes.id}`), extendSelection)
    },
    handleClickDiv () {
      document.activeElement.blur()
      event.preventDefault()
      event.stopPropagation()
      this.$refs.menuNodeDauNoi && this.$refs.menuNodeDauNoi.hideContextMenu()
      this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
      this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

      if (!(event.which === 1 && (event.ctrlKey || event.shiftKey))) {
        this.unSelectComponents()
      }
    },
    async DAOCHIEU_KETNOI (id) {
      try {
        if (id[0].toString() === 'dn') {
          for (var i = 1; i < id.Length; i++) {
            id[i - 1] = id[i]
          }
        }
        var data = {
          VTHIETBI_N_ID: id[0],
          VVITRI_N: id[1],
          VMAT_N: id[2],
          VTHIETBI_D_ID: id[3],
          VVITRI_D: id[4],
          VMAT_D: id[5]
        }
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 789 ~ DAOCHIEU_KETNOI ~ data',
          data
        )
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_daochieu_ketnoi',
          data
        )
        if (rs.data.length > 0 && rs.data[0].KETQUA > 0) {
          return true
        } else {
          return false
        }
      } catch (error) {
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 771 ~ DAOCHIEU_KETNOI ~ error',
          error.response
        )
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
        return false
      }
    },
    async XOA_DAU_NOI (id) {
      try {
        if (id[0].toString() === 'dn') {
          for (var i = 1; i < id.Length; i++) {
            id[i - 1] = id[i]
          }
        }
        var data = {
          VTHIETBI_N_ID: id[0],
          VVITRI_N: id[1],
          VMAT_N: id[2],
          VTHIETBI_D_ID: id[3],
          VVITRI_D: id[4],
          VMAT_D: id[5]
        }
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 712 ~ XOA_DAU_NOI ~ data',
          data
        )
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_xoa_ketnoi',
          data
        )
        if (rs.data.length > 0 && rs.data[0].KETQUA > 0) {
          return true
        } else {
          return false
        }
      } catch (error) {
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 699 ~ XOA_DAU_NOI ~ error',
          error
        )
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
        return false
      }
    },
    async DAU_NOI () {
      try {
        var startPort = this.startNode.node().id
        var endPort = this.endNode.node().id
        if (startPort === endPort) {
          this.startNode = null
          this.endNode = null
          this.unSelectComponents()
          return
        }
        // Dau noi ket cuoi
        if (startPort.startsWith('vt') && endPort.startsWith('vt')) {
          startPort = startPort.substring(3)
          endPort = endPort.substring(3)
          if (startPort === endPort) {
            this.startNode = null
            this.endNode = null
            this.unSelectComponents()
            return
          } else {
            startPort = startPort + '_25'
            endPort = endPort + '_25'
            if (!this.CHECK_PERMIT_AND_MESSAGE()) return
            var ketqua = await this.TAO_KETNOI_DN_V2(
              startPort.split('_'),
              endPort.split('_')
            )
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 248 ~ handleDblClickNode ~ ketqua',
              ketqua
            )
            if (ketqua) {
              this.startNode = null
              this.endNode = null
              await this.loadData()
            } else {
              this.startNode = null
              this.endNode = null
            }
            this.unSelectComponents()
          }
        } else if (!startPort.startsWith('vt') && !endPort.startsWith('vt')) {
          await this.$alert('Không được phép nối 2 sợi cáp với nhau', 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
          this.startNode = null
          this.endNode = null
        } else {
          if (startPort !== endPort) {
            var kcPort = startPort
            var capPort = endPort
            if (!kcPort.startsWith('vt')) {
              kcPort = endPort
              capPort = startPort
            }
            var src = []
            var dst = []
            var vcap_id
            var vmidspan_id
            var soi
            var vitri_n = 'null'
            var vitri_d = 'null'
            var dtDauNoiCap = capPort.startsWith('sv')
              ? this.DAUNOI_CAP_VAO
              : this.DAUNOI_CAP_RA
            var svRecord = dtDauNoiCap.filter(
              (m) => `${m.CAP_ID}_${m.MIDSPAN_ID}_${m.SOI}` === capPort.substring(3)
            )
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 607 ~ handleDblClickNode ~ svRecord',
              svRecord
            )
            if (svRecord.length === 0) {
              return
            }
            if (svRecord[0].READONLY && svRecord[0].READONLY === 1) {
              this.startNode = null
              this.endNode = null
              return
            }
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 613 ~ handleDblClickNode ~ capPort',
              capPort
            )
            if (capPort.startsWith('sv')) {
              // Cáp vào
              if (svRecord[0].THIETBI_D_ID !== null) {
                return
              }

              kcPort = kcPort.substring(3)
              capPort = capPort.substring(3)
              dst.push('MAT')
              dst.push(kcPort.split('_')[0])
              dst.push(kcPort.split('_')[2])
              vitri_d = kcPort.split('_')[1]
            } else {
              // Cáp ra
              if (svRecord[0].THIETBI_N_ID !== null) {
                return
              }
              kcPort = kcPort.substring(3)
              capPort = capPort.substring(3)
              src.push('MAT')
              src.push(kcPort.split('_')[0])
              src.push(kcPort.split('_')[2])
              vitri_n = kcPort.split('_')[1]
            }
            vcap_id = capPort.split('_')[0]
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 633 ~ handleDblClickNode ~ vcap_id',
              vcap_id
            )
            vmidspan_id = capPort.split('_')[1]
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 635 ~ handleDblClickNode ~ vmidspan_id',
              vmidspan_id
            )
            soi = capPort.split('_')[2]
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 637 ~ handleDblClickNode ~ soi',
              soi
            )
            var data = []
            data.push(
              `select soi, vitri_n, vitri_d from cabman.daunoi_cap where phanvung_id=${this.$root.token.getPhanVungID()} and 1 = 0`
            )
            data.push(` union all select ${soi}, ${vitri_n}, ${vitri_d} from dual`)
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 641 ~ handleDblClickNode ~ data',
              data
            )
            if (!this.CHECK_PERMIT_AND_MESSAGE()) return
            ketqua = await this.DAUNOI_CAP_V2(
              vcap_id,
              vmidspan_id,
              // data.join(' '),
              soi,
              vitri_n,
              vitri_d,
              src,
              dst,
              true
            )
            console.log(
              '🚀 ~ file: GraphVizControl.vue ~ line 645 ~ handleDblClickNode ~ ketqua',
              ketqua
            )
            if (ketqua) {
              this.unSelectComponents()
              this.startNode = null
              this.endNode = null
              await this.loadData()
            } else {
              this.startNode = null
              this.endNode = null
            }
          }
        }
      } catch (error) {
        this.startNode = null
        this.endNode = null
      }
    },
    async handleDblClickNode (d, i) {
      document.activeElement.blur()
      event.preventDefault()
      event.stopPropagation()
      this.unSelectComponents()
      if (this.isDrawingEdge) {
        // console.log('🚀 ~ file: DotLanguage.vue ~ line 438 ~ startNode', this.startNode.node().id)
        // var endNode = d3_select(`#${i.attributes.id}`)
        // console.log('🚀 ~ file: DotLanguage.vue ~ line 434 ~ endNode', endNode)
        // var startNodeName = this.startNode
        //   .selectWithoutDataPropagation('title')
        //   .text()
        // var endNodeName = endNode
        //   .selectWithoutDataPropagation('title')
        //   .text()
        // if (startNodeName === endNodeName) return
        // try {
        //   this.graphviz.insertDrawnEdge(startNodeName + '->' + endNodeName)
        //   console.log("🚀 ~ file: GraphVizControl.vue ~ line 227 ~ handleDblClickNode ~ startNodeName + '->' + endNodeName", startNodeName + '->' + endNodeName)
        // } catch (error) {
        //   console.log('🚀 ~ file: DotLanguage.vue ~ line 452 ~ error', error)
        // }
        // this.latestEdgeAttributes = Object.assign({}, this.defaultEdgeAttributes)
        // this.dotGraph.insertEdge(startNodeName, endNodeName, this.latestEdgeAttributes)
        // this.dataGraphviz = this.dotGraph.dotSrc
        // var startPort = this.startNode.node().id
        // var endNode = d3_select(`#${i.attributes.id}`)
        // var endPort = endNode.node().id
        // if (startPort === endPort) return
        // // Dau noi ket cuoi
        // if (startPort.startsWith('vt') && endPort.startsWith('vt')) {
        //   startPort = startPort.substring(3)
        //   endPort = endPort.substring(3)
        //   if (startPort === endPort) {
        //   } else {
        //     startPort = startPort + '_25'
        //     endPort = endPort + '_25'
        //     if (!this.CHECK_PERMIT_AND_MESSAGE()) return
        //     var ketqua = await this.TAO_KETNOI_DN_V2(startPort.split('_'), endPort.split('_'))
        //     console.log('🚀 ~ file: GraphVizControl.vue ~ line 248 ~ handleDblClickNode ~ ketqua', ketqua)
        //     if (ketqua) {
        //       this.loadData()
        //     }
        //   }
        // } else if (!startPort.startsWith('vt') && !endPort.startsWith('vt')) {
        //   // Msg.ShowWarning("Không được phép nối 2 sợi cáp với nhau");
        // } else {
        //   if (startPort !== endPort) {
        //     var kcPort = startPort
        //     var capPort = endPort
        //     if (!kcPort.startsWith('vt')) {
        //       kcPort = endPort
        //       capPort = startPort
        //     }
        //     var src = []
        //     var dst = []
        //     var vcap_id
        //     var vmidspan_id
        //     var soi
        //     var vitri_n = 'null'
        //     var vitri_d = 'null'
        //     var dtDauNoiCap = capPort.startsWith('sv') ? this.DAUNOI_CAP_VAO : this.DAUNOI_CAP_RA
        //     var svRecord = dtDauNoiCap.filter(m => `${m.CAP_ID}_${m.MIDSPAN_ID}_${m.SOI}` === capPort.substring(3))
        //     console.log('🚀 ~ file: GraphVizControl.vue ~ line 607 ~ handleDblClickNode ~ svRecord', svRecord)
        //     if (svRecord.length === 0) { return }
        //     if (svRecord[0].READONLY && svRecord[0].READONLY === 1) {
        //       return
        //     }
        //     console.log('🚀 ~ file: GraphVizControl.vue ~ line 613 ~ handleDblClickNode ~ capPort', capPort)
        //     if (capPort.startsWith('sv')) {
        //       // Cáp vào
        //       if (svRecord[0].THIETBI_D_ID !== null) { return }
        //       kcPort = kcPort.substring(3)
        //       capPort = capPort.substring(3)
        //       dst.push('MAT')
        //       dst.push(kcPort.split('_')[0])
        //       dst.push(kcPort.split('_')[2])
        //       vitri_d = kcPort.split('_')[1]
        //     } else {
        //       // Cáp ra
        //       if (svRecord[0].THIETBI_N_ID !== null) { return }
        //       kcPort = kcPort.substring(3)
        //       capPort = capPort.substring(3)
        //       src.push('MAT')
        //       src.push(kcPort.split('_')[0])
        //       src.push(kcPort.split('_')[2])
        //       vitri_n = kcPort.split('_')[1]
        //     }
        //     vcap_id = capPort.split('_')[0]
        //     console.log('🚀 ~ file: GraphVizControl.vue ~ line 633 ~ handleDblClickNode ~ vcap_id', vcap_id)
        //     vmidspan_id = capPort.split('_')[1]
        //     console.log('🚀 ~ file: GraphVizControl.vue ~ line 635 ~ handleDblClickNode ~ vmidspan_id', vmidspan_id)
        //     soi = capPort.split('_')[2]
        //     console.log('🚀 ~ file: GraphVizControl.vue ~ line 637 ~ handleDblClickNode ~ soi', soi)
        //     var data = []
        //     data.push(`select soi, vitri_n, vitri_d from cabman.daunoi_cap where phanvung_id=${this.$root.token.getPhanVungID()} and 1 = 0`)
        //     data.push(` union all select ${soi}, ${vitri_n}, ${vitri_d} from dual`)
        //     console.log('🚀 ~ file: GraphVizControl.vue ~ line 641 ~ handleDblClickNode ~ data', data)
        //     if (!this.CHECK_PERMIT_AND_MESSAGE()) return
        //     ketqua = await this.DAUNOI_CAP(vcap_id, vmidspan_id, data.join(' '), src, dst, true)
        //     console.log('🚀 ~ file: GraphVizControl.vue ~ line 645 ~ handleDblClickNode ~ ketqua', ketqua)
        //     if (ketqua) {
        //       this.loadData()
        //     }
        //  }
        //  }
      }
      this.isDrawingEdge = false
    },
    async DAUNOI_CAP (vcap_id, vmidspan_id, sData, src, dst, ktTruocKhiDauNoi) {
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 654 ~ DAUNOI_CAP ~ dst', dst)
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 654 ~ DAUNOI_CAP ~ src', src)
      try {
        var vdaucapvao = dst.length === 0 ? 0 : 1
        var vdaucapra = src.length === 0 ? 0 : 1
        var VLOAITBI_N_ID, VTHIETBI_N_ID, VMAT_N
        var VLOAITBI_D_ID, VTHIETBI_D_ID, VMAT_D

        if (src.length > 0) {
          switch (src[0].toString()) {
            case 'MAT':
              VLOAITBI_N_ID = 25
              VTHIETBI_N_ID = src[1]
              VMAT_N = src[2]
              break
            case 'PHIEN_DOC':
              VLOAITBI_N_ID = 23
              VTHIETBI_N_ID = src[1]
              VMAT_N = 2
              break
            case 'ODF':
              VLOAITBI_N_ID = 18
              VTHIETBI_N_ID = src[1]
              VMAT_N = 2
              break
            case 'CARD_DSL':
              VLOAITBI_N_ID = 6
              VTHIETBI_N_ID = src[1]
              VMAT_N = 2
              break
            case 'CARD_GPON':
              VLOAITBI_N_ID = 10
              VTHIETBI_N_ID = src[1]
              VMAT_N = 2
              break
            case 'SWITCH':
              VLOAITBI_N_ID = 2
              VTHIETBI_N_ID = src[1]
              VMAT_N = 2
              break
          }
        } else {
          VLOAITBI_N_ID = 'null'
          VTHIETBI_N_ID = 'null'
          VMAT_N = 'null'
        }

        if (dst.length > 0) {
          switch (dst[0].toString()) {
            case 'MAT':
              VLOAITBI_D_ID = 25
              VTHIETBI_D_ID = dst[1]
              VMAT_D = dst[2]
              break
            case 'PHIEN_DOC':
              VLOAITBI_D_ID = 23
              VTHIETBI_D_ID = dst[1]
              VMAT_D = 2
              break
            case 'ODF':
              VLOAITBI_D_ID = 18
              VTHIETBI_D_ID = dst[1]
              VMAT_D = 2
              break
          }
        } else {
          VLOAITBI_D_ID = 'null'
          VTHIETBI_D_ID = 'null'
          VMAT_D = 'null'
        }
        var data = {
          DATA: this.ascii_to_hex(sData),
          VCAP_ID: vcap_id,
          VDAUCAPVAO: vdaucapvao,
          VDAUCAPRA: vdaucapra,
          VKTTRUOCKHIDAUNOI: ktTruocKhiDauNoi === true ? 1 : 0,
          VMIDSPAN_ID: vmidspan_id,
          VLOAITBI_N_ID: VLOAITBI_N_ID,
          VTHIETBI_N_ID: VTHIETBI_N_ID,
          VMAT_N: VMAT_N,
          VLOAITBI_D_ID: VLOAITBI_D_ID,
          VTHIETBI_D_ID: VTHIETBI_D_ID,
          VMAT_D: VMAT_D
        }
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 738 ~ DAUNOI_CAP ~ data',
          data
        )
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_daunoi_cap',
          data
        )
        if (rs.data.length > 0) {
          if (rs.data[0].KETQUA > 0) return true
          else return false
        } else return false
      } catch (error) {
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
        return false
      }
    },
    async DAUNOI_CAP_V2 (vcap_id, vmidspan_id, V_SOI, V_VITRI_N, V_VITRI_D, src, dst, ktTruocKhiDauNoi) {
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 654 ~ DAUNOI_CAP ~ dst', dst)
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 654 ~ DAUNOI_CAP ~ src', src)
      try {
        var vdaucapvao = dst.length === 0 ? 0 : 1
        var vdaucapra = src.length === 0 ? 0 : 1
        var VLOAITBI_N_ID, VTHIETBI_N_ID, VMAT_N
        var VLOAITBI_D_ID, VTHIETBI_D_ID, VMAT_D

        if (src.length > 0) {
          switch (src[0].toString()) {
            case 'MAT':
              VLOAITBI_N_ID = 25
              VTHIETBI_N_ID = src[1]
              VMAT_N = src[2]
              break
            case 'PHIEN_DOC':
              VLOAITBI_N_ID = 23
              VTHIETBI_N_ID = src[1]
              VMAT_N = 2
              break
            case 'ODF':
              VLOAITBI_N_ID = 18
              VTHIETBI_N_ID = src[1]
              VMAT_N = 2
              break
            case 'CARD_DSL':
              VLOAITBI_N_ID = 6
              VTHIETBI_N_ID = src[1]
              VMAT_N = 2
              break
            case 'CARD_GPON':
              VLOAITBI_N_ID = 10
              VTHIETBI_N_ID = src[1]
              VMAT_N = 2
              break
            case 'SWITCH':
              VLOAITBI_N_ID = 2
              VTHIETBI_N_ID = src[1]
              VMAT_N = 2
              break
          }
        } else {
          VLOAITBI_N_ID = 'null'
          VTHIETBI_N_ID = 'null'
          VMAT_N = 'null'
        }

        if (dst.length > 0) {
          switch (dst[0].toString()) {
            case 'MAT':
              VLOAITBI_D_ID = 25
              VTHIETBI_D_ID = dst[1]
              VMAT_D = dst[2]
              break
            case 'PHIEN_DOC':
              VLOAITBI_D_ID = 23
              VTHIETBI_D_ID = dst[1]
              VMAT_D = 2
              break
            case 'ODF':
              VLOAITBI_D_ID = 18
              VTHIETBI_D_ID = dst[1]
              VMAT_D = 2
              break
          }
        } else {
          VLOAITBI_D_ID = 'null'
          VTHIETBI_D_ID = 'null'
          VMAT_D = 'null'
        }
        var data = {
          V_SOI: V_SOI,
          V_VITRI_N: V_VITRI_N,
          V_VITRI_D: V_VITRI_D,
          VCAP_ID: vcap_id,
          VDAUCAPVAO: vdaucapvao,
          VDAUCAPRA: vdaucapra,
          VKTTRUOCKHIDAUNOI: ktTruocKhiDauNoi === true ? 1 : 0,
          VMIDSPAN_ID: vmidspan_id,
          VLOAITBI_N_ID: VLOAITBI_N_ID,
          VTHIETBI_N_ID: VTHIETBI_N_ID,
          VMAT_N: VMAT_N,
          VLOAITBI_D_ID: VLOAITBI_D_ID,
          VTHIETBI_D_ID: VTHIETBI_D_ID,
          VMAT_D: VMAT_D
        }
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 738 ~ DAUNOI_CAP_V2 ~ data',
          data
        )
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_daunoi_cap_v2',
          data
        )
        if (rs.data.length > 0) {
          if (rs.data[0].KETQUA > 0) return true
          else return false
        } else return false
      } catch (error) {
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
        return false
      }
    },
    toHex (str) {
      var result = ''
      for (var i = 0; i < str.length; i++) {
        result += str.charCodeAt(i).toString(16)
      }
      return result
    },
    ascii_to_hex (str) {
      var arr1 = []
      for (var n = 0, l = str.length; n < l; n++) {
        var hex = Number(str.charCodeAt(n)).toString(16)
        arr1.push(hex)
      }
      return arr1.join('')
    },
    async TAO_KETNOI_DN_V2 (startPort, endPort) {
      try {
        var data = {
          VKIEU: startPort[0] === endPort[0] && startPort[2] !== endPort[2] ? 1 : 2,
          VTHIETBI_N_ID: startPort[0],
          VVITRI_N: startPort[1],
          VMAT_N: startPort[2],
          VLOAITBI_N_ID: startPort[3],
          VTHIETBI_D_ID: endPort[0],
          VVITRI_D: endPort[1],
          VMAT_D: endPort[2],
          VLOAITBI_D_ID: endPort[3],
          VCAPTD_ID: 0
        }
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 268 ~ TAO_KETNOI_DN_V2 ~ data',
          data
        )
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_tao_ketnoi_dn',
          data
        )
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 271 ~ TAO_KETNOI_DN_V2 ~ rs',
          rs
        )
        if (rs.data.length > 0) {
          if (rs.data[0].KETQUA > 0) return true
          else return false
        } else return false
      } catch (error) {
        console.log(
          '🚀 ~ file: GraphVizControl.vue ~ line 275 ~ TAO_KETNOI_DN_V2 ~ error',
          error
        )
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
        return false
      }
    },
    CHECK_PERMIT_AND_MESSAGE () {
      return true
    },
    handleRightClickNode (d, i) {
      document.activeElement.blur()
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 836 ~ handleRightClickNode ~ event',
        event
      )
      var e = event
      event.preventDefault()
      event.stopPropagation()
      this.unSelectComponents()
      this.graphviz.removeDrawnEdge()

      var id = i.attributes.id
      if (!id.startsWith('vt') && !id.startsWith('sv') && !id.startsWith('sr')) {
        return
      }

      var [x0, y0] = d3_pointer(event, this.graph0.node())
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 805 ~ handleRightClickNode ~ x0, y0',
        x0,
        y0
      )
      if (this.edgeIndex === null) {
        this.edgeIndex = d3_selectAll('.edge').size()
      } else {
        this.edgeIndex += 1
      }
      this.latestEdgeAttributes = Object.assign({}, this.defaultEdgeAttributes)
      this.latestEdgeAttributes.id = 'edge' + (this.edgeIndex + 1)
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 223 ~ handleRightClickNode ~ latestEdgeAttributes',
        this.latestEdgeAttributes
      )
      try {
        this.graphviz.drawEdge(x0, y0, x0, y0, this.latestEdgeAttributes)
      } catch (error) {
        console.log('🚀 ~ file: DotLanguage.vue ~ line 429 ~ drawEdge error', error)
      }
      if (!this.isDrawingEdge && event.which === 3) {
        let extendSelection = event.ctrlKey || event.shiftKey
        this.selectComponents(d3_select(`#${i.attributes.id}`), extendSelection)
      }
      this.isDrawingEdge = false
      setTimeout(() => {
        this.dispatch.call('contextmenu', this, {
          e: e,
          node: d3_select(`#${i.attributes.id}`)
        })
      }, 10)
    },
    handleClickNode (d, i) {
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 205 ~ handleClickNode ~ d, i',
        d,
        i
      )
      document.activeElement.blur()
      event.preventDefault()
      event.stopPropagation()

      var nodes = this.svg.selectAll('g.node')
      var edges = this.svg.selectAll('g.edge')
      edges.classed('selected', false)
      nodes.classed('selected', false)

      this.$refs.menuNodeDauNoi && this.$refs.menuNodeDauNoi.hideContextMenu()
      this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
      this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

      var id = i.attributes.id
      if (!id.startsWith('vt') && !id.startsWith('sv') && !id.startsWith('sr')) {
        return
      }

      this.dispatch.apply('start', this, [event])
      if (!this.isDrawingEdge && event.which === 1) {
        // let extendSelection = event.ctrlKey || event.shiftKey
        let extendSelection = false
        this.selectComponents(d3_select(`#${i.attributes.id}`), extendSelection)
      }
    },
    handleRightClickDiv (d) {
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 216 ~ handleRightClickDiv ~ d',
        d
      )
      event.preventDefault()
      event.stopPropagation()
      this.$refs.menuNodeDauNoi && this.$refs.menuNodeDauNoi.hideContextMenu()
      this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
      this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

      this.unSelectComponents()
    },
    unSelectComponents () {
      this.selectRects.remove()
      this.selectRects = d3_select(null)
      if (this.selectedComponents.size() > 0) {
        this.selectedComponents = d3_selectAll(null)
      }
      var nodes = this.svg.selectAll('g.node')
      var edges = this.svg.selectAll('g.edge')
      edges.classed('selected', false)
      nodes.classed('selected', false)
    },
    selectComponents (components, extendSelection = false) {
      if (extendSelection) {
        this.selectedComponents = d3_selectAll(
          this.selectedComponents.nodes().concat(components.nodes())
        )
      } else {
        this.unSelectComponents()
        this.selectedComponents = components
      }
      this.markSelectedComponents(components, extendSelection)
    },
    initContextMenu () {
      const svgDom = document.querySelector(`#${this.graphID} svg`)
      this.dispatch = d3_dispatch(
        'start',
        'contextmenu',
        'contextmenuEdge',
        'contextmenuCluster'
      )
        .on('start', (event) => {
          this.point = { x: event.x, y: event.y }
        })
        .on('contextmenu', (target) => {
          this.$refs.menuNodeDauNoi && this.$refs.menuNodeDauNoi.hideContextMenu()
          this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
          this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

          console.log('🚀 ~ file: GraphVizControl.vue ~ line 871 ~ .on ~ target', target)
          const svgRect = svgDom.getBoundingClientRect()
          this.$refs.menuNodeDauNoi.showMenu(target.e, target.node)
          var menu = document.getElementById('menuNodeDauNoi')
          menu.style.left = `${this.point.x - svgRect.x}px`
          menu.style.top = `${this.point.y - svgRect.y}px`
          menu.position = 'fixed'
          menu.classList.add('menu-context')
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 876 ~ .on ~ this.contextmenu contextmenu',
            menu
          )
        })
        .on('contextmenuEdge', (target) => {
          this.$refs.menuNodeDauNoi && this.$refs.menuNodeDauNoi.hideContextMenu()
          this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
          this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

          console.log('🚀 ~ file: GraphVizControl.vue ~ line 871 ~ .on ~ target', target)
          const svgRect = svgDom.getBoundingClientRect()
          this.$refs.menuEdgeDauNoi.showMenu(target.e, target.node)
          var menu = document.getElementById('menuEdgeDauNoi')
          menu.style.left = `${this.point.x - svgRect.x}px`
          menu.style.top = `${this.point.y - svgRect.y}px`
          menu.position = 'fixed'
          menu.classList.add('menu-context')
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 876 ~ .on ~ this.contextmenu contextmenu',
            menu
          )
        })
        .on('contextmenuCluster', (target) => {
          this.$refs.menuNodeDauNoi && this.$refs.menuNodeDauNoi.hideContextMenu()
          this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
          this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

          console.log('🚀 ~ file: GraphVizControl.vue ~ line 871 ~ .on ~ target', target)
          const svgRect = svgDom.getBoundingClientRect()
          this.$refs.menuEdgeDauNoi.showMenu(target.e, target.node)
          var menu = document.getElementById('menuClusterDauNoi')
          menu.style.left = `${this.point.x - svgRect.x}px`
          menu.style.top = `${this.point.y - svgRect.y}px`
          menu.position = 'fixed'
          menu.classList.add('menu-context')
          console.log(
            '🚀 ~ file: GraphVizControl.vue ~ line 876 ~ .on ~ this.contextmenu contextmenu',
            menu
          )
        })
      document.oncontextmenu = (event) => {
        console.log('document.oncontextmenu')
      }
      document.onmousedown = (event) => {
        console.log('document.onmousedown')
      }
    },
    handleRenderGraphReady () {
      console.log('handleRenderGraphReady')
      this.svg = this.div.select('svg')
      this.graph0 = this.svg.select('g')
      this.dotGraph = this.prelDotGraph
      this.addEventHandlers()
      this.initContextMenu()
      this.rendering = false

      if (!this.renderGraphReady) {
        this.renderGraphReady = true
        // this.setZoomScale(1, true)
        this.graphviz.transition(() =>
          d3_transition().duration(this.transitionDuration * 1000)
        )
        this.$emit('onInitialized')
      }
      if (this.pendingUpdate) {
        this.pendingUpdate = false
        this.renderGraph(this.dataGraphviz)
      }
      if (this.KETCUOI.length > 0) {
        this.grid_selectedRowChanged(this.KETCUOI[0])
      }
      this.setState({ busy: false })
    },
    handleZoomOutMapButtonClick () {
      let viewBox = this.svg.attr('viewBox').split(' ')
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 2021 ~ handleZoomOutMapButtonClick ~ viewBox',
        viewBox
      )
      let bbox = this.graph0.node().getBBox()
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 2023 ~ handleZoomOutMapButtonClick ~ bbox',
        bbox
      )
      let xRatio = viewBox[2] / bbox.width
      let yRatio = viewBox[3] / bbox.height
      let scale = Math.min(xRatio, yRatio)
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 2025 ~ handleZoomOutMapButtonClick ~ scale',
        scale
      )
      this.setZoomScale(scale, true)
    },
    handleDotLayoutReady () {
      console.log('handleDotLayoutReady')
      let [, , width, height] = this.graphviz.data().attributes.viewBox.split(' ')
      this.originalViewBox = { width, height }
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 868 ~ handleDotLayoutReady ~ this.originalViewBox ',
        this.originalViewBox
      )
      this.setState({ busy: false })
    },
    handleRenderStaged () {
      console.log('handleRenderStaged')
      if (this.renderGraphReady) {
        this.markSelectedComponents(this.selectedComponents)
      }
    },
    markSelectedComponents (components, extendSelection = false) {
      let scale = (this.graph0.node().getCTM().a * 3) / 4
      let dashLength = Math.max(4 / scale, 2)
      let dashWidth = Math.max(4 / scale, 2)
      let rectNodes = []
      let titles = []
      const self = this
      console.log(
        '🚀 ~ file: GraphVizControl.vue ~ line 267 ~ markSelectedComponents ~ components',
        components
      )
      components.each(function (d, i) {
        let component = d3_select(this)
        component.classed('selected', true)
      })
      if (extendSelection) {
        this.selectRects = d3_selectAll(this.selectRects.nodes().concat(rectNodes))
        this.selectNames = this.selectNames.concat(titles)
      } else {
        this.selectRects = d3_selectAll(rectNodes)
        this.selectNames = titles
      }
    }
  }
}
</script>
<style>
.graph-oneBSS .selected polygon,
.graph-oneBSS .selected path,
.graph-oneBSS .selected ellipse {
  stroke: red !important;
  stroke-width: 2px;
}

.graph-oneBSS .selectedKC polygon {
  stroke: red !important;
  stroke-width: 3px;
}
</style>
