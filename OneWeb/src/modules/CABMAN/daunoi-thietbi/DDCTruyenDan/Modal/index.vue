<template src="./index.popup.html"></template>
<script>
import GianDo1 from "./GianDo1"
import { Page, Filter, Sort } from "@syncfusion/ej2-vue-treegrid"
import Panzoom from '@panzoom/panzoom';
import {
    PortVisibility,
    NodeConstraints,
    ConnectorConstraints,
} from "@syncfusion/ej2-vue-diagrams";
import { style } from "d3-selection";
import TraCuuDoTruyenDanTheoThietBi from "../../../TraCuuDoTruyenDanTheoThietBi";
export default {
    provide: {
        treegrid: [Page, Filter, Sort],
    },
    components: {
        GianDo1,
        TraCuuDoTruyenDanTheoThietBi
    },
    name: 'ModalDDCTruyenDan',
    props: ["modalId", "data1"],
    data: function () {
        return {
            isShowGianDo: false,
            panzoom: '',
            gianDoLanDau: true,
            pageSettings: { pageSizes: true, pageCount: 4, pageSize: 10 },
            nodesGianDo1: [],
            connectorsGianDo1: [],
            danDo: {
                viTri: null,
                thietBiId: null,
                loaiTbiId: null,
                thietBiGocId: null,
                loaiTbiGocId: null,
                donViId: null,
                toVtId: null,
                ttVtId: null
            },
            dataSelected: {
                ttvt: 0,
                toKT: 0,
                tramTB: 0,
                cong: 0,
                loaiThietBiCha: 0,
                thietBiCha: 0,
                thietBiCon: '',
            },
            dsTTVT: [],
            dsToKT: [],
            dsTramTB: [],
            dsCong: [],
            dsLoaiThietBi: [],
            dsThietBiCha: [],
            dsThietBiCon: [],
            danDoList: [],
            searchText: null,
            gianDoRefresh: false,
            dsThietBiSearch: [],
            dataFake: {
                ttvt: 6,
                toKT: 179,
                tramTB: 418,
                loaiThietBiCha: 18,
                thietBi: 690,
                cong: 1
            }
        }
    },
    created: async function () {
        // await this.getTTVT()
        // await this.getLoaiThietBi()
        this.modalShow()
    },
    async mounted() {
        // var rs = await this.$root.context.get(
        //   'web-cabman/tracuu/dando/tracuu_dando/layThongTinCong'
        // )
        // rs.data.forEach(element => {
        //   let danDo = {
        //     viTri: element.VITRI,
        //     thietBiId: element.THIETBI_ID,
        //     loaiTbiId: element.LOAITBI_ID,
        //     thietBiGocId: element.THIETBI_GOC_ID,
        //     loaiTbiGocId: element.LOAITBI_GOC_ID,
        //     donViId: element.DONVI_ID,
        //     toVtId: element.TOVT_ID,
        //     ttVtId: element.TTVT_ID
        //   }
        //   this.danDoList.push(danDo)
        // })
    },
    watch: {
        // danDoList: function () {
        //   this.toVtIdSet = new Set()
        //   this.danDoList.forEach(element => {
        //     this.toVtIdSet.add(element.toVtId)
        //   })
        //   this.toVtIdSet = this.sortSet(this.toVtIdSet)
        // },
        // selected: function (val) {
        //   this.thietBiIdSet = new Set()
        //   this.danDoList.forEach(element => {
        //     if (element.toVtId === val) {
        //       this.thietBiIdSet.add(element.thietBiId)
        //     }
        //   })
        //   this.thietBiIdSet = this.sortSet(this.thietBiIdSet)
        //   console.log(this.thietBiIdSet)
        // },
    },
    methods: {
        modalShow: async function () {
            this.$root.showLoading(true);
            this.isShowGianDo = false;
            this.panzoom = Panzoom(document.getElementById('gian-do'), {
                maxScale: 5
            })
            await this.getTTVT()
            this.dataSelected.ttvt = this.data1.ttvt
            await this.getToKyThuat()
            this.dataSelected.toKT = this.data1.tokt
            await this.getTramTB()
            this.dataSelected.tramTB = this.data1.tramtb
            await this.getLoaiThietBi()
            this.dataSelected.loaiThietBiCha = this.data1.pid0
            await this.getThietBiCha()
            this.dataSelected.thietBiCha = this.data1.pid1
            await this.getThietBiCon()
            this.dataSelected.thietBiCon = this.dsThietBiCon.find(x => x.THIETBI_ID == this.data1.pid1).KEY
            await this.getCongThietBi()
            this.dataSelected.cong = this.data1.pid2
            this.isShowGianDo = true;
            await this.onClickTraCuu()
            this.$root.showLoading(false);
        },
        getTTVT: async function () {
            try {
                var rs = await this.$root.context.get(
                    "/web-cabman/donvi/ds-ttvt-phanvung"
                )
                this.dsTTVT = rs.data
            } catch (error) {
            } finally {
            }
        },

        getToKyThuat: async function () {
            try {
                var rs = await this.$root.context.get(
                    "/web-cabman/donvi/ds-to-vienthong/" + this.dataSelected.ttvt
                )
                this.dsToKT = rs.data
            } catch (error) {
            } finally {
            }
        },

        getTramTB: async function () {
            try {
                var rs = await this.$root.context.get(
                    "/web-cabman/donvi/ds-tramtb/" + this.dataSelected.toKT
                )
                this.dsTramTB = rs.data
            } catch (error) {
            } finally {
            }
        },

        getLoaiThietBi: async function () {
            try {
                var rs = await this.$root.context.post(
                    "/web-cabman/tracuu/dando/tracuu_dando/lay-ds-loai-thietbi-cha"
                )
                this.dsLoaiThietBi = rs.data
            } catch (error) {
            } finally {
            }
        },

        getThietBiCha: async function () {
            try {
                let input = {
                    loaiTbId: this.dataSelected.loaiThietBiCha,
                    donViId: this.dataSelected.tramTB
                }
                var rs = await this.$root.context.post(
                    "/web-cabman/tracuu/dando/tracuu_dando/lay-ds-thietbi-cha", input
                )
                this.dsThietBiCha = rs.data
            } catch (error) {
            } finally {
            }
        },

        getThietBiCon: async function () {
            this.$root.showLoading(true);
            try {
                let input = {
                    loaiTbiId: this.dataSelected.loaiThietBiCha,
                    doiTuongMapId: this.dataSelected.thietBiCha
                }
                var rs = await this.$root.context.post(
                    "/web-cabman/tracuu/dando/tracuu_dando/lay-ds-thietbi-con", input
                )
                this.dsThietBiCon = rs.data
            } catch (error) {
            } finally {
                this.$root.showLoading(false);
            }
        },

        getCongThietBi: async function () {
            try {
                let thietBi = this.dataSelected.thietBiCon.split('#')
                let input = {
                    loaiTbiId: thietBi[0],
                    thietBiId: thietBi[1]
                }
                console.log(input)
                var rs = await this.$root.context.post(
                    "/web-cabman/tracuu/dando/tracuu_dando/lay-ds-cong-thietbi", input
                )
                this.dsCong = rs.data
            } catch (error) {
            } finally {
            }
        },

        onChangeTTVT: async function () {
            await this.getToKyThuat()
        },

        onChangeToKT: async function () {
            await this.getTramTB()
        },

        onChangeTramTB: async function () {
            await this.getThietBiCha()
        },

        onChangeLoaiThietBi: async function () {
            await this.getThietBiCha()
        },

        onChangeThietBiCha: async function () {
            await this.getThietBiCon()
        },

        onChangeThietBiCon: async function () {
            await this.getCongThietBi()
        },

        sleep: function (ms) {
            return new Promise((resolve) => {
                setTimeout(resolve, ms)
            })
        },

        onChangeTextSearch: async function () {
            await this.sleep(2000)
            if (this.searchText != null) {
                await this.traCuu()
            }
        },

        traCuu: async function () {
            this.$root.showLoading(true)
            try {
                let rs = await this.$root.context.post(
                    'web-cabman/tracuu/dando/tracuu_dando',
                    {
                        like_query: this.searchText.toLowerCase(),
                        query: '%' + this.searchText.toLowerCase() + '%'
                    }
                )
                console.log('search', rs.data)
                this.dsThietBiSearch = rs.data
            } catch (e) {

            } finally {
                this.$root.showLoading(false)
            }
        },

        getThietBiSearch: async function (data) {
            this.$root.showLoading(true)
            try {
                this.dataSelected.ttvt = data.DATA_VALUE.split("|")[0]
                await this.getToKyThuat()
                this.dataSelected.toKT = data.DATA_VALUE.split("|")[1]
                await this.getTramTB()
                this.dataSelected.tramTB = data.DATA_VALUE.split("|")[2]
                await this.getLoaiThietBi()
                this.dataSelected.loaiThietBiCha = data.DATA_VALUE.split("|")[3]
                await this.getThietBiCha()
                this.dataSelected.thietBiCha = data.DATA_VALUE.split("|")[4]
                await this.getThietBiCon()
                this.dataSelected.thietBiCon = this.dsThietBiCon.find(x => x.THIETBI_ID == data.DATA_VALUE.split("|")[4]).KEY
                await this.getCongThietBi()
                this.searchText = data.TEN_TB
            } finally {
                this.$root.showLoading(false)
            }

        },
        traCuuDanDo: async function () {
            this.$root.showLoading(true);
            try {
                let thietBi = this.dataSelected.thietBiCon.split('#')
                let input = {
                    loaitbi_id: thietBi[0],
                    thietbi_id: thietBi[1],
                    vitri: this.dataSelected.cong,
                }

                let rs = await this.$root.context.post(
                    "web-ecms/tracuu/truyendan/dandocaptd", input
                )
                console.log(rs.data)
                return;
                this.refreshGianDo()
                this.hienThiGianDo(
                    rs.data
                )

            } catch (error) {
                this.refreshGianDo()
            } finally {
                this.$root.showLoading(false);
            }
        },
        onClickTraCuu: async function () {
            await this.traCuuDanDo()
            if (this.gianDoLanDau) {
                await this.traCuuDanDo()
                this.gianDoLanDau = false
            }
        },
        onClickZoomIn: function () {
            this.onClickZoom(1);
        },
        onClickZoomOut: function () {
            this.onClickZoom(2);
        },
        onClickZoomDefault: function () {
            this.onClickZoom(3);
        },
        onClickPan: function () {
            this.onClickZoom(3);
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
            try {
                const prtHtml = document.getElementById("diagram").innerHTML;

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
            }
            catch (e) {
                this.$toast.error('Đã có lỗi xảy ra')
                console.log(e)
            }

        },

        refreshGianDo: function () {
            this.gianDoRefresh = !this.gianDoRefresh;
        },

        hienThiGianDo: function (dataGianDo) {
            console.log('dataGianDo', dataGianDo)
            let nodes1 = []
            let connectors1 = []
            let dsGianDo = dataGianDo
            let dsTaoNode = []
            let node
            let index = 1
            let indexCha = 1
            let thietBi = this.dataSelected.thietBiCon.split('#')
            let thietBiId = thietBi[1]
            let toaDoCha
            let nodeNguon
            let nodeDich
            let tbiGoc = dsGianDo.find((x) => x.THIETBI_ID == thietBiId);
            if(!(tbiGoc == null || tbiGoc == undefined)){
                let nodeGoc = this.taoNode(index, tbiGoc.TEN_TBI != null ? tbiGoc.TEN_TBI : tbiGoc.TEN_CAP, tbiGoc.VITRI, tbiGoc.VITRI);
                nodes1.push(nodeGoc);
                let tbi = dsGianDo.find((x) => x.CAP_ID == tbiGoc.CAP_ID && (x.LOAITBI_ID != tbiGoc.LOAITBI_ID || x.THIETBI_ID != tbiGoc.THIETBI_ID));
                while(!(tbi == null || tbi == undefined)){
                    index++;
                    let tbi_tx = dsGianDo.find((x) => x.LOAITBI_ID == tbi.LOAITBI_ID && x.THIETBI_ID == tbi.THIETBI_ID && x.LOAI_VITRI != tbi.LOAI_VITRI);
                    let nodeTBI = this.taoNode(index, tbi.TEN_TBI, tbi.VITRI, (tbi_tx == null || tbi_tx == undefined) ? tbi.VITRI : tbi_tx.VITRI);
                    let ketNoi = this.taoKetNoi(index - 1, nodes1[index - 2], nodeTBI, tbi.TEN_CAP + " [" + tbi.SOICAP + "]");
                    nodes1.push(nodeTBI);
                    connectors1.push(ketNoi);

                    if(!(tbi_tx == null || tbi_tx == undefined)){
                        tbi = dsGianDo.find((x) => x.CAP_ID == tbi_tx.CAP_ID && (x.LOAITBI_ID != tbi_tx.LOAITBI_ID || x.THIETBI_ID != tbi_tx.THIETBI_ID));
                    }
                    else {
                        tbi = null;
                    }
                }
            }
            this.nodesGianDo1 = nodes1;
            this.connectorsGianDo1 = connectors1;
            return;
            try {
                //tao node goc
                let capIdTrai
                let soiCapTrai
                let capIdPhai
                let soiCapPhai
                let thuTuChan = 1
                let thuTuLe = 0
                dsTaoNode = dsGianDo.filter((x) => x.THIETBI_ID == thietBiId)
                node = this.taoNode(index, dsTaoNode[0].TEN_TBI, false, 0, {}, indexCha, false, dsTaoNode.length)
                nodes1.push(node)

                let nodeCha = nodes1.filter((x) => x.id == 'node_' + index)
                toaDoCha = {
                    x: nodeCha[0].offsetX,
                    y: nodeCha[0].offsetY
                }
                index++

                for (let i = 0; i < dsTaoNode.length; i++) {
                    let item = dsTaoNode[i]
                    if (item.LOAI_VITRI == 'tx') {
                        capIdTrai = item.CAP_ID
                        soiCapTrai = item.SOICAP
                        node = this.taoNode(index, item.VITRI.toString(), true, thuTuLe * 2 + 1, toaDoCha, indexCha, false, dsTaoNode.length, item.TEN_CAP + '(' + item.SOICAP + ')')
                        nodes1.push(node)
                        thuTuLe++
                    }

                    if (item.LOAI_VITRI == 'rx') {
                        capIdPhai = item.CAP_ID
                        soiCapPhai = item.SOICAP
                        node = this.taoNode(index, item.VITRI.toString(), true, thuTuChan * 2, toaDoCha, indexCha, false, dsTaoNode.length, item.TEN_CAP + '(' + item.SOICAP + ')')
                        nodes1.push(node)
                        thuTuChan++
                    }
                    index++
                }
                indexCha++

                let idx = dsGianDo.findIndex((element) => element.THIETBI_ID == thietBiId)
                while (idx > -1) {
                    console.log(idx)
                    dsGianDo.splice(idx, 1)
                    idx = dsGianDo.findIndex((element) => element.THIETBI_ID == thietBiId)
                }
                console.log(dsGianDo)


                let thietBiTrai = dsGianDo.filter((x) => x.CAP_ID == capIdTrai && x.SOICAP == soiCapTrai && x.LOAI_VITRI == 'rx')
                console.log('thietBiTrai', thietBiTrai)

                while (thietBiTrai.length > 0) {

                    let capIdTrai
                    let soiCapTrai
                    let capIdPhai
                    let soiCapPhai
                    let thuTuChan = 1
                    let thuTuLe = 0
                    dsTaoNode = dsGianDo.filter((x) => x.THIETBI_ID == thietBiTrai[0].THIETBI_ID)
                    node = this.taoNode(index, dsTaoNode[0].TEN_TBI, false, 0, {}, indexCha, true, dsTaoNode.length)
                    nodes1.push(node)

                    let nodeCha = nodes1.filter((x) => x.id == 'node_' + index)
                    toaDoCha = {
                        x: nodeCha[0].offsetX,
                        y: nodeCha[0].offsetY
                    }
                    index++

                    for (let i = 0; i < dsTaoNode.length; i++) {
                        let item = dsTaoNode[i]
                        if (item.LOAI_VITRI == 'tx') {
                            capIdTrai = item.CAP_ID
                            soiCapTrai = item.SOICAP
                            node = this.taoNode(index, item.VITRI.toString(), true, thuTuLe * 2 + 1, toaDoCha, indexCha, true, dsTaoNode.length, item.TEN_CAP + '(' + item.SOICAP + ')')
                            nodes1.push(node)
                            thuTuLe++
                        }

                        if (item.LOAI_VITRI == 'rx') {
                            capIdPhai = item.CAP_ID
                            soiCapPhai = item.SOICAP
                            node = this.taoNode(index, item.VITRI.toString(), true, thuTuChan * 2, toaDoCha, indexCha, true, dsTaoNode.length, item.TEN_CAP + '(' + item.SOICAP + ')')
                            nodes1.push(node)
                            thuTuChan++
                        }
                        index++
                    }
                    let idx = dsGianDo.findIndex((element) => element.THIETBI_ID == thietBiTrai[0].THIETBI_ID)
                    while (idx > -1) {
                        dsGianDo.splice(idx, 1)
                        idx = dsGianDo.findIndex((element) => element.THIETBI_ID == thietBiTrai[0].THIETBI_ID)
                    }
                    let thietBiNext = dsGianDo.filter((x) => x.CAP_ID == capIdTrai && x.SOICAP == soiCapTrai && x.LOAI_VITRI == 'tx')
                    console.log('thietBiNext', thietBiNext)
                    if (thietBiNext.length == 0 || thietBiNext == null) {
                        thietBiNext = dsGianDo.filter((x) => x.CAP_ID == capIdPhai && x.SOICAP == soiCapPhai && x.LOAI_VITRI == 'rx')
                    }
                    thietBiTrai = thietBiNext
                    indexCha++
                }
                console.log('capIdPhai', capIdPhai)
                console.log('soiCapPhai', soiCapPhai)
                let thietBiPhai = dsGianDo.filter((x) => x.CAP_ID == capIdPhai && x.SOICAP == soiCapPhai && x.LOAI_VITRI == 'rx')
                console.log('thietBiPhai', thietBiPhai)
                indexCha = 2
                while (thietBiPhai.length > 0) {
                    let capIdTrai
                    let soiCapTrai
                    let capIdPhai
                    let soiCapPhai
                    let thuTuChan = 1
                    let thuTuLe = 0
                    dsTaoNode = dsGianDo.filter((x) => x.THIETBI_ID == thietBiPhai[0].THIETBI_ID)
                    let slDsTaoNode = dsTaoNode.length

                    node = this.taoNode(index, dsTaoNode[0].TEN_TBI, false, 0, {}, indexCha, false, slDsTaoNode)
                    nodes1.push(node)

                    let nodeCha = nodes1.filter((x) => x.id == 'node_' + index)
                    toaDoCha = {
                        x: nodeCha[0].offsetX,
                        y: nodeCha[0].offsetY
                    }

                    index++

                    for (let i = 0; i < dsTaoNode.length; i++) {
                        let item = dsTaoNode[i]

                        if (item.LOAI_VITRI == 'tx') {
                            capIdTrai = item.CAP_ID
                            soiCapTrai = item.SOICAP
                            console.log('soNodeConPhai', slDsTaoNode)
                            node = this.taoNode(index, item.VITRI.toString(), true, thuTuLe * 2 + 1, toaDoCha, indexCha, false, slDsTaoNode, item.TEN_CAP + '(' + item.SOICAP + ')')
                            nodes1.push(node)
                            thuTuLe++
                        }

                        if (item.LOAI_VITRI == 'rx') {
                            capIdPhai = item.CAP_ID
                            soiCapPhai = item.SOICAP
                            node = this.taoNode(index, item.VITRI.toString(), true, thuTuChan * 2, toaDoCha, indexCha, false, slDsTaoNode, item.TEN_CAP + '(' + item.SOICAP + ')')
                            nodes1.push(node)
                            thuTuChan++
                        }
                        index++
                    }
                    let idx = dsGianDo.findIndex((element) => element.THIETBI_ID == thietBiPhai[0].THIETBI_ID)
                    while (idx > -1) {
                        dsGianDo.splice(idx, 1)
                        idx = dsGianDo.findIndex((element) => element.THIETBI_ID == thietBiPhai[0].THIETBI_ID)
                    }

                    let thietBiNext = dsGianDo.filter((x) => x.CAP_ID == capIdTrai && x.SOICAP == soiCapTrai && x.LOAI_VITRI == 'tx')
                    console.log('thietBiNext', thietBiNext)
                    if (thietBiNext.length == 0 || thietBiNext == null) {
                        thietBiNext = dsGianDo.filter((x) => x.CAP_ID == capIdPhai && x.SOICAP == soiCapPhai && x.LOAI_VITRI == 'rx')
                    }
                    thietBiPhai = thietBiNext
                    indexCha++
                }

                console.log('nodes1', nodes1)
                let demKetNoi = 1
                for (let i = 0; i < nodes1.length; i++) {
                    nodeNguon = nodes1[i]
                    let itemDich = nodes1[i + nodeNguon.soNodeCon - nodeNguon.thuTu + 1]
                    let nodeDich
                    if (nodeNguon.nodeCon && itemDich != null) {

                        switch (nodeNguon.thuTuCha) {
                            case 1:
                                break
                            case 2:
                                if (nodeNguon.benTrai === true) {
                                    if (nodeNguon.thuTu == 1 || nodeNguon.thuTu == 3) {
                                        nodeDich = nodeNguon
                                        nodeNguon = nodes1[nodeNguon.thuTu]
                                    } else {
                                        if (itemDich.benTrai == nodeNguon.benTrai) {
                                            if (itemDich.soNodeCon == 4) {
                                                nodeDich = nodes1[i + nodeNguon.soNodeCon + 1]
                                            } else if (itemDich.soNodeCon == 2) {
                                                if (nodeNguon.thuTu == 1) {
                                                    nodeDich = nodes1[i + nodeNguon.soNodeCon + 2]
                                                } else if (nodeNguon.thuTu == 3) {
                                                    nodeDich = nodes1[i + 3]
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (nodeNguon.thuTu == 2 || nodeNguon.thuTu == 4) {
                                        nodeDich = nodeNguon
                                        nodeNguon = nodes1[nodeNguon.thuTu]
                                    } else {
                                        if (itemDich.benTrai == nodeNguon.benTrai) {
                                            if (itemDich.soNodeCon == 4) {
                                                nodeDich = nodes1[i + nodeNguon.soNodeCon + 1]
                                            } else if (itemDich.soNodeCon == 2) {
                                                if (nodeNguon.thuTu == 1) {
                                                    nodeDich = nodes1[i + nodeNguon.soNodeCon + 2]
                                                } else if (nodeNguon.thuTu == 3) {
                                                    nodeDich = nodes1[i + 3]
                                                }
                                            }
                                        }
                                    }
                                }
                                break
                            default:
                                if (nodeNguon.thuTuCha % 2 == 0) {
                                    if (nodeNguon.benTrai === true) {
                                        if (nodeNguon.thuTu == 1 || nodeNguon.thuTu == 3) {
                                            nodeDich = nodeNguon
                                            nodeNguon = nodes1[i - nodeNguon.soNodeCon - 1]
                                        } else {
                                            if (itemDich.benTrai == nodeNguon.benTrai) {
                                                if (itemDich.soNodeCon == 4) {
                                                    nodeDich = nodes1[i + nodeNguon.soNodeCon + 1]
                                                } else if (itemDich.soNodeCon == 2) {
                                                    if (nodeNguon.thuTu == 1) {
                                                        nodeDich = nodes1[i + nodeNguon.soNodeCon + 2]
                                                    } else if (nodeNguon.thuTu == 3) {
                                                        nodeDich = nodes1[i + 3]
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (nodeNguon.thuTu == 2 || nodeNguon.thuTu == 4) {
                                            nodeDich = nodeNguon
                                            nodeNguon = nodes1[i - nodeNguon.soNodeCon - 1]
                                        } else {
                                            if (itemDich.benTrai == nodeNguon.benTrai) {
                                                if (itemDich.soNodeCon == 4) {
                                                    nodeDich = nodes1[i + nodeNguon.soNodeCon + 1]
                                                } else if (itemDich.soNodeCon == 2) {
                                                    if (nodeNguon.thuTu == 2) {
                                                        nodeDich = nodes1[i + nodeNguon.soNodeCon + 1]
                                                    } else if (nodeNguon.thuTu == 4) {
                                                        nodeDich = nodes1[i + 2]
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (nodeNguon.benTrai === true) {
                                        if (nodeNguon.thuTu == 2 || nodeNguon.thuTu == 4) {
                                            nodeDich = nodeNguon
                                            nodeNguon = nodes1[i - nodeNguon.soNodeCon - 1]
                                        } else {
                                            if (itemDich.benTrai == nodeNguon.benTrai) {
                                                if (itemDich.soNodeCon == 4) {
                                                    nodeDich = nodes1[i + nodeNguon.soNodeCon + 1]
                                                } else if (itemDich.soNodeCon == 2) {
                                                    if (nodeNguon.thuTu == 1) {
                                                        nodeDich = nodes1[i + nodeNguon.soNodeCon + 2]
                                                    } else if (nodeNguon.thuTu == 3) {
                                                        nodeDich = nodes1[i + 3]
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (nodeNguon.thuTu == 1 || nodeNguon.thuTu == 3) {
                                            nodeDich = nodeNguon
                                            nodeNguon = nodes1[i - nodeNguon.soNodeCon - 1]
                                        } else {
                                            if (itemDich.benTrai == nodeNguon.benTrai) {
                                                if (itemDich.soNodeCon == 4) {
                                                    nodeDich = nodes1[i + nodeNguon.soNodeCon + 1]
                                                } else if (itemDich.soNodeCon == 2) {
                                                    if (nodeNguon.thuTu == 2) {
                                                        nodeDich = nodes1[i + nodeNguon.soNodeCon + 1]
                                                    } else if (nodeNguon.thuTu == 4) {
                                                        nodeDich = nodes1[i + 2]
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            // if (itemDich.benTrai == nodeNguon.benTrai) {
                            //     if (itemDich.soNodeCon == 4) {
                            //         nodeDich = nodes1[i+nodeNguon.soNodeCon+1]
                            //     } else if (itemDich.soNodeCon == 2) {
                            //         if (nodeNguon.thuTu == 1) {
                            //             nodeDich = nodes1[i+nodeNguon.soNodeCon+2]
                            //         } else if (nodeNguon.thuTu == 3) {
                            //             nodeDich = nodes1[i+3]
                            //         }
                            //
                            //     }
                            // }
                        }

                        if (nodeDich != null) {
                            let connector = this.taoKetNoi(demKetNoi, nodeNguon, nodeDich, nodeNguon.cap)
                            connectors1.push(connector)
                            demKetNoi++
                        }
                    }
                }


                console.log('connectors1', connectors1)



                this.connectorsGianDo1 = connectors1
                this.nodesGianDo1 = nodes1

            } catch (e) {
                console.log(e)
            }


        },
        taoNode: function (nodeNumber, text, vitriRX, vitriTX) {
            let t = text + vitriRX + vitriTX;
            let PORT_TRAI = { x: 0.08, y: 0 }
            let PORT_PHAI = { x: 0.92, y: 1 }
            let node = {
                id: "node_" + nodeNumber,
                height: 25,
                width: 160,
                offsetX: (nodeNumber - 1) * 160 + 80 - (nodeNumber - 1) * 26,
                offsetY: (nodeNumber - 1) * 80 + 40,
                constraints:
                    NodeConstraints.Default &
                    ~NodeConstraints.Rotate &
                    ~NodeConstraints.Drag &
                    ~NodeConstraints.Resize &
                    ~NodeConstraints.Delete,
                shape: {
                    type: "HTML",
                    content: this.getTextHtml(nodeNumber == 1, text, vitriRX, vitriTX)
                },
                ports: [
                    {
                        id: "node_" + nodeNumber + "_port_1",
                        offset: PORT_TRAI,
                        //visibility: PortVisibility.Hidden,
                    },
                    {
                        id: "node_" + nodeNumber + "_port_2",
                        offset: PORT_PHAI,
                        //visibility: PortVisibility.Hidden,
                    },
                ],
            };

            return node;
        },

        taoKetNoi: function (connectorNumber, nodeSource, nodeTarget, text) {
            return {
                id: "connector_" + connectorNumber,
                sourceID: nodeSource.id,
                sourcePortID: nodeSource.ports[1].id,
                targetID: nodeTarget.id,
                targetPortID: nodeTarget.ports[0].id,
                style: { strokeWidth: 1, strokeColor: (connectorNumber == 1) ? "#FF0000" : "#0176FF", fill: (connectorNumber == 1) ? "#FF0000" : "#0176FF" },
                targetDecorator: {
                    shape : 'None'
                },
                type: 'Straight',
                annotations: [
                    {
                        content: text,
                    },
                ],
            };
        },
        getTextHtml: function(batDau, text, vitriRX, vitriTX){
            let html = "";
            html += "<div style='border: 1px solid #000; background-color: " + (batDau ? "#ed961d" : "#d1d1d1") + "; overflow: hidden; position: relative;'>";
                html += "<span style='border-right: 1px solid #000; padding: 3px; width: 26px; text-align: center; float: left;'>" + vitriRX + "</span>";
                html += "<span style='text-align: center; white-space: nowrap; padding: 3px; width: 106px; overflow: hidden; float: left;'>" + text + "</span>";
                html += "<span style='border-left: 1px solid #000; padding: 3px; width: 26px; text-align: center; float: left;'>" + vitriTX + "</span>";
                html += "</div>"
            return html;
        },
        taoNode1: function (nodeNumber, text, nodeCon, thuTu, toaDoCha, thuTuCha, benTrai, soNodeCon, cap) {
            //let PORT_TREN = { x: 0.5, y: -1.8 }
            //let PORT_DUOI = { x: 0.5, y: 2.4 }
            let PORT_TRAI = { x: 0, y: 0.5 }
            let PORT_PHAI = { x: 1, y: 0.5 }
            let ANNOTATION_MARGIN_TOP = { top: 20 }
            let ANNOTATION_MARGIN_BOTTOM = { bottom: 20 }
            //Xu ly text ko bi null
            if (text == null)
                text = "aaa";
            let height = soNodeCon > 2 ? 60 : 30
            let width = 180
            let x = thuTuCha == 1 ? 600 : benTrai ? 400 - (thuTuCha % 2) * 50 : 800 - (thuTuCha % 2) * 50
            let y = 100 * (thuTuCha - 1) + 60
            let color = thuTuCha > 1 ? 'grey' : 'orange'

            if (nodeCon) {
                height = 30
                width = 40
                x = thuTu % 2 == 0 ? toaDoCha.x + 110 : toaDoCha.x - 110
                let yCon = soNodeCon > 2 ? 15 : 0
                y = thuTu - 2 > 0 ? toaDoCha.y + yCon : toaDoCha.y - yCon
            }

            return {
                nodeCon: nodeCon,
                soNodeCon: soNodeCon,
                thuTu: thuTu,
                benTrai: benTrai,
                thuTuCha: thuTuCha,
                cap: cap,
                id: "node_" + nodeNumber,
                height: height,
                width: width,
                offsetX: x,
                offsetY: y,
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
                style: {
                    stroke: 'black',
                    strokeWidth: '5',
                    fill: color,
                    color: 'black',
                    textAlign: 'Center'
                },
                ports: [
                    {
                        id: "node_" + nodeNumber + "_port_1",
                        offset: thuTu % 2 == 0 ? PORT_PHAI : PORT_TRAI,
                        visibility: PortVisibility.Hidden,
                    },
                    {
                        id: "node_" + nodeNumber + "_port_2",
                        offset: thuTu % 2 == 0 ? PORT_PHAI : PORT_TRAI,
                        visibility: PortVisibility.Hidden,
                    },
                ],
                annotations: [
                    {
                        //content: textExt,
                        //margin: chan ? ANNOTATION_MARGIN_BOTTOM : ANNOTATION_MARGIN_TOP
                    },
                ],
            };
        },

        taoKetNoi1: function (connectorNumber, nodeSource, nodeTarget, text) {
            return {
                id: "connector_" + connectorNumber,
                sourceID: nodeSource.id,
                sourcePortID: nodeSource.ports[1].id,
                targetID: nodeTarget.id,
                targetPortID: nodeTarget.ports[0].id,
                style: { strokeWidth: 1, strokeColor: "#FF0000", fill: "#FF0000" },
                // targetDecorator: {
                //     style: { strokeWidth: 1, strokeColor: "#FF0000", fill: "#FF0000" },
                // },
                type: 'Bezier',
                annotations: [
                    {
                        content: text,
                        margin: { left: 25 },
                    },
                ],
            };
        }

    },
};
</script>
<style src="./index.scss"></style>
