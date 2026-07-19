<template src="./index.template.html"></template>
<script>
import GianDo1 from "./GianDo1";
import { Page, Filter, Sort } from "@syncfusion/ej2-vue-treegrid";
import {
  PortVisibility,
  NodeConstraints,
  ConnectorConstraints,
} from "@syncfusion/ej2-vue-diagrams";
export default {
  provide: {
    treegrid: [Page, Filter, Sort],
  },
  components: {
    GianDo1,
  },
  props: {
    options: { type: Object, default: () => {} },
  },
  data: function () {
    return {
      kqTimKiem: [],
      maFOS: "FOS000010001",
      ghiChu: "",
      zoom: 1,
      pageSettings: { pageSizes: true, pageCount: 4, pageSize: 10 },
      nodesGianDo1: [],
      connectorsGianDo1: [],
    };
  },
  created: function () {},
  mounted() {},
  methods: {
    traCuuDanDo: async function (maFOS) {
      this.$root.showLoading(true);
      try {
        var rs = await this.$root.context.get(
          "web-cabman/TraCuuTruyenDan/TraCuuDanDoTruyenDanTheoMaFOS",
          { maFOS: maFOS }
        );
        //console.log(rs.data)
        this.ghiChu = rs.data.ghiChu;
        let arr = [
          { STT: "Giản đồ 1", states: rs.data.dsDanDoTruyenDan1 },
          { STT: "Giản đồ 2", states: rs.data.dsDanDoTruyenDan2 },
        ];
        this.kqTimKiem = arr;

        console.log(rs.data.dsDanDoTruyenDan1);

        this.hienThiGianDo(
          rs.data.dsDanDoTruyenDan1,
          rs.data.dsDanDoTruyenDan2
        );

        let data = {
          dsDanDoTruyenDan: rs.data.dsDanDoTruyenDan1,
          name: "giando1",
        };
        this.veGianDo(data);
        data = {
          dsDanDoTruyenDan: rs.data.dsDanDoTruyenDan2,
          name: "giando2",
        };
        this.veGianDo(data);
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    onClickTraCuu: async function () {
      if (this.maFOS) {
        this.traCuuDanDo(this.maFOS);
      } else {
        this.$toast.error("Bạn chưa nhập mã FOS");
      }
    },
    onClickZoomIn: async function () {
      this.onClickZoom(1);
    },
    onClickZoomOut: async function () {
      this.onClickZoom(2);
    },
    onClickZoomDefault: async function () {
      this.onClickZoom(3);
    },
    onClickPan: async function () {
      this.onClickZoom(3);
    },
    onClickZoom(loai) {
      switch (loai) {
        case 1:
          break;
        case 2:
          break;
        case 3:
          break;
      }
    },
    onClickPrint: async function () {
      const prtHtml = document.getElementById("gian-do").innerHTML;

      // Get all stylesheets HTML
      let stylesHtml = "";
      for (const node of [
        ...document.querySelectorAll('link[rel="stylesheet"], style'),
      ]) {
        stylesHtml += node.outerHTML;
      }

      // Open the print window
      const WinPrint = window.open(
        "",
        "",
        "left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0"
      );

      WinPrint.document.write(`<!DOCTYPE html>
          <html>
            <head>
              ${stylesHtml}
            </head>
            <body>
              ${prtHtml}
            </body>
          </html>`);

      WinPrint.document.close();
      WinPrint.focus();
      WinPrint.print();
      WinPrint.close();
    },
    veGianDo: async function (data) {
      let el = document.querySelector("#insert-" + data.name);
      if (el) el.parentNode.removeChild(el);
      document
        .getElementById(data.name)
        .insertAdjacentHTML(
          "beforeend",
          "<div id='insert-" + data.name + "'></div>"
        );
      let newChild;
      let item;
      let itemNext;
      let itemPrevious;
      for (let i = 0; i < data.dsDanDoTruyenDan.length; i++) {
        item = data.dsDanDoTruyenDan[i];
        itemNext =
          i < data.dsDanDoTruyenDan.length
            ? data.dsDanDoTruyenDan[i + 1]
            : null;
        itemPrevious = i > 0 ? data.dsDanDoTruyenDan[i - 1] : null;
        if (item.LOAITBI_ID != 26) {
          if (newChild != null) {
            if (itemPrevious != null && itemPrevious.VITRI != null) {
              newChild =
                newChild +
                "<div>\n" +
                "                        <span>" +
                item.VITRI +
                "</span>\n" +
                "                      </div>\n" +
                "                      <div>\n" +
                "                        <span>" +
                item.LOAI_TBI +
                ": " +
                item.TEN_TBI +
                "(" +
                item.GHICHU +
                ")" +
                "</span>\n" +
                "                      </div>";
            } else if (itemNext != null && itemNext.VITRI != null) {
              newChild =
                newChild +
                "<div>\n" +
                "                        <span>" +
                item.VITRI +
                "</span>\n" +
                "                      </div>\n";
            }
          } else {
            newChild =
              "                      <div>\n" +
              "                        <span>" +
              item.LOAI_TBI +
              ": " +
              item.TEN_TBI +
              "(" +
              item.GHICHU +
              ")" +
              "</span>\n" +
              "                      </div>";
            newChild =
              (itemNext != null) & (itemNext.VITRI != null)
                ? newChild +
                  "<div>\n" +
                  "                        <span>" +
                  item.VITRI +
                  "</span>\n" +
                  "                      </div>"
                : newChild;
          }
        } else if (item.VITRI != null) {
          newChild =
            newChild +
            '<div class="gian-do-arrow">\n' +
            '                        <div class="svg-container">\n' +
            '                          <svg fill="red" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"\n' +
            '                               viewBox="0 0 512.171 512.171" style="enable-background:new 0 0 512.171 512.171; " xml:space="preserve">\n' +
            "                          <g>\n" +
            '                          <path d="M479.046,283.925c-1.664-3.989-5.547-6.592-9.856-6.592H352.305V10.667C352.305,4.779,347.526,0,341.638,0H170.971\n' +
            "                              c-5.888,0-10.667,4.779-10.667,10.667v266.667H42.971c-4.309,0-8.192,2.603-9.856,6.571c-1.643,3.989-0.747,8.576,2.304,11.627\n" +
            "                              l212.8,213.504c2.005,2.005,4.715,3.136,7.552,3.136s5.547-1.131,7.552-3.115l213.419-213.504\n" +
            '                              C479.793,292.501,480.71,287.915,479.046,283.925z"/>\n' +
            "                          </g>\n" +
            "                        </svg>\n" +
            "                        </div>\n" +
            "                        <span>" +
            item.TEN_TBI +
            "(" +
            item.VITRI +
            ")" +
            "</span>" +
            "                   </div>";
        }
      }
      document
        .getElementById("insert-" + data.name)
        .insertAdjacentHTML("beforeend", newChild);
    },
    hienThiGianDo: function (dataGianDo1, dataGianDo2) {
      let nodes1 = [];
      let connectors1 = [];

      //let PORT_TREN = { x: 0.5, y: 0 };
      //let PORT_DUOI = { x: 0.5, y: 1 };
      //let PORT_TRAI = { x: 0, y: 0.5 };
      //let PORT_PHAI = { x: 1, y: 0.5 };

      //let ANNOTATION_MARGIN_TOP = { top: 20 };
      //let ANNOTATION_MARGIN_BOTTOM = { bottom: 20 };

      for (let i = 0; i < dataGianDo1.length; i++) {
        let nodeNumber = i + 1;
        let text =
          dataGianDo1[i].LOAI_TBI +
          ": " +
          dataGianDo1[i].TEN_TBI +
          " (" +
          dataGianDo1[i].GHICHU +
          ")";
        let textExt = dataGianDo1[i].VITRI;
        let chan = nodeNumber % 2 == 0;
        let node = this.taoNode(
          nodeNumber,
          text,
          textExt,
          chan
        );
        if (i > 0) {
          let ketNoiText =
            dataGianDo1[i].DIACHI + " (" + dataGianDo1[i].VITRI + ")";
          let ketNoi = this.taoKetNoi(i, nodes1[i - 1], node, ketNoiText);
          connectors1.push(ketNoi);
        }
        nodes1.push(node);
      }
      console.log(nodes1);
      console.log(connectors1);

      this.connectorsGianDo1 = connectors1;
      this.nodesGianDo1 = nodes1;
    },
    taoNode: function (nodeNumber, text, textExt, chan) {
      let PORT_TREN = { x: 0.5, y: -1.8 };
      let PORT_DUOI = { x: 0.5, y: 2.4 };
      //let PORT_TRAI = { x: 0, y: 0.5 };
      //let PORT_PHAI = { x: 1, y: 0.5 };
      let ANNOTATION_MARGIN_TOP = { top: 20 };
      let ANNOTATION_MARGIN_BOTTOM = { bottom: 20 };
      //Xu ly text ko bi null
      if(text == null)
        text = "aaa";
      if(textExt == null)
        textExt = "aaaa";

      return {
        id: "node_" + nodeNumber,
        height: 15,
        width: 300,
        offsetX: 400,
        offsetY: 100 * (nodeNumber-1) + 30,
        constraints:
          NodeConstraints.Default &
          ~NodeConstraints.Rotate &
          ~NodeConstraints.Drag &
          ~NodeConstraints.Resize &
          ~NodeConstraints.Delete,
        shape: {
          type: "Text",
          content: text,
          //margin: {top: 15, bottom: 15},
        },
        ports: [
          {
            id: "node_" + nodeNumber + "_port_1",
            offset: PORT_TREN,
            visibility: PortVisibility.Hidden,
          },
          {
            id: "node_" + nodeNumber + "_port_2",
            offset: PORT_DUOI,
            visibility: PortVisibility.Hidden,
          },
        ],
        annotations: [
          {
            content: textExt,
            margin: chan ? ANNOTATION_MARGIN_BOTTOM : ANNOTATION_MARGIN_TOP
          },
        ],
      };
    },
    taoKetNoi: function (connectorNumber, nodeSource, nodeTarget, text) {
      return {
        id: "connector_" + connectorNumber,
        sourceID: nodeSource.id,
        sourcePortID: nodeSource.ports[1].id,
        targetID: nodeTarget.id,
        targetPortID: nodeTarget.ports[0].id,
        style: { strokeWidth: 1, strokeColor: "#FF0000", fill: "#FF0000" },
        targetDecorator: {
          style: { strokeWidth: 1, strokeColor: "#FF0000", fill: "#FF0000" },
        },
        constraints:
          ConnectorConstraints.Default &
          ~ConnectorConstraints.Drag &
          ~ConnectorConstraints.AllowDrop &
          ~ConnectorConstraints.Delete &
          ~ConnectorConstraints.DragSegmentThumb,
        annotations: [
          {
            content: text,
            margin: { left: 25 },
          },
        ],
      };
    },
  },
};
</script>
<style src="./index.scss">
</style>
