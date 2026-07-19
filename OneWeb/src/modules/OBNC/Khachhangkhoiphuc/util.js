import _ from 'lodash';
import moment from 'moment'

const item_test = {
  Uuid: '21529f3a-9672-4877-bb5e-512b00689946',
  campaign_id: '675',
  bot_campaign_name: 'Test_Chiến dịch OB AutoCall tập thuê bao HVC – Bán gói',
  user_ipcc: 'thuynt7',
  auto_call: 1,
  SoThueBao: '0842075768',
  PHANVUNG_ID: '28',
  MA_TINH: '28',
  OB_ID: 'data_3',
  SDT_THANH_TOAN: '0834727665',
  THUEBAO_ID: 'data_5',
  MA_TB: 'mylienpc12',
  LOAITB_ID: '58',
  TOCDO_ID: 'data_8',
  KHACHHANG_ID: '10148424',
  THANHTOAN_ID: 'data_10',
  TEN_KH: 'TRAN THI MAN',
  DIACHI_KH: 'DIACHI_KH',
  NGAY_BDDC: '2/21/2022',
  NGAY_KTDC: '2/21/2024',
  CUOC_DC: 'data_15',
  SO_THANGDC: 'data_16',
  CHITIETKM_ID: 'data_17',
  TEN_CTKM: 'data_18',
  SL_DATCOC: 'data_19',
  MATB_PHU: 'data_20'
}

class Util {
  decodeQuery(data) {
    if(!data) return {};
    try {
      let decode = atob(data)
      decode = decodeURIComponent(decode)
      decode = decode.split('=')[1].split('&')[0]
      decode = Buffer.from(decode, 'base64').toString('utf8')
      console.log('decode:', decode)
      // return item_test
      return JSON.parse(decode)
    } catch (error) {
      console.log(error);
      return {}
    }
    
  }
  //ma_tb em lấy thuê bao fiber, TH ko có fiber thì lấy bất kỳ thuê bao trong d/s các thuê bao cập nhật , các thuê bao còn lại sẽ truyền vào param matb_phu
  getItemReturn(listItemSelected) {
    if (!listItemSelected || listItemSelected.length === 0) return null;


    const itemFocus = listItemSelected.find(e => e.LOAITB_ID === '58') || listItemSelected[0];
    const maTbKt = listItemSelected.map(e => e.MA_TB).filter(c => c != itemFocus.MA_TB).join(';');

    return {
        MA_TB: itemFocus.MA_TB,
        LOAITB_ID: itemFocus.LOAITB_ID,
        MA_TB_KT: maTbKt,
        MA_GD: itemFocus?.MA_GD || '',
        DICVUVT_ID:  itemFocus?.DICVUVT_ID || '',
    };
  }
  mergeUnionByKey(...args) {

    const config = _.chain(args)
      .filter(_.isString)
      .value()
  
    const key = _.get(config, '[0]')
  
    const strategy = _.get(config, '[1]') === 'override' ? _.merge : _.defaultsDeep
  
    if (!_.isString(key))
      throw new Error('missing key')
  
    const datasets = _.chain(args)
      .reject(_.isEmpty)
      .filter(_.isArray)
      .value()
  
    const datasetsIndex = _.mapValues(datasets, dataset => _.keyBy(dataset, key))
  
    const uniqKeys = _.chain(datasets)
      .flatten()
      .map(key)
      .uniq()
      .value()
  
    return _.chain(uniqKeys)
      .map(val => {
        const data = {}
        _.each(datasetsIndex, dataset => strategy(data, dataset[val]))
        return data
      })
      .filter(key)
      .value()
  
  }

  //tích chọn  các thuê bao có ở tab D/S TB cần tư vấn em nhé (sửa ở OB version 2) trừ các thuê bao có ngay_ktdc = ngày cuối cùng của tháng t-1 nhé   
  getListIndexItemSelected(listItemSelected) {
    if (!listItemSelected || listItemSelected.length === 0) return [];
    //get last day of month - 1 with moment
    const lastDayOfMonth = moment().subtract(1, 'month').endOf('month').format('DD/MM/YYYY');
    return listItemSelected.filter(e => {
      return e.NGAY_KTDC !== lastDayOfMonth
    }).map((item, index) => index);
  }

}

export default new Util()
