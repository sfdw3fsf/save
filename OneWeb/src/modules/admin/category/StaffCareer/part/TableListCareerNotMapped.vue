<template>
  <div class="box-col box-form">
    <div class="legend-title">Danh sách nghề nghiệp chưa gán</div>
    <div class="table-content">
      <table class="table-result table-gachle">
        <thead class="center">
          <tr>
            <th class="w20"></th>
            <th class="w20">Chọn</th>
            <th>Nghành nghề</th>
            <th>Mã nghành nghề</th>
          </tr>
          <tr>
            <th class="w20"></th>
            <th class="w50">
              <div class="check-action">
                <input type="checkbox" class="check" v-model="filterChecked" />
                <span class="name"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" v-model="filterCareerName"/>
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" v-model="filterCareerCode"/>
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
          </tr>
        </thead>
        <tbody>
          <template v-if="!isLoadingCareerNotMappedList">
          <tr
            v-for="(career, index) in careerListFiltered"
            :key="`row-data-career-not-mapped-${page}-${index}`"
            :class="{'highlight' : career.checked, }"
          >
            <td class="w20 center">
              <span class="fa fa-play text-main" v-if="career.checked"></span>
            </td>
            <td class="w20 center">
              <div class="check-action">
                <input type="checkbox" class="check" :checked="career.checked" @change="changeChecked(career)"/>
                <span class="name"></span>
              </div>
            </td>
            <td :class="{'selected highlight1' : career.checked}">{{career.tennganhnghe}}</td>
            <td>{{career.manganh}}</td>
          </tr>
          </template>
          <template v-else>
              <tr class="text-center">
              <td colspan="75%">
                <b-spinner class="mt-3 mb-3" variant="primary" label="Spinning"></b-spinner>
              </td>
            </tr>
          </template>
        </tbody>
      </table>
    </div>
    <vue-paginate v-model="page" :per_page="perPage" :total="totalItem"></vue-paginate>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
export default {
  name: 'TableListCareerNotMapped',
  components: {
  },
  computed: {
    ...mapState('mapStaffAndCareer', ['careerNotMappedList', 'isLoadingCareerNotMappedList']),
    careerListFiltered: {
      get () {
        return this.careerNotMappedList
          .filter((career, index) => {
            const testName = new RegExp(this.filterCareerName, 'i')
            const testCode = new RegExp(this.filterCareerCode, 'i')
            return testName.test(career.tennganhnghe) && testCode.test(career.manganh) && this.filterChecked === false ? true : career.checked
          })
          .slice((this.page - 1) * this.perPage, this.page * this.perPage)
      }
    },
    totalItem: {
      get () {
        return this.careerNotMappedList.filter(career => {
          const testName = new RegExp(this.filterCareerName, 'i')
          const testCode = new RegExp(this.filterCareerCode, 'i')
          return testName.test(career.tennganhnghe) && testCode.test(career.manganh) && this.filterChecked === false ? true : career.checked
        }).length
      }
    }
  },
  methods: {
    ...mapMutations('mapStaffAndCareer', [
      'updateChecked'
    ]),
    changeChecked (career) {
      this.updateChecked(career)
    },
    resetPage () {
      this.page = 1
    }
  },
  data: () => ({
    filterCareerCode: '',
    filterCareerName: '',
    filterChecked: false,
    page: 1,
    perPage: 10
  }),
  watch: {
    careerNotMappedList: {
      handler (val) {
        this.page = 1
        this.filterCareerCode = ''
        this.filterCareerName = ''
        this.filterChecked = false
      }
    },
    // reset page when search
    filterCareerName: {
      handler (val) {
        this.page = 1
      }
    },
    filterCareerCode: {
      handler (val) {
        this.page = 1
      }
    },
    filterChecked: {
      handler (val) {
        this.page = 1
      }
    }
  }
}
</script>
