<template src="./index.template.html"></template>
<script>
    import GianDo1 from "./GianDo1";
    import DiagramLib from "../../../utils/DiagramLib";
    import { Page, Filter, Sort } from "@syncfusion/ej2-vue-treegrid";
    import Panzoom from '@panzoom/panzoom';
    import {
        PortVisibility,
        NodeConstraints,
        ConnectorConstraints,
    } from "@syncfusion/ej2-vue-diagrams";
    import {minLength,maxLength,numeric, required} from 'vuelidate/lib/validators'
    import { L10n } from '@syncfusion/ej2-base'
    L10n.load({
        'vi-VN': {
            grid: {
                EmptyRecord: 'Không có bản ghi nào để hiển thị',
            },
            pager: {
                pagerDropDown: 'Số bảng ghi trên một trang',
                //totalItemsInfo: '({0} items)',
                totalItemInfo: '({0} bảng ghi)',
                Item: 'bảng ghi',
                Items: 'Số bảng ghi',
                currentPageInfo: '{0} của {1} trang'
            }
        }
    })
    let filterBarTemplate = {
        create: function (args) {

            let div = document.createElement('div')
            div.classList.add('input-icon-right')

            let input = document.createElement('input')
            input.classList.add('form-control')
            input.id = args.column.foreignKeyField

            let span = document.createElement('span')
            span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

            div.append(input)
            div.append(span)

            return div
        },
        write: function (args) {

            args.element.querySelector('input').addEventListener('input', (args) => {

                let value = args.currentTarget.value

                if (!value) {
                    this.parent.removeFilteredColsByField(args.currentTarget.id);
                } else {
                    this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
                }
            })
        }
    }
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
                maFOS: '',//"FOS000010001",
                showGianDo: false,
                ghiChu: "",
                panzoom:'',
                pageSettings: { pageSizes: true, pageCount: 4, pageSize: 10 },
                nodesGianDo1: [],
                connectorsGianDo1: [],
                filterBarTemplate: filterBarTemplate,
                disablePan: false
            };
        },
        validations: {
            maFOS: {
                required
            }
        },
        created: function () {},
        mounted() {
            this.panzoom = Panzoom(document.getElementById('gian-do'), {
                maxScale: 5
            })
            //console.log('panzoom',this.panzoom)
        },
        methods: {
            rowDataBound: function (args) {
                //console.log(args)
                if(args.data.level === 0){
                    args.row.style.fontWeight = "bold"
                }
            },
            traCuuDanDo: async function (maFOS) {
                this.$root.showLoading(true);
                try {
                    this.showGianDo = false
                    this.ghiChu = null
                    this.kqTimKiem = []
                    let rs = await this.$root.context.get(
                        "web-cabman/TraCuuTruyenDan/TraCuuDanDoTruyenDanTheoMaFOS", { maFOS: maFOS }
                    );
                    //console.log(rs.data)
                    if (rs.data.dsOdf.length == 0) {
                        this.$toast.error('Tuyến ' + this.maFOS + ' không tồn tại.')
                        return
                    }
                    this.showGianDo = true
                    this.ghiChu = rs.data.ghiChu

                    // let arr = [
                    //     { STT: "Giản đồ 1", states: rs.data.dsDanDoTruyenDan1 },
                    //     { STT: "Giản đồ 2", states: rs.data.dsDanDoTruyenDan2 },
                    // ]
                    let arr = []
                    rs.data.dsDanDoTruyenDan1.forEach(item => {
                        item.GIANDO = '1'
                        arr.push(item)
                    })

                    rs.data.dsDanDoTruyenDan2.forEach(item => {
                        item.GIANDO = '2'
                        arr.push(item)
                    })
                    this.kqTimKiem = arr

                    console.log(this.kqTimKiem)

                    // this.hienThiGianDo(
                    //   rs.data.dsDanDoTruyenDan1,
                    //   rs.data.dsDanDoTruyenDan2
                    // );

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
                this.$v.$touch()
                if (this.maFOS) {
                    this.traCuuDanDo(this.maFOS.trim());
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
            onClickZoomFitWindow: function () {
                try {
                    let image = $('#gian-do')
                    //let container = $('#container-giando-maFOS')
                    let container = $(window)

                    //Get container dimensions
                    let container_height = container.height() - 400
                    let container_width = container.width()
                    console.log(container_height, container_width)
                    //Get image dimensions
                    let image_height = image.height()
                    let image_width = image.width()

                    if (image_height <= container_height && image_width <= container_width) {
                        this.onClickZoomDefault()
                        return
                    }

                    //Calculate the center of image since origin is at x:50% y:50%
                    let image_center_left = image_width / 2.0
                    let image_top = image_height

                    //Calculate scaling factor
                    let zoom_factor

                    //Check to determine whether to stretch along width or heigh
                    //if(image_height > image_width)
                        zoom_factor = container_height / image_height
                    // else
                    //     zoom_factor = container_width / image_width

                    //Zoom by zoom_factor
                    console.log(zoom_factor, image_height, image_width, container_height, container_width)
                    this.panzoom.zoom(zoom_factor)
                    //$panzoom.panzoom("zoom", zoom_factor, {animate: false});

                    //Calculate new image dimensions after zoom
                    // image_width = image_width * zoom_factor
                    // image_height = image_height * zoom_factor
                    //
                    // //Calculate offset of the image after zoom
                    // let image_offset_left = image_center_left - (image_width / 2.0)
                    // let image_offset_top = image_top - (image_height / 2.0)
                    //
                    // //Calculate desired offset for image
                    // let new_offset_left = (container_width - image_width) / 2.0
                    // let new_offset_top = container_height - image_top
                    zoom_factor = zoom_factor> 0.125? zoom_factor : 0.125

                    //Pan to set desired offset for image
                    let pan_left = 0
                    let pan_top = (container_height - (image_height/zoom_factor)/2.0) + (image_height/2)
                    //$panzoom.panzoom("pan", pan_left, pan_top);
                    console.log(pan_left, pan_top)
                    this.panzoom.pan(pan_left, pan_top)
                    //VTN-VMS7 (Hàn Thẳng)
                } catch (e) {
                    console.log(e)
                }

            },
            onClickPan: async function () {
                this.disablePan = !this.disablePan
                this.panzoom.disablePan = this.disablePan
                //this.onClickZoom(3);
            },
            onClickZoom(loai) {
                switch (loai) {
                    case 1:
                        this.panzoom.zoomIn()
                        break;
                    case 2:
                        this.panzoom.zoomOut()
                        break;
                    case 3:
                        this.panzoom.reset()
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
                                    "                      <div class=\"box-form\" style='background-image: linear-gradient(to right, gray, white);'>\n" +
                                    "                        <span style='font-weight: bold;'>" +
                                    item.LOAI_TBI + "</span><span>" +
                                    ": " +
                                    (item.TEN_TBI == null ? "" : item.TEN_TBI) + "</span><span style='color: red'>" +                                    
                                    (item.GHICHU!==null ? "(" + item.GHICHU + ")" : "") +                                   
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
                                "                      <div class=\"box-form\" style='background-image: linear-gradient(to right, gray, white);'>\n" +
                                "                        <span style='font-weight: bold;'>" +
                                item.LOAI_TBI + "</span><span>" +
                                ": " +
                                (item.TEN_TBI == null ? "" : item.TEN_TBI) + "</span><span style='color: red'>" +
                                (item.GHICHU!==null ? "(" + item.GHICHU + ")" : "") +       
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
                            '                               viewBox="0 0 512.171 512.171" style="enable-background:new 0 0 512.171 512.171;height: 60px " xml:space="preserve">\n' +
                            "                          <g>\n" +
                            '                          <path d="M479.046,283.925c-1.664-3.989-5.547-6.592-9.856-6.592H352.305V10.667C352.305,4.779,347.526,0,341.638,0H170.971\n' +
                            "                              c-5.888,0-10.667,4.779-10.667,10.667v266.667H42.971c-4.309,0-8.192,2.603-9.856,6.571c-1.643,3.989-0.747,8.576,2.304,11.627\n" +
                            "                              l212.8,213.504c2.005,2.005,4.715,3.136,7.552,3.136s5.547-1.131,7.552-3.115l213.419-213.504\n" +
                            '                              C479.793,292.501,480.71,287.915,479.046,283.925z"/>\n' +
                            "                          </g>\n" +
                            "                        </svg>\n" +
                            "                        </div>\n" +
                            "                        <span>" +
                            (item.TEN_TBI == null ? "" : item.TEN_TBI) +
                            "(" +
                            item.VITRI +
                            ")" +
                            "</span>" +
                            "                   </div>";

                        // newChild =
                        //     newChild +
                        //     '<div class="gian-do-arrow">\n' +
                        //     '                        <div class="svg-container">\n' +
                        //     '                          <svg fill="red" xmlns="http://www.w3.org/2000/svg" fill-rule="evenodd" clip-rule="evenodd"><path d="M11 21.883l-6.235-7.527-.765.644 7.521 9 7.479-9-.764-.645-6.236 7.529v-21.884h-1v21.883z"/></svg>\n' +
                        //     "                        </div>\n" +
                        //     "                        <span>" +
                        //     (item.TEN_TBI == null ? "" : item.TEN_TBI) +
                        //     "(" +
                        //     item.VITRI +
                        //     ")" +
                        //     "</span>" +
                        //     "                   </div>";
                    }
                }
                document
                    .getElementById("insert-" + data.name)
                    .insertAdjacentHTML("beforeend", newChild);
            },
            // hienThiGianDo: function (dataGianDo1, dataGianDo2) {
            //   let nodes1 = [];
            //   let connectors1 = [];
            //
            //   //let PORT_TREN = { x: 0.5, y: 0 };
            //   //let PORT_DUOI = { x: 0.5, y: 1 };
            //   //let PORT_TRAI = { x: 0, y: 0.5 };
            //   //let PORT_PHAI = { x: 1, y: 0.5 };
            //
            //   //let ANNOTATION_MARGIN_TOP = { top: 20 };
            //   //let ANNOTATION_MARGIN_BOTTOM = { bottom: 20 };
            //
            //   for (let i = 0; i < dataGianDo1.length; i++) {
            //     let nodeNumber = i + 1;
            //     let text =
            //       dataGianDo1[i].LOAI_TBI +
            //       ": " +
            //       dataGianDo1[i].TEN_TBI +
            //       " (" +
            //       dataGianDo1[i].GHICHU +
            //       ")";
            //     let textExt = dataGianDo1[i].VITRI;
            //     let kieuNode = 2;
            //     if(i == 0)
            //       kieuNode = 1;
            //     else if(i == dataGianDo1.length - 1)
            //       kieuNode = 4;
            //     let node = this.taoNode(nodeNumber, text, textExt, kieuNode);
            //     if (i > 0) {
            //       let ketNoiText =
            //         dataGianDo1[i].DIACHI + " (" + dataGianDo1[i].VITRI + ")";
            //       let ketNoi = this.taoKetNoi(i, nodes1[i - 1], node, ketNoiText);
            //       connectors1.push(ketNoi);
            //     }
            //     nodes1.push(node);
            //   }
            //   console.log(nodes1);
            //   console.log(connectors1);
            //
            //   this.connectorsGianDo1 = connectors1;
            //   this.nodesGianDo1 = nodes1;
            // },
            // taoNode: function (nodeNumber, text, textExt, kieuNode) {
            //   let ANNOTATION_MARGIN_TOP = { top: 20 };
            //   let ANNOTATION_MARGIN_BOTTOM = { bottom: 20 };
            //   //Xu ly text ko bi null
            //   if (text == null) text = "aaa";
            //   if (textExt == null) textExt = "aaaa";
            //
            //   let nodeID = "node_" + nodeNumber;
            //   let shape = { type: "Basic", shap:'Rectangle', cornerRadius: 5 };
            //   let ports = [];
            //   ports.push(DiagramLib.taoPort(nodeID + "_port_1", { x: 0.5, y: -1.0 }));
            //   ports.push(DiagramLib.taoPort(nodeID + "_port_2", { x: 0.5, y: 2.0 }));
            //   let annotations = [];
            //   annotations.push({content: text});
            //   if(kieuNode == 1){
            //     //Node dau tien
            //     annotations.push({content: textExt, margin: ANNOTATION_MARGIN_TOP});
            //   }
            //   else if(kieuNode == 4){
            //     //Node cuoi
            //     annotations.push({content: textExt, margin: ANNOTATION_MARGIN_BOTTOM});
            //   }
            //   else{
            //     //Node giua
            //     annotations.push({content: textExt, margin: ANNOTATION_MARGIN_TOP});
            //     annotations.push({content: textExt, margin: ANNOTATION_MARGIN_BOTTOM});
            //   }
            //   let node = DiagramLib.taoNode(nodeID, shape, ports, annotations);
            //   node.height = 20;
            //   node.width = 300;
            //   node.offsetX = 400;
            //   node.offsetY = 100 * (nodeNumber - 1) + 30;
            //   node.style = { fill: "#f9f9f9", strokeWidth: 1, strokeColor: "#d0d0d0" };
            //   return node;
            // },
            // taoKetNoi: function (connectorNumber, nodeSource, nodeTarget, text) {
            //   let id = "connector_" + connectorNumber;
            //   let sourceID = nodeSource.id;
            //   let sourcePortID = nodeSource.ports[1].id;
            //   let targetID = nodeTarget.id;
            //   let targetPortID = nodeTarget.ports[0].id;
            //   let style = { strokeWidth: 1, strokeColor: "#FF0000", fill: "#FF0000" };
            //   let annotations = [{ content: text, margin: { left: 30 }, style: { textWrapping: 'NoWrap' } }];
            //   let ketNoi = DiagramLib.taoKetNoi( id, sourceID, targetID, style, sourcePortID, targetPortID, annotations );
            //   ketNoi.targetDecorator = {
            //     style: { strokeWidth: 1, strokeColor: "#FF0000", fill: "#FF0000" },
            //   };
            //   return ketNoi;
            // },
        },
    };
</script>
<style src="./index.scss">
</style>
