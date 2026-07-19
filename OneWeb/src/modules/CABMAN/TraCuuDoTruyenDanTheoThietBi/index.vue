<template src="./index.html"></template>
<script>
//import GianDo1 from './GianDo1'
import { Page, Filter, Sort } from '@syncfusion/ej2-vue-treegrid'
import { async } from 'q'
//import Panzoom from '@panzoom/panzoom'
// import {
//   PortVisibility,
//   NodeConstraints,
//   ConnectorConstraints
// } from '@syncfusion/ej2-vue-diagrams'
export default {
  provide: {
    treegrid: [Page, Filter, Sort]
  },
  components: {
    //GianDo1,
    GraphVizControl: () => import('./GraphVizControl.vue')
  },
  props: {
    options: { type: Object, default: () => {} },
    dataDefault: { type: Object, default: () => {} }
  },
  computed: {
    graphID() {
      const uint32 = window.crypto.getRandomValues(new Uint32Array(1))[0]
      return 'graph-dando-oneBSS-' + uint32.toString(16)
    }
  },
  data: function () {
    return {
      //panzoom: '',
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
        thietBiCon: ''
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
      searchTextOld: null,
      gianDoRefresh: false,
      dsThietBiSearch: [],
      showDataSearch: false
    }
  },
  created: async function () {
    await this.getTTVT()
    await this.getLoaiThietBi()
  },
  async mounted() {
    // this.panzoom = Panzoom(document.getElementById('gian-do'), {
    //   maxScale: 5
    // })
    if (this.dataDefault) {
      try {
        this.dataSelected = {
          ttvt: this.dataDefault.ttvt,
          toKT: this.dataDefault.toKT,
          tramTB: this.dataDefault.tramTB,
          cong: this.dataDefault.cong,
          loaiThietBiCha: this.dataDefault.loaiThietBiCha,
          thietBiCha: this.dataDefault.thietBiCha,
          thietBiCon: this.dataDefault.thietBiCon
        }
        this.$root.showLoading(true)
        this.connectorsGianDo1 = []
        await this.getTTVT()
        this.dataSelected.ttvt = this.dataDefault.ttvt
        await this.getToKyThuat()
        this.dataSelected.toKT = this.dataDefault.toKT
        await this.getTramTB()
        this.dataSelected.tramTB = this.dataDefault.tramTB
        await this.getLoaiThietBi()
        this.dataSelected.loaiThietBiCha = this.dataDefault.loaiThietBiCha
        await this.getThietBiCha2()
        this.dataSelected.thietBiCha = this.dataDefault.thietBiCha
        await this.getThietBiCon2()
        this.dataSelected.thietBiCon = this.dsThietBiCon.find((x) => x.THIETBI_ID == this.dataDefault.thietBiCha).KEY
        await this.getCongThietBi2()
        this.dataSelected.cong = this.dataDefault.cong
        await this.onClickTraCuu()
      } catch (e) {
      } finally {
        this.$root.showLoading(false)
      }
    }
  },
  watch: {},
  methods: {
    getTTVT: async function () {
      try {
        let rs = await this.$root.context.get('/web-cabman/donvi/ds-ttvt-phanvung')
        this.dsTTVT = rs.data
      } catch (error) {
      } finally {
      }
    },

    getToKyThuat: async function () {
      try {
        let rs = await this.$root.context.get('/web-cabman/donvi/ds-to-vienthong/' + this.dataSelected.ttvt)
        this.dsToKT = rs.data
      } catch (error) {
      } finally {
      }
    },

    getTramTB: async function () {
      try {
        let rs = await this.$root.context.get('/web-cabman/donvi/ds-tramtb/' + this.dataSelected.toKT)
        this.dsTramTB = rs.data
      } catch (error) {
      } finally {
      }
    },

    getLoaiThietBi: async function () {
      try {
        let rs = await this.$root.context.post('/web-cabman/tracuu/dando/tracuu_dando/lay-ds-loai-thietbi-cha')
        this.dsLoaiThietBi = rs.data
      } catch (error) {
      } finally {
      }
    },

    getThietBiCha: async function () {
      try {
        if (this.dataSelected.loaiThietBiCha == undefined || this.dataSelected.loaiThietBiCha == null || this.dataSelected.loaiThietBiCha == 0) {
          this.dsThietBiCha = []
        } else {
          this.loading(true)
          this.dataSelected.thietBiCha = 0
          this.dsThietBiCon = []
          this.dataSelected.thietBiCon = ''
          this.dsCong = []
          this.dataSelected.cong = 0
          await this.getThietBiCha2()
        }
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    getThietBiCha2: async function () {
      try {
        let input = {
          loaiTbId: this.dataSelected.loaiThietBiCha,
          donViId: this.dataSelected.tramTB
        }
        let rs = await this.$root.context.post('/web-cabman/tracuu/dando/tracuu_dando/lay-ds-thietbi-cha', input)
        this.dsThietBiCha = rs.data
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    getThietBiCon: async function () {
      this.$root.showLoading(true)
      try {
        this.dataSelected.thietBiCon = ''
        this.dsCong = []
        this.dataSelected.cong = 0
        await this.getThietBiCon2()
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    getThietBiCon2: async function () {
      try {
        let input = {
          loaiTbiId: this.dataSelected.loaiThietBiCha,
          doiTuongMapId: this.dataSelected.thietBiCha
        }
        let rs = await this.$root.context.post('/web-cabman/tracuu/dando/tracuu_dando/lay-ds-thietbi-con', input)
        const data = rs.data
        await data.forEach((element) => {
          let elementParentFind = data.find((x) => x.KEY == element.PKEY)
          if (elementParentFind) {
            let parts = elementParentFind.TEN_TB.split(' ')
            let code = ''
            for (let part of parts) {
              if (part.startsWith('#')) {
                code = part
                break
              }
            }
            element.TEN_TB = code + ' ' + element.TEN_TB
          }
        })
        this.dsThietBiCon = data
      } catch (error) {
      } finally {
      }
    },

    getCongThietBi: async function () {
      try {
        this.loading(true)
        this.dataSelected.cong = 0
        await this.getCongThietBi2()
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    getCongThietBi2: async function () {
      try {
        let thietBi = this.dataSelected.thietBiCon.split('#')
        let input = {
          loaiTbiId: thietBi[0],
          thietBiId: thietBi[1]
        }
        let rs = await this.$root.context.post('/web-cabman/tracuu/dando/tracuu_dando/lay-ds-cong-thietbi', input)
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
      if (this.searchTextOld == this.searchText) {
        return
      }
      if (this.searchText != null) {
        this.searchTextOld = this.searchText
        await this.traCuu()
      }
    },

    traCuu: async function () {
      this.$root.showLoading(true)
      try {
        let rs = await this.$root.context.post('web-cabman/tracuu/dando/tracuu_dando', {
          like_query: this.searchText.trim().toLowerCase(),
          query: '%' + this.searchText.trim().toLowerCase() + '%'
        })

        if (rs.data == null || rs.data.length == 0) {
          this.$toast.error('Không có dữ liệu, vui lòng thử lại.')
        }
        this.dsThietBiSearch = rs.data
      } catch (e) {
      } finally {
        this.$root.showLoading(false)
      }
    },

    getThietBiSearch: async function (data) {
      this.showDataSearch = false
      this.$root.showLoading(true)
      try {
        this.dataSelected.ttvt = data.DATA_VALUE.split('|')[0]
        await this.getToKyThuat()
        this.dataSelected.toKT = data.DATA_VALUE.split('|')[1]
        await this.getTramTB()
        this.dataSelected.tramTB = data.DATA_VALUE.split('|')[2]
        await this.getLoaiThietBi()
        this.dataSelected.loaiThietBiCha = data.DATA_VALUE.split('|')[3]
        await this.getThietBiCha()
        this.dataSelected.thietBiCha = data.DATA_VALUE.split('|')[4]
        await this.getThietBiCon()
        this.dataSelected.thietBiCon = this.dsThietBiCon.find((x) => x.THIETBI_ID == data.DATA_VALUE.split('|')[4]).KEY
        await this.getCongThietBi()
        this.searchText = data.TEN_TB
      } finally {
        this.$root.showLoading(false)
      }
    },
    traCuuDanDo: async function () {
      this.$root.showLoading(true)
      try {
        if (this.dataSelected.thietBiCon == '') {
          this.$toast.error('Vui lòng nhập các thông tin cần tra cứu')
          return
        }
        await this.traCuuDanDo2()
      } catch (error) {
        this.refreshGianDo()
      } finally {
        this.$root.showLoading(false)
      }
    },
    traCuuDanDo2: async function () {
      let thietBi = this.dataSelected.thietBiCon.split('#')
      let input = {
        loaitbi_id: thietBi[0],
        thietbi_id: thietBi[1],
        vitri: this.dataSelected.cong
      }

      let rs = await this.$root.context.post('web-ecms/tracuu/truyendan/dandocaptd', input)
      this.refreshGianDo()
      await this.hienThiGianDo3(rs.data)
    },
    onClickTraCuu: async function () {
      await this.traCuuDanDo()
    },
    onClickZoomIn: async function () {
      this.$refs.GraphVizControl.zoomIn()
    },
    onClickZoomOut: async function () {
      this.$refs.GraphVizControl.zoomOut()
    },
    onClickZoomDefault: async function () {
      this.$refs.GraphVizControl.zoomDefault()
    },
    onClickZoomFitWindow: function () {
      //this.$refs.GraphVizControl.fitGraph();
    },
    onClickPan: async function () {
      //this.$refs.GraphVizControl.zoomDefault();
    },
    onClickPrint: async function () {
      try {
        const prtHtml = document.getElementById(this.graphID).innerHTML

        // Get all stylesheets HTML
        let stylesHtml = ''
        for (const node of [...document.querySelectorAll('link[rel="stylesheet"], style')]) {
          stylesHtml += node.outerHTML
        }

        // Open the print window
        const WinPrint = window.open('', '', 'left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0')

        WinPrint.document.write(`<!DOCTYPE html>
                <html>
                  <head>
                    ${stylesHtml}
                  </head>
                  <body>
                    ${prtHtml}
                  </body>
                </html>`)

        WinPrint.document.close()
        WinPrint.focus()
        WinPrint.print()
        WinPrint.close()
      } catch (e) {
        this.$toast.error('Đã có lỗi xảy ra')
        console.log(e)
      }
    },
    refreshGianDo: function () {
      this.gianDoRefresh = !this.gianDoRefresh
    },
    FORMAT_PORT_RX(row) {
      let str = `<rx${row.VITRI.toString().replace('-', '_')}>${row.VITRI.toString()}`
      return str
    },
    FORMAT_PORT_TX(row) {
      let str = `<tx${row.VITRI.toString().replace('-', '_')}>${row.VITRI.toString()}`
      return str
    },
    hienThiGianDo3: async function (dataGianDo) {
      let dsGianDo = dataGianDo
      var sb = []
      sb.push('graph G {\r\n')
      sb.push('  nodesep=.05;\r\n')
      sb.push('  rankdir=TB;\r\n')
      sb.push('  node [shape=record, width=.1, height=.1, fontname="Tahoma", fontsize=12, style=filled];\r\n')
      sb.push('  edge [fontname="Tahoma", fontsize=12];\r\n')

      sb.push('\r\n')

      var node = []
      dsGianDo
        .map(function (x) {
          return { MA_TBI: x.MA_TBI, TEN_TBI: x.TEN_TBI }
        })
        .filter(function (item) {
          var i = node.findIndex((x) => x.MA_TBI === item.MA_TBI && x.TEN_TBI === item.TEN_TBI)
          if (i <= -1) {
            node.push(item)
          }
          return null
        })
      node.forEach((element) => {
        let ma_tbi = element.MA_TBI.toString()
        let ten_tbi = element.TEN_TBI
        let l1 = dsGianDo.filter((e) => e.MA_TBI.toString() === ma_tbi)
        let l = []
        l1.map(function (x) {
          return { VITRI: x.VITRI }
        }).filter(function (item) {
          var i = l.findIndex((x) => x.VITRI === item.VITRI)
          if (i <= -1) {
            l.push(item)
          }
          return null
        })
        l.sort((a, b) => (a.VITRI > b.VITRI ? 1 : -1))
        sb.push(`  ${ma_tbi} [label="`)
        let a1 = []
        l.forEach((element) => {
          a1.push(this.FORMAT_PORT_RX(element))
        })
        let s1 = a1.join('|')
        sb.push(`{{{${s1}}}}`)
        sb.push(`|{{${ten_tbi}}}|`)

        let a2 = []
        l.forEach((element) => {
          a2.push(this.FORMAT_PORT_TX(element))
        })
        let s2 = a2.join('|')
        sb.push(`{{{${s2}}}}`)

        let vloaitbi_con_id = Number(this.dataSelected.thietBiCon.split('#')[0])
        let vthietbi_con_id = Number(this.dataSelected.thietBiCon.split('#')[1])

        if (ma_tbi.includes('n' + vloaitbi_con_id.toString() + '_' + vthietbi_con_id.toString())) {
          sb.push('", fillcolor="orange"];\r\n')
        } else {
          sb.push('"];\r\n')
        }
      })
      sb.push('\r\n')
      let edge = []
      dsGianDo
        .map(function (x) {
          return { CAP_ID: x.CAP_ID, SOICAP: x.SOICAP, TEN_CAP: x.TEN_CAP }
        })
        .filter(function (item) {
          var i = edge.findIndex((x) => x.CAP_ID === item.CAP_ID && x.SOICAP === item.SOICAP && x.TEN_CAP === item.TEN_CAP)
          if (i <= -1) {
            edge.push(item)
          }
          return null
        })
      edge.forEach((element) => {
        let l = dsGianDo.filter((e) => e.CAP_ID.toString() === element.CAP_ID.toString() && e.SOICAP.toString() === element.SOICAP.toString())
        for (let index = 0; index < l.length; index++) {
          const e = l[index]
          if (index === 0) {
            sb.push(`  ${e.MA_TBI}:${e.LOAI_VITRI}${e.VITRI.toString().replace('-', '_')}`)
          } else {
            sb.push(` -- ${e.MA_TBI}:${e.LOAI_VITRI}${e.VITRI.toString().replace('-', '_')}`)
          }
        }
        if (l.length === 1) {
          sb.push(' -- null')
        }
        let color = element.CAP_ID.toString().startsWith('-') ? 'red' : 'blue'
        sb.push(` [label=\"${element.TEN_CAP.toString() + ' [' + element.SOICAP.toString() + ']'}\", color=${color}];\r\n`)
      })
      sb.push('}')
      this.$refs.GraphVizControl && (await this.$refs.GraphVizControl.loadData(sb.join('')))
    },
    bindData: async function (args) {
      try {
        this.$root.showLoading(true)
        this.connectorsGianDo1 = []
        await this.getTTVT()
        if (args.ttvt) {
          this.dataSelected.ttvt = args.ttvt
          await this.getToKyThuat()
          this.dataSelected.toKT = args.toKT
          await this.getTramTB()
          this.dataSelected.tramTB = args.tramTB
        } else {
          await this.getDonViTheoThietBi()
        }

        await this.getLoaiThietBi()
        this.dataSelected.loaiThietBiCha = args.loaiThietBiCha
        await this.getThietBiCha2()
        this.dataSelected.thietBiCha = args.thietBi
        await this.getThietBiCon2()
        this.dataSelected.thietBiCon = this.dsThietBiCon.find((x) => x.THIETBI_ID == args.thietBi).KEY
        await this.getCongThietBi2()
        this.dataSelected.cong = args.cong
        await this.onClickTraCuu()
      } catch (e) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    getDonViTheoThietBi: async function () {
      let dataInput = {
        id: this.data.thietBi
      }
      let res = await this.$root.context.post('/web-cabman/tracuu/dando/layDonViTheoThietBi', dataInput)
      if (res.data.length > 0) {
        this.dataSelected.ttvt = res.data[0].TTVT_ID
        await this.getToKyThuat()
        this.dataSelected.toKT = res.data[0].TOVT_ID
        await this.getTramTB()
        this.dataSelected.tramTB = res.data[0].DONVI_ID
      }
    }
  }
}
</script>
<style src="./index.scss"></style>
