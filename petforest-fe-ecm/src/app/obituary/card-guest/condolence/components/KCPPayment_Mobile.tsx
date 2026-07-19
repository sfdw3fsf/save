import { ListData } from '@/components/list-data/ListData';
import { ObituaryMobileRegisterRESP } from '@/store/obituary/response/obituary.response';
import { MutableRefObject, useEffect } from 'react';

type KCPPayment_MobileProps = {
  selectedMoneyIndex: number;
  onChange: (index: number) => void;
  formRefs: MutableRefObject<(HTMLFormElement | null)[]>;
  orderId?: string;
  buyerName?: string;
  telephone?: string;
  data?: ObituaryMobileRegisterRESP;
};

const KCPPayment_Mobile = ({
  selectedMoneyIndex,
  onChange,
  formRefs,
  orderId,
  buyerName,
  telephone,
  data,
}: KCPPayment_MobileProps) => {
  const amounts = [10000, 20000, 30000, 50000, 100000];

  useEffect(() => {
    const script = document.createElement('script');
    script.innerHTML = `
      function reqPaySubmit() {
        var frm = document.order_info;
        document.getElementById("PAY_BTN").style.display = "none";
        frm.action = "https://pay.kcp.co.kr/support/msson/en/plugin/order_approval.jsp";
        frm.submit();
      }

      function call_pay_form(form) {
      var v_frm_name = form.name
        var v_frm = document[v_frm_name];
        v_frm.action = v_frm.PayUrl.value;

        if (v_frm.Ret_URL.value === "") {
          alert("Ret_URL");
          return false;
        } else {
          v_frm.submit(); 
        }
      }

      function chk_pay() {
        self.name = "tar_opener";
        var pay_form = document.pay_form;

        if (pay_form.res_cd.value === "3001") {
          alert("User cancel.");
          pay_form.res_cd.value = "";
        }

        if (pay_form.enc_info.value) {
          pay_form.submit();
        }
      }
    `;
    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script);
    };
  }, []);

  const jsf__pay = (form: HTMLFormElement) => {
    try {
      (window as any).call_pay_form(form);
    } catch (e) {
      console.error('Payment error:', e);
    }
  };

  const submit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const form = event.currentTarget;
    jsf__pay(form);

    return false;
  };

  const renderCondolenceMoney = (item: number, index: number) => {
    return (
      <form
        key={index}
        ref={(element) => {
          formRefs.current[index] = element;
        }}
        name={`order_info_${index}`}
        method='post'
        onSubmit={submit}>
        <input type='hidden' name='ordr_idxx' value={orderId} />
        <input type='hidden' name='good_name' value={`prd_${item}`} />
        <input type='hidden' name='good_mny' value={item} />
        <input type='hidden' name='buyr_name' value={buyerName} />
        <input type='hidden' name='buyr_tel2' value={telephone} />

        <input type='hidden' name='pay_method' value='CARD' />

        <input type='hidden' name='req_tx' value='pay' />
        <input type='hidden' name='shop_name' value='TEST SITE' />
        <input type='hidden' name='site_cd' value='T0000' />
        <input type='hidden' name='currency' value='410' />

        <input type='hidden' name='Ret_URL' value='https://pet-forest-api.wala-dev.com/api/v1/ecm/obituaries/payments/confirm' />

        <input type='hidden' name='approval_key' id='approval' value={data?.additionalData.approvalKey} />
        <input type='hidden' name='traceNo' value={data?.additionalData.traceNo} />
        <input type='hidden' name='PayUrl' value='https://testsmpay.kcp.co.kr/pay/mobileGW.kcp' />
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

export default KCPPayment_Mobile;
