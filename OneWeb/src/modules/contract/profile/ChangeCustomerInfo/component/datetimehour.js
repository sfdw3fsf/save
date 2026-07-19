
import moment from 'moment'

export default function (args,color) {
  return {
    key: `${args.FIELD_NAME}`,
    template: `
      <div class="row" style="width: 100%">
      <div class="text-body col-6" :style="color" >
            {{ caption }}
        </div>
      <div class="text-body mr-0 pr-0 col-6" >
          <ejs-datepicker
            :ref="'COMPO-'+FIELD_NAME"
            v-model="startDate"
            :format="dateFormat"
          ></ejs-datepicker>
        </div>
      </div>
    `,
    data() {
      return {
        caption : args.TEN_HIENTHI,
        id : args.FIELD_VALUE,
        dateFormat: 'dd/MM/yyyy hh:mm:ss',
        startDate: args.FIELD_VALUE  ,
        data:[],
        KEY_FIELD :'',
        KEY_VALUE :'',
        FIELD_NAME : args.FIELD_NAME,

      }
    },methods:{

    }, watch : {
      'startDate' (val) {
      //  args.FIELD_VALUE = val
        args.FIELD_VALUE = val
      }
    },mounted() {
      try {


      } catch (error) {

      } finally {
        this.loading(false);
      }
    },computed :{
      color() {
        return{
          background : color ===1 ? '#fff' : '#F1F8FF'
        }
      }
    }
  }
}
