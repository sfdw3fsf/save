<template>
  <div class="">
    <div class="row">
      <div class="legend-title">Thông tin cơ bản</div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w150">Mã hạ tầng mạng <span class="red">*</span></div>
          <div class="value d-flex align-items-center justify-content-between">
            <input
              type="text"
              class="form-control"
              v-model="currentItem.maHaTang"
              :disabled="mode === 'VIEW'"
              style="flex: 1;"
            />
            <div class="d-flex align-items-center ml-2">
              Hiệu lực
              <input
                type="checkbox"
                class="check ml-1"
                v-model="currentItem.hieuLuc"
                :true-value="1"
                :false-value="0"
                :disabled="mode === 'VIEW'"
              />
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w150">Tên hạ tầng mạng <span class="red">*</span></div>
          <div class="value">
            <input
              type="text"
              class="form-control"
              v-model="currentItem.ten"
              :disabled="mode === 'VIEW'"
              @blur="checkTenHaTangTonTai"
            />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w150">Trạng thái CCDV <span class="red">*</span></div>
          <div class="value">
            <SelectExt
              :dataSource="trangThaiCungCapDichVuList"
              dataTextField="TEN"
              dataValueField="ID"
              v-model="currentItem.trangThaiCCDVId"
              :disabled="mode === 'VIEW'"
            />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w150">IDC<span class="red">*</span></div>
          <div class="value">
            <SelectExt
              :dataSource="idcList"
              dataTextField="TEN"
              dataValueField="ID"
              v-model="currentItem.idcId"
              :disabled="mode === 'VIEW'"
            />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w150">Công nghệ sử dụng <span class="red">*</span></div>
          <div class="value">
            <SelectExt
              :dataSource="congNgheSuDungList"
              dataTextField="TEN"
              dataValueField="ID"
              v-model="currentItem.congNgheId"
              :disabled="mode === 'VIEW'"
            />
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="legend-title">Thông tin quản lý</div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w150">Đơn vị quản lý <span class="red">*</span></div>
          <div class="value">
            <TreeSelector
              v-model="currentItem.donviQlyId"
              :dataSource="donViQuanLyList"
              textField="TEN"
              valueField="ID"
              type="donvi"
              :disabled="mode === 'VIEW'"
              placeholder="-- Chọn đơn vị quản lý --"
              title="Chọn đơn vị quản lý"
            />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w150">Phòng/tổ quản lý <span class="red">*</span></div>
          <div class="value">
            <TreeSelector
              v-model="currentItem.phongBanQlyId"
              :dataSource="phongToQuanLyList"
              :parentId="currentItem.donviQlyId"
              :parentField="'DONVI_CHA_ID'"
              :requireParentId="true"
              type="phongban"
              textField="TEN"
              valueField="ID"
              :disabled="mode === 'VIEW'"
              placeholder="-- Chọn phòng/tổ quản lý --"
              title="Chọn phòng/tổ quản lý"
              parentRequiredMessage="Vui lòng chọn đơn vị quản lý trước"
            />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w150">Cá nhân quản lý<span class="red">*</span></div>
          <div class="value">
            <SimpleMultiSelect
              :dataSource="caNhanQuanLyList"
              v-model="currentItem.nguoiQlyId"
              title="Chọn cá nhân quản lý"
              :disabled="mode === 'VIEW'"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import api from '../api.js'
import TreeSelector from './TreeSelector.vue'
import SimpleMultiSelect from './SimpleMultiSelect.vue'
const defaultItem = {
  id: 0,
  ten: '',
  maHaTang: '',
  idcId: null,
  congNgheId: null,
  trangThaiCCDVId: 1,
  hieuLuc: 1,
  donviQlyId: null,
  phongBanQlyId: null,
  nguoiQlyId: [],
  ghiChu: ''
}

export default {
  name: 'ThongTinChungHTM',
  components: {
    TreeSelector,
    SimpleMultiSelect
  },
  props: {
    itemId: {
      type: Number,
      default: 0
    },
    mode: {
      type: String,
      default: 'VIEW',
      validator: (value) => ['ADD', 'EDIT', 'VIEW'].includes(value)
    }
  },
  data() {
    return {
      currentItem: { ...defaultItem },
      congNgheSuDungList: [],
      donViQuanLyList: [],
      caNhanQuanLyList: [],
      phongToQuanLyList: [],
      idcList: [],
      trangThaiCungCapDichVuList: []
    }
  },
  watch: {
    itemId: {
      immediate: true,
      async handler(newVal) {
        // Reset currentItem trước
        this.currentItem = { ...defaultItem }

        if (newVal && newVal > 0) {
          // Load data nếu là edit
          await this.loadData()
        } else {
          await this.loadDanhMuc()
        }
      }
    },
    'currentItem.donviQlyId': async function(newVal, oldVal) {
      if (newVal) {
        await this.getPhongBanQuanLyList(newVal)
      } else {
        this.phongToQuanLyList = []
      }
    },
    'currentItem.phongBanQlyId': async function(newVal, oldVal) {
      if (oldVal && newVal !== oldVal) {
        this.currentItem.nguoiQlyId = []
      }
      if (newVal) {
        await this.getNguoiQuanLyList(newVal)
      } else {
        this.caNhanQuanLyList = []
      }
    }
  },
  async mounted() {
    await this.loadDanhMuc()
  },
  methods: {
    async loadDanhMuc() {
      try {
        // Load IDC
        const idcResponse = await api.getIDC(this.$root.context)
        this.idcList = idcResponse || []

        // Load Đơn vị quản lý - lọc theo đơn vị cha của đơn vị đang login
        const donViResponse = await api.getDonViQuanLyList(this.$root.context)
        const currentDonViId = this.$root.token.getDonViID()

        // Tìm đơn vị hiện tại để lấy DONVI_CHA_ID
        if (currentDonViId && donViResponse) {
          const currentDonVi = donViResponse.find((dv) => dv.ID == currentDonViId)

          const parentDonViId = currentDonVi ? currentDonVi.DONVI_CHA_ID : null

          // Lọc chỉ lấy đơn vị cha và các đơn vị anh em (cùng cha)
          if (parentDonViId) {
            this.donViQuanLyList = donViResponse.filter((dv) => {
              return dv.ID == parentDonViId || dv.DONVI_CHA_ID == parentDonViId
            })
          } else {
            // Nếu không có cha thì lấy đơn vị hiện tại và con
            this.donViQuanLyList = donViResponse.filter((dv) => {
              return dv.ID == currentDonViId || dv.DONVI_CHA_ID == currentDonViId
            })
          }
        } else {
          this.donViQuanLyList = donViResponse || []
        }

        // Nếu đã có donviQlyId thì load phong ban
        if (this.currentItem.donviQlyId) {
          await this.getPhongBanQuanLyList(this.currentItem.donviQlyId)
        }

        // Nếu đã có phongBanQlyId thì load người quản lý
        if (this.currentItem.phongBanQlyId) {
          await this.getNguoiQuanLyList(this.currentItem.phongBanQlyId)
        }

        // Tạm thời dùng dữ liệu mẫu
        this.trangThaiCungCapDichVuList = await api.getTrangThaiCungCapDichVuList(this.$root.context)

        this.congNgheSuDungList = await api.getCongNgheList(this.$root.context, 1)
      } catch (error) {
        console.error('Lỗi khi tải danh mục:', error)
      }
    },

    async getPhongBanQuanLyList(donViId) {
      try {
        const response = await api.getPhongBanQuanLyList(this.$root.context, donViId)
        this.phongToQuanLyList = response || []
      } catch (error) {
        console.error('Lỗi lấy thông tin phòng ban: ', error)
      }
    },

    async getNguoiQuanLyList(phongBanId) {
      try {
        const response = await api.getNguoiQuanLyList(this.$root.context, phongBanId)
        this.caNhanQuanLyList = response || []
      } catch (error) {
        console.error('Lỗi lấy thông tin người quản lý: ', error)
      }
    },

    async checkTenHaTangTonTai() {
      // Chỉ check khi không phải mode VIEW và có nhập tên
      if (this.mode === 'VIEW' || !this.currentItem.ten || this.currentItem.ten.trim() === '') {
        return
      }

      try {
        await api.checkTenHaTang(this.$root.context, this.currentItem.id || null, this.currentItem.ten.trim())
      } catch (error) {
        const message = error.response?.data?.message
        this.$root.toastWarning(message || 'Tên hạ tầng mạng đã tồn tại')
      }
    },
    async validate() {
      const errors = []

      if (!this.currentItem.ten || this.currentItem.ten.trim() === '') {
        errors.push('Vui lòng nhập tên hạ tầng mạng')
      }

      if (!this.currentItem.idcId) {
        errors.push('Vui lòng chọn IDC')
      }
      if (!this.currentItem.maHaTang || this.currentItem.maHaTang.trim() === '') {
        errors.push('Vui lòng nhập mã hạ tầng mạng')
      }
      if (!this.currentItem.congNgheId) {
        errors.push('Vui lòng chọn công nghệ sử dụng')
      }
      if (!this.currentItem.donviQlyId) {
        errors.push('Vui lòng chọn đơn vị quản lý')
      }
      if (!this.currentItem.phongBanQlyId) {
        errors.push('Vui lòng chọn phòng/tổ quản lý')
      }
      if (!this.currentItem.nguoiQlyId || this.currentItem.nguoiQlyId.length === 0) {
        errors.push('Vui lòng chọn cá nhân quản lý')
      }

      if (errors.length > 0) {
        this.$root.toastError(errors.join('\n'))
        return false
      }
      //check tên tồn tại
      try {
        await api.checkTenHaTang(this.$root.context, this.currentItem.id || null, this.currentItem.ten.trim())
      } catch (error) {
        const message = error.response?.data?.message
        this.$root.toastError(message || 'Tên hạ tầng mạng đã tồn tại')
        return false
      }
      return true
    },
    async loadData() {
      try {
        this.$root.showLoading(true)
        const response = await api.getDetail(this.$root.context, this.itemId)
        if (response && response.data) {
          // Update currentItem through parent
          Object.assign(this.currentItem, response.data)

          // Load danh sách nhân viên riêng
          const nhanVienResponse = await api.getNhanVienByHtm(this.$root.context, this.itemId)
          if (nhanVienResponse && nhanVienResponse.data) {
            // Map data từ API sang format của SimpleMultiSelect
            this.currentItem.nguoiQlyId = nhanVienResponse.data.map((nv) => ({
              NHANVIEN_ID: nv.nhanvienId,
              TEN_NV: nv.nhanvienName,
              NHIEMVU_ID: nv.nhiemvuId,
              nhanvienId: nv.nhanvienId,
              nhiemvuId: nv.nhiemvuId
            }))
          } else {
            this.currentItem.nguoiQlyId = []
          }
        }
      } catch (error) {
        this.$root.toastError('Lỗi khi tải chi tiết hạ tầng mạng')
      } finally {
        this.$root.showLoading(false)
      }
    },

    async handleSave(itemId) {
      const isValid = await this.validate()
      if (!isValid) return false
      let flag = await this.$confirm('Có chắc bạn muốn cập nhật hạ tầng mạng?', 'Xác nhận lưu', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
      if (flag) {
        try {
          this.$root.showLoading(true)
          const employees = this.currentItem.nguoiQlyId || []

          const payload = {
            id: itemId || 0,
            ten: this.currentItem.ten,
            maHaTangMang: this.currentItem.maHaTang,
            idcId: this.currentItem.idcId,
            congNgheId: this.currentItem.congNgheId,
            trangThaiCCDVId: this.currentItem.trangThaiCCDVId,
            hieuLuc: this.currentItem.hieuLuc,
            donViQuanLyId: this.currentItem.donviQlyId,
            phongQuanLyId: this.currentItem.phongBanQlyId,
            ghiChu: this.currentItem.ghiChu
          }

          // Bước 1: Lưu thông tin chính
          const response = await api.insertOrUpdate(this.$root.context, payload)

          // Backend trả về data là ID trực tiếp khi INSERT, hoặc object khi UPDATE
          const savedId = typeof response.data === 'number' ? response.data : response.data?.id || itemId

          // Bước 2: Lưu danh sách nhân viên riêng (nếu có ID)
          if (savedId && employees.length > 0) {
            // Map sang format backend yêu cầu
            const employeesPayload = employees.map((emp) => ({
              nhanvienId: emp.nhanvienId || emp.NHANVIEN_ID,
              nhiemvuId: emp.nhiemvuId || emp.NHIEMVU_ID
            }))
            await api.saveNhanVienHtm(this.$root.context, savedId, employeesPayload)
          }

          this.$root.toastSuccess(response.data.message || 'Lưu thành công')
          return true
        } catch (error) {
          const errorMsg = error.response?.data?.message || 'Lỗi khi lưu dữ liệu'
          this.$root.toastError(errorMsg)
          return false
        } finally {
          this.$root.showLoading(false)
        }
      }
    }
  }
}
</script>
