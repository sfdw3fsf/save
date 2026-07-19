<template src="./index.html"></template>
<script>
import Vue from "vue";
import {
  Diagram,
  DiagramPlugin,
  DiagramTools,
  SelectorConstraints,
  SnapConstraints,
  DiagramConstraints,
  DiagramContextMenu,
  NodeConstraints,
  ConnectorConstraints,
  ConnectorSegment,
  BezierSegment,
  Point,
  ConnectorEditing
} from "@syncfusion/ej2-vue-diagrams";
Vue.use(DiagramPlugin);
Diagram.Inject(ConnectorEditing)

var diagramInstance
export default {
  provide: {
    diagram: [DiagramContextMenu],
  },
  props: {
    // nodes: {
    //   type: Array,
    //   default: () => [],
    // },
    // connectors: {
    //   type: Array,
    //   default: () => [],
    // },
    refresh: {
      type: Boolean,
      default: false
    },
    zoomFactor:null,
    isShowGridLine:{
      type:Boolean,
      default:true
    },
    verticalOffset: Number,
    horizontalOffset: Number,
    isHoverRange: Boolean,
    isHoverScroll:Boolean,
  },
  watch: {
    // nodes: function (value) {
    //   let diagramInstance = this.$refs.diagram.ej2Instances;
    //   // diagramInstance.clear();
    //   diagramInstance.refresh();
    //   //diagramInstance.reset();
    //   //diagramInstance.doLayout();
    //
    // },
    // connectors: function (value) {
    //   let diagramInstance = this.$refs.diagram.ej2Instances;
    //   diagramInstance.clear();
    //   diagramInstance.refresh();
    //   //diagramInstance.reset();
    //   //diagramInstance.doLayout();
    //
    // },
    refresh: function (value) {
      // let diagramInstance = this.$refs.diagram.ej2Instances;
      if(diagramInstance.width == "100%")
        diagramInstance.width = "99.9%";
      else
        diagramInstance.width = "100%";
      console.log("refresh")
      diagramInstance.fitToPage()
    },
    zoomFactor: function (value) {
        // let diagramInstance = this.$refs.diagram.ej2Instances;
      if (this.isHoverRange){
        let scale = value/this.currentZoomScale
        diagramInstance.zoom(scale)
      }
    },
    horizontalOffset: function (value){
      //Updates scroll settings
      diagramInstance.scrollSettings.horizontalOffset = value
      // diagramInstance.scrollSettings.verticalOffset = this.verticalOffset
      diagramInstance.dataBind()
    },
    verticalOffset: function (value){
      //Updates scroll settings
      console.log(value)
      // diagramInstance.scrollSettings.horizontalOffset = this.horizontalOffset
      diagramInstance.scrollSettings.verticalOffset = value
      diagramInstance.dataBind()
    },
    isShowGridLine: function (value){
      if (value) this.snapSettings = { constraints: SnapConstraints.ShowLines }
      else this.snapSettings = { constraints: SnapConstraints.None }
      diagramInstance.fitToPage()
    }
  },
  components: {
    BezierSegment, Point, ConnectorSegment
  },
  data: function () {
    var self = this;
    return {
      // current scale diagram
      currentZoomScale:  null,
      // node, connector previous selected
      previousNode:null,
      previousConnector: null,

      TYPE:"",
      click: (args) => {
      console.log("click",args)
     //   this.$toast.success("Thêm tiến trình thành công" +args.element.propName)

        // console.log("click")
        let obj ={
          id : args.element.id,
          type : "CLICK",
          typeObj : args.element.propName
        }
        obj.x = args.position.x
        obj.y = args.position.y

        // if(args.element.button === "Right"){
        //   if(args.element.propName === "nodes"){
        //     this.TYPE =1
        //   }else if(args.element.propName === "connectors"){
        //     this.TYPE =2
        //   } else {
        //     this.TYPE =3
        //   }
        // }
        this.$emit("taoLienKet", obj);

      //setting connector when click
      //   if (args.actualObject && args.actualObject.propName === "connectors"){
      //
      //     //setting constrains
      //     for (let i = 0; i < diagramInstance.connectors.length; i++) {
      //       let connector = diagramInstance.connectors[i];
      //       if (connector.constraints) {
      //         connector.constraints |=
      //           ConnectorConstraints.Default & ConnectorConstraints.ReadOnly;
      //       }
      //     }
      //   }
        let clickedItem = args.actualObject
        // remove color selected nodem connector
        if (this.previousNode){
          let idx = diagramInstance.nodes.findIndex(x => x.id.toString() === this.previousNode.id.toString())
          if (idx > -1) {
            diagramInstance.nodes[idx].shape.source = diagramInstance.nodes[idx].shape.source.replace('_sel', '')
            this.previousNode = null
          }
        }
        if (this.previousConnector){
          let idx = diagramInstance.connectors.findIndex(x => x.id.toString() === this.previousConnector.id.toString())
          if (idx > -1) {
            diagramInstance.connectors[idx].style.strokeColor = "#0000FF"
            // diagramInstance.dataBind()
            this.previousConnector = null
          }
        }
        if (clickedItem) {

          //setting color
          if (clickedItem.propName === "connectors"){
            let idx = diagramInstance.connectors.findIndex(x => x.id.toString() === clickedItem.id.toString())
            if (idx > -1){
              // set constrains
              diagramInstance.connectors[idx].constraints = (ConnectorConstraints.Default | ConnectorConstraints.DragSegmentThumb) & ~ConnectorConstraints.Drag

              diagramInstance.connectors[idx].style.strokeColor = "#000000"

              // cập nhật thông tin điểm uốn connector nếu connector đường cong
              if (diagramInstance.connectors[idx].type === "Bezier"){
                diagramInstance.connectors[idx].THONGTIN.DIEMUON_X1 = diagramInstance.connectors[idx].segments[0].point1.x
                diagramInstance.connectors[idx].THONGTIN.DIEMUON_Y1 = diagramInstance.connectors[idx].segments[0].point1.y

                diagramInstance.connectors[idx].THONGTIN.DIEMUON_X2 = diagramInstance.connectors[idx].segments[0].point2.x
                diagramInstance.connectors[idx].THONGTIN.DIEMUON_Y2 = diagramInstance.connectors[idx].segments[0].point2.y
              }
              this.previousConnector = diagramInstance.connectors[idx]

            }
          }
          if (clickedItem.propName === "nodes"){
            // set ICON selected
            let idx = diagramInstance.nodes.findIndex(x => x.id.toString() === clickedItem.id.toString())
            if (idx > -1) {
              // set constrains
              diagramInstance.nodes[idx].constraints = (NodeConstraints.Default | NodeConstraints.Tooltip | NodeConstraints.Drag) & ~NodeConstraints.Resize

              diagramInstance.nodes[idx].shape.source = diagramInstance.nodes[idx].shape.source.replace(diagramInstance.nodes[idx].IMAGE_KEY, diagramInstance.nodes[idx].IMAGE_KEY+'_sel')
              this.previousNode = diagramInstance.nodes[idx]
            }
          }
        }else {
          //remove selector khi clickedItem undefined
          diagramInstance.selectedItems.nodes = []
          diagramInstance.selectedItems.connectors = []
        }
        diagramInstance.dataBind()
      },
      doubleClick: (args) => {
        console.log("doubleClick",args)
        let obj ={
          id : args.source.id,
          type : "DOUBLE_CLICK",
          typeObj : args.source.propName
        }
        this.$emit("taoLienKet", obj);
      },
      positionChange: (args) => {

        if(args.state === "Completed"){
          let obj ={
            offsetX : args.oldValue.offsetX,
            offsetY : args.oldValue.offsetY,
            type : "OFFSET_CHANGE"
          }
          this.$emit("positionChange", obj);
        }


      },
      selectionChange: (args) => {
        let idBreak =""
        if(args.newValue[0]){
           idBreak =  args.newValue[0].id.split("_")[1]
        }
        let obj ={
          id : idBreak,
          type : "ID_CHANGE"
        }
        this.$emit("positionChange", obj);

      },
      scrollChange:(args) =>{
        //using get current zoom
        if (!this.isHoverRange){
          this.$emit("currentZoom", args.newValue.CurrentZoom)
        }
        if (!this.isHoverScroll){
          this.$emit("offset", args.newValue)
        }
        this.currentZoomScale = args.newValue.CurrentZoom
        // console.log(args)
      },
      width: "100%",
      height: "100%",
      scrollSetting:{
        scrollLimit: 'infinity',
        horizontalOffset:10,
        verticalOffset:10,
      },
      //Disables all interactions except zoom/pan
      tool:  DiagramTools.SingleSelect
          | DiagramTools.ZoomPan
      ,
      //Enables the virtualization constraint
      // constraints: DiagramConstraints.Default | DiagramConstraints.LineRouting |
      //   NodeConstraints.Default| NodeConstraints.Drag |
      //   ConnectorConstraints.Default | ConnectorConstraints.Drag,
      contextMenuSettings: {
        show: true ,
        items: [
          {
            text: "Tạo vòng",
            id: "MENU_ITEM_ADD_RING",
            target: ".e-diagramcontent",
            iconCss: "fa fa-exchange",
          },
          {
            id: "MENU_ITEM_SEPARATOR_1",
            separator: true,
          },
          {
            text: "Bỏ chọn danh sách liên kết",
            id: "MENU_ITEM_UNSELECTED_LINKS",
            target: ".e-diagramcontent",
          },
          {
            text: "Ẩn vòng (ring)",
            id: "MENU_ITEM_HIDE_RING",
            target: ".e-diagramcontent",
          },
          {
            text: "Xóa đối tượng",
            id: "MENU_ITEM_XOA_DOITUONG",
            target: ".e-diagramcontent",
            iconCss: "fa fa-remove",
          },
          {
            id: "MENU_ITEM_SEPARATOR_2",
            separator: true,
          },
          {
            text: "Thuộc tính",
            id: "MENU_ITEM_THUOC_TINH_DOITUONG",
            target: ".e-diagramcontent",
            iconCss: "fa fa-info-circle",
          },
          {
            text: "Xóa liên kết",
            id: "MENU_ITEM_XOA_LIENKET",
            target: ".e-diagramcontent",
            iconCss: "fa fa-remove",
          },
          {
            id: "MENU_ITEM_SEPARATOR_3",
            separator: true,
          },
          {
            text: "Thuộc tính",
            id: "MENU_ITEM_THUOC_TINH_LIENKET",
            target: ".e-diagramcontent",
            iconCss: "fa fa-info-circle",
          },
        ],
        // Hides the default context menu items
        showCustomMenuOnly: true,
      },
      selectedItems: {
        // constraints: SelectorConstraints.None,
        constraints: (SelectorConstraints.All | SelectorConstraints.ConnectorTargetThumb | SelectorConstraints.ConnectorSourceThumb) & ~SelectorConstraints.Rotate /*& ~SelectorConstraints.ResizeAll*/
      },
      snapSettings: { constraints: SnapConstraints.ShowLines },
      contextMenuOpen: function (args) {
        // console.log("aaa",args)
        if (this.selectedItems.nodes.length > 0) {
          args.hiddenItems.push("MENU_ITEM_ADD_RING");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_1");
          args.hiddenItems.push("MENU_ITEM_UNSELECTED_LINKS");
          args.hiddenItems.push("MENU_ITEM_HIDE_RING");

          args.hiddenItems.push("MENU_ITEM_XOA_LIENKET");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_3");
          args.hiddenItems.push("MENU_ITEM_THUOC_TINH_LIENKET");
        } else if (this.selectedItems.connectors.length > 0) {
          args.hiddenItems.push("MENU_ITEM_ADD_RING");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_1");
          args.hiddenItems.push("MENU_ITEM_UNSELECTED_LINKS");
          args.hiddenItems.push("MENU_ITEM_HIDE_RING");

          args.hiddenItems.push("MENU_ITEM_XOA_DOITUONG");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_2");
          args.hiddenItems.push("MENU_ITEM_THUOC_TINH_DOITUONG");
        } else {
          args.hiddenItems.push("MENU_ITEM_XOA_DOITUONG");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_2");
          args.hiddenItems.push("MENU_ITEM_THUOC_TINH_DOITUONG");

          args.hiddenItems.push("MENU_ITEM_XOA_LIENKET");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_3");
          args.hiddenItems.push("MENU_ITEM_THUOC_TINH_LIENKET");
          // args.cancel = true;
        }
      },
      contextMenuClick: function (args) {
        // console.log(args)
        let value = "";

        if (args.element.id === "MENU_ITEM_THUOC_TINH_DOITUONG") {
          self.$emit("capNhatDoiTuong",{
            id:this.selectedItems.nodes[0].id
          });
        //  console.log(args)
        }

        if (args.element.id === "MENU_ITEM_XOA_DOITUONG") {
          self.$emit("xoaDoiTuong");
        //  console.log(args)
        }

        if (args.element.id === "MENU_ITEM_XOA_LIENKET") {
          self.$emit("xoaLienKet",{
            id:this.selectedItems.connectors[0].id
          });
          console.log("âxaxaxaxa",this.selectedItems.connectors[0].id)
        }

        if (args.element.id === "MENU_ITEM_THUOC_TINH_LIENKET") {
          self.$emit("capNhatLienKet",{
            id:this.selectedItems.connectors[0].id
          });
        }

        console.log(
          "itemClick: '" + this.selectedItems.nodes.length + "  " + this.selectedItems.connectors.length
        );

        if (this.selectedItems.nodes.length > 0)
          value = "node: " + this.selectedItems.nodes[0].id;
        else if (this.selectedItems.connectors.length > 0)
          value = "connector: " + this.selectedItems.connectors[0].id;
        console.log(
          "itemClick: '" + args.element.id + "', arg: {" + value + "}"
        );
      },
      commandManager : {
        commands: [
          {
            name: 'copy',
            canExecute: function(){
              return  false
            },
          },
          {
            name: 'paste',
            canExecute: function() {
              return  false
            },
          },
          {
            name: 'cut',
            canExecute: function() {
              return  false
            },
          },
          {
            name: 'delete',
            canExecute: function() {
              return  false
            },
          },
        ]
      },
    };
  },
  methods:{
    getConnectors(){
      return diagramInstance.connectors
    },
    napGianDo(nodes, connectors){
      diagramInstance.clear()
      diagramInstance.refresh()
      nodes.forEach(item => diagramInstance.addNode(item))
      connectors.forEach(item => diagramInstance.addConnector(item))
      diagramInstance.dataBind()
    },
    zoomIn(){
      let zoomOptions = {
        type: "ZoomIn",
        //Sets the factor by which we can zoom in or zoom out
        zoomFactor: 0.5
      }
      //zoomin the diagram
      diagramInstance.zoomTo(zoomOptions)
    },
    zoomOut(){
      let zoomOptions = {
        type: "ZoomOut",
        //Sets the factor by which we can zoom in or zoom out
        zoomFactor: 0.5
      }
      //zoomout the diagram
      diagramInstance.zoomTo(zoomOptions)
    },
  },
  computed: {
    nodesData: function () {
      if (this.nodes == null) return [];
      else return this.nodes;
    },
    connectorsData: function () {
      if (this.connectors == null) return [];
      else return this.connectors;
    },
  },
  mounted() {
    diagramInstance = this.$refs.diagram.ej2Instances
    diagramInstance.fitToPage()
  }
};
</script>
