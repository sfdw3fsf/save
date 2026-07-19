<template src="./index.template.html"></template>
<script>

import GianDo1 from "./GianDo";
import TuyenTruyenDan from './modal/danhmucttd'
import QuanLyDoiTuong from './QuanLyDoiTuong/index'
import QuanLyLienKet from './QuanLyLienKet/index'
import DiagramLib from "../../../utils/DiagramLib";
import XemTruocKhiIn from "./XemTruocKhiIn"
import Panzoom from "@panzoom/panzoom";


export default {
  name: 'CauTrucMangBangThongRong',
  components: {QuanLyDoiTuong,QuanLyLienKet,TuyenTruyenDan,GianDo1,DiagramLib, XemTruocKhiIn},
  data() {
    return {
      count:0,
      obj1:Object,
      obj2:Object,
      objChange:Object,
      idChange:"",
      offsetx:"",
      offsety:"",
      position:{},
      dsLoaiTuyen: [],
      dsLoaiLk: [],
      dsTuyen: [], panzoom:'',
      range:'',
      dsLienKet: [],
      dsLoaiDoiTuong: [],
      dsDoiTuong: [],
      dsDoiTuongGianDo: [],
      dsDoiTuongAfterChange: [],
      loaiTuyen: null,
      doiTuongIdSelected: null,
      tenDoiTuongSelected: null,
      tenLoaiDoiTuongSelected: null,
      tenTuyenSelected: null,
      dataSelected: {
        loaiTuyen: null,
        tuyen: null,
        tenTuyen: null,
        loaiDoiTuong: null,
        doiTuong: null
      },
      indexFocus :1,
      dsNodes: [],
      objUpdate: {},
      objUpdateLienKet: {},
      dsConnectors: [],
      gianDoRefresh: false,
        zoomFactor:null,

      // Tiện ích diagram
      isShowDoiTuong: true,
      isShowLienKet:true,
      isShowTitle:true,
      isShowLayer:false,
      isShowGridLine:true,
      isHoverRange:false,

      //In gian do
      htmlDiagram:Object,
      horizontalOffset:null,
      verticalOffset:null,
      isHoverScroll:false,
      initOffset:{}
    }
  },
  methods: {
    showTaoDoiTuong() {
      this.$bvModal.show("QuanLyDoiTuong")
    }, showTaoLienKet() {
      this.$bvModal.show("QuanLyLienKet")
    },
    btnQLTuyen() {
      this.$bvModal.show("TuyenTruyenDan")
    },
    btnReloadLoaiTuyen: async function () {
      await this.getLoaiTuyen()
    },
    btnGhilai: async function () {
      try {
        this.loading(true)
        await this.SaveFullDoiTuong()
        await this.SaveFullLienKet()
        this.$root.toastSuccess("Ghi lại thành công")

      }catch (e){
        console.log(e)
        this.$root.toastError("Có lỗi xảy ra khi lưu giản đồ")
      }finally {
        this.loading(false)
      }
    }, reloadGianDo: async function () {
      this.obj1 = new Object()
      this.obj2 = new Object()
      let data = {
        P_LOAI_DOITUONG_ID: this.dataSelected.loaiDoiTuong,
        P_TUYENTD_ID: this.dataSelected.tuyen
      }
      await this.getDsDoiTuong(data)
      await this.napGianDo()
    }, taoLienKet: function (obj) {
      if (obj != null) {
        if (obj.type === "DOUBLE_CLICK") {
          if(obj.typeObj ==="nodes"){
            this.obj1 = obj
          }
        }else if (obj.type === "CLICK"){
          this.position.x = obj.x
          this.position.y = obj.y
          this.obj2 = obj
          if(this.obj1.id && this.obj1.id !==this.obj2.id && obj.typeObj ==="nodes"){
            this.obj1.id =   this.obj1.id.split("_")[1]
            this.obj2.id =   this.obj2.id.split("_")[1]

            let tenNguon = this.dsDoiTuongGianDo.filter(x => x.doituong_id === parseInt(this.obj1.id))
            let tenDich = this.dsDoiTuongGianDo.filter(x => x.doituong_id === parseInt(obj.id))
            window.dsDoiTuongAfterChange  =this.dsDoiTuongAfterChange
            this.objUpdateLienKet = {
              KIEU_ : "INSERT"
            }
            this.objUpdateLienKet.TUYENTD_ID = this.dataSelected.tuyen
            this.objUpdateLienKet.TEN_TUYENTD_ID = this.tenTuyenSelected
            this.objUpdateLienKet.DT_NGUON_ID = this.obj1.id
            this.objUpdateLienKet.DT_NGUON_TEN = "tenNguon"
            this.objUpdateLienKet.DT_DICH_TEN = "tenDich"
            this.objUpdateLienKet.DT_DICH_ID = this.obj2.id

          this.showTaoLienKet()
      }
        }
      }
    }, positionChange: function (obj) {
    if (obj != null) {
      this.offsetx =obj.offsetX
      this.offsety =obj.offsetY
      if(obj.type === "OFFSET_CHANGE"){
        this.objChange = obj

        let convert = this.dsDoiTuongAfterChange.map(item => { let newItem = { };  Object.keys(item).forEach(key => newItem[key.toLowerCase()] = item[key]); return newItem; } )
        this.dsDoiTuongAfterChange = convert.map(item => {
          return {   ...item,
            vitri_x: item.doituong_id == this.idChange ? obj.offsetX : item.vitri_x,vitri_y: item.doituong_id == this.idChange ? obj.offsetY: item.vitri_y
          };
        });
      }else if(obj.type === "ID_CHANGE") {
          this.idChange = obj.id

      }
      // console.log(this.dsDoiTuongAfterChange)

    }
  },
    SaveFullDoiTuong: async function () {
      try {
        // cap nhat ds doi tuong
        var rs = await this.$root.context.post(
          'web-suco/mang_bangrong/capNhatListDoiTuong',this.dsDoiTuongAfterChange
        )
        // if (rs.data) {
        //   this.$toast.success("Ghi lại thành công.")
        //   this.napGianDo()
        // }else{
        //   this.$toast.error("Ghi lại thất bại ")
        // }
      } catch (error) {
        // this.$toast.error("Ghi lại thất bại " + error)
      }
    },
    SaveFullLienKet: async function () {
      try {
        // cap nhat ds lien ket
        let dsLienKet = this.$refs.diagram.getConnectors()
        let countSuccess = 0
        for (let item of dsLienKet){
          let lienket = {}
          lienket.lienket_id = item.THONGTIN.LIENKET_ID
          lienket.ten_lk = item.THONGTIN.TEN_LK
          lienket.ten_ht = item.THONGTIN.TEN_HT
          lienket.loailk_id = item.THONGTIN.LOAILK_ID
          lienket.doituong_n_id = item.THONGTIN.DOITUONG_N_ID
          lienket.doituong_d_id = item.THONGTIN.DOITUONG_D_ID
          lienket.tuyentd_id = item.THONGTIN.TUYENTD_ID
          lienket.diemuon_x1 = item.THONGTIN.DIEMUON_X1
          lienket.diemuon_y1 = item.THONGTIN.DIEMUON_Y1
          lienket.diemuon_x2 = item.THONGTIN.DIEMUON_X2
          lienket.diemuon_y2 = item.THONGTIN.DIEMUON_Y2
          lienket.vitri_td_x = item.THONGTIN.VITRI_TD_X
          lienket.vitri_td_y = item.THONGTIN.VITRI_TD_Y
          let rs = await this.$root.context.post(
            'web-suco/mang_bangrong/capnhatlienket',lienket
          )
          if (rs.error === "200")
            countSuccess ++
        }
        // if (countSuccess === dsLienKet.length) {
        //   this.$toast.success("Ghi lại liên kết thành công.")
        // }else{
        //   this.$toast.error("Ghi lại thất bại ")
        // }
      } catch (error) {
      }
    },
    getLoaiTuyen: async function () {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          'web-cabman/mangloitruyendan/dsloaituyen'
        )
        this.dsLoaiTuyen = rs.data
        if (rs.data) {
          this.dataSelected.loaiTuyen = rs.data[0].LOAITTD_ID
          await this.getTuyen(rs.data[0].LOAITTD_ID)
          await this.getLoaiDoiTuong(rs.data[0].LOAITTD_ID)
        }
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    getLoaiLienKet: async function () {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          'web-suco/mang_bangrong/lay-loai-lk'
        )
        if (rs.data) {
          this.dsLoaiLk = rs.data
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    }, getTuyen: async function (loaiTuyenTdId) {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-cabman/mangloitruyendan/laydstruyendan?loaiTuyenTdId=${loaiTuyenTdId}`
        )
        this.dsTuyen = rs.data
        if (rs.data.length > 0) {
          this.dataSelected.tuyen = rs.data[0].TUYENTD_ID
          this.tenTuyenSelected = rs.data[0].TUYEN_TD
          this.napGianDo()
        } else {
          this.dataSelected.tuyen = ""
        }

      } catch (error) {

      } finally {
        this.loading(false)
      }
    }, getLoaiDoiTuong: async function (loaiTuyenTdId) {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-cabman/mangloitruyendan/laydsloaidoituong?loaiTuyenTdId=${loaiTuyenTdId}`
        )
        this.dsLoaiDoiTuong = rs.data
        if (rs.data.length > 0) {
          this.dataSelected.loaiDoiTuong = rs.data[0].LOAIDT_ID
          this.tenLoaiDoiTuongSelected = rs.data[0].TEN_LDT
        } else {
          this.dataSelected.loaiDoiTuong = ""
        }

      } catch (error) {
      } finally {
        this.loading(false)
      }
    }, getDsDoiTuong: async function (data) {
      if (!data.P_LOAI_DOITUONG_ID || !data.P_TUYENTD_ID){
        this.dsDoiTuong = []
        this.doiTuongIdSelected = null
        return
      }
      try {
        this.loading(true)
        await this.$root.context.post(
          '/web-cabman/mangloitruyendan/dsdoituong', data)
          .then(res => {
            if (this.dsDoiTuong) {
              this.dsDoiTuong = res.data
              this.doiTuongIdSelected = res.data[0].DOITUONG_ID
            }

          }).catch(e => {
            throw e
          }).finally(() => this.loading(false))

      } catch (error) {
      } finally {
        this.loading(false)
      }
    }, onChangeLoaiTuyen: async function () {

    }, onChangeTuyen: async function (dataItem) {
      this.tenTuyenSelected = this.dsTuyen[dataItem].TUYEN_TD
    }, onChangeLoaiDoiTuong: async function (dataItem) {
      this.tenLoaiDoiTuongSelected = this.dsLoaiDoiTuong[dataItem].TEN_LDT
    }
  , capnhatDoiTuong: function (item) {

      let ids = item.id.split("_")[1]
      window.ids = ids
     window.dsDoiTuongAfterChange = this.dsDoiTuongAfterChange
      this.objUpdate = this.dsDoiTuongAfterChange.filter(x => x.doituong_id === parseInt(ids))[0]
       this.objUpdate.KIEU_ = "UPDATE"
      this.objUpdate.TEN_LOAI_DT = this.tenLoaiDoiTuongSelected
      this.objUpdate.TEN_TTD = this.tenTuyenSelected
      // this.objUpdate.keys([key.toUpperCase()])
      let aa1= {}
      Object.keys(this.objUpdate).forEach(item => {
        aa1[item.toUpperCase()] = this.objUpdate[item]
      })
      this.objUpdate = aa1
      this.showTaoDoiTuong()
  },capNhatLienKet : function (item) {
      let lien_ket = this.dsLienKet.filter(x => x.LIENKET_ID === parseInt(item.id))[0]
      let idNguon = lien_ket.DOITUONG_N_ID
      let idDich = lien_ket.DOITUONG_D_ID
      let loailk_id = lien_ket.LOAILK_ID

      this.objUpdateLienKet = {
        KIEU_ : "UPDATE"
      }
      this.objUpdateLienKet.LIENKET_ID = parseInt(item.id)
      this.objUpdateLienKet.TUYENTD_ID = this.dataSelected.tuyen
      this.objUpdateLienKet.TEN_TUYENTD_ID = this.tenTuyenSelected
      this.objUpdateLienKet.DT_NGUON_ID = idNguon
      this.objUpdateLienKet.DT_NGUON_TEN = "tenNguon"
      this.objUpdateLienKet.DT_DICH_TEN = "tenDich"
      this.objUpdateLienKet.DT_DICH_ID = idDich
      this.objUpdateLienKet.LOAI_LK_ID = loailk_id
      this.objUpdateLienKet.TEN_HT = lien_ket.TEN_HT
      this.objUpdateLienKet.TEN_LK = lien_ket.TEN_LK

  this.showTaoLienKet()
},xoaDoiTuong: function () {

      this.$bvModal.msgBoxConfirm('Bạn có muốn xóa đối tượng  không?',
        {
          title: 'Xác nhận hành động.',
          centered: true,
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
        }).then((value) => {
        if (value) {
          this.loading(true)
          let ids = this.obj2.id.split("_")[1]
          let obj = this.dsDoiTuongGianDo.filter(x => x.DOITUONG_ID === parseInt(ids))[0]
          try {
            this.$root.context.post(
              "/web-suco/mang_bangrong/xoadoituong", {tuyentd_id: obj.TUYENTD_ID, doituong_id: ids}
            ).then(res => {

              if(res.data){
               this.$toast.success('Xoá thành công.')
                this.napGianDo()
                this.getDsDoiTuong({
                  P_LOAI_DOITUONG_ID: this.dataSelected.loaiDoiTuong,
                  P_TUYENTD_ID: this.dataSelected.tuyen
                })
                // this.napGianDo()
              }
            }).catch(e => {
              if(e.response.data.message_detail){
                this.$toast.error(e.response.data.message_detail)
              }else {
                this.$toast.error(e.response.data.message)
              }
            })
          }catch (e){
            console.log(e)
          }finally {
            this.loading(false)
          }
        }
      }
      )
    },xoaLienKet: function (item) {
    this.$bvModal.msgBoxConfirm('Bạn có muốn xóa liên kết này không?',
      {
        title: 'Xác nhận hành động.',
        centered: true,
        size: 'sm',
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy',
      }).then((value) => {
        if (value) {
          this.loading(true)
          let obj = this.dsLienKet.filter(x => x.LIENKET_ID === parseInt(item.id))[0]
          try {
            this.$root.context.post(
              "/web-suco/mang_bangrong/xoalienket", {lienket_id: item.id, tuyentd_id:obj.TUYENTD_ID }
            ).then(res => {

              if(res.data){
                this.$toast.success('Xoá thành công.')
                this.napGianDo()
              }
            }).catch(e => {
              if(e.response.data.message_detail){
                this.$toast.error(e.response.data.message_detail)
              }else {
                this.$toast.error(e.response.data.message)
              }
            })
          }catch (e){
            console.log(e)
          }finally {
            this.loading(false)
          }
        }
      })
  },
    grid_onRowDoubleClicked: function (rowIndex, dataItem) {

      this.objUpdate ={
        KIEU_ : "INSERT"
      }
      this.objUpdate.LOAI_DT = this.tenLoaiDoiTuongSelected
      this.objUpdate.LOAI_DT_ID = this.dataSelected.loaiDoiTuong
      this.objUpdate.DOITUONG_ID = this.doiTuongIdSelected
      this.objUpdate.TUYENTD_ID = this.dataSelected.tuyen
      this.objUpdate.TEN_DT = this.tenDoiTuongSelected
      this.objUpdate.TEN_TUYENTD = this.tenTuyenSelected

        this.objUpdate.KINHDO = 0
        this.objUpdate.VIDO = 0
      if(this.position){
        this.objUpdate.VITRI_X = parseInt(this.position.x)
        this.objUpdate.VITRI_Y = parseInt(this.position.y)
      }

      this.showTaoDoiTuong()

    }, async grid1_selectedRowChanged(dataItem) {
      if (dataItem) {
        this.doiTuongIdSelected = dataItem.DOITUONG_ID
        this.tenDoiTuongSelected = dataItem.TEN_DT
      }
    }, async napGianDo() {
      this.obj1 = new Object()
      this.obj2 = new Object()
      if (this.dataSelected.tuyen) {
        await this.getGianDo(this.dataSelected.tuyen)
        // refresh and fitToPage
        this.gianDoRefresh = !this.gianDoRefresh
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
    async getGianDo(tuyen_td_id) {
      try {
        this.loading(true)
        let nodes = []
        let connectors = []
        let dsDoiTuong = []
        let dsLienKet = []
        //get doi tuong
        await this.$root.context.post(
          "/web-suco/mang_bangrong/napgiando", {tuyen_td_id: tuyen_td_id, loai_doituong_id: 0}
        ).then(res => {
          dsDoiTuong = res.data
          this.dsDoiTuongGianDo = res.data
          this.dsDoiTuongAfterChange = res.data.map(item => { let newItem = { };  Object.keys(item).forEach(key => newItem[key.toLowerCase()] = item[key]); return newItem; } )

          //create node
          dsDoiTuong.forEach(item =>{
            let node = this.taoNode("DT_"+ item.DOITUONG_ID + "", item.TEN_HT,"SPLITTER")
            node.offsetX = item.VITRI_X
            node.offsetY = item.VITRI_Y
            node.constraints =5240814

            nodes.push(node)
          })
        }).catch(e => {
          throw e
        })
        // get lien ket
        await this.$root.context.post(
          "/web-suco/mang_bangrong/napgiando", {tuyen_td_id: tuyen_td_id, loai_doituong_id: 1}
        ).then(res => {
          dsLienKet = res.data
          this.dsLienKet = res.data
          //create connector
          let dsNodePushed = []
          if (dsLienKet.length > 0) {
            dsLienKet.forEach(itemLK => {
              let thongtinNodeN = dsDoiTuong.filter(item => item.DOITUONG_ID === itemLK.DOITUONG_N_ID)[0]
              if (thongtinNodeN){
                let nodeN = this.taoNode("DT_" + thongtinNodeN.DOITUONG_ID + "", thongtinNodeN.TEN_HT ? thongtinNodeN.TEN_HT : thongtinNodeN.TEN_DT, "SPLITTER")
                let thongtinNodeD = dsDoiTuong.filter(item => item.DOITUONG_ID === itemLK.DOITUONG_D_ID)[0]
                if (thongtinNodeD){
                  let nodeD = this.taoNode("DT_" + thongtinNodeD.DOITUONG_ID + "", thongtinNodeD.TEN_HT ? thongtinNodeD.TEN_HT : thongtinNodeD.TEN_DT, "SPLITTER")
                  //"1-2-3-1"  số 2 chọn kiểu default
                  let connector = this.taoKetNoi("1-1-3-1", itemLK.LIENKET_ID + "", nodeN, nodeD, itemLK.LOAILK_ID, itemLK.TEN_HT ? itemLK.TEN_HT : itemLK.TEN_LK, null)
                  // lưu thông tin connector
                  connector.THONGTIN = itemLK
                  // XÉT TỌA ĐỘ ĐIỂM UỐN ĐỐI VỚI LOẠI LIÊN KẾT CONG
                  const DUONG_CONG_1G = 0, DUONG_CONG_10G = 1
                  if (itemLK.LOAILK_ID === DUONG_CONG_1G || itemLK.LOAILK_ID === DUONG_CONG_10G){
                    connector.segments = [{
                      type: "Bezier",
                      point1:{x:itemLK.DIEMUON_X1, y:itemLK.DIEMUON_Y1},
                      point2:{x:itemLK.DIEMUON_X2, y:itemLK.DIEMUON_Y2}
                    }]
                  }
                  connectors.push(connector)
                }
              }
            })
          }
        }).catch(e => {
          throw e
        })
        // this.dsNodes = nodes
        // this.dsConnectors = connectors
        this.$refs.diagram.napGianDo(nodes, connectors)
      } catch (e) {

        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    // create diagram node
    taoNode: function (nodeID, text, stringStyle) {
      text =
        "<div style='position: absolute; top: -37px; left: -10px;'><div style='padding: 7px 10px; border: 0px solid #e0e0e0; background: none; display: inline-table;width: 70px;text-align: center; border-radius: 5px;'>" +
        text +
        "</div></div>";
      if (!this.isShowTitle) text = "<div></div>"
      let ports = [];
      ports.push(DiagramLib.taoPort(nodeID + "_port_1", null));
      ports.push(DiagramLib.taoPort(nodeID + "_port_2", null));
      let annotations = [];
      annotations.push({template: text, style: {textWrapping: "NoWrap"}});
      let node = DiagramLib.taoNodeTheoStyle(
        stringStyle,
        nodeID,
        ports,
        annotations
      );
      if (!this.isShowDoiTuong) node.visible = false

      // IMAGE_KEY
      node.IMAGE_KEY = stringStyle
      return node;
    },
    //create diagram connector
    taoKetNoi: function (stringStyle, id, nodeSource, nodeTarget, type, textN, textD) {
      let sourceID = nodeSource.id;
      let sourcePortID = nodeSource.ports[1].id;
      let targetID = nodeTarget.id;
      let targetPortID = nodeTarget.ports[0].id;
      //let style = { strokeWidth: 4, strokeColor: "#0176FF", fill: "#0176FF", strokeDashArray: "20" };
      let annotations = [
        {
          height: 25,
          width: 60,
          content: textN,
          margin: {top: 20
          },
          offset: (0.5, 0.5),
          style: {
            textWrapping: "NoWrap",
            // fill: "#FFFFFF",
            strokeWidth: 1,
            strokeColor: "#E0E0E0",
          },
        }
        // ,
        // {
        //   height: 25,
        //   width: 60,
        //   content: textD,
        //   margin: {top: 20},
        //   offset: (0.62, 0.7),
        //   style: {
        //     textWrapping: "NoWrap",
        //     fill: "#FFFFFF",
        //     strokeWidth: 1,
        //     strokeColor: "#E0E0E0",
        //   },
        // },
      ];
      let ketNoi = DiagramLib.taoKetNoiTheoStyle(
        stringStyle,
        id,
        sourceID,
        targetID,
        sourcePortID,
        targetPortID,
        annotations
      );
      //color
      ketNoi.style.strokeColor = "Blue"

      ketNoi.targetDecorator = {
        shape: "None",
      };
      // setting constrains

      //setting type connector
      switch (type){
        case 0: // Đường cong 10G
          ketNoi.type = "Bezier"
          ketNoi.style.strokeWidth = 5
          break
        case 1: // Đường cong 1G
          ketNoi.type = "Bezier"
          ketNoi.style.strokeWidth = 3
          break
        case 2: // Đường thẳng 1G
          ketNoi.type = "Straight"
          ketNoi.style.strokeWidth = 3
          break
        case 3: // Đường 3 đoạn 1G
          ketNoi.type = "Orthogonal"
          ketNoi.style.strokeWidth = 3
          break
        case 4: // Đường nhiều đoạn 1G
          ketNoi.type = "Orthogonal"
          ketNoi.style.strokeWidth = 3
          break
        case 5: // Vòng lõi
          break
        case 6: // Vòng ngoài
          break
        case 7: // Đường gấp khúc
          ketNoi.type = "Orthogonal"
          ketNoi.style.strokeWidth = 3
          break
        case 8: // Đường thẳng
          ketNoi.type = "Straight"
          ketNoi.style.strokeWidth = 3
          break
        default:// Đường thẳng
          ketNoi.type = "Straight"
          ketNoi.style.strokeWidth = 3
      }
      if (!this.isShowLienKet) ketNoi.visible = false
      return ketNoi;
    },

    // Tiện ích diagram
    btnShowHiddenDoiTuong(){
      this.isShowDoiTuong = !this.isShowDoiTuong
      this.napGianDo()
    },
    btnShowHiddenLienKet(){
      this.isShowLienKet = !this.isShowLienKet
      this.napGianDo()
    },
    btnShowHiddenTitle(){
      this.isShowTitle = !this.isShowTitle
      this.napGianDo()
    },
    btnShowHiddenLayer(){
      this.isShowLayer = !this.isShowLayer
    },
    btnShowHiddenGridLine(){
      this.isShowGridLine = !this.isShowGridLine
    },
    btnXemTruocKhiIn(){
      this.$bvModal.show("popupXemTruocKhiIn")
    },
    // updateRange(e){
    //   this.range = e
    // },
    getCurrentZoom(currentZoom){
      this.range = currentZoom
    },
    // getHorizontalOffset(e){
    //   this.horizontalOffset = parseInt(e, 10)
    // },
    // getVerticalOffset(e){
    //   this.verticalOffset = parseInt(e, 10)
    // },
    // getIsHoverRange(e){
    //   this.isHoverRange = e
    // },
    getCurrentOffset(e){
      this.initOffset = e
    },
    zoomIn(){
      this.$refs.diagram.zoomIn()
    },
    zoomOut(){
      this.$refs.diagram.zoomOut()
    },
  }

  ,
  watch:{
    'dataSelected.loaiTuyen':async function (val) {
    await this.getTuyen(val)
    await this.getLoaiDoiTuong(val)
    },
    // 'range': async function(val){
    // console.log("Range",this.range)
    //         this.zoomFactor = val
    //   //this.panzoom.zoom(val)
    // },
    dsTuyen (val) {

    },
    // isHoverRange: function (value){
    //   console.log(value)
    // },
    'dataSelected.tuyen':async function (val) {

      await this.getLoaiDoiTuong(this.dataSelected.loaiTuyen)
      let data = {
        P_LOAI_DOITUONG_ID: this.dataSelected.loaiDoiTuong,
        P_TUYENTD_ID: val
      }

      await this.getDsDoiTuong(data)
      await this.napGianDo()
  },'dataSelected.loaiDoiTuong' : async function(val){
      this.dataSelected.loaiDoiTuong = val
      let data = {
        P_LOAI_DOITUONG_ID: this.dataSelected.loaiDoiTuong,
        P_TUYENTD_ID: this.dataSelected.tuyen
      }
      await this.getDsDoiTuong(data)
    }
},
  async mounted() {

    // this.panzoom = Panzoom(document.getElementById('gian-do'), {
    //   maxScale: 4
    // })
    await this.getLoaiTuyen()
    await this.getLoaiLienKet()
  }
}
</script>
