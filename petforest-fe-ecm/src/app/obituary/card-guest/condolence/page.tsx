'use client';

import useResize from '@/hook/useResize';
import { createObituaryCouponApi, createObituaryMobileRegisterApi } from '@/store/obituary/obituary.api';
import { ObituaryCouponCreateREQ, ObituaryMobileRegisterREQ } from '@/store/obituary/request/obituary.request';
import { ObituaryMobileRegisterRESP } from '@/store/obituary/response/obituary.response';
import { orString } from '@/utils/parser/io.parser';
import { ChevronLeftIcon, ChevronRightIcon } from '@chakra-ui/icons';
import { Checkbox, Divider } from '@chakra-ui/react';
import { useMutation } from '@tanstack/react-query';
import { Input } from 'antd';
import { useRouter, useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useRef, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import '../../obituary.css';
import KCPPayment from './components/KCPPayment';
import KCPPayment_Mobile from './components/KCPPayment_Mobile';
const page = () => {
  const router = useRouter();
  const formRefs = useRef<(HTMLFormElement | null)[]>([]);
  const { control, handleSubmit, watch } = useForm<ObituaryCouponCreateREQ>({
    defaultValues: { purchaser: '', purchaserContact: '', amount: 10000 },
  });
  const searchParams = useSearchParams();
  const { isMobile } = useResize();

  const id: string = useMemo(() => {
    return orString(searchParams.get('id'));
  }, [searchParams]);

  const [selectedMoneyIndex, setSelectMoneyIndex] = useState<number>(0);
  const [isAgree, setIsAgree] = useState<boolean>(false);
  const [paymentCode, setPaymentCode] = useState<string>();
  const [registeredData, setRegisteredData] = useState<ObituaryMobileRegisterRESP>();

  const amounts = [10000, 20000, 30000, 50000, 100000];

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://pay.kcp.co.kr/plugin/kcp_spay_hub.js';
    script.async = true;
    document.body.appendChild(script);
    return () => {
      document.body.removeChild(script);
    };
  }, []);

  const jsf__pay = (form: HTMLFormElement) => {
    try {
      (window as any).KCP_Pay_Execute_Web(form);
    } catch (e) {
      console.error('Payment error:', e);
    }
  };

  const watchValues = watch();

  const isActive = useMemo(() => {
    const values = Object.values(watchValues);

    return values.every((item) => item && item !== '') && isAgree;
  }, [watchValues, isAgree]);

  const { mutate: mutateCreateObituaryCoupon } = useMutation({
    mutationFn: ({ id, body }: { id: string; body: ObituaryCouponCreateREQ }) => createObituaryCouponApi(id, body),
    onSuccess: async (resp) => {
      setPaymentCode(resp.data.data.paymentCode);
    },
  });

  const { mutate: mutateObituaryMobileRegister } = useMutation({
    mutationFn: (body: ObituaryMobileRegisterREQ) => createObituaryMobileRegisterApi(body),
    onSuccess: async (resp) => {
      setRegisteredData(resp.data.data);
    },
  });

  const onDetailPersonalInformation = () => {
    router.push('condolence/personal-information');
  };

  const handleGlobalSubmit = async (values: ObituaryCouponCreateREQ) => {
    await mutateCreateObituaryCoupon({ id, body: { ...values, amount: amounts[selectedMoneyIndex] } });
  };

  const jsf__pay_mobile = (form: HTMLFormElement) => {
    try {
      (window as any).call_pay_form(form);
    } catch (e) {
      console.error('Payment error:', e);
    }
  };

  useEffect(() => {
    if (!paymentCode) return;
    const form = formRefs.current[selectedMoneyIndex];

    if (form) {
      if (isMobile) {
        mutateObituaryMobileRegister({
          orderId: paymentCode,
          productPrice: amounts[selectedMoneyIndex].toString(),
          productName: `prd_${amounts[selectedMoneyIndex]}`,
          payMethod: 'CARD',
          Ret_URL: 'https://pet-forest-api.wala-dev.com/api/v1/ecm/obituaries/payments/confirm',
          currency: '410',
        });
      } else {
        jsf__pay(form);
      }
    }
  }, [paymentCode, formRefs.current, selectedMoneyIndex]);

  useEffect(() => {
    if (!registeredData) return;
    const form = formRefs.current[selectedMoneyIndex];
    if (form) {
      jsf__pay_mobile(form);
    }
  }, [registeredData]);

  return (
    <div className='flex flex-col text-center items-center px'>
      <div className='flex items-center w-full relative bg-grey-pearl py-6'>
        <button
          className='absolute left-[20px] top-2/4 -translate-y-2/4'
          onClick={() => {
            router.back();
          }}>
          <ChevronLeftIcon w={7} h={7} />
        </button>

        <div className=' w-full text-[17px] font-bold '>마음을 전하세요</div>
      </div>
      <div className='w-full max-w-[480px] mt-[15px] '>
        <div className='flex flex-col gap-1'>
          <div className='text-[16px] font-medium text-grey-text'>보호자</div>
          <div className='text-[20px] font-bold'>테스트</div>
          <div className='text-[15px] text-green-main'>
            결제된 조의금은 펫포레스트 장례시 <br /> 전액 장례비용으로 정산됩니다.
          </div>
        </div>

        <div className='mt-[42px] w-full flex justify-center'>
          {!isMobile && (
            <KCPPayment
              formRefs={formRefs}
              selectedMoneyIndex={selectedMoneyIndex}
              onChange={(index: number) => {
                setSelectMoneyIndex(index);
              }}
              paymentCode={paymentCode}
            />
          )}
          {isMobile && (
            <KCPPayment_Mobile
              formRefs={formRefs}
              selectedMoneyIndex={selectedMoneyIndex}
              onChange={(index: number) => {
                setSelectMoneyIndex(index);
              }}
              orderId={paymentCode}
              buyerName={watchValues.purchaser}
              telephone={watchValues.purchaserContact}
              data={registeredData}
            />
          )}
        </div>

        <Divider borderWidth={3} className='mt-[30px]' />

        <Controller
          name='purchaser'
          control={control}
          render={({ field: { onChange, value } }) => (
            <Input
              value={value}
              onChange={onChange}
              type='text'
              className='input mt-3'
              placeholder='보내시는 분 성함을 입력 해주세요.'
            />
          )}
        />

        <div className='flex gap-2 '>
          <div className='input mt-3 !w-[80px]'>010</div>
          <Controller
            name='purchaserContact'
            control={control}
            render={({ field: { onChange, value } }) => (
              <Input
                value={value}
                onChange={onChange}
                type='text'
                className='input mt-3'
                placeholder='-없이 휴대폰 번호를 입력 해주세요.'
              />
            )}
          />
        </div>

        <div className='flex items-center justify-between mt-[30px] px-5'>
          <div className='flex items-center gap-3 '>
            <Checkbox
              isChecked={isAgree}
              onChange={(e) => {
                setIsAgree(e.target.checked);
              }}
              size={'lg'}
              _checked={{
                '& .chakra-checkbox__control': { background: '#0d4221', borderColor: '#0d4221' },
                '& .chakra-checkbox__control:hover': { background: '#0d4221', borderColor: '#0d4221' },
              }}
              iconColor='white'
            />
            <span className='text-[14px] text-grey-text'>개인정보 취급방침 동의 (필수)</span>
          </div>
          <button onClick={onDetailPersonalInformation} className='flex justify-center items-center'>
            <ChevronRightIcon w={5} h={5} />
          </button>
        </div>

        <div className='mt-[20px] w-full h-[120px] border border-[#bfcdc4] rounded-lg overflow-auto p-[20px_20px_30px] text-[10px] text-grey-text text-left'>
          <span className='text-[11px] font-semibold text-black-nero'>조의금 이용안내</span>
          <p>
            조의금은 최대 30만원까지 받을 수 있습니다. 받는 분의 조의금 총 금액이 30만원을 초과할 시 전송이 불가할 수 있습니다.
          </p>
          <p>조의금은 장례시작 후 1시간 30분이 지나기 전까지 전송하실 수 있습니다.</p>
          <span className='text-[11px] font-semibold text-black-nero'>조의금 사용방법 안내</span>
          <p>[받는 분]</p>
          <p>조의금 송금 시 받는 분의 카카오톡 알림톡으로 [보내신 분 성함/금액]내역이 전송됩니다.</p>
          <p>송금된 조의금은 펫포레스트 장례 금액 정산시 포함하여 계산합니다.</p>
          <p>[보내시는 분]</p>
          <p>조의금 송금 시 보내시는 분의 카카오톡 알림톡으로 [성함/금액] 내용이 전송됩니다.</p>
          <span className='text-[11px] font-semibold text-black-nero'>조의금 유의사항</span>
          <p>- 본 조의금은 펫포레스트 전 지점에서 사용이 가능합니다.</p>
          <p>- 본 조의금은 카드사 등 기타 할인 혜택과 중복 사용이 가능합니다.</p>
          <p>- 본 조의금은 현금으로 교환이 불가합니다.</p>
          <p>- 본 조의금은 타인에게 양도 및 증여가 불가능합니다.</p>
          <p>- 조의금을 보낸 후에는 환불이 불가하오니 신중한 구매 바랍니다.</p>
          <p>- 조의금 관련 문의는 펫포레스트 고객센터로 연락 바랍니다.</p>
          <p>※펫포레스트 고객센터 : cs@petforest.co.kr / 1577-0996</p>
        </div>

        <button disabled={!isActive} onClick={handleSubmit(handleGlobalSubmit)} className='btn-primary-obituary mt-[22px]'>
          조의금 전송
        </button>
      </div>
    </div>
  );
};

export default page;
