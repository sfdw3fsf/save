<template>
  <div class="row">
    <div class="grid-stack-box">
      <ejs-splitter id='splitter_2' class='splitterContent' ref="splitterObj_2" :separatorSize=1 width='100%'>
        <e-panes>
          <e-pane size="50%" min="20%" content='#content113'></e-pane>
          <e-pane size="50%" min="20%" content='#content114'></e-pane>
        </e-panes>
      </ejs-splitter>
    </div>
    <div id='content113'>
      <div class="box-form">
            <div class="legend-title">Danh sách nhân viên đã gán</div>
            <!-- <div style="margin: 0px 0px 10px 0px;">
              <ejs-dropdownlist
                :allowFiltering="true"
                v-model="selectedDepartmentAddedId"
                :filtering="onFilteringDepartmentsAdded"
                :placeholder="'Chọn đơn vị'"
                :dataSource="departmentsAdded"
                :fields="{ text: 'ten_dv', value: 'donvi_id' }"/>
            </div> -->
            <div class="table-content grid-ext grid-ext-freeze" style="max-height: 248px;margin-top: 58px;">
                <ejs-grid ref="gridAdded"
                :dataSource="dataGridAdded.dataSource"
                :allowGrouping="true"
                :allowEditing="false"
                :groupSettings="dataGridAdded.groupOptions"              
                :allowSorting='true'
                :allowPaging='true'
                :pageSettings='pageSettings'
                :allowFiltering='true'
                :selectionSettings='dataGridAdded.selectionOptions'
                :pagerTemplate='pagerTemplate'
              >
                  <e-columns>
                      <e-column type='checkbox' width='50'></e-column>                    
                      <e-column field='ten_dv' headerText='Tên đơn vị' width='120'></e-column>
                      <e-column field='ma_nv' isPrimaryKey='true' headerText='Mã NV' width='120'></e-column>
                      <e-column field='ten_nv' headerText='Tên NV' width='180'></e-column>
                      <e-column
                        headerText="Quyền gửi"
                        field="quyen_kg"
                        width="120"
                        :template="this.cTemplateQuyengui_added(this)"
                        editType="booleanedit"
                        :edit="boolParams"
                        textAlign="Center"
                      ></e-column>
                      <e-column
                        headerText="Quyền nhận"
                        field="quyen_kg"
                        width="120"
                        :template="this.cTemplateQuyennhan_added(this)"
                        editType="booleanedit"
                        :edit="boolParams"
                        textAlign="Center"
                      ></e-column>
                  </e-columns>
                </ejs-grid>
            </div>
        </div>
    </div>
    <div id='content114'>
      <div class="box-form">
            <div class="legend-title">Danh sách nhân viên chưa gán</div>
            <div class="box-move-select-table">
                <div class="actions w40 padl0">
                    <button class="btn" @click="removeEmployeesAddedFromList">
                        <span class="-ap icon-chevron-thin-right"></span>
                    </button>
                    <button class="btn" @click="moveEmployeesToList">
                        <span class="-ap icon-chevron-thin-left"></span>
                    </button>
                </div>
                <div class="box-col" style="width:calc(100% - 40px);">
                    <div style="margin: 0px 0px 10px 0px;">
                      <!-- <ejs-dropdownlist
                        :allowFiltering="true"
                        v-model="selectedDepartmentNotAddedId"
                        :filtering="onFilteringDepartmentsNotAdded"
                        :placeholder="'Chọn đơn vị'"
                        :dataSource="departmentsNotAdded"
                        :fields="{ text: 'TEN_DV', value: 'DONVI_ID' }"/> -->
                        <ComboboxGrid2  class="select-custom"
                            :columns="[                                              
                            {fieldName: 'DONVI_ID', headerText: '', allowFiltering: true, width:'0'},
                            {fieldName: 'TEN_DV', headerText: 'Tên đơn vị', allowFiltering: true, width:'200'},      
                            {fieldName: 'TEN_DV_CHA', headerText: 'Đơn vị cha', allowFiltering: true, width:'300'}
                            ]"
                            :dataSource="departmentsNotAdded" textField="TEN_DV" valueField="DONVI_ID"
                            :enabledSelectFirstRow="false" :value="selectedDepartmentNotAddedId"
                            @selectedChanged="(item)=>{if(item) { selectedDepartmentNotAddedId=item.DONVI_ID;}}">
                        </ComboboxGrid2>
                    </div>
                    <div class="table-content grid-ext grid-ext-freeze" style="max-height: 248px;">
                      <ejs-grid ref="gridNotAdded"
                      :dataSource="dataGridNotAdded.dataSource"
                      :allowGrouping="true"
                      :editSettings="editSettings"
                      :groupSettings="dataGridNotAdded.groupOptions"
                      :allowSorting='false'
                      :allowPaging='true'
                      :pageSettings='pageSettings'
                      :allowFiltering='true'
                      :selectionSettings='dataGridNotAdded.selectionOptions'
                      :pagerTemplate='pagerTemplate'>
                        <e-columns>
                            <e-column type='checkbox' width='50'></e-column>
                            <e-column field='ten_dv' headerText='Tên đơn vị' width='120'></e-column>
                            <e-column field='ma_nv' isPrimaryKey='true' headerText='Mã NV' width='120'></e-column>
                            <e-column field='ten_nv' headerText='Tên NV' width='180'></e-column>
                            <e-column
                              headerText="Quyền gửi"
                              field="quyen_kg"
                              width="120"
                              :template="this.cTemplateQuyengui(this)"
                              editType="booleanedit"
                              :edit="boolParams"
                              textAlign="Center"
                            ></e-column>
                            <e-column
                              headerText="Quyền nhận"
                              field="quyen_kg"
                              width="120"
                              :template="this.cTemplateQuyennhan(this)"
                              editType="booleanedit"
                              :edit="boolParams"
                              textAlign="Center"
                            ></e-column>
                        </e-columns>
                      </ejs-grid>
                    </div>
                </div>
            </div>
  
        </div>
    </div>
      
  </div>
  </template>
  
  <script>
  import Vue from "vue";
  import { GridComponent, ColumnsDirective, ColumnDirective, Group, Sort, Page, Filter,Edit } from '@syncfusion/ej2-vue-grids'
  import { ButtonPlugin, CheckBoxPlugin } from "@syncfusion/ej2-vue-buttons";
  import { mapActions, mapGetters } from 'vuex'
  import mixinStore from '../mixin/index'
  import {Predicate, Query} from '@syncfusion/ej2-data'
  import ComboboxGrid2 from '@/components/Controls/ComboboxGrid2'
  Vue.use(CheckBoxPlugin);
  Vue.use(ButtonPlugin);
  
  export default {
    components: {
      'ejs-grid': GridComponent,
      'e-columns': ColumnsDirective,
      'e-column': ColumnDirective,
      ComboboxGrid2
    },
    provide: {
      grid: [Group, Sort, Page, Filter,Edit]
    },
    data () {
      return {
        dataGridAdded: {
          groupOptions: { showDropArea: false, columns: ['ten_dv'] },
          // pageOptions: { pageSize: 10, pageCount: 5 },
          selectionOptions: { type: 'Multiple',checkboxOnly: true },
          dataSource: []
        },
        dataGridNotAdded: {
          groupOptions: { showDropArea: false, columns: ['ten_dv'] },
          // pageOptions: { pageSize: 10, pageCount: 5 },
          selectionOptions: { type: 'Multiple',checkboxOnly: true },
          dataSource: []
        },
        selectedEmployeesNotAdded: [],
        selectedDepartmentNotAddedId: -1,
        departmentsNotAdded: [],
        departmentsAdded: [],
        selectedDepartmentAddedId: null,
        editSettings: {
          allowEditing: true,
          allowAdding: true,
          allowDeleting: true
          // mode: "Dialog"
        },
        boolParams: {
          params: { change: this.checkBoxChange } // define up the change event to the check box in edit mode.
        },
        list_qg:[],
        list_qn:[],
      }
    },
    computed: {
      ...mapGetters('employeesCommon', [
        'getSelectedEmployeesAdded',
        'getEmployeesNotAdded',
        'getEmployeesAdded'
      ]),
      ...mapGetters('storesCommon', [
        'getSelectedStore',
        'getSelectedDepartment',
        'getDepartmentsCustomName'
      ])
    },
    watch: {
      getDepartmentsCustomName: function (newValue, oldValue) {      
        this.departmentsNotAdded = Array.from([{ TEN_DV: 'Vui lòng chọn', DONVI_ID: -1 }, ...newValue])
        
      },
      getEmployeesNotAdded: function (newValue, oldValue) {
        this.dataGridNotAdded.dataSource = [...Array.from(newValue)]
        this.dataGridAdded.dataSource = [...Array.from(this.getEmployeesAdded)]
  
        // // filter departments
        // const originalList = newValue.map(i => {
        //   return {
        //     donvi_id: i.donvi_id,
        //     ten_dv: `${i.donvi_id} - ${i.ten_dv}`
        //   }
        // })
  
        // // remove duplicate element
        // const list = []
        // for (let i = 0; i < originalList.length; i++) {
        //   const item = originalList[i]
        //   if (!list.find(l => l.donvi_id === item.donvi_id)) {
        //     list.push(item)
        //   }
        // }
        // this.departmentsNotAdded = list
      },
      getEmployeesAdded: function (newValue, oldValue) {
        this.dataGridNotAdded.dataSource = [...Array.from(this.getEmployeesNotAdded)]
        this.dataGridAdded.dataSource = [...Array.from(newValue)]
  
        // filter departments
        const originalList = newValue.map(i => {
          return {
            donvi_id: i.ten_dv,
            ten_dv: `${i.donvi_id} - ${i.ten_dv}`
          }
        })
  
        // remove duplicate element
        const list = []
        for (let i = 0; i < originalList.length; i++) {
          const item = originalList[i]
          if (!list.find(l => l.donvi_id === item.donvi_id)) {
            list.push(item)
          }
        }
        this.departmentsAdded = list
      },
      selectedDepartmentNotAddedId: async function (newValue, oldValue) {
        //this.filterEmployeesNotAddedByDepartment(newValue)
        await this.loadEmployeesNotAdded({
          donvi_id: newValue,
          kho_id: this.getSelectedStore.kho_id,
          phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
        })
      },
      selectedDepartmentAddedId: function (newValue, oldValue) {
        this.filterEmployeesAddedByDepartment(newValue)
      }
    },
    mixins: [mixinStore],
    methods: {
      ...mapActions('employeesCommon', [
        'addEmployeesToEmployeesAdded',
        'removeEmployeesFromEmployeesNotAdded',
        'addEmployeesToEmployeesNotAdded',
        'removeEmployeesFromEmployeesAdded',
        'loadEmployeesNotAdded',
        'loadEmployeesAdded'
      ]),
      ...mapActions('storesCommon', [
        'insertEmployeesToStore',
        'deleteEmployeesFromStore'
      ]),
      cTemplateQuyengui(parent) { return function() {
          return {
            template: Vue.component("columnTemplate", {
              template: `<div class="template_checkbox" >
                  <ejs-checkbox  :checked="model==true" v-model="model"></ejs-checkbox>
              </div>`,
              data: function() {
                return {
                  model: {}
                };
              },
              computed: {              
              },
              methods: {},
              mounted() {
                this.model = (this.data.quyen_kg == 0 ? false:true);
              },
              watch: {
                model: async function(newval, oldval){                            
                    if (oldval!=true && oldval!=false) return                  
                    
                    this.data.quyen_kg = (newval==true?1:0);                  
                    try {
                      let index = parent.dataGridNotAdded.dataSource.findIndex(item => item.nhanvien_id == this.data.nhanvien_id)
                      if (newval==true){                      
                        parent.list_qg.push(this.data)
                      }
                      else {
                        let index1 = list_qg.findIndex(item => item.nhanvien_id == this.data.nhanvien_id)
                        if (index1 >= 0){
                          parent.list_qg.splice(index1)                        
                        }
                      }
                        
                    }
                    catch(ex){
  
                    }
                  
                },
              }
            })
          };
        }
      },
      cTemplateQuyennhan(parent) { return function() {
          return {
            template: Vue.component("columnTemplate", {
              template: `<div class="template_checkbox" >
                  <ejs-checkbox  :checked="model==true" v-model="model"></ejs-checkbox>
              </div>`,
              data: function() {
                return {
                  model: {}
                };
              },
              computed: {              
              },
              methods: {},
              mounted() {
                this.model = (this.data.quyen_kn == 0 ? false:true);
              },
              watch: {
                model: async function(newval, oldval){                            
                    if (oldval!=true && oldval!=false) return                  
                    
                    this.data.quyen_kn = (newval==true?1:0);
                    
                    try {
                      let index = parent.dataGridNotAdded.dataSource.findIndex(item => item.nhanvien_id == this.data.nhanvien_id)
                      if (newval==true){                      
                        parent.list_qn.push(this.data)                      
  
                      }
                      else {
                        let index1 = list_qn.findIndex(item => item.nhanvien_id == this.data.nhanvien_id)
                        if (index1 >= 0){
                          parent.list_qn.splice(index1)                        
                        }
                      }                    
                    }
                    catch(ex){
  
                    }
                },
              }
            })
          };
        }
      },
      cTemplateQuyengui_added(parent) { return function() {
          return {
            template: Vue.component("columnTemplate", {
              template: `<div class="template_checkbox" >
                  <ejs-checkbox  :checked="model==true" v-model="model"></ejs-checkbox>
              </div>`,
              data: function() {
                return {
                  model: {}
                };
              },
              computed: {              
              },
              methods: {},
              mounted() {
                this.model = (this.data.quyen_kg == 0 ? false:true);
              },
              watch: {
                
              }
            })
          };
        }
      },
      cTemplateQuyennhan_added(parent) { return function() {
          return {
            template: Vue.component("columnTemplate", {
              template: `<div class="template_checkbox" >
                  <ejs-checkbox  :checked="model==true" v-model="model"></ejs-checkbox>
              </div>`,
              data: function() {
                return {
                  model: {}
                };
              },
              computed: {              
              },
              methods: {},
              mounted() {
                this.model = (this.data.quyen_kn == 0 ? false:true);
              },
              watch: {
                
              }
            })
          };
        }
      },
     
      checkBoxChange(args) {      
        var formObj = args.event.target.closest(".e-gridform").ej2_instances[0];
        var emPloyeeIdInput = formObj.element[1].ej2_instances[0]; // get the instance of the input element from form element.
        emPloyeeIdInput.enabled = args.checked; // assaign enable disable to textbox.
      },
      reloadEmployeesData: async function () {
        try {
          //this.selectedDepartmentNotAddedId = -1
          if (this.selectedDepartmentNotAddedId != -1){
            await this.loadEmployeesNotAdded({
              donvi_id: this.selectedDepartmentNotAddedId,
              kho_id: this.getSelectedStore.kho_id,
              phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
            })
          }
          await this.loadEmployeesAdded({
            kho_id: this.getSelectedStore.kho_id
          })
        } catch (error) {
          console.log(error)
          this.$toast.error(error.toString())
        }
      },
      moveEmployeesToList: async function () {
        this.$root.showLoading(true)
        try {
          
          let data = JSON.parse(JSON.stringify(this.$refs.gridNotAdded.getSelectedRecords()))        
  
          if (data && data.length > 0) {
            // this.addEmployeesToEmployeesAdded(data)
            for (let i = 0;i< data.length; i++){
              let index = this.list_qg.findIndex(item => item.nhanvien_id == data[i].nhanvien_id)
              if (index>=0)
                data[i].quyen_kg=1
              index = this.list_qn.findIndex(item => item.nhanvien_id == data[i].nhanvien_id)
              if (index>=0)
                data[i].quyen_kn=1
            }
            // uppercase key
            const upperCaseKeyData = this.upperCaseKey(data)
            await this.insertEmployeesToStore(upperCaseKeyData)
            await this.reloadEmployeesData()
            this.list_qg = []
            this.list_qn = []
          }
        } catch (error) {
          console.log(error)
          this.$toast.error(error.toString())
        }
        this.$root.showLoading(false)
      },
      removeEmployeesAddedFromList: async function () {
        this.$root.showLoading(true)
        try {
          const data = this.$refs.gridAdded.getSelectedRecords()
          if (data && data.length > 0) {
            // this.removeEmployeesFromEmployeesAdded(data)
  
            // uppercase key
            const upperCaseKeyData = this.upperCaseKey(data)
            await this.deleteEmployeesFromStore(upperCaseKeyData)
            await this.reloadEmployeesData()
          }
        } catch (error) {
          console.log(error)
          this.$toast.error(error.toString())
        }
        this.$root.showLoading(false)
      },
      onFilteringDepartmentsNotAdded: function (e) {
        const query = new Query()
        // frame the query based on search string with filter type.
        if (e.text.trim() !== '') {
          const predicate = new Predicate('TEN_DV', 'contains', e.text.trim(), true)
          query.where(predicate)
        }
        // pass the filter data source, filter query to updateData method.
        e.updateData(this.departmentsNotAdded, query)
      },
      onFilteringDepartmentsAdded: function (e) {
        const query = new Query()
        // frame the query based on search string with filter type.
        if (e.text.trim() !== '') {
          const predicate = new Predicate('ten_dv', 'contains', e.text.trim(), true)
          query.where(predicate)
        }
        // pass the filter data source, filter query to updateData method.
        e.updateData(this.departmentsAdded, query)
      },
      filterEmployeesNotAddedByDepartment: function (selectedDepartmentId) {
        const data = selectedDepartmentId !== null && selectedDepartmentId !== undefined
          ? this.getEmployeesNotAdded.filter(i => i.donvi_id === selectedDepartmentId) : this.getEmployeesNotAdded
        this.dataGridNotAdded.dataSource = Array.from(data)
      },
      filterEmployeesAddedByDepartment: function (selectedDepartmentId) {
        const data = selectedDepartmentId !== null && selectedDepartmentId !== undefined
          ? this.getEmployeesAdded.filter(i => i.donvi_id === selectedDepartmentId) : this.getEmployeesAdded
        this.dataGridAdded.dataSource = Array.from(data)
      }
    }
  }
  
  </script>
  <style>
    .combobox-grid .grid-panel {    
      max-width: 200px;
      overflow: scroll;
    } 
  </style>
