<template>
  <div class="row">
    <div class="box-col box-form">
      <div class="legend-title">
        Đầu mối liên hệ
        <span class="right icon fa fa-plus-circle f30" @click="addNewRow" style="padding-right:30px"></span>
      </div>

      <div class="table-content">
        <DataGrid ref="gridDanhSach" :columns="grid_danhsach.columns" :dataSource="grid_danhsach.dataSource" :allowPaging="false" dataKeyField="STT" :editSettings="{ allowEditing: true, mode: 'Batch' }" :showFilter="false" />
      </div>
    </div>
  </div>
</template>
<script>
import moment from 'moment'
import _ from 'lodash'
import DatePicker from 'vue2-datepicker'

export default {
  name: 'DauMoiLienHe',
  props: {
    relevantInfo: {
      type: Array,
      default: () => []
    }
  },
  components: {
    DatePicker
  },
  data() {
    return {
      commands: [
        {
          buttonOption: { content: 'Xóa', cssClass: 'icon one-trash' }
        }
      ],
      grid_danhsach: {
        dataSource: [],
        columns: [
          { fieldName: 'order', headerText: 'STT', textAlign: 'center', allowFiltering: true, width: 60, allowEditing: false },
          { fieldName: 'relevantName', headerText: 'Họ tên(*)', textAlign: 'center', allowFiltering: true, width: 200, allowEditing: false, template: this.getInputTemplate(this, 'relevantName', 'gridDanhSach') },
          { fieldName: 'department', headerText: 'Phòng ban (*)', textAlign: 'center', allowFiltering: true, width: 130, allowEditing: false, template: this.getInputTemplate(this, 'department', 'gridDanhSach') },
          { fieldName: 'dateOfBirth', headerText: 'Ngày sinh(*)', textAlign: 'center', allowFiltering: true, width: 140, allowEditing: false, template: this.getDateTemplate(this, 'dateOfBirth', 'gridDanhSach') },
          { fieldName: 'gender', headerText: 'Giới tính', textAlign: 'center', allowFiltering: true, width: 90, allowEditing: false, template: this.getSelectedTemplate(this, 'gender', 'gridDanhSach') },
          { fieldName: 'phoneNumber', headerText: 'Số điện thoại(*)', textAlign: 'center', allowFiltering: true, width: 130, allowEditing: false, template: this.getInputTemplate(this, 'phoneNumber', 'gridDanhSach') },
          { fieldName: 'email', headerText: 'Email(*)', textAlign: 'center', allowFiltering: true, width: 130, allowEditing: false, template: this.getInputTemplate(this, 'email', 'gridDanhSach') },
          { fieldName: 'action', headerText: 'Thao tác', textAlign: 'center', allowFiltering: true, width: 60, allowEditing: false, template: this.templateDeleteRow(this) }
        ]
      }
    }
  },
  computed: {},
  mounted() {
    this.grid_danhsach.dataSource = [
      {
        order: 1,
        relevantName: '',
        department: '',
        dateOfBirth: '',
        gender: '',
        phoneNumber: '',
        email: '',
        action: ''
      },
    ]
  },

  methods: {
    handleRemoveRow(args) {
        
      this.$confirm(`Bạn có chắc chắn muốn xóa dòng này ?`, {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      }).then(async () => {
        this.grid_danhsach.dataSource = this.grid_danhsach.dataSource
          .filter((item) => item.order !== args.order)
          .map((item, index) => {
            item.order = index + 1
            return item
          })  
      })
    },
    getInputTemplate(parent, field, grid) {
      return function() {
        return {
          template: {
            template: `<div class="info-row"> <input class="form-control" type="text" @change="change" v-model="data[field]" > </div>`,

            data: function() {
              return { data: {}, field }
            },
            methods: {
              async change(event) {
                const dataUpdate = parent.$refs[grid].dataSource[this.data.index]
                dataUpdate[field] = event.target.value
              }
            },
            computed: {}
          }
        }
      }
    },
    getDateTemplate(parent, field, grid) {
      return function() {
        return {
          template: {
            template: `<div class="" >
                        <date-picker format="DD/MM/YYYY" v-model="cptBirthDate" type="date" @change="change">
                            <template #icon-calendar>
                                <span class="icon one-calendar" style="padding-right: 10px"></span>
                            </template>
                        </date-picker>
                    </div>`,

            components: {
              DatePicker
            },
            data: function() {
              return { data: {}, field }
            },
            methods: {
              async change(value) {
                // const birthDate =  moment(value).format('DD/MM/YYYY')
                const dataUpdate = parent.$refs[grid].dataSource[this.data.index]
                dataUpdate[field] = moment(value).format('DD/MM/YYYY')
              }
            },
            computed: {
              cptBirthDate: {
                get() {
                    const date = this.data[field]
                    return date ? moment(date, 'DD/MM/YYYY').toDate() : null
                },
                set(value) {
                    this.data[field] = moment(value).format('DD/MM/YYYY')
                }
              }
            }
          }
        }
      }
    },
    getSelectedTemplate(parent, field, grid) {
      return function() {
        return {
          template: {
            template: `<div class="select-custom">
                                        <SelectExt dataValueField="value" dataTextField="text" v-model="data[field]" @change="change"
                                            :dataSource="dataSource" />
                                    </div>`,

            data: function() {
              return {
                dataSource: [],
                sex: '',
                data: {},
                field
              }
            },
            methods: {
              async change(value) {
                const dataUpdate = parent.$refs[grid].dataSource[this.data.index]
                dataUpdate[field] = value
              }
            },
            mounted() {
              this.dataSource = [
                { value: 'Nam', text: 'Nam' },
                { value: 'Nữ', text: 'Nữ' }
              ]
            }
          }
        }
      }
    },
    templateDeleteRow(parent) {
      return function() {
        return {
          template: {
            template: `<div class="text-center"><button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnRedirectClick" :disabled="isActiveBtn" >
                        <span class="fa icon one-trash"></span>
                        </button></div>`,
            data: function() {
              return { data: {} }
            },
            methods: {
              btnRedirectClick() {
                // console.log("data", this.data);
                parent.handleRemoveRow(this.data)
              }
            },
            computed: {
              isActiveBtn() {      
                if(parent.grid_danhsach.dataSource.length === 1) return true
                return false
              }
            }
          }
        }
      }
    },

    addNewRow() {
      if (this.grid_danhsach.dataSource.length >= 10) return

      this.grid_danhsach.dataSource = [
        ...this.grid_danhsach.dataSource,
        {
          order: this.grid_danhsach.dataSource.length + 1,
          relevantName: '',
          department: '',
          dateOfBirth: '',
          gender: '',
          phoneNumber: '',
          email: '',
          action: ''
        }
      ]
    }
  },

  watch: {
    // 'grid_danhsach.dataSource': {
    //   handler: function(newVal, oldVal) {
    //     this.$emit('update-danhsach-daumoi', newVal)
    //   },
    //   deep: true,
    //   immediate: true
    // },
    'relevantInfo': {
      handler: function(newVal, oldVal) {
        if(newVal.length === 0) return;
        this.grid_danhsach.dataSource = newVal.map((item, index) => {
            item.order = index + 1
            return item
        })
            
      }
    }
  }
}
</script>
<style scoped>
.custom-cycle-plus {
  border: none;
  padding: 5px 10px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 5px;
}
</style>
