<template src="./index.html"></template>
<script>
export default {
  name: "PhanViTriKetCuoiChoNhanVien",
  props:{
    id: String,
    obj: Object
  },
  data: function (){
    return {
      isShown: false,

      // ktparent:false,
      // ketcuoi_id:null,
      // donvi_id:null,
      // cap_goc_id:0,
      // nhanvien_id: 0,
      dsql_capgoc:{},

      LOAI_DV:{
        DONVIQL_LD:3,
        TRAM_VT:5,
        TRAM_PORT:39,
        TRAM_VETINH:25,
      },

      dsDonvi:[],
      dsToQL:[],
      dsNguoiQL:[],
      dsTongDai:[],
      dsCapGoc:[],
      dsKetCuoi:[],
      dsKetCuoiChuaPhan:[],
      dsKetCuoiDaPhan:[],

      selected_ChuaPhan:[],
      selected_DaPhan:[],

      cboDonVi:null,
      cboTramTC:null,
      cboTramTB:null,
      cboCapGoc:null,
      cboNguoiQL:null,
      cboKetCuoi:null,

      totalItemsDaPhan: 0,
      totalItemsChuaPhan: 0,
    }
  },
  methods:{
    onHiddenModal() {
      this.Clear()
    },
    async onShownModal() {
      try {
        this.Clear()
        this.loading(true)
        // this.ktparent = this.obj.ktparent
        // this.donvi_id = this.obj.donvi_id
        // this.ketcuoi_id = this.obj.ketcuoi_id
        // this.cap_goc_id = this.obj.cap_goc_id
        // this.nhanvien_id = this.obj.nhanvien_id
        await this.NapDonViQL()
        if (this.obj.ktparent) {
          this.cboTramTC = this.obj.donvi_id
          await this.$root.context.get(`web-cabman/phan-vitri-ketcuoi-nv/lay-donvi-cap?cap_id=${this.obj.capgoc_id}`)
          .then(res =>{
            if (res.error === "200"){
              if (res.data.length === 0)
                this.cboTramTB = 0
              else this.cboTramTB = res.data[0].DONVI_ID
            }
          }).catch(e =>{throw e})
          this.cboCapGoc = this.obj.capgoc_id
        }
      } catch (e) {

      }finally {
        this.loading(false)
      }
    },
    async NapDonViQL(){
      await this.$root.context.get("web-cabman/phan-vitri-ketcuoi-nv/ds-donvi-theo-loaidv", {donvi_id:0, loaidv_id:this.LOAI_DV.DONVIQL_LD})
      .then(res =>{
        if (res.error === "200"){
            this.dsDonvi = res.data
        }
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi lấy danh sách đơn vị"))
      if (!await this.AutoIndexDonvi(this.obj.donvi_id))
        if (this.dsDonvi.length > 0){
          this.cboDonVi = this.dsDonvi[0].DONVI_ID
        }
    },
    async AutoIndexDonvi(tramtc_id){
      let dt = await this.$root.context.get("web-cabman/phan-vitri-ketcuoi-nv/lay-donvi-theo-tramtc", {tramtc_id:tramtc_id, loaidv_id:this.LOAI_DV.DONVIQL_LD})
      if (dt.data.length > 0){
        this.cboDonVi = dt.data[0].DONVI_ID
        this.cboTramTC = tramtc_id
        return true
      }else return false
    },
    async NapDanhSachKetCuoi_NhanVien(ketcuoi_id, cap_id, pageIndex, pageSize) {
      try {
        this.loading(true)
        if (!ketcuoi_id && !cap_id) return
        else {
          await this.$root.context.post(
            `/web-cabman/phan-vitri-ketcuoi-nv/lay-ds-ketcuoi-da-phan-nv-paging?ketcuoi_id=${ketcuoi_id}&cap_id=${cap_id}`,
            {
              pageIndex: pageIndex,
              pageSize: pageSize,
            }
          ).then(res =>{
            if (res.error === "200")
              this.dsKetCuoiDaPhan = res.data.pageItems
          }).catch(e =>{
            this.$root.toastError("Có lỗi xảy ra khi lấy danh sách kết cuối đã phân nhân viên")
            throw e
          })
        }
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    async NapDanhSachKetCuoi_ChuaPhanNV(ketcuoi_id, cap_id, pageIndex, pageSize) {
      try {
        this.loading(true)
        if (!ketcuoi_id && !cap_id) return
        else {
          await this.$root.context.post(
            `/web-cabman/phan-vitri-ketcuoi-nv/lay-ds-ketcuoi-chua-phan-nv-paging?ketcuoi_id=${ketcuoi_id}&cap_id=${cap_id}`,
            {
              pageIndex: pageIndex,
              pageSize: pageSize,
            }
          ).then(res => {
            if (res.error === "200")
              this.dsKetCuoiChuaPhan = res.data.pageItems
          }).catch(e => {
            this.$root.toastError("Có lỗi xảy ra khi lấy danh sách kết cuối đã phân nhân viên")
            throw e
          })
        }
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    updateDsChuaPhanCount: async function (ketcuoi_id, cap_id) {
      if (!ketcuoi_id && !cap_id) return
      var rs = await this.$root.context.post(
        `/web-cabman/phan-vitri-ketcuoi-nv/lay-ds-ketcuoi-chua-phan-nv-paging?ketcuoi_id=${ketcuoi_id}&cap_id=${cap_id}`,
        {
          pageIndex: 0,
          pageSize: 1,
        }
      );
      if (rs.data.allItems != -9999) {
        if (this.totalItemsChuaPhan == rs.data.allItems) this.$refs.gridChuaPhan.reloadCurrentPage()
        this.totalItemsChuaPhan = rs.data.allItems;
      } else {
        let total = 0
        if (this.totalItemsChuaPhan == total) this.$refs.gridChuaPhan.reloadCurrentPage()
        this.totalItemsChuaPhan = total;
        this.dsKetCuoiChuaPhan = []
      }
    },
    updateDsDaPhanCount: async function (ketcuoi_id, cap_id) {
      if (!ketcuoi_id && !cap_id) return
      await this.$root.context.post(
        `/web-cabman/phan-vitri-ketcuoi-nv/lay-ds-ketcuoi-da-phan-nv-paging?ketcuoi_id=${ketcuoi_id}&cap_id=${cap_id}`,
        {
          pageIndex: 0,
          pageSize: 1,
        }
      ).then(rs => {
        if (rs.data.allItems != -9999) { // ko co record
          if (this.totalItemsDaPhan == rs.data.allItems) this.$refs.gridDaPhan.reloadCurrentPage()
          this.totalItemsDaPhan = rs.data.allItems;
        } else {
          let total = 0
          if (this.totalItemsDaPhan == total) this.$refs.gridDaPhan.reloadCurrentPage()
          this.totalItemsDaPhan = total;
          this.dsKetCuoiDaPhan = []
        }
      }).catch(e => {
      })

    },
    async gridDaPhan_PageChanged(args){
      await this.NapDanhSachKetCuoi_NhanVien(this.cboKetCuoi, this.cboCapGoc, args.pageIndex, args.pageSize)
    },
    async gridChuaPhan_PageChanged(args){
      await this.NapDanhSachKetCuoi_ChuaPhanNV(this.cboKetCuoi, this.cboCapGoc, args.pageIndex, args.pageSize)
    },
    rowSelected_ChuaPhan(items){
      this.selected_ChuaPhan = items
    },
    rowSelected_DaPhan(items){
      this.selected_DaPhan = items
    },
    async ganKCChoNV() {
      if (!this.cboNguoiQL) {
        this.$toast.toastError("Chưa chọn nhân viên quản lý")
        return
      }
      if (!this.cboKetCuoi) {
        this.$toast.toastError("Bạn chưa chọn kết cuối")
        return
      }
      try {
        if (this.selected_ChuaPhan.length === 0) {
          this.$root.toastError("Bạn chưa tick chọn kết cuối!")
          return
        }
        this.loading(true)
        let ds = []
        for (let item of this.selected_ChuaPhan) {
          try {
            await this.$root.context.post(`web-cabman/phan-vitri-ketcuoi-nv/capnhat-donvi-ketcuoi?ketcuoi_id=${item.KETCUOI_ID}&donvi_id=${this.cboTramTC}`)
              .catch(e => {throw e})
            let row = {}
            row.KETCUOI_ID = item.KETCUOI_ID
            row.CAP_ID = this.cboCapGoc
            row.NHANVIEN_ID = this.cboNguoiQL
            row.VITRI = item.VITRI
            ds.push(row)
          }catch (e){
            console.log(e)
            break
          }
        }
        await this.$root.context.post("web-cabman/phan-vitri-ketcuoi-nv/themmoi-ketcuoi-nv",ds).catch(e => { throw e})
        this.$root.toastSuccess("Cập nhật thành công!")
      } catch (e) {
        this.$root.toastError("Có lỗi xảy ra khi phân vị trí kết cuối cho nhân viên")
        console.log(e)
      } finally {
        this.loading(false)
      }
      await this.updateDsDaPhanCount(this.cboKetCuoi, this.cboCapGoc)
      await this.updateDsChuaPhanCount(this.cboKetCuoi, this.cboCapGoc)
    },
    async ganAllKCChoNV() {
      if (!this.cboNguoiQL) {
        this.$root.toastError("Chưa chọn nhân viên quản lý")
        return
      }
      if (!this.cboKetCuoi) {
        this.$root.toastError("Bạn chưa chọn kết cuối")
        return
      }
      if (this.totalItemsChuaPhan > 2000) {
        this.$root.toastError(`Số lượng kết cuối quá lớn:${this.totalItemsChuaPhan}. Giới hạn <= 2000`)
        return
      }
      try {
        this.loading(true)
        await this.$root.context.post(`web-cabman/phan-vitri-ketcuoi-nv/gan-tatca-kc-cho-nv?ketcuoi_id=${this.cboKetCuoi}&donvi_id=${this.cboTramTC}&cap_id=${this.cboCapGoc}&nhanvien_id=${this.cboNguoiQL}`)
          .then(res => {
            if (res.data && res.data.result)
              this.$root.toastSuccess("Cập nhật thành công!")
            else this.$root.toastError("Lỗi khi cập nhật đơn vị kết cuối")
          }).catch(e => {
            console.log(e)
            this.$root.toastError("Lỗi khi gán kết cuối");
          })
        // let ds = []
        // for (let item of this.dsKetCuoiChuaPhan) {
        //   try {
        //     await this.$root.context.post(`web-cabman/phan-vitri-ketcuoi-nv/capnhat-donvi-ketcuoi?ketcuoi_id=${item.KETCUOI_ID}&donvi_id=${this.cboTramTC}`)
        //       .catch(e => {
        //         throw e
        //       })
        //     let row = {}
        //     row.KETCUOI_ID = item.KETCUOI_ID
        //     row.CAP_ID = this.cboCapGoc
        //     row.NHANVIEN_ID = this.cboNguoiQL
        //     row.VITRI = item.VITRI
        //     ds.push(row)
        //   } catch (e) {
        //     this.$root.toastError("Lỗi khi cập nhật đơn vị kết cuối:"+item.KETCUOI_ID)
        //     console.log(e)
        //     break
        //   }
        // }
        // await this.$root.context.post("web-cabman/phan-vitri-ketcuoi-nv/themmoi-ketcuoi-nv",ds).catch(e => { throw e})

      } catch (e) {
        console.log(e)
      } finally {
        await this.updateDsDaPhanCount(this.cboKetCuoi, this.cboCapGoc)
        await this.updateDsChuaPhanCount(this.cboKetCuoi, this.cboCapGoc)
        this.loading(false)
      }
    },
    async goKC() {
      if (this.selected_DaPhan.length === 0) {
        this.$root.toastError("Bạn chưa tick chọn kết cuối nào!")
        return
      }
      try {
        this.loading(true)
        for (let item of this.selected_DaPhan) {
          try {
            await this.$root.context.post(`web-cabman/phan-vitri-ketcuoi-nv/xoa-ketcuoi-nv?ketcuoi_id=${item.KETCUOI_ID}&vitri=${item.VITRI}`)
              .catch(e => {
                throw e
              })
          } catch (e) {
            console.log(e)
            break
          }
        }
        this.$root.toastSuccess("Cập nhật thành công!")
      }catch (e){}
      finally {
        this.loading(false)
      }
      await this.updateDsDaPhanCount(this.cboKetCuoi, this.cboCapGoc)
      await this.updateDsChuaPhanCount(this.cboKetCuoi, this.cboCapGoc)
    },
    async goAllKC() {
      if (this.totalItemsDaPhan > 2000) {
        this.$root.toastError(`Số lượng kết cuối quá lớn:${this.totalItemsChuaPhan}. Giới hạn <= 2000`)
      }
      try {
        this.loading(true)
        await this.$root.context.post(`web-cabman/phan-vitri-ketcuoi-nv/go-tatca-kc-khoi-nv?ketcuoi_id=${this.cboKetCuoi}&cap_id=${this.cboCapGoc}`)
          .then(res => {
            if (res.data && res.data.result)
              this.$root.toastSuccess("Cập nhật thành công!")
            else this.$root.toastError("Cập nhật đơn vị kết cuối thất bại")
          }).catch(e => {
            console.log(e)
            this.$root.toastError("Lỗi khi gỡ kết cuối");

          })
        // for (let item of this.dsKetCuoiDaPhan) {
        //   try {
        //     await this.$root.context.post(`web-cabman/phan-vitri-ketcuoi-nv/xoa-ketcuoi-nv?ketcuoi_id=${item.KETCUOI_ID}&vitri=${item.VITRI}`)
        //       .catch(e => {
        //         throw e
        //       })
        //   } catch (e) {
        //     console.log(e)
        //     break
        //   }
        // }
      } catch (e) {
      } finally {
        await this.updateDsDaPhanCount(this.cboKetCuoi, this.cboCapGoc)
        await this.updateDsChuaPhanCount(this.cboKetCuoi, this.cboCapGoc)
        this.loading(false)
      }
    },
    Clear(){
      this.totalItemsChuaPhan = null
      this.totalItemsDaPhan = null
      // this.ktparent = false
      // this.ketcuoi_id = null
      // this.donvi_id = null
      // this.cap_goc_id = 0
      // this.nhanvien_id = 0
      this.dsql_capgoc = {}

      this.dsDonvi = []
      this.dsToQL = []
      this.dsNguoiQL = []
      this.dsTongDai = []
      this.dsCapGoc = []
      this.dsKetCuoi = []
      this.dsKetCuoiChuaPhan = []
      this.dsKetCuoiDaPhan = []

      this.cboDonVi = null
      this.cboTramTC = null
      this.cboTramTB = null
      this.cboCapGoc = null
      this.cboNguoiQL = null
      this.cboKetCuoi = null
    },
    async cboTramTC_EditValueChanged() {
      this.dsCapGoc = []
      this.dsKetCuoi = []
      // TONG DAI
      await this.$root.context.get("web-cabman/phan-vitri-ketcuoi-nv/lay-ds-donvi-loaidv-tramtb", {
        donvi_id: this.cboTramTC,
        loaidv_id: this.LOAI_DV.TRAM_PORT.toString() + "," + this.LOAI_DV.TRAM_VETINH.toString()
      }).then(async res => {
        if (res.error === "200")
          if (res.data.length > 0) {
            this.dsTongDai = res.data
            let filter_temp = this.dsTongDai.filter(item => item.DONVI_ID == this.cboTramTB)
            if (filter_temp.length == 0)
              this.cboTramTB = this.dsTongDai[0].DONVI_ID
            await this.cboTramTB_EditValueChanged()
          } else {
            this.dsTongDai = []
            this.cboTramTB = null
            this.cboCapGoc = null
            this.cboKetCuoi = null
            this.dsKetCuoiDaPhan = []
            this.dsKetCuoiChuaPhan = []
          }
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi lấy danh sách tổng đài"))
      // NGUOI QUAN LY
      await this.$root.context.get("web-cabman/phan-vitri-ketcuoi-nv/lay-ds-nv-ql", {
        donvi_id: this.cboTramTC,
      }).then(res => {
        if (res.error === "200")
          if (res.data.length > 0) {
            this.dsNguoiQL = res.data
            let filter_temp = this.dsNguoiQL.filter(item => item.NHANVIEN_ID == this.cboNguoiQL)
            if (filter_temp.length == 0)
              this.cboNguoiQL = res.data[0].NHANVIEN_ID
          }
      })
    },
    async cboTramTB_EditValueChanged() {
      this.dsKetCuoiDaPhan = []
      this.dsKetCuoiChuaPhan = []
      this.dsCapGoc = []
      this.dsKetCuoi = []
      if (this.cboTramTB === null) return
      await this.$root.context.get("web-cabman/phan-vitri-ketcuoi-nv/lay-ds-cap-td", {tramtb_id: this.cboTramTB})
        .then(async res => {
          if (res.error === "200")
            if (res.data.length > 0) {
              this.dsCapGoc = res.data
              let filter_temp = this.dsCapGoc.filter(item => item.CAP_ID == this.cboCapGoc)
              if (filter_temp.length == 0)
                this.cboCapGoc = res.data[0].CAP_ID
              await this.cboCapGoc_EditValueChanged()
            } else {
              this.dsCapGoc = []
              this.cboCapGoc = null
            }
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi lấy danh sách cáp gốc"))
    },
    async cboCapGoc_EditValueChanged() {
      this.dsKetCuoiDaPhan = []
      this.dsKetCuoiChuaPhan = []
      if (this.cboCapGoc === null && this.dsCapGoc.length === 0)
        return
      await this.$root.context.get("web-cabman/phan-vitri-ketcuoi-nv/lay-ds-ketcuoi", {capgoc_id: this.cboCapGoc})
        .then(async res => {
          if (res.error === "200")
            if (res.data.length > 0) {
              this.dsKetCuoi = res.data
              let filter_temp = this.dsKetCuoi.filter(item => item.KETCUOI_ID == this.cboKetCuoi)
              if (filter_temp.length == 0)
                this.cboKetCuoi = /*this.obj.ketcuoi_id ? this.obj.ketcuoi_id :*/ res.data[0].KETCUOI_ID
              if (this.cboKetCuoi == -1) {
                // chọn tất cả
                await this.updateDsChuaPhanCount(this.cboKetCuoi, this.cboCapGoc)
                await this.updateDsDaPhanCount(this.cboKetCuoi, this.cboCapGoc)
              }
            } else {
              this.dsKetCuoi = []
              this.cboKetCuoi = null
            }
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi lấy danh sách kết cuối"))
    }
  },
  watch:{
    dsKetCuoiDaPhan: function (val) {
      if (val.length == 0) this.totalItemsDaPhan = 0
    },
    dsKetCuoiChuaPhan: function (val) {
      if (val.length == 0) this.totalItemsChuaPhan = 0
    },
    cboDonVi:async function (val) {
      this.dsToQL = []
      this.dsTongDai = []
      this.dsKetCuoiDaPhan = []
      this.dsKetCuoiChuaPhan = []
      this.dsCapGoc = []
      this.dsKetCuoi = []
      await this.$root.context.get("web-cabman/phan-vitri-ketcuoi-nv/lay-ds-donvicon-loaidv-nhanvien", {
        donvi_id: val,
        nhanvien_id:this.$auth.getNhanVienID(),
        loaidv_id: this.LOAI_DV.TRAM_VT
      }).then(async res => {
        if (res.error === "200")
          if (res.data.length > 0) {
            this.dsToQL = res.data
            // this.cboTramTC = this.$auth.getDonViID()
            let filter_temp = this.dsToQL.filter(item => item.DONVI_ID == this.cboTramTC)
            if (filter_temp.length == 0)
              this.cboTramTC = this.dsToQL[0].DONVI_ID
            await this.cboTramTC_EditValueChanged()
          } else {
            this.dsToQL = []
            this.cboTramTC = null
          }
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi lấy danh sách tổ quản lý"))
    },
    // cboTramTC: async function (val) {
    //   this.dsCapGoc = []
    //   this.dsKetCuoi = []
    //   // TONG DAI
    //   await this.$root.context.get("web-cabman/phan-vitri-ketcuoi-nv/lay-ds-donvi-loaidv-tramtb", {
    //     donvi_id: val,
    //     loaidv_id: this.LOAI_DV.TRAM_PORT.toString()+ ","+ this.LOAI_DV.TRAM_VETINH.toString()
    //   }).then(res =>{
    //     if (res.error === "200")
    //       if (res.data.length > 0){
    //         this.dsTongDai = res.data
    //         let filter_temp = this.dsTongDai.filter(item => item.DONVI_ID == this.cboTramTB)
    //         if (filter_temp.length == 0)
    //           this.cboTramTB = this.dsTongDai[0].DONVI_ID
    //       } else this.dsTongDai = []
    //   }).catch(e => this.$root.toastError("Có lỗi xảy ra khi lấy danh sách tổng đài"))
    //   // NGUOI QUAN LY
    //   await this.$root.context.get("web-cabman/phan-vitri-ketcuoi-nv/lay-ds-nv-ql", {
    //     donvi_id: val,
    //   }).then(res =>{
    //     if (res.error === "200")
    //       if (res.data.length > 0){
    //         this.dsNguoiQL = res.data
    //         let filter_temp = this.dsNguoiQL.filter(item => item.NHANVIEN_ID == this.cboNguoiQL)
    //         console.log("filter_temp1:",filter_temp)
    //         if (filter_temp.length == 0)
    //           this.cboNguoiQL = res.data[0].NHANVIEN_ID
    //       }
    //   })
    // },
    // cboTramTB: async function (val) {
    //   this.dsKetCuoiDaPhan = []
    //   this.dsKetCuoiChuaPhan = []
    //   this.dsCapGoc = []
    //   this.dsKetCuoi = []
    //   if (val === null) return
    //   await this.$root.context.get("web-cabman/phan-vitri-ketcuoi-nv/lay-ds-cap-td", {tramtb_id: val})
    //   .then(res =>{
    //     if (res.error === "200")
    //       if (res.data.length > 0){
    //         this.dsCapGoc = res.data
    //         let filter_temp = this.dsCapGoc.filter(item => item.CAP_ID == this.cboCapGoc)
    //         if (filter_temp.length == 0)
    //           this.cboCapGoc = res.data[0].CAP_ID
    //       }else this.dsCapGoc = []
    //   }).catch(e => this.$root.toastError("Có lỗi xảy ra khi lấy danh sách cáp gốc"))
    // },
    cboNguoiQL: function (val) {
      // await this.NapDanhSachKetCuoi_NhanVien()
    },
    // cboCapGoc: async function (val) {
    //   this.dsKetCuoiDaPhan = []
    //   this.dsKetCuoiChuaPhan = []
    //   if (val === null && this.dsCapGoc.length === 0)
    //     return
    //   await this.$root.context.get("web-cabman/phan-vitri-ketcuoi-nv/lay-ds-ketcuoi", {capgoc_id: val})
    //   .then(res =>{
    //     if (res.error === "200")
    //       if (res.data.length > 0){
    //         this.dsKetCuoi = res.data
    //         let filter_temp = this.dsKetCuoi.filter(item => item.KETCUOI_ID == this.cboKetCuoi)
    //         if (filter_temp.length == 0)
    //           this.cboKetCuoi = /*this.obj.ketcuoi_id ? this.obj.ketcuoi_id :*/ res.data[0].KETCUOI_ID
    //       }else this.dsKetCuoi = []
    //   }).catch(e => this.$root.toastError("Có lỗi xảy ra khi lấy danh sách kết cuối"))
    // },
    cboKetCuoi: async function (val) {
      await this.updateDsChuaPhanCount(val, this.cboCapGoc)
      await this.updateDsDaPhanCount(val, this.cboCapGoc)
    }
  }
}
</script>

<style>
.modal-phan-vitri-ketcuoi-cho-nhanvien .select2-container .select2-selection--single .select2-selection__rendered {
  white-space: initial;
  height: 28px;
}
</style>
