<template>
   <b-modal
    id="baoHanhThietBi"
    title="Mô hình bảo hành thiết bị"
    modal-class="bao-hanh-thiet-bi"
    header-class="bao-hanh-thiet-bi-header"
    content-class="bao-hanh-thiet-bi-content"
    body-class="bao-hanh-thiet-bi-body"
    size="xl"
    hide-footer
  >
     <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#popupTCTTTB" data-toggle="modal">
                    <span class="icon nc-icon-glyph files_chart-bar"></span> Trạng thái
                </a>
            </li>
            <li>
                <a href="#">
                    <span class="icon one-save"></span> Ghi lại
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="control-section">
            <div style="width:100%">
                <div class="sb-mobile-palette-bar">
                <div id="palette-icon" role="button"  class="e-ddb-icons1 e-toggle-palette"></div>
                </div>
                <div id="palette-space" class="sb-mobile-palette">
                <ejs-symbolpalette id="symbolpalette" :expandMode='expandMode' :palettes='palettes' :width='palettewidth' :height='paletteheight' :getNodeDefaults='palettegetNodeDefaults' :getSymbolInfo='getSymbolInfo' :symbolMargin='symbolMargin' :symbolHeight='symbolHeight'
                :symbolWidth='symbolWidth'></ejs-symbolpalette>
                </div>

                <div id="diagram-space" class="sb-mobile-diagram">
                <ejs-diagram style='display:block' ref="diagramObject" id="diagram" :width='width' :height='height' :nodes='nodes' :connectors='connectors' :getNodeDefaults='getNodeDefaults' :getConnectorDefaults='getConnectorDefaults' :dragEnter='dragEnter'
                                :snapSettings='snapSettings' :created='created'></ejs-diagram>
                </div>
            </div>
        </div>
    </div>

    <div class="modal" id="popupTCTTTB">
        <div class="modal-dialog" style="width: 100%; max-width:600px;">
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title">Chọn trạng thái bảo hành</div>
                    <div class="close -ap icon-close" data-dismiss="modal">
                    </div>
                </div>
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="#">
                                <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chọn
                            </a>
                        </li>
                
                    </ul>
                
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="info-row">
                            <div class="key w30 nowrap">Trạng thái</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
  </b-modal>
</template>

<style scoped>
  .e-ddb-icons {
    font-family: 'e-ddb-icons';
    speak: none;
    font-size: 16px;
    font-style: normal;
    font-weight: normal;
    font-variant: normal;
    text-transform: none;
    line-height: 1;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }
  
  .e-ddb-icons1 {
    font-family: 'e-ddb-icons1';
    speak: none;
    font-size: 16px;
    font-style: normal;
    font-weight: normal;
    font-variant: normal;
    text-transform: none;
    line-height: 1;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  .e-toggle-palette::before {
    content: "\e700"
  }

  .e-basic::before {
    content: "\e726";
  }

  .e-flow::before {
    content: "\e724";
  }

  .e-connector::before {
    content: "\e725";
  }

  .sb-mobile-palette {
    width:240px;
    height:100%;
    float:left;
}

.sb-mobile-palette-bar {
    display: none;
}

.sb-mobile-diagram {
    width:calc(100% - 242px);
    height: 100%;
    float: left;
}

@media (max-width: 550px) {

    .sb-mobile-palette {
        z-index: 19;
        position: absolute;
        display: none;
        transition: transform 300ms linear, visibility 0s linear 300ms;
        width:39%;
        height:100%;
    }
    
    .sb-mobile-palette-bar {
        display: block;
        width: 100%;
        background:#fafafa;
        padding: 10px 10px;
        border:0.5px solid #e0e0e0;
        min-height: 40px;
    }
    
    .sb-mobile-diagram {
        width: 100%;
        height: 100%;
        float: left;
        left: 0px;
    }

    #palette-icon {
        font-size: 20px; 
    }
}
    
.sb-mobile-palette-open {
    position: absolute;
    display: block;
    right: 15px;
}
</style>

<script>
import Vue from "vue";
import { Browser } from "@syncfusion/ej2-base";
import API from '../API'
import chonTrangThaiBaoHanh from './chonTrangThaiBaoHanh.vue';
import {
  DiagramPlugin,
  NodeModel,
  UndoRedo,
  ConnectorModel,
  PointPortModel,
  Connector,
  SymbolPalettePlugin,
  SymbolInfo,
  IDragEnterEventArgs,
  GridlinesModel,
  ConnectorConstraints,
  NodeConstraints,
  SnapConstraints,

} from "@syncfusion/ej2-vue-diagrams";
Vue.use(DiagramPlugin);
Vue.use(SymbolPalettePlugin);
Vue.use(chonTrangThaiBaoHanh)
let isMobile;
let interval;
interval = [
  1,
  9,
  0.25,
  9.75,
  0.25,
  9.75,
  0.25,
  9.75,
  0.25,
  9.75,
  0.25,
  9.75,
  0.25,
  9.75,
  0.25,
  9.75,
  0.25,
  9.75,
  0.25,
  9.75
];

let gridlines = {
  lineColor: "#e0e0e0",
  lineIntervals: interval
};

let nodes = [];
let connectors = [];

//Initialize the flowshapes for the symbol palatte
let flowshapes = [
  { id: "Terminator", shape: { type: "Flow", shape: "Terminator" } },
  { id: "Process", shape: { type: "Flow", shape: "Process" } },
];

//Initializes connector symbols for the symbol palette
let connectorSymbols = [
  {
    id: "Link21",
    type: "Straight",
    sourcePoint: { x: 0, y: 0 },
    targetPoint: { x: 60, y: 60 },
    targetDecorator: { shape: "Arrow", style: {strokeColor: "#757575", fill: "#757575"} },
    style: { strokeWidth: 2, strokeColor: "#757575" }
  },
];

export default Vue.extend({
  props: ['luongId'],
  data: function() {
    return {
      width: "100%",
      height: "600px",
      nodes: [],
      connectors: [
        {
          id: "connector1",
          // Sets source and target points
          sourcePoint: {
              x: 421,
              y: 87
          },
          targetPoint: {
              x: 421,
              y: 120
          }
        }
      ],
      luong_id_data : '',
      dtBaoHanh : [],
      dtLine : [],
      dtPic : [],
      moveToRight : 100,
      alignWidth : -22,
      alignHeight : 30,
      snapSettings: {
        // horizontalGridlines: gridlines,
        // verticalGridlines: gridlines
         constraints: 0
      },      
      created: (args) => {
        addEvents();
      },
      ShowAlert(mesage)
      {
        this.$toast.error(mesage);
      },
      showSuccess(mesage) {
        this.$toast.success(mesage);
      },
      GetData: function (response) {
        if (response.data.error === 200 || response.data.error === '200') {
          return response.data.data
        } else {
          console.log(response.data.error_code)
        }
        return []
      },

      show(){
          this.$bvModal.show("baoHanhThietBi");
          this.frmTrangThaiBH_Load()
      },

      frmTrangThaiBH_Load() {
        this.LayDuLieu()
      },
      async LayDuLieu() {
        let obj = {
          'LUONG_ID' : this.luong_id_data,
          'NHANVIEN_ID' : this.$root.token.getNhanVienID()
          // 'LUONG_ID' : 748,
          // 'NHANVIEN_ID' : 1
        }
        let input = {
          'p_ds_para' : JSON.stringify(obj)
        }
        let data = this.GetData(await API.fn_lay_dl_load_trangthai_bh(this.axios, input));
        data = JSON.parse(data)
        if (data) {
          this.dtBaoHanh = data.RESULT.DT_BAOHANH ? data.RESULT.DT_BAOHANH : []
          this.dtLine = data.RESULT.DT_LINE ? data.RESULT.DT_LINE : []
          this.dtPic = data.RESULT.DT_PIC ? data.RESULT.DT_PIC : []
          let arrDtBaoHanh = []
          let arrDtLine = []
          let arrDtPic = []
          if (this.dtBaoHanh.length > 0) {
            this.dtBaoHanh.map(item => {
              arrDtBaoHanh.push ({
                'offsetX' : item.TOADO_X + this.moveToRight,
                'offsetY' : item.TOADO_Y,
                'width' : item.DORONG,
                'height' : item.DOCAO,
                'annotations': [{
                    content: item.TRANGTHAI_BH
                }],
              })
            })
          }
          if (this.dtLine.length > 0) {
            this.dtLine.map(item => {
              let key = item.RL_ID
              let w = item.DORONG > 2 ? item.DORONG : 0
              let h = item.DOCAO > 2 ? item.DOCAO : 0
              let targetX = item.TOADO_X +w
              let targetY = item.TOADO_Y + h
              arrDtLine.push({
                id: 'connector1' + key,
                sourcePoint: {x:item.TOADO_X - this.alignWidth,y:item.TOADO_Y - this.alignHeight},
                targetPoint: {x:targetX - this.alignWidth,y: targetY - this.alignHeight},
                type: "Orthogonal",
                targetDecorator:  { shape: 'None' }
              })
            })
          }
          if (this.dtPic.length > 0) {
            this.dtPic.map(item => {
              arrDtPic.push ({
                'key' : 'arrow' + item.RL_ID,
                'image_index' : item.IMAGE_INDEX,
                'x' : item.TOADO_X,
                'y' : item.TOADO_Y,
                'width' : item.DORONG,
                'height' : item.DOCAO,
              })
            })
          }
          arrDtPic.forEach(c=>{
            let targetX = c.x 
            let targetY = c.y
            let plusX = 0
            let plusY = 0
            let subX = 0
            let subY = 0
            // arrow from top to bot
            if (c.image_index == 2){
              plusY = c.height;
              targetX += 4
            }
            // arrow from left to right
            else if(c.image_index == 1){
              plusX = c.width;
              targetY += 4
            }
            // arrow from right to left
            else if (c.image_index == 3){
              targetY += 4
              targetX += 15
              subX = c.width
            }
            
            let targetDecorator = {
                style: {
                      strokeColor: '#0176FF',
                      fill: '#0176FF', 
                      strokeWidth: 2
                  }, 
                shape: 'Arrow',
                width: c.width,
                height: c.height
              }
            arrDtLine.push({
              id: c.key.toString(),
              sourcePoint: {x: targetX - this.alignWidth ,y: targetY - this.alignHeight },
              targetPoint: {x: targetX + plusX - subX - this.alignWidth ,y: targetY  + plusY - subY - this.alignHeight},
              type: "Straight",
              targetDecorator: targetDecorator
            })
          })

          this.nodes = arrDtBaoHanh
          this.connectors = arrDtLine
          console.log('dt line is ', arrDtLine)
        }
      },
      //Sets the default values of a node
      getNodeDefaults: (node) => {
        let obj = {};
        if (obj.width === undefined) {
          obj.width = 145;
        } else {
          let ratio = 100 / obj.width;
          obj.width = 100;
          if (obj.height) {
            obj.height *= ratio;
          }
        }
        obj.style = { fill: "#357BD2", strokeColor: "white" };
        obj.annotations = [{ style: { color: "white", fill: "transparent" } }];

        //Set ports
        obj.ports = getPorts();
        return obj;
      },
      //Sets the default values of a connector
      getConnectorDefaults: (obj) => {
        obj.sourceDecorator = { shape: 'None' };
        obj.style = {
            strokeColor: '#0176FF',
            fill: '#0176FF',
            strokeWidth: 2
        },
        obj.strokeColor = '#0176FF',
        obj.fill = '0176FF',
        obj.strokeWidth = 2

      },
      //Sets the Node style for DragEnter element.
      dragEnter: (args) => {
        let obj = args.element;
        if (obj && obj.width && obj.height) {
          let oWidth = obj.width;
          let oHeight = obj.height;
          let ratio = 100 / obj.width;
          obj.width = 100;
          obj.height *= ratio;
          if (obj.offsetX) obj.offsetX += (obj.width - oWidth) / 2;
          if (obj.offsetY) obj.offsetY += (obj.height - oHeight) / 2;
          obj.style = { fill: "#357BD2", strokeColor: "white" };
        }
      },
      expandMode: "Multiple",
      palettes: [
        // {
        //   id: "flow",
        //   expanded: true,
        //   symbols: flowshapes,
        //   iconCss: "shapes",
        //   title: "Flow Shapes"
        // },
        {
          id: "connectors",
          expanded: true,
          symbols: connectorSymbols,
          iconCss: "shapes",
          title: "Connectors"
        }
      ],
      palettewidth: "100%",
      paletteheight: "700px",
      symbolHeight: 60,
      symbolWidth: 60,
      palettegetNodeDefaults: (symbol) => {
        symbol.style = {strokeColor: "#757575"}
        if (symbol.id === "Terminator" || symbol.id === "Process") {
          symbol.width = 80;
          symbol.height = 40;
        } else if (
          symbol.id === "Decision" ||
          symbol.id === "Document" ||
          symbol.id === "PreDefinedProcess" ||
          symbol.id === "PaperTap" ||
          symbol.id === "DirectData" ||
          symbol.id === "MultiDocument" ||
          symbol.id === "Data"
        ) {
          symbol.width = 50;
          symbol.height = 40;
        } else {
          symbol.width = 50;
          symbol.height = 50;
        }
      },
      symbolMargin: { left: 15, right: 15, top: 15, bottom: 15 },
      getSymbolInfo: (symbol) => {
        return { fit: true };
      }
    };
  },
  mounted: function() {
    this.frmTrangThaiBH_Load()
    let diagram = this.$refs.diagramObject.ej2Instances;
    diagram.fitToPage();
  },
  watch: {
    luongId(val) {
        this.luong_id_data = val
    },
  },
});

function getPorts() {
  let ports = [
    { id: "port1", shape: "Circle", offset: { x: 0, y: 0.5 } },
    { id: "port2", shape: "Circle", offset: { x: 0.5, y: 1 } },
    { id: "port3", shape: "Circle", offset: { x: 1, y: 0.5 } },
    { id: "port4", shape: "Circle", offset: { x: 0.5, y: 0 } }
  ];
  return ports;
}

function addEvents() {
    isMobile = window.matchMedia('(max-width:550px)').matches;
    if (isMobile) {
        let paletteIcon = document.getElementById('palette-icon');
        if (paletteIcon){
            paletteIcon.addEventListener('click', openPalette, false);
        }
    }
}

function openPalette() {
  let paletteSpace = document.getElementById('palette-space');
  isMobile = window.matchMedia('(max-width:550px)').matches;
  if (isMobile) {
    if (!paletteSpace.classList.contains('sb-mobile-palette-open')) {
      paletteSpace.classList.add('sb-mobile-palette-open');
    } else {
      paletteSpace.classList.remove('sb-mobile-palette-open');
    }
  }
}
</script>
