import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'

import {required} from 'vuelidate/lib/validators'

export default function (args,color) {

  return {
    key: `${args.FIELD_NAME}`,
    template: `
      <div class="row" style="width: 100%">
        <div class="text-body col-6" :style="color" >
            {{ caption }}
        </div>
        <div class="text-body mr-0 pr-0 col-6" >
            <input type="text" v-model="value" class="form-control" disabled="false">
        </div>
      </div>
    `,
    data() {
      return {
        caption: args.TEN_HIENTHI,
        value: args.FIELD_VALUE
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
