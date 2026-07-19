<template>
  <div>
    <script
      src="https://unpkg.com/@hpcc-js/wasm@1.20.1/dist/index.min.js"
      type="javascript/worker"
    ></script>
    <div class="graph-container" style="width: 100%;">
      <div class="graph-oneBSS" :id="graphID" />
    </div>
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
import {
  zoomIdentity as d3_zoomIdentity,
  zoomTransform as d3_zoomTransform
} from 'd3-zoom'
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
  },
  computed: {
  },
  data () {
    return {
      readonly: true,
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
    async loadData (sw) {
      try {
        this.startNode = null
        this.endNode = null
        this.selectedComponents = d3_selectAll(null)
        this.setDataGraphviz(sw)
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
      try {
        this.prelDotGraph = new DotGraph(dataGraphviz)
        this.$emit('onError', null)
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
      this.graphviz
        .fit(fit)
        .dot(dataGraphviz, () => this.handleDotLayoutReady())
        .on('renderEnd', () => this.handleRenderStaged())
        .on('end', () => this.handleEnd())
        .render(() => this.handleRenderGraphReady())
    },
    handleEnd () {
    },
    addEventHandlers () {
      var nodes = this.svg.selectAll('g.node')
      var edges = this.svg.selectAll('g.edge')

      var clusters = this.svg.selectAll('g[id*="kc_"].cluster')
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
    async handleDblClickNode (d, i) {
      document.activeElement.blur()
      event.preventDefault()
      event.stopPropagation()
      this.unSelectComponents()
      if (this.isDrawingEdge) {
      }
      this.isDrawingEdge = false
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
    CHECK_PERMIT_AND_MESSAGE () {
      return true
    },
    handleRightClickNode (d, i) {
      document.activeElement.blur()
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
      if (this.edgeIndex === null) {
        this.edgeIndex = d3_selectAll('.edge').size()
      } else {
        this.edgeIndex += 1
      }
      this.latestEdgeAttributes = Object.assign({}, this.defaultEdgeAttributes)
      this.latestEdgeAttributes.id = 'edge' + (this.edgeIndex + 1)
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
    handleRenderGraphReady () {
      this.svg = this.div.select('svg')
      this.graph0 = this.svg.select('g')
      this.dotGraph = this.prelDotGraph
      this.addEventHandlers()
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
      let bbox = this.graph0.node().getBBox()
      let xRatio = viewBox[2] / bbox.width
      let yRatio = viewBox[3] / bbox.height
      let scale = Math.min(xRatio, yRatio)
      this.setZoomScale(scale, true)
    },
    handleDotLayoutReady () {
      let [, , width, height] = this.graphviz.data().attributes.viewBox.split(' ')
      this.originalViewBox = { width, height }
      this.setState({ busy: false })
    },
    handleRenderStaged () {
      if (this.renderGraphReady) {
        this.markSelectedComponents(this.selectedComponents)
      }
    },
    markSelectedComponents (components, extendSelection = false) {
      let scale = (this.graph0.node().getCTM().a * 3) / 4
      let rectNodes = []
      let titles = []
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
</style>
