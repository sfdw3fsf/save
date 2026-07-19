<template>
<div>
    <div class="list-actions-top">
      <ul class="list">
          <li>
              <a href="javascript:void(0)" @click="loadData()">
                  <span class="icon one-reload"></span>Nạp lại
              </a>
          </li>
          <li>
              <a href="javascript:void(0)" @click="dauNoiDoHoa()">
                  <span class="icon one-daunoi2"></span>Giao diện đồ họa
              </a>
          </li>
      </ul>
    </div>
    <div id="target" class="default-splitter">
      <ejs-splitter orientation='Vertical'   separatorSize='1' id='splitterDNN' ref='splitterDNN' width='100%' height='auto'>
        <e-panes>
            <e-pane content='#content11' size ='200px'></e-pane>
            <!-- <e-pane content='#content22' size ='300px'></e-pane> -->
        </e-panes>
      </ejs-splitter>
    </div>

    <div id="content11" style="display: none; padding : 10px;">
        <div class="sitemap-chart-canvas relative" style="height: auto;">
          <script src="https://unpkg.com/@hpcc-js/wasm@1.20.1/dist/index.min.js" type="javascript/worker"></script>
          <div class="graph-container" style="width : 100%;  height: 100%;">
            <div class="graph-oneBSS" :id="graphID"/>
            <vue-simple-context-menu
                elementId="menuNodeDauNoi2"
                :options="getoptionsNodeMenu"
                ref="menuNodeDauNoi2"
                @option-clicked="menuNode_Clicked"
            />
          </div>
        </div>
    </div>
    <div id="content22" style="display: none; padding : 10px;">
          <!-- <a href="javascript:void(0)" class="tn-primary -ap icon-close" @click="removeDauNoi()"></a> -->
          <div class="pull-right">
            <button class="btn btn-main pad5 lh16" @click="removeDauNoi">
                <span class="-ap icon-close"></span>
            </button>
          </div>
          <Split  v-show="spcMain.PanelVisibility===2" :gutterSize="15">
            <SplitArea :size="20" :minSize="100">
              <div class="box-col box-form" id="boxLeft">
                <div class="legend-title">Kết cuối A</div>
                <div class="select-custom">
                  <select v-model="cboNguon" name="" class="form-control">
                    <option :key="item.ID" v-for="item in this.dsNguon" :value="item.ID"> {{item.MOTA}} </option>
                  </select>
                </div>
                <div class="legend-title mart20">Mặt</div>
                <div class="input-icon-left">
                  <div class="select-custom">
                    <ejs-dropdowntree id="thietbiNTree"
                                :key="1"
                                ref="thietbiNTree"
                                :readonly="true"
                                :showClearButton="false"
                                :value="cboThietBiNguon"
                                v-on:select='this.onchangeThietBiN'
                                :fields='this.fieldsThietBiN'
                                cssClass="dropdowntree-icons">
                    </ejs-dropdowntree>
                  </div>
                </div>
              </div>
              <div class="legend-title mart20">Vị trí còn rỗi</div>
              <div class="box-move-select-table">
                <div class="box-col" style="width: calc(100% - 10px);">
                  <div class="table-content" style="height:300px; overflow: scroll; border: 1px solid lightgray;">
                    <table class="table-result table-gachle">
                      <tbody :key="item.ID" v-for="item in this.dsViTriNguon">
                        <tr>
                          <td class="w20 center">
                            <div class="check-action">
                              <input v-model="vitriNChecked" type="checkbox" class="check" :value="item.ID">
                              <span class="name"></span>
                            </div>
                          </td>
                          <td>{{item.ID}}</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <button :disabled="vitriNChecked.length===0 || vitriDChecked.length===0" class="btn btn-main fullw" @click="btnDauNoi">Đấu nối</button>
            </SplitArea>
            <SplitArea :size="60" :minSize="100">
              <div class="table-content">
                  <DataGrid
                    :key="3"
                    ref='grid' :dataSource="dsDauNoiCap" panelDataHeight="440px"
                    :selectionSettings='selectionOptions'
                    :allowSorting='false' :enableHover='true'
                    :allowFiltering='false'
                    :allowPaging='false' :enablePagingServer="false"
                    @rowSelected="this.dauNoiCapRowSelected" @rowDeselected="this.dauNoiCapRowDeselected"
                    :enabledSelectFirstRow="false"
                    :showColumnCheckbox="true" dataKeyField="DAUNOI_ID"
                    :commands="[
                      {name: 'EDIT', cssClass: 'btn-primary -ap icon-close', width: 20}
                    ]"
                    :commandColumn="{headerText: 'Gỡ đấu nối', width: 65, firstColumn: false, show: true}"
                    @commandClicked="grid_onCommandClicked"
                    v-bind:columns="[
                      {headerText:'Vị trí A', textAlign:'Center',  fieldName: 'VITRI_A', headerAlign: 'Center' , width : 70},
                      {headerText:'Hướng kết nối',  textAlign:'Center',  fieldName: 'HUONG_DN', headerAlign: 'Center' , width : 70 },
                      {headerText:'Vị trí B',textAlign:'Center',  fieldName: 'VITRI_B', headerAlign: 'Center' , width : 70}
                    ]"
                ></DataGrid>
                <div class="info-row mart15">
                  <button class="btn btn-main" @click="btnGoDauNoi">Gỡ đấu nối</button>
                </div>
              </div>
            </SplitArea>
            <SplitArea :size="20" :minSize="100">
              <div class="box-col box-form" id="boxLeft">
                <div class="legend-title">Kết cuối B</div>
                <div class="select-custom">
                  <select v-model="cboDich" name="" class="form-control">
                    <option :key="item.ID" v-for="item in this.dsDich" :value="item.ID"> {{item.MOTA}} </option>
                  </select>
                </div>
                <div class="legend-title mart20">Mặt</div>
                <div class="input-icon-left">
                  <div class="select-custom">
                    <ejs-dropdowntree id="thietbiDTree"
                                :key="1"
                                ref="thietbiDTree"
                                :readonly="true"
                                :value="cboThietBiDich"
                                :showClearButton="false"
                                v-on:select='this.onchangeThietBiD'
                                :fields='this.fieldsThietBiD'
                                cssClass="dropdowntree-icons">
                    </ejs-dropdowntree>
                  </div>
                </div>
              </div>
              <div class="legend-title mart20">Vị trí còn rỗi</div>
              <div class="box-move-select-table">
                <div class="box-col" style="width: calc(100% - 10px);">
                  <div class="table-content" style="height: 300px; overflow: scroll; border: 1px solid lightgray;">
                    <table class="table-result table-gachle">
                      <tbody :key="item.ID" v-for="item in this.dsViTriDich">
                        <tr>
                          <td class="w20 center">
                            <div class="check-action">
                              <input v-model="vitriDChecked" type="checkbox" class="check" :value="item.ID">
                              <span class="name"></span>
                            </div>
                          </td>
                          <td>{{item.ID}}</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <button :disabled="vitriNChecked.length===0 || vitriDChecked.length===0" class="btn btn-main fullw" @click="btnDauNoi">Đấu nối</button>
            </SplitArea>
          </Split>
    </div>
  </div>
</template>

<script>
import DotGraph from './dot'
import {graphviz} from 'd3-graphviz'
import {select as d3_select, selectAll as d3_selectAll, pointer as d3_pointer} from 'd3-selection'
import { transition as d3_transition} from 'd3-transition'
import { dispatch as d3_dispatch} from 'd3-dispatch'
import { zoomIdentity as d3_zoomIdentity, zoomTransform as d3_zoomTransform} from 'd3-zoom'
import VueSimpleContextMenu from 'vue-simple-context-menu'
import ModalDauNoiThueBao from '../../TraCuuThueBaoChuaNhapCap/Modal/DauNoiThueBao'
import EventBus from '@/utils/eventBus'

export default {
  props: {
    graphID: String,
    defaultEdgeAttributes: {
      type: Object,
      default: null
    },
    transitionDuration:
    {
      type: Number,
      default: 1
    },
    fit:
    {
      type: Boolean,
      default: true
    },
    dotSrc:
    {
      type: String,
      default: `digraph G
      {
        a -> b;
      }`
    }
  },
  components: {'vue-simple-context-menu': VueSimpleContextMenu, ModalDauNoiThueBao},
  computed: {
    getSizeAreaL () {
      return this.spcMain.PanelVisibility === 1 ? 100 : 30
    },
    getSizeAreaR () {
      return this.spcMain.PanelVisibility === 1 ? 0 : 70
    },
    getbtnThemKC_Caption () { return this.btnThemKC_Caption },
    getoptionsNodeMenu () {
      if (this.selectedComponents === null) return []
      var menu = [
        {
          name: '<span class="icon text-main f20 one-daucuoi"></span> Đấu nối từ',
          slug: 'node_ket_noi_tu'
        },
        {
          name: '<span class="icon text-main f20 one-daucuoi"></span> Đấu nối đến',
          slug: 'node_ket_noi_den'
        },
        {
          name: '<span class="icon text-main f20 one-daochieu"></span> Hủy tạo đấu nối',
          slug: 'node_huy_tao_ket_noi'
        }
      ]
      var menu2 = []
      try {
        var id = this.selectedComponents.node() && this.selectedComponents.node().id
        if (id === null) return []
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 112 ~ getoptionsNodeMenu ~ id', id)
        if (this.startNode === null) {
          menu2.push(menu[0])
          console.log('🚀 ~ file: GraphVizControl2.vue ~ line 299 ~ getoptionsNodeMenu ~ menu2', menu2)
        } else {
          if (this.endNode === null) {
            menu2.push(menu[1])
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 114 ~ getoptionsNodeMenu ~ error', error)
      }
      return menu2
    }
  },
  data () {
    return {
      isModified: false,
      spcMain: {PanelVisibility: 1},
      selectionOptions: { type: 'Multiple', enableSimpleMultiRowSelection: true },
      vitriNChecked: [],
      vitriDChecked: [],
      dsViTriNguon: [],
      dsViTriDich: [],
      dsDauNoiCap: [],
      cboNguon: null,
      dsNguon: [],
      cboDich: null,
      dsDich: [],
      cboThietBiNguon: [],
      dsThietBiNguon: [],
      cboThietBiDich: [],
      dsThietBiDich: [],
      fieldsThietBiN: {},
      fieldsThietBiD: {},
      vnguon_id: -1,
      vdich_id: -1,
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
    },
    'cboNguon': async function (value) {
      try {
        if (value) {
          if (value === null) {
            this.cboThietBiNguon = []
            this.dsThietBiNguon = []
            this.dsViTriNguon = []
          } else {
            var ten_nguon, ten_tbi_nguon
            var nguon = this.cboNguon.split('_')
            if (nguon[0].toString() === 'TRAM') {
              ten_nguon = 'Trạm nguồn'
              ten_tbi_nguon = 'Thiết bị nguồn'
            }
            if (nguon[0].toString() === 'KETCUOI') {
              ten_nguon = 'Kết cuối nguồn'
              ten_tbi_nguon = 'Mặt'
            }
            var data = {
              VMA_LOAITBI_CHA: nguon[0].toString(),
              VTHIETBI_CHA_ID: parseInt(nguon[1]),
              VDSLOAITBI_ID: '18,23'
            }
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 461 ~ data', data)
            var rs = await this.$root.context.post(
              '/web-cabman/ban-do-mang-cap/sp_lay_ds_cay_thietbi_tongdai', data
            )
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 464 ~ rs cboNguon', rs)
            this.dsThietBiNguon = rs.data
            let treeThietBi = this.createTreeThietBi(this.dsThietBiNguon)
            this.fieldsThietBiN = { dataSource: treeThietBi, value: 'KEY', text: 'TEN_TB', parentValue: 'PKEY', hasChildren: 'hasChild', imageUrl: 'image'}
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 474 ~ this.cboThietBiNguon', this.cboThietBiNguon)

            if (this.dsThietBiNguon.length > 0) {
              if (this.cboNguon.split('_')[0] === 'KETCUOI') {
                this.cboThietBiNguon = [this.dsThietBiNguon[1].KEY]
              } else {
                if (this.cboNguon.split('_')[0] === 'TRAM') {
                  this.dsThietBiNguon.forEach(dr => {
                    if (dr['CO_PORT'].toString() === '1') {
                      this.cboThietBiNguon = dr.KEY
                    }
                  })
                }
              }
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 468 ~ error', error)
      }
    },
    'cboThietBiNguon': async function (value) {
      try {
        if (value.length > 0) {
          console.log('🚀 ~ file: GraphVizControl2.vue ~ line 498 ~ value', value)
          var nguon_id = value[0]
          if (nguon_id === null || nguon_id === -1 || nguon_id === 0) {

          } else {
            var nguon = nguon_id.split('#')
            var VPORTPLUG = nguon.length >= 4 ? nguon[3] : 0
            var data = {
              VMAT: nguon[0],
              VKETCUOI_ID: nguon[1],
              VMAT_N: nguon[2],
              VPORTPLUG: VPORTPLUG
            }
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 514 ~ data', data)
            var rs = await this.$root.context.post(
              '/web-cabman/ban-do-mang-cap/sp_lay_ds_vitri_nguon', data
            )
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 518 ~ rs', rs)
            this.dsViTriNguon = rs.data
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 499 ~ error', error)
      }
    },
    'cboDich': async function (value) {
      try {
        if (value) {
          if (value === null) {
            this.cboThietBiDich = []
            this.dsThietBiDich = []
            this.dsViTriDich = []
          } else {
            var ten_dich, ten_tbi_dich
            var dich = this.cboDich.split('_')
            if (dich[0].toString() === 'TRAM') {
              ten_dich = 'Trạm đích'
              ten_tbi_dich = 'Thiết bị đích'
            }
            if (dich[0].toString() === 'KETCUOI') {
              ten_dich = 'Kết cuối nguồn'
              ten_tbi_dich = 'Mặt'
            }
            var data = {
              VMA_LOAITBI_CHA: dich[0].toString(),
              VTHIETBI_CHA_ID: parseInt(dich[1]),
              VDSLOAITBI_ID: '18,23,43'
            }
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 461 ~ data', data)
            var rs = await this.$root.context.post(
              '/web-cabman/ban-do-mang-cap/sp_lay_ds_cay_thietbi_tongdai', data
            )
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 464 ~ rs cboDich', rs)
            this.dsThietBiDich = rs.data
            let treeThietBi = this.createTreeThietBi(this.dsThietBiDich)
            this.fieldsThietBiD = { dataSource: treeThietBi, value: 'KEY', text: 'TEN_TB', parentValue: 'PKEY', hasChildren: 'hasChild', imageUrl: 'image'}

            if (this.dsThietBiDich.length > 0) {
              if (this.cboDich.split('_')[0] === 'KETCUOI') {
                this.cboThietBiDich = [this.dsThietBiDich[0].KEY]
              } else {
                if (this.cboDich.split('_')[0] === 'TRAM') {
                  this.dsThietBiDich.forEach(dr => {
                    if (dr['CO_PORT'].toString() === '1') {
                      this.cboThietBiDich = dr.KEY
                    }
                  })
                }
              }
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 468 ~ error', error)
      }
    },
    'cboThietBiDich': async function (value) {
      try {
        if (value.length > 0) {
          console.log('🚀 ~ file: GraphVizControl2.vue ~ line 498 ~ value', value)
          var dich_id = value[0]
          if (dich_id === null || dich_id === -1 || dich_id === 0) {

          } else {
            var dich = dich_id.split('#')
            var data = {
              VMAT: dich[0],
              VKETCUOI_ID: dich[1],
              VMAT_N: dich[2]
            }
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 514 ~ data', data)
            var rs = await this.$root.context.post(
              '/web-cabman/ban-do-mang-cap/sp_lay_ds_vitri_dich', data
            )
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 518 ~ rs', rs)
            this.dsViTriDich = rs.data
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 499 ~ error', error)
      }
    }
  },
  methods: {
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
      }
    },
    createTreeThietBi (dsThietBi) {
      let tree = []
      let pkeyArray = dsThietBi.map(item => (item['PKEY']))
      let keyArray = dsThietBi.map(item => (item['KEY']))
      dsThietBi.forEach(item => {
        if (!keyArray.includes(item['PKEY'])) {
          delete item.PKEY
        }
        if (pkeyArray.includes(item['KEY'])) {
          tree.push({...item, image: '/static/icons/icon16x16/' + item.MA_LTB + '.png', hasChild: true, expanded: true})
        } else {
          tree.push({...item, image: '/static/icons/icon16x16/' + item.MA_LTB + '.png'})
        }
      })
      if (tree.length === 1) {
        tree[0].hasChild = true
        tree[0].expanded = true
        delete tree[0].PKEY
      }
      return tree
    },
    removeDauNoi () {
      // this.$refs.splitterDNN.removePane(1)
    },
    dauNoiDoHoa () {
      this.$emit('dauNoiDoHoa', this.node)
    },
    async btnGoDauNoi () {
      try {
        let items = this.$refs.grid.getSelectedRecords()
        console.log('🚀 ~ file: GraphVizControl2.vue:541 ~ btnGoDauNoi ~ items:', items)
        if (items.length === 0) return
        // var daunoi = this.dsDauNoiCap.filter(e => e.CHON === 1)
        var daunoi = items
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 873 ~ btnGoDauNoi ~ daunoi', daunoi)
        var result = await this.$confirm('Xác nhận gỡ bỏ ' + daunoi.length + ' kết nối ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        })
        if (result) {
          daunoi.forEach(async dn => {
            let daunoiID = dn.DAUNOI_ID.split('_')
            let ketqua = await this.XOA_DAU_NOI(daunoiID)
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 877 ~ btnGoDauNoi ~ ketqua', ketqua)
          })
          this.loadData()
          await this.NAPLAI_DAUNOI_CHITIET()
          this.isModified = true
        }
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl2.vue:561 ~ btnGoDauNoi ~ error:', error)
      }
    },
    async btnDauNoi () {
      const sortedvitriNChecked = this.vitriNChecked.sort(
        (objA, objB) => Number(objA) - Number(objB)
      )
      const sortedvitriDChecked = this.vitriDChecked.sort(
        (objA, objB) => Number(objA) - Number(objB)
      )
      // var nguon = this.vitriNChecked.sort()
      // var dich = this.vitriDChecked.sort()

      var nguon = sortedvitriNChecked
      console.log('🚀 ~ file: GraphVizControl2.vue ~ line 874 ~ btnDauNoi ~ vitriNChecked', nguon)
      var dich = sortedvitriDChecked
      console.log('🚀 ~ file: GraphVizControl2.vue ~ line 874 ~ btnDauNoi ~ vitriNChecked', dich)
      if (this.cboNguon === null) {
        await this.$alert('Chưa chọn thiết bị A', 'Thông báo',
          {
            confirmButtonText: 'OK',
            type: 'error'
          }
        )
        return
      }
      if (this.cboDich === null) {
        await this.$alert('Chưa chọn thiết bị B', 'Thông báo',
          {
            confirmButtonText: 'OK',
            type: 'error'
          }
        )
        return
      }
      var thietbi_n_id = this.cboNguon.split('_')[1]
      var thietbi_d_id = this.cboDich.split('_')[1]
      var loaitbi_n_id = this.cboNguon.split('_')[0] === 'KETCUOI' ? '25' : '-1'
      var loaitbi_d_id = this.cboDich.split('_')[0] === 'KETCUOI' ? '25' : '-1'
      var mat_n = this.cboThietBiNguon[0].split('#')[2]
      var mat_d = this.cboThietBiDich[0].split('#')[2]
      if (loaitbi_n_id === '-1') {
        await this.$alert('Hệ thống chưa hỗ trợ loại thiết bị ' + this.cboNguon.split('_')[0], 'Thông báo',
          {
            confirmButtonText: 'OK',
            type: 'error'
          }
        )
        return
      }
      if (loaitbi_d_id === '-1') {
        await this.$alert('Hệ thống chưa hỗ trợ loại thiết bị ' + this.cboDich.split('_')[0], 'Thông báo',
          {
            confirmButtonText: 'OK',
            type: 'error'
          }
        )
        return
      }
      var result = await this.DAU_NOI_DN(thietbi_n_id, thietbi_d_id, loaitbi_n_id, loaitbi_d_id, mat_n, mat_d, nguon, dich)
      console.log('🚀 ~ file: GraphVizControl2.vue ~ line 599 ~ btnDauNoi ~ thietbi_n_id, thietbi_d_id, loaitbi_n_id, loaitbi_d_id, mat_n, mat_d, nguon, dich', thietbi_n_id, thietbi_d_id, loaitbi_n_id, loaitbi_d_id, mat_n, mat_d, nguon, dich)
      console.log('🚀 ~ file: GraphVizControl2.vue ~ line 598 ~ btnDauNoi ~ result', result)
      if (result.success === 0) {
        await this.$alert('Đấu nối thất bại: ' + result.count, 'Thông báo',
          {
            confirmButtonText: 'Đóng',
            type: 'error'
          }
        )
        return
      } else {
        if (result.error !== 0) {
          await this.$alert('Đấu nối thành công ' + result.success + '/' + result.count + '. Đấu nối thất bại ' + result.error, 'Thông báo',
            {
              confirmButtonText: 'Đóng',
              type: 'error'
            }
          )
        } else {
          await this.$alert('Đấu nối thành công ' + result.success + '/' + result.count + ' kết nối', 'Thông báo',
            {
              confirmButtonText: 'Đóng',
              type: 'success'
            }
          )
        }
      }
      this.loadData()
      await this.NAPLAI_DAUNOI_CHITIET()
      this.isModified = true
    },
    async DAU_NOI_DN (thietbi_n_id, thietbi_d_id, loaitbi_n_id, loaitbi_d_id, mat_n, mat_d, nguon, dich) {
      var vcount = nguon.length <= dich.length ? nguon.length : dich.length
      var vsuccess = 0
      var verror = 0
      for (var i = 0; i < vcount; i++) {
        var startPort = `${thietbi_n_id}_${nguon[i]}_${mat_n}_${loaitbi_n_id}`
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 901 ~ DAU_NOI_DN ~ startPort', startPort)
        var endPort = `${thietbi_d_id}_${dich[i]}_${mat_d}_${loaitbi_d_id}`
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 903 ~ DAU_NOI_DN ~ endPort', endPort)
        let ketqua = await this.TAO_KETNOI_DN_V2(startPort.split('_'), endPort.split('_'))
        if (ketqua) {
          vsuccess = vsuccess + 1
        } else {
          verror = verror + 1
        }
      }
      // console.log('🚀 ~ file: GraphVizControl2.vue ~ line 908 ~ DAU_NOI_DN ~ vsuccess', vsuccess)
      // console.log('🚀 ~ file: GraphVizControl2.vue ~ line 911 ~ DAU_NOI_DN ~ verror', verror)
      return { count: vcount, success: vsuccess, error: verror }
    },
    async grid_onCommandClicked (a, b) {
      try {
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 916 ~ grid_onCommandClicked ~ a,b', a, b)
        var result = await this.$confirm('Gỡ kết nối ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        })
        if (result) {
          var daunoiID = b.DAUNOI_ID
          var ketqua = await this.XOA_DAU_NOI(daunoiID.split('_'))
          if (ketqua) {
            this.loadData()
          }
        }
      } catch (error) {

      }
    },
    onchangeThietBiN () {
      let defaultObj = this.$refs.thietbiNTree.ej2Instances
      this.cboThietBiNguon = [defaultObj.value.toString()]
      console.log('🚀 ~ file: GraphVizControl2.vue ~ line 660 ~ onchangeThietBiN ~ this.cboThietBiNguon', this.cboThietBiNguon)
    },
    onchangeThietBiD () {
      let defaultObj = this.$refs.thietbiDTree.ej2Instances
      this.cboThietBiDich = [defaultObj.value.toString()]
    },
    onChangeDauNoiThueBao () {

    },
    onHideDauNoiThueBao () {
      this.loadData()
    },
    dauNoiCapRowSelected (args) {
      this.dsDauNoiCap[args.rowIndex].CHON = 1
      console.log('🚀 ~ file: GraphVizControl2.vue ~ line 1162 ~ dauNoiCapRowSelected ~ this.dsDauNoiCap', this.dsDauNoiCap)
    },
    dauNoiCapRowDeselected (args) {
      this.dsDauNoiCap[args.rowIndex].CHON = 0
      console.log('🚀 ~ file: GraphVizControl2.vue ~ line 1166 ~ dauNoiCapRowDeselected ~ this.dsDauNoiCap', this.dsDauNoiCap)
    },
    deleteSelectedComponents (component) {
      this.selectedComponents.style('display', 'none')
      let self = this
      var componentName = component
        .select('title')
        .text()
      if (component.attr('class') === 'node') {
        self.dotGraph.deleteNode(componentName)
      } else {
        self.dotGraph.deleteEdge(componentName)
      }
      this.unSelectComponents()
    },
    setNode (node) { this.node = node },
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
    async loadData () {
      try {
        this.setState({busy: true})

        this.startNode = null
        this.endNode = null
        this.selectedComponents = d3_selectAll(null)

        let ketcuoi_id = this.node.KETCUOI_ID.split('_')[1].toString()
        let rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/lay-gd-tong-quan' + '?ketcuoi_id=' + ketcuoi_id
        )
        let ds = rs.data
        console.log('🚀 ~ file: index.vue ~ line 334 ~ dauNoiBenTrong ~ ds', ds)
        var sw = `digraph G\n`
        sw = sw + `{\n`
        sw = sw + ` graph [label=\"*Hướng dẫn: Click chuột phải vào kết cuối chọn 'Đấu nối từ' rồi Click kết cuối khác chọn 'Đấu nối đến'." labelloc=t fontsize=14 rankdir=LR nodesep=0.7 margin=10];\n`
        sw = sw + ` node [shape=record, style=\"rounded,filled\", fillcolor=\"#F7F7F7\", fontname=\"Arial\", fontsize=14];\n`

        var l = []
        var childs = []

        ds.dsCS1.forEach(dr => {
          var n = dr['LOAITBI_ID'] + '_' + dr['THIETBI_ID']
          var label = dr['THONGTIN'] === null ? '\"\"' : dr['THONGTIN']
          if (dr['THIETBI_ID'].toString() === ketcuoi_id) { sw = sw + `  n${n} [id="tb${n}", label=${label},height=${ds.dsCS1.length - 1}, fillcolor="#E7E6E5"];\r\n` } else {
            sw = sw + `  n${n} [id="tb${n}", label=${label}];\r\n`
            childs.push('n' + n)
          }
          l.push(n)
        })

        var tmp = ds.dsCS1.filter(e => e.THIETBI_ID.toString() === ketcuoi_id)
        if (tmp.length > 0) {
          var main_kc = tmp[0]
          for (let index = 0; index < childs.length; index++) {
            const child = childs[index]
            sw = sw + `  n${main_kc['LOAITBI_ID']}_${main_kc['THIETBI_ID']}->${child} [style=invis];\r\n`
          }
        }
        if (childs.length > 0) { sw = sw + '  { rank = same; ' + childs.join(';') + ' }' }
        ds.dsCS2.forEach(dr => {
          var n = dr['LOAITBI_N_ID'] + '_' + dr['THIETBI_N_ID']
          if (!l.includes(n)) {
            sw = sw + `  n${n} [id="tb${n}", label=<<b>${dr['TEN_TBI_N'].toString()}</b>>];\r\n`
            l.push(n)
          }
          var d = dr['LOAITBI_D_ID'] + '_' + dr['THIETBI_D_ID']
          if (!l.includes(d)) {
            sw = sw + ` n${d} [id="tb${d}", label=<<b>${dr['TEN_TBI_D'].toString()}</b>>];\r\n`
            l.Add(d)
          }
          sw = sw + `  n${dr['LOAITBI_N_ID']}_${dr['THIETBI_N_ID']}->n${dr['LOAITBI_D_ID']}_${dr['THIETBI_D_ID']} [id="dn${dr['LOAITBI_N_ID']}_${dr['THIETBI_N_ID']}_${dr['LOAITBI_D_ID']}_${dr['THIETBI_D_ID']}"];\r\n`
        })
        sw = sw + '}\n'
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 899 ~ loadData ~ sw', sw)
        this.setState({busy: false})
        this.setDataGraphviz(sw)
        await this.NAPLAI_DAUNOI_CHITIET()
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 801 ~ loadData ~ error', error)
        this.setDataGraphviz(null)
        this.setState({busy: false})
        if (error.response && error.response.status === 400) {
          await this.$alert(error.response.data.message_detail, 'Thông báo', {
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
      }
    },
    setDataGraphviz (data) {
      console.log('🚀 ~ file: GraphVizControl2.vue ~ line 915 ~ setDataGraphviz ~ data', data)
      this.dataGraphviz = data
    },
    createGraph (renderGraph) {
      console.log('🚀 ~ file: GraphVizControl2.vue ~ line 918 ~ createGraph ~ renderGraph', renderGraph)
      this.graphviz = graphviz(`#${this.graphID}`, {useWorker: true, totalMemory: this.totalMemory})
        .onerror(() => this.handleError())
        .on('initEnd', () => this.renderGraph(renderGraph))
    },
    unFitGraph () {
      let width = this.div.node().parentElement.clientWidth
      let height = this.div.node().parentElement.clientHeight
      this.svg.attr('viewBox', `0 0 ${(width * 3) / 4} ${(height * 3) / 4}`)
    },
    fitGraph () {
      this.svg.attr('viewBox', `0 0 ${this.originalViewBox.width} ${this.originalViewBox.height}`
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
      this.graphviz.zoomSelection().call(this.graphviz.zoomBehavior().transform, transform)
    },
    setState (state) {
      this.state = state
    },
    handleError (errorMessage) {
      if (!errorMessage) return
      let line = errorMessage.replace(/.*error in line ([0-9]*) .*\n/, '$1')
      this.$emit('onError', {message: errorMessage, line: line})
      this.rendering = false
      this.setState({busy: false})
      if (this.pendingUpdate) {
        this.pendingUpdate = false
        this.$forceUpdate()
      }
    },
    renderGraph (dataGraphviz) {
      let fit = this.fit
      if (dataGraphviz === null || dataGraphviz.length === 0) {
        console.log('this.dataGraphviz.length === 0')
        this.svg.remove()
        this.svg = d3_select(null)
        this.$emit('onError', null)
        this.renderGraphReady = false
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
      // console.log('🚀 ~ file: GraphVizControl.vue ~ line 162 ~ renderGraph ~ this.prevDataGraphviz', this.prevDataGraphviz)
      try {
        this.prelDotGraph = new DotGraph(dataGraphviz)
        this.$emit('onError', null)
        // console.log('🚀 ~ file: DotLanguage.vue ~ line 251 ~ renderGraph ~ this.prelDotGraph', (this.prelDotGraph.ast.children))
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 165 ~ renderGraph ~ error', error)
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
      this.graphviz.fit(fit).dot(dataGraphviz, () => this.handleDotLayoutReady()).on('renderEnd', () => this.handleRenderStaged()).render(() => this.handleRenderGraphReady())
    },
    addEventHandlers () {
      let self = this
      var nodes = this.svg.selectAll('g.node')
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 196 ~ addEventHandlers ~ nodes', nodes)
      var edges = this.svg.selectAll('g.edge')
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 199 ~ addEventHandlers ~ edges', edges)

      // var clusters = this.svg.selectAll('g[id*="kc_"]')
      var clusters = this.svg.selectAll('g[id*="kc_"].cluster')
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 1225 ~ addEventHandlers ~ clusters', clusters)

      this.div.on('click', (d) => this.handleClickDiv())
      this.div.on('contextmenu', (d) => this.handleRightClickDiv(d))

      nodes.on('click mousedown', (d, i) => this.handleClickNode(d, i))
      nodes.on('contextmenu', (d, i) => this.handleRightClickNode(d, i))
      nodes.on('dblclick', (d, i) => this.handleDblClickNode(d, i))

      edges.on('click mousedown', (d, i) => this.handleClickEdge(d, i))
      // edges.on('contextmenu', (d, i) => this.handleRightClickEdge(d, i))

      // clusters.on('click', (d, i) => this.handleClickCluster(d, i))
      // clusters.on('contextmenu', (d, i) => this.handleRightCluster(d, i))
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
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 636 ~ handleRightClickEdge ~ d', d)
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
        this.dispatch.call('contextmenuEdge', this, { e: e, node: d3_select(`#${i.attributes.id}`) })
      }, 10)
    },
    async handleClickEdge (d, i) {
      console.log('handleClickEdge', `#${i.attributes.id}`)
      document.activeElement.blur()
      event.preventDefault()
      event.stopPropagation()

      var nodes = this.svg.selectAll('g.node')
      var edges = this.svg.selectAll('g.edge')
      nodes.classed('selected', false)
      edges.classed('selected', false)

      var id = i.attributes.id
      if (!id.startsWith('dn') && !id.startsWith('dv') && !id.startsWith('dr')) {
        return
      }
      let extendSelection = false
      this.selectComponents(d3_select(`#${i.attributes.id}`), extendSelection)
      try {
        var link = i.attributes.id.substring(2).split('_')
        await this.HIENTHI_DAUNOI_CHITIET(link[0], link[1], link[2], link[3])
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 980 ~ handleClickEdge ~ error', error)
      }
    },
    handleClickDiv () {
      document.activeElement.blur()
      event.preventDefault()
      event.stopPropagation()
      this.$refs.menuNodeDauNoi2 && this.$refs.menuNodeDauNoi2.hideContextMenu()
      // this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
      // this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

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
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 789 ~ DAOCHIEU_KETNOI ~ data', data)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_daochieu_ketnoi', data
        )
        if (rs.data.length > 0 && rs.data[0].KETQUA > 0) {
          return true
        } else { return false }
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 771 ~ DAOCHIEU_KETNOI ~ error', error.response)
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
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 712 ~ XOA_DAU_NOI ~ data', data)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_xoa_ketnoi', data
        )
        if (rs.data.length > 0 && rs.data[0].KETQUA > 0) {
          return true
        } else { return false }
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 699 ~ XOA_DAU_NOI ~ error', error)
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
        if (startPort.startsWith('tb') && endPort.startsWith('tb')) {
          startPort = startPort.substring(2)
          endPort = endPort.substring(2)
          if (startPort === endPort) {
            this.startNode = null
            this.endNode = null
            this.unSelectComponents()
            return
          } else {
            var sne = startPort.split('_')
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 1309 ~ DAU_NOI ~ sne', sne)
            var ene = endPort.split('_')
            console.log('🚀 ~ file: GraphVizControl2.vue ~ line 1311 ~ DAU_NOI ~ ene', ene)
            await this.HIENTHI_DAUNOI_CHITIET(sne[0], sne[1], ene[0], ene[1])
            this.startNode = null
            this.endNode = null
            this.unSelectComponents()
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 1315 ~ DAU_NOI ~ error', error)
        this.startNode = null
        this.endNode = null
      }
    },
    async NAPLAI_DAUNOI_CHITIET () {
      if (this.cboNguon === null || this.cboDich === null) {
        this.dsDauNoiCap = []
        return null
      }
      var thietbi_n_id = this.cboNguon.split('_')[1]
      var thietbi_d_id = this.cboDich.split('_')[1]

      var loaitbi_n_id = this.cboNguon.split('_')[0] === 'KETCUOI' ? '25' : '-1'
      var loaitbi_d_id = this.cboDich.split('_')[0] === 'KETCUOI' ? '25' : '-1'

      var mat_n = this.cboThietBiNguon[0].split('#')[2]
      var mat_d = this.cboThietBiDich[0].split('#')[2]

      await this.HIENTHI_DAUNOI_CHITIET(loaitbi_n_id, thietbi_n_id, loaitbi_d_id, thietbi_d_id)
    },
    async addPane () {
      await this.$refs.splitterDNN.$el.ej2_instances[0].addPane({
        size: '400px',
        content: '#content22',
        min: '300px'
      }, 1)
    },
    async HIENTHI_DAUNOI_CHITIET (loaitbi_n_id, thietbi_n_id, loaitbi_d_id, thietbi_d_id) {
      this.spcMain.PanelVisibility = 2
      var allPanes = this.$refs.splitterDNN.$el.ej2_instances[0].allPanes.length
      if (allPanes === 1) {
        this.addPane()
      }
      await this.NAP_DS_THIETBI(thietbi_n_id, thietbi_d_id)
      await this.NAP_DS_DAUNOI_DN()
    },
    async NAP_DS_THIETBI (kcn_id, kcd_id) {
      try {
        this.dsNguon = []
        this.dsDich = []
        this.cboNguon = null
        this.cboDich = null
        this.vitriNChecked = []
        this.vitriDChecked = []
        var data = {
          P_KETCUOI_ID: this.node.KETCUOI_ID.split('_')[1],
          P_KETCUOI_ID_OUT: null
        }
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 1333 ~ NAP_DS_THIETBI ~ data', data)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_lay_ds_ketcuoi_bentrong_kc', data
        )
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 1336 ~ NAP_DS_THIETBI ~ rs', rs)
        if (rs.data.length > 0) {
          this.dsNguon = rs.data
          this.cboNguon = 'KETCUOI_' + kcn_id

          this.dsDich = rs.data
          this.cboDich = 'KETCUOI_' + kcd_id
        }
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 1339 ~ NAP_DS_THIETBI ~ error', error)
      }
    },
    async NAP_DS_DAUNOI_DN () {
      try {
        var thietbi_a = this.cboNguon.split('_')
        var thietbi_b = this.cboDich.split('_')
        var data = {
          VLOAITBI_A_ID: 25,
          VTHIETBI_A_ID: thietbi_a[1],
          VLOAITBI_B_ID: 25,
          VTHIETBI_B_ID: thietbi_b[1]
        }
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 1580 ~ NAP_DS_DAUNOI_DN ~ data', data)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_lay_ds_daunoi_dn', data
        )
        console.log('🚀 ~ file: GraphVizControl2.vue ~ line 1584 ~ NAP_DS_DAUNOI_DN ~ rs', rs)
        this.dsDauNoiCap = rs.data
      } catch (error) {
        this.dsDauNoiCap = []
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
          DATA: sData,
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
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 738 ~ DAUNOI_CAP ~ data', data)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_daunoi_cap', data
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
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 268 ~ TAO_KETNOI_DN_V2 ~ data', data)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/sp_tao_ketnoi_dn', data
        )
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 271 ~ TAO_KETNOI_DN_V2 ~ rs', rs)
        if (rs.data.length > 0) {
          if (rs.data[0].KETQUA > 0) return true
          else return false
        } else return false
      } catch (error) {
        console.log('🚀 ~ file: GraphVizControl.vue ~ line 275 ~ TAO_KETNOI_DN_V2 ~ error', error)
        return false
      }
    },
    CHECK_PERMIT_AND_MESSAGE () {
      return true
    },
    handleRightClickNode (d, i) {
      document.activeElement.blur()
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 836 ~ handleRightClickNode ~ event', event)
      var e = event
      event.preventDefault()
      event.stopPropagation()
      this.unSelectComponents()
      this.graphviz.removeDrawnEdge()

      var id = i.attributes.id
      if (!id.startsWith('tb')) {
        return
      }

      var [x0, y0] = d3_pointer(event, this.graph0.node())
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 805 ~ handleRightClickNode ~ x0, y0', x0, y0)
      if (this.edgeIndex === null) {
        this.edgeIndex = d3_selectAll('.edge').size()
      } else {
        this.edgeIndex += 1
      }
      this.latestEdgeAttributes = Object.assign({}, this.defaultEdgeAttributes)
      this.latestEdgeAttributes.id = 'edge' + (this.edgeIndex + 1)
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 223 ~ handleRightClickNode ~ latestEdgeAttributes', this.latestEdgeAttributes)
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
        this.dispatch.call('contextmenu', this, { e: e, node: d3_select(`#${i.attributes.id}`) })
      }, 10)
    },
    handleClickNode (d, i) {
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 205 ~ handleClickNode ~ d, i', d, i)
      document.activeElement.blur()
      event.preventDefault()
      event.stopPropagation()

      var nodes = this.svg.selectAll('g.node')
      var edges = this.svg.selectAll('g.edge')
      edges.classed('selected', false)
      nodes.classed('selected', false)

      this.$refs.menuNodeDauNoi2 && this.$refs.menuNodeDauNoi2.hideContextMenu()
      // this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
      // this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

      var id = i.attributes.id
      if (!id.startsWith('tb')) {
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
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 216 ~ handleRightClickDiv ~ d', d)
      event.preventDefault()
      event.stopPropagation()
      this.$refs.menuNodeDauNoi2 && this.$refs.menuNodeDauNoi2.hideContextMenu()
      // this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
      // this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

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
        this.selectedComponents = d3_selectAll(this.selectedComponents.nodes().concat(components.nodes()))
      } else {
        this.unSelectComponents()
        this.selectedComponents = components
      }
      this.markSelectedComponents(components, extendSelection)
    },
    initContextMenu () {
      const svgDom = document.querySelector(`#${this.graphID} svg`)
      this.dispatch = d3_dispatch('start', 'contextmenu', 'contextmenuEdge', 'contextmenuCluster')
        .on('start', (event) => {
          this.point = { x: event.x, y: event.y }
        })
        .on('contextmenu', (target) => {
          this.$refs.menuNodeDauNoi2 && this.$refs.menuNodeDauNoi2.hideContextMenu()
          // this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
          // this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

          console.log('🚀 ~ file: GraphVizControl.vue ~ line 871 ~ .on ~ target', target)
          const svgRect = svgDom.getBoundingClientRect()
          this.$refs.menuNodeDauNoi2.showMenu(target.e, target.node)
          var menu = document.getElementById('menuNodeDauNoi2')
          menu.style.left = `${this.point.x - svgRect.x}px`
          menu.style.top = `${this.point.y - svgRect.y}px`
          menu.position = 'fixed'
          menu.classList.add('menu-context')
          console.log('🚀 ~ file: GraphVizControl.vue ~ line 876 ~ .on ~ this.contextmenu contextmenu', menu)
        })
        .on('contextmenuEdge', (target) => {
          this.$refs.menuNodeDauNoi2 && this.$refs.menuNodeDauNoi2.hideContextMenu()
          // this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
          // this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

          // console.log('🚀 ~ file: GraphVizControl.vue ~ line 871 ~ .on ~ target', target)
          // const svgRect = svgDom.getBoundingClientRect()
          // this.$refs.menuEdgeDauNoi.showMenu(target.e, target.node)
          // var menu = document.getElementById('menuEdgeDauNoi')
          // menu.style.left = `${this.point.x - svgRect.x}px`
          // menu.style.top = `${this.point.y - svgRect.y}px`
          // menu.position = 'fixed'
          // menu.classList.add('menu-context')
          // console.log('🚀 ~ file: GraphVizControl.vue ~ line 876 ~ .on ~ this.contextmenu contextmenu', menu)
        })
        .on('contextmenuCluster', (target) => {
          this.$refs.menuNodeDauNoi2 && this.$refs.menuNodeDauNoi2.hideContextMenu()
          // this.$refs.menuEdgeDauNoi && this.$refs.menuEdgeDauNoi.hideContextMenu()
          // this.$refs.menuClusterDauNoi && this.$refs.menuClusterDauNoi.hideContextMenu()

          // console.log('🚀 ~ file: GraphVizControl.vue ~ line 871 ~ .on ~ target', target)
          // const svgRect = svgDom.getBoundingClientRect()
          // this.$refs.menuEdgeDauNoi.showMenu(target.e, target.node)
          // var menu = document.getElementById('menuClusterDauNoi')
          // menu.style.left = `${this.point.x - svgRect.x}px`
          // menu.style.top = `${this.point.y - svgRect.y}px`
          // menu.position = 'fixed'
          // menu.classList.add('menu-context')
          // console.log('🚀 ~ file: GraphVizControl.vue ~ line 876 ~ .on ~ this.contextmenu contextmenu', menu)
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
        this.graphviz.transition(() => d3_transition().duration(this.transitionDuration * 1000))
        this.$emit('onInitialized')
      }
      this.setState({ busy: false })
      if (this.pendingUpdate) {
        this.pendingUpdate = false
        this.renderGraph(this.dataGraphviz)
      }
      if (this.KETCUOI.length > 0) {
        this.grid_selectedRowChanged(this.KETCUOI[0])
      }
    },
    handleDotLayoutReady () {
      let [, , width, height] = this.graphviz.data().attributes.viewBox.split(' ')
      this.originalViewBox = { width, height }
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 868 ~ handleDotLayoutReady ~ this.originalViewBox ', this.originalViewBox)
      this.setState({ busy: false })
    },
    handleRenderStaged () {
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
      console.log('🚀 ~ file: GraphVizControl.vue ~ line 267 ~ markSelectedComponents ~ components', components)
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
    },
    handleZoomOutMapButtonClick () {
      let viewBox = this.svg.attr('viewBox').split(' ')
      let bbox = this.graph0.node().getBBox()
      let xRatio = viewBox[2] / bbox.width
      let yRatio = viewBox[3] / bbox.height
      let scale = Math.min(xRatio, yRatio)
      this.setZoomScale(scale, true)
    }
  }
}
</script>
<style>
.graph-oneBSS .selected polygon,
.graph-oneBSS .selected path,
.graph-oneBSS .selected ellipse
{
  stroke: red !important;
  stroke-width: 2px;
}
.graph-oneBSS .selectedKC polygon
{
  stroke: red !important;
  stroke-width: 3px;
}
.graph-oneBSS {
  width: 100%;
  height: 885px;
}
.split .split
{
    padding: 0 0 !important;
    display: block;
}
</style>
