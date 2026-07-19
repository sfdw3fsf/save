<template>
  <div class="list-actions-top">
    <ul class="list">
      <li>
        <a href="#" @click.prevent="searchStaff"><span class="icon one-search" ></span>Tìm kiếm</a>
      </li>
      <li>
        <a href="#" @click.prevent="updateMappedCareerAndStaff"> <span class="icon one-save"></span>Ghi lại</a>
      </li>
    </ul>
  </div>
</template>

<script>
import api from '../api'
import { mapState, mapMutations } from 'vuex'

export default {
  name: 'HeaderSearchStaff',
  computed: {
    ...mapState('mapStaffAndCareer', [
      'selectedUnit',
      'selectedTypeOfStaff',
      'selectedTypeOfArea',
      'staffCode',
      'selectedStaff',
      'careerMappedList'
    ])
  },
  methods: {
    ...mapMutations('mapStaffAndCareer', [
      'setStaffList',
      'setCareerMappedList',
      'setCareerNotMappedList',
      'setIsLoadingStaffList',
      'setSelectedStaff'
    ]),
    async searchStaff () {
      const data = {
        'p_donvi_id': this.selectedUnit,
        'p_loaikhuvuc': this.selectedTypeOfArea,
        'p_loainv_id': this.selectedTypeOfStaff,
        'p_ma_nv': this.staffCode
      }
      this.loading(true)
      await api.searchStaff(this.axios, data)
        .then(response => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.setStaffList(response.data.data)
          } else {
            this.setStaffList([])
            this.$toast.error('Không có thông tin tìm kiếm !')
            this.setCareerMappedList([])
            this.setCareerNotMappedList([])
            this.setSelectedStaff({})
          }
        })
        .finally(() => {
          this.loading(false)
        })
        .catch(err => {
          this.$toast.error(err.response.data.message)
        })
    },

    updateMappedCareerAndStaff () {
      if (!this.selectedStaff) {
        return
      }
      const staffId = this.selectedStaff.nhanvien_id
      const data = {
        p_data: this.careerMappedList.map(element => {
          return {
            'NGANHNGHE_ID': element.nganhnghe_id,
            'NHANVIEN_ID': staffId
          }
        }),
        p_nhanvien_id: staffId
      }
      this.loading(true)
      api.updateCareerMappedStaff(this.axios, data).then(() => {
        this.$toast.success('Cập nhật dữ liệu thành công!')
      }).catch(() => {
        this.$toast.error('Cập nhật dữ liệu lỗi!')
      }).finally(() => {
        this.loading(false)
      })
    }
  }
}
</script>
