
export default function (args,loaitb_id,isEnable,color) {
  return {
    key: `${args.FIELD_NAME}`,
    template: `

      <div class="row" style="width: 100%">
        <div class="text-body col-6" :style="color">
            {{ caption }}
        </div>
          <div class="text-body mr-0 pr-0 col-6" >

<!--            <ComboboxGrid :ref="'ComboboxGrid-'+ FIELD_NAME" v-bind:columns="columns"-->
<!--                          v-bind:dataSource="data"-->
<!--                          :value="value"-->
<!--                          :showColumnCheckbox="true"-->
<!--                          :enabledSelectFirstRow="false"-->
<!--                          :valueField="KEY_FIELD" :textField="KEY_VALUE"-->
<!--                          @selectedChanged="cbbDslam_selectedChanged"-->
<!--            >-->
<!--            </ComboboxGrid>-->

            <treeselect
              :ref="'ComboboxGrid-'+ FIELD_NAME"
              :multiple="true"
              v-model="idArgs"
              :options="this.treeSelectOptions"
              placeholder="..."/>
        </div>
      </div>
    `,

    data() {
      return {
        caption : args.TEN_HIENTHI,
        value : args.FIELD_VALUE,
        FIELD_NAME : args.FIELD_NAME,
        sql: args.LOOKUP_SQL,
        data:[],
        KEY_FIELD :'',
        KEY_VALUE :'',
        columns :[],
        idArgs :[],
        enable: true
      }
    },methods:{
      cbbDslam_selectedChanged: async function (data) {
        this.value = data.map(field => field[this.KEY_FIELD])
      },
      // setComboboxGrid (value) {
      //   this.$nextTick(() => {
      //     let grid = this.$refs['ComboboxGrid-'+ this.FIELD_NAME].$children[0].grid
      //     let idx = this.data.findIndex(item => item[this.KEY_FIELD] == value)
      //     setTimeout(() => {  if (idx > -1) grid.selectRow(idx) }, 300)
      //   })
      // },
      getData : async function(){
        let rs = await this.$root.context.post('/web-cabman/daunoi-thietbi/lay-data-sql-thuoctinh', {
          loai_tbi: loaitb_id,
          opt_list: "00000",
          field_name: args.FIELD_NAME,
          ten_bang: "HDTB_CNTT"
        });
        this.data = rs.data
        this.KEY_FIELD = Object.keys(this.data[0])[0]
        this.KEY_VALUE = Object.keys(this.data[0])[1]
        Object.keys(this.data[0]).forEach((item,i)=> {
          this.columns.push( {fieldName: item, headerText: '', allowFiltering: true, width: '50%'})
        })
        this.enable = isEnable
      }
    } ,mounted() {
      try {

        console.log('args.FIELD_VALUE',args.FIELD_VALUE)
        // this.value = args.FIELD_VALUE
        //this.idArgs = [...args.FIELD_VALUE.split(',').map(item => parseInt(item))]

        this.getData().then(r => {
          // this.value = Array.from(args.FIELD_VALUE.split(','),Number)
        //  this.setComboboxGrid(this.value)

        })

      } catch (error) {

      } finally {
        this.loading(false);
      }
    },watch : {
      'idArgs' (val) {
        args.FIELD_VALUE = val.toString()

      }
    },computed :{
      color() {
        return{
          background : color ===1 ? '#fff' : '#F1F8FF'
        }
      },treeSelectOptions () {
        console.log(this.data)
        return this.data.map(item => { return { id:  item[this.KEY_FIELD], label:item[this.KEY_VALUE] }})
      },onChange(data){
        console.log(data)
      }
    }
  }
}
