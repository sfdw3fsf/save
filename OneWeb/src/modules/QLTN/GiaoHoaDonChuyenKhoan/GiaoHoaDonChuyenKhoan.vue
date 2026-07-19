<template src="./GiaoHoaDonChuyenKhoan.html"></template>
<style scoped src="./GiaoHoaDonChuyenKhoan.scss"></style>

<script>
import Vue from 'vue'
import moment from 'moment'
import KyCuoc from '@/components/KyCuoc'
import PhieuChuaGiao from './components/PhieuChuaGiao'
import PhieuDaGiao from './components/PhieuDaGiao'
import CommonsAPI from '../api/Commons'
import TuyenThuAPI from '../api/TuyenThuAPI'
import { TreeViewPlugin } from "@syncfusion/ej2-vue-navigations"
import { Query } from '@syncfusion/ej2-data'
import EventBus from '@/utils/eventBus'
import PopupLuotGiao from '../GiaoPhieuTon/components/PopupLuotGiao.vue'
import { currency } from '@/filters/currency'


Vue.use(TreeViewPlugin)


export default Vue.extend({
  components: {
    appKyCuoc : KyCuoc,
    appPhieuDaGiao : PhieuDaGiao,
    appPhieuChuaGiao: PhieuChuaGiao,
    PopupLuotGiao,
    },
  name: 'GiaoHoaDonChuyenKhoan',
  async created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)

    this.loading(true)

    await Promise.all([
      this.loadNVThuCuoc(),
      this.initDotGiaoData(),
      //this.getTienDieuChinh()
    ])

    this.loading(false)
  },
  mounted () {
  },
  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler);
  },
  computed : {
    f_nhanvienthu_enabled () {
      return this.params.p_chk_nhanvienthu === '1'
    },
    f_tongphieu_enabled () {
      return this.params.p_chk_tongphieu === '1'
    },
    f_tongtien_enabled () {
      return this.params.p_chk_tongtien === '1'
    },
    f_nhanvienthu (){
      return this.params.p_chk_nhanvienthu
    },
    p_ngaygiao: {
      get() {
        return this.params.p_ngaygiao
      },
      set(value) {
        return this.params.p_ngaygiao = value
      }
    },
  },
  data () {

    return {

      countSelectedRows: function(){
        return  { template : Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" :value="data.Custom" :disabled="true" style="font-weight:bold" />`,
            data () { return { data: {data: {}}}; }
          })
        }
      },
      sumSelectedRows: function() {
        return  { template : Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold" />`,
            data () { return { data: {data: {}}}; }
          })
        }
      },

      tienTraTemplate : function() {
        return  { template : Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
            data () { return { data: {data: {}}}; }
          })
        }
      },
      countPhieu: function(){
        return  { template : Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
            data () { return { data: {data: {}}}; }
          })
        }
      },

      countSelectedRowsData :{
        1: 0,
        2: 0,
      },
      sumRows :{
        1: {
          selected: 0,
          total: 0
        },
        2: {
          selected: 0,
          total: 0
        }
      },
      tabPageInfo:{
        1: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        2: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
      },

      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        }
      },
      dataNV: [],
      dataDotGiao: [],
      dataTuyenThu: [],
      params : {
        p_chk_nhanvienthu: '1',
        p_nhanvienthu_options: [],
        p_nhanvienthu: null,
        nvtc: {
          id: "",
          text: "",
        },
        p_chk_tongphieu: '0',
        p_tongphieu: 0,
        p_tongphieu_den: 0,
        p_chk_tongtien: '1',
        p_tongtien: 0,
        p_ngaygiao: new Date(),
        p_kycuoc : null,
        p_tien_dc: -1,
      },
      hideTable: true,
      treeDotGiaoFields: { dataSource: [], id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild' },
      /*
      treeDotGiaoFields: {
        dataSource:  this.remoteData,
        id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild',
        //child: {dataSource: this.remoteData, id: 'id', text: 'name', parentID: 'pid'}
        },
      */
      treeTuyenThuFields: { dataSource:  [], id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild' },
      resultDotGiao : [],
      resultTuyenThu : [],
      resultDaGiao : [],
      resultChuaGiao : [],
      selectDotGiaoTab: 1,
      selectPhieuGiaoTab: 1,
      pageinfoChuaGiao : {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      pageinfoDaGiao : {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      selectedTotalDaGiao: 0,
      totalDaGiao: 0,
      selectedTotalChuaGiao: 0,
      totalChuaGiao: 0,
      selectedCountChuaGiao: 0,
      selectedCountDaGiao: 0,
      keyChuaGiao: 0,
      keyDaGiao: 0,
      keyNVTC: 0,
      default_kycuoc: moment(new Date()).subtract(1,'months').startOf('month').format('YYYYMMDD'),
      luotGiao_Input: {
          kyCuoc: 0,
          kieu: 0,
          NVTCId: 0,
          NVTC: {}
      },
    }
  },
  methods : {
    countSelectedRowsAgg() {
      return currency(this.countSelectedRowsData[this.selectPhieuGiaoTab]) + '/' + currency(this.tabPageInfo[this.selectPhieuGiaoTab].totalElement)
    },
    sumTienAgg()
    {
      return currency(this.sumRows[this.selectPhieuGiaoTab].selected) + '/' + currency(this.sumRows[this.selectPhieuGiaoTab].total)
    },
    tienTraChuaGiaoAggregate() {
      return currency(this.selectedTotalDaGiao) + '/' + currency(this.totalDaGiao);
    },
    tienTraDaGiaoAggregate() {
      return currency(this.selectedTotalChuaGiao) + '/' + currency(this.totalChuaGiao);
    },
    countPhieuChuaGiaoAggregate() {
      return currency(this.selectedCountChuaGiao) + '/' + currency(this.pageinfoChuaGiao.totalElement);
    },
    countPhieuDaGiaoAggregate() {
      return currency(this.selectedCountDaGiao) + '/' + currency(this.pageinfoDaGiao.totalElement);
    },
    changeDotGiaoTab: function (tabId) {
      this.selectDotGiaoTab = tabId;
    },
    changePhieuGiaoTab: function (tabId) {
      this.selectPhieuGiaoTab = tabId;
    },
    clearResult : function (){
      this.resultDotGiao = []
      this.resultTuyenThu = []
      this.resultChuaGiao = []
      this.resultDaGiao = []

      this.countSelectedRowsData[1] = 0
      this.countSelectedRowsData[2] = 0

      this.sumRows[1].total = 0
      this.sumRows[2].total = 0
      this.sumRows[1].selected = 0
      this.sumRows[2].selected = 0

      this.tabPageInfo[1].totalElement = 0
      this.tabPageInfo[2].totalElement = 0

      this.tabPageInfo[1].page = 0
      this.tabPageInfo[2].page = 0
    },
    async getTienDieuChinh()
    {
      let postData = {
        "tenTruong": "tien_dc",
        "schema": "admin",
        "tenBang": "nhanvien",
        "dieuKien": "nhanvien_id = " + this.params.nvtc.id
      }

      const res = await this.getMapID(postData)
      if(res.length > 0)
      {
        this.params.tien_dc = res[0].KETQUA
        this.params.p_tongtien = this.params.tien_dc
      }        
      else
        this.params.tien_dc = -1
      
    
    },
    async loadNVThuCuoc() {
      this.params.p_nhanvienthu_options = []
      this.params.p_nhanvienthu = null

      await TuyenThuAPI.getDanhSachNVTC(this.axios, {pNguoiDungId: this.$auth.getNguoiDungID(), pageNo: 0, pageSize: 100})
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.data !== undefined
              && response.data.data.data.length > 0)
          {
            this.params.p_nhanvienthu_options = response.data.data.data
            this.params.p_nhanvienthu = response.data.data.data[0].NHANVIEN_ID
            this.keyNVTC++
          }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
      })
    },
    // lấy full danh sách
    
    async loadTuyenThu() {
      let postData = {
        pNhanVienId : this.params.nvtc.id
      }

      let $this = this
      
      await TuyenThuAPI.getTuyenThuTheoNhanVien(this.axios, postData)
      .then(
        (response) => {
          // console.log(response)
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            // $this.treeDotGiaoFields.dataSource = $this.resultDotGiao
            
            let ds_tuyen = []

            ds_tuyen.push({
              id: 'NV-' + $this.params.nvtc.id,
              name: $this.params.nvtc.text,
              hasChild: true,
              expanded: true,
              pid: null
            })

            response.data.data.forEach( function (item) {
              ds_tuyen.push({ id : item.TUYENTHU_ID, name : item.TENTUYEN, pid : 'NV-' + $this.params.nvtc.id, hasChild: false })
            })


            $this.dataTuyenThu = [...ds_tuyen]
            
          }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {

        this.treeTuyenThuFields = {
          dataSource: $this.dataTuyenThu,
          id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
        }

      })
    },

    async loadDotGiao() {
      let postData = {
        pNhanVienId : this.params.nvtc.id
      }

      let $this = this
      
      await TuyenThuAPI.getDotGiaoTheoNhanVien(this.axios, postData)
      .then(
        (response) => {
          // console.log(response)
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            
            let ds_dot = []

            ds_dot.push({
              id: 'NV-' + $this.params.nvtc.id,
              name: $this.params.nvtc.text,
              hasChild: true,
              expanded: true,
              pid: null
            })

            response.data.data.forEach( function (item) {
              ds_dot.push({ id : item.DOTGIAO_ID, name : item.DOTGIAO, pid : 'NV-' +  $this.params.nvtc.id, hasChild: false})
            })


            this.dataDotGiao = [...ds_dot]
            
          }
        }
      )
      .catch(error => {
        console.log(error)
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {

        this.treeDotGiaoFields = {
          dataSource: this.dataDotGiao,
          id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
        }

      })
    },
    
    async initDotGiaoData()
    {
      let postData = {
        pNhanVienId : null,
      }
      
      await TuyenThuAPI.getDotGiaoTheoNhanVien(this.axios, postData)
        .then(
          (response) => {
            if(response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined && response.data.data.length > 0
            )
            {
              let ds_dot = []
              let ds_nv = []
              ds_nv.push({
                id : 'NV-0',
                name: 'Quản trị hệ thống',
                hasChild: true,
                expanded: true,
                pid: null
              })
              ds_dot.push({
                id : 'NV-0',
                name: 'Quản trị hệ thống',
                hasChild: true,
                expanded: true,
                pid: null
              })
              /*
              response.data.data.forEach( function (item) {
                //$this.resultDotGiao.push({ id : item.DOTGIAO_ID, name : item.DOTGIAO, pid : pid })
                let f = ds_nv.filter(x => x.id === 'NV-' + item.NHANVIEN_ID)
                if(f.length <= 0)
                {
                  ds_nv.push({
                    id : 'NV-' + item.NHANVIEN_ID,
                    name: item.TEN_NV,
                    hasChild: false,
                    pid: 'NV-0'
                  })

                }

              })  // end forEach
              */
              let  i = 0
              for(i = 0; i < response.data.data.length - 1; i++)
              {
                if(response.data.data[i].NHANVIEN_ID !== response.data.data[i+1].NHANVIEN_ID)
                {
                  ds_nv.push({
                    id : 'NV-' + response.data.data[i].NHANVIEN_ID,
                    name: response.data.data[i].TEN_NV,
                    hasChild: true,
                    pid: 'NV-0'
                  })

                  ds_dot.push({
                    id : 'NV-' + response.data.data[i].NHANVIEN_ID,
                    name: response.data.data[i].TEN_NV,
                    hasChild: true,
                    pid: 'NV-0'
                  })

                }

                ds_dot.push({
                  id : response.data.data[i].DOTGIAO_ID,
                  name: response.data.data[i].DOTGIAO,
                  hasChild: false,
                  pid: 'NV-' + response.data.data[i].NHANVIEN_ID
                })
              }
              ds_dot.push({
                id : response.data.data[i].DOTGIAO_ID,
                name: response.data.data[i].DOTGIAO,
                hasChild: false,
                pid: 'NV-' + response.data.data[i].NHANVIEN_ID
              })
              this.dataNV = [...ds_nv]
              this.dataDotGiao = [...ds_dot]
            }
          })
    },
    async initTuyenThuData()
    {
      let postData = {
        pNhanVienId : null
      }
      await TuyenThuAPI.getTuyenThuTheoNhanVien(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            
            let ds_tuyen = [...this.dataNV]

            response.data.data.forEach( function (item) {
              ds_tuyen.push({ id : item.TUYENTHU_ID, name : item.TENTUYEN, pid : 'NV-' + item.NHANVIEN_ID })
            })

            this.dataTuyenThu = [...ds_tuyen]                
          }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
      })
    },
    /*
    async loadDotGiao(id = null) {
      let postData = {
        pNhanVienId : id,
      }
      let $this  = this


      if(id === null)
      {
        if(this.dataDotGiao.length > 0)
        {
          $this.treeDotGiaoFields =
          {
            dataSource: this.dataDotGiao,
            id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
          }

          $this.treeTuyenThuFields = {
            dataSource: this.dataTuyenThu,
            id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
          }

          return

        }
        this.dataNV = []
        this.dataDotGiao = []
        await TuyenThuAPI.getDotGiaoTheoNhanVien(this.axios, postData)
        .then(
          (response) => {
            if(response.data.error_code === 'BSS-00000000')
            {
              if(response.data.data !== undefined && response.data.data.length > 0)
              {

                let ds_dotgiao = []
                let ds_nv = []
                ds_dotgiao.push({
                  id : 'NV-0',
                  name: 'Quản trị hệ thống',
                  hasChild: true,
                  pid: null
                })
                ds_nv.push({
                  id : 'NV-0',
                  name: 'Quản trị hệ thống',
                  hasChild: true,
                  pid: null
                })
                //this.dataDotGiao = [...response.data.data]
                response.data.data.forEach( function (item) {
                  //$this.resultDotGiao.push({ id : item.DOTGIAO_ID, name : item.DOTGIAO, pid : pid })
                  let f = ds_dotgiao.filter(x => x.id === 'NV-' + item.NHANVIEN_ID)
                  if(f.length <= 0)
                  {
                    ds_dotgiao.push({
                      id : 'NV-' + item.NHANVIEN_ID,
                      name: item.TEN_NV,
                      hasChild: true,
                      pid: 'NV-0'
                    })

                    ds_nv.push({
                      id : 'NV-' + item.NHANVIEN_ID,
                      name: item.TEN_NV,
                      hasChild: true,
                      pid: 'NV-0'
                    })

                  }

                    ds_dotgiao.push(
                      {
                        id : item.DOTGIAO_ID,
                        name: item.DOTGIAO,
                        pid: 'NV-' + item.NHANVIEN_ID
                      }
                    )


                })

                this.dataNV = [...ds_nv]
                this.dataDotGiao = [...ds_dotgiao]
                this.loadTuyenThu()
              }
            }
          }
        )
        .catch(error => {
          //console.log(error)
          //this.$toast.error('Đã phát sinh lỗi.')
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          //this.loading(false)
          $this.treeDotGiaoFields =
          {
            dataSource: this.dataDotGiao,
            id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
          }
        })
      } else
      {
        let key = 'NV-' + id
        let p = this.dataDotGiao.filter(x => x.id === key)
        let c = this.dataDotGiao.filter(x => x.pid === key)
        let ds = []
        if(p.length > 0)
        {
          p[0].pid = null
          p[0].expanded = true
          ds = [...p, ...c]
        }
        $this.treeDotGiaoFields =
        {
          dataSource: ds,
          id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
        }
      }



    },
    */
    async loadHoaDonChuyenKhoan (postData){
      try{
        let res = await TuyenThuAPI.getDanhSachHoaDonChuyenKhoan(this.axios, postData)
        return res.data
      }catch(err){
        console.log(err)
      }
    },
    validateData: function () {
      let error = []

      if(!moment(this.params.p_ngaygiao).isValid())
      {
        error.push('Ngày giao không hợp lệ')
      }

      if(this.params.p_chk_nhanvienthu === '1'
        && this.params.p_nhanvienthu === null) {
         error.push('Vui lòng chọn nhân viên thu')
        }

      if(this.params.p_chk_tongphieu === '1'
        && this.params.p_tongphieu_den < this.params.p_tongphieu) {
          error.push('Tổng phiếu từ phải lớn hơn hoặc bằng tổng phiếu đến')
        }
      if(error.length > 0)
      {
        this.$toast.error(error.join('\r\n'))
        return true
      }
      return false
    },
    searchGiaoHoaDonChuyenKhoan: function() {

      if(this.validateData())
        return false

      //console.log(this.$refs.treeDotGiao.getAllCheckedNodes())
      let arrtreeDotGiao = []
      this.$refs.treeDotGiao.getAllCheckedNodes().forEach((item) => {
        let k = parseInt(item)
        if(!isNaN(k))
          arrtreeDotGiao.push(k)
      })

      if(this.selectDotGiaoTab === 1 && arrtreeDotGiao.length <= 0)
      {
        this.$toast.error('Chưa chọn đợt giao')
        return
      }


      /*
      let arrtreeDotGiao = this.$refs.treeDotGiao.getAllCheckedNodes().map(function(item) {
          return parseInt(item, 10)
      })
      */

      //console.log(arrtreeDotGiao)

      let arrtreeTuyenThu = []

      this.$refs.treeTuyenThu.getAllCheckedNodes().forEach((item) => {
        let k = parseInt(item)
        if(!isNaN(k))
          arrtreeTuyenThu.push(k)
      })

      if(this.selectDotGiaoTab === 2 && arrtreeTuyenThu.length <= 0)
      {
        this.$toast.error('Chưa chọn tuyến thu')
        return
      }
/*
      let arrtreeTuyenThu = this.$refs.treeTuyenThu.getAllCheckedNodes().map(function(item) {
          return parseInt(item, 10)
      })
*/

      // console.log(arrtreeDotGiao)

      let postData = {
        ngayTT : moment(this.params.p_ngaygiao).format('DD/MM/YYYY'),
        nvtcId : this.params.p_chk_nhanvienthu === '1' ? this.params.p_nhanvienthu : null,
        dsDot : arrtreeDotGiao.join(','),
        dsTuyenThu : arrtreeTuyenThu.join(','),
        trangThaiHDId : this.selectPhieuGiaoTab,
        loaiBC : this.selectDotGiaoTab,
        kyCuoc : this.params.p_kycuoc
      }

      this.loading(true)

      if(this.selectPhieuGiaoTab === 1) // chưa giao
      {
        this.resultChuaGiao = []
        this.tabPageInfo[1].totalElement = 0
        this.tabPageInfo[1].page = 0
        this.countSelectedRowsData[1] = 0

        this.sumRows[1].total = 0
        this.sumRows[1].selected = 0
      } else
      {
        this.resultDaGiao = []
        this.tabPageInfo[2].totalElement = 0
        this.tabPageInfo[2].page = 0
        this.countSelectedRowsData[2] = 0

        this.sumRows[2].total = 0
        this.sumRows[2].selected = 0
      }

      this.loadHoaDonChuyenKhoan(postData).then(res => {
        //console.log(res)
        if(res.error_code === 'BSS-00000000'
            && res.data !== undefined
            && res.data.length > 0)
        {
          let f = false
          let tongtien = 0

          if(this.selectPhieuGiaoTab === 1) // chưa giao
          {
            let dt = []
            let dateStartOfMonth = moment(this.params.p_ngaygiao).subtract(1,'months').startOf('month').toDate()
            let dateNgayGiao = moment(this.params.p_ngaygiao)
            
            res.data.forEach((item, index) => {
              if(moment(item.NGAY_TT, 'DD/MM/YYYY').isBetween(dateStartOfMonth, dateNgayGiao))
              {
                dt.push(item)
              }


                /*
                if(this.params.p_chk_tongphieu === '1') // checked
                {
                  if(index > this.params.p_tongphieu)
                    f = true
                }

                

                if(!f && this.params.p_chk_tongtien === '1')
                {
                  if(this.params.p_tien_dc !== -1)
                  {
                    if(this.params.p_tongtien <= this.params.p_tien_dc)
                    {
                      tongtien += parseInt(item.TIENTRA)
                      if(tongtien > this.params.p_tongtien)
                      {
                        f = true
                        continue
                      }
                    } else
                    {
                      let tien_nud = this.params.p_tien_dc + this.params.p_tien_dc / 10
                      if (tien_nud > this.params.p_tongtien)
                        tien_nud = this.params.p_tongtien
                      
                      this.params.p_tongtien = tien_nud
                      tongtien += parseInt(item.TIENTRA)
                      if(tongtien > tien_nud)
                      {
                        f = true
                        continue
                      }
                    }
                  } 


                }
                */
              
            })
            //console.log(dt)
            
            this.resultChuaGiao = [...dt]

            this.tabPageInfo[1].totalElement = this.resultChuaGiao.length
            this.tabPageInfo[1].page = 0
            

            this.keyChuaGiao++
            //
            this.sumRows[1].total = this.resultChuaGiao.reduce((acc, curr) => {
              return acc = Number(curr.TIENTRA) + acc
            }, 0)
          }

          else
          {
            let dt = []
            let dateStartOfMonth = moment(this.params.p_ngaygiao).subtract(1,'months').startOf('month').toDate()
            let dateNgayGiao = moment(this.params.p_ngaygiao)
            
            res.data.forEach((item, index) => {
              if(moment(item.NGAY_TT, 'DD/MM/YYYY').isBetween(dateStartOfMonth, dateNgayGiao))
              {
                dt.push(item)
              }
            })
            this.resultDaGiao = [...dt]

            this.tabPageInfo[2].totalElement = this.resultDaGiao.length
            this.tabPageInfo[2].page = 0

            this.keyDaGiao++
            //
            this.sumRows[2].total = this.resultDaGiao.reduce((acc, curr) => {
              return acc = Number(curr.TIENTRA) + acc
            }, 0)

          }

        } else {
          this.$toast.error('Không tìm thấy dữ liệu thỏa mãn điều kiện. Vui lòng thử lại.')
        }
      })
      .catch(error => {
        console.log(error)
      })
      .finally(() => {
        this.loading(false)
      })
    },
    exportExcel: function () {

      if(this.selectPhieuGiaoTab === 1)
      {
        if(this.resultChuaGiao.length <= 0){
          this.$toast.error('Không tìm thấy dữ liệu để xuất ra excel. Vui lòng thử lại.')
          return
        } else {
          // tạm thời xuất data hiện tại
          //this.$refs.resultChuaGiao.exportExcel(this.resultChuaGiao)
          let excelExportProperties = {
            dataSource: this.resultChuaGiao,
            fileName: 'DanhSachChuaGiao.xlsx'
          };
          this.$refs.gridChuaGiao.excelExport(excelExportProperties)
        }
      } else {
         if(this.resultDaGiao.length <= 0){
          this.$toast.error('Không tìm thấy dữ liệu để xuất ra excel. Vui lòng thử lại.')
          return
        } else {
          // tạm thời xuất data hiện tại
          //this.$refs.resultDaGiao.exportExcel(this.resultDaGiao)
          let excelExportProperties = {
            dataSource: this.resultDaGiao,
            fileName: 'DanhSachDaGiao.xlsx'
          };
          this.$refs.gridDaGiao.excelExport(excelExportProperties)
        }
      }

    },
    doGiaoPhieu()
    {
      if(this.resultChuaGiao.length <= 0)
      {
        this.$toast.error('Không có phiếu để giao')
        return
      }

      let selectedRows = this.$refs.gridChuaGiao.getSelectedRecords()
      if(selectedRows.length <= 0)
      {
        this.$toast.error('Chưa chọn phiếu để giao')
        return
      }

      let postData = {
        "dsPhieuGiao": [],
        "giaoVet": 0,
        "kieuGiao": 1,
        "kyCuoc": this.params.p_kycuoc,
        "ngayGiao": moment(this.params.p_ngaygiao).format('DD/MM/yyyy'),
        "nguoiGiaoId": this.$auth.getNhanVienID(),
        "nhanVienTCId": this.params.nvtc.id,
        "soLuongHD": this.countSelectedRowsData[1],
        "tongTienHD": this.sumRows[1].selected
      }

      selectedRows.forEach((item) => {
        postData.dsPhieuGiao.push({
          "id": item.PHIEU_ID,
          "nhanVienId": item.NHANVIEN_ID,
          "thanhToanId": item.THANHTOAN_ID
        })
      })


      this.loading(true)

      TuyenThuAPI.giaophieuChuyenKhoanNVTC(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Giao phiếu thành công.')
            this.searchGiaoHoaDonChuyenKhoan()
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false)})


    },


    doHuyGiao()
    {
      if(this.resultDaGiao.length <= 0)
      {
        this.$toast.error('Không có phiếu để hủy giao')
        return
      }

      let selectedRows = this.$refs.gridDaGiao.getSelectedRecords()
      if(selectedRows.length <= 0)
      {
        this.$toast.error('Chưa chọn phiếu để hủy giao')
        return
      }

      let postData = {
        "dsPhieuHuy": [],
        "mayHuy": "-",
        "ngayHuy": moment().format('DD/MM/yyyy'),
        "nguoiHuy": this.$auth.getNhanVienID()
      }

      selectedRows.forEach((item) => {
        postData.dsPhieuHuy.push({
          "giaoPhieuId": item.GIAOPHIEU_ID,
          "nhanVienId": item.NHANVIEN_ID,
          "id": item.PHIEU_ID
        })
      })

      this.loading(true)

      TuyenThuAPI.huyphieuChuyenKhoanNVTC(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Hủy giao phiếu thành công.')
            this.searchGiaoHoaDonChuyenKhoan()
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false)})

    },
    doXemLuotDaGiao()
    {
      this.luotGiao_Input = {
          kyCuoc: this.params.p_kycuoc,
          kieu: 2,
          NVTCId: this.params.nvtc.id,
          NVTC: this.params.p_nhanvienthu_options.find(e => e.NHANVIEN_ID === this.params.nvtc.id)
      },
      this.$refs.dlgLuotGiao.openDialog();
    },
    onFilteringNVT(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_HIENTHI', 'contains', e.text, true) : query;
      e.updateData(this.params.p_nhanvienthu_options, query);
    },
    chukycuocChangeHandler(e) {
      this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
    },
    onChangeCustomDropDown(id) {
      const isHIdden = $(id).is(":hidden");
      if (isHIdden) {
        $(id).show();
      } else {
        $(id).hide();
      }
    },
    onSelectNVTC: async function (value) {
      if (value) {
        this.params.nvtc.id = value.NHANVIEN_ID;
        this.params.nvtc.text = value.TEN_HIENTHI;
        $("#nvtc-pd").hide();

        await this.getTienDieuChinh()

        this.loadDataTree()
        

      } else {
        this.params.nvtc.id = "";
        this.params.nvtc.text = "";
        this.resultDotGiao = []
        this.resultTuyenThu = []
        this.resultDaGiao = []
        this.resultChuaGiao = []
      }
    },
    chuaGiaoRowChanged(args)
    {
      /*
      let selectedRows = this.$refs.gridChuaGiao.getSelectedRecords()
      this.countSelectedRowsData[this.selectPhieuGiaoTab] = selectedRows.length

      this.sumRows[this.selectPhieuGiaoTab].selected = selectedRows.reduce((acc, curr) => {
        return acc = Number(curr.TIENTRA) + acc
      }, 0)

      this.$refs.gridChuaGiao.$refs.grid.ej2Instances.aggregateModule.refresh()
      */
    },
    daGiaoRowChanged(args)
    {
      /*
      if(args === null)
      {
        this.countSelectedRowsData[2] = 0
        this.sumRows[2].selected = 0
        this.sumRows[2].total = 0

      } else
      {

        let selectedRows = this.$refs.gridDaGiao.getSelectedRecords()
        this.countSelectedRowsData[this.selectPhieuGiaoTab] = selectedRows.length

        this.sumRows[this.selectPhieuGiaoTab].selected = selectedRows.reduce((acc, curr) => {
          return acc = Number(curr.TIENTRA) + acc
        }, 0)
      }


      this.$refs.gridDaGiao.$refs.grid.ej2Instances.aggregateModule.refresh()
      */
    },
    async getMapID(postData)
    {
      try
      {
        const res = await CommonsAPI.getMapID(this.axios, postData)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      }catch(err)
      {
        return []
      }
      
    },
    onCloseModal() {
      this.$bvModal.hide("modal-luot-da-giao");
    },
    onChonLuotGiao(value) {
       console.log(value);
       /*
      if (this.cbkHtg && this.form.htg.id.length == 0) {
        this.$toast.error("Chưa chọn hình thức giao!");
        return false;
      }
      if (this.cbkKhLon && this.form.khhl.id.length == 0) {
        this.$toast.error("Chưa chọn khách hàng lớn!");
        return false;
      }
      this.getDsPhieuDaGiao(value);
      */
    },
    chuaGiaoRowSelected(args)
    {
      this.refreshAggChuaGiao(args)
    },
    chuaGiaoRowDeselected(args)
    {
      this.refreshAggChuaGiao(args)
    },
    refreshAggChuaGiao(args)
    {
      let selectedRows = this.$refs.gridChuaGiao.getSelectedRecords()
      this.countSelectedRowsData[this.selectPhieuGiaoTab] = selectedRows.length
      if(selectedRows.length > 0)
        this.sumRows[this.selectPhieuGiaoTab].selected = selectedRows.reduce((acc, curr) => {
          return acc = Number(curr.TIENTRA) + acc
        }, 0)
      else
        this.sumRows[this.selectPhieuGiaoTab].selected = 0

      this.$refs.gridChuaGiao.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    daGiaoRowSelected(args)
    {
      this.refreshAggDaGiao(args)
    },
    daGiaoRowDeselected(args)
    {
      this.refreshAggDaGiao(args)
    },
    refreshAggDaGiao(args)
    {
      if(args === null)
      {
        this.countSelectedRowsData[2] = 0
        this.sumRows[2].selected = 0
        this.sumRows[2].total = 0

      } else
      {

        let selectedRows = this.$refs.gridDaGiao.getSelectedRecords()
        this.countSelectedRowsData[this.selectPhieuGiaoTab] = selectedRows.length

        if(selectedRows.length > 0)
          this.sumRows[this.selectPhieuGiaoTab].selected = selectedRows.reduce((acc, curr) => {
            return acc = Number(curr.TIENTRA) + acc
          }, 0)
        else
          this.sumRows[this.selectPhieuGiaoTab].selected = 0
      }


      this.$refs.gridDaGiao.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    dataBoundDotGiao(args)
    {
      
    },
    tabDotGiaoChange(index)
    {
      this.selectDotGiaoTab = index
      this.loadDataTree()
    },
    tabGiaoChange(index)
    {
      this.selectPhieuGiaoTab = index
      this.searchGiaoHoaDonChuyenKhoan()
    },
    async loadDataTree()
    {
      if(this.selectDotGiaoTab === 1) // đợt giao
      {
        if(this.params.p_chk_nhanvienthu === '0') // không check
        {
          if(this.dataDotGiao.length <= 0)
          {
            this.loading(true)
            await this.initDotGiaoData()
            this.loading(false)
          }
            
          this.treeDotGiaoFields =
            {
              dataSource: this.dataDotGiao,
              id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
            }
        } else  // có check
        {
          if(this.params.nvtc.id === '') // nếu chưa chọn NVTC
          {
            this.treeDotGiaoFields =
            {
              dataSource: [],
              id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
            }
          } else
          {
            this.loading(true)
            //await this.loadDotGiao()
            let ds_dot = []
              ds_dot.push({
                id : 'NV-' + this.params.nvtc.id,
                name: this.params.nvtc.text,
                hasChild: true,
                expanded: true,
                pid: null
              })
            let dot = this.dataDotGiao.filter(x => x.pid === 'NV-' + this.params.nvtc.id)
            ds_dot = [...ds_dot, ...dot]

            //console.log(ds_dot)

            this.treeDotGiaoFields =
            {
              dataSource: ds_dot,
              id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
            }

            this.loading(false)
          }
        }
        
      } else  // tuyến giao
      {
        if(this.params.p_chk_nhanvienthu === '0') // không check
        {
          
          if(this.dataTuyenThu.length <= 0)
          {
            this.loading(true)
            await this.initTuyenThuData()
            this.loading(false)
          }
          
            
          this.treeTuyenThuFields = {
            dataSource: this.dataTuyenThu,
            id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
          }

        } else
        {
          if(this.params.nvtc.id === '') // nếu chưa chọn NVTC
          {
            this.treeTuyenThuFields =
            {
              dataSource: [],
              id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
            }
          } else
          {
            this.loading(true)
            
            if(this.dataTuyenThu.length <= 0)
              await this.initTuyenThuData()
            let ds_tuyen = []
            ds_tuyen.push({
                id : 'NV-' + this.params.nvtc.id,
                name: this.params.nvtc.text,
                hasChild: true,
                expanded: true,
                pid: null
              })
            let tt = this.dataTuyenThu.filter(x => x.pid === 'NV-' + this.params.nvtc.id)
            ds_tuyen = [...ds_tuyen, ...tt]
            

            //console.log(ds_dot)

            this.treeTuyenThuFields =
            {
              dataSource: ds_tuyen,
              id: 'id', text: 'name', parentID: 'pid', hasChildren: 'hasChild'
            }
            
            //await this.loadTuyenThu()
            this.loading(false)
          }
        }
      }
    },

    actionCompleteHandler(args)
    {
      if(args.requestType === 'refresh')
      {
        let selected = []
        let f = false
        let tongtien = 0

        if(this.selectPhieuGiaoTab === 1) // chưa giao
        {
          
          this.resultChuaGiao.forEach((item, index) => {
            if(this.params.p_chk_tongphieu === '1') // checked
            {
              if(index > this.params.p_tongphieu)
                f = true
            }

            if(!f && this.params.p_chk_tongtien === '1')
            {
              if(this.params.p_tien_dc !== -1)
              {
                if(this.params.p_tongtien <= this.params.p_tien_dc)
                {
                  tongtien += parseInt(item.TIENTRA)
                  if(tongtien > this.params.p_tongtien)
                  {
                    f = true
                  } else
                    selected.push(index)
                } else
                {
                  let tien_nud = this.params.p_tien_dc + this.params.p_tien_dc / 10
                  if (tien_nud > this.params.p_tongtien)
                    tien_nud = this.params.p_tongtien
                  
                  this.params.p_tongtien = tien_nud
                  tongtien += parseInt(item.TIENTRA)
                  if(tongtien > tien_nud)
                  {
                    f = true
                  } else
                    selected.push(index)
                    
                }
              }
            }
          })
          this.$refs.gridChuaGiao.$refs.grid.selectRow(selected)
        } else
        {

        }
      }
      
    },
    async onSelectLuotGiao(value) {
      /*
        this.params.kieu = 2;
        this.params.giaoPhieuId = value;
        await this.getDSPhieuTon(this.params.giaoPhieuId, this.params.maTT.trim(), this.params.kieu);
        if (this.phieuTon_grid.items.length <= 0)
            this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
      */
     console.log(value)
    },

  },
  watch : {
    f_nhanvienthu :{
      handler: function(new_value, old_value) {
        this.loadDataTree()
      }
    }
  }
})
</script>
