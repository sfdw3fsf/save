import { ListData } from '@/components/list-data/ListData';
import { MutableRefObject } from 'react';

type KCPPaymentProps = {
  selectedMoneyIndex: number;
  onChange: (index: number) => void;
  formRefs: MutableRefObject<(HTMLFormElement | null)[]>;
  paymentCode?: string;
};
const KCPPayment = ({ selectedMoneyIndex, onChange, formRefs, paymentCode }: KCPPaymentProps) => {
  const amounts = [10000, 20000, 30000, 50000, 100000];

  const renderCondolenceMoney = (item: number, index: number) => {
    return (
      <form
        key={index}
        ref={(element) => {
          formRefs.current[index] = element;
        }}
        name={`order_info_${item}`}
        method='get'
        action='https://pet-forest-api.wala-dev.com/api/v1/ecm/obituaries/payments/confirm'
        style={{ marginBottom: '20px' }}>
        <input type='hidden' name='ordr_idxx' value={paymentCode} />
        <input type='hidden' name='good_name' value={`Product_${item}`} />
        <input type='hidden' name='good_mny' value={item} />
        <input type='hidden' name='pay_method' value='100000000000' />
        <input type='hidden' name='site_cd' value='AISW8' />
        <input type='hidden' name='site_key' value='3grptw1.zW0GSo4PQdaGvsF__' />
        <input type='hidden' name='site_name' value='펫포레스트' />
        <input type='hidden' name='res_cd' value='' />
        <input type='hidden' name='res_msg' value='' />
        <input type='hidden' name='enc_info' value='' />
        <input type='hidden' name='enc_data' value='' />
        <input type='hidden' name='ret_pay_method' value='' />
        <input type='hidden' name='tran_cd' value='' />
        <input type='hidden' name='use_pay_method' value='' />
        <button
          type='button'
          onClick={() => {
            onChange(index);
          }}
          className={`inline-flex w-full justify-center items-center h-10 ${selectedMoneyIndex === index ? 'bg-green-main text-white' : 'bg-grey-light'} border border-light-grey rounded-md text-[16px] font-bold`}>
          {item.toLocaleString() + '원'}
        </button>
      </form>
    );
  };

  return (
    <div className='payment-forms items-center justify-center flex'>
      <ListData
        data={amounts}
        keyExtractor={(item) => item}
        renderItem={renderCondolenceMoney}
        numberOfColumns={2}
        styleContainer='w-[250px]'
        gap={10}
      />
    </div>
  );
};

export default KCPPayment;
