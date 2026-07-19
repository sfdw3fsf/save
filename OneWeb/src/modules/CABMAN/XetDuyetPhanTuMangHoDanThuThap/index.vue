<template src="./index.html"></template>
<script>
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import { L10n } from '@syncfusion/ej2-base'
import QuanLyKetCuoiDoiThu from './QuanLyKetCuoiDoiThu'
import QuanLyKetCuoi from './QuanLyKetCuoi'
import ModalXuatDuLieu from '../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu'
import GhiChuDuyet from './GhiChuDuyet'
import ModalChonToaDo from './ChonToaDo'
import QuanLyHoDan from './QuanLyHoDan'
import BssRequiredMarker from '@/components/BssRequiredMarker'

import { saveAs } from 'file-saver';
L10n.load({
  'vi-VN': {
    'dropdowns': {
      'noRecordsTemplate': "Không có dữ liệu",
      'actionFailureTemplate': ""
    }
  }
})
export default {
  name: "index",
  provide: {
    multiselect: [CheckBoxSelection]
  },
  components:{QuanLyKetCuoi, QuanLyKetCuoiDoiThu, ModalXuatDuLieu, GhiChuDuyet, ModalChonToaDo, QuanLyHoDan, BssRequiredMarker},
  data:function (){
    return {
      dsChuQuan:[],
      dsDoiTuong:[],
      dsTrangThai:[],
      dsDonVi:[],
      checkedDonVi:[],
      dsNhanVien: [],
      dsKetCuoiHoDan: [],
      dsIn:[],
      // dsTimKiem:[], // ds hien thi
      dsGrouped:[], //ds groud by date

      cboChuQuan:[],
      cboDoiTuong:null,
      cboTrangThai:[],
      cboNhanVien:null,
      kyHieuFilterInput: '',
      ketCuoiObject: new Object, // ketcuoi dang chon cho frm QuanLyKetCuoi, QuanLyKetCuoiDoiThu
      hoDanObject: new Object,    // hodan dang chon cho frm QuanLyHoDan

      isShowChuQuan:true,
      isShowFilter:false,
      fieldsDonVi:'',
      dsHidden:[],  //ds collapse(true/false)

      ketCuoiSelected:{}, // ketcuoi đang chọn cho frm ToaDo

      checkedKC:[],
      checkAll: false,
      dsThuocTinh:[],// ds trường để xuất file

      isDuyet: false, // true: Duyet, false: Khong Dat
      // Phan trang
      totalRows: 0,
      dsPage:[],
      pSize:100,
      pNo:1,

      gridTitle:"Danh sách kết cuối thu thập",
    }
  },
  async created() {
    try {
      this.loading(true)
      await this.getDsDoiTuong()
      await this.getDsTrangThai()
      await this.getDsChuQuan()
      // await this.getDsDonViTheoNV()
      // let donViTree = this.createTreeDonVi(this.dsDonVi)
      // this.fieldsDonVi = { dataSource: donViTree, id: 'DONVI_ID', text: 'TEN_DV', parentID: 'DONVI_CHA_ID', hasChildren: 'hasChild' }
    }catch (e){
      console.log(e)
    }finally {
      this.loading(false)
    }
  },
  computed: {
    getPosition () {
      return {
        lat: parseFloat(this.ketCuoiSelected.VIDO),
        lng: parseFloat(this.ketCuoiSelected.KINHDO)
      }
    },
    getDiachi () {
      return this.ketCuoiSelected.DIACHI
    },
    getTenKC(){ // get TEN_KC or TEN_KH
      return this.cboDoiTuong !== 2 ? this.ketCuoiSelected.TEN_KC : this.ketCuoiSelected.TEN_KH
    },
    getHoDan(){
      return this.hoDanObject
    }
  },
  methods:{
    btnCollapse(index){
      if (this.dsHidden[index] === true){
        this.dsHidden[index] = !this.dsHidden[index]
        $( '#'+index).show()
        $( '#title'+index).removeClass()
        $( '#title'+index).addClass("icon fa fa-angle-down")
      }else{
        this.dsHidden[index] = !this.dsHidden[index]
        $( '#'+index).hide()
        $( '#title'+index).removeClass()
        $( '#title'+index).addClass("icon fa fa-angle-up")
      }

    },
    async btnHienThi() {
      //reset checkKC
      this.checkedKC = []
      let object = new Object()
      let isVNPT = false
      let chuquans = this.cboChuQuan
      if (this.cboChuQuan.includes(1)) {
        isVNPT = true
        chuquans = this.cboChuQuan.filter(item => item !== 1)
      } else isVNPT = false
      if (this.cboTrangThai.length !== 0) {
        object.dsTtDuyet = this.cboTrangThai.toString()
      } else {
        this.$root.toastError("Chưa chọn trạng thái")
        return
      }
      if ((this.cboChuQuan.length === 0 && !isVNPT) && this.cboDoiTuong !== "Hộ dân cư") {
        this.$root.toastError("Chưa chọn chủ quản")
        return
      }
      if (this.checkedDonVi.length === 0) {
        this.$root.toastError("Chưa chọn đơn vị")
        return
      }
      if (this.checkedDonVi.length >= 200) {
        this.$root.toastError("Vui lòng chọn số đơn vị <= 200")
        return
      }
      object.isVnpt = isVNPT ? 1 : 0
      object.dsChuQuan = chuquans.toString()
      object.dsDonvi = this.checkedDonVi.toString()
      object.psize = this.pSize
      object.pNo = this.pNo
      object.nhanvienId = this.cboNhanVien === null || this.cboNhanVien.toString() === "-1" ? null : this.cboNhanVien // -1 : Tất cả,
      object.tenKc = this.kyHieuFilterInput !== "" && this.kyHieuFilterInput !== null ? this.kyHieuFilterInput.toUpperCase().toString().trim() : null
      try {
        this.loading(true)
        switch (this.cboDoiTuong) {
          case 0: //"Hạ tầng mạng"
            object.doituong = 0
            break
          case 1: //"Kết cuối đối thủ cập nhật lại"
            object.doituong = 1
            break
          case 2: //"Hộ dân cư"
            object.doituong = 2
        }
        await this.$root.context.post("/web-cabman/phantu_mang/tracuu_ketcuoi_hodan", object)
          .then(async (res) => {
            if (res.error === "200" && res.error_code === "BSS-00000000") {
              if (res.data.DS_KC.length > 0) this.totalRows = res.data.DS_KC[0].TOTAL_ROWS
              else this.totalRows = 0
              // bổ sung 1 số thuộc tính phục vụ load ảnh, kiểm tra đối tượng, next img
              this.dsKetCuoiHoDan = res.data.DS_KC
              for (let i = 0; i < this.dsKetCuoiHoDan.length; i++) {
                let images = await this.getThongTinImages(this.dsKetCuoiHoDan[i].MOTA);
                this.dsKetCuoiHoDan[i] =
                  {
                    ...this.dsKetCuoiHoDan[i],
                    LOAI_DT: object.doituong,
                    IMG: images,
                    CURRENT_IMG: images.length > 0 ? 1 : 0,
                  }
              }
              this.dsGrouped = this.createGroupByDate(this.dsKetCuoiHoDan)
              //create ds status id collapse
              this.dsHidden = this.dsGrouped.map(item => false)
              if (res.data.DS_NV.length > 0) {
                // let sets = new Set(res.data.map(item => item.TEN_NV_NHAP.split(" (")[0]))
                // this.dsNhanVien = Array.from(sets).map((item) => ({NV_VALUE:item, NV_TEXT:item}))
                this.dsNhanVien = res.data.DS_NV
                this.dsNhanVien.unshift({NHANVIEN_ID: -1, TEN_NV: "--Tất cả--"})
                this.isShowFilter = true
                if (this.dsNhanVien.length > 0) this.cboNhanVien = this.cboNhanVien === null ? this.dsNhanVien[0].NHANVIEN_ID : this.cboNhanVien
                // this.filterByKyHieu()
              } else {
                this.isShowFilter = false
                // this.dsTimKiem = []
                this.dsNhanVien = []
                this.cboNhanVien = null
              }
            }
          }).catch(e => {
            let msg = e.response.data.message_detail
            if (msg && msg.includes("maximum number of expressions in a list is 1000"))
              this.$root.toastError("Số lượng đơn vị tìm kiếm vượt quá 1000");
            else this.$root.toastError(msg)
          })
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }


    },
    // filter by ten_kc/ ten_kh
    filterByKyHieu(){
        // let value = this.kyHieuFilterInput.toUpperCase().toString().trim()
        // this.dsTimKiem = this.cboDoiTuong !== 2 ? this.dsKetCuoiHoDan.filter(item => item.KYHIEU.toUpperCase().includes(value)) : this.dsKetCuoiHoDan.filter(item => item.TEN_KH.toUpperCase().includes(value))
        // this.dsTimKiem = this.createGroupByDate(this.dsTimKiem)
        this.pNo = 1
        this.chonTrang(this.pNo)
    },
    showQLKetCuoiDoiThu(item){
      this.ketCuoiObject = item
      this.$bvModal.show('popupQuanLyKetCuoiDoiThu')
    },
    showQLKetCuoi(item){
      this.ketCuoiObject = item
      this.$bvModal.show('popupQuanLyKetCuoi')
    },
    showQuanLyHoDan(item){
      this.hoDanObject = item
      this.$bvModal.show("popupQuanLyHoDan")
    },
    showChonToaDo(item){
      //reset kết cuối đang chọn
      this.ketCuoiSelected = {}
      if (item.KINHDO === 0 && item.VIDO === 0){
        this.$root.toastError("Kinh độ: 0, Vĩ độ : 0 không thể mở popup")
        return
      }
      this.ketCuoiSelected = item
      this.$bvModal.show('ModalChonToaDo')
    },
    XacNhanToaDo (position) {
      // this.dataSelected.VIDO = position.lat
      // this.dataSelected.KINHDO = position.lng
    },
    btnXoaKCDoiThu(item){
      this.$bvModal.msgBoxConfirm("Bạn có chắc muốn xóa?",
        {
          title: "Xác nhận hành động",
          centered: true,
          size: "sm",
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
        }).then((value) => {
        if (value){
          if (item.LOAI_DT === 0 || item.LOAI_DT === 1){
            if (item.TENCHUQUAN.toLowerCase().includes("vnpt")){
              this.$root.toastError("Không thể xóa kết cuối VNPT!")
              return
            }
            // xoa ket cuoi
            this.$root.context.post("/web-cabman/phantu_mang/xoaketcuoi", {doituong:1,ketcuoi_id:item.KETCUOI_ID})
            .then(res =>{
              if (res.error === "200" && res.error_code === "BSS-00000000"){
                //load ds
                this.btnHienThi()
                this.$root.toastSuccess("Xóa kết cuối thành công")
              }
            })
            .catch(e => this.$root.toastError("Có lỗi xảy ra khi xóa kết cuối"))
          }else if (item.LOAI_DT === 2){
            // xoa ket cuoi
            this.$root.context.post("/web-cabman/phantu_mang/xoaketcuoi", {doituong:2,ketcuoi_id:item.ID})
              .then(res =>{
                if (res.error === "200" && res.error_code === "BSS-00000000"){
                  this.btnHienThi()
                  this.$root.toastSuccess("Xóa kết cuối thành công")
                }
              })
              .catch(e => this.$root.toastError("Có lỗi xảy ra khi xóa kết cuối"))
          }
        }
        else{
          return
        }
      })
    },
    btnDuyet(){
      if (this.checkedKC.length === 0){
        this.$root.toastError("Hãy tick chọn các đối tượng trước")
        return
      }
      this.$bvModal.msgBoxConfirm("Bạn có chắc muốn thực hiện?",
        {
          title: "Xác nhận hành động Duyệt",
          centered: true,
          size: "sm",
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
        }).then((value) => {
        if (value){
          this.isDuyet = true
          this.$bvModal.show('popupGhiChuDuyet')
        }
        else{
          return
        }
      })
    },
    btnKhongDat(){
      if (this.checkedKC.length === 0){
        this.$root.toastError("Hãy tick chọn các đối tượng trước")
        return
      }
      this.$bvModal.msgBoxConfirm("Bạn có chắc muốn thực hiện?",
        {
          title: "Xác nhận hành động Không Đạt",
          centered: true,
          size: "sm",
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
        }).then((value) => {
        if (value){
          this.isDuyet = false
          this.$bvModal.show('popupGhiChuDuyet')
        }
        else{
          return
        }
      })
    },
    async btnXuatFile(){
      this.dsIn = []
      this.dsThuocTinh = []

      let object = new Object()
      let isVNPT = false
      let chuquans  = this.cboChuQuan
      if (this.cboChuQuan.includes(1)){
        isVNPT = true
        chuquans = this.cboChuQuan.filter(item => item !== 1)
      }else isVNPT = false
      if (this.cboTrangThai.length !== 0){
        object.dsTtDuyet = this.cboTrangThai.toString()
      }else {
        this.$root.toastError("Chưa chọn trạng thái")
        return
      }
      if ((this.cboChuQuan.length === 0 && !isVNPT)  && this.cboDoiTuong !== "Hộ dân cư" ){
        this.$root.toastError("Chưa chọn chủ quản")
        return
      }
      if (this.checkedDonVi.length === 0){
        this.$root.toastError("Chưa chọn đơn vị")
        return
      }
      object.isVnpt = isVNPT ? 1: 0
      object.dsChuQuan = chuquans.toString()
      object.dsDonvi = this.checkedDonVi.toString()
      object.nhanvienId = this.cboNhanVien === null || this.cboNhanVien.toString() === "-1" ?  null : this.cboNhanVien // -1 : Tất cả,
      object.tenKc = this.kyHieuFilterInput !== "" && this.kyHieuFilterInput !== null ? this.kyHieuFilterInput.toUpperCase().toString().trim() : null
      switch (this.cboDoiTuong){
        case 0: //"Hạ tầng mạng"
          object.doituong = 0
          break
        case 1: //"Kết cuối đối thủ cập nhật lại"
          object.doituong = 1
          break
        case 2: //"Hộ dân cư"
          object.doituong = 2
      }
      try {
        this.loading(true)
        try {
          const baseUrl = process.env.API;
          await this.axios({
            url: `${baseUrl}/web-cabman/phantu_mang/excelExport`,
            data: object,
            method: "POST",
            responseType: "blob",
          }).then((response) => {
            console.log(response)
            const url = URL.createObjectURL(new Blob([response.data], {
              type: 'application/vnd.ms-excel'
            }))
            const link = document.createElement('a')
            link.href = url
            link.setAttribute('download', "danhsach.xlsx")
            document.body.appendChild(link)
            link.click()
          })
        }catch (e){}
          //window.open(url) // Mostly the same, I was just experimenting with different approaches, tried link.click, iframe and other solutions
          // })
          // await this.$root.context.post("/web-cabman/phantu_mang/in_ketcuoi_hodan", object)
          // await this.$root.context.post("/web-cabman/phantu_mang/excelExport",object)
          // .then(res => {
          //   // if (res.error === "200" && res.error_code === "BSS-00000000" ){
          //   //   this.dsIn = res.data.DS_KC
          //   // }
          //
          //   let file =  new File(res, 'file.xlsx', {headers: {'Content-Type': 'application/vnd.ms-excel' }})
          //   var fileURL = window.URL.createObjectURL(file);
          //   var fileLink = document.createElement('a');
          //
          //   fileLink.href = file;
          //   fileLink.setAttribute('download', 'file.xlsx');
          //   document.body.appendChild(fileLink);
          //
          //   fileLink.click();
          //   // let blob = new Blob([res]), { type: 'application/vnd.ms-excel' }),
          //   // let disposition = res.headers("content-disposition");
          //   // let filename = disposition.match(/filename="(\w*.\w*)"/)[1];
          //   // const blob = new Blob([res])
          //   // const url = window.URL.createObjectURL(blob);
          //   // const link = document.createElement('a');
          //   // link.href = url;
          //   // link.setAttribute('download', filename);
          //   // // link.setAttribute('target', '_blank');
          //   // document.body.appendChild(link);
          //   // link.click();
          //
          // }).catch(e =>{
          //   console.log(e.message)
          //     throw e
          //   })
          // if (this.dsIn.length === 0){
          //   this.$root.toastError("Không có dữ liệu để xuất")
          //   return
          // }
          // for (let key in this.dsIn[0]){
          //   //không lấy các trường tự thêm
          //   if (key !== 'LOAI_DT' && key !== 'IMG' && key !=='CURRENT_IMG' && key !== 'TOTAL_ROWS' && key !== 'TEN_NV'){
          //     this.dsThuocTinh.push(key)
          //   }
          // }
          // this.$bvModal.show('modal-xuat-dulieu')

        } catch (e) {
          this.$root.toastError(e.message)
        } finally {
          this.loading(false)
        }
    },
    closeFormXuatDuLieu(){
      this.dsIn = []
    },
    async getDsChuQuan(){
      await this.$root.context.get(
        "/web-cabman/phantu_mang/ds_chuquan"
      ).then(res =>{
        this.dsChuQuan = res.data
        if (res.data.length > 0) {
          this.cboChuQuan = res.data.map(item => item.CHUQUAN_ID)
        }
      }).catch(e=> {throw e})
    },
    async getDsDoiTuong(){
      await this.$root.context.get(
        "/web-cabman/phantu_mang/ds_doituong"
      ).then(res =>{
        this.dsDoiTuong = res.data
        if (res.data.length > 0) this.cboDoiTuong = 0
      }).catch(e=> {throw e})
    },
    async getDsTrangThai(){
      await this.$root.context.get(
        "/web-cabman/phantu_mang/ds_trangthai"
      ).then(res =>{
        if (res.data.length > 0){
          this.dsTrangThai = res.data.map((item,index) =>({ TRANGTHAI_VALUE:index, TRANGTHAI_TEXT:item}))
          this.cboTrangThai = this.dsTrangThai.filter(item => item.TRANGTHAI_TEXT === "Chờ duyệt").map(item => item.TRANGTHAI_VALUE)
        }else this.dsTrangThai = []
      }).catch(e=> {throw e})
    },
    async getDsDonViTheoNV(){
      await this.$root.context.get(
        "/web-cabman/phantu_mang/lay_caydv_theo_nv"
      ).then(res =>{
        this.dsDonVi = res.data
      }).catch(e=> {throw e})
    },
    async getDsDonViTheoDVCha(){
      await this.$root.context.get(
        "/web-cabman/phantu_mang/lay_caydv_theo_dv"
      ).then(res =>{
        this.dsDonVi = res.data.dsdonvi
      }).catch(e=> {
        throw e
      })
    },
    createTreeDonVi(dsDonVi) {
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
        //check item la parent node
        if (donviChaArray.includes(item['DONVI_ID'])) {
          //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
          if (!donviArray.includes(item['DONVI_CHA_ID'])) {
            delete item.DONVI_CHA_ID
          }
          tree.push({...item, hasChild: true, expanded: item.LVL < 2})
        } else {
          if (item['DONVI_CHA_ID'] != null && donviArray.includes(item['DONVI_CHA_ID'])) tree.push(item)
          else {
            if (!donviArray.includes(item['DONVI_CHA_ID'])) {
              delete item.DONVI_CHA_ID
            }
            tree.push({...item, hasChild: true, expanded: item.LVL < 2}) // DONVI_CHA_ID = null, -> item is parent node
          }
        }
      })
      return tree
    },
    createGroupByDate(data){
      let tree = []
      let nhanvienArr = new Set(data.map(item => item.TEN_NV_NHAP))
      nhanvienArr.forEach(nhanvien =>{
        let list = data.filter(item => item.TEN_NV_NHAP === nhanvien)
        tree.push({TEN_NV_NHAP: nhanvien, size:list.length, list:list})
      })
      return tree
    },
    nodeChecked(treeObj){
      var treeObj = document.getElementById('treeview').ej2_instances[0];
      this.checkedDonVi = treeObj.checkedNodes
      // if (treeObj.action === "check"){
      //   let CheckedObjs = treeObj.data.filter(item => item['isChecked'] === "true")
      //   CheckedObjs.forEach(item => this.checkedDonVi.push(item.id))
      // }else if (treeObj.action === "uncheck"){
      //   let CheckedObjs = treeObj.data.filter(item => item['isChecked'] === "false")
      //   CheckedObjs.forEach(item => {
      //     // console.log(this.checkedDonVi)
      //     let index = this.checkedDonVi.findIndex((element) => element === item.id)
      //     if (index != -1) //index = -1 khong co element trong arr
      //       this.checkedDonVi.splice(this.checkedDonVi.findIndex((element) => element === item.id),1)
      //     // delete this.checkedDonVi[this.checkedDonVi.findIndex((element) => element === item.id)]
      //   })
      //   // console.log("end:",this.checkedDonVi)
      // }
    },
    async getThongTinImages(mota){
      let images = []
      try {
        this.loading(true)
        if (window.DOMParser)
        {
          let parser = new DOMParser();
          let xmlDoc = parser.parseFromString(mota.trim(), "text/xml")
          let imgs = xmlDoc.getElementsByTagName("img")
          if (imgs.length > 0){
            // for img
            for (let i = 0; i < imgs.length;i++){
              await this.$root.context.post("/web-cabman/storage/getPresignedUrl",{fileSource:imgs[i].attributes["url"].value})
                .then(res =>{
                  if (res.error=== "200" && res.error_code === "BSS-00000000"){
                    images.push({id: imgs[i].attributes["id"].value, title: imgs[i].attributes["title"].value, url: res.data.result})
                  }
                }).catch(e => this.$root.toastError("Lỗi tải ảnh"))
            }
          }
        }else { // Internet Explorer
          let xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
          xmlDoc.async = false;
          xmlDoc.loadXML(mota.trim());
          let imgs = xmlDoc.getElementsByTagName("img")
          if (imgs.length > 0){
            // for img
            for (let i = 0; i < imgs.length;i++){
              await this.$root.context.post("/web-cabman/storage/getPresignedUrl",{fileSource:imgs[i].attributes["url"].value})
                .then(res =>{
                  if (res.error=== "200" && res.error_code === "BSS-00000000"){
                    images.push({id: imgs[i].attributes["id"].value, title: imgs[i].attributes["title"].value, url: res.data.result})
                  }
                }).catch(e => this.$root.toastError("Lỗi tải ảnh"))
            }
          }
        }
      }catch (e){}
      finally {
        this.loading(false)
      }
      return images
    },
    //next,previous image
    previousImage(data){
      this.dsGrouped.forEach(item=>{
        if (item.TEN_NV_NHAP === data.TEN_NV_NHAP){
          item.list.forEach(i =>{
            if (i.ID === data.ID && i.CURRENT_IMG > 1){
              i.CURRENT_IMG--
            }
          })
        }
      })
    },
    nextImage(data){
      this.dsGrouped.forEach(item=>{
        if (item.TEN_NV_NHAP === data.TEN_NV_NHAP){
          item.list.forEach(i =>{
            if (i.ID === data.ID && i.CURRENT_IMG < i.IMG.length){
              i.CURRENT_IMG++
            }
          })
        }
      })
    },
  // event disable chuquan vnpt when KetCuoiDoiThu
    onOpen(args){
      let liCollections = args.popup.element.querySelectorAll('.e-list-item');
      liCollections.forEach(item =>{
        if (item.innerText === "VNPT"){
          item.classList.add('e-disabled');
          item.classList.add('e-overlay');
          if (item.querySelector('.e-check') !== null) {
            item.querySelector('.e-check').classList.remove('e-check');
          }
        }
      })
    },
    // event disable chuquan vnpt when KetCuoiDoiThu
    onSelect(args){
      if (args.itemData.CHUQUAN_ID === 1){
        if (args.item.querySelector('.e-check'))
          args.item.querySelector('.e-check').classList.remove('e-check')
        args.item.classList.add('e-disabled');
        args.item.classList.add('e-overlay');
      }
    },
    //calculate number of page
    calcPageCount(total){
      let totalPage = total / this.pSize;
      if (total % this.pSize !== 0){
        totalPage++
      }
      return totalPage
    },
    chonTrang(i){
      //thêm css active cho trang thứ i
      let liCollection = $('#paging').children()
      // liCollection[0].classList.add('active')
      for (let j =0 ; j < liCollection.length; j++){
        //clear class active
        liCollection[j].classList.remove('active')
        if (liCollection[j].innerText === i.toString()) {
          //add class active cho trang thứ i
          liCollection[j].classList.add('active')
        }
      }
      // trang thứ i
      this.pNo = i
      this.btnHienThi()
    },
    //clear data khi thay đổi đối tượng
    clearDS(){
      this.checkedDonVi = []
      // this.dsTimKiem = []
      this.dsGrouped = []
      this.dsKetCuoiHoDan = []
      this.totalRows = 0
      this.dsNhanVien = []
      this.cboNhanVien = null
      this.pNo = 1
      this.kyHieuFilterInput = ''
    }
  },
  watch:{
    "cboDoiTuong": async function(value){
      try {
        this.loading(true)
        let donViTree = []
        switch (value){
          case 0: //"Hạ tầng mạng"
            this.gridTitle = "Danh sách kết cuối thu thập"
            this.isShowChuQuan = true
            await this.getDsDonViTheoNV()
            donViTree = this.createTreeDonVi(this.dsDonVi);
            this.fieldsDonVi = { dataSource: donViTree, id: 'DONVI_ID', text: 'TEN_DV', parentID: 'DONVI_CHA_ID', hasChildren: 'hasChild' }
            //reset checkDonvi
            this.clearDS()
            break
          case 1: //"Kết cuối đối thủ cập nhật lại"
            this.gridTitle = "Danh sách kết cuối đối thủ cập nhật lại"
            this.isShowChuQuan = true
            this.cboChuQuan = this.cboChuQuan.filter(item => item !== 1)
            await this.getDsDonViTheoNV()
            donViTree = this.createTreeDonVi(this.dsDonVi);
            this.fieldsDonVi = { dataSource: donViTree, id: 'DONVI_ID', text: 'TEN_DV', parentID: 'DONVI_CHA_ID', hasChildren: 'hasChild' }
            //reset checkDonvi
            this.clearDS()

            break
          case 2: //"Hộ dân cư"
            this.gridTitle = "Danh sách hộ dân thu thập"
            this.isShowChuQuan = false
            await this.getDsDonViTheoDVCha()
            donViTree = this.createTreeDonVi(this.dsDonVi);
            this.fieldsDonVi = { dataSource: donViTree, id: 'DONVI_ID', text: 'TEN_DV', parentID: 'DONVI_CHA_ID', hasChildren: 'hasChild' }
            //reset checkDonvi
            this.clearDS()

        }
      } catch (e){

      } finally {
        this.loading(false)
      }

    },
    "cboChuQuan": function(value){
      if (value.includes(1) && this.cboDoiTuong ===1){
        this.cboChuQuan = value.filter(item => item !== 1)
      }
    },
    "cboNhanVien": function (value){
      if (value === null) return
      this.pNo = 1
      this.chonTrang(this.pNo)
    },
    "checkAll": function (value){
      if (value){
        this.checkedKC = []
        this.dsGrouped.forEach(item =>{
          this.checkedKC = [...this.checkedKC,...item.list]
        })
      }else {
        this.checkedKC = []
      }
    },
    "totalRows": function (value){
      //calculate ds page
      this.dsPage = []
      for (let i = 1; i <= this.calcPageCount(value);i++){
        this.dsPage.push(i)
      }
    }
  },
  async mounted()  {
    try {
      this.$store.dispatch('setHeader', {
        title: 'DUYỆT PHẦN TỬ MẠNG/HỘ DÂN THU THẬP'
      })
    } catch (err) {
      console.log(err)
    }
  }
}
</script>

<style>
.xetduyet-phantu-mang-hodan-thuthap .e-checkboxfilter .e-checkbox-wrapper .e-frame.e-check, .e-css.e-checkbox-wrapper .e-frame.e-check {
  background-color: white !important;
  padding-top: 0px !important;
}
.bss-1803-list-kc .box-content .image {
  max-width: 100%;
  width: auto;
  margin-right: 10px;
  height: 140px;
}
.bss-1803-list-kc .box-content .image img {
  max-width: 100%;
  width: auto;
  margin-right: 10px;
  height: 140px;
}
.image img:hover {
  transform: scale(3.0);
}
.thumbnail:hover {
  position:relative;
  top:-25px;
  left:150px;
  width:500px;
  height:auto;
  display:block;
  z-index:999;
}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.tc-pagination .dropdown .input-number1 {
  height: 25px;
  font-size: 12px;
  border: 1px solid #C3C8D2;
  border-radius: 4px;
  padding: 0px 5px;
  padding-right: 5px;
  display: inline-block;
  vertical-align: middle;
  line-height: 25px;
  width: 75px;
}
</style>
