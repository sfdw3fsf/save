
import {required} from 'vuelidate/lib/validators'

export default function (args,color) {

  return {
    key: `${args.FIELD_NAME}`,
    template: `
      <div class="row" style="width: 100%">
      <div class="text-body col-6" :style="color">
            {{ caption }}
        </div>
      <div class="text-body mr-0 pr-0 col-6" >
            <input type="text" v-model="value" :maxlength="length" :ref="'COMPO-'+FIELD_NAME" class="form-control">
        </div>
      </div>
    `,
    data() {
      return {
        caption: args.TEN_HIENTHI,
        value: args.FIELD_VALUE,
        length: args.FIELD_LENGTH,
        FIELD_NAME: args.FIELD_NAME,
      }
    }, watch : {
      'value' (val) {
        args.FIELD_VALUE = val
      }
    },computed :{
      color() {
        return{
          background : color ===1 ? '#fff' : '#F1F8FF'
        }
      }
    },methods:{
      valueChange: function (value)  {
        console.log('value Change ', value)
      }
    }
  }
}
