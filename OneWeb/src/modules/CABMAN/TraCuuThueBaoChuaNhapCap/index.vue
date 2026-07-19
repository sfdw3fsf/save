<template src="./index.html"></template>
<script>
import Modal from './Modal/LichSuTacDongCap'
import ModalQuanLyCapGoc from './Modal/QuanLyCapGoc'
import ModalXuatDuLieu from '../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu'
import ModalDauNoiThueBao from './Modal/DauNoiThueBao'

export default {
  components: { Modal, ModalQuanLyCapGoc, ModalXuatDuLieu, ModalDauNoiThueBao },
  name: 'TraCuuThueBaoChuaNhapCap',
  computed: {
    getMA_TB () {
      return this.thuebao !== null ? this.thuebao.MA_TB : null
    },
    expanded () {
      return []
    },
    dsThuocTinh () { return [...this.dsThuocTinhXuatDuLieu] },
    dsData () {
      return [...this.dsThueBaoChuaNhapCap]
    },
    disableBtnDN () {
      let disableBtnDN = (this.thuebao === null || (this.thuebao !== null && this.thuebao.TEN_KET_CUOI !== null))
      return disableBtnDN
    },
    disableBtnQL () {
      let disableBtnQL = (this.thuebao === null || (this.thuebao !== null && this.thuebao.TEN_KET_CUOI === null))
      return false
    },
    disableBtnXuatFile () {
      let disableBtn = this.dsThueBaoChuaNhapCap.length === 0
      return disableBtn
    }
  },
  methods: {

    dauNoiThuebao () {
      if (this.disableBtnDN) return false
      let thongTinDauNoi = {
        choPhepDoiTramTbi: true,
        tramTbiId: -1,
        chonTramTbiTheoTB: false,
        thueBaoId: this.thuebao.THUEBAO_ID,
        kieu: 0
      }
      let modal = this.$refs['ModalDauNoiThueBao']
      modal.setData(thongTinDauNoi)
      modal.show()
    },
    onChange () {
      this.layDsThueBaoChuaNhapCap()
    },
    hide () {},
    xuatFileExcel () {
      if (this.disableBtnXuatFile) return false
      if (this.dsThueBaoChuaNhapCap.length === 0) {
        this.$root.toastError('Không có dữ liệu để xuất')
        return
      }
      this.dsThuocTinhXuatDuLieu = []
      for (let key in this.dsThueBaoChuaNhapCap[0]) {
        this.dsThuocTinhXuatDuLieu.push(key)
      }
      this.$bvModal.show('modal-xuat-dulieu')
    },
    grid_doubleClick (args) {
      console.log('grid_doubleClick', args)
      this.thuebao = args.rowData
      this.$bvModal.show('modal-id')
    },
    grid_DataBound () {
      var grid = document.getElementById('grid').getElementsByClassName('e-grid')
      if (grid.length > 0) {
        var gridInstance = grid[0].ej2_instances[0]
        gridInstance.autoFitColumns(['DIACHI_LD', 'TEN_TB'])
        // gridInstance.height = gridInstance.getContentTable().offsetHeight
      }
    },
    phanToQLCap () {
      if (this.disableBtnQL) return false
      this.$refs.ModalQuanLyCapGoc.show().then((result) => {
        if (result.ok) {

        }
      })
    },
    nodeclick (node) {
      console.log('nodeclick', node)
      this.node = node
    },
    async layDsDonVi () {
      var rs = await this.$root.context.get(
        '/web-cabman/mang-truyen-dan/cay-donvi'
      )
      this.ds_donvi = this.array_to_tree(rs.data)
      console.log('ds_donvi', this.ds_donvi, this.$root.token.getDonViID())
    },
    createTreeDonVi (dsDonVi) {
      let tree = []
      let donviChaArray = dsDonVi.map(item => (item['DONVI_CHA_ID']))
      donviChaArray = new Set(donviChaArray)
      donviChaArray = Array.from(donviChaArray).sort(function (a, b) {
        return a - b
      })
      let donviArray = dsDonVi.map(item => (item['DONVI_ID'])).sort(function (a, b) {
        return a - b
      })
      dsDonVi.forEach(item => {
        // check item la parent node
        if (donviChaArray.includes(item['DONVI_ID'])) {
          // check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
          if (!donviArray.includes(item['DONVI_CHA_ID'])) {
            delete item.DONVI_CHA_ID
          }
          tree.push({...item, hasChild: true, expanded: true})
        } else {
          if (item['DONVI_CHA_ID'] != null && donviArray.includes(item['DONVI_CHA_ID'])) tree.push(item)
          else {
            if (!donviArray.includes(item['DONVI_CHA_ID'])) {
              delete item.DONVI_CHA_ID
            }
            tree.push({...item, hasChild: false, expanded: true}) // DONVI_CHA_ID = null, -> item is parent node
          }
        }
      })
      return tree
    },
    // createTreeDonVi (dsDonVi) {
    //   let tree = []
    //   let donviChaArray = dsDonVi.map(item => (item['DONVI_CHA_ID']))
    //   let donviArray = dsDonVi.map(item => (item['DONVI_ID']))
    //   dsDonVi.forEach(item => {
    //     if (donviChaArray.includes(item['DONVI_ID'])) {
    //       if (!donviArray.includes(item['DONVI_CHA_ID'])) {
    //         delete item.DONVI_CHA_ID
    //       }
    //       tree.push({...item, hasChild: true, expanded: true})
    //     } else {
    //       tree.push(item)
    //     }
    //   })
    //   return tree
    // },
    async getDsDonViTheoNV () {
      await this.$root.context.get(
        '/web-cabman/mang-truyen-dan/cay-donvi'
      ).then(res => {
        this.dsDonVi = res.data
        console.log('getDsDonViTheoNV', this.dsDonVi)
      }).catch(e => { throw e })
    },
    async layDsThueBaoChuaNhapCap () {
      if (this.node === null) return false
      try {
        this.loading(true)
        this.thuebao = null
        this.dsThueBaoChuaNhapCap = [

        ]
        // this.dsThueBaoChuaNhapCap.push({
        //   DIACHI_LD: 'An Hòa, Xã An Lư, Huyện Thủy Nguyên',
        //   DOI_CAP_GOC: null,
        //   LOAIHINH: 'MyTV',
        //   MA_TB: 'hpgmytv120050',
        //   NGAY_SD: '21/08/2015',
        //   TENTO: 'Tổ Kỹ thuật Thủy Nguyên 1',
        //   TENTRUNGTAM: 'Trung tâm Viễn thông 2',
        //   TEN_CAP_GOC: null,
        //   TEN_KET_CUOI: null,
        //   TEN_NV: null,
        //   TEN_TB: 'Trịnh Văn Chung',
        //   THUEBAO_ID: 1273458,
        //   TRANGTHAI_TB: 'Hoạt động bình thường',
        //   VITRI: null
        // })
        console.log('🚀 ~ file: index.vue ~ line 142 ~ layDsThueBaoChuaNhapCap ~ rs.data', this.node.nodeData.id)
        var rs = await this.$root.context.get(
          '/web-cabman/mang-truyen-dan/ds-thuebao-chua-nhapcap' + '?donvi_id=' + this.node.nodeData.id
        )
        console.log('layDsThueBaoChuaNhapCap', rs.data)

        this.dsThueBaoChuaNhapCap = rs.data
        this.loading(false)
      } catch (error) {
        this.loading(false)
      }
      this.loading(false)
    },
    filterNode (value, data) {
      if (!value) return true
      return data.TEN_DV.indexOf(value) !== -1
    },
    array_to_tree (items, DONVI_ID = 900, link = 'DONVI_CHA_ID') {
      return items.filter(item => item[link] === DONVI_ID).map(item => ({ ...item, DON_VI_CON: this.array_to_tree(items, item.DONVI_ID) }))
    },
    grid_selectedRowChanged (data) {
      // console.log('grid_selectedRowChanged', data)
      this.thuebao = data
    },
    onEnter () {
      console.log('onEnter', this.filterText)
      if (this.filterText.length > 0) {
        var text = this.filterText
        var treeObj = document.getElementById('treeview').ej2_instances[0]
        // console.log('treeObj', treeObj)
        if (treeObj) {
          var element = this.fieldsDonVi.dataSource.find(function (element) {
            return element.TEN_DV.toUpperCase().includes(text.toUpperCase())
          })
          if (element) {
            var node = treeObj.getNode(element.DONVI_ID.toString())
            // console.log('node', node)
            treeObj.selectedNodes = [node.id.toString()]
          }
        }
      }
    },
    async loadTreeDonVi () {
      try {
        this.loading(true)
        this.filterText = null
        await this.getDsDonViTheoNV()
        let donViTree = this.createTreeDonVi(this.dsDonVi)
        this.fieldsDonVi = { dataSource: donViTree, id: 'DONVI_ID', text: 'TEN_DV', parentID: 'DONVI_CHA_ID', hasChildren: 'hasChild' }
        console.log('🚀 ~ file: index.vue ~ line 195 ~ loadTreeDonVi ~ donViTree', donViTree)
      } catch (err) {
        console.log(err)
      } finally {
        this.loading(false)
      }
    }
  },
  created () {
    this.loadTreeDonVi()
  },
  data () {
    return {
      defaultProps: {
        children: 'DON_VI_CON',
        label: 'TEN_DV'
      },
      filterText: null,
      ds_donvi: [],
      dsThueBaoChuaNhapCap: [],
      totalRow: 50,
      pageIndex: 0,
      isSelected: -1,
      thuebao: null,
      indexFocus: 0,
      node: null,
      dsDonVi: [],
      fieldsDonVi: '',
      dsThuocTinhXuatDuLieu: []
    }
  },
  watch: {
    // disableBtnDN (val) {
    //   console.log('disableBtnDN', val)
    // },
    // disableBtnQL (val) {
    //   console.log('disableBtnQL', val)
    // },
    thuebao (val) {
      console.log('thuebao', val)
    }
  },
  async mounted () {

  }
}
</script>
<style scoped src="./index.scss">
</style>
