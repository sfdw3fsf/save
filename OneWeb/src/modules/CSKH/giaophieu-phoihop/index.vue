<template src="./index.template.html"></template>
<script>
import { required } from 'vuelidate/lib/validators'
import DateTimeLib from '../../../utils/DateTimeLib'
import {DataManager, Predicate, Query} from '@syncfusion/ej2-data'

export default {
  name: 'GiaoPhieuPhoiHop',
  data: function () {
    return {
      dsHuongGiao: [],
      dsDonViDaGiao: [],
      dsDonViCanGiao: [],
      currentItem: {
        MA_HT: '',
        MA_TB: '',
        QUYTRINH_ID: 0,
        TEN_TB: '',
        DIACHI: '',
        HTKH_ID: null,
        PHIEUHT_ID: null,
        TTHT_ID: null
      },
      txtNoiDung: '',
      txtCongVan: '',
      huongGiaoID: 0,
      dialog: {
        target: '.main-wrapper',
        width: '85%',
        animationSettings: { effect: 'Zoom' },
        position: { X: 'center', Y: 'top' }
      },
      donViChuaGiao_grid: {
        items: [],
        selected: {},
        key: 0
      },
      donViDaGiao_grid: {
        items: [],
        selected: {},
        key: 0
      },
      donViTree: [],
      treeView: null,
      searchDonViValue: '',
      danhsachDonViTree: [],
      danhsachFilterTree: [],
      fieldsDonVi: '',
    }
  },
  validations: {
    txtNoiDung: {
      required
    }
  },
  props: { pQuyTrinhID: Number, pMaHT: String, pHuongGiaoID: Number},
  computed: {
    // fieldsDonVi() {
    //   return {
    //     dataSource: this.donViTree,
    //     id: 'DONVI_ID',
    //     parentID: 'DONVICHA_ID',
    //     text: 'TEN_DV',
    //     hasChildren: 'hasChild',
    //     expanded: 'expanded',
    //   }
    // },
    // huongGiaoText: function () {
    //   let r = "";
    //   let f = this.dsHuongGiao.find((x) => x.HUONGGIAO_ID == this.huongGiaoID);
    //   if (f != null) {
    //     r = f.HUONGGIAO;
    //   }
    //   return r;
    // },
  },
  async created() {
    await this.loadData()

  },
  async updated() {
    // if (this.pMaHT!=this.currentItem.MA_HT)
    // {
    //   await this.getThongTinHoTroKH(this.pMaHT)
    //   //this.donViChuaGiao_grid.items=await this.getDSDonViChuaGiaoPhoiHop(this.currentItem.HTKH_ID);
    // }
  },
  methods: {
    // move() {
    //   const selected = this.donViDaGiao_grid.selected;
    //   selected.join(',');

    //   const arrselected = [];
    //   const unSelected = [];
    //   let count=0;
    //  this.donViDaGiao_grid.items.forEach(item=>{
    //   count=0;
    //   selected.forEach(x=>
    //   {
    //     if (x.DONVI_ID==item.DONVI_ID)
    //     count=1;
    //   })
    //     if (count==1)
    //       arrselected.push(item);
    //     else
    //       unSelected.push(item)
    //  })
    //  this.donViChuaGiao_grid.items=[...this.donViChuaGiao_grid.items,...arrselected]
    //  this.donViDaGiao_grid.items=[...unSelected];
    //  console.log('this.selected',selected)

    // },
    // unmove() {
    //   // const selected = this.dsKhongKhoa.filter(x => x._CHECK === true)
    //   // const unSelected = this.dsKhongKhoa.filter(x => x._CHECK === false)
    //   // this.dsKhoaThueBao = [...selected, ...this.dsKhoaThueBao]
    //   // this.dsKhongKhoa = [...unSelected]
    //   // this.bk_dsKhoaThueBao = [...this.bk_dsKhoaThueBao, ...selected]
    //   const selected = this.donViChuaGiao_grid.selected;
    //   selected.join(',');
    //   const arrselected = [];
    //   const unSelected = [];
    //  this.donViChuaGiao_grid.items.forEach(item=>{
    //     if (selected.includes(item.DONVI_ID))
    //     {
    //       arrselected.push(item);

    //     }
    //     else
    //     {
    //       unSelected.push(item)
    //     }
    //  })
    //  this.donViDaGiao_grid.items=[...this.donViDaGiao_grid.items,...arrselected]
    //  this.donViChuaGiao_grid.items=[...unSelected];
    //  console.log('donViDaGiao_grid',this.donViDaGiao_grid.items)
    // },
    closePopup() {},
    async openDialog() {
      console.log('openDialog', this.pQuyTrinhID, this.pMaHT, this.pHuongGiaoID)
      this.$refs.popupGiaoPhieuPhoiHop.show()
      await this.getThongTinHoTroKH(this.pMaHT)
      await this.bindDonViDaGiao()
      await this.bindDonViCanGiao()
    },
    xoaPhieu: async function (phieuht_id) {
      let rs = await this.$root.context.post('/web-suco/htkh/xoa-phieu', {
        id: phieuht_id
      })
      return rs.data.result
    },
    itemSelectedChangedDaGiao(value) {
      this.donViDaGiao_grid.selected = value
      console.log('this.donViDaGiao_grid.selected', this.donViDaGiao_grid.selected)
    },
    itemSelectedChangedChuaGiao(value) {
      this.donViChuaGiao_grid.selected = value
    },
    huyGiao: async function () {
      let rs = await this.$root.context.post('/web-suco/htkh/huygiao-phieu', {
        id: this.currentItem.PHIEUHT_ID
      })
      return rs.data.result
    },
    capNhatDonVi: async function (huongGiaoID, donViID) {
      let rs = await this.$root.context.post('/web-suco/htkh/capnhat-donvi', {
        htkhID: this.currentItem.HTKH_ID,
        huongGiaoID: huongGiaoID,
        donViID: donViID
      })
      return rs.data.result
    },
    giaoPhieu: async function (dsDonVi) {
      try {
        this.loading(true)
        let rs = await this.$root.context.post('/web-cskh/api/xu-ly-thue-bao/giaophieu-hotro-phoihop', {
          pHTKHID: this.currentItem.HTKH_ID,
          pNoiDung: this.txtNoiDung,
          pHuongGiaoID: this.huongGiaoID,
          soCongVan: this.txtCongVan,
          pDSDonVi: dsDonVi
        })
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Giao phiếu thành công!')
        }
      } catch (error) {
        this.$toast.error(error.response.data.message)
      } finally {
        this.loading(false)
      }
    },
    getDsDonViTheoHuongGiaoID: async function (huongGiaoID) {
      let rs = await this.$root.context.post('/web-suco/htkh/get-ds-donvi-theo-huonggiao', {
        id: this.huongGiaoID
      })
      return rs.data
    },
    getDSDonViChuaGiaoPhoiHop: async function (pHTKHID) {
      let rs = await this.$root.context.get('/web-cskh/api/xu-ly-thue-bao/lay-danhsach-donv-vi-giao-phoi-hop/' + pHTKHID)
      return rs.data
    },
    getDSDonViDaGiaoPhoiHop: async function () {
      let req = {
        pHTKHID: this.currentItem.HTKH_ID,
        pTTHTID: 3
      }
      let rs = await this.$root.context.get('/web-cskh/api/xu-ly-thue-bao/lay-danhsach-phieugiao-phoihop_by_htkh_id', req)
      return rs.data
    },
    getDsDonViChuaGiao: async function (huongGiaoID) {
      let rs = await this.$root.context.post('/web-suco/htkh/get-ds-donvi-chuagiao', {
        htkhID: this.currentItem.HTKH_ID,
        huongGiaoID: this.huongGiaoID
      })
      return rs.data
    },
    getDsPhieuDaGiao: async function () {
      let rs = await this.$root.context.post('/web-suco/htkh/get-ds-phieu-dagiao', {
        id: this.currentItem.HTKH_ID
      })
      return rs.data
    },
    getDsHuongGiao: async function () {
      let data = {
        pQuyTrinhID: this.currentItem.QUYTRINH_ID,
        pTTHTID: this.currentItem.TTHT_ID
      }
      let rs = await this.$root.context.get('/web-cskh/api/xu-ly-thue-bao/lay-huonggiao-theo-trangthai', data)
      this.dsHuongGiao = rs.data
      this.huongGiaoID = this.dsHuongGiao[0].HUONGGIAO_ID
      console.log('huongGiaoID', this.huongGiaoID)
    },
    kiemTraGiaoDonVi: async function (donvi_id) {
      let rs = await this.$root.context.post('/web-suco/htkh/kiemtra-giao-donvi', {
        htkhID: this.currentItem.HTKH_ID,
        donViID: donvi_id
      })
      return rs.data.result
    },
    getTrangThaiPhoiHop: async function (phieuht_id) {
      let rs = await this.$root.context.post('/web-suco/htkh/get-trangthai-phoihop', {
        id: phieuht_id
      })
      return rs.data
    },
    bindData: async function (phieuht_id) {
      this.$root.showLoading(true)
      try {
        let htkh = await this.getHTKHTheoPhieu(phieuht_id)
        if (htkh != null) {
          this.currentItem = {
            MA_HT: htkh.MA_HT,
            MA_TB: htkh.MA_TB,
            QUYTRINH_ID: htkh.QUYTRINH_ID,
            TEN_TB: htkh.TEN_TB,
            DIACHI: htkh.DIACHI,
            HTKH_ID: htkh.HTKH_ID,
            PHIEUHT_ID: htkh.PHIEUHT_ID,
            NGAY_TN: DateTimeLib.toDateObjectWithFormat(htkh.NGAY_TN, 'DD/MM/YYYY HH:mm:ss')
          }
          await this.loadData()
        } else {
          this.$root.toastError('Phiếu hỗ trợ không hợp lệ.')
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    getHTKH: async function (htkh_id) {
      let rs = await this.$root.context.post('/web-suco/htkh/get-thongtin-htkh', {
        id: htkh_id
      })
      return rs.data
    },
    async getThongTinHoTroKH(pMaHT) {
      try {
        var rs = await this.$root.context.get(`web-cskh/api/xu-ly-thue-bao/lay-thongtin-hotro-khachhang-theoma?pMaHT=${pMaHT}`)
        if (rs.data) {
          this.currentItem.HTKH_ID = rs.data[0].HTKH_ID
          this.currentItem.MA_TB = rs.data[0].MA_TB
          this.currentItem.MA_HT = rs.data[0].MA_HT
          this.currentItem.NGAY_TN = rs.data[0].NGAY_TN
          this.currentItem.TEN_TB = rs.data[0].TEN_TB
          this.currentItem.DIACHI = rs.data[0].DIACHI_LD
          this.currentItem.QUYTRINH_ID = this.pQuyTrinhID
          this.currentItem.TTHT_ID = rs.data[0].TTHT_ID
          console.log('currentItem', this.currentItem)
          await this.getDsHuongGiao()
        }

        console.log('infoPhieuHoTro.data = ', this.infoPhieuHoTro.data)
      } catch (errrrrrr) {}
    },

    getHTKHTheoPhieu: async function (phieuht_id) {
      let rs = await this.$root.context.post('/web-suco/htkh/get-thongtin-htkh-theo-phieu', {
        id: phieuht_id
      })
      return rs.data
    },
    loadData: async function () {
      this.dsHuongGiao = []
      this.dsDonViDaGiao = []
      this.dsDonViCanGiao = []
      this.dsHuongGiao = await this.getDsHuongGiao()
      if (this.dsHuongGiao.length > 0) {
        this.huongGiaoID = this.dsHuongGiao[0].HUONGGIAO_ID
      }
      await this.bindDonViDaGiao()
      await this.bindDonViCanGiao()
    },
    bindDonViCanGiao: async function () {
      //this.dsDonViCanGiao = await this.getDsDonViChuaGiao();
      this.donViChuaGiao_grid.items = await this.getDSDonViChuaGiaoPhoiHop(this.currentItem.HTKH_ID)
      this.donViTree = this.createTreeDonVi(this.donViChuaGiao_grid.items)
      this.defaultDsDonViTree = this.donViTree
      this.danhsachDonViTree = this.donViTree
      this.fieldsDonVi = {
        dataSource: this.donViTree,
        id: 'DONVI_ID',
        parentID: 'DONVICHA_ID',
        text: 'TEN_DV',
        hasChildren: 'hasChild',
         expanded: 'expanded',
      }
    },
    bindDonViDaGiao: async function () {
      //this.dsDonViDaGiao = await this.getDsPhieuDaGiao();
      this.donViDaGiao_grid.items = await this.getDSDonViDaGiaoPhoiHop()
    },
    comboboxHuongGiao_Change: async function (args) {
      this.$root.showLoading(true)
      try {
        await this.bindDonViCanGiao()
      } finally {
        this.$root.showLoading(false)
      }
    },
    btnGiaoPhieu_Click: async function (args) {
      this.$root.showLoading(true)
      try {
        this.$v.$touch()
        if (this.$v.$invalid) {
          if (this.$v.txtNoiDung.$invalid) {
            this.$root.toastError('Bạn hãy nhập nội dung giao')
          }
        } else {
          if (this.huongGiaoID == 0) {
            this.$root.toastError('Bạn chọn hướng giao')
          } else {
            this.treeView = this.$refs['gridDonViCanGiao']
            const items = this.treeView.getAllCheckedNodes()

            // Lấy DS ID cha
            const isParent = new Set(this.fieldsDonVi.dataSource.map(item => item.DONVICHA_ID).filter(id => id != null))
            // // Lọc ra chỉ giữ các ID đơn vị con đã được check
            const leafCheckedIds = items.filter(id => !isParent.has(parseInt(id)))  // nếu id là số

            if (leafCheckedIds.length === 0) {
              return this.$root.toastError('Bạn chưa chọn đơn vị nhận!');
            }
            let accept = false
            accept = await this.$confirm('Bạn chắc chắn muốn giao phiếu hỗ trợ đến các đơn vị đang chọn ?', 'Xác nhận thực hiện', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
              type: 'warning',
              dangerouslyUseHTMLString: true
            })
              .then((s) => true)
              .catch((e) => {
                return false
              })
            if (accept) {
              const idList = leafCheckedIds.join(',');
              this.$emit('dataChanged', args)
              await this.giaoPhieu(idList)
              await this.bindDonViDaGiao()
              await this.bindDonViCanGiao()
              this.$refs.gridDonViDaGiao.refresh()
              this.fieldsDonVi.dataSource = this.donViTree
              // let items = this.donViChuaGiao_grid.selected
              // if (items.length > 0) {
              //   this.$emit('dataChanged', args)
              //   await this.giaoPhieu(items)
              //   await this.bindDonViDaGiao()
              //   await this.bindDonViCanGiao()
              //   this.$refs.gridDonViDaGiao.refresh()
              //   this.$refs.gridDonViCanGiao.refresh()
              // } else {
              //   this.$root.toastError('Bạn chưa chọn đơn vị nhận')
              // }
            }
          }
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    checkEmpty: function (value) {
      return value == null || value == undefined || value.toString().trim() == ''
    },
    gridDsDonViDaGiao_onCommandClicked: async function (cmdName, args) {
      if (cmdName == 'DELETE') {
        //console.log("args", args);
        //let ck = await this.getTrangThaiPhoiHop(args.PHIEUHT_ID);
        if (args.TTPH_ID == 1) {
          let accept = false
          accept = await this.$confirm('Bạn chắc chắn muốn xóa phiếu đã giao ?', 'Xác nhận thực hiện', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning',
            dangerouslyUseHTMLString: true
          })
            .then((s) => true)
            .catch((e) => {
              return false
            })
          if (accept) {
            this.$root.showLoading(true)
            try {
              let rs = await this.xoaPhieu(args.PHIEUHT_ID)
              if (rs) {
                this.$emit('dataChanged', args)
                this.$root.toastSuccess('Đã xóa phiếu thành công')
                await this.bindDonViDaGiao()
                await this.bindDonViCanGiao()
                this.$refs.gridDonViDaGiao.refresh()
                this.fieldsDonVi.dataSource = this.donViTree
              } else {
                this.$root.toastError('Xóa phiếu thất bại.')
              }
            } finally {
              this.$root.showLoading(false)
            }
          }
        } else {
          this.$root.toastError('Bạn không được phép xóa phiếu đang ở trạng thái khác MỚI')
        }
      }
    },
    btnHuyGiao_Click: async function (args) {
      let accept = false
      accept = await this.$confirm('Bạn chắc chắn muốn thực hiện hủy giao phiếu ?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (accept) {
        this.$root.showLoading(true)
        try {
          let ck = await this.huyGiao()
          if (ck == 'OK') {
            this.$root.toastSuccess('Hủy giao thành công.')
            this.$emit('dataChanged', args)
            this.$emit('dataDeleted', args)
          } else this.$root.toastError('Hủy giao thất bại: ' + ck)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    btnCapNhatDonVi_Click: async function (args) {
      let items = this.$refs.gridDonViCanGiao.getSelectedRecords()
      if (items == null || items.length == 0) {
        this.$root.toastError('Bạn chưa chọn đơn vị.')
      } else {
        if (this.huongGiaoID == 0) {
          this.$root.toastError('Bạn chưa chọn hướng giao.')
        } else {
          let accept = false
          accept = await this.$confirm('Bạn chắc chắn muốn cập nhật đơn vị hỗ trợ ?', 'Xác nhận thực hiện', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning',
            dangerouslyUseHTMLString: true
          })
            .then((s) => true)
            .catch((e) => {
              return false
            })
          if (accept) {
            let rs = await this.capNhatDonVi(this.huongGiaoID, items[0].DONVI_ID)
            if (rs) {
              this.$emit('dataChanged', args)
              await this.bindDonViDaGiao()
              await this.bindDonViCanGiao()
              this.$refs.gridDonViDaGiao.refresh()
              this.fieldsDonVi.dataSource = this.donViTree
              this.$root.toastSuccess('Cập nhật đơn vị hỗ trợ thành công.')
            } else {
              this.$root.toastError('Cập nhật đơn vị hỗ trợ thất bại.')
            }
          }
        }
      }
    },
    btnTimKiem_Click: async function (args) {
      if (this.checkEmpty(this.currentItem.PHIEUHT_ID)) {
        this.$root.toastError('Chưa có thông tin phiếu.')
      } else {
        await this.bindData(this.currentItem.PHIEUHT_ID)
      }
    },
    createTreeDonVi(dsDonvi) {
      //     let tree = []
      //     let lvtChaArray = lvtDonVi.map(item => (item['DONVICHA_ID']))
      //     lvtDonVi.forEach(item => {
      //       if (lvtChaArray.includes(item['DONVI_ID'])) {
      //         tree.push({...item, hasChild: true, expanded: false})
      //       } else {
      //         tree.push(item)
      //       }
      //     })
      // return tree;
      let tree = []
      let donViChaArray = dsDonvi.map((item) => item['DONVICHA_ID'])
      let donViArray = dsDonvi.map((item) => item['DONVI_ID'])
      dsDonvi.forEach((item) => {
      //check item la parent node
      if (donViChaArray.includes(item['DONVI_ID'])) {
                //check donViChaArray khong co trong ds donViArray thi xoa donViChaArray
                if (!donViArray.includes(item['DONVICHA_ID'])) {
                  delete item.DONVICHA_ID
                }
                tree.push({ ...item, hasChild: true, expanded: false })
              } else {
                tree.push({ ...item, hasChild: false, expanded: false })
              }
      })
      return tree
    },
    // Lấy tất cả ID cha của các node đã được check
    getAllParentIds(checkedIds) {
      const parentIds = new Set()

      const idMap = this.donViTree.reduce((map, item) => {
        map[item.DONVI_ID] = item
        return map
      }, {})

      checkedIds.forEach(id => {
        let current = idMap[id]
        while (current && current.DONVICHA_ID) {
          parentIds.add(current.DONVICHA_ID.toString())
          current = idMap[current.DONVICHA_ID]
        }
      })

      return [...parentIds]
    },
    onTreeDataBound() {
      const tree = this.$refs.gridDonViCanGiao?.ej2Instances;
      if (!tree) return;

      const tickedIDs = this.donViTree
        .filter(item => item.TICK === 1)
        .map(item => item.DONVI_ID.toString());
      // Checked các node mặc định với TICK = 1
      tree.checkedNodes = tickedIDs;
      // Mở rộng các node cha của các node đã được check mặc định
      const expanded = this.getAllParentIds(tickedIDs);
      tree.expandedNodes = expanded;
    },
    async searchDonVi () {
              try {
                this.loading(true)
                let predicate
                let predicats = []
                let _array = []
                let _filter = []
                if (this.searchDonViValue.trim() !== '') {
                  predicate = new Predicate('TEN_DV', 'contains', this.searchDonViValue.trim(), true)
                  let filteredList = new DataManager(this.danhsachDonViTree).executeLocal(new Query().where(predicate))
                  for (let j = 0; j < filteredList.length; j++) {
                    _filter.push(filteredList[j]['DONVI_ID'])
                    let filters = this.getFilterItems(filteredList[j], this.danhsachDonViTree)
                    for (let i = 0; i < filters.length; i++) {
                      if (_array.indexOf(filters[i]) === -1 && filters[i] != null) {
                        _array.push(filters[i])
                        predicats.push(new Predicate('DONVI_ID', 'equal', filters[i], false))
                      }
                    }
                  }
                  if (predicats.length === 0) {
                    this.changeDataSource([])
                  } else {
                    let query = new Query().where(Predicate.or(predicats))
                    let newList = new DataManager(this.danhsachDonViTree).executeLocal(query)
                    this.changeDataSource(newList)
                    setTimeout(() => {
                      this.$refs.gridDonViCanGiao.expandAll()
                    }, 100)
                  }
                } else {
                  this.changeDataSource(this.danhsachDonViTree)
                }
              } catch (error) {
                console.log(error)
              } finally {
                this.loading(false)
              }
    },
    // Find the Parent Nodes for corresponding childs
    getFilterItems (fList, list) {
      let nodes = []
      nodes.push(fList['DONVI_ID'])
      let query2 = new Query().where('DONVI_ID', 'equal', fList['DONVICHA_ID'], false)
      let fList1 = new DataManager(list).executeLocal(query2)
      if (fList1.length !== 0) {
        let pNode = this.getFilterItems(fList1[0], list)
        for (let i = 0; i < pNode.length; i++) {
          if (nodes.indexOf(pNode[i]) === -1 && pNode[i] != null) {
            nodes.push(pNode[i])
          }
        }
        return nodes
      }
      return nodes
    },
    changeDataSource (data) {
        this.fieldsDonVi = {
        dataSource: data, id: 'DONVI_ID', text: 'TEN_DV', parentID: 'DONVICHA_ID', hasChildren: 'hasChild'
      }
    },
  }
}
</script>
<style>
.e-treeview .e-list-item.e-active > .e-text-content .e-check
 {
    border-color: #007bff;
}
</style>
