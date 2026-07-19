<template>
  <div>
    <ejs-diagram
      id="gian-do"
      ref="diagram"
      :click="diagramClick"
      :doubleClick="doubleClick"
      :height="height"
      :snapSettings="snapSettings"
      :tool="tool"
      :getConnectorDefaults="getConnectorDefaults"
      :getNodeDefaults="getNodeDefaults"
      :width="width"
      :keyDown="keyDown"
      :keyUp="keyUp"
      :mouseOver="mouseOver"
      :mouseDown="mouseDown"
      :mouseUp="mouseUp"
      :contextMenuOpen="contextMenuOpen"
      :contextMenuClick="contextMenuClick"
      :mouseLeave="mouseLeave"
      :selectedItems="selectedItems"
      :contextMenuSettings="contextMenuSettings"
      :commandManager="commandManager"
      :scrollChange="scrollChange"
      :scrollSettings="scrollSettings"
      :rulerSettings="rulerSettings"
      mode="SVG"
      backgroundColor="white"
      enableAnimation="false"
    ></ejs-diagram>
    <DauNoiThietBi id="popupDauNoiThietBiGianDo" :doiTuong="doiTuong" />
    <QuanLyTramThietBi
      id="popupTramThietBiGianDo"
      :tramTb_id="tramTb_id"
      @isOk="QuanLyTramThietBiIsOk"
    />
    <QLKetCuoi modalID="QLKetCuoiGianDo" ref="QLKetCuoiGianDo" />
    <QLCap
      id="popupQuanLyCapGianDo"
      :capObj="thongTinCap.capObj"
      :taomoi="thongTinCap.taomoi"
      :editable="thongTinCap.editable"
      @isOk="QLCapIsOk"
    ></QLCap>
    <ChonDoiTuong
      id="popupChonDoiTuongDichGianDo"
      @chon="ChonDTisOK"
      :listObj="listObj"
    />
    <DauNoiCap id="popupChiTietDauNoiGianDo" :capObject="capObject"></DauNoiCap>
    <ModalTracnghiemExt id="ModalTracnghiemExt" ref="ModalTracnghiemExt" />
    <ModalChonDSKcExt
      id="ModalChonDSKcExt"
      ref="ModalChonDSKcExt"
      :doiTuong="doiTuongKc"
    />
    <ModalPhanChiaDoanCapChenKetCuoi
      modalID="ModalPhanChiaDoanCapChenKetCuoi"
      ref="ModalPhanChiaDoanCapChenKetCuoi"
    />
    <ModalChonTramTbi
      modalId="modalChonTramTbi"
      :valTtvtId="ttvt_id"
      @select="modalChonTramTbi_OnSelectTramTbi"
    />
    <frmDauNoiKC ref="frmDauNoiKC" graphID="frmDauNoiKC" scale="xl"></frmDauNoiKC>
  </div>
</template>
<script>
import Vue from "vue";
import EventBus from "@/utils/eventBus";
import {
  Diagram,
  NodeModel,
  Node,
  ConnectorConstraints,
  Connector,
  PortVisibility,
  ConnectorEditing,
  DataBinding,
  SnapConstraints,
  SnapSettingsModel,
  DiagramContextMenu,
  Snapping,
  NodeConstraints,
  DiagramConstraints,
  UndoRedo,
  SelectorConstraints,
  DiagramTools,
  ZoomPanTool,
  ConnectionPointOrigin,
  ChildArrangement,
  PortConstraints,
  ComplexHierarchicalTree,
  HierarchicalTree,
  LineDistribution,
  LayoutOrientation,
  LineRouting,
  DiagramPlugin,
  StraightSegment,
  AnnotationConstraints,
  AnnotationModel,
  ShapeAnnotationModel,
  Rect,
} from "@syncfusion/ej2-vue-diagrams";
import { ContextMenuComponent } from "@syncfusion/ej2-vue-navigations";
import Point from "../class/Point";
import Link from "../class/Link";
import { getterName, actionName, statePropertyName } from "../store";
import DiagramLib from "@/utils/DiagramLib";
//import DauNoiThietBi from '../../QuanLyMangTruyenDan-Minh/daunoi-thietbi/Modal'
import DauNoiThietBi from "../../daunoi-thietbi/Modal";
import QuanLyTramThietBi from "../../QuanLyMangTruyenDan-Cuong/QuanLyTramThietBi";
import QLKetCuoi from "../Modal/QLKetCuoi";
import { mapActions } from "vuex";
import ChonDoiTuong from "../../QuanLyMangTruyenDan-Cuong/ChonDoiTuongDich/Modal";
import QLCap from "../../QLCap/QLCap";
import DauNoiCap from "../../DauNoiCap/DauNoiCap";
import ModalTracnghiemExt from "../TracNghiemExt";
import ModalChonDSKcExt from "../ChenKetCuoi";
import ModalPhanChiaDoanCapChenKetCuoi from "../PhanChiaDoanCapChenKetCuoi";
import frmDauNoiKC from "../../BanDoMangCap/modal/DauNoiKCv3";
import ModalChonTramTbi from "../../quanly-hoso-dauchuyen/modal-chon-tramtbi/index.vue";
Vue.use(DiagramPlugin);
Diagram.Inject(LineRouting, ConnectorEditing);
let diagramInstance;
export default Vue.extend({
  components: {
    DauNoiThietBi,
    QuanLyTramThietBi,
    QLKetCuoi,
    QLCap,
    StraightSegment,
    ChonDoiTuong,
    DauNoiCap,
    ModalTracnghiemExt,
    ModalChonDSKcExt,
    ModalPhanChiaDoanCapChenKetCuoi,
    frmDauNoiKC,
    ModalChonTramTbi,
  },
  props: {
    ADD_TRAM_TB_FLAG: Boolean,
  },
  data: function () {
    return {
      ttvt_id: 0,
      flag_nguon: 0, //Nguon thay doi thiet bi
      // object form DauNoiCap: Chi tiết đấu nối
      capObject: {},
      // Tạo cáp tới bằng (double click: fromObj, click:toObj)
      fromObjDoubleClick: null,
      listObj: {},
      currentOffset: {
        x: null,
        y: null,
      },
      previousConnector: null,
      previousNode: null,
      doiTuong: {},
      tramTb_id: null,
      thongTinCap: {
        capObj: {},
        taomoi: false,
        editable: false,
      },
      doiTuongKc: {
        kieu: 0,
      },
      Points: [],
      Links: [],
      width: "100%",
      height: "calc(100vh - 277px)",
      constraints:
        DiagramConstraints.Default |
        DiagramConstraints.LineRouting |
        AnnotationConstraints.Interaction,
      layout: {},
      // layout: {
      //   arrangement: ChildArrangement.Linear,
      //   type: 'ComplexHierarchicalTree',
      //   connectionPointOrigin: ConnectionPointOrigin.DifferentPoint,
      //   orientation: 'LeftToRight',
      //   verticalSpacing: 100,
      //   horizontalSpacing: 270,
      //   margin: { left: 10, right: 0, top: 50, bottom: 0 },
      //   enableRouting: true
      // },
      snapSettings: {
        /*horizontalGridlines: {
            lineColor: '#cdcdcd',
            dotIntervals: [0.95, 20.75],
            lineIntervals: [0.95, 20.75],
            snapIntervals: [10]
          },
          verticalGridlines: {
            lineColor: '#cdcdcd',
            dotIntervals: [0.95, 20.75],
            lineIntervals: [0.95, 20.75],
            snapIntervals: [10]
          },
          gridType: 'Dots',*/
        //constraints: SnapConstraints.SnapToLines | SnapConstraints.ShowLines
      },
      tool: DiagramTools.SingleSelect | DiagramTools.ZoomPan,
      selectedItems: {
        constraints: SelectorConstraints.None, //SelectorConstraints.All & ~SelectorConstraints.Rotate
      },
      scrollSettings: {
        canAutoScroll: true,
        scrollLimit: "Infinity",
        horizontalOffset: 10,
        verticalOffset: 10,
        padding: { right: 50, bottom: 50 },
      },
      rulerSettings: {
        showRulers: false,
      },
      getNodeDefaults: (obj) => {
        obj.constraints =
          (NodeConstraints.Default | NodeConstraints.ReadOnly) &
          ~NodeConstraints.Resize &
          ~NodeConstraints.Rotate;
      },
      getConnectorDefaults: (obj) => {
        obj.constraints =
          (ConnectorConstraints.Default | ConnectorConstraints.ReadOnly) &
          ~ConnectorConstraints.Drag &
          ~ConnectorConstraints.DragSourceEnd &
          ~ConnectorConstraints.DragTargetEnd;
        obj.targetDecorator = {
          shape: "None",
        };
        obj.allowNodeOverlap = false;
      },
      contextMenuSettings: {
        show: true,
        items: [
          {
            text: "Đấu nối thiết bị",
            id: "MENU_ITEM_DAU_NOI_THIET_BI",
            target: ".e-diagramcontent",
            iconCss: "icon one-td-journey2",
          },
          {
            id: "MENU_ITEM_SEPARATOR_1",
            separator: true,
          },
          {
            text: "Đổi biểu tượng",
            id: "MENU_DOI_BIEU_TUONG",
            target: ".e-diagramcontent",
            iconCss: "icon nc-icon-outline ui-1_eye-17",
            items: [
              {
                text: "Host",
                id: "ICON_HOST",
                target: ".e-diagramcontent",
                iconCss: "icon one-td-host",
              },
              {
                text: "Trạm MSAN,RSU có BTS",
                id: "ICON_MSAN_BTS",
                target: ".e-diagramcontent",
                iconCss: "icon one-td-play2",
              },
              {
                text: "Trạm MSAN,RSU không có BTS",
                id: "ICON_MSAN_NO_BTS",
                target: ".e-diagramcontent",
                iconCss: "icon one-td-arrow-up",
              },
              {
                text: "Trạm BTS",
                id: "ICON_BTS",
                target: ".e-diagramcontent",
                iconCss: "icon one-td-settings",
              },
            ],
          },
          {
            text: "Đổi đánh dấu",
            id: "MENU_DOI_DANH_DAU",
            target: ".e-diagramcontent",
            iconCss: "icon one-chart-radar",
            items: [
              {
                text: "Không có đánh dấu",
                id: "NO_TICK",
                target: ".e-diagramcontent",
                iconCss: "one-circle-dot",
              },
              {
                id: "MENU_ITEM_SEPARATOR_2",
                separator: true,
              },
              {
                text: "Đánh dấu kiểu 1",
                id: "TICK_1",
                target: ".e-diagramcontent",
                iconCss: "one-circle-dot red",
              },
              {
                text: "Đánh dấu kiểu 2",
                id: "TICK_2",
                target: ".e-diagramcontent",
                iconCss: "one-circle-dot green",
              },
              {
                text: "Đánh dấu kiểu 3",
                id: "TICK_3",
                target: ".e-diagramcontent",
                iconCss: "one-circle-dot blue",
              },
            ],
          },
          {
            text: "Tạo cáp tới",
            id: "MENU_TAO_CAP_TOI",
            target: ".e-diagramcontent",
            iconCss: "icon nc-icon-outline arrows-1_tail-right",
          },
          {
            text: "Đấu nối cáp",
            id: "MENU_DAU_NOI_CAP",
            target: ".e-diagramcontent",
            iconCss: "icon nc-icon-outline ui-2_link-72",
          },
          {
            text: "Xoá kết cuối",
            id: "MENU_XOA_KET_CUOI",
            target: ".e-diagramcontent",
            iconCss: "icon nc-icon-outline ui-1_circle-remove",
          },
          // MENU CAP
          {
            text: "Thêm điểm uốn",
            id: "MENU_ITEM_THEM_DIEMUON_CAP",
            target: ".e-diagramcontent",
            iconCss: "icon nc-icon-outline ui-1_simple-add",
          },
          {
            text: "Xóa điểm uốn",
            id: "MENU_ITEM_XOA_DIEMUON_CAP",
            target: ".e-diagramcontent",
            iconCss: "icon one-trash",
          },
          {
            text: "Xóa toàn bộ điểm uốn",
            id: "MENU_ITEM_XOA_TOANBO_DIEMUON",
            target: ".e-diagramcontent",
            iconCss: "icon one-trash",
          },
          {
            id: "MENU_ITEM_SP_THAYDOI_TBI",
            separator: true,
          },
          {
            text: "Thay đổi thiết bị nguồn",
            id: "MENU_ITEM_THAYDOI_TBI_NGUON",
            target: ".e-diagramcontent",
            iconCss: "fa fa-pencil-square-o",
          },
          {
            text: "Thay đổi thiết bị đích",
            id: "MENU_ITEM_THAYDOI_TBI_DICH",
            target: ".e-diagramcontent",
            iconCss: "fa fa-pencil-square-o",
          },
          {
            id: "MENU_ITEM_SEPARATOR_2",
            separator: true,
          },
          {
            text: "Tạo kết cuối",
            id: "MENU_ITEM_TAO_KETCUOI",
            target: ".e-diagramcontent",
            iconCss: "fa fa-info-circle",
          },
          {
            text: "Chèn kết cuối",
            id: "MENU_ITEM_CHEN_KETCUOI",
            target: ".e-diagramcontent",
            iconCss: "icon one-td-journey",
          },
          {
            text: "Chèn kết cuối",
            id: "MENU_ITEM_CHEN_KETCUOI_DIAGRAM",
            target: ".e-diagramcontent",
            iconCss: "icon one-td-journey",
          },
          {
            text: "Chi tiết đấu nối",
            id: "MENU_ITEM_CHITIET_DAUNOI",
            target: ".e-diagramcontent",
            iconCss: "icon one-td-usb",
          },
          {
            id: "MENU_ITEM_SEPARATOR_2",
            separator: true,
          },
          {
            text: "Chuyển kiểu cáp ...",
            id: "MENU_ITEM_CHUYEN_KIEUCAP",
            target: ".e-diagramcontent",
            iconCss: "icon one-td-arrow-change",
            items: [
              {
                text: "Cáp thuê bao",
                id: "MENU_ITEM_CAP_THUEBAO",
                target: ".e-diagramcontent",
                iconCss: "fa fa-exchange",
              },
              {
                text: "Cáp truyền dẫn",
                id: "MENU_ITEM_CAP_TRUYENDAN",
                target: ".e-diagramcontent",
                iconCss: "fa fa-exchange",
              },
            ],
          },
          {
            text: "Chuyển kiểu đi cáp ...",
            id: "MENU_ITEM_CHUYEN_KIEU_DI_CAP",
            target: ".e-diagramcontent",
            iconCss: "icon one-td-journey1",
            items: [
              {
                text: "Kéo cống",
                id: "MENU_ITEM_CHUYEN_KIEU_DI_CAP_KEOCONG",
                target: ".e-diagramcontent",
                iconCss: "icon one-td-line",
              },
              {
                text: "Kéo treo",
                id: "MENU_ITEM_CHUYEN_KIEU_DI_CAP_KEOTREO",
                target: ".e-diagramcontent",
                iconCss: "icon one-td-line2",
              },
              {
                text: "Chôn",
                id: "MENU_ITEM_CHUYEN_KIEU_DI_CAP_CHON",
                target: ".e-diagramcontent",
                iconCss: "icon one-td-line3",
              },
              {
                id: "MENU_ITEM_SEPARATOR_2",
                separator: true,
              },
              {
                text: "Đề xuất kéo mới",
                id: "MENU_ITEM_CHUYEN_KIEU_DI_CAP_DEXUAT_KEOMOI",
                target: ".e-diagramcontent",
                iconCss: "icon one-td-line4",
              },
            ],
          },
          {
            text: "Xóa cáp",
            id: "MENU_ITEM_XOA_CAP",
            target: ".e-diagramcontent",
            iconCss: "icon nc-icon-outline ui-1_circle-remove red",
          },
          {
            id: "MENU_ITEM_SEPARATOR_3",
            separator: true,
          },
          {
            text: "Thuộc tính",
            id: "MENU_ITEM_THUOC_TINH",
            target: ".e-diagramcontent",
            iconCss: "icon -ap icon-info_outline",
          },
          {
            text: "Thuộc tính",
            id: "MENU_ITEM_THUOC_TINH_CAP",
            target: ".e-diagramcontent",
            iconCss: "icon -ap icon-info_outline",
          },
          {
            id: "MENU_ITEM_SEPARATOR_4",
            separator: true,
          },
          {
            text: "Bố cục giản đồ",
            id: "MENU_ITEM_BOCUC_GIANDO",
            target: ".e-diagramcontent",
            iconCss: "fa fa-info-circle",
            items: [
              // {
              //   text: "Glee",
              //   id: "MENU_ITEM_BOCUC_GIANDO_GLEE",
              //   target: ".e-diagramcontent",
              //   iconCss: "fa fa-info-circle",
              // },
              // {
              //   id: "MENU_ITEM_SEPARATOR_4",
              //   separator: true,
              // },
              // {
              //   text: "Dot",
              //   id: "MENU_ITEM_BOCUC_GIANDO_DOT",
              //   target: ".e-diagramcontent",
              //   iconCss: "fa fa-info-circle",
              // },
              // {
              //   text: "Neato",
              //   id: "MENU_ITEM_BOCUC_GIANDO_NEATO",
              //   target: ".e-diagramcontent",
              //   iconCss: "fa fa-info-circle",
              // },
              // {
              //   text: "Sfdp",
              //   id: "MENU_ITEM_BOCUC_GIANDO_SFDP",
              //   target: ".e-diagramcontent",
              //   iconCss: "fa fa-info-circle",
              // },
              // {
              //   text: "Fdp",
              //   id: "MENU_ITEM_BOCUC_GIANDO_FDP",
              //   target: ".e-diagramcontent",
              //   iconCss: "fa fa-info-circle",
              // },
              // {
              //   text: "Circo",
              //   id: "MENU_ITEM_BOCUC_GIANDO_CIRCO",
              //   target: ".e-diagramcontent",
              //   iconCss: "fa fa-info-circle",
              // },
              // {
              //   text: "Twopi",
              //   id: "MENU_ITEM_BOCUC_GIANDO_TWOPI",
              //   target: ".e-diagramcontent",
              //   iconCss: "fa fa-info-circle",
              // },
              {
                text: "ComplexHierarchicalTree",
                id: "MENU_ITEM_BOCUC_GIANDO_CHTree",
                target: ".e-diagramcontent",
                iconCss: "",
              },
              {
                id: "MENU_ITEM_SEPARATOR_4",
                separator: true,
              },
              {
                text: "Tùy chỉnh",
                id: "MENU_ITEM_BOCUC_GIANDO_TUYCHINH",
                target: ".e-diagramcontent",
                iconCss: "",
              },
            ],
          },
          {
            text: "Xóa toàn bộ điểm uốn",
            id: "MENU_ITEM_XOA_TOANBO_DIEMUON_GIANDO",
            target: ".e-diagramcontent",
            iconCss: "fa fa-info-circle",
          },
          {
            text: "Lưu bố cục giản đồ",
            id: "MENU_ITEM_LUU_BOCUC_GIANDO",
            target: ".e-diagramcontent",
            iconCss: "fa fa-info-circle",
          },
          {
            text: "Làm tươi",
            id: "MENU_ITEM_LAMTUOI",
            target: ".e-diagramcontent",
            iconCss: "fa fa-info-circle",
          },
        ],
        // Hides the default context menu items
        showCustomMenuOnly: true,
      },
      contextMenuOpen: function (args) {
        if (this.selectedItems.nodes.length > 0) {
          // Hiện menu tuỳ thuộc vào đối tượng node
          if (diagramInstance.selectedItems.nodes[0].Group === "KETCUOI") {
            args.hiddenItems.push("MENU_ITEM_DAU_NOI_THIET_BI");
            args.hiddenItems.push("MENU_ITEM_SEPARATOR_1");
            args.hiddenItems.push("MENU_DOI_BIEU_TUONG");
            args.hiddenItems.push("MENU_DOI_DANH_DAU");
            // disable KETCUOI khi KETCUOI có ràng buộc liên kết
            let node_id = diagramInstance.selectedItems.nodes[0].id;
            if (
              diagramInstance.connectors.filter(
                (item) => item.targetID === node_id || item.sourceID === node_id
              ).length > 0
            ) {
              let xoa_ketcuoi = document.getElementById("MENU_XOA_KET_CUOI");
              xoa_ketcuoi.style.pointerEvents = "none";
              xoa_ketcuoi.style.opacity = "0.6";
            }
          } else if (diagramInstance.selectedItems.nodes[0].Group === "TRAM_TB") {
            args.hiddenItems.push("MENU_DAU_NOI_CAP");
            args.hiddenItems.push("MENU_XOA_KET_CUOI");
          }
          //thay doi thiet bi
          args.hiddenItems.push("MENU_ITEM_SP_THAYDOI_TBI");
          args.hiddenItems.push("MENU_ITEM_THAYDOI_TBI_NGUON");
          args.hiddenItems.push("MENU_ITEM_THAYDOI_TBI_DICH");
          args.hiddenItems.push("MENU_ITEM_XOA_DIEMUON_CAP");
          args.hiddenItems.push("MENU_ITEM_THEM_DIEMUON_CAP");
          args.hiddenItems.push("MENU_ITEM_XOA_TOANBO_DIEMUON");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_2");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_3");
          args.hiddenItems.push("MENU_ITEM_CHEN_KETCUOI");
          args.hiddenItems.push("MENU_ITEM_CHEN_KETCUOI_DIAGRAM");
          args.hiddenItems.push("MENU_ITEM_CHITIET_DAUNOI");
          args.hiddenItems.push("MENU_ITEM_CHUYEN_KIEUCAP");
          args.hiddenItems.push("MENU_ITEM_CHUYEN_KIEU_DI_CAP");
          args.hiddenItems.push("MENU_ITEM_XOA_CAP");
          args.hiddenItems.push("MENU_ITEM_TAO_KETCUOI");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_4");
          args.hiddenItems.push("MENU_ITEM_BOCUC_GIANDO");
          args.hiddenItems.push("MENU_ITEM_LUU_BOCUC_GIANDO");
          args.hiddenItems.push("MENU_ITEM_LAMTUOI");
          args.hiddenItems.push("MENU_ITEM_THUOC_TINH_CAP");
          args.hiddenItems.push("MENU_ITEM_XOA_TOANBO_DIEMUON_GIANDO");
        } else if (this.selectedItems.connectors.length > 0) {
          args.hiddenItems.push("MENU_ITEM_DAU_NOI_THIET_BI");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_1");
          args.hiddenItems.push("MENU_DOI_BIEU_TUONG");
          args.hiddenItems.push("MENU_DOI_DANH_DAU");
          args.hiddenItems.push("MENU_DAU_NOI_CAP");
          args.hiddenItems.push("MENU_XOA_KET_CUOI");
          args.hiddenItems.push("MENU_TAO_CAP_TOI");
          args.hiddenItems.push("MENU_ITEM_THUOC_TINH");
          args.hiddenItems.push("MENU_ITEM_CHEN_KETCUOI_DIAGRAM");
          args.hiddenItems.push("MENU_ITEM_TAO_KETCUOI");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_4");
          args.hiddenItems.push("MENU_ITEM_BOCUC_GIANDO");
          args.hiddenItems.push("MENU_ITEM_LUU_BOCUC_GIANDO");
          args.hiddenItems.push("MENU_ITEM_LAMTUOI");
          args.hiddenItems.push("MENU_ITEM_XOA_TOANBO_DIEMUON_GIANDO");
          // console.log("11",diagramInstance.selectedItems.connectors[0].segments.map(item => ({x:item.point.x, y:item.point.y})))
          // Disable Thêm điểm uốn, Xóa toàn bộ điểm uốn (cáp) khi layout không phải custom
          if (diagramInstance.layout.type !== null) {
            let them_diemuon = document.getElementById("MENU_ITEM_THEM_DIEMUON_CAP");
            them_diemuon.style.pointerEvents = "none";
            them_diemuon.style.opacity = "0.6";
            let xoa_toanbo_diemuon = document.getElementById(
              "MENU_ITEM_XOA_TOANBO_DIEMUON"
            );
            xoa_toanbo_diemuon.style.pointerEvents = "none";
            xoa_toanbo_diemuon.style.opacity = "0.6";
            // hidden XOA DIEM UON CAP
            args.hiddenItems.push("MENU_ITEM_XOA_DIEMUON_CAP");
            let xoa_diemuon = document.getElementById("MENU_ITEM_XOA_DIEMUON_CAP");
            xoa_diemuon.style.pointerEvents = "none";
            xoa_diemuon.style.opacity = "0.6";
          } else {
            let them_diemuon = document.getElementById("MENU_ITEM_THEM_DIEMUON_CAP");
            them_diemuon.style = {};
            let xoa_toanbo_diemuon = document.getElementById(
              "MENU_ITEM_XOA_TOANBO_DIEMUON"
            );
            xoa_toanbo_diemuon.style = {};
            let xoa_diemuon = document.getElementById("MENU_ITEM_XOA_DIEMUON_CAP");
            if (
              this.selectedItems.connectors[0].segments.length == 1 ||
              !(
                args.event.target.nodeName == "circle" &&
                !args.event.target.classList.contains("e-diagram-endpoint-handle")
              )
            ) {
              // hidden XOA DIEM UON CAP
              args.hiddenItems.push("MENU_ITEM_XOA_DIEMUON_CAP");
              xoa_diemuon.style.pointerEvents = "none";
              xoa_diemuon.style.opacity = "0.6";
            } else {
              xoa_diemuon.style.pointerEvents = "";
              xoa_diemuon.style.opacity = "1";
              // hidden all MENU CAP except MENU_ITEM_XOA_DIEMUON_CAP
              args.hiddenItems.push("MENU_ITEM_THEM_DIEMUON_CAP");
              args.hiddenItems.push("MENU_ITEM_XOA_TOANBO_DIEMUON");
              args.hiddenItems.push("MENU_ITEM_SEPARATOR_2");
              args.hiddenItems.push("MENU_ITEM_SEPARATOR_3");
              args.hiddenItems.push("MENU_ITEM_CHEN_KETCUOI");
              args.hiddenItems.push("MENU_ITEM_CHITIET_DAUNOI");
              args.hiddenItems.push("MENU_ITEM_CHUYEN_KIEUCAP");
              args.hiddenItems.push("MENU_ITEM_CHUYEN_KIEU_DI_CAP");
              args.hiddenItems.push("MENU_ITEM_XOA_CAP");
              args.hiddenItems.push("MENU_ITEM_THUOC_TINH_CAP");
            }
          }
        } else {
          //thay doi thiet bi
          args.hiddenItems.push("MENU_ITEM_SP_THAYDOI_TBI");
          args.hiddenItems.push("MENU_ITEM_THAYDOI_TBI_NGUON");
          args.hiddenItems.push("MENU_ITEM_THAYDOI_TBI_DICH");
          args.hiddenItems.push("MENU_ITEM_DAU_NOI_THIET_BI");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_1");
          args.hiddenItems.push("MENU_DOI_BIEU_TUONG");
          args.hiddenItems.push("MENU_DOI_DANH_DAU");
          args.hiddenItems.push("MENU_TAO_CAP_TOI");
          args.hiddenItems.push("MENU_ITEM_CHEN_KETCUOI");
          args.hiddenItems.push("MENU_DAU_NOI_CAP");
          args.hiddenItems.push("MENU_XOA_KET_CUOI");
          args.hiddenItems.push("MENU_ITEM_THUOC_TINH");
          args.hiddenItems.push("MENU_ITEM_XOA_TOANBO_DIEMUON");
          args.hiddenItems.push("MENU_ITEM_XOA_DIEMUON_CAP");
          args.hiddenItems.push("MENU_ITEM_THEM_DIEMUON_CAP");
          args.hiddenItems.push("MENU_ITEM_THUOC_TINH_CAP");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_2");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_3");
          args.hiddenItems.push("MENU_ITEM_SEPARATOR_4");
          args.hiddenItems.push("MENU_ITEM_CHITIET_DAUNOI");
          args.hiddenItems.push("MENU_ITEM_CHUYEN_KIEUCAP");
          args.hiddenItems.push("MENU_ITEM_CHUYEN_KIEU_DI_CAP");
          args.hiddenItems.push("MENU_ITEM_XOA_CAP");
          // Disable Lưu bố cục khi layout không phải custom
          if (diagramInstance.layout.type !== null) {
            let luu_bocuc = document.getElementById("MENU_ITEM_LUU_BOCUC_GIANDO");
            luu_bocuc.style.pointerEvents = "none";
            luu_bocuc.style.opacity = "0.6";
          }
          // args.cancel = true;
        }
      },
      commandManager: {
        commands: [
          {
            name: "copy",
            canExecute: function () {
              return false;
            },
          },
          {
            name: "paste",
            canExecute: function () {
              return false;
            },
          },
          {
            name: "cut",
            canExecute: function () {
              return false;
            },
          },
          {
            name: "delete",
            canExecute: function () {
              return false;
            },
          },
        ],
      },
    };
  },
  provide: {
    diagram: [
      DiagramContextMenu,
      ComplexHierarchicalTree,
      HierarchicalTree,
      LineDistribution,
    ],
  },
  watch: {},
  computed: {},
  mounted: function () {
    diagramInstance = this.$refs.diagram.ej2Instances;
    diagramInstance.fitToPage();
  },
  created() {},
  methods: {
    ...mapActions("cabman/QuanLyMangTruyenDan", actionName),
    contextMenuClick: function (args) {
      /***
       * HANDLE MENU TRẠM/KẾT CUỐI
       */
      console.log(" MENU TRẠM/KẾT CUỐI", args);
      // ĐỔI BIỂU TƯỢNG
      // console.log(diagramInstance.selectedItems.connectors[0])
      if (args.element.id === "ICON_HOST") {
        let idx = diagramInstance.nodes.findIndex(
          (x) => x.id.toString() === diagramInstance.selectedItems.nodes[0].id.toString()
        );
        if (idx > -1) {
          this.mniDoiBieuTuong_Click(
            parseInt(diagramInstance.nodes[idx].id.split("TRAM_TB_")[1], 10),
            "HOST"
          );
        }
      }
      if (args.element.id === "ICON_MSAN_BTS") {
        let idx = diagramInstance.nodes.findIndex(
          (x) => x.id.toString() === diagramInstance.selectedItems.nodes[0].id.toString()
        );
        if (idx > -1) {
          this.mniDoiBieuTuong_Click(
            parseInt(diagramInstance.nodes[idx].id.split("TRAM_TB_")[1], 10),
            "MSAN_RSU_BTS"
          );
        }
      }
      if (args.element.id === "ICON_MSAN_NO_BTS") {
        let idx = diagramInstance.nodes.findIndex(
          (x) => x.id.toString() === diagramInstance.selectedItems.nodes[0].id.toString()
        );
        if (idx > -1) {
          this.mniDoiBieuTuong_Click(
            parseInt(diagramInstance.nodes[idx].id.split("TRAM_TB_")[1], 10),
            "MSAN_RSU"
          );
        }
      }
      if (args.element.id === "ICON_BTS") {
        let idx = diagramInstance.nodes.findIndex(
          (x) => x.id.toString() === diagramInstance.selectedItems.nodes[0].id.toString()
        );
        if (idx > -1) {
          this.mniDoiBieuTuong_Click(
            parseInt(diagramInstance.nodes[idx].id.split("TRAM_TB_")[1], 10),
            "BTS"
          );
        }
      }
      // ĐỔI ĐÁNH DẤU
      if (args.element.id === "NO_TICK") {
        this.mniDanhDau_Click(null, false);
      }
      if (args.element.id === "TICK_1") {
        this.mniDanhDau_Click(1, true);
      }
      if (args.element.id === "TICK_2") {
        this.mniDanhDau_Click(2, true);
      }
      if (args.element.id === "TICK_3") {
        this.mniDanhDau_Click(3, true);
      }
      // Tạo cáp tới
      if (args.element.id === "MENU_TAO_CAP_TOI") {
        // CHO KETCUOi / TRAM_TB
        let dsketcuoi = diagramInstance.nodes
          .filter(
            (item) =>
              item.id !== diagramInstance.selectedItems.nodes[0].id &&
              item.Group === "KETCUOI"
          )
          .map((item) => item.id.split("KETCUOI_")[1])
          .toString();
        let dstram = diagramInstance.nodes
          .filter((item) => item.Group === "TRAM_TB")
          .map((item) => item.id.split("TRAM_TB_")[1].split("_")[0])
          .toString();
        this.listObj = {
          dsketcuoi,
          dstram,
        };
        this.$bvModal.show("popupChonDoiTuongDichGianDo");
        // let dsketcuoi = '536268,536272, 536275, 536278, 536279'
        // let dstram = '331,332,333, 334, 335, 336, 337, 338, 339, 340,341'
        // this.listObj = {dsketcuoi, dstram}
        // this.$bvModal.show('popupChonDoiTuongDichGianDo')
      }
      // Xóa kết cuối
      if (args.element.id === "MENU_XOA_KET_CUOI") {
        this.mniXoaKetCuoi_Click(diagramInstance.selectedItems.nodes[0]);
      }
      // Đấu nối kết cuối
      if (args.element.id === "MENU_DAU_NOI_CAP") {
        this.mniDauNoiCap_Click(diagramInstance.selectedItems.nodes[0]);
      }
      /***
       * HANDLE MENU CAP
       */
      //Thay đổi thiết bị nguồn
      if (args.element.id === "MENU_ITEM_THAYDOI_TBI_NGUON") {
        this.thayDoiTBI({ nguon: 1 });
      }
      //Thay đổi thiết bị đích
      if (args.element.id === "MENU_ITEM_THAYDOI_TBI_DICH") {
        this.thayDoiTBI({ nguon: 0 });
      }
      // TẠO ĐIỂM UỐN
      if (args.element.id === "MENU_ITEM_THEM_DIEMUON_CAP") {
        // this.$emit(
        //   "mniThemDiemUonGD_Click",
        //   diagramInstance.selectedItems.connectors[0],
        //   this.currentOffset
        // );
        this.mniThemDiemUonGD_Click(this.currentOffset);
      }
      // XÓA ĐIỂM UỐN
      if (args.element.id === "MENU_ITEM_XOA_DIEMUON_CAP") {
        this.mniXoaDiemUon_Click(this.currentOffset);
      }
      // XÓA TOÀN BỘ ĐIỂM UỐN
      if (args.element.id === "MENU_ITEM_XOA_TOANBO_DIEMUON") {
        // XÓA TOÀN BỘ ĐIỂM UỐN - CÁP
        // this.$emit(
        //   "mniXoaToanBoDU_Click",
        //   diagramInstance.selectedItems.connectors[0]
        // );
        this.mniXoaToanBoDU_Click(diagramInstance.selectedItems.connectors[0]);
      }
      // XÓA TOÀN BỘ ĐIỂM UỐN - GIẢN ĐỒ
      if (args.element.id === "MENU_ITEM_XOA_TOANBO_DIEMUON_GIANDO") {
        // this.$emit("mniXoaTBDU_GianDo_Click");
        this.mniXoaTBDU_GianDo_Click();
      }
      // CHI TIẾT ĐẤU NỐI
      if (args.element.id === "MENU_ITEM_CHITIET_DAUNOI") {
        this.capObject.CAP_ID = parseInt(
          diagramInstance.selectedItems.connectors[0].id,
          10
        );
        this.capObject.MIDSPAN_ID = 0;
        this.$bvModal.show("popupChiTietDauNoiGianDo");
      }
      // CHUYỂN KIỂU CÁP
      if (args.element.id === "MENU_ITEM_CAP_THUEBAO") {
        this.$emit(
          "mniChuyenKieuCap_Click",
          0,
          parseInt(diagramInstance.selectedItems.connectors[0].id, 10)
        );
      }
      if (args.element.id === "MENU_ITEM_CAP_TRUYENDAN") {
        this.$emit(
          "mniChuyenKieuCap_Click",
          1,
          parseInt(diagramInstance.selectedItems.connectors[0].id, 10)
        );
      }
      // CHUYỂN KIỂU ĐI CÁP
      if (args.element.id === "MENU_ITEM_CHUYEN_KIEU_DI_CAP_KEOCONG") {
        this.mniChuyenKieuDiCap_Click(2);
      }
      if (args.element.id === "MENU_ITEM_CHUYEN_KIEU_DI_CAP_KEOTREO") {
        this.mniChuyenKieuDiCap_Click(3);
      }
      if (args.element.id === "MENU_ITEM_CHUYEN_KIEU_DI_CAP_CHON") {
        this.mniChuyenKieuDiCap_Click(4);
      }
      if (args.element.id === "MENU_ITEM_CHUYEN_KIEU_DI_CAP_DEXUAT_KEOMOI") {
        this.mniChuyenKieuDiCap_Click(5);
      }
      if (args.element.id === "MENU_ITEM_XOA_CAP") {
        this.$emit("mniXoaCap_Click", diagramInstance.selectedItems.connectors[0]);
      }
      if (args.element.id === "MENU_ITEM_CHEN_KETCUOI") {
        var tracnghiem = {
          title: "Chèn kết cuối",
          caption: "Chọn loại kết cuối muốn chèn",
          options: ["Kết cuối đã nối cáp", "Kết cuối chưa nối cáp", "Kết cuối mới"],
          // Chỗ data này là cáp ID
          data: 123,
        };
        this.$refs.ModalTracnghiemExt.show(tracnghiem).then((result) => {
          console.log(result);
          switch (result.option) {
            case 0:
              // this.$refs.ModalChonDSKetCuoiExt.show()
              this.$refs.ModalChonDSKcExt.show(0).then((result) => {
                let data = {};
                data.KETCUOI_ID = result.doituong_id;
                data.CAP_ID = parseInt(diagramInstance.selectedItems.connectors[0].id);
                this.onChonKetCuoi(data);
              });
              break;
            case 1:
              this.$refs.ModalChonDSKcExt.show(1).then((result) => {
                let data = {};
                data.KETCUOI_ID = result.doituong_id;
                data.CAP_ID = parseInt(diagramInstance.selectedItems.connectors[0].id);
                this.onChonKetCuoi(data);
              });
              break;
            case 2:
              this.$refs.QLKetCuoiGianDo.show(true, null, true).then(async (result) => {
                if (result.ok) {
                  let data = {};
                  data.KETCUOI_ID = result.data.KETCUOI_ID;
                  data.CAP_ID = parseInt(diagramInstance.selectedItems.connectors[0].id);
                  await this.onChonKetCuoi(data);
                } else {
                }
              });
              break;
          }
        });
      }
      if (args.element.id === "MENU_ITEM_CHEN_KETCUOI_DIAGRAM") {
        console.log("xxx", "voo ddaay chuaw");
        var tracnghiem = {
          title: "Chèn kết cuối",
          caption: "Chọn loại kết cuối muốn chèn",
          options: ["Kết cuối đã nối cáp", "Kết cuối chưa nối cáp"],
          // Chỗ data này là cáp ID
          data: 123,
        };
        this.$refs.ModalTracnghiemExt.show(tracnghiem).then((result) => {
          this.$refs.ModalChonDSKcExt.show().then(async (result) => {
            console.log("xxx", result);
            let ketCuoiId = result.doituong_id;
            let point = new Point();
            if (result.doituong_id) {
              let rs = await this.LAY_TT_KETCUOI(ketCuoiId);
              console.log("rs  ss", rs);
              point = this.TimPoint("KETCUOI_" + ketCuoiId.toString());
              if (Object.keys(point).length === 0) {
                point = this.TaoPoint(
                  "KETCUOI_" + ketCuoiId.toString(),
                  rs.TEN_KC,
                  "MANG_XONG",
                  rs.TEN_KC
                );
                point.Group = "KETCUOI";
                point.offsetX = this.currentOffset.x;
                point.offsetY = this.currentOffset.y;
                point.ImageKey = "SPLITTER";
                point.Name = rs.TEN_KC;
                point.DisplayName = rs.TEN_KC;
                point.Size = {
                  width: 24,
                  height: 24,
                };
                point.AllowMove = true;
                point.RootId = -1;
                point.Position = this.currentOffset;
                this.CapNhatPoint(point);
                this.DataBind();
              } else {
                this.$root.$toast.error("Kết cuối đã có trên giản đồ");
              }
            }
          });
        });
      }
      // Đấu nối
      if (args.element.id === "MENU_ITEM_DAU_NOI_THIET_BI") {
        if (diagramInstance.selectedItems.nodes[0].Group === "TRAM_TB") {
          let tram_tbi_id = diagramInstance.selectedItems.nodes[0].id.split("_")[2];
          this.doiTuong = {
            tramtb_id: tram_tbi_id,
          };
          this.$bvModal.show("popupDauNoiThietBiGianDo");
        }
      }
      // Thuộc tính trạm, kết cuối
      if (args.element.id === "MENU_ITEM_THUOC_TINH") {
        if (diagramInstance.selectedItems.nodes[0].Group === "KETCUOI") {
          var ketcuoi_id = parseInt(
            diagramInstance.selectedItems.nodes[0].id.split("_")[1]
          );
          console.log("ketcuoi_id", ketcuoi_id);
          this.quanLyKetCuoi(ketcuoi_id);
        } else if (diagramInstance.selectedItems.nodes[0].Group === "TRAM_TB") {
          this.tramTb_id = parseInt(
            diagramInstance.selectedItems.nodes[0].id.split("_")[2]
          );
          this.$bvModal.show("popupTramThietBiGianDo");
        }
      }
      if (args.element.id === "MENU_ITEM_THUOC_TINH_CAP") {
        if (diagramInstance.selectedItems.connectors[0].Group === "CAP") {
          console.log(diagramInstance.connectors[0].RootId);
          this.thongTinCap.capObj.CAP_ID = parseInt(
            diagramInstance.selectedItems.connectors[0].id.toString(),
            10
          );
          this.thongTinCap.taomoi = false;
          this.thongTinCap.editable = true;
          this.$bvModal.show("popupQuanLyCapGianDo");
        }
      }
      /***
       * HANDLE MENU DIAGRAM
       */
      // TẠO KẾT CUỐI
      if (args.element.id === "MENU_ITEM_TAO_KETCUOI") {
        this.mniTaoKetCuoi_Click();
      }
      // THAY ĐỔI BỐ CỤC
      if (args.element.id === "MENU_ITEM_BOCUC_GIANDO_CHTree") {
        this.$emit("CAPNHAT_BOCUC_GIANDO", 1);
      }
      if (args.element.id === "MENU_ITEM_BOCUC_GIANDO_TUYCHINH") {
        // args.item.iconCss = "fa fa-check"
        this.$emit("CAPNHAT_BOCUC_GIANDO", 8);
      }
      // LƯU BỐ CỤC GIẢN ĐỒ
      if (args.element.id === "MENU_ITEM_LUU_BOCUC_GIANDO") {
        this.$emit("mniLuuBoCuc_Click");
      }
      // LÀM TƯƠI GIẢN ĐỒ
      if (args.element.id === "MENU_ITEM_LAMTUOI") {
        this.$emit("mniLamTuoi_Click");
      }
    },
    async onChonKetCuoi(data) {
      let ketcuoi_id_new = data.KETCUOI_ID;
      let cap_id = data.CAP_ID;
      if (ketcuoi_id_new !== null) {
        let chieudaicap = 100;
        let rs = await this.$root.context.post(
          "/web-cabman/ban-do-mang-cap/chieu-dai-cap?cap_id=" + cap_id
        );
        if (rs.data && rs.data.length > 0) {
          chieudaicap = parseInt(rs.data[0].CHIEUDAI);
        }
        console.log("chieudaicap", chieudaicap);
        var cap1Length = Math.round(chieudaicap / 2);
        var cap2Length = Math.round(chieudaicap - cap1Length);
        console.log("cap1Length 1", cap1Length);
        console.log("cap2Length 1", cap2Length);
        this.$refs.ModalPhanChiaDoanCapChenKetCuoi.show(chieudaicap).then((result) => {
          if (result.ok) {
            cap1Length = result.data.chieudaicap_truoc;
            cap2Length = result.data.chieudaicap_sau;
          } else {
          }
          console.log("cap1Length 2", cap1Length);
          console.log("cap2Length 2", cap2Length);
          this.$confirm(
            `Sau khi chèn kết cuối thành công, thông tin đấu nối vào đích của cáp hiện tại sẽ bị gỡ bỏ. Bạn có chắc muốn thực hiện ?`,
            "Giản đồ mạng cáp",
            {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
              type: "warning",
            }
          )
            .then(() => {
              this.loading(true);
              this.$root.context
                .post(
                  `/web-cabman/ban-do-mang-cap/chen-ket-cuoi-v2?cap_id=${cap_id}&ketcuoi_id=${ketcuoi_id_new}&dodaicapmoi=${cap2Length}`
                )
                .then((rep) => {
                  this.loading(false);
                  this.$root.$toast.success("Chèn kết cuối thành công");
                  // EventBus.$emit('NAP_GIANDO', 'NAP_GIANDO')
                  this.$emit("nap_gian_do");
                })
                .catch((e) => {
                  this.loading(false);
                  this.$root.$toast.error(e.message);
                });
            })
            .catch((e) => {});
        });
      }
    },
    // chọn đối tượng tạo cáp tới
    ChonDTisOK(data) {
      console.log("data from child ", data);
      // console.log('Nodes ', this.Nodes)
      // return data
      let toObj = diagramInstance.nodes.filter(
        (item) => item.id === data.loai_dt + "_" + data.doituong_id
      );
      if (toObj.length > 0) {
        let fromObj = diagramInstance.selectedItems.nodes[0];
        toObj = toObj[0];
        this.$emit("diaGianDo_ObjectAdded", "Connector", fromObj, toObj);
      }
    },
    QuanLyTramThietBiIsOk(data) {
      let tramtb = diagramInstance.selectedItems.nodes[0];
      console.log("QuanLyTramThietBiIsOk", data);
      this.$emit("SUA_THUOCTINH_TRAM_TB", tramtb.id, data);
    },
    // show form quản lý kết cuối
    async quanLyKetCuoi(ketcuoi_id) {
      console.log("vô đây", ketcuoi_id);
      var ketcuoi = await this.LAY_TT_KETCUOI(ketcuoi_id);
      if (ketcuoi) {
        this.$refs.QLKetCuoiGianDo.show(false, ketcuoi, false).then(async (result) => {
          if (result.ok) {
            let ketcuoi_data = await this.LAY_TT_KETCUOI(ketcuoi_id);
            this.$emit("SUA_THUOCTINH_KETCUOI", ketcuoi_data);
          } else {
          }
        });
      }
    },
    mniXoaKetCuoi_Click(ketcuoi) {
      let ketcuoi_id = parseInt(ketcuoi.id.split("KETCUOI_")[1], 10);
      this.$bvModal
        .msgBoxConfirm(
          "Bạn có thực sự muốn xóa kết cuối [" + ketcuoi.Name + "] không ?",
          {
            title: "Xác nhận hành động",
            centered: true,
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        )
        .then(async (value) => {
          if (value) {
            this.$root.showLoading(true);
            await this.$root.context
              .post(`web-cabman/mang-truyen-dan/xoa-ketcuoi-v2/${ketcuoi_id}`)
              .then((res) => {
                if (res.error === "200" && res.data.result === true) {
                  this.$root.toastSuccess("Xóa kết cuối thành công");
                  diagramInstance.remove(ketcuoi);
                  diagramInstance.dataBind();
                }
              })
              .catch((e) => {
                this.$root.toastError(e.response.data.message_detail);
              })
              .finally(() => {
                this.$root.showLoading(false);
              });
          } else {
          }
        });
    },
    mniDauNoiCap_Click(ketcuoi) {
      let node = {
        KETCUOI_ID: ketcuoi.id,
        KYHIEU_KC: ketcuoi.Name,
      };
      this.$refs.frmDauNoiKC.show(node);
    },
    // lấy Trạng thái kết cuối
    async LAY_TT_KETCUOI(ketcuoi_id) {
      try {
        console.log("vô đây", ketcuoi_id);
        // let rs = await this.api_execute({
        //   data: {
        //     VPHANVUNG_ID: this.$root.token.getPhanVungID(),
        //     VKETCUOI_ID: ketcuoi_id
        //   },
        //   function: 'CABMAN.DANHMUC_TD_V2.LAY_TT_KETCUOI'
        // })
        let rs = await this.$root.context.get(
          "web-cabman/mang-truyen-dan/lay-thongtin-ketcuoi",
          {
            ketcuoiId: ketcuoi_id,
          }
        );
        console.log("LAY_TT_KETCUOI", rs.data);
        if (rs.data.length) return rs.data[0];
      } catch (error) {
        console.log("vô đây", error);
        return null;
      }
    },
    /***
     * HANLE MENU CÁP
     */
    // THÊM ĐIỂM UỐN - CÁP
    mniThemDiemUonGD_Click(position) {
      function ThreePointStraightLine(p1, p2, p3) {
        let x1 = p1.x,
          y1 = p1.y;
        let x2 = p2.x,
          y2 = p2.y;
        let x3 = p3.x,
          y3 = p3.y;
        // Formula for area of triangle is :
        // let rs = 0.5 * [x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)]
        // console.log(rs)
        // check p3 nằm giữa p1, p2 và thẳng hàng
        let p1p3_distance = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
        let p2p3_distance = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        let p1p2_distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        console.log(
          p1p2_distance,
          p1p3_distance + p2p3_distance,
          p1p2_distance - (p1p3_distance + p2p3_distance)
        );
        // +-1000: threshold của 3 điểm thẳng hàng, +-20: threshold p3 nằm giữa p1, p2 -> ~ 0
        return (
          /* Math.abs(rs) < 1000 && */ Math.abs(
            p1p2_distance - (p1p3_distance + p2p3_distance)
          ) < 20
        );
      }
      let idx = diagramInstance.connectors.findIndex(
        (x) => x.id === diagramInstance.selectedItems.connectors[0].id
      );
      if (idx > 1) {
        let cap = diagramInstance.connectors[idx];
        // get ds điểm uốn
        let diemuon_list = diagramInstance.connectors[idx].segments.map(
          (item) => item.point
        );
        let segments = [...diemuon_list];
        diemuon_list.pop(); // loại bỏ point mặc đinh 0 0
        diemuon_list.unshift({
          x: cap.sourcePoint.x,
          y: cap.sourcePoint.y,
        });
        diemuon_list.push({
          x: cap.targetPoint.x,
          y: cap.targetPoint.y,
        });
        if (diemuon_list.length >= 2) {
          for (let i = 0; i < diemuon_list.length - 1; i++) {
            if (ThreePointStraightLine(diemuon_list[i], diemuon_list[i + 1], position)) {
              segments.splice(i, 0, position);
              break;
            }
          }
        }
        if (segments.length > 0) {
          diagramInstance.connectors[idx].segments = segments.map((item) => ({
            type: "Straight",
            point: item,
            allowDrag: true,
          }));
        }
        // diagramInstance.resetSegments()
        diagramInstance.dataBind();
      }
    },
    // Xóa ĐIỂM UỐN - CÁP
    mniXoaDiemUon_Click(args) {
      console.log("mniXoaDiemUon_Click: ", args);
      console.log("connectors: ", diagramInstance.selectedItems.connectors[0]);
      if (diagramInstance.selectedItems.connectors.length > 0) {
        let segments = diagramInstance.selectedItems.connectors[0].segments;
        if (segments.length > 1) {
          let chkIndex = -1;
          let khoangCach = -1;
          // let point1 = null;
          // let point2 = null;
          for (let i = 0; i < segments.length; i++) {
            const segment = segments[i];
            if (chkIndex == -1) {
              chkIndex = i;
              khoangCach = this.tinhKhoangCach(args, segment.point);
              // point1 = segment.points[0];
              // point2 = segment.points[1];
            } else {
              let newKhoangCach = this.tinhKhoangCach(args, segment.point);
              if (newKhoangCach < khoangCach) {
                chkIndex = i;
                khoangCach = newKhoangCach;
                // point1 = segment.points[0];
                // point2 = segment.points[1];
              }
            }
            console.log(this.tinhKhoangCach(args, segment.point));
          }
          if (chkIndex > -1) {
            segments.splice(chkIndex, 1);
            // for (let i = 0; i < segments.length; i++) {
            //   let sp1 = segments[i].points[0];
            //   let sp2 = segments[i].points[1];
            //   if (
            //     (sp1.x == point1.x && sp1.y == point1.y) ||
            //     (sp1.x == point2.x && sp1.y == point2.y) ||
            //     (sp2.x == point1.x && sp2.y == point1.y) ||
            //     (sp2.x == point2.x && sp2.y == point2.y)
            //   ) {
            //     if (!(sp1.x == point1.x && sp1.y == point1.y)) {
            //       sp1.x = point1.x;
            //       sp1.y = point1.y;
            //     }
            //     else if (!(sp1.x == point2.x && sp1.y == point2.y)) {
            //       sp1.x = point2.x;
            //       sp1.y = point2.y;
            //     }
            //     else if (!(sp2.x == point1.x && sp2.y == point1.y)) {
            //       sp2.x = point1.x;
            //       sp2.y = point1.y;
            //     }
            //     else if (!(sp2.x == point2.x && sp2.y == point2.y)) {
            //       sp2.x = point2.x;
            //       sp2.y = point2.y;
            //     }
            //     //segments[i].point.x = sp1.x;
            //     //segments[i].point.y = sp1.y;
            //     console.log(11111111);
            //     break;
            //   }
            // }
            diagramInstance.selectedItems.connectors[0].segments = segments;
            // diagramInstance.refresh();
            // diagramInstance.dataBind();
          }
        }
      }
    },
    // XÓA TOÀN BỘ ĐIỂM UỐN - CÁP
    mniXoaToanBoDU_Click(cap) {
      let idx = diagramInstance.connectors.findIndex((x) => x.id === cap.id);
      if (idx > -1) {
        diagramInstance.connectors[idx].segments = [];
        diagramInstance.dataBind();
      }
    },
    // CHUYỂN KIỂU CÁP
    mniChuyenKieuCap_Click(KIEU) {
      // THAY ĐỔI TRÊN GIẢN ĐỒ
      let idx = diagramInstance.connectors.findIndex(
        (x) =>
          x.id.toString() === diagramInstance.selectedItems.connectors[0].id.toString()
      );
      if (idx > -1) {
        const CAP_THUEBAO = 0,
          CAP_TRUYENDAN = 1;
        if (KIEU === CAP_THUEBAO) {
          diagramInstance.connectors[idx].style.strokeWidth = 2;
          // cập nhật thông tin cáp
          diagramInstance.connectors[idx].Width = 1;
          diagramInstance.connectors[idx].SelectedWidth = 2;
          diagramInstance.connectors[idx].style.strokeWidth =
            diagramInstance.connectors[idx].SelectedWidth;
        } else if (KIEU === CAP_TRUYENDAN) {
          // cập nhật thông tin cáp
          diagramInstance.connectors[idx].Width = 3;
          diagramInstance.connectors[idx].SelectedWidth = 5;
          diagramInstance.connectors[idx].style.strokeWidth =
            diagramInstance.connectors[idx].SelectedWidth;
        }
        diagramInstance.dataBind();
      }
    },
    // CHUYỂN KIỂU ĐI CÁP
    async mniChuyenKieuDiCap_Click(KIEUDC_ID) {
      let idx = diagramInstance.connectors.findIndex(
        (x) =>
          x.id.toString() === diagramInstance.selectedItems.connectors[0].id.toString()
      );
      if (idx > -1) {
        // CAPNHAT_KIEU_DC
        try {
          await this.$root.context
            .post("web-cabman/mang-truyen-dan/capnhat-kieudc", {
              KIEUDC_ID: KIEUDC_ID,
              CAP_ID: parseInt(diagramInstance.selectedItems.connectors[0].id, 10),
            })
            .then((rs) => {
              if (
                rs.error === "200" &&
                rs.error_code === "BSS-00000000" &&
                rs.data.result === true
              ) {
                // THAY ĐỔI TRÊN GIẢN ĐỒ
                const KIEUDICAP_KEOCONG = 2,
                  KIEUDICAP_KEOTREO = 3,
                  KIEUDICAP_CHON = 4,
                  KIEUDICAP_DEXUAT_KEOMOI = 5;
                let con = diagramInstance.connectors[idx];
                switch (KIEUDC_ID) {
                  case KIEUDICAP_KEOCONG:
                    con.DashStyle = "Dash";
                    con.SelectedDashStyle = "Dash";
                    break;
                  case KIEUDICAP_KEOTREO:
                    con.DashStyle = "Solid";
                    con.SelectedDashStyle = "Solid";
                    break;
                  case KIEUDICAP_CHON:
                    con.DashStyle = "DashDotDot";
                    con.SelectedDashStyle = "DashDotDot";
                    break;
                  case KIEUDICAP_DEXUAT_KEOMOI:
                    con.DashStyle = "Dot";
                    con.SelectedDashStyle = "Dot";
                    break;
                }
                this.CapNhatLink(con);
              }
            })
            .catch((e) => {
              this.$root.toastError("Có lỗi xảy ra khi cập nhật kiểu đi cáp");
              throw e;
            });
        } catch (e) {
          console.log(e);
        }
      }
    },
    // XÓA CÁP
    mniXoaCap_Click(cap) {
      diagramInstance.remove(cap);
      diagramInstance.dataBind();
    },
    // Nhận thông tin update từ QLCap
    QLCapIsOk(...args) {
      const [TYPE, DATA] = args;
      this.$emit("QLCapIsOk", TYPE, DATA);
    },
    /***
     *HANDLE MENU TRẠM
     */
    // ĐỔI BIỂU TƯỢNG
    async mniDoiBieuTuong_Click(TRAMTB_ID, ICON) {
      if (TRAMTB_ID && ICON) {
        await this.$root.context
          .post("web-cabman/mang-truyen-dan/capnhat-icon-tramtb", {
            TRAMTB_ID: TRAMTB_ID,
            ICON: ICON,
          })
          .then((rs) => {
            if (
              rs.error === "200" &&
              rs.error_code === "BSS-00000000" &&
              rs.data.result === true
            ) {
              let idx = diagramInstance.nodes.findIndex(
                (x) =>
                  x.id.toString() === diagramInstance.selectedItems.nodes[0].id.toString()
              );
              if (idx > -1) {
                diagramInstance.nodes[idx].shape.source =
                  "/static/icons/icon100x100/" + ICON + ".jpg";
                diagramInstance.nodes[idx].ImageKey = ICON;
                diagramInstance.dataBind();
              }
            }
          })
          .catch((e) => this.$root.toastError("Có lỗi xảy ra khi đổi biểu tượng"));
      }
    },
    // ĐỔI ĐÁNH DẤU
    mniDanhDau_Click(type, isMark) {
      let idx = diagramInstance.nodes.findIndex(
        (x) => x.id.toString() === diagramInstance.selectedItems.nodes[0].id.toString()
      );
      if (!isMark) {
        // diagramInstance.nodes[idx].style.fill = 'None'
        diagramInstance.nodes[idx].style.strokeColor = "None";
      } else {
        switch (type) {
          case 1: // RED
            // diagramInstance.nodes[idx].style.fill = 'Red'
            diagramInstance.nodes[idx].style.strokeColor = "Red";
            break;
          case 2: // GREEN
            // diagramInstance.nodes[idx].style.fill = 'Green'
            diagramInstance.nodes[idx].style.strokeColor = "Green";
            break;
          case 3: // BLUE
            // diagramInstance.nodes[idx].style.fill = 'Blue'
            diagramInstance.nodes[idx].style.strokeColor = "Blue";
            break;
        }
        // diagramInstance.nodes[idx].borderWidth = 5
        // diagramInstance.nodes[idx].borderColor = "Red"
        diagramInstance.nodes[idx].style.strokeWidth = 3;
        diagramInstance.nodes[idx].style.bold = true;
        // diagramInstance.nodes[idx].shape.shape = "Ellipse"
        // diagramInstance.nodes[idx].shape.cornerRadius = 20
      }
      diagramInstance.dataBind();
    },
    /***
     * HANDLE MENU GIẢN ĐỒ
     */
    // TẠO KẾT CUỐI
    mniTaoKetCuoi_Click() {
      // call form frmQLKetCuoi
      let point = new Point();
      // point.id = -1
      // point.Group = 'KETCUOI'
      // point.ImageKey = 'CHUA_XAC_DINH'
      // point.AllowMove = true
      // point.Position = this.currentOffset
      point.CAPGOC_ID = -1;
      point.PHANLOAIKC_ID = 0;
      point.DONVI_ID = -1;
      this.$refs.QLKetCuoiGianDo.show(true, point, true).then(async (result) => {
        if (result.ok) {
          console.log(result.data);
          let point = await this.LAY_TT_KETCUOI(result.data.KETCUOI_ID);
          point.id = result.data.KETCUOI_ID;
          // tạo node kết cuối
          await this.$root.context
            .get("web-cabman/mang-truyen-dan/lay-ten-ketcuoi", {
              ketcuoiId: point.id,
              kieu: 0,
            })
            .then((res) => {
              point.Name = res.data.RESULT;
            });
          await this.$root.context
            .get("web-cabman/mang-truyen-dan/lay-ten-ketcuoi", {
              ketcuoiId: point.id,
              kieu: 1,
            })
            .then((res) => {
              point.DisplayName = res.data.RESULT;
            });
          this.$emit("mniTaoKetCuoi_Click", point, {
            ...this.currentOffset,
          });
        } else {
        }
      });
    },
    // XÓA TOÀN BỘ ĐIỂM UỐN _ GIẢN ĐỒ
    mniXoaTBDU_GianDo_Click() {
      this.$bvModal
        .msgBoxConfirm("Bạn có muốn xóa toàn bộ điểm uốn trên giản đồ không?", {
          title: "VNPT",
          centered: true,
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Không đồng ý",
        })
        .then((value) => {
          if (value) {
            diagramInstance.connectors.map((item) => ({
              ...item,
              segments: [],
            }));
            diagramInstance.resetSegments();
            diagramInstance.dataBind();
          } else {
          }
        });
    },
    async changeLayout(layoutType) {
      if (layoutType === "") {
        diagramInstance.layout = {};
      } else {
        diagramInstance.layout = {
          arrangement: ChildArrangement.Nonlinear, // Linear/Nonlinear
          type: layoutType,
          connectionPointOrigin: ConnectionPointOrigin.SamePoint, // SamePoint/DifferentPoint
          connectionDirection: "Orientation",
          orientation: "TopToBottom",
          verticalSpacing: 200,
          verticalAlignment: "Center",
          horizontalAlignment: "Center",
          horizontalSpacing: 150,
          margin: {
            left: 50,
            right: 50,
            top: 50,
            bottom: 50,
          },
        };
      }
      await diagramInstance.dataBind();
      // diagramInstance.clear()
      await diagramInstance.refresh();
      await diagramInstance.fitToPage();
      setTimeout(async () => {
        await diagramInstance.doLayout();
      }, 1000);
    },
    CapNhatLink(cap) {
      let idx = diagramInstance.connectors.findIndex(
        (p) => p.id.toString() === cap.id.toString()
      );
      if (idx > -1) {
        // NHOM LC
        diagramInstance.connectors[idx].style.strokeColor = cap.BackColor;
        // KIỂU ĐI CÁP
        switch (cap.DashStyle) {
          case "Dot":
            diagramInstance.connectors[idx].style.strokeDashArray = "2";
            break;
          case "Solid":
            diagramInstance.connectors[idx].style.strokeDashArray = "";
            break;
          case "Dash":
            diagramInstance.connectors[idx].style.strokeDashArray = "10";
            break;
          case "DashDotDot":
            diagramInstance.connectors[idx].style.strokeDashArray = "10,2,2";
            break;
        }
        // KIỂU CÁP
        // diagramInstance.connectors[idx].strokeWidth = cap.Width
        diagramInstance.connectors[idx].style.strokeWidth = cap.Width;
        if (cap.BoCuc != "Custom")
          if (
            diagramInstance.connectors.filter(
              (item) =>
                item.sourceID + "-" + item.targetID ===
                  cap.sourceID + "-" + cap.targetID ||
                item.sourceID + "-" + item.targetID === cap.targetID + "-" + cap.sourceID
            ).length > 1
          )
            diagramInstance.connectors[idx].type = "Orthogonal";
          else diagramInstance.connectors[idx].type = "Straight";
        else {
          diagramInstance.connectors[idx].type = "Straight";
        }
        // THÔNG TIN CÁP
        diagramInstance.connectors[idx].LOPMC_ID = cap.LOPMC_ID;
        diagramInstance.connectors[idx].BackColor = cap.BackColor;
        diagramInstance.connectors[idx].DashStyle = cap.DashStyle;
        diagramInstance.connectors[idx].DisplayName = cap.DisplayName;
        diagramInstance.connectors[idx].Group = cap.Group;
        diagramInstance.connectors[idx].Name = cap.Name;
        diagramInstance.connectors[idx].RootId = cap.RootId;
        diagramInstance.connectors[idx].SelectedBackColor = cap.SelectedBackColor;
        diagramInstance.connectors[idx].SelectedDashStyle = cap.SelectedDashStyle;
        diagramInstance.connectors[idx].SelectedWidth = cap.SelectedWidth;
        diagramInstance.connectors[idx].ShowCaption = cap.ShowCaption;
        diagramInstance.connectors[idx].Width = cap.Width;
        diagramInstance.dataBind();
      }
    },
    CapNhatBanner(type) {
      let clickedItem;
      if (type === "connectors") {
        clickedItem = diagramInstance.selectedItems.connectors[0];
      } else clickedItem = diagramInstance.selectedItems.nodes[0];
      console.log("CapNhatBanner", clickedItem);
      this.$emit("showInforSelected", clickedItem);
    },
    mouseOver: (args) => {
      // this.actualObject = args.actualObject
      // if (this.actualObject.constructor.name === 'Node') {
      //   // let connect = diagramInstance.connectors.find(x => x.id === this.actualObject.id)
      //   // console.log('mouse Over connect', connect)
      //   // this.actualObject.style.strokeColor = 'red'
      //   console.log('mouse Over', args)
      // }
    },
    mouseDown: (args) => {
      currentOffset.x = window.scrollX;
      currentOffset.y = window.scrollY;
    },
    mouseUp: (args) => {
      window.scrollTo(currentOffset.x, currentOffset.y);
    },
    mouseWheel: (args) => {
      console.log(args);
    },
    mouseLeave: (args) => {},
    scrollChange(e) {
      this.$emit("currentZoomScale", e.newValue);
    },
    onConnectionChange(e) {},
    keyDown(e) {
      // if (e.key === ' ') {
      //   this.tool = DiagramTools.ZoomPan
      // }
    },
    keyUp(e) {},
    doubleClick(args) {
      if (args.source.propName === "nodes") {
        this.fromObjDoubleClick = args.source.id;
      }
    },
    diagramClick(args) {
      console.log(args);
      this.currentOffset = args.position;
      let clickedItem = args.actualObject;
      // remove previous Node/Connector strokeColor
      if (this.previousNode) {
        this.previousNode.shape.source = this.previousNode.shape.source.replace(
          "_sel",
          ""
        );
        this.previousNode = null;
      }

      if (this.previousConnector) {
        let idx = diagramInstance.connectors.findIndex(
          (x) => x.id.toString() === this.previousConnector.id.toString()
        );
        if (idx > -1) {
          diagramInstance.connectors[
            idx
          ].style.strokeWidth = this.previousConnector.Width;
          diagramInstance.connectors[
            idx
          ].style.strokeColor = this.previousConnector.BackColor;
          diagramInstance.removeLabels(
            diagramInstance.connectors[idx],
            diagramInstance.connectors[idx].annotations
          );
          diagramInstance.dataBind();
          this.previousConnector = null;
        }
      }
      // ADD TRAM_TB
      if (this.ADD_TRAM_TB_FLAG) {
        this.$emit("diaGianDo_ObjectAdded", "Node", args.position, null);
      }
      // show infor selected
      this.$emit("showInforSelected", clickedItem);
      if (clickedItem) {
        // set css for connector/node when selected
        if (clickedItem.propName === "connectors") {
          // set strokeColor selected
          let idx = diagramInstance.connectors.findIndex(
            (x) => x.id.toString() === clickedItem.id.toString()
          );
          if (idx > -1) {
            // connector selected
            diagramInstance.connectors[idx].style.strokeColor =
              diagramInstance.connectors[idx].SelectedBackColor;
            diagramInstance.connectors[idx].style.strokeWidth =
              diagramInstance.connectors[idx].SelectedWidth;
            // this.$emit("ThayDoiToaDoDiemUon", diagramInstance.connectors[idx]);
            // add ANNOTATION
            let content = diagramInstance.connectors[idx].DisplayName;
            let annotation = [
              {
                // height: 25,
                // width: 200,
                content: content !== null ? content : "",
                margin: {
                  top: 20,
                },
                offset: 0.5,
                style: {
                  textWrapping: "WrapWithOverflow",
                  // fill: "#FFFFFF",
                  strokeWidth: 1,
                  // strokeColor: "#1E90FF",
                  color: "Blue",
                },
                // Sets the constraints as Interaction
                constraints: AnnotationConstraints.Interaction,
              },
            ];
            diagramInstance.addLabels(diagramInstance.connectors[idx], annotation);
            diagramInstance.dataBind();
            this.previousConnector = diagramInstance.connectors[idx];
            //diagramInstance.connectors[idx].constraints = ConnectorConstraints.Default & ~ConnectorConstraints.Drag & ~ConnectorConstraints.DragSegmentThumb &  ~ConnectorConstraints.DragTargetEnd & ~ConnectorConstraints.DragSourceEnd
          }
        }
        if (clickedItem.propName === "nodes") {
          // set ICON selected
          let idx = diagramInstance.nodes.findIndex(
            (x) => x.id.toString() === clickedItem.id.toString()
          );
          if (idx > -1) {
            diagramInstance.nodes[idx].shape.source = diagramInstance.nodes[
              idx
            ].shape.source.replace(
              diagramInstance.nodes[idx].ImageKey,
              diagramInstance.nodes[idx].ImageKey + "_sel"
            );
            this.previousNode = diagramInstance.nodes[idx];
          }
          console.log("fromObjDoubleClick", this.fromObjDoubleClick);
        }
        diagramInstance.dataBind();
        if (args.button === "Left") {
        }
        if (args.button === "Right") {
        }
      } else {
        // remove selector khi clickedItem undefined
        diagramInstance.selectedItems.nodes = [];
        diagramInstance.selectedItems.connectors = [];
        diagramInstance.dataBind();
      }
      // this.$refs['diagram'].contextMenuOpen()
    },
    onDrop(e) {},
    dragEnter(e) {},
    TimPoint(Id) {
      try {
        return diagramInstance.getNodeObject(Id);
      } catch (error) {
        console.log("TimPoint", error);
      }
    },
    TimPoint2(Id) {
      try {
        return diagramInstance.nodes.filter((item) => item.id == Id)[0];
      } catch (error) {
        console.log("TimPoint2", error);
      }
    },
    TimLink(Id) {
      try {
        var obj = diagramInstance.getConnectorObject(Id);
        return obj;
      } catch (error) {
        console.log("TimLink", error);
      }
    },
    TimLink2(Id) {
      return diagramInstance.connectors.filter((item) => item.id == Id.toString())[0];
    },
    taoNode: function (id, shape, ports, annotations) {
      let node = {
        id: id,
        constraints:
          NodeConstraints.Default &
          ~NodeConstraints.Rotate &
          // ~NodeConstraints.Drag &
          ~NodeConstraints.Resize &
          ~NodeConstraints.Delete,
      };
      if (!(typeof shape === "undefined" || shape == null)) {
        node.shape = shape;
      }
      if (!(typeof ports === "undefined" || ports == null)) {
        node.ports = ports;
      }
      if (!(typeof annotations === "undefined" || annotations == null)) {
        node.annotations = annotations;
      }
      return node;
    },
    taoNodeTheoStyle: function (stringStyle, id, ports, annotations) {
      let node = this.taoNode(id, null, ports, annotations);
      let iconFile = "/static/icons/icon100x100/" + stringStyle + ".jpg";
      node.shape = {
        type: "Image",
        source: iconFile,
        scale: "None",
      };
      node.style = {
        fill: "none",
        strokeWidth: 0,
      };
      node.height = 40;
      node.width = 40;
      return node;
    },
    TaoPoint(nodeID, text, stringStyle, tooltip) {
      let annotations = [];
      annotations.push({
        content: text,
        offset: {
          x: 0.5,
          y: 1,
        },
        margin: {
          top: 15,
        },
        style: {
          textWrapping: "NoWrap",
          bold: true,
          fontfamily: "Arial",
          fontsize: 12,
        },
      });
      let point = this.taoNodeTheoStyle(stringStyle, nodeID, null, annotations);
      /*point.tooltip = {
          content: tooltip,
          position: 'BottomCenter',
          relativeMode: 'Object'
        }*/
      return point;
    },
    CapNhatPoint(Point) {
      diagramInstance.addNode(Point);
    },
    TaoLink(id, ketcuoi_n, ketcuoi_d, segments) {
      var link = new Link();
      link.id = id;
      link.sourceID = ketcuoi_n.id;
      link.targetID = ketcuoi_d.id;
      link.allowDrag = true;
      link.annotations = [
        {
          constraints: AnnotationConstraints.Interaction,
        },
      ];
      if (segments.length > 0) {
        let idx = segments.findIndex((p) => p.li.toString() === id.toString());
        if (idx > -1) {
          link.segments = segments[idx].points.map((item) => ({
            type: "Straight",
            point: item,
          }));
          link.TextPoint = segments[idx].textPoint;
        }
      }
      diagramInstance.addConnector(link);
      return link;
    },
    GetNodes() {
      return diagramInstance.nodes;
    },
    GetConnectors() {
      return diagramInstance.connectors;
    },
    DeleteNode(node) {
      return diagramInstance.remove(node);
    },
    DeleteConnector(connector) {
      return diagramInstance.remove(connector);
    },
    Clear() {
      diagramInstance.clear();
    },
    DoLayout() {
      diagramInstance.doLayout();
    },
    DataBind() {
      diagramInstance.dataBind();
    },
    FitToPage() {
      diagramInstance.reset();
      diagramInstance.fitToPage();
    },
    zoomIn() {
      let zoomOptions = {
        type: "ZoomIn",
        // Sets the factor by which we can zoom in or zoom out
        zoomFactor: 0.5,
      };
      // zoomin the diagram
      diagramInstance.zoomTo(zoomOptions);
    },
    zoomOut() {
      let zoomOptions = {
        type: "ZoomOut",
        // Sets the factor by which we can zoom in or zoom out
        zoomFactor: 0.5,
      };
      // zoomout the diagram
      diagramInstance.zoomTo(zoomOptions);
    },
    zoom(scale) {
      diagramInstance.zoom(scale);
    },
    showDoiTuong(isVisible) {
      diagramInstance.nodes.forEach((item) => {
        item.visible = isVisible;
      });
      diagramInstance.dataBind();
    },
    showLienKet(isVisible) {
      diagramInstance.connectors.forEach((item) => {
        item.visible = isVisible;
      });
      diagramInstance.dataBind();
    },
    showTitle(isVisible) {
      diagramInstance.nodes.forEach((item) => {
        item.annotations[0].visibility = isVisible;
      });
      diagramInstance.connectors.forEach((item) => {
        item.annotations.forEach((i) => {
          i.visibility = isVisible;
        });
      });
      diagramInstance.dataBind();
    },
    showGridLine(isVisible) {
      if (isVisible)
        diagramInstance.snapSettings.constraints =
          SnapConstraints.SnapToLines | SnapConstraints.ShowLines;
      else
        diagramInstance.snapSettings.constraints =
          SnapConstraints.SnapToLines | SnapConstraints.None;
      diagramInstance.dataBind();
    },
    selectNode(id) {
      diagramInstance.reset();
      let node = diagramInstance.nodes.find((item) => item.id === id);
      if (node !== null) {
        let bound = new Rect(
          node.offsetX - node.width / 2,
          node.offsetY - node.height / 2,
          node.width,
          node.height
        );
        diagramInstance.select([node]);
        let args = {
          actualObject: node,
          position: {
            x: node.offsetX,
            y: node.offsetY,
          },
          element: node,
          button: "Left",
          count: 1,
        };
        this.diagramClick(args);
        diagramInstance.bringToCenter(bound);
      }
    },
    selectConnector(id) {
      diagramInstance.reset();
      let connector = diagramInstance.connectors.find((item) => item.id === id);
      if (connector !== null) {
        //let bound = new Rect(connector.targetPoint.x, connector.targetPoint.y, connector.sourcePoint.x, connector.sourcePoint.y)
        let bound = new Rect(connector.sourcePoint.x, connector.sourcePoint.y, 50, 50);
        diagramInstance.select([connector]);
        let args = {
          actualObject: connector,
          element: connector,
          button: "Left",
          count: 1,
        };
        this.diagramClick(args);
        diagramInstance.bringToCenter(bound);
      }
    },
    getCurrentOffset() {
      return this.currentOffset;
    },
    bringIntoView(translate) {
      this.currentOffset = {
        x: translate.x,
        y: translate.y,
      };
      let bound = new Rect(translate.x, translate.y, 600, 600);
      diagramInstance.bringIntoView(bound);
    },
    /**
     * Tính khoảng cách
     * @param s Source: Nguồn
     * @param d Destination: Đích
     */
    tinhKhoangCach: function (s, d) {
      let R = 6371; // km
      let dLat = ((d.x - s.x) * Math.PI) / 180;
      let dLon = ((d.y - s.y) * Math.PI) / 180;
      let lat1 = (s.x * Math.PI) / 180;
      let lat2 = (d.x * Math.PI) / 180;
      let a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
      let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
      let rs = R * c;
      return rs;
    },
    thayDoiTBI: async function (args) {
      this.flag_nguon = args.nguon;
      let nguonString = this.flag_nguon == 1 ? "nguồn" : "đích";
      let cap = diagramInstance.selectedItems.connectors[0];
      let ckNguon = true;
      if (this.flag_nguon == 1) {
        if (cap.LOPMC_ID == 1) {
          ckNguon = false;
          this.$root.toastError("Không hỗ trợ thay đổi thiết bị nguồn đối với cáp gốc.");
        }
      }
      if (ckNguon) {
        let accept = false;
        accept = await this.$confirm(
          "Sau khi thay đổi thiết bị " +
            nguonString +
            " thành công, thông tin đấu nối vào " +
            nguonString +
            " của cáp hiện tại sẽ bị gỡ bỏ.<br />Bạn có chắc muốn thực hiện ?",
          "Xác nhận thực hiện",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "warning",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
          });
        if (accept) {
          let ck = await this.$confirm(
            "Hãy chọn đối tượng " + nguonString + " để tiếp tục: ",
            "Chọn đối tượng",
            {
              confirmButtonText: "Trạm thiết bị",
              cancelButtonText: "Kết cuối",
              // showClose: false,
              // closeOnPressEscape: false,
              // closeOnClickModal: false,
              distinguishCancelAndClose: true,
            }
          )
            .then(() => true)
            .catch((e) => {
              return e === "cancel" ? false : null;
            });
          if (ck != null) {
            if (ck) {
              try {
                this.ttvt_id = this.$parent.$parent.$parent.selected.TTVT_ID;
              } catch (ex) {}
              this.$bvModal.show("modalChonTramTbi");
            } else {
              this.$refs.ModalChonDSKcExt.show(2).then(async (result) => {
                await this.thayDoiThietBi(0, result.doituong_id);
              });
            }
          }
        }
      }
    },
    modalChonTramTbi_OnSelectTramTbi: async function (args) {
      if (!(args == null || args == undefined)) {
        await this.thayDoiThietBi(1, args.DONVI_ID);
      }
    },
    thayDoiThietBi: async function (loaiDT, doiTuongID) {
      let nguonString = this.flag_nguon == 1 ? "nguồn" : "đích";
      let loaiDTString = loaiDT == 0 ? "kết cuối" : "trạm thiết bị";
      this.$root.showLoading(true);
      try {
        let capID = diagramInstance.selectedItems.connectors[0].id;
        let rs = await this.thucHienThayDoiThietBi(
          capID,
          this.flag_nguon,
          loaiDT,
          doiTuongID
        );
        if (!(rs == null || rs == undefined || rs.Result == false)) {
          this.$root.toastSuccess(
            "Thực hiện thay đổi " +
              loaiDTString +
              " " +
              nguonString +
              " cho cáp thành công!"
          );
          this.$emit("nap_gian_do");
        } else {
          this.$root.toastWarning(
            "Thực hiện thay đổi " +
              loaiDTString +
              " " +
              nguonString +
              " cho cáp thất bại!"
          );
        }
      } catch (e) {
        this.$root.toastError(
          "Có lỗi xả ra trong quá trình thay đổi " +
            loaiDTString +
            " " +
            nguonString +
            " cho cáp!"
        );
      } finally {
        this.$root.showLoading(false);
      }
    },
    thucHienThayDoiThietBi: async function (capID, nguon, loaiDT, doiTuongID) {
      let rs = await this.$root.context.post("/web-cabman/cap/thayDoiThietBi", {
        capID: capID,
        nguon: nguon,
        loaiDT: loaiDT,
        doiTuongID: doiTuongID,
      });
      return rs.data;
    },
  },
});
</script>
<style>
.green {
  color: green !important;
}

.blue {
  color: blue !important;
}

.context-menu {
  display: none;
  position: absolute;
  background-color: white;
}

.e-diagram {
  height: auto;
  min-height: auto;
}

.disabled {
  pointer-events: none;
  opacity: 0.6;
}

#gian-docontent {
  overflow: hidden !important;
}
</style>
