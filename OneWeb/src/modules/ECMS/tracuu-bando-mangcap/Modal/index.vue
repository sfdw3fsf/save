<template src="./index.template.html"></template>

<script>
import VnptMap from '@vnpt/maps-onebss'
import './../../../../utils/gmaps'
import BanDo from '../components/BanDoVNPT.vue'
import ModalChonDiaChi from '../ChonDiaChi/Modal'
export default {
  components: { BanDo, ModalChonDiaChi },
  name: 'Modal',
  props: {
    modalShow: Boolean,
    data: Object,
    doituong: Object,
    id: String
  },

  computed: {
    getMarkers() {
      return this.markers
    },
    radiusObj() {
      return this.radius
    }
  },

  data() {
    return {
      listAddress: [],
      mapTypeId: 'ROADMAP',
      key: 1,
      isShown: false,
      dsBlock: [],
      cicles: {},
      ketquaTK: '',
      bankinhTK: 100,
      value: 100,
      viDo: -1,
      kinhDo: -1,
      validated: 1,
      diachi: '',
      dataSelected: {
        diachi_id: null,
        tinh_id: null,
        quan_id: null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id: null,
        dac_diem_id: null,
        so_nha: ''
      },
      markers: [],
      radius: {},
      mapType: 'ROADMAP',
      dsThuoctinh: [],
      lat: -1,
      lng: -1,
      center: {
        lat: this.lat,
        lng: this.lng
      },
      defaultRadius: -1,
      isShowListAddress: false
    }
  },
  watch: {
    mapType(val) {
      this.$refs.map.setmapType(val)
    },
    value(val) {
      this.defaultRadius = Math.round((val * this.value) / 10)
      this.changeAuto(this.defaultRadius)
    },
    defaultRadius(val) {
      if (val > 1000) {
        this.defaultRadius = 1000
      }
      if (val <= 50) {
        this.defaultRadius = 50
      }
      this.value = Math.round(Math.sqrt(val * 10))
      const r = parseInt(this.defaultRadius, 10)
      this.radius = {
        position: { lat: this.lat, lng: this.lng },
        r: r,
        id: 'x'
      }
    }
  },
  methods: {
    debounceInput: _.debounce(function () {
      this.onEnter()
    }, 500),
    dblclickMap(event) {
      console.log('🚀 ~ file: index.vue:91 ~ dblclickMap ~ event:', event)
      this.viDo = event.lat
      this.kinhDo = event.lng
      this.lat = event.lat
      this.lng = event.lng
      this.markers = []
      this.center.lat = event.lat
      this.center.lng = event.lng
    },
    onHiddenModal() {
      this.isShown = false
      this.show = false
      this.key++
    },
    async onShownModal() {
      // clear market
      this.markers = []
      this.isShown = true
      this.show = true
      try {
        this.loading(true)
        await this.getThuocTinh()
        if (this.doituong != null) {
          if (this.doituong.txtDiaChi != '') {
            await this.isHere(this.doituong.txtDiaChi)
          }
        } else {
          await this.getThongTinMap()
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    thaydoiMapType() {
      try {
        if (this.mapTypeId === 'ROADMAP') {
          this.mapTypeId = 'SATELLITE'
          this.$refs.map.mapVNPT.setStyle('https://maps.vnpt.vn/storage/vnptmaps/style/style_vetinh.json')
        } else {
          this.mapTypeId = 'ROADMAP'
          this.$refs.map.mapVNPT.setStyle('https://maps.vnpt.vn/tileserver/styles/mapvnpt_v2/style.json')
        }
      } catch (e) {
        console.log(e)
      }
    },
    changeAuto: function(rd) {
      this.radius = {}
      const r = parseInt(rd, 10)
      this.radius = {
        position: { lat: this.lat, lng: this.lng },
        r: r,
        id: 'x'
      }
    },
    closeModal: function() {
      this.$emit('closeModal', this.modalShow)
    },
    timKiemKC: async function() {
      try {
        this.loading(true)
        let data = {
          lat: this.lat,
          lng: this.lng,
          bankinh: this.defaultRadius,
          phanloaikc_id: 1,
          laykccon: 1
        }
        let res = await this.$root.context.post('/web-ecms/tracuubando/timketcuoi', data)
        let ds = res.data
        let soKetCuoi
        if (ds.length > 0) {
          soKetCuoi = ds.length
        }
        if (soKetCuoi > 0) {
          this.ketquaTK = 'Tìm thấy ' + soKetCuoi + ' kết cuối trong bán kính ' + this.defaultRadius + ' m'
        } else {
          this.ketquaTK = 'Không tìm thấy kết cuối nào.'
        }
        this.viDo = this.lat
        this.kinhDo = this.lng
        this.markers = []
        ds.forEach((element) => {
          var ThuocTinhObject = this.getThuocTinhObject(10, element.LOAIKC_ID, element.PHANLOAIKC_ID)
          var StyleThuocTinhObject = this.getStyleThuocTinhObject(ThuocTinhObject)
          StyleThuocTinhObject = {
            ToolTip: '<b>Kết cuối:</b> ' + element.KYHIEU + '</b><br>' + ('<b>Sử dụng:</b> ' + element.DLSD + ' / ' + element.DUNGLUONG) + '<br>' + '<b>Dung lượng chưa đấu nối thuê bao:</b> ' + element.DLCDN + '<br>' + element.DIACHI,
            ...StyleThuocTinhObject
          }
          this.markers.push({
            KETCUOI_ID: element.KETCUOI_ID,
            VIDO: parseFloat(element.VIDO),
            KINHDO: parseFloat(element.KINHDO),
            KYHIEU: element.KYHIEU,
            STYLE: StyleThuocTinhObject
          })
        })
      } catch (error) {
        this.$root.toastError(' ' + error.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },
    getThuocTinhObject(DoiTuongMC, LoaiDoiTuong, PhanLoaiDoiTuong) {
      var result = this.dsThuoctinh.filter((obj) => {
        return (obj.DOITUONG_MC === DoiTuongMC) & (obj.LOAI_DT === LoaiDoiTuong) & (obj.PHANLOAI_DT === PhanLoaiDoiTuong)
      })
      return result.length > 0 ? result[0] : {}
    },
    getStyleThuocTinhObject(ThuocTinhObject) {
      var StyleThuocTinhObject = {}
      if (ThuocTinhObject.GRAPHIC_TYPE === 'DIEM') {
        StyleThuocTinhObject.MauVien = ThuocTinhObject.BORDER_COLOR
        StyleThuocTinhObject.MauNen = ThuocTinhObject.S_COLOR
        if (ThuocTinhObject.IMAGE !== null) {
          const image = Buffer.from(ThuocTinhObject.IMAGE, 'base64')
          const blob = new Blob([image], { type: 'image/png' })
          const imgurl = window.URL.createObjectURL(blob)
          StyleThuocTinhObject.ImageUrl = imgurl
        } else {
          StyleThuocTinhObject.ImageUrl = '/static/icons/icon100x100/CHUA_XAC_DINH.jpg'
        }
        StyleThuocTinhObject.WIDTH = ThuocTinhObject.WIDTH
        StyleThuocTinhObject.HEIGHT = ThuocTinhObject.HEIGHT
        if (ThuocTinhObject.hasOwnProperty('S_FORMAT')) {
          StyleThuocTinhObject.TenHienThi = ThuocTinhObject.S_FORMAT
        }
      }
      return StyleThuocTinhObject
    },
    getThuocTinh: async function() {
      try {
        let rs = await this.$root.context.get('/web-ecms/tracuubando/lay-ds-thuoctinh')
        if (rs.error_code == 'BSS-00000000') {
          console.log('getThuocTinh', rs.data)
          this.dsThuoctinh = rs.data
        }
      } catch (error) {
        this.$root.toastError('' + error)
      }
    },
    getDiaChi: async function(data) {
      this.diachi = data.diachimoi
      this.dataSelected.diachi = data.diachimoi
      this.dataSelected.tinh_id = data.tinh_id
      this.dataSelected.quan_id = data.quan_id
      this.dataSelected.phuong_id = data.phuong_id
      this.dataSelected.pho_id = data.pho_id
      this.dataSelected.ap_id = data.ap_id
      this.dataSelected.khu_id = data.khu_id
      this.dataSelected.dac_diem_id = data.dacdiem_id
      this.dataSelected.so_nha = data.so_nha
      await this.onEnter()
    },
    getThongTinMap: async function() {
      try {
        this.$root.showLoading(true)
        this.lat = this.$root.context.user.getProperty('latitus')
        this.lng = this.$root.context.user.getProperty('longtitus')
        this.center.lat = this.lat
        this.center.lng = this.lng
        this.defaultRadius = this.$root.context.user.getProperty('bankinh_khaosat')
        console.log('🚀 ~ file: index.vue:243 ~ this.defaultRadius:', this.defaultRadius)
        this.radius = {
          position: { lat: this.lat, lng: this.lng },
          r: this.defaultRadius,
          id: 'x'
        }
        this.viDo = this.lat
        this.kinhDo = this.lng
        this.value = this.defaultRadius
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    formatTooltip(val) {
      let v = Math.round((this.value * val) / 10)
      return v.toString()
    },
    changeR: function(v) {
      const a = Math.round((this.value * v) / 10)
      this.defaultRadius = a.toString()
    },
    isHere: async function() {
      this.markers = []
      const address = this.diachi
      var re = new RegExp(/-?(\d*\.)?\d+,\s*-?(\d*\.)?\d+/)
      if (re.test(address)) {
        let arr = address.split(',')
        let location = {
          lat: parseFloat(arr[1]),
          lng: parseFloat(arr[0])
        }
        this.center.lat = location.lat
        this.center.lng = location.lng
        this.lat = location.lat
        this.lng = location.lng
        this.radius = {}
        this.radius = {
          position: {
            lat: location.lat,
            lng: location.lng
          },
          r: this.$root.context.user.getProperty('bankinh_khaosat'),
          id: 'x'
        }
        this.radiusObj.r = this.$root.context.user.getProperty('bankinh_khaosat')
        this.defaultRadius = this.$root.context.user.getProperty('bankinh_khaosat')
      } else {
        const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'
        var basemapApi = VnptMap.initBasemapApi()
        const geocoder = basemapApi.getLonLatByAddressAndLocation(address, [10.778933, 106.688096], TOKEN, (rs) => {
          this.listAddress = rs.result
          if (this.listAddress.length > 0) {
            this.isShowListAddress = true
          }
        })
      }
      await this.timKiemKC()
    },
    async chonDiaChi(item) {
      this.markers = []
      this.diachi = item.address
      this.isShowListAddress = false
      this.center.lat = item.lat
      this.center.lng = item.long
      this.lat = item.lat
      this.lng = item.long
      this.radius = {}
      this.radius = {
        position: {
          lat: item.lat,
          lng: item.long
        },
        r: this.$root.context.user.getProperty('bankinh_khaosat'),
        id: 'x'
      }
      this.radiusObj.r = this.$root.context.user.getProperty('bankinh_khaosat')
      this.defaultRadius = this.$root.context.user.getProperty('bankinh_khaosat')
      await this.timKiemKC()
    },
    isHere2: async function(diachi) {
      this.markers = []
      const address = diachi
      const geocoder = new window.google.maps.Geocoder()
      geocoder
        .geocode({ address: address })
        .then((response) => {
          if (response.results[0]) {
            this.point = response.results[0].geometry.location.lat() + ',' + response.results[0].geometry.location.lng()
            this.address = response.results[0].formatted_address
            this.center.lat = response.results[0].geometry.location.lat()
            this.center.lng = response.results[0].geometry.location.lng()
            this.lat = response.results[0].geometry.location.lat()
            this.lng = response.results[0].geometry.location.lng()
            this.radius = {}
            this.radius = {
              position: {
                lat: response.results[0].geometry.location.lat(),
                lng: response.results[0].geometry.location.lng()
              },
              r: this.$root.context.user.getProperty('bankinh_khaosat'),
              id: 'x'
            }
            this.viDo = this.lat
            this.kinhDo = this.lng
            this.value = this.$root.context.user.getProperty('bankinh_khaosat')
            this.diachi = diachi
          } else {
            this.$root.toastError('Không tìm thấy địa danh!')
          }
        })
        .catch((e) => this.$root.toastError('Không tìm thấy địa danh!'))
    },
    onEnter: async function() {
      try {
        this.loading(true)
        if (this.diachi != '') {
          await this.isHere();
        } else {
          this.$root.toastError('Bạn chưa nhập địa chỉ!')
        }
      } catch (error) {
        this.$root.toastError('Lỗi phát sinh: ' + error)
      } finally {
        this.loading(false)
      }
    },
    addMarker: async function() {
      try {
        this.loading(true)
        if (this.diachi != '') {
          await this.isHere()
        } else {
          this.$root.toastError('Bạn chưa nhập địa chỉ!')
        }
      } catch (error) {
        this.$root.toastError('Lỗi phát sinh: ' + error)
      } finally {
        this.loading(false)
      }
    },
    handleClickOutside(event) {
      const list = document.getElementById('listAddress')
      const input = document.getElementById('inputDiachi')
      if (list && !list.contains(event.target) && input && !input.contains(event.target)) {
        this.isShowListAddress = false
      }
    }
  },
  async mounted() {
    document.addEventListener('click', this.handleClickOutside.bind(this))
  },
  beforeDestroy() {
    document.removeEventListener('click', this.handleClickOutside.bind(this))
  },
  async created() {}
}
</script>
<style>
.el-slider.is-vertical .el-slider__runway {
  width: 6px;
  margin: 5px 20px;
  height: 275px;
  float: right;
  margin-right: 0px;
}
.modal-body {
  max-height: 80%;
}

.list-address {
  position: absolute;
  list-style-type: none;
  background-color: white;
  max-height: 300px;
  overflow-y: scroll;
  padding: 10px;
}
.list-add-ul {
  list-style-type: none;
  padding-left: 0;
}
.address-item {
  cursor: pointer;
  padding: 5px;
  border-bottom: 1px solid #ccc;
}
.address-item:hover {
  background-color: #f5f5f5;
}
.popup-box{
  max-height: 90vh;
}
</style>
