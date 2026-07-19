<template src="./index.html"></template>
<script>
const ModalInNhanh_XemTruocKhiIn = () => import('../InNhanh_XemTruocKhiIn')
export default {
  name: 'GianDoTreeList',
  components: {
    ModalInNhanh_XemTruocKhiIn
  },
  props: { modalID: null },
  data: function () {
    return {
      fieldsDonVi: null,
      data: [],
      title: null,
      resolvePromise: null,
      rejectPromise: null
    }
  },
  watch: {
  },
  mounted () {
  },
  methods: {
    closeModal: function () {
      this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide(this.modalID)
    },
    onHiddenModal () {},
    onShownModal () {
      this.loadTreeDonVi()
    },
    inGianDo () {
      let DOMPrinter = this.PrintElem('#idGianDoTreeList')
      DOMPrinter.print()
    },
    xuatExcel () {

    },
    exportToExcel () {
      var htmls = ''
      var uri = 'data:application/vnd.ms-excel;base64,'
      var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
      var base64 = function (s) {
        return window.btoa(unescape(encodeURIComponent(s)))
      }

      var format = function (s, c) {
        return s.replace(/{(\w+)}/g, function (m, p) {
          return c[p]
        })
      }

      var table = document.getElementById('idGianDoTreeList')
      var html = table.innerHTML

      // htmls = 'YOUR HTML AS TABLE'

      var ctx = {
        worksheet: 'Worksheet',
        table: html
      }

      var link = document.createElement('a')
      link.download = 'giando.xls'
      link.href = uri + base64(format(template, ctx))
      link.click()
    },
    exportF () {
      var table = document.getElementById('idGianDoTreeList')
      var html = table.outerHTML
      var url = 'data:application/vnd.ms-excel,' + escape(html) // Set your html table into url
      let elem = document.getElementById('downloadLink')
      elem.setAttribute('href', url)
      elem.setAttribute('download', 'export.xls') // Choose the file name
      return false
    },
    PrintElem (elem, title, css) {
      var tmpWindow = window.open('', 'PRINT', 'location=yes,height=570,width=520,scrollbars=yes,status=yes')
      var tmpDoc = tmpWindow.document

      title = title || document.title
      css = css || ''

      this.setTitle = function (newTitle) {
        title = newTitle || document.title
      }

      this.setCSS = function (newCSS) {
        css = newCSS || ''
      }

      this.basicHtml5 = function (innerHTML) {
        return '<!doctype html><html>' + (innerHTML || '') + '</html>'
      }

      this.htmlHead = function (innerHTML) {
        return '<head>' + (innerHTML || '') + '</head>'
      }

      this.htmlTitle = function (title) {
        return '<title>' + (title || '') + '</title>'
      }

      this.styleTag = function (innerHTML) {
        return '<style>' + (innerHTML || '') + '</style>'
      }

      this.htmlBody = function (innerHTML) {
        return '<body>' + (innerHTML || '') + '</body>'
      }

      this.build = function () {
        tmpDoc.write(
          this.basicHtml5(
            this.htmlHead(
              this.htmlTitle(title) + this.styleTag(css)
            ) + this.htmlBody(
              document.querySelector(elem).innerHTML
            )
          )
        )
        tmpDoc.close() // necessary for IE >= 10
      }

      this.print = function () {
        tmpWindow.focus() // necessary for IE >= 10*/
        tmpWindow.print()
        tmpWindow.close()
      }

      this.build()
      return this
    },
    loadTreeDonVi () {
      let tree = this.createTreeDonVi(this.data)
      this.fieldsDonVi = { dataSource: tree, id: 'ID', text: 'NAME', parentID: 'PARENT_ID', hasChildren: 'hasChild' }
    },
    show (title, data) {
      return new Promise((resolve, reject) => {
        this.data = data
        this.title = title
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    createTreeDonVi (data) {
      console.log('🚀 ~ file: index.vue ~ line 31 ~ createTreeDonVi ~ data', data)
      let tree = []
      let donviChaArray = data.map(item => (item['PARENT_ID']))
      let donviArray = data.map(item => (item['ID']))
      data.forEach(item => {
        if (donviChaArray.includes(item['ID'])) {
          if (!donviArray.includes(item['PARENT_ID'])) {
            delete item.PARENT_ID
          }
          tree.push({...item, hasChild: true, expanded: true})
        } else {
          // tree.push(item)
          // node is only child
          if (item['PARENT_ID'] != null && donviArray.includes(item['PARENT_ID'])) tree.push(item)
          else {
            // case both child, parent
            if (!donviArray.includes(item['PARENT_ID'])) {
              delete item.PARENT_ID
            }
            tree.push({...item, hasChild: true, expanded: true}) // PARENT_ID = null, -> item is parent node
          }
        }
      })
      return tree
    }
  }
}
</script>
<style>
</style>
